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
public class Token {
    public String Lexeme;

    public String getLexeme() {
        return Lexeme;
    }

    public void setLexeme(String Lexeme) {
        this.Lexeme = Lexeme;
    }

    public TokenTypes getType() {
        return Type;
    }

    public void setType(TokenTypes Type) {
        this.Type = Type;
    }

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
    public TokenTypes Type;
    public int Row;
    public int Column;
}
