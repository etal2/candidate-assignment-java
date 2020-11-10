package ch.aaap.assignment.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class PostalCommunityImpl implements PostalCommunity {

  private String zipCode;

  private String zipCodeAddition;

  private String name;

  private String cantonCode;

  @Singular
  private Set<String> politicalCommunityNumbers;

}
