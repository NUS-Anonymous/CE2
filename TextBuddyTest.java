import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
		TextBuddy.executeCommand(fileName, "add firstline");// add firstline
		assertEquals("add firstline", "1.firstline", TextBuddy.unitTest(fileName));
	}
	@Test
	public void testTwoLine() throws IOException {
		TextBuddy test = new TextBuddy();
		String fileName = "text.txt";
		TextBuddy.executeCommand(fileName, "clear");
		TextBuddy.executeCommand(fileName, "add firstline");// add firstline
		TextBuddy.executeCommand(fileName, "add secondline");
		assertEquals("add firstline", "1.firstline"
				+ "2.secondline", TextBuddy.unitTest(fileName));
	}
	public void testSort() throws IOException {
		TextBuddy test = new TextBuddy();
		String fileName = "text.txt";
		TextBuddy.executeCommand(fileName, "clear");
		TextBuddy.executeCommand(fileName, "add firstline");// add firstline
		TextBuddy.executeCommand(fileName, "add secondline");
		assertEquals("No sorting, hence pass test", "1.firstline"
				+ "2.secondline", TextBuddy.unitTest(fileName));
	}
}
