// Generated from EPA SWMM 5.2.4 (treatmnt.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_treatmnt {
    public static int PVMAX = 5;
    public static int ErrCode = 0;
    public static int J = 0;
    public static double Dt = 0.0d;
    public static double Q = 0.0d;
    public static double V = 0.0d;
    public static double[] R_ = null;
    public static double[] Cin = null;

    // treatmnt.c:66
    static int treatmnt_open() {
        R_ = (null);
        Cin = (null);
        if ((F_swmm5.Nobjects[POLLUT] > 0)) {
            R_ = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            Cin = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            if (((R_ == null) || (Cin == null))) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return 0;
            }
        }
        return 1;
    }

    // treatmnt.c:90
    static void treatmnt_close() {
        if ((R_ != null)) {
            R.free(R_);
            R_ = (null);
        }
        if ((Cin != null)) {
            R.free(Cin);
            Cin = (null);
        }
    }

    // treatmnt.c:103
    static int treatmnt_readExpression(CPtr[] tok, int ntoks) {
        CPtr s = R.buf(1025);
        CPtr expr = null;
        int i = 0;
        int j = 0;
        int k = 0;
        int p = 0;
        MathExpr equation = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(NODE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        p = F_project.project_findObject(POLLUT, tok[1]);
        if ((p < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        Overrides.sstrncpy(s, tok[2], 1024);
        L_f1: for (i = 3; (i < ntoks); i++) {
            Overrides.sstrcat(s, L33, 1024);
            Overrides.sstrcat(s, tok[i], 1024);
        }
        if ((((((((s.g(0)) >= 97) && ((s.g(0)) <= 122))) ? (((s.g(0)) & (~32))) : (s.g(0)))) == 82)) {
            k = 0;
        } else if ((((((((s.g(0)) >= 97) && ((s.g(0)) <= 122))) ? (((s.g(0)) & (~32))) : (s.g(0)))) == 67)) {
            k = 1;
        } else {
            return F_error.error_setInpError(ERR_KEYWORD, tok[2]);
        }
        expr = R.strchr(s, 61);
        if ((expr == null)) {
            return F_error.error_setInpError(ERR_KEYWORD, L0);
        } else {
            expr = expr.add(1);
        }
        if ((F_swmm5.Node_[j].treatment == null)) {
            if (!(createTreatment(j) != 0)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
        }
        equation = F_mathexpr.mathexpr_create(expr, F_treatmnt::treatmnt_getVariableIndex);
        if ((equation == null)) {
            return F_error.error_setInpError(ERR_MATH_EXPR, L0);
        }
        if ((F_swmm5.Node_[j].treatment != null)) {
            F_swmm5.Node_[j].treatment[p].@treatType = k;
            F_swmm5.Node_[j].treatment[p].@equation = equation;
        }
        return 0;
    }

    // treatmnt.c:165
    static void treatmnt_delete(int j) {
        int p = 0;
        if ((F_swmm5.Node_[j].treatment != null)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                F_mathexpr.mathexpr_delete(F_swmm5.Node_[j].treatment[p].equation);
            }
            R.free(F_swmm5.Node_[j].treatment);
        }
        F_swmm5.Node_[j].@treatment = (null);
    }

    // treatmnt.c:184
    static void treatmnt_setInflow(double qIn, double[] wIn) {
        int p = 0;
        if (R.dgt(qIn, 0.0d)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                Cin[p] = (wIn[p] / qIn);
            }
        } else {
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                Cin[p] = 0.0d;
            }
        }
    }

    // treatmnt.c:202
    static void treatmnt_treat(int j, double q, double v, double tStep) {
        int p = 0;
        double cOut = 0.0d;
        double massLost = 0.0d;
        TTreatment treatment = null;
        if ((F_swmm5.Node_[j].treatment == null)) {
            return;
        }
        ErrCode = 0;
        J = j;
        Dt = tStep;
        Q = q;
        V = v;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            R_[p] = (-1.0d);
        }
        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            treatment = F_swmm5.Node_[j].treatment[p];
            if ((treatment.equation == null)) {
                R_[p] = 0.0d;
            } else if (((treatment.treatType == REMOVAL) && (q <= 1e-10d))) {
                R_[p] = 0.0d;
            } else {
                getRemoval(p);
            }
        }
        if ((ErrCode == ERR_CYCLIC_TREATMENT)) {
            F_report.report_writeErrorMsg(ERR_CYCLIC_TREATMENT, F_swmm5.Node_[J].ID);
        } else {
            L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                if ((R_[p] == 0.0d)) {
                    continue L_f3;
                }
                treatment = F_swmm5.Node_[j].treatment[p];
                if ((treatment.treatType == REMOVAL)) {
                    if ((Cin[p] == 0.0d)) {
                        cOut = F_swmm5.Node_[j].newQual[p];
                    } else {
                        cOut = (((1.0d - R_[p])) * Cin[p]);
                    }
                    cOut = (((((cOut) <= (F_swmm5.Node_[j].newQual[p]))) ? (cOut) : (F_swmm5.Node_[j].newQual[p])));
                } else {
                    cOut = (((1.0d - R_[p])) * F_swmm5.Node_[j].newQual[p]);
                }
                massLost = ((((((Cin[p] * q) * tStep) + (F_swmm5.Node_[j].oldQual[p] * F_swmm5.Node_[j].oldVolume)) - (cOut * (((q * tStep) + F_swmm5.Node_[j].oldVolume))))) / tStep);
                massLost = (((R.dge((0.0d), (massLost))) ? (0.0d) : (massLost)));
                F_massbal.massbal_addReactedMass(p, massLost);
                F_swmm5.Node_[j].newQual[p] = cOut;
            }
        }
    }

    // treatmnt.c:288
    static int createTreatment(int j) {
        int p = 0;
        F_swmm5.Node_[j].@treatment = R.fill(new TTreatment[(int) (F_swmm5.Nobjects[POLLUT])], (() -> new TTreatment()));
        if ((F_swmm5.Node_[j].treatment == null)) {
            return 0;
        }
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Node_[j].treatment[p].@equation = (null);
        }
        return 1;
    }

    // treatmnt.c:311
    static int treatmnt_getVariableIndex(CPtr s) {
        int k = 0;
        int m = PVMAX;
        k = F_input.findmatch(s, F_keywords.ProcessVarWords);
        if ((k >= 0)) {
            return k;
        }
        k = F_project.project_findObject(POLLUT, s);
        if ((k >= 0)) {
            return ((k + m));
        }
        if (((((((((s.g(0)) >= 97) && ((s.g(0)) <= 122))) ? (((s.g(0)) & (~32))) : (s.g(0)))) == 82) && (s.g(1) == 95))) {
            k = F_project.project_findObject(POLLUT, s.add(2));
            if ((k >= 0)) {
                return (((F_swmm5.Nobjects[POLLUT] + k) + m));
            }
        }
        return (-1);
    }

    // treatmnt.c:340
    static double treatmnt_getVariableValue(int varCode) {
        int p = 0;
        double a1 = 0.0d;
        double a2 = 0.0d;
        double y = 0.0d;
        TTreatment treatment = null;
        if ((varCode < PVMAX)) {
            int __sw1 = varCode;
            if (__sw1 == pvHRT) {
                if ((F_swmm5.Node_[J].type == STORAGE)) {
                    return (F_swmm5.Storage[F_swmm5.Node_[J].subIndex].hrt / 3600.0d);
                } else {
                    return 0.0d;
                }
            } else if (__sw1 == pvDT) {
                return Dt;
            } else if (__sw1 == pvFLOW) {
                return (Q * F_swmm5.UCF(FLOW));
            } else if (__sw1 == pvDEPTH) {
                y = (((F_swmm5.Node_[J].oldDepth + F_swmm5.Node_[J].newDepth)) / 2.0d);
                return (y * F_swmm5.UCF(LENGTH));
            } else if (__sw1 == pvAREA) {
                a1 = F_node.node_getSurfArea(J, F_swmm5.Node_[J].oldDepth);
                a2 = F_node.node_getSurfArea(J, F_swmm5.Node_[J].newDepth);
                return (((((a1 + a2)) / 2.0d) * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LENGTH));
            } else {
                return 0.0d;
            }
        } else if ((varCode < (PVMAX + F_swmm5.Nobjects[POLLUT]))) {
            p = (varCode - PVMAX);
            treatment = F_swmm5.Node_[J].treatment[p];
            if ((treatment.treatType == REMOVAL)) {
                return Cin[p];
            }
            return F_swmm5.Node_[J].newQual[p];
        } else {
            p = ((varCode - PVMAX) - F_swmm5.Nobjects[POLLUT]);
            if ((p >= F_swmm5.Nobjects[POLLUT])) {
                return 0.0d;
            }
            return getRemoval(p);
        }
        throw new IllegalStateException('getVariableValue: no return');
    }

    // treatmnt.c:404
    static double getRemoval(int p) {
        double c0 = F_swmm5.Node_[J].newQual[p];
        double r = 0.0d;
        TTreatment treatment = null;
        if (((R.dgt(R_[p], 1.0d)) || (ErrCode != 0))) {
            ErrCode = 1;
            return 0.0d;
        }
        if (((R.dge(R_[p], 0.0d)) && (R_[p] <= 1.0d))) {
            return R_[p];
        }
        R_[p] = 10.0d;
        if ((c0 == 0.0d)) {
            R_[p] = 0.0d;
            return 0.0d;
        }
        treatment = F_swmm5.Node_[J].treatment[p];
        r = F_mathexpr.mathexpr_eval(treatment.equation, F_treatmnt::treatmnt_getVariableValue);
        r = (((R.dge((0.0d), (r))) ? (0.0d) : (r)));
        if ((treatment.treatType == REMOVAL)) {
            r = (((((1.0d) <= (r))) ? (1.0d) : (r)));
            R_[p] = r;
        } else {
            r = (((((c0) <= (r))) ? (c0) : (r)));
            R_[p] = (1.0d - (r / c0));
        }
        return R_[p];
    }

}
