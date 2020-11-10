package ch.aaap.assignment.model;

import java.util.Set;

public interface District {

  public String getNumber();

  public String getName();

  public Set<String> getPoliticalCommunityNumbers();

  public String getCantonCode();
}
