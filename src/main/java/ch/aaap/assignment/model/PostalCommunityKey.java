package ch.aaap.assignment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PostalCommunityKey {

  private final String zipCode;

  private final String zipCodeAddition;
  
  public PostalCommunityKey(String zipCode, String zipCodeAddition) {
    this.zipCode = zipCode;
    this.zipCodeAddition = zipCodeAddition;
  }
}