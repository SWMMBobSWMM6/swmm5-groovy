// Generated from EPA SWMM 5.2.4 (kinwave.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_kinwave {
    public static double WX = 0.6d;
    public static double WT = 0.6d;
    public static double EPSIL = 0.001d;
    public static double Beta1 = 0.0d;
    public static double C1 = 0.0d;
    public static double C2 = 0.0d;
    public static double Afull = 0.0d;
    public static double Qfull = 0.0d;
    public static TXsect pXsect = null;

    // kinwave.c:57
    static int kinwave_execute(int j, DRef qinflow, DRef qoutflow, double tStep) {
        int k = 0;
        int result = 1;
        double dxdt = 0.0d;
        double dq = 0.0d;
        double ain = 0.0d;
        DBox aout = new DBox(0.0d);
        double qin = 0.0d;
        double qout = 0.0d;
        double a1 = 0.0d;
        double a2 = 0.0d;
        double q1 = 0.0d;
        double q2 = 0.0d;
        double q3 = 0.0d;
        qoutflow.set((qinflow.get()));
        if ((F_swmm5.Link[j].type != CONDUIT)) {
            return result;
        }
        if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
            return result;
        }
        pXsect = F_swmm5.Link[j].xsect;
        Qfull = F_swmm5.Link[j].qFull;
        Afull = F_swmm5.Link[j].xsect.aFull;
        k = F_swmm5.Link[j].subIndex;
        Beta1 = (F_swmm5.Conduit[k].beta / Qfull);
        q1 = (F_swmm5.Conduit[k].q1 / Qfull);
        q2 = (F_swmm5.Conduit[k].q2 / Qfull);
        qin = (((qinflow.get()) / F_swmm5.Conduit[k].barrels) / Qfull);
        q3 = (F_link.link_getLossRate(j, KW, (qin * Qfull), tStep) / Qfull);
        a1 = (F_swmm5.Conduit[k].a1 / Afull);
        a2 = (F_swmm5.Conduit[k].a2 / Afull);
        if (R.dge(qin, 1.0d)) {
            ain = 1.0d;
        } else {
            ain = (F_xsect.xsect_getAofS(pXsect, (qin / Beta1)) / Afull);
        }
        if (((qin <= 1e-06d) && (q2 <= 1e-06d))) {
            qout = 0.0d;
            aout.@v = 0.0d;
        } else {
            dxdt = (((F_link.link_getLength(j) / tStep) * Afull) / Qfull);
            dq = (q2 - q1);
            C1 = ((dxdt * WT) / WX);
            C2 = (((1.0d - WT)) * ((ain - a1)));
            C2 = (C2 - (WT * a2));
            C2 = ((C2 * dxdt) / WX);
            C2 = ((C2 + ((((1.0d - WX)) / WX) * dq)) - qin);
            C2 = (C2 + (q3 / WX));
            aout.@v = a2;
            result = solveContinuity(qin, ain, aout);
            if ((result == (-1))) {
                F_report.report_writeErrorMsg(ERR_KINWAVE, F_swmm5.Link[j].ID);
                return 1;
            }
            if ((result <= 0)) {
                result = 1;
            }
            qout = (Beta1 * F_xsect.xsect_getSofA(pXsect, (aout.v * Afull)));
            if (R.dgt(qin, 1.0d)) {
                qin = 1.0d;
            }
        }
        F_swmm5.Conduit[k].@q1 = (qin * Qfull);
        F_swmm5.Conduit[k].@a1 = (ain * Afull);
        F_swmm5.Conduit[k].@q2 = (qout * Qfull);
        F_swmm5.Conduit[k].@a2 = (aout.v * Afull);
        F_swmm5.Conduit[k].@fullState = F_link.link_getFullState(F_swmm5.Conduit[k].a1, F_swmm5.Conduit[k].a2, Afull);
        qinflow.set((F_swmm5.Conduit[k].q1 * F_swmm5.Conduit[k].barrels));
        qoutflow.set((F_swmm5.Conduit[k].q2 * F_swmm5.Conduit[k].barrels));
        return result;
    }

    // kinwave.c:170
    static int solveContinuity(double qin, double ain, DRef aout) {
        int n = 0;
        double aLo = 0.0d;
        double aHi = 0.0d;
        double aTmp = 0.0d;
        double fLo = 0.0d;
        double fHi = 0.0d;
        double tol = EPSIL;
        aHi = 1.0d;
        fHi = ((1.0d + C1) + C2);
        aLo = (F_xsect.xsect_getAmax(pXsect) / Afull);
        if ((aLo < aHi)) {
            fLo = ((((Beta1 * pXsect.sMax)) + ((C1 * aLo))) + C2);
        } else {
            fLo = fHi;
        }
        if (R.dgt((fHi * fLo), 0.0d)) {
            aHi = aLo;
            fHi = fLo;
            aLo = 0.0d;
            fLo = C2;
        }
        if (((fHi * fLo) <= 0.0d)) {
            if (((aout.get() < aLo) || (R.dgt(aout.get(), aHi)))) {
                aout.set((0.5d * ((aLo + aHi))));
            }
            if (R.dgt(fLo, fHi)) {
                aTmp = aLo;
                aLo = aHi;
                aHi = aTmp;
            }
            n = F_findroot.findroot_Newton(aLo, aHi, aout, tol, F_kinwave::evalContinuity, null);
            if ((n <= 0)) {
                n = (-1);
            }
        } else if ((fLo < 0.0d)) {
            if (R.dgt(qin, 1.0d)) {
                aout.set(ain);
            } else {
                aout.set(1.0d);
            }
            n = (-2);
        } else if (R.dgt(fLo, 0)) {
            aout.set(0.0d);
            n = (-3);
        } else {
            n = (-1);
        }
        return n;
    }

    // kinwave.c:261
    static void evalContinuity(double a, DRef f, DRef df, Object p) {
        f.set(((((Beta1 * F_xsect.xsect_getSofA(pXsect, (a * Afull)))) + ((C1 * a))) + C2));
        df.set(((((Beta1 * Afull) * F_xsect.xsect_getdSdA(pXsect, (a * Afull)))) + C1));
    }

}
