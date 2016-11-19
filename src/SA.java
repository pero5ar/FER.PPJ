// TODO before submission, move to analizator directory

import lab2.models.AnalizerInput;
import lab2.models.DoubleMap;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SA {
	public static void main(String[] args){
		String uniform = "";
		String lines;
		ArrayList<AnalizerInput> ulaz = new ArrayList<AnalizerInput>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while((lines = reader.readLine())!=null ) {
				AnalizerInput input = new AnalizerInput(lines.split(" ")[0], Integer.parseInt(lines.split(" ")[1]), lines.split(" ")[2]);
				ulaz.add(input);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		DoubleMap<String, String, String> tablica = new DoubleMap<>();
		ArrayList<String> stog = new ArrayList<>();
		ArrayList<String> niz = new ArrayList<>();
		String zavrsni = "<ZAVRSNI>";
		String pocetni = "<POCETNO>";
		stog.add(zavrsni);
		String trenutnoStanje = "0";
		stog.add(trenutnoStanje);
		String ispis="";
		for(AnalizerInput znak: ulaz){
			stog.add(znak.getIdentifikator());
			String operacija = tablica.get(trenutnoStanje, znak.getIdentifikator());
			String podatak = operacija.split("\\(")[1];
			String pod=podatak.substring(0, podatak.length()-2);
			if(operacija.startsWith("R")){
				ArrayList<String> reduciranje = Reduciraj(niz, pod);
			} else if(operacija.startsWith("P")){
				ArrayList<String> stogTempOne = Pomakni(stog, trenutnoStanje);
				stog.clear();
				stog.addAll(stogTempOne);
			} else if(operacija.startsWith("S")){
				ArrayList<String> stogTempTwo = Stavi(stog, trenutnoStanje);
				stog.clear();
				stog.addAll(stogTempTwo);
			}
			trenutnoStanje=stog.get(stog.size()-1);
		}
		if(stog.get(stog.size()-1).equals(zavrsni)){
			//SVE OK
		} else {
			//NIJE SVE OK
		}


	}

	private static ArrayList<String> Reduciraj(ArrayList<String> stog, String redukcija){
		String stogString = "";
		String lijeva = "";
		String desna = "";
		boolean pronaden;
		int duljinaRedukcije = redukcija.length();
		int polozaj = 0;
		for(int i=2;i<stog.size();i++){
			if(i%2==0){
				if(stog.get(i).equals(redukcija.substring(polozaj, polozaj +1))){
					if(polozaj==duljinaRedukcije){
						for(int k=0; k<duljinaRedukcije*2; k++){
							stog.remove(k+i);
						}
					}
				} else {
					polozaj=0;
				}
			}
		}
		stogString.replaceAll(redukcija,"");
		return new ArrayList<String>(Arrays.asList(stogString.split("")));
	}

	private static ArrayList<String> Pomakni(ArrayList<String> stog, String stanje){
		stog.add(stanje);

		return  stog;
	}

	private static ArrayList<String> Stavi(ArrayList<String> stog, String stanje){
		stog.add(stanje);
		return stog;
	}
}
