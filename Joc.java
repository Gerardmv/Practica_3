package Joc;

import Estructura.*;
import Keyboard.Keyboard;

public class Joc {

	public static void main(String[] args) {
		String missatge = null, animal1, animal2;
		boolean continuar = true, trobat = false;
		ArbreB arrel = null;

		System.out.println("Benvingut al joc dels animals");
		System.out.println("Primer de tot, vols carregar un fitxer? ('Si' o 'No')");
		missatge = comprovador(); // TODO input carrega fitxersi
		if (missatge.equalsIgnoreCase("Si")) {
			System.out.println("Perfecte! Digues el nom del fitxer que vols carregar!");
			missatge = Keyboard.readString();
			try {
				arrel = new ArbreB(missatge);
			} catch (Exception e) {
				System.out.println("El document no existeix");
			}
			System.out.println("L'arbre conté els següents animals:");
			arrel.visualitzarAnimals();
			System.out.println("En total té: " + arrel.quantsAnimals());
			System.out.println("L'alçada és de: " + arrel.alsada());
			System.out.println("Vols visualitzar les preguntes del arbre?");
			missatge = comprovador();
			if (missatge.equalsIgnoreCase("si"))
				arrel.mostraPreguntes();
			System.out.println("JUGEM!!!");
		} else {
			System.out.println("Perfecte! Per començar afageix una pregunta");
			System.out.println("Indica la pregunta de l'arrel de l'arbre");
			missatge = Keyboard.readString();
			System.out.println("Indica Animal per Si");
			animal1 = Keyboard.readString();
			System.out.println("Indica Animal per No");
			animal2 = Keyboard.readString();
			arrel = new ArbreB(new ArbreB(null, null, animal1), new ArbreB(null, null, animal2), missatge);
		}
		ArbreB aux = arrel;
		while (continuar) {
			while (!trobat) {
				System.out.println(aux.getContents());
				missatge = comprovador();
				if (missatge.equalsIgnoreCase("Si")) {
					if (aux.prgYes().atAnswer()) {
						comprovarresultat(aux.prgYes());
						trobat = true;
					} else {
						aux = aux.prgYes();
					}
				}

				else {
					if (aux.prgNo().atAnswer()) {

						comprovarresultat(aux.prgNo());
						trobat = true;
					} else {
						aux = aux.prgNo();
					}
				}	
			}
			arrel.rewind();
			aux = arrel;
			System.out.println("Vols seguir jugant? ('Si' o 'No')");
			missatge = comprovador();
			if (missatge.equalsIgnoreCase("Si")) {
				trobat = false;
				arrel.rewind();
				System.out.println("Alçada: "+arrel.alsada());
			} else {
				continuar = false;
			}
		}
		System.out.println("Vols guardar l'arbre en un fitxer? ('Si' o 'No')");
		missatge = comprovador();
		if (missatge.equalsIgnoreCase("Si")) {
			System.out.println("Indica nom del fitxer:");
			missatge = Keyboard.readString();
			try {
				arrel.save(missatge);
			} catch (Exception e) {
				System.out.println("Error");
			}
		}

	}

	private static void comprovarresultat(ArbreB aux) {
		String missatge, animal1;
		System.out.println(aux.getContents());
		System.out.println("Es aquest l'animal que pensaves? ('Si' o 'No')");
		missatge = comprovador();
		if (missatge.equals("Si"))
			System.out.println("L'he endivinat!!!");
		else {
			System.out.println("Digues nom animal que has pensat");
			missatge = Keyboard.readString();
			System.out.println("Pregunta per poder reconeixer-lo");
			animal1 = Keyboard.readString();
			aux.improve(missatge, animal1);
		}
	}

	private static String comprovador() {
		String missatge = Keyboard.readString();
		while (!missatge.equalsIgnoreCase("Si") && !missatge.equalsIgnoreCase("No")) {
			System.out.println("Siusplau, escriu 'Si' o 'No'");
			missatge = Keyboard.readString();
		}
		return missatge;

	}

}
