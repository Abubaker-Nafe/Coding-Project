import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

// Class for Huffman Tree Nodes
class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}

public class main {
    static int Nhuffman = 0; // Total bits required for Huffman encoding

    public static void main(String[] args) throws IOException {
        try {
            int character; // for reading from the text file
            int[] charactersOccurances = new int[31]; // Array to hold the occurrences of the characters (26 letters +
                                                      // 5// symbols)
            double[] charactersFrequencies = new double[31]; // Array to hold the frequencies of the characters
            int charactersCount = 0; // Counter for all the characters
            double Entropy = 0;

            // Create a FileReader to read "Story.txt"
            FileReader reader = new FileReader("Story.txt");

            while ((character = reader.read()) != -1) {
                // Convert the character code to a char
                char ch = (char) character;

                // Check if the character is a letter or a target symbol
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                    charactersOccurances[Character.toLowerCase(ch) - 'a']++;
                    charactersCount++;
                } else if (ch == ' ') {
                    charactersOccurances[26]++;
                    charactersCount++;
                } else if (ch == ',') {
                    charactersOccurances[27]++;
                    charactersCount++;
                } else if (ch == '-') {
                    charactersOccurances[28]++;
                    charactersCount++;
                } else if (ch == '\'') {
                    charactersOccurances[29]++;
                    charactersCount++;
                } else if (ch == '.') {
                    charactersOccurances[30]++;
                    charactersCount++;
                }
            }

            // Print the occurrences of each letter and symbol in the text file.
            System.out.println("\n=== Character Occurrences ===");
            System.out.printf("%-10s%-10s%n", "Character", "Occurrences");
            System.out.println("----------------------------");
            for (int i = 0; i < 26; i++) {
                System.out.printf("%-10c%-10d%n", (char) (i + 'a'), charactersOccurances[i]);
            }
            System.out.printf("%-10s%-10d%n", "Space", charactersOccurances[26]);
            System.out.printf("%-10s%-10d%n", ",", charactersOccurances[27]);
            System.out.printf("%-10s%-10d%n", "-", charactersOccurances[28]);
            System.out.printf("%-10s%-10d%n", "'", charactersOccurances[29]);
            System.out.printf("%-10s%-10d%n", ".", charactersOccurances[30]);

            // Calculate the frequencies of each letter and symbol by dividing its
            // occurrence over the total characters count.
            System.out.println("\n=== Character Frequencies ===");
            System.out.printf("%-10s%-15s%n", "Character", "Frequency");
            System.out.println("-------------------------------");
            for (int i = 0; i < 31; i++) {
                charactersFrequencies[i] = (double) charactersOccurances[i] / charactersCount;
                if (i < 26) {
                    System.out.printf("%-10c%-15.6f%n", (char) (i + 'a'), charactersFrequencies[i]);
                } else if (i == 26) {
                    System.out.printf("%-10s%-15.6f%n", "Space", charactersFrequencies[i]);
                } else if (i == 27) {
                    System.out.printf("%-10s%-15.6f%n", ",", charactersFrequencies[i]);
                } else if (i == 28) {
                    System.out.printf("%-10s%-15.6f%n", "-", charactersFrequencies[i]);
                } else if (i == 29) {
                    System.out.printf("%-10s%-15.6f%n", "'", charactersFrequencies[i]);
                } else if (i == 30) {
                    System.out.printf("%-10s%-15.6f%n", ".", charactersFrequencies[i]);
                }
            }

            // Calculating the Entropy of the source
            for (int i = 0; i < 31; i++) {
                if (charactersFrequencies[i] > 0) { // Avoid log(0)
                    Entropy += -1 * charactersFrequencies[i] * Math.log10(charactersFrequencies[i]) / Math.log10(2);
                }
            }
            System.out.println("\nEntropy = " + Entropy);

            // Generating Huffman Codes
            generateHuffmanCodes(charactersOccurances);

            // Calculating NASCII (ASCII encoding size)
            int NASCII = charactersCount * 8; // Each character uses 8 bits in ASCII
            System.out.println("\nNASCII (ASCII bits required): " + NASCII);

            // Printing Nhuffman (already calculated during Huffman coding)
            System.out.println("Nhuffman (Huffman bits required): " + Nhuffman);

            // Calculating Compression Percentage
            double compressionPercentage = ((double) (NASCII - Nhuffman) / NASCII) * 100;
            System.out.println("Compression Percentage: " + compressionPercentage + "%");

            // Calculating Average Bits per Character using Huffman Code
            double averageBitsPerCharacter = (double) Nhuffman / charactersCount;
            System.out.println("Average Bits per Character (Huffman): " + averageBitsPerCharacter);

            // Comparing Average Bits per Character to Entropy
            System.out.println(
                    "Comparison: Average Bits/Character = " + averageBitsPerCharacter + ", Entropy = " + Entropy);

            // Printing Summary
            System.out.println("\n=== Summary ===");
            System.out.printf("%-25s: %d%n", "Total Characters", charactersCount);
            System.out.printf("%-25s: %.6f%n", "Entropy", Entropy);
            System.out.printf("%-25s: %d bits%n", "NASCII (ASCII bits)", NASCII);
            System.out.printf("%-25s: %d bits%n", "Nhuffman (Huffman bits)", Nhuffman);
            System.out.printf("%-25s: %.6f bits%n", "Avg Bits/Char (Huffman)", averageBitsPerCharacter);
            System.out.printf("%-25s: %.2f%%%n", "Compression Percentage", compressionPercentage);

            // Closing the reader
            reader.close();
        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.out.println("The file 'Story.txt' was not found!");
        }
    }

    // Function to generate Huffman codes
    public static void generateHuffmanCodes(int[] frequencies) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        // Create a node for each character with its frequency
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                if (i < 26) {
                    queue.add(new HuffmanNode((char) (i + 'a'), frequencies[i]));
                } else if (i == 26) {
                    queue.add(new HuffmanNode(' ', frequencies[i]));
                } else if (i == 27) {
                    queue.add(new HuffmanNode(',', frequencies[i]));
                } else if (i == 28) {
                    queue.add(new HuffmanNode('-', frequencies[i]));
                } else if (i == 29) {
                    queue.add(new HuffmanNode('\'', frequencies[i]));
                } else if (i == 30) {
                    queue.add(new HuffmanNode('.', frequencies[i]));
                }
            }
        }

        // Build the Huffman Tree
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();

            HuffmanNode newNode = new HuffmanNode('\0', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;

            queue.add(newNode);
        }

        // Generate the Huffman codes
        HuffmanNode root = queue.poll();
        System.out.println("\n=== Huffman Codes ===");
        System.out.printf("%-10s%-15s%n", "Character", "Huffman Code");
        System.out.println("--------------------------------");
        printHuffmanCodes(root, "");
    }

    // Function to print Huffman codes and calculate Nhuffman
    public static void printHuffmanCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }
        if (root.character != '\0') { // Leaf node
            System.out.printf("%-10c%-15s%n", root.character, code);
            Nhuffman += root.frequency * code.length();
        }
        printHuffmanCodes(root.left, code + "0");
        printHuffmanCodes(root.right, code + "1");
    }
}
