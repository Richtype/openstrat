/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Common trait for [[Ellipse]] and EArc. The main purpose of this trait is not to share code, but to establish naming conventions for elliptical
 * properties. */
trait EllipseBased extends GeomElem
{
  /** Radius 1 of the ellipse. By default this is the horizontal axis of the ellipse. This can be the major or minor axis. */
  def radius1: Double

  /** Diameter 1 of the ellipse. By default this is the horizontal axis of the ellipse.This can be the major or minor axis. */
  final def diameter1: Double = radius1 * 2

  /** Radius 2 of the ellipse. By default this is the vertical axis of the ellipse. This can be the major or minor axis. */
  def radius2: Double

  /** Diameter 2 of this ellipse. By default this is the vertical axis of the ellipse. This can be the major or minor axis. */
  final def diameter2: Double = radius2 * 2

  /** The X component of the centre of the Ellipse. */
  def xCen: Double

  /** The Y component of the centre of the Ellipse. */
  def yCen: Double

  /** The centre of the ellipse. */
  def cen: Pt2

  /** The end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */
  def pAxes1: Pt2

  /** The X component of the end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex
   *  for the major axis or a co-vertex for the minor axis. */
  def xAxes1: Double

  /** The Y component of the end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex
   *  for the major axis or a co-vertex for the minor axis. */
  def yAxes1: Double

  /** The start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
   *  axis or a co-vertex for the minor axis. */
  def pAxes2: Pt2

  /** The X component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
   *  axis or a co-vertex for the minor axis.. */
  def xAxes2: Double

  /** The y component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a
   *  vertex for the major axis or a co-vertex for the minor axis. */

  def yAxes2: Double
  /** The start point of axis 1. By default this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */

  def pAxes3: Pt2

  def xAxes3: Double

  def yAxes3: Double

  /** The end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
   *  a co-vertex for the minor axis. */
  def pAxes4: Pt2

  /** The X component of the end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
   *  a co-vertex for the minor axis. */
  def xAxis4: Double

  /** The Y component of the end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
   *  a co-vertex for the minor axis. */
  def yAxis4: Double

  /** The 2D vector [[Vec2]] from the centre point to pAxes1, the end point of axis 1 , by default on the right of the Ellipse this arc is based
   *  on. */
  def cenP1: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes2, the start point of axis 2, by default at the bottom of the Ellipse this arc is based
   *  on. */
  def cenP2: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes3, the start point of axis 1 , by default on the left of the Ellipse this arc is based
   *  on. */
  def cenP3: Vec2

  /** The 2D vector [[Vec2]] from the centre point to pAxes4, the end point of axis 2, by default at the top of the Ellipse this arc is based on. */
  def cenP4: Vec2
}