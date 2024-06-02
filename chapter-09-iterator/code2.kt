/**
 * Composite Pattern
 *
 * The Composite Pattern allows you to
 * compose objects into a tree structures to
 * represent part-whole hierarchies. Composite
 * lets clients treat individual objects and
 * compositions of objects uniformly.
 */

abstract class MenuComponent {
    
    abstract val name: String
    abstract val description: String
    
    // composite methods
    open fun add(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }
    open fun remove(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }
    open fun getChild(i: Int): MenuComponent {
        throw UnsupportedOperationException()
    }

    // operation methods
    fun getName() {
        throw UnsupportedOperationException()
    }
    fun getDescription() {
        throw UnsupportedOperationException()
    }

    fun getPrice() {
        throw UnsupportedOperationException()
    }

    fun isVegetarian(): Boolean {
        throw UnsupportedOperationException()
    }

    // used by both composite and operations
    open fun print() {
        throw UnsupportedOperationException()
    }
}

class MenuItem(
    override val name: String,
    override val description: String,
    val vegetarian: Boolean,
    val price: Double
) : MenuComponent() {

    override fun print() {
        print(" " + name)
        if (vegetarian) {
            print("(v)")
        }
        print(", " + price)
        println(" -- " + description)
    }
}

class Menu(
    override val name: String,
    override val description: String
): MenuComponent() {
    var menuComponents: ArrayList<MenuComponent>
    init {
        menuComponents = arrayListOf()
    }
    override fun add(menuComponent: MenuComponent) {
        menuComponents.add(menuComponent)
    }

    override fun remove(menuComponent: MenuComponent) {
        menuComponents.remove(menuComponent)
    }

    override fun getChild(i: Int): MenuComponent {
        return menuComponents.get(i)
    }

    override fun print() {
        print("\n" + name)
        println(", " + description)
        println("----------------")

        for (menuComponent in menuComponents) {
            menuComponent.print()
        }
    }
}

class MenuTest() {
    // $ kotlinc code2.kt -include-runtime -d code2.jar
    // $ java -jar code2.jar MenuTest
    // $ java -cp code2.jar MenuTest
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val menu1 = Menu("Dinner", "What you eat a night.")
            val menuItem1_1 = MenuItem("Burger", "Burger made with all beef", false, 5.99)
            val menuItem1_2 = MenuItem("Steak", "Steak. Great cut.", false, 6.99)
            menu1.add(menuItem1_1)
            menu1.add(menuItem1_2)

            val menu2 = Menu("Dessert", "What you eat after dinner.")
            val menuItem2_1 = MenuItem("Apple Pie", "American Pie", false, 5.99)
            val menuItem2_2 = MenuItem("Strawberry Pie", "Pie made with strawberries", false, 6.99)
            menu2.add(menuItem2_1)
            menu2.add(menuItem2_2)
            menu1.add(menu2)

            val menu3 = Menu("Cafe", "Coffee shop menu")
            val menuItem3_1 = MenuItem("Coffee", "Dark Roast", false, 5.99)
            val menuItem3_2 = MenuItem("Matcha Tea", "Japanese tea", false, 6.99)
            menu3.add(menuItem3_1)
            menu3.add(menuItem3_2)
            menu1.add(menu3)

            menu1.print()
        }
    }

}
