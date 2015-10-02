/*
 * Mehgan Cook
 * Assign 3- Huffman encoding
 */

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class CodingTree  {

	private int[] myCharFreq; 
	private Map<Character, String> myCode;	
	private String myMessage;
	private PriorityQueue<Tree> myTrees;

	public CodingTree(String message) {
		myCharFreq = new int[256];
		myCode = new HashMap<>();
		myMessage = message;
		myTrees = new PriorityQueue<Tree>();
		getCharArray(message);
		createTrees();
		createSingleTree();
	}

	private void getCharArray(String message) {
		for (int i = 0; i < message.length(); i++) {
			myCharFreq[(int)message.charAt(i)]++;
		}
	}

	private void createTrees() {
		for (int i = 0; i < myCharFreq.length; i++) {
			if(myCharFreq[i] != 0) {
				char c = (char) i;
				Tree tree = new Tree(myCharFreq[i], c);
				myTrees.add(tree);
			}
		}
	}

	private void createSingleTree() {	
		while (myTrees.size() != 1) {
			Tree temp1 = myTrees.poll();
			Tree temp2 = myTrees.poll();
			Tree tree = new Tree(temp1, temp2);
			myTrees.add(tree);
		}		
	}

	public Map<Character, String> codes() {
		Node root = myTrees.peek().root;
		traverse(root, "");
		return myCode;
	}

	public String bits() {
		StringBuilder binary = new StringBuilder();
		for (int i = 0; i < myMessage.length(); i++) {
			binary.append(myCode.get(myMessage.charAt(i)));
		}
		return binary.toString();

	}

	private void traverse(Node n, String path) {
		if (n.left != null) traverse(n.left, path+"0");
		if (n.right != null) traverse(n.right, path+"1");
		if (n.left == null && n.right == null) { 
			n.code = path;
			myCode.put(n.element, n.code);
		}
	}
	
	private class Tree implements Comparable<Tree> {
		Node root; 

		public Tree(Tree t1, Tree t2) {
			root = new Node();
			root.left = t1.root;
			root.right = t2.root;
			root.weight = t1.root.weight + t2.root.weight;
		}

		public Tree(int weight, char element) {
			root = new Node(weight, element);
		}

		@Override
		public int compareTo(Tree t) {
			return root.weight - t.root.weight;
		}
	}

	private class Node {
		char element;
		int weight;
		Node left;
		Node right;
		String code = "";

		public Node() {			
		}

		public Node(int weight, char element) {
			this.weight = weight;
			this.element = element;
		}
	}
}
