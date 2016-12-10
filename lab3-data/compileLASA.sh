#!/bin/bash

# ./compileLASA.sh /path/to/ppjC.lan /path/to/ppjC.san
# stores storages to ./lab3-data/storage-SA.lib and ./lab3-data/storage-LA.lib

javac -cp bin -sourcepath src -d bin src/GLA.java
java -cp bin GLA lab3-data/storage-LA.lib < $1

javac -cp bin -sourcepath src -d bin src/GSA.java
java -cp bin GSA lab3-data/storage-SA.lib < $2
