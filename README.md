# plagiarism-checker

#LCS                        #LCS using \\w+ regex       #LCS using \\w+ regex,lowercase   #LCS using minlength        #LCS using minlength,editdistance 2   #LCS using minlength,editdistance 1

okay01 : 15.78              okay01 : 22.22              okay01 : 22.22                     okay01 : 22.41             okay01 : 37.93                        okay01 : 29.3   
okay02 : 43.03              okay02 : 46.54              okay02 : 46.54                     okay02 : 49.33             okay02 : 61.33                        okay02 : 52.00            
okay03 : 16.98              okay03 : 19.49              okay03 : 20.21                     okay03 : 28.57             okay03 : 43.87                        okay03 : 29.59         
okay04 : 30.00              okay04 : 38.78              okay04 : 41.21                     okay04 : 55.73             okay04 : 67.21                        okay04 : 59.01             
okay05 : 12.33              okay05 : 13.15              okay05 : 13.69                     okay05 : 15.26             okay05 : 48.50                        okay05 : 33.83     
okay06 : 15.32              okay06 : 11.93              okay06 : 12.41                     okay06 : 14.77             okay06 : 49.71                        okay06 : 36.93

plagiarism01 : 69.02        plagiarism01 : 68.42        plagiarism01 : 68.42               plagiarism01 : 70.90       plagiarism01 : 80.00                  plagiarism01 : 72.72
plagiarism02 : 53.48        plagiarism02 : 60.67        plagiarism02 : 60.67               plagiarism02 : 62.79       plagiarism02 : 65.11                  plagiarism02 : 65.11
plagiarism03 : 40.83        plagiarism03 : 44.35        plagiarism03 : 44.35               plagiarism03 : 49.54       plagiarism03 : 61.26                  plagiarism03 : 51.35
plagiarism04 : 78.66        plagiarism04 : 82.89        plagiarism04 : 82.89               plagiarism04 : 92.64       plagiarism04 : 92.64                  plagiarism04 : 92.64
plagiarism05 : 48.52        plagiarism05 : 47.05        plagiarism05 : 47.05               plagiarism05 : 61.81       plagiarism05 : 68.18                  plagiarism05 : 62.72
plagiarism06 : 29.48        plagiarism06 : 37.26        plagiarism06 : 39.75               plagiarism06 : 56.14       plagiarism06 : 68.42                  plagiarism06 : 59.64
                                                        plagiarism07 : 38.22               plagiarism07 : 68.90       plagiarism07 : 70.58                  plagiarism07 : 69.74


1) double plagiarismPercentage = (2.0 * lcs) / (words1.length + words2.length);

2) double plagiarismPercentage = lcs / minLength;


//set flag if references exist and unset in plagiarized


check plagiarism 2, dates are different (so count is equal in both cases);

http not matching

call lcs for plagiarism 7






Make command to run the program:

make run FILE1="/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/1.txt" FILE2="/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/2.txt"

Implementation of Plagiarism detection using a combination of the 'Longest Common Subsequence' and 'Edit distance' algorithms.  

Run the following command to execute the algorithm. 

make run FILE1=<path-to-file> FILE2=<path-to-file> 

Problem statement: 
To compare two text files and print 1 if the contents of the file is plagiarised, 0 otherwise.

Procedure:

The algorithm consists of 3 main phases :

1) Reading the files.
2) Calculation of longest common subsequence.
3) Indication of plagiarism.

Reading the files:

To read the text files, BufferedReader is used in this code to efficiently read the file line by line. This is more efficient than using a regular Reader because it reads larger chunks of the file at a time, which reduces the number of disk accesses and allows for faster reading of large files. Additionally, BufferedReader provides useful methods for reading lines of text and tokenizing strings, which make it easy to extract words from the file. Also, LinkedHashSet is used since this is more efficient than using a List and checking for duplicates because a Set only allows unique elements, so it automatically eliminates duplicates as they are added. This eliminates the need for extra checks and iteration through the list, which can be time-consuming for large datasets. Additionally, a LinkedHashSet maintains the insertion order of its elements, so the final array of unique words will be in the same order as they were encountered in the file.

Calculation of longest common subsequence:
The longest common subsequence (LCS) between two arrays of strings is calculated. It does this by constructing a table of LCS lengths for all subproblems and then using this table to compute the LCS length for the whole problem. The function uses the editdistance function to calculate the distance between two words, and considers words to be a match if the distance is less than or equal to 1. The LCS length is then computed by adding 1 to the length of the LCS of the previous subproblem if the words are a match, or by taking the maximum of the lengths of the LCS of the previous subproblems if the words are not a match.


Indication of plagiarism:
The first step is to find the length of the longest common subsequence (LCS) between the two strings. This is done using a method called lcs, which takes in words1 and words2 as inputs and returns an integer representing the length of the LCS.
Next, the minimum length of the two strings is calculated using the Math.min method, which returns the smaller of the two lengths.
The plagiarism score is then calculated by dividing the LCS length by the minimum length of the two strings. This value is a decimal number between 0 and 1, where 0 indicates no plagiarism and 1 indicates complete plagiarism.
Finally, the plagiarism percentage is calculated by multiplying the plagiarism score by 100, which gives a number between 0 and 100 representing the percentage of plagiarism.
The minLength variable is used in the formula to normalize the plagiarism score. In other words, it is used to adjust the score so that it is not skewed by the difference in length between the two strings.
For example, if words1 is a very short string and words2 is a very long string, then the LCS length is likely to be much smaller compared to the minimum length of the two strings. This would result in a very low plagiarism score, even if there is a significant amount of overlap between the two strings.
By dividing the LCS length by the minimum length of the two strings, the formula ensures that the plagiarism score is calculated based on the relative overlap between the two strings, rather than their absolute length. This allows for a more accurate comparison of the amount of plagiarism between the two strings.
