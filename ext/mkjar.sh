#!/bin/bash

cd "$(dirname "$0")"

(cd android-smart-image-view; ant jar)
(cd commons-io; ant jar)
(cd commons-lang; ant jar)
(cd scribe-java; mvn source:jar)

