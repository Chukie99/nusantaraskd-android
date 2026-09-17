#!/usr/bin/env bash
set -e
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"
echo "NusantaraSKD Android Project Structure Created"
echo "Build command: gradle assembleDebug"
echo "Note: Install Android Studio or Gradle to build."
