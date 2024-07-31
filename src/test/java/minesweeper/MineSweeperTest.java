package minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import minesweeper.dto.Cell;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MineSweeperTest {

    private Scanner mockScanner;

    private MineSweeper mineSweeper;

    @BeforeEach
    public void setUp() {
    	mockScanner = new Scanner(System.in);
        mineSweeper = new MineSweeper();
    }

    @Test
    public void testGetInput_ValidInputWithinRange() {
        // Mock Scanner and set up input
        String input = "a\n5\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        

        int result = mineSweeper.getInput(10, "Enter a number:");

        assertEquals(5, result);

        // Reset System.in
        System.setIn(stdin);
    }
    
    @Test
    public void testUserSelection() {
    	String input = "A1\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        String s = mineSweeper.getUserSelection(5, input);
    }
    
    @Test
    public void testSetupGame() {
        
        Cell[][] s = mineSweeper.setUpGame(5, 5);
    }
    
    @Test
    public void testGetAlphabets() {
    	
    	List<Character> l = mineSweeper.getAlphabets(5);
    	assertEquals('A', l.get(0));
    	
    }

}
