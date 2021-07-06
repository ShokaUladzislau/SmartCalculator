package calculator
import java.math.BigInteger
import java.util.Stack
import java.util.*


fun main() {
    val variableMap = mutableMapOf<String, BigInteger>()
    loop@ while (true) {
        var str = readLine()!!

        if (str.isEmpty()) {
            continue
        } else if (str == "/exit") {
            println("Bye!")
            break
        } else if (str == "/help") {
            println("The program solve the given expression")
            continue
        } else if (str.first() == '/') {
            println("Unknown command")
            continue
        } else if (str.contains('=') && str.substringAfter('=').contains('=')) {
            println("Invalid assignment")
            continue
        } else if (str.contains('=')) {
            for (i in str.substringBefore('=').trim()) {
                if (i.toLowerCase() !in 'a'..'z'  ) {
                    println("Invalid identifier")
                    continue@loop
                }
            }
        } else if (str.contains("**") || str.contains("//")) {
            println("Invalid expression")
            continue
        } else {
            var count = 0
            for(i in str) {
                if (i == '(') count++
                if (i == ')') count--
            }
            if (count != 0) {
                println("Invalid expression")
                continue
            }
        }
        here@ while(true) {
            when {
                str.contains("++++++") -> str =
                    str.substringBefore("++++++") + " + " + str.substringAfter("++++++").trim()
                str.contains("+++++") -> str =
                    str.substringBefore("+++++") + " + " + str.substringAfter("+++++").trim()
                str.contains("++++") -> str =
                    str.substringBefore("++++") + " + " + str.substringAfter("++++").trim()
                str.contains("+++") -> str =
                    str.substringBefore("+++") + " + " + str.substringAfter("+++").trim()
                str.contains("++") -> str =
                    str.substringBefore("++") + " + " + str.substringAfter("++").trim()
                str.contains("------") -> str =
                    str.substringBefore("------") + " + " + str.substringAfter("------").trim()
                str.contains("-----") -> str =
                    str.substringBefore("-----") + " - " + str.substringAfter("-----").trim()
                str.contains("----") -> str =
                    str.substringBefore("----") + " + " + str.substringAfter("----").trim()
                str.contains("---") -> str =
                    str.substringBefore("---") + " - " + str.substringAfter("---").trim()
                str.contains("--") -> str =
                    str.substringBefore("--") + " + " + str.substringAfter("--").trim()
                else -> break@here
            }
        }

        if (str.contains('=')) {
            if (variableMap.containsKey(str.substringAfter('=').trim())) {
                variableMap[str.substringBefore('=').trim()] = variableMap.getValue(str.substringAfter('=').trim())
                continue
            } else {
                try {
                    variableMap[str.substringBefore('=').trim()] = str.substringAfter('=').trim().toBigInteger()
                    continue
                } catch (e: NumberFormatException) {
                    for (i in str.substringAfter('=').trim()) {
                        if (i.toLowerCase() !in 'a'..'z'  ) {
                            println("Invalid identifier")
                            continue@loop
                        }
                    }
                    println("Unknown variable")
                    continue
                }
            }
        }
        if ('=' !in str) {
            var postfixStr = ""
            val stack = Stack<Char>()
            for (i in str.trim()) {
                when {
                    precedence(i) > 0 -> {
                        while(!stack.isEmpty() && precedence(stack.peek()) >= precedence(i)) {
                            postfixStr += " ${stack.pop()}"
                        }
                        stack.push(i)
                        postfixStr +=" "
                    }
                    i == ')' -> {
                        var x = stack.pop()
                        while(x!='('){
                            postfixStr += " $x"
                            x = stack.pop()
                        }
                    }
                    i=='(' -> {
                        stack.push(i)
                        postfixStr +=" "
                    }
                    else -> postfixStr += i

                }
            }
            for (i in 0 until stack.size) {
                postfixStr += " ${stack.pop()}"
            }

            //println(postfixStr)
            val numberList: List<String> = postfixStr.split(" ")
            //println("length of postfix string = ${numberList.size}")
            val stack1 = Stack<BigInteger>()
            for (i in numberList) {
                if (i.isNotEmpty()) when (i.trim()) {
                    "+" -> {
                        stack1.push(stack1.pop() + stack1.pop())
                    }
                    "-" ->{
                        val temp = stack1.pop()
                        stack1.push(stack1.pop() - temp)
                    }
                    "*" ->{
                        stack1.push(stack1.pop() * stack1.pop())
                    }
                    "/" ->{
                        val temp = stack1.pop()
                        stack1.push(stack1.pop() / temp)
                    }
                    "^" -> {
                        val pow = stack1.pop()
                        val base = stack1.pop()
                        var temp = base / base
                        repeat(pow.toInt()) {
                            temp *= base
                        }
                        stack1.push(temp)
                    }
                    else -> {
                        try {
                            stack1.push(i.toBigInteger())
                        } catch (e: NumberFormatException) {
                            if (variableMap.containsKey(i)){
                                stack1.push(variableMap[i]!!)
                            } else{
                                println("Unknown variable")
                                continue@loop
                            }
                        }

                    }
                }
            }
            println(stack1.pop())
        }
    }
}

fun precedence(c: Char) :Int{
    return when (c){
        '+', '-' -> 1
        '*', '/' -> 2
        '^' -> 3
        else -> -1
    }
}