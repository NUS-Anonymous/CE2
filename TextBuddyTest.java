import static org.junit.Assert.*;
import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testSortCommand(){
		testOneCommand("simple before sort", "null", "text.txt");
		
	}
	
	private void testOneCommand(String description, String expected, String command) {
		assertEquals(description, expected,TextBuddy.sort(command)); 
	}
}
