/**
 * Observer Pattern
 *
 * The Observer Pattern defines a one-to-many dependency between objets so that
 * when one object changes state, all of its dependents are notified and
 * updated automatically.
 *
 * Design Principle
 *
 * - Strive for loosely coupled designs between objects that interact.
 *
 */

interface Observer {
    fun update(temperature: Float , humidity: Float, pressure: Float, heatIndex: Float)
}

interface DisplayElement {
    fun display()
}

interface Subject {
    fun registerObserver(o: Observer )
    fun removeObserver(o: Observer)
    fun notifyObservers()
}

class CurrentConditionsDisplay : DisplayElement, Observer {
    var temperature: Float? = null
    var humidity: Float? = null
    var pressure: Float? = null
    var heatIndex: Float? = null
    var weatherData: WeatherData? = null

    constructor(weatherData: WeatherData) {
        this.weatherData = weatherData
        weatherData.registerObserver(this)
    }

    override fun display() {
        println("statistics: "
            + "temperature: $temperature, "
            + "humidity: $humidity, "
            + "pressure: $pressure, "
            + "heatIndex: $heatIndex")
    }

    override fun update(temperature: Float , humidity: Float, pressure: Float, heatIndex: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.heatIndex = heatIndex
        display() 
    }

     fun remove() {
        weatherData!!.removeObserver(this)
    }

}

class StatisticsDisplay : DisplayElement, Observer {
    var temperature: Float? = null
    var humidity: Float? = null
    var pressure: Float? = null
    var heatIndex: Float? = null
    var weatherData: WeatherData? = null

    constructor(weatherData: WeatherData) {
        this.weatherData = weatherData
        weatherData.registerObserver(this)
    }

    override fun display() {
        println("statistics: "
            + "temperature: $temperature, "
            + "humidity: $humidity, "
            + "pressure: $pressure, "
            + "heatIndex: $heatIndex")
    }

    override fun update(temperature: Float , humidity: Float, pressure: Float, heatIndex: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.heatIndex = heatIndex
        display() 
    }

    fun remove() {
        weatherData!!.removeObserver(this)
    }
}

class WeatherData() : Subject {
    var observers: MutableList<Observer> = mutableListOf()
    var temperature: Float? = null
    var humidity: Float? = null
    var pressure: Float? = null
    var heatIndex: Float? = null

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        for (o in observers ) {
            o.update(temperature!!, humidity!!, pressure!!, heatIndex!!)
        }
    }

    fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeassurements(temperature: Float, humidity: Float, pressure: Float) {
        println("\n--- setMeassurements ---")
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.heatIndex = computeHeatIndex(temperature, humidity)
        measurementsChanged();
    }

    private fun computeHeatIndex(t: Float, rh: Float): Float {
        val index = ((16.923 + (0.185212 * t) + (5.37941 * rh) - (0.100254 * t * rh) +
            (0.00941695 * (t * t)) + (0.00728898 * (rh * rh)) +
            (0.000345372 * (t * t * rh)) - (0.000814971 * (t * rh * rh)) +
            (0.0000102102 * (t * t * rh * rh)) - (0.000038646 * (t * t * t)) + (0.0000291583 *  
            (rh * rh * rh)) + (0.00000142721 * (t * t * t * rh)) +
            (0.000000197483 * (t * rh * rh * rh)) - (0.0000000218429 * (t * t * t * rh * rh)) +     
            0.000000000843296 * (t * t * rh * rh * rh)) -
            (0.0000000000481975 * (t * t * t * rh * rh * rh)))
        return index.toFloat();
    }

}

// $ kotlinc code1.kt -include-runtime -d code1.jar
// $ java -jar code1.jar WeatherStation
object WeatherStation {
    @JvmStatic
    fun main(args: Array<String>) {
        val weatherData = WeatherData()
        val currentConditionsDisplay = CurrentConditionsDisplay(weatherData)
        val statisticsDisplay = StatisticsDisplay(weatherData)

        weatherData.setMeassurements(80f, 65f, 30.4f)
        weatherData.setMeassurements(82f, 70f, 29.2f)

        currentConditionsDisplay.remove()
        weatherData.setMeassurements(78f, 90f, 29.2f)

        weatherData.removeObserver(statisticsDisplay)

        statisticsDisplay.remove()
        weatherData.setMeassurements(99f,80f, 33.3f)

        println()
    }
}
