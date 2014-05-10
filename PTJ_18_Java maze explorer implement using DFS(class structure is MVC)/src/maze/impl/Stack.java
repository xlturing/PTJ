package maze.impl;

import java.util.LinkedList;

/**
 * @description: Packag a stack class using LinkedList
 * @param <T>
 */
public class Stack<T> {
	private LinkedList<T> stack = new LinkedList<T>();

	public void push(T v) {
		stack.addFirst(v);
	}

	public T peek() {
		return stack.getFirst();
	}

	public T pop() {
		return stack.removeFirst();
	}

	public boolean empty() {
		return stack.isEmpty();
	}

	public String toString() {
		return stack.toString();
	}

}
