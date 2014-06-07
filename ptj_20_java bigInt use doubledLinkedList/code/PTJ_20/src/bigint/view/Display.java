package bigint.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bigint.module.BigInt;

public class Display {
	public void showStr(String s) {
		System.out.println(s);
	}

	public void showMenu() {
		System.out.print("Enter here> ");
	}

	public boolean choice(String s) {
		if (s.equals("q")) {
			return false;
		}
		else if (s.equals("h")) {
			System.out
					.println("Help: This is going to be a text-based program where the user can input the 4 arithmetic operations (+, -, *, !)"
							+ "Attention: when use subtract please use ~(e.g -2-10, you should input -2~10");
		}
		else {
			// remove ( and )
			s = s.replaceAll("\\(", "");
			s = s.replaceAll("\\)", "");
			if (s.contains("+")) {
				String[] nums = s.split("\\+");
				BigInt a = new BigInt(nums[0]);
				BigInt b = new BigInt(nums[1]);
				System.out.println(a.add(b));
			}
			else if (s.contains("~")) {
				String[] nums = s.split("\\~");
				BigInt a, b;
				a = new BigInt(nums[0]);
				b = new BigInt(nums[1]);
				System.out.println(a.subtract(b));
			}
			else if (s.contains("*")) {
				String[] nums = s.split("\\*");
				BigInt a = new BigInt(nums[0]);
				BigInt b = new BigInt(nums[1]);
				System.out.println(a.multiply(b));
			}
			else if (s.contains("!")) {
				BigInt a = new BigInt(s.substring(0, s.length() - 1));
				try {
					System.out.println(a.factorial());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}
}
