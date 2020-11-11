package ch.aaap.assignment;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelFactory;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  private Model model = null;

  public Application() {
    initModel();
  }

  public static void main(String[] args) {
    new Application();
  }

  /** Reads the CSVs and initializes a in memory model. */
  private void initModel() {
    Set<CSVPoliticalCommunity> politicalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> postalCommunities = CSVUtil.getPostalCommunities();

    model = new ModelFactory(politicalCommunities, postalCommunities).build();
  }

  /**
   *  Gts the model.
   *  @return {@link Model} 
   */
  public Model getModel() {
    return model;
  }

  /**
   * Get the number of political communities in a canton.
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    Canton canton = getModel().getCantonByCode(cantonCode);
    if (canton == null) {
      throw new IllegalArgumentException(cantonCode);
    }
    return canton.getPoliticalCommunityNumbers().size();
  }

  /**
   * Get amount of districts in given canton.
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    Canton canton = model.getCantonByCode(cantonCode);
    if (canton == null) {
      throw new IllegalArgumentException(cantonCode);
    }
    return canton.getDistrictNumbers().size();
  }

  /**
   * Get amount of political communities in given district.
   * @param districtNumber of a district (e.g. 101)
   * @return number of political communities
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    District district = model.getDistrictByNumber(districtNumber);
    if (district == null) {
      throw new IllegalArgumentException(districtNumber);
    }
    return district.getPoliticalCommunityNumbers().size();
  }

  /**
   * Get districts that belongs to specified zip code.
   * @param zipCode 4 digit zip code
   * @return matching districts
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return model.getPostalCommunities().stream()
      .filter(pc -> pc.getZipCode().equals(zipCode))
      .map(PostalCommunity::getPoliticalCommunityNumbers)
      .flatMap(Set::stream)
      .map(model::getPoliticalCommunityByNumber)
      .map(PoliticalCommunity::getDistrictNumber)
      .map(model::getDistrictByNumber)
      .map(District::getName)
      .collect(Collectors.toSet());
  }

  /**
   * Get date of lastUpdate for a given postal community name.
   * Handles multiple political communities belonging to the same postal commuity
   * @param postalCommunityName name
   * @return lastUpdate date
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    return model.getPostalCommunities().stream()
      .filter(pc -> pc.getName().equals(postalCommunityName))
      .map(PostalCommunity::getPoliticalCommunityNumbers)
      .flatMap(Set::stream)
      .map(model::getPoliticalCommunityByNumber)
      .map(PoliticalCommunity::getLastUpdate)
      .max(LocalDate::compareTo)
      .orElse(null);
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    return model.getPoliticalCommunities().stream()
      .map(PoliticalCommunity::getPostalCommunityKeys)
      .filter(Set::isEmpty)
      .count();
  }
}
