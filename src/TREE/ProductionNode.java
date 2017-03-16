/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TREE;

import Errors.SemanticError;
import Semantic.SymbolTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sequeirios
 */
public class ProductionNode extends SentencesNode {
    public String nonTerminal;
    public List<Production> allProduction;

    @Override
    public void ValidateSemantic() {
      /*
       if (SymbolTable.getInstance().CheckIfVariableExist(nonTerminal)){
            for (Production production :  allProduction) {
                production.ValidateSemantic();
            }
        }else{
           try {
               throw new SemanticError("Non terminal "+ nonTerminal +" not defined, error at row: ");
           } catch (SemanticError ex) {
               Logger.getLogger(ProductionNode.class.getName()).log(Level.SEVERE, null, ex);
           }
        }

        if (!SymbolTable.getInstance().SymbolIsNonTerminal(nonTerminal)){
           try {
               throw new SemanticError("Symbol "+ nonTerminal +" must be a non terminal ");
           } catch (SemanticError ex) {
               Logger.getLogger(ProductionNode.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
      */
    }
}
