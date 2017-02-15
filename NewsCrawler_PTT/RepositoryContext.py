from Entity.article import Article

class RepositoryContext:
	def __init__(self, db):
		self.db = db
		self.Article = Article()
	def insert(self, added_object):
		success = True
		uniq = added_object.get_unique(self.db)
		if uniq is None:
			self.db.session.add(added_object)
			try:
				self.db.session.commit()
				print ("success")
			except Exception as e:
				#log your exception in the way you want -> log to file, log as error with default logging, send by email. It's upon you
				print ("fail", e)
				self.db.session.rollback()
				self.db.session.flush() # for resetting non-commited .add()
				success = False
		else:
			success = False
		return success
	def delete(self, deleted_object):
		success = True
		selected = None
		uniq = deleted_object.get_unique(self.db)
		if uniq is not None:
			try:
				self.db.session.delete(deleted_object)
				self.db.session.commit()
				print ('success')
			except Exception as e:
				print ('fail', e)
				self.db.session.rollback()
				self.db.session.flush() # for resetting non-commited .add()
				success = False
		else:
			success = False
			print ('No Such Object To Be Deleted!')
		return success
	def select(self, table, **kwargs):
		success = True
		selected = None
		table_clz = table.__class__
		# No filter constraints assigned then all entries are queried
		print (kwargs)
		if bool(kwargs)==False:
			try:
				selected = self.db.session.query(table_clz).all()
				print ('No Constraint query, success', selected)
			except Exception as e:
				print ('fail', e)
				self.db.session.rollback()
				self.db.session.flush() # for resetting non-commited .add()

		elif len(kwargs)==1:
			for p,q in kwargs.items():
				try:
					selected = self.db.session.query(table_clz).filter(getattr(table_clz,p) == q).all()
					print ('One constrant query, success', selected)
				except Exception as e:
					print ('fail', e)
					self.db.session.rollback()
					self.db.session.flush() # for resetting non-commited .add()
		else:
			query = None
			for p,q in kwargs.items():
				if query is None:
					query = self.db.session.query(table_clz).filter(getattr(table_clz,p) == q)
				else:
					query = query.filter(getattr(table_clz,p) == q)
			try:
				selected = query.all()
				print ('success', selected)
			except Exception as e:
				print ('fail', e)
				self.db.session.rollback()
				self.db.session.flush() # for resetting non-commited .add()

		return selected
