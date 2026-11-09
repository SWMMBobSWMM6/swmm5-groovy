// Generated from EPA SWMM 5.2.4 (gwater.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_gwater {
    public static double GWTOL = 0.0001d;
    public static double XTOL = 0.001d;
    public static CPtr[] GWVarWords = new CPtr[] { L707, L708, L709, L710, L711, L712, L713, L714, L715, L716, L717, null };
    public static double Area = 0.0d;
    public static double gwater_Infil = 0.0d;
    public static double MaxEvap = 0.0d;
    public static double AvailEvap = 0.0d;
    public static double UpperEvap = 0.0d;
    public static double LowerEvap = 0.0d;
    public static double UpperPerc = 0.0d;
    public static double LowerLoss = 0.0d;
    public static double GWFlow = 0.0d;
    public static double MaxUpperPerc = 0.0d;
    public static double MaxGWFlowPos = 0.0d;
    public static double MaxGWFlowNeg = 0.0d;
    public static double FracPerv = 0.0d;
    public static double TotalDepth = 0.0d;
    public static double Theta = 0.0d;
    public static double HydCon = 0.0d;
    public static double Hgw = 0.0d;
    public static double Hstar = 0.0d;
    public static double Hsw = 0.0d;
    public static double gwater_Tstep = 0.0d;
    public static TAquifer A = new TAquifer();
    public static TGroundwater GW = null;
    public static MathExpr LatFlowExpr = null;
    public static MathExpr DeepFlowExpr = null;

    // gwater.c:119
    static int gwater_readAquiferParams(int j, CPtr[] tok, int ntoks) {
        int i = 0;
        int p = 0;
        double[] x = new double[12];
        CPtr id = null;
        if ((ntoks < 13)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(AQUIFER, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (i = 0; (i < 11); i++) {
            x[i] = 0.0d;
        }
        L_f2: for (i = 1; (i < 13); i++) {
            if (!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        p = (-1);
        if ((ntoks > 13)) {
            p = F_project.project_findObject(TIMEPATTERN, tok[13]);
            if ((p < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[13]);
            }
        }
        F_swmm5.Aquifer[j].@ID = id;
        F_swmm5.Aquifer[j].@porosity = x[0];
        F_swmm5.Aquifer[j].@wiltingPoint = x[1];
        F_swmm5.Aquifer[j].@fieldCapacity = x[2];
        F_swmm5.Aquifer[j].@conductivity = (x[3] / F_swmm5.UCF(RAINFALL));
        F_swmm5.Aquifer[j].@conductSlope = x[4];
        F_swmm5.Aquifer[j].@tensionSlope = (x[5] / F_swmm5.UCF(LENGTH));
        F_swmm5.Aquifer[j].@upperEvapFrac = x[6];
        F_swmm5.Aquifer[j].@lowerEvapDepth = (x[7] / F_swmm5.UCF(LENGTH));
        F_swmm5.Aquifer[j].@lowerLossCoeff = (x[8] / F_swmm5.UCF(RAINFALL));
        F_swmm5.Aquifer[j].@bottomElev = (x[9] / F_swmm5.UCF(LENGTH));
        F_swmm5.Aquifer[j].@waterTableElev = (x[10] / F_swmm5.UCF(LENGTH));
        F_swmm5.Aquifer[j].@upperMoisture = x[11];
        F_swmm5.Aquifer[j].@upperEvapPat = p;
        return 0;
    }

    // gwater.c:179
    static int gwater_readGroundwaterParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        double[] x = new double[11];
        TGroundwater gw = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((ntoks < 11)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        k = F_project.project_findObject(AQUIFER, tok[1]);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n = F_project.project_findObject(NODE, tok[2]);
        if ((n < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        L_f1: for (i = 0; (i < 7); i++) {
            if (!(F_input.getDouble(tok[(i + 3)], new DElem(x, i)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(i + 3)]);
            }
        }
        L_f2: for (i = 7; (i < 11); i++) {
            x[i] = (-10000000000.0d);
            m = (i + 3);
            if (((ntoks > m) && (tok[m].g(0) != 42))) {
                if (!(F_input.getDouble(tok[m], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[m]);
                }
                if ((i < 10)) {
                    x[i] = (x[i] / (F_swmm5.UCF(LENGTH)));
                }
            }
        }
        if (!(F_swmm5.Subcatch[j].groundwater != null)) {
            gw = new TGroundwater();
            if (!(gw != null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            F_swmm5.Subcatch[j].@groundwater = gw;
        } else {
            gw = F_swmm5.Subcatch[j].groundwater;
        }
        gw.@aquifer = k;
        gw.@node = n;
        gw.@surfElev = (x[0] / F_swmm5.UCF(LENGTH));
        gw.@a1 = x[1];
        gw.@b1 = x[2];
        gw.@a2 = x[3];
        gw.@b2 = x[4];
        gw.@a3 = x[5];
        gw.@fixedDepth = (x[6] / F_swmm5.UCF(LENGTH));
        gw.@nodeElev = x[7];
        gw.@bottomElev = x[8];
        gw.@waterTableElev = x[9];
        gw.@upperMoisture = x[10];
        return 0;
    }

    // gwater.c:258
    static int gwater_readFlowExpression(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        CPtr exprStr = R.buf(1025);
        MathExpr expr = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = 1;
        if ((F_input.match(tok[1], L158) != 0)) {
            k = 1;
        } else if ((F_input.match(tok[1], L159) != 0)) {
            k = 2;
        } else {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        Overrides.sstrncpy(exprStr, tok[2], 1024);
        L_f1: for (i = 3; (i < ntoks); i++) {
            Overrides.sstrcat(exprStr, L33, (1024 + 1));
            Overrides.sstrcat(exprStr, tok[i], (1024 + 1));
        }
        if ((k == 1)) {
            F_mathexpr.mathexpr_delete(F_swmm5.Subcatch[j].gwLatFlowExpr);
        } else {
            F_mathexpr.mathexpr_delete(F_swmm5.Subcatch[j].gwDeepFlowExpr);
        }
        expr = F_mathexpr.mathexpr_create(exprStr, F_gwater::gwater_getVariableIndex);
        if ((expr == null)) {
            return F_error.error_setInpError(ERR_MATH_EXPR, L0);
        }
        if ((k == 1)) {
            F_swmm5.Subcatch[j].@gwLatFlowExpr = expr;
        } else {
            F_swmm5.Subcatch[j].@gwDeepFlowExpr = expr;
        }
        return 0;
    }

    // gwater.c:315
    static void gwater_deleteFlowExpression(int j) {
        F_mathexpr.mathexpr_delete(F_swmm5.Subcatch[j].gwLatFlowExpr);
        F_mathexpr.mathexpr_delete(F_swmm5.Subcatch[j].gwDeepFlowExpr);
    }

    // gwater.c:328
    static void gwater_validateAquifer(int j) {
        int p = 0;
        if ((((((((((((F_swmm5.Aquifer[j].porosity <= 0.0d) || (R.dge(F_swmm5.Aquifer[j].fieldCapacity, F_swmm5.Aquifer[j].porosity))) || (R.dge(F_swmm5.Aquifer[j].wiltingPoint, F_swmm5.Aquifer[j].fieldCapacity))) || (F_swmm5.Aquifer[j].conductivity <= 0.0d)) || (F_swmm5.Aquifer[j].conductSlope < 0.0d)) || (F_swmm5.Aquifer[j].tensionSlope < 0.0d)) || (F_swmm5.Aquifer[j].upperEvapFrac < 0.0d)) || (F_swmm5.Aquifer[j].lowerEvapDepth < 0.0d)) || (F_swmm5.Aquifer[j].waterTableElev < F_swmm5.Aquifer[j].bottomElev)) || (R.dgt(F_swmm5.Aquifer[j].upperMoisture, F_swmm5.Aquifer[j].porosity))) || (F_swmm5.Aquifer[j].upperMoisture < F_swmm5.Aquifer[j].wiltingPoint))) {
            F_report.report_writeErrorMsg(ERR_AQUIFER_PARAMS, F_swmm5.Aquifer[j].ID);
        }
        p = F_swmm5.Aquifer[j].upperEvapPat;
        if (((p >= 0) && (F_swmm5.Pattern[p].type != MONTHLY_PATTERN))) {
            F_report.report_writeErrorMsg(ERR_AQUIFER_PARAMS, F_swmm5.Aquifer[j].ID);
        }
    }

    // gwater.c:359
    static void gwater_validate(int j) {
        TAquifer a = new TAquifer();
        TGroundwater gw = null;
        gw = F_swmm5.Subcatch[j].groundwater;
        if ((gw != null)) {
            a = F_swmm5.Aquifer[gw.aquifer].copy();
            if ((gw.bottomElev == (-10000000000.0d))) {
                gw.@bottomElev = a.bottomElev;
            }
            if ((gw.waterTableElev == (-10000000000.0d))) {
                gw.@waterTableElev = a.waterTableElev;
            }
            if ((gw.upperMoisture == (-10000000000.0d))) {
                gw.@upperMoisture = a.upperMoisture;
            }
            if ((gw.surfElev < gw.waterTableElev)) {
                F_report.report_writeErrorMsg(ERR_GROUND_ELEV, F_swmm5.Subcatch[j].ID);
            }
        }
    }

    // gwater.c:382
    static void gwater_initState(int j) {
        TAquifer a = new TAquifer();
        TGroundwater gw = null;
        gw = F_swmm5.Subcatch[j].groundwater;
        if ((gw != null)) {
            a = F_swmm5.Aquifer[gw.aquifer].copy();
            gw.@theta = gw.upperMoisture;
            if (R.dge(gw.theta, a.porosity)) {
                gw.@theta = (a.porosity - XTOL);
            }
            gw.@lowerDepth = (gw.waterTableElev - gw.bottomElev);
            if (R.dge(gw.lowerDepth, (gw.surfElev - gw.bottomElev))) {
                gw.@lowerDepth = ((gw.surfElev - gw.bottomElev) - XTOL);
            }
            gw.@oldFlow = 0.0d;
            gw.@newFlow = 0.0d;
            gw.@evapLoss = 0.0d;
            gw.@maxInfilVol = ((((gw.surfElev - gw.waterTableElev)) * ((a.porosity - gw.theta))) / F_subcatch.subcatch_getFracPerv(j));
        }
    }

    // gwater.c:425
    static void gwater_getState(int j, double[] x) {
        TGroundwater gw = F_swmm5.Subcatch[j].groundwater;
        x[0] = gw.theta;
        x[1] = (gw.bottomElev + gw.lowerDepth);
        x[2] = gw.newFlow;
        x[3] = gw.maxInfilVol;
    }

    // gwater.c:441
    static void gwater_setState(int j, double[] x) {
        TGroundwater gw = F_swmm5.Subcatch[j].groundwater;
        if ((gw == null)) {
            return;
        }
        gw.@theta = x[0];
        gw.@lowerDepth = (x[1] - gw.bottomElev);
        gw.@oldFlow = x[2];
        if ((x[3] != (-10000000000.0d))) {
            gw.@maxInfilVol = x[3];
        }
    }

    // gwater.c:458
    static double gwater_getVolume(int j) {
        TAquifer a = new TAquifer();
        TGroundwater gw = null;
        double upperDepth = 0.0d;
        gw = F_swmm5.Subcatch[j].groundwater;
        if ((gw == null)) {
            return 0.0d;
        }
        a = F_swmm5.Aquifer[gw.aquifer].copy();
        upperDepth = ((gw.surfElev - gw.bottomElev) - gw.lowerDepth);
        return (((upperDepth * gw.theta)) + ((gw.lowerDepth * a.porosity)));
    }

    // gwater.c:477
    static void gwater_getGroundwater(int j, double evap, double infil, double tStep) {
        int n = 0;
        double[] x = new double[2];
        double vUpper = 0.0d;
        double nodeFlow = 0.0d;
        GW = F_swmm5.Subcatch[j].groundwater;
        if ((GW == null)) {
            return;
        }
        LatFlowExpr = F_swmm5.Subcatch[j].gwLatFlowExpr;
        DeepFlowExpr = F_swmm5.Subcatch[j].gwDeepFlowExpr;
        A = F_swmm5.Aquifer[GW.aquifer].copy();
        FracPerv = F_subcatch.subcatch_getFracPerv(j);
        if ((FracPerv <= 0.0d)) {
            return;
        }
        Area = F_swmm5.Subcatch[j].area;
        infil = ((infil / Area) / tStep);
        gwater_Infil = infil;
        gwater_Tstep = tStep;
        evap = ((evap / Area) / tStep);
        MaxEvap = (F_swmm5.Evap.rate * FracPerv);
        AvailEvap = (((R.dge((((MaxEvap - evap))), (0.0d))) ? (((MaxEvap - evap))) : (0.0d)));
        TotalDepth = (GW.surfElev - GW.bottomElev);
        if ((TotalDepth <= 0.0d)) {
            return;
        }
        n = GW.node;
        if ((GW.nodeElev != (-10000000000.0d))) {
            Hstar = (GW.nodeElev - GW.bottomElev);
        } else {
            Hstar = (F_swmm5.Node_[n].invertElev - GW.bottomElev);
        }
        if (R.dgt(GW.fixedDepth, 0.0d)) {
            Hsw = ((GW.fixedDepth + F_swmm5.Node_[n].invertElev) - GW.bottomElev);
        } else {
            Hsw = ((F_swmm5.Node_[n].newDepth + F_swmm5.Node_[n].invertElev) - GW.bottomElev);
        }
        x[THETA] = GW.theta;
        x[LOWERDEPTH] = GW.lowerDepth;
        vUpper = (((TotalDepth - x[LOWERDEPTH])) * ((x[THETA] - A.fieldCapacity)));
        vUpper = (((R.dge((0.0d), (vUpper))) ? (0.0d) : (vUpper)));
        MaxUpperPerc = (vUpper / tStep);
        MaxGWFlowPos = ((x[LOWERDEPTH] * A.porosity) / tStep);
        MaxGWFlowNeg = ((((TotalDepth - x[LOWERDEPTH])) * ((A.porosity - x[THETA]))) / tStep);
        nodeFlow = (((F_swmm5.Node_[n].inflow + (F_swmm5.Node_[n].newVolume / tStep))) / Area);
        MaxGWFlowNeg = (-(((((MaxGWFlowNeg) <= (nodeFlow))) ? (MaxGWFlowNeg) : (nodeFlow))));
        Overrides.odesolve_integrate(x, 2, 0, tStep, GWTOL, tStep, F_gwater::getDxDt);
        x[THETA] = (((R.dge((x[THETA]), (A.wiltingPoint))) ? (x[THETA]) : (A.wiltingPoint)));
        if (R.dge(x[THETA], A.porosity)) {
            x[THETA] = (A.porosity - XTOL);
            x[LOWERDEPTH] = (TotalDepth - XTOL);
        }
        x[LOWERDEPTH] = (((R.dge((x[LOWERDEPTH]), (0.0d))) ? (x[LOWERDEPTH]) : (0.0d)));
        if (R.dge(x[LOWERDEPTH], TotalDepth)) {
            x[LOWERDEPTH] = (TotalDepth - XTOL);
        }
        GW.@theta = x[THETA];
        GW.@lowerDepth = x[LOWERDEPTH];
        getFluxes(GW.theta, GW.lowerDepth);
        GW.@oldFlow = GW.newFlow;
        GW.@newFlow = GWFlow;
        GW.@evapLoss = (UpperEvap + LowerEvap);
        GW.@maxInfilVol = ((((TotalDepth - x[LOWERDEPTH])) * ((A.porosity - x[THETA]))) / FracPerv);
        updateMassBal(Area, tStep);
        F_stats.stats_updateGwaterStats(j, infil, GW.evapLoss, GWFlow, LowerLoss, GW.theta, (GW.lowerDepth + GW.bottomElev), tStep);
    }

    // gwater.c:605
    static void updateMassBal(double area, double tStep) {
        double vInfil = 0.0d;
        double vUpperEvap = 0.0d;
        double vLowerEvap = 0.0d;
        double vLowerPerc = 0.0d;
        double vGwater = 0.0d;
        double ft2sec = (area * tStep);
        vInfil = (gwater_Infil * ft2sec);
        vUpperEvap = (UpperEvap * ft2sec);
        vLowerEvap = (LowerEvap * ft2sec);
        vLowerPerc = (LowerLoss * ft2sec);
        vGwater = ((0.5d * ((GW.oldFlow + GW.newFlow))) * ft2sec);
        F_massbal.massbal_updateGwaterTotals(vInfil, vUpperEvap, vLowerEvap, vLowerPerc, vGwater);
    }

    // gwater.c:631
    static void getFluxes(double theta, double lowerDepth) {
        double upperDepth = 0.0d;
        lowerDepth = (((R.dge((lowerDepth), (0.0d))) ? (lowerDepth) : (0.0d)));
        lowerDepth = (((((lowerDepth) <= (TotalDepth))) ? (lowerDepth) : (TotalDepth)));
        upperDepth = (TotalDepth - lowerDepth);
        Hgw = lowerDepth;
        Theta = theta;
        gwater_getEvapRates(theta, upperDepth);
        UpperPerc = getUpperPerc(theta, upperDepth);
        UpperPerc = (((((UpperPerc) <= (MaxUpperPerc))) ? (UpperPerc) : (MaxUpperPerc)));
        if ((DeepFlowExpr != null)) {
            LowerLoss = (F_mathexpr.mathexpr_eval(DeepFlowExpr, F_gwater::gwater_getVariableValue) / F_swmm5.UCF(RAINFALL));
        } else {
            LowerLoss = ((A.lowerLossCoeff * lowerDepth) / TotalDepth);
        }
        LowerLoss = (((((LowerLoss) <= ((lowerDepth / gwater_Tstep)))) ? (LowerLoss) : ((lowerDepth / gwater_Tstep))));
        GWFlow = getGWFlow(lowerDepth);
        if ((LatFlowExpr != null)) {
            GWFlow += (F_mathexpr.mathexpr_eval(LatFlowExpr, F_gwater::gwater_getVariableValue) / F_swmm5.UCF(GWFLOW));
        }
        if (R.dge(GWFlow, 0.0d)) {
            GWFlow = (((((GWFlow) <= (MaxGWFlowPos))) ? (GWFlow) : (MaxGWFlowPos)));
        } else {
            GWFlow = (((R.dge((GWFlow), (MaxGWFlowNeg))) ? (GWFlow) : (MaxGWFlowNeg)));
        }
    }

    // gwater.c:677
    static void getDxDt(double t, double[] x, double[] dxdt) {
        double qUpper = 0.0d;
        double qLower = 0.0d;
        double denom = 0.0d;
        getFluxes(x[THETA], x[LOWERDEPTH]);
        qUpper = ((gwater_Infil - UpperEvap) - UpperPerc);
        qLower = (((UpperPerc - LowerLoss) - LowerEvap) - GWFlow);
        denom = (TotalDepth - x[LOWERDEPTH]);
        if (R.dgt(denom, 0.0d)) {
            dxdt[THETA] = (qUpper / denom);
        } else {
            dxdt[THETA] = 0.0d;
        }
        denom = (A.porosity - x[THETA]);
        if (R.dgt(denom, 0.0d)) {
            dxdt[LOWERDEPTH] = (qLower / denom);
        } else {
            dxdt[LOWERDEPTH] = 0.0d;
        }
    }

    // gwater.c:713
    static void gwater_getEvapRates(double theta, double upperDepth) {
        int p = 0;
        int month = 0;
        double f = 0.0d;
        double lowerFrac = 0.0d;
        double upperFrac = 0.0d;
        UpperEvap = 0.0d;
        LowerEvap = 0.0d;
        if (R.dgt(gwater_Infil, 0.0d)) {
            return;
        }
        upperFrac = A.upperEvapFrac;
        f = 1.0d;
        p = A.upperEvapPat;
        if ((p >= 0)) {
            month = F_datetime.datetime_monthOfYear(F_swmm5.getDateTime(F_swmm5.NewRunoffTime));
            f = F_swmm5.Pattern[p].factor[(month - 1)];
        }
        upperFrac *= f;
        if (R.dgt(theta, A.wiltingPoint)) {
            UpperEvap = (upperFrac * MaxEvap);
            UpperEvap = (((((UpperEvap) <= (AvailEvap))) ? (UpperEvap) : (AvailEvap)));
        }
        if (R.dgt(A.lowerEvapDepth, 0.0d)) {
            lowerFrac = (((A.lowerEvapDepth - upperDepth)) / A.lowerEvapDepth);
            lowerFrac = (((R.dge((0.0d), (lowerFrac))) ? (0.0d) : (lowerFrac)));
            lowerFrac = (((((lowerFrac) <= (1.0d))) ? (lowerFrac) : (1.0d)));
            LowerEvap = ((lowerFrac * ((1.0d - upperFrac))) * MaxEvap);
            LowerEvap = (((((LowerEvap) <= (((AvailEvap - UpperEvap))))) ? (LowerEvap) : (((AvailEvap - UpperEvap)))));
        }
    }

    // gwater.c:769
    static double getUpperPerc(double theta, double upperDepth) {
        double delta = 0.0d;
        double dhdz = 0.0d;
        double hydcon = 0.0d;
        if (((upperDepth <= 0.0d) || (theta <= A.fieldCapacity))) {
            return 0.0d;
        }
        delta = (theta - A.porosity);
        hydcon = (A.conductivity * Glibm.exp((delta * A.conductSlope)));
        delta = (theta - A.fieldCapacity);
        dhdz = (1.0d + (((A.tensionSlope * 2.0d) * delta) / upperDepth));
        HydCon = hydcon;
        return (hydcon * dhdz);
    }

    // gwater.c:799
    static double getGWFlow(double lowerDepth) {
        double q = 0.0d;
        double t1 = 0.0d;
        double t2 = 0.0d;
        double t3 = 0.0d;
        if ((lowerDepth <= Hstar)) {
            return 0.0d;
        }
        if ((GW.b1 == 0.0d)) {
            t1 = GW.a1;
        } else {
            t1 = (GW.a1 * Glibm.pow((((lowerDepth - Hstar)) * F_swmm5.UCF(LENGTH)), GW.b1));
        }
        if ((GW.b2 == 0.0d)) {
            t2 = GW.a2;
        } else if (R.dgt(Hsw, Hstar)) {
            t2 = (GW.a2 * Glibm.pow((((Hsw - Hstar)) * F_swmm5.UCF(LENGTH)), GW.b2));
        } else {
            t2 = 0.0d;
        }
        t3 = ((((GW.a3 * lowerDepth) * Hsw) * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LENGTH));
        q = ((((t1 - t2) + t3)) / F_swmm5.UCF(GWFLOW));
        if (((q < 0.0d) && (GW.a3 != 0.0d))) {
            q = 0.0d;
        }
        return q;
    }

    // gwater.c:834
    static int gwater_getVariableIndex(CPtr s) {
        int k = 0;
        k = F_input.findmatch(s, GWVarWords);
        if ((k >= 0)) {
            return k;
        }
        return (-1);
    }

    // gwater.c:850
    static double gwater_getVariableValue(int varIndex) {
        int __sw1 = varIndex;
        if (__sw1 == gwvHGW) {
            return (Hgw * F_swmm5.UCF(LENGTH));
        } else if (__sw1 == gwvHSW) {
            return (Hsw * F_swmm5.UCF(LENGTH));
        } else if (__sw1 == gwvHCB) {
            return (Hstar * F_swmm5.UCF(LENGTH));
        } else if (__sw1 == gwvHGS) {
            return (TotalDepth * F_swmm5.UCF(LENGTH));
        } else if (__sw1 == gwvKS) {
            return (A.conductivity * F_swmm5.UCF(RAINFALL));
        } else if (__sw1 == gwvK) {
            return (HydCon * F_swmm5.UCF(RAINFALL));
        } else if (__sw1 == gwvTHETA) {
            return Theta;
        } else if (__sw1 == gwvPHI) {
            return A.porosity;
        } else if (__sw1 == gwvFI) {
            return (gwater_Infil * F_swmm5.UCF(RAINFALL));
        } else if (__sw1 == gwvFU) {
            return (UpperPerc * F_swmm5.UCF(RAINFALL));
        } else if (__sw1 == gwvA) {
            return (Area * F_swmm5.UCF(LANDAREA));
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('getVariableValue: no return');
    }

}
