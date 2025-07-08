#!/bin/bash
VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)

if [ -z "$VERSION" ]; then
  echo "No se pudo obtener la versión del pom.xml"
  exit 1
fi

APP_VERSION=$VERSION docker-compose down
APP_VERSION=$VERSION docker-compose up -d --build
