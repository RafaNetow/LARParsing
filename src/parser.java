import java_cup.runtime.*;
import java.io.IOException; import java.util.Stack;import Automaton.State;import Automaton.Action;
import java.util.List;
import java.io.ObjectInputStream; ;import java.util.stream.Collectors;
import java.io.FileInputStream;
import Automaton.LR1Parser;
import Semantic.SymbolTable; import java.io.FileReader;;
import java_cup.runtime.XMLElement;
public class parser  implements java.io.Serializable {  public parser(Lexer lexer) {
    }
 public final Class getSymbolContainer() {
return sym.class;
}  static boolean EvaluateString(String cadena,   LR1Parser lr1){
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
                 
                
          
            } return false;}
public static void main(String args []) throws IOException, Exception {    Lexer lexer = new Lexer(new FileReader("C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\test.txt"));;     Symbol current = lexer.next_token();

LR1Parser parser = null;
SymbolTable table = null;
try {
 FileInputStream fileIn = new FileInputStream("C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\Automaton.cupra");
 FileInputStream tableObject = new FileInputStream("C:\\Users\\Sequeirios\\Documents\\NetBeansProjects\\LParserTest\\src\\table.cupra");
 ObjectInputStream in = new ObjectInputStream(fileIn);
 parser = (LR1Parser) in.readObject();
 System.out.println(parser.grammar.terminals.size());
 ObjectInputStream tab = new ObjectInputStream(tableObject);
in.close();
fileIn.close();
} catch(IOException i ){
i.printStackTrace();
return;
}catch(ClassNotFoundException c) {
 System.out.println("Employee class not found");
 c.printStackTrace();
 return;
}
}
}
