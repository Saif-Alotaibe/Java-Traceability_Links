package concepts;

public class Artifact extends Concept {

	private ArtifactKind kind;

	public Artifact(ArtifactKind kind, String identifier) {
		super();
		this.kind = kind;
		this.identifier = identifier;
	}

	public ArtifactKind getKind() {
		return kind;
	}

	public void setKind(ArtifactKind kind) {
		this.kind = kind;
	}

	@Override
	public void display() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "a " + kind.getIdentifier() + " " + identifier + ".";
	}

}
