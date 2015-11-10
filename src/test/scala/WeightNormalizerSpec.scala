import org.scalatest.FlatSpec
import input.Normalizers.weightNormalizer
import data.UnitConversion


/**
 * Weight normalizer specs.
 */
class WeightNormalizerSpec extends FlatSpec {

   def testNormalizer = weightNormalizer( UnitConversion(2, Map( "kg" -> 1, "ton" -> 1000 ) ) )(_,_)

    "Any value without conversion" should "return None" in {
      assert( weightNormalizer( UnitConversion(2, Map( "kg" -> 1 )))( 2, "ss").isEmpty )
    }

    "A value found" should "be multiplied by the conversion and then by 100 to consider only two decimal placess" in {

      val converted = testNormalizer( 2, "ton")

      assert( converted.contains(200000) )
    }

    "Two converted values with less than two decimal digits difference" should "be equals" in {

      val converted1 = testNormalizer( 2.001, "kg")
      val converted2 = testNormalizer( 2.002, "kg")

      assert( converted1 == converted2 )
    }

    "What happens with null as unit" should " return None" in {

      val converted = testNormalizer(2, null)

      assert( converted.isEmpty )

    }
}
