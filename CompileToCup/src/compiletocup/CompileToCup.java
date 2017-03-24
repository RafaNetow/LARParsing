/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiletocup;

import Automaton.Automaton;
import Automaton.LR1;
import Lexer.Lexer;
import Lexer.StringContent;
import Syntax.Parser;
import TREE.SentencesNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CompileToCup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        List<String> list = readFile();
//         Automaton AutomatonLr1 = new LR1();
         
   
        StringBuilder sb = new StringBuilder();
        assert list != null;
        for (String s : list)
        {
            sb.append(s);
            sb.append("\t");
        }

        Lexer lex = new Lexer(new StringContent(sb.toString()));    

        Parser parser = new Parser(lex);

        try {
             List<SentencesNode> root =  parser.Parser();
             
             LR1 AutomatonLr1 = new LR1();
             
          AutomatonLr1.evaluateAutomaton(root);
            System.out.println("\n");
                   


// 1. Java object to JSON, and save into a file

            for (SentencesNode statement:root){
                System.out.println(statement);

                
      
    

// 2. Java object to JSON, and assign to a String

                   
                System.out.println(statement);
                 System.out.println("\n");
            }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
       System.out.println(gson.toJson(root));


        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
     private static List<String> readFile()
    {
        List<String> records = new ArrayList<>();
        try
        {
            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Sequeirios\\Documents\\Programas\\ycal.cup"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);

                    records.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", "./src/test.txt");
            e.printStackTrace();
            return null;
        }
    }
}
