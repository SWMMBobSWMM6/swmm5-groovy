// Generated from EPA SWMM 5.2.4 (forcmain.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_forcmain {
    public static double VISCOS = 1.1E-5d;

    // forcmain.c:37
    static double forcemain_getEquivN(int j, int k) {
        TXsect xsect = F_swmm5.Link[j].xsect.copy();
        double f = 0.0d;
        double d = xsect.yFull;
        int __sw1 = F_swmm5.ForceMainEqn;
        if (__sw1 == H_W) {
            return ((1.067d / xsect.rBot) * Glibm.pow((d / F_swmm5.Conduit[k].slope), 0.04d));
        } else if (__sw1 == D_W) {
            f = forcemain_getFricFactor(xsect.rBot, (d / 4.0d), 1.0e12d);
            return (Math.sqrt((f / 185.0d)) * Glibm.pow(d, ((1.0d / 6.0d))));
        }
        return F_swmm5.Conduit[k].roughness;
    }

    // forcmain.c:64
    static double forcemain_getRoughFactor(int j, double lengthFactor) {
        TXsect xsect = F_swmm5.Link[j].xsect.copy();
        double r = 0.0d;
        int __sw1 = F_swmm5.ForceMainEqn;
        if (__sw1 == H_W) {
            r = ((1.318d * xsect.rBot) * Glibm.pow(lengthFactor, 0.54d));
            return (32.2d / Glibm.pow(r, 1.852d));
        } else if (__sw1 == D_W) {
            return ((1.0d / 8.0d) / lengthFactor);
        }
        return 0.0d;
    }

    // forcmain.c:88
    static double forcemain_getFricSlope(int j, double v, double hrad) {
        double re = 0.0d;
        double f = 0.0d;
        TXsect xsect = F_swmm5.Link[j].xsect.copy();
        int __sw1 = F_swmm5.ForceMainEqn;
        if (__sw1 == H_W) {
            return ((xsect.sBot * Glibm.pow(v, 0.852d)) / Glibm.pow(hrad, 1.1667d));
        } else if (__sw1 == D_W) {
            re = forcemain_getReynolds(v, hrad);
            f = forcemain_getFricFactor(xsect.rBot, hrad, re);
            return (((f * xsect.sBot) * v) / hrad);
        }
        return 0.0d;
    }

    // forcmain.c:117
    static double forcemain_getReynolds(double v, double hrad) {
        return (((4.0d * hrad) * v) / VISCOS);
    }

    // forcmain.c:130
    static double forcemain_getFricFactor(double e, double hrad, double re) {
        double f = 0.0d;
        if ((re < 10.0d)) {
            re = 10.0d;
        }
        if ((re <= 2000.0d)) {
            f = (64.0d / re);
        } else if ((re < 4000.0d)) {
            f = forcemain_getFricFactor(e, hrad, 4000.0d);
            f = (0.032d + ((((f - 0.032d)) * ((re - 2000.0d))) / 2000.0d));
        } else {
            f = ((e / 3.7d) / ((4.0d * hrad)));
            if ((re < 1.0e10d)) {
                f += (5.74d / Glibm.pow(re, 0.9d));
            }
            f = Glibm.log10(f);
            f = ((0.25d / f) / f);
        }
        return f;
    }

}
