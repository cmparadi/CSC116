import java.io.*;
import java.util.*;

/**
*<br>Cipher- This program implements a userInterface that allows the user to encode and decode files. It supports overwriting of the files as well. The cypher is run on the basis of substitution. The class variables hold the cypher substitutions used. The program will read in the input file and parse it line by line and encode and decode it and rewrite it to the output file. </br>
*<br>Sites used as reference are linked below:</br>
*<br><a href="http://entertainment.howstuffworks.com/puzzles/cryptoquote-puzzles.htm">http://entertainment.howstuffworks.com/puzzles/cryptoquote-puzzles.htm</a></br>
*@author Colby Paradiso
*/
public class Cipher {
	
	/**
	* These are the cypher strings. They are referenced to determine what index 
	* and ultimately, what substituion to use. ALPHABET is the base non-encoded version.
	*/
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	/**
	* These are the cypher strings. They are referenced to determine what index 
	* and ultimately, what substituion to use. REPLACEMENT is the base encoded version.
	*/
	public static final String REPLACEMENT = "ckdlurmxnwvehbayoifgqzjstp";
	/**
	* These are the cypher strings. They are referenced to determine what index 
	* and ultimately, what substituion to use. REPLACEMENTCAPS is the base, 
	* capitilized encoded version.
	*/
	public static final String REPLACEMENTCAPS = "CKDLURMXNWVEHBAYOIFGQZJSTP";
	
	/**
	* Used for user input.
	*/
	public static final Scanner console = new Scanner(System.in);

	/**
	* Boolean values for the loop processing exit flags.
	*/
	public static boolean outputValid = false,
								 quitConfirm = false,
								 actionValid = false,
									   encode = true,
								   capLetter = false,
						 sameNameOverwrite = false,
					     fileAlreadyExist = true;
	/**
	* File names that are more explicitly initialized so they can be used in
	* multiple methods and outside of try/catch blocks.
	*/
	public static File outputFileName = null,
							      inputFile = null,
							       tempFile = null;

	/**
	* PrintStream that is more explicitly initialized so they can be used in
	* multiple methods and outside of try/catch blocks.
	*/
	public static PrintStream outputRead = null;
		
	/**
	* String that is more explicitly initialized so they can be used in
	* multiple methods and outside of try/catch blocks.
	*/
	public static String tempfilename = null;

	/**
	*<br>Prints program header once.</br>
	*<br>Persistantly runs the user interface until user quits.</br>
	*@param args None.
	**/
	public static void main(String[] args) {

		//Header
		System.out.println("\nCipher.java - Enter input and output file names and also to encode or decode and the program will process it with a TOP SECRET substitution cypher. Uses current directory to reference files. Will accept E,D,Q,y,n and .txt files as user input. Program will NOT exit or quit unless an error occurs or you type Q when prompted. Overwriting files works. i.e. Don't want to create a new file at all and keep the same name? Try having the same input file and output file filename! Do it... I worked hard on that part. (Cypher is in the .java file if you really need it.LOL)\n");

		//UI
		while(true) {
			userInterface();
		}
	}

	/**
   * Runs the user interface and calls on the encoding and decoding methods.
   */
	public static void userInterface() {	
		Scanner inputRead = null;

		//Action. Repeats until quit entry.
		do {
			System.out.print("Enter E-ncode, D-ecode, or Q-uit: ");
			String action = console.next();
			if (action.matches("^[Q|q]$")) {
				quitConfirm = true;
				actionValid = true;
			} else if (action.matches("^[E|e]$")) {
				encode = true;
				actionValid = true;
			} else if (action.matches("^[D|d]$")) {
				encode = false;
				actionValid = true;
			} else {
				System.out.print("Input does not match accepted inputs. Re-Enter. \n\n");
			}
		} while (!actionValid);

		if(quitConfirm) {
			
			System.out.println();
			System.out.println("Thank you, come again!");
			System.exit(0);
		}
		
		//Starts Input File read and Output Stream.
		inputRead = getInputScanner(console);
		getOutputPrintStream(console);

		//Encodes/Decodes input file and outputs to new file.
		processFile(encode, inputRead, outputRead);

		//Close Stream
		inputRead.close();
		outputRead.close();

		//Reset Vars
		resetVars();
	}

	//Returns Scanner for an input file
	//Use a try/catch block to catch and handle any FileNotFoundException's that occur

	/**
   * Get's input file to open scanner with. This is the file that holds the text
   * that will be encodede/decoded. Has FileNotFound support.
   * @param console is user input to enter the input file
   * @return Scanner for program to use to scan text file.
   */
	public static Scanner getInputScanner(Scanner console) {
		Scanner input = null;
		while (input == null) {
			System.out.print("Enter input file: ");
			String filename = console.next();
			tempfilename = filename;
			try {
				inputFile = new File(filename);
				input = new Scanner(inputFile);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return input;
	}

	//Returns PrintStream for output file
	//Use a try/catch block to catch and handle any FileNotFoundException's that occur
	/**
   * Get's output file to open printstream with. This is the file that holds the text
   * that will is encoded/decoded. Has Overwrite and same file Overwrite support.
   * @param console is user input to enter the output file
   * @return PrintStream for program to use to print to text file.
   */
	public static PrintStream getOutputPrintStream(Scanner console) {
			while(!outputValid) {
				try {

					//Prompting for Filename. Only accepts new names. No overwrite. 
					//fileAlreadyExist is exit flag for do-while.
					do {
						System.out.print("Enter output file: ");
						String outputFile = console.next();

						outputFileName = new File(outputFile);

						//Does the file exist already?
						if (!outputFileName.exists()) {
							System.out.println("Filename is acceptable. \n");
							fileAlreadyExist = false;

						} else if(outputFileName.exists()) {

							//Overwrite processing.
							System.out.println("Filename already exists, is it ok to overwrite? [y/n]");
							if(console.next().matches("^[y/Y]$")) {

								//Same input and output filename case.
								//Creates tempfile on the side. Make tempfile the main outputRead.
								//Later uses sameNameOverwrite flag to delete previous file and rename tempfile.
								if(outputFile.equals(tempfilename)) {
									tempFile = new File("temp.txt");
									tempFile.createNewFile();
									outputRead = new PrintStream("temp.txt");
									fileAlreadyExist = false;
									sameNameOverwrite = true;
									outputValid = true;
								}
	
								System.out.println("Overwriting...DONE!\n");
								fileAlreadyExist = false;

							} else {
								System.out.println("Please enter a new filename.");
							}
						} //else if 'outputFileName.exists()'
					} while (fileAlreadyExist);

					//Create file. Skip if temp file was created for Overwrite.
					if (!sameNameOverwrite) {
						outputFileName.createNewFile();
						outputRead = new PrintStream(outputFileName);
						outputValid = true;
					}

					//Reset Toggles.
					fileAlreadyExist = true;

				} catch (Exception e) {
					System.out.println("Error with output file name. Re-Enter. \n");
					outputValid = false;
				}
			}

			return outputRead;
	}

	//If encode is true, encodes lines in input and outputs encoded file.
	//If encode is false, decodes lines in input and outputs decoded file.
	/**
   * Get's encoding or decoding instrurction. The input scanner and output printstream 
   * are used to read and write text. Calls encode and decode methods line by line then
	* then prints line by line. Small same file overwrite process; renames temp file 
	* to overwrite previous filename.
   * @param encode to signal encode or decode process.
   * @param input is input file Scanner.
   * @param output is output file PrintStream.
   */
	public static void processFile(boolean encode, Scanner input, PrintStream output) {
		String currentLine = null;

		//Encode path.
		if(encode) {
			do {

				//Read in line by line.
				currentLine = input.nextLine();

				//Line is encoded.
				String fixedString = encodeLine(currentLine);

				//Prints to new file.
				output.print(fixedString);
				output.println();

			} while (input.hasNext());

		//Decode path.
		} else {
			do {

				//Read in line by line.
				currentLine = input.nextLine();

				//Line is encoded.
				String fixedString = decodeLine(currentLine);

				//Prints to new file.
				output.print(fixedString);
				output.println();

			} while (input.hasNext());
		}

		//Overwrite process. Deletes previous file used to encode/decode. Renames temp file as same name as previous file.
		if(sameNameOverwrite) {
			inputFile.delete();
			tempFile.renameTo(inputFile);
			sameNameOverwrite = false;
		}
	}	

	//Returns string containing encoded line.
	/**
   * Get's line to be encoded. Parse the line token by token. References the cypher 
	* strings to exhange indexes. New character is selected and concatenated into  
	* new line. 
   * @param line passed in to be encoded.
   * @return String of newly encoded line.
   */
	public static String encodeLine(String line) {
		//Initialize
		String newString = "";
		int letterIndex = 0;

		//Step through line token by token.
		for(int i = 0; i < line.length(); i++) {
			char currentChar = line.charAt(i);
			
			//If it's a letter. a-z or A-Z.
			if (Character.isLetter(currentChar)) {

				//Lower-case index.
				if(currentChar >= 'a') {
					letterIndex = currentChar - 'a';

				//Upper-case index.
				} else {
					//Toggle
					capLetter = true;
					letterIndex = currentChar - 'A';
				}

			//Non-letter. (whitespace, numbers, symbols.) Copy directly.
			} else {
				letterIndex = -1;
			}
			
			//Copying non-letter directly.
			if(letterIndex == -1) {
				newString += currentChar;
			}

			//Capital Letter replacement.
			else if(capLetter) {
				char c = REPLACEMENT.charAt(letterIndex);
				newString += Character.toUpperCase(c); 

			//Lower-case letter replacement.
			} else {
				newString += REPLACEMENT.charAt(letterIndex);
			}
	
			//Reset toggle.
			capLetter = false;
		}

		return newString;
	}
	
	//Returns string containing decoded line.
	/**
   * Get's line to be decoded. Parse the line token by token. References the cypher 
	* strings to exhange indexes between ALPHABET AND CYPHER. New character is 
	* selected and concatenated into new line. 
   * @param line passed in to be encoded.
   * @return String of newly encoded line.
   */
	public static String decodeLine(String line) {
		//Initialize
		String newString = "";
		int letterIndex = 0;

		//Step through line token by token.
		for(int j = 0; j < line.length(); j++) {
			char currentChar = line.charAt(j);
			
			//If it's a letter. a-z or A-Z.
			if (Character.isLetter(currentChar)) {
				//Lower-case index.
				if(currentChar >= 'a') {
					letterIndex = REPLACEMENT.indexOf(currentChar);

				//Upper-case index.
				} else {
					//Toggle
					capLetter = true;
					letterIndex = REPLACEMENTCAPS.indexOf(currentChar);
				}

			//Non-letter. (whitespace, numbers, symbols.) Copy directly.
			} else {
				letterIndex = -1;
			}
			
			//Copying non-letter directly.
			if(letterIndex == -1) {
				newString += currentChar;
			}

			//Capital Letter replacement.
			else if(capLetter) {
				char c = ALPHABET.charAt(letterIndex);
				newString += Character.toUpperCase(c); 

			//Lower-case letter replacement.
			} else {
				newString += ALPHABET.charAt(letterIndex);
			}
	
			//Reset toggle.16
			capLetter = false;
		}

		return newString;
	}

	/**
   * Refreshes flag variables and some file names. Ensures userInterface seperates file
	* processes. 
   */
	public static void resetVars(){
		outputValid = false;
		quitConfirm = false;
	   actionValid = false;
			  encode = true;
		  capLetter = false;
 fileAlreadyExist = true;
   outputFileName = null;
		 outputRead = null;
	}
}
