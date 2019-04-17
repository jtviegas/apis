NAME=api
IMG=$NAME
IMG_VERSION=latest
CONTAINER=$NAME
HOST=$NAME
BUILD_DIR=target
JAR_NAME=$NAME
JAR_VERSION=0.0.1-SNAPSHOT
PORT=7710
DEBUG_PORT=7610
DOCKER_HUB_IMG=caquicode/$NAME
AZURE_APPNAME=$NAME-aprestos-apis
AZURE_RESOURCE_GROUP=aprestos-apis-springboot2
AZURE_RESOURCE_GROUP_LOCATION=westeurope
AZURE_RESOURCE_GROUP_TAGS="org=aprestos proj=apis app=$NAME"
AZURE_CONTAINER_REGISTRY=aprestos
AZURE_CONTAINER_REGISTRY_LOCATION=$AZURE_RESOURCE_GROUP_LOCATION
AZURE_APP_INSIGHTS_RESOURCE=$AZURE_RESOURCE_GROUP-appInsights