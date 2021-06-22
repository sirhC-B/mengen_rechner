import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		
		input();	// Aufruf der input()-Methode beim Start des Programms

		}
	
	/** 
	 * input(), liest mit hilfe der Scanner-Class den user-input ein, 
	 * leitet ihn weiter und faengt fehlerhafte Eingaben ab.
	 * Nach Ausfuehrung der Berrechnung wird sie erneut ausgefuehrt bis eine
	 * leere Eingabe folgt, dann wird das Programm beendet.
	 * 
	 */
	
	public static void input(){
		
		Scanner scan  = new Scanner(System.in);
		System.out.println("Enter Menge:");
		String in = scan.nextLine();
		
		
		if (in.isEmpty()) {		//prueft ob leere Eingabe --> END
			System.out.println("Auf Wiedersehen!");
			scan.close();
			System.exit(0);
		}
		
		else {
			try {
				System.out.println(mengen_rechner(in).toString() +"\n"); // weiterleitung an die Berrechnungsmethoden
			}	
			catch(Exception e) { //wirft exception bei unzulaessiger Eingabe
				System.err.println("\nFehlerhafte Eingabe!");
				System.out.println("Zulaessige Syntax: [1,2,3,4] + [5,6,7]\n");
			}
				
		}
		input(); // ruft input() fuer eine erneute Rechnung auf
	}
	
	
	/*
	 * mengen_rechner(), ist die Verwaltungsmethode, sie leitet den input erst an
	 * die Hilfsmethoden weiter und dann im richtigen Format an die jeweillige
	 * Berrechnungsmethode
	 */
	
	public static List<Integer> mengen_rechner(String input1){
		List<String> operant = new ArrayList<String>();
		List<String> split = new ArrayList<String>();
		List<Integer> int_lst1 = new ArrayList<Integer>();		
		List<Integer> int_lst2 = new ArrayList<Integer>();
		List<Integer> result = new ArrayList<Integer>();
		
		operant = operant(input1); 					// zieht den Operant aus dem input 
		
		if (operant.size()>1) {
			throw new ArithmeticException(); 
		}
		
		split = split_input(input1,operant);		// teilt den input in zwei Strings und in einer String-liste zurueck
		int_lst1 = transform_to_int(split.get(0));	// wandelt die strings fuer die Rechnung in int-listen um
		int_lst2 = transform_to_int(split.get(1));
		
		switch(operant.get(0)) {					// leitet listen je nach operant an die richtige Berrechnungsmethode weiter
			case "*":
				result = durchschnitt(int_lst2,int_lst1);
				break;
			case "+":
				result = vereinigung(int_lst2,int_lst1);
				break;
			case "-":
				result = differenz(int_lst2,int_lst1);
				break;
		}
		
		
		return result;
	}
	
	/*
	 * transform_to_int(), empfaengt einen String, entfernt illegale character
	 * und wandelt sie in Integer-listen um.
	 * Falls eine leere Liste uebergeben wird, gibt er diese gleich zurueck.
	 * 
	 */
	
	public static List<Integer> transform_to_int(String input2){
		List<Integer> int_lst = new ArrayList<Integer>();
			
		input2 = input2.replace('[',' ');	// entfernt die eckigen Klammern
		input2 = input2.replace(']',' ');
		input2 = input2.replaceAll(" ", "");	// entfernt die Leerzeichen
		
		List<String> str_lst = new ArrayList<>(Arrays.asList(input2.split(","))); // erstellt aus dem String eine String-liste
		
		if(str_lst.contains("")) { // gibt bei lerrem String um Exceptions zu vermeiden eine leere Liste zurueck
			
			return int_lst;
		}
		
		else {	// wandelt die String-liste in eine Integer-liste um und gibt sie zueruck
			for (int i = 0; i<str_lst.size();i++) 
				int_lst.add(Integer.parseInt(str_lst.get(i)));	
			return int_lst;
	
		}
	}
	
	/*
	 * operant(), prueft welche Rechenoperation ist in dem input befindet und gibt diese zurueck.
	 * 
	 */
	
	public static List<String> operant(String input3) {
		List<String> result_operant = new ArrayList<String>();
		
		if (input3.contains("*")==true) {
			result_operant.add("*");
		}
		if(input3.contains("+")==true) {
			result_operant.add("+");
		}
		if(input3.contains("-")==true) {
			result_operant.add("-");
		}
		
		ceck_double_operant(input3,result_operant);
		
		return result_operant;
	}
	
	/*
	 *  ceck_double_operant(), prueft ob es den gleichen operator mehr als ein mal gibt.
	 */
	
	public static void ceck_double_operant(String input_string, List<String> operant_in1) {
		char operant_c = operant_in1.get(0).charAt(0); // Operator
		int op_count = 0;

		for(int i = 0; i<input_string.length(); i++) { // geht durch den string und erhoeht den counter bei vorkommen des Operators
				char c = input_string.charAt(i);
			    if(operant_c == c) op_count++;
			    if(op_count >= 2)	throw new ArithmeticException();	// schmeißt exception wenn op_count groesser als 1 ist 
		}
		    	
	}
	
	/*
	 *  split_input(), teilt den input an der Stelle des Operators in zwei Strings und 
	 *  gibt sie in einer String-liste zurueck
	 */
	
	public static List<String> split_input(String input4, List<String> operant_in) {
		String operant_s = operant_in.get(0); // Operator
		List<String> splitlist = new ArrayList<>(Arrays.asList(input4.split("\\"+operant_s))); // Teilung der Strings

		
		return splitlist;
	}
	
	/*
	 * durchschnitt(), ist die Berrechnungsmethode fur die Schnittmenge (*)
	 * sie prueft beide listen auf gleiche Elemente und fuegt diese zu eine 
	 * neuen Liste hinzu.
	 * Am Ende werden die Duplikate in einer extra Methode entfernt und das Ergebnis zurueck gegeben.
	 * 
	 */
	
	public static List<Integer> durchschnitt(List<Integer> m1, List<Integer> m2) {
		List<Integer> result_durch = new ArrayList<Integer>();		
		ListIterator<Integer> iter_durch = m1.listIterator();

		while(iter_durch.hasNext()) {			// iteriert durch Elemente der listen
			int i = iter_durch.next();
			for (int j = 0; j<m2.size();j++) {	
				if(i == m2.get(j)) {			// prueft jedes Element der einen Liste mit jedem der anderen auf Gleichheit
					result_durch.add(m2.get(j));	// fuegt der Ergebnisliste hinzu
				}
			}			
		}
		
		result_durch = duplikate_entfernen(result_durch); // entfernt Duplikate
		return result_durch;
	}
	
	
	/*
	 * vereinigung(), ist die Berrechnungsmethode fur die Vereinigung (+)
	 * sie vereint beide Listen und entfernt die Duplikate
	 * 
	 */
	
	public static List<Integer> vereinigung(List<Integer> m1, List<Integer> m2) {
		List<Integer> tmp = new ArrayList<Integer>();
		List<Integer> result_verein = new ArrayList<Integer>();
		ListIterator<Integer> iter_verein = m1.listIterator();
		tmp.addAll(m2); // fuegt erste Teilliste hinzu
		
		while(iter_verein.hasNext()) { // fuegt zweite Teilliste hinzu
			tmp.add(iter_verein.next());
		}
		
		result_verein = duplikate_entfernen(tmp); // entfernt Duplikate
		return result_verein;
	}
	
	
	/*
	 * differenz(), ist die Berrechnungsmethode fur die Differenz (-)
	 * sie bildet eine Vereinigung aus beiden Listen, und eine Schnittmenge.
	 * Fuer die Differenz vergleicht sie beide Listen und fuegt alle einzigartigen 
	 * Elemente aus Liste A in die Ergebnisliste
	 * 
	 */
	
	public static List<Integer> differenz(List<Integer> m1, List<Integer> m2){
		List<Integer> tmp1 = new ArrayList<Integer>();
		List<Integer> tmp2 = new ArrayList<Integer>();
		List<Integer> result_diff = new ArrayList<Integer>();		
		ListIterator<Integer> iter_diff;
				
		tmp1.addAll(vereinigung(m1,m2)); // bildet Vereinigung
		tmp2.addAll(durchschnitt(m1,m2)); // bildet Schnittmenge
		iter_diff = tmp1.listIterator();
	
		while(iter_diff.hasNext()) {	// iteriert durch die Vereinigungsliste
			int i = iter_diff.next();
			if(!tmp2.contains(i))	// prueft ob die Elemente einzigartig sind
				result_diff.add(i);	// wenn ja, zur Ergebnisliste hinzu
		}	
		
		return result_diff;		
	}
	
	/*
	 * duplikate_entfernen(), entfernt alle doppelten Elemente einer Liste
	 * 
	 */
	
	public static List<Integer> duplikate_entfernen(List<Integer> menge) { 
		List<Integer> result_dupl = new ArrayList<Integer>();
		ListIterator<Integer> iter_dupl = menge.listIterator();
		
		while(iter_dupl.hasNext()) { // iteriert durch die Liste
			int i = iter_dupl.next();
			if(!result_dupl.contains(i)) // fuegt jeweills nur einzigartige Element in die Ergebnisliste hinzu 
				result_dupl.add(i);
		}	
		
		return result_dupl;
		
	}
}

	

