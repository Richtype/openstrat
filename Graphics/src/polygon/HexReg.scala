/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class HexReg(xCen: Double, yCen: Double, x6: Double, y6: Double) extends Polygon
{
  override def apply(index: Int): Vec2 = ???

  override def cen: Vec2 = Vec2(xCen, yCen)
  def v6: Vec2 = Vec2(x6, y6)

  /** The 1st Vertex. */
  override def v1: Vec2 = v1.rotateAbout(cen, deg60)

  /** May throw on a 0 vertices polygon. */
  override def x1: Double = v1.x

  /** May throw on a 0 vertices polygon. */
  override def y1: Double = v1.y

  override def foreachVert(f: Vec2 => Unit): Unit = ???

  override def foreachVertTail[U](f: Vec2 => U): Unit = ???

  override def ptsArray: Array[Double] = ???

  override def xVertsArray: Array[Double] = ???

  override def yVertsArray: Array[Double] = ???

  override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

  /** The number of vertices and also the number of sides in this Polygon. */
  override def vertsNum: Int = ???

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def xVertGet(index: Int): Double = ???

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVertGet(index: Int): Double = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a polygon, returns a Polygon. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): Polygon = ???

  /** XY scaling 2D geometric transformation on a Polygon returns a Polygon. This allows different scaling factors across X and Y dimensions. The
   * return type will be narrowed in some, but not all descendant Polygon types. */
  override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???
}
