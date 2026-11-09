#!/bin/sh
# run_crtcheck.sh - check the Groovy runtime's printf, strtod and sscanf against the system glibc: tools/crt/test/mkfmt.c
# writes about 1.4 million reference cases from glibc, test/CrtCheck.groovy replays them through src/swmm5/R.groovy
# (with the JVM flags runswmm uses) and counts the differences. The vectors go to a temporary folder and are deleted.
# Written by Claude (Anthropic) for Robert Dickinson; see ../README.md.
set -e
cd "$(dirname "$0")/.."
T=$(mktemp -d "${TMPDIR:-/tmp}/gvcrt.XXXXXX")
gcc -O2 -o "$T/mkfmt" ../tools/crt/test/mkfmt.c -lm
"$T/mkfmt" > "$T/fmt.txt"
L=${GROOVY_LIB:-/opt/gradle/lib}
GJ=$(ls "$L"/groovy-3.*.jar | grep -E '/groovy-3\.[0-9.]+\.jar$' | sort -V | tail -1)
CP=$GJ:$(ls "$L"/asm-[0-9]*.jar | head -1):$(ls "$L"/asm-commons-*.jar | head -1):$(ls "$L"/asm-tree-*.jar | head -1)
CP=$CP:$(ls "$L"/antlr4-runtime-*.jar | head -1)
mkdir -p "$T/classes"
env -u JAVA_TOOL_OPTIONS java -Xmx1g -cp "$CP" org.codehaus.groovy.tools.FileSystemCompiler -d "$T/classes" test/CrtCheck.groovy \
    src/swmm5/R.groovy src/swmm5/Refs.groovy src/swmm5/Glibm.groovy src/swmm5/GlibmRt.groovy
env -u JAVA_TOOL_OPTIONS java -XX:-DontCompileHugeMethods -XX:+UseParallelGC -cp "$T/classes:$GJ" swmm5.CrtCheck "$T/fmt.txt"
rm -rf "$T"
