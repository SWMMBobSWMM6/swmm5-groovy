#!/bin/bash
# build.sh - compile the Groovy port into swmm5.jar.
#   ./build.sh            then   ./runswmm <input file> <report file> [output file]
# The compiler: Groovy 3.0.24's FileSystemCompiler from the jars Gradle ships (GROOVY_LIB, default /opt/gradle/lib:
# groovy-3.0.24.jar, with the ASM and ANTLR jars beside it), run with the JDK's java; every class is @CompileStatic,
# so the jar needs only groovy-3.0.24.jar at run time (runswmm puts it on the class path). Needs a JDK 17 or newer.
# Written by Claude (Anthropic) for Robert Dickinson; see README.md.
set -e
cd "$(dirname "$0")"
L=${GROOVY_LIB:-/opt/gradle/lib}
GJ=$(ls "$L"/groovy-3.*.jar | grep -E '/groovy-3\.[0-9.]+\.jar$' | sort -V | tail -1)
CP=$GJ:$(ls "$L"/asm-[0-9]*.jar | head -1):$(ls "$L"/asm-commons-*.jar | head -1):$(ls "$L"/asm-tree-*.jar | head -1)
CP=$CP:$(ls "$L"/antlr4-runtime-*.jar | head -1)
rm -rf classes && mkdir classes
env -u JAVA_TOOL_OPTIONS java -Xmx2g -Xss64m -cp "$CP" org.codehaus.groovy.tools.FileSystemCompiler -d classes src/swmm5/*.groovy
jar cfe swmm5.jar swmm5.Main -C classes .
rm -rf classes
echo "built $(pwd)/swmm5.jar with $(basename "$GJ")"
