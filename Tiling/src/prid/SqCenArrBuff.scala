/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

/** An Array of [[ArrayBuffer]]s corresponding to (the centres of a) Hex tile Grid. */
class SqCenArrBuff[A <: AnyRef](val unsafeArr: Array[Buff[A]])
{
  def appendAt(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = appendAt(SqCen(y, c), value)
  def appendAt(hCen: SqCen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(hCen)).append(value)
  def foreach(f: (SqCen, Buff[A]) => Unit)(implicit grid: SqGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.arrIndex(r))) }
}

/** Companion object for the Hex (centres) grid Array of ArrayBuffer classes. */
object SqCenArrBuff
{ /** Apply factory method, creates a Hex Grid Array of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArrBuff[A] =
  { val array = new Array[Buff[A]](length)
    iUntilForeach(0, array.length)(array(_) = new Buff[A])
    new SqCenArrBuff[A](array)
  }
}