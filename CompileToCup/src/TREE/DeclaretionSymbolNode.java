/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TREE;

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
       
     if( SymbolTable.getInstance().CheckIfVariableExist(NameOfClass))
     {
         try {
             throw new SemanticException("Error al intentar declarar la variable"+ NameOfClass+" ya existe");
         } catch (SemanticException ex) {
             Logger.getLogger(DeclaretionSymbolNode.class.getName()).log(Level.SEVERE, null, ex);
         }
    
     
    }
     
    }
     
}
