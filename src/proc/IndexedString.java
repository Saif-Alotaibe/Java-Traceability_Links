
////////////////////////////
// DO NOT MODIFY THIS FILE//
////////////////////////////



package proc;

public class IndexedString {
	private String value;
	private int index;
	private int lineNumber;

	public IndexedString(String value, int index, int lineNumber) {
		this.value = value;
		this.index = index;
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void incrementLineNumber() {
		lineNumber++;
	}

	public String getValue() {
		return value.substring(index);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void moveIndex(int offset) {
		this.index += offset;
	}

	public boolean isAtEnd() {
		return index >= value.length();
	}

}
