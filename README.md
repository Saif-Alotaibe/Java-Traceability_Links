# Java-Traceability_Links
# Introduction
We would like to develop an application that helps with the management of traceability in software engineering. Traceability information is stored in a text file. The application will be used to query and update the information stored in this file.

# Traceability concepts
The two important concepts with this respect are ArtifactKind and LinkKind. An artifact kind represents a set of traceable entities. It’s defined by

1. an identifier: a unique identifier that can be used to refer to the artifact kind.
2. a name,
3. a description.

Examples of artifact kinds: software feature, requirement, use case, activity diagram, sequence diagram, class, and test case. A link kind represents a set of traceability links between two artifact kinds. It’s defined by

1. an identifier: a unique identifier that can be used to refer to the link kind.
2. a name,
3. a description,
4. the identifier of the first artifact kind (previously defined)
5. the alias of the second artifact kind (previously defined), with multiplicity Examples of link kinds: dependency between requirement, refinement of a software feature into requirements, and realization of a use case by a sequence diagram.
File Format
Traceability information is stored in a text file. This file is composed of three parts:

Definitions of artifact kinds. Each artifact kind is defined as follows:
def artifact identifier name description

Definitions of link kinds. Each link kind is defined textually as follows:
def link identifier name firstArtifactKindIdentifier secondArtifactKindIdentifier [ ] description

The star (*) after the second artifact kind identifier is optional. When present, it will mean that several artifacts of this kind may be linked by this link (a one-to-many link). 3. Definition of actual artifacts and links. An artifact is defined textually by providing its kind and a unique identifier as follows:

a artifactKindIdentifier identifier

A traceability link is defined textually by providing its kind, a unique identifier, the identifier of the first artifact, and the identifier(s) of the linked artifacts as follows (starts with a small L):

l LinkKindIdentifier identifier firstArtifactIdentifier linkedArtifactIdentifier, ...

An identifier is a single word string (that contains only letters and digits). A name or a description is a double quoted string that contains letters, digits, and whitespaces. Each definition ends with a dot. A comment line starts with a semi-colon(;).

# Examples
We give in this section examples of definitions of traceability information. We start by defining three artifact kinds: software feature, requirement, use case:

def artifact sf "Software feature" "A high level characteristic or functionality".

def artifact req "Requirement" "A functionality or characteristic of a software".

def artifact uc "Use case" "An interaction with the system that provides a value".

We then define two link kinds:

def link depends "depends" req req* "Dependency between two requirements".

def link refines "refines" req sf "A requirement refines a software feature".

We define actual artifacts: one software feature and four requirements

a sf Feature1.

a req Req1.

a req Req2.

a req Req3.

a req Req4.

and actual links: a dependency link between requirements and a refinement link between a requirement and a software feature.

l depends l1 Req1 Req2 Req4.

l refines l2 Req2 Feature1.

# Getting Started
Prerequisites:

The program written in java , jdk must be installed to run the program https://www.oracle.com/sa/java/technologies/javase-downloads.html .
Text file must be created and included in main class arguments before running the program . We already created text file named (traceability) to run the program , here an explanaition of main arguments and how to access them https://stackoverflow.com/questions/890966/what-is-string-args-parameter-in-main-method-java .
Inputs to the program must follow specific format which we described above.
The program provide a textual interface to interact.

Interface Structure :

(1) Edit Information:

(1) Add new element:

       (1) Add new Artifact Kind
       
       (2) Add new Link kind 
       
       (3) Add new Artifact 
       
       (4) Add new Link 
       
       (5) Back
       
(2) Remove element:

       (1) Remove Artifact Kind
       
       (2) Remove Link kind 
       
       (3) Remove Artifact 
       
       (4) Remove Link
       
       (5) Back
       
(3) Back 
(2) Query Information:

(1) Display Statstics 

(2) Find Forward Links

(3) Find Backword Links

(4) Back
(3) Save Information

(4) Exit

# Provided Code
The attached archive contains Java code organized into three packages.

Package concepts contains classes that represent traceability elements:
• Concept: an abstract class, is the superclass of the other classes in the package • ArtifactKind • LinkKind • Artifact • Link All the classes contain the appropriate attributes and getters and setters. The package concepts contains also the class TraceabilityInfo that encapsulates traceability information in the form of lists of artifact kinds, link kinds, artifacts, and links. Necessary methods for the management of these elements are defined:

• methods used to add new elements: addX where X∈{ArtifactKind, LinkKind, Artifact, Link}.

• methods used to remove elements by identifier: removeXByIdentifier where X∈{ArtifactKind, LinkKind, Artifact, Link}.

• methods used to retrieve elements by identifier: getXByIdentifier where X∈{ArtifactKind, LinkKind, Artifact, Link}.

• methods used to retrieve all the elements: getXs where X∈{ArtifactKind, LinkKind, Artifact, Link}.

Package proc contains classes used to read the contents of a text file, analyze them, create instances of the appropriate traceability elements classes, and store them in an instance of TraceabilityInfo Class Reader defines methods used for low level reading of the contents of the text file:
– readWord: tries to read a string composed of letters and digits and return it. If this was not possible, it returns null. – readDoubleQuotedString: tries to read a double quoted string composed of letters, digits, and whitespaces and return it. If this was not possible, it returns null. – readDot: tries to read a string composed of one dot and return it. If this was not possible, it returns null.

Class Analyzer defines methods that use the methods defined in Reader to get the contents of a text file, analyze them, create instances of the appropriate traceability elements classes, and store them in an instance of TraceabilityInfo.

– analyze: the principal analysis method. It reads the first tokens and then calls the appropriate analysis method of the class to analyze the discovered traceability element. – analyzeX where X ArtifactKind, LinkKind, Artifact, Link : uses Reader method to analyze a traceability element and store it in an instance of TraceabilityInfo.

Package main contains a main class Main. The defined program takes as ar- gument the name of the file to be analyzed. The main methods shows you how the constructors of Reader and Analyzer are called. An instance of TracebilityInfo is then created. Then the method analyze is called in a loop to populate this instance until an error or the end of the file is reached.
