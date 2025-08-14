#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE usuariosdb;
    CREATE USER usrsrv WITH ENCRYPTED PASSWORD 'pwdsrv';
    GRANT ALL PRIVILEGES ON DATABASE usuariosdb TO usrsrv;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "usuariosdb" <<-EOSQL
    GRANT ALL PRIVILEGES ON SCHEMA public TO usrsrv;
    GRANT CREATE ON SCHEMA public TO usrsrv;
EOSQL