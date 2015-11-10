package output

import data.Schedule
import org.joda.time.DateTime

/**
 *  Writers for Schedules
 */
object Writers {

  /**
   * Writes the schedules to the standard output as comma separated values.
   * @param initDate Initial day.
   * @param schedules Schedules.
   */
  def stdoutWriter( initDate: DateTime, schedules : Seq[Schedule] ) : Unit ={
    println("day,hour,slot,id")
    val csv = schedules.map {
                s => initDate.plusDays(s.day).toString("yyyy-MM-dd") + "," +
                     "%02d".format(s.hour) + ":00," +
                     s.slot + "," +
                     s.id
              }

    csv.foreach( println )
  }
}
