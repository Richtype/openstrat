/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

trait CircleGraphic extends ShapeGraphic
{ type GraphicT <: CircleGraphic
  def circle: Circle
  def fTrans(newCircle: Circle): GraphicT
  override def mirrorXOffset(yOffset: Double): GraphicT = fTrans(circle.mirrorXOffset(yOffset))
  override def mirrorX: GraphicT = fTrans(circle.mirrorX)
  override def mirrorYOffset(xOffset: Double): GraphicT = fTrans(circle.mirrorYOffset(xOffset))
  override def mirrorY: GraphicT = fTrans(circle.mirrorY)
  override def slate(offset: Vec2): GraphicT = fTrans(circle.slate(offset))
  @inline def slate(xOffset: Double, yOffset: Double): GraphicT = fTrans(circle.slate(xOffset, yOffset))
  override def scale(operand: Double): GraphicT = fTrans(circle.scale(operand))
  override def prolign(matrix: ProlignMatrix): GraphicT = fTrans(circle.prolign(matrix))
  override def rotate90: GraphicT = fTrans(circle.rotate90)
  override def rotate180: GraphicT = fTrans(circle.rotate180)
  override def rotate270: GraphicT = fTrans(circle.rotate270)
  override def rotateRadians(radians: Double): GraphicT = fTrans(circle.rotateRadians(radians))
  override def mirror(line: Line2): GraphicT = fTrans(circle.mirror(line))
}

final case class CircleFill(circle: Circle, fillColour: Colour) extends CircleGraphic with ShapeFill
{ type GraphicT = CircleFill
  override def fTrans(newCircle: Circle): GraphicT = CircleFill(newCircle, fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

final case class CircleDraw(circle: Circle, lineWidth: Double, lineColour: Colour) extends CircleGraphic with ShapeDraw
{ type GraphicT = CircleDraw
  override def fTrans(newCircle: Circle): CircleDraw = CircleDraw(newCircle, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = ??? // cp.circleDraw(this) 
  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def scaleSlate(scale: Double, cen: Vec2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}