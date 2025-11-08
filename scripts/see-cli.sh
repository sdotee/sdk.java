#!/usr/bin/env bash

# SEE CLI Wrapper Script
# This script makes it easier to run the SEE CLI tool

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Path to the JAR file
JAR_FILE="$SCRIPT_DIR/target/see-cli.jar"

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    echo "Please run 'mvn clean package' first to build the project."
    exit 1
fi

# Check if API key is set
if [ -z "$SEE_API_KEY" ]; then
    echo "Error: SEE_API_KEY environment variable is not set"
    echo "Please set it using: export SEE_API_KEY='your-api-key'"
    exit 1
fi

# Run the CLI with all arguments passed to this script
java -jar "$JAR_FILE" "$@"
