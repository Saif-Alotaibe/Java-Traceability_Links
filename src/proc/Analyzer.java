package proc;

import concepts.Artifact;
import concepts.ArtifactKind;
import concepts.Concept;
import concepts.Link;
import concepts.LinkKind;
import concepts.TraceabilityInfo;

public class Analyzer {

	private Reader reader;

	public Analyzer(Reader reader) {
		super();
		this.reader = reader;
	}

	// Main analysis method.
	// It reads the first tokens to recognize the traceability element 
	// and then calls the appropriate analysis method defined below.
	public Concept analyze(TraceabilityInfo info) {
		Concept concept = null;
		String s = reader.readWord();
		if (s != null) {
			if (s.equals("def")) {
				s = reader.readWord();
				if (s.equals("artifact")) {
					concept = analyzeArtifactKind(info);
				} else if (s.contentEquals("link")) {
					concept = analyzeLinkKind(info);
				} else {
					reader.error("'artifact' or 'link' are expected, but " + s + " is found.");
				}
			} else if (s.contentEquals("a"))
				concept = analyzeArtifact(info);
			else if (s.contentEquals("l"))
				concept = analyzeLink(info);
			else
				reader.error("'def' or 'a' or 'l' are expected, but " + s + " is found.");
		}
		return concept;
	}

	
///////////////////////////////////////////
//DO NOT MODIFY THE CODE BEFORE THIS LINE//
///////////////////////////////////////////
	
	// def artifact identifier name description.
	private ArtifactKind analyzeArtifactKind(TraceabilityInfo info) {
		ArtifactKind artifactKind = null;
		String name;
		String identifier;
		String description;

		/* Read  the identifier, the name, the description, and the dot by calling the appropriate
		 * methods (used methods: reader.readWord(), reader.readDoubleQuotedString(), reader.readDot()). 
		 * Create then an instance of the class ArtifactKind by calling the appropriate constructor.
		 * Store the newly created instance by calling info.addArtifactKind.
		*/

		/* Checkings before creating the instance:
		 * 1) make sure that the expected strings are found. Otherwise display an appropriate error message.
		 * 2) make sure that no other artifact kind was created with the same identifier. You can use 
		 * the method  info.getArtifactKindByIdentifier for this purpose.
		 */
		
		/* 
		 As the checking procedure in reader class return null if there are errors in attributes patterns so, 
		  here will check if any of strings are null or not  and the uniqueness of the identifier
		 */
		identifier = reader.readWord();
		name = reader.readDoubleQuotedString();
		description = reader.readDoubleQuotedString();
		String CheckEnding = reader.readDot();
		
		
		ArtifactKind CheckUniqueness; 
		boolean CheckAttributes;
		
			CheckAttributes = (identifier !=null) && (name !=null) && (description !=null) && (CheckEnding !=null); 
		if(CheckAttributes==false)
			System.out.println("Error in ArtifactKind inputs in the file , please rewrite the inputs in correct form \nEx:def artifact identifier name description.");
		else {
			CheckUniqueness = info.getArtifactKindByIdentifier(identifier);
			if(CheckUniqueness ==null) 
			artifactKind = new ArtifactKind(identifier,name,description);	
			
			
		}
			if(artifactKind!=null)
			info.addArtifactKind(artifactKind);
		return artifactKind;
	}

	// def link identifier name firstArtifactKind secondArtifactKind[*] description
	private LinkKind analyzeLinkKind(TraceabilityInfo info) {
		LinkKind linkKind = null;
		String name;
		String identifier;
		String description;
		String firstArtifactIdentifier;
		String secondArtifactIdentifier;
		boolean isMultiple = false;
		ArtifactKind first, second;
		
		/* Read the the identifier, the name, the two artifact kind identifiers, (optionally the star,)
		 *  the description, and the dot by calling the appropriate methods 
		 * (used methods: reader.readWord(), reader.readDoubleQuotedString(), reader.readDot()). 
		 * Create then an instance of the class LinkKind by calling the appropriate constructor.
		 * Store the newly created instance by calling info.addLinkKind.
		*/
		identifier=reader.readWord();
		name =reader.readDoubleQuotedString();
		firstArtifactIdentifier=reader.readWord();
		secondArtifactIdentifier = reader.readWord();
		isMultiple = secondArtifactIdentifier.contains("*");
		if(isMultiple)
			secondArtifactIdentifier = secondArtifactIdentifier.substring(0, secondArtifactIdentifier.length()-1);
		description= reader.readDoubleQuotedString();
		String CheckEnding = reader.readDot();
		/* Checkings before creating the instance:
		 * 1) make sure that the expected strings are found in the specified order. Otherwise display
		 *    an appropriate error message.
		 * 2) make sure that  the two artifact kinds were previously defined and stored in info. You can
		 *    use the method info.getArtifactKindByIdentifier for this purpose.
		 * 2) make sure that no other link kind was created with the same identifier. You can use 
		 *    the method  info.getLinkKindByIdentifier for this purpose.
		 */
		boolean CheckAttributes;
		LinkKind CheckUniqueness;
	CheckAttributes = (identifier !=null) && (name !=null) && (firstArtifactIdentifier !=null ) &&  (secondArtifactIdentifier !=null ) && (description !=null) && (CheckEnding !=null)  ; 
		if(CheckAttributes==false)
			System.out.println("Error in LinkKind inputs in the file , please rewrite the inputs in correct form \nEx:def link identifier name firstArtifactKind secondArtifactKind[*] description.");
		else {
			first= info.getArtifactKindByIdentifier(firstArtifactIdentifier); 
			second=info.getArtifactKindByIdentifier(secondArtifactIdentifier);
			CheckUniqueness = info.getLinkKindByIdentifier(identifier);
			if(	(first!=null)	&&	(second!=null)	&& (CheckUniqueness==null))
				linkKind = new LinkKind(identifier,name,first,second,isMultiple,description);
		}
		
		if(linkKind!=null)
		info.addLinkKind(linkKind);
		return linkKind;
	}

	// a artifactKindIdentifier identifier.
	private Artifact analyzeArtifact(TraceabilityInfo info) {
		Artifact artifact = null;
		String kindIdentifier;
		String identifier;
		
		kindIdentifier= reader.readWord();
		identifier=reader.readWord();
		String CheckEnding = reader.readDot();
		
		ArtifactKind kind;
		
		boolean CheckAttributes ;
		CheckAttributes = (kindIdentifier !=null ) && (identifier !=null ) && (CheckEnding !=null) ; 
		
		if(CheckAttributes==false)
			System.out.println("Error in Artifact inputs in the file , please rewrite the inputs in correct form \nEx:a artifactKindIdentifier identifier.");
		
		else {
			kind = info.getArtifactKindByIdentifier(kindIdentifier);
		Artifact CheckEx = info.getArtifactByIdentifier(identifier);
			if(	(kind!=null) && (CheckEx == null))
				artifact = new Artifact(kind,identifier);
		}
		// Implement this method similarly to analyzeArtifactKind and analyzeLinkKind
	
		if(artifact!=null)
		info.addArtifact(artifact);
		return artifact;
	}

	// l LinkKindIdentifier identifier firstArtifactIdentifier linkedArtifactIdentifier ...
	
	private Link analyzeLink(TraceabilityInfo info) {
		Link link = null;
		String linkKindIdentifier;
		String identifier;
		String firstIdentifier;
		String secondIdentifier;
		boolean CheckMultiple = false ;
		String CheckEnding= "";
		linkKindIdentifier = reader.readWord();
		
		if(linkKindIdentifier!=null)
		CheckMultiple = info.getLinkKindByIdentifier(linkKindIdentifier).isMultiple();
		identifier=reader.readWord();
		firstIdentifier = reader.readWord();
	
		
		secondIdentifier=reader.readWord();
		
		boolean CheckAttributes; 
		CheckAttributes = (linkKindIdentifier !=null ) && (identifier !=null ) && (firstIdentifier !=null) && (secondIdentifier !=null) ; 
		boolean CheckEx1= (info.getArtifactByIdentifier(firstIdentifier) !=null ) && (info.getArtifactByIdentifier(secondIdentifier) !=null) ;
		if(CheckAttributes==false )
			System.out.println("Error in Link inputs in the file , please rewrite the inputs in correct form \nEx:l LinkKindIdentifier identifier firstArtifactIdentifier linkedArtifactIdentifier ...");
		else if(!CheckEx1) {
			System.out.println("one of the Artifacts not exists please rewrite the artifacts information");
		}
		else {
			
		if(CheckMultiple==false) {
			CheckEnding = reader.readDot();
			CheckAttributes = CheckAttributes && (CheckEnding !=null);
			link = new Link(info.getLinkKindByIdentifier(linkKindIdentifier),identifier,info.getArtifactByIdentifier(firstIdentifier));
			//add the second artifact
			link.addSecond(info.getArtifactByIdentifier(secondIdentifier));
		}
		else {
			//Means there exist one to many relationship and we want to make sure all artifacts in the system
			link = new Link(info.getLinkKindByIdentifier(linkKindIdentifier),identifier,info.getArtifactByIdentifier(firstIdentifier));
		
			link.addSecond(info.getArtifactByIdentifier(secondIdentifier));
			String id = reader.readWord();
			while(id!=null) {
				Artifact a= info.getArtifactByIdentifier(id);
				if(a!=null)
					link.addSecond(a);
				id=reader.readWord();
			}
			reader.readDot();
		}
		
		
		}
	
		// Implement this method similarly to analyzeArtifactKind and analyzeLinkKind
		if(link!=null)
		info.addLink(link);
		return link;
	}
}
