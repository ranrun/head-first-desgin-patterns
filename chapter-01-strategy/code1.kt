// kotlinc code1.kt -include-runtime -d code1.jar
// java -jar code1.jar

interface Duck {
    fun fly()
    fun quack()
}

class Mallard() : Duck {
    override fun fly() {
        println("Mallard fly")
    }
    override fun quack() {
        println("Mallard quack")
    }
}

class WildTurkey() : Duck {
    override fun fly() {
        println("Wild turkey short flight")
    }
    override fun quack() {
        println("Gobble, gobble")
    }
}

fun main() {
    val mallard = Mallard()
    mallard.fly()
    mallard.quack()

    val wildTurkey = WildTurkey()
    wildTurkey.fly()
    wildTurkey.quack()

    var obj: WildTurkey? = WildTurkey()
    obj = null
    if (obj == null && obj === null) {
        println("is null")
    } else {
        println("is not null")
    }


}