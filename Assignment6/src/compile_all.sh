#!/bin/bash

# Set the root directory to the folder containing the Java files
ROOT_DIR="./"

# Find all Java files in the root directory and its subdirectories
FILES=$(find "$ROOT_DIR" -name "*.java")

# Compile each Java file
for FILE in $FILES
do
  javac "$FILE"
done