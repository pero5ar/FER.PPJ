// TODO before submission, move to analizator directory

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
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while((lines = reader.readLine())!=null ) {
				// ...
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		ArrayList<String> stog = new ArrayList<>();
		ArrayList<String> niz = new ArrayList<>();

	}

	private ArrayList<String> Reduciraj(ArrayList<String> niz, String redukcija){
		String nizString = niz.toString();
		nizString.replaceAll(redukcija,"");
		return new ArrayList<String>(Arrays.asList(nizString.split("")));
	}

	private void Pomakni(ArrayList<String> stog, String stanje){
		stog.add(stanje);
	}

	private void Stavi(ArrayList<String> stog, String stanje){
		stog.add(stanje);
	}
}
