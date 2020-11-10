package ch.aaap.assignment.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class DistrictImpl implements District {

  private String number;

  private String name;

  @Singular
  private Set<String> politicalCommunityNumbers;

  private String cantonCode;
}
