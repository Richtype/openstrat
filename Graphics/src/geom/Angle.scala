/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Angle of inclination. Its particularly important not to use this class to represent Latitudes as the Angle class has a normal range 0 <= a < 360
 *  degrees, while Latitudes have a normal range +- 90 degrees. Unlike [[AngleVec]] this class has no multiply or divide, * or / methods. It has add
 *  and subtract, + and - methods, but these take [[AngleVec]]s as operands not other Angles. To Add,subtract or scale angles of inclination would
 *  make no sense. */
final class Angle private(val milliSecs: Double) extends AnyVal with AngleLike  with ProdDbl1
{
  @inline override def dblValue: Double = secs

  /** Creates a Vec2 from this Angle for the given scalar magnitude parameter. */
  def toVec2(magnitude: Double): Vec2 = Vec2(math.cos(radians) * magnitude, math.sin(radians) * magnitude)

  override def toString = degStr2
  def degStr2: String = degs.str2 + "\u00B0"

  def +(other: AngleVec): Angle = Angle.milliSecs(milliSecs + other.milliSecs)
  def -(other: AngleVec): Angle = Angle.milliSecs(milliSecs - other.milliSecs)

  def rotationFrom0: AngleVec = AngleVec.milliSecs(milliSecs)
  def rotationFrom90: AngleVec = AngleVec.milliSecs(milliSecs - MilliSecsIn90Degs)

  /** Plus 90, add 90 degrees to this Angle, rotate this angle by 90 degrees in an anti-clockwise direction. */
  def p90: Angle = Angle.milliSecs(milliSecs + MilliSecsIn90Degs)

  /** Minus 90, subtract 90 degrees from this Angle, rotate this angle by 90 degrees in a clockwise direction. */
  def m90: Angle = Angle.milliSecs(milliSecs - MilliSecsIn90Degs)

  /** plus 180, adds / subtracts 180 degrees from this Angle. As an Angle's range is 360 > a >= 0, adding or subtracting 180 degrees gives the same
   *  result. */
  def p180: Angle = Angle.milliSecs(milliSecs + MilliSecsIn180Degs)

  /** Returns the positive [[AngleVec]] from this Angle to the operand Angle. A value from 0 until 360 degrees.  */
  def deltaPosTo(other: Angle): AngleVec = other.milliSecs - milliSecs match
  { case ms if ms < 0 => AngleVec.milliSecs(ms + MilliSecsIn360Degs)
    case ms => AngleVec.milliSecs(ms)
  }

  /** Returns the negative [[AngleVec]] from this Angle to the operand Angle. A value from 0 until -360 degrees.  */
  def deltaNegTo(other: Angle): AngleVec = other.milliSecs - milliSecs match
  { case ms if ms > 0 => AngleVec.milliSecs(ms - MilliSecsIn360Degs)
    case ms => AngleVec.milliSecs(ms)
  }
  
  def addRadians(other: Double): Angle = Angle.radians(radians + other)
  def subRadians(other: Double): Angle = Angle.radians(radians - other)

  /** bisects the positive or anti-clockwise arc between this Angle and the operand Angle. */
  def bisectPos(operand: Angle): Angle = this + deltaPosTo(operand) / 2

  /** bisects the negative or clockwise arc between this Angle and the operand Angle. */
  def bisectNeg(operand: Angle): Angle = this + deltaNegTo(operand) / 2
}

/** Angle Companion object. */
object Angle
{
  /** Factory method for Angle from number of degrees */
  @inline def apply(degrees: Double): Angle = new Angle((degrees %% 360) * MilliSecsInDeg)

  /** Factory method for creating Angle from the number of radians. */
  @inline def radians(radians: Double): Angle = new Angle(radians.radiansToMilliSecs %% MilliSecsIn360Degs)// (radians %+- Pi1) * 180 * MilliSecsInDeg / Pi1)

  /** Factory method for creating Angle from the number of angle seconds. */
  @inline def secs(value: Double): Angle = new Angle((value %% SecsIn360Degs) * 1000)

  /** Factory method for creating Angle from the number of thousands of an arc second. */
  @inline def milliSecs(value: Double): Angle = new Angle(value %% MilliSecsIn360Degs)
}

/** Efficient Immutable Array[Double] based collection class, with the Angle values stored as arc seconds. */
final class Angles(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl1[Angle]
{ override type ThisT = Angles
  override def typeStr: String = "Angles"
  override def newElem(dblValue: Double): Angle = Angle.secs(dblValue)
  override def unsafeFromArray(array: Array[Double]): Angles = new Angles(array)
  override def fElemStr: Angle => String = _.toString

  /** Not sure about this method. */
  override def foreachArr(f: Dbls => Unit): Unit = ???
}

/** Companion object for [[Angles]] class. */
object Angles
{
  def apply(elems: Angle*): Angles =
  { val array: Array[Double] = new Array[Double](elems.length)
    elems.iForeach((a, i) => array(i) = a.secs)
    new Angles(array)
  }

  /** Sequence of the four cardinal angles, 0, -90, 180, 90 degrees in clockwise order. */
  val cross: Angles = Angles(0.degsAng, 270.degsAng, 180.degsAng, 90.degsAng)

  /** Sequence of the four cardinal angles rotated by 45 degrees, 45, -45, -135, 135 degrees in clockwise order. */
  val cross45: Angles = Angles(45.degsAng, 315.degsAng, 225.degsAng, 135.degsAng)
}