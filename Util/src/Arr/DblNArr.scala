/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

trait ArrayDblBased extends Any
{ def arrayUnsafe: Array[Double]
}

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait DblNArr[A] extends Any with ValueNArr[A] with ArrayDblBased
{ type ThisT <: DblNArr[A]

  def unsafeFromArray(array: Array[Double]): ThisT
  final override def unsafeNew(length: Int): ThisT = unsafeFromArray(new Array[Double](length * productSize))
  def unsafeCopyFromArray(opArray: Array[Double], offset: Int = 0): Unit = { opArray.copyToArray(arrayUnsafe, offset * productSize); () }
  def arrLen = arrayUnsafe.length

  def foreachArr(f: Dbls => Unit): Unit

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * productSize)
    arrayUnsafe.copyToArray(acc)
    acc
  }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[DblNArr]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrTFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait DblNsArrBuilders[B, ArrB <: DblNArr[B]] extends ValueNsArrBuilders[B, ArrB]
{ type BuffT <: DblNsBuffer[B]
  def fromDblArray(array: Array[Double]): ArrB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemSize))
  final override def newArr(length: Int): ArrB = fromDblArray(new Array[Double](length * elemSize))
  final override def buffToArr(buff: BuffT): ArrB = fromDblArray(buff.buffer.toArray)
  override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { buff.buffer.addAll(arr.arrayUnsafe); () }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait DblNsBuffer[A] extends Any with ValueNsBuffer[A]
{ type ArrT <: DblNArr[A]
  def buffer: ArrayBuffer[Double]

  def elemsLen: Int = buffer.length / elemSize
  def toArray: Array[Double] = buffer.toArray[Double]
  def grow(newElem: A): Unit
  override def grows(newElems: ArrT): Unit = { buffer.addAll(newElems.arrayUnsafe); () }
}

trait ProdDblNsCompanion[T,  ST <: DblNArr[T]]
{ def prodLen: Int
  implicit val persistImplicit: DblNsArrPersist[T, ST]
  implicit val factory: Int => ST = len => persistImplicit.fromArray(new Array[Double](len * prodLen))
}

/** Persists and assists in building ArrProdDblN */
abstract class DblNsArrPersist[A, M <: DblNArr[A]](typeStr: String) extends ValueNsArrPersist[A, M](typeStr) with EqT[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  override def eqv(m1: M, m2: M): Boolean = m1.arrayUnsafe equ m2.arrayUnsafe
}