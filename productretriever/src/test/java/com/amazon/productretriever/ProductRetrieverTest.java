package com.amazon.productretriever;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;

import config.JSONReader;

public class ProductRetrieverTest {

    @Test()
    public void TotalElementValidatorTest() throws JSONException, IOException, ParseException {
        JSONReader e = new JSONReader();
		MaptoExcel MapExcel = new MaptoExcel();
		String url = e.parseJson("url");
		String departmentcategory = e.parseJsonObj("data", "department");
		String item = e.parseJsonObj("data", "item");
		String avgCustomerReview = e.parseJsonObj("data", "avgCustomerReview");
		int [] ResultedElements=ProductRetriever.RetrieveProducts(url,departmentcategory,item,avgCustomerReview);
		int ExpectedProductCount= ResultedElements[0]; 
		int ActualProductCount= ResultedElements[1];
		
		
		
				Assert.assertEquals(ActualProductCount, ExpectedProductCount,"The Number of Products filtered does not match the Expected Results");
		    }
}