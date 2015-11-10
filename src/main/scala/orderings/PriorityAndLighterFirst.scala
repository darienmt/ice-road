package orderings

import data.Shipment

/**
 * Ordering for shipments by priority and lighter shipment first.
 */
object PriorityAndLighterFirst extends Ordering[Shipment]{

  override def compare(x: Shipment, y: Shipment): Int = (x,y) match {
    case t if x.priority < y.priority => -1 // x < y => true
    case t if x.priority > y.priority => 1 // x > y => true
    case t if x.priority == y.priority => t match {
      case tt if x.weight < y.weight => -1 // x < y => true
      case tt if x.weight > y.weight => 1  // x > y => true
      case _ => 0 // x, y are equals
    }
  }
}
