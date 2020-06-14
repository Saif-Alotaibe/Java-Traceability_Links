package concepts;

public class LinkKind extends ArtifactKind {

	private ArtifactKind first;
	private ArtifactKind second;
	private boolean isMultiple;

	public LinkKind(String identifier, String name, ArtifactKind first, ArtifactKind second, boolean isMultiple,
			String description) {
		super(identifier, name, description);
		this.first = first;
		this.second = second;
		this.isMultiple = isMultiple;

	}

	public ArtifactKind getFirst() {
		return first;
	}

	public void setFirst(ArtifactKind first) {
		this.first = first;
	}

	public ArtifactKind getSecond() {
		return second;
	}

	public void setSecond(ArtifactKind second) {
		this.second = second;
	}

	public boolean isMultiple() {
		return isMultiple;
	}

	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

	@Override
	public void display() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		String str = "def link " + identifier + " " + name + " " + first.identifier + " " + second.identifier;
		if (isMultiple)
			str += "*";
		str += " " + description + ".";
		return str;
	}
}
