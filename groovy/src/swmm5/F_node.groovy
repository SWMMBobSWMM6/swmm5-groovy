// Generated from EPA SWMM 5.2.4 (node.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_node {
    // node.c:102
    static int node_readParams(int j, int type, int k, CPtr[] tok, int ntoks) {
        int __sw1 = type;
        if (__sw1 == JUNCTION) {
            return junc_readParams(j, k, tok, ntoks);
        } else if (__sw1 == OUTFALL) {
            return outfall_readParams(j, k, tok, ntoks);
        } else if (__sw1 == STORAGE) {
            return storage_readParams(j, k, tok, ntoks);
        } else if (__sw1 == DIVIDER) {
            return divider_readParams(j, k, tok, ntoks);
        } else {
            return 0;
        }
        throw new IllegalStateException('node_readParams: no return');
    }

    // node.c:125
    static void node_setParams(int j, int type, int k, double[] x) {
        F_swmm5.Node_[j].@type = type;
        F_swmm5.Node_[j].@subIndex = k;
        F_swmm5.Node_[j].@invertElev = (x[0] / F_swmm5.UCF(LENGTH));
        F_swmm5.Node_[j].@crownElev = F_swmm5.Node_[j].invertElev;
        F_swmm5.Node_[j].@initDepth = 0.0d;
        F_swmm5.Node_[j].@newVolume = 0.0d;
        F_swmm5.Node_[j].@fullVolume = 0.0d;
        F_swmm5.Node_[j].@fullDepth = 0.0d;
        F_swmm5.Node_[j].@surDepth = 0.0d;
        F_swmm5.Node_[j].@pondedArea = 0.0d;
        F_swmm5.Node_[j].@degree = 0;
        F_swmm5.Node_[j].@inlet = NO_INLET;
        int __sw1 = type;
        if (__sw1 == JUNCTION) {
            F_swmm5.Node_[j].@fullDepth = (x[1] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@initDepth = (x[2] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@surDepth = (x[3] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@pondedArea = (x[4] / ((F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LENGTH))));
        } else if (__sw1 == OUTFALL) {
            F_swmm5.Outfall[k].@type = R.D2I(x[1]);
            F_swmm5.Outfall[k].@fixedStage = (x[2] / F_swmm5.UCF(LENGTH));
            F_swmm5.Outfall[k].@tideCurve = R.D2I(x[3]);
            F_swmm5.Outfall[k].@stageSeries = R.D2I(x[4]);
            F_swmm5.Outfall[k].@hasFlapGate = ((byte) (R.D2I(x[5])));
            F_swmm5.Outfall[k].@routeTo = R.D2I(x[6]);
            F_swmm5.Outfall[k].@wRouted = (null);
            if ((F_swmm5.Outfall[k].routeTo >= 0)) {
                F_swmm5.Outfall[k].@wRouted = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            }
        } else if (__sw1 == STORAGE) {
            F_swmm5.Node_[j].@fullDepth = (x[1] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@initDepth = (x[2] / F_swmm5.UCF(LENGTH));
            F_swmm5.Storage[k].@shape = R.D2I(x[3]);
            F_swmm5.Storage[k].@a1 = x[4];
            F_swmm5.Storage[k].@a2 = x[5];
            F_swmm5.Storage[k].@a0 = x[6];
            F_swmm5.Storage[k].@aCurve = R.D2I(x[7]);
            F_swmm5.Node_[j].@surDepth = (x[8] / F_swmm5.UCF(LENGTH));
            F_swmm5.Storage[k].@fEvap = x[9];
        } else if (__sw1 == DIVIDER) {
            F_swmm5.Divider[k].@link = R.D2I(x[1]);
            F_swmm5.Divider[k].@type = R.D2I(x[2]);
            F_swmm5.Divider[k].@flowCurve = R.D2I(x[3]);
            F_swmm5.Divider[k].@qMin = (x[4] / F_swmm5.UCF(FLOW));
            F_swmm5.Divider[k].@dhMax = x[5];
            F_swmm5.Divider[k].@cWeir = x[6];
            F_swmm5.Node_[j].@fullDepth = (x[7] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@initDepth = (x[8] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@surDepth = (x[9] / F_swmm5.UCF(LENGTH));
            F_swmm5.Node_[j].@pondedArea = (x[10] / ((F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LENGTH))));
        }
    }

    // node.c:200
    static void node_validate(int j) {
        TDwfInflow inflow = null;
        if (((R.dgt(F_swmm5.Node_[j].fullDepth, F_swmm5.Node_[j].oldDepth)) && (R.dgt(F_swmm5.Node_[j].oldDepth, 0.0d)))) {
            F_report.report_writeWarningMsg(L317, F_swmm5.Node_[j].ID);
        }
        if (R.dgt(F_swmm5.Node_[j].initDepth, (F_swmm5.Node_[j].fullDepth + F_swmm5.Node_[j].surDepth))) {
            F_report.report_writeErrorMsg(ERR_NODE_DEPTH, F_swmm5.Node_[j].ID);
        }
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            if ((node_getVolume(j, F_swmm5.Node_[j].fullDepth) < 0.0d)) {
                F_report.report_writeErrorMsg(ERR_STORAGE_VOLUME, F_swmm5.Node_[j].ID);
            }
        }
        if ((F_swmm5.Node_[j].type == DIVIDER)) {
            divider_validate(j);
        }
        inflow = F_swmm5.Node_[j].dwfInflow;
        L_w1: while ((inflow != null)) {
            F_inflow.inflow_initDwfInflow(inflow);
            inflow = inflow.next;
        }
    }

    // node.c:237
    static void node_initState(int j) {
        int p = 0;
        int k = 0;
        F_swmm5.Node_[j].@oldDepth = F_swmm5.Node_[j].initDepth;
        F_swmm5.Node_[j].@newDepth = F_swmm5.Node_[j].oldDepth;
        F_swmm5.Node_[j].@crownElev = F_swmm5.Node_[j].invertElev;
        F_swmm5.Node_[j].@fullVolume = node_getVolume(j, F_swmm5.Node_[j].fullDepth);
        F_swmm5.Node_[j].@oldVolume = node_getVolume(j, F_swmm5.Node_[j].oldDepth);
        F_swmm5.Node_[j].@newVolume = F_swmm5.Node_[j].oldVolume;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Node_[j].oldQual[p] = 0.0d;
            F_swmm5.Node_[j].newQual[p] = 0.0d;
        }
        F_swmm5.Node_[j].@oldLatFlow = 0.0d;
        F_swmm5.Node_[j].@newLatFlow = 0.0d;
        F_swmm5.Node_[j].@apiExtInflow = 0.0d;
        F_swmm5.Node_[j].@losses = 0.0d;
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            k = F_swmm5.Node_[j].subIndex;
            F_swmm5.Storage[k].@hrt = 0.0d;
            if ((F_swmm5.Storage[k].exfil != null)) {
                F_exfil.exfil_initState(k);
            }
        }
        if ((F_swmm5.Node_[j].type == OUTFALL)) {
            k = F_swmm5.Node_[j].subIndex;
            if ((F_swmm5.Outfall[k].routeTo >= 0)) {
                F_swmm5.Outfall[k].@vRouted = 0.0d;
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    F_swmm5.Outfall[k].wRouted[p] = 0.0d;
                }
            }
        }
    }

    // node.c:293
    static void node_setOldHydState(int j) {
        F_swmm5.Node_[j].@oldDepth = F_swmm5.Node_[j].newDepth;
        F_swmm5.Node_[j].@oldVolume = F_swmm5.Node_[j].newVolume;
        F_swmm5.Node_[j].@oldFlowInflow = F_swmm5.Node_[j].inflow;
        F_swmm5.Node_[j].@oldNetInflow = (F_swmm5.Node_[j].inflow - F_swmm5.Node_[j].outflow);
    }

    // node.c:308
    static void node_setOldQualState(int j) {
        int p = 0;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Node_[j].oldQual[p] = F_swmm5.Node_[j].newQual[p];
            F_swmm5.Node_[j].newQual[p] = 0.0d;
        }
    }

    // node.c:325
    static void node_initFlows(int j, double tStep) {
        F_swmm5.Node_[j].@inflow = F_swmm5.Node_[j].newLatFlow;
        F_swmm5.Node_[j].@outflow = F_swmm5.Node_[j].losses;
        if (R.dgt(F_swmm5.Node_[j].newVolume, F_swmm5.Node_[j].fullVolume)) {
            F_swmm5.Node_[j].@overflow = (((F_swmm5.Node_[j].newVolume - F_swmm5.Node_[j].fullVolume)) / tStep);
        } else {
            F_swmm5.Node_[j].@overflow = 0.0d;
        }
    }

    // node.c:345
    static double node_getDepth(int j, double v) {
        int __sw1 = F_swmm5.Node_[j].type;
        if (__sw1 == STORAGE) {
            return storage_getDepth(j, v);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('node_getDepth: no return');
    }

    // node.c:362
    static double node_getVolume(int j, double d) {
        int __sw1 = F_swmm5.Node_[j].type;
        if (__sw1 == STORAGE) {
            return storage_getVolume(j, d);
        } else {
            if (R.dgt(F_swmm5.Node_[j].fullDepth, 0.0d)) {
                return (F_swmm5.Node_[j].fullVolume * ((d / F_swmm5.Node_[j].fullDepth)));
            } else {
                return 0.0d;
            }
        }
        throw new IllegalStateException('node_getVolume: no return');
    }

    // node.c:383
    static double node_getSurfArea(int j, double d) {
        int __sw1 = F_swmm5.Node_[j].type;
        if (__sw1 == STORAGE) {
            return storage_getSurfArea(j, d);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('node_getSurfArea: no return');
    }

    // node.c:400
    static double node_getOutflow(int j, int k) {
        int __sw1 = F_swmm5.Node_[j].type;
        if (__sw1 == DIVIDER) {
            return divider_getOutflow(j, k);
        } else if (__sw1 == STORAGE) {
            return storage_getOutflow(j, k);
        } else {
            return (F_swmm5.Node_[j].inflow + F_swmm5.Node_[j].overflow);
        }
        throw new IllegalStateException('node_getOutflow: no return');
    }

    // node.c:418
    static double node_getMaxOutflow(int j, double q, double tStep) {
        double qMax = 0.0d;
        if (R.dgt(F_swmm5.Node_[j].fullVolume, 0.0d)) {
            qMax = (F_swmm5.Node_[j].inflow + (F_swmm5.Node_[j].oldVolume / tStep));
            if (R.dgt(q, qMax)) {
                q = qMax;
            }
        }
        return (((R.dge((0.0d), (q))) ? (0.0d) : (q)));
    }

    // node.c:438
    static double node_getSystemOutflow(int j, IRef isFlooded) {
        double outflow = 0.0d;
        isFlooded.set(0);
        if ((F_swmm5.Node_[j].type == OUTFALL)) {
            if ((F_swmm5.Node_[j].outflow == 0.0d)) {
                outflow = F_swmm5.Node_[j].inflow;
            } else {
                if ((F_swmm5.Node_[j].inflow == 0.0d)) {
                    outflow = (-F_swmm5.Node_[j].outflow);
                    F_swmm5.Node_[j].@inflow = Math.abs(outflow);
                }
            }
            F_swmm5.Node_[j].@overflow = 0.0d;
            F_swmm5.Node_[j].@newVolume = 0.0d;
        } else if ((((F_swmm5.RouteModel != DW) && (F_swmm5.Node_[j].degree == 0)) && (F_swmm5.Node_[j].type != STORAGE))) {
            if ((F_swmm5.Node_[j].outflow == 0.0d)) {
                outflow = F_swmm5.Node_[j].inflow;
            }
            F_swmm5.Node_[j].@overflow = 0.0d;
            F_swmm5.Node_[j].@newVolume = 0.0d;
        } else {
            if ((F_swmm5.Node_[j].newVolume <= F_swmm5.Node_[j].fullVolume)) {
                outflow = F_swmm5.Node_[j].overflow;
            }
            if (R.dgt(outflow, 0.0d)) {
                isFlooded.set(1);
            }
        }
        return outflow;
    }

    // node.c:497
    static void node_getResults(int j, double f, float[] x) {
        int p = 0;
        double z = 0.0d;
        double f1 = (1.0d - f);
        z = ((((f1 * F_swmm5.Node_[j].oldDepth) + (f * F_swmm5.Node_[j].newDepth))) * F_swmm5.UCF(LENGTH));
        x[NODE_DEPTH] = ((float) (z));
        z = (F_swmm5.Node_[j].invertElev * F_swmm5.UCF(LENGTH));
        x[NODE_HEAD] = ((float) (x[NODE_DEPTH] + ((float) (z))));
        z = ((((f1 * F_swmm5.Node_[j].oldVolume) + (f * F_swmm5.Node_[j].newVolume))) * F_swmm5.UCF(VOLUME));
        x[NODE_VOLUME] = ((float) (z));
        z = ((((f1 * F_swmm5.Node_[j].oldLatFlow) + (f * F_swmm5.Node_[j].newLatFlow))) * F_swmm5.UCF(FLOW));
        x[NODE_LATFLOW] = ((float) (z));
        z = ((((f1 * F_swmm5.Node_[j].oldFlowInflow) + (f * F_swmm5.Node_[j].inflow))) * F_swmm5.UCF(FLOW));
        x[NODE_INFLOW] = ((float) (z));
        z = (F_swmm5.Node_[j].overflow * F_swmm5.UCF(FLOW));
        x[NODE_OVERFLOW] = ((float) (z));
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                z = ((f1 * F_swmm5.Node_[j].oldQual[p]) + (f * F_swmm5.Node_[j].newQual[p]));
                x[(NODE_QUAL + p)] = ((float) (z));
            }
        }
    }

    // node.c:532
    static void node_setOutletDepth(int j, double yNorm, double yCrit, double z) {
        int __sw1 = F_swmm5.Node_[j].type;
        if (__sw1 == STORAGE) {
            return;
        } else if (__sw1 == OUTFALL) {
            outfall_setOutletDepth(j, yNorm, yCrit, z);
        } else {
            if (R.dgt(z, 0.0d)) {
                F_swmm5.Node_[j].@newDepth = 0.0d;
            } else {
                F_swmm5.Node_[j].@newDepth = (((((yNorm) <= (yCrit))) ? (yNorm) : (yCrit)));
            }
        }
    }

    // node.c:562
    static double node_getPondedArea(int j, double d) {
        double a = 0.0d;
        if (((d <= F_swmm5.Node_[j].fullDepth) || (F_swmm5.Node_[j].pondedArea == 0.0d))) {
            return node_getSurfArea(j, d);
        }
        d = (d - F_swmm5.Node_[j].fullDepth);
        a = F_swmm5.Node_[j].pondedArea;
        if ((a <= 0.0d)) {
            a = node_getSurfArea(j, F_swmm5.Node_[j].fullDepth);
        }
        return a;
    }

    // node.c:589
    static double node_getLosses(int j, double tStep) {
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            return storage_getLosses(j, tStep);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('node_getLosses: no return');
    }

    // node.c:606
    static int junc_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int i = 0;
        double[] x = new double[6];
        CPtr id = null;
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(NODE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (i = 1; (i <= 5); i++) {
            x[(i - 1)] = 0.0d;
            if ((i < ntoks)) {
                if (!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
        }
        L_f2: for (i = 1; (i <= 4); i++) {
            if ((x[i] < 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(i + 1)]);
            }
        }
        F_swmm5.Node_[j].@ID = id;
        node_setParams(j, JUNCTION, k, x);
        return 0;
    }

    // node.c:654
    static int storage_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int i = 0;
        int m = 0;
        int n = 0;
        double[] x = new double[10];
        double[] y = new double[3];
        double A = 0.0d;
        double B = 0.0d;
        double L = 0.0d;
        double W = 0.0d;
        double Z = 0.0d;
        CPtr id = null;
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(NODE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (i = 1; (i <= 3); i++) {
            if (!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        m = F_input.findmatch(tok[4], F_keywords.RelationWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
        }
        x[3] = m;
        x[4] = 0.0d;
        x[5] = 0.0d;
        x[6] = 0.0d;
        x[7] = (-1.0d);
        x[8] = 0.0d;
        x[9] = 0.0d;
        if ((m == TABULAR)) {
            i = F_project.project_findObject(CURVE, tok[5]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[5]);
            }
            x[7] = i;
            n = 6;
        } else {
            if ((ntoks < 8)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f2: for (i = 5; (i <= 7); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(y, (i - 5))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            n = 8;
        }
        int __sw1 = m;
        if (__sw1 == FUNCTIONAL) {
            if ((y[2] < 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        } else if (__sw1 == CYLINDRICAL || __sw1 == CONICAL || __sw1 == PARABOLOID || __sw1 == PYRAMIDAL) {
            if ((y[0] <= 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
            if ((y[1] <= 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
            if ((y[2] < 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        if (((m == PARABOLOID) && (y[2] == 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[7]);
        }
        int __sw2 = m;
        if (__sw2 == FUNCTIONAL) {
            x[4] = y[0];
            x[5] = y[1];
            x[6] = y[2];
        } else if (__sw2 == CYLINDRICAL) {
            A = (y[0] / 2.0d);
            B = (y[1] / 2.0d);
            x[4] = 0.0d;
            x[5] = 0.0d;
            x[6] = ((3.141592654d * A) * B);
        } else if (__sw2 == CONICAL) {
            A = (y[0] / 2.0d);
            B = (y[1] / 2.0d);
            Z = y[2];
            x[4] = (((2.0d * 3.141592654d) * B) * Z);
            x[5] = ((((3.141592654d * B) / A) * Z) * Z);
            x[6] = ((3.141592654d * A) * B);
        } else if (__sw2 == PARABOLOID) {
            A = (y[0] / 2.0d);
            B = (y[1] / 2.0d);
            Z = y[2];
            x[4] = (((3.141592654d * A) * B) / Z);
            x[5] = 0.0d;
            x[6] = 0.0d;
        } else if (__sw2 == PYRAMIDAL) {
            L = y[0];
            W = y[1];
            Z = y[2];
            x[4] = ((2.0d * ((L + W))) * Z);
            x[5] = ((4.0d * Z) * Z);
            x[6] = (L * W);
        }
        if ((ntoks > n)) {
            if (!(F_input.getDouble(tok[n], new DElem(x, 8)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[n]);
            }
            n++;
        }
        if ((ntoks > n)) {
            if (!(F_input.getDouble(tok[n], new DElem(x, 9)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[n]);
            }
            n++;
        }
        F_swmm5.Node_[j].@ID = id;
        node_setParams(j, STORAGE, k, x);
        if ((ntoks > n)) {
            return F_exfil.exfil_readStorageParams(k, tok, ntoks, n);
        }
        return 0;
    }

    // node.c:813
    static double storage_getDepth(int j, double v) {
        int k = F_swmm5.Node_[j].subIndex;
        int i = F_swmm5.Storage[k].aCurve;
        int shape = F_swmm5.Storage[k].shape;
        double a0 = F_swmm5.Storage[k].a0;
        double a1 = F_swmm5.Storage[k].a1;
        double a2 = F_swmm5.Storage[k].a2;
        DBox d = new DBox(0.0d);
        double e = 0.0d;
        TStorageVol storageVol = new TStorageVol();
        if (((R.dgt(F_swmm5.Node_[j].fullVolume, 0.0d)) && (R.dge(v, F_swmm5.Node_[j].fullVolume)))) {
            return F_swmm5.Node_[j].fullDepth;
        }
        if ((v == 0.0d)) {
            return 0.0d;
        }
        v *= (F_swmm5.UCF(VOLUME));
        storageVol.@k = k;
        storageVol.@v = v;
        int __sw1 = shape;
        if (__sw1 == TABULAR) {
            i = F_swmm5.Storage[k].aCurve;
            if ((i >= 0)) {
                d.@v = F_table.table_getStorageDepth(F_swmm5.Curve[i], v);
            } else {
                d.@v = 0.0d;
            }
        } else if (__sw1 == CYLINDRICAL) {
            d.@v = (v / a0);
        } else if (__sw1 == PARABOLOID) {
            d.@v = Math.sqrt(((2.0d * v) / a1));
        } else if (__sw1 == FUNCTIONAL) {
            if ((a2 == 0.0d)) {
                d.@v = (v / ((a0 + a1)));
            } else if ((a0 == 0.0d)) {
                e = (1.0d / ((a2 + 1.0d)));
                d.@v = Glibm.pow((v / ((a1 * e))), e);
            } else if (((a2 == 1.0d) && (R.dgt(a1, 0.0d)))) {
                d.@v = (((Math.sqrt(((a0 * a0) + ((2.0d * a1) * v))) - a0)) / a1);
            } else {
                d.@v = (v / ((a0 + a1)));
                F_findroot.findroot_Newton(0.0d, (F_swmm5.Node_[j].fullDepth * F_swmm5.UCF(LENGTH)), d, 0.001d, F_node::storage_getVolDiff, storageVol);
            }
        } else if (__sw1 == CONICAL || __sw1 == PYRAMIDAL) {
            d.@v = (v / a0);
            F_findroot.findroot_Newton(0.0d, (F_swmm5.Node_[j].fullDepth * F_swmm5.UCF(LENGTH)), d, 0.001d, F_node::storage_getVolDiff, storageVol);
        } else {
            d.@v = 0.0d;
        }
        d.@v = (d.v / (F_swmm5.UCF(LENGTH)));
        if (R.dgt(d.v, F_swmm5.Node_[j].fullDepth)) {
            d.@v = F_swmm5.Node_[j].fullDepth;
        }
        return d.v;
    }

    // node.c:906
    static void storage_getVolDiff(double y, DRef f, DRef df, Object p) {
        int k = 0;
        TStorageVol storageVol = null;
        storageVol = ((TStorageVol) p);
        k = storageVol.k;
        f.set((storage_getVolume(k, y) - storageVol.v));
        df.set(storage_getSurfArea(k, y));
    }

    // node.c:930
    static double storage_getVolume(int j, double d) {
        int k = F_swmm5.Node_[j].subIndex;
        int i = 0;
        double n = 0.0d;
        double v = 0.0d;
        if ((d == 0.0d)) {
            return 0.0d;
        }
        if (((R.dge(d, F_swmm5.Node_[j].fullDepth)) && (R.dgt(F_swmm5.Node_[j].fullVolume, 0.0d)))) {
            return F_swmm5.Node_[j].fullVolume;
        }
        int __sw1 = F_swmm5.Storage[k].shape;
        if (__sw1 == TABULAR) {
            i = F_swmm5.Storage[k].aCurve;
            if ((i >= 0)) {
                return (F_table.table_getStorageVolume(F_swmm5.Curve[i], (d * F_swmm5.UCF(LENGTH))) / F_swmm5.UCF(VOLUME));
            } else {
                return 0.0d;
            }
        } else if (__sw1 == FUNCTIONAL) {
            d *= (F_swmm5.UCF(LENGTH));
            n = (F_swmm5.Storage[k].a2 + 1.0d);
            v = (((F_swmm5.Storage[k].a0 * d)) + ((F_swmm5.Storage[k].a1 / n) * Glibm.pow(d, n)));
            return (v / F_swmm5.UCF(VOLUME));
        } else if (__sw1 == CYLINDRICAL || __sw1 == CONICAL || __sw1 == PARABOLOID || __sw1 == PYRAMIDAL) {
            d *= (F_swmm5.UCF(LENGTH));
            v = (d * ((F_swmm5.Storage[k].a0 + (d * (((F_swmm5.Storage[k].a1 / 2.0d) + ((d * F_swmm5.Storage[k].a2) / 3.0d)))))));
            return (v / F_swmm5.UCF(VOLUME));
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('storage_getVolume: no return');
    }

    // node.c:979
    static double storage_getSurfArea(int j, double d) {
        double area = 0.0d;
        int k = F_swmm5.Node_[j].subIndex;
        int i = 0;
        int __sw1 = F_swmm5.Storage[k].shape;
        if (__sw1 == TABULAR) {
            i = F_swmm5.Storage[k].aCurve;
            if ((i >= 0)) {
                area = F_table.table_lookupEx(F_swmm5.Curve[i], (d * F_swmm5.UCF(LENGTH)));
            }
        } else if (__sw1 == FUNCTIONAL) {
            area = (F_swmm5.Storage[k].a0 + (F_swmm5.Storage[k].a1 * Glibm.pow((d * F_swmm5.UCF(LENGTH)), F_swmm5.Storage[k].a2)));
        } else if (__sw1 == CYLINDRICAL || __sw1 == CONICAL || __sw1 == PARABOLOID || __sw1 == PYRAMIDAL) {
            d *= (F_swmm5.UCF(LENGTH));
            area = (F_swmm5.Storage[k].a0 + (d * ((F_swmm5.Storage[k].a1 + (d * F_swmm5.Storage[k].a2)))));
        } else {
            return 0.0d;
        }
        return ((area / F_swmm5.UCF(LENGTH)) / F_swmm5.UCF(LENGTH));
    }

    // node.c:1022
    static double storage_getOutflow(int j, int i) {
        int k = 0;
        double a = 0.0d;
        double y = 0.0d;
        if ((F_swmm5.Link[i].type != CONDUIT)) {
            return 0.0d;
        }
        y = (F_swmm5.Node_[j].newDepth - F_swmm5.Link[i].offset1);
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        if (R.dge(y, F_swmm5.Link[i].xsect.yFull)) {
            return F_swmm5.Link[i].qFull;
        }
        k = F_swmm5.Link[i].subIndex;
        a = F_xsect.xsect_getAofY(F_swmm5.Link[i].xsect, y);
        return (F_swmm5.Conduit[k].beta * F_xsect.xsect_getSofA(F_swmm5.Link[i].xsect, a));
    }

    // node.c:1052
    static double storage_getLosses(int j, double tStep) {
        int k = 0;
        double depth = 0.0d;
        double area = 0.0d;
        double evapRate = 0.0d;
        double exfilRate = 0.0d;
        double totalLoss = 0.0d;
        double lossRatio = 0.0d;
        TExfil exfil = null;
        k = F_swmm5.Node_[j].subIndex;
        evapRate = (F_swmm5.Evap.rate * F_swmm5.Storage[k].fEvap);
        exfil = F_swmm5.Storage[k].exfil;
        if (((R.dgt(evapRate, 0.0d)) || (exfil != null))) {
            depth = F_swmm5.Node_[j].newDepth;
            area = storage_getSurfArea(j, depth);
            if (R.dgt(F_swmm5.Node_[j].newVolume, 0.0001d)) {
                evapRate = (area * evapRate);
            }
            if ((exfil != null)) {
                exfilRate = F_exfil.exfil_getLoss(exfil, tStep, depth, area);
            }
            totalLoss = (((evapRate + exfilRate)) * tStep);
            if (R.dgt(totalLoss, F_swmm5.Node_[j].newVolume)) {
                lossRatio = (F_swmm5.Node_[j].newVolume / totalLoss);
                evapRate *= lossRatio;
                exfilRate *= lossRatio;
            }
        }
        F_swmm5.Storage[F_swmm5.Node_[j].subIndex].@evapLoss = (evapRate * tStep);
        F_swmm5.Storage[F_swmm5.Node_[j].subIndex].@exfilLoss = (exfilRate * tStep);
        return (evapRate + exfilRate);
    }

    // node.c:1113
    static int divider_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int i = 0;
        int m = 0;
        int m1 = 0;
        int m2 = 0;
        int n = 0;
        double[] x = new double[11];
        CPtr id = null;
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(NODE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if (!(F_input.getDouble(tok[1], new DElem(x, 0)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[1]);
        }
        L_f1: for (i = 1; (i < 11); i++) {
            x[i] = 0.0d;
        }
        if (((R.strlen(tok[2]) == 0) || (R.strcmp(tok[2], L275) == 0))) {
            x[1] = (-1.0d);
        } else {
            m1 = F_project.project_findObject(LINK, tok[2]);
            if ((m1 < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            x[1] = m1;
        }
        n = 4;
        m1 = F_input.findmatch(tok[3], F_keywords.DividerTypeWords);
        if ((m1 < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
        }
        x[2] = m1;
        x[3] = (-1);
        if ((m1 == TABULAR_DIVIDER)) {
            if ((ntoks < 5)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            m2 = F_project.project_findObject(CURVE, tok[4]);
            if ((m2 < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[4]);
            }
            x[3] = m2;
            n = 5;
        }
        if ((m1 == CUTOFF_DIVIDER)) {
            if ((ntoks < 5)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[4], new DElem(x, 4)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[4]);
            }
            n = 5;
        }
        if ((m1 == WEIR_DIVIDER)) {
            if ((ntoks < 7)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f2: for (i = 4; (i < 7); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            n = 7;
        }
        if ((m1 == OVERFLOW_DIVIDER)) {
            n = 4;
        }
        m = 7;
        L_f3: for (i = n; ((i < ntoks) && (m < 11)); i++) {
            if (!(F_input.getDouble(tok[i], new DElem(x, m)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
            m++;
        }
        F_swmm5.Node_[j].@ID = id;
        node_setParams(j, DIVIDER, k, x);
        return 0;
    }

    // node.c:1216
    static void divider_validate(int j) {
        int i = 0;
        int k = 0;
        k = F_swmm5.Node_[j].subIndex;
        i = F_swmm5.Divider[k].link;
        if (((i < 0) || (((F_swmm5.Link[i].node1 != j) && (F_swmm5.Link[i].node2 != j))))) {
            F_report.report_writeErrorMsg(ERR_DIVIDER_LINK, F_swmm5.Node_[j].ID);
        }
        if ((F_swmm5.Divider[k].type == WEIR_DIVIDER)) {
            if (((F_swmm5.Divider[k].dhMax <= 0.0d) || (F_swmm5.Divider[k].cWeir <= 0.0d))) {
                F_report.report_writeErrorMsg(ERR_WEIR_DIVIDER, F_swmm5.Node_[j].ID);
            } else {
                F_swmm5.Divider[k].@qMax = ((F_swmm5.Divider[k].cWeir * Glibm.pow(F_swmm5.Divider[k].dhMax, 1.5d)) / F_swmm5.UCF(FLOW));
                if (R.dgt(F_swmm5.Divider[k].qMin, F_swmm5.Divider[k].qMax)) {
                    F_report.report_writeErrorMsg(ERR_WEIR_DIVIDER, F_swmm5.Node_[j].ID);
                }
            }
        }
    }

    // node.c:1251
    static double divider_getOutflow(int j, int k) {
        int i = 0;
        int m = 0;
        double qIn = 0.0d;
        double qOut = 0.0d;
        double f = 0.0d;
        qIn = (F_swmm5.Node_[j].inflow + F_swmm5.Node_[j].overflow);
        i = F_swmm5.Node_[j].subIndex;
        int __sw1 = F_swmm5.Divider[i].type;
        if (__sw1 == CUTOFF_DIVIDER) {
            if ((qIn <= F_swmm5.Divider[i].qMin)) {
                qOut = 0.0d;
            } else {
                qOut = (qIn - F_swmm5.Divider[i].qMin);
            }
        } else if (__sw1 == OVERFLOW_DIVIDER) {
            if ((k != F_swmm5.Divider[i].link)) {
                qOut = qIn;
            } else {
                qOut = (qIn - F_swmm5.Node_[j].outflow);
            }
            if ((qOut < 1e-05d)) {
                qOut = 0.0d;
            }
            return qOut;
        } else if (__sw1 == WEIR_DIVIDER) {
            if ((qIn <= F_swmm5.Divider[i].qMin)) {
                qOut = 0.0d;
            } else {
                f = (((qIn - F_swmm5.Divider[i].qMin)) / ((F_swmm5.Divider[i].qMax - F_swmm5.Divider[i].qMin)));
                if (R.dgt(f, 1.0d)) {
                    qOut = (F_swmm5.Divider[i].qMax * Math.sqrt(f));
                } else {
                    qOut = ((F_swmm5.Divider[i].cWeir * Glibm.pow((f * F_swmm5.Divider[i].dhMax), 1.5d)) / F_swmm5.UCF(FLOW));
                }
            }
        } else if (__sw1 == TABULAR_DIVIDER) {
            m = F_swmm5.Divider[i].flowCurve;
            if ((m >= 0)) {
                qOut = (F_table.table_lookup(F_swmm5.Curve[m], (qIn * F_swmm5.UCF(FLOW))) / F_swmm5.UCF(FLOW));
            } else {
                qOut = 0.0d;
            }
        } else {
            qOut = 0.0d;
        }
        if (R.dgt(qOut, qIn)) {
            qOut = qIn;
        }
        if ((k != F_swmm5.Divider[i].link)) {
            qOut = (qIn - qOut);
        }
        return qOut;
    }

    // node.c:1333
    static int outfall_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int i = 0;
        int m = 0;
        int n = 0;
        double[] x = new double[7];
        CPtr id = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(NODE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if (!(F_input.getDouble(tok[1], new DElem(x, 0)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[1]);
        }
        i = F_input.findmatch(tok[2], F_keywords.OutfallTypeWords);
        if ((i < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[2]);
        }
        x[1] = i;
        x[2] = 0.0d;
        x[3] = (-1.0d);
        x[4] = (-1.0d);
        x[5] = 0.0d;
        x[6] = (-1.0d);
        n = 4;
        if ((i >= FIXED_OUTFALL)) {
            if ((ntoks < 4)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            n = 5;
            int __sw1 = i;
            if (__sw1 == FIXED_OUTFALL) {
                if (!(F_input.getDouble(tok[3], new DElem(x, 2)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[3]);
                }
            } else if (__sw1 == TIDAL_OUTFALL) {
                m = F_project.project_findObject(CURVE, tok[3]);
                if ((m < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[3]);
                }
                x[3] = m;
            } else if (__sw1 == TIMESERIES_OUTFALL) {
                m = F_project.project_findObject(TSERIES, tok[3]);
                if ((m < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[3]);
                }
                x[4] = m;
                F_swmm5.Tseries[m].@refersTo = TIMESERIES_OUTFALL;
            }
        }
        if ((ntoks == n)) {
            m = F_input.findmatch(tok[(n - 1)], F_keywords.NoYesWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[(n - 1)]);
            }
            x[5] = m;
        }
        if ((ntoks == (n + 1))) {
            m = F_project.project_findObject(SUBCATCH, tok[n]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[n]);
            }
            x[6] = m;
        }
        F_swmm5.Node_[j].@ID = id;
        node_setParams(j, OUTFALL, k, x);
        return 0;
    }

    // node.c:1413
    static void outfall_setOutletDepth(int j, double yNorm, double yCrit, double z) {
        DBox x = new DBox(0.0d);
        DBox y = new DBox(0.0d);
        double yNew = 0.0d;
        double stage = 0.0d;
        int k = 0;
        int i = F_swmm5.Node_[j].subIndex;
        double currentDate = 0.0d;
        int __sw1 = F_swmm5.Outfall[i].type;
        if (__sw1 == FREE_OUTFALL) {
            if (R.dgt(z, 0.0d)) {
                F_swmm5.Node_[j].@newDepth = 0.0d;
            } else {
                F_swmm5.Node_[j].@newDepth = (((((yNorm) <= (yCrit))) ? (yNorm) : (yCrit)));
            }
            return;
        } else if (__sw1 == NORMAL_OUTFALL) {
            if (R.dgt(z, 0.0d)) {
                F_swmm5.Node_[j].@newDepth = 0.0d;
            } else {
                F_swmm5.Node_[j].@newDepth = yNorm;
            }
            return;
        } else if (__sw1 == FIXED_OUTFALL) {
            stage = F_swmm5.Outfall[i].fixedStage;
        } else if (__sw1 == TIDAL_OUTFALL) {
            k = F_swmm5.Outfall[i].tideCurve;
            F_table.table_getFirstEntry(F_swmm5.Curve[k], x, y);
            currentDate = (F_swmm5.NewRoutingTime / 86400000.0d);
            x.@v = (x.v + (((currentDate - Math.floor(currentDate))) * 24.0d));
            stage = (F_table.table_lookup(F_swmm5.Curve[k], x.v) / F_swmm5.UCF(LENGTH));
        } else if (__sw1 == TIMESERIES_OUTFALL) {
            k = F_swmm5.Outfall[i].stageSeries;
            currentDate = (F_swmm5.StartDateTime + (F_swmm5.NewRoutingTime / 86400000.0d));
            stage = (F_table.table_tseriesLookup(F_swmm5.Tseries[k], currentDate, 1) / F_swmm5.UCF(LENGTH));
        } else {
            stage = F_swmm5.Node_[j].invertElev;
        }
        yCrit = (((((yCrit) <= (yNorm))) ? (yCrit) : (yNorm)));
        if ((((yCrit + z) + F_swmm5.Node_[j].invertElev) < stage)) {
            yNew = (stage - F_swmm5.Node_[j].invertElev);
        } else if (R.dgt(z, 0.0d)) {
            if ((stage < (F_swmm5.Node_[j].invertElev + z))) {
                yNew = (((R.dge((0.0d), (((stage - F_swmm5.Node_[j].invertElev))))) ? (0.0d) : (((stage - F_swmm5.Node_[j].invertElev)))));
            } else {
                yNew = (z + yCrit);
            }
        } else {
            yNew = yCrit;
        }
        F_swmm5.Node_[j].@newDepth = yNew;
    }

}
