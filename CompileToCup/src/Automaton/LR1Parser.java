/**
 * This class provides for initialization of grammars and productions
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

import Automaton.Grammar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class LR1Parser
{
    private Utils u;
    public Grammar g;                                                       // Grammar take from the input
    public ParsingAutomaton pa;                                             // Parsing automaton object

    public ArrayList<String> nonTerminalSymbols;                            // List of nonterminal symbols
    public ArrayList<String> terminalSymbols;                               // List of terminal symbols
    public HashMap<String, Production> productionsOfANonTerminal;           // Productions of any nonterminal symbol
    public HashMap<String, ArrayList<String>> firstSet;                     // First1 set


    /**
     * Constructor for the class
     *
     * @param givenGrammar
     */
    public LR1Parser(String givenGrammar)
    {
        this.g = new Grammar(givenGrammar);
        this.nonTerminalSymbols = new ArrayList<>();
        this.terminalSymbols = new ArrayList<>();
        this.productionsOfANonTerminal = new HashMap<>();
        this.firstSet = new HashMap<>();
        this.u = new Utils();
        this.pa = new ParsingAutomaton(this.g,this.productionsOfANonTerminal,this.nonTerminalSymbols,this.terminalSymbols);
    }

    /**
     * Generic method used for recall different function that allow us to parse the string
     * take from the input
     */
    public void execute()
    {
        System.out.print("\nCheck on grammar input and will calculate its augmented version.. \n");
        if(!checkInputGrammar())
        {
            System.out.println("\nThe inserted grammar is not correct \n");
            System.exit(-1);
        }

        System.out.print("\nAugmented grammar: \n\n");
        printGrammar();

        System.out.print("Calculation of relative parsing automaton...\n\n");

        this.pa.parsingAutomaton();

    }

    public boolean getInputStringToAutomaton(String stringToParse)
    {
        return this.pa.parse(stringToParse);
    }


    /**
     *  Parsing of the grammar to verify its correctness and create the augmented grammar
     *  Syntax control on the grammar take from the input
     *
     *  @return: boolean
     */

    public boolean checkInputGrammar()
    {
        int i = 0;
        String leftHandSide, character;
        Production newProduction, usedProductions;

        newProduction = new Production("W",g,this.productionsOfANonTerminal);                       // New axiom, augmented grammar
        newProduction.addProduction(u.getCharFromString(0,this.g.getGrammarString()));              // First character of the grammar, adding the production W->S, where S was the previous axiom of the grammar
        this.g.setGrammarAxiom("W");

        while(true)
        {
            leftHandSide = u.getCharFromString(i,this.g.getGrammarString());

            if(!nonTerminalSymbols.contains(leftHandSide))
                nonTerminalSymbols.add(leftHandSide);

            if((newProduction = productionsOfANonTerminal.get(leftHandSide)) == null)
            {
                newProduction = new Production(leftHandSide,g,this.productionsOfANonTerminal);
                if (!u.isUpperCase(leftHandSide))                                                   // If lhs isn't a nonterminal symbol, return false
                    return false;
            }

            i+=1;                                                                                   // next char

            if(!this.g.getGrammarString().substring(i, i + 2).equals("->"))                         // if the axiom is not followed by "->", the grammar isn't correct
                return false;

            i+=2;

            // Syntax analysis of the grammar
            for(int j = i; j < this.g.getGrammarString().length(); j++)
            {
                character = u.getCharFromString(j,this.g.getGrammarString());

                if(character.equals("|")){                                                          // production over, create it (from the SAME nonterminal)
                    newProduction.addProduction(this.g.getGrammarString().substring(i, j));
                    i = j + 1;
                }

                if(character.equals(";"))                                                           // production over, create it. After this step, new nonterminal will be analyzed
                {
                    newProduction.addProduction(this.g.getGrammarString().substring(i, j));
                    i = j + 1;
                    break;
                }

                if(character.equals("."))                                                            // finished grammar
                {
                    newProduction.addProduction(this.g.getGrammarString().substring(i, j));
                    this.g.setAlphabet((ArrayList<String>) nonTerminalSymbols.clone());
                    this.g.getAlphabet().addAll(terminalSymbols);
                    computeNullable();
                    computeFirst();
                    return true;
                }

                if (u.isUpperCase(character)){                                                      // if the character is a nonterminal, get his productions
                    if((usedProductions = productionsOfANonTerminal.get(character)) == null){
                        usedProductions = new Production(character,g,this.productionsOfANonTerminal);
                    }
                    if(!nonTerminalSymbols.contains(character))
                        nonTerminalSymbols.add(character);

                    usedProductions.addUsedProduction(new Pair<>(leftHandSide, newProduction.getRightHandSide().size()));
                }
                else if (!Pattern.matches("[A-Z[->|]]", character))                                 // Syntax control
                    if(!terminalSymbols.contains(character))
                        terminalSymbols.add(character);

            }
        }
    }

    /**
     *  Calculation of all the terminals that are nullable
     */

    public void computeNullable()
    {
        Production production;
        String rightHandSide;
        ArrayList<Production> listOfProductions = new ArrayList<>();                // List of all productions
        HashMap<String, Production> nullableMap = new HashMap<>();                  // Mapping of the terminal nullable with their relative productions

        // Mapping of the nonterminal with their productions
        for(String str: nonTerminalSymbols)
            nullableMap.put(str, new Production(productionsOfANonTerminal.get(str).getLeftHandSide().getCharacter(),
                    (ArrayList<String>) productionsOfANonTerminal.get(str).getRightHandSide().clone()
                    , productionsOfANonTerminal.get(str).isNullable()));

        // Analysis of all productions
        for(Production p: this.g.getProductions())
            if(p.isNullable())
                listOfProductions.add(nullableMap.get(p.getLeftHandSide().getCharacter()));

        while(listOfProductions.size() != 0)
        {
            // Scan of all productions used by a single production
            production = listOfProductions.get(0);
            listOfProductions.remove(0);
            for(Pair<String, Integer> t: production.usedProduction)
            {
                if(!productionsOfANonTerminal.get(t.x).isNullable())
                {
                    rightHandSide = nullableMap.get(t.x).getRightHandSide().get(t.y);
                    for(int i = 0; i < rightHandSide.length(); i++)
                    {
                        if(rightHandSide.substring(i, i+1).equals(production.getLeftHandSide().getCharacter()))
                            rightHandSide = rightHandSide.substring(0, i) + rightHandSide.substring(i+1, rightHandSide.length());
                    }
                    if(rightHandSide.length() == 0){
                        productionsOfANonTerminal.get(t.x).setNullable(true);
                        productionsOfANonTerminal.get(t.x).nullableProduction.add(t.y);
                        nullableMap.get(t.x).setNullable(true);
                        listOfProductions.add(nullableMap.get(t.x));
                    }
                    else
                        nullableMap.get(t.x).getRightHandSide().set(t.y, rightHandSide);
                }
            }
        }
        // At the end, the variable "nullableMap" have all the nullable symbol
    }

    /**
     *  Calculation of the function First1() for all symbols
     *  First1(x) is the set of the terminal symbol in Vt. beginning any string b such that S -->* x -->* b
     *  with the stipulation that First1(x) should have the extra element "e" iff either x is "e" or x is a nullable
     *  nonterminal symbol
     */

    private void computeFirst()
    {
        Production productionToAdd;
        ArrayList<String> temp;

        for(String a: terminalSymbols){
            firstSet.put(a, new ArrayList<>());
            firstSet.get(a).add(a);
        }

        for(String n: nonTerminalSymbols){
            firstSet.put(n, new ArrayList<>());
            productionToAdd = productionsOfANonTerminal.get(n);
            temp = new ArrayList<>();
            temp.add(productionToAdd.getLeftHandSide().getCharacter());
            productionToAdd.setOfFirstElement.addAll(algorithmToCalculateTheFirstSet(temp, productionToAdd.getLeftHandSide().getCharacter()));
        }

        for(Production p: this.g.getProductions()){
            Set<String> noDoubleElement = new HashSet<>();              // For avoid duplicate elements
            noDoubleElement.addAll(p.setOfFirstElement);
            p.setOfFirstElement.clear();
            p.setOfFirstElement.addAll(noDoubleElement);
        }

    }

    /**
     *  Recursive algorithm for calculating the First1() set
     *
     *  The following code follows the Algorithm 4.2.2, page 55 of the book "Techniques for searching, parsing and matching"
     *
     */

    public ArrayList<String> algorithmToCalculateTheFirstSet(ArrayList<String> string, String next)
    {
        Production production =  productionsOfANonTerminal.get(next);
        ArrayList<String> listOfFirstSet = new ArrayList<>();
        Production productionTemp;

        for(int j = 0; j < production.getRightHandSide().size(); j++)
        {
            String rightHandSide =  production.getRightHandSide().get(j);
            if(!rightHandSide.equals("e"))                                                                  // if right hand side is "e", i don't consider it
            {
                for(int i = 0; i < rightHandSide.length(); i++){
                    if(terminalSymbols.contains(rightHandSide.substring(i,i + 1)))                          // Case (ii) of the algorithm on the book
                    {
                        listOfFirstSet.add(rightHandSide.substring(i,i + 1));
                        break;
                    }
                    else {                                                                                  // Case (iii)
                        productionTemp = productionsOfANonTerminal.get(rightHandSide.substring(i, i+1));
                        if(!productionTemp.isNullable())
                        {
                            if(string.contains(productionTemp.getLeftHandSide().getCharacter()))
                                break;
                            string.add(productionTemp.getLeftHandSide().getCharacter());
                            listOfFirstSet.addAll(algorithmToCalculateTheFirstSet(string, productionTemp.getLeftHandSide().getCharacter()));
                            break;
                        }
                        else {
                            if(string.contains(productionTemp.getLeftHandSide().getCharacter()))
                                continue;
                            string.add(productionTemp.getLeftHandSide().getCharacter());
                            listOfFirstSet.addAll(algorithmToCalculateTheFirstSet(string, productionTemp.getLeftHandSide().getCharacter()));
                        }
                    }

                }
            }

        }

        return listOfFirstSet;
    }


    /**
     * Screen printing of the grammar
     */
    public void printGrammar()
    {

        for(Production p: this.g.getProductions())
        {
            System.out.print(p.getLeftHandSide().getCharacter()+ "->");
            for(String rhs: p.getRightHandSide()){
                System.out.print(rhs);
                if (!rhs.equals(p.getRightHandSide().get(p.getRightHandSide().size() - 1)))
                    System.out.print(" | ");
            }
            System.out.print("\n\n");
        }

    }

}
