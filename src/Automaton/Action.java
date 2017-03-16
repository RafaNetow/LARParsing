/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automaton;

/**
 *
 * @author Sequeirios
 */
public class Action implements java.io.Serializable {
   private String terminal;
    public final String getTerminal()
    {
        return terminal;
    }
    public final void setTerminal(String value)
    {
        terminal = value;
    }
    private String action;
    public final String getAction()
    {
        return action;
    }
    public final void setAction(String value)
    {
        action = value;
    }
    private int toState;
    public final int getToState()
    {
        return toState;
    }
    public final void setToState(int value)
    {
        toState = value;
    }

    public Action() { }

    public Action(String terminal)
    {
        this.setTerminal(terminal);
        setAction("");
        setToState(-1);
    }  
}
