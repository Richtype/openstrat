/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The Longitude class is a compile time wrapper around a Double. The longitude value is stored in arc seconds,to allow precise storage of values
 * specified in the old Degrees, Minutes and Seconds system. Decimals of a degree can also be stored precisely. */
final class Longitude private(val milliSecs: Double) extends AnyVal with AngleLike
{
}

/** Companion object of the [[Longitude]] class. */
object Longitude
{ def degs(degVal: Double): Longitude = new Longitude(degVal.degsToMilliSecs)
  def radians(value: Double): Longitude = new Longitude(value.radiansToMilliSecs)
  def secs(value: Double): Longitude = new Longitude(value * 1000)
}