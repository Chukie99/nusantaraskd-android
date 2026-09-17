#!/usr/bin/env bash
set -e
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"
if [ -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    java -jar gradle/wrapper/gradle-wrapper.jar "$@"
else
    echo "ERROR: Gradle wrapper not found. Please run 'gradle wrapper' to install."
    exit 1
fi
