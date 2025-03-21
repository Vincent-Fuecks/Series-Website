#!/bin/bash

# Konfiguration: Definiere Variablen
JBOSS_HOME="/home/vincent/Desktop/Repository/Series-Website/Server"  # Ersetze mit dem tatsächlichen Pfad zu deinem WildFly-Server
POSTGRES_JAR_PATH="~/Downloads/postgresql-42.7.5.jar"  # Ersetze mit dem tatsächlichen Pfad zum PostgreSQL JDBC-Treiber
JDBC_URL="jdbc:postgresql://localhost:5432/postgres"
DB_USER="admin"
DB_PASSWORD="password"
DATA_SOURCE_NAME="PostgresDS"
JNDI_NAME="java:jboss/datasources/PostgresDS"
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

# PostgreSQL CLI öffnen
echo "Warte auf PostgreSQL-Container..."
docker exec -it java_ee_postgres bash -c "PGPASSWORD='password' psql -U admin -d postgres"
# Schritt 3: Erstellen der Tabelle im PostgreSQL-Container
echo "Erstelle die Datenbanktabelle im PostgreSQL-Container..."
sudo docker exec -it $CONTAINER_NAME \
    bash -c "PGPASSWORD='$DB_PASSWORD' psql -U $DB_USER -d postgres -c 'CREATE TABLE IF NOT EXISTS public.account (id SERIAL PRIMARY KEY, name VARCHAR, email VARCHAR UNIQUE);'"

# Schritt 4: JBoss-CLI-Sitzung starten
echo "Starte JBoss-CLI-Sitzung..."
$JBOSS_HOME/bin/jboss-cli.sh -c <<EOF

# Schritt 5: PostgreSQL als Modul hinzufügen
echo "Füge PostgreSQL-Modul hinzu..."
module add \
  --name=org.postgresql \
  --resources=$POSTGRES_JAR_PATH \
  --dependencies=javax.api,javax.transaction.api

# Schritt 6: PostgreSQL JDBC-Treiber hinzufügen
echo "Füge PostgreSQL JDBC-Treiber hinzu..."
/subsystem=datasources/jdbc-driver=postgres:add( \
  driver-name="postgres", \
  driver-module-name="org.postgresql", \
  driver-class-name="org.postgresql.Driver" \
)

# Schritt 7: PostgreSQL-Datenquelle hinzufügen
echo "Füge PostgreSQL-Datenquelle hinzu..."
data-source add \
  --jndi-name=$JNDI_NAME \
  --name=$DATA_SOURCE_NAME \
  --connection-url=$JDBC_URL \
  --driver-name=postgres \
  --user-name=$DB_USER \
  --password=$DB_PASSWORD

EOF

# Überprüfen, ob das Skript erfolgreich ausgeführt wurde
if [ $? -eq 0 ]; then
    echo "PostgreSQL-Datenquelle erfolgreich registriert!"
else
    echo "Fehler bei der Registrierung der PostgreSQL-Datenquelle."
    exit 1
fi

# Schritt 8: Docker-Container herunterfahren (optional)
#docker-compose down

echo "Setup abgeschlossen!"
