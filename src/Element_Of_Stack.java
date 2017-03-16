/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sequeirios
 */
public class Element_Of_Stack {
       public  String Symbol;
        public int State ;

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

        public Element_Of_Stack()
        {

        }

        public Element_Of_Stack(String symbol, int state)
        {
            Symbol = symbol;
            State = state;
        }
}
