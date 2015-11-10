import data.Shipment
import orderings.PriorityAndHeavierFirst
import org.scalatest.FlatSpec

/**
 * Priority and heavier first ordering specs.
 */
class PriorityAndHeavierFirstSpec extends FlatSpec{

  "The order" should "compare priorities first" in {

    val l = List( Shipment(1,2000,2), Shipment(2,1000,1) )

    val lSorted = l.sorted( PriorityAndHeavierFirst )

    assert( lSorted.head.id == 2)
  }

  "Two items wil equal priority" should "find the heavier first" in {

    val l = List( Shipment(1,2000,1), Shipment(2,1000,1), Shipment(3, 2400, 2) )

    val lSorted = l.sorted( PriorityAndHeavierFirst )

    assert( lSorted.head.id == 1)
  }

}
