/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCanv
import geom._

/** This trait is for Canvas Implementations with a Top left origin and downward y axis. It should not be used directly by graphical applications. */
trait CanvasTopLeft extends CanvasPlatform
{
  def tlCen: Vec2 => Vec2 = v => Vec2(width / 2 + v.x, height / 2 - v.y)
  def matrix: ProlignMatrix = ProlignMatrix.mirrorY.slate(width / 2, height / 2)
 
  final override def pPolyFill(poly: PolygonTr, colour: Colour): Unit = tlPolyFill(poly.fTrans(tlCen), colour)
  final override def pPolyDraw(poly: PolygonTr, lineWidth: Double, colour: Colour): Unit = tlPolyDraw(poly.fTrans(tlCen), lineWidth, colour)
  //final override def pPolyFillDraw(pfd: PolyFillDraw): Unit = tlPolyFillDraw(pfd.fTrans(tlCen))
  final override def pLinePathDraw(pod: LinePathDraw): Unit = tlLinePathDraw(pod.fTrans(tlCen))
  final override def lineDraw(ld: LineDraw): Unit = tlLineDraw(ld.fTrans(tlCen))

  final override def cArcDrawOld(ad: CArcDrawOld): Unit = tlCArcDrawOld(ad.fTrans(tlCen))

  final override def cArcDraw(cad: CArcDraw): Unit = tlCArcDraw(cad.reflectX.slate(width / 2, height / 2))

  final override def bezierDraw(bd: BezierDraw): Unit = tlBezierDraw(bd.fTrans(tlCen))
  final override def linesDraw(lsd: LinesDraw): Unit = tlLinesDraw(lsd.fTrans(tlCen)): Unit
  final override def dashedLineDraw(dld: DashedLineDraw): Unit = tlDashedLineDraw(dld.fTrans(tlCen))

  final override def pShapeFill(shape: PolyCurve, colour: Colour): Unit = tlShapeFill(shape.fTrans(tlCen), colour)

  final override def pShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit =
    tlShapeDraw(shape.fTrans(tlCen), lineWidth, colour: Colour)
  // final override def circleFillOld(cf: CircleFillOld): Unit = tlCircleFillOld(cf.mirrorX.slate(width / 2, height / 2))
  final override def circleFillOld(cf: CircleFillOld): Unit = tlCircleFillOld(cf.reflectX.slate(width / 2, height / 2))
  final override def circleFill(circle: Circle, colour: Colour): Unit = tlCircleFill(circle.reflectX.slate(width / 2, height / 2), colour)

  final override def circleFillRadial(circle: Circle, fill: FillRadial): Unit =
    tlCircleFillRadial(circle.reflectX.slate(width / 2, height / 2), fill)

  final override def circleDrawOld(cd: CircleDraw): Unit = tlCircleDrawOld(cd.reflectX.slate(width / 2, height / 2))

  final override def circleDraw(circle: Circle, lineWidth: Double, colour: Colour): Unit =
    tlCircleDraw(circle.reflectX.slate(width / 2, height / 2), lineWidth, colour)

  final override def circleFillDraw(cfd: CircleFillDraw): Unit = tlCircleFillDraw(cfd.reflectX.slate(width / 2, height / 2))

  final override def ellipseFill(cf: EllipseFill): Unit =
  { val e1: EllipseFill = cf.reflectX
    val e2: EllipseFill = e1.slate(width / 2, height / 2)
    tlEllipseFill(e2)
  }

  final override def textGraphic(tg: TextGraphic): Unit = tlTextGraphic(tg.fTrans(tlCen))
  final override def textOutline(tl: TextOutline): Unit = tlTextOutline(tl.fTrans(tlCen))

  final override def clip(pts: Polygon): Unit = tlClip(pts.fTrans(tlCen))

  protected[this] def tlPolyFill(poly: PolygonTr, colour: Colour): Unit
  protected[this] def tlPolyDraw(poly: PolygonTr, lineWidth: Double, colour: Colour): Unit
  protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit

  protected[this] def tlLineDraw(ld: LineDraw): Unit
  protected[this] def tlCArcDrawOld(ad: CArcDrawOld): Unit

  protected[this] def tlCArcDraw(tld: CArcDraw): Unit

  protected[this] def tlLinesDraw(lsd: LinesDraw): Unit
  protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit

  protected[this] def tlShapeFill(shape: PolyCurve, colour: Colour): Unit

  protected[this] def tlShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit
  protected[this] def tlCircleFillOld(cf: CircleFillOld): Unit
  protected[this] def tlCircleFill(circle: Circle, colour: Colour): Unit
  protected[this] def tlCircleFillRadial(circle: Circle, fill: FillRadial): Unit

  protected[this] def tlCircleDrawOld(cd: CircleDraw): Unit
  protected[this] def tlCircleDraw(circle: Circle, lineWidth: Double, lineColour: Colour): Unit
  
  protected[this] def tlCircleFillDraw(cfd: CircleFillDraw): Unit
  protected[this] def tlEllipseFill(cf: EllipseFill): Unit
  
  protected[this] def tlBezierDraw(bezierDraw: BezierDraw): Unit
   
  protected[this] def tlTextGraphic(tg: TextGraphic): Unit
  protected[this] def tlTextOutline(tl: TextOutline): Unit

  protected[this] def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseDownTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDown(Vec2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseMovedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseMoved(Vec2(x - width / 2, height / 2 - y), mb)
  protected[this] def mouseDraggedTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseDragged(Vec2(x - width / 2, height / 2 - y), mb)
   
  protected[this] def tlClip(pts: Polygon): Unit
}