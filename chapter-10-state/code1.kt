/**
 * State Pattern
 *
 * The State Pattern allows an object to alter its behavior when its internal
 * state changes. The object with appear to change its class.
 *
 */

class GumballMachineOriginal {

    val SOLD_OUT = 0
    val NO_QUARTER = 1
    val HAS_QUARTER = 2
    val SOLD = 3

    var state = SOLD_OUT
    var count = 0

    fun constructor(count: Int) {
        this.count = count
        if (count > 0) {
            state = NO_QUARTER
        }
    }

    fun insertQuarter() {
        if (state == HAS_QUARTER) {
            println("You can't insert another quarter")
        } else if (state == NO_QUARTER) {
            println("You inserted a quarter")
        } else if (state == SOLD) {
            println("Please wait, we're already giving you a gumball")
        } else if (state == SOLD_OUT) {
            println("You can't insert a quarter,  the machine is sold out")
        }
    }

    fun ejectQuarter() {
        if (state == HAS_QUARTER) {
            println("Quarter returned")
        } else if (state == NO_QUARTER) {
            println("You haven't inserted a quarter")
        } else if (state == SOLD) {
            println("Sorry, you already turned the crank")
        } else if (state == SOLD_OUT) {
            println("You can't eject, you haven't inserted a quarter yet")
        }
    }

    fun turnCrank() {
        if (state == HAS_QUARTER) {
            println("You turned...")
            state = SOLD
            dispense()
        } else if (state == NO_QUARTER) {
            println("You turned but there's no quarter")
        } else if (state == SOLD) {
            println("Turning twice doesn't get you another gumball!")
        } else if (state == SOLD_OUT) {
            println("You turned, but there are no gumballs")
        }
    }

    fun dispense() {
        if (state == HAS_QUARTER) {
            println("You need to turn the crank")
        } else if (state == NO_QUARTER) {
            println("You need to pay first")
        } else if (state == SOLD) {
            println("A gumball comes rolling out the slot")
            count = count - 1
            if (count == 0) {
                println("Oops, out of gumballs!")
                state = SOLD_OUT
            } else {
                state = NO_QUARTER
            }
        } else if (state == SOLD_OUT) {
            println("You turned, but there are no gumballs")
        }   
    }
}

interface State {
    fun insertQuarter()
    fun ejectQuarter()
    fun turnCrank()
    fun dispense()
}

class NoQuarterState(
    val gumballMachine: GumballMachine
) : State {
    override fun insertQuarter() {
        println("You inserted a quarter")
        // gumballMachine.setState(gumballMachine.hasQuarterState)
        gumballMachine.state = gumballMachine.hasQuarterState
    }

    override fun ejectQuarter() {
        println("You haven't inserted a quarter")
    }

    override fun turnCrank() {
        println("You turned, but there's no quarter")
    }

    override fun dispense() {
        println("You need to pay first")
    }
}

class HasQuarterState(
    val gumballMachine: GumballMachine
) : State {
    override fun insertQuarter() {
        println("You can't insert another quarter")
    }

    override fun ejectQuarter() {
        println("Quarter returned")
        gumballMachine.state = gumballMachine.noQuarterState
    }

    override fun turnCrank() {
        println("You turned...")
        gumballMachine.state = gumballMachine.soldState
    }

    override fun dispense() {
        println("No gumball dispensed")
    }
}

class SoldState(
    val gumballMachine: GumballMachine
) : State {
    override fun insertQuarter() {
        println("Please wait, we're already giving you a gumball")
    }

    override fun ejectQuarter() {
        println("Sorry, you already turned the crank")
    }

    override fun turnCrank() {
        println("Turing twice doesn't get you another gumball")
    }

    override fun dispense() {
        gumballMachine.releaseBall()
        if (gumballMachine.count > 0) {
            gumballMachine.state = gumballMachine.noQuarterState
        } else {
            gumballMachine.state = gumballMachine.soldOutState
        }
    }
}

class SoldOutState(
    val gumballMachine: GumballMachine
) : State {
    override fun insertQuarter() {
        println("Sold out, not taking quarters")
    }

    override fun ejectQuarter() {
        println("Sold out, quarter shouldn't have been recieved")
    }

    override fun turnCrank() {
        println("Sold out, shouldn't have accepted quarter")
    }

    override fun dispense() {
        println("Sold out, no gumballs to dispense")
    }
}

class GumballMachine(
    var count: Int
) {
    var state: State
    val noQuarterState: NoQuarterState
    val hasQuarterState: HasQuarterState
    val soldState: SoldState
    val soldOutState: SoldOutState

    init {
        noQuarterState = NoQuarterState(this)
        hasQuarterState = HasQuarterState(this)
        soldState = SoldState(this)
        soldOutState = SoldOutState(this)
        state = noQuarterState
    }

    fun insertQuarter() {
        state.insertQuarter()
    }

    fun ejectQuarter() {
        state.ejectQuarter()
    }

    fun turnCrank() {
        state.turnCrank()
        if (state == soldState) {
            state.dispense()
        }
    }

    fun releaseBall() {
        println("A gumball comes rolling out the slot...")
        if (count > 0) {
            count = count - 1
        }
    }

}

// $ kotlinc code1.kt -include-runtime -d code2.jar
// $ java -jar code2.jar GumballMachineTestDrive
object GumballMachineTestDrive {
    @JvmStatic
    fun main(args: Array<String>) {
        val gumballMachine = GumballMachine(5)
        gumballMachine.insertQuarter()
        gumballMachine.ejectQuarter()

        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
        gumballMachine.insertQuarter()
        gumballMachine.turnCrank()
    }
}