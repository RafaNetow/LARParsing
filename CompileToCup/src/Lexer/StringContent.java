/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author Sequeirios
 */
public class StringContent {
   public String Input;
   private int _currentIndex;
   private int  _row;
   private int _column;
   
   
   
   public StringContent(String input){
       this.Input = input;
       _currentIndex = 0;
       _row = 1;
       _column = 0;
   }
   
   
   public final Symbol GetNextSymbol(){
       if (_currentIndex >= Input.length())
		{
			Symbol tempVar = new Symbol();
			tempVar.setRow(_row);
			tempVar.setColumn(_column);
			tempVar.setCSymbol('\0');
			return tempVar;
		}

		Symbol symbol = new Symbol();
		symbol.setRow(_row);
		symbol.setColumn(_column);
		symbol.setCSymbol(Input.charAt(_currentIndex++));

		if ((new Character(symbol.getCSymbol())).equals('\t'))
		{
			_column = 0;
			_row += 1;
		}
		else
		{
			_column += 1;
		}

		return symbol;
   }
}
