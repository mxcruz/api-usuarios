#!/bin/bash

./mvnw clean package

VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)

if [ -z "$VERSION" ]; then
  echo "No se pudo obtener la versión del pom.xml"
  exit 1
fi

docker build -t api-usuarios:$VERSION .
