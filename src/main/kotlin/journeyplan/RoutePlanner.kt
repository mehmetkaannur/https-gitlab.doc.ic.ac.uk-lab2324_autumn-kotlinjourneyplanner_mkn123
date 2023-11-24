package journeyplan

// Add your code for the route planner in this file.
class SubwayMap(val segmentList: List<Segment>) {
  private fun helper(origin: Station, destination: Station, visited: Set<Station>): List<Route> {
    if (origin == destination) {
      return emptyList()
    }
    val connected = mutableListOf<Segment>()
    for (i in segmentList) {
      if (i.station1.name == origin.name) {
        connected.add(i)
      }
    }
    return connected.map { helper(it.station2, destination, visited.plus(it.station2)) }.flatten()
  }

  fun routesFrom(origin: Station, destination: Station): List<Route> {
    return helper(origin, destination, emptySet())
  }
}

fun londonUnderground(): SubwayMap {
  val station1 = Station("Notting Hill Gate")
  val station2 = Station("South Kensington")
  val station3 = Station("Earl's Court")
  val line1 = Line("Circle")
  val line2 = Line("District")
  val a = Segment(station1, station2, line1, 5)
  val b = Segment(station2, station3, line2, 4)
  return SubwayMap(listOf(a, b))
}

class Route(private val segmentList: List<Segment>) {
  private var totalTime = 0
  fun duration(): Int {
    for (i in segmentList) {
      totalTime += i.time
    }
    return totalTime
  }

  fun numChanges(): Int {
    var counter = 0
    var recentLine = segmentList.first().line
    for (i in segmentList) {
      if (recentLine != i.line) {
        counter += 1
        recentLine = i.line
      }
    }
    return counter
  }

  override fun toString(): String {
    val origin: String = segmentList.first().station1.name
    val dest: String = segmentList.last().station2.name
    val fromTo = "$origin to $dest - ${duration()} minutes, ${numChanges()} changes"
    var journey = "$fromTo\n"
    val lineRecent = segmentList.first().line
    var initialStation = segmentList.first().station1
    var previousPath = segmentList.first()
    for (i in segmentList) {
      if (segmentList.last().station2 == i.station2) {
        journey += " - $initialStation to ${i.station2} by ${previousPath.line}"
      } else if (lineRecent != i.line) {
        journey += " - $initialStation to ${i.station1} by ${previousPath.line}\n"
        initialStation = i.station1
      }
      previousPath = i
    }
    return journey
  }
}

fun main() {
  val map = londonUnderground()
  println(map.routesFrom(map.segmentList.first().station1, map.segmentList.last().station2))
}
