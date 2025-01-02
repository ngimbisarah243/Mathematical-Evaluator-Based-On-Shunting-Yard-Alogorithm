package org.example

import java.util.Stack

fun main() {

    // I need an expression that eqauls 42 ahhhh...but
    // ChatGPT is not able generating mathematic expressions that returns
    // the right number
    val infix = "(8+4)*(5-2)+ (10/2)*3+6-2" 
    val postfix = parseIntoPostfix(infix)
    println(postfix)
    println(postfixCalculatorAlgorithm(postfix))
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

    fun addValueToStack(character: Char) {
        when (character) {
            ')' -> {
                // Handle parentheses: Process everything in the stack up to the opening parenthesis
                while (symbolStack.isNotEmpty() && symbolStack.peek() != '(') {
                    postfix.add(symbolStack.pop().toString())
                }
            /*    while (symbolStack.isNotEmpty()) {
                    symbolStack.pop()  // Remove opening parenthesis
                }*/

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

    var numberBuffer = StringBuilder() // StringBuilder für mehrstellige Zahlen

    for (character in expression) {
        if (character.isWhitespace()) continue

        if (character.isDigit()) {
            numberBuffer.append(character)  // Ziffer zur Zahl hinzufügen
            //  postfix.add(character)
        } else {

            if (numberBuffer.isNotEmpty()) {
                // Wenn eine Zahl vollständig ist, diese zum Postfix-Ausdruck hinzufügen
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

            when (token) {
                "+" -> temp = num2 + num1
                "-" -> temp = num2 - num1
                "*" -> temp = num2 * num1
                "/" -> temp = num2 / num1
            }
            numberStack.push(temp)
            println(temp)
        }
    }
    result = numberStack.pop()  //nur ein Wert ist drinnen

    return result
}