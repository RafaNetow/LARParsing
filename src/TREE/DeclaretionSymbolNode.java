/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TREE;

import Errors.SemanticError;
import Lexer.TokenTypes;
import Semantic.SymbolTable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sequeirios
 */
public class DeclaretionSymbolNode extends SentencesNode {
     public TokenTypes TypeOfSymbol;
    public String NameOfClass;

    public List<String> NameOfSymbols;

    @Override
    public void ValidateSemantic()   {
       
     for (String symbol:NameOfSymbols ) {
            if (!SymbolTable.getInstance().CheckIfVariableExist(symbol)){
                try {
                    SymbolTable.getInstance().RegisterType(symbol,TypeOfSymbol,NameOfClass);
                } catch (IOException ex) {
                    Logger.getLogger(DeclaretionSymbolNode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                try {
                    throw new SemanticError("Symbol "+ symbol +" is already defined, error  ");
                } catch (SemanticError ex) {
                    Logger.getLogger(DeclaretionSymbolNode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
     
    }
     
}
