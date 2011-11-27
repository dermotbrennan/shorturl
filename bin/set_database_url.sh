#!/bin/bash
# Sets DATABASE_URL parameter in local shell
# Usage: source set_database_url.sh

DB="postgresql"
DB_USERNAME="myapp"
DB_PASSWORD="myapp"
HOST="localhost"
DB_PORT="5433" # 5432 for Postgresql<9
DB_NAME="shorturl"
export DATABASE_URL=$DB://$DB_USERNAME:$DB_PASSWORD@$HOST:$DB_PORT/$DB_NAME
echo "DATABASE_URL exported: $DATABASE_URL"
