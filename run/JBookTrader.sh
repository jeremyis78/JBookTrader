#!/bin/bash

cd $(dirname "$0")/..
echo JAVA_HOME: "$JAVA_HOME"
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-6-sun
    export PATH=$JAVA_HOME/bin:$PATH
    export LD_LIBRARY_PATH=$JAVA_HOME/lib:$LD_LIBRARY_PATH
fi
echo JAVA_HOME: "$JAVA_HOME"
#svn -q up
java -version

#fix for Ubuntu Hardy
export LIBXCB_ALLOW_SLOPPY_LOCK=1

CLASSPATH=

for JAR in $(pwd)/lib/*.jar
do
    CLASSPATH=$JAR:$CLASSPATH
done

#echo "Classpath: $CLASSPATH"
rm -f reports/*

RESOURCES=$(pwd)/jbooktrader
CLASSES=$(pwd)/jbooktrader/target/classes
if [ ! -d "$CLASSES" ]; then
  echo "$CLASSES doesn't exist. Did you run 'mvn clean compile'?"
  exit 1
fi

#JVM_OPTS="-XX:+AggressiveHeap"
[ -x "$(which uname)" ] && [ "$(uname -m)" == "x86_64" ] && JVM_OPTS="$JVM_OPTS -d64"

echo java -cp "$CLASSES:$CLASSPATH:$RESOURCES:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
exec java -cp "$CLASSES:$CLASSPATH:$RESOURCES:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
