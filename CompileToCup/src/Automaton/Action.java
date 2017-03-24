/**
 * This class represents an operation of the parsing automaton. When adding a new one, choose between:
 * sh, red, acc, goto which stand for shift ,reduce, accept and goto.
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

public class Action
{
    public String action;                               // String that identifies the relative action
    public int numberOfAction;

    /**
     * Constructor for the class
     *
     * @param ac
     * @param n
     */
    public Action(String ac, int n){
        this.action = ac;
        this.numberOfAction = n;
    }

    /**
     *  Get-Set methods
     */
    public int getNumberOfAction(){
        return this.numberOfAction;
    }

    public boolean isShiftAction() {
        return action.equals("sh");
    }

    public boolean isReduceAction() {
        return action.equals("red");
    }

    public boolean isAcceptAction() {
        return action.equals("acc");
    }

    public boolean isGotoAction() {
        return action.equals("goto");
    }
}