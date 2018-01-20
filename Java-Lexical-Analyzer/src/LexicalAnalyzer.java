/* LexicalAnalyzer.java - a lexical analyzer system for simple arithmetic expressions */
import java.io.*;
import java.util.Arrays;

public class LexicalAnalyzer {
	
	/* Global Variables */
	public static char[] lexeme = new char[100];
	public static char nextChar;
	public static int lexLen;
	public static int Endtoken;
	public static String charClass;
	public static String nextToken;
	public static BufferedReader File;
	/* End Global Variables */
	/* MAIN */
	public static void main(String[] args) throws IOException {		
		File = new BufferedReader(new FileReader("MyText.txt"));
		
		//With the programming approach I used, at least the first equation has to be printed first without automation.
		File.mark(20);
		System.out.println("Input: "+ File.readLine());
		File.reset();
		
		getChar();
		do {
			lex();
		 } while (nextToken != "END_OF_FILE");
				
	}
	/* End of MAIN */
	/* lookup = a function to lookup operators and parentheses and return the token */
	private static void lookup(char ch) {
		switch(ch){
		case '(':
			addChar();
			nextToken = "LEFT_PAREN"; 
			break;
		case ')':
			addChar();
			nextToken = "RIGHT_PAREN";	
			break;
		case '+':
			addChar();
			nextToken = "ADD_OP";
			break;
		case '–':
			addChar();
			nextToken = "SUB_OP";
			break;
		case '-':
			addChar();
			nextToken = "SUB_OP";
			break;
		case '*':
			addChar();
			nextToken = "MULT_OP";
			break;
		case '/':
			addChar();
			nextToken = "DIV_OP";
			break;
		case '=':
			addChar();
			nextToken = "ASSIGN_OP";
			break;
		default:
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			nextToken = "END_OF_FILE";	
			break;
		}
	}
	/* addChar - a function to get the next character of input and determine its character class */
	private static void addChar() {
		if(lexLen <= 98){
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;		
		}
		else
			System.out.println("Error - lexeme is too long");	
	}
	/* getChar - a function to get the next character input and determine its character  class */
	public static void getChar(){
	try {
		nextChar = (char) File.read();
		if(nextChar != '?'){
			if(Character.isAlphabetic(nextChar)){
				charClass = "LETTER";
			}
			else if(Character.isDigit(nextChar)){
				charClass = "DIGIT";
			}
			else{
				charClass = "UNKNOWN";	
			}
		}
		else{
			charClass = "END_OF_FILE";
		}
	
	} catch (IOException e) {
		e.printStackTrace();
		}
	}	
	/* getNonBlank - a function to call getChar until it returns a non-whitespace character and Prints Inputs */
	private static void getNonBlank() {				
		//When there is a new line Break the Input is printed
		try {
			File.mark(40);
			if(((char)File.read()) == '\n'){	
				File.readLine();
				System.out.println("Input: " + File.readLine());		
			}		
			File.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(Character.isWhitespace(nextChar)){
			getChar();
		}
	}
	/* lex - a simple lexical analyzer for arithmetic expression */
	public static void lex(){
		lexLen = 0;
		getNonBlank();
		
		switch(charClass){		
		/*Parse Indentifiers */
		case "LETTER":
			addChar();
			getChar();
			while (charClass == "LETTER" || charClass == "DIGIT"){
				addChar();
				getChar();
			}
			nextToken = "IDENT";
			break;		
		/*Parse integer Literals */
		case "DIGIT":
			addChar();
			getChar();
			while (charClass == "DIGIT"){
				addChar();
				getChar();
			}
			nextToken = "INT_LIT";
			break;		
		/* Parentheses and Operators */
		case "UNKNOWN":
			lookup(nextChar);
			getChar();
			break;
		/* EOF */
		case "END_OF_FILE":
			nextToken = "END_OF_FILE";
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';		
			break;		
		} /* End of Switch */		
	System.out.println("Next token is: " + nextToken + ", Next lexeme is " + String.valueOf(lexeme));
	lexeme = new char[100];
	}/* End of function lex */
	
}
