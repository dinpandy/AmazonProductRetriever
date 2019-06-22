package com.amazon.productretriever;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonProcessing {

			   public static void main(String[] args) {
			
		      
		      String s = "{\"foo\":{\"defaultValue\":\"defaultfoo\"},\"bar\":{\"defaultValue\":\"defaultbar\"},\"baz\":{\"defaultValue\":\"defaultbaz\"},\"anyOther\":{\"defaultValue\":\"defaultanyOther\"}}";
				
		      JsonParser parser = new JsonParser();
			  JsonObject o = parser.parse(s).getAsJsonObject();

			 		
			  o.entrySet().parallelStream().forEach(entry -> {
				    ((JsonObject) entry.getValue()).entrySet().parallelStream().forEach(entryo -> {
				    	String OutValue= "Child-Key: "+entryo.getKey()+" corresponding value for Parent-Key : "+entry.getKey()+" is ==>: "+entryo.getValue();
					    System.out.println(OutValue);});					
				});
		   }}

		