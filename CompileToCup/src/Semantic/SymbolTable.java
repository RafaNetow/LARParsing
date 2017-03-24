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
public class SymbolTable {
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
   
    public void RegisterType(String name, TokenTypes token, String type) throws IOException
        {
       
      
            
            if (SymbolTable.getInstance().noTerminal.containsKey(name) || SymbolTable.getInstance().Terminal.containsKey(name))
            {
                throw new IOException("Name"+name+"Al ready exist");
            }
           
           if( token == TokenTypes.RwTERMINAL)
         SymbolTable.getInstance().Terminal.put(type,name);
           else
               SymbolTable.getInstance().noTerminal.put(type,name);
        }
      
      
          public String GetType(String name) throws IOException
        {
           if(SymbolTable.getInstance().Terminal.containsKey(name))
                 return SymbolTable.getInstance().Terminal.get(name);
           
           if(SymbolTable.getInstance().noTerminal.containsKey(name))
                 return SymbolTable.getInstance().noTerminal.get(name);
            throw new IOException("Type name doesnt exist.");
        }
          
          
            public boolean CheckIfVariableExist(String nameOfVariable)
        {
              if(SymbolTable.getInstance().Terminal.containsKey(nameOfVariable) || SymbolTable.getInstance().noTerminal.containsKey(nameOfVariable))
                     return true;
             
              return false;
        }
}

