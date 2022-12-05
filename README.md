Plagiarism Detection using Cosine Similarity and Longest Common Subsequence Algorithms.

The algorithm consists of 3 main phases :

1) Reading the files
2) Running the Cosine similarity or LCS algorithm
3) Calculation of plagiarism value

In the end, we also discuss how the implementation is optimized.

1. Reading the files:
   If the files are plaintext files, we count the number of occurrences of each word. This is necessary for the cosine similarity algorithm to quickly and easily compare the word frequencies in the two files.
   For code files, we store each word in the file as an element in a String array to easily compare words in the two files and find common sequences.

2. Running the Cosine similarity or LCS algorithm:
   Once the files have been read and processed, one of two algorithms is applied.
   For plaintext files, the cosine similarity algorithm is used which calculates the cosine of the angle between the two vectors formed by the wordcounts. This value is then used to determine the level of similarity between the two files.
   For code files, the LCS algorithm is used to find the longest sequence of words that appears in both files and the level of similarity between the two files is determined.

3. Calculation of plagiarism value:
   Finally, the code outputs a 1 if the plagiarism percentage is above the threshold set as 70, indicating that plagiarism has been detected. If the plagiarism percentage is below 70, the code outputs a 0, indicating that no plagiarism has been detected.
   When running the LCS algorithm, we calculate the minimum length among the two String arrays of file1 and file2. The code then calculates the plagiarism score by dividing the lcsLength by the minLength. This value is multiplied by 100 to calculate the plagiarism score.
   When running cosine similarity, the similarity value between both files is multiplied by 100 to calculate the plagiarism score.

Measures for optimization:
i. While reading, a LinkedHashset is used to eliminate any duplicates and preserve order of words. This increased efficiency because it not only stores unique words but the data structure also has efficient access time.
ii. Since the files can be as large as 500kb, it is not suitable to read the whole file as a String. Hence, BufferedReader is used because it reads the file in blocks or chunks. BufferedReader helps reading the file in a more structured manner, such as by lines or by blocks, which can make it easier to process the file.
iii. The references made from a website or by a person in a certain year are considered and if proper credit has been given to the same reference, there is a significant reduction in the plagiarism value and vice versa.
iv. While using LCS, The minLength is used instead of the total length of the words because the length of the LCS cannot be longer than the length of the shortest file. This ensures that the plagiarism score is calculated correctly. 