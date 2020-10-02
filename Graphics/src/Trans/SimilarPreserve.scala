/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** All leaf classes of this type that will preserve their types for all the Similar 2d geometrical transformations. */
trait SimilarPreserve extends ProlignPreserve
{ type ThisT <: SimilarPreserve
  override def negY: ThisT = fTrans(_.negY)
  override def negX: ThisT = fTrans(_.negX)

  def reflect(lineLike: LineLike): ThisT = fTrans(_.reflect(lineLike))
  //def reflect(lineSeg: LineSeg): ThisT = fTrans(_.reflect(lineSeg))
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  override def rotateRadians(radians: Double): ThisT = fTrans(_.rotateRadians(radians))
}

/** A trait that preserves one type through all the similar 2D geometrical transformations and preserves a second type ThisT2 through the other
 * affine transformations. */
trait SimilarAffPreserve extends SimilarPreserve
{ type ThisT2 <: TransElem
  def fTrans2(f: Vec2 => Vec2): ThisT2
  override def xyScale(xOperand: Double, yOperand: Double): ThisT2 = fTrans2(_.xyScale(xOperand, yOperand))
  override def xShear(operand: Double): ThisT2 = fTrans2(_.xShear(operand))
  override def yShear(operand: Double): ThisT2 = fTrans2(_.yShear(operand))
}