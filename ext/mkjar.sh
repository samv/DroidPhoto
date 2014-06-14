#!/bin/bash

cd "$(dirname "$0")"

(cd android-smart-image-view; ant jar)
(cd commons-io; ant jar)
(cd commons-lang; ant jar)
(cd scribe-java; mvn source:jar)
(cd ../lib
 wget http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.4.0/jackson-databind-2.4.0.jar
 wget http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.4.0/jackson-core-2.4.0.jar
 wget http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.4.0/jackson-annotations-2.4.0.jar
 )

