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
        inputStream = getClass().getResourceAsStream("/Maps/ColMap.txt");
        br = new BufferedReader(new java.io.InputStreamReader(inputStream));

        try {
            string = br.readLine();
            String[] numbers = string.split(" ");
            colCount = numbers.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(new ColCountString().colCount);
    }
}
