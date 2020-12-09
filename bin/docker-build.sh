#!/usr/bin/env sh

# get application name from project directory
APP_NAME=${PWD##*/}
GCP_PROJECT="$(gcloud config get-value project)"
IMG_TAG="$(git rev-parse --short HEAD)"
IMG=gcr.io/"$GCP_PROJECT"/"$APP_NAME"


docker build -t "$IMG":"$IMG_TAG" -t "$IMG":latest . && \
  docker push "$IMG":"$IMG_TAG" && \
  docker push "$IMG":latest
