package bigint.module;

//This is node structure
public class Link {
	public int iData;
	public Link next;
	public Link previous;

	public Link(int id) {
		iData = id;
	}

	public String toString() {
		return Integer.toString(iData);
	}
}