import java.util.*;
import java.io.*;



/**
 * This class is used to create a new text file.
 * There are command ADD,DISPLAY,DELETE,CLEAR and EXIT for user to manipulate
 * the content of the file.
 * 
 * Assumption: There is no current file that user wants to append.
 * Instead: By using this java code, user wants to create an entirely new file
 * in the directory, and having the ability to add, remove and modify the content
 * inside that file.
 * 
 * Assumption on input of file: to initialize and create the file user will have to input
 * javac TextBuddy.java
 * java TextBuddy mytextfile.txt < testinput.txt 
 * the name of the file "mytextfile.txt" could be replaced by any other name
 * 
 * Assumption on input of command:
 * A valid command would have this form
 * [command word] [additional info depends on the command] [enter]
 * COMMAND WORDS are: ADD,DISPLAY,DELETE,CLEAR, EXIT (input of lowercase or uppercase letter is fine)
 **/

public class TextBuddy {
	
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy %1$s is ready for use";
	private static final String MESSAGE_COMMAND = "command: ";
	private static final String MESSAGE_INVALID = "INVALID command:";
	
	// These are used to indicate the action taken by the file.
	private static final String MESSAGE_COMMAND_NULL = "Error reading file %1$s !";
	private static final String MESSAGE_ADDING = "added to %1$s : \" %1$s \" ";
	private static final String MESSAGE_EMPTY= "%1$s is empty!";
	private static final String MESSAGE_DELETE = "deleted %1$s : \" %2$s\"";
	
	// These are used to indicate the error from different exception.
	private static final String MESSAGE_UNRECOGNIZE = "Unrecognized command input!";
	private static final String MESSAGE_ERROR_ADDING = "Error adding to file %1$s !";
	private static final String MESSAGE_ERROR_DELETING = "Error deleting file %1$s !";
	private static final String MESSAGE_ERROR_CLEARING = "Error clearing file %1$s !";
	private static final String MESSAGE_ERROR_READING = "Error reading file %1$s !";
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, SORT,INVALID, EXIT, 
	};

	public static void main(String[] args) {
		//String fileName = getFileName(args);
		String fileName = "text.txt";
		welcomeUser(fileName);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		executeCommand(fileName,sc,command);
	}
	
	/* This method display the welcome message to the user 
	 * so that the user is aware that the program is running.
	 */
	private static void welcomeUser(String fileName) {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
		System.out.print(MESSAGE_COMMAND);
	}

	/* This method determines which of the supported command types the user
	 * wants to perform.
	 * @String command is the first word of the input
	 */
	private static COMMAND_TYPE determineCommandType(String command) {
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
		} else if (command.equalsIgnoreCase("sort")){
			return COMMAND_TYPE.SORT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	/* This method execute respective command type based on the command keyword
	 * onto the given file
	 * Each of the command keyword would then be passed to different method to execute.
	 */
	private static void executeCommand(String fileName,Scanner sc, String command) throws Error {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			performFunction(writer,command,sc,fileName);
		}
		catch (IOException ex) {
			showToUser(String.format(MESSAGE_ERROR_ADDING, fileName));
		}
	}

	/* This method is used to printout messages to user
	*/
	public static void showToUser(String message){
		System.out.println(message);
	}

	/* This method is used for converting intended name of the file to be
	* created.
	* Precondition: User need to provide the file name
	* Post-con: return name of the file in string.
	*/
	public static String getFileName(String[] args) {
		int length = args.length;
		String file = "";
		for (int i = 0; i < length; i++)
			file = file + args[i].toString();
		return file;
	}
	
	public static void performFunction(BufferedWriter writer, String command, Scanner sc, String fileName) throws IOException {
		int count=0;
		while (true) {
			COMMAND_TYPE commandType = determineCommandType(command);
			switch (commandType) {
			case ADD:
				count = add(fileName, writer, count, sc);
				break;
			case DISPLAY:
				display(fileName);
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case DELETE:
				delete(fileName, sc.next().charAt(0));
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case CLEAR:
				count = clear(fileName);
				writer = new BufferedWriter(new FileWriter(fileName, true));
				break;
			case SORT:
				sort(fileName);
				writer = new BufferedWriter(new FileWriter(fileName, true));
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
			showToUser(MESSAGE_COMMAND);
			command = sc.next();
		}		
	}

	/* This method is to add new line to txt file
	* Precondition: The input is in form [add] [content]
	* Post-con: return the order of the new line add to (or line number)
	*/
	public static int add(String fileName, BufferedWriter writer, int count, Scanner sc) {
		count++;
		String line = sc.nextLine();
		try {
			writer.write(count + "." + line);
			writer.newLine();
			writer.flush();
			showToUser(String.format(MESSAGE_ADDING,fileName,line.substring(1)));
		} catch (IOException addEx) {
			showToUser(String.format(MESSAGE_ERROR_ADDING, fileName));
		}
		return count;
	}

	/* This method display the content of the file
	 * If the file is empty, it will display empty message instead
	 */
	public static void display(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			
			if ((line = reader.readLine()) == null) {
				showToUser(String.format(MESSAGE_EMPTY,fileName));
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

	/* This method is to delete a line in the .txt file
	 * Pre-con: The file contains at least 1 line to delete or else 
	 * do nothing.
	 * Post-con: create a new file with the same name as the initial file 
	 */
	public static void delete(String fileName, char number) {
		File inputFile = new File(fileName);
		File tempOutPut = new File("new" + fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutPut));
			String currentLine;
			
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.charAt(0) == number) {
					showToUser(String.format(MESSAGE_DELETE, fileName,currentLine.substring(3)));
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
	}
	


	/* This method is to clear all content in the .txt file
	 * Pre-con: The file contains at least 1 line to delete or else 
	 * do nothing.
	 * Post-con: create a new file with the same name as the initial file 
	 */
	public static int clear(String fileName) {
		File inputFile = new File(fileName);
		File tempOutPut = new File("new" + fileName);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutPut));
			tempOutPut.renameTo(inputFile);
			writer.close();
		} catch (IOException clearEx) {
			showToUser(String.format(MESSAGE_ERROR_CLEARING, fileName));
		}
		return 0;
	}
}