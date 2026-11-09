// Generated from EPA SWMM 5.2.4 (subcatch.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_subcatch {
    public static double MCOEFF = 1.49d;
    public static double MEXP = 1.6666667d;
    public static double ODETOL = 0.0001d;
    public static double Vevap = 0.0d;
    public static double Vpevap = 0.0d;
    public static double Vinfil = 0.0d;
    public static double Vinflow = 0.0d;
    public static double Voutflow = 0.0d;
    public static double VlidIn = 0.0d;
    public static double VlidInfil = 0.0d;
    public static double VlidOut = 0.0d;
    public static double VlidDrain = 0.0d;
    public static double VlidReturn = 0.0d;
    public static TSubarea theSubarea = null;
    public static double Dstore = 0.0d;
    public static double Alpha = 0.0d;
    public static CPtr[] RunoffRoutingWords = new CPtr[] { L663, L2, L3, null };

    // subcatch.c:119
    static int subcatch_readParams(int j, CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        int m = 0;
        CPtr id = null;
        double[] x = new double[9];
        if ((ntoks < 8)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(SUBCATCH, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = F_project.project_findObject(GAGE, tok[1]);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        x[0] = k;
        m = F_project.project_findObject(NODE, tok[2]);
        x[1] = m;
        m = F_project.project_findObject(SUBCATCH, tok[2]);
        x[2] = m;
        if (((x[1] < 0.0d) && (x[2] < 0.0d))) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        L_f1: for (i = 3; (i < 8); i++) {
            if (((!(F_input.getDouble(tok[i], new DElem(x, i)) != 0)) || (x[i] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        x[8] = (-1);
        if ((ntoks > 8)) {
            k = F_project.project_findObject(SNOWMELT, tok[8]);
            if ((k < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[8]);
            }
            x[8] = k;
        }
        F_swmm5.Subcatch[j].@ID = id;
        F_swmm5.Subcatch[j].@gage = R.D2I(x[0]);
        F_swmm5.Subcatch[j].@outNode = R.D2I(x[1]);
        F_swmm5.Subcatch[j].@outSubcatch = R.D2I(x[2]);
        F_swmm5.Subcatch[j].@area = (x[3] / F_swmm5.UCF(LANDAREA));
        F_swmm5.Subcatch[j].@fracImperv = ((((((x[4]) <= (100.0d))) ? (x[4]) : (100.0d))) / 100.0d);
        F_swmm5.Subcatch[j].@width = (x[5] / F_swmm5.UCF(LENGTH));
        F_swmm5.Subcatch[j].@slope = (x[6] / 100.0d);
        F_swmm5.Subcatch[j].@curbLength = x[7];
        F_swmm5.Subcatch[j].@nPervPattern = (-1);
        F_swmm5.Subcatch[j].@dStorePattern = (-1);
        F_swmm5.Subcatch[j].@infilPattern = (-1);
        if (R.dge(x[8], 0)) {
            if (!(F_snow.snow_createSnowpack(j, R.D2I(x[8])) != 0)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
        }
        return 0;
    }

    // subcatch.c:196
    static int subcatch_readSubareaParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        double[] x = new double[7];
        if ((ntoks < 7)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (i = 0; (i < 5); i++) {
            if (((!(F_input.getDouble(tok[(i + 1)], new DElem(x, i)) != 0)) || (x[i] < 0.0d))) {
                return F_error.error_setInpError(ERR_NAME, tok[(i + 1)]);
            }
        }
        m = F_input.findmatch(tok[6], RunoffRoutingWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[6]);
        }
        x[5] = m;
        x[6] = 1.0d;
        if ((ntoks >= 8)) {
            if ((((!(F_input.getDouble(tok[7], new DElem(x, 6)) != 0)) || (x[6] < 0.0d)) || (R.dgt(x[6], 100.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
            x[6] = (x[6] / 100.0d);
        }
        F_swmm5.Subcatch[j].subArea[IMPERV0].@N = x[0];
        F_swmm5.Subcatch[j].subArea[IMPERV1].@N = x[0];
        F_swmm5.Subcatch[j].subArea[PERV].@N = x[1];
        F_swmm5.Subcatch[j].subArea[IMPERV0].@dStore = 0.0d;
        F_swmm5.Subcatch[j].subArea[IMPERV1].@dStore = (x[2] / F_swmm5.UCF(RAINDEPTH));
        F_swmm5.Subcatch[j].subArea[PERV].@dStore = (x[3] / F_swmm5.UCF(RAINDEPTH));
        F_swmm5.Subcatch[j].subArea[IMPERV0].@fArea = ((F_swmm5.Subcatch[j].fracImperv * x[4]) / 100.0d);
        F_swmm5.Subcatch[j].subArea[IMPERV1].@fArea = (F_swmm5.Subcatch[j].fracImperv * ((1.0d - (x[4] / 100.0d))));
        F_swmm5.Subcatch[j].subArea[PERV].@fArea = ((1.0d - F_swmm5.Subcatch[j].fracImperv));
        L_f2: for (i = IMPERV0; (i <= PERV); i++) {
            F_swmm5.Subcatch[j].subArea[i].@routeTo = TO_OUTLET;
            F_swmm5.Subcatch[j].subArea[i].@fOutlet = 1.0d;
        }
        k = R.D2I(x[5]);
        if (((F_swmm5.Subcatch[j].fracImperv == 0.0d) || (F_swmm5.Subcatch[j].fracImperv == 1.0d))) {
            k = TO_OUTLET;
        }
        if (((k == TO_IMPERV) && (F_swmm5.Subcatch[j].fracImperv != 0.0d))) {
            F_swmm5.Subcatch[j].subArea[PERV].@routeTo = k;
            F_swmm5.Subcatch[j].subArea[PERV].@fOutlet = (1.0d - x[6]);
        }
        if ((k == TO_PERV)) {
            F_swmm5.Subcatch[j].subArea[IMPERV0].@routeTo = k;
            F_swmm5.Subcatch[j].subArea[IMPERV1].@routeTo = k;
            F_swmm5.Subcatch[j].subArea[IMPERV0].@fOutlet = (1.0d - x[6]);
            F_swmm5.Subcatch[j].subArea[IMPERV1].@fOutlet = (1.0d - x[6]);
        }
        return 0;
    }

    // subcatch.c:284
    static int subcatch_readLanduseParams(CPtr[] tok, int ntoks) {
        int j = 0;
        int k = 0;
        int m = 0;
        DBox f = new DBox(0.0d);
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (k = 2; (k <= ntoks); k = (k + 2)) {
            m = F_project.project_findObject(LANDUSE, tok[(k - 1)]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[(k - 1)]);
            }
            if (((k + 1) > ntoks)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[k], f) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[k]);
            }
            F_swmm5.Subcatch[j].landFactor[m].@fraction = (f.v / 100.0d);
        }
        return 0;
    }

    // subcatch.c:324
    static int subcatch_readInitBuildup(CPtr[] tok, int ntoks) {
        int j = 0;
        int k = 0;
        int m = 0;
        DBox x = new DBox(0.0d);
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (k = 2; (k <= ntoks); k = (k + 2)) {
            m = F_project.project_findObject(POLLUT, tok[(k - 1)]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[(k - 1)]);
            }
            if (((k + 1) > ntoks)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[k], x) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[k]);
            }
            F_swmm5.Subcatch[j].initBuildup[m] = x.v;
        }
        return 0;
    }

    // subcatch.c:364
    static void subcatch_validate(int j) {
        int i = 0;
        double area = 0.0d;
        double nonLidArea = F_swmm5.Subcatch[j].area;
        if (((F_swmm5.Subcatch[j].outNode >= 0) && (F_swmm5.Subcatch[j].outSubcatch >= 0))) {
            F_report.report_writeErrorMsg(ERR_SUBCATCH_OUTLET, F_swmm5.Subcatch[j].ID);
        }
        F_gwater.gwater_validate(j);
        nonLidArea -= F_swmm5.Subcatch[j].lidArea;
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            if ((i == PERV)) {
                area = (((1.0d - F_swmm5.Subcatch[j].fracImperv)) * nonLidArea);
            } else {
                area = (F_swmm5.Subcatch[j].fracImperv * nonLidArea);
            }
            F_swmm5.Subcatch[j].subArea[i].@alpha = 0.0d;
            if (((R.dgt(area, 0.0d)) && (R.dgt(F_swmm5.Subcatch[j].subArea[i].N, 0.0d)))) {
                F_swmm5.Subcatch[j].subArea[i].@alpha = ((((MCOEFF * F_swmm5.Subcatch[j].width) / area) * Math.sqrt(F_swmm5.Subcatch[j].slope)) / F_swmm5.Subcatch[j].subArea[i].N);
            }
        }
        i = F_swmm5.Subcatch[j].gage;
        if ((i >= 0)) {
            F_swmm5.Gage[i].@isUsed = 1;
        }
    }

    // subcatch.c:419
    static void subcatch_initState(int j) {
        int i = 0;
        F_swmm5.Subcatch[j].@rainfall = 0.0d;
        F_swmm5.Subcatch[j].@oldRunoff = 0.0d;
        F_swmm5.Subcatch[j].@newRunoff = 0.0d;
        F_swmm5.Subcatch[j].@oldSnowDepth = 0.0d;
        F_swmm5.Subcatch[j].@newSnowDepth = 0.0d;
        F_swmm5.Subcatch[j].@runon = 0.0d;
        F_swmm5.Subcatch[j].@evapLoss = 0.0d;
        F_swmm5.Subcatch[j].@infilLoss = 0.0d;
        if ((F_swmm5.Subcatch[j].infil == j)) {
            F_infil.infil_initState(j);
        }
        if ((F_swmm5.Subcatch[j].groundwater != null)) {
            F_gwater.gwater_initState(j);
        }
        if ((F_swmm5.Subcatch[j].snowpack != null)) {
            F_snow.snow_initSnowpack(j);
        }
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            F_swmm5.Subcatch[j].subArea[i].@depth = 0.0d;
            F_swmm5.Subcatch[j].subArea[i].@inflow = 0.0d;
            F_swmm5.Subcatch[j].subArea[i].@runoff = 0.0d;
        }
        F_surfqual.surfqual_initState(j);
    }

    // subcatch.c:457
    static void subcatch_setOldState(int j) {
        int i = 0;
        F_swmm5.Subcatch[j].@oldRunoff = F_swmm5.Subcatch[j].newRunoff;
        F_swmm5.Subcatch[j].@oldSnowDepth = F_swmm5.Subcatch[j].newSnowDepth;
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            F_swmm5.Subcatch[j].subArea[i].@inflow = 0.0d;
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
            F_swmm5.Subcatch[j].oldQual[i] = F_swmm5.Subcatch[j].newQual[i];
            F_swmm5.Subcatch[j].newQual[i] = 0.0d;
        }
        F_lid.lid_setOldGroupState(j);
    }

    // subcatch.c:481
    static double subcatch_getFracPerv(int j) {
        double fracPerv = (1.0d - F_swmm5.Subcatch[j].fracImperv);
        if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
            fracPerv = ((((fracPerv * ((F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea))) + F_lid.lid_getPervArea(j))) / F_swmm5.Subcatch[j].area);
            fracPerv = (((((fracPerv) <= (1.0d))) ? (fracPerv) : (1.0d)));
        }
        return fracPerv;
    }

    // subcatch.c:502
    static double subcatch_getStorage(int j) {
        int i = 0;
        double v = 0.0d;
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            v += (F_swmm5.Subcatch[j].subArea[i].depth * F_swmm5.Subcatch[j].subArea[i].fArea);
        }
        return ((v * ((F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea))) + F_lid.lid_getStoredVolume(j));
    }

    // subcatch.c:523
    static void subcatch_getRunon(int j) {
        int k = 0;
        int p = 0;
        double q = 0.0d;
        double q1 = 0.0d;
        double q2 = 0.0d;
        double pervArea = 0.0d;
        k = F_swmm5.Subcatch[j].outSubcatch;
        q = F_swmm5.Subcatch[j].oldRunoff;
        if (((k >= 0) && (k != j))) {
            subcatch_addRunonFlow(k, q);
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                F_swmm5.Subcatch[k].newQual[p] += ((q * F_swmm5.Subcatch[j].oldQual[p]) * 28.317d);
            }
        }
        if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
            F_lid.lid_addDrainRunon(j);
        }
        if (((F_swmm5.Subcatch[j].fracImperv < 1.0d) && (F_swmm5.Subcatch[j].subArea[IMPERV0].routeTo == TO_PERV))) {
            q1 = (F_swmm5.Subcatch[j].subArea[IMPERV0].runoff * F_swmm5.Subcatch[j].subArea[IMPERV0].fArea);
            q2 = (F_swmm5.Subcatch[j].subArea[IMPERV1].runoff * F_swmm5.Subcatch[j].subArea[IMPERV1].fArea);
            q = (q1 + q2);
            F_swmm5.Subcatch[j].subArea[PERV].@inflow = (F_swmm5.Subcatch[j].subArea[PERV].inflow + ((q * ((1.0d - F_swmm5.Subcatch[j].subArea[IMPERV0].fOutlet))) / F_swmm5.Subcatch[j].subArea[PERV].fArea));
        }
        if ((((R.dgt(F_swmm5.Subcatch[j].fracImperv, 0.0d)) && (F_swmm5.Subcatch[j].subArea[PERV].routeTo == TO_IMPERV)) && (R.dgt(F_swmm5.Subcatch[j].subArea[IMPERV1].fArea, 0.0d)))) {
            q = F_swmm5.Subcatch[j].subArea[PERV].runoff;
            F_swmm5.Subcatch[j].subArea[IMPERV1].@inflow = (F_swmm5.Subcatch[j].subArea[IMPERV1].inflow + (((q * ((1.0d - F_swmm5.Subcatch[j].subArea[PERV].fOutlet))) * F_swmm5.Subcatch[j].subArea[PERV].fArea) / F_swmm5.Subcatch[j].subArea[IMPERV1].fArea));
        }
        if (((R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) && (F_swmm5.Subcatch[j].fracImperv < 1.0d))) {
            pervArea = (F_swmm5.Subcatch[j].subArea[PERV].fArea * ((F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea)));
            q = F_lid.lid_getFlowToPerv(j);
            if (R.dgt(pervArea, 0.0d)) {
                F_swmm5.Subcatch[j].subArea[PERV].@inflow = (F_swmm5.Subcatch[j].subArea[PERV].inflow + (q / pervArea));
            }
        }
    }

    // subcatch.c:600
    static void subcatch_addRunonFlow(int k, double q) {
        int i = 0;
        double nonLidArea = 0.0d;
        if ((F_swmm5.Subcatch[k].area <= 0.0d)) {
            return;
        }
        nonLidArea = (F_swmm5.Subcatch[k].area - F_swmm5.Subcatch[k].lidArea);
        if (R.dgt(nonLidArea, 0.0d)) {
            q = (q / nonLidArea);
        } else {
            q = (q / F_swmm5.Subcatch[k].area);
        }
        F_swmm5.Subcatch[k].@runon = (F_swmm5.Subcatch[k].runon + q);
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            F_swmm5.Subcatch[k].subArea[i].@inflow = (F_swmm5.Subcatch[k].subArea[i].inflow + q);
        }
    }

    // subcatch.c:629
    static double subcatch_getRunoff(int j, double tStep) {
        int i = 0;
        double nonLidArea = 0.0d;
        double area = 0.0d;
        double[] netPrecip = new double[3];
        double vRain = 0.0d;
        double vRunon = 0.0d;
        double vOutflow = 0.0d;
        double runoff = 0.0d;
        double evapRate = 0.0d;
        double subAreaRunoff = 0.0d;
        double vImpervRunoff = 0.0d;
        double vPervRunoff = 0.0d;
        Vevap = 0.0d;
        Vpevap = 0.0d;
        Vinfil = 0.0d;
        Voutflow = 0.0d;
        VlidIn = 0.0d;
        VlidInfil = 0.0d;
        VlidOut = 0.0d;
        VlidDrain = 0.0d;
        VlidReturn = 0.0d;
        nonLidArea = (F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea);
        vRunon = ((F_swmm5.Subcatch[j].runon * tStep) * nonLidArea);
        Vinflow = (vRunon + (subcatch_getDepth(j) * nonLidArea));
        if ((nonLidArea == 0.0d)) {
            vRunon = ((F_swmm5.Subcatch[j].runon * tStep) * F_swmm5.Subcatch[j].area);
        }
        getNetPrecip(j, netPrecip, tStep);
        if (((F_swmm5.Evap.dryOnly != 0) && (R.dgt(F_swmm5.Subcatch[j].rainfall, 0.0d)))) {
            evapRate = 0.0d;
        } else {
            evapRate = F_swmm5.Evap.rate;
        }
        F_infil.infil_setInfilFactor(j);
        if (R.dgt(nonLidArea, 0.0d)) {
            L_f1: for (i = IMPERV0; (i <= PERV); i++) {
                area = (nonLidArea * F_swmm5.Subcatch[j].subArea[i].fArea);
                F_swmm5.Subcatch[j].subArea[i].@runoff = getSubareaRunoff(j, i, area, netPrecip[i], evapRate, tStep);
                subAreaRunoff = (F_swmm5.Subcatch[j].subArea[i].runoff * area);
                if ((i == PERV)) {
                    vPervRunoff = (subAreaRunoff * tStep);
                } else {
                    vImpervRunoff += (subAreaRunoff * tStep);
                }
                runoff += subAreaRunoff;
            }
        }
        if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
            F_lid.lid_getRunoff(j, tStep);
        }
        if (((!(F_swmm5.IgnoreGwater != 0)) && (F_swmm5.Subcatch[j].groundwater != null))) {
            F_gwater.gwater_getGroundwater(j, Vpevap, (Vinfil + VlidInfil), tStep);
        }
        area = F_swmm5.Subcatch[j].area;
        F_swmm5.Subcatch[j].@evapLoss = ((Vevap / tStep) / area);
        F_swmm5.Subcatch[j].@infilLoss = ((((Vinfil + VlidInfil)) / tStep) / area);
        vOutflow = ((Voutflow - VlidIn) + VlidOut);
        F_swmm5.Subcatch[j].@newRunoff = (vOutflow / tStep);
        vRain = ((F_swmm5.Subcatch[j].rainfall * tStep) * area);
        F_stats.stats_updateSubcatchStats(j, vRain, vRunon, Vevap, (Vinfil + VlidInfil), vImpervRunoff, vPervRunoff, (vOutflow + VlidDrain), (F_swmm5.Subcatch[j].newRunoff + (VlidDrain / tStep)));
        if (((F_swmm5.Subcatch[j].outNode == (-1)) && (F_swmm5.Subcatch[j].outSubcatch != j))) {
            vOutflow = 0.0d;
        }
        F_massbal.massbal_updateRunoffTotals(RUNOFF_RAINFALL, vRain);
        F_massbal.massbal_updateRunoffTotals(RUNOFF_EVAP, Vevap);
        F_massbal.massbal_updateRunoffTotals(RUNOFF_INFIL, (Vinfil + VlidInfil));
        F_massbal.massbal_updateRunoffTotals(RUNOFF_RUNOFF, vOutflow);
        return (runoff / area);
    }

    // subcatch.c:758
    static void getNetPrecip(int j, double[] netPrecip, double tStep) {
        int i = 0;
        int k = 0;
        DBox rainfall = new DBox(0.0d);
        DBox snowfall = new DBox(0.0d);
        k = F_swmm5.Subcatch[j].gage;
        if ((k >= 0)) {
            F_gage.gage_getPrecip(k, rainfall, snowfall);
        }
        F_swmm5.Subcatch[j].@rainfall = (rainfall.v + snowfall.v);
        if (((F_swmm5.Subcatch[j].snowpack != null) && (!(F_swmm5.IgnoreSnowmelt != 0)))) {
            F_swmm5.Subcatch[j].@newSnowDepth = F_snow.snow_getSnowMelt(j, rainfall.v, snowfall.v, tStep, netPrecip);
        } else {
            L_f1: for (i = IMPERV0; (i <= PERV); i++) {
                netPrecip[i] = (rainfall.v + snowfall.v);
            }
        }
    }

    // subcatch.c:798
    static double subcatch_getDepth(int j) {
        int i = 0;
        double fArea = 0.0d;
        double depth = 0.0d;
        L_f1: for (i = IMPERV0; (i <= PERV); i++) {
            fArea = F_swmm5.Subcatch[j].subArea[i].fArea;
            if (R.dgt(fArea, 0.0d)) {
                depth += (F_swmm5.Subcatch[j].subArea[i].depth * fArea);
            }
        }
        return depth;
    }

    // subcatch.c:820
    static double subcatch_getWtdOutflow(int j, double f) {
        if ((F_swmm5.Subcatch[j].area == 0.0d)) {
            return 0.0d;
        }
        return ((((1.0d - f)) * F_swmm5.Subcatch[j].oldRunoff) + (f * F_swmm5.Subcatch[j].newRunoff));
    }

    // subcatch.c:834
    static void subcatch_getResults(int j, double f, float[] x) {
        int p = 0;
        int k = 0;
        double f1 = (1.0d - f);
        double z = 0.0d;
        double runoff = 0.0d;
        TGroundwater gw = null;
        k = F_swmm5.Subcatch[j].gage;
        if ((k >= 0)) {
            x[SUBCATCH_RAINFALL] = ((float) (F_swmm5.Gage[k].reportRainfall));
        } else {
            x[SUBCATCH_RAINFALL] = 0.0f;
        }
        z = ((((f1 * F_swmm5.Subcatch[j].oldSnowDepth) + (f * F_swmm5.Subcatch[j].newSnowDepth))) * F_swmm5.UCF(RAINDEPTH));
        x[SUBCATCH_SNOWDEPTH] = ((float) (z));
        x[SUBCATCH_EVAP] = ((float) (((F_swmm5.Subcatch[j].evapLoss * F_swmm5.UCF(EVAPRATE)))));
        x[SUBCATCH_INFIL] = ((float) (((F_swmm5.Subcatch[j].infilLoss * F_swmm5.UCF(RAINFALL)))));
        runoff = ((f1 * F_swmm5.Subcatch[j].oldRunoff) + (f * F_swmm5.Subcatch[j].newRunoff));
        if (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)) {
            runoff += ((f1 * F_lid.lid_getDrainFlow(j, PREVIOUS)) + (f * F_lid.lid_getDrainFlow(j, CURRENT)));
        }
        if ((runoff < (2.31481e-08d * F_swmm5.Subcatch[j].area))) {
            runoff = 0.0d;
        }
        x[SUBCATCH_RUNOFF] = ((float) (((runoff * F_swmm5.UCF(FLOW)))));
        gw = F_swmm5.Subcatch[j].groundwater;
        if ((gw != null)) {
            z = (((((f1 * gw.oldFlow) + (f * gw.newFlow))) * F_swmm5.Subcatch[j].area) * F_swmm5.UCF(FLOW));
            x[SUBCATCH_GW_FLOW] = ((float) (z));
            z = (((gw.bottomElev + gw.lowerDepth)) * F_swmm5.UCF(LENGTH));
            x[SUBCATCH_GW_ELEV] = ((float) (z));
            z = gw.theta;
            x[SUBCATCH_SOIL_MOIST] = ((float) (z));
        } else {
            x[SUBCATCH_GW_FLOW] = 0.0f;
            x[SUBCATCH_GW_ELEV] = 0.0f;
            x[SUBCATCH_SOIL_MOIST] = 0.0f;
        }
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                if ((runoff == 0.0d)) {
                    z = 0.0d;
                } else {
                    z = ((f1 * F_swmm5.Subcatch[j].oldQual[p]) + (f * F_swmm5.Subcatch[j].newQual[p]));
                }
                x[(SUBCATCH_WASHOFF + p)] = ((float) (z));
            }
        }
    }

    // subcatch.c:907
    static double getSubareaRunoff(int j, int i, double area, double precip, double evap, double tStep) {
        DBox tRunoff = new DBox(0.0d);
        double surfMoisture = 0.0d;
        double surfEvap = 0.0d;
        double infil = 0.0d;
        double runoff = 0.0d;
        TSubarea subarea = null;
        if ((area == 0.0d)) {
            return 0.0d;
        }
        subarea = F_swmm5.Subcatch[j].subArea[i];
        tRunoff.@v = tStep;
        surfMoisture = (subarea.depth / tStep);
        surfEvap = (((((surfMoisture) <= (evap))) ? (surfMoisture) : (evap)));
        if ((i == PERV)) {
            infil = getSubareaInfil(j, subarea, precip, tStep);
        }
        subarea.@inflow = (subarea.inflow + precip);
        surfMoisture += subarea.inflow;
        Vinflow += ((precip * area) * tStep);
        Vevap += ((surfEvap * area) * tStep);
        if ((i == PERV)) {
            Vpevap += Vevap;
        }
        Vinfil += ((infil * area) * tStep);
        Alpha = subarea.alpha;
        Dstore = subarea.dStore;
        adjustSubareaParams(i, j);
        if (R.dge((surfEvap + infil), surfMoisture)) {
            subarea.@depth = 0.0d;
        } else {
            subarea.@inflow = (subarea.inflow - (surfEvap + infil));
            updatePondedDepth(subarea, tRunoff);
        }
        runoff = findSubareaRunoff(subarea, tRunoff.v);
        Voutflow += (((subarea.fOutlet * runoff) * area) * tStep);
        return runoff;
    }

    // subcatch.c:985
    static double getSubareaInfil(int j, TSubarea subarea, double precip, double tStep) {
        double infil = 0.0d;
        infil = F_infil.infil_getInfil(j, tStep, precip, subarea.inflow, subarea.depth);
        if (((!(F_swmm5.IgnoreGwater != 0)) && (F_swmm5.Subcatch[j].groundwater != null))) {
            infil = (((((infil) <= ((F_swmm5.Subcatch[j].groundwater.maxInfilVol / tStep)))) ? (infil) : ((F_swmm5.Subcatch[j].groundwater.maxInfilVol / tStep))));
        }
        return infil;
    }

    // subcatch.c:1013
    static double findSubareaRunoff(TSubarea subarea, double tRunoff) {
        double xDepth = (subarea.depth - Dstore);
        double runoff = 0.0d;
        if (R.dgt(xDepth, 1e-10d)) {
            if (R.dgt(subarea.N, 0.0d)) {
                runoff = (Alpha * Glibm.pow(xDepth, MEXP));
            } else {
                runoff = (xDepth / tRunoff);
                subarea.@depth = Dstore;
            }
        } else {
            runoff = 0.0d;
        }
        return runoff;
    }

    // subcatch.c:1048
    static void updatePondedDepth(TSubarea subarea, DRef dt) {
        double ix = subarea.inflow;
        double dx = 0.0d;
        double tx = dt.get();
        if (((subarea.depth + (ix * tx)) <= Dstore)) {
            subarea.@depth = (subarea.depth + (ix * tx));
        } else {
            dx = (Dstore - subarea.depth);
            if (((R.dgt(dx, 0.0d)) && (R.dgt(ix, 0.0d)))) {
                tx -= (dx / ix);
                subarea.@depth = Dstore;
            }
            if (((R.dgt(Alpha, 0.0d)) && (R.dgt(tx, 0.0d)))) {
                theSubarea = subarea;
                double[] __a1 = new double[] { subarea.depth };
                Overrides.odesolve_integrate(__a1, 1, 0, tx, ODETOL, tx, F_subcatch::getDdDt);
                subarea.@depth = __a1[0];
            } else {
                if ((tx < 0.0d)) {
                    tx = 0.0d;
                }
                subarea.@depth = (subarea.depth + (ix * tx));
            }
        }
        if ((subarea.depth < 0.0d)) {
            subarea.@depth = 0.0d;
        }
        dt.set(tx);
    }

    // subcatch.c:1101
    static void getDdDt(double t, double[] d, double[] dddt) {
        double ix = theSubarea.inflow;
        double rx = (d[0] - Dstore);
        if ((rx < 0.0d)) {
            rx = 0.0d;
        } else {
            rx = (Alpha * Glibm.pow(rx, MEXP));
        }
        dddt[0] = (ix - rx);
    }

    // subcatch.c:1125
    static void adjustSubareaParams(int i, int j) {
        int p = 0;
        int m = 0;
        double f = 0.0d;
        if ((i == PERV)) {
            p = F_swmm5.Subcatch[j].dStorePattern;
            if (((p >= 0) && (F_swmm5.Pattern[p].type == MONTHLY_PATTERN))) {
                m = (F_datetime.datetime_monthOfYear(F_swmm5.getDateTime(F_swmm5.OldRunoffTime)) - 1);
                f = F_swmm5.Pattern[p].factor[m];
                if (R.dge(f, 0.0d)) {
                    Dstore *= f;
                }
            }
            p = F_swmm5.Subcatch[j].nPervPattern;
            if (((p >= 0) && (F_swmm5.Pattern[p].type == MONTHLY_PATTERN))) {
                m = (F_datetime.datetime_monthOfYear(F_swmm5.getDateTime(F_swmm5.OldRunoffTime)) - 1);
                f = F_swmm5.Pattern[p].factor[m];
                if ((f <= 0.0d)) {
                    Alpha = 0.0d;
                } else {
                    Alpha = (Alpha / f);
                }
            }
        }
    }

}
