package ch.aaap.assignment.model;

import java.util.Set;

public interface Canton {

  public String getCode();

  public String getName();

  public Set<String> getDistrictNumbers();
  
  public Set<String> getPoliticalCommunityNumbers();
}
