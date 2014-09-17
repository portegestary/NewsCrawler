package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import parser.NewsHtmlParseData;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class AppleNewsCrawler extends WebCrawler{
	 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
		      + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	 private String repoPath = "E:\\Apple.txt"; 
		  /**
		   * You should implement this function to specify whether the given url
		   * should be crawled or not (based on your crawling logic).
		   */
	 		@Override
	 		public boolean shouldVisit(WebURL url) {
		    String href = url.getURL().toLowerCase();
		    if(href.startsWith("http://www.appledaily.com.tw/"))
		    {
		    	
		    }
		    
		    return href.startsWith("http://www.appledaily.com.tw/");
		  }

		  /**
		   * This function is called when a page is fetched and ready to be processed
		   * by your program.
		   */
		  @Override
		  public void visit(Page page) {
		    int docid = page.getWebURL().getDocid();
		    String url = page.getWebURL().getURL();
		    String domain = page.getWebURL().getDomain();
		    String path = page.getWebURL().getPath();
		    String subDomain = page.getWebURL().getSubDomain();
		    String parentUrl = page.getWebURL().getParentUrl();
		    String anchor = page.getWebURL().getAnchor();

		    System.out.println("Docid: " + docid);
		    System.out.println("URL: " + url);
		    //System.out.println("Domain: '" + domain + "'");
		    //System.out.println("Sub-domain: '" + subDomain + "'");
		    //System.out.println("Path: '" + path + "'");
		    //System.out.println("Parent page: " + parentUrl);
		   // System.out.println("Anchor text: " + anchor);

		    if (page.getParseData() instanceof HtmlParseData) {
		    	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		      String text = htmlParseData.getText();
		      String html = htmlParseData.getHtml();
		      Document doc = Jsoup.parseBodyFragment(html);
		      List<WebURL> links = htmlParseData.getOutgoingUrls();
		      /*
		       * Get content and timestamp from html
		       */
		      Elements textContent = doc.select(".articulum > p,.articulum >h2");
		      Elements newsTime = doc.select(".gggs > time");
		      
		      String content = "";
		      for(int section = 0 ; section<textContent.size() ; section++)
		      {
		    	  content = content +""+ textContent.get(section).text();
		      }
		      DecimalFormat twoDeg = new DecimalFormat("00");
		      String title = htmlParseData.getTitle();
		      String time = newsTime.get(0).text().split("年")[0]
		    		  +"-"+twoDeg.format(Integer.parseInt(newsTime.get(0).text().split("年")[1].split("月")[0]))
		    		  +"-"+twoDeg.format(Integer.parseInt(newsTime.get(0).text().split("年")[1].split("月")[1].replace("日","")));
		      System.out.println("Time: " +  time);
		      System.out.println("Title: " + title);
		      System.out.println("Text: " +  content);
		      //System.out.println("html: " + htmlParseData.getHtml());
		      /*
		       System.out.println("Text length: " + text.length());
		      System.out.println("Text: " +  content);
		      
		      System.out.println("Html length: " + html.length());
		      System.out.println("Number of outgoing links: " + links.size());
		      */
		      if(!isDuplicated(title)){
			    try {
					FileWriter fw = new FileWriter(new File(repoPath),true);
					fw.append(time + "\t" + htmlParseData.getTitle() + "\t" +url + "\t"+ content + "\n");
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    }
		    
		    Header[] responseHeaders = page.getFetchResponseHeaders();
		    if (responseHeaders != null) {
		      //System.out.println("Response headers:");
		      for (Header header : responseHeaders) {
		        //System.out.println("\t" + header.getName() + ": " + header.getValue());
		      }
		    }

		    System.out.println("=============");
		  }
		  private boolean isDuplicated (String title)
		  {
			  
			  Vector<String[]> all = new Vector<String[]>();
				try {
					String line = "";
					File repoFile = new File(repoPath);
					if(!repoFile.exists())
					{
						repoFile.createNewFile();
						return false;
					}
					BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(new File(repoPath))));
					while((line=br.readLine())!=null)
					{
						all.add(line.split("\t"));
						
					}
					for(int count = 0 ;count<all.size() && all.size()!=0;count++)
					{
							 if(all.get(count)[1]==null && count==all.size()-1)
							{
								return true;
							}
							else if(all.get(count)[1].equals(title))
							{
								return true;
							}
							
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int matched = 0;


				//System.out.println("Checking Complete! " + matched + " matched!");
				return false;
		  }
}
