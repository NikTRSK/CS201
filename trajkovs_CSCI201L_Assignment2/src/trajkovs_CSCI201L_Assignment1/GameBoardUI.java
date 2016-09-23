package trajkovs_CSCI201L_Assignment1;

import trajkovs_CSCI201L_Assignment1.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;


// Ref: https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
public class GameBoardUI extends JFrame {
	static private CardLayout cl = new CardLayout();
	static private JPanel mainPanel;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem restartGame, chooseNewGameFile, exitGame;
	JLabel titleLbl;
	JLabel progressTitle;
	JTextArea teamPrompt;

	// Question Panel
	JPanel answerQuestionPanel, finalJeopardyPanel;
	JLabel qTeamLbl, qCatLbl, qPtValueLbl, qErrorLbl;
	JTextArea qQuestionArea;
	JTextArea qAnswerArea;
	JButton qSubmitBtn;
	
	JLabel [] catLbls = new JLabel [5];
//	List<Pair<JLabel, JLabel>> teamLbl = new ArrayList<Pair<JLabel, JLabel>>(Collections.nCopies(arg0, arg1)(4);
	List<Pair<JLabel, JLabel>> teamLbl;
	JButton [][] qBtns = new JButton[5][5];
//	List<List<JButton>> qBtns = new ArrayList<List<JButton>>();
	//////////////// debug temp
	
	
	// Final Jeopardy components
	JPanel [] finalPtChooser = new JPanel[4];
	JLabel [] teamPtLbl = new JLabel[4];
	JSlider [] ptSelectSlider = new JSlider[4];
	JLabel [] teamPtValLbl = new JLabel[4];
	JButton [] setBetBtn = new JButton[4];
	JTextArea FJQArea;
	JTextField [] FJAArea = new JTextField[4];
	JButton [] FJABtn = new JButton[4];
	
	// tmp
	int [] ptvals = {200, 5000, 100, 500};
//	String [] teamvals = {"Team1", "Awesome", "What", "Good"};
	
	public GameBoardUI () {
		super("Play Jeopardy");
		initializeComponents();
		createGUI();
		addEvents();
		showPanel("finalJeopardyPanel");
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
			catLbls[i] = new JLabel(GamePlay.Categories[i], SwingConstants.CENTER);
			Helpers.styleComponent(catLbls[i], Color.RED, Color.BLUE, 20);
		}

		// Create Question buttons
		for (int pt = 0; pt < 5; ++pt) {
			for (int cat = 0; cat < 5; ++cat) {
				qBtns[cat][pt] = (new JButton("$" + GamePlay.Points[pt]));
				Helpers.styleComponent(qBtns[cat][pt], Color.DARK_GRAY, Color.BLUE, 22);
				// Make it look flat
				qBtns[cat][pt].setForeground(Color.WHITE);
				qBtns[cat][pt].setPreferredSize(new Dimension(100, 100));
			}
		}
		
		// fill out an empty array of pairs for teams
		teamLbl = new ArrayList<Pair<JLabel, JLabel> >() ;
		for (int i = 0; i < 4; ++i)
			teamLbl.add(new Pair<JLabel, JLabel>(new JLabel("", SwingConstants.CENTER), new JLabel("", SwingConstants.CENTER)));
		
		// Generate all teams and values to Score Panel
		for (int i = 0; i < GamePlay.Teams.size(); i++) {
			System.out.println(teamLbl.size());
			teamLbl.get(i).getItem1().setText(GamePlay.Teams.get(i).getName()); 
			teamLbl.get(i).getItem1().setFont(new Font("Cambria", Font.BOLD, 20));
			teamLbl.get(i).getItem1().setForeground(Color.WHITE);
			teamLbl.get(i).getItem2().setText("$" + GamePlay.Teams.get(i).getPoints());
			teamLbl.get(i).getItem2().setFont(new Font("Cambria", Font.BOLD, 20));
			teamLbl.get(i).getItem2().setForeground(Color.WHITE);
		}
		
		// Progress panel components
		progressTitle = new JLabel("Game Progress", SwingConstants.CENTER);
		progressTitle.setAlignmentX(CENTER_ALIGNMENT); progressTitle.setAlignmentY(TOP_ALIGNMENT);
		progressTitle.setPreferredSize(new Dimension(400, 80));
		progressTitle.setBackground(Color.BLACK);
		progressTitle.setFont(new Font("Cambria", Font.BOLD, 25));
		
		teamPrompt = new JTextArea("Welcome to Jeopardy!\nThe team to go first is " + GamePlay.Teams.get(GamePlay.currTeam).getName() + "\n");
//		teamPrompt = new JTextArea("");
		teamPrompt.setLineWrap(true);
		teamPrompt.setForeground(Color.BLACK);
		teamPrompt.setOpaque(false);
		teamPrompt.setFont(new Font("Cambria", Font.BOLD, 17));
		teamPrompt.setEditable(false);
		DefaultCaret caret = (DefaultCaret)teamPrompt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		qTeamLbl = new JLabel("Team 201", SwingConstants.CENTER);
		qTeamLbl.setVerticalAlignment(SwingConstants.CENTER);
		qTeamLbl.setBackground(Color.BLUE);
		qTeamLbl.setOpaque(true);
		qTeamLbl.setBorder(new LineBorder(Color.BLUE));
		qTeamLbl.setForeground(Color.LIGHT_GRAY);
		qTeamLbl.setFont(new Font("Cambria", Font.BOLD, 30));
		
		qCatLbl = new JLabel("Category 2", SwingConstants.CENTER);
		qCatLbl.setVerticalAlignment(SwingConstants.CENTER);
		qCatLbl.setBackground(Color.BLUE);
		qCatLbl.setOpaque(true);
		qCatLbl.setBorder(new LineBorder(Color.BLUE));
		qCatLbl.setForeground(Color.LIGHT_GRAY);
		qCatLbl.setFont(new Font("Cambria", Font.BOLD, 30));
		
		qPtValueLbl = new JLabel("$100", SwingConstants.CENTER);
		qPtValueLbl.setVerticalAlignment(SwingConstants.CENTER);
		qPtValueLbl.setBackground(Color.BLUE);
		qPtValueLbl.setOpaque(true);
		qPtValueLbl.setBorder(new LineBorder(Color.BLUE));
		qPtValueLbl.setForeground(Color.LIGHT_GRAY);
		qPtValueLbl.setFont(new Font("Cambria", Font.BOLD, 30));
		
		qQuestionArea = new JTextArea();
		qQuestionArea.setFont(new Font("Cambria", Font.BOLD, 28));
		qQuestionArea.setWrapStyleWord(true);
		qQuestionArea.setLineWrap(true);
		qQuestionArea.setFocusable(false);
		qQuestionArea.setCursor(null);
		qQuestionArea.setPreferredSize(new Dimension(850, 500));
	  qQuestionArea.setEditable(false);
		
		qAnswerArea = new JTextArea();
		qAnswerArea.setPreferredSize(new Dimension(500, 60));
		qAnswerArea.setLineWrap(true);
		qAnswerArea.setWrapStyleWord(true);
		qAnswerArea.setBackground(Color.WHITE);
		qAnswerArea.setForeground(Color.LIGHT_GRAY);
		qAnswerArea.setFont(new Font("Cambria", Font.BOLD, 22));
		
		qSubmitBtn = new JButton("Submit Answer");
    // Make it look flat
		qSubmitBtn.setForeground(Color.BLACK);
		qSubmitBtn.setBackground(Color.WHITE);
	  Border line = new LineBorder(Color.WHITE);
	  Border margin = new EmptyBorder(5, 15, 5, 15);
	  Border compound = new CompoundBorder(line, margin);
		qSubmitBtn.setBorder(compound);
		qSubmitBtn.setPreferredSize(new Dimension(150, 60));
		qSubmitBtn.setFont(new Font("Cambria", Font.BOLD, 18));
		
		qErrorLbl = new JLabel("This is a place holder for error message", SwingConstants.CENTER);
		qErrorLbl.setVerticalAlignment(SwingConstants.CENTER);
//		qErrorLbl.setBackground(Color.DARK_GRAY);
//		qErrorLbl.setOpaque(true);
//		qErrorLbl.setBorder(new LineBorder(Color.DARK));
		qErrorLbl.setForeground(Color.LIGHT_GRAY);
		qErrorLbl.setFont(new Font("Cambria", Font.BOLD, 22));
		
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
		add(screen, BorderLayout.WEST);
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1030,825));
		mainPanel.setLayout(cl);
		mainPanel.setPreferredSize(new Dimension(200, 200));
		screen.add(mainPanel);
		
		// Create the main Panel
		JPanel questionListPanel = new JPanel();
		questionListPanel.setLayout(new BoxLayout(questionListPanel, BoxLayout.Y_AXIS));
		questionListPanel.setPreferredSize(new Dimension(1030,825));
	  Border mainBorder = new EmptyBorder(10, 20, 50, 20);
		questionListPanel.setBorder(mainBorder);
		mainPanel.add(questionListPanel, "questionListPanel");
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
		for (int pt = 0; pt < 5; ++pt) {
			for (int cat = 0; cat < 5; ++cat) {
				questionPanel.add(qBtns[cat][pt]);
			}
		}
		
		// Side panel
		JPanel sidePanel = new JPanel(new GridLayout(2, 1));
		sidePanel.setPreferredSize(new Dimension(screen.getWidth()/6, screen.getHeight()));
//		screen.add(sidePanel);
		add(sidePanel, BorderLayout.CENTER);
		
		JPanel scorePanel = new JPanel(new GridLayout(4, 2));
		scorePanel.setBackground(Color.DARK_GRAY);
		sidePanel.add(scorePanel);
		
		// add all teams to Score Panel
		for (int i = 0; i < teamLbl.size(); i++) {
			scorePanel.add(teamLbl.get(i).getItem1());
			scorePanel.add(teamLbl.get(i).getItem2());
		}
		// conditional of number of teams
		
		JPanel gameProgressPanel = new JPanel();
		gameProgressPanel.setLayout(new BoxLayout(gameProgressPanel, BoxLayout.Y_AXIS));
		gameProgressPanel.setBackground(Color.YELLOW);
		sidePanel.add(gameProgressPanel);
		
		// Create the Game Progress panel		

		gameProgressPanel.add(progressTitle);
		
//		// Create the team prompt
		gameProgressPanel.add(teamPrompt);
		
		createQuestionPanel();
		
		// Create question panel
		
//		questionListPanel.setVisible(false);
		
//		JPanel testpanel = new JPanel();
//		testpanel.setPreferredSize(new Dimension(1030,825));
//		testpanel.setBackground(Color.RED);
//		mainPanel.add(testpanel, "2");
//		
//		cl.show(mainPanel, "2");
//		cl.add(testpanel, "2");
//		cl.show(testpanel, "2");
		
//		CardLayout cl = (CardLayout)(mainPanel.getLayout());
//		cl.show(questionListPanel, "2");
		
	mainPanel.add(answerQuestionPanel, "answerQuestionPanel");
	mainPanel.add(finalJeopardyPanel, "finalJeopardyPanel");
//	frame.add(test);
////	finalJeopardyPanel.setVisible(true);
//	cl.show(test, "1");		

	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		////////////////////
		// Menu Listeners //
		////////////////////
		restartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset Game Board
				GamePlay.InitGame();
				if (FileChooser.quickPlay.isSelected())
					GamePlay.qsAnswered = 20;
				
				// Create Question buttons
				for (int pt = 0; pt < 5; ++pt) {
					for (int cat = 0; cat < 5; ++cat) {
						qBtns[cat][pt].setEnabled(true);
					}
				}
			}
		});
		
		chooseNewGameFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Jeopardy.fileChooser.inputFile = null;
//				Jeopardy.fileChooser.fileNameLbl.setText("");
//				Jeopardy.fileChooser.inputFile = null;
//				Jeopardy.fileChooser.teamSelectSlider.setValue(0);
//				Jeopardy.fileChooser.setVisible(true);
				Jeopardy.fileChooser = null;
				Jeopardy.fileChooser = new FileChooser();
				Jeopardy.GameBoard.setVisible(false);
				Jeopardy.GameBoard = null;
				GamePlay.resetVariables();
			}
		});
		
		///////////////////////////////
		// Select Question Listeners //
		///////////////////////////////
		class questionSelectedListener implements ActionListener {
			int cat, ptValue;
			
			public questionSelectedListener(int cat, int ptValue) {
				this.cat = cat;
				this.ptValue = ptValue;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePlay.currQuestion = GamePlay.getQuestion(GamePlay.Categories[cat],  GamePlay.Points[ptValue]);
				// Update info on questionPanel
//				GamePlay.displayAllQuestions();
//				System.out.println(GamePlay.Categories[cat] + " | " + GamePlay.Points[ptValue]);
//				System.out.println(GamePlay.currQuestion.getQuestion() + " | " + GamePlay.currQuestion.getCategory() + " | " + GamePlay.currQuestion.getPointValue());
				
				qTeamLbl.setText(GamePlay.Teams.get(GamePlay.currTeam).getName());
				qCatLbl.setText(GamePlay.currQuestion.getCategory());
				qPtValueLbl.setText("$" + GamePlay.currQuestion.getPointValue());
				qQuestionArea.setText(GamePlay.currQuestion.getQuestion());
				qErrorLbl.setText("");
				qAnswerArea.setText("");
				showPanel("answerQuestionPanel"); // show question panel
				((JButton)e.getSource()).setEnabled(false);
			}
		}
		// Create Question buttons
		for (int pt = 0; pt < 5; ++pt) {
			for (int cat= 0; cat < 5; ++cat) {
				qBtns[cat][pt].addActionListener(new questionSelectedListener(cat, pt));
			}
		}
		
		// Final Jeopardy Sliders
		// Custom Listener that changes the label based on the slider value
		class updateSelectedPtLabelListener implements ChangeListener {
			JSlider slider; JLabel label;
			
			public updateSelectedPtLabelListener(JSlider slider, JLabel label) {
				this.slider = slider;
				this.label = label;
			}

			@Override
			public void stateChanged(ChangeEvent e) {
				label.setText(Integer.toString(slider.getValue()));
			}
		}
		
		for (int i = 0; i < GamePlay.Teams.size(); ++i) {
			ptSelectSlider[i].addChangeListener(new updateSelectedPtLabelListener(ptSelectSlider[i], teamPtValLbl[i]));
		}
		
		// Answer Button
		qSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer = qAnswerArea.getText().trim();
				String [] ansBeginning = answer.split("\\s+");
				System.out.println(GamePlay.currTeam);
				// Check if the user's answer begins with the proper format
				if ( Helpers.elementExists(GamePlay.Answers[0], ansBeginning[0]) && Helpers.elementExists(GamePlay.Answers[1], ansBeginning[1]) ) {
					if (GamePlay.checkAnswer(ansBeginning, GamePlay.currQuestion.getAnswer())) { // if the answer is correct add points to the user
						GamePlay.currQuestion.setAnswered();
						teamPrompt.append(GamePlay.Teams.get(GamePlay.currTeam).getName() + ", you got the answer right! $" + GamePlay.currQuestion.getPointValue() + " will be added to your score.\n");
						GamePlay.Teams.get(GamePlay.currTeam).addPoints(GamePlay.currQuestion.getPointValue());
						teamLbl.get(GamePlay.currTeam).getItem2().setText("$" + GamePlay.Teams.get(GamePlay.currTeam).getPoints());
						GamePlay.updateCurrentTeam();
//						GamePlay.numTries = 0;
					} else { // if not correct subtract points
						GamePlay.currQuestion.setAnswered();
						teamPrompt.append(GamePlay.Teams.get(GamePlay.currTeam).getName() + ", that is the wrong answer! $" + GamePlay.currQuestion.getPointValue() + " will be subtracted from your score.\n");
						GamePlay.Teams.get(GamePlay.currTeam).subPoints(GamePlay.currQuestion.getPointValue());
						teamLbl.get(GamePlay.currTeam).getItem2().setText("$" + GamePlay.Teams.get(GamePlay.currTeam).getPoints());
						GamePlay.updateCurrentTeam();
//						GamePlay.numTries = 0;
					}
					if (GamePlay.qsAnswered == 25)
						showPanel("finalJeopardyPanel");
					else showPanel("questionListPanel");
					GamePlay.qsAnswered++;
					teamPrompt.append("Now it's " + GamePlay.Teams.get(GamePlay.currTeam).getName() + "'s Please Choose a question.\n");
					GamePlay.numTries = 0;
				} else { // If it doesn't give the user a second chance
					qErrorLbl.setText("The answer needs to be posed as a question. Try again!");
					qAnswerArea.setText("");
					++GamePlay.numTries;
				}
				// check if number of tries exceeded
				if (GamePlay.numTries == 2) {
					teamPrompt.append(GamePlay.Teams.get(GamePlay.currTeam).getName() + ", that is the wrong answer! $" + GamePlay.currQuestion.getPointValue() + " will be subtracted from your score.\n");
					GamePlay.Teams.get(GamePlay.currTeam).subPoints(GamePlay.currQuestion.getPointValue());
					System.out.println("Qans:" + GamePlay.qsAnswered);
					if (GamePlay.qsAnswered == 25)
						showPanel("finalJeopardyPanel");
					else showPanel("questionListPanel");
					GamePlay.qsAnswered++;
					teamLbl.get(GamePlay.currTeam).getItem2().setText("$" + GamePlay.Teams.get(GamePlay.currTeam).getPoints());
					GamePlay.updateCurrentTeam();
					teamPrompt.append("Now it's " + GamePlay.Teams.get(GamePlay.currTeam).getName() + "'s Please Choose a question.\n");
					GamePlay.numTries = 0;
				}
//				teamLbl.get(GamePlay.currTeam).getItem2().setText("$" + GamePlay.Teams.get(GamePlay.currTeam).getPoints());
//				teamPrompt.append("Now it's " + GamePlay.Teams.get(GamePlay.currTeam).getName() + "'s Please Choose a question.\n");
				if (GamePlay.qsAnswered == 25)
					showPanel("finalJeopardyPanel");
				System.out.println("QsAns: " + GamePlay.qsAnswered + "tries: " + GamePlay.numTries);
			}
		});
		
		///////////////////////////////
		// Final Jeoppardy Listeners //
		///////////////////////////////
		class setBetListener implements ActionListener {
			int teamID;
			
			public setBetListener(int teamID) {
				this.teamID = teamID;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePlay.FJBets[teamID] = ptSelectSlider[teamID].getValue();
				((JButton)e.getSource()).setEnabled(false);
			}
		}
		
		for (int i = 0; i < GamePlay.Teams.size(); ++i) {
			setBetBtn[i].addActionListener(new setBetListener(i));
		}
	}
	
	private void createQuestionPanel() {		
		answerQuestionPanel = new JPanel();
		answerQuestionPanel.setLayout(new BoxLayout(answerQuestionPanel, BoxLayout.Y_AXIS));
		answerQuestionPanel.setBackground(Color.DARK_GRAY);
		
		JPanel qTitlePanel = new JPanel(new GridLayout(1, 3));
		qTitlePanel.setPreferredSize(new Dimension(answerQuestionPanel.getWidth(), 100));
		qTitlePanel.setBackground(Color.BLUE);
		qTitlePanel.add(qTeamLbl);
		qTitlePanel.add(qCatLbl);
		qTitlePanel.add(qPtValueLbl);
		answerQuestionPanel.add(qTitlePanel);
		
		JPanel errorPanel = new JPanel(new GridBagLayout());
		errorPanel.setPreferredSize(new Dimension(answerQuestionPanel.getWidth(), 150));
		errorPanel.setBackground(Color.DARK_GRAY);
		errorPanel.add(qErrorLbl);
		answerQuestionPanel.add(errorPanel);
		
		JPanel qPanel = new JPanel();
//		qPanel.setPreferredSize(new Dimension(questionPanel.getWidth(), 300));
	  Border qBorder = new EmptyBorder(10, 20, 50, 20);
	  qPanel.setBorder(qBorder);
	  answerQuestionPanel.add(qPanel);
//	  qQuestionArea.setSize(new Dimension(800, 700));
//	  qQuestionArea.setText("");
	  qPanel.add(qQuestionArea);
		
	  JPanel answerPanel = new JPanel(new GridBagLayout());
	  GridBagConstraints gbc = new GridBagConstraints();
	  answerPanel.setBackground(Color.DARK_GRAY);
	  answerPanel.setPreferredSize(new Dimension(answerQuestionPanel.getWidth(), 100));
//	  answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.X_AXIS));
	  answerQuestionPanel.add(answerPanel);
	  gbc.insets = new Insets(0,0,0,10);
	  answerPanel.add(qAnswerArea, gbc);
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
		FJTitlePanel.setBorder(new EmptyBorder(10, 20, 20, 20));
		FJTitlePanel.setBackground(Color.DARK_GRAY);
		JLabel FJTitle = new JLabel("Final Jeopardy Round", SwingConstants.CENTER);
		FJTitle.setPreferredSize(new Dimension(950, 60));
		Helpers.styleComponent(FJTitle, Color.BLUE, Color.BLUE, 40);
		FJTitle.setForeground(Color.LIGHT_GRAY);
	  FJTitlePanel.add(FJTitle);
	  
	  finalJeopardyPanel.add(FJTitlePanel);
	  
//	  JPanel BetsPanel
	  for (int i = 0; i < GamePlay.Teams.size(); ++i) {
	  	finalPtChooser[i] = new JPanel(); finalPtChooser[i].setLayout(new BoxLayout(finalPtChooser[i], BoxLayout.X_AXIS));
	  	finalPtChooser[i].setBackground(Color.DARK_GRAY);
	  	
	  	teamPtLbl[i] = new JLabel(GamePlay.Teams.get(i).getName() + "'s bet:", SwingConstants.CENTER);
	  	Helpers.styleComponent(teamPtLbl[i], Color.DARK_GRAY, Color.DARK_GRAY, 15);
	  	teamPtLbl[i].setForeground(Color.LIGHT_GRAY);
	  	teamPtLbl[i].setPreferredSize(new Dimension(200, 50));
	  	finalPtChooser[i].add(teamPtLbl[i]);
	  	
	  	ptSelectSlider[i] = new JSlider();
	  	// do a check for negative value
	  	ptSelectSlider[i].setMaximum(ptvals[i]);
	  	ptSelectSlider[i].setMinimum(0);
	  	ptSelectSlider[i].setValue(1);
			ptSelectSlider[i].setPaintLabels(true);
			ptSelectSlider[i].setPaintTicks(true);
			int spacing = 0;
			if (ptvals[i] <= 100) spacing = 10;
			else if (ptvals[i] <= 1000) spacing = 100;
			else spacing = 200;
			ptSelectSlider[i].setMajorTickSpacing(spacing);
//			ptSelectSlider[i].setMinorTickSpacing(1);
			ptSelectSlider[i].setFont(new Font("Cambria", Font.BOLD, 15));
			ptSelectSlider[i].setBackground(Color.DARK_GRAY);
			ptSelectSlider[i].setForeground(Color.WHITE);
			ptSelectSlider[i].setPreferredSize(new Dimension(700, 70));
			// Create Border
		  Border line = new LineBorder(Color.DARK_GRAY);
		  Border margin = new EmptyBorder(5, 15, 5, 15);
		  Border compound = new CompoundBorder(line, margin);
			ptSelectSlider[i].setBorder(compound);
			finalPtChooser[i].add(ptSelectSlider[i]);
			
			teamPtValLbl[i] = new JLabel("$" + Integer.toString(ptSelectSlider[i].getValue()), SwingConstants.CENTER);
			Helpers.styleComponent(teamPtValLbl[i], Color.DARK_GRAY, Color.DARK_GRAY, 15);
			teamPtValLbl[i].setForeground(Color.WHITE);
			teamPtValLbl[i].setPreferredSize(new Dimension(50, 50));
			finalPtChooser[i].add(teamPtValLbl[i]);
			
			setBetBtn[i] = new JButton("Set Bet");
	    // Make it look flat
			setBetBtn[i].setForeground(Color.BLACK);
			setBetBtn[i].setBackground(Color.WHITE);
	    setBetBtn[i].setBorder(compound);
	    setBetBtn[i].setPreferredSize(new Dimension(80, 80));
			finalPtChooser[i].add(setBetBtn[i]);

			finalPtChooser[i].setBorder(new EmptyBorder(30, 0, 30, 0));
	  	finalJeopardyPanel.add(finalPtChooser[i]);
	  }
	  
	  
	  FJQArea = new JTextArea("And the quesiton is...");
	  FJQArea.setFont(new Font("Cambria", Font.BOLD, 22));
	  FJQArea.setPreferredSize(new Dimension(950, 65));
	  FJQArea.setEditable(false);
	  FJQArea.setCursor(null);
	  FJQArea.setFocusable(false);
	  FJQArea.setLineWrap(true);
	  FJQArea.setBackground(Color.BLUE);
	  FJQArea.setForeground(Color.WHITE);
//	  FJQArea.setsty
	  
	  JPanel FJQAreaPanel = new JPanel();
	  FJQAreaPanel.setBackground(Color.DARK_GRAY);
	  FJQAreaPanel.setBorder(new EmptyBorder(0, 20, 50, 20));
	  FJQAreaPanel.add(FJQArea);	  
	  finalJeopardyPanel.add(FJQAreaPanel);
//	  SwingConstants.CENTER
	  
	  JPanel [] FJanswerPanel = new JPanel[4]; 
	  for (int i = 0; i < GamePlay.Teams.size(); ++i) {
	  	FJanswerPanel[i] = new JPanel(); FJanswerPanel[i].setLayout(new BoxLayout(FJanswerPanel[i], BoxLayout.X_AXIS));
//	  	FJanswerPanel[i].setPreferredSize(new Dimension(300, 100));
	  	FJAArea[i] = new JTextField();
	  	FJAArea[i].setText(GamePlay.Teams.get(i).getName() + ", enter your answer.");
	  	FJAArea[i].setFont(new Font("Cambria", Font.BOLD, 15));
	  	FJanswerPanel[i].add(FJAArea[i]);
	  	
	  	JLabel pad = new JLabel("   ");
	  	FJanswerPanel[i].add(pad);
	  	
	  	FJABtn[i] = new JButton("Submit Answer");
	  	FJABtn[i].setForeground(Color.BLACK);
	  	FJABtn[i].setBackground(Color.WHITE);
	  	FJABtn[i].setPreferredSize(new Dimension(150, 80));
	  	FJABtn[i].setFont(new Font("Cambria", Font.BOLD, 15));
	  	FJanswerPanel[i].add(FJABtn[i]);
	  	
	  	
	  	FJanswerPanel[i].setBorder(new EmptyBorder(15, 30, 15, 30));
	  	FJanswerPanel[i].setBackground(Color.DARK_GRAY);
	  	
	  }
	  // add all panels to the layout
	  JPanel FJAPanel = new JPanel(new GridLayout(2,4));
	  FJAPanel.setBackground(Color.DARK_GRAY);
	  FJAPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
	  for (int i = 0; i < GamePlay.Teams.size(); ++i)
	  	FJAPanel.add(FJanswerPanel[i]);
	  finalJeopardyPanel.add(FJAPanel);
//		JTextField [] FJAArea = new JTextField[4];
//		JButton [] FJABtn = new JButton[4];

	  JPanel FJQPrompt = new JPanel();
	  FJQArea = new JTextArea();
	  
	  FJQPrompt.add(FJQArea);
	  
	  finalJeopardyPanel.add(FJQPrompt);
	}
	
	public static void showPanel(String panelName) {
		if (panelName.equals("questionListPanel") || panelName.equals("answerQuestionPanel") || panelName.equals("finalJeopardyPanel"))
			cl.show(mainPanel, panelName);
	}
	
	public static void main(String [] args) {
		GameBoardUI menubar = new GameBoardUI();
		menubar.setVisible(true);
	}

}
