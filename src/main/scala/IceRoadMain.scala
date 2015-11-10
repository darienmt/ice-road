import data.{SchedulerParameters, UnitConversion}
import org.joda.time.{DateTimeZone, DateTime}
import output.Writers

import scala.io.Source

/**
 *  Entry point for the ice road
 */
object IceRoadMain {
  def main(args: Array[String]) = {

    // Reading shipments from stdin.
    val convs = UnitConversion(2, Map( "kg" -> 1, "ton" -> 1000, "lbs" ->  0.453592 ) )

    val configuredNormalized = input.Normalizers.weightNormalizer(convs)(_,_)

    val lineReader = input.Readers.shipmentLineReader( configuredNormalized )(_)

    val shipments = for {
      line <- Source.stdin.getLines().toVector.drop(1)
      shipment <- lineReader(line)
    } yield shipment

    // Constructing schedule.
    val scheduler = ScheduleManager.ScheduleShipments ( SchedulerParameters(8, 7, configuredNormalized(15, "ton").get ) ) (_)
    val schedules = scheduler(shipments)

    // Writing schedules to stdout.
    val year = DateTime.now().year().get()
    val date = new DateTime( year, 2, 1, 0, 0)

    Writers.stdoutWriter( date, schedules  )
  }

}
