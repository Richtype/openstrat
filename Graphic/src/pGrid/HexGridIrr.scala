package ostrat
package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row. */
class HexGridIrr(override val yTileMin: Int, val indexArr: Array[Int]) extends HexGrid
{
  override def numOfRows: Int = indexArr.length / 2
  def index(c: Int, y: Int): Int = (c - indexArr(y - yTileMin + 2)) / 4
  val numOfTiles: Int = iToFoldInt(yTileMin, yTileMax, 2) { (acc, i) => acc + xRowLen(i) }
  def xRowStart(y: Int): Int = indexArr(y - yTileMin)
  def xRowLen(y: Int): Int = indexArr(y - yTileMin + 1)
  def xRowEnd(y: Int): Int = xRowStart(y) + xRowLen(y) * 4 - 4

  def tilesAllForeach(f: Cood => Unit): Unit = tileRowsAllForeach{y => iToForeach(0, 0){x => f(Cood(x, y)) } }

  @inline override def yTileMax: Int = yTileMin + indexArr.length - 2

}

