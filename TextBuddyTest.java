import static org.junit.Assert.*;
import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testSearchCommand(){
		TextBuddy.executeCommand("text.txt", "add this is the firstline");
		TextBuddy.executeCommand("text.txt", "add this is the Secondline");
		TextBuddy.executeCommand("text.txt", "add this is the Thirdine");
		TextBuddy.executeCommand("text.txt", "add this is the Fourthline");
		// null fail case
		testOneCommand("fail method","","firstline");
		
	}
	
	private void testOneCommand(String description, String expected, String command) {
		assertEquals(description, expected,TextBuddy.search("text.txt",command)); 
	}
}
