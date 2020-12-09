# Twitter API Caller  
Twitter API caller written in Kotlin. Update searches in the file `src/main/resources/twitter-search.json`. The caller is currently setup to be deployed as a daily kubernetes cronjob and write the output to a GCS bucket for further processing.  

All requests are run concurrently using Kotlin coroutines. Depending on the number of search terms, the caller can pull roughly 200,00 + tweets in < 30 seconds.  

Additional work needs to be done to handle API rate limiting.
