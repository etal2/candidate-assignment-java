package ch.aaap.assignment.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.aaap.assignment.model.CantonImpl.CantonImplBuilder;
import ch.aaap.assignment.model.DistrictImpl.DistrictImplBuilder;
import ch.aaap.assignment.model.PoliticalCommunityImpl.PoliticalCommunityImplBuilder;
import ch.aaap.assignment.model.PostalCommunityImpl.PostalCommunityImplBuilder;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

public class ModelFactory {

    private Set<CSVPoliticalCommunity> rawPoliticalCommunities;
    private Set<CSVPostalCommunity> rawPostalCommunities;

    private Map<String, CantonImplBuilder> cantonByCode;
    private Map<String, DistrictImplBuilder> districtByNumber;
    private Map<String, PoliticalCommunityImplBuilder> politicalCommunitiesByNumber;
    private Map<PostalCommunityKey, PostalCommunityImplBuilder> postalCommunitiesByZip;

    public ModelFactory(Set<CSVPoliticalCommunity> politicalCommunities, Set<CSVPostalCommunity> postalCommunities) {
        rawPoliticalCommunities = politicalCommunities;
        rawPostalCommunities = postalCommunities;
    }

    public Model build() {
        init();
        processData();
        return loadData2Model();
    }

    private void init() {
        cantonByCode = new HashMap<>();
        districtByNumber = new HashMap<>();
        politicalCommunitiesByNumber = new HashMap<>();
        postalCommunitiesByZip = new HashMap<>();
    }

    private void processData(){
        //Process cantons and districts
        rawPoliticalCommunities.forEach((CSVPoliticalCommunity csvPoliticalCom) -> {
            addCanton(csvPoliticalCom);
            addDistrict(csvPoliticalCom);
        });
        
        //Process political communities
        rawPoliticalCommunities.forEach((CSVPoliticalCommunity csvPoliticalCom) -> {
            addPoliticalCommunity(csvPoliticalCom);
        });

        //Process postal communities
        rawPostalCommunities.forEach((CSVPostalCommunity csvPostalCom) -> {
            addPostalCommunity(csvPostalCom);
        });
    }

    private Model loadData2Model(){
        ModelImpl model = new ModelImpl();
        cantonByCode.values().forEach(c -> model.addCanton(c.build()));
        districtByNumber.values().forEach(d -> model.addDistrict(d.build()));
        postalCommunitiesByZip.values().forEach(pc -> model.addPostalCommunity(pc.build()));
        politicalCommunitiesByNumber.values().forEach(pc -> model.addPoliticalCommunity(pc.build()));

        return model;
    }

    private void addPoliticalCommunity(CSVPoliticalCommunity csvPoliticalCom) {
        politicalCommunitiesByNumber.put(csvPoliticalCom.getNumber(), toPoliticalCommunityBuilder(csvPoliticalCom));
        cantonByCode.get(csvPoliticalCom.getCantonCode())
            .districtNumber(csvPoliticalCom.getDistrictNumber())
            .politicalCommunityNumber(csvPoliticalCom.getNumber());
        districtByNumber.get(csvPoliticalCom.getDistrictNumber())
            .cantonCode(csvPoliticalCom.getCantonCode())
            .politicalCommunityNumber(csvPoliticalCom.getNumber());
	}

	private void addPostalCommunity(CSVPostalCommunity csvPostalCom) {
        PostalCommunityKey postalCommunityKey = new PostalCommunityKey(csvPostalCom.getZipCode(), csvPostalCom.getZipCodeAddition());
        postalCommunitiesByZip.computeIfAbsent(postalCommunityKey, key -> toPostalCommunityBuilder(csvPostalCom))
            .cantonCode(csvPostalCom.getCantonCode())
            .politicalCommunityNumber(csvPostalCom.getPoliticalCommunityNumber());
        
        politicalCommunitiesByNumber.get(csvPostalCom.getPoliticalCommunityNumber()).postalCommunityKey(postalCommunityKey);
	}

    private void addDistrict(CSVPoliticalCommunity csvPoliticalCom) {
        districtByNumber.put(csvPoliticalCom.getDistrictNumber(), DistrictImpl.builder()
            .name(csvPoliticalCom.getDistrictName())
            .number(csvPoliticalCom.getDistrictNumber())
        );
	}

    private void addCanton(CSVPoliticalCommunity csvPoliticalCom) {
        cantonByCode.put(csvPoliticalCom.getCantonCode(), CantonImpl.builder()
            .name(csvPoliticalCom.getCantonName())
            .code(csvPoliticalCom.getCantonCode())
        );
    }

    private PostalCommunityImplBuilder toPostalCommunityBuilder(CSVPostalCommunity csvPostalCom) {
        return PostalCommunityImpl.builder()
            .name(csvPostalCom.getName())
            .zipCode(csvPostalCom.getZipCode())
            .zipCodeAddition(csvPostalCom.getZipCodeAddition());
            //.cantonCode(csvPostalCom.getCantonCode())
            //.politicalCommunityNumber(csvPostalCom.getPoliticalCommunityNumber());
    }

    private PoliticalCommunityImplBuilder toPoliticalCommunityBuilder(CSVPoliticalCommunity csvPoliticalCom) {
        return PoliticalCommunityImpl.builder()
            .name(csvPoliticalCom.getName())
            .number(csvPoliticalCom.getNumber())
            .shortName(csvPoliticalCom.getShortName())
            .lastUpdate(csvPoliticalCom.getLastUpdate())
            .cantonCode(csvPoliticalCom.getCantonCode())
            .districtNumber(csvPoliticalCom.getDistrictNumber());
    }
}
