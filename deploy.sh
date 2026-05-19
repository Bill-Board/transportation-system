#!/bin/bash

# Configuration
TOMCAT_DIR="/usr/local/tomcat"
WEBAPPS_DIR="$TOMCAT_DIR/webapps"
PORT=8080

echo "1. Stopping Tomcat server..."
# Try to stop Tomcat, ignoring errors if it's already stopped
$TOMCAT_DIR/bin/shutdown.sh 2>/dev/null
# Give it a few seconds to shut down gracefully
sleep 3

echo "2. Building the project with Gradle..."
./gradlew clean build -x test

# Check if build was successful
if [ $? -ne 0 ]; then
    echo "Build failed! Aborting deployment."
    exit 1
fi

# Find the built WAR file
WAR_FILE=$(find build/libs -name "*.war" | head -n 1)
if [ -z "$WAR_FILE" ]; then
    echo "Error: No WAR file found in build/libs/"
    exit 1
fi

WAR_BASENAME=$(basename "$WAR_FILE" .war)

echo "3. Cleaning up old deployment and copying new WAR file..."
# Remove old unzipped folder to ensure a clean deployment
rm -rf "$WEBAPPS_DIR/$WAR_BASENAME"
cp "$WAR_FILE" "$WEBAPPS_DIR/"

if [ $? -ne 0 ]; then
    echo "Failed to copy WAR file. Please check permissions for $WEBAPPS_DIR or run with sudo."
    exit 1
fi

echo "4. Starting Tomcat server..."
$TOMCAT_DIR/bin/startup.sh

echo "======================================================="
echo "✅ Deployment successful!"
echo "⏳ Server is starting up. It may take a moment to initialize."
echo ""
echo "🔗 Your application will be available at:"
echo "http://localhost:${PORT}/${WAR_BASENAME}/"
echo "======================================================="
