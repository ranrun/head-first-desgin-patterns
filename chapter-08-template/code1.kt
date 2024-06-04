/**
 * Template Pattern
 *
 * The Template Method Pattern defines the skeleton of an algoritm in a method,
 * deferring some steps to subclasses. Template Method lets subclasses redefine
 * certain steps of an algorithm without changing the algorithm's structure.
 *
 * The Hollywood Principle
 *
 * - Don't call us, we'll call you.
 *
 */

import java.io.*

abstract class CaffeineBeverage {
    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCup()
        addCondiments()
    }

    abstract fun brew()
    abstract fun addCondiments()
    fun boilWater() {
        println("Boiling water")
    }

    fun pourInCup() {
        println("Pouring into cup")
    }
}

abstract class CaffeineBeverageWithHook {
    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCup()
        if (customerWantsCondiments()) {
            addCondiments()
        }
    }

    abstract fun brew()
    abstract fun addCondiments()
    fun boilWater() {
        println("Boiling water")
    }

    fun pourInCup() {
        println("Pouring into cup")
    }

    open fun customerWantsCondiments(): Boolean {
        return true
    }
}

class Coffee : CaffeineBeverage() {
    override fun brew() {
        println("Dripping Coffee through filter")
    }

    override fun addCondiments() {
        println("Adding Sugar and Milk")
    }
}

class CoffeeWithHook : CaffeineBeverageWithHook() {
    override fun brew() {
        println("Dripping Coffee through filter")
    }

    override fun addCondiments() {
        println("Adding Sugar and Milk")
    }

    override fun customerWantsCondiments(): Boolean {
        val answer = userInput
        return if (answer.lowercase().startsWith("y")) {
            true
        } else {
            false
        }
    }

    private val userInput: String
        get() {
            var answer: String? = null
            print("Would you like milk and sugar with your coffee (y/n)? ")
            val `in` = BufferedReader(InputStreamReader(System.`in`))
            try {
                answer = `in`.readLine()
            } catch (ioe: IOException) {
                System.err.println("IO error trying to read your answer")
            }
            return answer ?: "no"
        }
}

class Tea : CaffeineBeverage() {
    override fun brew() {
        println("Steeping the tea")
    }

    override fun addCondiments() {
        println("Adding Lemon")
    }
}

class TeaWithHook : CaffeineBeverageWithHook() {
    override fun brew() {
        println("Steeping the tea")
    }

    override fun addCondiments() {
        println("Adding Lemon")
    }

    override fun customerWantsCondiments(): Boolean {
        val answer = userInput
        return if (answer.lowercase().startsWith("y")) {
            true
        } else {
            false
        }
    }

    private val userInput: String
        get() {
            // get the user's response
            var answer: String? = null
            print("Would you like lemon with your tea (y/n)? ")
            val `in` = BufferedReader(InputStreamReader(System.`in`))
            try {
                answer = `in`.readLine()
            } catch (ioe: IOException) {
                System.err.println("IO error trying to read your answer")
            }
            return answer ?: "no"
        }
}

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ java -jar code1.jar BeverageTestDrive
object BeverageTestDrive {
    @JvmStatic
    fun main(args: Array<String>) {
        val tea = Tea()
        val coffee = Coffee()
        println("\nMaking tea...")
        tea.prepareRecipe()
        println("\nMaking coffee...")
        coffee.prepareRecipe()
        val teaHook = TeaWithHook()
        val coffeeHook = CoffeeWithHook()
        println("\nMaking tea...")
        teaHook.prepareRecipe()
        println("\nMaking coffee...")
        coffeeHook.prepareRecipe()
    }
}
