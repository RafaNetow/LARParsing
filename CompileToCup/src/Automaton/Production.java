/**
 * This class represent a generic production of a grammar
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */


import java.util.ArrayList;
import java.util.HashMap;

public class Production
{
    private Char leftHandSide;                                          // Left Hand Side
    private ArrayList<String> rightHandSide;                            // Right Hand Side
    private boolean isNullable;                                         // Boolean value, which indicates if a production is nullable
    public ArrayList<Pair<String, Integer>> usedProduction;             // Productions used by this production
    public ArrayList<Integer> nullableProduction;                       // List of the relative nullable production associated
    public ArrayList<String> setOfFirstElement;                         // First set for all elements


    /**
     * Constructor for the class
     * @param leftHandSide
     * @param g
     * @param map
     */

    public Production(String leftHandSide, Grammar g, HashMap<String, Production> map)
    {
        this.rightHandSide = new ArrayList<>();
        this.usedProduction = new ArrayList<>();
        this.isNullable = false;
        this.leftHandSide = new Char(leftHandSide);
        this.setOfFirstElement = new ArrayList<>();
        this.nullableProduction = new ArrayList<>();

        g.addProduction(this);
        map.put(leftHandSide, this);
    }

    /**
     * Constructor for the class
     * @param leftHandSide
     * @param rightHandSide
     * @param isNullable
     */

    public Production(String leftHandSide, ArrayList<String> rightHandSide, boolean isNullable)
    {
        this.rightHandSide = rightHandSide;
        this.usedProduction = new ArrayList<>();
        this.isNullable = isNullable;
        this.leftHandSide = new Char(leftHandSide);
        this.setOfFirstElement = new ArrayList<>();
        this.nullableProduction = new ArrayList<>();
    }

    public Char getLeftHandSide() {
        return leftHandSide;
    }

    public void addProduction(String stringToAdd)
    {
        if(stringToAdd.equals(leftHandSide.character))
            return;

        if(stringToAdd.equals("e"))
            isNullable = true;

        rightHandSide.add(stringToAdd);
    }

    public ArrayList<String> getRightHandSide() {
        return rightHandSide;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        this.isNullable = nullable;
    }

    public void addUsedProduction(Pair<String, Integer> usage) {
        this.usedProduction.add(usage);
    }
}
