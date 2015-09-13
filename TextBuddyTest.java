import static org.junit.Assert.*;
import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testSortCommand(){
		TextBuddy.executeCommand("text.txt", "add d");
		testOneCommand("fail file","fail","new.txt");
	}
	
	private void testOneCommand(String description, String expected, String command) {
		assertEquals(description, expected,TextBuddy.sort(command)); 
	}
}
