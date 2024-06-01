import java.lang.reflect.*

//
// remote proxy pattern
//

interface Person {
    var name: String
    var gender: String
    var interests: String
    var geekRating: Int
}

class PersonImpl(
    override var name: String,
    override var gender: String,
    override var interests: String,
    geekRating: Int
) : Person {
    var ratingCount = 0

    override var geekRating = geekRating
        get() {
            if (field == 0) {
                return 0
            }
            return field / ratingCount
        }
        set(value) {
            field = field + value
            ratingCount = ratingCount + 1
        }

    override fun toString(): String {
        return "name: $name " +
            ", gender: $gender " +
            ", geek rating: $geekRating" +
            ", interests: $interests"
    }

}

class OwnerInvocationHandler(
    val person: Person
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any? {
        try {
            if (method.name.startsWith("get")) {
                return method.invoke(person)
            } else if (method.name.equals("setGeekRating")) {
                throw IllegalAccessException()
            } else if (method.name.startsWith("set")) {
                return method.invoke(person, args!![0] as String)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}

class NonOwnerInvocationHandler(
    val person: Person
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any? {
        try {
            if (method.name.startsWith("get")) {
                return method.invoke(person)
            } else if (method.name.equals("setGeekRating")) {
                return method.invoke(person, args!![0] as String)
            } else if (method.name.startsWith("set")) {
                throw IllegalAccessException()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}

// $ kotlinc code2.kt -include-runtime -d code2.jar
// $ java -cp code2.jar MatchMakingTestDrive
class MatchMakingTestDrive {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val test = MatchMakingTestDrive()
            test.drive()
        }
    }

    fun drive() {
        val joe = PersonImpl("Edward", "Male", "Lifting weight, reading", 0)
        val ownerProxy = getOwnerProxy(joe)
        println("Name is ${ownerProxy.name}")
        ownerProxy.interests = "bowling, Go"
        try {
            ownerProxy.geekRating = 10
        } catch (e: Exception) {
            println("Can't set rating from owner proxy")
        }
        println("Rating is ${ownerProxy.geekRating}")

        val nonOwnerProxy = getNonOwnerProxy(joe)
        println("Name is ${nonOwnerProxy.name}")
        try {
            nonOwnerProxy.interests = "bowling, Go"
        } catch (e: Exception) {
            println("Can't set interests from non owner proxy")
        }
        nonOwnerProxy.geekRating = 3
        println("Rating set from non owner proxy")
        println("Rating is ${nonOwnerProxy.geekRating}")
    }

    fun getOwnerProxy(person: Person): Person {
        return Proxy.newProxyInstance(
            person.javaClass.classLoader,
            person.javaClass.interfaces,
            OwnerInvocationHandler(person)
        ) as Person
    }

    fun getNonOwnerProxy(person: Person): Person {
        return Proxy.newProxyInstance(
            person.javaClass.classLoader,
            person.javaClass.interfaces,
            NonOwnerInvocationHandler(person)
        ) as Person
    }
}
