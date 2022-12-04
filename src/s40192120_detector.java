import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class s40192120_detector {

    public static void main(String[] args) throws IOException{

// Check that the user provided the names of the two text files
//        if (args.length != 2) {
//            System.err.println("Invalid args");
//            System.exit(1);
//        }


        String okayfilePrefix = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/okay0";
        String plagiarismPrefix = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/plagiarism0";
        String file1Suffix = "/1.txt";

        String file2Suffix = "/2.txt";

        for(int i=1; i<=6; i++)
        {
            String file1 = okayfilePrefix+i+file1Suffix;
            String file2 = okayfilePrefix+i+file2Suffix;


//         Read the two text files line by line
//        BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
//        BufferedReader br2 = new BufferedReader(new FileReader(args[1]));

            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));

            long startTime = System.currentTimeMillis();
            // Calculate the plagiarism using the LCS algorithm
            double plagiarism = calculatePlagiarism(br1, br2);

            // Print the plagiarism percentage
            System.out.println("okay0"+i);
            System.out.println("Plagiarism: " + plagiarism + "%");

            double endTime = System.currentTimeMillis()-startTime;

            System.out.println(endTime/1000);
        }

        System.out.println("\n\n");

        for(int i=1; i<=8; i++)
        {
            String file1 = plagiarismPrefix+i+file1Suffix;
            String file2 = plagiarismPrefix+i+file2Suffix;


//         Read the two text files line by line
//        BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
//        BufferedReader br2 = new BufferedReader(new FileReader(args[1]));

            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));

            long startTime = System.currentTimeMillis();
            // Calculate the plagiarism using the LCS algorithm
            double plagiarism = calculatePlagiarism(br1, br2);

            // Print the plagiarism percentage
            System.out.println("plagiarism0"+i);
            System.out.println("Plagiarism: " + plagiarism + "%");
            double endTime = System.currentTimeMillis()-startTime;

            System.out.println(endTime/1000);
        }

    }

    private static double calculatePlagiarism(BufferedReader br1, BufferedReader br2) throws IOException {

        String[] words1 = readWordsUsingBuffer(br1);
        String[] words2 = readWordsUsingBuffer(br2);

        for (int i = 0; i < words1.length; i++) {
            words1[i] = words1[i].toLowerCase();
        }
        for (int i = 0; i < words2.length; i++) {
            words2[i] = words2[i].toLowerCase();
        }


        int lcsLength = lcs(words1, words2);


        int minLength = Math.min(words1.length, words2.length);

        double plagiarismScore = (double) (lcsLength) / minLength;

        double plagiarismPercentage = plagiarismScore * 100;


        return plagiarismPercentage;
    }

    private static String[] readWordsUsingBuffer(BufferedReader br) throws IOException {
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
        Set<String> words = new LinkedHashSet<>();
        String line = br.readLine();
        while (line != null) {
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                Matcher wordMatcher = wordPattern.matcher(token);
                if (wordMatcher.find()) {
                    words.add(wordMatcher.group());
                }
            }
            line = br.readLine();
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

                int distance = editdistance(words1[i - 1], words2[j - 1]);

                // If the distance is less than or equal to a certain threshold, consider the words to be a match
                if (distance<=1) {
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

    public static int editdistance(String word1, String word2) {
        // Create a matrix to store the distances
        int[][] distances = new int[word1.length() + 1][word2.length() + 1];

        // Initialize the first row and column
        for (int i = 0; i <= word1.length(); i++) {
            distances[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            distances[0][j] = j;
        }

        // Iterate over the matrix and fill in the distances
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // If the characters are the same, the distance is the same as the distance for the previous characters
                    distances[i][j] = distances[i - 1][j - 1];
                } else {
                    // Otherwise, the distance is the minimum of the three possible operations (insertion, deletion, substitution) plus 1
                    distances[i][j] = Math.min(distances[i - 1][j], Math.min(distances[i][j - 1], distances[i - 1][j - 1])) + 1;
                }
            }
        }

        // The distance is the value in the bottom-right cell of the matrix
        return distances[word1.length()][word2.length()];
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
    private static double lcsLength(String s1, String s2) {
        // if either string is blank, return 0
        if (s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        int[][] lcsArr = new int[2][s2.length() + 1];    // instantiate lcsArr with 2 rows
        char[] charArr1 = s1.toCharArray();     // s1 converted to char array
        char[] charArr2 = s2.toCharArray();     // s2 converted to char array

        for (int i = 1; i <= charArr1.length; i++) {
            for (int j = 1; j <= charArr2.length; j++) {
                // if two chars are the same:
                if (charArr1[i - 1] == charArr2[j - 1]) {
                    if (i % 2 == 0) // row is even (row 1 is previous row)
                        lcsArr[0][j] = lcsArr[1][j - 1] + 1;
                    else  // row is odd (row 0 is previous row)
                        lcsArr[1][j] = lcsArr[0][j - 1] + 1;
                } else {
                    if (i % 2 == 0) // row is even (row 1 is previous row)
                        lcsArr[0][j] = Integer.max(lcsArr[1][j], lcsArr[0][j - 1]);
                    else  // row is odd (row 0 is previous row)
                        lcsArr[1][j] = Integer.max(lcsArr[0][j], lcsArr[1][j - 1]);
                }
            }
        }
        // return last position in arr
        System.out.println("S1 LENGTH: "+s1.length() + "\n" + "S2 Length:" + s2.length());
        return lcsArr[1][s2.length()] * 100.00 / Math.min(s1.length(),s2.length()) ;
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
}
