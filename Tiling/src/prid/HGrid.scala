/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import scala.math.sqrt

/** A grid of Hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows. */
trait HGrid extends TGrid
{
  def numOfRow2s: Int
  def numOfRow0s: Int

  /** Carries out the procedure function on each Hex tile centre coordinate in the given tile row. This method is defined here rather than on TileGrid
   * so it can take the specfific narrow [[HCen]] parameter to the foreach function. */
  def rowForeachTile(r: Int)(f: HCen => Unit): Unit

  override def numOfTileRows: Int = numOfRow2s + numOfRow0s

  override def xRatio: Double = 1.0 / sqrt(3)

  /** foreachs over each tile's centre Roord. */
  final def foreach(f: HCen => Unit): Unit = ??? // foreachRow(y => rowForeachTile(y)(f))

  /** flatMaps from all hex tile cntre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: ArrBase[_]](f: HCen => ArrT)(implicit build: ArrFlatBuild[ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToArr(buff)
  }
}
