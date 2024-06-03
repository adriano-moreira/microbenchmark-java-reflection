#!/usr/bin/env bash
set -e

JVM_LIST='adoptopenjdk-8.0.412+8 adoptopenjdk-21.0.3+9.0.LTS'

# validate required asdf-vm
command -v asdf > /dev/null || (
	echo "

	command asdf not found, please install asdf-vm
	    https://asdf-vm.com/

	"
	exit 1
)

rm -f .tool-versions #to use default java version on build
mvn clean package

rm -rf out*.txt #remove old reports output

for JVM in $JVM_LIST
do
  echo "Run benchmarks with $JVM"
  asdf install java "$JVM"
  asdf local java "$JVM"
  java -version
  java -jar target/benchmarks.jar -o "out_$JVM.txt"
done