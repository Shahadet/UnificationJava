

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class unification {
	
	static boolean printLog=false; //alter it to print or not print log
	static int counter = 0;
	static String term1 = null;
	static String term2 = null;
	static Map<String,String> variables = new HashMap<String, String>();
	
	public static void main(String[] args) {
		// Unification Algorithm
		String command="";
		System.out.println("This Program works similar to the unify_with_occurs_check(term1,term2) function of prolog");
		
		while(!command.equalsIgnoreCase("e")) {
			
			//term1 = "p(Max,father(Y),Tax)" ; 
			//term2 = "p(Y,father(all),f(all))"; 
			term1 =  input(1);
			term2 =  input(2);
			term1 = term1.replaceAll(" ", "");
			term2 = term2.replaceAll(" ", "");
			unify();

			counter=0;
			variables.clear();
			term1="";
			term2="";
			
			if(!printLog)
				System.out.println("Type E to exit,L for turning on Log or press enter to continue");
			else
				System.out.println("Type E to exit,L for turning off Log or press enter to continue");
			Scanner scan = new Scanner(System.in);
			command = scan.nextLine();
			if(command.equalsIgnoreCase("l")) {
				if(printLog) {
					printLog=false;
					System.out.println("*****Log Printing Disabled*****");
				}
				else {
					printLog=true;
					System.out.println("*****Log Printing Enabled*****");
				}
			}
		}
		
	}
	
	//Rule:
	//1. Variable can be replaced with constant,function or variable.
	public static void unify() {
		Boolean unifiable=true;
		for(;counter<term1.length();counter++) {
			char cX1 = term1.charAt(counter);			
			char cX2 = term2.charAt(counter);
			String variable1 = "";
			String variable2 = "";
			//first one is variable
			if(Character.isUpperCase(cX1)) {
				variable1 = getTheVariable(term1);
				//both are variable
				if(Character.isUpperCase(cX2)) {
					variable2 = getTheVariable(term2);
					//No need to store the same variables value (i.e. X=X)
					if(!variable1.equals(variable2))
						setVariableAndUnifyTerms(variable1,variable2);
				}
				//second one is constant or function
				else {
					String constantOrFunction = getConstantOrFunction(term2);
					//Check for Loop
					if(constantOrFunction.contains(variable1)) {
						unifiable=false;
						break;
					}
					setVariableAndUnifyTerms(variable1, constantOrFunction);
				}
			}
			//First one constantOrFunction, Second one is variable
			else if(Character.isUpperCase(cX2)) {
				variable2 = getTheVariable(term2);
				String constantOrFunction = getConstantOrFunction(term1);
				//Check for Loop
				if(constantOrFunction.contains(variable2)) {
					unifiable=false;
					break;
				}
				setVariableAndUnifyTerms(variable2, constantOrFunction);
			}
			//both are constant or other character
			else {
				if(cX1!=cX2) {
					unifiable=false;
					break;
				}
			}
			//if(printLog)	System.out.println("Test :Counter"+counter);
		}
		
		
		if(unifiable) {
			System.out.println();
			for(String v:variables.keySet()) {
				System.out.println(v +"="+variables.get(v));
			}
			System.out.println("\nyes");
		}
		else {
			System.out.println("\nno");
		}
	}
	public static void unifyBothTerm( String var,String funOrConOrVar) {
		if(printLog)	System.out.println("Changing   :"+var+"---->"+funOrConOrVar);
		term1 = term1.replaceAll(var, funOrConOrVar);
		term2 = term2.replaceAll(var, funOrConOrVar);
		
		//unify previous variable's value
		for(String s:variables.keySet()) {
			String value = variables.get(s);
			if(value.contains(var)) {
				value = value.replace(var, funOrConOrVar);
				variables.put(s, value);
			}
		}
	}
	//Set the variable value into a Map and unify both expression
	public static void setVariableAndUnifyTerms(String variable, String Value) {
		variables.put(""+variable, Value);
		unifyBothTerm(""+variable,Value);
		if(printLog)	System.out.println("Updated term1 ->"+term1);
		if(printLog)	System.out.println("Updated term2 ->"+term2);
	}
	
	//Pick the function from the term. 
	//A function can be consist of multiple functions,variables or/and constants 
	public static String getConstantOrFunction(String x) {
		int i = counter;
		//take the first character that the pointer is pointing to
		String funOrCon = ""+x.charAt(i);
		i++;
		
		//reading up to the (first/last parenthesis or comma) for (function or constant) with multiple characters
		for(;i<x.length();i++) {
			char temp = x.charAt(i);
			if(temp != '(' && temp != ')' && temp != ',')
				funOrCon+=temp;
			else
				break;
		}
		//Check whether this is the last constant
		if(i>=x.length())
			return funOrCon;
		
		//check whether this is a function or a constant
		int bracStart = 0;
		char temp = x.charAt(i);
		
		//is a function, not a constant. Start reading the whole function
		if(temp=='(') {
			funOrCon+=temp;
			bracStart++;
			for(i=i+1;i<x.length();i++) {
				temp = x.charAt(i);
				if(temp=='(')
					bracStart++;
				else if(temp == ')')
					bracStart--;
				else if(temp == ',' && bracStart==0)
					break;
				if(bracStart<0)
					break;
				funOrCon+=temp;
			}
		}
		
		if(printLog)	System.out.println("Constant or function::["+funOrCon+"] at-"+counter);
		
		return funOrCon;
	}
	
	// This method will take input from users for EX1 and EX2
	public static String input(int number){
		System.out.println("Please enter Term "+number+": ");
		Scanner scan = new Scanner(System.in);
		String EX = scan.nextLine();
		while(EX.startsWith("(")){
			System.out.println("Please, start with a variable or constant");
			EX = scan.nextLine();
		}
		
		while(!checkParenthesisInExpression(EX)){
			System.out.println("Please enter Term-"+number+" again, you missed a parenthese : ");
			EX = scan.nextLine();
		}
		return EX;
	}
	
	//Check the parenthesis is correctly formated or placed
	public static boolean checkParenthesisInExpression(String ex) {
		// Calculate the parentheses to see if they are equal
		int firstParanthesis = ex.indexOf('(');
		//No Parenthesis is possible i.e. [unify_with_occurs_check(X,Y). result X=Y Yes]
		if(firstParanthesis < 0)
			return true;
		int numberOfParanthesis =1;
		for (int i=firstParanthesis+1;i<ex.length();i++) {
			if(ex.charAt(i) == '(')
				numberOfParanthesis++;
			else if(ex.charAt(i) == ')')
				numberOfParanthesis--;
			if(numberOfParanthesis<0)
			{
				System.out.println("Error: Parenthesis Mispatch at the position->"+i);
				return false;
			}
		}
		if(numberOfParanthesis!=0) {
			return false;
		}
		return true;
	}
	
	public static String getTheVariable(String x) {
		String var = "";
		char temp;
		for(int i = counter;i<x.length();i++) {
			temp = x.charAt(i);
			if(temp!=',' && temp != '(' && temp !=')') {
				var+=temp;
			}
			else if(temp == '(') {
				System.out.println("Error::Comma or end parenthesis missing in--> ["+i+"]");
				break;
			}
			else
				break;
		}
		return var;
	}
}