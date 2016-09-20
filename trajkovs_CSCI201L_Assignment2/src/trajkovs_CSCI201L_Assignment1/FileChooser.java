package trajkovs_CSCI201L_Assignment1;

import javax.swing.JFrame;
import java.awt.BorderLayout;
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
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.applet.Applet;

public class FileChooser extends JFrame {
	// GUI Elements
	private JLabel welcomeLbl, promptLbl, fileChooserLbl, fileNameLbl, teamPromptLbl;
	private JLabel [] teamLbls = new JLabel[4];
	private JTextField [] teamTxtBoxes = new JTextField[4];
	private JButton chooseFileBtn, startBtn, clearBtn, exitBtn;
	private JSlider teamSelectSlider;
	private JFileChooser chooseFile;
	static JDialog popup;
	
	// Create Border
  Border line = new LineBorder(Color.DARK_GRAY);
  Border margin = new EmptyBorder(5, 15, 5, 15);
  Border compound = new CompoundBorder(line, margin);
	
  // Variables
  File inputFile;
  
	public FileChooser() {
		super("Welcome to Jeopardy");
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents() {
		
		// Create welcome Label
		welcomeLbl = new JLabel("Welcome to Jeopardy");
		welcomeLbl.setFont(new Font("Cambria", Font.BOLD, 25));
		welcomeLbl.setForeground(Color.WHITE);
		welcomeLbl.setHorizontalAlignment(JLabel.CENTER);
		
		// Create prompt Label
		promptLbl = new JLabel("Choose the game file, number of teams, and team names before starting the game");
		promptLbl.setFont(new Font("Cambria", Font.BOLD, 15));
		promptLbl.setForeground(Color.WHITE);
		promptLbl.setHorizontalAlignment(JLabel.CENTER);
		
		//////////////////
		// File Chooser //
		//////////////////
		fileChooserLbl = new JLabel("Please choose a game file.");
		fileChooserLbl.setFont(new Font("Cambria", Font.BOLD, 15));
		fileChooserLbl.setForeground(Color.WHITE);
		
		chooseFileBtn = new JButton("Choose File");
		chooseFileBtn.setFont(new Font("Cambria", Font.BOLD, 15));
		chooseFileBtn.setForeground(Color.WHITE);
    // Make it look flat
    chooseFileBtn.setForeground(Color.WHITE);
    chooseFileBtn.setBackground(Color.DARK_GRAY);
    chooseFileBtn.setBorder(compound);
    
		fileNameLbl = new JLabel("");
		fileNameLbl.setFont(new Font("Cambria", Font.BOLD, 15));
		fileNameLbl.setForeground(Color.BLACK);
		
    //////////////////
    // Team Select //
    /////////////////
		teamPromptLbl = new JLabel("Please choose the number of teams that will be playing on the slider below.");
		teamPromptLbl.setFont(new Font("Cambria", Font.BOLD, 15));
		teamPromptLbl.setForeground(Color.BLACK);
		
		createTeamSlider();
		
		///////////////
		// Team List //
		///////////////
		for (int teamLbl = 0; teamLbl < 4; ++teamLbl) {
			teamLbls[teamLbl] = new JLabel("Please name Team " + (teamLbl+1));
			teamLbls[teamLbl].setFont(new Font("Cambria", Font.BOLD, 17));
			teamLbls[teamLbl].setForeground(Color.WHITE);
			teamLbls[teamLbl].setBackground(Color.DARK_GRAY);
			teamLbls[teamLbl].setOpaque(true);
			teamLbls[teamLbl].setBorder(compound);
		}
		
		for (int team = 0; team < 4; ++team) {
			teamTxtBoxes[team] = new JTextField("");
			teamTxtBoxes[team].setFont(new Font("Cambria", Font.BOLD, 17));
			teamTxtBoxes[team].setForeground(Color.WHITE);
			teamTxtBoxes[team].setBackground(Color.GRAY);
			teamTxtBoxes[team].setOpaque(true);
			teamTxtBoxes[team].setBorder(compound);
			teamTxtBoxes[team].setPreferredSize(new Dimension(teamLbls[0].getPreferredSize()));
		}

		/////////////////////
		// Nav bar buttons //
		/////////////////////
		startBtn = new JButton("Start Jeopardy");
		startBtn.setFont(new Font("Cambria", Font.BOLD, 17));
		startBtn.setForeground(Color.WHITE);
		startBtn.setBackground(Color.DARK_GRAY);
		startBtn.setBorder(compound);
		startBtn.setPreferredSize(new Dimension(150, 35));
		
		clearBtn = new JButton("Clear Choices");
		clearBtn.setFont(new Font("Cambria", Font.BOLD, 17));
		clearBtn.setForeground(Color.WHITE);
		clearBtn.setBackground(Color.DARK_GRAY);
		clearBtn.setBorder(compound);
		clearBtn.setPreferredSize(new Dimension(150, 35));
		
		exitBtn = new JButton("Exit");
		exitBtn.setFont(new Font("Cambria", Font.BOLD, 17));
		exitBtn.setForeground(Color.WHITE);
		exitBtn.setBackground(Color.DARK_GRAY);
		exitBtn.setBorder(compound);
		exitBtn.setPreferredSize(new Dimension(150, 35));
		
	}
	
	private void createGUI() {
		setSize(1000, 600);
		setLocation(100, 100);
//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(true);
		
		// Create Grid Bag Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		
		////////////////////////////////
		// Top part of Welcome Screen //
		////////////////////////////////
		
		// Create TOP panel and set color
		JPanel welcomePanel = new JPanel(new GridLayout(2, 0));
		welcomePanel.setBackground(Color.blue);
		welcomePanel.add(welcomeLbl);
		welcomePanel.add(promptLbl);
		add(welcomePanel, BorderLayout.NORTH);
		
		//////////////////
		// CENTER PANEL //
		//////////////////
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//		mainPanel.setBackground(Color.CYAN);
		add(mainPanel, BorderLayout.CENTER);
		
		//////////////////////////
		// File / Team chooser //
		/////////////////////////
		JPanel filePanel = new JPanel();
//		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		filePanel.setBackground(new Color(131, 205, 251));
		FlowLayout filePanelLayout = new FlowLayout();
		filePanelLayout.setHgap(5);
		filePanel.setLayout(filePanelLayout);
//		filePanel.setHgap(5);
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
		
		gbc.gridx = 1; gbc.gridy = 1;
		teamListPanel.add(teamLbls[0], gbc);
		gbc.gridx = 1; gbc.gridy = 2;
		teamListPanel.add(teamTxtBoxes[0], gbc);
		
		gbc.gridx = 2; gbc.gridy = 1;
		teamListPanel.add(teamLbls[2], gbc);
		gbc.gridx = 2; gbc.gridy = 2;
		teamListPanel.add(teamTxtBoxes[2], gbc);
		
		gbc.gridx = 1; gbc.gridy = 4;
		teamListPanel.add(teamLbls[1], gbc);
		gbc.gridx = 1; gbc.gridy = 5;
		teamListPanel.add(teamTxtBoxes[1], gbc);
		
		gbc.gridx = 2; gbc.gridy = 4;
		teamListPanel.add(teamLbls[3], gbc);
		gbc.gridx = 2; gbc.gridy = 5;
		teamListPanel.add(teamTxtBoxes[3], gbc);		
		
		////////////////////
		// Navigation Bar //
		////////////////////
		JPanel navigationPanel = new JPanel();
		navigationPanel.setBackground(new Color(131, 205, 251));
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
//	    chooseFile.setDialogTitle("Open");
//	    chooseFile.setFileSelectionMode();
		    
		    int returnVal = chooseFile.showOpenDialog(null);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	inputFile = chooseFile.getSelectedFile();
		    	System.out.println(inputFile.getName());
		    }
			}
		});
		
		
		// Select teams
		teamSelectSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = teamSelectSlider.getValue();
				
				// Hide all of the teams
				for(int i = 0; i < value; ++i) {
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
				Helpers.ParseFile(inputFile);
				GenerateTeams();
			}
			
			private void GenerateTeams() {
				for (int i = 1; i <= GamePlay.numTeams; ++i) {
					String teamName = teamTxtBoxes[i-1].getText().trim();
					if (teamName.isEmpty())
						teamName = "Team " + i;
					GamePlay.Teams.add(new Team(teamName));
				}
				
//////////////// DEBUG
				for (Team t : GamePlay.Teams)
					System.out.println(t.getName());
				GamePlay.displayAllQuestions();
			}
		});
		
		clearBtn.addActionListener((ActionEvent event) -> {
      displayPopup("TEST");
		});
		
		exitBtn.addActionListener((ActionEvent event) -> {
      System.exit(0);
		});
	}
	
	private void createTeamSlider() {
		teamSelectSlider = new JSlider(JSlider.HORIZONTAL);
		teamSelectSlider.setMaximum(4);
		teamSelectSlider.setMinimum(1);
		teamSelectSlider.setValue(2);
		teamSelectSlider.setPaintLabels(true);
		teamSelectSlider.setPaintTicks(true);
		teamSelectSlider.setMajorTickSpacing(1);
		teamSelectSlider.setMinorTickSpacing(1);
		teamSelectSlider.setFont(new Font("Cambria", Font.BOLD, 15));
		teamSelectSlider.setBackground(Color.DARK_GRAY);
		teamSelectSlider.setForeground(Color.WHITE);
		teamSelectSlider.setBorder(compound);
	}
	
	private void displayTeam(int i) {
		
	}
	
	static void displayPopup(String text) {
		popup = new JDialog();
		popup.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
//		popup.setSize(400, 200);
//		JLabel info = new JLabel(text);
//		
//		popup.add(info);
//		popup.setVisible(true);
		
		JOptionPane.showMessageDialog(popup, text, "Parsing Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main (String [] args) {
		FileChooser fc = new FileChooser();
		fc.setVisible(true);
	}
}
