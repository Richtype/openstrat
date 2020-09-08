// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._, publish._

trait Common extends ScalaModule
{ def version = "0.2.1snap"
def scalaVersion = "2.13.3"
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint")
}

trait CommonJvm extends Common
{ 
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm, millSourcePath / 'srcExs)

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.5")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)
    def resources = T.sources(millSourcePath / 'res)
  }
}

trait CommonJs extends ScalaJSModule with Common
{ def scalaJSVersion = "1.1.1"
  //def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
  def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs1.0:1.0.0")
}

object MacrosJvm extends CommonJvm// with PublishModule
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
  def sources = T.sources(Util.millSourcePath / 'Macros / 'src)
}

object MacrosJs extends CommonJs

object Util extends CommonJvm
{
  def moduleDeps = Seq(MacrosJvm)
  object test extends InnerTests  
}

object js extends CommonJs
{ def moduleDeps = Seq(MacrosJs)
}

/*object Graphics extends PlatformsModule
{ def moduleDeps = Seq(Util)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Util.js)  }  

  //def ivyDeps = Agg(ivy"org.openjfx:javafx:13.0.2")
}

object Tiling extends PlatformsModule
{ def moduleDeps = Seq(Graphics)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Graphics.js)  }
  object Nat extends InnerNative

  object examples extends InnerLearn
  { def moduleDeps = Seq(Tiling)
  }
}

object World extends PlatformsModule
{ def moduleDeps = Seq(Tiling)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(Tiling.js) }
  object Nat extends InnerNative
}

object Dev extends PlatformsModule
{ def moduleDeps = Seq(World)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm , Graphics.millSourcePath / 'learn / 'src, World.millSourcePath / 'learn / 'src)
  def resources = T.sources(millSourcePath / 'User)


  object js extends InnerJs
  { def moduleDeps = Seq(World.js)
    def sources = T.sources(millSourcePath / 'src, Dev.millSourcePath / 'srcLearn)
  } 
}

def run() = Dev.runBackground()
def test = Util.test
def jsfast = Dev.js.fastOpt
def jsfull = Dev.js.fullOpt*/