import sqlite3
import csv

with open('DbSql\\create_db.sql') as f:
	create_db_sql = f.read()
db = sqlite3.connect('ptt.db')

with db:
	db.executescript(create_db_sql)
	#db.executemany('INSERT INTO  members (name, group_name) VALUES (?, ?)', members)
	#c = db.execute('SELECT * FROM members LIMIT 3')

"""
with open('./members.csv', newline='') as f:
	csv_reader = csv.DictReader(f)
    members = [
        (row[' '], row[' '])
        for row in csv_reader
    ]
"""
