# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.common.exceptions import NoSuchElementException
import time
from datetime import datetime
from Entity.article import Article
from RepositoryContext import RepositoryContext
class NewsCrawlerPTT:
	ARTICLE_UPPER_BOUND = 25
	DELAY=3
	BOARDNAME = 'Tainan'
	STARTPAGE = 1
	def configure(self, artNumber=25, delay=3, boardName='Tainan', startPage = 1):
		self.ARTICLE_UPPER_BOUND=artNumber
		self.DELAY=delay
		self.BOARDNAME=boardName
		self.STARTPAGE=startPage

	def crawlFromOld(self, db, app):
		browser = webdriver.Chrome()
		repoContext = RepositoryContext(db)
		browser.get('https://www.ptt.cc/bbs/'+self.BOARDNAME+'/index'+str(self.STARTPAGE)+'.html')
		assert ('批踢踢') in browser.title
		article_number = 0

		# article attributes
		while article_number<=self.ARTICLE_UPPER_BOUND:
			mainWindow = browser.current_window_handle
			divs = browser.find_elements_by_css_selector('div.title')
			for d in divs:
				# Open the element in a new Window
				d.find_element_by_tag_name('a').send_keys(Keys.SHIFT + Keys.ENTER)
				browser.switch_to_window(browser.window_handles[1])
				# Here you do whatever you want in the new window
				content = browser.find_elements_by_css_selector('#main-content')[0].text
				temp =  browser.find_elements_by_css_selector('span.article-meta-tag')
				article = self._buildArticle(temp)
				article_number = article_number + 1
				# Close the window and continue
				try:
					browser.close()
				except Exception as e:
					print ('Can\'t close!!!', e)
				print ('sleep for',self.DELAY, 'seconds!')
				time.sleep(self.DELAY)
				with app.app_context():
					repoContext.insert(article)
				print ('switch')
				browser.switch_to_window(mainWindow)
				if article_number>self.ARTICLE_UPPER_BOUND:
					break
			
			page_changes = browser.find_elements_by_css_selector('a.btn.wide')
			for pc in page_changes:
				if "下頁" in pc.text:
					print (pc.get_attribute("href"))
					mainWindow = browser.get(pc.get_attribute("href"))
					break
		browser.quit()
	def crawlFromNew(self, db, app):
		browser = webdriver.Chrome()
		repoContext = RepositoryContext(db)
		browser.get('https://www.ptt.cc/bbs/'+self.BOARDNAME+'/index'+str(self.STARTPAGE)+'.html')
		assert ('批踢踢') in browser.title
		article_number = 0

		# article attributes
		while article_number<=self.ARTICLE_UPPER_BOUND:
			mainWindow = browser.current_window_handle
			divs = browser.find_elements_by_css_selector('div.title')
			for d in divs:
				# Open the element in a new Window
				try:
					title_achor = d.find_element_by_tag_name('a')
					title_achor.send_keys(Keys.SHIFT + Keys.ENTER)
				except NoSuchElementException as e:
					print('no anchor found!')
					continue

				browser.switch_to_window(browser.window_handles[1])
				# Here you do whatever you want in the new window
				content = browser.find_elements_by_css_selector('#main-content')[0].text
				temp =  browser.find_elements_by_css_selector('span.article-meta-tag')
				article = self._buildArticle(temp)
				article_number = article_number + 1

				# Close the window and continue
				try:
					browser.close()
				except Exception as e:
					print ('Can\'t close!!!', e)
				print ('sleep for',self.DELAY, 'seconds!')
				time.sleep(self.DELAY)
				with app.app_context():
					
					repoContext.insert(article)
				print ('switch')
				browser.switch_to_window(mainWindow)
				if article_number>self.ARTICLE_UPPER_BOUND:
					break
			
			page_changes = browser.find_elements_by_css_selector('a.btn.wide')
			for pc in page_changes:
				if "上頁" in pc.text:
					print (pc.get_attribute("href"))
					mainWindow = browser.get(pc.get_attribute("href"))
					break
		browser.quit()
	def _buildArticle(self,title_set):
		title = ""
		articleType = ""
		content = ""
		author = ""
		nickname = ""
		publishedTime = None
		for t in title_set:
			if t.text =='標題':
				title = t.find_elements_by_xpath("..")[0].find_elements_by_css_selector('span.article-meta-value')[0].text
				if ('[' in title) and (']' in title):
					articleType = title[title.index('[')+1:title.index(']')]
					title = title[title.index(']')+1:]
				# Record article number
				#article_number = article_number + 1
			elif t.text == '作者':
				author = t.parent.find_elements_by_css_selector('span.article-meta-value')[0].text.split("(")[0]
				if len(t.find_elements_by_xpath("..")[0].find_elements_by_css_selector('span.article-meta-value')[0].text.split("(")) >1:
					nickname = t.find_elements_by_xpath("..")[0].find_elements_by_css_selector('span.article-meta-value')[0].text.split("(")[1].replace(")","")
				else:
					nickname = 'N/A'
			elif t.text == '時間':
				publishedTime_string = t.find_elements_by_xpath("..")[0].find_elements_by_css_selector('span.article-meta-value')[0].text
				publishedTime = datetime.strptime(publishedTime_string, '%a %b %d %H:%M:%S %Y')
		art = Article()
		art.initialize(ArticleType=articleType, ArticleTitle=title, ArticleAuthorID=author, ArticleAuthorNick=nickname,
									ArticlePublishedTime=publishedTime, ArticleContent=content, BoardName=self.BOARDNAME)
		return art