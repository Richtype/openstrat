/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** SVG ViewBox. */
case class ViewBox(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{ override def name: String = "viewBox"
  override def valueStr: String = minX.str -- minY.str -- width.str -- height.str
}

/** XML attribute ofr width. */
case class WidthAtt(value: Double) extends XmlAtt
{ override def name: String = "width"
  override def valueStr: String = value.toString
}

/** XML attribute for height. */
case class HeighAtt(value: Double) extends XmlAtt
{ override def name: String = "height"
  override def valueStr: String = value.toString
}

case class SvgRotate(degrees: Double, x: Double, y: Double) extends XmlAtt
{ override def name: String = "transform"
  override def valueStr: String = "rotate" + Arr(degrees, x, y).mkString(" ").enParenth
}