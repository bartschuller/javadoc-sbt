# javadoc sbt

Generate java api docs with javadoc.

## Installation

Use sbt 0.11.0 on this project and type `publish-local`.

In your own project, create or edit `project/plugins.sbt` and add

    addSbtPlugin("org.smop" % "javadoc-sbt" % "0.1.0-SNAPSHOT")

In your `build.sbt` add

    seq(javadoc.JavadocPlugin.javadocSettings: _*)

## Usage

Use the new `javadoc` task to generate api documentation for your java
source code (as opposed to scala code, for which you can use the normal
`doc` task).


Bart Schuller.
