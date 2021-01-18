package ostrat
package pParse
import utest._

object Hexa0xTokenTest extends TestSuite
{
  val c1 = "0x1234".toChars
  val o1 = c1.offsetter0
  val p1 = StrPosn(1, 1)
  val r1 = srcToETokens.str("0x1234")
  println(r1)
  val Ht1 = Nat0xToken(p1, "1234")
  val rr1 = 0x1234

  val c2 = "0xC32AB34".toChars
  val o2 = c2.offsetter0
  val r2 = parseNatRawToken(o2, p1)(c2)
  val Ht2 = Nat0xToken(p1, "C32AB34")
  val rr2 = 0xC32AB34

  val tests = Tests
  {
    "Parse" -
    {
      assertMatch(r1){ case GoodArr1(Nat0xToken(p1, "1234")) => }
      //assertMatch(r2){ case Good3(CharsOff(9), StrPosn(1, 10), Ht2 ) => }
    }
    "Values" -
    {
      Ht1.getInt ==> rr1
      Ht1.asHexaInt ==> rr1
      Ht2.getInt ==> rr2
    }
  }
}
