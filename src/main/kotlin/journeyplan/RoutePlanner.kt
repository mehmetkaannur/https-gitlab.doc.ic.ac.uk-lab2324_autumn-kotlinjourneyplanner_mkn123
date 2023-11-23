package journeyplan

// Add your code for the route planner in this file.
class SubwayMap(val segment_list: List<Segment>)

fun londonUnderground(): SubwayMap {
  val station1: Station = Station("Notting Hill Gate")
  val station2: Station = Station("South Kensington")
  val station3: Station = Station("Knightsbridge")
  val line1: Line = Line("Circle")
  val line2: Line = Line("District")
  val a: Segment = Segment(station1, station2, line1, 5)
  val b: Segment = Segment(station2, station3, line2, 4)
  val map: SubwayMap = SubwayMap(listOf(a, b))
  return map
}

class Route(val segment_list: List<Segment>) {
  override fun toString(): String {
    var journey = " ${segment_list.first().station1.name} to ${segment_list.last().station2.name}\n"
    for (i in segment_list) {
      journey += "- ${i.station1.name} to ${i.station2.name} by ${i.line.name}\n"
    }
    return journey
  }
}
