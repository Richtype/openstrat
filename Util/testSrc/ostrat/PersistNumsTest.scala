/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object PersistNumsTest  extends TestSuite
{
  val tests = Tests
  {
    "Ints" -
    {
      5.str ==> "5"
      "5".findType[Int] ==> Good(5)
      "228".findInt ==> Good(228)
      "-228".findInt ==> Good(-228)
      (-86).str ==> "-86"
      (-86).strComma ==> "-86"
      (-86).strTyped ==> "Int(-86)"
      "7".findType[Int] ==> Good(7)
      "7".findType[Double] ==> Good(7)
    }

    "Doubles" -
     {
       23.4.str ==> "23.4"
       8.0.str ==> "8"
       9.12345.show(Show.Standard, 0, 0) ==> "9"
       9.12345.show(Show.Standard, 1, 0) ==> "9.1"
       9.12345.show(Show.Standard, 4, 0) ==> "9.1234"
       4.3234.show(Show.Standard, 6, 6) ==> "4.323400"
       //d.strTyped ==> "DFloat(8)"
      // (-6.00).str ==> "-6"
     }
  }
}
