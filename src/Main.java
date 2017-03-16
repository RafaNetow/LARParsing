

import Automaton.Action;
import Automaton.LR1Parser;
import Automaton.NonTerminal;
import Automaton.State;
import Lexer.Lexer;
import Lexer.StringContent;
import Syntax.Parser;
import TREE.SentencesNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Semantic.SymbolTable;
import TREE.ProductionNode;
import java.util.Random;

import Lexer.Lexer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main
{

    private static void GenereteSym(HashMap<String, String> Terminal) {
       try{
    PrintWriter writer = new PrintWriter("C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\sym.java", "UTF-8");
     writer.println("public class sym {");
     writer.println("public static final int EOF=0;");
     writer.println("public static final int error=1;");
     int Count =2;
     for(Map.Entry<String, String> entry : Terminal.entrySet()) {
    String key = entry.getKey();
    String value = entry.getValue();
       writer.println("public static final int "+key+" = "+Count+";");
      Count++;   
}  
          writer.println(" public static final String[] terminalNames = new String[] {");
      for(Map.Entry<String, String> entry : Terminal.entrySet()) {
    String key = entry.getKey();
       writer.println("\""+key+"\""+",");
      
}  
     writer.println("};");
     writer.println("}");
     
    writer.close();
} catch (IOException e) {
   // do something
}
    }


    public static void main(String args []) throws IOException, Exception
    {
        String inputGrammar;
        String inputString;

        
         List<String> list = readFile();

         
   
        StringBuilder sb = new StringBuilder();
        assert list != null;
        for (String s : list)
        {
            sb.append(s);
            sb.append("\t");
        }

        Lexer lex = new Lexer(new StringContent(sb.toString()));    

        Parser parser = new Parser(lex);

      
             List<SentencesNode> root =  parser.Parser();
           
       
             
           String grammar = "";
         
            
            System.out.println("\n");
            
          SymbolTable general = SymbolTable.getInstance();
          
         
       for (SentencesNode statement:root){
               statement.ValidateSemantic();
            }

       String[] stringArray;
            ArrayList<String> stringList = new ArrayList<>();
            List<Character> taken = new ArrayList<>();
       
    for(SentencesNode node : root){
       if(node instanceof ProductionNode){
              String nonTerminal =((ProductionNode) node).nonTerminal.toUpperCase();

                    System.out.print(nonTerminal.toUpperCase()+"->");
                    Random r = new Random();
                     String fixedElementOfGrammar = nonTerminal.toUpperCase()+"->";
                        int pos=0;
                        for (TREE.Production production :   ((ProductionNode) node).allProduction) {

                       String[] splittedBySpace = production.name.split(" ");
                    
                        if (pos > 0){
                            System.out.print("|");
                            fixedElementOfGrammar += "|";
                        }

                        if(production.name.equals("")){
                            System.out.print("~");
                            fixedElementOfGrammar+= "~";
                            pos++;
                            break;
                        }
                         for (String elemento :  splittedBySpace ) {
                            if(!Objects.equals(elemento, "") && !Objects.equals(elemento, "javaCode")){
                               String symbol = elemento.split(":")[0];

                               if (SymbolTable.getInstance().SymbolIsNonTerminal(symbol)){
                                   System.out.print("<"+symbol.toUpperCase()+">");
                                   fixedElementOfGrammar += ("<"+symbol.toUpperCase()+">");
                               }
                                if (SymbolTable.getInstance().SymbolIsTerminal(symbol)) {
                                   char c = (char) (r.nextInt(26) + 'a');
                                   if (symbol.length() > 1){
                                       while (taken.contains(c)){
                                           c = (char) (r.nextInt(26) + 'a');
                                       }
                                       taken.add(c);
                                   }
                                   else{
                                       taken.add(symbol.charAt(0));
                                       c = symbol.charAt(0);
                                   }

                                    System.out.print(c);
                                    fixedElementOfGrammar += (String.valueOf(c));
                                }
                            }
                       } 
                          pos++;
                        }
                        System.out.println();
                    stringList.add(fixedElementOfGrammar);
                   System.out.println(fixedElementOfGrammar);
       
                        
       } 
    }
                    try {
         FileOutputStream tableObject =
         new FileOutputStream("C:\\\\Users\\\\Sequeirios\\\\Documents\\\\NetBeansProjects\\\\LParserTest\\\\src\\\\table.cupra");
         /*C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\Sym.java*/
         ObjectOutputStream out = new ObjectOutputStream(tableObject);
         out.writeObject(general);
         out.close();
         tableObject.close();
       //  System.out.printf("Serialized data is saved in /tmp/employee.ser");
      }catch(IOException i) {
         i.printStackTrace();
      }
        
 System.out.println();


            //Gson gson = new Gson();
     
stringArray = new String[stringList.size()];

            int x = 0;
            for (String prod: stringList ) {
                stringArray[x] = prod;
                x++;
            }

            Automaton.Grammar  Grammar = new Automaton.Grammar(stringArray); //Se crea un objeto de la clase grammar y se inicializa con las producciones de la gramática

        
            
          
            if (Grammar.isValid())
            {
                LR1Parser lr1 = new LR1Parser(Grammar);

                List<State> states = lr1.getAutomaton().getStatesOfAutomaton();

               // String json = gson.toJson(states);

                printStatesOfAutomaton(states);

                lr1.getAutomaton().ConvertLr1ToLalr();

                System.out.println("\nNEW STATES AFTER CONVERTION\n");

                printStatesOfAutomaton(lr1.getAutomaton().getStatesOfAutomaton());

                lr1.buildLALRParsingTable();

                Integer columns = lr1.grammar.getTerminals().size() + lr1.grammar.getNonTerminals().size()-1;

                ArrayList<String> symbols = (ArrayList<String>) lr1.grammar.getTerminals().clone();
                for (NonTerminal nonTerminal :
                        lr1.grammar.getNonTerminals()) {
                    if (!nonTerminal.getNonTerminal().contains("'"))
                        symbols.add(nonTerminal.getNonTerminal());
                }

                String[] arreglo = new String[columns];
                int y = 0;
                for (String sym: symbols ) {
                    arreglo[y] = sym;
                    y++;
                }

               

                // from a list
                for (State state : states) {
                   // state.getActions().forEach(action -> tl.addRow(action., action.getS1(), action.getS2());
                   for (Action action: state.getActions() ) {
                       if (action.getToState() != -1 || !Objects.equals(action.getAction(), "")){
                            System.out.println(state.toString()+" "+ action.getAction() +" de " + action.getToState() +" con simbolo "+action.getTerminal());
                        }
                    }
                    System.out.println();
             //   }
                
           
                            try {
         FileOutputStream lar1Object =
         new FileOutputStream("C:\\\\Users\\\\Sequeirios\\\\Documents\\\\NetBeansProjects\\\\LParserTest\\\\src\\\\Automaton.cupra");
        
         ObjectOutputStream out = new ObjectOutputStream(lar1Object);
         out.writeObject(lr1);
         out.close();
         lar1Object.close();
      
      }catch(IOException i) {
         i.printStackTrace();
      }
      
            //    System.out.println(response);

            
            
            }
                  boolean response =   EvaluateString("cd",lr1);


    }
               GenereteSym(general.Terminal);
              
               GenereteParser();
            }

   static void GenereteParser(){
       try{/** public final Class getSymbolContainer() {
    return sym.class;
}
import java.io.FileInputStream;*/
    PrintWriter writer = new PrintWriter("C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\parser.java", "UTF-8");
     writer.println("import java_cup.runtime.*;");
   /*import java.io.IOException;*/
      writer.println("import java.io.IOException; import java.util.Stack;import Automaton.State;import Automaton.Action;\n" +
"import java.util.List;");
      writer.println("import java.io.ObjectInputStream; ;import java.util.stream.Collectors;");
      writer.println("import java.io.FileInputStream;");
      writer.println("import Automaton.LR1Parser;");
      writer.println("import Semantic.SymbolTable; import java.io.FileReader;;");
       writer.println("import java_cup.runtime.XMLElement;");
     writer.println("public class parser  implements java.io.Serializable {"
             + "  public parser(Lexer lexer) {\n" +
"    }");
      writer.println(" public final Class getSymbolContainer() {");
       writer.println("return sym.class;");
       writer.println("}  static boolean EvaluateString(String cadena,   LR1Parser lr1){\n" +
"       String buffer = cadena + \"$\";\n" +
"         Stack<Element_Of_Stack> stack = new Stack<Element_Of_Stack>();\n" +
"       State state;\n" +
"    Element_Of_Stack current_element_of_stack;String symbol;\n" +
"             List<Action> actions;\n" +
"            int index_of_buffer = 0;\n" +
"            boolean evaluar = true;\n" +
"             stack.push(new Element_Of_Stack(\"$\", 0));\n" +
"            if (cadena.length() > 0)\n" +
"                symbol = String.valueOf(buffer.charAt(index_of_buffer));\n" +
"            else\n" +
"            {\n" +
"                symbol = \"$\";\n" +
"            }          \n" +
"           while (evaluar)\n" +
"            {\n" +
"                current_element_of_stack = stack.peek();\n" +
"                state = lr1.getAutomaton().getState(current_element_of_stack.State);\n" +
"                \n" +
"               String c = symbol;\n" +
"                actions = state.actions.stream().filter(p -> p.getTerminal().equals(c)).collect(Collectors.toList());\n" +
"                String cadena_pila = \"\";\n" +
"               \n" +
"                 if (actions.size() > 0)\n" +
"                {\n" +
"                   \n" +
"                       if (actions.get(0).getAction().equals(\"Desplazamiento\"))\n" +
"                    {\n" +
"           \n" +
"            \n" +
"         \n" +
"                     symbol = String.valueOf(buffer.charAt(++index_of_buffer));\n" +
"                        stack.push(new Element_Of_Stack(actions.get(0).getTerminal(), actions.get(0).getToState()));\n" +
"                       \n" +
"\n" +
"                    } else\n" +
"                    {\n" +
"                        if (actions.get(0).getAction().equals(\"Reduccion\"))\n" +
"                        {\n" +
"                           int eliminar_pila = lr1.grammar.getProductions().get(actions.get(0).getToState()).Get_Number_Of_Grammar_Of_Symbols();\n" +
"                            for(int i = 0; i < eliminar_pila; i++)\n" +
"                            {\n" +
"                                stack.pop();\n" +
"                            }\n" +
"                            current_element_of_stack = stack.peek();\n" +
"                            state = lr1.getAutomaton().getState(current_element_of_stack.State);\n" +
"                          stack.push(new Element_Of_Stack(lr1.grammar.getProductions().get(actions.get(0).getToState()).leftSide, state.thereIsTransition(lr1.grammar.getProductions().get(actions.get(0).getToState()).leftSide)));\n" +
"                          \n" +
"                           \n" +
"                        }\n" +
"                        else\n" +
"                        {\n" +
"                            if (actions.get(0).getAction().equals(\"Aceptacion\"))\n" +
"                            {\n" +
"                             \n" +
"                                System.out.println(\"Aceptada\");\n" +
"                                break;\n" +
"                            }\n" +
"                            else\n" +
"                            {\n" +
"                               \n" +
"                                System.out.println(\"No Aceptada\");\n" +
"                                break;\n" +
"                            }\n" +
"                        }\n" +
"                    }\n" +
"                } \n" +
"                 \n" +
"                \n" +
"          \n" +
"            } return false;}");
           writer.println("public static void main(String args []) throws IOException, Exception {"    
                   + "    Lexer lexer = new Lexer(new FileReader(\"test.txt\"));;     Symbol current = lexer.next_token();");
       writer.println("");
    
       
       writer.println("LR1Parser parser = null;");
        writer.println("SymbolTable table = null;");
       writer.println("try {");
       writer.println(" FileInputStream fileIn = new FileInputStream(\"C:\\\\Users\\\\Sequeirios\\\\Documents\\\\NetBeansProjects\\\\LParserTest\\\\src\\\\Automaton.cupra\");");
          writer.println(" FileInputStream tableObject = new FileInputStream(\"C:\\\\Users\\\\Sequeirios\\\\Documents\\\\NetBeansProjects\\\\LParserTest\\\\src\\\\table.cupra\");");
         writer.println(" ObjectInputStream in = new ObjectInputStream(fileIn);");  
          writer.println(" parser = (LR1Parser) in.readObject();");  
               writer.println(" ObjectInputStream tab = new ObjectInputStream(tableObject);"); 
             writer.println("in.close();");
            writer.println("fileIn.close();");
       writer.println("} catch(IOException i ){");
       writer.println("i.printStackTrace();");
         writer.println("return;");
           writer.println("}catch(ClassNotFoundException c) {");
             writer.println(" System.out.println(\"Employee class not found\");");
              writer.println(" c.printStackTrace();");
                  writer.println(" return;");
                    writer.println("}");
            writer.println("}");
       
       /* Employee e = null;
      try {
               public static void main(String args []) throws IOException, Exception
    {
     
         FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         e = (Employee) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i) {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c) {
         System.out.println("Employee class not found");
         c.printStackTrace();
         return;
      }*/
     writer.println("}");
     
     
    writer.close();
} catch (IOException e) {
   // do something
}System.out.println(""); 
       
}
   static boolean EvaluateString(String cadena,   LR1Parser lr1){
       String buffer = cadena + "$";
         Stack<Element_Of_Stack> stack = new Stack<Element_Of_Stack>();
       State state;
    Element_Of_Stack current_element_of_stack;String symbol;
             List<Action> actions;
            int index_of_buffer = 0;
            boolean evaluar = true;
             stack.push(new Element_Of_Stack("$", 0));
            if (cadena.length() > 0)
                symbol = String.valueOf(buffer.charAt(index_of_buffer));
            else
            {
                symbol = "$";
            }          
           while (evaluar)
            {
                current_element_of_stack = stack.peek();
                state = lr1.getAutomaton().getState(current_element_of_stack.State);
                
               String c = symbol;
                actions = state.actions.stream().filter(p -> p.getTerminal().equals(c)).collect(Collectors.toList());
                String cadena_pila = "";
               
                 if (actions.size() > 0)
                {
                   
                       if (actions.get(0).getAction().equals("Desplazamiento"))
                    {
           
            
         
                     symbol = String.valueOf(buffer.charAt(++index_of_buffer));
                        stack.push(new Element_Of_Stack(actions.get(0).getTerminal(), actions.get(0).getToState()));
                       

                    } else
                    {
                        if (actions.get(0).getAction().equals("Reduccion"))
                        {
                           int eliminar_pila = lr1.grammar.getProductions().get(actions.get(0).getToState()).Get_Number_Of_Grammar_Of_Symbols();
                            for(int i = 0; i < eliminar_pila; i++)
                            {
                                stack.pop();
                            }
                            current_element_of_stack = stack.peek();
                            state = lr1.getAutomaton().getState(current_element_of_stack.State);
                          stack.push(new Element_Of_Stack(lr1.grammar.getProductions().get(actions.get(0).getToState()).leftSide, state.thereIsTransition(lr1.grammar.getProductions().get(actions.get(0).getToState()).leftSide)));
                          
                           
                        }
                        else
                        {
                            if (actions.get(0).getAction().equals("Aceptacion"))
                            {
                             
                                System.out.println("Aceptada");
                                break;
                            }
                            else
                            {
                               
                                System.out.println("No Aceptada");
                                break;
                            }
                        }
                    }
                } 
                 
                
          
            }
       
    return false;
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
       private static void printStatesOfAutomaton(List<State> states) {
        for (State state : states) {
            System.out.println(state.toString());
           /* for (ElementOfProduction element :
                    state.getElementsOfProductions()) {
                System.out.println(" "+element.toString());
            }*/

          /*  System.out.println("Transitions: ");
            for (Transition transition :
                    state.getTransitions()) {
                System.out.println(transition.toString());
            }*/
            System.out.println();
        }
    }

}

