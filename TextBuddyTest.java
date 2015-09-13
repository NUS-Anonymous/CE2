import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TextBuddyTest {
	
	public void testSortCommand(){
		
	}
	
	private void testOneCommand(String description, String expected, String command) {
		assertEquals(description, expected, TextBuddy.executeCommand(command)); 
	}
}
