package minesweeper.dto;

public class Cell {
	
	private String value;
	private boolean isOpened;
	
	public Cell(String val) {
		this.value = val;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isOpened() {
		return isOpened;
	}
	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

}
