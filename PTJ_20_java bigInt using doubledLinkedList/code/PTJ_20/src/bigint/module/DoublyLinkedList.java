package bigint.module;

import java.util.Scanner;

/**
 * @description: The double linked list structure
 * @date: date:2014年6月4日 time:下午9:48:40
 */
/**
 * @description:
 * @author: XLT
 * @date: date:2014年6月4日 time:下午10:20:08
 */
public class DoublyLinkedList {

	private Link first;
	private Link last;
	private int length = 0;

	public Link getFirst() {
		return first;
	}

	public Link getLast() {
		return last;
	}

	public int size() {
		return length;
	}

	public DoublyLinkedList() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public void insertFirst(int dd) {
		Link newLink = new Link(dd);
		if (isEmpty()) {
			last = newLink;
		}
		else {
			first.previous = newLink;
		}
		newLink.next = first;
		first = newLink;
		length++;
	}

	public void insertLast(int dd) {
		Link newLink = new Link(dd);
		if (isEmpty()) {
			first = newLink;
		}
		else {
			last.next = newLink;
			newLink.previous = last;
		}
		last = newLink;
		length++;
	}

	public Link deleteFirst() {
		Link temp = first;
		if (first.next == null) {
			last = null;
		}
		else {
			first.next.previous = null;
		}
		first = first.next;
		length--;
		return temp;
	}

	public Link deleteLast() {
		Link temp = last;
		if (first.next == null) {
			first = null;
		}
		else {
			last.previous.next = null;
		}
		last = last.previous;
		length--;
		return temp;
	}

	public boolean insertAfter(int key, int dd) {
		Link current = first;
		while (current.iData != key) {
			current = current.next;
			if (current == null) {
				return false;
			}
		}
		Link newLink = new Link(dd);

		if (current == last) {
			newLink.next = null;
			last = newLink;
		}
		else {
			newLink.next = current.next;

			current.next.previous = newLink;
		}
		newLink.previous = current;
		current.next = newLink;
		length++;
		return true;
	}

	public Link deleteKey(int key) {
		Link current = first;
		while (current.iData != key) {
			current = current.next;
			if (current == null)
				return null;
		}
		if (current == first) {
			first = current.next;
		}
		else {
			current.previous.next = current.next;
		}

		if (current == last) {
			last = current.previous;
		}
		else {
			current.next.previous = current.previous;
		}
		length--;
		return current;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Link current = last;
		while (current != null) {
			sb.append(current.toString());
			current = current.previous;
		}
		return sb.toString();
	}

	// Test main
	public static void main(String[] args) {
		DoublyLinkedList theList = new DoublyLinkedList();

		theList.insertFirst(22);
		theList.insertFirst(44);
		theList.insertFirst(66);

		theList.insertLast(11);
		theList.insertLast(33);
		theList.insertLast(55);

		System.out.println(theList);

		theList.deleteFirst();
		theList.deleteLast();
		theList.deleteKey(11);

		System.out.println(theList);

		theList.insertAfter(22, 77);
		theList.insertAfter(33, 88);

		System.out.println(theList);

		Scanner in = new Scanner(System.in);
		System.out.println(in.next().charAt(0));
	}
}
