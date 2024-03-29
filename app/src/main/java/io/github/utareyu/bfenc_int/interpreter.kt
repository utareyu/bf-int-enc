package io.github.utareyu.bfenc_int

// https://www.m3tech.blog/entry/kmm-brainfuck-app
class Interpreter {
    companion object {
        fun execute(bfInput: String): Output {
            val state = State()
            var outputString = ""
            var index = 0

            try {
                while (index < bfInput.length) {
                    when (bfInput[index]) {
                        '>' -> state.incrementPointer()
                        '<' -> state.decrementPointer()
                        '+' -> state.incrementValue()
                        '-' -> state.decrementValue()
                        '.' -> outputString += state.value.toChar()
                        ',' -> throw Exception("\",\" is not implemented")
                        '[' -> {
                            if (state.value == 0.toByte()) {
                                var leftBracketCount = 1
                                while (index < bfInput.length - 1 && leftBracketCount > 0) {
                                    index++
                                    when {
                                        bfInput[index] == '[' -> leftBracketCount++
                                        bfInput[index] == ']' -> leftBracketCount--
                                    }
                                }
                            }
                        }
                        ']' -> {
                            if (state.value != 0.toByte()) {
                                var rightBracketCount = 1
                                while (index > 0 && rightBracketCount > 0) {
                                    index--
                                    when {
                                        bfInput[index] == '[' -> rightBracketCount--
                                        bfInput[index] == ']' -> rightBracketCount++
                                    }
                                }
                            }
                        }
                    }
                    index++
                }
            } catch (e: Throwable) {
                return Output.Error(e)
            }

            return Output.Success(outputString)
        }
    }
}

class State {
    private val memory: MutableList<Byte> = mutableListOf(0)

    private var pointer = 0

    var value: Byte
        get() = memory[pointer]
        set(value) {
            memory[pointer] = value
        }

    fun incrementPointer() {
        pointer++
        if (pointer >= memory.count()) {
            memory.add(0)
        }
    }

    fun decrementPointer() {
        pointer--
        if (pointer < 0) {
            throw Exception("Pointer out of range")
        }
    }

    fun incrementValue() {
        memory[pointer]++
    }

    fun decrementValue() {
        memory[pointer]--
    }
}

sealed class Output {
    data class Success(val outputString: String) : Output()
    data class Error(val cause: Throwable) : Output()
}