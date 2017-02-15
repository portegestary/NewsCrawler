import sqlite3
import datetime
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
class Article(db.Model):
	__tablename__ = 'ARTICLE01'
	ArticlePK = db.Column('ARTICLE01_01', db.Integer, primary_key=True)
	ArticleType = db.Column('ARTICLE01_02',db.String)
	ArticleTitle = db.Column('ARTICLE01_03',db.String)
	ArticleAuthorID = db.Column('ARTICLE01_04',db.String)
	ArticleAuthorNick = db.Column('ARTICLE01_05',db.String)
	ArticlePublishedTime = db.Column('ARTICLE01_06',db.DateTime)
	ArticleContent = db.Column('ARTICLE01_07',db.String)
	BoardName = db.Column('ARTICLE01_08',db.String)
	CreateDate = db.Column('ARTICLE01_20',db.DateTime)
	ModifyDate = db.Column('ARTICLE01_21',db.DateTime)
	CreateUser = db.Column('ARTICLE01_22',db.Integer)
	ModifyUser = db.Column('ARTICLE01_23',db.Integer)
	DataState = db.Column('ARTICLE01_24',db.Integer)

	def initialize(self, ArticleType='', ArticleTitle='', ArticleAuthorID='', ArticleAuthorNick='',
										ArticlePublishedTime=None, ArticleContent='', BoardName=''):
		self.ArticleType = ArticleType
		self.ArticleTitle = ArticleTitle
		self.ArticleAuthorID = ArticleAuthorID
		self.ArticleAuthorNick = ArticleAuthorNick
		self.ArticlePublishedTime = ArticlePublishedTime
		self.ArticleContent = ArticleContent
		self.BoardName = BoardName
		self.CreateDate = datetime.datetime.now()
		self.ModifyDate = None
		self.CreateUser = 'System'
		self.ModifyUser = None
		self.DataState = 1
	def get_unique(self, db):
		# get the session cache, creating it if necessary
		cache = db.session._unique_cache = getattr(db.session, '_unique_cache', {})
		# create a key for memoizing
		key = (self.__class__.__name__, self.ArticlePublishedTime)
		# check the cache first
		o = cache.get(key)
		print ('cache:',o)
		if o is None:
			# check the database if it's not in the cache
			#o = db.session.query(cls).filter_by(UserIDNO=userIDNO).first()
			o = Article.query.filter_by(ArticlePublishedTime=self.ArticlePublishedTime).filter_by(ArticleAuthorID=self.ArticleAuthorID).first()

		return o