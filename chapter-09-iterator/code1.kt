//
// iterator pattern
//

interface Iterator {
    fun hasNext(): Boolean
    fun next(): MenuItem
}

class MenuItem(
    val name: String,
    val description: String,
    val vegetarian: Boolean,
    val price: Double
)

class PancakeHouseMenu {

    val menuItems: MutableList<MenuItem>

    constructor() {
        menuItems = mutableListOf()
        addItem("K&B's Pancake Breakfast",
            "Pancakes with scrambled eggs and toast",
            true,
            2.99
        )

        addItem("Regular Pancake Breakfast",
            "Pancakeswith fried eggs, sausage",
            false,
            2.99
        )

        addItem("Blueberry Pancakes",
            "Pancakes made with fresh blueberries",
            true,
            3.49
        )

        addItem("Waffles",
            "Waffles with your choice of blueberries or strawberries",
            true,
            3.59
        )
    }
    
    fun createIterator(): Iterator {
        return PancakeHouseIterator(menuItems)
    }

    fun addItem(
        name: String,
        description: String,
        vegetarian: Boolean,
        price: Double
    ) {
        val menuItem = MenuItem(name, description, vegetarian, price)
        menuItems.add(menuItem)
    }
}

class DinerMenu {

    val MAX_ITEMS = 6
    var numberOfItems = 0
    val menuItems: Array<MenuItem?>

    constructor() {
        menuItems = arrayOfNulls(MAX_ITEMS)
        addItem("Vegetarian BLT",
            "(Fakin') Bacon with lettuce & tomato on whole wheat",
            true,
            2.99
        )

        addItem("BLT",
            "Bacon with lettuce & tomato on whole wheat",
            false,
            2.99
        )

        addItem("Soup of the day",
            "Soup of the day, with a side of potato salad",
            false,
            3.29
        )

        addItem("Hotdog",
            "A hot dog, with sauerkraut, relish, onions, topped with cheese",
            false,
            3.05
        )
    }

    fun createIterator(): Iterator {
        return DinerMenuIterator(menuItems)
    }

    fun addItem(
        name: String,
        description: String,
        vegetarian: Boolean,
        price: Double
    ) {
        val menuItem = MenuItem(name, description, vegetarian, price)
        if (numberOfItems >= MAX_ITEMS) {
            println("Sorry, menu is full! Can't add item to menu")
        } else {
            menuItems[numberOfItems] = menuItem
            numberOfItems = numberOfItems + 1
        }
    }
}

class PancakeHouseIterator(
    val items: MutableList<MenuItem>
): Iterator {
    var position = 0
    override fun next(): MenuItem {
        val menuItem = items.get(position)
        position = position + 1
        return menuItem
    }
    override fun hasNext(): Boolean {
        // if (position >= items.size || items.get(position) == null) {
        if (position >= items.size) {
            return false
        } else {
            return true
        }
    }
}

class DinerMenuIterator(
    val items: Array<MenuItem?>
): Iterator {
    var position = 0
    override fun next(): MenuItem {
        val menuItem = items[position]
        position = position + 1
        return menuItem!!
    }
    override fun hasNext(): Boolean {
        if (position >= items.size || items[position] == null) {
            return false
        } else {
            return true
        }
    }
}

fun printMenu(iterator: Iterator) {
    while(iterator.hasNext()) {
        val menuItem = iterator.next()
        print(menuItem.name + " ")
        print(menuItem.price.toString() + " ")
        println(menuItem.description + " ")
    }
}

//
// main
//
fun main() {
    val pancakeHouseMenu = PancakeHouseMenu()
    val breakfastItems = pancakeHouseMenu.menuItems
    val pancakeIterator = pancakeHouseMenu.createIterator()

    val dinerMenu = DinerMenu()
    val lunchItems = dinerMenu.menuItems
    val dinerIterator = dinerMenu.createIterator()

    for (i in 0 until breakfastItems.size) {
        val menuItem = breakfastItems.get(i)
        print(menuItem.name + " ")
        print(menuItem.price.toString() + " ")
        println(menuItem.description + " ")
    }
    println("---")
    for (i in 0 until lunchItems.size) {
        val menuItem = lunchItems[i]
        if (menuItem != null) {
            print(menuItem.name + " ")
            print(menuItem.price.toString() + " ")
            println(menuItem.description + " ")
        }
    }
    println("---")
    printMenu(pancakeIterator)
    println("---")
    printMenu(dinerIterator)


}