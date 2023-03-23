#!/bin/bash

# Variables for directory
TOMCAT=$HOME/Desktop/tomcat/webapps/
PROJECT='My'
SRC='./src/' # src folder
BIN='./bin/' # bin folder
TESTPROJECT="Test Framework" # Project Test Folder
WEB="WEB-INF" # Web-Inf folder
LIB="lib" # lib folder

FILES=($(find $SRC -name "*.java")) # Find file in $SRC for every
for FILE in "${FILES[@]}"
do
	cp "$FILE" "$SRC"
done

javac -d $BIN "$SRC"/*.java
rm $SRC/*.java

# Vita ny partie java
# Ny manaraka dia ny mi-build an'ilay izy

cd $BIN
jar -cf framework.jar .

cp 'framework.jar' $JAVAPROJ/"$TESTPROJECT"/$WEB/$LIB

cd $JAVAPROJ/"$TESTPROJECT"/

jar -cf web.war .

cp web.war $TOMCAT