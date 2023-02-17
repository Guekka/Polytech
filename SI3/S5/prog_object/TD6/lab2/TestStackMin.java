package lab2;

import lab2.utils.TestClass;

import java.util.Scanner;

/**
 * A class for interactive testing stacks with findMin operation
 */
public class TestStackMin extends TestClass {
	
	private StackMin<Integer> stack;
	private Scanner input;
	
	public TestStackMin() {
		stack = new StackMin<Integer>();
		input = new Scanner(System.in);
	}
	
	public void isEmpty() {
		if ( stack.isEmpty() )
			System.out.println("the stack is empty");
		else
			System.out.println("the stack is not empty");
	}
	
	public void push() {
		System.out.print("next string to push ? ");
		Integer s = input.nextInt();
		stack.push(s);
	}
	
	public void pop() {
		try {
			System.out.println(stack.pop());
		}
		catch ( EmptyStackException ese ) {
			System.out.println("oops! stack is empty!");
		}
	}
	
	public void peek() {
		try {
			System.out.println(stack.peek());
		}
		catch ( EmptyStackException ese ) {
			System.out.println("oops! stack is empty!");
		}
	}
	
	public void findMin() {
		try {
			System.out.println(stack.findMin());
		}
		catch ( EmptyStackException ese ) {
			System.out.println("oops! stack is empty!");
		}		
	}
	
	public void showStack() {
		System.out.println(stack);
	}
	
    public static void main(String[] args) {
    	TestStackMin test = new TestStackMin();
        test.tester();
    }
}
