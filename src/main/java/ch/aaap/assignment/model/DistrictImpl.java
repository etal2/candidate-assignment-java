package ch.aaap.assignment.model;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DistrictImpl implements District {

  @EqualsAndHashCode.Include
  private String number;

  private String name;

  @Singular
  private Set<String> politicalCommunityNumbers;

  private String cantonCode;
}
