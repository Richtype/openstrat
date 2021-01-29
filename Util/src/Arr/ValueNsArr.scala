/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A class that can be construct from a fixed number of homogeneous primitive values such as Ints, Doubles or Longs. The final class can be stored as
 *  an Array of primitive values. */
trait ValueNElem extends Any with SpecialT

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ValueNsArr[A] extends Any with ArrImut[A]
{ type ThisT <: ValueNsArr[A]

  /** The number of atomic values, Ints, Doubles, Longs etc that specify / construct an element of this immutable flat Array based collection
   *  class. */
  def elemvaluesNum: Int

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def arrLen: Int


  final def elemsLen: Int = arrLen / elemvaluesNum

  def pMap[B, N <: ValueNsArr[B]](f: A => B)(implicit factory: Int => N): N =
  { val res = factory(elemsLen)
    var count: Int = 0
    while (count < elemsLen) {
      val newValue: B = f(apply(count))
      res.unsafeSetElem(count, newValue)
      count += 1
    }
    res
  }

  /** Appends ProductValue collection with the same type of Elements to a new ValueProduct collection. Note the operand collection can have a different
   * type, although it shares the same element type. In such a case, the returned collection will have the type of the operand not this collection. */
  def ++[N <: ValueNsArr[A]](operand: N)(implicit factory: Int => N): N =
  { val res = factory(elemsLen + operand.elemsLen)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    operand.iForeach((elem, i) => res.unsafeSetElem(i + elemsLen, elem))
    res
  }

  /** Appends an element to a new ProductValue collection of type N with the same type of Elements. */
  def :+[N <: ValueNsArr[A]](operand: A)(implicit factory: Int => N): N =
  { val res = factory(elemsLen + 1)
    iForeach((elem, i) => res.unsafeSetElem(i, elem))
    res.unsafeSetElem(elemsLen, operand)
    res
  }

  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ValueNsArr]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrTFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ValueNsArrBuilders[B, ArrB <: ValueNsArr[B]] extends ArrTBuilder[B, ArrB] with ArrTFlatBuilder[ArrB]
{ def elemSize: Int
}

/** Specialised flat arraybuffer based collection class, where the underlying ArrayBuffer element is an atomic value like [[Int]], [[Double]] or
 *  [[Long]]. */
trait ValueNsBuffer[A] extends Any with ArrayLike[A]
{ type ArrT <: ValueNsArr[A]
  def elemSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  def toArr(implicit build: ArrTBuilder[A, ArrT]): ArrT = ???
}

/** Class to Persist specialised flat Array[Value] type based collections. */
abstract class ValueNsArrPersist[A, M](val typeStr: String) extends PersistCompound[M]
{ /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: Buff[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: Buff[VT]): M
  def newBuffer: Buff[VT]
}