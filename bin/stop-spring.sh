#!/bin/bash

PID_FILE="/tmp/spring-boot-app.pid"

if [ -f "$PID_FILE" ]; then
  SPRING_PID=$(cat $PID_FILE)
  echo "Stopping Spring Boot (PID $SPRING_PID)..."
  kill $SPRING_PID
  rm $PID_FILE
else
  echo "PID file not found. Nothing to stop."
fi