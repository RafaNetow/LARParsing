
/********************************************************************************************
 *                            LR1 Parser                                                    *
 *                                                                                          *
 *  Overview:                                                                               *
 *                                                                                          *
 *  A LR1 Parser is an LR(k) parser for k=1, with a single lookahead terminal.              *
 *  The special attribute of this parsers is that all LR(k) parsers with k>1 can be         *
 *  transformed into a LR(1) parser. Moreover, the class of LR(1) grammars, coincides       *
 *  with the deterministic context-free grammars.                                           *
 *  Basically, the LR(1) parser is a deterministic pushdown automaton and as such its       *
 *  operation is based on static state transition tables. These codify the grammar of       *
 *  the language it recognized and are typically called "parsing table". That table are     *
 *  parametrized with a lookahead terminal                                                  *
 *                                                                                          *
 *  Notation:                                                                               *
 *  Grammar to be included in input has to follow a strict notation, for example:           *
 *  S->aB|e;B->ab                                                                           *
 *  - Char ' | ': It indicates the separation between the different productions of the      *
 *                same non terminal symbol.                                                 *
 *  - Char ' ; ': It indicates the end of production of a given non terminal symbol.        *
 *  - Char ' . ': It indicates the end of grammar                                           *
 *                                                                                          *
 *                                                                                          *
 *  Instructions:                                                                           *
 *                                                                                          *
 *  For running the algorithm from command line, first moved in the program folder, compile *
 *  it and, then, digits the following command:                                             *
 *                                                                                          *
 *  "java al2016_Ceravolo_Danilo_0192498_LR1Parser"                                         *
 *                                                                                          *
 *  The program does not requires arguments. The grammar will be inserted when the program  *
 *  is running.                                                                             *
 *                                                                                          *
 ********************************************************************************************/

import java.io.IOException;
import java.util.Scanner;


public class al2016_Ceravolo_Danilo_0192498_LR1Parser
{

    public al2016_Ceravolo_Danilo_0192498_LR1Parser()
    {}

    public static void main(String args []) throws IOException
    {
        String inputGrammar;
        String inputString;

        System.out.println("     LR1 PARSER      \n");

        Scanner input = new Scanner(System.in);

        System.out.print("Insert the grammar: ");                       // Take the grammar from the input
        inputGrammar = input.nextLine();


        System.out.print("\nInsert the string to parse: ");             // Take the string to parse from the input
        inputString = input.nextLine();

        LR1Parser p = new LR1Parser("88");
        p.execute();


        if(!p.getInputStringToAutomaton(inputString))
            System.out.println("String not accepted");
        else
            System.out.println("String accepted");


    }

}


/************************************************************************************************
 *                                    Run Tests                                                 *
 *                                                                                              *
 *                                  1. FIRST TEST                                               *
 *                                                                                              *
 * (Grammar taken from the example 5.4.11, on page 113 of 'Techniques for searching,            *
 *  parsing and matching')                                                                      *
 *                                                                                              *
 *                                                                                              *
 *                                   LR1 PARSER                                                 *
 *                                                                                              *
 *                                                                                              *
 * Insert the grammar: A->BA|e;B->aB|b.                                                         *
 *                                                                                              *
 * Insert the string to parse: aabb                                                             *
 *                                                                                              *
 * Check on grammar input and will calculate its augmented version..                            *
 *                                                                                              *
 * Augmented grammar:                                                                           *
 *                                                                                              *
 * W->A                                                                                         *
 *                                                                                              *
 * A->BA | e                                                                                    *
 *                                                                                              *
 * B->aB | b                                                                                    *
 *                                                                                              *
 * Calculation of relative parsing automaton...                                                 *
 *                                                                                              *
 * Big Productions:                                                                             *
 * ---------------------                                                                        *
 * Q0                                                                                           *
 * W -> .A , { $ }                                                                              *
 * A -> .B A, { $ }                                                                             *
 * A -> . , { $ }                                                                               *
 * B -> .a B, { a b $ }                                                                         *
 * B -> .b , { a b $ }                                                                          *
 * --                                                                                           *
 * Q1                                                                                           *
 * W -> A. , { $ }                                                                              *
 * --                                                                                           *
 * Q2                                                                                           *
 * A -> B.A , { $ }                                                                             *
 * A -> .B A, { $ }                                                                             *
 * A -> . , { $ }                                                                               *
 * B -> .a B, { a b $ }                                                                         *
 * B -> .b , { a b $ }                                                                          *
 * --                                                                                           *
 * Q3                                                                                           *
 * B -> a.B , { a b $ }                                                                         *
 * B -> .a B, { a b $ }                                                                         *
 * B -> .b , { a b $ }                                                                          *
 * --                                                                                           *
 * Q4                                                                                           *
 * B -> b. , { a b $ }                                                                          *
 * --                                                                                           *
 * Q5                                                                                           *
 * A -> BA. , { $ }                                                                             *
 * --                                                                                           *
 * Q6                                                                                           *
 * B -> aB. , { a b $ }                                                                         *
 * --                                                                                           *
 *                                                                                              *
 * Arcs:                                                                                        *
 * ------------------------                                                                     *
 * From Q0 to Q1 by label: A                                                                    *
 * From Q0 to Q2 by label: B                                                                    *
 * From Q0 to Q3 by label: a                                                                    *
 * From Q0 to Q4 by label: b                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q1                                                                     *
 * ------------------------                                                                     *
 * From Q2 to Q5 by label: A                                                                    *
 * From Q2 to Q2 by label: B                                                                    *
 * From Q2 to Q3 by label: a                                                                    *
 * From Q2 to Q4 by label: b                                                                    *
 * ------------------------                                                                     *
 * From Q3 to Q6 by label: B                                                                    *
 * From Q3 to Q3 by label: a                                                                    *
 * From Q3 to Q4 by label: b                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q4                                                                     *
 * ------------------------                                                                     *
 * There isn't arcs from Q5                                                                     *
 * ------------------------                                                                     *
 * There isn't arcs from Q6                                                                     *
 * ------------------------                                                                     *
 *                                                                                              *
 *                                                                                              *
 * LR(1) Parsing Table:                                                                         *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 *         |    a   |    b   |    $   |    A   |    B   |                                       *
 * --------|        |        |        |        |        |                                       *
 *     Q0  | sh 3   | sh 4   | red 0  | goto 1 | goto 2 |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q1  |        |        | acc    |        |        |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q2  | sh 3   | sh 4   | red 1  | goto 5 | goto 2 |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q3  | sh 3   | sh 4   |        |        | goto 6 |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q4  | red 2  | red 2  | red 2  |        |        |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q5  |        |        | red 3  |        |        |                                       *
 *         |        |        |        |        |        |                                       *
 *     Q6  | red 4  | red 4  | red 4  |        |        |                                       *
 *         |        |        |        |        |        |                                       *
 *                                                                                              *
 *                                                                                              *
 * Where 'n' is the number identifying the productions:                                         *
 *                                                                                              *
 * 0: A -> e                                                                                    *
 * 1: A -> e                                                                                    *
 * 2: B -> b                                                                                    *
 * 3: A -> BA                                                                                   *
 * 4: B -> aB                                                                                   *
 *                                                                                              *
 *                                                                                              *
 * The sequence of configurations of the parsing automaton                                      *
 * while parsing aabb$ using the LR(1) parsing table:                                           *
 *                                                                                              *
 * 0. Stack: [0]                                                                                *
 * Input: a                                                                                     *
 * Action: sh 3                                                                                 *
 *                                                                                              *
 * 1. Stack: [0, a, 3]                                                                          *
 * Input: a                                                                                     *
 * Action: sh 3                                                                                 *
 *                                                                                              *
 * 2. Stack: [0, a, 3, a, 3]                                                                    *
 * Input: b                                                                                     *
 * Action: sh 4                                                                                 *
 *                                                                                              *
 * 3. Stack: [0, a, 3, a, 3, b, 4]                                                              *
 * Input: b                                                                                     *
 * Action: red 2 goto: 6                                                                        *
 *                                                                                              *
 * 4. Stack: [0, a, 3, a, 3, B, 6]                                                              *
 * Input: b                                                                                     *
 * Action: red 4 goto: 6                                                                        *
 *                                                                                              *
 * 5. Stack: [0, a, 3, B, 6]                                                                    *
 * Input: b                                                                                     *
 * Action: red 4 goto: 2                                                                        *
 *                                                                                              *
 * 6. Stack: [0, B, 2]                                                                          *
 * Input: b                                                                                     *
 * Action: sh 4                                                                                 *
 *                                                                                              *
 * 7. Stack: [0, B, 2, b, 4]                                                                    *
 * Input: $                                                                                     *
 * Action: red 2 goto: 2                                                                        *
 *                                                                                              *
 * 8. Stack: [0, B, 2, B, 2]                                                                    *
 * Input: $                                                                                     *
 * Action: red 1 goto: 5                                                                        *
 *                                                                                              *
 * 9. Stack: [0, B, 2, B, 2, A, 5]                                                              *
 * Input: $                                                                                     *
 * Action: red 3 goto: 5                                                                        *
 *                                                                                              *
 * 10. Stack: [0, B, 2, A, 5]                                                                   *
 * Input: $                                                                                     *
 * Action: red 3 goto: 1                                                                        *
 *                                                                                              *
 * 11. Stack: [0, A, 1]                                                                         *
 * Input: $                                                                                     *
 * Action: acc                                                                                  *
 *                                                                                              *
 * String accepted                                                                              *
 ************************************************************************************************/

/************************************************************************************************
 *                                    Run Tests                                                 *
 *                                                                                              *
 *                                  2. SECOND TEST                                              *
 *                                                                                              *
 * (Grammar taken from the example 5.4.12, on page 115 of 'Techniques for searching,            *
 *  parsing and matching')                                                                      *
 *                                                                                              *
 *                                                                                              *
 *                                   LR1 PARSER                                                 *
 *                                                                                              *
 *                                                                                              *
 * Insert the grammar: S->e.                                                                    *
 *                                                                                              *
 * Insert the string to parse: e                                                                *
 *                                                                                              *
 * Check on grammar input and will calculate its augmented version..                            *
 *                                                                                              *
 * Augmented grammar:                                                                           *
 *                                                                                              *
 * W->S                                                                                         *
 *                                                                                              *
 * S->e                                                                                         *
 *                                                                                              *
 * Calculation of relative parsing automaton...                                                 *
 *                                                                                              *
 * Big Productions:                                                                             *
 * ---------------------                                                                        *
 * Q0                                                                                           *
 * W -> .S , { $ }                                                                              *
 * S -> . , { $ }                                                                               *
 * --                                                                                           *
 * Q1                                                                                           *
 * W -> S. , { $ }                                                                              *
 * --                                                                                           *
 *                                                                                              *
 * Arcs:                                                                                        *
 * ------------------------                                                                     *
 * From Q0 to Q1 by label: S                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q1                                                                     *
 * ------------------------                                                                     *
 *                                                                                              *
 * LR(1) Parsing Table:                                                                         *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 *          |    $   |    S   |                                                                 *
 *  --------|        |        |                                                                 *
 *      Q0  | red 0  | goto 1 |                                                                 *
 *          |        |        |                                                                 *
 *      Q1  | acc    |        |                                                                 *
 *          |        |        |                                                                 *
 *                                                                                              *
 *                                                                                              *
 * Where 'n' is the number identifying the productions:                                         *
 *                                                                                              *
 * 0: S -> e                                                                                    *
 *                                                                                              *
 *                                                                                              *
 * The sequence of configurations of the parsing automaton                                      *
 * while parsing aabb$ using the LR(1) parsing table:                                           *
 *                                                                                              *
 * 0. Stack: [0]                                                                                *
 * Input: $                                                                                     *
 * Action: red 0 goto: 1                                                                        *
 *                                                                                              *
 * 1. Stack: [0, S, 1]                                                                          *
 * Input: $                                                                                     *
 * Action: acc 0                                                                                *
 *                                                                                              *
 * String accepted                                                                              *
 ************************************************************************************************/


/************************************************************************************************
 *                                      Run Tests                                               *
 *                                                                                              *
 *                                    3. THIRD TEST                                             *
 *                                                                                              *
 * (Grammar taken from the example 5.4.9, on page 107 of 'Techniques for searching,             *
 *  parsing and matching')                                                                      *
 *                                                                                              *
 *                                                                                              *
 *                                      LR1 PARSER                                              *
 *                                                                                              *
 * Insert the grammar: S->CC;C->cC|d.                                                           *
 *                                                                                              *
 * Insert the string to parse: cdcd                                                             *
 *                                                                                              *
 * Check on grammar input and will calculate its augmented version..                            *
 *                                                                                              *
 *                                                                                              *
 * Augmented grammar:                                                                           *
 *                                                                                              *
 * W->S                                                                                         *
 *                                                                                              *
 * S->CC                                                                                        *
 *                                                                                              *
 * C->cC | d                                                                                    *
 *                                                                                              *
 * Calculation of relative parsing automaton...                                                 *
 *                                                                                              *
 * Big Productions:                                                                             *
 * ---------------------                                                                        *
 * Q0                                                                                           *
 * W -> .S , { $ }                                                                              *
 * S -> .C C, { $ }                                                                             *
 * C -> .c C, { c d }                                                                           *
 * C -> .d , { c d }                                                                            *
 * --                                                                                           *
 * Q1                                                                                           *
 * W -> S. , { $ }                                                                              *
 * --                                                                                           *
 * Q2                                                                                           *
 * S -> C.C , { $ }                                                                             *
 * C -> .c C, { $ }                                                                             *
 * C -> .d , { $ }                                                                              *
 * --                                                                                           *
 * Q3                                                                                           *
 * C -> c.C , { c d }                                                                           *
 * C -> .c C, { c d }                                                                           *
 * C -> .d , { c d }                                                                            *
 * --                                                                                           *
 * Q4                                                                                           *
 * C -> d. , { c d }                                                                            *
 * --                                                                                           *
 * Q5                                                                                           *
 * S -> CC. , { $ }                                                                             *
 * --                                                                                           *
 * Q6                                                                                           *
 * C -> c.C , { $ }                                                                             *
 * C -> .c C, { $ }                                                                             *
 * C -> .d , { $ }                                                                              *
 * --                                                                                           *
 * Q7                                                                                           *
 * C -> d. , { $ }                                                                              *
 * --                                                                                           *
 * Q8                                                                                           *
 * C -> cC. , { c d }                                                                           *
 * --                                                                                           *
 * Q9                                                                                           *
 * C -> cC. , { $ }                                                                             *
 * --                                                                                           *
 *                                                                                              *
 * Arcs:                                                                                        *
 * ------------------------                                                                     *
 * From Q0 to Q1 by label: S                                                                    *
 * From Q0 to Q2 by label: C                                                                    *
 * From Q0 to Q3 by label: c                                                                    *
 * From Q0 to Q4 by label: d                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q1                                                                     *
 * ------------------------                                                                     *
 * From Q2 to Q5 by label: C                                                                    *
 * From Q2 to Q6 by label: c                                                                    *
 * From Q2 to Q7 by label: d                                                                    *
 * ------------------------                                                                     *
 * From Q3 to Q8 by label: C                                                                    *
 * From Q3 to Q3 by label: c                                                                    *
 * From Q3 to Q4 by label: d                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q4                                                                     *
 * ------------------------                                                                     *
 * There isn't arcs from Q5                                                                     *
 * ------------------------                                                                     *
 * From Q6 to Q9 by label: C                                                                    *
 * From Q6 to Q6 by label: c                                                                    *
 * From Q6 to Q7 by label: d                                                                    *
 * ------------------------                                                                     *
 * There isn't arcs from Q7                                                                     *
 * ------------------------                                                                     *
 * There isn't arcs from Q8                                                                     *
 * ------------------------                                                                     *
 * There isn't arcs from Q9                                                                     *
 * ------------------------                                                                     *
 *                                                                                              *
 *                                                                                              *
 * LR(1) Parsing Table:                                                                         *
 *                                                                                              *
 *                                                                                              *
 *         |    c   |    d   |    $   |    S   |    C   |                                       *
 * --------|                                                                                    *
 *     Q0  | sh 3   | sh 4   |        | goto 1 | goto 2 |                                       *
 *         |                                                                                    *
 *     Q1  |        |        | acc    |        |        |                                       *
 *         |                                                                                    *
 *     Q2  | sh 6   | sh 7   |        |        | goto 5 |                                       *
 *         |                                                                                    *
 *     Q3  | sh 3   | sh 4   |        |        | goto 8 |                                       *
 *         |                                                                                    *
 *     Q4  | red 0  | red 0  |        |        |        |                                       *
 *         |                                                                                    *
 *     Q5  |        |        | red 1  |        |        |                                       *
 *         |                                                                                    *
 *     Q6  | sh 6   | sh 7   |        |        | goto 9 |                                       *
 *         |                                                                                    *
 *     Q7  |        |        | red 2  |        |        |                                       *
 *         |                                                                                    *
 *     Q8  | red 3  | red 3  |        |        |        |                                       *
 *         |                                                                                    *
 *     Q9  |        |        | red 4  |        |        |                                       *
 *         |                                                                                    *
 *                                                                                              *
 *                                                                                              *
 * Where 'n' is the number identifying the productions:                                         *
 *                                                                                              *
 * 0: C -> d                                                                                    *
 * 1: S -> CC                                                                                   *
 * 2: C -> d                                                                                    *
 * 3: C -> cC                                                                                   *
 * 4: C -> cC                                                                                   *
 *                                                                                              *
 * The sequence of configurations of the parsing automaton                                      *
 * while parsing cdcd$ using the LR(1) parsing table:                                           *
 *                                                                                              *
 *                                                                                              *
 * 0. Stack: [0]                                                                                *
 * Input: c                                                                                     *
 * Action: sh 3                                                                                 *
 *                                                                                              *
 *                                                                                              *
 * 1. Stack: [0, c, 3]                                                                          *
 * Input: d                                                                                     *
 * Action: sh 4                                                                                 *
 *                                                                                              *
 *                                                                                              *
 * 2. Stack: [0, c, 3, d, 4]                                                                    *
 * Input: c                                                                                     *
 * Action: red 0 goto: 8                                                                        *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 * 3. Stack: [0, c, 3, C, 8]                                                                    *
 * Input: c                                                                                     *
 * Action: red 3 goto: 2                                                                        *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 * 4. Stack: [0, C, 2]                                                                          *
 * Input: c                                                                                     *
 * Action: sh 6                                                                                 *
 *                                                                                              *
 *                                                                                              *
 * 5. Stack: [0, C, 2, c, 6]                                                                    *
 * Input: d                                                                                     *
 * Action: sh 7                                                                                 *
 *                                                                                              *
 *                                                                                              *
 * 6. Stack: [0, C, 2, c, 6, d, 7]                                                              *
 * Input: $                                                                                     *
 * Action: red 2 goto: 9                                                                        *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 * 7. Stack: [0, C, 2, c, 6, C, 9]                                                              *
 * Input: $                                                                                     *
 * Action: red 4 goto: 5                                                                        *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 * 8. Stack: [0, C, 2, C, 5]                                                                    *
 * Input: $                                                                                     *
 * Action: red 1 goto: 1                                                                        *
 *                                                                                              *
 *                                                                                              *
 *                                                                                              *
 * 9. Stack: [0, S, 1]                                                                          *
 * Input: $                                                                                     *
 * Action: acc 0                                                                                *
 *                                                                                              *
 *                                                                                              *
 * String accepted                                                                              *
 ***********************************************************************************************/