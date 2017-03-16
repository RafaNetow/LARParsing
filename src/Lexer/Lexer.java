/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author Sequeirios
 */
public class Lexer {
   public StringContent sourceCode;
   private Symbol _currentSymbol;
   private ReserverdWords rWords = new ReserverdWords();

    public StringContent getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(StringContent sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Symbol getCurrentSymbol() {
        return _currentSymbol;
    }

    public void setCurrentSymbol(Symbol _currentSymbol) {
        this._currentSymbol = _currentSymbol;
    }

    public ReserverdWords getrWords() {
        return rWords;
    }

    public void setrWords(ReserverdWords rWords) {
        this.rWords = rWords;
    }
   
   
   public Lexer(StringContent code){
       this.sourceCode = code;
       _currentSymbol = code.GetNextSymbol();
     
   }
   
   public void MapperToLexeme(Lexeme currentLexeme){
     currentLexeme.Column = _currentSymbol.getColumn();
     currentLexeme.Row = _currentSymbol.getRow();
     currentLexeme.value += _currentSymbol.getCSymbol();
     _currentSymbol = this.sourceCode.GetNextSymbol();
         
   }
   
   public Token GetNextToken(){
       int state = 0;
       String lexeme = "";
       int eof=0;
       int tokenRow=0;
       int tokenColumn = 0;
       
     
       
       
       while(true){
          switch(state){
          
              case 0:
                  if(_currentSymbol.getCSymbol() =='\0'){
                      
                    Token tokenToReturn = new Token();
                     tokenToReturn.setType(TokenTypes.EOF);
                     tokenToReturn.setRow(tokenRow);
                     tokenToReturn.setColumn(tokenColumn);
                     tokenToReturn.Lexeme = lexeme;
                    return tokenToReturn;
                  
                  
                  
                  }else if(Character.isWhitespace(_currentSymbol.getCSymbol())){
                      
                   _currentSymbol.setCSymbol(this.sourceCode.GetNextSymbol().getCSymbol());
                    
                  }
                   else if(Character.isLetter(_currentSymbol.getCSymbol()) |_currentSymbol.getCSymbol()=='_'){
                           state = 1;
                          tokenColumn = _currentSymbol.getColumn();
                          tokenRow = _currentSymbol.getRow();
                          lexeme+= _currentSymbol.getCSymbol();
                            _currentSymbol.setCSymbol(this.sourceCode.GetNextSymbol().getCSymbol());
                                           
              }
                  else if(this.rWords.Separators.containsKey(String.valueOf(_currentSymbol.getCSymbol()))){
                      
                       Token tokenToReturn = new Token();  
                       tokenColumn = this._currentSymbol.getColumn();
                        tokenRow = this._currentSymbol.getRow();
                        lexeme+= _currentSymbol.getCSymbol();                       
                        tokenToReturn.setColumn(tokenColumn);
                        tokenToReturn.setRow(tokenRow);
                        tokenToReturn.setType(this.rWords.Separators.get(lexeme));
                         _currentSymbol.setCSymbol(this.sourceCode.GetNextSymbol().getCSymbol()); 
                        return tokenToReturn;
                        
                   }else if(this.rWords.Operators.containsKey(String.valueOf(_currentSymbol.getCSymbol()))){
                           tokenColumn = this._currentSymbol.getColumn();
                        tokenRow = this._currentSymbol.getRow();
                        lexeme+= _currentSymbol.getCSymbol();
                        state =2;
                   }
                  
                  break;
                  
                  
              case 1:
                     if(Character.isLetter(_currentSymbol.getCSymbol()) |_currentSymbol.getCSymbol()=='_' | _currentSymbol.getCSymbol() =='$'){
                           state = 1;
                          tokenColumn = _currentSymbol.getColumn();
                          tokenRow = _currentSymbol.getRow();
                          lexeme+= _currentSymbol.getCSymbol();
                            _currentSymbol.setCSymbol(this.sourceCode.GetNextSymbol().getCSymbol());
                                           
                     }  else if (rWords.reserverdWords.containsKey(lexeme))
                        {
                                Token tokenToReturn = new Token();
                                tokenToReturn.setType(this.rWords.reserverdWords.get(lexeme));
                                tokenToReturn.setColumn(tokenColumn);
                                tokenToReturn.setRow(tokenRow);
                               tokenToReturn.Lexeme = lexeme;
                                
                                return tokenToReturn;
                                                    
                         }else{
                         
                              Token tokenToReturn = new Token();
                                 tokenToReturn.setType(TokenTypes.Id);
                                tokenToReturn.setColumn(tokenColumn);
                                tokenToReturn.setRow(tokenRow);
                                tokenToReturn.Lexeme = lexeme;
                                return tokenToReturn;
   
                         
                     }
                     break;
                     
              case 2: 
                  setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (this.rWords.SpecialSymbols.contains(String.valueOf(getCurrentSymbol().getCSymbol()))
                && !(lexeme.equals(":") && getCurrentSymbol().getCSymbol() == '/')
                && !(lexeme.equals("*") && getCurrentSymbol().getCSymbol() == '*'))
		{
			lexeme += getCurrentSymbol().getCSymbol();
			setCurrentSymbol(this.getSourceCode().GetNextSymbol());

            if (lexeme.equals("{:"))
            {
                setCurrentSymbol(getSourceCode().GetNextSymbol());

                String javaCode = GetJavaCodeBlock(lexeme);

                Token temp = new Token();

                temp.setType(TokenTypes.JavaCode);
                temp.setLexeme(javaCode);
                temp.setColumn(tokenColumn);
                temp.setRow(tokenRow);

                return temp;
            }

            //Special case for comments, we've got to get the line(S) of the comments
			if (lexeme.equals("//"))
			{
				String str = "";
				GetLineComment(str);
				return GetNextToken();
			}

			//For block comments
			if (lexeme.equals("/*"))
			{
				String str = "";
				GetBlockComment(str);
				return GetNextToken();
			}

            //special operators like ::=
            if (this.rWords.SpecialSymbols.contains(lexeme.substring(0, 2)))
            {
                if (getCurrentSymbol().getCSymbol() == '=')
                {
                    lexeme += getCurrentSymbol().getCSymbol();
                    if (lexeme.equals("::="))
                    {
                        setCurrentSymbol(getSourceCode().GetNextSymbol());
                    }
                    Token tempVar2 = new Token();
                    tempVar2.setType(rWords.Operators.get(lexeme.substring(0, 3)));
                    tempVar2.setLexeme(lexeme);
                    tempVar2.setColumn(tokenColumn);
                    tempVar2.setRow(tokenRow);
                    return tempVar2;
                }
            }

			Token tempVar3 = new Token();
			tempVar3.setType(rWords.Operators.get(lexeme.substring(0,2)));
			tempVar3.setLexeme(lexeme);
			tempVar3.setColumn(tokenColumn);
			tempVar3.setRow(tokenRow);
			return tempVar3;
		}

		Token tempVar4 = new Token();
		tempVar4.setType(rWords.Operators.get(lexeme));
		tempVar4.setLexeme(lexeme);
		tempVar4.setColumn(tokenColumn);
		tempVar4.setRow(tokenRow);
		return tempVar4;
           
              
                    
               
       
       }
   
   }
   
   
   
   
   
   
   
}

    private String GetJavaCodeBlock(String lexeme) {
      while (getCurrentSymbol().getCSymbol() != ':')
        {
            lexeme += getCurrentSymbol().getCSymbol();
            setCurrentSymbol(getSourceCode().GetNextSymbol());
        }

        //Adding the * to the lexeme string
        lexeme += getCurrentSymbol().getCSymbol();

        //Get the char right after the *, to check if it's a / so we can close the comment
        setCurrentSymbol(getSourceCode().GetNextSymbol());

        if (getCurrentSymbol().getCSymbol() == '}')
        {
            lexeme += getCurrentSymbol().getCSymbol();
            setCurrentSymbol(getSourceCode().GetNextSymbol());
            return lexeme;
        }

        return GetJavaCodeBlock(lexeme); //To change body of generated methods, choose Tools | Templates.
    }

    private void GetLineComment(String str) {
      while (getCurrentSymbol().getCSymbol() != '\t')
		{
			str += getCurrentSymbol().getCSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		setCurrentSymbol(getSourceCode().GetNextSymbol());
    }

    private String GetBlockComment(String str) {
      while (getCurrentSymbol().getCSymbol() != '*')
		{
			str += getCurrentSymbol().getCSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		//Adding the * to the lexeme string
		str += getCurrentSymbol().getCSymbol();

		//Get the char right after the *, to check if it's a / so we can close the comment
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getCurrentSymbol().getCSymbol() == '/')
		{
			str += getCurrentSymbol().getCSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
			return str;
		}

		return GetBlockComment(str);
    }
}
