package main;


import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class NewsCrawlerController {
	public static void main(String[] args) throws Exception {

		 GregorianCalendar start =new  GregorianCalendar(2014, 3, 14);
		
		 GregorianCalendar end =new  GregorianCalendar(2014, 9, 14);
	    /*
	     * crawlStorageFolder is a folder where intermediate crawl data is
	     * stored.
	     */
	    String crawlStorageFolder = "E:\\crawled2\\";

	    /*
	     * numberOfCrawlers shows the number of concurrent threads that should
	     * be initiated for crawling.
	     */
	    int numberOfCrawlers = Integer.parseInt("2");

	    CrawlConfig config1 = new CrawlConfig();
	    CrawlConfig config2 = new CrawlConfig();
	    CrawlConfig config3 = new CrawlConfig();

	    config1.setCrawlStorageFolder(crawlStorageFolder+"\\H2");
	    config2.setCrawlStorageFolder(crawlStorageFolder+"\\H1");
	    config3.setCrawlStorageFolder(crawlStorageFolder+"\\H3");

	    /*
	     * Be polite: Make sure that we don't send more than 1 request per
	     * second (1000 milliseconds between requests).
	     */
	    config1.setPolitenessDelay(1500);
	    config2.setPolitenessDelay(1500);
	    config3.setPolitenessDelay(1500);

	    /*
	     * You can set the maximum crawl depth here. The default value is -1 for
	     * unlimited depth
	     */
	    config1.setMaxDepthOfCrawling(3);
	    config2.setMaxDepthOfCrawling(2);
	    config3.setMaxDepthOfCrawling(3);

	    /*
	     * You can set the maximum number of pages to crawl. The default value
	     * is -1 for unlimited number of pages
	     * 
	     */
	    config1.setMaxPagesToFetch(8000);
	    config2.setMaxPagesToFetch(10000);
	    config3.setMaxPagesToFetch(30000);
	    
	    /*
	     * Do you need to set a proxy? If so, you can use:
	     * config.setProxyHost("proxyserver.example.com");
	     * config.setProxyPort(8080);
	     *
	     * If your proxy also needs authentication:
	     * config.setProxyUsername(username); config.getProxyPassword(password);
	     */

	    /*
	     * This config parameter can be used to set your crawl to be resumable
	     * (meaning that you can resume the crawl from a previously
	     * interrupted/crashed crawl). Note: if you enable resuming feature and
	     * want to start a fresh crawl, you need to delete the contents of
	     * rootFolder manually.
	     */
	    config1.setResumableCrawling(false);
	    config2.setResumableCrawling(false);
	    config3.setResumableCrawling(false);
	    
	    /*
	     * Instantiate the controller for this crawl.
	     */
	    PageFetcher pageFetcher1 = new PageFetcher(config1);
	    PageFetcher pageFetcher2 = new PageFetcher(config2);
	    PageFetcher pageFetcher3 = new PageFetcher(config3);
	    
	   
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	    RobotstxtServer robotstxtServer1 = new RobotstxtServer(robotstxtConfig, pageFetcher1);
	    RobotstxtServer robotstxtServer2 = new RobotstxtServer(robotstxtConfig, pageFetcher2);
	    RobotstxtServer robotstxtServer3 = new RobotstxtServer(robotstxtConfig, pageFetcher3);
	    CrawlController controller1 =null;
	    CrawlController controller2 =null;
	    CrawlController controller3 =null;
	    
	    String dateInUrl1 = ""; //ex:20140314
	    String dateInUrl2 = ""; //ex:2014/3/14
	    controller1 = new CrawlController(config1, pageFetcher1, robotstxtServer1);
	    controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer2);
	    controller3 = new CrawlController(config3, pageFetcher3, robotstxtServer3);
	    //controller2.addSeed("http://web.pts.org.tw/php/news/pts_news/calander.php?d=2014-9-10" );
	    while(!start.equals(end) ){
		    
		    DecimalFormat twoDeg = new DecimalFormat("00");
		    dateInUrl1 = Integer.toString(start.get(Calendar.YEAR))
		    		+twoDeg.format(start.get(Calendar.MONTH))
		    		+twoDeg.format(start.get(Calendar.DATE));
		    dateInUrl2 = Integer.toString(start.get(Calendar.YEAR))
		    		+"-"+start.get(Calendar.MONTH)
		    		+"-"+start.get(Calendar.DATE);
		    //CrawlController controller2 = new CrawlController(config2, pageFetcher2, robotstxtServer);
		    /*
		     * For each crawl, you need to add some seed urls. These are the first
		     * URLs that are fetched and then the crawler starts following links
		     * which are found in these pages
		     */
		    System.out.println(dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/focus/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/politics/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/society/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/local/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/life/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/opinion/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/world/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/business/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/sports/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/entertainment/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/consumer/" + dateInUrl1);
		    controller1.addSeed("http://news.ltn.com.tw/newspaper/supplement/" + dateInUrl1);
		    
		    //System.out.println(dateInUrl1);
		    controller2.addSeed("http://web.pts.org.tw/php/news/pts_news/news_calander.php?d="+dateInUrl2+"&page=0");
		    controller2.addSeed("http://web.pts.org.tw/php/news/pts_news/news_calander.php?d="+dateInUrl2+"&page=1");
		    controller2.addSeed("http://web.pts.org.tw/php/news/pts_news/news_calander.php?d="+dateInUrl2+"&page=2");
		    
		    controller3.addSeed("http://www.appledaily.com.tw/appledaily/archive/"+dateInUrl1);
		    start.add(Calendar.DATE, 1);
	    }

		    /*
		     * Start the crawl. This is a blocking operation, meaning that your code
		     * will reach the line after this only when crawling is finished.
		     */
	   
		    //controller1.startNonBlocking(NewsCrawler.class, 5);
		    //controller2.startNonBlocking(PTSNewsCrawler.class, 5);
		    controller3.startNonBlocking(AppleNewsCrawler.class, 5);
		    // Wait for 30 seconds
		    Thread.sleep(20 * 1000);

		    // Send the shutdown request and then wait for finishing
		    
		    //controller1.waitUntilFinish();
		    //System.out.println("controller1 finished!");
		    //controller2.waitUntilFinish();
		    //System.out.println("controller2 finished!");
		    controller3.waitUntilFinish();
		    System.out.println("controller3 finished!");

	   

	  }
	
}
