package ostrat
package pFlags
import geom._, Colour._

object UnitedStates extends Flag
{ val name = "United States"
  val ratio = 1.9

  /** Old Glory Red */
  val oGRed = Colour(0xFFB22234)
  val usBlue = Colour(0xFF3C3B6E)

  /** star horizontal spacing */
  val starX = 1.9 * 2 / 5 / 12

  /** star vertical spacing */
  val starY = 1.0 * 7 / 13 / 10

  /** Diameter of star = four-fifths of the stripe width, Width of stripe = Height/13, Height = Width/1.9) */
  val starScale = 4.0 / 5 / 13 / 1.9

  val star0 = Star5().scale(starScale).fill(White)
  val star = star0.slate(-0.95, 0.5)

  val apply =
  { val blueFieldOld = Rectangle.fromTL(0.76, 7.0/ 13, -0.95 vv 0.5).fill(usBlue)
    val stars = ijToMap(0, 10, 2)(1, 9, 2) { (x, y) => star.slate(starX + x * starX, -y * starY) }
    val starsInner = ijToMap(2, 10, 2)(2, 8, 2) { (x, y) => star.slate(x * starX, -y * starY) }
    val stripes = topToBottomRepeat(13, oGRed, White)
    stripes +- blueFieldOld ++ stars ++ starsInner
  }
}

object Colombia extends Flag
{
 val name = "Colombia"
 val ratio = 1.5
 val apply: Arr[PaintFullElem] = topToBottom(Colour(0xFFFCD116), Colour(0xFFFCD116), Colour(0xFF003893), Colour(0xFFCE1126))
}


object Chile extends Flag
{
 val name = "Chile"
 val ratio = 1.5
 val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
      Rectangle(ratio, 1).fill(White),
      Rectangle(ratio, 0.5, 0 vv -0.25).fill(Colour(0xFFD52B1E)),
      Rectangle(0.5, 0.5, -0.5 vv 0.25).fill(Colour(0xFF0039A6)),
      Star5().scale(0.125).slate(-0.5 vv 0.25).fill(White)
    )
  }
}
