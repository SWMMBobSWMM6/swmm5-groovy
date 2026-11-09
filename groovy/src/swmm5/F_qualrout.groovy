// Generated from EPA SWMM 5.2.4 (qualrout.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_qualrout {
    public static double ZeroVolume = 0.0353147d;
    public static double ZeroDepth = 0.003281d;

    // qualrout.c:63
    static void qualrout_init() {
        int i = 0;
        int p = 0;
        int isWet = 0;
        double c = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            isWet = ((R.dgt(F_swmm5.Node_[i].newDepth, ZeroDepth)) ? 1 : 0);
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                c = 0.0d;
                if ((isWet != 0)) {
                    c = F_swmm5.Pollut[p].initConcen;
                }
                F_swmm5.Node_[i].oldQual[p] = c;
                F_swmm5.Node_[i].newQual[p] = c;
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            isWet = ((R.dgt(F_swmm5.Link[i].newDepth, ZeroDepth)) ? 1 : 0);
            L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                c = 0.0d;
                if ((isWet != 0)) {
                    c = F_swmm5.Pollut[p].initConcen;
                }
                F_swmm5.Link[i].oldQual[p] = c;
                F_swmm5.Link[i].newQual[p] = c;
            }
        }
    }

    // qualrout.c:100
    static void qualrout_execute(double tStep) {
        int i = 0;
        int j = 0;
        double qIn = 0.0d;
        double vAvg = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            findLinkMassFlow(i, tStep);
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@qualInflow = F_swmm5.Node_[j].inflow;
            qIn = F_swmm5.Node_[j].qualInflow;
            vAvg = (((F_swmm5.Node_[j].oldVolume + F_swmm5.Node_[j].newVolume)) / 2.0d);
            if ((F_swmm5.Node_[j].treatment != null)) {
                if ((qIn < 1e-10d)) {
                    qIn = 0.0d;
                }
                F_treatmnt.treatmnt_setInflow(qIn, F_swmm5.Node_[j].newQual);
            }
            if (((F_swmm5.Node_[j].type == STORAGE) || (R.dgt(F_swmm5.Node_[j].oldVolume, ZeroVolume)))) {
                findStorageQual(j, tStep);
            } else {
                findNodeQual(j);
            }
            if ((F_swmm5.Node_[j].treatment != null)) {
                F_treatmnt.treatmnt_treat(j, qIn, vAvg, tStep);
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            findLinkQual(i, tStep);
        }
    }

    // qualrout.c:146
    static double getMixedQual(double c, double v1, double wIn, double qIn, double tStep) {
        double vIn = 0.0d;
        double cIn = 0.0d;
        double cMax = 0.0d;
        if ((qIn <= 1e-10d)) {
            return c;
        }
        vIn = (qIn * tStep);
        cIn = ((wIn * tStep) / vIn);
        cMax = (((R.dge((c), (cIn))) ? (c) : (cIn)));
        c = ((((c * v1) + (wIn * tStep))) / ((v1 + vIn)));
        c = (((((c) <= (cMax))) ? (c) : (cMax)));
        c = (((R.dge((c), (0.0d))) ? (c) : (0.0d)));
        return c;
    }

    // qualrout.c:179
    static void findLinkMassFlow(int i, double tStep) {
        int j = 0;
        int p = 0;
        double qLink = 0.0d;
        double w = 0.0d;
        qLink = F_swmm5.Link[i].newFlow;
        j = F_swmm5.Link[i].node2;
        if ((qLink < 0.0d)) {
            j = F_swmm5.Link[i].node1;
        }
        qLink = Math.abs(qLink);
        if ((F_swmm5.RouteModel != DW)) {
            qLink -= (F_inlet.inlet_capturedFlow(i));
        }
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            w = (qLink * F_swmm5.Link[i].oldQual[p]);
            F_swmm5.Node_[j].newQual[p] += w;
            F_swmm5.Link[i].totalLoad[p] += (w * tStep);
        }
        F_swmm5.Node_[j].@qualInflow = (F_swmm5.Node_[j].qualInflow + qLink);
    }

    // qualrout.c:221
    static void findNodeQual(int j) {
        int p = 0;
        double qNode = 0.0d;
        qNode = F_swmm5.Node_[j].qualInflow;
        if (R.dgt(qNode, 1e-10d)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                F_swmm5.Node_[j].newQual[p] = (F_swmm5.Node_[j].newQual[p] / qNode);
            }
        } else {
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                if (R.dgt(F_swmm5.Node_[j].newDepth, ZeroDepth)) {
                    F_swmm5.Node_[j].newQual[p] = F_swmm5.Node_[j].oldQual[p];
                } else {
                    F_swmm5.Node_[j].newQual[p] = 0.0d;
                }
            }
        }
    }

    // qualrout.c:253
    static void findLinkQual(int i, double tStep) {
        int j = 0;
        int k = 0;
        int p = 0;
        double wIn = 0.0d;
        double qIn = 0.0d;
        double qSeep = 0.0d;
        double v1 = 0.0d;
        double v2 = 0.0d;
        double c1 = 0.0d;
        double c2 = 0.0d;
        double vEvap = 0.0d;
        double vLosses = 0.0d;
        double fEvap = 0.0d;
        double barrels = 0.0d;
        j = F_swmm5.Link[i].node1;
        if ((F_swmm5.Link[i].newFlow < 0.0d)) {
            j = F_swmm5.Link[i].node2;
        }
        if (((F_swmm5.Link[i].type != CONDUIT) || (F_swmm5.Link[i].xsect.type == DUMMY))) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                F_swmm5.Link[i].newQual[p] = F_swmm5.Node_[j].newQual[p];
            }
            return;
        }
        k = F_swmm5.Link[i].subIndex;
        barrels = F_swmm5.Conduit[k].barrels;
        qIn = (Math.abs(F_swmm5.Conduit[k].q1) * barrels);
        qSeep = (F_swmm5.Conduit[k].seepLossRate * barrels);
        vEvap = ((F_swmm5.Conduit[k].evapLossRate * barrels) * tStep);
        v1 = F_swmm5.Link[i].oldVolume;
        v2 = F_swmm5.Link[i].newVolume;
        vLosses = ((qSeep * tStep) + vEvap);
        fEvap = 1.0d;
        if (((R.dgt(vEvap, 0.0d)) && (R.dgt(v1, ZeroVolume)))) {
            fEvap += (vEvap / v1);
        }
        if ((F_swmm5.RouteModel == SF)) {
            findSFLinkQual(i, qSeep, fEvap, tStep);
            return;
        }
        if ((F_swmm5.RouteModel == DW)) {
            qIn = (qIn + ((((v2 + vLosses) - v1)) / tStep));
            qIn = (((R.dge((qIn), (0.0d))) ? (qIn) : (0.0d)));
        }
        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            c1 = F_swmm5.Link[i].oldQual[p];
            F_massbal.massbal_addSeepageLoss(p, (qSeep * c1));
            c1 *= fEvap;
            c2 = getReactedQual(p, c1, v1, tStep);
            wIn = (F_swmm5.Node_[j].newQual[p] * qIn);
            c2 = getMixedQual(c2, v1, wIn, qIn, tStep);
            if (((v2 < ZeroVolume) || (F_swmm5.Link[i].newDepth <= ZeroDepth))) {
                F_massbal.massbal_addToFinalStorage(p, (c2 * v2));
                c2 = 0.0d;
            }
            F_swmm5.Link[i].newQual[p] = c2;
        }
    }

    // qualrout.c:357
    static void findSFLinkQual(int i, double qSeep, double fEvap, double tStep) {
        int j = F_swmm5.Link[i].node1;
        int p = 0;
        double c1 = 0.0d;
        double c2 = 0.0d;
        double lossRate = 0.0d;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            c1 = F_swmm5.Node_[j].newQual[p];
            F_massbal.massbal_addSeepageLoss(p, (qSeep * c1));
            c1 *= fEvap;
            c2 = c1;
            if (R.dgt(F_swmm5.Pollut[p].kDecay, 0.0d)) {
                c2 = (c1 * Glibm.exp(((-F_swmm5.Pollut[p].kDecay) * tStep)));
                c2 = (((R.dge((0.0d), (c2))) ? (0.0d) : (c2)));
                lossRate = (((c1 - c2)) * F_swmm5.Link[i].newFlow);
                F_massbal.massbal_addReactedMass(p, lossRate);
            }
            F_swmm5.Link[i].newQual[p] = c2;
        }
    }

    // qualrout.c:398
    static void findStorageQual(int j, double tStep) {
        int p = 0;
        int k = 0;
        double qIn = 0.0d;
        double wIn = 0.0d;
        double v1 = 0.0d;
        double c1 = 0.0d;
        double c2 = 0.0d;
        double qExfil = 0.0d;
        double vEvap = 0.0d;
        double fEvap = 1.0d;
        qIn = F_swmm5.Node_[j].qualInflow;
        v1 = F_swmm5.Node_[j].oldVolume;
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            updateHRT(j, F_swmm5.Node_[j].oldVolume, qIn, tStep);
            k = F_swmm5.Node_[j].subIndex;
            qExfil = (F_swmm5.Storage[k].exfilLoss / tStep);
            vEvap = F_swmm5.Storage[k].evapLoss;
            if (((R.dgt(vEvap, 0.0d)) && (R.dgt(v1, ZeroVolume)))) {
                fEvap += (vEvap / v1);
            }
        }
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            c1 = F_swmm5.Node_[j].oldQual[p];
            F_massbal.massbal_addSeepageLoss(p, (qExfil * c1));
            c1 *= fEvap;
            if (((F_swmm5.Node_[j].treatment == null) || (F_swmm5.Node_[j].treatment[p].equation == null))) {
                c1 = getReactedQual(p, c1, v1, tStep);
            }
            wIn = F_swmm5.Node_[j].newQual[p];
            c2 = getMixedQual(c1, v1, wIn, qIn, tStep);
            if (((((F_swmm5.Node_[j].newVolume <= ZeroVolume) || (F_swmm5.Node_[j].newDepth <= ZeroDepth))) && (qIn <= 1e-10d))) {
                F_massbal.massbal_addToFinalStorage(p, (c2 * F_swmm5.Node_[j].newVolume));
                c2 = 0.0d;
            }
            F_swmm5.Node_[j].newQual[p] = c2;
        }
    }

    // qualrout.c:478
    static void updateHRT(int j, double v, double q, double tStep) {
        int k = F_swmm5.Node_[j].subIndex;
        double hrt = F_swmm5.Storage[k].hrt;
        if ((v < 1e-10d)) {
            hrt = 0.0d;
        } else {
            hrt = ((((hrt + tStep)) * v) / ((v + (q * tStep))));
        }
        F_swmm5.Storage[k].@hrt = (((R.dge((hrt), (0.0d))) ? (hrt) : (0.0d)));
    }

    // qualrout.c:498
    static double getReactedQual(int p, double c, double v1, double tStep) {
        double c2 = 0.0d;
        double lossRate = 0.0d;
        double kDecay = F_swmm5.Pollut[p].kDecay;
        if ((kDecay == 0.0d)) {
            return c;
        }
        c2 = (c * ((1.0d - (kDecay * tStep))));
        c2 = (((R.dge((0.0d), (c2))) ? (0.0d) : (c2)));
        lossRate = ((((c - c2)) * v1) / tStep);
        F_massbal.massbal_addReactedMass(p, lossRate);
        return c2;
    }

}
