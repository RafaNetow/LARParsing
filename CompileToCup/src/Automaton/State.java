/**
 * This class represent a generic state of the parsing automaton.
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class State
{
    public int state;                                                                       // Number of the state
    public HashMap<String, State> arcsOfTheAutomaton;                                       // Arcs of the automaton <Value of arcs, state>
    public ArrayList<Item> items;                                                           // Items associated with the state
    public ArrayList<Pair<String, ArrayList<String>>> elemUsedForClosureRule;               // List of elements necessary to apply correctly the closure rule
    public HashMap<String, ArrayList<Item>> itemMap;                                        // Mapping of all items
    public HashMap<String, Production> productionMap;                                       // Mapping of all productions


    /**
     * Constructor for the class
     * @param state
     * @param m
     */
    public State(int state, HashMap<String, Production> m)
    {
        this.state = state;
        this.arcsOfTheAutomaton = new HashMap<>();
        this.items = new ArrayList<>();
        this.elemUsedForClosureRule = new ArrayList<>();
        this.itemMap = new HashMap<>();
        this.productionMap = m;
    }

    public int getState() {
        return state;
    }

    public void addArcsOfTheAutomaton(String chr, State s) {
        this.arcsOfTheAutomaton.put(chr, s);
    }


    /**
     * Find if there are loops after application closure(2) and (3)
     * @return: int
     */

    public int isLoop(ArrayList<Item> listOfItem)
    {

        ArrayList<String> lookaheadSet1, lookaheadSet2;
        String leftOfTheDot1, leftOfTheDot2, rightOfTheDot2;

        // Analysis of all items of the automaton
        for (Item item : listOfItem)
        {
            if (!item.getLoop())                    // if there isn't a loop
            {
                leftOfTheDot1 = item.getLeftHandSide().getCharacter();
                lookaheadSet1 = item.getLookaheadSet();
                if (Pattern.matches("[A-Z]", item.getCharRightOfTheDot().getCharacter()))           // if is a nonterminal symbol
                {
                    for (Item j : items)
                    {
                        leftOfTheDot2 = j.getLeftHandSide().getCharacter();
                        rightOfTheDot2 = j.getLeftOfTheDot();
                        lookaheadSet2 = j.getLookaheadSet();
                        if (leftOfTheDot2.equals(leftOfTheDot1) && rightOfTheDot2.length() == 0 &&
                                (item.getLeftOfTheDot() + item.getCharRightOfTheDot().getCharacter() +
                                        item.getRightOfTheDot()).equals(rightOfTheDot2 + j.getCharRightOfTheDot().getCharacter()
                                        + j.getRightOfTheDot()))
                        {
                            for (String s : lookaheadSet1)
                            {
                                if (!lookaheadSet2.contains(s))
                                    return -1;
                            }
                            j.setLoop(true);
                        }
                    }
                }
            }
            else
                return item.getStateOfLoop();
        }
        return -1;
    }

    public State getNextState(String c) {
        return arcsOfTheAutomaton.get(c);
    }


    /**
     * Adding a new item
     * @param item
     */
    public void addItem(Item item)
    {
        this.items.add(item);

        if (this.itemMap.get(item.getCharRightOfTheDot().getCharacter()) == null)               // if there isn't in itemMap, add it
            itemMap.put(item.getCharRightOfTheDot().getCharacter(), new ArrayList<>());

        this.itemMap.get(item.getCharRightOfTheDot().getCharacter()).add(item);

        if (item.getCharRightOfTheDot().isUpperCase())                          // If at the right of the dot, there is a nonterminal symbol, i need of new one (or more) items, thus will be an element necessary for the closure rule
            elemUsedForClosureRule.add(new Pair<>(item.getCharRightOfTheDot().getCharacter(), item.getLookaheadSet()));
    }


    /**
     * Calculation lookahead set.
     * The following code follows the steps shown on page 103 of the book "Techniques for searching, parsing and matching"
     *
     * @param L
     * @param right
     * @return ArrayList
     */

    public ArrayList<String> getLookAheadSet(ArrayList<String> L, String right)
    {
        ArrayList<String> oldLookaheadSet = (ArrayList<String>) L.clone();
        ArrayList<String> newLookaheadSet;

        if (right.length() == 0)                            // case (i), right = e
            return oldLookaheadSet;
        else if (isNullable(right))                         // case (ii), right != e, right -->* e
        {
            newLookaheadSet = new ArrayList<>();
            newLookaheadSet.addAll(oldLookaheadSet);
            newLookaheadSet.addAll(first(right));
        }
        else                                                // case (iii), right != e, right !-->* e
            newLookaheadSet = first(right);

        Set<String> noDoubleElement = new HashSet<>();      // To avoid duplicate elements
        noDoubleElement.addAll(newLookaheadSet);
        newLookaheadSet.clear();
        newLookaheadSet.addAll(noDoubleElement);

        return newLookaheadSet;
    }


    /**
     * Setting of the item after applying the closure rule
     *
     * @param list
     */

    public void setItemsListShifted(ArrayList<Item> list)
    {
        Item newItem;
        Char character;
        String rightOfTheDot;

        for (Item item : list)
        {
            if (item.getRightOfTheDot().length() == 0)                                  // Complete item, if there isn't elements after the dot
            {
                rightOfTheDot = "";
                character = new Char("");
                newItem = new Item(item.getLeftHandSide(), item.getLeftOfTheDot() +
                        item.getCharRightOfTheDot().getCharacter(), character, rightOfTheDot, item.getLookaheadSet());
                newItem.end = true;                                                     // Marked the complete item
            }
            else                                                                        // if isn't a complete item
            {
                ArrayList<String> lookAhead = (ArrayList<String>) item.getLookaheadSet().clone();       // Take the lookahead set
                character = new Char(item.getRightOfTheDot().substring(0, 1));
                rightOfTheDot = item.getRightOfTheDot().substring(1, item.getRightOfTheDot().length());
                newItem = new Item(item.getLeftHandSide(), item.getLeftOfTheDot() +
                        item.getCharRightOfTheDot().getCharacter(), character, rightOfTheDot, lookAhead);
                newItem.setStateOfLoop(item.getStateOfLoop());
            }

            if (newItem.getLeftOfTheDot().length() == 1)
                newItem.setStateOfLoop(this.getState());

            this.addItem(newItem);
        }
    }


    public ArrayList<Item> getItems(String elem) {
        return itemMap.get(elem);
    }

    /**
     * Method that check if a string is nullable
     * @param str
     * @return boolean
     */
    private boolean isNullable(String str)
    {

        for (int i = 0; i < str.length(); i++) {
            if (!Pattern.matches("[A-Z]", str.substring(i, i + 1)))
                return false;
            else if (!productionMap.get(str.substring(i, i + 1)).isNullable())
                return false;
        }
        return true;
    }

    /**
     * Compute the First set
     *
     * The following code follows the algorithm at page 55, of the book "Techniques for searching, parsing
     * and matching"
     *
     * @param toAnalyze
     * @return ArrayList
     */

    public ArrayList<String> first(String toAnalyze)
    {
        ArrayList<String> listOfFirstElement = new ArrayList<>();

        if (toAnalyze.length() == 0)                                            // Case (i), toAnalyze is "e"
            return listOfFirstElement;

        if (!Pattern.matches("[A-Z]", toAnalyze.substring(0, 1))                // Case (ii), toAnalyze is a terminal symbol
                && !toAnalyze.substring(0, 1).equals("e"))
        {
            listOfFirstElement.add(toAnalyze.substring(0, 1));
            return listOfFirstElement;
        }
        else {                                                                  // Case (iii), toAnalyze is a nonterminal symbol
            if (productionMap.get(toAnalyze.substring(0, 1)).isNullable()) {
                listOfFirstElement.addAll(productionMap.get(toAnalyze.substring(0, 1)).setOfFirstElement);
                listOfFirstElement.addAll(first(toAnalyze.substring(1, toAnalyze.length())));           // Recursive call, as indicated by the algorithm in the book
                return listOfFirstElement;
            } else {
                listOfFirstElement.addAll(productionMap.get(toAnalyze.substring(0, 1)).setOfFirstElement);
                return listOfFirstElement;
            }
        }
    }
}
