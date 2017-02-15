# -*- coding: utf-8 -*-
from selenium import webdriver
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from PTTNewsCrawler import PTTNewsCrawler
from RepositoryContext import RepositoryContext


class Trigger:
	if __name__=="__main__":
		# app configuration
		app = Flask(__name__)
		app.config.from_pyfile('db.cfg')
		# notify db with the app
		db = SQLAlchemy(app)

		browser = webdriver.Chrome()
		boardName = "Tainan"
		crawler = PTTNewsCrawler()
		crawler.configure(artNumber=500, delay=3, 
			boardName='Tainan', startPage = 33)
		crawler.crawl(db, app)
