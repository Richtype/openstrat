/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pX11
import geom._, scalanative._, unsafe._, unsigned._

@link("X11") @extern object Xlib {
  def XOpenDisplay(display_name: CString): Ptr[CStruct2[CInt, CInt]] = extern
  def XCloseDisplay(disp: Display): Unit = extern
  def XDefaultScreen(disp: Display): CInt = extern
  def XRootWindow(disp: Display, screen_number: CInt): Window = extern
  def XBlackPixel(disp: Display, scren_number: CInt): CUnsignedLong = extern
  def XWhitePixel(disp: Display, scren_number: CInt): CUnsignedLong = extern
  def XCreateSimpleWindow(disp: Display, parent: Window, x: CInt, y: CInt, width: CUnsignedInt, height: CUnsignedInt, border_width: CUnsignedLong,
                          border: CUnsignedLong, background: CUnsignedLong): Window = extern
  def XSelectInput(disp: Display, window: Window, event_mask: CLong): Unit = extern
  def XMapWindow(disp: Display, window: Window): Unit = extern
  def XDefaultGC(disp: Display, screen_number: CInt): GC = extern
  def XFillRectangle(disp: Display, drawable: Window, gc: GC, x: CInt, y: CInt, width: CUnsignedInt, height: CUnsignedInt): Unit = extern
  def XNextEvent(disp: Display, eventPtr: EventPtr): Unit = extern
}

@extern object myapi {
  def add3(in: CLong): CLong = extern
}

 object Main
 {
   import Xlib._
   def main(args: Array[String]): Unit =
   {
   	 val i: Long = myapi.add3(-3L)
   	 println(i)
   	 val p1 = Pt2(5.6, 7)
     deb("Hello, native world!, p1 = " + p1.toString)
     val disp = XOpenDisplay(null)
     if(disp == null) println("Cannot open display")
     else {
       println("Display opened")
       val defualtScn = XDefaultScreen(disp)
       val rootWin = XRootWindow(disp, defualtScn)
       val wn = rootWin.toInt
       println(s"root window = $wn")
       val bp = XBlackPixel(disp, defualtScn)
       val wp = XWhitePixel(disp, defualtScn)
       val window = XCreateSimpleWindow(disp, rootWin, 10, 10, 200.toUInt, 100.toUInt, 1.toUInt, bp, wp)
       XSelectInput(disp, window, ExposureMask | KeyPressMask)
       XMapWindow(disp, window)
       val event = stackalloc[Event]
       var continue = true
       while(continue) {
         XNextEvent(disp, event.toPtr)
         event._1 match {
           case 12 =>
           { println("Expose")
             val gc = XDefaultGC(disp, defualtScn)
             XFillRectangle(disp, window, gc, 20, 20, 40.toUInt, 10.toUInt)
           }
           case 2 => {
             println("Key pressed")
             continue = false
           }
           case et => println(et.toString + " Unrecognised event.")
         }
       }
       XCloseDisplay(disp)
       println("Display closed")
     }
   }
}
