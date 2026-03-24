#!/bin/bash
# =======================================================
# 🚀 Tomcat + Gradle Auto-Restart Script
# Author: Akib (Certified Tech Wizard™)
# =======================================================

TOMCAT_DIR="/usr/local/tomcat-9"
CATALINA_START="$TOMCAT_DIR/bin/startup.sh"
CATALINA_STOP="$TOMCAT_DIR/bin/shutdown.sh"
WAR_SOURCE="build/libs/transportation_system-1.0-SNAPSHOT.war"
WAR_DEST="$TOMCAT_DIR/webapps/transportation_system.war"
LOG_FILE="$TOMCAT_DIR/logs/catalina.out"

kill_tomcat() {
  echo "🔍 Checking for running Tomcat process..."
  PID=$(ps -ef | grep "$TOMCAT_DIR" | grep -v grep | awk '{print $2}')

  if [ -n "$PID" ]; then
    echo "Tomcat is running with PID: $PID"
    echo "➡Attempting graceful shutdown..."
    sudo "$CATALINA_STOP" >/dev/null 2>&1

    sleep 5

    if ps -p "$PID" > /dev/null; then
      echo "Still running... fou̶rcing kill "
      sudo kill -9 "$PID"
    else
      echo "Tomcat stopped successfully."
    fi
  else
    echo "Tomcat is not running."
  fi
}

start_tomcat() {
  echo "Starting Tomcat..."
  sudo "$CATALINA_START" >/dev/null 2>&1
}

gradle_clean_build() {
  echo "Running Gradle clean build..."
  gradle clean build
  if [ $? -ne 0 ]; then
    echo "Gradle build failed! Check the logs."
    exit 1
  fi
}

remove_existing() {
  echo "🧼 Removing old WAR and exploded folders..."
  sudo rm -rf "$TOMCAT_DIR/webapps/transportation_system"*
}

copy_to_tomcat() {
  echo "📦 Deploying new WAR to Tomcat..."
  if [ ! -f "$WAR_SOURCE" ]; then
    echo "WAR file not found: $WAR_SOURCE"
    exit 1
  fi
  sudo cp "$WAR_SOURCE" "$WAR_DEST"
}

show_log() {
  echo "📜 Showing live Tomcat logs..."
  if [ -f "$LOG_FILE" ]; then
    tail -f "$LOG_FILE"
  else
    echo "⚠️  Log file not found: $LOG_FILE"
  fi
}

move_to_webapps() {
  remove_existing
  copy_to_tomcat
}

initiating() {
  kill_tomcat
  gradle_clean_build
  move_to_webapps
}

start_all() {
  initiating
  start_tomcat

  sleep 5

  echo "✅ Tomcat restarted successfully!"
  echo "🌐 Check app: http://localhost:8080/"
  show_log
}

# ================================
# 🔧 Main Logic
# ================================
case "$1" in
  start | "")
    start_all
    ;;
  kill)
    kill_tomcat
    ;;
  tomcat)
    start_tomcat
    ;;
  init)
    initiating
    ;;
  *)
    echo "Usage: $0 {start|kill|tomcat|init}"
    exit 1
    ;;
esac
