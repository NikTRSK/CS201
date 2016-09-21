package trajkovs_CSCI201L_Assignment1;

import trajkovs_CSCI201L_Assignment1.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
	JLabel titleLbl;
	JLabel progressTitle;
	JTextArea teamPrompt;

	// Question Panel
	JPanel questionPanel, finalJeopardyPanel;
	JLabel qTeamLbl, qCatLbl, qPtValueLbl, qErrorLbl;
	JTextArea qQuestionArea;
	JTextField qAnswerArea;
	JButton qSubmitBtn;
	
	JLabel [] catLbls = new JLabel [5];
	List<Pair<JLabel, JLabel>> teamLbl = new ArrayList<Pair<JLabel, JLabel>>(4);
	List<List<JButton>> qBtns = new ArrayList<List<JButton>>();
	
	// Final Jeopardy components
	JPanel [] finalPtChooser = new JPanel[4];
	JLabel [] teamPtLbl = new JLabel[4];
	JSlider [] ptSelectSlider = new JSlider[4];
	JLabel [] teamPtValLbl = new JLabel[4];
	JButton [] setBetBtn = new JButton[4];
	JTextArea FJQArea = new JTextArea();
	
	// tmp
	int [] ptvals = {200, 5000, 100, 500};
	String [] teamvals = {"Team1", "Awesome", "What", "Good"};
	
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
		for (int i = 0; i < catLbls.length; ++i) {
			catLbls[i] = new JLabel("cat1", SwingConstants.CENTER);
			Helpers.styleComponent(catLbls[i], Color.RED, Color.BLUE, 20);
		}

//		cat1Lbl.setBackground(Color.RED);
//		cat1Lbl.setOpaque(true);
//		cat1Lbl.setBorder(new LineBorder(Color.BLUE));
//		cat1Lbl.setFont(new Font("Cambria", Font.BOLD, 20));
		
		// Create Question buttons
		for (int cat = 0; cat < 5; ++cat) {
			qBtns.add(new ArrayList<JButton>());
			for (int pt = 0; pt < 5; ++pt) {
//				questionTitlePanel.add(qBtns.get(cat).get(pt));
				qBtns.get(cat).add(new JButton("cat " + pt + " pt " + cat));
				Helpers.styleComponent(qBtns.get(cat).get(pt), Color.GRAY, Color.BLUE, 15);
				// Make it look flat
			  qBtns.get(cat).get(pt).setForeground(Color.WHITE);
			  qBtns.get(cat).get(pt).setPreferredSize(new Dimension(100, 100));
			}
		}
		
		// Generate all teams and values to Score Panel
		for (int i = 0; i < teamLbl.size(); i++) {
			teamLbl.get(i).setItem1(new JLabel("Test " + i)); 
			teamLbl.get(i).setItem2(new JLabel("$000" + i));
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
		qQuestionArea.setFont(new Font("Cambria", Font.BOLD, 20));
		qQuestionArea.setLineWrap(true);;
		
		qAnswerArea = new JTextField(20);
		qAnswerArea.setBackground(Color.WHITE);
		qAnswerArea.setFont(new Font("Cambria", Font.BOLD, 18));
		
		qSubmitBtn = new JButton("Submit Answer");
		qSubmitBtn.setFont(new Font("Cambria", Font.BOLD, 18));
		
		qErrorLbl = new JLabel("This is a place holder for error message");
//		qErrorLbl.setBackground(Color.DARK_GRAY);
//		qErrorLbl.setOpaque(true);
//		qErrorLbl.setBorder(new LineBorder(Color.DARK));
		qErrorLbl.setFont(new Font("Cambria", Font.BOLD, 15));
		
//		JLabel qTeamLbl, qCatLbl, qPtValueLbl;
//		JTextArea qQuestionArea;
//		JTextField qAnswerArea;
//		JButton qSubmitBtn;
	}
	
	private void createGUI() {
		// Initial setup
		setSize(1500, 825);
		setLocation(100, 30);
		add(menuBar, BorderLayout.NORTH);
		JPanel screen = new JPanel();
		screen.setLayout(new BoxLayout(screen, BoxLayout.X_AXIS));
		screen.setPreferredSize(new Dimension(1030,825));
//		add(screen, BorderLayout.CENTER);
		add(screen, BorderLayout.WEST);
		
		JPanel mainPanel = new JPanel(/*new CardLayout()*/);
		CardLayout cl = new CardLayout();
		mainPanel.setLayout(cl);
		mainPanel.setPreferredSize(new Dimension(200, 200));
		screen.add(mainPanel);
		
		// Create the main Panel
		JPanel questionListPanel = new JPanel();
		questionListPanel.setLayout(new BoxLayout(questionListPanel, BoxLayout.Y_AXIS));
	  Border mainBorder = new EmptyBorder(10, 20, 50, 20);
		questionListPanel.setBorder(mainBorder);
		mainPanel.add(questionListPanel, "1");
//		screen.add(questionListPanel);
				
		JPanel titlePanel = new JPanel(); titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		titlePanel.setBackground(Color.blue);
//		questionListPanel.add(titlePanel);
		titlePanel.add(titleLbl);
		questionListPanel.add(titlePanel);
		
		// Create Category Panel
		JPanel questionLabelsPanel = new JPanel(); questionLabelsPanel.setLayout(new GridLayout(1, 5));
		questionListPanel.add(questionLabelsPanel);
		
		// add category labels to grid
		for (int i = 0; i < catLbls.length; ++i)
		questionLabelsPanel.add(catLbls[i]);
		
		// Create whole board
		JPanel questionPanel = new JPanel(); questionPanel.setLayout(new GridLayout(5, 5));
		questionListPanel.add(questionPanel);
		for (int cat = 0; cat < 5; ++cat) {
			qBtns.add(new ArrayList<JButton>());
			for (int pt = 0; pt < 5; ++pt) {
				questionPanel.add(qBtns.get(cat).get(pt));
			}
		}
		
		// Side panel
		JPanel sidePanel = new JPanel(new GridLayout(2, 1));
		sidePanel.setPreferredSize(new Dimension(screen.getWidth()/6, screen.getHeight()));
//		screen.add(sidePanel);
		add(sidePanel, BorderLayout.CENTER);
		
		JPanel scorePanel = new JPanel(new GridLayout(2, 4));
		scorePanel.setBackground(Color.DARK_GRAY);
		sidePanel.add(scorePanel);
		
		// add all teams to Score Panel
		for (int i = 0; i < teamLbl.size(); i++) {
			scorePanel.add(teamLbl.get(i).getItem1());
			scorePanel.add(teamLbl.get(i).getItem2());
		}
		
//		for (int i = 0; i < teamsLbl.size(); i++) {
//			teamsLbl.get(i) = new JLabel("Test " + i);
//			teamsLbl.get(i).setFont(new Font("Cambria", Font.BOLD, 20));
//		}
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
		
//		questionListPanel.setVisible(false);
		
//		fJeopardy = new JLabel()
		
		createQuestionPanel();
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createQuestionPanel() {
		JFrame frame = new JFrame();
		JPanel test = new JPanel();
		frame.setSize(700, 500);
		CardLayout cl = new CardLayout();
		test.setLayout(cl);
		test.setSize(700, 500);
		
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		questionPanel.setBackground(Color.DARK_GRAY);
		
		JPanel qTitlePanel = new JPanel(new GridLayout(1, 3));
		qTitlePanel.setBackground(Color.BLUE);
		qTitlePanel.add(qTeamLbl);
		qTitlePanel.add(qCatLbl);
		qTitlePanel.add(qPtValueLbl);
		questionPanel.add(qTitlePanel);
		
		JPanel errorPanel = new JPanel();
		errorPanel.setBackground(Color.DARK_GRAY);
		errorPanel.add(qErrorLbl);
		questionPanel.add(errorPanel);
		
		JPanel qPanel = new JPanel();
	  Border qBorder = new EmptyBorder(10, 20, 50, 20);
	  qPanel.setBorder(qBorder);
	  questionPanel.add(qPanel);
	  qQuestionArea.setSize(new Dimension(500, 300));
	  qQuestionArea.setText("This is a test text | This is a test text | This is a test text | This is a test text");
	  qPanel.add(qQuestionArea);
		
	  JPanel answerPanel = new JPanel();
//	  answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.X_AXIS));
	  questionPanel.add(answerPanel);
	  answerPanel.add(qAnswerArea);
	  answerPanel.add(qSubmitBtn);
//	JTextArea qQuestionArea;
//	JTextField qAnswerArea;
//	JButton qSubmitBtn;
		
	  ////////////////////////////////////////////////////////
	  // Final Jeopardy Panel //
	  ////////////////////////////////////////////////////////
	  finalJeopardyPanel = new JPanel();
	  finalJeopardyPanel.setLayout(new BoxLayout(finalJeopardyPanel, BoxLayout.Y_AXIS));
	  
		JPanel FJTitlePanel = new JPanel();
		FJTitlePanel.setBorder(new EmptyBorder(0, 20, 50, 20));
		JLabel FJTitle = new JLabel("Final Jeopardy Round");
		Helpers.styleComponent(FJTitle, Color.BLUE, Color.BLUE, 40);
		FJTitle.setForeground(Color.LIGHT_GRAY);
	  FJTitlePanel.add(FJTitle);
	  
	  finalJeopardyPanel.add(FJTitlePanel);
	  
	  for (int i = 0; i < 4; ++i) {
	  	finalPtChooser[i] = new JPanel();
	  	finalPtChooser[i].setBackground(Color.DARK_GRAY);
	  	
	  	teamPtLbl[i] = new JLabel(teamvals[i] + "'s bet:");
	  	Helpers.styleComponent(teamPtLbl[i], Color.DARK_GRAY, Color.DARK_GRAY, 15);
	  	teamPtLbl[i].setForeground(Color.LIGHT_GRAY);
	  	finalPtChooser[i].add(teamPtLbl[i]);
	  	
	  	ptSelectSlider[i] = new JSlider();
	  	// do a check for negative value
	  	ptSelectSlider[i].setMaximum(ptvals[i]);
	  	ptSelectSlider[i].setMinimum(1);
	  	ptSelectSlider[i].setValue(1);
			ptSelectSlider[i].setPaintLabels(true);
			ptSelectSlider[i].setPaintTicks(true);
			ptSelectSlider[i].setMajorTickSpacing(10);
//			ptSelectSlider[i].setMinorTickSpacing(1);
			ptSelectSlider[i].setFont(new Font("Cambria", Font.BOLD, 15));
			ptSelectSlider[i].setBackground(Color.DARK_GRAY);
			ptSelectSlider[i].setForeground(Color.WHITE);
			ptSelectSlider[i].setPreferredSize(new Dimension(400, 70));
			// Create Border
		  Border line = new LineBorder(Color.DARK_GRAY);
		  Border margin = new EmptyBorder(5, 15, 5, 15);
		  Border compound = new CompoundBorder(line, margin);
			ptSelectSlider[i].setBorder(compound);
			finalPtChooser[i].add(ptSelectSlider[i]);
			
			teamPtValLbl[i] = new JLabel(Integer.toString(ptSelectSlider[i].getValue()));
			Helpers.styleComponent(teamPtValLbl[i], Color.DARK_GRAY, Color.DARK_GRAY, 15);
			teamPtValLbl[i].setForeground(Color.WHITE);
			finalPtChooser[i].add(teamPtValLbl[i]);
			
			setBetBtn[i] = new JButton("Set Bet");
			finalPtChooser[i].add(setBetBtn[i]);

	  	finalJeopardyPanel.add(finalPtChooser[i]);
	  }

	  JPanel FJQPrompt = new JPanel();
	  FJQArea = new JTextArea();
	  
	  FJQPrompt.add(FJQArea);
	  
	  finalJeopardyPanel.add(FJQPrompt);
	  
		test.add(questionPanel, "1");
		test.add(finalJeopardyPanel, "2");
		frame.add(test);
//		finalJeopardyPanel.setVisible(true);
		cl.show(test, "2");
		
//		((CardLayout) test.getLayout()).show(questionPanel, "1");
		frame.setVisible(true);
	}
	
	public static void main(String [] args) {
		GameBoardUI menubar = new GameBoardUI();
		menubar.setVisible(true);
	}

}
