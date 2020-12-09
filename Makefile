.PHONY: deploy docker-build test encrypt-secrets

deploy:
	bash bin/deploy.sh

docker-build:
	bash bin/docker-build.sh

test:
	bash bin/test.sh

encrypt-secrets:
	bash bin/encrypt-secrets.sh config.json