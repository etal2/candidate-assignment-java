package ch.aaap.assignment.model;

import ch.aaap.assignment.model.CantonImpl.CantonImplBuilder;
import ch.aaap.assignment.model.DistrictImpl.DistrictImplBuilder;
import ch.aaap.assignment.model.PoliticalCommunityImpl.PoliticalCommunityImplBuilder;
import ch.aaap.assignment.model.PostalCommunityImpl.PostalCommunityImplBuilder;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a helper class to initialize the in memory Model.
 */
public class ModelFactory {

  private Set<CSVPoliticalCommunity> rawPoliticalCommunities;
  private Set<CSVPostalCommunity> rawPostalCommunities;

  private Map<String, CantonImplBuilder> cantonByCode;
  private Map<String, DistrictImplBuilder> districtByNumber;
  private Map<String, PoliticalCommunityImplBuilder> politicalCommunitiesByNumber;
  private Map<PostalCommunityKey, PostalCommunityImplBuilder> postalCommunitiesByZip;

  public ModelFactory(Set<CSVPoliticalCommunity> politicalCommunities,
      Set<CSVPostalCommunity> postalCommunities) {
          
    rawPoliticalCommunities = politicalCommunities;
    rawPostalCommunities = postalCommunities;
  }

  /**
   * Build the in memory model.
   * @return model
   */
  public Model build() {
    init();
    processData();
    return loadData2Model();
  }

  /**
   * Initialize helper structures.
   */
  private void init() {
    cantonByCode = new HashMap<>();
    districtByNumber = new HashMap<>();
    politicalCommunitiesByNumber = new HashMap<>();
    postalCommunitiesByZip = new HashMap<>();
  }

  /**
   * Process the CSV data and extract the model.
   */
  private void processData() {
    //Process cantons and districts
    rawPoliticalCommunities.forEach((CSVPoliticalCommunity csvPoliticalCom) -> {
      processCanton(csvPoliticalCom);
      processDistrict(csvPoliticalCom);
    });
        
    //Process political communities
    rawPoliticalCommunities.forEach((CSVPoliticalCommunity csvPoliticalCom) -> {
      processPoliticalCommunity(csvPoliticalCom);
    });

    //Process postal communities
    rawPostalCommunities.forEach((CSVPostalCommunity csvPostalCom) -> {
      processPostalCommunity(csvPostalCom);
    });
  }

  /**
   * Load the data into the model.
   * @return {@link ch.aaap.assignment.model.Model}
   */
  private Model loadData2Model() {
    ModelImpl model = new ModelImpl();
    cantonByCode.values().forEach(c -> model.addCanton(c.build()));
    districtByNumber.values().forEach(d -> model.addDistrict(d.build()));
    postalCommunitiesByZip.values().forEach(pc -> model.addPostalCommunity(pc.build()));
    politicalCommunitiesByNumber.values().forEach(pc -> model.addPoliticalCommunity(pc.build()));

    return model;
  }

  private void processPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCom) {
    politicalCommunitiesByNumber.put(csvPoliticalCom.getNumber(),
        toPoliticalCommunityBuilder(csvPoliticalCom));

    cantonByCode.get(csvPoliticalCom.getCantonCode())
      .districtNumber(csvPoliticalCom.getDistrictNumber())
      .politicalCommunityNumber(csvPoliticalCom.getNumber());

    districtByNumber.get(csvPoliticalCom.getDistrictNumber())
      .cantonCode(csvPoliticalCom.getCantonCode())
      .politicalCommunityNumber(csvPoliticalCom.getNumber());
  }

  private void processPostalCommunity(CSVPostalCommunity csvPostalCom) {
    PostalCommunityKey postalComKey = new PostalCommunityKey(csvPostalCom.getZipCode(),
        csvPostalCom.getZipCodeAddition());
    //handle multiple csv rows belonging to the same postal community
    postalCommunitiesByZip.computeIfAbsent(postalComKey,
        key -> toPostalCommunityBuilder(csvPostalCom))
      .cantonCode(csvPostalCom.getCantonCode())
      .politicalCommunityNumber(csvPostalCom.getPoliticalCommunityNumber());
        
    politicalCommunitiesByNumber.get(csvPostalCom.getPoliticalCommunityNumber())
      .postalCommunityKey(postalComKey);
  }

  private void processDistrict(CSVPoliticalCommunity csvPoliticalCom) {
    districtByNumber.computeIfAbsent(csvPoliticalCom.getDistrictNumber(), key -> {
      return DistrictImpl.builder()
        .name(csvPoliticalCom.getDistrictName())
        .number(csvPoliticalCom.getDistrictNumber());
    });
  }

  private void processCanton(CSVPoliticalCommunity csvPoliticalCom) {
    cantonByCode.computeIfAbsent(csvPoliticalCom.getCantonCode(), key -> {
      return CantonImpl.builder()
        .name(csvPoliticalCom.getCantonName())
        .code(csvPoliticalCom.getCantonCode());
    });
  }

  private PostalCommunityImplBuilder toPostalCommunityBuilder(CSVPostalCommunity csvPostalCom) {
    return PostalCommunityImpl.builder()
        .name(csvPostalCom.getName())
        .zipCode(csvPostalCom.getZipCode())
        .zipCodeAddition(csvPostalCom.getZipCodeAddition());
  }

  private PoliticalCommunityImplBuilder toPoliticalCommunityBuilder(
      CSVPoliticalCommunity csvPoliticalCom) {
    
    return PoliticalCommunityImpl.builder()
        .name(csvPoliticalCom.getName())
        .number(csvPoliticalCom.getNumber())
        .shortName(csvPoliticalCom.getShortName())
        .lastUpdate(csvPoliticalCom.getLastUpdate())
        .cantonCode(csvPoliticalCom.getCantonCode())
        .districtNumber(csvPoliticalCom.getDistrictNumber());
  }
}
