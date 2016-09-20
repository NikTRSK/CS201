package trajkovs_CSCI201L_Assignment1;

import trajkovs_CSCI201L_Assignment1.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


// Ref: https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
public class GameBoardUI extends JFrame {
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem restartGame, chooseNewGameFile, exitGame;
	JLabel titleLbl, cat1Lbl, cat2Lbl, cat3Lbl, cat4Lbl, cat5Lbl;
	JLabel progressTitle;
	JTextArea teamPrompt;

	// Question Panel
	JLabel qTeamLbl, qCatLbl, qPtValueLbl;
	JTextArea qQuestionArea;
	JTextField qAnswerArea;
	JButton qSubmitBtn;
	
	List<List<JButton>> qBtns = new ArrayList<List<JButton>>();
	
	public GameBoardUI () {
		super("Play Jeopardy");
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {

		// Create the menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);
		
		// Create Menu items
		restartGame = new JMenuItem("Restart This Game", KeyEvent.VK_T);
		menu.add(restartGame);
		
		chooseNewGameFile = new JMenuItem("Choose New Game File", KeyEvent.VK_T);
		menu.add(chooseNewGameFile);

		exitGame= new JMenuItem("Exit Game", KeyEvent.VK_T);
		exitGame.addActionListener((ActionEvent event) -> {
      System.exit(0);
		});
		menu.add(exitGame);
		
		//////////////////////////
		// Question Panel Items //
		//////////////////////////
		// Create title Label
		titleLbl = new JLabel("Jeopardy");
		titleLbl.setFont(new Font("Cambria", Font.BOLD, 25));
		titleLbl.setForeground(Color.WHITE);
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		
		// Create Category Labels
		cat1Lbl = new JLabel("cat1", SwingConstants.CENTER);
		cat1Lbl.setBackground(Color.RED);
		cat1Lbl.setOpaque(true);
		cat1Lbl.setBorder(new LineBorder(Color.BLUE));
		cat1Lbl.setFont(new Font("Cambria", Font.BOLD, 20));

		cat2Lbl = new JLabel("cat2", SwingConstants.CENTER);
		cat2Lbl.setBackground(Color.RED);
		cat2Lbl.setOpaque(true);
		cat2Lbl.setBorder(new LineBorder(Color.BLUE));
		cat2Lbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		cat3Lbl = new JLabel("cat3", SwingConstants.CENTER);
		cat3Lbl.setBackground(Color.RED);
		cat3Lbl.setOpaque(true);
		cat3Lbl.setBorder(new LineBorder(Color.BLUE));
		cat3Lbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		cat4Lbl = new JLabel("cat4", SwingConstants.CENTER);
		cat4Lbl.setBackground(Color.RED);
		cat4Lbl.setOpaque(true);
		cat4Lbl.setBorder(new LineBorder(Color.BLUE));
		cat4Lbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		cat5Lbl = new JLabel("cat5", SwingConstants.CENTER);
		cat5Lbl.setBackground(Color.RED);
		cat5Lbl.setOpaque(true);
		cat5Lbl.setBorder(new LineBorder(Color.BLUE));
		cat5Lbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		// Create Question buttons
		for (int cat = 0; cat < 5; ++cat) {
			qBtns.add(new ArrayList<JButton>());
			for (int pt = 0; pt < 5; ++pt) {
//				questionTitlePanel.add(qBtns.get(cat).get(pt));
				qBtns.get(cat).add(new JButton("cat " + pt + " pt " + cat));
				// Make it look flat
				qBtns.get(cat).get(pt).setForeground(Color.BLACK);
				qBtns.get(cat).get(pt).setBackground(Color.gray);
//			  qBtns.get(cat).get(pt).setBorder(compound);
			  qBtns.get(cat).get(pt).setFont(new Font("Cambria", Font.BOLD, 15));
			  qBtns.get(cat).get(pt).setForeground(Color.WHITE);
			  qBtns.get(cat).get(pt).setPreferredSize(new Dimension(100, 100));
			  qBtns.get(cat).get(pt).setBorder(new LineBorder(Color.BLUE));
			}
		}
		
		// Progress panel components
		progressTitle = new JLabel("Game Progress", SwingConstants.CENTER);
		progressTitle.setAlignmentX(CENTER_ALIGNMENT); progressTitle.setAlignmentY(TOP_ALIGNMENT);
		progressTitle.setPreferredSize(new Dimension(400, 80));
		progressTitle.setBackground(Color.BLACK);
		progressTitle.setFont(new Font("Cambria", Font.BOLD, 25));
		
		teamPrompt = new JTextArea("Welcome to Jeopardy! The team to go first is Team __________dadasdasdasdas");
		teamPrompt.setLineWrap(true);
		teamPrompt.setForeground(Color.BLACK);
		teamPrompt.setOpaque(false);
		teamPrompt.setFont(new Font("Cambria", Font.BOLD, 17));
		
		qTeamLbl = new JLabel("Team 201");
		qTeamLbl.setBackground(Color.BLUE);
		qTeamLbl.setOpaque(true);
		qTeamLbl.setBorder(new LineBorder(Color.BLUE));
		qTeamLbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		qCatLbl = new JLabel("Category 2");
		qCatLbl.setBackground(Color.BLUE);
		qCatLbl.setOpaque(true);
		qCatLbl.setBorder(new LineBorder(Color.BLUE));
		qCatLbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		qPtValueLbl = new JLabel("$100");
		qPtValueLbl.setBackground(Color.BLUE);
		qPtValueLbl.setOpaque(true);
		qPtValueLbl.setBorder(new LineBorder(Color.BLUE));
		qPtValueLbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		qQuestionArea = new JTextArea();
		qAnswerArea = new JTextField();
		qSubmitBtn = new JButton();
	}
	
	private void createGUI() {
		setSize(1500, 1000);
		setLocation(100, 30);
		add(menuBar, BorderLayout.NORTH);
		
		JPanel screen = new JPanel();
		screen.setLayout(new BoxLayout(screen, BoxLayout.X_AXIS));
		add(screen, BorderLayout.CENTER);
		
		
		
//		JPanel test = new JPanel();
//		screen.add(test);
				
				
		// Create the main Panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	  Border mainBorder = new EmptyBorder(10, 20, 50, 20);
		mainPanel.setBorder(mainBorder);
		screen.add(mainPanel);
				
		JPanel titlePanel = new JPanel(); titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		titlePanel.setBackground(Color.blue);
//		mainPanel.add(titlePanel);
		titlePanel.add(titleLbl);
		mainPanel.add(titlePanel);
		
		// Create Category Panel
		JPanel questionLabelsPanel = new JPanel(); questionLabelsPanel.setLayout(new GridLayout(1, 5));
		mainPanel.add(questionLabelsPanel);
		
		questionLabelsPanel.add(cat1Lbl);
		questionLabelsPanel.add(cat2Lbl);
		questionLabelsPanel.add(cat3Lbl);
		questionLabelsPanel.add(cat4Lbl);
		questionLabelsPanel.add(cat5Lbl);
		
		// Create whole board
		JPanel questionPanel = new JPanel(); questionPanel.setLayout(new GridLayout(5, 5));
		mainPanel.add(questionPanel);
		for (int cat = 0; cat < 5; ++cat) {
			qBtns.add(new ArrayList<JButton>());
			for (int pt = 0; pt < 5; ++pt) {
				questionPanel.add(qBtns.get(cat).get(pt));
			}
		}
		
		// Side panel
		JPanel sidePanel = new JPanel(new GridLayout(2, 1));
		sidePanel.setPreferredSize(new Dimension(screen.getWidth()/3, screen.getHeight()));
		screen.add(sidePanel);
		
		JPanel scorePanel = new JPanel(new GridLayout(2, 4));
		scorePanel.setBackground(Color.DARK_GRAY);
		sidePanel.add(scorePanel);
		// add teams
		// conditional of number of teams
		
		JPanel gameProgressPanel = new JPanel();
		gameProgressPanel.setLayout(new BoxLayout(gameProgressPanel, BoxLayout.Y_AXIS));
		gameProgressPanel.setBackground(Color.YELLOW);
		sidePanel.add(gameProgressPanel);
		
		// Create the Game Progress panel		

		gameProgressPanel.add(progressTitle);
		
//		// Create the team prompt
		gameProgressPanel.add(teamPrompt);
		
		// Create question panel
		
//		mainPanel.setVisible(false);
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args) {
		GameBoardUI menubar = new GameBoardUI();
		menubar.setVisible(true);
	}

}
