#!/usr/bin/env sh

# install required dependences to run applicaiton

gcloud auth login

SET project_name = "test-project"
echo "project name" $project_name


tar -zcvf archive_name.tar.gz folder_to_compress
