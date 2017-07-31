import java.io.Console;
import java.util.Scanner;

public class Crossword_Puzzle {

	public char[][] answer_matrix;
	public int number_of_words = 0;
	public Console console = System.console();
	public int xmax;
	public int ymax;
	public Clue[] clue_array = new Clue[1000];
	public char[][] guessed_matrix;
	
	
	public Crossword_Puzzle(int xmax, int ymax) {
		this.xmax = xmax;
		this.ymax = ymax;
		answer_matrix = new char[xmax][ymax];
		guessed_matrix = new char[xmax][ymax];
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				answer_matrix[i][j] = '0';
				guessed_matrix[i][j] = '0';
			}
		}
		
	}
	
	public void add_word(String clue,String word, int x_start, int y_start, boolean horizontal) {
		
		if (check_availability(word, x_start, y_start, horizontal) == false) {
			System.out.println("Oops something was wrong with the word you tried to input!");
			return;
		}
		
		int current_x = x_start;
		int current_y = y_start;
		
		if (horizontal == true) {
			for (int i = 0; i < word.length(); i++) {
				answer_matrix[current_x][current_y] = word.charAt(i);
				current_x ++;
			}
		}
		
		if (horizontal == false) {
			for (int i = 0; i < word.length(); i++) {
				answer_matrix[current_x][current_y] = word.charAt(i);
				current_y ++;
			}
		}
		
		clue_array[number_of_words] = new Clue(clue, x_start, y_start, word, horizontal);
		number_of_words ++;
	}
		
	
	//checks whether a word can go in the puzzle there or not
	public boolean check_availability(String word, int x_start, int y_start, boolean horizontal) {
		
		int current_x = x_start;
		int current_y = y_start;
		
		for (int i = 0; i < word.length(); i++) {
			if ((answer_matrix[current_x][current_y] == word.charAt(i) || answer_matrix[current_x][current_y] == '0')
					&& ((current_x >=0 && current_x < xmax) && (current_y >=0 && current_y < ymax))) {
				if (horizontal == true) {
					current_x ++;
				} else {
					current_y ++;
				}
			}
			else {
				return false;
			}
		}
		return true;	
	}
	
	
	public void display_clues() {
		if (this.number_of_words == 0) {
			System.out.println("NO CLUES");
		}
		for (int i = 0; i < number_of_words; i++) {
			Clue c = clue_array[i];
			System.out.println("Clue #" + i + " starts at coordinates (" + c.x_start + "," +
			c.y_start + ") and is " + c.word.length() + " letters long, travelling in the " + 
					c.direction + " direction.");
		}
	}
	
	public void show_clue(int index) {
		System.out.println("Clue #" + index + ": " + clue_array[index].clue);
	}
	
	//tells whether user is done or not
	public boolean check_for_completion() {
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j< ymax; j++) {
				if (answer_matrix[i][j] != guessed_matrix[i][j]) {
					return false;
				}
			}
		}
		if (this.number_of_words > 0) {
			System.out.println("CONGRATULATIONS: YOU WON!!!!");
		}
		return true;
	}
	
	public void show_current_state() {
		for (int j = 0; j < xmax; j++) {
			for (int i = 0; i < ymax; i++) {
				if (guessed_matrix[i][j] == '0' && ! (answer_matrix[i][j] == '0') ) {
					System.out.print("- ");
				} else {
					System.out.print(guessed_matrix[i][j] + " ");
				}
			}
			System.out.print("\n\n");
		}
	}
	
	public void show_errors() {
		System.out.println("A missed letter is indicated by an asterisk");
		for (int j = 0; j < xmax; j++) {
			for (int i = 0; i<ymax; i++) {
				if (answer_matrix[i][j] == guessed_matrix[i][j]) {
					System.out.print(guessed_matrix[i][j] + " ");
				} else {
					System.out.print("* ");
				}
			}
			System.out.print("\n");
		}
	}
	
	public void guess_word(int index, String guess) {
		if (this.number_of_words == 0) {
			System.out.println("There are no words yet!");
			return;
		}
		Clue c = clue_array[index];
		if (guess.length() != c.word.length()) {
			System.out.println("*********************************************************");
			System.out.println("Word is wrong length. It should be " + c.word.length() + " letters long.");
			System.out.println("*********************************************************");
		} else {
			int current_x = c.x_start;
			int current_y = c.y_start;
			for (int i = 0; i < c.word.length(); i ++) {
				guessed_matrix[current_x][current_y] = guess.charAt(i);
				if (c.horizontal) {
					current_x ++;
				} else {
					current_y ++;
				}
			}
		}
	}
	
	public void print_commands() {
		System.out.println("METHODS YOU CAN USE:");
		System.out.println("*****************************************************************************");
		System.out.println("add word");
		System.out.println("display clues");
		System.out.println("show clue");
		System.out.println("show puzzle");
		System.out.println("show errors");
		System.out.println("guess word");
		System.out.println("*****************************************************************************");
	}
	
	
	public static void main (String[] args) {
		Crossword_Puzzle puzzle = new Crossword_Puzzle(10,10);
		System.out.println("***********************************************************");
		System.out.println(" ");
		System.out.println("Your puzzle is called 'puzzle' and is 10 x 10");
		System.out.println(" ");
		System.out.println("***********************************************************");
		
		
		while (true) {
			
			if (puzzle.check_for_completion() && puzzle.number_of_words >= 1) {
				break;
			} else{
				puzzle.print_commands();
				
				Scanner s = new Scanner(System.in);
				System.out.println("Please input name of method you'd like to run");
				String user_input = s.nextLine();
				
				if (user_input.toUpperCase().equals("ADD WORD")) {
					System.out.println("Please input word: \n");
					String word = s.next();
					System.out.println("How many words is the clue?");
					int num = s.nextInt();
					System.out.println("Please input clue: \n");
					String clue = s.next();
					for (int i = 1; i < num; i++) {
						clue = clue + " " + s.next();
					}
					System.out.println("Please input starting x coordinate. (Between 0 and " + puzzle.xmax + ")");
					int x = s.nextInt();
					System.out.println("Please input starting y coordinate. (Between 0 and " + puzzle.ymax + ")");
					int y = s.nextInt();
					System.out.println("Please input 'True' if horizontal or 'False' if vertical");
					String horizon = s.next();
					boolean horizontal = true;
					if (horizon.toUpperCase().equals("TRUE")) {
						horizontal = true;
					} else if (horizon.toUpperCase().equals("FALSE")) {
						horizontal = false;
					} else {
						System.out.println("Please input true or false");
					}
					puzzle.add_word(clue, word, x, y, horizontal);
				} else if (user_input.toUpperCase().equals("DISPLAY CLUES")) {
					puzzle.display_clues();
				} else if (user_input.toUpperCase().equals("SHOW CLUE")) {
					System.out.println("Which clue would you like to show?");
					int index = s.nextInt();
					puzzle.show_clue(index);
				} else if (user_input.toUpperCase().equals("SHOW PUZZLE")) {
					puzzle.show_current_state();
				} else if (user_input.toUpperCase().equals("SHOW ERRORS")) {
					puzzle.show_errors();
				} else if (user_input.toUpperCase().equals("GUESS WORD")) {
					System.out.println("Which number word would you like to guess? (0 - " + (puzzle.number_of_words-1) + ")");
					int index = s.nextInt();
					System.out.println("What is your guess for this word?");
					String guess = s.next();
					puzzle.guess_word(index, guess);
				} else {
					System.out.println("Please make sure to type in a valid command");
				}
				
			}
		}
	}
	
	
	
}
