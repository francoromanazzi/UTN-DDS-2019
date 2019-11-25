#!/usr/bin/env bash

mv src/main/resources/META-INF/persistence.xml src/main/resources/META-INF/persistence-local.xml
mv src/main/resources/META-INF/persistence-deploy.xml src/main/resources/META-INF/persistence.xml
mvn clean heroku:deploy -Dmaven.test.skip=true
mv src/main/resources/META-INF/persistence.xml src/main/resources/META-INF/persistence-deploy.xml
mv src/main/resources/META-INF/persistence-local.xml src/main/resources/META-INF/persistence.xml

read x