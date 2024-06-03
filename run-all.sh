#!/usr/bin/env bash
set -ex

rm -f .tool-versions #to use default java version on build
mvn clean package

rm -rf out*.txt #remove old reports output

JVM_LIST='semeru-openj9-8u332-b09_openj9-0.32.0 adoptopenjdk-8.0.412+8 adoptopenjdk-21.0.3+9.0.LTS'

for JVM in $JVM_LIST
do
  asdf install java "$JVM"
  asdf local java "$JVM"
  java -version
  java -jar target/benchmarks.jar -o "out_$JVM.txt"
done