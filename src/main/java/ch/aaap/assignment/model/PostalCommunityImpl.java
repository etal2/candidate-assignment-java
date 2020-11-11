package ch.aaap.assignment.model;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostalCommunityImpl implements PostalCommunity {

  @EqualsAndHashCode.Include
  private String zipCode;

  @EqualsAndHashCode.Include
  private String zipCodeAddition;

  private String name;

  private String cantonCode;

  @Singular
  private Set<String> politicalCommunityNumbers;

}
