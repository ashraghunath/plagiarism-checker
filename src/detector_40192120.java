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

//        /Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/plagiarism01/1.txt
//        /Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/okay01/1.txt

        String file1 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/plagiarism07/1.txt";
        String file2 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/plagiarism07/2.txt";

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
        System.out.printf ("Plagiarism percentage using edit distance : %.2f\n", plagiarism);

        int m = file1Words.length();
        int n = file2Words.length();

        int lcs = lcs(file1Words, file2Words);
        double plagiarism2 = (200 * lcs(file1Words, file2Words)) / (double)(m + n);
        System.out.printf ("Plagiarism percentage using LCS : %.2f\n", plagiarism2);

        if(plagiarism>threshhold)
            return true;
        else
            return false;
    }


    private static int editDistDP(String str1, String str2)
    {
        int len1 = str1.length();
        int len2 = str2.length();

        int [][]DP = new int[2][len1 + 1];

        for (int i = 0; i <= len1; i++)
            DP[0][i] = i;

        for (int i = 1; i <= len2; i++)
        {

            for (int j = 0; j <= len1; j++)
            {

                if (j == 0)
                    DP[i % 2][j] = i;

                else if (str1.charAt(j - 1) == str2.charAt(i - 1)) {
                    DP[i % 2][j] = DP[(i - 1) % 2][j - 1];
                }
                else {
                    DP[i % 2][j] = 1 + Math.min(DP[(i - 1) % 2][j],
                            Math.min(DP[i % 2][j - 1],
                                    DP[(i - 1) % 2][j - 1]));
                }
            }
        }

        return DP[len2 % 2][len1];
    }

    public static int lcs( String prog1, String prog2)
    {
        int m = prog1.length();
        int n = prog2.length();
        int previous;
        int current = 0;

        int L[][] = new int[2][n+1];

        for (int i=0; i<=m; i++)
        {
            for (int j=0; j<=n; j++)
            {
                if(i % 2 == 0){
                    previous = 1;
                    current = 0;
                } else {
                    previous = 0;
                    current = 1;
                }
                if (i == 0 || j == 0)
                    L[current][j] = 0;
                else if (prog1.charAt(i-1) == prog2.charAt(j-1))
                    L[current][j] = L[previous][j-1] + 1;
                else
                    L[current][j] = Math.max(L[previous][j], L[current][j-1]);
            }
        }
        return L[current][n];
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
