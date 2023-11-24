package journeyplan

import org.junit.Test
import kotlin.test.assertEquals

class RoutePlannerTest {

  private val northernLine = Line("Northern")
  private val victoriaLine = Line("Victoria")
  private val centralLine = Line("Central")

  private val highgate = Station("Highgate")
  private val archway = Station("Archway")
  private val tufnellPark = Station("Tufnell Park")
  private val kentishTown = Station("Kentish Town")
  private val camden = Station("Camden Town")
  private val euston = Station("Euston")
  private val warrenStreet = Station("Warren Street")
  private val oxfordCircus = Station("Oxford Circus")
  private val bondStreet = Station("Bond Street")

  private val tufnellParkToHighgate =
    Route(
      listOf(
        Segment(tufnellPark, archway, northernLine, 3),
        Segment(archway, highgate, northernLine, 3)
      )
    )

  private val highgateToOxfordCircus =
    Route(
      listOf(
        Segment(highgate, archway, northernLine, 3),
        Segment(archway, kentishTown, northernLine, 3),
        Segment(kentishTown, camden, northernLine, 3),
        Segment(camden, euston, northernLine, 3),
        Segment(euston, warrenStreet, victoriaLine, 3),
        Segment(warrenStreet, oxfordCircus, victoriaLine, 3)
      )
    )

  private val camdenToBondStreet =
    Route(
      listOf(
        Segment(camden, euston, northernLine, 3),
        Segment(euston, warrenStreet, victoriaLine, 3),
        Segment(warrenStreet, oxfordCircus, victoriaLine, 3),
        Segment(oxfordCircus, bondStreet, centralLine, 2)
      )
    )

  @Test
  fun `can calculate number of changes`() {
    assertEquals(0, tufnellParkToHighgate.numChanges())
    assertEquals(1, highgateToOxfordCircus.numChanges())
    assertEquals(2, camdenToBondStreet.numChanges())
  }

  @Test
  fun `can calculate total duration`() {
    assertEquals(6, tufnellParkToHighgate.duration())
    assertEquals(18, highgateToOxfordCircus.duration())
    assertEquals(11, camdenToBondStreet.duration())
  }

  @Test
  fun `toString omits calling points`() {
    assertEquals(
      """
                Tufnell Park to Highgate - 6 minutes, 0 changes
                 - Tufnell Park to Highgate by Northern Line
      """.trimIndent(),
      tufnellParkToHighgate.toString()
    )
  }

  @Test
  fun `toString shows changes`() {
    assertEquals(
      """
                Highgate to Oxford Circus - 18 minutes, 1 changes
                 - Highgate to Euston by Northern Line
                 - Euston to Oxford Circus by Victoria Line
      """.trimIndent(),
      highgateToOxfordCircus.toString()
    )
  }
}
