package Estructura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ArbreB {
	private class NodeA {
		String contents;
		ArbreB yes, no;	
		NodeA(String contents) {
			//Constructor 1. Inicialitza als atributys yes i no a null
			this(contents,null,null);
		}
		NodeA(String pregunta, ArbreB a1, ArbreB a2) {
			//Constructor 2. Crea el node i l'inicialitza amb els par�metres
			this.contents = pregunta;
			this.yes = a1;
			this.no = a2;
		}
	}
	// Atributs: Taula de 2 posicions
	private NodeA[] root = {null, null};

	/* CONSTRUCTORS */
	public ArbreB(ArbreB a1, ArbreB a2, String pregunta) {
		//Constructor 1. Crea un arbre amb una pregunta i dos respostes
		root[0] = new NodeA(pregunta, a1, a2);
	}
	public ArbreB() {
		root[0] = null;
	}	
	public ArbreB(String filename) throws Exception{
		root[0] = this.loadFromFile(filename);
	}

	/* PUBLIC METHODS */
	public boolean isEmpty() {
		return this.root == null;
	}
	
	public NodeA[] arrel() {
		return this.root;
	}
	
	public ArbreB prgYes() {
		return this.root[0].yes;
	}
	
	public ArbreB prgNo() {
		return this.root[0].no;
	}
	
	public void rewind() {
		this.root[1] = this.root[0];
	}
	/* True if the current node is an answer (a leaf) */
	public boolean atAnswer() {
		//COMPLETE
		return root[0].no==null && root[0].yes==null;
	}
	/* move current to yes-descendant of itself */
	public void moveToYes() {
		root[0].yes = new ArbreB(null, null, this.getContents()); 
	}
	/* move current to no-descendant of itself */
	public void moveToNo() {
		root[0].no = new ArbreB(null, null, this.getContents());
	}
	/* get the contents of the current node */
	public String getContents() {
		return root[0].contents;
	}
	 /* Substituir la informaci� del node actual
	 * per la pregunta donada pel jugador. Previament crear el node que ser� el
	 * seu fill dret, resposta no encertada, amb la informaci� del node actual.
	 */
	public void improve(String question, String answer) {
		this.moveToNo();
		this.root[0].contents = answer;
		this.moveToYes();
		this.root[0].contents = question;
		
	}
	private void preorderWrite(BufferedWriter buw) throws Exception {
		//Imprescindible que la implementaci� sigui recursiva
		if (this.isEmpty())	throw new Exception("Arbre inexistent");
		
		String frase=this.getContents(); 
		
		if(this.atAnswer()) {
			if(!(frase.substring(frase.length()-1).equals("?"))) root[0].contents=frase+"?";
		}
		else { 
			if((frase.substring(frase.length()-1).equals("?"))) root[0].contents=frase.substring(0,frase.length()-2);
			}
		buw.write (frase);
		buw.newLine();
		this.prgYes().preorderWrite(buw);
		this.prgNo().preorderWrite(buw);
	}
	
	/* Saves contents of tree in a text file */
public void save(String filename) throws Exception {
		BufferedWriter buw = null;
		try {
			//buw = new BufferedWriter(new FileWriter(filename));
			buw = new BufferedWriter(new FileWriter(new File(filename)));
			this.preorderWrite(buw);
			buw.close();

		} catch (IOException e) {
			System.err.println("saveToTextFile failed: " + e);
			System.exit(0);
		}
	}
private NodeA loadFromFile(String filename){
		//Imprescindible implementaci� recursiva
		BufferedReader bur = null;
		NodeA node = new NodeA("");
		try {
			bur = new BufferedReader(new FileReader(filename));
			node.contents= bur.readLine();
			node.yes.loadFromFile(filename);
			node.no.loadFromFile(filename);
			bur.close();

		} catch (IOException e) {
			System.err.println("initialization testField failed: " + e);
			System.exit(0);
		}
		return node;
	}
	
	public void visualitzarAnimals() {
		/* Following the guidelines indicated in the statement of practice */
		if (this.atAnswer()) {
			System.out.println(this.getContents());
		}
		else {
			this.prgYes().visualitzarAnimals();
			this.prgNo().visualitzarAnimals();
		}
		
		
	}
	public int quantsAnimals() {
		int resultat=0;
		if (this.atAnswer()) {
			resultat++;
		}
		else {
			this.prgYes().visualitzarAnimals();
			this.prgNo().visualitzarAnimals();
		}
		return resultat;
		/* Following the guidelines indicated in the statement of practice */
		/* COMPLETE */
	}
	public int alsada() {
		return 0;
		/* COMPLETE */
		// Imprescindible invocar a un m�tode la classe NodeA
	}
	public void mostraPreguntes() {
		/* COMPLETE */
		// Visualitza a pantalla les preguntes
		// Imprescindible invocar a un m�tode la classe NodeA
		if (!this.atAnswer()) {
			System.out.println(this.getContents());
		}
		else {
			this.prgYes().visualitzarAnimals();
			this.prgNo().visualitzarAnimals();
		}
		
	}
}