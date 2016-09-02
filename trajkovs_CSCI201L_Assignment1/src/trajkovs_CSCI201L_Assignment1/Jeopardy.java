package trajkovs_CSCI201L_Assignment1;

//import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.*;
//import java.util.Scanner;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Set;
//import java.util.Iterator;

//import Question

public class Jeopardy {
	static private String [] Categories = new String[5]; // holds all the categories for the game
	static private int [] Points = new int[5];	// holds the point values for the game
//	static private ArrayList<Question> Questions = new ArrayList<Question>(0);	// holds all the questions for the game
	static private HashMap<String, ArrayList<Question>> Questions = new HashMap<String, ArrayList<Question>>();
	static private Question FJQuestion = null;
	
	static private int numTeams;
	static private ArrayList<Team> Teams = new ArrayList<Team>(0);
	
	static private String [][] Answers = { {"what", "who", "where", "how"}, {"is", "are"} };
	
	private static void setCategories(String [] cat) {
		Categories = cat.clone();
	}
	
	private static void listCategories() {
		for (String cat: Categories)
			System.out.println(cat);
	}
	
	private static void setPoints(String [] pts) {
		for (int i = 0; i < pts.length; ++i) {
			Points[i] = Integer.parseInt(pts[i].trim());
		}
	}

	private void listPointValues(String cat) {
		ArrayList<Question> qs = Questions.get(cat.toLowerCase());
		for (int pts: this.Points) {
			for (Question q: qs) {
				if (q.getPointValue() == pts && !q.isAnswered())
					System.out.println(pts);
			}
		}
	}
	
	private static Question getQuestion(String cat, int ptValue) {
		ArrayList<Question> qs = Questions.get(cat.toLowerCase());
		for (Question q: qs) {
			if (q.getPointValue() == ptValue)
				return q;
		}
		return null;
	}
	
	private static boolean checkAnswer(String [] userAns, String answer) {
		String [] actual = answer.split("\\s+");
		
		// make sure that varying length works
		for (int i = 0; i < actual.length; ++i) {
			// trim the strings to remove the whitespace
			if (!actual[i].toLowerCase().equals(userAns[i+2].toLowerCase()))
				return false;
		}
		
		return true;
	}
	
	private void displayAllQuestions() {
		for (String key: this.Questions.keySet()) {
			// Get all the Questions in a Category
			ArrayList<Question> qs = Questions.get(key);
			for (Question q: qs) {
	//			System.out.println("Q1:");
				System.out.println(q.getCategory());
				System.out.println(q.getPointValue());
				System.out.println(q.getQuestion());
				System.out.println(q.getAnswer());
				System.out.println("*****");
			}
		}
	}
	
	private void showAllTeams() {
		for (ArrayList<Team> team : this.Teams) {
			System.out.println("Team: " + team.getName() + ", Points: " + team.getPoints());
		}
	}
	
	private void showScores() { // add exceptions for when empty array
//		ArrayList<Integer> winner = new ArrayList<Integer>(0); 
		
		for (Team team : Teams)
			System.out.println("Team: " + team.getName() + ", Points: " + team.getPoints());
	}
	
	private static void showWinner() { // add exceptions for when empty array
		ArrayList<Integer> winner = new ArrayList<Integer>(0); 
		winner.add(0);
//		Team team = Teams.get(0);
//		team.add(Teams.get(0));
		for (int i = 1; i < Teams.size(); ++i) {
			if (Teams.get(i).getPoints() > Teams.get(winner.get(0)).getPoints()) {
				winner.clear();
				winner.add(i);
			} else if (Teams.get(i).getPoints() == Teams.get(winner.get(0)).getPoints())
				winner.add(i);
		}
		
		System.out.print("The winning team");
		if (winner.size() == 1)
			System.out.print(" is: ");
		else
			System.out.print("s are: ");
		for (int team = 0; team < winner.size(); ++team)
			System.out.print(Teams.get(team).getName() + Teams.get(team).getPoints());
		System.out.println();
	}
	
	private void listTeams() {
		for (Team team: Teams) {
			System.out.println(team.getName());
			System.out.println(team.getPoints());
		}
	}
	
	private static boolean elementExists(String [] array, String value) {
		for (String toTest: array) {
			if ((toTest.toLowerCase()).equals(value.toLowerCase()))
				return true;
		}
		return false;
	}
	
	private static boolean elementExists(int [] array, int value) {
		for (int toTest: array) {
			if (toTest == value)
				return true;
		}
		return false;
	}
	
	private static void checkGame(String input) {
		if (input.split("\\s+").length == 1) {
			if (input.toLowerCase().equals("replay")) {
				System.out.println("Restarting...");
				PlayGame();
			} else if (input.toLowerCase().equals("exit")) {
				System.out.println("Exiting...");
				System.exit(0);
			}
		}
	}
	
	private static boolean isNumber(String input) {
		for (int i = 0; i < input.length(); ++i) {
			if (!Character.isDigit(input.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static void ParseFile(String input/*, ArrayList<Question> Questions, String [] Categories, int [] Points*/) {	
		String currLine;
		int questionCount = 0;
		BufferedReader br = null;
		
		try {
			// Create the file stream
			br = new BufferedReader(new FileReader(input));
			
			// Parse in the Categories
			currLine = br.readLine();
			String [] line = currLine.split("::");
			if (line.length != 5) {
				System.out.println("Wrong number of categories"); // change this to an exception
				System.exit(1);
			}
			else
				/*this.*/setCategories(line);
			
			// Parse in the Point values for questions
			currLine = br.readLine().trim();
			line = currLine.split("::");
			if (line.length != 5) {
				System.out.println("Wrong number of categories"); // change this to an exception. check if only digits
				System.exit(1);
			}
			else
				/*this.*/setPoints(line);
		
			// Parse in the questions
			while ((currLine = br.readLine()) != null) {
				if (!currLine.trim().startsWith("::")) { // check if it's alpha after the :: also
					System.out.println("Wrong question format");
					System.exit(1);
				}
				else if (currLine.startsWith("::FJ")) {
					line = currLine.split("::");
					if (line.length != 4) {
						System.out.println("Wrong format for Final Jeopardy question!");
						System.exit(1);
					}
					FJQuestion = new Question(line[2].trim(), line[3].trim());
//					System.out.println("LEN: " + line.length);
//					System.out.println(Arrays.toString(line));
				} else {
					line = currLine.split("::");
//					error checking for valid category and, values
					String cat = line[1].trim();
					int pts = Integer.parseInt(line[2]);
					String question = line[3].trim();
					String answer = line[4].trim();
					
					Arrays.sort(Points);
					
					if (elementExists(Categories, cat) /*&& Points.contains(pts)*/) {
						// if first time adding key
						if (Questions.get(cat.toLowerCase()) == null)
							Questions.put(cat.toLowerCase(), new ArrayList<Question>());
//							Questions.put(cat, new Question(cat, pts, question, answer));
						Questions.get(cat.toLowerCase()).add(new Question(cat.toLowerCase(), pts, question, answer));
						//Questions.put(cat, new Question(cat, pts, question, answer));
						questionCount++;
					} else{
						System.out.println("Category or Points Value invalid");
						return;
					}
				}
			}

		} catch (FileNotFoundException fnfe) {
			System.out.println("FileNotFoundException: " + fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		} finally {
			// Close the file stream
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}

			if ( !(questionCount == 25 && FJQuestion != null) ) {	// when both a question and FJ missing
				System.out.println("Wrong number of questions");
				System.exit(1);
			}
			else
				System.out.println("Guccy");
		}
	}
	
	private static void GenerateTeams() {
		Scanner userInput = new Scanner(System.in);
		numTeams = 0;

		System.out.print("Please enter the number of teams that will be playing the game (1-4): ");
		while (numTeams < 1 || numTeams > 4) {
			numTeams = Integer.parseInt(userInput.nextLine());

			if (numTeams < 1 || numTeams > 4 /*|| numTeams == null*/)
				System.out.println("Invalid number of teams! Please try again!");
		}
		
		for (int i = 1; i <= numTeams; ++i) {
//			String teamName;
//			String teamName = "Team " + i;
			System.out.print("Enter name for team " + i + " (Deafult 'Team " + i + "'): ");
			String teamName = (userInput.nextLine()).trim();
			if (teamName.isEmpty())
				teamName = "Team " + i;
			Teams.add(new Team(teamName));
			// error checking duplicste names. set default name
		}
	}
	
	private static void PlayGame() {
		// Create a starting team
		Jeopardy Game = new Jeopardy();
		int currTeam = (int)(Math.random() * numTeams);
		int qsAnswered = 0;
		
		Scanner userInput = new Scanner(System.in);
		String catChoice;
		int ptChoice;
		
		while (qsAnswered <= 25) {
			System.out.println(currTeam);
			System.out.println("Team " + Teams.get(currTeam).getName() + " turn.");
			
			// Category selection
			while (true) {
				System.out.println("Choose a category:");
				Game.listCategories();
				catChoice = userInput.nextLine();
				checkGame(catChoice);	// check for replay or exit
				if (elementExists(Categories, catChoice))
					break;
				System.out.println("Wrong Category choice!!!");
			}

			// Point choice
			while (true) {
				System.out.println("Choose a point value:");
				Game.listPointValues(catChoice);
				ptChoice = Integer.parseInt(userInput.nextLine());
				checkGame(Integer.toString(ptChoice));	// check for replay or exit
				if (elementExists(Points, ptChoice))
					break;
				System.out.println("Wrong Point Value choice!!!");
			}
			
			// Ask question
			Question currQuestion = getQuestion(catChoice, ptChoice);
			System.out.println("Question: " + currQuestion.getQuestion());
			int numTries = 0;	// holds the number of tries
			// Give the player a 2 tries
//			boolean correctAnswer = false;
			System.out.println(currQuestion.isAnswered());
			while (numTries<= 2/* && !correctAnswer*/) {
				System.out.println("numTries: " + numTries);
				if (numTries == 2) {
					currQuestion.setAnswered();
					System.out.println("Wrong answer!!! " + ptChoice + " will be subtracted from your score.");
					Teams.get(currTeam).subPoints(ptChoice);
					break;
				}
				else {
					System.out.print("Answer: ");
					String ans = userInput.nextLine();
					checkGame(ans);	// check for replay or exit
		
					String [] ansBeginning = ans.split("\\s+");
//					System.out.println(Arrays.toString(ansBeginning));
					if ( elementExists(Answers[0], ansBeginning[0]) && elementExists(Answers[1], ansBeginning[1]) ) {
//						if (ans.toLowerCase() == currQuestion.getAnswer()) {
						if (checkAnswer(ansBeginning, currQuestion.getAnswer())) {
							currQuestion.setAnswered();
							System.out.println("Correct answer!!! " + ptChoice + " will be added to your score.");
							Teams.get(currTeam).addPoints(ptChoice);
							break;
						} else {
							currQuestion.setAnswered();
							System.out.println("Wrong answer!!! " + ptChoice + " will be subtracted from your score.");
							Teams.get(currTeam).subPoints(ptChoice);
//							break;
						}
						System.out.println(currQuestion.isAnswered());
						break;
					} else
						++numTries;
				}
			}
			System.out.println("The current scores are: ");
			Game.showScores();
			
			
			// Update current team
			++currTeam;
			if (currTeam >= numTeams)
				currTeam = 0;
			
			++qsAnswered;
		}
	}
	
	private static void FinalJeopardy() {
		System.out.println("********************************");
		System.out.println("******** Final Jeopardy ********");
		System.out.println("********************************");
		
		System.out.println("Teams place your bets!!!");
		
		Scanner userInput = new Scanner(System.in);
		String bet;
		ArrayList<Integer> Bets = new ArrayList<Integer>(0);
		
		for (Team team : Teams) {
			System.out.print(team.getName() + ": ");
			boolean correct = false;
			while (!correct) {
				bet = userInput.nextLine();
				checkGame(bet);
				while (!isNumber(bet.trim())) {
					System.out.println("Not a number. Please input a number for your bet.");
					bet = userInput.nextLine();
					checkGame(bet);
				}
				if (Integer.parseInt(bet.trim()) > team.getPoints())
					System.out.println("Invalid bed. Please enter a bet withing range.");
				else {
					Bets.add(Integer.parseInt(bet.trim()));
					correct = true;
				}
			}
		}
		
		System.out.println(FJQuestion.getQuestion());
		for (int i = 0; i < Teams.size(); ++i) {
			int numTries = 0;
			while (numTries<= 2) {
				if (numTries == 2) {
					System.out.println("Wrong answer!!! " + Bets.get(i) + " will be subtracted from your score.");
					Teams.get(i).subPoints(Bets.get(i));
					break;
				}
				else {
					System.out.print(Teams.get(i).getName() + " answer: ");
					String ans = userInput.nextLine();
					checkGame(ans);	// check for replay or exit
		
					String [] ansBeginning = ans.split("\\s+");
					if ( elementExists(Answers[0], ansBeginning[0]) && elementExists(Answers[1], ansBeginning[1]) ) {
						if (checkAnswer(ansBeginning, FJQuestion.getAnswer())) {
							System.out.println("Correct answer!!! " + Bets.get(i) + " will be added to your score.");
							Teams.get(i).addPoints(Bets.get(i));
							break;
						} else {
							System.out.println("Wrong answer!!! " + Bets.get(i) + " will be subtracted from your score.");
							Teams.get(i).subPoints(Bets.get(i));
						};
						break;
					} else
						++numTries;
				}
			}
		}		
	}
	
	public static void main(String [] args) {

		// Init the game and parse in input file
		ParseFile(args[0]);
		
		System.out.println("Welcome to Jeopardy!");
		// Play the game
		// Create the teams
		GenerateTeams();
		System.out.println("Thank you! Setting up the game for you...");
		System.out.println("Ready to play!");
		
		System.out.println("Autoplay?"); // implement this. what is autoplay
		
		// Play game
//		FinalJeopardy();
		PlayGame();
		FinalJeopardy();
//		showWinner();

		System.out.println("--Game Finished--");
		
	}
}