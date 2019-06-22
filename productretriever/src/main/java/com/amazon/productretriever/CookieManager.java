package com.amazon.productretriever;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CookieManager {
	
	public static void main(String args[])
	{
	String cookie = "userID=ABC123456789; expires=Tue, 16-Jul-2019 08:06:02 UTC;path=/se;Domain=.knowit.se;HttpOnly,CAN_USER_CONFIRM=1238b6f787d0f731560c3ee8e10fb60e; expires=Tue, 16-Jul-2019 08:06:02 UTC;path=/se;Domain=.knowit.se;HttpOnly,session=GLAlLLLKJAF80_7hhkhkkpFmxfPxR-5SuysAVjQGbpbvhc-8WS5I!1275757575; path=/se;Domain=.knowit.se; HttpOnly";
	String regx= "([^=]+)=([^\\;]*);?\\s?";
	
	List<String> result =splitCookies(cookie, regx);
	for (String string : result) {
		System.out.println(string);
	}
	}
	
	
	
	public static List<String> splitCookies(String cookie, String regx) {
		
		Pattern pattern = Pattern.compile(regx);

		Matcher matcher = pattern.matcher(cookie);
	
		List<String> array = new ArrayList<String>();
		while (matcher.find()) {

			String cookieKey = matcher.group(1);			
			if (cookieKey.contains("userID")||cookieKey.contains("CAN_USER_CONFIRM")||cookieKey.contains("session")) {
				String cookieValue = matcher.group(2);
				if (cookieKey.contains(",")) {
					String[] parts = cookieKey.split(",");
					cookieKey= parts[1];
				}
				array.add(cookieKey+"="+cookieValue);
				
			}
			
		}    
		
		return array;
	}


}
