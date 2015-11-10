package input

import data.UnitConversion


object Normalizers {

  /**
   * Normalize the provided ''value'' from ''unit'' to an internal representation of the value.
   * If the ''unit'' conversion could not be found, None is returned.
   * Two values should be converted to the same internal representation if the difference between then is less than
   * the precision informed on ''conversions''.
   * @param value Value to be normalized.
   * @param valueUnit Value's units.
   * @param conversions Implicit conversion data.
   * @return The internal representation of the value.
   */
  def weightNormalizer(conversions: UnitConversion)(value: Double, valueUnit: String) : Option[Long] =
    conversions.table.get( valueUnit ) match {
      case None => None
      case Some(conversion) => Some( ( value * conversion * Math.pow( 10, conversions.precision ) ).round )
    }


}
