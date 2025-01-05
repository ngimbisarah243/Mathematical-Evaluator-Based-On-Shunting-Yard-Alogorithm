package org.example

import java.util.Stack

fun main() {

   // The answer of everything hihi
    val infix = "(((((((7+5)*3)-(6*2))/8)-19)*2-2)/4)+50+1/2"

    // Convert the infix expression to postfix notation
    val postfix = parseIntoPostfix(infix)
    println("Postfix notation: $postfix")

    // Evaluation of the postfix expression
    val result = postfixCalculatorAlgorithm(postfix)
    println("Result = $result")
}

fun parseIntoPostfix(expression: String): ArrayDeque<String> {
    var symbolStack: Stack<Char> = Stack()
    var postfix: ArrayDeque<String> = ArrayDeque()
    val opPriorities: Map<Char, Int> = mapOf(
        '+' to 1,   // low priority
        '-' to 1,   // low priority
        '*' to 2,   // high priority
        '/' to 2    // high priority
    )

    // Helper function to handle operators and parentheses
    fun addValueToStack(character: Char) {
        when (character) {
            ')' -> {
                // Handle parentheses: Process everything in the stack up to the opening parenthesis
                while (symbolStack.isNotEmpty() && symbolStack.peek() != '(') {
                    postfix.add(symbolStack.pop().toString())
                }

                // Remove opening parenthesis
                while(symbolStack.isNotEmpty()&&symbolStack.peek()=='('){

                    symbolStack.pop()
                }

            }

            '(' -> {
                // Opening parenthesis is simply pushed onto the stack
                symbolStack.push(character)
            }

            else -> {
                // Process operators
                while (symbolStack.isNotEmpty() && symbolStack.peek()!='('&& opPriorities[symbolStack.peek()] ?: -1 >= opPriorities[character] ?: -1) {
                    val topOperator = symbolStack.pop()
                    postfix.add(topOperator.toString())  // Pop the first operator
                }
                symbolStack.push(character)  // push operator onto the stack
            }
        }
    }

    var numberBuffer = StringBuilder() // Buffer to handle multi-digit numbers

    for (character in expression) {
        if (character.isWhitespace()) continue

        if (character.isDigit()) {
            numberBuffer.append(character)  // Append digit to the current number buffer
            //  postfix.add(character)
        } else {

            if (numberBuffer.isNotEmpty()) {
                //  If a complete number is found, add it to the postfix expression
                postfix.add(numberBuffer.toString())
                numberBuffer = StringBuilder() // zurücksetzen
            }
            addValueToStack(character)
        }
    }
    if (numberBuffer.isNotEmpty()) {
        postfix.add(numberBuffer.toString())
    }

    while (symbolStack.isNotEmpty()) {
        postfix.add(symbolStack.pop().toString())
    }

    return postfix
}

fun postfixCalculatorAlgorithm(tokens: ArrayDeque<String>): Int {
    var numberStack: Stack<Int> = Stack()
    var result = 0

    for (token in tokens) {
        var temp = 0
        if (token.toDoubleOrNull() != null) {
            numberStack.push(token.toInt())
        } else {
            if (numberStack.size < 2) {
                throw IllegalStateException("Nicht genügend Operanden für den Operator $token")
            }

            val num1 = numberStack.pop()
            val num2 = numberStack.pop()

          result=  when (token) {
                "+" -> num2 + num1
                "-" -> num2 - num1
                "*" ->  num2 * num1
                "/" ->  num2 / num1
              else -> throw IllegalArgumentException("Unknown operator: $token")
            }
            numberStack.push(result)
        }
    }
   // The final result will be the only number in the stack..
    return numberStack.pop()
}