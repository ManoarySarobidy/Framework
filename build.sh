#!/bin/bash

# Compilena daholo aloha ilay etu2032
# SCRIPT 1

TEST="./Test Framework/"
FRAMEWORK="./etu2032"
TOMCAT=$HOME/Desktop/tomcat/webapps

cd './etu2032/'
./compile.sh framework
cd '..'
# mkdir "$TEST"/lib
LIB="$TEST"/lib
cp "$FRAMEWORK/framework.jar" "$LIB"
cp "$FRAMEWORK/gson-2.10.1.jar" "$LIB"

# Script 2
cd "$TEST"
./predeploy.sh testFramework

cp "$2.war" ../

