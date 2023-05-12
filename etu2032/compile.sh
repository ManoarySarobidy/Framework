#!/bin/bash


FILES=($(find './src' -name "*.java")) # Find file in $SRC for every
for FILE in "${FILES[@]}"
do
	cp "$FILE" "./src" # Copiena eo ivelany ny . java rehetra
done

# Compile all java script

javac -d "./bin" "./src"/*.java # compilena any anaty bin ny java
rm "./src"/*.java # alana ny java eo ivelany mba tsy hisy trace

# Change it to war
cd './bin'

jar -cf '../'$1.jar  '.'

cd '..'
