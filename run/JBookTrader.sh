#!/bin/bash

cd $(dirname "$0")/..

if [ -z "$JAVA_HOME" ]; then
    #TODO: update to use java8
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
    CLASSPATH=${CLASSPATH}:${JAR}
done

declare -a module=("jbooktrader" "tws-api")
for MOD in "${module[@]}"
do
     CLASSES=$(pwd)/${MOD}/target/classes
     if [ ! -d "$CLASSES" ]; then
       echo "$CLASSES doesn't exist. Did you run 'mvn clean compile'?"
       exit 1
     fi
   CLASSPATH=${CLASSPATH}:${CLASSES}
done

echo "Classpath: $CLASSPATH"

#JVM_OPTS="-XX:+AggressiveHeap"
[ -x "$(which uname)" ] && [ "$(uname -m)" == "x86_64" ] && JVM_OPTS="$JVM_OPTS -d64"
JVM_OPTS="$JVM_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

echo "JVM_OPTS: $JVM_OPTS"

#Cleanup previous logs
rm -f reports/*

#echo java -cp "$CLASSPATH" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
exec java -cp "$CLASSPATH" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
