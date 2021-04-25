/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._, math.sqrt, reflect.ClassTag

/** A grid of Hexs. The grid may be a regular rectangle of hexs or an irregular grid with variable length rows.
 *  @groupdesc SidesGroup Trait members that operate on the sides of the Hex Grid.
 *  @groupname SidesGroup Side Members
 *  @groupprio SidesGroup 1010 */
trait HGrid extends TGrid
{
  def numOfRow2s: Int
  def numOfRow0s: Int

  /** Carries out the procedure function on each Hex tile centre coordinate in the given tile row. This method is defined here rather than on TileGrid
   * so it can take the specific narrow [[HCen]] parameter to the foreach function. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit

  def rowIForeach(r: Int, count: Int)(f: (HCen, Int) => Unit): Int

  override def numOfTileRows: Int = numOfRow2s + numOfRow0s

  override def xRatio: Double = 1.0 / sqrt(3)
  def cCen: Double = (cTileMin + cTileMax) / 2.0

  def xCen: Double = cCen * xRatio

  /** foreachs over each Hex tile's centre HCen. */
  final def foreach(f: HCen => Unit): Unit = foreachRow(r => rowForeach(r)(f))

  final def iForeach(f: (HCen, Int) => Unit) =
  { var count: Int = 0
    foreachRow{r => count = rowIForeach(r, count)(f) }
  }

  final def map[B, BB <: ArrImut[B]](f: HCen => B)(implicit build: ArrTBuilder[B, BB]): BB =
  { val res = build.newArr(numOfTiles)
    iForeach((hCen, i) => res.unsafeSetElem(i, f(hCen)))
    res
  }

  /** flatMaps from all hex tile centre coordinates to an Arr of type ArrT. The elements of this array can not be accessed from this grid class as the
   *  TileGrid structure is lost in the flatMap operation. */
  final def flatMap[ArrT <: ArrImut[_]](f: HCen => ArrT)(implicit build: ArrTFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numOfTiles)
    foreach{ hCen => build.buffGrowArr(buff, f(hCen))}
    build.buffToArr(buff)
  }

  override def foreachCenCoord(f: TCoord => Unit): Unit = foreach(f)

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive] = map(_.active())

  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline final def arrIndex(hc: HCen): Int = arrIndex(hc.r, hc.c)

  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  def arrIndex(r: Int, c: Int): Int

  /** New immutable Arr of Tile data. */
  final def newTileArr[A <: AnyRef](value: A)(implicit ct: ClassTag[A]): HCenArr[A] =
  { val res = HCenArr[A](numOfTiles)
    res.mutSetAll(value)
    res
  }

  final def newTileBuffArr[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrBuff[A] = HCenArrBuff(numOfTiles)

  /** New Tile immutable Tile Arr of Opt data values. */
  final def newTileArrOpt[A <: AnyRef](implicit ct: ClassTag[A]): HCenArrOpt[A] = new HCenArrOpt(new Array[A](numOfTiles))

  /* Methods that operate on Hex tile sides. ******************************************************/

  /** The number of Sides in the TileGrid. Needs reimplementing.
   *  @group SidesGroup */
  final val numSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }

  override def sideLines: LineSegs = sideCoordLines.map(_.toLine2)

  /** foreach Hex side's coordinate HSide, calls the effectfull function.
   * @group SidesGroup */
  final def sidesForeach(f: HSide => Unit): Unit = sideRowForeach(r => rowForeachSide(r)(f))

  /** Calls the Foreach procedure on every Hex Side in the row given by the input parameter.
   *  @group */
  def rowForeachSide(r: Int)(f: HSide => Unit): Unit

  /** maps over each Hex Side's coordinate [[HSide]] in the given Row.
   *  @group SidesGroup */
  final def sidesMap[B, ArrT <: ArrImut[B]](f: HSide => B)(implicit build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val res: ArrT = build.newArr(numSides)
    var count = 0
    sidesForeach{hs =>
      res.unsafeSetElem(count, f(hs))
      count += 1
    }
    res
  }

  /** The Hex Sides of the Hex Grid defined in integer constructed [[HCoordLineSeg.]].
   *  @group SidesGroup */
  def sideCoordLines: Arr[HCoordLineSeg] = sidesMap[HCoordLineSeg, Arr[HCoordLineSeg]](_.coordLine)
}