package pikado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 5arAcer on 22.10.2016..
 */
public class Pikado {
    public static void main(String[] args){
        try(BufferedReader reader = new BufferedReader(new FileReader("pikado.md"))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] split = line.split(" ");
                String player = split[0];
                String scores = split[1];

                System.out.println(player + " = " + parseScores(scores));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int parseScores(String scores){
        String[] splitScore = scores.split(",");
        int sum = 0;
        for(String score : splitScore){
            for(String i : score.split("\\+")){
                sum += Integer.parseInt(i);
            }
        }

        return sum;
    }
}
