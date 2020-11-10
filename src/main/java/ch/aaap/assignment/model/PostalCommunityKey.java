package ch.aaap.assignment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PostalCommunityKey{

	public PostalCommunityKey(String zipCode, String zipCodeAddition){
		this.zipCode = zipCode;
		this.zipCodeAddition = zipCodeAddition;
	}

	final private String zipCode;

	final private String zipCodeAddition;
}