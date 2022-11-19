import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class detector_40192120 {

    static int threshhold = 60;

    public static void main(String[] args) {

        String file1Words = null;
        String file2Words = null;

        String file1 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/okay01/1.txt";
        String file2 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/okay01/2.txt";

        file1Words = readFileAsString(file1);
        file2Words = readFileAsString(file2);

        if(detectAsString(file1Words, file2Words)) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }

    }

    private static String readFileAsString(String fileName)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static boolean detectAsString(String file1Words, String file2Words)
    {
        int editDistance = editDistDP(file1Words, file2Words);
        double plagiarism = (1.0 - (double) editDistance / (Math.max(file1Words.length(), file2Words.length()))) * 100;
        System.out.printf ("Plagiarism percentage : %.2f\n", plagiarism);

        if(plagiarism>threshhold)
            return true;
        else
            return false;
    }


    private static int editDistDP(String str1, String str2)
    {
        int len1 = str1.length();
        int len2 = str2.length();

        // Create a DP array to memoize result
        // of previous computations
        int [][]DP = new int[2][len1 + 1];


        // Base condition when second String
        // is empty then we remove all characters
        for (int i = 0; i <= len1; i++)
            DP[0][i] = i;

        // Start filling the DP
        // This loop run for every
        // character in second String
        for (int i = 1; i <= len2; i++)
        {

            // This loop compares the char from
            // second String with first String
            // characters
            for (int j = 0; j <= len1; j++)
            {

                // if first String is empty then
                // we have to perform add character
                // operation to get second String
                if (j == 0)
                    DP[i % 2][j] = i;

                    // if character from both String
                    // is same then we do not perform any
                    // operation . here i % 2 is for bound
                    // the row number.
                else if (str1.charAt(j - 1) == str2.charAt(i - 1)) {
                    DP[i % 2][j] = DP[(i - 1) % 2][j - 1];
                }

                // if character from both String is
                // not same then we take the minimum
                // from three specified operation
                else {
                    DP[i % 2][j] = 1 + Math.min(DP[(i - 1) % 2][j],
                            Math.min(DP[i % 2][j - 1],
                                    DP[(i - 1) % 2][j - 1]));
                }
            }
        }

        // after complete fill the DP array
        // if the len2 is even then we end
        // up in the 0th row else we end up
        // in the 1th row so we take len2 % 2
        // to get row
        return DP[len2 % 2][len1];
    }


    private static List<String> readFile(String fileName) throws FileNotFoundException {

        List<String> words = new ArrayList<>();

        words = Arrays.asList(
                new Scanner(new File(fileName)).useDelimiter("\\Z").next().split("[\\r\\n ]+")
        );

        return words;
    }

    private static boolean detect(List<String> file1Words, List<String> file2Words)
    {
        return false;
    }

}
