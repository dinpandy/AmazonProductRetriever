package com.amazon.productretriever;


public class ObjectHandling {
	
	
		public static String dept= "//span[contains(text(),'Departments')]";
		public static String searchBox ="//input[@id='twotabsearchtextbox']";
		public static String pagination ="//ul[@class='a-pagination']/li";
		public static String paginationLink ="//ul[@class='a-pagination']/li/a";
		public static String resultList = "//span[@data-component-type='s-result-info-bar']//div[@class='a-section a-spacing-small a-spacing-top-small']/span";
		public static String searchResult = "//div[starts-with(@data-cel-widget,'search_result_')]";
		public static String sponsoredItems = ".a-size-base.a-color-secondary";
		public static String productName =".a-size-medium.a-color-base.a-text-normal";
		public static String price =".a-price";
		
	

}
