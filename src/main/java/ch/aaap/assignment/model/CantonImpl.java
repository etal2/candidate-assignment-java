package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CantonImpl implements Canton {

  @EqualsAndHashCode.Include
  private String code;

  private String name;

  @Singular
  private Set<String> districtNumbers;

  @Singular
  private Set<String> politicalCommunityNumbers;
}
