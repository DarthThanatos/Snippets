import wikipedia
import os
import re

max_amount = 10000
checkpoint = 30
saved_pages = os.listdir("wikipedia_articles")
saved_pages_amount = saved_pages.__len__()

while saved_pages_amount < max_amount:

    random_pages =  wikipedia.random(10)
    for random_page in random_pages:

        if saved_pages_amount % checkpoint == 0:
            print "verifying if progress number is right"
            right_amount = os.listdir("wikipedia_articles").__len__()
            if saved_pages_amount != right_amount:
                print "amount of saved pages not right, correcting from current", saved_pages_amount, "to actual:", right_amount
                saved_pages_amount = right_amount
            else:
                print "amount correct, skipping adjusting phase"

        random_page = random_page.encode('utf8')
        try:
            print "{0}% [{1}/{2}] searching for". format(int(saved_pages_amount/float(max_amount) * 100),saved_pages_amount, max_amount), random_page,
            wiki_page = wikipedia.page(random_page)
        except Exception as e:
            print random_page, " caused error:", e.message
            continue

        try:
            content = wiki_page.content.encode('utf8')
            file_name = "wikipedia_articles/{0}.txt".format(re.sub(r'[\\/*?:"<>|]', "", random_page))
            print "saving to", file_name
            with open(file_name, "w") as f:
                f.write(content)
            saved_pages_amount +=1
        except Exception as e:
            print random_page, "error receiving content of article:", e.message
