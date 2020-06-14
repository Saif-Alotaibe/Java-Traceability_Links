package concepts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.LinkedList;

public class TraceabilityInfo {

	private LinkedList<ArtifactKind> artifactKinds;
	private LinkedList<LinkKind> linkKinds;
	private LinkedList<Artifact> artifacts;
	private LinkedList<Link> links;

	private Hashtable<String, ArtifactKind> hArtifactKinds;
	private Hashtable<String, LinkKind> hLinkKinds;
	private Hashtable<String, Artifact> hArtifacts;
	private Hashtable<String, Link> hLinks;

	private String filename;
	boolean isSaved;

	public TraceabilityInfo(String filename) {
		this.filename = filename;
		this.artifactKinds = new LinkedList<ArtifactKind>();
		this.linkKinds = new LinkedList<LinkKind>();
		this.artifacts = new LinkedList<Artifact>();
		this.links = new LinkedList<Link>();
		this.hArtifactKinds = new Hashtable<String, ArtifactKind>();
		this.hLinkKinds = new Hashtable<String, LinkKind>();
		this.hArtifacts = new Hashtable<String, Artifact>();
		this.hLinks = new Hashtable<String, Link>();
		this.isSaved = true;
	}

	public void addArtifact(Artifact artifact) {
		this.artifacts.add(artifact);
		this.hArtifacts.put(artifact.getIdentifier(), artifact);
		isSaved = false;
	}

	public void addArtifactKind(ArtifactKind artifactKind) {
		this.artifactKinds.add(artifactKind);
		this.hArtifactKinds.put(artifactKind.getIdentifier(), artifactKind);
		isSaved = false;
	}

	public void addLink(Link link) {
		this.links.add(link);
		this.hLinks.put(link.getIdentifier(), link);
		isSaved = false;
	}

	public void addLinkKind(LinkKind linkKind) {
		this.linkKinds.add(linkKind);
		this.hLinkKinds.put(linkKind.getIdentifier(), linkKind);
		isSaved = false;
	}

	public Artifact getArtifactByIdentifier(String identifier) {
		return this.hArtifacts.get(identifier);
	}

	public ArtifactKind getArtifactKindByIdentifier(String identifier) {
		return this.hArtifactKinds.get(identifier);
	}

	public LinkedList<ArtifactKind> getArtifactKinds() {
		return artifactKinds;
	}

	public LinkedList<Artifact> getArtifacts() {
		return artifacts;
	}

	public Link getLinkByIdentifier(String identifier) {
		return this.hLinks.get(identifier);
	}

	public LinkKind getLinkKindByIdentifier(String identifier) {
		return this.hLinkKinds.get(identifier);
	}

	public LinkedList<LinkKind> getLinkKinds() {
		return linkKinds;
	}

	public LinkedList<Link> getLinks() {
		return links;
	}

	public String gettFilename() {
		return this.filename;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void removeArtifactByIdentifier(String identifier) {
		artifacts.removeIf(a -> a.getIdentifier().equals(identifier));
		hArtifacts.remove(identifier);
	}

	public void removeArtifactKindByIdentifier(String identifier) {
		artifactKinds.removeIf(a -> a.getIdentifier().equals(identifier));
		hArtifactKinds.remove(identifier);
	}

	public void removeLinkByIdentifier(String identifier) {
		links.removeIf(a -> a.getIdentifier().equals(identifier));
		hLinks.remove(identifier);
	}

	public void removeLinkKindByIdentifier(String identifier) {
		linkKinds.removeIf(a -> a.getIdentifier().equals(identifier));
		hLinkKinds.remove(identifier);
	}

	public void save() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for (ArtifactKind ak : artifactKinds)
			writer.println(ak.toString());
		for (LinkKind lk : linkKinds)
			writer.println(lk.toString());
		for (Artifact a : artifacts)
			writer.println(a.toString());
		for (Link l : links)
			writer.println(l.toString());
		writer.close();
		isSaved = true;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public void statistics() {
		System.out.println("" + artifactKinds.size() + " artifact kinds");
		System.out.println("" + artifacts.size() + " artifacts");
		System.out.println("" + linkKinds.size() + " link kinds");
		System.out.println("" + links.size() + " links");

	}

	public void toRepresentation() {
		for (ArtifactKind ak : artifactKinds)
			System.out.println(ak.toString());
		for (LinkKind lk : linkKinds)
			System.out.println(lk.toString());
		for (Artifact a : artifacts)
			System.out.println(a.toString());
		for (Link l : links)
			System.out.println(l.toString());
	}

}
