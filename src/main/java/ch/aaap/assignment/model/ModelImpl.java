package ch.aaap.assignment.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModelImpl implements Model {

	private Map<String, Canton> cantonByCode = new HashMap<>();
	private Map<String, District> districtByNumber = new HashMap<>();
	private Map<String, PoliticalCommunity> politicalCommunitiesByNumber = new HashMap<>();
	private Map<PostalCommunityKey, PostalCommunity> postalCommunitiesByZip = new HashMap<>();

	public void addPoliticalCommunity(PoliticalCommunity politicalcom){
		politicalCommunitiesByNumber.put(politicalcom.getNumber(), politicalcom);
	}

	public void addPostalCommunity(PostalCommunity postalcom){
		postalCommunitiesByZip.put(new PostalCommunityKey(postalcom.getZipCode(), postalcom.getZipCodeAddition()), postalcom);
	}

	public void addDistrict(District district){
		districtByNumber.put(district.getNumber(), district);
	}

	public void addCanton(Canton canton){
		cantonByCode.put(canton.getCode(), canton);
	}

	@Override
	public Set<PoliticalCommunity> getPoliticalCommunities() {
		return new HashSet<>(politicalCommunitiesByNumber.values());
	}

	@Override
	public Set<PostalCommunity> getPostalCommunities() {
		return new HashSet<>(postalCommunitiesByZip.values());
	}

	@Override
	public Set<Canton> getCantons() {
		return new HashSet<>(cantonByCode.values());
	}

	@Override
	public Set<District> getDistricts() {
		return new HashSet<>(districtByNumber.values());
	}

	@Override
	public Canton getCantonByCode(String cantonCode) {
		return cantonByCode.get(cantonCode);
	}

	@Override
	public District getDistrictByNumber(String districtNumber) {
		return districtByNumber.get(districtNumber);
	}

	@Override
	public PoliticalCommunity getPoliticalCommunityByNumber(String politicalCommunityNumber) {
		return politicalCommunitiesByNumber.get(politicalCommunityNumber);
	}

	@Override
	public PostalCommunity getPostalCommunityByZip(String zipCode, String zipCodeAddition) {
		return postalCommunitiesByZip.get(new PostalCommunityKey(zipCode, zipCodeAddition));
	}
}
