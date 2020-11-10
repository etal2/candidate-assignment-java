package ch.aaap.assignment.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class CantonImpl implements Canton {

  private String code;

  private String name;

  @Singular
  private Set<String> districtNumbers;

  @Singular
  private Set<String> politicalCommunityNumbers;

}
