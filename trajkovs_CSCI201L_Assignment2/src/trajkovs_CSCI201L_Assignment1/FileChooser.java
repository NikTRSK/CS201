package trajkovs_CSCI201L_Assignment1;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.applet.Applet;

public class FileChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	// GUI Elements
	JLabel welcomeLbl, promptLbl, fileChooserLbl, fileNameLbl, teamPromptLbl;
	private JLabel [] teamLbls = new JLabel[4];
	private JTextField [] teamTxtBoxes = new JTextField[4];
	private JButton chooseFileBtn, startBtn, clearBtn, exitBtn;
	private JSlider teamSelectSlider;
	private JFileChooser chooseFile;
	static protected JCheckBox quickPlay;
	static JDialog popup;
	File inputFile;
	
	// Create Border
  Border line = new LineBorder(Color.DARK_GRAY);
  Border margin = new EmptyBorder(5, 15, 5, 15);
  Border compound = new CompoundBorder(line, margin);
	
	public FileChooser() {
		super("Welcome to Jeopardy");
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		
		// Create welcome Label
		welcomeLbl = new JLabel("Welcome to Jeopardy!");
		welcomeLbl.setFont(new Font("Cambria", Font.BOLD, 45));
		welcomeLbl.setForeground(Color.WHITE);
		welcomeLbl.setHorizontalAlignment(JLabel.CENTER);
		welcomeLbl.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		// Create Quick Play checkbox
		quickPlay = new JCheckBox("Quick Play");
		quickPlay.setBackground(Color.BLUE);
		quickPlay.setForeground(Color.WHITE);
		quickPlay.setFont(new Font("Cambria", Font.BOLD, 15));
		
		// Create prompt Label
		promptLbl = new JLabel("Choose the game file, number of teams, and team names before starting the game");
		promptLbl.setFont(new Font("Cambria", Font.BOLD, 18));
		promptLbl.setForeground(Color.WHITE);
		promptLbl.setHorizontalAlignment(JLabel.CENTER);
		
		//////////////////
		// File Chooser //
		//////////////////
		fileChooserLbl = new JLabel("Please choose a game file.");
		fileChooserLbl.setFont(new Font("Cambria", Font.BOLD, 17));
		fileChooserLbl.setForeground(Color.WHITE);
		
		chooseFileBtn = new JButton("Choose File");
		Helpers.styleComponentFlat(chooseFileBtn, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, 17, true);
    
		fileNameLbl = new JLabel("", SwingConstants.CENTER);
		fileNameLbl.setPreferredSize(new Dimension(200, 20));
		fileNameLbl.setFont(new Font("Cambria", Font.BOLD, 17));
		fileNameLbl.setForeground(Color.WHITE);
		
    //////////////////
    // Team Select //
    /////////////////
		teamPromptLbl = new JLabel("Please choose the number of teams that will be playing on the slider below.");
		teamPromptLbl.setFont(new Font("Cambria", Font.BOLD, 17));
		teamPromptLbl.setForeground(Color.WHITE);
		teamPromptLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		teamPromptLbl.setBorder(new EmptyBorder(0,0,5,0));
		
		createTeamSlider();
		
		///////////////
		// Team List //
		///////////////
		for (int teamLbl = 0; teamLbl < 4; ++teamLbl) {
			teamLbls[teamLbl] = new JLabel("Please name Team " + (teamLbl+1));
			Helpers.styleComponentFlat(teamLbls[teamLbl], Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, 22, true);
			teamLbls[teamLbl].setVisible(false);
		}
		
		for (int team = 0; team < 4; ++team) {
			teamTxtBoxes[team] = new JTextField("");
			Helpers.styleComponentFlat(teamTxtBoxes[team], Color.WHITE, Color.GRAY, Color.DARK_GRAY, 22, true);
			teamTxtBoxes[team].setPreferredSize(new Dimension(teamLbls[0].getPreferredSize()));
			teamTxtBoxes[team].setVisible(false);
		}

		/////////////////////
		// Nav bar buttons //
		/////////////////////
		startBtn = new JButton("Start Jeopardy");
		Helpers.styleComponentFlat(startBtn, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, 22, true);
		startBtn.setPreferredSize(new Dimension(200, 35));
//		startBtn.setEnabled(false);
		
		clearBtn = new JButton("Clear Choices");
		Helpers.styleComponentFlat(clearBtn, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, 22, true);
		clearBtn.setPreferredSize(new Dimension(200, 35));
		
		exitBtn = new JButton("Exit");
		Helpers.styleComponentFlat(exitBtn, Color.WHITE, Color.DARK_GRAY, Color.DARK_GRAY, 22, true);
		exitBtn.setPreferredSize(new Dimension(200, 35));
		
	}
	
	private void createGUI() {
		setSize(800, 825);
		setLocation(100, 100);
		setVisible(true);
		
		// Create Grid Bag Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		
		////////////////////////////////
		// Top part of Welcome Screen //
		////////////////////////////////
		JPanel welcomePanelTop = new JPanel(); welcomePanelTop.setLayout(new BoxLayout(welcomePanelTop, BoxLayout.X_AXIS));
		welcomePanelTop.setBackground(Color.BLUE);
		JPanel welcomeLblPanel = new JPanel();
		welcomeLbl.setBorder(new EmptyBorder(0,100,0,0));
		welcomeLblPanel.add(welcomeLbl); welcomeLblPanel.setBackground(Color.BLUE);
		welcomePanelTop.add(welcomeLblPanel);
		welcomePanelTop.add(quickPlay);
		
		JPanel welcomePanel = new JPanel(); welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
		welcomePanel.setBackground(Color.BLUE);
		welcomePanel.add(welcomePanelTop);
		promptLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		promptLbl.setBorder(new EmptyBorder(20,0,0,0));
		welcomePanel.add(promptLbl);
		
		add(welcomePanel, BorderLayout.NORTH);
		
		//////////////////
		// CENTER PANEL //
		//////////////////
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel, BorderLayout.CENTER);
		
		//////////////////////////
		// File / Team chooser //
		/////////////////////////
		JPanel filePanel = new JPanel();
		filePanel.setBackground(new Color(131, 205, 251));
		FlowLayout filePanelLayout = new FlowLayout();
		filePanelLayout.setHgap(5);
		filePanel.setLayout(filePanelLayout);
		mainPanel.add(filePanel);
		filePanel.add(fileChooserLbl);
		filePanel.add(chooseFileBtn);
		filePanel.add(fileNameLbl);
		
		// Select number of teams
		JPanel selectTeamPanel = new JPanel();
		selectTeamPanel.setLayout(new BoxLayout(selectTeamPanel, BoxLayout.Y_AXIS));
		selectTeamPanel.setBackground(new Color(131, 205, 251));
		mainPanel.add(selectTeamPanel);
		selectTeamPanel.add(teamPromptLbl);
		selectTeamPanel.add(teamSelectSlider);
		
		// Team list
		JPanel teamListPanel = new JPanel();
		teamListPanel.setLayout(new GridBagLayout());
		teamListPanel.setBackground(new Color(131, 205, 251));
		mainPanel.add(teamListPanel);
		gbc.weightx = 1;
		
//		JPanel topTeamPanel
		gbc.gridx = 1; gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 2, 0);
		teamListPanel.add(teamLbls[0], gbc);
		gbc.gridx = 1; gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 25, 0);
		teamListPanel.add(teamTxtBoxes[0], gbc);
		
		gbc.gridx = 1; gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 2, 0);
		teamListPanel.add(teamLbls[1], gbc);
		gbc.gridx = 1; gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 25, 0);
		teamListPanel.add(teamTxtBoxes[1], gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 2, 0);
		teamListPanel.add(teamLbls[2], gbc);
		gbc.gridx = 2; gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 25, 0);
		teamListPanel.add(teamTxtBoxes[2], gbc);
		
		gbc.gridx = 2; gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 2, 0);
		teamListPanel.add(teamLbls[3], gbc);
		gbc.gridx = 2; gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 25, 0);
		teamListPanel.add(teamTxtBoxes[3], gbc);		
		// Default state show only Team 1
		teamLbls[0].setVisible(true);
		teamTxtBoxes[0].setVisible(true);
		////////////////////
		// Navigation Bar //
		////////////////////
		JPanel navigationPanel = new JPanel();
		// cahnge this to just empty border
		navigationPanel.setBackground(new Color(131, 205, 251));
	  Border navLine = new LineBorder(new Color(131, 205, 251));
	  Border navMargin = new EmptyBorder(30, 0, 30, 0);
	  Border navCompound = new CompoundBorder(navLine, navMargin);
		navigationPanel.setBorder(navCompound);
		add(navigationPanel, BorderLayout.SOUTH);
		navigationPanel.add(startBtn);
		navigationPanel.add(clearBtn);
		navigationPanel.add(exitBtn);
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Choose input file
		chooseFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    chooseFile = new JFileChooser();
		    chooseFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
		    chooseFile.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
		    
		    int returnVal = chooseFile.showOpenDialog(null);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	inputFile = chooseFile.getSelectedFile();
					try {
//						GamePlay.numTeams = teamSelectSlider.getValue();
						Helpers.ParseFile(inputFile);
						fileNameLbl.setText(inputFile.getName());
//						GenerateTeams();
//						GamePlay.InitGame();
//						Jeopardy.createGameBoard();
					} catch (RuntimeException rte) {displayPopup(rte.getMessage());}
		    }
			}
		});
		
		
		// Select teams
		teamSelectSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = teamSelectSlider.getValue();
				
				// Hide all of the teams
				for(int i = 0; i < 4; ++i) {
					teamLbls[i].setVisible(false);
					teamTxtBoxes[i].setVisible(false);
				}
				// Show only the chosen teams
				for(int i = 0; i < value; ++i) {
					teamLbls[i].setVisible(true);
					teamTxtBoxes[i].setVisible(true);
				}
			}
		});
		
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			GamePlay.numTeams = teamSelectSlider.getValue();
			System.out.println("TEAMS: " + GamePlay.Teams.size());
			GenerateTeams();
			GamePlay.InitGame();
			// Check for Quick Play
			if (quickPlay.isSelected())
				GamePlay.qsAnswered = 20;
			Jeopardy.createGameBoard();
			}
			
			private void GenerateTeams() {
				for (int i = 1; i <= GamePlay.numTeams; ++i) {
					String teamName = teamTxtBoxes[i-1].getText().trim();
					if (teamName.isEmpty())
						teamName = "Team " + i;
					GamePlay.Teams.add(new Team(teamName));
				}
			}
		});
		
		clearBtn.addActionListener((ActionEvent event) -> {
      // Clear file
			inputFile = null;
			fileNameLbl.setText("");
			
			// Clear teams
			for (int team = 0; team < 4; ++team)
				teamTxtBoxes[team].setText("");
			teamSelectSlider.setValue(1);
			
		});
		
		exitBtn.addActionListener((ActionEvent event) -> {
      System.exit(0);
		});
	}
	
	private void createTeamSlider() {
		teamSelectSlider = new JSlider(JSlider.HORIZONTAL);
		teamSelectSlider.setMaximum(4);
		teamSelectSlider.setMinimum(1);
		teamSelectSlider.setValue(1);
		teamSelectSlider.setPaintLabels(true);
		teamSelectSlider.setPaintTicks(true);
		teamSelectSlider.setMajorTickSpacing(1);
		teamSelectSlider.setMinorTickSpacing(1);
		teamSelectSlider.setFont(new Font("Cambria", Font.BOLD, 15));
		teamSelectSlider.setBackground(Color.DARK_GRAY);
		teamSelectSlider.setForeground(Color.WHITE);
		teamSelectSlider.setBorder(compound);
	}
	
	static void displayPopup(String text) {
		popup = new JDialog();
		popup.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);		
		JOptionPane.showMessageDialog(popup, text, "Parsing Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main (String [] args) {
		FileChooser fc = new FileChooser();
		fc.setVisible(true);
	}
}
