#!/bin/bash

# ./test-lab4/testAll.sh GeneratorKoda /media/data/FER/5SEM/PPJ/lab-primjeri/4labos-2012-2012/

# node ./node_modules/friscjs/consoleapp/frisc-console.js ~/projects/java/FER/PPJ/lab/a.frisc 2>/dev/null
# sudo rm -rf bin/* && ./lab3-data/testAll.sh SemantickiAnalizator ~/projects/java/FER/PPJ/testovi/lab3-custom/primjeri/ -d
# sudo rm -rf bin/* && ./lab3-data/testAll.sh SemantickiAnalizator ~/projects/java/FER/PPJ/testovi/lab3-custom/primjeri-backup/

total=0
fail=0

RED='\033[0;31m'
GREEN='\033[1;32m'
CYAN='\033[0;36m'
YELLOW='\033[1;33m'
NC='\033[0m'

rm -rf bin
mkdir bin
javac -cp bin -sourcepath src -d bin src/$1.java

for testFolder in $2/* ; do
	total=$((total+1))
	inFile="$testFolder/test.in"
	outFile="$testFolder/test.out"

	printf "${YELLOW}$inFile${NC}"

	$(timeout 5 java -cp bin $1 < $inFile 2>/dev/null)
	theDiff=$(node ./node_modules/friscjs/consoleapp/frisc-console.js a.frisc 2>/dev/null | diff - $outFile)

	[[ !  -z  $theDiff  ]] && printf "${RED}   INVALID${NC}\n$theDiff\n" && ((fail++))
	[[ !  -n  $theDiff  ]] && printf "${GREEN}   OK${NC}" && [ "$3" = "-d" ] && rm -rf $testFolder
	printf "\n"
done

echo ""
printf "${CYAN}Total: $total${NC}\n"
printf "${RED}Fail: $fail${NC}\n"

pass=$((total-fail))
printf "${GREEN}Pass: $pass${NC}\n"
