// Generated from EPA SWMM 5.2.4 (exfil.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_exfil {
    // exfil.c:34
    static int exfil_readStorageParams(int k, CPtr[] tok, int ntoks, int n) {
        int i = 0;
        double[] x = new double[3];
        if ((ntoks == (n + 1))) {
            if (!(F_input.getDouble(tok[n], new DElem(x, 1)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[n]);
            }
            x[0] = 0.0d;
            x[2] = 0.0d;
        } else if ((ntoks < (n + 3))) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        } else {
            L_f1: for (i = 0; (i < 3); i++) {
                if (!(F_input.getDouble(tok[(n + i)], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(n + i)]);
                }
            }
        }
        if ((x[1] == 0.0d)) {
            return 0;
        }
        return createStorageExfil(k, x);
    }

    // exfil.c:74
    static void exfil_initState(int k) {
        int i = 0;
        DBox a = new DBox(0.0d);
        double alast = 0.0d;
        DBox d = new DBox(0.0d);
        TTable aCurve = null;
        TExfil exfil = F_swmm5.Storage[k].exfil;
        if ((exfil != null)) {
            F_infil.grnampt_initState(exfil.btmExfil);
            F_infil.grnampt_initState(exfil.bankExfil);
            int __sw1 = F_swmm5.Storage[k].shape;
            if (__sw1 == TABULAR) {
                i = F_swmm5.Storage[k].aCurve;
                exfil.@btmArea = 0.0d;
                exfil.@bankMinDepth = 0.0d;
                exfil.@bankMaxDepth = 0.0d;
                exfil.@bankMaxArea = 0.0d;
                if ((i >= 0)) {
                    aCurve = F_swmm5.Curve[i];
                    F_swmm5.Storage[k].exfil.@btmArea = F_table.table_lookupEx(aCurve, 0.0d);
                    F_table.table_getFirstEntry(aCurve, d, a);
                    alast = a.v;
                    L_w2: while ((F_table.table_getNextEntry(aCurve, d, a) != 0)) {
                        if ((a.v < alast)) {
                            break L_w2;
                        } else if (R.dgt(a.v, alast)) {
                            exfil.@bankMaxArea = a.v;
                            exfil.@bankMaxDepth = d.v;
                        } else if ((exfil.bankMaxArea == 0.0d)) {
                            exfil.@bankMinDepth = d.v;
                        } else {
                            break L_w2;
                        }
                        alast = a.v;
                    }
                    exfil.@btmArea = (exfil.btmArea / (F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LENGTH)));
                    exfil.@bankMaxArea = (exfil.bankMaxArea / (F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LENGTH)));
                    exfil.@bankMinDepth = (exfil.bankMinDepth / (F_swmm5.UCF(LENGTH)));
                    exfil.@bankMaxDepth = (exfil.bankMaxDepth / (F_swmm5.UCF(LENGTH)));
                }
            } else if (__sw1 == FUNCTIONAL) {
                exfil.@btmArea = F_swmm5.Storage[k].a0;
                if ((F_swmm5.Storage[k].a2 == 0.0d)) {
                    exfil.@btmArea = (exfil.btmArea + F_swmm5.Storage[k].a1);
                }
                exfil.@bankMinDepth = 0.0d;
                exfil.@bankMaxDepth = 10000000000.0d;
                exfil.@bankMaxArea = 10000000000.0d;
            } else if (__sw1 == CYLINDRICAL || __sw1 == CONICAL || __sw1 == PYRAMIDAL) {
                exfil.@btmArea = F_swmm5.Storage[k].a0;
                exfil.@bankMinDepth = 0.0d;
                exfil.@bankMaxDepth = 10000000000.0d;
                exfil.@bankMaxArea = 10000000000.0d;
            }
        }
    }

    // exfil.c:158
    static double exfil_getLoss(TExfil exfil, double tStep, double depth, double area) {
        double exfilRate = 0.0d;
        if ((exfil.btmExfil.IMDmax == 0.0d)) {
            exfilRate = (exfil.btmExfil.Ks * F_swmm5.Adjust.hydconFactor);
        } else {
            exfilRate = F_infil.grnampt_getInfil(exfil.btmExfil, tStep, 0.0d, depth, MOD_GREEN_AMPT);
        }
        exfilRate *= exfil.btmArea;
        if (R.dgt(depth, exfil.bankMinDepth)) {
            area = ((((((area) <= (exfil.bankMaxArea))) ? (area) : (exfil.bankMaxArea))) - exfil.btmArea);
            if (R.dgt(area, 0.0d)) {
                if ((exfil.btmExfil.IMDmax == 0.0d)) {
                    exfilRate += ((area * exfil.btmExfil.Ks) * F_swmm5.Adjust.hydconFactor);
                } else {
                    if (R.dgt(depth, exfil.bankMaxDepth)) {
                        depth = ((depth - exfil.bankMaxDepth) + (((exfil.bankMaxDepth - exfil.bankMinDepth)) / 2.0d));
                    } else {
                        depth = (((depth - exfil.bankMinDepth)) / 2.0d);
                    }
                    exfilRate += (area * F_infil.grnampt_getInfil(exfil.bankExfil, tStep, 0.0d, depth, MOD_GREEN_AMPT));
                }
            }
        }
        return exfilRate;
    }

    // exfil.c:218
    static int createStorageExfil(int k, double[] x) {
        TExfil exfil = null;
        exfil = F_swmm5.Storage[k].exfil;
        if ((exfil == null)) {
            exfil = new TExfil();
            if ((exfil == null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            F_swmm5.Storage[k].@exfil = exfil;
            exfil.@btmExfil = (null);
            exfil.@bankExfil = (null);
            exfil.@btmExfil = new TGrnAmpt();
            if ((exfil.btmExfil == null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            exfil.@bankExfil = new TGrnAmpt();
            if ((exfil.bankExfil == null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
        }
        if (!(F_infil.grnampt_setParams(exfil.btmExfil, x) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, L0);
        }
        F_infil.grnampt_setParams(exfil.bankExfil, x);
        return 0;
    }

}
