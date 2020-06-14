
////////////////////////////
// DO NOT MODIFY THIS FILE//
////////////////////////////


package proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

	private Scanner fileScanner;
	private IndexedString indexedString;
	private Pattern wordPattern;
	private Pattern dqStringPattern;
	private Pattern dotPattern;

	public Reader(String filename) throws FileNotFoundException {
		fileScanner = new Scanner(new File(filename));

		indexedString = new IndexedString("", 0, 0);
		if (fileScanner.hasNextLine()) {
			indexedString.setValue(fileScanner.nextLine());
			indexedString.incrementLineNumber();
		}
		wordPattern = Pattern.compile("^\\s*\\w(\\w)*[*]?");
		dqStringPattern = Pattern.compile("^\\s*\"\\w(\\w|\\s)*\"");
		dotPattern = Pattern.compile("^\\s*\\.");
	}

	private IndexedString getIndexedString() {
		String line = "";
		boolean notEnd;
		if (!indexedString.isAtEnd())
			return indexedString;
		while ((notEnd = fileScanner.hasNextLine()) && ((line = fileScanner.nextLine()).matches("\\s*")))
			indexedString.incrementLineNumber();
		if (notEnd) {
			indexedString.setValue(line);
			indexedString.incrementLineNumber();
			indexedString.setIndex(0);
			return indexedString;
		}
		return null;
	}

	private String read(Pattern p) {
		Matcher matcher;
		IndexedString istr = getIndexedString();
		if (istr != null) {
			matcher = p.matcher(istr.getValue());
			if (matcher.find()) {
				indexedString.moveIndex(matcher.group(0).length());
				return matcher.group(0).trim();
			} 
		}
		return null;
	}

	// Read a single word string that contains only letters and digits.
	// If not possible, return null.
	public String readWord() {
		String str = read(wordPattern);
		return str;
	}

	// Read a double quoted string that contains only letters, digits, and whitespaces.
	// If not possible, return null.
	public String readDoubleQuotedString() {
		return read(dqStringPattern);
	}

	// Read a dot.
	// If not possible, return null.
	public String readDot() {
		String str = read(dotPattern);
		return str;
	}

	
	
	public void error(String message) {
		System.out.println("**** [" + indexedString.getValue() + "]");
		System.out.format("(line: %d, character: %d): %s\n", indexedString.getLineNumber(), indexedString.getIndex(),
				message);
	}

	public String remainingString() {
		return "/" + indexedString.getValue() + "/";
	}

}
