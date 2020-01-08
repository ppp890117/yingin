package yingin;



import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WordCount {
	private String urlStr;
    private String content;
    
    public WordCount(String urlStr){
    	this.urlStr = urlStr;
    }
    
    private String fetchContent() throws IOException, InterruptedException

	{System.setProperty("webdriver.chrome.driver", "/Users/yvon/Downloads/chromedriver");
	ChromeOptions options = new ChromeOptions();
	options.setHeadless(true);
    WebDriver driver = new ChromeDriver(options);
    driver.get(this.urlStr);
    Thread.sleep(5000);  // Let the user actually see something!
    WebElement searchBox = driver.findElement(By.tagName("html"));
    content = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", searchBox);
    driver.close();
	return content;
	}
    
    public int countKeyword(String keyword) throws IOException, InterruptedException{
		if (content == null){
		    content = fetchContent();
		}
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:

	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
	
		return retVal;
    }
}
