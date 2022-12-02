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















Make command to run the program:

make run FILE1="/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/1.txt" FILE2="/Users/ashwinraghunath/Documents/Fall_2022/COMP 6651 ADT notes /Project/plagiarism-detection/data/okay01/2.txt"


