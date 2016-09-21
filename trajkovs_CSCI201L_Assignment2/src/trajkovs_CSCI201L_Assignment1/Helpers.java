package trajkovs_CSCI201L_Assignment1;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class Helpers {
	public static boolean elementExists(String [] array, String value) {
		for (String toTest: array) {
			if ((toTest.toLowerCase()).equals(value.toLowerCase()))
				return true;
		}
		return false;
	}
	
	public static boolean elementExists(int [] array, int value) {
		for (int toTest: array) {
			if (toTest == value)
				return true;
		}
		return false;
	}
	
	// Reference of parseInt https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#parseInt(java.lang.String,%20int)
	public static boolean isNumber(String input) {
		// if ParseInt throws exception we know it's not a digit
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException nfe) {}
		return false;
	}
	
	public static boolean allNumbers(String [] vals) {
		for (String val : vals) {
			if (!isNumber(val.trim()))
				return false;
		}
		return true;
	}
	
	// additionally checks for empty string
	public static boolean hasDuplicates(String [] in) {
		for (int i = 0; i < in.length; ++i) {
			//check if empty string
			if (in[i].trim().isEmpty())
				return true;
			for (int j = i + 1; j < in.length; ++j) {
				if (in[i].trim().equals(in[j].trim()))
					return true;
			}
		}
		return false;
	}
	
	// Checks if there are any empty elements in an array
	public static boolean checkArrayElements(String [] words) {
		for (int i = 0; i < words.length; ++i) {
			//check if empty string
			if (words[i].trim().isEmpty())
				return true;
		}
		return false;
	}
	
	public static String [] appendToArray(String [] original, String [] toAdd) {
		String [] newArray = new String[original.length+toAdd.length-1];
		for (int i = 0; i < original.length; ++i)
			newArray[i] = original[i];
		
		for (int i = 0; i < toAdd.length; ++i) {
//			System.out.println(original.length-1+i);
			if (newArray[original.length-1 + i] == null || newArray[original.length-1 + i].isEmpty())
				newArray[original.length-1 + i] = toAdd[i];
			else {
				String temp = newArray[original.length-1 + i] + toAdd[i];
				newArray[original.length-1 + i] = temp;
			}
		}
		
//		System.out.println("appendToArray: " + Arrays.toString(newArray));
		return newArray;
	}
	
	public static boolean arrayEmpty(String [] array, int startIdx, int endIdx) {
		for (int i = startIdx; i <= endIdx; ++i) {
			if (array[i].isEmpty())
				return true;
		}
		return false;
	}
	
	public static boolean questionExists(String question, ArrayList<Question> qlist) {
		for (Question q : qlist) {
			if (q.getQuestion().toLowerCase().equals(question.toLowerCase()))
				return true;
		}
		return false;
	}

	public static void styleComponent(JComponent item, Color backgroundColor, Color borderColor, int fontSize) {
		item.setBackground(backgroundColor);
		item.setOpaque(true);
		item.setBorder(new LineBorder(borderColor));
		item.setFont(new Font("Cambria", Font.BOLD, fontSize));
	}
	
  public static void ParseFile(File input) {  
    String currLine; // holds the current line
    int questionCount = 0; // holds the number of questions loaded
    BufferedReader br = null;
    
    try {
      // Create the file stream
      br = new LineNumberReader(new FileReader(input));
      
      // Parse in the Categories
      currLine = br.readLine();
      String [] line = currLine.split("::", -1);
      // Check if there are duplicate categories
      if (line.length != 5 || Helpers.hasDuplicates(line)) {
        FileChooser.displayPopup("Wrong number of categories or duplicate categories");
        return;
      }
      else
        Jeopardy.setCategories(line);

      // Parse in the Point values for questions
      currLine = br.readLine().trim();
      line = currLine.split("::", -1);
      // Check if there are duplicate point values or if they are all numbers
      if (line.length != 5 || Helpers.hasDuplicates(line) || !Helpers.allNumbers(line)) {
        FileChooser.displayPopup("Wrong number of point values or duplicate point values");
        return;
      }
      else
        Jeopardy.setPoints(line);
    
      // Sort the point values (for convenience)
      Arrays.sort(GamePlay.Points);
      
      // Parse in the questions
      while ((currLine = br.readLine()) != null) {
        currLine.trim();
        // check if the line starts with ::
        if (!currLine.startsWith("::")) {
          FileChooser.displayPopup("Wrong question format");
          return;
        } else {
          line = currLine.split("::", -1);
          // lookahed to see if the quesiton is on 2 lines
          try { br.mark(10000); }
          catch (IOException ioe) { FileChooser.displayPopup(ioe.getMessage()); }
          currLine = br.readLine();
          // Handles the last line of file
          if (currLine != null) {
            currLine.trim();
            // if the question has 2nd line
            if (!currLine.startsWith("::")) {
              line = Helpers.appendToArray(line, currLine.split("::"));
            } else {  // if it's not go back  
              try { br.reset(); }
              catch (IOException ioe) { FileChooser.displayPopup(ioe.getMessage()); } 
            }
          }
          if (Helpers.arrayEmpty(line, 1, line.length-1)) {
            FileChooser.displayPopup("Wrong question format");
            return;           
          }
          // error checking for valid category and, values
          String cat = line[1].trim();
          String question = "", answer = "";
          int pts = 0;

          // Check for FINAL JEOPARDY Question
          if (cat.toLowerCase().equals("fj")) {
            if (GamePlay.FJQuestion != null) {
              FileChooser.displayPopup("Final Jeopardy question already exists! Exiting...");
              return;
            }
            if (line.length != 4) {
              FileChooser.displayPopup("Wrong format for Final Jeopardy question!");
              return;
            }
            GamePlay.FJQuestion = new Question(line[2].trim(), line[3].trim());
          } else {  // Regular questions
            if (Helpers.isNumber(line[2]))
              pts = Integer.parseInt(line[2]);
            else {
              FileChooser.displayPopup("Wrong question format1");
              return;
            }
            question = line[3].trim();
            answer = line[4].trim();
            
            // Create a new question and add it to the Question list
            if (Helpers.elementExists(GamePlay.Categories, cat) && Helpers.elementExists(GamePlay.Points, pts)) {
              // if first time adding key
              if (GamePlay.Questions.get(cat.toLowerCase()) == null)
                GamePlay.Questions.put(cat.toLowerCase(), new ArrayList<Question>());
              
              // checks for questions with duplicate point values
              if (Jeopardy.pointsExist(cat, pts)) {
                FileChooser.displayPopup("Duplicate point value!\nExiting...");
                return;
              }
              
              // Checks if the question exists. Only checks for same question not answer, since a question can't have 2 answers.
              // Only checks within the same category
              if (Helpers.questionExists(question, GamePlay.Questions.get(cat.toLowerCase()))) {
                FileChooser.displayPopup("Duplicate question!\nExiting...");
                return;
              }
              
              GamePlay.Questions.get(cat.toLowerCase()).add(new Question(cat.toLowerCase(), pts, question, answer));
              if (!cat.toLowerCase().equals("fj"))
                questionCount++;
            } else{
              FileChooser.displayPopup("Category or Points Value invalid");
              return;
            }
          }
        }
      }
    } catch (FileNotFoundException fnfe) { FileChooser.displayPopup("FileNotFoundException: " + fnfe.getMessage()); }
      catch (IOException ioe) { FileChooser.displayPopup("IOException: " + ioe.getMessage()); }
      finally {
      // Close the file stream
      if (br != null) {
        try {
          br.close();
        } catch (IOException ioe) {
          FileChooser.displayPopup(ioe.getMessage());
        }
      }
    }
    // See if everything was loaded correctly
    Jeopardy.checkValidGame(questionCount);
  }

}
