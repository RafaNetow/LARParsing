
 

import java.util.ArrayList;

public class Item
{
    public Char leftHandSide;                     
    public String leftOfTheDot;                     
    public Char charRightOfTheDot;                 
    public String rightOfTheDot;                    
    public ArrayList<String> lookaheadSet;          


    public boolean end;                             
    public int stateOfLoop;
    public boolean loop;                           


 
    public Item(Char lhs, String leftOfTheDot, Char r, String rightOfTheDot, ArrayList<String> lookaheadSet){
        this.leftHandSide = lhs;
        this.leftOfTheDot = leftOfTheDot;
        this.charRightOfTheDot = r;
        this.rightOfTheDot = rightOfTheDot;
        this.lookaheadSet = lookaheadSet;
        this.end = false;
        this.loop = false;
    }

 
    public boolean comparisonWithLookaheadSet(ArrayList<String> listToCompareWithLookaheadSet){
        for(String s: this.lookaheadSet){
            if(!listToCompareWithLookaheadSet.contains(s))
                return false;
        }
        return true;
    }

    

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

