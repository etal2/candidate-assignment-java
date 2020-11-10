package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

  private String number;

  private String name;

  private String shortName;

  private LocalDate lastUpdate;

  private String cantonCode;

  private String districtNumber;

  @Singular
  private Set<PostalCommunityKey> postalCommunityKeys;

}
