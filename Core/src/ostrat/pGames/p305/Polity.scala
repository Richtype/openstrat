/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import Colour._

trait Polity extends StringerSingleton
{
   def colour: Colour
   def typeSym: Symbol = 'Polity
}

object Rome extends Polity
{
   def sym = 'Rome
   def colour = Red
}

 object Macedon extends Polity
{
   def sym = 'Macedon
   def colour = Blue
}
