A Java program that computes Pointwise Mutual Information (PMI) for word pairs across a directory of text files, then lets you look up the top 5 most associated words for any given word.

How It Works:
1) The program reads all files in a given directory and builds frequency counts for every word bigram.
2) When you enter a word, it searches all bigrams where that word appears first, computes the PMI score for each pair, and returns the top 5 highest-scoring pairs
