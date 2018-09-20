name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.6"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/version := "0.0.1"

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value
)

val macrosSettings = List(Compile/scalaSource := (ThisBuild/baseDirectory).value / "Macros/src") ::: commonSettings

lazy val MacrosJvm = project.settings(macrosSettings)
lazy val MacrosJs = project.settings(macrosSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val coreSettings = List(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Core/src", 
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Core/resources"
) ::: commonSettings

lazy val CoreJvm = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Core/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.5" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(MacrosJvm, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(MacrosJvm, Compile, packageSrc).value
)
lazy val CoreJs = project.dependsOn(MacrosJs).settings(coreSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

lazy val FxStrat = project.dependsOn(CoreJvm).settings(commonSettings).settings(  
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
  libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
  Compile/mainClass := Some("ostrat.pFx.DevApp"),
  mappings in (Compile, packageBin) ++= mappings.in(MacrosJvm, Compile, packageBin).value,
  mappings in (Compile, packageBin) ++= mappings.in(CoreJvm, Compile, packageBin).value,
  mappings in (Compile, packageSrc) ++= mappings.in(MacrosJvm, Compile, packageSrc).value,
  mappings in (Compile, packageSrc) ++= mappings.in(CoreJvm, Compile, packageSrc).value,
  //artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) => "ostrat" + artifact + ".jar" },
)

lazy val JsStrat = project.dependsOn(CoreJs).enablePlugins(ScalaJSPlugin).settings(commonSettings).settings(
  //scalaJSUseMainModuleInitializer := true,
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/src",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/srcPlay",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6",  
)

lazy val NatStrat = project.enablePlugins(ScalaNativePlugin).settings(
Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Macros/src",
//Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Core/src",		
Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "NatStrat/src",
scalaVersion := "2.11.12"
)

lazy val DocProj = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",  
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
)

lazy val LearnScala = project.settings(coreSettings).settings(
Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "LearnScala/src",
//Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "LearnScala/libs",
)
