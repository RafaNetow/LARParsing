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
public class Transition implements java.io.Serializable {
     private State link;
    private String linkState;
    private String value;

    public State getLink() {
        return link;
    }

    public void setLink(State link) {
        this.link = link;
    }

    public String getLinkState() {
        return linkState;
    }

    public void setLinkState(String linkState) {
        this.linkState = linkState;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Transition() {  }

    public Transition(State link, String value)
    {
        setLink(link);
        setLinkState(String.valueOf(link.getNumberOfState()));
        setValue(value);
    }

    public void changeLinkState(State newLink){
        setLink(newLink);
        setLinkState(String.valueOf(newLink.getNumberOfState()));
    }

    public String toString(){
        return "With symbol: "+ getValue() +" - " +"goes to: "+getLinkState();
    }


}
