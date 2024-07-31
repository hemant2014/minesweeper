package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import minesweeper.dto.Cell;

public class MineSweeper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Minesweeper!");
		Scanner sc = new Scanner(System.in);
		int gridSize = 0;
		int mines = 0;
		MineSweeper ms = new MineSweeper();
		gridSize = ms.getInput(26, "Enter the size of the grid (e.g. 4 for a 4x4 grid):");
		int maxMines = (int) ((Math.pow(gridSize, 2) * 35)/100);
		mines = ms.getInput(maxMines, "Enter the number of mines to place on the grid (maximum is 35% of the total squares):");
		ms.mineSweeperStart(gridSize, mines);
	}
	
	public int getInput(int number, String msg) {
		Scanner sc = new Scanner(System.in);
		boolean isInputValid = true;
		int input = 0;
		do {
			isInputValid = true;
			System.out.println(msg);
			try {
				input = sc.nextInt();
				if(input > number) {
					isInputValid = false;
					System.out.println("Your input is incorrect.");
				}
			} catch(Exception e) {
				sc.nextLine();
				isInputValid = false;
				System.out.println("Your input is incorrect.");
			}
		} while(!isInputValid);
		return input;
	}
	
	public String getUserSelection(int number, String s) {
		List<Character> alphabets = getAlphabets(number);
		boolean isInputValid = true;
		String input = "";
		do {
			isInputValid = true;
			Scanner sc =new Scanner(System.in);
			System.out.println(s);
			try {
				input = sc.nextLine();
				if(Integer.parseInt(input.substring(1)) > number) {
					isInputValid = false;
					System.out.println("Your input is incorrect.");
				}
				if(!alphabets.contains(input.charAt(0))) {
					isInputValid = false;
					System.out.println("Your input is incorrect.");
				}
			} catch (Exception e) {
				isInputValid = false;
				System.out.println("Your input is incorrect.");
			}
		} while(!isInputValid);
		return input;
	}
	
	public void mineSweeperStart(int gridSize, int mines) {
		Cell[][] cells = setUpGame(gridSize, mines);
		playGame(cells, mines);
	}
	
	public void playGame(Cell[][] cells, int mines) {
		List<Character> alphabets = getAlphabets(cells.length);
		int totalCells = cells.length * cells.length;
		int openedCell = 0;
		
		do {
			String userSelection = getUserSelection(cells.length, "Select a square to reveal (e.g. A1):");
			int row = alphabets.indexOf(userSelection.charAt(0));
			int col = Integer.parseInt(userSelection.substring(1)) - 1;
			System.out.println("This square contains " + cells[row][col].getValue() + " adjacent mines.");
			if(cells[row][col].getValue().equals("*")) {
				System.out.println("You lost the game.");
				return;
			} else {
				expose(row, col, cells);
				printCells(cells);
			}
			openedCell = Arrays.stream(cells).flatMap(Arrays::stream).reduce(0, (val, cell) -> {
				if(cell.isOpened()) {
					val = val + 1;
				}
				return val;
			}, Integer::sum);
		} while((totalCells - openedCell) > mines);
		System.out.println("Congratulations!!!! you won the game.");
	}

	public Cell[][] setUpGame(int gridSize, int mines) {
		Cell[][] cells = fillCells(gridSize, mines);
		printCells(cells);
		initCells(cells);
		return cells;
		
	}
	
	public void initCells(Cell[][] cells) {
		for(int i=0; i< cells.length; i++) {
			for(int j=0;j<cells.length;j++) {
				if(!cells[i][j].getValue().equals("*")) {
					int l1 = getCellValue(i-1, j-1, cells);
					int l2 = getCellValue(i-1, j, cells);
					int l3 = getCellValue(i-1, j+1, cells);
					int s1 = getCellValue(i, j-1, cells);
					int s2 = getCellValue(i, j+1, cells);
					int r1 = getCellValue(i+1, j-1, cells);
					int r2 = getCellValue(i+1, j, cells);
					int r3 = getCellValue(i+1, j+1, cells);
					cells[i][j].setValue(Integer.toString(l1+l2+l3+s1+s2+r1+r2+r3));
				}
			}
		}
	}
	
	public int getCellValue(int i, int j, Cell[][] cells) {
		if(!checkValidCell(i, j, cells.length)) {
			return 0;
		}
		
		if(cells[i][j].getValue().equals("*")) {
			return 1;
		}
		return 0;
	}
	
	private boolean checkValidCell(int i, int j, int size) {
		return (i <= size-1) && (i >= 0) && (j <= size-1) && (j >= 0);
	}
	
	private Cell[][] fillCells(int size, int maxMines) {

		Cell[][] cells = new Cell[size][size];
		List<Integer> randomCell = new ArrayList();
		while(randomCell.size()<maxMines) {
			int r = (int) (Math.random() * (size * size));
			if(!randomCell.contains(r)) {
				randomCell.add(r);
			}
		}
		Collections.sort(randomCell);
		int cellCount = 0;
		int mineCount = 0;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				Cell c = new Cell("0");
				c.setOpened(false);
				if(maxMines>0 && cellCount == randomCell.get(mineCount)) {
					c.setValue("*");
					maxMines--;
					mineCount++;
				} else {
					c.setValue("0");
				}
				cells[i][j] = c;
				cellCount++;
			}
		}
		return cells;
	}
	
	private void expose(int i, int j, Cell[][] cells) {
		if(!checkValidCell(i, j, cells.length)) {
			return;
		}
		
		if(cells[i][j].getValue().equals("*")) {
			return;
		}
		
		if(!cells[i][j].getValue().equals("0")) {
			cells[i][j].setOpened(true);
		} else {
			if(!cells[i][j].isOpened()) {
				expose(i+1, j, cells);
				expose(i, j+1, cells);
				cells[i][j].setOpened(true);
				expose(i-1, j, cells);
				expose(i, j-1, cells);
			}
		}
	}
	
	private void printCells(Cell[][] cells) {
		System.out.println("Here is your minefield:");
		List<Character> alphabets = getAlphabets(cells.length);
		for(int i =0;i<=cells.length;i++) {
			if(i>0) {
				System.out.print(i+" ");
			} else {
				System.out.print("  ");
			}
		}
		System.out.println();
		for(int i =0;i<cells.length; i++) {
			System.out.print(alphabets.get(i)+" ");
			for(int j=0;j<cells.length;j++) {
				if(j>8) {
					if(cells[i][j].isOpened()) {
						System.out.print(cells[i][j].getValue()+"  ");
					} else {
						System.out.print("_  ");
					}
				} else {
					if(cells[i][j].isOpened()) {
						System.out.print(cells[i][j].getValue()+" ");
					} else {
						System.out.print("_ ");
					}
				}
			}
			System.out.println();
		}
	}
	
	public List<Character> getAlphabets(int number) {
		List<Character> alphabets = new ArrayList<Character>();
		for(char ch='A'; ch<='Z'; ch++) {
			alphabets.add(ch);
			number--;
			if(number==0) {
				break;
			}
		}
		return alphabets;
	}

}
