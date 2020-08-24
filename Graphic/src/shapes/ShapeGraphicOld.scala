/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._

/** A shape based graphic. */
trait ShapeGraphicOld extends DisplayElem
{ def shape: Shape
  def attribs: Arr[Attrib]
  def svgStr: String
}

/** Companion object for the ShapeGraphic class. */
object ShapeGraphicOld
{
  implicit class ArrImplicit(val thisArr: Arr[ShapeGraphicOld])
  {
    def svgList: String = thisArr.foldLeft("")(_ + "\n" + _.svgStr)
  }
}

/** A shape graphic that includes a fill. */
trait ShapeFillTr extends ShapeGraphicOld
{ def fillColour: Colour
  def fillAttrib: FillAttrib = FillAttrib(fillColour)
}

/** A Graphic that just fills a shape. */
trait ShapeFill extends ShapeFillTr

trait ShapeDrawTr extends ShapeGraphicOld
{ def lineWidth: Double
  def lineColour: Colour
  def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
}

trait ShapeDraw extends ShapeDrawTr
{
  def drawAttribs: Arr[Attrib] = Arr(strokeWidthAttrib, strokeAttrib)
}

trait ShapeFillDraw extends ShapeFillTr with ShapeDrawTr
{ def fillDrawAttribs: Arr[Attrib] = Arr(fillAttrib, strokeWidthAttrib, strokeAttrib)
}