/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.util.ArrayList;
import java.util.HashMap;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author Sequeirios
 */
public class ReserverdWords {
     
       public HashMap<String, TokenTypes> reserverdWords;
	public HashMap<String, TokenTypes> Operators;
	public HashMap<String, TokenTypes> Separators;
    	public ArrayList<String> SpecialSymbols;
        public ReserverdWords(){
             reserverdWords = new HashMap<String, TokenTypes>();
		Operators = new HashMap<String, TokenTypes>();
		Separators = new HashMap<String,TokenTypes>();
		SpecialSymbols = new ArrayList<>();

		IntializeReserverdWords();
		InitializeOperators();
		InitializeSeparators();
		InitializeSpecial();
        }
        private void InitializeSpecial()
	{
		SpecialSymbols.add("{");
		SpecialSymbols.add(":");
                SpecialSymbols.add("/");
		SpecialSymbols.add("*");
                SpecialSymbols.add("::");
              
	}
        private void InitializeSeparators()
	{
		Separators.put(";", TokenTypes.EOS);
		Separators.put(",", TokenTypes.Comma);
		Separators.put(".", TokenTypes.Dot);
	}
        private void InitializeOperators()
	{
		//Asignament Operators
		Operators.put("*", TokenTypes.Mult);
		Operators.put("/", TokenTypes.Division);
		Operators.put("::=", TokenTypes.Assigmanet);
		Operators.put("{:", TokenTypes.Opencode);
		Operators.put(":}", TokenTypes.CloseCode);
		Operators.put("|", TokenTypes.Pipe);
		Operators.put("*", TokenTypes.IncludeAll);
		Operators.put("%", TokenTypes.Precedence);
                Operators.put("{", TokenTypes.OpenBracket);
                Operators.put("}", TokenTypes.CloseBracket);
		Operators.put(":", TokenTypes.Colon);
	}
        
        private void IntializeReserverdWords()
	{
	   //CUP file reserved words
		reserverdWords.put("import", TokenTypes.RwIMPORT);
		reserverdWords.put("package", TokenTypes.RwPACKAGE);
		reserverdWords.put("code", TokenTypes.RwCODE);
		reserverdWords.put("action", TokenTypes.RwACTION);
		reserverdWords.put("parser", TokenTypes.RwPARSER);
		reserverdWords.put("terminal", TokenTypes.RwTERMINAL);
		reserverdWords.put("nonterminal", TokenTypes.RwNONTERMINAL);
		reserverdWords.put("non", TokenTypes.RwNON);
		reserverdWords.put("init", TokenTypes.RwINIT);
		reserverdWords.put("scan", TokenTypes.RwSCAN);
		reserverdWords.put("with", TokenTypes.RwWITH);
		reserverdWords.put("start", TokenTypes.RwSTART);
		reserverdWords.put("precedence", TokenTypes.RwPRECEDENCE);
		reserverdWords.put("left", TokenTypes.RwLEFT);
		reserverdWords.put("right", TokenTypes.RwRIGHT);
		reserverdWords.put("nonassoc", TokenTypes.RwNONASSOC);
		
		reserverdWords.put("String", TokenTypes.RwString);
		
               reserverdWords.put("Float", TokenTypes.RwFloat);
                reserverdWords.put("Double", TokenTypes.RwDouble);
	}
}
