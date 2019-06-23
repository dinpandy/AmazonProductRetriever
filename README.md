# AmazonProductRetriever

Project scope:
Automate the following feature from https://www.amazon.com
  1) Launch any browser and open https://www.amazon.com
  2) Select department as Electronics
  3) Search for "Lenovo T470 Laptop"
  4) Select Avg. Customer Review as 3 stars
  5) Print Name and Price for all the filtered products including products which displays on pages 2,3… after navigating to those pages through pagination
  6) Print the total number of products filtered
  7) Find the Minimum price laptop and print its name and price
 
Goal:
1. Create Compartmentalized Architecture for handling different activities.
2. use testdata.json file to provide configurations for test case and load test data dynamically for running the tests from the configuration file.
3. use all variables at one place and utilize those variables in class and test files.
4. Traverse through all the pages and print the product name and product price of all the products using Pagination.
5. Find Maximum and Minimum Product Price from the Filtered Elements.
6. Save the Filtered Product Details in a Excel File.
7. Create TestNG Test file and invoke the Main class using the test to print all the details.
8. Create a Framework which performs all the automated process and also validate the data.

Benefits:
1.	Amazon displays the AD / Sponsored Products while Filtering the Products, which will be ignored / excluded from the filtered list through this framework.
Below Screenshot shows the list of filtered products along with the sponsored products.
Page 1 shows 16 Products excluding the sponsored products. This Framework will also perform the same operation of ignoring/excluding the Sponsored Products during Printing the details (Name and Price) of all the products. 


![image](https://user-images.githubusercontent.com/7686716/59982054-de9b3b80-960c-11e9-93d1-661f0a5bae29.png)

2.	This framework finds the Minimum and Maximum Product Price of the Filtered Products and prints the Minimum and Maximum Product details (Product Name and Product Price)


![image](https://user-images.githubusercontent.com/7686716/59982142-15258600-960e-11e9-9dd1-c991332a9d58.png)


3.	This framework saves the all the filtered product details (Product name and Product Price ) in an Excel Sheet apart from printing in the console.

![image](https://user-images.githubusercontent.com/7686716/59982182-93822800-960e-11e9-8c41-2139397b836e.png)

4. Framework Replaces the Price of the Products , which has no Main Price and only Price under “More Buying Options” as “$0”, but this value will not be coming under Minimum price as the framework handles this exclusively and provides the correct minimum price product details.

![image](https://user-images.githubusercontent.com/7686716/59982212-fecbfa00-960e-11e9-9851-d1d29be2f32a.png)

Source Code:
https://github.com/dinpandy/AmazonProductRetriever/

Configuration File:
{
"revision":"20190227085714",
"url" : "https://www.amazon.com",
"data" :{
  "department":"Electronics",
  "item":"Lenovo T470 Laptop",
  "avgCustomerReview": "3 Stars & Up"
 }
}

Architecture:

![image](https://user-images.githubusercontent.com/7686716/59982262-9d585b00-960f-11e9-839d-74f67e04012d.png)

![image](https://user-images.githubusercontent.com/7686716/59982273-cd076300-960f-11e9-8ee5-8e7376d8f662.png)

Test Data (testdata.json) is placed under /src/test/resources folder.
Config Folder has JSONReader class, which reads the data from the testdata.json and passes the data dynamically while running the test.

![image](https://user-images.githubusercontent.com/7686716/59982315-882ffc00-9610-11e9-98ee-d0e9c3919ab3.png)

ObjectHandling class file contains all the WebElement  XPath and CSS locator element details as variables, which will be used by the other classes. This helps in adding extra elements in this class without modifying multiple classes to add the element.

MaptoExcel class – creates the Excel file and also provides the Minimum and Maximum Price Product details from the Filtered Products List.

ProductRetriever class – Has Method RetrieveProducts() Method , which Traverses through all the Pages and PrintProductDetails() Method, which Prints the details of all the Filtered Products.

![image](https://user-images.githubusercontent.com/7686716/59982429-85360b00-9612-11e9-9267-b450e7d2a301.png)

ProductRetrieverTest class – Runs the Test Case and Validates and Asserts the Total number of filtered Products Dynamically and provides the test result.

![image](https://user-images.githubusercontent.com/7686716/59982455-1311f600-9613-11e9-989a-0a8532d808ee.png)

Design Considerations:

1.	Handling of Test Data through a JSONReader File which passes the data to all the required classes, which calls the class. This helps in reducing the retrieval of json test data multiple times in all places and it is handled in one place and traverses to different classes, which calls this class. 

2.	Amazon shows product list at the Top of the Page ( 1-16 out of 53 Results) , which is excluding the Sponsored Products list (Total Products in the page could be 20 including 4 sponsored products, but Amazon displays only 16 items in Top of the page) . 
Framework also has the same functionality of excluding the Sponsored Products list from the list of Filtered Product Items and displays the details of the filtered items.

![image](https://user-images.githubusercontent.com/7686716/59982582-dcd57600-9614-11e9-9b5c-f4bdafad1ad1.png)

![image](https://user-images.githubusercontent.com/7686716/59982570-908a3600-9614-11e9-8237-3afcac64b2ca.png)

3. Object Handling class handles most of the used Web Elements in one place and passes on to the required classes. This helps in adding extra elements in this class without modifying multiple classes to add the element.

![image](https://user-images.githubusercontent.com/7686716/59982333-c62d2000-9610-11e9-8a02-5eda5da358cb.png)

![image](https://user-images.githubusercontent.com/7686716/59982344-d9d88680-9610-11e9-9a0a-5631c0f915fe.png)

4. Framework handles the Decimal price value of the Product Price effectively and displays it under the price of the filtered products.

5. MaptoExcel class creates the Excel with the Filtered Products list and also provides the Minimum and Maximum Product details (Name and Price). While calculating the Minimum Price, it excludes the Items with No “Main” price ( only secondary buying price available).

![image](https://user-images.githubusercontent.com/7686716/59982668-2b374480-9616-11e9-8659-efa8e6bba931.png)


6. Framework is a Maven Product with TestNG involved, which helps in running the builds and testcases seamlessly using Maven commands and also since dependencies are set in pom.xml , it removes the problem of handling jar files by downloading them separately.

7. Framework Testcase Handles the Expected Data Dynamically. It collects the Expected Data from the Top of the page and then traverses through the page and then compares both the results and provides the test case status.
This helps in avoiding the manual expected test data, which has more probability for failing since there could be increase or decrease in number of filtered products for each of the run and manually given expected test data would fail with the actual data.

Note: a)All the Dependancy jars are available in the pom.xml file
      b)Chrome browser has been selected for this framework and the chromedriver has been uploaded under chromeExe folder


Code:
List<WebElement> Result= driver.findElements(By.xpath(ObjectHandling.resultList));
	    	for (WebElement elemnt: Result) {
	    	  if (elemnt.getText().toString().contains("of")) {
	    		  ExpectedProductCount=Integer.parseInt(elemnt.getText().split(" of ")[1].split(" results ")[0].trim());
	    	  };
	    	}

![image](https://user-images.githubusercontent.com/7686716/59982838-7e11fb80-9618-11e9-938b-756997bd6399.png)


Result Screenshot:

![image](https://user-images.githubusercontent.com/7686716/59982484-903d6b00-9613-11e9-9f2b-0e2347a35f20.png)

![image](https://user-images.githubusercontent.com/7686716/59982182-93822800-960e-11e9-8c41-2139397b836e.png)

![image](https://user-images.githubusercontent.com/7686716/59982807-03e17700-9618-11e9-949b-0a6746d12f0a.png)

![image](https://user-images.githubusercontent.com/7686716/59982698-849f7380-9616-11e9-91c2-47565aea9112.png)

Reports under Target/surefire-reports directory:

![image](https://user-images.githubusercontent.com/7686716/59983233-87519700-961d-11e9-93e5-d0e9b8a257ec.png)

![image](https://user-images.githubusercontent.com/7686716/59983248-a2bca200-961d-11e9-9279-7998919260eb.png)

![image](https://user-images.githubusercontent.com/7686716/59983261-c54ebb00-961d-11e9-9714-26e9e235afad.png)


Execution Method:
1.	Pull the source code from the Git Repository through git clone command or download the Zip from the Git Repository.

Download Zip:
https://github.com/dinpandy/AmazonProductRetriever/

Clone the Repository in Git:
https://github.com/dinpandy/AmazonProductRetriever.git


![image](https://user-images.githubusercontent.com/7686716/59982744-1dce8a00-9617-11e9-8e8d-20989ef6ce6c.png)


![image](https://user-images.githubusercontent.com/7686716/59982763-756cf580-9617-11e9-8228-f3889e99a3ce.png)

2. Once the Project is cloned / downloaded, move to the directory and run the Maven command ( Install Maven, if it is not installed already)
https://docs.wso2.com/display/IS323/Installing+Apache+Maven+on+Windows

3. Run “mvn clean install” in the directory where pom.xml resides, which will clean compile and run the test cases.
If you run the command in the directory, where pom.xml doesn’t reside, you will get the below error.

![image](https://user-images.githubusercontent.com/7686716/59982911-625b2500-9619-11e9-8daa-7e5a893e9b6a.png)

4. Move to the directory, where pom.xml exist and run the “mvn clean install” command .
It runs the clean, compile and execute the test cases and then will print the filtered product details and also the Minimum and Maximum price details.
It will also create a file called “ResourceDetails.xls” in the same directory, where the command was executed, which contains the full details of the filtered product items ( Name and Price).


![image](https://user-images.githubusercontent.com/7686716/59982933-b1a15580-9619-11e9-97aa-52a0871c1f69.png)

![image](https://user-images.githubusercontent.com/7686716/59983296-3aba8b80-961e-11e9-8739-194fce014c37.png)

![image](https://user-images.githubusercontent.com/7686716/59983000-98e56f80-961a-11e9-8085-47717ecb3c31.png)

![image](https://user-images.githubusercontent.com/7686716/59983021-cc27fe80-961a-11e9-9dac-50acd3a8e3cf.png)

5. Alternate commands to run the test;
mvn clean
mvn compile 
mvn test
The above command (mvn test) will launch the testcase and will launch the browser and will perform the expected functionality and will print the product details in console and in excel file and will also print the Minimum and Maximum product details.



