/*
 * Mehgan Cook
 * Assign 3- Huffman encoding
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		NumberFormat nf = NumberFormat.getInstance();
		Map<Character, String> myCodes = new HashMap<>();
		StringBuilder book = new StringBuilder();
		String text = "";
		PrintStream output1 = new PrintStream(new File("codes.txt"));
		PrintStream output2 = new PrintStream(new File("compressed.txt"));

		try {
		//	text = new Scanner( new File(".\\src\\WarAndPeace.txt") ).useDelimiter("\\A").next();
			text = new Scanner( new File(".\\src\\WarOfTheWorlds.txt") ).useDelimiter("\\A").next();
			book.append(text);
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		CodingTree tree = new CodingTree(book.toString());
		
		//writes to the code file.
		myCodes = tree.codes();
		for (char c : myCodes.keySet()) {
			String path = myCodes.get(c);
			output1.println(c + " = " + path);
		}
		
		// creates the compressed file.
		String message = tree.bits();
		final BitSet bitSet = new BitSet();
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '0') {
				bitSet.set(i, false);
			} else {
				bitSet.set(i, true);
			}
		}
		output2.write(bitSet.toByteArray());
		
		//statistics of the code.
		//counts characters in War and Peace
		int j = 0;
		while (j < text.length()) {
			j++;
		}
		System.out.println("size before compression = " + nf.format(j));		
		System.out.println("size after compression =  " + nf.format(bitSet.toByteArray().length));
		System.out.printf("compression ratio = %.2f percent\n", ((double)bitSet.toByteArray().length / j));
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Running time = "  + totalTime + " milliseconds");
		
		
		
	} 
}



