package compute

import java.io.Serializable
import java.rmi.*
import java.rmi.server.*

/** 
 * Proxy Pattern
 *
 * The Proxy Pattern provides a surrogate or
 * placeholder for another object to control access to it.
 */

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ rmiregistry & java -cp code1.jar GumballMachineTestDrive santafe.mightygumball.com 100
// $ rmiregistry & java -cp code1.jar GumballMachineTestDrive boulder.mightygumball.com 200
// $ rmiregistry & java -cp code1.jar GumballMachineTestDrive austin.mightygumball.com 250
//
// $ java -cp code1.jar GumballMachineTestDrive santafe.mightygumball.com 100
// $ java -cp code1.jar GumballMachineTestDrive boulder.mightygumball.com 200
// $ java -cp code1.jar GumballMachineTestDrive austin.mightygumball.com 250
// $ java -cp code1.jar GumballMonitorTestDrive

// jar cfm code1.jar manifest.txt compute/*.class


interface State : Serializable {
    fun insertQuarter()
    fun ejectQuarter()
    fun turnCrank()
    fun dispense()
}

class NoQuarterState(
    @Transient val gumballMachine: GumballMachine
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
    @Transient val gumballMachine: GumballMachine
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
    @Transient val gumballMachine: GumballMachine
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
    @Transient val gumballMachine: GumballMachine
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

// interface MyRemote {
//     fun getMyCount()
//     fun getMyLocation()
//     fun getMyState()
// }

interface GumballMachineRemote : Remote {
    @Throws(RemoteException::class)
    fun machineCount(): Int

    @Throws(RemoteException::class)
    fun machineLocation(): String

    @Throws(RemoteException::class)
    fun machineState(): State
}

class GumballMachine 
@Throws(RemoteException::class)
constructor(
    val location: String,
    var count: Int
) : UnicastRemoteObject(), GumballMachineRemote {
    // var count: Int    
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

    override fun machineCount(): Int {
        return count
    }

    override fun machineLocation(): String {
        return location
    }

    override fun machineState(): State {
        return state
    }
}

class GumballMachineTestDrive() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size < 2) {
                println("GumballMachine <name> <inventory>")
                System.exit(1)
            }
            try {
                val location = args[0]
                val count = Integer.parseInt(args[1])
                val gumballMachine = GumballMachine(location, count)
                    as GumballMachineRemote
                val rmiLocation = "//${location}/gumballmachine"
                Naming.rebind(rmiLocation, gumballMachine)
            } catch (e: Exception) {
                println("GumballMachineTestDrive exception ---")
                e.printStackTrace()
            }
        }
    }
}

class GumballMonitor(val machine: GumballMachineRemote) {
    fun report() {
        println("Gumball Machine: ${machine.machineLocation()}")
        println("Current inventory: ${machine.machineCount()} gumballs")
        println("Current state: ${machine.machineState()}")
    }
}

class GumballMonitorTestDrive() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val locations = listOf(
                "rmi://santafe.mightygumball.com/gumballmachine",
                "rmi://boulder.mightygumball.com/gumballmachine",
                "rmi://austin.mightygumball.com/gumballmachine"
            )

            val monitors = mutableListOf<GumballMonitor>()

            for (location in locations) {
                try {
                    val machine = Naming.lookup(location) as GumballMachineRemote
                    val monitor = GumballMonitor(machine)
                    monitors.add(monitor)
                } catch (e: Exception) {
                    println("GumballMonitorTestDrive ---")
                    e.printStackTrace()
                }
            }

            for (monitor in monitors) {
                monitor.report()
            }

        }
    }
}
