/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantic;

import Lexer.TokenTypes;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;

/**
 *
 * @author Sequeirios
 */
public class SymbolTable implements java.io.Serializable {
   public static SymbolTable _instance;
   public HashMap<String, String> noTerminal;
   public HashMap<String, String> Terminal;
   
   
  
   public SymbolTable (){
          noTerminal = new HashMap();
          Terminal = new HashMap();       
   }
   
   public static SymbolTable getInstance(){
   
    return (_instance != null) ? _instance :(_instance = new SymbolTable());
   
   }
   
    public final boolean SymbolIsTerminal(String name)
    {
        return Terminal.containsKey(name);
    }

    public  boolean SymbolIsNonTerminal(String name)
    {
        return noTerminal.containsKey(name);
    }
    public void RegisterType(String name, TokenTypes token, String type) throws IOException
        {
       
       if (token == TokenTypes.RwTERMINAL){
            Terminal.put(name,type);
        }else if(token == TokenTypes.RwNONTERMINAL){
            noTerminal.put(name,type);
        }
        }
           
          
          
            public boolean CheckIfVariableExist(String nameOfVariable)
        {
              if(SymbolTable.getInstance().Terminal.containsKey(nameOfVariable) || SymbolTable.getInstance().noTerminal.containsKey(nameOfVariable))
                     return true;
             
              return false;
        }
}


