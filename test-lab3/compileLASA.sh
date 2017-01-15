#!/bin/bash

# ./compileLASA.sh /path/to/ppjC.lan /path/to/ppjC.san
# stores storages to ./test-lab3/storage-SA.lib and ./test-lab3/storage-LA.lib

javac -cp bin -sourcepath src -d bin src/GLA.java
java -cp bin GLA test-lab3/storage-LA.lib < $1

javac -cp bin -sourcepath src -d bin src/GSA.java
java -cp bin GSA test-lab3/storage-SA.lib < $2
