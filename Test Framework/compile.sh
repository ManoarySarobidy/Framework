#!/bin/bash

FILES=($(find "./src" -name "*.java")) # Find file in $SRC for every
for FILE in "${FILES[@]}"
do
	cp "$FILE" "./src" # Copiena eo ivelany ny . java rehetra
done

javac -cp "$1/framework.jar" -d $2 "./src/"*.java


rm "./src/"*.java