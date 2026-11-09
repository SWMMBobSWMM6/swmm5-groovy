// Generated from EPA SWMM 5.2.4 (infil.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_infil {
    public static TInfil[] Infil = null;
    public static double Fumax = 0.0d;
    public static double InfilFactor = 0.0d;

    // infil.c:101
    static void infil_create(int n) {
        Infil = R.fill(new TInfil[(int) (n)], (() -> new TInfil()));
        if ((Infil == null)) {
            F_swmm5.@ErrorCode = ERR_MEMORY;
        }
        InfilFactor = 1.0d;
        return;
    }

    // infil.c:116
    static void infil_delete() {
        if ((Infil != null)) {
            R.free(Infil);
            Infil = (null);
        }
    }

    // infil.c:128
    static int infil_readParams(int m, CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int n = 0;
        int status = 0;
        double[] x = new double[5];
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        i = F_input.findmatch(tok[(ntoks - 1)], F_keywords.InfilModelWords);
        if ((i >= 0)) {
            m = i;
            ntoks--;
        }
        if ((m == HORTON)) {
            n = 5;
        } else if ((m == MOD_HORTON)) {
            n = 5;
        } else if ((m == GREEN_AMPT)) {
            n = 4;
        } else if ((m == MOD_GREEN_AMPT)) {
            n = 4;
        } else if ((m == CURVE_NUMBER)) {
            n = 4;
        } else {
            return 0;
        }
        if ((ntoks < n)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 0; (i < 5); i++) {
            x[i] = 0.0d;
        }
        L_f2: for (i = 1; (i < n); i++) {
            if (!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        if (((((m == HORTON) || (m == MOD_HORTON))) && (ntoks > n))) {
            if (!(F_input.getDouble(tok[n], new DElem(x, (n - 1))) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[n]);
            }
        }
        F_swmm5.Subcatch[j].@infil = j;
        F_swmm5.Subcatch[j].@infilModel = m;
        int __sw1 = m;
        if (__sw1 == HORTON || __sw1 == MOD_HORTON) {
            status = horton_setParams(Infil[j].horton, x);
        } else if (__sw1 == GREEN_AMPT || __sw1 == MOD_GREEN_AMPT) {
            status = grnampt_setParams(Infil[j].grnAmpt, x);
        } else if (__sw1 == CURVE_NUMBER) {
            status = curvenum_setParams(Infil[j].curveNum, x);
        } else {
            status = 1;
        }
        if (!(status != 0)) {
            return F_error.error_setInpError(ERR_INFIL_PARAMS, L0);
        }
        return 0;
    }

    // infil.c:201
    static void infil_initState(int j) {
        int __sw1 = F_swmm5.Subcatch[j].infilModel;
        if (__sw1 == HORTON || __sw1 == MOD_HORTON) {
            horton_initState(Infil[j].horton);
        } else if (__sw1 == GREEN_AMPT || __sw1 == MOD_GREEN_AMPT) {
            grnampt_initState(Infil[j].grnAmpt);
        } else if (__sw1 == CURVE_NUMBER) {
            curvenum_initState(Infil[j].curveNum);
        }
    }

    // infil.c:221
    static void infil_getState(int j, double[] x) {
        int __sw1 = F_swmm5.Subcatch[j].infilModel;
        if (__sw1 == HORTON || __sw1 == MOD_HORTON) {
            horton_getState(Infil[j].horton, x);
        } else if (__sw1 == GREEN_AMPT || __sw1 == MOD_GREEN_AMPT) {
            grnampt_getState(Infil[j].grnAmpt, x);
        } else if (__sw1 == CURVE_NUMBER) {
            curvenum_getState(Infil[j].curveNum, x);
        }
    }

    // infil.c:241
    static void infil_setState(int j, double[] x) {
        int __sw1 = F_swmm5.Subcatch[j].infilModel;
        if (__sw1 == HORTON || __sw1 == MOD_HORTON) {
            horton_setState(Infil[j].horton, x);
        } else if (__sw1 == GREEN_AMPT || __sw1 == MOD_GREEN_AMPT) {
            grnampt_setState(Infil[j].grnAmpt, x);
        } else if (__sw1 == CURVE_NUMBER) {
            curvenum_setState(Infil[j].curveNum, x);
        }
    }

    // infil.c:262
    static void infil_setInfilFactor(int j) {
        int m = 0;
        int p = 0;
        InfilFactor = F_swmm5.Adjust.hydconFactor;
        if ((j >= 0)) {
            p = F_swmm5.Subcatch[j].infilPattern;
            if (((p >= 0) && (F_swmm5.Pattern[p].type == MONTHLY_PATTERN))) {
                m = (F_datetime.datetime_monthOfYear(F_swmm5.getDateTime(F_swmm5.OldRunoffTime)) - 1);
                InfilFactor = F_swmm5.Pattern[p].factor[m];
            }
        }
    }

    // infil.c:288
    static double infil_getInfil(int j, double tstep, double rainfall, double runon, double depth) {
        int __sw1 = F_swmm5.Subcatch[j].infilModel;
        if (__sw1 == HORTON) {
            return horton_getInfil(Infil[j].horton, tstep, (rainfall + runon), depth);
        } else if (__sw1 == MOD_HORTON) {
            return modHorton_getInfil(Infil[j].horton, tstep, (rainfall + runon), depth);
        } else if (__sw1 == GREEN_AMPT || __sw1 == MOD_GREEN_AMPT) {
            return grnampt_getInfil(Infil[j].grnAmpt, tstep, (rainfall + runon), depth, F_swmm5.Subcatch[j].infilModel);
        } else if (__sw1 == CURVE_NUMBER) {
            depth += (runon * tstep);
            return curvenum_getInfil(Infil[j].curveNum, tstep, rainfall, depth);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('infil_getInfil: no return');
    }

    // infil.c:325
    static int horton_setParams(THorton infil, double[] p) {
        int k = 0;
        L_f1: for (k = 0; (k < 5); k++) {
            if ((p[k] < 0.0d)) {
                return 0;
            }
        }
        infil.@f0 = (p[0] / F_swmm5.UCF(RAINFALL));
        infil.@fmin = (p[1] / F_swmm5.UCF(RAINFALL));
        infil.@decay = (p[2] / 3600.0d);
        if ((p[3] == 0.0d)) {
            p[3] = 1e-06d;
        }
        infil.@regen = (((-(Glibm.log((1.0d - 0.98d)))) / p[3]) / 86400);
        infil.@Fmax = (p[4] / F_swmm5.UCF(RAINDEPTH));
        if ((infil.f0 < infil.fmin)) {
            return 0;
        }
        return 1;
    }

    // infil.c:357
    static void horton_initState(THorton infil) {
        infil.@tp = 0.0d;
        infil.@Fe = 0.0d;
    }

    // infil.c:370
    static void horton_getState(THorton infil, double[] x) {
        x[0] = infil.tp;
        x[1] = infil.Fe;
    }

    // infil.c:376
    static void horton_setState(THorton infil, double[] x) {
        infil.@tp = x[0];
        infil.@Fe = x[1];
    }

    // infil.c:384
    static double horton_getInfil(THorton infil, double tstep, double irate, double depth) {
        int iter = 0;
        double fa = 0.0d;
        double fp = 0.0d;
        double Fp = 0.0d;
        double F1 = 0.0d;
        double t1 = 0.0d;
        double tlim = 0.0d;
        double ex = 0.0d;
        double kt = 0.0d;
        double FF = 0.0d;
        double FF1 = 0.0d;
        double r = 0.0d;
        double f0 = (infil.f0 * InfilFactor);
        double fmin = (infil.fmin * InfilFactor);
        double Fmax = infil.Fmax;
        double tp = infil.tp;
        double df = (f0 - fmin);
        double kd = infil.decay;
        double kr = (infil.regen * F_swmm5.Evap.recoveryFactor);
        if ((((df < 0.0d) || (kd < 0.0d)) || (kr < 0.0d))) {
            return 0.0d;
        }
        if (((df == 0.0d) || (kd == 0.0d))) {
            fp = f0;
            fa = (irate + (depth / tstep));
            if (R.dgt(fp, fa)) {
                fp = fa;
            }
            return (((R.dge((0.0d), (fp))) ? (0.0d) : (fp)));
        }
        fa = (irate + (depth / tstep));
        if (R.dgt(fa, 1e-10d)) {
            t1 = (tp + tstep);
            tlim = (16.0d / kd);
            if (R.dge(tp, tlim)) {
                Fp = ((fmin * tp) + (df / kd));
                F1 = (Fp + (fmin * tstep));
            } else {
                Fp = ((fmin * tp) + ((df / kd) * ((1.0d - Glibm.exp(((-kd) * tp))))));
                F1 = ((fmin * t1) + ((df / kd) * ((1.0d - Glibm.exp(((-kd) * t1))))));
            }
            fp = (((F1 - Fp)) / tstep);
            fp = (((R.dge((fp), (fmin))) ? (fp) : (fmin)));
            if (R.dgt(fp, fa)) {
                fp = fa;
            }
            if (R.dgt(t1, tlim)) {
                tp = t1;
            } else if ((fp < fa)) {
                tp = t1;
            } else {
                F1 = (Fp + (fp * tstep));
                tp = (tp + (tstep / 2.0d));
                L_f1: for (iter = 1; (iter <= 20); iter++) {
                    kt = (((((60.0d) <= ((kd * tp)))) ? (60.0d) : ((kd * tp))));
                    ex = Glibm.exp((-kt));
                    FF = (((fmin * tp) + ((df / kd) * ((1.0d - ex)))) - F1);
                    FF1 = (fmin + (df * ex));
                    r = (FF / FF1);
                    tp = (tp - r);
                    if ((Math.abs(r) <= (0.001d * tstep))) {
                        break L_f1;
                    }
                }
            }
            if (R.dgt(Fmax, 0.0d)) {
                if (R.dgt((infil.Fe + (fp * tstep)), Fmax)) {
                    fp = (((Fmax - infil.Fe)) / tstep);
                }
                fp = (((R.dge((fp), (0.0d))) ? (fp) : (0.0d)));
                infil.@Fe = (infil.Fe + (fp * tstep));
            }
        } else if (R.dgt(kr, 0.0d)) {
            r = Glibm.exp(((-kr) * tstep));
            tp = (1.0d - Glibm.exp(((-kd) * tp)));
            tp = ((-(Glibm.log((1.0d - (r * tp))))) / kd);
            if (R.dgt(Fmax, 0.0d)) {
                infil.@Fe = ((fmin * tp) + (((df / kd)) * ((1.0d - Glibm.exp(((-kd) * tp))))));
            }
        }
        infil.@tp = tp;
        return fp;
    }

    // infil.c:496
    static double modHorton_getInfil(THorton infil, double tstep, double irate, double depth) {
        double f = 0.0d;
        double fp = 0.0d;
        double fa = 0.0d;
        double f0 = (infil.f0 * InfilFactor);
        double fmin = (infil.fmin * InfilFactor);
        double df = (f0 - fmin);
        double kd = infil.decay;
        double kr = (infil.regen * F_swmm5.Evap.recoveryFactor);
        if ((((df < 0.0d) || (kd < 0.0d)) || (kr < 0.0d))) {
            return 0.0d;
        }
        if (((df == 0.0d) || (kd == 0.0d))) {
            fp = f0;
            fa = (irate + (depth / tstep));
            if (R.dgt(fp, fa)) {
                fp = fa;
            }
            return (((R.dge((0.0d), (fp))) ? (0.0d) : (fp)));
        }
        fa = (irate + (depth / tstep));
        if (R.dgt(fa, 1e-10d)) {
            if (((R.dgt(infil.Fmax, 0.0d)) && (R.dge(infil.Fe, infil.Fmax)))) {
                return 0.0d;
            }
            fp = (f0 - (kd * infil.Fe));
            fp = (((R.dge((fp), (fmin))) ? (fp) : (fmin)));
            f = (((((fa) <= (fp))) ? (fa) : (fp)));
            infil.@Fe = (infil.Fe + ((((R.dge((((f - fmin))), (0.0d))) ? (((f - fmin))) : (0.0d))) * tstep));
            if (R.dgt(infil.Fmax, 0.0d)) {
                infil.@Fe = (((R.dge((infil.Fe), (infil.Fmax))) ? (infil.Fe) : (infil.Fmax)));
            }
        } else if (R.dgt(kr, 0.0d)) {
            infil.@Fe = (infil.Fe * (Glibm.exp(((-kr) * tstep))));
            infil.@Fe = (((R.dge((infil.Fe), (0.0d))) ? (infil.Fe) : (0.0d)));
        }
        return f;
    }

    // infil.c:559
    static void grnampt_getParams(int j, double[] p) {
        p[0] = (Infil[j].grnAmpt.S * F_swmm5.UCF(RAINDEPTH));
        p[1] = (Infil[j].grnAmpt.Ks * F_swmm5.UCF(RAINFALL));
        p[2] = Infil[j].grnAmpt.IMDmax;
    }

    // infil.c:574
    static int grnampt_setParams(TGrnAmpt infil, double[] p) {
        double ksat = 0.0d;
        if (((((p[0] < 0.0d) || (p[1] <= 0.0d)) || (p[2] < 0.0d)) || (R.dgt(p[2], 1.0d)))) {
            return 0;
        }
        infil.@S = (p[0] / F_swmm5.UCF(RAINDEPTH));
        infil.@Ks = (p[1] / F_swmm5.UCF(RAINFALL));
        infil.@IMDmax = p[2];
        ksat = ((infil.Ks * 12.0d) * 3600.0d);
        infil.@Lu = ((4.0d * Math.sqrt(ksat)) / 12.0d);
        return 1;
    }

    // infil.c:597
    static void grnampt_initState(TGrnAmpt infil) {
        if ((infil == null)) {
            return;
        }
        infil.@IMD = infil.IMDmax;
        infil.@Fu = 0.0d;
        infil.@F = 0.0d;
        infil.@Sat = 0;
        infil.@T = 0.0d;
    }

    // infil.c:612
    static void grnampt_getState(TGrnAmpt infil, double[] x) {
        x[0] = infil.IMD;
        x[1] = infil.F;
        x[2] = infil.Fu;
        x[3] = infil.Sat;
        x[4] = infil.T;
    }

    // infil.c:621
    static void grnampt_setState(TGrnAmpt infil, double[] x) {
        infil.@IMD = x[0];
        infil.@F = x[1];
        infil.@Fu = x[2];
        infil.@Sat = ((byte) (R.D2I(x[3])));
        infil.@T = x[4];
    }

    // infil.c:632
    static double grnampt_getInfil(TGrnAmpt infil, double tstep, double irate, double depth, int modelType) {
        Fumax = ((infil.IMDmax * infil.Lu) * Math.sqrt(InfilFactor));
        infil.@T = (infil.T - tstep);
        if ((infil.Sat != 0)) {
            return grnampt_getSatInfil(infil, tstep, irate, depth);
        } else {
            return grnampt_getUnsatInfil(infil, tstep, irate, depth, modelType);
        }
        throw new IllegalStateException('grnampt_getInfil: no return');
    }

    // infil.c:660
    static double grnampt_getUnsatInfil(TGrnAmpt infil, double tstep, double irate, double depth, int modelType) {
        double ia = 0.0d;
        double c1 = 0.0d;
        double F2 = 0.0d;
        double dF = 0.0d;
        double Fs = 0.0d;
        double kr = 0.0d;
        double ts = 0.0d;
        double ks = (infil.Ks * InfilFactor);
        double lu = (infil.Lu * Math.sqrt(InfilFactor));
        ia = (irate + (depth / tstep));
        if ((ia < 1e-10d)) {
            ia = 0.0d;
        }
        if ((ia == 0.0d)) {
            if ((infil.Fu <= 0.0d)) {
                return 0.0d;
            }
            kr = ((lu / 90000.0d) * F_swmm5.Evap.recoveryFactor);
            dF = ((kr * Fumax) * tstep);
            infil.@F = (infil.F - dF);
            infil.@Fu = (infil.Fu - dF);
            if ((infil.Fu <= 0.0d)) {
                infil.@Fu = 0.0d;
                infil.@F = 0.0d;
                infil.@IMD = infil.IMDmax;
                return 0.0d;
            }
            if ((infil.T <= 0.0d)) {
                infil.@IMD = (((Fumax - infil.Fu)) / lu);
                infil.@F = 0.0d;
            }
            return 0.0d;
        }
        if ((ia <= ks)) {
            dF = (ia * tstep);
            infil.@F = (infil.F + dF);
            infil.@Fu = (infil.Fu + dF);
            infil.@Fu = (((((infil.Fu) <= (Fumax))) ? (infil.Fu) : (Fumax)));
            if (((modelType == GREEN_AMPT) && (infil.T <= 0.0d))) {
                infil.@IMD = (((Fumax - infil.Fu)) / lu);
                infil.@F = 0.0d;
            }
            return ia;
        }
        infil.@T = ((5400.0d / lu) / F_swmm5.Evap.recoveryFactor);
        Fs = (((ks * ((infil.S + depth))) * infil.IMD) / ((ia - ks)));
        if (R.dgt(infil.F, Fs)) {
            infil.@Sat = 1;
            return grnampt_getSatInfil(infil, tstep, irate, depth);
        }
        if (((infil.F + (ia * tstep)) < Fs)) {
            dF = (ia * tstep);
            infil.@F = (infil.F + dF);
            infil.@Fu = (infil.Fu + dF);
            infil.@Fu = (((((infil.Fu) <= (Fumax))) ? (infil.Fu) : (Fumax)));
            return ia;
        }
        ts = (tstep - (((Fs - infil.F)) / ia));
        if ((ts <= 0.0d)) {
            ts = 0.0d;
        }
        c1 = (((infil.S + depth)) * infil.IMD);
        F2 = grnampt_getF2(Fs, c1, ks, ts);
        if (R.dgt(F2, (Fs + (ia * ts)))) {
            F2 = (Fs + (ia * ts));
        }
        dF = (F2 - infil.F);
        infil.@F = F2;
        infil.@Fu = (infil.Fu + dF);
        infil.@Fu = (((((infil.Fu) <= (Fumax))) ? (infil.Fu) : (Fumax)));
        infil.@Sat = 1;
        return (dF / tstep);
    }

    // infil.c:767
    static double grnampt_getSatInfil(TGrnAmpt infil, double tstep, double irate, double depth) {
        double ia = 0.0d;
        double c1 = 0.0d;
        double dF = 0.0d;
        double F2 = 0.0d;
        double ks = (infil.Ks * InfilFactor);
        double lu = (infil.Lu * Math.sqrt(InfilFactor));
        ia = (irate + (depth / tstep));
        if ((ia < 1e-10d)) {
            return 0.0d;
        }
        infil.@T = ((5400.0d / lu) / F_swmm5.Evap.recoveryFactor);
        c1 = (((infil.S + depth)) * infil.IMD);
        F2 = grnampt_getF2(infil.F, c1, ks, tstep);
        dF = (F2 - infil.F);
        if (R.dgt(dF, (ia * tstep))) {
            dF = (ia * tstep);
            infil.@Sat = 0;
        }
        infil.@F = (infil.F + dF);
        infil.@Fu = (infil.Fu + dF);
        infil.@Fu = (((((infil.Fu) <= (Fumax))) ? (infil.Fu) : (Fumax)));
        return (dF / tstep);
    }

    // infil.c:813
    static double grnampt_getF2(double f1, double c1, double ks, double ts) {
        int i = 0;
        double f2 = f1;
        double f2min = 0.0d;
        double df2 = 0.0d;
        double c2 = 0.0d;
        f2min = (f1 + (ks * ts));
        if ((c1 == 0.0d)) {
            return f2min;
        }
        if (((ts < 10.0d) && (R.dgt(f1, (0.01d * c1))))) {
            f2 = (f1 + ((ks * ((1.0d + (c1 / f1)))) * ts));
            return (((R.dge((f2), (f2min))) ? (f2) : (f2min)));
        }
        c2 = ((c1 * Glibm.log((f1 + c1))) - (ks * ts));
        L_f1: for (i = 1; (i <= 20); i++) {
            df2 = (((((f2 - f1) - (c1 * Glibm.log((f2 + c1)))) + c2)) / ((1.0d - (c1 / ((f2 + c1))))));
            if ((Math.abs(df2) < 0.00001d)) {
                return (((R.dge((f2), (f2min))) ? (f2) : (f2min)));
            }
            f2 -= df2;
        }
        return f2min;
    }

    // infil.c:861
    static int curvenum_setParams(TCurveNum infil, double[] p) {
        if ((p[0] < 10.0d)) {
            p[0] = 10.0d;
        }
        if (R.dgt(p[0], 99.0d)) {
            p[0] = 99.0d;
        }
        infil.@Smax = ((((1000.0d / p[0]) - 10.0d)) / 12.0d);
        if ((infil.Smax < 0.0d)) {
            return 0;
        }
        if (R.dgt(p[2], 0.0d)) {
            infil.@regen = (1.0d / ((p[2] * 86400)));
        } else {
            return 0;
        }
        infil.@Tmax = (0.06d / infil.regen);
        return 1;
    }

    // infil.c:888
    static void curvenum_initState(TCurveNum infil) {
        infil.@S = infil.Smax;
        infil.@P = 0.0d;
        infil.@F = 0.0d;
        infil.@T = 0.0d;
        infil.@Se = infil.Smax;
        infil.@f = 0.0d;
    }

    // infil.c:903
    static void curvenum_getState(TCurveNum infil, double[] x) {
        x[0] = infil.S;
        x[1] = infil.P;
        x[2] = infil.F;
        x[3] = infil.T;
        x[4] = infil.Se;
        x[5] = infil.f;
    }

    // infil.c:913
    static void curvenum_setState(TCurveNum infil, double[] x) {
        infil.@S = x[0];
        infil.@P = x[1];
        infil.@F = x[2];
        infil.@T = x[3];
        infil.@Se = x[4];
        infil.@f = x[5];
    }

    // infil.c:925
    static double curvenum_getInfil(TCurveNum infil, double tstep, double irate, double depth) {
        double F1 = 0.0d;
        double f1 = 0.0d;
        double fa = (irate + (depth / tstep));
        if (R.dgt(irate, 1e-10d)) {
            if (R.dge(infil.T, infil.Tmax)) {
                infil.@P = 0.0d;
                infil.@F = 0.0d;
                infil.@f = 0.0d;
                infil.@Se = infil.S;
            }
            infil.@T = 0.0d;
            infil.@P = (infil.P + (irate * tstep));
            F1 = (infil.P * ((1.0d - (infil.P / ((infil.P + infil.Se))))));
            f1 = (((F1 - infil.F)) / tstep);
            if (((f1 < 0.0d) || (infil.S <= 0.0d))) {
                f1 = 0.0d;
            }
        } else {
            if (((R.dgt(depth, 0.004167d)) && (R.dgt(infil.S, 0.0d)))) {
                f1 = infil.f;
                if (R.dgt((f1 * tstep), infil.S)) {
                    f1 = (infil.S / tstep);
                }
            } else {
                infil.@T = (infil.T + tstep);
            }
        }
        if (R.dgt(f1, 0.0d)) {
            f1 = (((((f1) <= (fa))) ? (f1) : (fa)));
            f1 = (((R.dge((f1), (0.0d))) ? (f1) : (0.0d)));
            infil.@F = (infil.F + (f1 * tstep));
            if (R.dgt(infil.regen, 0.0d)) {
                infil.@S = (infil.S - (f1 * tstep));
                if ((infil.S < 0.0d)) {
                    infil.@S = 0.0d;
                }
            }
        } else {
            infil.@S = (infil.S + (((infil.regen * infil.Smax) * tstep) * F_swmm5.Evap.recoveryFactor));
            if (R.dgt(infil.S, infil.Smax)) {
                infil.@S = infil.Smax;
            }
        }
        infil.@f = f1;
        return f1;
    }

}
