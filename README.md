# HotelOffers
HotelOffers

technologies/tools:
java8,maven,netbeans8.2,junit test

main idea:

*define each provider as a enum that is concern with a list of constant.

*each provider has its enum with
 main fields as //name("content")// >> 
name represent availableHotel request data, and content represent provider request data 
and also you can find inner enum belongs to response data in the same way //name("content")//
 were name represent availableHotel response and content represent provider responsedata

*enumFields method return the list of filed for requested enum

*initJsonRequest/initJsonResponse  these methods are to draw the json string on request/response depending on providers key and availablehotel key 

*convertResponseKeyToOriginal after response from providers we need to change the response result key of providers to the original one (available hotel key )this method deals with that
*mapToObject to convert the response result to our java object to edit value if need and calculate all result in one array list
 

**note that executePostRequest is adummy step only
and you can finds some test strings instead of post response 


**to add new providers you need to add its own enum fields for response and request and add new block in enumFields method and convertResponseKeyToOriginal 
in future we can enhance our code through add a generic type on some methods to shrink the changes when add providers like(convertResponseKeyToOriginal )method and (searchKey)method
