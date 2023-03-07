package binarypk;

import java.util.Scanner;

/**
 * A class for interactive testing binary nodes
 */
public class TestBinaryTree extends TestClass {

	private BinaryTree<String> tree;
	private final Scanner input;
	
	public TestBinaryTree() {
		super();
		tree = BinaryTree.read("A$");
		input = new Scanner(System.in);
	}
	
	public void readTree() {
		System.out.print("enter the tree in linear form: ");
		String linear = input.nextLine();
		tree = BinaryTree.read(linear);
	}
	
	public void showTree() {
		System.out.println(tree);
	}
	
	public void height() {
		System.out.println(tree.height());
	}
	
	public void lowness() {
		System.out.println(tree.lowness());
	}
	
	public void size() {
		System.out.println(tree.size());
	}
	
	public void leaves() {
		System.out.println(tree.leaves());
	}		
	
	public void balanced1() {
		if ( tree.balanced1() )
			System.out.println("the tree is balanced");
		else
			System.out.println("the tree is not balanced");
	}		
	
	public void balanced2() {
		if ( tree.balanced2() )
			System.out.println("the tree is balanced");
		else
			System.out.println("the tree is not balanced");
	}		
	
	public void shapely1() {
		if ( tree.shapely1() )
			System.out.println("the tree is shapely");
		else
			System.out.println("the tree is not shapely");
	}		
	
	public void shapely2() {
		if ( tree.shapely2() )
			System.out.println("the tree is shapely");
		else
			System.out.println("the tree is not shapely");
	}		
	
    public static void main(String[] args) {
    	TestBinaryTree test = new TestBinaryTree();
        test.tester();
    }
}
