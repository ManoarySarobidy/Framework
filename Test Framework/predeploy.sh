#!/bin/bash

# Mamorona dossier temp aloha
TEMP="./temp"
WEBINF=$TEMP/"WEB-INF"
CLASSES=$WEBINF/"classes"
LIBS=$WEBINF/"lib"
function makeFolders(){
	mkdir $TEMP
	mkdir $WEBINF
	mkdir $CLASSES
	mkdir $LIBS
}

makeFolders


cp -R "./lib/"* $LIBS
./compile.sh $LIBS $CLASSES
cp  "./"*.jsp $TEMP
cp  "./"*.xml $WEBINF
cd $TEMP
jar -cf $1.war .
cd ..
cp $TEMP/$1.war .
# pwd
rm -R $TEMP
# Copiena any anaty TOMCAT AMIN'IZAY