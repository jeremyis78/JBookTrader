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

CLASSES=$(pwd)/jbooktrader/target/classes
if [ ! -d "$CLASSES" ]; then
  echo "$CLASSES doesn't exist. Did you run 'mvn clean compile'?"
  exit 1
fi
TWS_CLASSES=$(pwd)/tws-api/target/classes
if [ ! -d "$TWS_CLASSES" ]; then
  echo "$TWS_CLASSES doesn't exist. Did you run 'mvn clean compile'?"
  exit 1
fi
CLASSES=${CLASSES}:${TWS_CLASSES}

#JVM_OPTS="-XX:+AggressiveHeap"
[ -x "$(which uname)" ] && [ "$(uname -m)" == "x86_64" ] && JVM_OPTS="$JVM_OPTS -d64"
JVM_OPTS="$JVM_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

echo java -cp "$CLASSES:$CLASSPATH:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
exec java -cp "$CLASSES:$CLASSPATH:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
