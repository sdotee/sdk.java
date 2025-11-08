#!/usr/bin/env fish

# SEE CLI Wrapper Script for Fish Shell
# This script makes it easier to run the SEE CLI tool

# Get the directory where this script is located
set SCRIPT_DIR (dirname (status --current-filename))

# Path to the JAR file
set JAR_FILE "$SCRIPT_DIR/target/see-cli.jar"

# Check if JAR file exists
if not test -f "$JAR_FILE"
    echo "Error: JAR file not found at $JAR_FILE"
    echo "Please run 'mvn clean package' first to build the project."
    exit 1
end

# Check if API key is set
if not set -q SEE_API_KEY
    echo "Error: SEE_API_KEY environment variable is not set"
    echo "Please set it using: set -x SEE_API_KEY 'your-api-key'"
    exit 1
end

# Run the CLI with all arguments passed to this script
java -jar "$JAR_FILE" $argv
