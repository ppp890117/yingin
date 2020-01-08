package yingin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



/**
 * Servlet implementation class TestProject
 */
@WebServlet("/TestProject")
public class TestProject extends HttpServlet {
 
 private static final long serialVersionUID = 1L;
 private static ArrayList<WebNode> lst = new ArrayList<WebNode>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestProject() {
        super();
        // TODO Auto-generated constructor stub
    }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  class scoreComparator implements Comparator { 
       public int compare(Object object1, Object object2) {// 實現介面中的方法 
       WebNode p1= (WebNode) object1; // 強制轉換 
       WebNode p2 = (WebNode) object2; 
       return new Double(p1.nodeScore).compareTo(new Double(p2.nodeScore)); 
        } 
       } 
  response.setCharacterEncoding("UTF-8");
  request.setCharacterEncoding("UTF-8");
  response.setContentType("text/html");
  if(request.getParameter("keyword")== null) {
   String requestUri = request.getRequestURI();
   request.setAttribute("requestUri", requestUri);
   request.getRequestDispatcher("Search.jsp").forward(request, response);
   return;
  }
  
  
  try {
   GoogleQuery google = new GoogleQuery(request.getParameter("keyword"));

   HashMap<String, String> query = google.query();
   String[][] s = new String[query.size()][2];
   int num = 0;
   for(Entry<String, String> entry : query.entrySet()) {
       String key = entry.getKey();
       String value = entry.getValue();
       s[num][0] = key;
       s[num][1] = value;
       num++;
   }
   
//   建立tree來評分
   String queryString = request.getParameter("keyword");

   WebPage rootPage1 = new WebPage(s[0][1], s[0][0]);
   WebTree tree1 = new WebTree(rootPage1);
   
   WebPage rootPage2 = new WebPage(s[1][1], s[1][0]);
   WebTree tree2 = new WebTree(rootPage2);
   
   WebPage rootPage3 = new WebPage(s[2][1], s[2][0]);
   WebTree tree3 = new WebTree(rootPage3);
   
   WebPage rootPage4 = new WebPage(s[3][1], s[3][0]);
   WebTree tree4 = new WebTree(rootPage4);
   
   WebPage rootPage5 = new WebPage(s[4][1], s[4][0]);
   WebTree tree5 = new WebTree(rootPage5);
   
   WebPage rootPage6 = new WebPage(s[5][1], s[5][0]);
   WebTree tree6 = new WebTree(rootPage6);
   

   
   
   String query1 = "https://tw.iqiyi.com/search?q="+queryString;
   WebPage iqiyiRootPage = new WebPage(query1 , "愛奇藝");  
   WebTree iqiyiTree = new WebTree(iqiyiRootPage);
//   
   String query5 = "https://pttplay.com/vod-search.html?wd="+queryString;
   WebPage pttPlayRootPage = new WebPage(query5, "pttPlay");
   WebTree pttTree = new WebTree(pttPlayRootPage);
   
   String query6 = "http://www.142mov.com/vod-search-wd-"+queryString+".html";
   WebPage littleDuckRootPage = new WebPage(query6, "小鴨影音");
   WebTree littleDuckTree = new WebTree(littleDuckRootPage);
   
   String query7 = "https://www.momovod.com/vod-search.html?wd="+queryString;
   WebPage momoRootPage = new WebPage(query7, "momovod");
   WebTree momoTree = new WebTree(momoRootPage);
   
   
//   建立keywordlist
   ArrayList<Keyword> keyworedList = new ArrayList<Keyword>();
   keyworedList.add(new Keyword(queryString, 1));
   keyworedList.add(new Keyword("線上看", 10));
   keyworedList.add(new Keyword("VIP", 5));
   keyworedList.add(new Keyword("付費", 5));
   keyworedList.add(new Keyword("試用", 5));
   keyworedList.add(new Keyword("租", 5));
   keyworedList.add(new Keyword("方案", 5));
   keyworedList.add(new Keyword("年份", 20));   
   keyworedList.add(new Keyword("維基百科", -10000));
   keyworedList.add(new Keyword("影評", -5));
   keyworedList.add(new Keyword("票房", -5));
   keyworedList.add(new Keyword("劇透", -5));
   
   
//   把評分的網頁的子網頁加入tree裡
//   //1.愛奇藝
   Document doc1 = Jsoup.connect(query1).get();
   Elements postItems1 = doc1.getElementsByClass("search-item");
   Elements titleEle1 = postItems1.select(".search-item__title a[class='search-item__link jsAlbumUrl']");
   int size1 = titleEle1.size();
   if(size1<5) {
     for(int i=0;i<size1;i++) {
     if(postItems1.get(i)!=null) {
      iqiyiTree.root.addChild(new WebNode(new WebPage("https:"+titleEle1.get(i).attr("href"),titleEle1.get(i).text())));
     }else {
     }
    }
   }else {
    for(int i=0;i<5;i++) {
     if(postItems1.get(i)!=null) {
      iqiyiTree.root.addChild(new WebNode(new WebPage("https:"+titleEle1.get(i).attr("href"),titleEle1.get(i).text())));
     }else {
     }
    }
   }

//////    //5.pttplay
    Document doc5 = Jsoup.connect(query5).get();
    Elements postItems5 = doc5.getElementsByClass("content");
    Elements titleEle5 = postItems5.select(".head");
    int size5 = titleEle5.size();
    if(size5<5) {
     for(int i=0;i<size5;i++) {
      if(titleEle5.get(i)!=null) {
       pttTree.root.addChild(new WebNode(new WebPage("https://pttplay.com/"+titleEle5.get(i).select("a[href]").attr("href"),titleEle5.get(i).text())));
      }else {
      }
     }
    }else {
     for(int i=0;i<5;i++) {
      if(titleEle5.get(i)!=null) {
       pttTree.root.addChild(new WebNode(new WebPage("https://pttplay.com/"+titleEle5.get(i).select("a[href]").attr("href"),titleEle5.get(i).text())));
      }else {
      }
     }
    }
//    
////   //6.小鴨
       Document doc6 = Jsoup.connect(query6).get();
       Elements postItem6 =doc6.select("ul:not(ul[style='padding:0;'])");
       Elements titleEle6 = postItem6.select(".con");
       int size6 = titleEle6.size();
       if(size6<5) {
        for(int i = 0;i<size6;i++) {
         if(titleEle6.get(i)!=null) {
          littleDuckTree.root.addChild(new WebNode(new WebPage("http://www.139mov.com/"+titleEle6.get(i).select("a[href]").attr("href"), titleEle6.get(i).select(".sTit").text())));
         }else {
         }
        }
       }else {
        for(int i = 0;i<5;i++) {
         if(titleEle6.get(i)!=null) {
          littleDuckTree.root.addChild(new WebNode(new WebPage("http://www.139mov.com/"+titleEle6.get(i).select("a[href]").attr("href"), titleEle6.get(i).select(".sTit").text())));
         }else {
         }
        }
       }
//       
//   //7.momovod
   Document doc7 = Jsoup.connect(query7).get();
   Elements postItems7 = doc7.select("dd.clearfix");
   Elements titleEle7 = postItems7.select(".head");
   int size7 = titleEle7.size();
       if(size7<5) {
        for(int i = 0;i<size7;i++) {
         if(titleEle7.get(i)!=null) {
          momoTree.root.addChild(new WebNode(new WebPage("https://www.momovod.com/"+titleEle7.get(i).select("a[href]").attr("href"), titleEle7.get(i).text())));
         }else {
         }
        }
       }else {
        for(int i = 0;i<5;i++) {
         if(titleEle7.get(i)!=null) {
          momoTree.root.addChild(new WebNode(new WebPage("https://www.momovod.com/"+titleEle7.get(i).select("a[href]").attr("href"), titleEle7.get(i).text())));
         }else {
         }
        }
       }
       
//   算分數
      iqiyiTree.setPostOrderScore(keyworedList);
   pttTree.setPostOrderScore(keyworedList);
   littleDuckTree.setPostOrderScore(keyworedList);
   momoTree.setPostOrderScore(keyworedList);
   tree1.setPostOrderScore(keyworedList);
   tree2.setPostOrderScore(keyworedList);
   tree3.setPostOrderScore(keyworedList);
   tree4.setPostOrderScore(keyworedList);
   tree5.setPostOrderScore(keyworedList);
   tree6.setPostOrderScore(keyworedList);

//    依分數排順序
       lst.add(littleDuckTree.root);
       lst.add(momoTree.root);
       lst.add(iqiyiTree.root);
       lst.add(pttTree.root);
       lst.add(tree1.root);
       lst.add(tree2.root);
       lst.add(tree3.root);
       lst.add(tree4.root);
       lst.add(tree5.root);
       lst.add(tree6.root);
       Collections.sort(lst, Collections.reverseOrder(new scoreComparator()));
       for(WebNode iNode : lst) {
        System.out.println(iNode.webPage.name+"  "+iNode.nodeScore);
       }
       String[][] s1 = new String[lst.size()][2];
       for(int i=0;i<lst.size();i++) {
        s1[i][0] = lst.get(i).webPage.name;
        s1[i][1] = lst.get(i).webPage.url;
       }
       request.setAttribute("query", s1);
       
       request.getRequestDispatcher("googleitem.jsp")
    .forward(request, response); 
       
  } catch (InterruptedException e1) {
   // TODO Auto-generated catch block
   e1.printStackTrace();
  }
  
  
  


       
      
      
      
  
  
  
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  doGet(request, response);
 }

}