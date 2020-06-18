/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Rectangle aligned to the X and Y axes. */
trait Rectanglelign extends Rectangle with Rectangularlign
{ 
}

/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
case class Rectlign(xCen: Double, yCen: Double, width: Double, height: Double) extends Rectanglelign
{
  override def fill(colour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): TransElem = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): TransElem = ???

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): TransElem = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def mirrorYOffset(xOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def mirrorXOffset(yOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def mirrorX: TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def mirrorY: TransElem = ???

  override def prolign(matrix: ProlignMatrix): TransElem = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: TransElem = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: TransElem = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: TransElem = ???

  override def rotateRadians(radians: Double): TransElem = ???

  override def mirror(line: Line2): TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

/** Companion object for the Rectlign class */
object Rectlign
{ def apply(cen: Vec2, width: Double, height: Double): Rectlign = new Rectlign(cen.x, cen.y, width, height)
}
