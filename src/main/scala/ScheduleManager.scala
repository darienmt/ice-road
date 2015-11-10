import data.{SchedulerParameters, Schedule, Shipment}
import orderings.{PriorityAndHeavierFirst, PriorityAndLighterFirst}

object ScheduleManager {

  /**
   * Schedule the shipments based on the provided parameters.
   * @param params Schedule parameters.
   * @param shipments Shipments to schedule.
   * @return Schedules.
   */
  def ScheduleShipments(params: SchedulerParameters)( shipments : Seq[Shipment] ) : Seq[Schedule] = {

    val ( less, more ) = shipments partition(_.weight <= params.maxNormalizedWeight )

    val lessOrdered = less sorted PriorityAndLighterFirst

    val maxFirstDayTake = (23 - params.firstHourFirstDay) * params.slotsPerHour
    val firstDay = lessOrdered
                    .take( maxFirstDayTake )
                    .grouped(params.slotsPerHour)
                    .toVector
                    .zip( params.firstHourFirstDay to 23 )
                    .flatMap {
                        case ( hourShipment, hour ) => hourShipment.zip(1 to params.slotsPerHour)
                          .map {
                            case  ( s, slot ) => Schedule(0, hour, slot, s.id)
                          }
                    }

    val afterFirstDay = lessOrdered.drop(maxFirstDayTake)
    val until15 = ScheduleGenerator(1, 14, params.slotsPerHour, afterFirstDay)


    val after15 = afterFirstDay.drop(until15.size)
    val rest = ( more ++ after15 ) sorted PriorityAndHeavierFirst

    val until60 = ScheduleGenerator(15, 59, params.slotsPerHour, rest )

    firstDay ++ until15 ++ until60

  }

  /**
   * Generates schedules form init date to en date for the shipments provided.
   * @param init Initial date.
   * @param end End date.
   * @param slotPerHour Shipment per hour.
   * @param shipments Shipment's collection.
   * @return Schedules.
   */
  def ScheduleGenerator( init: Int, end: Int, slotPerHour: Int, shipments: Seq[Shipment]) : Seq[Schedule] =
    shipments
      .grouped(slotPerHour).toVector
      .grouped(24).toVector
      .zip(init to end)
      .flatMap {
        case ( dayShipments, day ) => dayShipments.zip(0 to 23)
          .flatMap {
          case (hourShipments, hour) => hourShipments.zip(1 to slotPerHour )
            .map {
            case ( s, slot ) => Schedule(day, hour, slot, s.id)
          }
        }
    }
}
