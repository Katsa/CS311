from TwitterAPI import TwitterAPI
api = TwitterAPI(
      "fOhNVit4tZ9zXnvG7oTpEVKlR",
      "bzYxdpoXE8ljgdjZbcQAAVERgX6gQwWRWZNzlaQP4XG33qV3Im",
      "896215020-9iBsUlSBy2kDvSY5FL41JOhQQz2zOqC0OCbWPtpF",
      "cpnzGD6OwjxFupPaeRWpb2NnACyaDN5S3qR3rhHmy97do")


# https://dev.twitter.com/rest/reference/get/search/tweets

r = api.request('statuses/user_timeline', {'screen_name':'runmeb', "count": 200})

for item in r:
    print item['text']
        