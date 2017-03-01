from selenium import webdriver
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from NewsCrawler_PTT import NewsCrawlerPTT
from RepositoryContext import RepositoryContext
import schedule
import time


class Scheduler:
	def job(*args, **kwargs):
		print ('start job!')
		browser = webdriver.Chrome()
		boardName = "Tainan"
		crawler = NewsCrawlerPTT()
		crawler.configure(artNumber=1, delay=3, 
			boardName=boardName, startPage = '')
		crawler.crawlFromNew(kwargs['db'], kwargs['app'])
		print('end job')
		#return schedule.CancelJob
	if __name__=="__main__":
		# app configuration
		app = Flask(__name__)
		app.config.from_pyfile('db.cfg')
		# notify db with the app
		db = SQLAlchemy(app)
		#job = self.job()
		try:
			jobInstance = schedule.every().days.at("14:56").do(job, db=db, app=app)
		except TypeError as te:
			print ('Type Error Occur!')

		print('pending...')
		while 1:
			print (schedule.next_run())
			schedule.run_pending()
			time.sleep(1)
		print('finished')			

	
		
