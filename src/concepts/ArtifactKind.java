package concepts;

public class ArtifactKind extends Concept {

	protected String name;
	protected String description;

	public ArtifactKind(String identifier, String name, String description) {

		this.identifier = identifier;
		this.name = name;
		this.description = description;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void display() {
		System.out.println(toString());

	}

	@Override
	public String toString() {
		return "def artifact " + identifier + " " + name + " " + description + ".";
	}

}
