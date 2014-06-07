package bigint.controller;

import java.util.Scanner;

import bigint.module.BigInt;
import bigint.view.Display;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean flag = true;
		Display dis = new Display();
		while (flag) {
			dis.showMenu();
			String s = in.next();
			try {
				flag = dis.choice(s);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Please input again!");
			}
		}
	}
}
