/**
 * This class represent a parsing automaton
 * In addition to the representation, it builds parsing table
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class ParsingAutomaton
{
    public Grammar augmentedGrammar;                                                    // Grammar transformed into augmented grammar
    public HashMap<String ,Production> maps;                                            // Mapping of productions
    public ArrayList<String> nonTerminals;                                              // List of the nonterminal symbols
    public ArrayList<String> terminals;                                                 // List of the terminal symbols

    public ArrayList<State> listOfStates;                                               // States of the automaton
    public int numberOfState;                                                           // Number of states of the automaton
    private ArrayList<String> stackOfAutomaton;                                         // Stack of the automaton

    // Two different list for the states, because i need to distinguish between all states of the automaton and the states which use for the closure rules
    public ArrayList<State> tailStates;
    public ArrayList<State> tailStatesToElaborate;

    private ArrayList<HashMap<String, Action>> parsingTable;                            // LR(1) parsing table
    private ArrayList<Production> productionToReduce;                                   // List of productions to reduce


    /**
     * Constructor for the class
     * @param g
     * @param m
     * @param nT
     * @param t
     */

    public ParsingAutomaton(Grammar g,HashMap<String, Production> m,ArrayList<String> nT,ArrayList<String> t)
    {
        this.augmentedGrammar = g;
        this.maps = m;
        this.nonTerminals = nT;
        this.terminals = t;
        this.listOfStates = new ArrayList<>();
        this.tailStates = new ArrayList<>();
        this.tailStatesToElaborate = new ArrayList<>();
        this.numberOfState = 0;
        this.stackOfAutomaton = new ArrayList<>();
    }

    /**
     * Generic method, used for starting the build of the automaton and of the parsing table
     */
    public void parsingAutomaton()
    {
        createAutomaton();
        if(!buildTable()) {
            System.out.println("The grammar isn't LR(1)");
            System.exit(-1);
        }
        printTable();
    }

    /**
     * Build of the LR(1) parsing table
     * @return
     */
    public boolean buildTable()
    {
        State next;
        HashMap<String, Action> row;
        productionToReduce = new ArrayList<>();
        parsingTable = new ArrayList<>();

        this.augmentedGrammar.getAlphabet().clear();
        terminals.add("$");                                                                                     // add to the set of terminal symbol, the symbol "$"
        this.augmentedGrammar.getAlphabet().addAll(terminals);
        this.augmentedGrammar.getAlphabet().addAll(nonTerminals);

        for(State s: listOfStates){
            row = new HashMap<>();
            parsingTable.add(row);                                                                              // New row for the table
            for(String a: this.augmentedGrammar.getAlphabet()){
                next = s.getNextState(a);                                                                       // Take the state from the arc "s"
                if(next != null){
                    if(!Pattern.matches("[A-Z]", a))
                        row.put(a, new Action("sh", next.getState()));                                          // Shift
                    else
                        row.put(a, new Action("goto", next.getState()));                                        // Goto
                }

                // If s is the axiom and the relative item is a complete item, we state in a final state and accept
                if(s.items.get(0).getLeftHandSide().getCharacter().equals(this.augmentedGrammar.getGrammarAxiom()) && s.items.get(0).end){
                    row.put("$", new Action("acc", 0));                                                         // Accept
                    break;
                }

                if(a.equals(this.augmentedGrammar.getAlphabet().get(0)))
                    // Analysis of the items of the state
                    for(Item i: s.items){
                        if(i.getCharRightOfTheDot().getCharacter().equals("") && i.end) {                       // Complete item, production to reduce
                            productionToReduce.add(new Production(i.getLeftHandSide().getCharacter(),this.augmentedGrammar,this.maps));
                            productionToReduce.get(productionToReduce.size() - 1).addProduction(i.getLeftOfTheDot());
                            // Analysis of the lookahead set
                            for (String str : i.getLookaheadSet()) {
                                row.put(str, new Action("red", productionToReduce.size() - 1));
                            }
                        }
                    }
            }

        }

        return true;
    }

    /**
     * Screen printing of the LR(1) parsing table
     */

    public void printTable()
    {
        HashMap<String, Action> h;
        Production p;
        this.augmentedGrammar.getAlphabet().remove("e");

        System.out.print("\n\n LR(1) Parsing Table: \n\n   ");
        System.out.print("\n           |");
        for(String s: this.augmentedGrammar.getAlphabet())
            if(!s.equals("e"))                                              // if entry is empty
                System.out.print("    " + s + "   |");

        System.out.print("\n   --------|\n");

        for(int j = 0; j < parsingTable.size(); j++){
            System.out.print("     ");
            h =  parsingTable.get(j);
            if(j < 10)
                System.out.print("  Q" + j + "  |");
            else
                System.out.print(" Q" + j + "  |");
            for(String s: this.augmentedGrammar.getAlphabet()){
                if(s.equals("e"))
                    continue;
                if(h.get(s) != null){
                    if (h.get(s).isGotoAction() && h.get(s).numberOfAction >= 10)
                        System.out.print("goto " +h.get(s).numberOfAction + " |");
                    else if(h.get(s).isGotoAction() && h.get(s).numberOfAction < 10 )
                        System.out.print(" goto " +h.get(s).numberOfAction + " |");
                    else if(h.get(s).isAcceptAction())
                        System.out.print(" acc   |");
                    else if(h.get(s).isShiftAction() && h.get(s).numberOfAction < 10)
                        System.out.print(" sh " + h.get(s).numberOfAction + "   |");
                    else if(h.get(s).isShiftAction() && h.get(s).numberOfAction >= 10)
                        System.out.print(" sh " + h.get(s).numberOfAction + "   |");
                    else if(h.get(s).isReduceAction() && h.get(s).numberOfAction >= 10)
                        System.out.print(" red " + h.get(s).numberOfAction + "  |");
                    else if(h.get(s).isReduceAction())
                        System.out.print(" red " + h.get(s).numberOfAction + "  |");
                }
                else
                    System.out.print("        |");
            }
            System.out.print("\n           |\n");
        }
        System.out.print("\n\n Where 'n' is the number identifying the productions: \n\n");
        for(int i = 0; i < productionToReduce.size(); i++){
            p = productionToReduce.get(i);
            if(p.getRightHandSide().get(0).equals(""))
                System.out.print("     " + i + ": " + p.getLeftHandSide().getCharacter() +" -> e\n");
            else
                System.out.print("     " + i + ": " + p.getLeftHandSide().getCharacter() +" -> " + p.getRightHandSide().get(0) + "\n");
        }
    }


    /**
     * Calculation of the parsing automaton
     * In the following code, it was avoided to use the Powerset Construction Procedure by using other rules for
     * constructing the finite automaton
     *
     * It was taken as a reference the paragraph 5.4.1, page 118 of the book "Techniques for searching, parsing and matching
     *
     * @return boolean
     */

    public boolean createAutomaton()
    {
        Production production;
        Item item;                                                              // Item
        ArrayList<String> lookAheadSet;                                         // Lookahead set
        ArrayList<Item> itemsList;                                              // Items of the automaton
        State newState,genericState, initialState;                              // Temporary variables
        boolean change;                                                         // boolean value, that indicates if the closure rule (2) and (3) must be apply
        int stateOfLoop,k=0;
        String lhs;                                                             // Temporary variable

        /**
         * Initialization Rule
         * Build of the initial state Q0 of the finite automaton
          */

        production = this.augmentedGrammar.getProductions().get(0);             // Production of the automaton
        initialState = createInitialState(production);
        addToQueue(initialState);                                               // add it to queue of all states
        addE(initialState);                                                     // add it to queue of all states that have not yet been elaborated

        while(true)
        {
            k+=1;
            change = false;

            /**
             * Closure Rule (1)
             */
            while(!emptyQueue())                                                // while all the states aren't analyzed
            {
                genericState = getFromQueue();                                  // At the first visit, we start from the initial state

                // Analysis of the items of the state
                for(int j = 0; j < genericState.items.size(); j++)
                {
                    Item i =  genericState.items.get(j);
                    lhs = i.getCharRightOfTheDot().getCharacter();
                    if(Pattern.matches("[A-Z]", lhs))           // if the character at the right of the dot of "i" is a nonterminal symbol, take the productions
                    {
                        lookAheadSet = genericState.getLookAheadSet(i.getLookaheadSet(), i.getRightOfTheDot());
                        production = maps.get(lhs);
                        for(String rhs: production.getRightHandSide())      // add the items for the nonterminal symbol at the right of the dot
                        {
                            if(rhs.equals("e"))
                            {
                                item = new Item(new Char(lhs), "", new Char(""),"", lookAheadSet);
                                item.end = true;                // complete item
                            }
                            else
                                item =  new Item(new Char(lhs), "", new Char(rhs.substring(0,1))
                                        ,rhs.substring(1,rhs.length()), lookAheadSet);
                            item.setStateOfLoop(genericState.getItems(lhs).get(0).getStateOfLoop());
                            genericState.addItem(item);
                        }
                    }

                }
                addE(genericState);     // add genericState to the elaborate queue (that contains the states which passed the closure rule (1))
            }


            /**
             * Closure Rule (2) and (3)
             */

            // Analysis of the elaborate queue
            while(!emptyEQueue())
            {
                genericState = getFromEQueue();
                for (String chr : this.augmentedGrammar.getAlphabet()) {
                    if(chr.equals("e"))
                        continue;
                    itemsList = genericState.getItems(chr);             // take all items
                    if (itemsList != null) {
                        change = true;
                        if((stateOfLoop = genericState.isLoop(itemsList)) != -1)      // Searching for possible loop
                            genericState.addArcsOfTheAutomaton(chr, listOfStates.get(stateOfLoop));         // if isn't a loop

                        else if((newState = findSimilState(itemsList, genericState)) != null) {
                            genericState.addArcsOfTheAutomaton(chr, newState);
                        }
                        else
                        {
                            newState = createNewState();
                            addToQueue(newState);
                            newState.setItemsListShifted(itemsList);
                            genericState.addArcsOfTheAutomaton(chr, newState);
                        }

                    }
                }
            }

            if (!change)
                break;
        }

        printFiniteAutomaton();

        return true;
    }


    /**
     * Method that create the initial state of the automaton
     * @param p
     * @return
     */
    public State createInitialState(Production p)
    {
        ArrayList<String> lookAheadSet;
        Item axiomItem;
        State initialState;

        initialState = createNewState();
        lookAheadSet = new ArrayList<>();
        lookAheadSet.add("$");
        axiomItem = new Item(p.getLeftHandSide(), "", new Char(p.getRightHandSide().get(0)), "", lookAheadSet);
        initialState.addItem(axiomItem);

        return initialState;
    }

    public void addToQueue(State s)
    {
        tailStates.add(s);
    }

    public State getFromQueue(){
        State s;
        s = tailStates.get(0);
        tailStates.remove(0);

        return s;
    }

    public boolean emptyQueue(){

        return tailStates.size() == 0;
    }

    public void addE(State s)
    {
        if(!tailStatesToElaborate.contains(s))
            tailStatesToElaborate.add(s);
    }

    public State getFromEQueue(){
        State s;
        s = tailStatesToElaborate.get(0);
        tailStatesToElaborate.remove(0);

        return s;
    }

    public boolean emptyEQueue(){

        return tailStatesToElaborate.size() == 0;
    }

    public void clear(){
        stackOfAutomaton = new ArrayList<>();
    }

    /**
     * Method for creating a new state and, after, adding them to the list of states
     * @return
     */
    public State createNewState()
    {
        State newState = new State(getNewStateValue(),this.maps);
        listOfStates.add(newState);
        return newState;
    }

    /**
     * Method to take the string from the stack of the automaton
     * @return String
     */
    public String getStringFromStackOfAutomaton()
    {
        String str;
        str = stackOfAutomaton.get(stackOfAutomaton.size() - 1);
        stackOfAutomaton.remove(stackOfAutomaton.size() -1);

        return str;
    }

    public String read(){
        return stackOfAutomaton.get(stackOfAutomaton.size() -1);
    }

    public void add(String n){
        stackOfAutomaton.add(n);
    }

    public int getNewStateValue()
    {
        numberOfState = numberOfState + 1;
        return numberOfState - 1;
    }

    /**
     * Method for check there are duplicate of states
     * @param itemsList
     * @param state
     * @return
     */
    private State findSimilState(ArrayList<Item> itemsList, State state) {

        for(State s: listOfStates){
            for(int i = 0; i < 1; i++){
                if(s != state && s.items.get(i).getLeftHandSide().getCharacter().equals(itemsList.get(i).getLeftHandSide().getCharacter()) &&
                        s.items.get(i).getLeftOfTheDot().equals(itemsList.get(i).getLeftOfTheDot() + itemsList.get(i).getCharRightOfTheDot().getCharacter()) &&
                        s.items.get(i).comparisonWithLookaheadSet(itemsList.get(i).getLookaheadSet()))
                    return s;
            }
        }
        return null;
    }


    /**
     * Screen printing of the finite automaton
     */

    public void printFiniteAutomaton()
    {
        System.out.print("Big Productions: \n---------------------\n");
        for(State s: listOfStates)
        {
            System.out.print("Q" + s.getState() + "\n");
            // For any item of the state
            for(Item i: s.items){
                System.out.print(i.getLeftHandSide().getCharacter() + " -> " + i.getLeftOfTheDot() + "." +i.getCharRightOfTheDot().getCharacter() + " " + i.getRightOfTheDot() + ", { ");
                for(int j = 0; j < i.getLookaheadSet().size(); j++)
                    System.out.print(i.getLookaheadSet().get(j) + " ");
                System.out.print("}\n");
            }
            System.out.print("--\n");
        }

        State next;
        int i=0;
        ArrayList<String> V = (ArrayList)nonTerminals.clone();
        V.addAll(terminals);

        System.out.print("\nArcs: \n------------------------\n");
        for(State s: listOfStates)
        {
            for(String c: V)
            {
                next = s.getNextState(c);
                if(next != null) {
                    i += 1;
                    System.out.print("From Q" + s.getState() + " to Q" + next.getState() + " by label: " + c + " \n");
                }
            }
            if(i==0)
                System.out.print("There isn't arcs from Q"+s.getState()+"\n");
            else
                i=0;
            System.out.print("------------------------\n");
        }

    }

    /**
     * Method which use the parsing table to verify if the string take from the input belongs to the given grammar
     * @param stringToParse
     * @return
     */

    public boolean parse(String stringToParse)
    {
        String input, rightHandSide, leftHandSide;
        int state, operationNum=0;
        Action action;
        Production production;

        clear();
        add("0");                                               // Add to stack the initial state
        stringToParse = stringToParse + "$";                    // Add to the string to parse the symbol "$"

        System.out.println("\nThe sequence of configurations of the parsing automaton\n" +
                "while parsing "+stringToParse+" using the LR(1) parsing table:\n\n");

        while(true)
        {
            input = stringToParse.substring(0,1);
            if(input.equals("e")){                                                  // If the first character is the epsilon, ignore it and continue
                stringToParse = stringToParse.substring(1, stringToParse.length());
                continue;
            }
            state = Integer.parseInt(read());
            action = parsingTable.get(state).get(input);
            if (action == null)
                return false;
            System.out.print(+operationNum+". Stack: " + stackOfAutomaton
                    + "\n   Input: "+ input + "\n   Action: " + action.action + " " + action.numberOfAction + " ");

            /**
             * Execute shift action
             */
            if(action.isShiftAction())
            {
                add(input);
                add(action.getNumberOfAction() + "");
                stringToParse = stringToParse.substring(1,stringToParse.length());
            }

            /**
             * Execute reduce action
             */
            else if(action.isReduceAction())
            {
                production = productionToReduce.get(action.getNumberOfAction());
                rightHandSide = production.getRightHandSide().get(0);
                leftHandSide = production.getLeftHandSide().character;

                while(rightHandSide.length() != 0){
                    getStringFromStackOfAutomaton();
                    input = rightHandSide.substring(rightHandSide.length() - 1, rightHandSide.length());
                    rightHandSide = rightHandSide.substring(0, rightHandSide.length() - 1);
                    if(!input.equals(getStringFromStackOfAutomaton()))
                        return false;
                }
                state = Integer.parseInt(read());
                add(leftHandSide);
                action = parsingTable.get(state).get(leftHandSide);

                if(action.isGotoAction())
                {
                    add(action.getNumberOfAction()+"");
                    System.out.println("goto: " + action.getNumberOfAction());
                }
                else
                    return false;

            }

            /**
             * Execute accept action
             */
            else if(action.isAcceptAction()){
                System.out.println("\n\n");
                return true;
            }
            System.out.println("\n\n");
            operationNum+=1;
        }
    }


}
