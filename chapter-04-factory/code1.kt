//
// factory pattern
//

interface Pizza {
    fun prepare()
    fun bake()
    fun cut()
    fun box()
}

class PizzaFactory {

    fun createPizza(type: String) {
        if ()
    }

}

class PizzaStore(
    val pizzaFactory: PizzaFactory
) {

    fun orderPizza(type: String): Pizza {
        
    }

}