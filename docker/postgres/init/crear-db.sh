#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE fluxdb;
    CREATE USER fluxusr WITH ENCRYPTED PASSWORD 'fluxpwd';
    GRANT ALL PRIVILEGES ON DATABASE fluxdb TO fluxusr;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "fluxdb" <<-EOSQL
    GRANT ALL PRIVILEGES ON SCHEMA public TO fluxusr;
    GRANT CREATE ON SCHEMA public TO fluxusr;
EOSQL