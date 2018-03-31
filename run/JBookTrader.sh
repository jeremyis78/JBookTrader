#!/bin/bash

cd $(dirname "$0")/..

if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-6-sun
    export PATH=$JAVA_HOME/bin:$PATH
    export LD_LIBRARY_PATH=$JAVA_HOME/lib:$LD_LIBRARY_PATH
fi

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

#rm -f reports/*
#mkdir -p bin
find bin           -name "*.class" | xargs rm -f
find src/main/java -name "*.java"  | xargs javac -cp "$CLASSPATH" -d bin

#JVM_OPTS="-XX:+AggressiveHeap"
[ -x "$(which uname)" ] && [ "$(uname -m)" == "x86_64" ] && JVM_OPTS="$JVM_OPTS -d64"

echo java -cp "$(pwd)/target/JBookTrader-0.0.1-SNAPSHOT.jar:$CLASSPATH:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
exec java -cp "$(pwd)/target/JBookTrader-0.0.1-SNAPSHOT.jar:$CLASSPATH:$(pwd)/lib" $JVM_OPTS com.jbooktrader.platform.startup.JBookTrader "$(pwd)" "$@"
