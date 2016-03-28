package controller;

import java.util.Scanner;

import view.GridSheet;

public class Control {
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("The grid is a square, please input the # of boxes per side.");
		int dimension = scan.nextInt();
		dimension = 2*dimension - 1;
		
		GridSheet gameSheet = new GridSheet(dimension, dimension, "D", "C");
	}
	
}
