#!/usr/bin/env bash

# By using this script we would no longer need the JBookTrader.sh one
# and we could remove the lib/ directory entirely but I'm leaving it
# as is for now.

cd $(dirname "$0")/..

if [ -z "$JAVA_HOME" ]; then
    #TODO: update to use java8
    export JAVA_HOME=/usr/lib/jvm/java-6-sun
    export PATH=$JAVA_HOME/bin:$PATH
    export LD_LIBRARY_PATH=$JAVA_HOME/lib:$LD_LIBRARY_PATH
fi
echo JAVA_HOME: "$JAVA_HOME"

java -version

#fix for Ubuntu Hardy
export LIBXCB_ALLOW_SLOPPY_LOCK=1

#JVM_OPTS="-XX:+AggressiveHeap"
[ -x "$(which uname)" ] && [ "$(uname -m)" == "x86_64" ] && JVM_OPTS="$JVM_OPTS -d64"
JVM_OPTS="$JVM_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

echo "JVM_OPTS: $JVM_OPTS"

java ${JVM_OPTS} -jar jbooktrader/target/JBookTrader-9.0.2.0-SNAPSHOT.jar "$(pwd)" "$@"