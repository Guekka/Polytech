package lab2;

import lab2.utils.TestClass;
import java.util.Scanner;

/**
 * A class for interactive testing of queues
 */
public class TestQueue extends TestClass {

	private QueueInterface<String> queue;
	private Scanner input;
	
	public TestQueue() {
		queue = new ListQueue<>();
		input = new Scanner(System.in);
	}
	
	public void chooseQueue() {
		String choice;
		String queueType = "List";
		do {
			System.out.print("choose your queue (enter 'list' or 'stack'): ");
			choice = input.nextLine().trim().toLowerCase();
		} while ( ! ( "list".equals(choice) || choice.equals("stack")) );
		if ( choice.equals("list") )
			queue = new ListQueue<>();
		else {
			queue = new StackQueue<>();
			queueType = "Stack";
		}
		System.out.println("Queue was reset to a " + queueType + "Queue");
	}
	
	public void isEmpty() {
		if ( queue.isEmpty() )
			System.out.println("the queue is empty");
		else
			System.out.println("the queue is not empty");
	}
	
	public void size() {
		System.out.println("the size of the queue is " + queue.size());
	}
	
	public void peek() {
		try {
			System.out.println(queue.peek());
		}
		catch ( EmptyQueueException eqe ) {
			System.out.println("oops! the queue is empty!");
		}
	}
	
	public void enqueue() {
		System.out.print("next string to enqueue ? ");
		String s = input.nextLine().trim();
		queue.enqueue(s);
	}
	
	public void dequeue() {
		try {
			System.out.println(queue.dequeue());
		}
		catch ( EmptyQueueException eqe ) {
			System.out.println("oops! the queue is empty!");
		}		
	}
	
	public void showQueue() {
		System.out.println(queue);
	}
	
    public static void main(String[] args) {
    	TestQueue test = new TestQueue();
        test.tester();
    }
}