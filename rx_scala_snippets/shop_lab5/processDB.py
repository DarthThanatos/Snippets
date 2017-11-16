import re 

with open("query_result", "r+", encoding="utf8") as f:
	content = f.read()
	content = re.sub(r'\"', r'"', content)

with open("db", "w+", encoding="utf8") as f:
		f.write(content)