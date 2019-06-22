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

public class ProductRetriever {

	public static void main(String[] args) throws IOException, ParseException, JSONException, InterruptedException {
		JSONReader e = new JSONReader();
		MaptoExcel MapExcel = new MaptoExcel();
		String url = e.parseJson("url");
		String departmentcategory = e.parseJsonObj("data", "department");
		String item = e.parseJsonObj("data", "item");
		String avgCustomerReview = e.parseJsonObj("data", "avgCustomerReview");
		int TotalProducts = RetrieveProducts(url,departmentcategory,item,avgCustomerReview);
		System.out.println("Total Product Values are "+TotalProducts);
	}
		
		public static  int RetrieveProducts(String url, String departmentcategory, String item, String avgCustomerReview)
		{
			JSONReader e = new JSONReader();
			MaptoExcel MapExcel = new MaptoExcel();
			int TotalNoOfProducts = 0;

		System.setProperty("webdriver.chrome.driver", "chromeExe\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		
		try
		{
		
		WebElement Root = (new WebDriverWait(driver, 2))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Departments')]")));
		WebElement Dep = driver.findElement(By.xpath("//span[contains(text(),'" + departmentcategory + "')]"));
		Actions actions = new Actions(driver);
		
		actions.moveToElement(Root).click(Dep).build().perform();

		// WebElement search =
		// driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		WebElement search = (new WebDriverWait(driver, 2))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='twotabsearchtextbox']")));

		search.sendKeys(new String[] { item });
		search.sendKeys(Keys.ENTER);
		
		WebElement rating = (new WebDriverWait(driver, 2)).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[contains(text(),'" + avgCustomerReview + "')]")));
		
		rating.click();
		
		Thread.sleep(6000);
		List<WebElement> resultsList = driver.findElements(By.xpath("//div[starts-with(@data-cel-widget,'search_result_')]"));
		
		double ProductPrice = 0.0;
		String ProductName = "";
		String ProductPriceText = "";
		
		HashMap<String, Double> hash_map = new HashMap<String, Double>(); 
	    
	    
	    
	     for (WebElement result:resultsList) {
			 
	    	     	 
	    	 if(!(result.findElement(By.cssSelector(".a-size-base.a-color-secondary")).getText().equalsIgnoreCase("Sponsored")))
	    	 {
	    	 ProductName = result.findElement(By.cssSelector(".a-size-medium.a-color-base.a-text-normal")).getText();
			 
	        //System.out.println("Result Text Data is "+result.findElement(By.cssSelector(".a-size-medium.a-color-base.a-text-normal")).getText());
	        if(result.findElements(By.cssSelector(".a-price")).size()>0)
	        {
	        	
	        	ProductPriceText =result.findElement(By.cssSelector(".a-price")).getText().replaceAll("[$,]","");
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
		 //System.out.println("Hash Map Content is "+hash_map);
		 MapExcel.ExcelCreation(hash_map);
		 TotalNoOfProducts =hash_map.size();
		 System.out.println("Total Number of Products Filtered is "+hash_map.size());
	
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
		return TotalNoOfProducts;
		
	}
		
	

}
