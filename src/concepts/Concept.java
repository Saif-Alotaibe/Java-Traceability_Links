package concepts;

public abstract class Concept {

	protected String identifier;

	public abstract void display();

	public abstract String toString();

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return identifier;
	}
}
