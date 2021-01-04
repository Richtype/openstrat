/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.immutable.ArraySeq

/** A type class for string, text and visual represention of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. */
trait ShowT[-T]
{ /** The RSON type of T. */
  def typeStr: String

  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: T): String

  def showT(obj: T, way: Show.Way, decimalPlaces: Int): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepth: Int

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is 2. if it is 3 or greater return the standard Show compound Show string of the type name followed by a
   *  parenthesis block. */
  def showComma(obj: T): String

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. This method will rarely be used, as it is only applicable when the object is being shown stand alone and
   *  not as part of a containing object. So generally the full show method string will be desired. It may have uses for on the fly aggregation of
   *  strings.
   *
   *  This class may cease to be abstract. */
  def showSemi(obj: T): String
 }

/* The companion object for the Show type class. Persist extends Show with UnShow. As its very unlikley that anyone would want to create an UnShow
   instance without a Show instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object ShowT //extends ShowInstancesPriority2
{
  implicit val intPersistImplicit: Persist[Int] = new PersistSimple[Int]("Int")
  {
    def strT(obj: Int): String = obj.toString

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case DecimalToken(_, i) => Good(i.toInt)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "+" => Good(i.toInt)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "-" => Good(-i.toInt)
      case _ => expr.exprParseErr[Int]
    }
  }

  implicit val doublePersistImplicit: Persist[Double] = new PersistPrecisionSimple[Double]("DFloat")
  {
    def strT(obj: Double): String = obj.toString

    override def showT(obj: Double, way: Show.Way, decimalPlaces: Int): String = {
      val inner = decimalPlaces match {
      case 0 => f"$obj%1.0f"
      case 1 => f"$obj%1.1f"
      case 2 => f"$obj%1.2f"
      case 3 => f"$obj%1.3f"
      case _ => obj.toString
    }
      way match {
        case Show.Typed => typeStr + inner.enParenth
        case _ => inner
      }
    }

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case DecimalToken(_, i) => Good(i.toDouble)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "+" => Good(i.toDouble)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "-" => Good(-(i.toDouble))
      /* case FloatToken(_, _, d) => Good(d)
       case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "+" => Good(d)
       case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "-" => Good(-d)
     */  case  _ => expr.exprParseErr[Double]
    }
  }

  implicit val longPersistImplicit: Persist[Long] = new PersistSimple[Long]("Long")
  { def strT(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case DecimalToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val floatPersistImplicit: Persist[Float] = new PersistSimple[Float]("SFloat")
  { def strT(obj: Float): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Float] = expr match
    { case DecimalToken(_, i) => Good(i.toFloat)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
      /*  case FloatToken(_, _, d) => Good(d.toFloat)
        case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "+" => Good(d.toFloat)
        case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "-" => Good(-d.toFloat)
       */ case  _ => expr.exprParseErr[Float]
    }
  }

  implicit val BooleanPersistImplicit: Persist[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def strT(obj: Boolean): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentifierLowerOnlyToken(_, str) if str == "true" => Good(true)
      case IdentifierLowerOnlyToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val stringPersistImplicit: Persist[String] = new PersistSimple[String]("Str")
  { def strT(obj: String): String = obj.enquote
    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val charPersistImplicit: Persist[Char] = new PersistSimple[Char]("Char")
  { def strT(obj: Char): String = obj.toString.enquote1
    override def fromExpr(expr: Expr): EMon[Char] = expr match
    { case CharToken(_, char) => Good(char)
      case  _ => expr.exprParseErr[Char]
    }
  }

  class ShowIterableClass[A, R <: Iterable[A]](val evA: ShowT[A]) extends ShowIterable[A, R]{}

  implicit def ShowIterableImplicit[A](implicit evA: ShowT[A]): ShowT[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: ShowT[A]): ShowT[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: ShowT[A]): ShowT[List[A]] = new ShowIterableClass[A, List[A]](ev)

  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  //implicit def consPersistImplicit[A](implicit ev: Persist[A]): Persist[::[A]] = new PersistConsImplicit[A](ev)

  //implicit def nilPersistImplicit[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)

  implicit def vectorImplicit[A](implicit ev: ShowT[A]): ShowT[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val ArrayIntPersistImplicit: Persist[Array[Int]] = new PersistSeqLike[Int, Array[Int]](ShowT.intPersistImplicit)
  {
    override def showSemi(thisArray: Array[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map((obj: Int) => evA.strT(obj)).commaFold

    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(IdentifierUpperToken(_, "Seq"), Arr2(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => ???
      //sts.eMap[Int](_.errGet[Int](evA)).map(_.array)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }

    override def showT(obj: Array[Int], way: Show.Way, decimalPlaces: Int): String = ???
  }

  class ArrRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, ArraySeq[A]](ev)
  {
    override def showSemi(thisArr: ArraySeq[A]): String = thisArr.map(ev.showComma(_)).semiFold
    override def showComma(thisArr: ArraySeq[A]): String = thisArr.map((obj: A) => ev.strT(obj)).commaFold

    override def fromExpr(expr: ParseExpr): EMon[ArraySeq[A]] =  expr match
    { case AlphaBracketExpr(IdentifierUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentifierUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showT(obj: ArraySeq[A], way: Show.Way, decimalPlaces: Int): String = ???
  }

  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev)
  class ArrayRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Array[A]](ev)
  {
    override def showSemi(thisArray: Array[A]): String = thisArray.map(ev.showComma(_)).semiFold
    override def showComma(thisArray: Array[A]): String = thisArray.map((obj: A) => ev.strT(obj)).commaFold

    override def fromExpr(expr: ParseExpr): EMon[Array[A]] =  expr match
    {
      case AlphaBracketExpr(IdentifierLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentifierLowerToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showT(obj: Array[A], way: Show.Way, decimalPlaces: Int): String = ???
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: ShowT[A]): ShowT[collection.immutable.ArraySeq[A]] = new ShowSeqLike[A, ArraySeq[A]]
  { override def evA: ShowT[A] = ev
    override def showSemi(thisArr: ArraySeq[A]): String = thisArr.map(ev.showComma(_)).semiFold
    override def showComma(thisArr: ArraySeq[A]): String = thisArr.map((obj: A) => ev.strT(obj)).commaFold

    /** Not fully correct yet. */
    override def showT(obj: ArraySeq[A], way: Show.Way, decimalPlaces: Int): String =
      obj.map(el => ev.showT(el, Show.Standard, decimalPlaces)).semiFold
  }

  implicit def somePersistImplicit[A](implicit ev: Persist[A]): Persist[Some[A]] = new Persist[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def strT(obj: Some[A]): String = ev.strT(obj.value)
    override def showSemi(obj: Some[A]) = ev.showSemi(obj.value)
    override def showComma(obj: Some[A]) = ev.showComma(obj.value)

    override def showT(obj: Some[A], way: Show.Way, decimalPlaces: Int): String = ???

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentifierUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit val nonePersistImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {
    override def strT(obj: None.type): String = ""

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentifierLowerOnlyToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }

  implicit def optionPersistImplicit[A](implicit evA: Persist[A]): Persist[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](somePersistImplicit[A](evA), nonePersistImplicit)
  { override def typeStr: String = "Option" + evA.typeStr.enSquare
      override def syntaxDepth: Int = evA.syntaxDepth
  }
}

sealed trait ShowInstancesPriority2
{
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqPersistImplicit[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
}