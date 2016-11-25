// TODO before submission, move to analizator directory

import lab2.models.AnalizerInput;
import lab2.models.DoubleMap;
import lab2.models.Production;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SA {
	public static void main(String[] args){
		String uniform = "";
		String lines;
		ArrayList<AnalizerInput> ulaz = new ArrayList<AnalizerInput>();
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"+"));
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"*"));
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"<OznakaKrajaNiza>"));

		/*try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while((lines = reader.readLine())!=null ) {
				AnalizerInput input = new AnalizerInput(lines.split(" ")[0], Integer.parseInt(lines.split(" ")[1]), lines.split(" ")[2]);
				ulaz.add(input);
			}
		}catch(IOException e){
			e.printStackTrace();
		}*/



		DoubleMap<String, String, String> tablica = new DoubleMap<>();
		/*tablica.put("0","a","P(3)");
		tablica.put("0","b","P(4)");
		tablica.put("0","<OznakaKrajaNiza>","R(A->epsilon)");
		tablica.put("0","B","S(2)");
		tablica.put("0","A","S(1)");

		tablica.put("1","<OznakaKrajaNiza>","O");

		tablica.put("2","a","P(3)");
		tablica.put("2","b","P(4)");
		tablica.put("2","<OznakaKrajaNiza>","R(A->epsilon)");
		tablica.put("2","B","S(2)");
		tablica.put("2","A","S(5)");

		tablica.put("3","a","P(3)");
		tablica.put("3","b","P(4)");
		tablica.put("3","B","S(6)");

		tablica.put("4","a","R(B->b)");
		tablica.put("4","b","R(B->b)");
		tablica.put("4","<OznakaKrajaNiza>","R(B->b)");

		tablica.put("5","<OznakaKrajaNiza>","R(A->BA)");

		tablica.put("4","a","R(B->aB)");
		tablica.put("4","b","R(B->aB)");
		tablica.put("4","<OznakaKrajaNiza>","R(B->aB)");*/

		tablica.put("0","var","P(5)");
		tablica.put("0","\\(","P(4)");
		tablica.put("0","E","S(1)");
		tablica.put("0","T","S(2)");
		tablica.put("0","F","S(3)");

		tablica.put("1","+","P(6)");
		tablica.put("1","<OznakaKrajaNiza>","O()");

		tablica.put("2","+","R(E->T)");
		tablica.put("2","*","P(7)");
		tablica.put("2","\\)","R(E->T)");
		tablica.put("2","<OznakaKrajaNiza>","R(E->T)");

		tablica.put("3","+","R(T->F)");
		tablica.put("3","*","R(T->F)");
		tablica.put("3","\\)","R(T->F)");
		tablica.put("3","<OznakaKrajaNiza>","R(T->F)");

		tablica.put("4","var","P(5)");
		tablica.put("4","\\(","P(4)");
		tablica.put("4","E","S(8)");
		tablica.put("4","T","S(2)");
		tablica.put("4","F","S(3)");

		tablica.put("5","+","R(F->var)");
		tablica.put("5","*","R(F->var)");
		tablica.put("5","\\)","R(F->var)");
		tablica.put("5","<OznakaKrajaNiza>","R(F->var)");

		tablica.put("6","var","P(5)");
		tablica.put("6","\\(","P(4)");
		tablica.put("6","T","S(9)");
		tablica.put("6","F","S(3)");

		tablica.put("7","var","P(5)");
		tablica.put("7","\\(","P(4)");
		tablica.put("7","F","S(10)");

		tablica.put("8","+","P(6)");
		tablica.put("8","\\)","P(11)");

		tablica.put("9","+","R(E->E + T)");
		tablica.put("9","*","P(7)");
		tablica.put("9","\\)","R(E->E + T)");
		tablica.put("9","<OznakaKrajaNiza>","R(E->E + T)");

		tablica.put("10","+","R(T->T * F)");
		tablica.put("10","*","R(T->T * F)");
		tablica.put("10","\\)","R(T->T * F)");
		tablica.put("10","<OznakaKrajaNiza>","R(T->T * F)");

		tablica.put("11","+","R(F->(E))");
		tablica.put("11","*","R(F->(E))");
		tablica.put("11","\\)","R(F->(E))");
		tablica.put("11","<OznakaKrajaNiza>","R(F->(E))");


		ArrayList<String> stog = new ArrayList<>();
		ArrayList<String> niz = new ArrayList<>();
		String zavrsni = "<OznakaKrajaNiza>";
		String pocetni = "<POCETNO>";
		stog.add(zavrsni);
		String trenutnoStanje = "0";
		stog.add(0,trenutnoStanje);
		String ispis="";

		ArrayList<String> stablo = new ArrayList<>();
		HashMap<String, String> stablo2 = new HashMap<>();
		boolean krajNiiza=false;
		int elementUNizu = 0;
		AnalizerInput znak  = ulaz.get(elementUNizu);
		while(!krajNiiza) {
			//for(AnalizerInput znak: ulaz){


			String operacija = tablica.get(trenutnoStanje, znak.getIdentifikator());
			String podatak = operacija.split("\\(")[1];
			String pod = podatak.substring(0, podatak.length() - 1);
			/*for(String s:stog) {
				System.out.print(s + ",  ");
			}
			System.out.println();*/
			if (operacija.startsWith("R")) {

				ArrayList<String> reduciraj = Reduciraj(new ArrayList<>(stog), pod, stablo, stablo2);
				stog.clear();
				stog.addAll(reduciraj);

				String stogStanje = stog.get(1);
				String temp1 = tablica.get(stogStanje, stog.get(0)).split("\\(")[1];
				String temp2 = temp1.split("\\)")[0];
				stog.add(0, temp2);
				trenutnoStanje= temp2;

			} else if (operacija.startsWith("P")) {
				trenutnoStanje = pod.split("\\)")[0];
				stog.add(0, znak.getIdentifikator());
				stog.add(0, trenutnoStanje);
				elementUNizu++;
				znak = ulaz.get(elementUNizu);

			} else if(operacija.startsWith("O")){
				System.out.println("SVE OK");
				krajNiiza=true;
			}
		}
		for(String st:stablo){
			System.out.println(st);
		}

		/*for(String st: stablo2.keySet()){
			System.out.println(stablo2.get(st));
		}*/



	}

	private static ArrayList<String> Reduciraj(ArrayList<String> stog, String redukcija, ArrayList<String> stablo, HashMap<String, String> stablo2){
		String lijeva = redukcija.split("-")[0];   //JE LI OVAJ FORMAT?
		String desno = redukcija.split(">")[1];
		if(!desno.equals("$")) {
			Production prod = new Production();
			ArrayList<String> desna = prod.transformFromString(redukcija);
			int duljinaRedukcije = desna.size();
			//System.out.println(redukcija);
			for (int i = 0; i < duljinaRedukcije * 2; i++) {
				stog.remove(0);
			}
			//System.out.println(desna);

			//System.out.println(lijeva);
			stablo2.put(desno,lijeva);
			stablo.add(lijeva);
			stog.add(0, lijeva);

			return stog;
		} else{
			stog.add(0,lijeva);
			return stog;
		}
	}
}
