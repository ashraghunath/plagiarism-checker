import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class s40192120_detector {

    public static void main(String[] args) throws IOException{

// Check that the user provided the names of the two text files
//        if (args.length != 2) {
//            System.err.println("Invalid args");
//            System.exit(1);
//        }

        String file1 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/1.txt";
        String file2 = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/2.txt";

//         Read the two text files line by line
//        BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
//        BufferedReader br2 = new BufferedReader(new FileReader(args[1]));

        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        BufferedReader br2 = new BufferedReader(new FileReader(file2));

        // Calculate the plagiarism using the LCS algorithm
        double plagiarism = calculatePlagiarism(br1, br2);

        // Print the plagiarism percentage
        System.out.println("Plagiarism: " + plagiarism + "%");
    }

    private static String readFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }

        return sb.toString();
    }

    private static double calculatePlagiarism(BufferedReader br1, BufferedReader br2) throws IOException {

        String[] words1 = readWords(br1);
        String[] words2 = readWords(br2);



        for (int i = 0; i < words1.length; i++) {
            words1[i] = words1[i].toLowerCase();
        }
        for (int i = 0; i < words2.length; i++) {
            words2[i] = words2[i].toLowerCase();
        }


        int lcsLength = lcs(words1, words2);


        int minLength = Math.min(words1.length, words2.length);

        int totalWords = words1.length + words2.length;

        double plagiarismScore = (double) (lcsLength) / minLength;

        double plagiarismPercentage = plagiarismScore * 100;


        return plagiarismPercentage;
    }

    private static String[] readWords(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();

        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }

        Pattern wordPattern = Pattern.compile("\\w+");
        Matcher wordMatcher = wordPattern.matcher(sb.toString());
        List<String> words = new ArrayList<>();
        while (wordMatcher.find()) {
            words.add(wordMatcher.group());
        }

        br.close();

        return words.toArray(new String[0]);

    }

    private static int lcs(String[] words1, String[] words2) {
        // Create a table to store the lengths of the LCSes of subproblems
        int[][] lcsLengths = new int[words1.length + 1][words2.length + 1];

        // Loop through the words in the first text
        for (int i = 1; i <= words1.length; i++) {
            // Loop through the words in the second text
            for (int j = 1; j <= words2.length; j++) {
                // If the current words are the same, add one to the length of the LCS
                // of the previous subproblem
                if (words1[i - 1].equals(words2[j - 1])) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    // Otherwise, take the maximum of the lengths of the LCSes of the
                    // previous subproblems
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }

        // Return the length of the LCS of the whole problem
        return lcsLengths[words1.length][words2.length];
    }
}
