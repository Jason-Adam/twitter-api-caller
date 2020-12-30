# Twitter API Caller  
Twitter API caller written in Kotlin. Update searches in the file `src/main/resources/twitter-search.json`. The caller is currently setup to be deployed as a daily kubernetes cronjob and write the output to a GCS bucket for further processing.  

All requests are run concurrently using Kotlin coroutines. Depending on the number of search terms, the caller can pull roughly 200,00 + tweets in < 30 seconds.  

Additional work needs to be done to handle API rate limiting.  

## Environment Variables  
Required Variables are located in the `example.env` file.  

## Running the Bot Locally  
The bot assumes you are authenticated into a GCP project via the CLI. It will use your default project and credentials for loading the extracted tweets.  

It also assumes you have a storage bucket called `twitter-api` in your project.  

The `makefile` commands as setup to build / push the image to a GCP container registry, then deploy to GKE, but you can build the image locally with:  

```bash  
docker build -t twitter-api:latest .
```  

Running the bot:  

```bash  
docker run -env-file .env twitter-api:latest
```
