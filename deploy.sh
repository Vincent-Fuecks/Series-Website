#!/bin/bash

# Konfiguration: Definiere die Variablen für den Projektpfad und das Ziel-WAR
PROJECT_PATH="/home/vincent/Desktop/Repository/Series-Website/Series-Website"
TARGET_WAR_PATH="$PROJECT_PATH/target/series-website.war"
SERVER_BIN_PATH="/home/vincent/Desktop/Repository/Series-Website/Server/bin"
CLI_SCRIPT="$SERVER_BIN_PATH/jboss-cli.sh"
DEPLOYMENT_PATH="/home/vincent/Desktop/Repository/Series-Website/Series-Website/target/series-website.war"

# Schritt 1: Maven clean und package ausführen
echo "Maven clean und package wird ausgeführt..."
cd $PROJECT_PATH
mvn clean package

# Überprüfen, ob der Maven-Build erfolgreich war
if [ $? -ne 0 ]; then
    echo "Maven-Build ist fehlgeschlagen. Bitte überprüfen Sie den Build-Fehler."
    exit 1
fi

# Schritt 2: JBoss Deployment durchführen
echo "Deployment auf JBoss wird ausgeführt..."
cd $SERVER_BIN_PATH
./jboss-cli.sh -c --command="deploy $DEPLOYMENT_PATH --force"

# Überprüfen, ob das Deployment erfolgreich war
if [ $? -ne 0 ]; then
    echo "Deployment auf JBoss ist fehlgeschlagen. Bitte überprüfen Sie den JBoss-Log."
    exit 1
fi

echo "Deployment erfolgreich abgeschlossen!"
