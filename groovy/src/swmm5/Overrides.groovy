// Overrides.groovy - hand-ported pieces of EPA SWMM 5.2.4 that use C pointer arithmetic the translator does not
// map: odesolve.c (whole file), mempool.c (its pool becomes plain byte buffers) and six helpers from swmm5.c and
// project.c. Each keeps the C statement order and floating-point expressions exactly; pow is glibc's (Glibm). The
// Java port's Overrides.java in Groovy (characters as codes, double literals with d, public fields, and every > or
// >= on doubles through R.dgt / R.dge: Groovy 3 compiles them with dcmpg, true for NaN).
// Written by Claude (Anthropic) for Robert Dickinson; see ../../README.md.
package swmm5

import groovy.transform.CompileStatic

interface OdeDerivs { void call(double x, double[] y, double[] dydx) }

@CompileStatic
final class Overrides {
    private Overrides() {}
    public static final int MAXFNAME_ = 259

    static long sstrncpy(CPtr dest, CPtr src, long n) {
        int offset = 0
        if (n > 0L) {
            while (src.g(offset) != 0) {
                if (offset == n) break
                dest.s(offset, src.g(offset))
                offset++
            }
        }
        dest.s(offset, 0)
        return R.strlen(dest)
    }

    static long sstrcat(CPtr dest, CPtr src, long size) {
        int offset = R.strlenI(dest)
        int srcIndex = 0
        while (src.g(srcIndex) != 0) {
            dest.s(offset, src.g(srcIndex))
            offset++
            srcIndex++
            if (offset == size - 1L) break
        }
        dest.s(offset, 0)
        return R.strlen(dest)
    }

    private static boolean isRelative(CPtr fname) {
        if (R.strchr(fname, 58) != null) return false      // ':'
        if (fname.g(0) == 92) return false                   // '\\'
        if (fname.g(0) == 47) return false                   // '/'
        return true
    }

    static void getAbsolutePath(CPtr fname, CPtr absPath, long size) {
        if (fname == null || R.strlen(fname) == 0L) return
        if (isRelative(fname)) {
            String full = R.realpathOf(R.strOf(fname))        // realpath(fname, absPath)
            int m = Math.min(full.length(), absPath.b.length - absPath.o - 1)
            for (int i = 0; i < m; i++) absPath.s(i, (int) full.charAt(i))
            absPath.s(m, 0)
        } else {
            sstrncpy(absPath, fname, R.strlen(fname))
        }
        CPtr endOfDir = R.strrchr(absPath, 47)
        if (endOfDir != null) endOfDir.s(1, 0)
    }

    static CPtr getTempFileName(CPtr fname) {
        sstrncpy(fname, R.lit('swmmXXXXXX'), (long) MAXFNAME_)
        R.mkstemp(fname)
        return fname
    }

    static double[][] project_createMatrix(int nrows, int ncols) {
        if (nrows <= 0 || ncols <= 0) return null
        return new double[nrows][ncols]
    }

    static void project_freeMatrix(double[][] a) {}

    static Object AllocInit() { return new Object() }
    static CPtr Alloc(long n) { return R.buf(n) }
    static Object AllocSetPool(Object p) { return p }
    static void AllocReset() {}
    static void AllocFreePool() {}

    // ------------------------------------------------------------ odesolve.c (Cash-Karp Runge-Kutta)
    public static final int MAXSTP = 10000
    public static final double TINY = 1.0e-30d, SAFETY = 0.9d, PGROW = -0.2d, PSHRNK = -0.25d, ERRCON = 1.89e-4d
    private static int nmax = 0
    private static double[] y, yscal, dydx, yerr, ytemp, ak2, ak3, ak4, ak5, ak6

    static int odesolve_open(int n) {
        nmax = 0
        y = new double[n]; yscal = new double[n]; dydx = new double[n]; yerr = new double[n]; ytemp = new double[n]
        ak2 = new double[n]; ak3 = new double[n]; ak4 = new double[n]; ak5 = new double[n]; ak6 = new double[n]
        nmax = n
        return 1
    }

    static void odesolve_close() { nmax = 0 }

    static int odesolve_integrate(double[] ystart, int n, double x1, double x2, double eps, double h1, OdeDerivs derivs) {
        double[] hdid = new double[1], hnext = new double[1], x = new double[1]
        x[0] = x1
        double h = h1
        if (nmax < n) return 1
        for (int i = 0; i < n; i++) y[i] = ystart[i]
        for (int nstp = 1; nstp <= MAXSTP; nstp++) {
            derivs.call(x[0], y, dydx)
            for (int i = 0; i < n; i++) yscal[i] = Math.abs(y[i]) + Math.abs(dydx[i] * h) + TINY
            if (R.dgt((x[0] + h - x2) * (x[0] + h - x1), 0.0d)) h = x2 - x[0]
            int errcode = rkqs(x, n, h, eps, hdid, hnext, derivs)
            if (errcode != 0) break
            if (R.dge((x[0] - x2) * (x2 - x1), 0.0d)) {
                for (int i = 0; i < n; i++) ystart[i] = y[i]
                return 0
            }
            if (Math.abs(hnext[0]) <= 0.0d) return 2
            h = hnext[0]
        }
        return 3
    }

    private static int rkqs(double[] x, int n, double htry, double eps, double[] hdid, double[] hnext, OdeDerivs derivs) {
        double xold = x[0]
        double h = htry
        for (;;) {
            rkck(xold, n, h, derivs)
            double errmax = 0.0d
            for (int i = 0; i < n; i++) {
                double err = Math.abs(yerr[i] / yscal[i])
                if (R.dgt(err, errmax)) errmax = err
            }
            errmax /= eps
            if (R.dgt(errmax, 1.0d)) {
                double htemp = SAFETY * h * Glibm.pow(errmax, PSHRNK)
                if (R.dge(h, 0.0d)) h = R.dgt(htemp, 0.1d * h) ? htemp : 0.1d * h
                else h = htemp < 0.1d * h ? htemp : 0.1d * h
                double xnew = xold + h
                if (xnew == xold) return 2
                continue
            } else {
                if (R.dgt(errmax, ERRCON)) hnext[0] = SAFETY * h * Glibm.pow(errmax, PGROW)
                else hnext[0] = 5.0d * h
                hdid[0] = h
                x[0] += h
                for (int i = 0; i < n; i++) y[i] = ytemp[i]
                return 0
            }
        }
    }

    private static void rkck(double x, int n, double h, OdeDerivs derivs) {
        final double a2 = 0.2d, a3 = 0.3d, a4 = 0.6d, a5 = 1.0d, a6 = 0.875d
        final double b21 = 0.2d, b31 = 3.0d / 40.0d, b32 = 9.0d / 40.0d, b41 = 0.3d, b42 = -0.9d, b43 = 1.2d
        final double b51 = -11.0d / 54.0d, b52 = 2.5d, b53 = -70.0d / 27.0d, b54 = 35.0d / 27.0d
        final double b61 = 1631.0d / 55296.0d, b62 = 175.0d / 512.0d, b63 = 575.0d / 13824.0d
        final double b64 = 44275.0d / 110592.0d, b65 = 253.0d / 4096.0d, c1 = 37.0d / 378.0d
        final double c3 = 250.0d / 621.0d, c4 = 125.0d / 594.0d, c6 = 512.0d / 1771.0d
        final double dc5 = -277.0d / 14336.0d
        final double dc1 = c1 - 2825.0d / 27648.0d, dc3 = c3 - 18575.0d / 48384.0d
        final double dc4 = c4 - 13525.0d / 55296.0d, dc6 = c6 - 0.25d
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + b21 * h * dydx[i]
        derivs.call(x + a2 * h, ytemp, ak2)
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + h * (b31 * dydx[i] + b32 * ak2[i])
        derivs.call(x + a3 * h, ytemp, ak3)
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + h * (b41 * dydx[i] + b42 * ak2[i] + b43 * ak3[i])
        derivs.call(x + a4 * h, ytemp, ak4)
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + h * (b51 * dydx[i] + b52 * ak2[i] + b53 * ak3[i] + b54 * ak4[i])
        derivs.call(x + a5 * h, ytemp, ak5)
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + h * (b61 * dydx[i] + b62 * ak2[i] + b63 * ak3[i] + b64 * ak4[i] + b65 * ak5[i])
        derivs.call(x + a6 * h, ytemp, ak6)
        for (int i = 0; i < n; i++) ytemp[i] = y[i] + h * (c1 * dydx[i] + c3 * ak3[i] + c4 * ak4[i] + c6 * ak6[i])
        for (int i = 0; i < n; i++) yerr[i] = h * (dc1 * dydx[i] + dc3 * ak3[i] + dc4 * ak4[i] + dc5 * ak5[i] + dc6 * ak6[i])
    }
}
