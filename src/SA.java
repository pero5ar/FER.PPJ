// TODO before submission, move to analizator directory

import lab2.models.*;
import lab2.storage.ProductionRulesStorage;
import lab2.storage.SynchronizationSymbolsStorage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SA {
	public static final String EPSILON = ProductionRulesStorage.EPSILON;

    private static final String IN_PATH = "storage.bin";

    private static SynchronizationSymbolsStorage synchronizationStorage;
    private static AnalizerTables tables;

	public static void main(String[] args){
		String uniform = "";
		String lines;
		ArrayList<AnalizerInput> ulaz = new ArrayList<AnalizerInput>();
		/*
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"+"));
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"*"));
		ulaz.add(new AnalizerInput(null,0,"var"));
		ulaz.add(new AnalizerInput(null,0,"<OznakaKrajaNiza>"));
		*/

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(IN_PATH))) {
            synchronizationStorage = (SynchronizationSymbolsStorage) in.readObject();
            tables = (AnalizerTables) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Storage class not found.");
            e.printStackTrace();
            return;
        }

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while((lines = reader.readLine())!=null && !lines.equals("")) {
				AnalizerInput input = new AnalizerInput(lines.split(" ",3)[2], Integer.parseInt(lines.split(" ")[1]), lines.split(" ")[0]);
				ulaz.add(input);
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		ulaz.add(new AnalizerInput(null, 0,"OznakaKrajaNiza"));

		DoubleMap<String, String, String> actionTable = tables.getActionTable();
		DoubleMap<String, String, String> newStateTable = tables.getNewStateTable();

		/*for(String state : actionTable.getMap().keySet()){
			for(String symbol : actionTable.get(state).keySet()){
				System.out.println(state + " --- " + symbol + " --- " + actionTable.get(state,symbol));
			}
		}*/
		/*actionTable.put("0","a","P(3)");
		actionTable.put("0","b","P(4)");
		actionTable.put("0","<OznakaKrajaNiza>","R(A->epsilon)");
		actionTable.put("0","B","S(2)");
		actionTable.put("0","A","S(1)");

		actionTable.put("1","<OznakaKrajaNiza>","O");

		actionTable.put("2","a","P(3)");
		actionTable.put("2","b","P(4)");
		actionTable.put("2","<OznakaKrajaNiza>","R(A->epsilon)");
		actionTable.put("2","B","S(2)");
		actionTable.put("2","A","S(5)");

		actionTable.put("3","a","P(3)");
		actionTable.put("3","b","P(4)");
		actionTable.put("3","B","S(6)");

		actionTable.put("4","a","R(B->b)");
		actionTable.put("4","b","R(B->b)");
		actionTable.put("4","<OznakaKrajaNiza>","R(B->b)");

		actionTable.put("5","<OznakaKrajaNiza>","R(A->BA)");

		actionTable.put("4","a","R(B->aB)");
		actionTable.put("4","b","R(B->aB)");
		actionTable.put("4","<OznakaKrajaNiza>","R(B->aB)");

		actionTable.put("0","var","P(5)");
		actionTable.put("0","\\(","P(4)");
		actionTable.put("0","E","S(1)");
		actionTable.put("0","T","S(2)");
		actionTable.put("0","F","S(3)");

		actionTable.put("1","+","P(6)");
		actionTable.put("1","<OznakaKrajaNiza>","O()");

		actionTable.put("2","+","R(E->T)");
		actionTable.put("2","*","P(7)");
		actionTable.put("2","\\)","R(E->T)");
		actionTable.put("2","<OznakaKrajaNiza>","R(E->T)");

		actionTable.put("3","+","R(T->F)");
		actionTable.put("3","*","R(T->F)");
		actionTable.put("3","\\)","R(T->F)");
		actionTable.put("3","<OznakaKrajaNiza>","R(T->F)");

		actionTable.put("4","var","P(5)");
		actionTable.put("4","\\(","P(4)");
		actionTable.put("4","E","S(8)");
		actionTable.put("4","T","S(2)");
		actionTable.put("4","F","S(3)");

		actionTable.put("5","+","R(F->var)");
		actionTable.put("5","*","R(F->var)");
		actionTable.put("5","\\)","R(F->var)");
		actionTable.put("5","<OznakaKrajaNiza>","R(F->var)");

		actionTable.put("6","var","P(5)");
		actionTable.put("6","\\(","P(4)");
		actionTable.put("6","T","S(9)");
		actionTable.put("6","F","S(3)");

		actionTable.put("7","var","P(5)");
		actionTable.put("7","\\(","P(4)");
		actionTable.put("7","F","S(10)");

		actionTable.put("8","+","P(6)");
		actionTable.put("8","\\)","P(11)");

		actionTable.put("9","+","R(E->E + T)");
		actionTable.put("9","*","P(7)");
		actionTable.put("9","\\)","R(E->E + T)");
		actionTable.put("9","<OznakaKrajaNiza>","R(E->E + T)");

		actionTable.put("10","+","R(T->T * F)");
		actionTable.put("10","*","R(T->T * F)");
		actionTable.put("10","\\)","R(T->T * F)");
		actionTable.put("10","<OznakaKrajaNiza>","R(T->T * F)");

		actionTable.put("11","+","R(F->(E))");
		actionTable.put("11","*","R(F->(E))");
		actionTable.put("11","\\)","R(F->(E))");
		actionTable.put("11","<OznakaKrajaNiza>","R(F->(E))");
        */
		/*for(String nesto : actionTable.getMap().keySet()){
			for(String nestoDrugo : actionTable.get(nesto).keySet()){
				System.out.println(nesto + " --- " + nestoDrugo + " --- " + actionTable.get(nesto,nestoDrugo));
			}
		}*/

		/*for(AnalizerInput nestoDrugo : ulaz){
			System.out.println(nestoDrugo.getIdentifikator());
		}*/

		ArrayList<String> stog = new ArrayList<>();
		ArrayList<Node> stogNode = new ArrayList<>();
		ArrayList<String> niz = new ArrayList<>();
		String zavrsni = "<OznakaKrajaNiza>";
		String pocetni = "<POCETNO>";
		stog.add(zavrsni);
		String trenutnoStanje = "0";
		stog.add(0,trenutnoStanje);
		String ispis="";
		Node root = new Node(null, null);
		ArrayList<String> stablo = new ArrayList<>();
		HashMap<String, String> stablo2 = new HashMap<>();
		boolean krajNiiza=false;
		int elementUNizu = 0;
		AnalizerInput znak  = ulaz.get(elementUNizu);
		while(!krajNiiza) {
			//for(AnalizerInput znak: ulaz){


			String operacija = actionTable.get(trenutnoStanje, znak.getIdentifikator());
			//System.out.print(trenutnoStanje+"    ----   ");
			//System.out.println(znak.getIdentifikator());
			//System.out.println(trenutnoStanje +" +++ " +znak.getIdentifikator());
			String podatak = "";
			String pod = "";
			if(operacija!=null) {
				 podatak = operacija.split("\\(")[1];
				pod = podatak.substring(0, podatak.length() - 1);
			}

			if (operacija != null && operacija.startsWith("R")) {

				ArrayList<String> reduciraj = Reduciraj(new ArrayList<>(stog), pod, stogNode, root);
				stog.clear();
				stog.addAll(reduciraj);

				String stogStanje = stog.get(1);
				String temp1 = newStateTable.get(stogStanje, stog.get(0));
				//String temp2 = temp1.split("\\)")[0];
				stog.add(0, temp1);
				trenutnoStanje= temp1;

			} else if (operacija != null && operacija.startsWith("P")) {
				trenutnoStanje = pod.split("\\)")[0];
				stog.add(0, znak.getIdentifikator());
				Node node = new Node(znak.getIdentifikator(), znak);
				stogNode.add(0, node);
				stog.add(0, trenutnoStanje);
				elementUNizu++;
				znak = ulaz.get(elementUNizu);

			} else if(operacija != null && operacija.startsWith("O")){
				krajNiiza=true;
			} else {
				while(true) {

					elementUNizu++;
					if(elementUNizu==ulaz.size()){
						krajNiiza=true;
						break;
					}
					znak = ulaz.get(elementUNizu);
					if (synchronizationStorage.getStorage().contains(znak.getIdentifikator())) {

						while(true) {
							String tempi = "";
							if(stog.size()>1) {
								tempi = stog.remove(0);
							} else {
								krajNiiza=true;
								break;
							}
							if (actionTable.get(tempi, znak.getIdentifikator()) != null) {
								//System.out.println("TU SAM");
								trenutnoStanje = tempi;
								stog.add(0,tempi);
								break;
							}
							stog.remove(0);
						}
						break;
					}
				}

			}
			/*for(String tem : stog){
				System.out.print(tem+"  ");
			}
			System.out.println();*/
		}
		printTree(root,0);



	}

	private static ArrayList<String> Reduciraj(ArrayList<String> stog, String redukcija, ArrayList<Node> stogNode, Node root){
		String lijeva = redukcija.split("->")[0];   //JE LI OVAJ FORMAT?
		String desno = redukcija.split("->")[1];
		//System.out.println(desno);
		Node node = new Node(lijeva, null);
		root.setNode(node);
		if(!desno.startsWith(EPSILON)) {
			Production prod = new Production();
			ArrayList<String> desna = prod.transformFromString(redukcija);
			int duljinaRedukcije = desna.size();
			//System.out.println(redukcija);
			for (int i = 0; i < duljinaRedukcije * 2; i++) {
				stog.remove(0);
				if (i % 2 == 1) {
					Node child = stogNode.remove(0);
					node.addChild(child);

				}
			}
			//System.out.println(lijeva);
			stog.add(0, lijeva);
			stogNode.add(0, node);

			return stog;
		} else{
			stog.add(0,lijeva);
			Node epsilon = new Node(EPSILON, null);
			node.addChild(epsilon);
			stogNode.add(0, node);
			return stog;
		}
	}
	public static void printTree(Node node, int indent){
		for (int i=0; i<indent; i++) System.out.print(" ");
		System.out.println(node.getName() + (node.getValue() != null ? node.printValue() : ""));
		for (Node child : node.getChildren()) {
			printTree(child, indent+1);
		}
	}
}
