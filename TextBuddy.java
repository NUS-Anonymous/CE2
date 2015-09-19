import java.util.*;
import java.io.*;

/**
 * This class is used to create a new text file. There are command
 * ADD,DISPLAY,DELETE,CLEAR and EXIT for user to manipulate the content of the
 * file.
 * 
 * Assumption: There is no current file that user wants to append. Instead: By
 * using this java code, user wants to create an entirely new file in the
 * directory, and having the ability to add, remove and modify the content
 * inside that file.
 * 
 * Assumption on input of file: to initialize and create the file user will have
 * to input javac TextBuddy.java java TextBuddy mytextfile.txt < testinput.txt
 * the name of the file "mytextfile.txt" could be replaced by any other name
 * 
 * Assumption on input of command: A valid command would have this form [command
 * word] [additional info depends on the command] [enter] COMMAND WORDS are:
 * ADD,DISPLAY,DELETE,CLEAR, EXIT (input of lowercase or uppercase letter is
 * fine)
 **/

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy %1$s is ready for use";
	private static final String MESSAGE_COMMAND = "command: ";
	private static final String MESSAGE_INVALID = "INVALID command:";
	private static final String MESSAGE_NOTFOUND = "Not Found";
	

	// These are used to indicate the action taken by the file.
	private static final String MESSAGE_COMMAND_NULL = "Error reading file %1$s !";
	private static final String MESSAGE_ADDING = "added to %1$s : \" %2$s \" ";
	private static final String MESSAGE_EMPTY = "%1$s is empty!";
	private static final String MESSAGE_DELETE = "deleted %1$s : \" %2$s\"";

	// These are used to indicate the error from different exception.
	private static final String MESSAGE_UNRECOGNIZE = "Unrecognized command input!";
	private static final String MESSAGE_ERROR_ADDING = "Error adding to file %1$s !";
	private static final String MESSAGE_ERROR_DELETING = "Error deleting file %1$s !";
	private static final String MESSAGE_ERROR_CLEARING = "Error clearing file %1$s !";
	private static final String MESSAGE_ERROR_READING = "Error reading file %1$s !";
	private static final String MESSAGE_ERROR_SORTING = "Error sorting file %1$s !";
	
	private static final int LIMIT_LINE = 20;

	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, SORT, SEARCH, INVALID, EXIT,
	};

	private static Scanner sc = new Scanner(System.in);
	private static int count = 0;
	private static FileWriter fileWriter;
	private static BufferedWriter writer;

	public static void main(String[] args) throws IOException, Error {
		// String fileName = getFileName(args);
		String fileName = "text.txt";
		welcomeUser(fileName);
		String command = sc.nextLine();
		
		try {
			fileWriter = new FileWriter(fileName);
			writer = new BufferedWriter(fileWriter);
		} catch (IOException ex) {
			showToUser(String.format(MESSAGE_ERROR_ADDING, fileName));
		}
		while (command!=""){
		executeCommand(fileName, command);
		showToUser(MESSAGE_COMMAND);
		command = sc.nextLine();
		}
	}

	/*
	 * This method display the welcome message to the user so that the user is
	 * aware that the program is running.
	 */
	private static void welcomeUser(String fileName) {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
		System.out.print(MESSAGE_COMMAND);
	}
	/*
	 * This method execute respective command type based on the command keyword
	 * onto the given file Each of the command keyword would then be passed to
	 * different method to execute.
	 */
	public static void executeCommand(String fileName, String command) throws Error, IOException {
		performFunction(command, fileName);
	}
	
	private static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}
	
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}

	/*
	 * This method determines which of the supported command types the user
	 * wants to perform.
	 * 
	 * @String command is the first word of the input
	 */
	private static COMMAND_TYPE determineCommandType(String userCommand) {
		String command = getFirstWord(userCommand);
		if (command == null) {
			throw new Error(MESSAGE_COMMAND_NULL);
		}
		if (command.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (command.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (command.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (command.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (command.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else if (command.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
		} else if (command.equalsIgnoreCase("search")){
			return COMMAND_TYPE.SEARCH; 
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}
	
	public static void performFunction(String command, String fileName) throws IOException {
			COMMAND_TYPE commandType = determineCommandType(getFirstWord(command));
			switch (commandType) {
			case ADD:
				add(fileName,command);
                break;
			case DISPLAY:
				display(fileName);
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case DELETE:
				showToUser(delete(fileName, command));
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case CLEAR:
				clear(fileName);
				count =0;
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case SORT:
				sort(fileName);
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case SEARCH:
				showToUser(search(fileName, sc.next().trim()));
				break;
			case INVALID:
				showToUser(MESSAGE_INVALID);
				break;
			case EXIT:
				writer.close();
				System.exit(0);
			default:
				throw new Error(MESSAGE_UNRECOGNIZE);
			}
	}

	

	/*
	 * This method is used to printout messages to user
	 */
	public static void showToUser(String message) {
		System.out.println(message);
	}

	/*
	 * This method is used for converting intended name of the file to be
	 * created. Precondition: User need to provide the file name Post-con:
	 * return name of the file in string.
	 */
	public static String getFileName(String[] args) {
		int length = args.length;
		String file = "";
		for (int i = 0; i < length; i++)
			file = file + args[i].toString();
		return file;
	}

	
	
	/*Search a word or a line in the file, if contain then return the line
	* else return "not found";
	*/
	public static String search(String fileName, String trimLine) {
		File inputFile = new File(fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.toLowerCase().contains(trimLine.toLowerCase()))
					return currentLine;
			}
			reader.close();
		} catch (IOException e) {
			showToUser(String.format(MESSAGE_ERROR_SORTING, fileName));
		}	
		return MESSAGE_NOTFOUND;
	}

	/*
	 * This method is to add new line to txt file Precondition: The input is in
	 * form [add] [content] Post-con: return the order of the new line add to
	 * (or line number)
	 */
	public static String add(String fileName,String command) {
		count++;
		String line = removeFirstWord(command);
		try {
			writer.write(count + "." + line);
			writer.newLine();
			writer.flush();
		} catch (IOException addEx) {
			showToUser(String.format(MESSAGE_ERROR_ADDING, fileName));
		}
		return String.format(MESSAGE_ADDING, fileName, line);
	}

	/*
	 * This method display the content of the file If the file is empty, it will
	 * display empty message instead
	 */
	public static void display(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			if ((line = reader.readLine()) == null) {
				showToUser(String.format(MESSAGE_EMPTY, fileName));
			} else {
				do {
					showToUser(line);
				} while ((line = reader.readLine()) != null);
			}
			reader.close();
		} catch (IOException ex) {
			showToUser(String.format(MESSAGE_ERROR_READING, fileName));
		}
	}
	
	public static String unitTest(String fileName) {
		String stream = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			if ((line = reader.readLine()) == null) {
				showToUser(String.format(MESSAGE_EMPTY, fileName));
			} else {
				do {
					stream += line;
				} while ((line = reader.readLine()) != null);
			}
			reader.close();
		} catch (IOException ex) {
			showToUser(String.format(MESSAGE_ERROR_READING, fileName));
		}
		return stream;
	}

	/*
	 * This method is to delete a line in the .txt file Pre-con: The file
	 * contains at least 1 line to delete or else do nothing. Post-con: create a
	 * new file with the same name as the initial file
	 */
	public static String delete(String fileName, String command) {
		File inputFile = new File(fileName);
		File tempOutPut = new File("new" + fileName);
		char lineNumber = removeFirstWord(command).charAt(0);
		System.out.println("lineNumber: "+ lineNumber);
		String deletedLine = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutPut));
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.charAt(0) == lineNumber) {
					deletedLine = String.format(MESSAGE_DELETE, fileName, currentLine.substring(2));
					continue;
				}
				writer.write(currentLine);
				writer.newLine();
			}
			writer.close();
			reader.close();
			tempOutPut.renameTo(inputFile);
		} catch (IOException exDelete) {
			showToUser(String.format(MESSAGE_ERROR_DELETING, fileName));
		}
		return deletedLine;
	}
	
	/* The sort method would arrange lines in alphabetical order. however, there is
	* limitation of maximum 20 lines of text in the file.
	*/
	public static String sort(String fileName) {
		File inputFile = new File(fileName);
		File tempOutPut = new File("new" + fileName);
		String[] array = new String[LIMIT_LINE];
		for (int i = 0; i < LIMIT_LINE; i++) {
			array[i] = "{";
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutPut));
			String currentLine;
			int count = 0;
			while ((currentLine = reader.readLine()) != null) {
				array[count++]= currentLine.substring(3);
				}
			Arrays.sort(array);
			for(int i = 0; i< count; i++){
				int n = i+1;
				writer.write(""+ n+". " + array[i]);
				writer.newLine();
			}
			writer.close();
			reader.close();
			tempOutPut.renameTo(inputFile);
		} catch (IOException e) {
			showToUser(String.format(MESSAGE_ERROR_SORTING, fileName));
			return "fail";
		}
		return "successful";
	}
	

	/*
	 * This method is to clear all content in the .txt file Pre-con: The file
	 * contains at least 1 line to delete or else do nothing. Post-con: create a
	 * new file with the same name as the initial file
	 */
	public static void clear(String fileName) {
		File inputFile = new File(fileName);
		File tempOutPut = new File("new" + fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutPut));
			tempOutPut.renameTo(inputFile);
			writer.close();
		} catch (IOException clearEx) {
			showToUser(String.format(MESSAGE_ERROR_CLEARING, fileName));
		}
	}
}