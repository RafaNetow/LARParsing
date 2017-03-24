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
public class Transition {
      State Origin;
      State Destination;
      Character Symbol;
      String Symbols;
      
      public Transition(State Origin,State destination, Character Symbol){

        this.Origin = Origin;
        this.Destination = destination;
        this.Symbol = Symbol;


    }
    public Transition(State Origin,State destination,String Symbols){

        this.Origin = Origin;
        this.Destination = destination;
        this.Symbols = Symbols;
    }
}
