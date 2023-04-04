#!/bin/bash

FILES=($(find "./src" -name "*.java")) # Find file in $SRC for every
for FILE in "${FILES[@]}"
do
	cp "$FILE" "./src" # Copiena eo ivelany ny . java rehetra
done

javac -cp "./Test/WEB-INF/lib/framework.jar" -d ./Test/WEB-INF/classes "./src/"*.java

rm "./src/"*.java