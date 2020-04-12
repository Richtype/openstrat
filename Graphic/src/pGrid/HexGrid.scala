package ostrat
package pGrid
import geom._, math.sqrt

trait HexGrid extends TileGrid
{ def cTileMin: Int
  def cTileMax: Int
  def cStep: Int = 4
  override def roordToVec2(roord: Roord): Vec2 = HexGrid.roordToVec2(roord)
  def cCen: Double = (cTileMin + cTileMax) / 2.0
  def roordCen = Vec2(cCen, yCen)
  def xRatio: Double = HexGrid.xRatio
  override def xCen: Double = (cTileMin + cTileMax) / 2.0 * xRatio
  //override def sideRoordToLineRel(sideRoord: Roord, scale: Double, relPosn: Vec2): Line2 = HexGrid.sideRoordToLineRel(sideRoord, scale, relPosn)
  override def sideRoordsOfTile(tileRoord: Roord): Roords = HexGrid.sideRoordsOfTile(tileRoord)
  override def xLeft: Double = (cTileMin - 2) * xRatio
  override def xRight: Double = (cTileMax + 2) * xRatio
  def top: Double = yTileMax + HexGrid.yDist2
  def bottom: Double = yTileMin - HexGrid.yDist2
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = HexGrid.sideRoordToRoordLine(sideRoord)
  override def tileVertRoords(roord: Roord): Roords = HexGrid.vertRoordsOfTile(roord)
  def isTileRoord(r: Roord): Boolean = r.y.div4Rem2 & r.c.div4Rem2 | r.y.div4Rem0 & r.c.div4Rem0

  override def sideIndex(inp: Roord): Int =
  {
    var count = 0
    var res: Int = -1
    sidesForeach{r =>
      if (r == inp) res = count
      count += 1
    }
    res
  }

  /** Gives a Coods Seq of Cood along a horisonatal line */
  def SidesHorr(y: Int, xStart: Int, xEnd : Int): Roords =
  { val xs = if (xStart > xEnd) xStart.roundDownToOdd to xEnd.roundUpToOdd by -2 else xStart.roundUpToOdd to xEnd.roundDownToOdd by 2
    xs.pMap(c => Roord(y, c))
  }

  /*def findPath[TileT](startCood: Cood, endCood: Cood, fTerrCost: (TileT, TileT) => OptInt): Option[List[Cood]] =
  {
    var open: List[Node[TileT]] = Node(this.getTile(startCood), 0, getHCost(startCood, endCood), NoRef) :: Nil
    var closed: List[Node[TileT]] = Nil
    var found: Option[Node[TileT]] = None
    while (open.nonEmpty & found == None)
    {
      val curr: Node[TileT] = open.minBy(_.fCost)
      //if (curr.tile.cood == endCood) found = true
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: Refs[TileT] = this.tileNeighbours(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match
        {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) =>
          {
            val newGCost = nc + curr.gCost

            open.find(_.tile == tile) match
            {
              case Some(node) if newGCost < node.gCost => { node.gCost = newGCost; node.parent = OptRef(curr) }
              case Some(node) =>
              case None =>
              {
                val newNode  = Node(tile, newGCost, getHCost(tile.cood, endCood), OptRef(curr))
                open ::= newNode
                if (tile.cood == endCood) found = Some(newNode)
              }
            }
          }
        }
      }
    }*/
}

object HexGrid
{ /* converts Grid c to x. */
  val xRatio = 1.0 / sqrt(3)
  /** Verts start at Up and follow clockwise */
  val vertRoordsOfTile00: Roords = Roords(1 rr 0, 1 rr 2, -1 rr 2, -1 rr 0,  -1 rr -2, 1 rr -2)
  def vertRoordsOfTile(y: Int, c: Int): Roords = vertRoordsOfTile(y rr c)
  def vertRoordsOfTile(tileRoord: Roord): Roords = vertRoordsOfTile00.pMap(_ + tileRoord)
  val sideRoordsOfTile00: Roords = Roords(1 rr 1, 0 rr 2, -1 rr 1, -1 rr -1, 0 rr -2, 1 rr -1)
  //val sideRoordsOfTile00List: List[Roord] = Roords(1 cc 1, 2 cc 0, 1 cc -1, -1  cc -1, -2 cc 0, -1 cc 1)
  def sideRoordsOfTile(tileRoord: Roord): Roords = sideRoordsOfTile00.pMap(tileRoord + _)
  val adjTileRoordsOfTile00: Roords = sideRoordsOfTile00.pMap(_ * 2)
  def adjTileRoordsOfTile(tileRoord: Roord): Roords = adjTileRoordsOfTile00.pMap(tileRoord + _)

  def sideRoordToLineRel(sideRoord: Roord, scale: Double, relPosn: Vec2 = Vec2Z): Line2 =
    sideRoordToRoordLine(sideRoord).toLine2(c => (roordToVec2(c) -relPosn) * scale)

  def sideRoordToLine(sideRoord: Roord): Line2 = sideRoordToRoordLine(sideRoord).toLine2(roordToVec2)
  def sideRoordToRoordLine(sideRoord: Roord): RoordLine = sideRoordToRoordLine(sideRoord.y, sideRoord.c)
  //override def sideRoordToRoordLine(sideRoord: Roord): ostrat.pGrid.RoordLine = HexGrid.sideRoordToRoordLine(sideRoord)

  def sideRoordToRoordLine(y: Int, c: Int): RoordLine = fOrientation(y, c, RoordLine(y, c - 1, y, c + 1), RoordLine(y + 1, c, y - 1, c),
    RoordLine(y, c + 1, y, c - 1))

  def roordToVec2Rel(roord: Roord, relPosn: Vec2): Vec2 = roordToVec2(roord.y, roord.c) -relPosn

  /** Used for regular HexGrids and the regular aspect of irregular Hexgrids */
  def roordToVec2(roord: Roord): Vec2 = roordToVec2(roord.y, roord.c)

  def roordToVec2(y: Int, c: Int): Vec2 =
  { def x: Double = c * xRatio
    (y %% 4, c %% 4) match
    { case (yr, cr) if yr.isEven && cr.isEven => Vec2(x, y)
      case (yr, _) if yr.isEven => throw new Exception("Hex Roord " + y.toString -- c.toString + ", y is even but c is odd. This is an invalid HexRoord")
      case (yr, cr) if cr.isOdd  && yr.isOdd => Vec2(x, y)
      case (1, 0) | (3, 2)  =>  Vec2(x, y + yDist /2)
      case _ => Vec2(x, y - yDist / 2)
    }
  }

  @inline def fOrientation[A](y: Int, c: Int, upRight: => A, rightSide: => A, downRight: => A): A = if3Excep(
    (y.div4Rem1 && c.div4Rem1) || (y.div4Rem3 && c.div4Rem3), upRight,
    (y.div4Rem0 && c.div4Rem2) || (y.div4Rem2 && c.div4Rem0), rightSide,
    (y.div4Rem1 && c.div4Rem3) || (y.div4Rem3 && c.div4Rem1), downRight,
    "invalid Hex Side coordinate: " + y.toString.appendCommas(c.toString))

  def orientationStr(x: Int, y: Int): String = fOrientation(x, y, "UpRight", "Right", "DownRight")
  /** The previous value was 2 / sqrt(3). */
  val yDist = 2.0 / 3

  /** The previous value was 4 /  sqrt(3). */
  val yDist2 = 4.0 / 3

  @inline def x0 = 0
  @inline def y0 = yDist2
  /** The Up vertice */
  val v0 = Vec2(x0, y0)

  @inline def x1 = 2
  @inline def y1 = yDist
  /** The Up Right vertice */
  val v1 = Vec2(x1, y1)

  @inline def x2 = 2
  @inline def y2 = -yDist
  /** Down Right vertice */
  val v2 = Vec2(x2, y2)

  @inline def x3 = 0
  @inline def y3 = -yDist2
  /** The Down vertice */
  val v3 = Vec2(x3, y3)

  @inline def x4 = -2
  @inline def y4 = -yDist
  /** The Down Left vertice */
  val v4 = Vec2(x4, y4)

  @inline def x5 = -2
  @inline def y5 = yDist
  /** The up left vertice */
  val v5 = Vec2(x5, y5)

  val verts: Seq[Vec2] = Seq(v0, v1, v2, v3, v4,  v5)
  val cenVerts: Seq[Vec2] = Seq(Vec2Z, v0, v1, v2, v3, v4, v5)

  val triangleFan = Seq(Vec2Z, v0, v5, v4, v3, v2, v1)

  def latLong(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): LatLong =
  { val lat = (pt.y * gridScale + xyOffset.y) / EarthPolarRadius + latLongOffset.lat
    val long = (pt.x * gridScale + xyOffset.x) / (EarthEquatorialRadius * math.cos(lat)) + latLongOffset.long
    LatLong(lat, long)
  }

  def latLongToRoord(latLong: LatLong, latLongOffset: LatLong, xyOffset: Dist2, gridScale: Dist): Vec2 =
  { val y: Double = ((latLong.lat - latLongOffset.lat) * EarthPolarRadius - xyOffset.y) / gridScale
    val x: Double = ((latLong.long - latLongOffset.long) * EarthEquatorialRadius * math.cos(latLong.lat) - xyOffset.x) / gridScale
    Vec2(x * xRatio, y)
  }

  def latLongU(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridU))
  def latLongV(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridV))
  def latLongW(pt: Vec2, latLongOffset: LatLong, xyOffset: Dist2): LatLong = latLong(pt, latLongOffset, xyOffset, Dist(gridW))

  val gridA: Int = 1//3.125cm
  val gridB: Int = 2//6.25cm
  val gridC: Int = 4//12.5cm
  val gridD: Int = 8//0.25m
  val gridE: Int = 16//0.5m
  val gridF: Int = 32//1m
  val gridG: Int = 64//2m
  val gridH: Int = 128//4m
  val gridI: Int = 256//8m
  val gridJ: Int = 512//16m
  val gridK: Int = 1024//32m
  val gridL: Int = 2048//64m
  val gridM: Int = 4096//128m
  val gridN: Int = 8192//256m
  val gridO: Int = 16384//512m
  val gridP: Int = 32768//1.024km
  val gridQ: Int = 65536//2.048km
  val gridR: Int = 131072//4.096km
  val gridS: Int = 262144//8.192km
  val gridT: Int = 524288//16.384km
  val gridU: Int = 1048576//32.768km
  val gridV: Int = 2097152//65.536km
  val gridW: Int = 4194304//131.072km
  val gridX: Int = 8388608//266.144km
  val gridY: Int = 16777216//524.288km
  val gridZ: Int = 33554432//1048.576km

  def gridToGridDist(i: Int): Dist  = Dist(i / 32.0)
  val gridDistU: Dist = gridToGridDist(gridU)
  val gridDistV: Dist = gridToGridDist(gridV)
  val gridDistW: Dist = gridToGridDist(gridW)
  val gridDistX: Dist = gridToGridDist(gridX)
  val gridDistY: Dist = gridToGridDist(gridY)
}