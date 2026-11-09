#!/bin/bash
# run_libm_tests.sh [N]
# Check the Groovy translation of glibc's math routines (src/swmm5/Glibm.groovy + GlibmRt.groovy) bit for bit against
# the system libm (the library EPA's runswmm calls), the way tools/libm/test/run_tests.sh checks the TypeScript, Java
# and Go ones: N random arguments per function over SWMM's ranges plus special values (tools/libm/test/mkvectors.c)
# and an edge-case set (mkedge.c). Every bit of each result is compared, NaN payloads and signs included. Run with the
# JVM flags groovy/runswmm uses. Deletes the vectors (about 260 MB at N = 1000000; GV_LIBM_WORK puts them elsewhere,
# e.g. in /dev/shm) when done. Written by Claude (Anthropic) for Robert Dickinson; see ../README.md.
set -e
HERE=$(cd "$(dirname "$0")" && pwd); G=$(cd "$HERE/.." && pwd); ROOT=$(cd "$G/.." && pwd)
N=${1:-1000000}; W=${GV_LIBM_WORK:-$ROOT/build/gv_libm_test}; rm -rf "$W"; mkdir -p "$W"
gcc -O2 -o "$W/mkvectors" "$ROOT/tools/libm/test/mkvectors.c" -lm
gcc -O2 -o "$W/mkedge" "$ROOT/tools/libm/test/mkedge.c" -lm
"$W/mkvectors" "$N" "$W/vectors.bin" > /dev/null
"$W/mkedge" 100000 "$W/edge.bin" > /dev/null
L=${GROOVY_LIB:-/opt/gradle/lib}
GJ=$(ls "$L"/groovy-3.*.jar | grep -E '/groovy-3\.[0-9.]+\.jar$' | sort -V | tail -1)
CP=$GJ:$(ls "$L"/asm-[0-9]*.jar | head -1):$(ls "$L"/asm-commons-*.jar | head -1):$(ls "$L"/asm-tree-*.jar | head -1)
CP=$CP:$(ls "$L"/antlr4-runtime-*.jar | head -1)
mkdir -p "$W/classes"
env -u JAVA_TOOL_OPTIONS java -Xmx1g -Xss64m -cp "$CP" org.codehaus.groovy.tools.FileSystemCompiler -d "$W/classes" \
    "$G/src/swmm5/Glibm.groovy" "$G/src/swmm5/GlibmRt.groovy" "$HERE/Check.groovy"
JV="-XX:-DontCompileHugeMethods -XX:+UseParallelGC"
echo "== Groovy $(basename "$GJ" .jar | sed 's/groovy-//') on $(java -version 2>&1 | grep -v Picked | sed -n 1p): vectors.bin (N = $N per function)"
env -u JAVA_TOOL_OPTIONS java $JV -cp "$W/classes:$GJ" swmm5.Check "$W/vectors.bin"
echo "== edge.bin"
env -u JAVA_TOOL_OPTIONS java $JV -cp "$W/classes:$GJ" swmm5.Check "$W/edge.bin"
rm -rf "$W"
