package journeyplan

// Add your code for the route planner in this file.
class SubwayMap(val segment_list: List<Segment>) {
  fun helper(origin: Station, destination: Station, visited: Set<Station>): List<Route> {
    if (origin == destination) {
      return emptyList()
    }
    val connected = mutableListOf<Segment>()
    for (i in segment_list) {
      if (i.station1.name.toString() == origin.name.toString()) {
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
  val station3 = Station("Knightsbridge")
  val line1 = Line("Circle")
  val line2 = Line("District")
  val a = Segment(station1, station2, line1, 5)
  val b = Segment(station2, station3, line2, 4)
  return SubwayMap(listOf(a, b))
}

class Route(val segment_list: List<Segment>) {
  var total_time = 0
  fun duration(): Int {
    for (i in segment_list) {
      total_time += i.time
    }
    return total_time
  }


  fun numChanges(): Int {
    var counter = 0
    var recentLine = segment_list.first().line
    for (i in segment_list) {
      if (recentLine != i.line) {
        counter += 1
        recentLine = i.line
      }
    }
    return counter
  }

  override fun toString(): String {
    var journey =
      " ${segment_list.first().station1.name} to ${segment_list.last().station2.name} - ${duration()} minutes, ${numChanges()} changes\n"
    for (i in segment_list) {
      journey += "- ${i.station1.name} to ${i.station2.name} by ${i.line.name}\n"
    }
    return journey
  }
}
