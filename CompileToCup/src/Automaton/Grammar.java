/**
 * This class represents a grammar G=<Vt,Vn,P,S>, where:
 *
 * - S: String grammarAxiom
 * - P: ArrayList<Production> productions
 * - Vt U Vn : ArrayList<String> alphabet
 *
 * @author: Danilo Ceravolo, Enrolment Number: 0192498
 */

import java.util.ArrayList;

public class Grammar
{
    public String grammarString;                            // String that represent the grammar
    public String grammarAxiom;                             // Axiom of the grammar
    public ArrayList<String> alphabet;                      // Alphabet of the grammar
    public ArrayList<Production> productions;               // Productions of the grammar

    /**
     * Constructor for the class
     * @param inputGrammar
     */
    public Grammar(String inputGrammar)
    {
        this.grammarString = inputGrammar;
        this.alphabet = new ArrayList<>();
        this.productions = new ArrayList<>();
    }


    /**
     * Get-set methods
     */

    public String getGrammarString() {
        return grammarString;
    }

    public String getGrammarAxiom() {
        return grammarAxiom;
    }

    public void setGrammarAxiom(String grammarAxiom) {
        this.grammarAxiom = grammarAxiom;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void addProduction(Production p)
    {
        this.productions.add(p);
    }
}
