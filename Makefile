.PHONY: build deploy docker-build install test encrypt-secrets

build:
	bash bin/build.sh

deploy:
	bash bin/deploy.sh

docker-build:
	bash bin/docker-build.sh

install:
	bash bin/install.sh

test:
	bash bin/test.sh

encrypt-secrets:
	bash bin/encrypt-secrets.sh config.json