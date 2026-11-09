// Generated from EPA SWMM 5.2.4 (findroot.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_findroot {
    // findroot.c:20
    static int findroot_Newton(double x1, double x2, DRef rts, double xacc, FP0 func, Object p) {
        int j = 0;
        int n = 0;
        DBox df = new DBox(0.0d);
        double dx = 0.0d;
        double dxold = 0.0d;
        DBox f = new DBox(0.0d);
        double x = 0.0d;
        double temp = 0.0d;
        double xhi = 0.0d;
        double xlo = 0.0d;
        x = rts.get();
        xlo = x1;
        xhi = x2;
        dxold = Math.abs((x2 - x1));
        dx = dxold;
        func.call(x, f, df, p);
        n++;
        L_f1: for (j = 1; (j <= 60); j++) {
            if ((((R.dge(((((((x - xhi)) * df.v) - f.v)) * (((((x - xlo)) * df.v) - f.v))), 0.0d)) || (R.dgt(Math.abs((2.0d * f.v)), Math.abs((dxold * df.v))))))) {
                dxold = dx;
                dx = (0.5d * ((xhi - xlo)));
                x = (xlo + dx);
                if ((xlo == x)) {
                    break L_f1;
                }
            } else {
                dxold = dx;
                dx = (f.v / df.v);
                temp = x;
                x -= dx;
                if ((temp == x)) {
                    break L_f1;
                }
            }
            if ((Math.abs(dx) < xacc)) {
                break L_f1;
            }
            func.call(x, f, df, p);
            n++;
            if ((f.v < 0.0d)) {
                xlo = x;
            } else {
                xhi = x;
            }
        }
        rts.set(x);
        if ((n <= 60)) {
            return n;
        } else {
            return 0;
        }
        throw new IllegalStateException('findroot_Newton: no return');
    }

    // findroot.c:91
    static double findroot_Ridder(double x1, double x2, double xacc, FP1 func, Object p) {
        int j = 0;
        double ans = 0.0d;
        double fhi = 0.0d;
        double flo = 0.0d;
        double fm = 0.0d;
        double fnew = 0.0d;
        double s = 0.0d;
        double xhi = 0.0d;
        double xlo = 0.0d;
        double xm = 0.0d;
        double xnew = 0.0d;
        flo = func.call(x1, p);
        fhi = func.call(x2, p);
        if ((flo == 0.0d)) {
            return x1;
        }
        if ((fhi == 0.0d)) {
            return x2;
        }
        ans = (0.5d * ((x1 + x2)));
        if (((((R.dgt(flo, 0.0d)) && (fhi < 0.0d))) || (((flo < 0.0d) && (R.dgt(fhi, 0.0d)))))) {
            xlo = x1;
            xhi = x2;
            L_f1: for (j = 1; (j <= 60); j++) {
                xm = (0.5d * ((xlo + xhi)));
                fm = func.call(xm, p);
                s = Math.sqrt(((fm * fm) - (flo * fhi)));
                if ((s == 0.0d)) {
                    return ans;
                }
                xnew = (xm + (((xm - xlo)) * (((((R.dge(flo, fhi) ? 1.0d : (-1.0d))) * fm) / s))));
                if ((Math.abs((xnew - ans)) <= xacc)) {
                    break L_f1;
                }
                ans = xnew;
                fnew = func.call(ans, p);
                if ((((R.dge((fnew), 0.0d) ? Math.abs(fm) : (-(Math.abs(fm))))) != fm)) {
                    xlo = xm;
                    flo = fm;
                    xhi = ans;
                    fhi = fnew;
                } else if ((((R.dge((fnew), 0.0d) ? Math.abs(flo) : (-(Math.abs(flo))))) != flo)) {
                    xhi = ans;
                    fhi = fnew;
                } else if ((((R.dge((fnew), 0.0d) ? Math.abs(fhi) : (-(Math.abs(fhi))))) != fhi)) {
                    xlo = ans;
                    flo = fnew;
                } else {
                    return ans;
                }
                if ((Math.abs((xhi - xlo)) <= xacc)) {
                    return ans;
                }
            }
            return ans;
        }
        return (-1.0e20d);
    }

}
