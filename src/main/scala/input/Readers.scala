package input

import data.Shipment

import scala.io.Source
import scala.util.{Success, Try}


object Readers {

  /**
   * Reads the line data to create a shipment value object and use the normalizer to convert the weight to an internal value.
   * @param normalizer Normalizer.
   * @param line Line to be read.
   * @return
   */
  def shipmentLineReader(normalizer : (Double, String) => Option[Long])( line: String ) : Option[Shipment] = {

    /**
     * Convert s to T using trans in a saver way.
     * @param s String to convert
     * @param trans Transformation
     * @tparam T Output type
     * @return T version of s
     */
    def saveConvert[T](s: String, trans: String => T) : Option[T] = Try( trans(s) ) match {
      case Success(value) => Some(value)
      case _ => None
    }

    /**
     * Convert the string parameters to a shipment value, and use the normalizer on the weight.
     * @param id Shipment id.
     * @param weight Weight
     * @param unit Weight units.
     * @param priority Priority
     * @return Shipment
     */
    def convert( id: String, weight: String, unit: String, priority: String ) : Option[Shipment] = for {
      realId:Long <- saveConvert(id, s => s.toLong )
      realWeight:Double <- saveConvert(weight, s => s.toDouble )
      normalizedWeight <- normalizer(realWeight, unit)
      realPriority:Int <- saveConvert(priority, s => s.toInt )
    } yield Shipment(realId, normalizedWeight, realPriority )


    line match {
      case null => None
      case s if s.isEmpty => None
      case s => s.split(",") match {
        case Array(id, _, weight, unit) => convert(id, weight, unit, Int.MaxValue.toString )
        case Array(id, _, weight, unit, priority) => convert(id, weight, unit, priority)
        case _ => None
      }
    }
  }

}
