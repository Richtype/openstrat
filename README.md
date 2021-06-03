<html>
<head>
<link rel="stylesheet" type="text/css" href="Documentation.css">  
</head>
<body>
<h1>openstrat</h1>

<p>So this branch has been created for raising an issue on Scala.js. Scala.js is showing errors compiling under Scala 3.0.1-RC1, while the errors dont' show
    for the Jvm or for Scala.js under Scala 2.13.6.

   ```
  GraphicsJvm2/compile //no errors
  GraphicsJvm3/compile //no errors
  GraphicsJs2/compile  //no errors
  GraphicsJs3/compile  //Gives the following errors:

  
   Error: /Common/openstrat/Graphics/src/Trans/ProlignMatrix.scala:48:18 -------
[error] 48 |    (obj, offset) => obj.map(ev.prolignObj(_, offset))(new AnyBuild[A])[br]
[error]                     ^
[error]    bridge generated for member method prolignObj(obj: ostrat.Arr[A], offset: ostrat.geom.ProlignMatrix): ostrat.Arr[A] in anonymous class Object with ostrat.geom.Prolign {...}[br]
[error]    |which overrides method prolignObj(obj: A, prolignMatrix: ostrat.geom.ProlignMatrix): A in trait Prolign[br]
[error]    |clashes with definition of the member itself; both have erased type (obj: Object, offset: ostrat.geom.ProlignMatrix): Object."[br]
[error] -- Error: /Common/openstrat/Graphics/src/Trans/Slate.scala:28:84 ---------------[br]
[error] 28 |  implicit def arrImplicit[A](implicit ev: Slate[A]): Slate[Arr[A]] = (obj, dx, dy) => obj.smap(ev.SlateXYT(_, dx, dy))
[error]    |                                                                                    ^
[error]    |bridge generated for member method SlateXYT(obj: ostrat.Arr[A], dx: Double, dy: Double): ostrat.Arr[A] in anonymous class Object with ostrat.geom.Slate {...}
[error]    |which overrides method SlateXYT(obj: T, xDelta: Double, yDelta: Double): T in trait Slate
[error]    |clashes with definition of the member itself; both have erased type (obj: Object, dx: Double, dy: Double): Object."
[warn] one warning found
[error] two errors found
 ```

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
![Scala CI](https://github.com/Rich2/openstrat/workflows/Scala%20CI/badge.svg)
![Sbt Tests](https://github.com/Rich2/openstrat/workflows/Sbt%20Tests/badge.svg)
![Docs](https://github.com/Rich2/openstrat/workflows/Docs/badge.svg)
[![Actions Panel](https://img.shields.io/badge/actionspanel-enabled-brightgreen)](https://www.actionspanel.app/app/w0d/openstrat)
<h3>Full documentation at <a href="https://richstrat.com/">https://richstrat.com/</a></h3>
<h3>Scala Docs for Jvm at <a href="https://richstrat.com/api/ostrat/index.html">https://richstrat.com/api/ostrat/index.html</a></h3>
<h3>Scala Docs for Js <a href="https://richstrat.com/apiJs/ostrat/index.html">https://richstrat.com/apiJs/ostrat/index.html</a></h3>

<h3>A functional Geometry and Vector Graphics library</h3>

<h3>A functional strategy game and historical education library, particularly focused on simultaneous-turn, tile-based games.</h3>

<h3>RSON A Show, 2D-Show and Persistence Library / Framework</h3>

<h3> Heapless Compound Value type collections library</h3>

<p>Scala currently set to 3.0.0 amd 2.13.6. Jdk 11+, 11 prefered. Scala.Js set to 1.5.1. Sbt currently set to 1.5.3, Sbt will not work running on
 Windows in Git Bash. Update your Mill to 0.9.7. Mill couldn't run on JavaFx, but this should be corrected with 0.9.7.</p>

</body>
</html>
