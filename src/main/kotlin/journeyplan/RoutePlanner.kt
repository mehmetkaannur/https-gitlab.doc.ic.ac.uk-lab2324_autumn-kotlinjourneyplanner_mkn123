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
    val origin: String = segment_list.first().station1.name
    val dest: String = segment_list.last().station2.name

    val fromTo = "$origin to $dest - ${duration()} minutes, ${numChanges()} changes"
    var journey = "$fromTo\n"

    val lineRecent = segment_list.first().line
    var stinit = segment_list.first().st1
    var prevpath = segment_list.first()


    for (i in segment_list) {

      if (segment_list.last().station2 == i.station2) {
        journey += " - $stinit to ${i.st2} by ${prevpath.line}"

      }
      else if (lineRecent != i.line) {

        journey += " - $stinit to ${i.st1} by ${prevpath.line}\n"
        stinit = i.st1
      }
      prevpath = i
    }
    return journey
  }
}

fun main() {
  val map = londonUnderground()
  println(map.routesFrom(map.segment_list.first().station1, map.segment_list.last().station2))
}