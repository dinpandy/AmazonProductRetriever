package com.amazon.productretriever;


import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;

import config.JSONReader;

public class AppTest {

    @Test()
    public void AddContactHappyPathTest() throws JSONException, IOException, ParseException {
        System.out.println("hello world");
        JSONReader e = new JSONReader();
		MaptoExcel MapExcel = new MaptoExcel();
		String url = e.parseJson("url");
		String departmentcategory = e.parseJsonObj("data", "department");
		String item = e.parseJsonObj("data", "item");
		String avgCustomerReview = e.parseJsonObj("data", "avgCustomerReview");
		int ExpectedElements = 16;
		Assert.assertEquals(ProductRetriever.RetrieveProducts(url,departmentcategory,item,avgCustomerReview), ExpectedElements,"The Number of Products filtered does not match the Expected Results");
		    }
}