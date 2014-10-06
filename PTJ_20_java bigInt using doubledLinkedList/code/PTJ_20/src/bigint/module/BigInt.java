package bigint.module;

import java.util.ArrayList;
import java.util.List;

/**
 * @description The main class about bigint use DuoblyLinkedList
 * @date: date:2014年6月4日 time:下午10:05:27
 */
public class BigInt implements Comparable<BigInt> {
	// positive or negative
	public boolean isPositive;
	// number length
	public int length = 0;
	// double linked list to store big int
	public DoublyLinkedList bigInt = new DoublyLinkedList();

	/**
	 * @description default construct method
	 */
	public BigInt() {
		this.isPositive = true;
		this.length = 0;
	}

	/**
	 * @description Construct a BigInt object and initialize it with the integer
	 *              represented by the String. Throw an appropriate exception
	 * @param val
	 */
	public BigInt(String val) {
		if (val == null || val.trim() == "") {
			throw new NullPointerException(
					"Sorry! you must initialize the bigint!");
		}
		int i = 0;
		char c = val.charAt(0);
		if (c == '-') {
			isPositive = false; // negative number
			i = 1;
		}
		else
			isPositive = true; // positive number
		for (; i < val.length(); i++) {
			c = val.charAt(i);
			if (c < '0' || c > '9')
				throw new NumberFormatException("Error! wrong format number");

			bigInt.insertFirst(c - '0');
		}
		this.length = bigInt.size();
	}

	/**
	 * @description This is the copy constructor. It should make a deep copy of
	 *              val. Making a deep copy is not strictly necessary since as
	 *              designed a BigInt is immutable, but it is good practice.
	 * @param val
	 */
	public BigInt(BigInt val) {
		this.isPositive = val.isPositive;
		Link current = (val.bigInt.getFirst());
		while (current != null) {
			this.bigInt.insertLast(current.iData);
			current = current.next;
		}
		this.length = bigInt.size();
	}

	/**
	 * @description Construct a BigInt object and intitialize it wth the value
	 *              stored in val
	 * @param val
	 */
	public BigInt(long val) {
		if (val < 0) {
			this.isPositive = false;
			val = -val;
		}
		else
			this.isPositive = true;
		// use do while because when val==0, it will at least excure once
		int dd;
		do {
			dd = (int) (val % 10);
			bigInt.insertLast(dd);
			val /= 10;
		} while (val != 0);
		this.length = bigInt.size();
	}

	/**
	 * @description use int array init BigInt
	 * @param val
	 * @param isPositive
	 */
	public BigInt(int[] val, boolean isPositive, int len) {
		for (int i = 0; i < len; i++) {
			bigInt.insertLast(val[i]);
		}
		this.isPositive = isPositive;
		this.length = bigInt.size();
	}

	/**
	 * @description Returns a BigInt whose value is (this + val)
	 * @param val
	 * @return BigInt
	 */
	public BigInt add(BigInt val) {
		if (this.isPositive == val.isPositive) {
			int len = this.length >= val.length ? this.length : val.length;
			len += 1; // 可能会进位
			int[] r = new int[len];
			int i = 0;
			Link curThis = (this.bigInt.getFirst());
			Link curVal = (val.bigInt.getFirst());
			while (curThis != null && curVal != null) {
				int a = curThis.iData, b = curVal.iData;
				r[i] += a + b;
				r[i + 1] += r[i] / 10;
				r[i] = r[i] % 10;
				curThis = curThis.next;
				curVal = curVal.next;
				++i;
			}
			while (curThis != null) {
				r[i] += curThis.iData;
				r[i + 1] += r[i] / 10;
				r[i] = r[i] % 10;
				curThis = curThis.next;
				++i;
			}
			while (curVal != null) {
				r[i] += curVal.iData;
				r[i + 1] += r[i] / 10;
				r[i] = r[i] % 10;
				curVal = curVal.next;
				++i;
			}
			i = len - 1;
			for (; i >= 0; i--) {
				if (r[i] == 0)
					len--;
				else
					break;
			}
			return new BigInt(r, this.isPositive, len);
		}
		// one positive number add a negative number
		else {
			// decide which number is bigger
			int flag;
			if (this.isPositive) {
				val.isPositive = true;
				flag = compareTo(val);
				val.isPositive = false;
			}
			else {
				this.isPositive = true;
				flag = compareTo(val);
				this.isPositive = false;
			}
			BigInt big, small;
			int len;
			if (flag == 0)
				return new BigInt(0); // opposite numbers
			else if (flag > 0) {
				big = this;
				small = val;
			}
			else {
				big = val;
				small = this;
			}
			len = big.length;
			int[] r = new int[len];
			int i = 0;
			Link curB = big.bigInt.getFirst();
			Link curS = small.bigInt.getFirst();
			while (curB != null && curS != null) {
				r[i++] = curB.iData - curS.iData;
				curB = curB.next;
				curS = curS.next;
			}
			while (curB != null) {
				r[i++] = curB.iData;
				curB = curB.next;
			}
			for (i = 0; i < len; i++) {
				if (r[i] < 0) {
					r[i] += 10;
					r[i + 1]--;
				}
			}
			i = len - 1;
			for (; i >= 0; i--) {
				if (r[i] == 0)
					len--;
				else
					break;
			}
			return new BigInt(r, big.isPositive, len);
		}
	}

	/**
	 * @description Returns a BigInt whose value is (this * val)
	 * @param val
	 * @return BigInt
	 */
	public BigInt multiply(BigInt val) {
		BigInt tmp;
		if (val.isPositive)
			tmp = new BigInt(val);
		else {
			val.isPositive = true;
			tmp = new BigInt(val);
			val.isPositive = false;
		}
		BigInt result = new BigInt(0);
		while (!tmp.toString().equals("0")) {
			result = result.add(this); // accumulation
			tmp = tmp.add(new BigInt(-1)); // val--
		}
		if (this.isPositive == val.isPositive)
			result.isPositive = true;
		else
			result.isPositive = false;
		return result;
	}

	/**
	 * @description Returns a BigInt whose value is (this - val)
	 * @param val
	 * @return BigInt
	 */
	public BigInt subtract(BigInt val) {
		BigInt bi = new BigInt(val);
		bi.isPositive = !bi.isPositive;
		return this.add(bi);
	}

	/**
	 * @description Returns a BigInt whose value is this!
	 * @return BigInt
	 */
	public BigInt factorial() throws NumberFormatException {
		if (!this.isPositive) {
			System.out.println("The number must positive when factorial");
			throw new NumberFormatException();
		}
		BigInt result = new BigInt(1);
		BigInt tmp = new BigInt(this);
		while (!tmp.toString().equals("0")) {
			result = result.multiply(tmp);
			tmp = tmp.add(new BigInt(-1)); // tmp--;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)\
	 * 
	 * @description Have the BigInt class implement the Comparable interface.
	 */
	@Override
	public int compareTo(BigInt bi) {
		// decide according to plus or minus
		if (this.isPositive != bi.isPositive)
			return (this.isPositive ? 1 : -1);
		// decide according to number length
		if (this.length != bi.length) {
			if (this.isPositive)
				return (this.length > bi.length ? 1 : -1);
			else
				return (this.length > bi.length ? -1 : 1);
		}
		Link current = (this.bigInt.getLast());
		Link currentBi = (bi.bigInt.getLast());
		while (current != null && currentBi != null) {
			if (current.iData != currentBi.iData) {
				if (this.isPositive)
					return (current.iData > currentBi.iData ? 1 : -1);
				else
					return (current.iData > currentBi.iData ? -1 : 1);
			}
			current = current.previous;
			currentBi = currentBi.previous;
		}

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * @description Override the equals() method from Object.
	 */
	@Override
	public boolean equals(Object obj) {
		return this.compareTo((BigInt) obj) == 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * @decription Returns the decimal representation of this BigInt as a String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// if it's negative number, add the minus
		if (!this.isPositive)
			sb.append("-");
		Link current = (this.bigInt.getLast());
		while (current != null) {
			sb.append(current.iData);
			current = current.previous;
		}

		return sb.toString();
	}

	/**
	 * @description Returns the 2's complement representation of this BigInt as
	 *              a String using the minimum number of digits necessary
	 * @return
	 */
	public String toString2s() {
		long tmp = 0;
		Link current = (this.bigInt.getLast());
		while (current != null) {
			tmp = tmp * 10 + current.iData;
			current = current.previous;
		}

		return Long.toBinaryString(tmp);
	}
}
