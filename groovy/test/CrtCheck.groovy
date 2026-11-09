// CrtCheck.groovy - compare the Groovy port's printf, strtod and sscanf (src/swmm5/R.groovy) with the system glibc's
// results written by tools/crt/test/mkfmt.c (lines KIND \t FORMAT \t ARG \t RESULT, hex-encoded), as the PHP and Ruby
// ports' crtcheck scripts do. Written by Claude (Anthropic) for Robert Dickinson; see ../README.md.
package swmm5

import groovy.transform.CompileStatic
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

@CompileStatic
final class CrtCheck {
  static byte[] unhex(String h) {
    byte[] b = new byte[R.idiv(h.length(), 2)]
    for (int i = 0; i < b.length; i++) b[i] = (byte) Integer.parseInt(h.substring(2 * i, 2 * i + 2), 16)
    return b
  }
  static CPtr cstr(byte[] b) { byte[] z = new byte[b.length + 1]; System.arraycopy(b, 0, z, 0, b.length); return new CPtr(z, 0) }
  static String hexOf(CPtr p) {
    StringBuilder s = new StringBuilder()
    int n = R.strlenI(p)
    for (int i = 0; i < n; i++) s.append(String.format('%02x', p.b[p.o + i] & 255))
    return s.toString()
  }
  static String bits(double v) { return String.format('%016x', Double.doubleToRawLongBits(v)) }

  static void main(String[] a) {
    String[] names = ['printf %f', 'printf %d', 'printf %ld', 'printf %s', 'strtod', 'sscanf'] as String[]
    long[] total = new long[6], bad = new long[6]
    int shown = 0
    CPtr out = R.buf(1024)
    Map<String, CPtr> fcache = new HashMap<String, CPtr>()
    BufferedReader rd = Files.newBufferedReader(Paths.get(a[0]), StandardCharsets.ISO_8859_1)
    String line
    while ((line = rd.readLine()) != null) {
      if (line.isEmpty()) continue
      String[] p = line.split('\t', 4)
      String kind = p[0], fm = p[1], arg = p[2], want = p[3]
      CPtr f = fcache.get(fm)
      if (f == null) { f = R.lit(fm); fcache.put(fm, f) }
      int k
      String got
      if (kind == 'f') {
        k = 0
        double x = Double.longBitsToDouble(Long.parseUnsignedLong(arg, 16))
        if (fm == '%*.*f') R.snprintf(out, 1024, f, 9, 3, x) else R.snprintf(out, 1024, f, x)
        got = hexOf(out)
      } else if (kind == 'i') {
        k = 1
        R.snprintf(out, 1024, f, (int) Long.parseLong(arg, 16))
        got = hexOf(out)
      } else if (kind == 'l') {
        k = 2
        R.snprintf(out, 1024, f, Long.parseUnsignedLong(arg, 16))
        got = hexOf(out)
      } else if (kind == 's') {
        k = 3
        R.snprintf(out, 1024, f, cstr(unhex(arg)))
        got = hexOf(out)
      } else if (kind == 'd') {
        k = 4
        CPtr s = cstr(unhex(arg))
        OBox<CPtr> e = new OBox<CPtr>(null)
        double v = R.strtod(s, e)
        got = bits(v) + ':' + (e.v.o - s.o)
      } else if (kind == 'n') {
        k = 5
        CPtr s = cstr(unhex(arg))
        IBox i1 = new IBox(-77)
        DBox d1 = new DBox(-77.0d)
        CPtr b = R.buf(32)
        b.s(0, 63)
        int n = R.sscanf(s, R.lit('%d %lf %s'), i1, d1, b)
        got = n + ':' + i1.v + ':' + bits(d1.v) + ':' + hexOf(b)
      } else continue
      total[k]++
      if (got == want) continue
      bad[k]++
      if (shown < 25) { shown++; System.err.println('  ' + kind + ' ' + fm + ' arg=' + arg + '  glibc=' + want + '  groovy=' + got) }
    }
    long t = 0L, b = 0L
    for (int k = 0; k < 6; k++) { println(names[k] + ': ' + total[k] + ' cases, ' + bad[k] + ' differ'); t += total[k]; b += bad[k] }
    println(b + ' of ' + t + ' cases differ')
  }
}
