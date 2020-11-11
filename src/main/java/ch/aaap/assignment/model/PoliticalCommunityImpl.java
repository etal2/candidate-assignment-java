package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PoliticalCommunityImpl implements PoliticalCommunity {

  @EqualsAndHashCode.Include
  private String number;

  private String name;

  private String shortName;

  private LocalDate lastUpdate;

  private String cantonCode;

  private String districtNumber;

  @Singular
  private Set<PostalCommunityKey> postalCommunityKeys;
}
