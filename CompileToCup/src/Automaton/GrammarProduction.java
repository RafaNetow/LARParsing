package Automaton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import TREE.Production;
import java.util.ArrayList;
import java.util.HashMap;

public class GrammarProduction {
    public Char Factor;

    public Char getFactor() {
        return Factor;
    }

    public ArrayList<String> getProductiosnOfFactor() {
        return ProductiosnOfFactor;
    }

    public ArrayList<Pair<String, Integer>> getUsedProduction() {
        return usedProduction;
    }

    public ArrayList<String> getSetOfFirstElemnt() {
        return setOfFirstElemnt;
    }

    public boolean isIsNull() {
        return isNull;
    }

    public ArrayList<Integer> getNullableProduction() {
        return nullableProduction;
    }

    public ArrayList<String> getSetOfFirstElement() {
        return setOfFirstElement;
    }
    private ArrayList<String>  ProductiosnOfFactor;
  public ArrayList<Pair<String, Integer>> usedProduction;  
    public ArrayList<String> setOfFirstElemnt;
    private boolean isNull;
    public ArrayList<Integer> nullableProduction;                       // List of the relative nullable production associated
    public ArrayList<String> setOfFirstElement; 
      public String grammarAxiom; 

 public GrammarProduction(String Factor, Grammar g, HashMap<String, GrammarProduction> map)
    {
        this.ProductiosnOfFactor = new ArrayList<>();
        this.setOfFirstElemnt = new ArrayList<>();
        this.isNull = false;
        this.Factor = new Char(Factor);
        this.setOfFirstElement = new ArrayList<>();
        this.nullableProduction = new ArrayList<>();
        this.usedProduction = new ArrayList<>();
        g.addProduction(this);
        map.put(Factor, this);
 
    }
     
 
  public GrammarProduction(String leftHandSide, ArrayList<String> rightHandSide, boolean isNullable)
    {
        this.ProductiosnOfFactor = rightHandSide;     
        this.isNull = isNullable;
        this.Factor = new Char(leftHandSide);
        this.setOfFirstElement = new ArrayList<>();
        this.nullableProduction = new ArrayList<>();
        this.usedProduction = new ArrayList<>();
    }
     public Char getLeftHandSide() {
        return Factor;
    }

    public void addProduction(String stringToAdd)
    {
        if(stringToAdd.equals(Factor.character))
            return;

        if(stringToAdd.equals("e"))
            isNull = true;

        ProductiosnOfFactor.add(stringToAdd);
    }

    public ArrayList<String> getRightHandSide() {
        return ProductiosnOfFactor;
    }

    public boolean isNullable() {
        return isNull;
    }

    public void setNullable(boolean nullable) {
        this.isNull = nullable;
    }
 

    public String getGrammarAxiom() {
        return grammarAxiom;
    }

    public void setGrammarAxiom(String grammarAxiom) {
        this.grammarAxiom = grammarAxiom;
    }
    public void addUsedProduction(Pair<String, Integer> usage) {
       
        this.usedProduction.add(usage);
    }
}
