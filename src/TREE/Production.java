
package TREE;

import Errors.SemanticError;
import Semantic.SymbolTable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sequeirios
 */
public class Production extends SentencesNode{
     public String name =" ";
     public String jCode;

    @Override
    public void ValidateSemantic() {
   //     String[] splited = name.split(" ");

      /*  for (String symbol :  splited) {
            if (!(symbol.equals("javaCode") || symbol.contains("%")) && !symbol.equals("") ){

                if (symbol.contains(":")){
                    int index = symbol.indexOf(":");
                    symbol = symbol.substring(0,index);
                }

                if (!SymbolTable.getInstance().CheckIfVariableExist(symbol))
                    try {
                        throw new SemanticError("Symbol +not defined");
                } catch (SemanticError ex) {
                    Logger.getLogger(Production.class.getName()).log(Level.SEVERE, null, ex);
                }
                }

            }*/
        }
    }

