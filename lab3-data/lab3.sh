#!/bin/bash

# ./lab3.sh /path/to/program.c

javac -cp bin -sourcepath src -d bin src/LA.java
javac -cp bin -sourcepath src -d bin src/SA.java

java -cp bin LA lab3-data/storage-LA.lib < $1 | java -cp bin SA lab3-data/storage-SA.lib
