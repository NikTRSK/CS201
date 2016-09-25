package trajkovs_CSCI201L_Assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.LineNumberReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
//import Question

public class Jeopardy {
	protected static GameBoardUI GameBoard;
	protected static FileChooser fileChooser = new FileChooser(); 
	
	// Adds the categories to the Categories variable
	protected static void setCategories(String [] cat) {
		GamePlay.Categories = cat.clone();
	}
	
	// Adds the point values to the Points variable
	protected static void setPoints(String [] pts) {
		for (int i = 0; i < pts.length; ++i) {
			GamePlay.Points[i] = Integer.parseInt(pts[i].trim());
		}
	}
	
	// Checks if the point value exists
	protected static boolean pointsExist(String cat, int pts) {
		ArrayList<Question> qs = GamePlay.Questions.get(cat.toLowerCase());
		for (Question q : qs) {
			if (q.getPointValue() == pts)
				return true;
		}
		return false;
	}
	
	// Checks if the game is loaded correctly
	protected static void checkValidGame(int qCount) {
		if ( !(qCount == 25 && GamePlay.FJQuestion != null) ) {	// when both a question and FJ missing
			FileChooser.displayPopup("Wrong number of questions");
			System.exit(1);
		}
		
		if (GamePlay.Questions.size() != 5)
			throw new RuntimeException("Incorrect number of categories\n Terminating...");
		
		for (String key: GamePlay.Questions.keySet()) {
			if (GamePlay.Questions.get(key).size() != 5) {
				throw new RuntimeException("Incorrect number of point values\n Terminating...");
//				System.exit(1);
			}
		}
	}
	
	public static void createGameBoard() {
		fileChooser.setVisible(false);
		GameBoard = new GameBoardUI();
		GameBoard.setVisible(true);
	}
	
	public static void main(String [] args) {
		fileChooser.setVisible(true);
	}
}