/**
 * This class represents an item of parsing automaton
 * An item is a production of the grammar with one extra symbol "." (dot) in any
 * position in the right hand side.
 * For a production whose right hand side has length n, we have n+1 items
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

import java.util.ArrayList;

public class Item
{
    public Char leftHandSide;                       // Left hand side
    public String leftOfTheDot;                     // String at the left of the dot
    public Char charRightOfTheDot;                  // Char immediately at the right of the dot
    public String rightOfTheDot;                    // String at the right of the dot
    public ArrayList<String> lookaheadSet;          // Lookahead set


    public boolean end;                             // boolean value, which indicates if the item is an complete item
    public int stateOfLoop;
    public boolean loop;                            // boolean value that indicates if there is a loop


    /**
     * Constructor for the class
     * @param lhs
     * @param leftOfTheDot
     * @param r
     * @param rightOfTheDot
     * @param lookaheadSet
     */
    public Item(Char lhs, String leftOfTheDot, Char r, String rightOfTheDot, ArrayList<String> lookaheadSet){
        this.leftHandSide = lhs;
        this.leftOfTheDot = leftOfTheDot;
        this.charRightOfTheDot = r;
        this.rightOfTheDot = rightOfTheDot;
        this.lookaheadSet = lookaheadSet;
        this.end = false;
        this.loop = false;
    }

    /**
     * Method used for compare two lookahead set
     * @param listToCompareWithLookaheadSet
     * @return
     */
    public boolean comparisonWithLookaheadSet(ArrayList<String> listToCompareWithLookaheadSet){
        for(String s: this.lookaheadSet){
            if(!listToCompareWithLookaheadSet.contains(s))
                return false;
        }
        return true;
    }

    /**
     * Get-set methods
     */

    public Char getCharRightOfTheDot() {
            return charRightOfTheDot;
        }

    public String getLeftOfTheDot() {
            return leftOfTheDot;
        }

    public String getRightOfTheDot() {
            return rightOfTheDot;
        }

    public Char getLeftHandSide() {
            return leftHandSide;
        }

    public ArrayList<String> getLookaheadSet() {
            return lookaheadSet;
        }

    public int getStateOfLoop() {
            return stateOfLoop;
        }

    public void setStateOfLoop(int stateOfLoop) {
            this.stateOfLoop = stateOfLoop;
        }

    public boolean getLoop() {
            return loop;
        }

    public void setLoop(Boolean loop) {
            this.loop = loop;
        }

}

