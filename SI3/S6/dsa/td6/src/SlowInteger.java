package ads.poo2.lab5.sorting;

import java.util.Objects;

/**
 * A class for integer with slow comparison function
 */
public class SlowInteger implements Comparable<SlowInteger> {
	
	 // the time in milliseconds taken by a call to compareTo
	private static int slowness = 1;
	
	private int value;
	
	/**
	 * Built a slow integer equal to i
	 */
	public SlowInteger(int i) {
		value = i;
	}
	
	/**
	 * Built a slow integer equal to i
	 */	
	public SlowInteger(Integer i) {
		value = i;
	}
	
	/**
	 * Set the slowness of the compareTo method
	 * to 'slowness' milliseconds
	 */
	public static void setSlowness(int slowness) {
		SlowInteger.slowness = slowness;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SlowInteger that = (SlowInteger) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	/**
	 * Compare two slow integers
	 */
	public int compareTo(SlowInteger i) {
		try {
			Thread.sleep(slowness);
		}
		catch ( Exception e ) {}
		return value - i.value;
	}
	
	/**
	 * Return the string representation
	 * of a slow integer
	 */
	public String toString() {
		return "" + value;
	}
}
