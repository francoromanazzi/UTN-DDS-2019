#!/usr/bin/env bash

if mv src/main/resources/META-INF/persistence.xml src/main/resources/META-INF/persistence-local.xml;
then
    if mv src/main/resources/META-INF/persistence-deploy.xml src/main/resources/META-INF/persistence.xml;
    then
        if mvn clean heroku:deploy -Dmaven.test.skip=true;
        then
            echo "SUCCESS"
        else
            echo "ERROR IN DEPLOY"
        fi

        mv src/main/resources/META-INF/persistence.xml src/main/resources/META-INF/persistence-deploy.xml
    else
        echo "Error al renombrar persistence-deploy.xml a persistence.xml"
    fi

    mv src/main/resources/META-INF/persistence-local.xml src/main/resources/META-INF/persistence.xml
else
    echo "Error al renombrar persistence.xml a persistence.xml"
fi

read x