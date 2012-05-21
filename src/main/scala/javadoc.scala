package javadoc

import sbt._
import Keys._

object JavadocPlugin extends Plugin {
  import JavadocKeys._

  object JavadocKeys {
    val javadoc = TaskKey[File]("javadoc", "Generate java api docs from java sources.")
    val javadocSubpackages = TaskKey[Seq[String]]("javadoc-subpackages", "Package names to recursively document.")
    val javadocOptions = TaskKey[Seq[String]]("javadoc-options", "Extra options for Javadoc.")
  }
  
  private def javadocTask(target: File, source: File, packages: Seq[String], options: Seq[String], log: Logger): File = {
    println("javadoccing like a boss.")
    println("Target dir: "+target)
    println("Source dir: "+source)
    println("Options: "+options.mkString(" "))
    val cmd = <x>javadoc -sourcepath {source} -d {target} -subpackages {packages.mkString(":")} {options.mkString(" ")}</x>
    println("Executing: "+cmd.text)
    cmd ! log
    target
  }

  val javadocSettings = Seq(
    javadocOptions <<= (fullClasspath in Compile) map { fcp => Seq(
        "-link", "http://download.oracle.com/javase/6/docs/api/"
      , "-classpath", fcp.map(_.data).map(_.getPath).mkString(java.io.File.pathSeparator)
    )},
    javadoc <<= (target in javadoc, javaSource in Compile, javadocSubpackages, javadocOptions, streams) map {
      (t, source, pkgs, opts, s) => javadocTask(t, source, pkgs, opts, s.log)
    },
    target in javadoc <<= (target) {
      (t) => t / "java" / "apidoc"
    },
    javadocSubpackages <<= (javaSource in Compile) map {
      src => src.list().toSeq
    }
  )
}
