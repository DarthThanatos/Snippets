import re

file_name = "00002.vcf"
CONTACT_NAME_PREFIX_ONE = "FN:"
CONTACT_NAME_PREFIX_TWO = "FN;"
CONTACT_NUMBER_PREFIX = "TEL;CELL:"
CHARSET_PREFIX = "CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:"

i = 0
res = []

with open(file_name, "r") as f:
	content = f.read()
	all = re.findall(r'BEGIN:VCARD(.+?)END:VCARD', content,re.DOTALL)
	for one in all: 
		if (CONTACT_NAME_PREFIX_ONE in one or CONTACT_NAME_PREFIX_TWO in one) and CONTACT_NUMBER_PREFIX in one:				
			contact_name = None 
			phone_number = None
			for line in one.split():
				if line.startswith(CONTACT_NAME_PREFIX_ONE):
					contact_name = line.replace(CONTACT_NAME_PREFIX_ONE, "")
				elif line.startswith(CONTACT_NAME_PREFIX_TWO):
					numbers = filter(
						lambda x: x != '', 
						line.replace(CONTACT_NAME_PREFIX_TWO + CHARSET_PREFIX,"")
							.split("=")
					)
					contact_name = "".join(map(lambda y:  chr(int(y,16)), numbers))
				elif line.startswith(CONTACT_NUMBER_PREFIX):
					phone_number = line.replace(CONTACT_NUMBER_PREFIX, "")
			if contact_name is not None and phone_number is not None:
				res.append((contact_name, phone_number))
			i+=1
	res.sort(key=lambda s: s[0])
	for i, (contact_name, phone_number) in enumerate(res):
		print (
			(str(i) + ". " + contact_name + ": " + phone_number)
				.encode('iso-8859-1')
				.decode('utf-8')
		)
		