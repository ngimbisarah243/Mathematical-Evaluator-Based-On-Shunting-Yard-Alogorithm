# Shunting Yard Algorithm Calculator

This project implements the **Shunting Yard algorithm**, a method for parsing and evaluating mathematical expressions written in infix notation. The algorithm is used to convert infix expressions into postfix notation (Reverse Polish Notation, RPN), which can then be easily evaluated.

## Features

- **Infix to Postfix Conversion**: Converts standard mathematical expressions into postfix notation.
- **Expression Evaluation**: Evaluates expressions written in postfix notation.
- **Operator Precedence Handling**: Correctly handles operator precedence and parentheses.

## How It Works

The Shunting Yard algorithm processes mathematical expressions by using a stack to handle operators and operands. Parentheses are treated as special cases, and the algorithm ensures the correct order of operations is maintained.

- **Step 1**: Parse the expression from left to right.
- **Step 2**: Use a stack to store operators and apply precedence rules.
- **Step 3**: Convert the expression to postfix notation and evaluate it.

