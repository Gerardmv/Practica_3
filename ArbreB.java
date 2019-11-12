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
			// Constructor 1. Inicialitza als atributys yes i no a null
			this(contents, null, null);
		}

		NodeA(String pregunta, ArbreB a1, ArbreB a2) {
			// Constructor 2. Crea el node i l'inicialitza amb els paràmetres
			this.contents = pregunta;
			this.yes = a1;
			this.no = a2;
		}

		public void visualitzarAnimalsA() {
			/* Following the guidelines indicated in the statement of practice */
			if (!contents.substring(contents.length() - 1).equals("?")) {
				System.out.println(contents);
			}
			if (yes != null)
				yes.root[0].visualitzarAnimalsA();
			if (no != null)
				no.root[0].visualitzarAnimalsA();
		}

		public int quantsAnimalsA() {
			int resultat = 0;
			if (contents.substring(contents.length() - 1).equals("?"))
				resultat++;

			if (yes != null)
				yes.root[0].quantsAnimalsA();
			if (no != null)
				no.root[0].quantsAnimalsA();
			return resultat;
			/* Following the guidelines indicated in the statement of practice */
			/* COMPLETE */
		}

		public int alsadaA() {
			int y = 1, n = 1;
			if (yes != null) y+= yes.root[0].alsadaA();
			if (no != null) n+= no.root[0].alsadaA();
			return n>=y ? n : y;
			/* COMPLETE */
			// Imprescindible invocar a un mètode la classe NodeA
		}

		public void mostraPreguntesA() {
			/* COMPLETE */
			// Visualitza a pantalla les preguntes
			// Imprescindible invocar a un màtode la classe NodeA
			if (contents.substring(contents.length() - 1).equals("?"))
				System.out.println(contents);
			if (yes != null)
				yes.root[0].mostraPreguntesA();
			if (no != null)
				no.root[0].mostraPreguntesA();
		}
	}

	// Atributs: Taula de 2 posicions
	private NodeA[] root = { null, null };

	/* CONSTRUCTORS */
	public ArbreB(ArbreB a1, ArbreB a2, String pregunta) {
		// Constructor 1. Crea un arbre amb una pregunta i dos respostes
		root[0] = new NodeA(pregunta, a1, a2);
	}

	public ArbreB() {
		root[0] = null;
	}

	public ArbreB(String filename) throws Exception {
		root[0] = this.loadFromFile(filename);
		root[1] = root[0];
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
		// COMPLETE
		return root[0].no == null && root[0].yes == null;
	}

	/* move current to yes-descendant of itself */
	public void moveToYes() {
		root[0].yes = new ArbreB(null, null, this.getContents());
	}

	/* move current to yes-descendant of itself */
	public void moveToNo() {
		root[0].no = new ArbreB(null, null, this.getContents());
	}

	/* get the contents of the current node */
	public String getContents() {
		return root[0].contents;
	}

	/*
	 * Substituir la informació del node actual per la pregunta donada pel jugador.
	 * Previament crear el node que serà el seu fill dret, resposta no encertada,
	 * amb la informació del node actual.
	 */
	public void improve(String answer, String question) {
		this.moveToNo();
		this.root[0].contents = answer;
		this.moveToYes();
		this.root[0].contents = question;

	}

	private void preorderWrite(BufferedWriter buw) throws Exception {
		// Imprescindible que la implementació sigui recursiva
		if (this.isEmpty())
			throw new Exception("Arbre inexistent");

		String frase = this.getContents();

		if (this.atAnswer()) {
			if (!(frase.substring(frase.length() - 1).equals("?")))
				root[0].contents = frase + "?";
		} else {
			if ((frase.substring(frase.length() - 1).equals("?")))
				root[0].contents = frase.substring(0, frase.length() - 2);
		}
		buw.write(frase);
		buw.newLine();
		this.prgYes().preorderWrite(buw);
		this.prgNo().preorderWrite(buw);
	}

	/* Saves contents of tree in a text file */
	public void save(String filename) throws Exception {
		BufferedWriter buw = null;
		try {
			// buw = new BufferedWriter(new FileWriter(filename));
			buw = new BufferedWriter(new FileWriter(new File(filename)));
			this.preorderWrite(buw);
			buw.close();

		} catch (IOException e) {
			System.err.println("Hi ha hagut un problema al inicialitzar el fitxer");
			System.exit(0);
		}
	}

	private NodeA loadFromFile(String filename) {
		// Imprescindible implementació recursiva
		BufferedReader bur = null;
		NodeA node = null;

		try {
			bur = new BufferedReader(new FileReader(filename));
			node = loadR(bur);
			bur.close();
			return node;

		} catch (IOException e) {
			System.err.println("Hi ha hagut un problema al inicialitzar el fitxer");
			System.exit(0);
		}
		return node;
	}

	private NodeA loadR(BufferedReader bur) {
		NodeA node;
		try {
			node = new NodeA(bur.readLine());
			if (node.contents.substring(node.contents.length() - 1).equals("?")) {
				if (node.yes == null) {
					node.yes = new ArbreB();
					node.yes.root[0] = node.yes.loadR(bur);
				}
				if (node.no == null) {
					node.no = new ArbreB();
					node.no.root[0] = node.no.loadR(bur);
				}
			}
			return node;

		} catch (IOException e) {
			System.err.println("Hi ha hagut un problema al inicialitzar el fitxer");
			System.exit(0);
		}
		return null;

	}

	public void visualitzarAnimals() {
		/* Following the guidelines indicated in the statement of practice */
		if (root[0] != null)
			root[0].visualitzarAnimalsA();
	}

	public int quantsAnimals() {
		int resultat = 0;
		if (root[0] != null)
			resultat = root[0].quantsAnimalsA();
		return resultat;
		/* Following the guidelines indicated in the statement of practice */
		/* COMPLETE */
	}

	public int alsada() {
		int resultat = 0;
		if (root[0] != null)
			resultat = root[0].alsadaA();
		return resultat;
		/* COMPLETE */
		// Imprescindible invocar a un mètode la classe NodeA
	}

	public void mostraPreguntes() {
		/* COMPLETE */
		// Visualitza a pantalla les preguntes
		// Imprescindible invocar a un màtode la classe NodeA
		if (root[0] != null)
			root[0].mostraPreguntesA();

	}

}
