package com.amazon.productretriever;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import config.JSONReader;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.amazon.productretriever.ObjectHandling;

public class ProductRetriever  {

	public static void main(String[] args) throws IOException, ParseException, JSONException, InterruptedException {
		JSONReader e = new JSONReader();		
		String url = e.parseJson("url");
		String departmentcategory = e.parseJsonObj("data", "department");
		String item = e.parseJsonObj("data", "item");
		String avgCustomerReview = e.parseJsonObj("data", "avgCustomerReview");
		int[] foundProducts = RetrieveProducts(url,departmentcategory,item,avgCustomerReview);
		System.out.println("Expected Product Values are "+foundProducts[0]+ " \n Actual Product found are "+foundProducts[1]);
		
	}
		
		public static  int[] RetrieveProducts(String url, String departmentcategory, String item, String avgCustomerReview)
		{
			MaptoExcel MapExcel = new MaptoExcel();
			int ExpectedProductCount=0;
			int TotalNoOfProducts = 0;
			HashMap<String, Double> hash_map = new HashMap<String, Double>();
		System.setProperty("webdriver.chrome.driver", "chromeExe\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		
		try
		{
		
		//Thread.sleep(1000);
			WebElement Root,Dep,Dep1;
		int timeout=0;
		Actions actions;

		while(true) {
			timeout=timeout++;
			try{
				Thread.sleep(500);
				//Root = (new WebDriverWait(driver, 10))
					//	.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Departments')]"))); ;
				
				Root = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ObjectHandling.dept))); ;
			
				
				Dep = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'" + departmentcategory + "')]")));
				actions = new Actions(driver);	
				actions.moveToElement(Root).click(Dep).build().perform();
				Dep1 = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/h1/b[contains(text(),'" + departmentcategory + "')]")));
				
				if (Dep1.isDisplayed()) {	
					break;
				}else if(timeout>10) {
					throw new Error ("Page Not loaded for Selected Department- "+ departmentcategory);
					};}
			catch(Exception ex) {};
		}

		// WebElement search =
		// driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		WebElement search = (new WebDriverWait(driver, 50))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ObjectHandling.searchBox)));

		search.sendKeys(new String[] { item });
		search.sendKeys(Keys.ENTER);
		
		WebElement rating = (new WebDriverWait(driver, 50)).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[contains(text(),'" + avgCustomerReview + "')]")));
		
		rating.click();
		
			
		 //list to store page numbers
	    List<WebElement> elements;
	    
	    //Count no of pagination link
	    new WebDriverWait(
	            driver, 20).until(
	                    ExpectedConditions.presenceOfElementLocated(
	                            By.xpath(ObjectHandling.pagination)));
	
	       
	    elements = driver.findElements(By.xpath(ObjectHandling.paginationLink));
	    
	    	for (int i = 0; i < elements.size(); i++) {
	    		WebElement element;
	    		try {
	    			element= driver.findElement(By.xpath(".//li/a[contains(text(),"+i+")]"));
	    			element.sendKeys(Keys.ENTER);

	    			
	    			new WebDriverWait(
	    		            driver, 80).until(
	    		                    ExpectedConditions.presenceOfElementLocated(
	    		                            By.xpath(".//li/a[contains(text(),"+i+")]/ancestor::li[@class='a-selected']")));
	    			if(driver.findElement(By.xpath(".//li/a[contains(text(),"+i+")]/ancestor::li")).getAttribute("class").toString().equals("a-selected"))
	    					{
	    				PrintProductDetails(driver, hash_map);
		    			System.out.println("The Total Number of Products until Page " +i+" is " +hash_map.size());}
	    					}
	    			
	    		catch(Exception ex) {
	    			
	    		}    	
		    	
		        }
	    
		
	    	List<WebElement> Result= driver.findElements(By.xpath(ObjectHandling.resultList));
	    	for (WebElement elemnt: Result) {
	    	  if (elemnt.getText().toString().contains("of")) {
	    		  ExpectedProductCount=Integer.parseInt(elemnt.getText().split(" of ")[1].split(" results ")[0].trim());
	    	  };
	    	}
	    	    	
		 
		 System.out.println("Total Expected Products Count is "+ExpectedProductCount);
		 MapExcel.ExcelCreation(hash_map);
		 TotalNoOfProducts =hash_map.size();
		 System.out.println("Total Number of Products Filtered(Actual Products Count) is "+TotalNoOfProducts);
	
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			ex.printStackTrace();
		}
		finally
		{
			driver.close();
			driver.quit();
		}
		int [] result = {ExpectedProductCount, TotalNoOfProducts};
	
		return result;
		
	}
		
		public static   HashMap<String, Double> PrintProductDetails(WebDriver driver, HashMap<String, Double> hash_map)
		{
		List<WebElement> resultsList = driver.findElements(By.xpath(ObjectHandling.searchResult));
		
		double ProductPrice = 0.0;
		String ProductName = "";
		String ProductPriceText = "";
		
		 
	    
	    
	    
	     for (WebElement result:resultsList) {
			 
	    	 if(result.findElements(By.cssSelector(ObjectHandling.sponsoredItems)).size()>0)
		        {
	    	     	 
	    	 if(!(result.findElement(By.cssSelector(ObjectHandling.sponsoredItems)).getText().equalsIgnoreCase("Sponsored")))
	    	 {
	    	 ProductName = result.findElement(By.cssSelector(ObjectHandling.productName)).getText();
			 
	        //System.out.println("Result Text Data is "+result.findElement(By.cssSelector(".a-size-medium.a-color-base.a-text-normal")).getText());
	        if(result.findElements(By.cssSelector(ObjectHandling.price)).size()>0)
	        {
	        	
	        	ProductPriceText =result.findElement(By.cssSelector(ObjectHandling.price)).getText().replaceAll("[$,]","");
	        	String[] Pricevalue = ProductPriceText.split("\n");
	        	int firstNum = Integer.parseInt(Pricevalue[0]);
	        	int SecondNum = Integer.parseInt(Pricevalue[1]);
	        	ProductPrice = Double.valueOf(firstNum + "." + SecondNum);
	        
	        }
	        else
	        {
	        	
	        	ProductPrice = 0.0;
	        	//continue;
	        }
	        
	        System.out.println("Product Name is --- "+ProductName + " And Product Price is ---- $"+ProductPrice);
	        hash_map.put(ProductName, ProductPrice);
	     }  
		        }
	     }
	     return hash_map;
	     
		}
		
	

}
