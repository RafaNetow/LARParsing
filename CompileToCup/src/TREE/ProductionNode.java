/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TREE;

import Semantic.SymbolTable;
import java.util.List;

/**
 *
 * @author Sequeirios
 */
public class ProductionNode extends SentencesNode {
    public String nonTerminal;
    public List<Production> allProduction;

    @Override
    public void ValidateSemantic() {
      
        for (Production produc : allProduction) {
                  produc.ValidateSemantic();
    }
      
    }
}
