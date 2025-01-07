# Huffman Code Implementation
This project implements the Huffman coding algorithm for efficient, lossless text compression. The program analyzes the character frequencies in a given text, constructs a Huffman tree, and generates optimal prefix codes for each character. This approach minimizes the number of bits required to encode the text while ensuring accurate decoding.

## Features
- Calculates character frequencies and probabilities from a text file.
- Constructs a Huffman tree to generate prefix-free binary codes.
- Computes the entropy and compares Huffman coding efficiency against ASCII encoding.
- Outputs detailed statistics, including compression percentage and average bits per character.

## Input
The program reads a text file named Story.txt, which must contain the text to be compressed.

## Output
The program provides the following details:
- Character occurrences and frequencies.
- Huffman codes for each character.
- Entropy and compression statistics, including:
  - Total bits required using ASCII encoding.
  - Total bits required using Huffman encoding.
  - Compression percentage.
  - Average bits per character compared to entropy.

### Example Output
Sample summary of results:

- Total Characters: 37,623
- Entropy: 4.16 bits/character 
- ASCII Encoding Size: 301,048 bits 
- Huffman Encoding Size: 158,348 bits 
- Compression Percentage: 47.4% 
- Average Bits per Character (Huffman): 4.21 bits


## Technologies Used
- **Programming Language**: Java
- **Concepts**: Huffman Coding, Data Compression, Entropy, Priority Queues

## File Overview
- **main.java**: Java implementation of the Huffman coding algorithm.
- **Story.txt**: Input text file (must be provided).
- **ProjectReport.pdf**: Detailed explanation of the methodology, results, and analysis.

## How It Works
- **Character Frequency Analysis**: Reads the input text and counts character occurrences.
- **Huffman Tree Construction**: Builds a binary tree based on character frequencies.
- **Code Generation**: Generates binary codes for each character based on the tree structure.
- **Compression Analysis**: Compares the efficiency of Huffman coding with fixed-length ASCII encoding.
