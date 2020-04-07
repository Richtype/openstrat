package ostrat
package pChess
import geom._, pGrid._, Colour._

sealed trait Piece extends UnScaledPolygon
{
  def apply: Polygon = Square.apply
}
object Pawn extends Piece with UnScaledPolygonYMirror
{
  def rtLine100: Vec2s = Vec2s(20 vv 80, 20 vv 20, 40 vv 20, 40 vv 0)
}

object Rook extends Piece with UnScaledPolygonYMirror
{ def rtLine100: Vec2s = Vec2s(5 vv 100, 5 vv 95, 10 vv 95, 10 vv 100, 25 vv 100, 25 vv 80, 20 vv 80, 20 vv 75, 15 vv 75, 20 vv 15, 30 vv 10, 30 vv 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)

}
object Knight extends Piece
object Bishop extends Piece

object Queen extends Piece with UnScaledPolygonYMirror
{ def rtLine100: Vec2s = Vec2s(0 vv 100, 10 vv 90, 25 vv 100, 15 vv 75, 20 vv 75, 20 vv 70, 15 vv 70, 20 vv 30, 30 vv 10, 30 vv 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)
}

object King extends Piece

sealed trait Player extends WithColour

object PWhite extends Player
{ def colour = White
}

object PBlack extends Player
{ def colour = Black
}

/** Player Piece */
case class PPiece(player: Player, piece: Piece)

trait ChessScen
{ val turnSeg: Int
  implicit def grid: SquareGrid
  def pieces: OptRefs[PPiece]
}

object ChessStart extends ChessScen
{
  val turnSeg = 0
  implicit val grid = SquareGrid(2, 16, 2, 16)
  val pieces = grid.newOptRefs[PPiece]
  val rp = Refs(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach{(p, i) => pieces.gridUnsafeSetSome(2, i * 2 + 2, PPiece(PWhite, p)) }
  iToForeach(2, 16, 2)(c => pieces.gridUnsafeSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach{(p, i) => pieces.gridUnsafeSetSome(16, i * 2 + 2, PPiece(PBlack, p)) }
  iToForeach(2, 16, 2)(c => pieces.gridUnsafeSetSome(14, c, PPiece(PBlack, Pawn)))
}