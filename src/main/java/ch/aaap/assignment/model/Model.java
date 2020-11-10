package ch.aaap.assignment.model;

import java.util.Set;

public interface Model {

  public Set<PoliticalCommunity> getPoliticalCommunities();

  public Set<PostalCommunity> getPostalCommunities();

  public Set<Canton> getCantons();

  public Set<District> getDistricts();

	public Canton getCantonByCode(String cantonCode);

  public District getDistrictByNumber(String districtNumber);
  
  public PoliticalCommunity getPoliticalCommunityByNumber(String politicalCommunityNumber);

  public PostalCommunity getPostalCommunityByZip(String zipCode, String zipCodeAddition);
}
