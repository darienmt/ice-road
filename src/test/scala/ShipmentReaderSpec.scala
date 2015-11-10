import org.scalatest.FlatSpec
import input.Readers.shipmentLineReader

/**
 * Shipment reader specs.
 */
class ShipmentReaderSpec extends FlatSpec {

    def testShipmentReader = shipmentLineReader( ( value, string) => Some(value.round ))(_)

    "An empty line" should "return None" in {

      val s = testShipmentReader("")

      assert( s.isEmpty )

    }

    "A null line" should "return None" in {
      val s = testShipmentReader(null )

      assert( s.isEmpty )
    }

    "A line with more commas than usual" should "return None" in {
      val s = testShipmentReader("a,2,3,4,5,5,6,6,4,2")

      assert( s.isEmpty )
    }

    "A line with less commas than usual" should "return None" in {
      val s = testShipmentReader("a,2," )

      assert( s.isEmpty )
    }

    "A line with exact values but missing the last one" should "return Some(Shipment)" in {
      val s = testShipmentReader("27,drill bits,17000,lbs,")

      assert( s.isDefined )
    }

    "A line with exact values" should "return Some(Shipment)" in {
      val s = testShipmentReader("27,drill bits,17000,lbs,1" )

      assert( s.isDefined )
    }

    "A line with exact number of parameters but some empty" should "return None" in {
      val s = testShipmentReader("27,drill bits,,lbs,1")

      assert( s.isEmpty )
    }

  "The first item is not a number" should "return None" in {
    val s = testShipmentReader("2s7,drill bits,,lbs,1")

    assert( s.isEmpty )
  }
}
