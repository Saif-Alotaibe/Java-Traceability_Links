package main;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import concepts.Artifact;
import concepts.ArtifactKind;
import concepts.Link;
import concepts.LinkKind;
import concepts.TraceabilityInfo;
import proc.Analyzer;
import proc.Reader;



public class Main {

	public static Scanner read = new Scanner(System.in);
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		if (args.length < 1) {
			System.out.println("Please provide a file name.");
			System.exit(1);
		}
		
		// Create an instance of TraceabilityInfo
		TraceabilityInfo info = new TraceabilityInfo(args[0]);			
		
		// Create an instance of Analyzer. 
		Analyzer analyzer = new Analyzer(new Reader(args[0]));		
		
		// Analyze the text file: 
		// the content is stored in the previously created instance of TraceabilityInfo
		while (analyzer.analyze(info)!=null);
			
		
		// Test the analysis:
		// Display the contents of the instance of TraceabilityInfo
		info.toRepresentation();
		info.statistics();
		
		
		// DO NOT MODIFY THE CODE BEFORE THIS LINE
		
		// Insert your code here
		// ...
		
		int option = 0 ; 
		while(option!=4) {
			MainMenuUI();//MainMenu UI 
			System.out.println("Choose an option (between 1 and 4)");
			option = read.nextInt();
			
			
			switch(option) {
			
			//Update UI
			case 1:
				
				while(option !=3) {
				UpdateUI();
				System.out.println("Choose an option (between 1 and 3)");
				option =read.nextInt();
				
				switch(option) {
				//Add UI (Inside Update UI)
				case 1 :
					while(option!=5) {
						AddUI();
						System.out.println("Choose an option (between 1 and 5)");
						option=read.nextInt();
						
						switch(option) {
						// Add Artifact kind
						case 1: 
							AddArtifactKindUI(info);
							break;
						//Add link kind
						case 2:	
							AddLinkKindUI(info);
							break;
						//Add artifact 
						case 3: 
							AddArtifactUI(info);
							break;
						//Add link
						case 4:
							AddLinkUI(info);
							break;
						}
						
					}
						break;
				case 2: 
						
						while(option!=5) {
							RemoveUI();
							option=read.nextInt();
							switch(option){
							
							//Remove Artifact kind
							case 1:
								RemoveArtifactKindUI(info);
								break;
							//Remove Link Kind 
							case 2: 
								RemoveLinkKindUI(info);
								break;
							//Remove Artifact 
							case 3:
								RemoveArtifactUI(info);
								break;
							//Remove Link	
							case 4:
								RemoveLinkUI(info);
								break;
								}
							
							}
						break;
					
					}
				}
				
				break;
				//Query
			case 2:
				
				while(option!=4) {
					QueryUI();
					option=read.nextInt();
					switch(option) {
						//Display stat
					case 1:
						info.statistics();
						break;
						
					//Find Forward Links	
					case 2:
						FindForwardLinksUI(info);
						break;
				
					//Find Backword links
					case 3:
						FindBackwordLinksUI(info);
						break;
					
					
					}
					
					
					
				}
				if(option==4)
					option=100;//random number to avoid stop the program cause of option 4
				break;
				//Save
			case 3:
				SaveUI(info);
				break;
				
			}
		}
	}
	
	private static void UpdateUI() {
		
			System.out.println("-------- Update traceability information--------");
			System.out.println("(1) Add new traceability element");
			System.out.println("(2) Remove a traceability element");
			System.out.println("(3) Back");
			
		
	}

	private static void AddUI() {
		System.out.println("-------- Add new traceability element--------");
		System.out.println("(1) Add new artifact kind");
		System.out.println("(2) Add new link kind");
		System.out.println("(3) Add new artifact");
		System.out.println("(4) Add new link");
		System.out.println("(5) Back");
	}
	
	private static void MainMenuUI() {


		System.out.println("-------- Main menu--------");
		System.out.println("(1) Update the traceability information");
		System.out.println("(2) Query the traceability information");
		System.out.println("(3) Save the traceability information");
		System.out.println("(4) Quit");
	}
	
	private static void AddArtifactKindUI(TraceabilityInfo information) {
		System.out.println("Please provide The following information:");
		System.out.print("Identifier:");
		String id= read.next();
		boolean IsValidId=id.matches("^[0-9]*[a-zA-Z]*[0-9]*$");
		boolean CheckEx = false;
		
		if(!IsValidId) {
			System.out.println("Please provide correct pattern for identifier(only digits and letters)");
			return;
		}
		read.nextLine();// Avoid nextLine issue with spaces 
		CheckEx=information.getArtifactKindByIdentifier(id)==null;
		if(!CheckEx) {
		System.out.println("There Exists ArtifactKind with same identifier. Please provide another identifier");
		return;
		}
		System.out.print("Name:");
		String name = read.nextLine();
		System.out.print("Description:");
		String desc = read.nextLine();
		
			//To avoid double quotes problem after saving
			name="\""+name+"\"";
			desc="\""+desc+"\"";
			information.addArtifactKind(new ArtifactKind(id,name,desc));
			System.out.println("ArtifactKind Added Seccussfully !");
	}
	
	
	private static void AddLinkKindUI(TraceabilityInfo information) {
		if(information.getArtifactKinds().size()<=1) {
			System.out.println("Sorry we can't add Linkkind because no enough Artifactkind to choose from ");
			return;
		}
		System.out.println("Please provide The following information:");
		System.out.print("Identifier:");
		String id= read.next();
		boolean CheckEx = information.getLinkKindByIdentifier(id)==null;
		boolean IsValidId=id.matches("^[0-9]*[a-zA-Z]*[0-9]*$");
		
		
		if(!IsValidId) {
			System.out.println("Please provide correct pattern for identifier(only digits and letters)");
		return;	
		}
		if(!CheckEx) {
			System.out.println("There exist LinkKind with the same Identifier.Please provide another identifier");
			return ; 
		}
		read.nextLine();
		System.out.print("Name:");
		String name = read.nextLine();
		
		Hashtable<Integer, String> h = new Hashtable<Integer,String>(); //to make sure any option in correct range of keys 
		//Get All ArtifactKinds for selection
		LinkedList<ArtifactKind> l = information.getArtifactKinds();
		for(int i = 0; i<l.size();i++) {
			System.out.print((i+1)+")");
			l.get(i).display();
			h.put(i+1, l.get(i).getIdentifier());
		}
		System.out.printf("Please choose two numbers (between 1 and %d)",h.size());
		int op =read.nextInt();
		int op2 = read.nextInt();
		
		if(!h.containsKey(op) || !h.containsKey(op2)) {
			System.out.println("Please try again and choose correct number within the range ");
			return;
		}
			
		
		ArtifactKind first = information.getArtifactKindByIdentifier(h.get(op));
		ArtifactKind second = information.getArtifactKindByIdentifier(h.get(op2));
		System.out.print("Is the relation one-to-many ? (y/n)");
		String IsMultiple = read.next();
		boolean IsMult = false;
		IsMult=IsMultiple.equalsIgnoreCase("y");
		read.nextLine();
		System.out.print("Description:");
		String desc = read.nextLine();
		
		//Avoid double quotes problem After saving
		name="\""+name+"\"";
		desc="\""+desc+"\"";
			information.addLinkKind(new LinkKind(id,name,first,second,IsMult,desc));
			System.out.println("LinkKind Added Seccussfully !");
			
	}

	private static void AddArtifactUI(TraceabilityInfo information) {
		if(information.getArtifactKinds().size()==0) {
			System.out.println("Sorry we can't add Artifact because no existing Artifactkind to choose from ");
			return;
		}
		System.out.println("Please Enter the identifier");
		String id = read.next();
		boolean CheckEx = information.getArtifactByIdentifier(id)==null;
		boolean IsValidId=id.matches("^[0-9]*[a-zA-Z]*[0-9]*$");
		
		
		if(!IsValidId) {
			System.out.println("Please provide correct pattern for identifier(only digits and letters)");
		return;	
		}
		if(!CheckEx) {
			System.out.println("There exist Artifact with the same Identifier.Please provide another identifier");
			return ; 
		}
		Hashtable<Integer, String> h = new Hashtable<Integer,String>(); //to make sure any option in correct range of keys 
		//Get All ArtifactKinds for selection
		LinkedList<ArtifactKind> l = information.getArtifactKinds();
		for(int i = 0; i<l.size();i++) {
			System.out.print((i+1)+")");
			l.get(i).display();
			h.put(i+1, l.get(i).getIdentifier());
		}
		System.out.printf("Please choose (between 1 and %d)",h.size());
		int op = read.nextInt();
		if(!h.containsKey(op)) {
			System.out.println("Please try again and choose correct number within the range ");
			return;
		}
		
		information.addArtifact(new Artifact(information.getArtifactKindByIdentifier(h.get(op)),id));
			System.out.println("Artifact Added Seccussfully !");
		
	}
	
	private static void AddLinkUI(TraceabilityInfo information) {
		if(information.getLinkKinds().size()==0) {
			System.out.println("Sorry we can't add Link because no linkkind to choose from ");
			return;
		}
		if(information.getArtifacts().size()<=1) {
			System.out.println("Sorry we can't add Link because no enough Artifacts th choose from  ");
			return;
		}
		System.out.println("Please Enter the identifier");
		String id  = read.next();
		boolean CheckEx = information.getLinkKindByIdentifier(id)==null;
		boolean IsValidId=id.matches("^[0-9]*[a-zA-Z]*[0-9]*$");
		
		
		if(!IsValidId) {
			System.out.println("Please provide correct pattern for identifier(only digits and letters)");
		return;	
		}
		if(!CheckEx) {
			System.out.println("There exist Link with the same Identifier.Please provide another identifier");
			return ; 
		}
		LinkedList<LinkKind> l = information.getLinkKinds();
		LinkedList<Artifact> l2=information.getArtifacts();
		for(int i=0 ; i <l.size();i++) {
			System.out.print((i+1) +") ");
			l.get(i).display();
		}
		System.out.printf("Please Choose between 1 and %d",l.size());
		int op=read.nextInt();
			boolean check=CheckInRange(op,l);
			if(!check)
				return;
		LinkKind L = l.get(op-1); 
		for(int i =0 ; i<l2.size();i++) {
			System.out.print((i+1) +") ");
			l2.get(i).display();
		}
		System.out.printf("Please Choose one number for the first Artifact between 1 and %d",l2.size());
		int option = read.nextInt();
		check=CheckInRange(option,l2);
		if(!check)
			return;
		Artifact first=l2.get(option-1);
		boolean isMult = L.isMultiple();
		l2.remove(option-1);
		for(int i=0; i<l2.size();i++) {
			System.out.print((i+1) +") ");
			l2.get(i).display();
		}
		System.out.printf("Please Choose one number for the Second Artifact between 1 and %d",l2.size());
		option=read.nextInt();
		check=CheckInRange(option,l2);
		if(!check)
			return;
		Artifact second = l2.get(option-1);
		l2.remove(option-1);
		Link Li = new Link(L,id,first);
		Li.addSecond(second);
		if(isMult) {
			System.out.println("Do you want insert another Artifact ? (y/n)");
			String ans = read.next();
			
			while(!l2.isEmpty() && ans.equalsIgnoreCase("y")) {
				for(int i=0; i<l2.size();i++) {
					System.out.print((i+1)+")");
					l2.get(i).display();
				}
				System.out.printf("Please choose one number between 1 and %d",l2.size());
				option=read.nextInt();
				check=CheckInRange(option,l2);
				if(!check)
					return;
				Artifact a = l2.get(option-1);
				Li.addSecond(a);
				l2.remove(option-1);
				if(!l2.isEmpty()) {
					System.out.println("Do you want insert another Artifact ? (y/n)");
					ans=read.next();
				}
			}
		}
		
		information.addLink(Li);
		System.out.println("Link added Successfully !");
		System.out.println("");
		
		
	}
	
	private static void RemoveArtifactKindUI(TraceabilityInfo information) {
		if(information.getArtifactKinds().size()==0) {
			System.out.println("Sorry no ArtifactKinds to be removed !");
			return;
		}
		LinkedList<ArtifactKind> Ak = new LinkedList<>();
		LinkedList<Artifact> Ar = new LinkedList<>();
		LinkedList<LinkKind> Lk = new LinkedList<>();
		LinkedList<Link> Li = new LinkedList<>();
		//Intialize all lists (using this to avoid two list point to same reference)
		
		for(Artifact a : information.getArtifacts())
			Ar.add(a);
		for(ArtifactKind ak: information.getArtifactKinds())
			Ak.add(ak);
		for(LinkKind lk: information.getLinkKinds())
			Lk.add(lk);
		for(Link l : information.getLinks())
			Li.add(l);
		
		
		int option =0 ;
		boolean check=false;
		for (int i =0 ; i<Ak.size();i++) {
			System.out.print((i+1)+")");
			Ak.get(i).display();
		}
		if(Ak.size()>1)
		System.out.printf("Please choose one to be removed between 1 and %d",Ak.size());
		else
			System.out.println("Please choose one to be removed");
		option=read.nextInt();
		check=CheckInRange(option,Ak);
		if(!check)
			return;
		String IdAk= Ak.get(option-1).getIdentifier();
		
		//1 remove all links that have any artifact for the removed artifact kind
		
				for(int i=0;i<Li.size();i++) {
					Link L1 = Li.get(i);
					boolean contain = false;
					if(L1.getFirst().getKind().getIdentifier().equals(IdAk) || L1.getSecond().get(0).getKind().getIdentifier().equals(IdAk) ) 
						contain=true;
					LinkedList<Artifact> temp = L1.getSecond();
					for(int j=1;j<temp.size();j++)
						if(temp.get(j).getKind().getIdentifier().equals(IdAk))
							contain=true;
					if(contain)
						information.removeLinkByIdentifier(L1.getIdentifier());
						
				}
		
		//2 remove all artifact of that kind
		
		for(int i=0;i<Ar.size();i++) {
			Artifact a = Ar.get(i);
			if(a.getKind().getIdentifier().equals(IdAk))
				information.removeArtifactByIdentifier(a.getIdentifier());
		}
		//3 remove all LinkKind of including that kind
		for(int i=0;i<Lk.size();i++) {
			LinkKind lk = Lk.get(i);
			if(lk.getFirst().getIdentifier().equals(IdAk)|| lk.getSecond().getIdentifier().equals(IdAk))
				information.removeLinkKindByIdentifier(lk.getIdentifier());
		}
		
		//4 remove Artifact kind
		
		information.removeArtifactKindByIdentifier(IdAk);
		System.out.println("Artifact Kind removed !");
		
		
		
			
	}
	
	private static<T> boolean CheckInRange(int op , LinkedList<T> l ){
		if(op<1 || op>l.size()) {
			System.out.println("Please try again with number within the range ");
			return false;
		}
		return true;
	}

	private static void RemoveLinkKindUI(TraceabilityInfo information) {
		if(information.getLinkKinds().size()==0) {
			System.out.println("Sorry no linkkinds to be removed !");
			return;
		}
		LinkedList<LinkKind> Lk = new LinkedList<>();
		LinkedList<Link> Li = new LinkedList<>();
		
		for(LinkKind lk: information.getLinkKinds())
			Lk.add(lk);
		for(Link l : information.getLinks())
			Li.add(l);
		
		int option=0;
		for(int i=0;i<Lk.size();i++) {
			System.out.print((i+1)+")");
			Lk.get(i).display();;
		}
		if(Lk.size()>1)
		System.out.printf("Please choose one to be removed between 1 and %d",Lk.size());
		else
			System.out.println("Please choose one to be removed ");
		option=read.nextInt();
		boolean check=CheckInRange(option,Lk);
		if(!check)
			return;
		String IdLk = Lk.get(option-1).getIdentifier();
		//1 remove all links related to that kind
		for (int i=0;i<Li.size();i++) {
			Link l = Li.get(i);
			if(l.getKind().getIdentifier().equals(IdLk))
				information.removeLinkByIdentifier(l.getIdentifier());
		}
		
		//2 remove The linkkind
		information.removeLinkKindByIdentifier(IdLk);
		System.out.println("LinkKind removed !");
	}

	private static void RemoveArtifactUI(TraceabilityInfo information) {
		if(information.getArtifacts().size()==0) {
			System.out.println("Sorry no Artifacts to be removed");
			return;
		}
		LinkedList<Artifact> Ar = new LinkedList<>();
		LinkedList<Link> Li =new LinkedList<>();
		
		for(Artifact a : information.getArtifacts())
			Ar.add(a);
		for(Link l : information.getLinks())
			Li.add(l);
		
		for(int i=0;i<Ar.size();i++) {
			System.out.print((i+1)+")");
			Ar.get(i).display();
		}
		if(Ar.size()>1)
		System.out.printf("Please choose one to be removed between 1 and %d",Ar.size());
		else
			System.out.println("Please choose one to be removed");
		int option=read.nextInt();
		boolean check=CheckInRange(option,Ar);
		if(!check)
			return;
		String IdAr=Ar.get(option-1).getIdentifier();
		// remove all links including that artifact
		for(int i=0;i<Li.size();i++) {
			Link L1 = Li.get(i);
			boolean contain = false;
			if(L1.getFirst().getIdentifier().equals(IdAr) || L1.getSecond().get(0).getIdentifier().equals(IdAr) ) 
				contain=true;
			LinkedList<Artifact> temp = L1.getSecond();
			for(int j=1;j<temp.size();j++)
				if(temp.get(j).getIdentifier().equals(IdAr))
					contain=true;
			if(contain)
				information.removeLinkByIdentifier(L1.getIdentifier());
				
		}
		//remove the Artifact
		information.removeArtifactByIdentifier(IdAr);
		System.out.println("Artifact removed !");
		
		
	
	}

	private static void RemoveLinkUI(TraceabilityInfo information) {
		if(information.getLinks().size()==0) {
			System.out.println("Sorry no Links to be removed ");
			return;
		}
			
		LinkedList<Link> Li =new LinkedList<>();
		for(Link l : information.getLinks())
			Li.add(l);
		
		for (int i=0 ;i<Li.size();i++) {
			System.out.print((i+1)+")");
			Li.get(i).display();
		}
		if(Li.size()>1)
		System.out.printf("Please choose one to be removed between 1 and %d",Li.size());
		else
			System.out.println("Please choose one to be removed");
		int option= read.nextInt();
		boolean check=CheckInRange(option,Li);
		if(!check)
			return;
		information.removeLinkByIdentifier(Li.get(option-1).getIdentifier());
		System.out.println("Link removed !");
	}

	private static void RemoveUI() {
		System.out.println("-------- Remove a traceability element--------");
		System.out.println("(1) Remove an artifact kind");
		System.out.println("(2) Remove a link kind");
		System.out.println("(3) Remove an artifact");
		System.out.println("(4) Remove a link");
		System.out.println("(5) Back");
		System.out.println("Choose an option (betweem 1 and 5):");
		
	}
	
	private static void QueryUI() {
		System.out.println("-------- Query traceability information--------");
		System.out.println("(1) Display statistics");
		System.out.println("(2) Find forward links");
		System.out.println("(3) Find backward links");
		System.out.println("(4) Back");
	}

	private static void FindForwardLinksUI(TraceabilityInfo information) {
		if(information.getArtifacts().size()==0) {
			System.out.println("Sorry no existing artifacts ");
			return;
		}
		if(information.getLinks().size()==0) {
			System.out.println("Sorry no existing links");
			return;
		}
			
		LinkedList<Artifact> Ar= information.getArtifacts();
		for(Artifact a : Ar)
			a.display();
		
		if(Ar.size()>1)
		System.out.printf("Please choose one of the following Artifacts between 1 and %d",Ar.size());
		else 
			System.out.println("Please choose one ");
		int option =read.nextInt();
		boolean check=CheckInRange(option,Ar);
		if(!check)
			return;
		
		LinkedList<Link> Li = information.getLinks();
		boolean Exist = false;
		for(Link l : Li) {
			if(l.getFirst().getIdentifier().equals(Ar.get(option-1).getIdentifier())) {
				l.display();
				Exist=true;
			}
			
		}
		
		if(!Exist)
			System.out.println("Sorry no ForwardLink(s) for "+Ar.get(option-1).getIdentifier() );
	}
	
	private static void FindBackwordLinksUI(TraceabilityInfo information) {
		if(information.getArtifacts().size()==0) {
			System.out.println("Sorry no existing artifacts ");
			return;
		}
		if(information.getLinks().size()==0) {
			System.out.println("Sorry no existing links");
			return;
		}
			
		LinkedList<Artifact> Ar= information.getArtifacts();
		for(Artifact a : Ar)
			a.display();
		
		if(Ar.size()>1)
		System.out.printf("Please choose one of the following Artifacts between 1 and %d",Ar.size());
		else 
			System.out.println("Please choose one ");
		int option =read.nextInt();
		boolean check=CheckInRange(option,Ar);
		if(!check)
			return;
		
		LinkedList<Link> Li = information.getLinks();
		boolean Exist = false;
		for(Link l : Li) {
			for(Artifact a : l.getSecond()) {
				if(a.getIdentifier().equals(Ar.get(option-1).getIdentifier())) {
					l.display();
					Exist=true;
				}
			}
		}
		
		
		if(!Exist)
			System.out.println("Sorry no BackwardLink(s) for "+Ar.get(option-1).getIdentifier() );
		
	}

	private static void SaveUI(TraceabilityInfo information) throws FileNotFoundException, UnsupportedEncodingException{
		System.out.println("Do you want to save the information with the same file or Another file ?");
		System.out.println("1) Same file");
		System.out.println("2) Another file");
		int option=read.nextInt();
		if(option==1) {
			information.save();
			System.out.println("Information saved !");
		}
		else if(option==2) {
			System.out.println("Please provide the file name in form of filename.txt");
			String fname=read.next();
			information.setFilename(fname);
			information.save();
			System.out.println("Information saved !");
		}
		else 
			System.out.println("Please try again and provide correct number ");
		
	}
}