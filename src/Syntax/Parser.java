/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Syntax;

import Lexer.Lexer;
import Lexer.LexerException;
import Lexer.Token;
import Lexer.TokenTypes;
import TREE.DeclaretionSymbolNode;
import TREE.JavaCodeNode;
import TREE.PackageNode;
import TREE.Production;
import TREE.ProductionNode;
import TREE.SentencesNode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eSequeirios
 */
public class Parser {
 
    public Lexer _lexer;
    public Token currentToken;
    
    
    
    public Parser(Lexer lexer){
        _lexer = lexer;
        currentToken = lexer.GetNextToken();
        
    }
    
    public List<SentencesNode> Parser() throws Exception{
       List<SentencesNode> cupCode= CupCode();
       if (currentToken.getType() != TokenTypes.EOF)
			{
				throw new LexerException("Expected EOF: " + currentToken.getRow() + " , column: " + currentToken.getColumn());
			}
        return cupCode;
    }

    private List<SentencesNode> CupCode() throws Exception {
     return ListOfSentences();
      
    }
     public boolean CompareTokenType(TokenTypes type)
        {
            return currentToken.Type == type;
        }
    private List<SentencesNode> ListOfSentences() throws Exception {
      if (CompareTokenType(TokenTypes.EOF))
		{
			return new ArrayList<>();
		}
		//Lista_Sentencias->Sentence Lista_Sentencias
		if (!CompareTokenType(TokenTypes.EOF))
		{
			SentencesNode statement = Setences();
			List<SentencesNode> statementList = ListOfSentences();

			
                        if(statement !=null)
                        statementList.add(0,statement);

			return statementList;
		}
		//Lista_Sentencia->Epsilon
		else
		{
            return new ArrayList<>();
		}
      
    }

    private SentencesNode Setences() throws Exception {
     if( CompareTokenType(TokenTypes.RwPACKAGE)){
            currentToken = _lexer.GetNextToken();
            ArrayList<String> list = new ArrayList<>();
            packageListId(list);
            PackageNode node = new PackageNode();
            node.packages = list;
            return node;        
            
    }else if(CompareTokenType(TokenTypes.RwIMPORT)){
          currentToken = _lexer.GetNextToken();
          while(!CompareTokenType(TokenTypes.EOS)){
           currentToken = _lexer.GetNextToken();
          
          }
             currentToken = _lexer.GetNextToken();
          return null;
    }
     
     
     else if(CompareTokenType(TokenTypes.RwPARSER) || (CompareTokenType(TokenTypes.RwACTION)) || (CompareTokenType(TokenTypes.RwINIT) || CompareTokenType(TokenTypes.RwSCAN))){
         
        TokenTypes type = currentToken.getType();
        currentToken = _lexer.GetNextToken();
           return jcode(type);
        
         
    }else if(CompareTokenType(TokenTypes.RwTERMINAL) || CompareTokenType(TokenTypes.RwNONTERMINAL) || CompareTokenType(TokenTypes.RwNON)){
       TokenTypes type = currentToken.getType();
       currentToken = _lexer.GetNextToken();
       
       return SymbolList(type);
    
    }else if(CompareTokenType(TokenTypes.RwSTART)){
        currentToken = _lexer.GetNextToken();
        return start();
    }else if(CompareTokenType(TokenTypes.Id)){
        return ProductionList();
    }else{
      throw new Exception("Sentences is no valid");
    }
     
      
    }

    private void packageListId(ArrayList<String> list) throws Exception {
      if(CompareTokenType(TokenTypes.Id)){
        list.add(currentToken.getLexeme());
           currentToken = _lexer.GetNextToken();
           optionalPackageId(list);
      
      }else{
          throw new Exception("Id expected row :"+currentToken.getRow()+", column:"+currentToken.getColumn());
      }
    }

    private void optionalPackageId(ArrayList<String> list) throws Exception {
            if(CompareTokenType(TokenTypes.Dot))   {           
                      currentToken = _lexer.GetNextToken();
            packageListId(list);
            }
    }

    private SentencesNode jcode(TokenTypes type) throws Exception {
      
       currentToken = _lexer.GetNextToken();
      String code = JcodeCore();
      if(CompareTokenType(TokenTypes.EOS)){
          currentToken = _lexer.GetNextToken();
      }else{
          throw new Exception("EOS expected at row: "+currentToken.getRow()+", column: "+currentToken.getColumn());
      }
      JavaCodeNode jcode = new JavaCodeNode();
      jcode.Code = code;
      jcode.Type = type;
      
      return jcode;
     
    }

    private String JcodeCore() throws Exception {
      String code;
      if(CompareTokenType(TokenTypes.JavaCode)){
          code = currentToken.Lexeme;
          currentToken = _lexer.GetNextToken();
      }else{
          throw new Exception("JavaCode Exepcted Token row :" + currentToken.getRow()+" col: "+currentToken.getColumn());     
      }
      return code;
    }

    private SentencesNode SymbolList(TokenTypes type) throws Exception {
      String className = null;
      if(CompareTokenType(TokenTypes.RwTERMINAL)){
           currentToken = _lexer.GetNextToken();
           type = TokenTypes.RwNONTERMINAL;
           currentToken = _lexer.GetNextToken();
      }
      
      ArrayList<String>list = new ArrayList<>();;
      if(CompareTokenType(TokenTypes.Id)){
         className = currentToken.getLexeme();
        currentToken = _lexer.GetNextToken();
        
        if(CompareTokenType(TokenTypes.Id)){
            
             SymbolListString(list);
        }else{
          list.add(className);
          className ="Object";
          OpSymbol(list);
      
      
      }
      }
    if(CompareTokenType(TokenTypes.EOS)){
            currentToken = _lexer.GetNextToken();
    }else{
        throw new Exception("End of sentences"+currentToken.getRow()+", column:"+currentToken.getColumn());
    }
    
    DeclaretionSymbolNode declaretionNode = new DeclaretionSymbolNode();
    declaretionNode.NameOfClass = className;
    declaretionNode.NameOfSymbols = list;
    declaretionNode.TypeOfSymbol = type;
    
    
        return declaretionNode;
      
    }

    private void SymbolListString(ArrayList<String> list) throws Exception {
       if(CompareTokenType(TokenTypes.Id)){
           list.add(currentToken.getLexeme());
           currentToken = _lexer.GetNextToken();
        if(CompareTokenType(TokenTypes.EOS)){
            return;       
        }
         OpSymbol(list);         
       }else{
           throw new Exception("Id expected at row :"+currentToken.getRow()+", column"+currentToken.getColumn());
       
       }
    }

    private void OpSymbol(ArrayList<String> list) throws Exception {
       if(CompareTokenType(TokenTypes.Comma)){
         currentToken = _lexer.GetNextToken();
         SymbolListString(list);
       
       }
    }

    private SentencesNode start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private SentencesNode ProductionList() throws Exception {
         String terminalName = currentToken.getLexeme();
         currentToken = _lexer.GetNextToken();
         
         ArrayList<Production> listOfProduction = new ArrayList();
         if(CompareTokenType(TokenTypes.Assigmanet)){
          currentToken = _lexer.GetNextToken();
          productionList(listOfProduction);
         
          
          
           if (CompareTokenType(TokenTypes.Pipe))
        {
            optional_production(listOfProduction);
        }
          
          
          if(CompareTokenType(TokenTypes.EOS)){
              currentToken = _lexer.GetNextToken();
          }else{
              throw new Exception("EOS expected");
          }
          
         }else{
             throw new Exception("Assigmanet expected");
         }
         
        ProductionNode productionNode = new ProductionNode();
        productionNode.nonTerminal = terminalName;
        productionNode.allProduction = listOfProduction;
        return productionNode;
       

      
         
    }

    private void productionList(ArrayList<Production> listOfProduction) throws Exception {
           Production production = new Production();
     
    

        while (CompareTokenType(TokenTypes.Id)){

            production.name += " " + (currentToken.getLexeme());
            currentToken = _lexer.GetNextToken();

            if (CompareTokenType(TokenTypes.Colon)){

                production.name += (currentToken.getLexeme());
          currentToken = _lexer.GetNextToken();

                if (!CompareTokenType(TokenTypes.Id)){
                        throw  new Exception("A label was expected row: " + currentToken.getRow() + " , column: " + currentToken.getColumn());
                }
                production.name += (currentToken.getLexeme());
                  currentToken = _lexer.GetNextToken();
            }

            if(CompareTokenType(TokenTypes.JavaCode)){
                production.name += " javaCode " ;
                production.jCode = currentToken.getLexeme();
                currentToken = _lexer.GetNextToken();
            }
        }
        listOfProduction.add(production);
     if (CompareTokenType(TokenTypes.Pipe))
        {
            optional_production(listOfProduction);
        }
            
        //To change body of generated methods, choose Tools | Templates.
    }

    private void optional_production(ArrayList<Production> listOfProduction) throws Exception {
         if (CompareTokenType(TokenTypes.Pipe))
        {
               currentToken = _lexer.GetNextToken();
            productionList(listOfProduction);
        }
    }

  

   
   }

    


