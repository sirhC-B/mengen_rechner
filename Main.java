import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Main {
	public static void main(String[] args) {
		
		
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> list1 = new ArrayList<Integer>();
	
		int array[] = {34,35,36,37,38,40};
		int array1[] = {23,24,25,26,40,34,34,46,35,36};
		
		
		
		for (int i = 0; i<array.length; i++)
			list.add(array[i]);
		for (int i = 0; i<array1.length; i++)
			list1.add(array1[i]);

		
		input();


		}
	
	public static void input(){
		Scanner scan  = new Scanner(System.in);
		System.out.println("Enter Menge:");
		
		String in = scan.nextLine();
		
		if (in.isEmpty()) {
			System.out.println("Auf Wiedersehen!");
			System.exit(0);
		}
		
		else {
			try {
				System.out.println(mengen_rechner(in).toString() +"\n");
			}	
			catch(Exception e) {
				System.err.println("\nFehlerhafte Eingabe!");
				System.out.println("Zulaessige Syntax: [1,2,3,4] + [5,6,7]\n");
			}
				
		}
		input();
	}
	
	
	public static List<Integer> mengen_rechner(String input1){
		List<String> operant = new ArrayList<String>();
		List<String> split = new ArrayList<String>();
		List<Integer> int_lst1 = new ArrayList<Integer>();		
		List<Integer> int_lst2 = new ArrayList<Integer>();
		List<Integer> result = new ArrayList<Integer>();
		
		operant = operant(input1);
		split = split_input(input1,operant);
		int_lst1 = transform_to_int(split.get(0));
		int_lst2 = transform_to_int(split.get(1));
		
		switch(operant.get(0)) {		
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
	
	
	
	public static List<Integer> transform_to_int(String input2){
		List<Integer> int_lst = new ArrayList<Integer>();
		
		input2 = input2.replace('[',' ');
		input2 = input2.replace(']',' ');
		input2 = input2.replaceAll(" ", "");
		
		List<String> str_lst = new ArrayList<>(Arrays.asList(input2.split(",")));
		
		
		for (int i = 0; i<str_lst.size();i++) 
			int_lst.add(Integer.parseInt(str_lst.get(i)));
		
		return int_lst;
	}
	
	
	
	public static List<String> operant(String input3) {
		List<String> result_operant = new ArrayList<String>();
		
		if (input3.contains("*")==true) {
			result_operant.add("*");
		}
		else if(input3.contains("+")==true) {
			result_operant.add("+");
		}
		else if(input3.contains("-")==true) {
			result_operant.add("-");
		}
		
		return result_operant;
	}
	
	public static List<String> split_input(String input4, List<String> operant_in) {
		String operant_s = operant_in.get(0);
		List<String> splitlist = new ArrayList<>(Arrays.asList(input4.split("\\"+operant_s)));
		
		
		return splitlist;
	}
	

	public static List<Integer> durchschnitt(List<Integer> m1, List<Integer> m2) {
		List<Integer> result_durch = new ArrayList<Integer>();		
		ListIterator<Integer> iter_durch = m1.listIterator();

		while(iter_durch.hasNext()) {
			int i = iter_durch.next();
			for (int j = 0; j<m2.size();j++) {
				if(i == m2.get(j)) {
					result_durch.add(m2.get(j));
				}
			}			
		}
		
		result_durch = duplikate_entfernen(result_durch);
		return result_durch;
	}
	
	
	public static List<Integer> vereinigung(List<Integer> m1, List<Integer> m2) {
		List<Integer> tmp = new ArrayList<Integer>();
		List<Integer> result_verein = new ArrayList<Integer>();
		ListIterator<Integer> iter_verein = m1.listIterator();
		tmp.addAll(m2);
		
		while(iter_verein.hasNext()) {
			tmp.add(iter_verein.next());
		}
		
		result_verein = duplikate_entfernen(tmp);
		return result_verein;
	}
	
	
	public static List<Integer> differenz(List<Integer> m1, List<Integer> m2){
		List<Integer> tmp1 = new ArrayList<Integer>();
		List<Integer> tmp2 = new ArrayList<Integer>();
		List<Integer> result_diff = new ArrayList<Integer>();		
		ListIterator<Integer> iter_diff;
				
		tmp1.addAll(vereinigung(m1,m2));
		tmp2.addAll(durchschnitt(m1,m2));
		iter_diff = tmp1.listIterator();
	
		while(iter_diff.hasNext()) {
			int i = iter_diff.next();
			if(!tmp2.contains(i))
				result_diff.add(i);
		}	
		
		return result_diff;		
	}
	
	public static List<Integer> duplikate_entfernen(List<Integer> menge) { 
		List<Integer> result_dupl = new ArrayList<Integer>();
		ListIterator<Integer> iter_dupl = menge.listIterator();
		
		while(iter_dupl.hasNext()) {
			int i = iter_dupl.next();
			if(!result_dupl.contains(i))
				result_dupl.add(i);
		}	
		
		return result_dupl;
		
	}
}

	
