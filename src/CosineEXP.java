import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class CosineEXP {


    public static double cosineSimilarity(Map<String, Integer> doc1, Map<String, Integer> doc2) {
        // calculate the dot product
        int dotProduct = 0;
        for (String key : doc1.keySet()) {
            if (doc2.containsKey(key)) {
                dotProduct += doc1.get(key) * doc2.get(key);
            }
        }

        // calculate the magnitude of the first document
        double magnitude1 = 0;
        for (int value : doc1.values()) {
            magnitude1 += value * value;
        }
        magnitude1 = Math.sqrt(magnitude1);

        // calculate the magnitude of the second document
        double magnitude2 = 0;
        for (int value : doc2.values()) {
            magnitude2 += value * value;
        }
        magnitude2 = Math.sqrt(magnitude2);

        // calculate and return the cosine similarity
        return dotProduct / (magnitude1 * magnitude2);
    }

    public static Map<String,Integer> countReferences(Map<String, Integer> map) {

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

        // Copy all of the mappings from the specified map to the new map
        copy.putAll(map);

        // Retain only the keys from the given set of strings
        copy.keySet().retainAll(keys);

        // Return the number of keys from the given set of strings that exist in the map
        return copy;
    }


    public static Map<String, Integer> readFile(File file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            Map<String, Integer> frequency = new HashMap<>();
            while (scanner.hasNext()) {
                // get the next word and remove any punctuation
                String word = scanner.next().replaceAll("\\p{Punct}", "");
                if (!word.isEmpty()) { // make sure the word is not empty after removing punctuation
                    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
                }
            }
            return frequency;
        }

    }

    public static void main(String[] args) throws IOException {

        String okayfilePrefix = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/okay0";
        String plagiarismPrefix = "/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-checker/data/plagiarism0";
        String file1Suffix = "/1.txt";
        String file2Suffix = "/2.txt";

        for(int i=1; i<=7; i++) {

            String file1Path = okayfilePrefix + i + file1Suffix;
            String file2Path = okayfilePrefix + i + file2Suffix;
            // read the first file
            long startTime = System.currentTimeMillis();
            File file1 = new File(file1Path);
            Map<String, Integer> doc1 = readFile(file1);
            Map<String, Integer> references = countReferences(doc1);

            // read the second file
            File file2 = new File(file2Path);
            Map<String, Integer> doc2 = readFile(file2);
            Map<String, Integer> referencesFound = countReferences(doc2);
            int originalReferencesSize = referencesFound.size();


            Map result = new HashMap(references);
            result.keySet().retainAll(referencesFound.keySet());
            int referencesRemoved = result.size();
            references.keySet().removeAll(referencesFound.keySet());
            // calculate and print the cosine similarity
            double similarity = cosineSimilarity(doc1, doc2);

            double adjustment = adjustReferences(references.size(),referencesRemoved);
            similarity=similarity*100;
            similarity+=adjustment;

            System.out.println("okay0"+i+" similarity : " + similarity);

            double endTime = System.currentTimeMillis()-startTime;
            System.out.println("Time : "+endTime/1000);
        }


        for(int i=1; i<=9; i++) {

            String file1Path = plagiarismPrefix + i + file1Suffix;
            String file2Path = plagiarismPrefix + i + file2Suffix;
            // read the first file
            long startTime = System.currentTimeMillis();
            File file1 = new File(file1Path);
            Map<String, Integer> doc1 = readFile(file1);
            Map<String, Integer> references = countReferences(doc1);

            // read the second file
            File file2 = new File(file2Path);
            Map<String, Integer> doc2 = readFile(file2);
            Map<String, Integer> referencesFound = countReferences(doc2);
            int originalReferencesSize = referencesFound.size();

            Map result = new HashMap(references);
            result.keySet().retainAll(referencesFound.keySet());
            int referencesRemoved = result.size();
            references.keySet().removeAll(referencesFound.keySet());
            // calculate and print the cosine similarity
            double similarity = cosineSimilarity(doc1, doc2);

            double adjustment = adjustReferences(references.size(),referencesRemoved);
            similarity=similarity*100;
            similarity+=adjustment;

            System.out.println("plagiarism0"+i+" similarity : " + similarity);

            double endTime = System.currentTimeMillis()-startTime;
            System.out.println("Time : "+endTime/1000);
        }
    }

    private static double adjustReferences(int remainingReferences,int referencesRemoved) {

        double adjustmentValue = remainingReferences * 7;
        adjustmentValue -= (referencesRemoved*7);
        return adjustmentValue;

    }


}
