#!/bin/bash

# Konfiguration: Definiere Variablen
JBOSS_HOME="/home/vincent/Desktop/Repository/Series-Website/Server/bin"  # Ersetze mit dem tatsächlichen Pfad zu deinem WildFly-Server
DOCKER_COMPOSE_FILE="/home/vincent/Desktop/Repository/Series-Website/Series-Website/docker-compose.yaml"
CONTAINER_NAME="java_ee_postgres"

# Schritt 1: Überprüfen, ob bereits ein PostgreSQL-Service läuft
echo "Überprüfen, ob ein PostgreSQL-Service bereits läuft..."
if systemctl is-active --quiet postgresql; then
    echo "Ein PostgreSQL-Service läuft bereits. Stoppe den Service..."
    sudo systemctl stop postgresql
else
    echo "Kein PostgreSQL-Service läuft derzeit."
fi

# Schritt 2: Docker-Container starten
echo "Starte den PostgreSQL-Docker-Container..."
if [ ! -f "$DOCKER_COMPOSE_FILE" ]; then
    echo "Docker Compose-Datei ($DOCKER_COMPOSE_FILE) nicht gefunden. Bitte stelle sicher, dass die Datei im richtigen Verzeichnis vorhanden ist."
    exit 1
fi

sudo docker-compose -f $DOCKER_COMPOSE_FILE up -d

# Warten auf den Container
echo "Warte auf den PostgreSQL-Container..."
# Container prüfen
while ! sudo docker ps -q --filter "name=java_ee_postgres"; do
  sleep 1
done

echo "Starte Wildfly Server ..."
cd $JBOSS_HOME
./standalone.sh
