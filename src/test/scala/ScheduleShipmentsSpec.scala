import data.{SchedulerParameters, Shipment}
import org.scalatest.FlatSpec

import scala.util.Random


/**
 * Schedule shipments specs.
 */
class ScheduleShipmentsSpec   extends FlatSpec {

  val testScheduler = ScheduleManager.ScheduleShipments ( SchedulerParameters(8, 7, 10 ) ) (_)

  "a shipment is more than max weight " should "be scheduled after day 15" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 20, 1)))

    assert( schedules.head.day == 15 )

  }

  "a shipment is less than max weight " should "be scheduled the first day (0)" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 1, 1)))

    assert( schedules.head.day == 0 )

  }

  "a shipment is less than max weight " should "be scheduled on the first hour the first day" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 1, 1)))

    assert( schedules.head.hour == 8 )

  }


  "two shipments with the less weight then the max" should "be scheduled on the first day smaller priority(high priority) first" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 2, 3), Shipment(2,1,1)))

    assert( schedules.head.id == 2 )

  }

  "two shipments with the more weight then the max" should "be scheduled on the 15 day smaller priority(high priority) first" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 12, 3), Shipment(2,11,1)))

    assert( schedules.head.id == 2 )

  }

  "two shipments with equal priority and weight less than the max" should "be scheduled on the first day lighter shipment first" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 2, 1), Shipment(2,1,1)))

    assert( schedules.head.id == 2 )
  }

  "two shipments with equal priority and weight more than the max" should "be scheduled on the 15 day heavier shipment first" in {

    val schedules = testScheduler( List[Shipment](Shipment(1, 12, 1), Shipment(2,14,1)))

    assert( schedules.head.id == 2 )
  }

  "no shipments with more than max weight" should "be schedule before 15 day" in {

    val shipments = 1 to 60*24*7 map {
      case i => Shipment(i, Random nextInt 100, Random nextInt 4)
    }

    val schedules = testScheduler(shipments)

    assert( !schedules.exists( s => s.day < 15 && shipments.find( ss => ss.id == s.id ).get.weight > 10 )  )

  }

  "the shipments" should "be scheduled only once" in {

    val shipmentCount = 30*24*7

    val shipments = 1 to shipmentCount map {
      case i => Shipment(i, Random nextInt 100, Random nextInt 4)
    }

    val schedules = testScheduler(shipments)

    val scheduleCount = schedules.groupBy(_.id).values.toVector.map( g => g.size ).sum

    assert( shipmentCount == scheduleCount )

  }
}
