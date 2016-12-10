# FER.PPJ

## Submission upute:
- Prije predaje, potrebno je premjestiti LA i SA u `/analizator`

## LAB3 setup
Datoteke `lab3-data/storage-LA.lib` i `lab3-data/storage-SA.lib` su generirane za ppjC korišten u LAB3. Kako su svi primjeri dani kroz
source (C), korisno je imati pregled generativnog stabla koji se generira za određeni C source. Postupak je slijedeći:

Pozicioniraš se u root direktorij projekta (tamo gdje su `src` i `bin` direktoriji) i
pokreneš slijedeću magiju **samo prvi put** (da se LA i SA skompajliraju):
```bash
javac -cp bin -sourcepath src -d bin src/LA.java
javac -cp bin -sourcepath src -d bin src/SA.java
```

Zatim, za dobivanje generativnog stabla ulazne datoteke `/path/to/in.c` pokreneš slijedeće:
```bash
java -cp bin LA lab3-data/storage-LA.lib < /path/to/in.c | java -cp bin SA lab3-data/storage-SA.lib
```

Bash skripta koja ovo radi za tvoju lijenu guzicu je `./lab3-data/lab3.sh /path/to/in.c`. Moraš ju pokretati iz roota projekta.
