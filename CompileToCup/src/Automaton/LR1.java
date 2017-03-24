package Automaton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import TREE.Production;
import TREE.ProductionNode;
import TREE.SentencesNode;
import java.util.List;

/**
 *
 * @author Sequeirios
 */
public class LR1 extends Automaton {

    @Override
    public boolean evaluateAutomaton(List<SentencesNode> sentences) {
  
        int split =0;
        boolean productionNoFound = true;
        
        while(productionNoFound  && split < sentences.size()){
                 if(sentences.get(split) instanceof ProductionNode){
                        productionNoFound = false;           
         
                 }else{
                          split++;
                 }
        
                 
        }
         
    
     if(!productionNoFound)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
         buildAutomaton(sentences.subList(split, sentences.size())); // verificar si esta enviando el size correcto
       
              
        return true;
               
  
}
  
        
        
   

    private void buildAutomaton(List<SentencesNode> subList) {
        System.out.println("entro");   
        String grammarOfAutomaton="";
        for(int i = 0; i<subList.size(); i++){
           grammarOfAutomaton+=(buildProductionGrammar((ProductionNode) subList.get(i)));
           if(i+1== subList.size())
                 grammarOfAutomaton+='.';
           else
              grammarOfAutomaton+=';';
        }
          grammarOfAutomaton=   removeWords(grammarOfAutomaton,"javaCode");
          grammarOfAutomaton = removeWords(grammarOfAutomaton,":");
          grammarOfAutomaton = removeWords(grammarOfAutomaton," ");
          LR1Parser p = new LR1Parser(grammarOfAutomaton);
        p.execute();
    }
 
     
    
    private String buildProductionGrammar( ProductionNode produc){
     
         String producGrammar = "";
         producGrammar +=produc.nonTerminal+"->";
         int count = 0;
         boolean endOfProductions = false;
         while(endOfProductions == false){
            
            producGrammar+=produc.allProduction.get(count).name;
             count++;
            if(count == produc.allProduction.size())
             endOfProductions =true;
            else
               producGrammar+="|";
            
           
         }
         return producGrammar;
         
        
    }
    public static String removeWords(String word ,String remove) {
    return word.replace(remove,"");
}
  
    private String  getFirst(ProductionNode production){    //Esto deberia devolver un baseType o algo que devuelva el semantico
       return null;
    }
    
    private String GetSecond(ProductionNode production){  // Obtener el segundo de esa producion 
      
     return null;
    }

   
 
    
}
