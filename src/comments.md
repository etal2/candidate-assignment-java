# 3AP Political and Postal communities in Switzerland

## Assignment 2
- The datasets are correlated using the political community number which appeares in both csv files
- Postal communities are unique using a composite key using zipCode and zipCodeAdditions together
- I added looking up entities directly by keys
- Since the data is pretty much immutable I de-normalize the data and enabled bidirectional relations on some entities

## Assignment 3
- The method getLastUpdateOfPoliticalCommunityByPostalCommunityName has some unclear edge cases. There can be multiple postal communities with the same name and there can be multiple political communities belonging to each postal community. In the case of multiple answers I'm currently picking the latest LastUpdate out of them all but it might be better to just throw on any ambiguity.There are no tests for these edge cases.

## Assignment 5
- Political and Postal Community names are not unique
- Postal Communities can have more then one political community and vise versa, a many-to-many relationship
- Postal communities can even span more then one canton (8866)
- The Postal Communities CSV has an additional value %_IN_GDE that represents the percentage of that community that is in each political community.
- There are political communities without postal communities (can they get mail?)