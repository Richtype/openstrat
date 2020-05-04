/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object CircleIcon

final case class Circle(radius: Double, x: Double, y: Double) extends EllipseLike with Shape
{ override type RigidT = Circle
  def vCen: Vec2 = x vv y
  def shear(xScale: Double, yScale: Double): Ellipse = new Ellipse(x, y, x + radius, 0, radius)
  override def rotateRadians(radians: Double): Circle = Circle(radius, vCen.rotateRadians(radians))
  override def slate(offset: Vec2): Circle = Circle(radius, x + offset.x, y + offset.y)
  override def scale(operand: Double): Circle = Circle(radius * operand, x * operand, y * operand)

  override def mirror(line: Line2): Circle = Circle(radius, vCen.mirror(line))

  def fill(colour: Colour): CircleFill = CircleFill(this, colour)
  def draw(lineWidth: Double = 2, colour: Colour): CircleDraw = CircleDraw(this, lineWidth, colour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour): CircleFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)
}

/** This object provides factory methods for circles. */
object Circle
{
  def apply(radius: Double, cen: Vec2 =Vec2Z): Circle = new Circle(radius, cen.x, cen.y)

  def segs(scale: Double = 1.0): PolyCurve =
  { val a = ArcSeg(Vec2Z, Vec2(0.5 * scale, 0))
    val sg1 = (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i))))
    PolyCurve(sg1 :_*)
  }

  def fillNew(colour: Colour): Unit = ???

  def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): PolyCurveFill =
  { val fSegs = segs(radius).slate(posn)            
    PolyCurveFill(fSegs, colour)
  }
}