/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Cubic bezier curve. */
class Bezier (val xStart: Double, val yStart: Double, val xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double,
      val xEnd: Double, val yEnd: Double) extends CurveSeg with AffinePreserve
{ override type ThisT = Bezier
  def typeStr: String = "Bezier"
  def fTrans(f: Pt2 => Pt2): Bezier = Bezier(f(pStart), f(pC1), f(pC2), f(pEnd))
  final def pC1: Pt2 = Pt2(xC1, yC1)
  final def pC2: Pt2 = Pt2(xC2, yC2)
  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  def draw(colour: Colour = Black, lineWidth: Double = 2): BezierDraw = BezierDraw(this, colour, lineWidth)
}

object Bezier
{ def apply(pStart: Pt2, pC1: Pt2, pC2: Pt2, pEnd: Pt2): Bezier = new Bezier(pStart.x, pStart.y, pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
}
      
/** Functional class for Drawing a cubic Bezier curve. */
case class BezierDraw (curveSeg: Bezier, colour: Colour, lineWidth: Double) extends CurveSegDraw with GraphicAffineElem with CanvElem
{ override type ThisT = BezierDraw
  def typeStr: String = "BezierDraw"
  //def str = persist6(pStart, pC1, pC2, pEnd, lineWidth, colour) 
  override def fTrans(f: Pt2 => Pt2): BezierDraw = BezierDraw(curveSeg.fTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.bezierDraw(this)
  def xC1: Double = curveSeg.xC1
  def yC1: Double = curveSeg.yC1
  final def pC1: Pt2 = Pt2(xC1, yC1)
  def xC2: Double = curveSeg.xC1
  def yC2: Double = curveSeg.xC2
  final def pC2: Pt2 = Pt2(xC2, yC2)
}

/** Companion object for the BezierDraw class. */
object BezierDraw
{
   //def apply (curveSeg: Bezier, lineWidth: Double, colour: Colour): BezierDraw = new BezierDraw(curveSeg, colour, lineWidth)
}