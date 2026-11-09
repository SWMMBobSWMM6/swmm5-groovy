// Generated from EPA SWMM 5.2.4 (surfqual.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_surfqual {
    // surfqual.c:65
    static void surfqual_initState(int j) {
        int p = 0;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Subcatch[j].oldQual[p] = 0.0d;
            F_swmm5.Subcatch[j].newQual[p] = 0.0d;
            F_swmm5.Subcatch[j].pondedQual[p] = 0.0d;
        }
        F_landuse.landuse_getInitBuildup(F_swmm5.Subcatch[j].landFactor, F_swmm5.Subcatch[j].initBuildup, F_swmm5.Subcatch[j].area, F_swmm5.Subcatch[j].curbLength);
    }

    // surfqual.c:89
    static void surfqual_getBuildup(int j, double tStep) {
        int i = 0;
        int p = 0;
        double f = 0.0d;
        double area = 0.0d;
        double curb = 0.0d;
        double oldBuildup = 0.0d;
        double newBuildup = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
            f = F_swmm5.Subcatch[j].landFactor[i].fraction;
            if ((f == 0.0d)) {
                continue L_f1;
            }
            area = ((f * F_swmm5.Subcatch[j].area) * F_swmm5.UCF(LANDAREA));
            curb = (f * F_swmm5.Subcatch[j].curbLength);
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                if (((F_swmm5.Pollut[p].snowOnly != 0) && (F_swmm5.Subcatch[j].newSnowDepth < (0.001d / 12.0d)))) {
                    continue L_f2;
                }
                oldBuildup = F_swmm5.Subcatch[j].landFactor[i].buildup[p];
                newBuildup = F_landuse.landuse_getBuildup(i, p, area, curb, oldBuildup, tStep);
                newBuildup = (((R.dge((newBuildup), (oldBuildup))) ? (newBuildup) : (oldBuildup)));
                F_swmm5.Subcatch[j].landFactor[i].buildup[p] = newBuildup;
                F_massbal.massbal_updateLoadingTotals(BUILDUP_LOAD, p, ((newBuildup - oldBuildup)));
            }
        }
    }

    // surfqual.c:137
    static void surfqual_sweepBuildup(int j, double aDate) {
        int i = 0;
        int p = 0;
        double oldBuildup = 0.0d;
        double newBuildup = 0.0d;
        if (((F_swmm5.Subcatch[j].snowpack != null) && (R.dgt(F_swmm5.Subcatch[j].snowpack.wsnow[IMPERV0], 0.004167d)))) {
            return;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
            if ((F_swmm5.Subcatch[j].landFactor[i].fraction == 0.0d)) {
                continue L_f1;
            }
            if ((F_swmm5.Landuse[i].sweepInterval == 0.0d)) {
                continue L_f1;
            }
            if (R.dge((aDate - F_swmm5.Subcatch[j].landFactor[i].lastSwept), F_swmm5.Landuse[i].sweepInterval)) {
                F_swmm5.Subcatch[j].landFactor[i].@lastSwept = aDate;
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    oldBuildup = F_swmm5.Subcatch[j].landFactor[i].buildup[p];
                    newBuildup = (oldBuildup * ((1.0d - (F_swmm5.Landuse[i].sweepRemoval * F_swmm5.Landuse[i].washoffFunc[p].sweepEffic))));
                    newBuildup = (((((oldBuildup) <= (newBuildup))) ? (oldBuildup) : (newBuildup)));
                    newBuildup = (((R.dge((0.0d), (newBuildup))) ? (0.0d) : (newBuildup)));
                    F_swmm5.Subcatch[j].landFactor[i].buildup[p] = newBuildup;
                    F_massbal.massbal_updateLoadingTotals(SWEEPING_LOAD, p, (oldBuildup - newBuildup));
                }
            }
        }
    }

    // surfqual.c:192
    static void surfqual_getWashoff(int j, double runoff, double tStep) {
        int p = 0;
        int hasOutflow = 0;
        double cOut = 0.0d;
        double massLoad = 0.0d;
        double vLidRain = 0.0d;
        double vLidRunon = 0.0d;
        double vSurfOut = 0.0d;
        double vOut1 = 0.0d;
        double vOut2 = 0.0d;
        double area = 0.0d;
        area = F_swmm5.Subcatch[j].area;
        if (((F_swmm5.Nobjects[POLLUT] == 0) || (area == 0.0d))) {
            return;
        }
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_runoff.OutflowLoad[p] = 0.0d;
        }
        findWashoffLoads(j, runoff);
        findPondedLoads(j, tStep);
        findLidLoads(j, tStep);
        vLidRain = ((F_swmm5.Subcatch[j].rainfall * F_swmm5.Subcatch[j].lidArea) * tStep);
        vLidRunon = 0.0d;
        if ((area == F_swmm5.Subcatch[j].lidArea)) {
            vLidRunon = ((F_swmm5.Subcatch[j].runon * area) * tStep);
        }
        vOut1 = ((F_subcatch.Voutflow + vLidRain) + vLidRunon);
        vSurfOut = (F_swmm5.Subcatch[j].newRunoff * tStep);
        vOut2 = (vSurfOut + F_subcatch.VlidDrain);
        hasOutflow = ((R.dgt(vOut2, ((2.31481e-08d * area) * tStep))) ? 1 : 0);
        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            cOut = 0.0d;
            if (((R.dgt(vOut1, 0.0d)) && (hasOutflow != 0))) {
                cOut = (F_runoff.OutflowLoad[p] / vOut1);
            }
            if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
                massLoad = ((cOut * ((vOut1 - vOut2))) * F_swmm5.Pollut[p].mcf);
                if (R.dgt(massLoad, 0.0d)) {
                    F_massbal.massbal_updateLoadingTotals(BMP_REMOVAL_LOAD, p, massLoad);
                }
            }
            massLoad = ((cOut * vOut2) * F_swmm5.Pollut[p].mcf);
            F_swmm5.Subcatch[j].totalLoad[p] += massLoad;
            if ((((F_swmm5.Subcatch[j].outNode >= 0) || (F_swmm5.Subcatch[j].outSubcatch == j)))) {
                massLoad = ((cOut * vSurfOut) * F_swmm5.Pollut[p].mcf);
                F_massbal.massbal_updateLoadingTotals(RUNOFF_LOAD, p, massLoad);
            }
            F_swmm5.Subcatch[j].newQual[p] = (cOut / 28.317d);
        }
        if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
            F_lid.lid_addDrainLoads(j, F_swmm5.Subcatch[j].newQual, tStep);
        }
    }

    // surfqual.c:298
    static double surfqual_getWtdWashoff(int j, int p, double f) {
        return (((((1.0d - f)) * F_swmm5.Subcatch[j].oldRunoff) * F_swmm5.Subcatch[j].oldQual[p]) + ((f * F_swmm5.Subcatch[j].newRunoff) * F_swmm5.Subcatch[j].newQual[p]));
    }

    // surfqual.c:313
    static void findPondedLoads(int j, double tStep) {
        int p = 0;
        double cPonded = 0.0d;
        double wPonded = 0.0d;
        double bmpRemoval = 0.0d;
        double vRain = 0.0d;
        double wRain = 0.0d;
        double wRunon = 0.0d;
        double wInfil = 0.0d;
        double wOutflow = 0.0d;
        double fullArea = 0.0d;
        double nonLidArea = 0.0d;
        if ((F_swmm5.Subcatch[j].area == F_swmm5.Subcatch[j].lidArea)) {
            return;
        }
        fullArea = F_swmm5.Subcatch[j].area;
        nonLidArea = (fullArea - F_swmm5.Subcatch[j].lidArea);
        vRain = ((F_swmm5.Subcatch[j].rainfall * nonLidArea) * tStep);
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            wRain = ((F_swmm5.Pollut[p].pptConcen * 28.317d) * vRain);
            F_massbal.massbal_updateLoadingTotals(DEPOSITION_LOAD, p, (wRain * F_swmm5.Pollut[p].mcf));
            if ((F_subcatch.Vinflow == 0.0d)) {
                F_massbal.massbal_updateLoadingTotals(FINAL_LOAD, p, (F_swmm5.Subcatch[j].pondedQual[p] * F_swmm5.Pollut[p].mcf));
                F_swmm5.Subcatch[j].pondedQual[p] = 0.0d;
            } else {
                wRunon = (F_swmm5.Subcatch[j].newQual[p] * tStep);
                wPonded = ((F_swmm5.Subcatch[j].pondedQual[p] + wRain) + wRunon);
                cPonded = (wPonded / F_subcatch.Vinflow);
                wInfil = (cPonded * F_subcatch.Vinfil);
                wInfil = (((((wInfil) <= (wPonded))) ? (wInfil) : (wPonded)));
                F_massbal.massbal_updateLoadingTotals(INFIL_LOAD, p, (wInfil * F_swmm5.Pollut[p].mcf));
                wPonded -= wInfil;
                wOutflow = (cPonded * F_subcatch.Voutflow);
                wOutflow = (((((wOutflow) <= (wPonded))) ? (wOutflow) : (wPonded)));
                wPonded -= wOutflow;
                bmpRemoval = (F_landuse.landuse_getAvgBmpEffic(j, p) * wOutflow);
                F_massbal.massbal_updateLoadingTotals(BMP_REMOVAL_LOAD, p, (bmpRemoval * F_swmm5.Pollut[p].mcf));
                wOutflow -= bmpRemoval;
                F_swmm5.Subcatch[j].pondedQual[p] = ((cPonded * F_subcatch.subcatch_getDepth(j)) * nonLidArea);
                F_runoff.OutflowLoad[p] += wOutflow;
            }
        }
    }

    // surfqual.c:390
    static void findWashoffLoads(int j, double runoff) {
        int i = 0;
        int p = 0;
        int k = 0;
        double w = 0.0d;
        double area = F_swmm5.Subcatch[j].area;
        if ((runoff < 2.31481e-08d)) {
            return;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
            if (R.dgt(F_swmm5.Subcatch[j].landFactor[i].fraction, 0.0d)) {
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    F_runoff.OutflowLoad[p] += (F_landuse.landuse_getWashoffLoad(i, p, area, F_swmm5.Subcatch[j].landFactor, runoff, F_subcatch.Voutflow));
                }
            }
        }
        L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            k = F_swmm5.Pollut[p].coPollut;
            if ((k >= 0)) {
                w = (F_swmm5.Pollut[p].coFraction * F_runoff.OutflowLoad[k]);
                F_massbal.massbal_updateLoadingTotals(BUILDUP_LOAD, p, (w * F_swmm5.Pollut[p].mcf));
                F_runoff.OutflowLoad[p] += w;
            }
        }
    }

    // surfqual.c:443
    static void findLidLoads(int j, double tStep) {
        int p = 0;
        int useRunon = 0;
        double lidArea = 0.0d;
        double vLidRain = 0.0d;
        double wLidRain = 0.0d;
        double wLidRunon = 0.0d;
        lidArea = F_swmm5.Subcatch[j].lidArea;
        if ((lidArea == 0.0d)) {
            return;
        }
        vLidRain = ((F_swmm5.Subcatch[j].rainfall * lidArea) * tStep);
        useRunon = (((lidArea == F_swmm5.Subcatch[j].area)) ? 1 : 0);
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            wLidRain = ((F_swmm5.Pollut[p].pptConcen * vLidRain) * 28.317d);
            F_massbal.massbal_updateLoadingTotals(DEPOSITION_LOAD, p, (wLidRain * F_swmm5.Pollut[p].mcf));
            if ((useRunon != 0)) {
                wLidRunon = (F_swmm5.Subcatch[j].newQual[p] * tStep);
            } else {
                wLidRunon = 0.0d;
            }
            F_runoff.OutflowLoad[p] += (wLidRain + wLidRunon);
        }
    }

}
