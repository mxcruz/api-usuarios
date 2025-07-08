#!/bin/bash

APP_PORT=8888
PROFILE=it
PID_FILE="/tmp/spring-boot-app.pid"

# Start Spring Boot in background with nohup to ensure it stays running
nohup ./mvnw spring-boot:run -Dspring-boot.run.profiles=$PROFILE > spring-boot.log 2>&1 &
SPRING_PID=$!

# Save PID for later stop
echo $SPRING_PID > $PID_FILE

# Wait for health endpoint
echo "Waiting for Spring Boot to be UP..."
for i in {1..30}; do
  STATUS=$(curl -s http://localhost:$APP_PORT/actuator/health | jq -r .status)
  if [ "$STATUS" = "UP" ]; then
    echo "Spring Boot is UP!"
    exit 0
  fi
  sleep 2
done

echo "Spring Boot did not start in time"
kill $SPRING_PID
exit 1