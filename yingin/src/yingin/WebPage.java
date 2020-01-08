package yingin;

import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
	public String url;
	public String name;
	public WordCount counter;
	public double score;
	
	public WebPage(String url,String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCount(url);	
	}
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException, InterruptedException{
		score = 0;
		for(Keyword k : keywords){			
			score += counter.countKeyword(k.name)* k.weight;	
		}
	}
	
}