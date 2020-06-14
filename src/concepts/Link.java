package concepts;

import java.util.LinkedList;

public class Link extends Concept {

	private LinkKind kind;
	private Artifact first;
	private LinkedList<Artifact> second;

	public Link(LinkKind kind, String identifier, Artifact first) {
		this.kind = kind;
		this.identifier = identifier;
		this.first = first;
		this.second = new LinkedList<Artifact>();
	}

	public LinkKind getKind() {
		return kind;
	}

	public Artifact getFirst() {
		return first;
	}

	public LinkedList<Artifact> getSecond() {
		return second;
	}

	public void addSecond(Artifact s) {
		second.add(s);
	}

	@Override
	public void display() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		String str = "l " + kind.getIdentifier() + " " + identifier + " " + first.getIdentifier();
		for (Artifact a : second)
			str += " " + a.getIdentifier();
		str += ".";
		return str;
	}

}
