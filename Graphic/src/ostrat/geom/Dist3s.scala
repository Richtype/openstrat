/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Collection class for Dist3s. Not clear if this a Polygon equivalent or a Vec3s equivalent */
class Dist3s(val arr: Array[Double]) extends AnyVal with ProductDouble3s[Dist3]
{ override def typeName: Symbol = 'Dist3s
  override def newElem(d1: Double, d2: Double, d3: Double): Dist3 = new Dist3(d1, d2, d3)
  /** This methods function is to work on a sequence of 3d points representing a polygon on the surface a globe (eg the Earth). If Z is positive its
   *  on the side of the Earth that the viewer is looking at. Returns z positive dist2 points if 1 or more of the points are z positive. Z negative
   *  points are moved to the horizon. */ 
  def earthZPositive: GlobedArea =
  {         
    filterCount(_.z.pos) match
    { case 0 => GlobedNone
      case n if n == length => GlobedAll(pMap(_.xy))
      case n => GlobedNone
//      {
//        var els: List[Either[Dist2, Dist2]] = lMap {
//          case el if el.z.pos => Right(el.xy)
//          case el =>
//          { val xy = el.xy
//            val fac = xy.magnitude / EarthAvRadius
//            Left(xy / fac)
//          }
//        }
//        while (els.head.isLeft && els.last.isLeft && els.init.last.isLeft) els = els.init
//      
//        val els2: List[Either[Dist2, Dist2]] = els.drop(2).foldLeft(els.take(2))((acc, el) => el match
//          {
//            case Left(v) if acc.last.isLeft && acc.init.last.isLeft => acc.init :+ el
//            case el => acc :+ el
//          })
//            
//        val acc: CurveSegDists = CurveSegDists.factory(els2.length)// List[CurveSegDist] = Nil
//        var last: Either[Dist2, Dist2] = els2.last
//        els2.iForeach {(e, i) =>
//          e match
//          { case Right(d2) => acc.setElem(i, LineSegDist(d2))
//            case Left(d2) if last.isLeft => acc.setElem(i, ArcSegDist(Dist2Z, d2))
//            case Left(d2) => acc.setElem(i, LineSegDist(d2))
//          }
//          last = e
//        }               
//        GlobedSome(acc)                
//      }
    }        
  }      
 } 

object Dist3s
{
   implicit val factory: Int => Dist3s = i => new Dist3s(new Array[Double](i * 3))
}
