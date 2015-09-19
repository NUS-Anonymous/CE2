import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {
	@Test
	public void testUnitTest() throws IOException{
		TextBuddy test = new TextBuddy();
		String fileName = "text.txt";
		TextBuddy.executeCommand(fileName, "clear");
		assertEquals("clear","", TextBuddy.unitTest(fileName));
	}
	@Test
	public void testOneLine() throws IOException {
		TextBuddy test = new TextBuddy();
		String fileName = "text.txt";
		TextBuddy.executeCommand(fileName, "delete 1");
		assertEquals("clear", "", TextBuddy.unitTest(fileName));
		
	}
	
}
