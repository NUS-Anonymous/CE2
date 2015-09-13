import static org.junit.Assert.*;
import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testSortCommand(){
		TextBuddy.executeCommand("text.txt", "add d");
		testOneCommand("fail file","fail","new.txt");
		
		testOneCommand("successful test","successful","text.txt");
		TextBuddy.executeCommand("text.txt","add d");
		testOneCommand("1 line","1. d","text.txt");
		
	}
	
	private void testOneCommand(String description, String expected, String command) {
		assertEquals(description, expected,TextBuddy.display(command)); 
	}
}
