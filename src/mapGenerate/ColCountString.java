package mapGenerate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class ColCountString {
    private String string;
    private InputStream inputStream;
    private BufferedReader br;
    private int colCount;

    public ColCountString() {
        inputStream = getClass().getResourceAsStream("/Maps/map_1_Tile_Layer_Main.csv");
        br = new BufferedReader(new java.io.InputStreamReader(inputStream));

        try {
            string = br.readLine();
            String[] numbers = string.split(",");
            colCount = numbers.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Row count: " + rowCount());
    }

    private int rowCount() {
        int rowCount = 1;
        try {
            while (string != null) {
                string = br.readLine();
                if (string != null) {
                    rowCount++;
                }
            }
            return rowCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(new ColCountString().colCount);
    }
}
