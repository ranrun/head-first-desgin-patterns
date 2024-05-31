import java.util.concurrent.locks.*

// TODO - try using enum
class ChocolateBoiler private constructor() {
    companion object {
        private var instance : ChocolateBoiler? = null
        @Volatile
        private val lock = ReentrantLock()
        fun getInstance(): ChocolateBoiler {
            if (instance == null) {
                synchronized(lock) {
                    instance = ChocolateBoiler()
                }
            }
            return instance!!
        }
    }

    // private constructor(): ChocolateBoiler {
    //     companion object {
    //         // private var instance: ChocolateBoiler? = null
    //         fun getInstance(): ChocolateBoiler {
    //             if (instance == null) {
    //                 instance = ChocolateBoiler()
    //             }
    //             return instance
    //         }
    //     }
    // }

    var empty: Boolean = true
    var boiled: Boolean = false

    fun fill() {
        println("--- fill ---")
        if (isEmpty()) {
            empty = false
            boiled = false
            // fill the boiler with a milk/choclate mixture
        }
    }

    fun drain() {
        println("--- drain ---")
        if (!isEmpty() && isBoiled()) {
            // drain the boiled milk and chocolate
            empty = true
        }
    }

    fun boil() {
        println("--- boil ---")
        if (!isEmpty() && !isBoiled()) {
            // bring the contents to a boil
            boiled = true
        }
    }

    fun isEmpty(): Boolean {
        return empty
    }

    fun isBoiled(): Boolean {
        return boiled
    }

    override fun toString(): String {
        return "chocolateBoiler: empty: $empty, boiled: $boiled"
    }
}

fun main() {
    val chocolateBoiler = ChocolateBoiler.getInstance()
    println(System.identityHashCode(chocolateBoiler))
    println("--- chocolateBoiler ---")
    println(chocolateBoiler)
    chocolateBoiler.fill()
    println(chocolateBoiler)
    chocolateBoiler.boil()
    println(chocolateBoiler)

    println("--- chocolateBoiler2 ---")
    val chocolateBoiler2 = ChocolateBoiler.getInstance()
    println(System.identityHashCode(chocolateBoiler2))
    println(chocolateBoiler2)
    chocolateBoiler2.fill()
    println(chocolateBoiler2)
}