/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author Sequeirios
 */
public class Symbol {
    private int Row;

    public int getRow() {
        return Row;
    }

    public void setRow(int Row) {
        this.Row = Row;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int Column) {
        this.Column = Column;
    }

    public char getCSymbol() {
        return CSymbol;
    }

    public void setCSymbol(char CSymbol) {
        this.CSymbol = CSymbol;
    }
    private int Column;
    private char CSymbol;
}
