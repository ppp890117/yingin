package yingin;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class GoogleQuery 

{

	public String searchKeyword;

	public String url;

	public String content;
	
	private WebDriver driver;
	
	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=10";

	}

	

	private String fetchContent() throws IOException, InterruptedException

	{
		System.setProperty("webdriver.chrome.driver", "/Users/yvon/Downloads/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
	    driver = new ChromeDriver(options);
	    driver.get(this.url);
	    Thread.sleep(5000);  // Let the user actually see something!
	    WebElement searchBox = driver.findElement(By.tagName("html"));
	    content = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", searchBox);
	    
	    
		return content;
	}
	public HashMap<String, String> query() throws IOException, InterruptedException

	{

		if(content==null)

		{

			content= fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();

		List<WebElement> lis = driver.findElements(By.cssSelector("div.r"));
		List<WebElement> titleEle = driver.findElements(By.cssSelector("h3.LC20lb"));
	    for(int i=0;i<=5;i++) {
	    	try {
	    		String title = titleEle.get(i).getText();
	    		String citeUrl = lis.get(i).findElement(By.cssSelector("a[href]")).getAttribute("href");
	    		retVal.put(title, citeUrl);
	    		
	    	}catch (IndexOutOfBoundsException e) {

				e.printStackTrace();

			}
	    	
	    }


			

		

		
	    driver.close();

		return retVal;

	}

	

	

}