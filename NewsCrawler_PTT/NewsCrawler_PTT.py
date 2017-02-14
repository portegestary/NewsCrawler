# -*- coding: utf-8 -*-

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.remote.webdriver import WebDriver
import time
browser = webdriver.Chrome()
boardName = "Tainan"
browser.get('https://www.ptt.cc/bbs/'+boardName+'/index1.html')
#assert ('批踢踢'.encode('utf-8')) in browser.title
print 'before'
#elem = browser.find_element_by_class_name('alpha tweet-title')  # Find the search box
i=0
string = ''
ARTICLE_UPPER_BOUND = 25
article_number = 0
# article attributes
title = []
content = []
author = []
nickname = []
publishedTime = []
delay=1
while article_number<ARTICLE_UPPER_BOUND:
	mainWindow = browser.current_window_handle
	divs = browser.find_elements_by_css_selector('div.title')
	for d in divs:
		# Open the element in a new Window
		d.find_element_by_tag_name('a').send_keys(Keys.SHIFT + Keys.ENTER)
		browser.switch_to_window(browser.window_handles[1])
		# Here you do whatever you want in the new window
		content = browser.find_elements_by_css_selector('#main-content')
		temp =  browser.find_elements_by_css_selector('span.article-meta-tag')
		for t in temp:
			if t.text.encode('utf-8') =='標題':
				title = t.parent.find_elements_by_css_selector('span.article-meta-value')[0].text
				article_number = article_number + 1
			elif t.text.encode('utf-8') == '作者':
				author = t.parent.find_elements_by_css_selector('span.article-meta-value')[0].text.split("(")[0]
				nickname = t.parent.find_elements_by_css_selector('span.article-meta-value')[0].text.split("(")[1].replace(")","")
			elif t.text.encode('utf-8') == '時間':
				publishedTime = t.parent.find_elements_by_css_selector('span.article-meta-value')[0].text
			else:
				continue
		print 'title:',title.encode('utf-8')
		print 'content:',content.encode('utf-8')
		print 'author:',author.encode('utf-8')
		print 'nickname:', nickname.encode('utf-8')
		print 'publishedTime:',publishedTime.encode('utf-8')				
		if article_number<ARTICLE_UPPER_BOUND:
			break
		# Close the window and continue
		#browser.find_element_by_tag_name('body').send_keys(Keys.CONTROL + 'w')
		#browser.find_element_by_tag_name('body').send_keys(Keys.CONTROL + 'w')
		browser.close()
		time.sleep(delay)
		browser.switch_to_window(mainWindow)
	
	
	page_changes = browser.find_elements_by_css_selector('a.btn.wide')
	for pc in page_changes:
		if "下頁" in pc.text.encode('utf-8'):
			print pc.get_attribute("href")
			mainWindow = browser.get(pc.get_attribute("href"))
			break

'''
elems = browser.find_elements_by_css_selector('div.title>a')

for e in elems:
	
	actions = ActionChains(browser)
	print 'move'
	actions.move_to_element(e)
	print 'click'
	actions.click(e)
	print 'perform'
	actions.perform()

	e.click()
	#browser.implicitly_wait(10)
	print 'browser'
	temp =  browser.find_elements_by_css_selector('span.article-meta-tag')
	print i
	i=i+1
	for t in temp:
		if t.text.encode('utf-8') =='標題':
			title = t.parent.find_elements_by_css_selector('span.article-meta-value')
			content = browser.find_elements_by_css_selector('#main-content')
			break

	print 'title:',title[0].text.encode('utf-8')
	print 'content:',content[0].text.encode('utf-8')
	browser.back()
	#print str(i)+':'+e.text.encode('utf-8')+'\n'
'''
browser.quit()
print string

#elem.send_keys('seleniumhq' + Keys.RETURN)

browser.quit()