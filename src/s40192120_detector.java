import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class s40192120_detector {

    private static final double THRESHHOLD = 70;

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("2 files needed!\nFormat is : make FILE1=<path-to-file> FILE2=<path-to-file> run");
            System.exit(1);
        }

        int plagiarismResult = 0;

        File file1 = new File(args[0]);
        boolean isFile1Code = containsCode(file1);

        File file2 = new File(args[1]);
        boolean isFile2Code = containsCode(file2);

        if (isFile1Code && isFile2Code) {
            plagiarismResult = runLCS(file1, file2);
        } else if (!isFile1Code && !isFile2Code) {
            plagiarismResult = runCosine(file1, file2);
        } else {
            plagiarismResult = 0;
        }

        System.out.println(plagiarismResult);

    }

    /**
     * Calculates the plagiarism value using cosine similarity
     * @param file1 first text file
     * @param file2 second text file
     * @return 1 if plagiarized, 0 otherwise
     * @throws IOException if no file found
     */
    private static int runCosine(File file1, File file2) throws IOException {

        Map<String, Integer> file1Words = readFileForCosine(file1);
        Map<String, Integer> references = countReferences(file1Words);

        Map<String, Integer> file2Words = readFileForCosine(file2);
        Map<String, Integer> referencesFound = countReferences(file2Words);

        Map<String, Integer> result = new HashMap<>(references);
        result.keySet().retainAll(referencesFound.keySet());
        int referencesRemoved = result.size();
        references.keySet().removeAll(referencesFound.keySet());

        double similarity = cosineSimilarity(file1Words, file2Words);

        double adjustment = adjustReferences(references.size(), referencesRemoved);
        similarity = similarity * 100;
        similarity += adjustment;

        if (Math.round(similarity) >= THRESHHOLD)
            return 1;
        else
            return 0;
    }

    /**
     * Runs the cosine similarity algorithm
     * @param file1Words first text file
     * @param file2Words second text file
     * @return value of cosine similarity
     */
    private static double cosineSimilarity(Map<String, Integer> file1Words, Map<String, Integer> file2Words) {
        // calculate the dot product
        int dotProduct = 0;
        for (String key : file1Words.keySet()) {
            if (file2Words.containsKey(key)) {
                dotProduct += file1Words.get(key) * file2Words.get(key);
            }
        }

        // calculate the magnitude of the first document
        double magnitude1 = 0;
        for (int value : file1Words.values()) {
            magnitude1 += value * value;
        }
        magnitude1 = Math.sqrt(magnitude1);

        // calculate the magnitude of the second document
        double magnitude2 = 0;
        for (int value : file2Words.values()) {
            magnitude2 += value * value;
        }
        magnitude2 = Math.sqrt(magnitude2);

        // calculate and return the cosine similarity
        return dotProduct / (magnitude1 * magnitude2);
    }

    /**
     * Counts the references mentioned in the file
     * @param map wordcount of the file
     * @return stored references
     */
    private static Map<String, Integer> countReferences(Map<String, Integer> map) {

        Pattern yearPattern = Pattern.compile("\\d{4}|http.*");
        Set<String> keys = new HashSet<>();
        keys.add("Wikipedia");
        keys.add("www");

        // Iterate over the keys in the input map
        for (String key : map.keySet()) {
            // If the key matches the year pattern, add it to the keys set
            if (yearPattern.matcher(key).matches()) {
                keys.add(key);
            }
        }

        HashMap<String, Integer> copy = new HashMap<>();
        copy.putAll(map);

        // Retain only the keys from the given set of strings
        copy.keySet().retainAll(keys);

        // Return the number of keys from the given set of strings that exist in the map
        return copy;
    }

    /**
     * Calculates the wordcount in the file
     * @param file input file
     * @return map containing wordcount
     * @throws IOException if no file found
     */
    private static Map<String, Integer> readFileForCosine(File file) throws IOException {
        Map<String, Integer> frequency = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // split line into words
                for (String word : words) {
                    String cleanWord = word.replaceAll("\\p{Punct}", ""); // remove punctuation
                    if (!cleanWord.isEmpty()) { // make sure the word is not empty after removing punctuation
                        frequency.put(cleanWord, frequency.getOrDefault(cleanWord, 0) + 1);
                    }
                }
            }
        }
        return frequency;

    }

    /**
     * Identifies if text file contains cpp or java code
     * @param file input file
     * @return true if file contains code, 0 otherwise
     * @throws IOException if no file found
     */
    private static boolean containsCode(File file) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            // Read the next lines until a line that starts with a comment is found or the end of the file is reached
            while (line != null && !(line.startsWith("//") || line.startsWith("/*") || line.startsWith("#include <") || line.startsWith("import "))) {
                line = reader.readLine();
            }

            //line containing character was found
            if (line != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Calculates the plagiarism value using LCS
     * @param file1 first input file
     * @param file2 second input file
     * @return 1 if plagiarized, 0 otherwise
     * @throws IOException
     */
    private static int runLCS(File file1, File file2) throws IOException {

        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        BufferedReader br2 = new BufferedReader(new FileReader(file2));

        String[] words1 = readWordsForLCS(br1);
        String[] words2 = readWordsForLCS(br2);

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

        if (Math.round(plagiarismPercentage) >= THRESHHOLD)
            return 1;
        else
            return 0;
    }

    /**
     * Reads words from the text file
     * @param br bufferedReader of file to be read
     * @return words in the text
     * @throws IOException if no file is found
     */
    private static String[] readWordsForLCS(BufferedReader br) throws IOException {
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

    /**
     * Runs the LCS algorithm
     * @param words1 words of file1
     * @param words2 words of file2
     * @return LCS value
     */
    private static int lcs(String[] words1, String[] words2) {
        int[][] lcsLengths = new int[words1.length + 1][words2.length + 1];

        for (int i = 1; i <= words1.length; i++) {
            for (int j = 1; j <= words2.length; j++) {
                // If current words are same add one to the length of the LCS
                if (words1[i - 1].equals(words2[j - 1])) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    // Else take the maximum of the lengths of the LCSes of the previous subproblems
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }

        return lcsLengths[words1.length][words2.length];
    }

    /**
     * Calculates adjustment value for correct references mentioned
     * @param remainingReferences number of references not given credit
     * @param referencesRemoved number of references credited
     * @return value to add for adjustment of plagiarism percentage
     */
    private static double adjustReferences(int remainingReferences, int referencesRemoved) {

        double adjustmentValue = remainingReferences * 5;
        adjustmentValue -= (referencesRemoved * 5);
        return adjustmentValue;

    }


}
