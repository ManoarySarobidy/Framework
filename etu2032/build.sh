#!/bin/bash

# Variables for directory
HOME_JAVA_LIB=$HOME/lib # Folder where the user put their lib file (jar file)
JAVA_PROJECT=$HOME/Documents/GitHub/Framework 
TOMCAT=$HOME/Desktop/tomcat/webapps/ # Tomcat home
SRC='./src/' # src folder
BIN='./bin/' # bin folder
TEST_DIR="Test Framework/" # Project Test Folder
TEST_PROJET="Test"
WEB="WEB-INF" # Web-Inf folder
LIB="lib" # lib folder



FILES=($(find $SRC -name "*.java")) # Find file in $SRC for every
for FILE in "${FILES[@]}"
do
	cp "$FILE" "$SRC" # Copiena eo ivelany ny . java rehetra
done

javac -cp $BIN"gson-2.10.1.jar" -d $BIN "$SRC"/*.java -parameters # compilena any anaty bin ny java
rm $SRC/*.java # alana ny java eo ivelany mba tsy hisy trace

cd $BIN # miditra any anaty bin!
jar -cvf framework.jar . # avadika framework.jar ny contenu 

cp 'framework.jar' $HOME_JAVA_LIB

export CLASSPATH=$CLASSPATH:$HOME_JAVA_LIB/'framework.jar' # atao anaty classpath ilay framework

# Apetraka any anaty web-inf an'ilay test_project ilay framework

cp 'framework.jar' $JAVA_PROJECT/"$TEST_DIR"/"$TEST_PROJET"/$WEB/$LIB

cd $JAVA_PROJECT/"$TEST_DIR"/
./compile.sh
cd $JAVA_PROJECT/"$TEST_DIR"/"$TEST_PROJET"

# # Rehefa tonga ato de c

# # FI=($(find $SRC -name "*.java"))
# # for FILE in "${FI[@]}"
# # do
# # 	cp "$FI" .
# # done

# pwd
# javac -d ./"$TEST_PROJET"/$WEB

jar -cvf $1.war .

cp $1.war $TOMCAT
rm *.war