package test;

import java.io.*;
import java.util.*;


public class Main {

	public static void main(String[] args) {
	
		try {
			File file = new File("C:\\Users\\hp-pc\\Desktop\\file.txt");
			Scanner in = new Scanner(file);
			double a = 1.29;
			double b = 2.90;
			
			Point pt = new Point(a, b);
			
			System.out.println(pt.getY());
			int i = 0;
			
//			while (in.hasNext() != false) {
//		
//				String n[] = in.nextLine().split(",");
//
//				for (int j = 0; j < 2; j++) {
//					System.out.println(i + "," + j + ": " + n[j]);
//				}
//			
//				i++;
//			}
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
		
		
	}

}
