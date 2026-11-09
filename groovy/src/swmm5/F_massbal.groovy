// Generated from EPA SWMM 5.2.4 (massbal.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_massbal {
    public static double MAX_RUNOFF_BALANCE_ERR = 10.0d;
    public static double MAX_FLOW_BALANCE_ERR = 10.0d;
    public static TRunoffTotals RunoffTotals = new TRunoffTotals();
    public static TLoadingTotals[] LoadingTotals = null;
    public static TGwaterTotals GwaterTotals = new TGwaterTotals();
    public static TRoutingTotals FlowTotals = new TRoutingTotals();
    public static TRoutingTotals[] QualTotals = null;
    public static TRoutingTotals StepFlowTotals = new TRoutingTotals();
    public static TRoutingTotals OldStepFlowTotals = new TRoutingTotals();
    public static TRoutingTotals[] StepQualTotals = null;
    public static double[] NodeInflow = null;
    public static double[] NodeOutflow = null;
    public static double TotalArea = 0.0d;

    // massbal.c:100
    static int massbal_open() {
        int j = 0;
        int n = 0;
        F_swmm5.@RunoffError = 0.0d;
        F_swmm5.@GwaterError = 0.0d;
        F_swmm5.@FlowError = 0.0d;
        F_swmm5.@QualError = 0.0d;
        RunoffTotals.@rainfall = 0.0d;
        RunoffTotals.@evap = 0.0d;
        RunoffTotals.@infil = 0.0d;
        RunoffTotals.@runoff = 0.0d;
        RunoffTotals.@runon = 0.0d;
        RunoffTotals.@drains = 0.0d;
        RunoffTotals.@snowRemoved = 0.0d;
        RunoffTotals.@initStorage = 0.0d;
        RunoffTotals.@initSnowCover = 0.0d;
        TotalArea = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            RunoffTotals.@initStorage = (RunoffTotals.initStorage + (F_subcatch.subcatch_getStorage(j)));
            RunoffTotals.@initSnowCover = (RunoffTotals.initSnowCover + (F_snow.snow_getSnowCover(j)));
            TotalArea += F_swmm5.Subcatch[j].area;
        }
        GwaterTotals.@infil = 0.0d;
        GwaterTotals.@upperEvap = 0.0d;
        GwaterTotals.@lowerEvap = 0.0d;
        GwaterTotals.@lowerPerc = 0.0d;
        GwaterTotals.@gwater = 0.0d;
        GwaterTotals.@initStorage = 0.0d;
        GwaterTotals.@finalStorage = 0.0d;
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            GwaterTotals.@initStorage = (GwaterTotals.initStorage + (F_gwater.gwater_getVolume(j) * F_swmm5.Subcatch[j].area));
        }
        FlowTotals.@dwInflow = 0.0d;
        FlowTotals.@wwInflow = 0.0d;
        FlowTotals.@gwInflow = 0.0d;
        FlowTotals.@iiInflow = 0.0d;
        FlowTotals.@exInflow = 0.0d;
        FlowTotals.@flooding = 0.0d;
        FlowTotals.@outflow = 0.0d;
        FlowTotals.@evapLoss = 0.0d;
        FlowTotals.@seepLoss = 0.0d;
        FlowTotals.@reacted = 0.0d;
        FlowTotals.@initStorage = 0.0d;
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            FlowTotals.@initStorage = (FlowTotals.initStorage + F_swmm5.Node_[j].newVolume);
        }
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            FlowTotals.@initStorage = (FlowTotals.initStorage + F_swmm5.Link[j].newVolume);
        }
        StepFlowTotals = FlowTotals.copy();
        LoadingTotals = (null);
        QualTotals = (null);
        StepQualTotals = (null);
        NodeInflow = (null);
        NodeOutflow = (null);
        n = F_swmm5.Nobjects[POLLUT];
        if ((n > 0)) {
            LoadingTotals = R.fill(new TLoadingTotals[(int) (n)], (() -> new TLoadingTotals()));
            if ((LoadingTotals == null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
            L_f5: for (j = 0; (j < n); j++) {
                LoadingTotals[j].@initLoad = massbal_getBuildup(j);
                LoadingTotals[j].@buildup = 0.0d;
                LoadingTotals[j].@deposition = 0.0d;
                LoadingTotals[j].@sweeping = 0.0d;
                LoadingTotals[j].@infil = 0.0d;
                LoadingTotals[j].@bmpRemoval = 0.0d;
                LoadingTotals[j].@runoff = 0.0d;
                LoadingTotals[j].@finalLoad = 0.0d;
            }
        }
        if ((n > 0)) {
            QualTotals = R.fill(new TRoutingTotals[(int) (n)], (() -> new TRoutingTotals()));
            StepQualTotals = R.fill(new TRoutingTotals[(int) (n)], (() -> new TRoutingTotals()));
            if (((QualTotals == null) || (StepQualTotals == null))) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
        }
        L_f6: for (j = 0; (j < n); j++) {
            QualTotals[j].@dwInflow = 0.0d;
            QualTotals[j].@wwInflow = 0.0d;
            QualTotals[j].@gwInflow = 0.0d;
            QualTotals[j].@exInflow = 0.0d;
            QualTotals[j].@flooding = 0.0d;
            QualTotals[j].@outflow = 0.0d;
            QualTotals[j].@evapLoss = 0.0d;
            QualTotals[j].@seepLoss = 0.0d;
            QualTotals[j].@reacted = 0.0d;
            QualTotals[j].@initStorage = massbal_getStoredMass(j);
        }
        massbal_initTimeStepTotals();
        if ((F_swmm5.Nobjects[NODE] > 0)) {
            NodeInflow = new double[(int) (F_swmm5.Nobjects[NODE])];
            if ((NodeInflow == null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
            NodeOutflow = new double[(int) (F_swmm5.Nobjects[NODE])];
            if ((NodeOutflow == null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
            L_f7: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                NodeInflow[j] = F_swmm5.Node_[j].newVolume;
            }
        }
        return F_swmm5.ErrorCode;
    }

    // massbal.c:246
    static void massbal_close() {
        if ((LoadingTotals != null)) {
            R.free(LoadingTotals);
            LoadingTotals = (null);
        }
        if ((QualTotals != null)) {
            R.free(QualTotals);
            QualTotals = (null);
        }
        if ((StepQualTotals != null)) {
            R.free(StepQualTotals);
            StepQualTotals = (null);
        }
        if ((NodeInflow != null)) {
            R.free(NodeInflow);
            NodeInflow = (null);
        }
        if ((NodeOutflow != null)) {
            R.free(NodeOutflow);
            NodeOutflow = (null);
        }
    }

    // massbal.c:262
    static void massbal_report() {
        int j = 0;
        double gwArea = 0.0d;
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            if (((R.dgt(massbal_getRunoffError(), MAX_RUNOFF_BALANCE_ERR)) || (F_swmm5.RptFlags.continuity == 1))) {
                F_report.report_writeRunoffError(RunoffTotals, TotalArea);
            }
            if (((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) {
                if (((R.dgt(massbal_getLoadingError(), MAX_RUNOFF_BALANCE_ERR)) || (F_swmm5.RptFlags.continuity == 1))) {
                    F_report.report_writeLoadingError(LoadingTotals);
                }
            }
        }
        if (((F_swmm5.Nobjects[AQUIFER] > 0) && (!(F_swmm5.IgnoreGwater != 0)))) {
            if (((R.dgt(massbal_getGwaterError(), MAX_RUNOFF_BALANCE_ERR)) || (F_swmm5.RptFlags.continuity == 1))) {
                L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                    if ((F_swmm5.Subcatch[j].groundwater != null)) {
                        gwArea += F_swmm5.Subcatch[j].area;
                    }
                }
                if (R.dgt(gwArea, 0.0d)) {
                    F_report.report_writeGwaterError(GwaterTotals, gwArea);
                }
            }
        }
        if (((F_swmm5.Nobjects[NODE] > 0) && (!(F_swmm5.IgnoreRouting != 0)))) {
            if (((R.dgt(massbal_getFlowError(), MAX_FLOW_BALANCE_ERR)) || (F_swmm5.RptFlags.continuity == 1))) {
                F_report.report_writeFlowError(FlowTotals);
            }
            if (((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) {
                if (((R.dgt(massbal_getQualError(), MAX_FLOW_BALANCE_ERR)) || (F_swmm5.RptFlags.continuity == 1))) {
                    F_report.report_writeQualError(QualTotals);
                }
            }
        }
    }

    // massbal.c:316
    static double massbal_getBuildup(int p) {
        int i = 0;
        int j = 0;
        double load = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
                load += F_swmm5.Subcatch[j].landFactor[i].buildup[p];
            }
            load += (F_swmm5.Subcatch[j].pondedQual[p] * F_swmm5.Pollut[p].mcf);
        }
        return load;
    }

    // massbal.c:339
    static void massbal_updateRunoffTotals(int flowType, double v) {
        int __sw1 = flowType;
        if (__sw1 == RUNOFF_RAINFALL) {
            RunoffTotals.@rainfall = (RunoffTotals.rainfall + v);
        } else if (__sw1 == RUNOFF_EVAP) {
            RunoffTotals.@evap = (RunoffTotals.evap + v);
        } else if (__sw1 == RUNOFF_INFIL) {
            RunoffTotals.@infil = (RunoffTotals.infil + v);
        } else if (__sw1 == RUNOFF_RUNOFF) {
            RunoffTotals.@runoff = (RunoffTotals.runoff + v);
        } else if (__sw1 == RUNOFF_DRAINS) {
            RunoffTotals.@drains = (RunoffTotals.drains + v);
        } else if (__sw1 == RUNOFF_RUNON) {
            RunoffTotals.@runon = (RunoffTotals.runon + v);
        }
    }

    // massbal.c:360
    static void massbal_updateGwaterTotals(double vInfil, double vUpperEvap, double vLowerEvap, double vLowerPerc, double vGwater) {
        GwaterTotals.@infil = (GwaterTotals.infil + vInfil);
        GwaterTotals.@upperEvap = (GwaterTotals.upperEvap + vUpperEvap);
        GwaterTotals.@lowerEvap = (GwaterTotals.lowerEvap + vLowerEvap);
        GwaterTotals.@lowerPerc = (GwaterTotals.lowerPerc + vLowerPerc);
        GwaterTotals.@gwater = (GwaterTotals.gwater + vGwater);
    }

    // massbal.c:381
    static void massbal_initTimeStepTotals() {
        int j = 0;
        OldStepFlowTotals = StepFlowTotals.copy();
        StepFlowTotals.@dwInflow = 0.0d;
        StepFlowTotals.@wwInflow = 0.0d;
        StepFlowTotals.@gwInflow = 0.0d;
        StepFlowTotals.@iiInflow = 0.0d;
        StepFlowTotals.@exInflow = 0.0d;
        StepFlowTotals.@flooding = 0.0d;
        StepFlowTotals.@outflow = 0.0d;
        StepFlowTotals.@evapLoss = 0.0d;
        StepFlowTotals.@seepLoss = 0.0d;
        StepFlowTotals.@reacted = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
            StepQualTotals[j].@dwInflow = 0.0d;
            StepQualTotals[j].@wwInflow = 0.0d;
            StepQualTotals[j].@gwInflow = 0.0d;
            StepQualTotals[j].@iiInflow = 0.0d;
            StepQualTotals[j].@exInflow = 0.0d;
            StepQualTotals[j].@flooding = 0.0d;
            StepQualTotals[j].@outflow = 0.0d;
            StepQualTotals[j].@reacted = 0.0d;
            StepQualTotals[j].@seepLoss = 0.0d;
            StepQualTotals[j].@initStorage = 0.0d;
            StepQualTotals[j].@finalStorage = 0.0d;
        }
    }

    // massbal.c:418
    static void massbal_addInflowFlow(int type, double q) {
        int __sw1 = type;
        if (__sw1 == DRY_WEATHER_INFLOW) {
            StepFlowTotals.@dwInflow = (StepFlowTotals.dwInflow + q);
        } else if (__sw1 == WET_WEATHER_INFLOW) {
            StepFlowTotals.@wwInflow = (StepFlowTotals.wwInflow + q);
        } else if (__sw1 == GROUNDWATER_INFLOW) {
            StepFlowTotals.@gwInflow = (StepFlowTotals.gwInflow + q);
        } else if (__sw1 == RDII_INFLOW) {
            StepFlowTotals.@iiInflow = (StepFlowTotals.iiInflow + q);
        } else if (__sw1 == EXTERNAL_INFLOW) {
            StepFlowTotals.@exInflow = (StepFlowTotals.exInflow + q);
        }
    }

    // massbal.c:438
    static void massbal_updateLoadingTotals(int type, int p, double w) {
        int __sw1 = type;
        if (__sw1 == BUILDUP_LOAD) {
            LoadingTotals[p].@buildup = (LoadingTotals[p].buildup + w);
        } else if (__sw1 == DEPOSITION_LOAD) {
            LoadingTotals[p].@deposition = (LoadingTotals[p].deposition + w);
        } else if (__sw1 == SWEEPING_LOAD) {
            LoadingTotals[p].@sweeping = (LoadingTotals[p].sweeping + w);
        } else if (__sw1 == INFIL_LOAD) {
            LoadingTotals[p].@infil = (LoadingTotals[p].infil + w);
        } else if (__sw1 == BMP_REMOVAL_LOAD) {
            LoadingTotals[p].@bmpRemoval = (LoadingTotals[p].bmpRemoval + w);
        } else if (__sw1 == RUNOFF_LOAD) {
            LoadingTotals[p].@runoff = (LoadingTotals[p].runoff + w);
        } else if (__sw1 == FINAL_LOAD) {
            LoadingTotals[p].@finalLoad = (LoadingTotals[p].finalLoad + w);
        }
    }

    // massbal.c:461
    static void massbal_addInflowQual(int type, int p, double w) {
        if (((p < 0) || (p >= F_swmm5.Nobjects[POLLUT]))) {
            return;
        }
        int __sw1 = type;
        if (__sw1 == DRY_WEATHER_INFLOW) {
            StepQualTotals[p].@dwInflow = (StepQualTotals[p].dwInflow + w);
        } else if (__sw1 == WET_WEATHER_INFLOW) {
            StepQualTotals[p].@wwInflow = (StepQualTotals[p].wwInflow + w);
        } else if (__sw1 == GROUNDWATER_INFLOW) {
            StepQualTotals[p].@gwInflow = (StepQualTotals[p].gwInflow + w);
        } else if (__sw1 == EXTERNAL_INFLOW) {
            StepQualTotals[p].@exInflow = (StepQualTotals[p].exInflow + w);
        } else if (__sw1 == RDII_INFLOW) {
            StepQualTotals[p].@iiInflow = (StepQualTotals[p].iiInflow + w);
        }
    }

    // massbal.c:483
    static void massbal_addOutflowFlow(double q, int isFlooded) {
        if ((isFlooded != 0)) {
            StepFlowTotals.@flooding = (StepFlowTotals.flooding + q);
        } else {
            StepFlowTotals.@outflow = (StepFlowTotals.outflow + q);
        }
    }

    // massbal.c:497
    static void massbal_addOutflowQual(int p, double w, int isFlooded) {
        if (((p < 0) || (p >= F_swmm5.Nobjects[POLLUT]))) {
            return;
        }
        if (R.dge(w, 0.0d)) {
            if ((isFlooded != 0)) {
                StepQualTotals[p].@flooding = (StepQualTotals[p].flooding + w);
            } else {
                StepQualTotals[p].@outflow = (StepQualTotals[p].outflow + w);
            }
        } else {
            StepQualTotals[p].@exInflow = (StepQualTotals[p].exInflow - w);
        }
    }

    // massbal.c:517
    static void massbal_addReactedMass(int p, double w) {
        if (((p < 0) || (p >= F_swmm5.Nobjects[POLLUT]))) {
            return;
        }
        StepQualTotals[p].@reacted = (StepQualTotals[p].reacted + w);
    }

    // massbal.c:531
    static void massbal_addSeepageLoss(int p, double w) {
        if (((p < 0) || (p >= F_swmm5.Nobjects[POLLUT]))) {
            return;
        }
        StepQualTotals[p].@seepLoss = (StepQualTotals[p].seepLoss + w);
    }

    // massbal.c:545
    static void massbal_addToFinalStorage(int p, double w) {
        if (((p < 0) || (p >= F_swmm5.Nobjects[POLLUT]))) {
            return;
        }
        StepQualTotals[p].@finalStorage = (StepQualTotals[p].finalStorage + w);
    }

    // massbal.c:559
    static void massbal_addNodeLosses(double evapLoss, double seepLoss) {
        StepFlowTotals.@evapLoss = (StepFlowTotals.evapLoss + evapLoss);
        StepFlowTotals.@seepLoss = (StepFlowTotals.seepLoss + seepLoss);
    }

    // massbal.c:573
    static void massbal_addLinkLosses(double evapLoss, double seepLoss) {
        StepFlowTotals.@evapLoss = (StepFlowTotals.evapLoss + evapLoss);
        StepFlowTotals.@seepLoss = (StepFlowTotals.seepLoss + seepLoss);
    }

    // massbal.c:587
    static void massbal_updateRoutingTotals(double tStep) {
        int j = 0;
        FlowTotals.@dwInflow = (FlowTotals.dwInflow + (StepFlowTotals.dwInflow * tStep));
        FlowTotals.@wwInflow = (FlowTotals.wwInflow + (StepFlowTotals.wwInflow * tStep));
        FlowTotals.@gwInflow = (FlowTotals.gwInflow + (StepFlowTotals.gwInflow * tStep));
        FlowTotals.@iiInflow = (FlowTotals.iiInflow + (StepFlowTotals.iiInflow * tStep));
        FlowTotals.@exInflow = (FlowTotals.exInflow + (StepFlowTotals.exInflow * tStep));
        FlowTotals.@flooding = (FlowTotals.flooding + (StepFlowTotals.flooding * tStep));
        FlowTotals.@outflow = (FlowTotals.outflow + (StepFlowTotals.outflow * tStep));
        FlowTotals.@evapLoss = (FlowTotals.evapLoss + (StepFlowTotals.evapLoss * tStep));
        FlowTotals.@seepLoss = (FlowTotals.seepLoss + (StepFlowTotals.seepLoss * tStep));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
            QualTotals[j].@dwInflow = (QualTotals[j].dwInflow + (StepQualTotals[j].dwInflow * tStep));
            QualTotals[j].@wwInflow = (QualTotals[j].wwInflow + (StepQualTotals[j].wwInflow * tStep));
            QualTotals[j].@gwInflow = (QualTotals[j].gwInflow + (StepQualTotals[j].gwInflow * tStep));
            QualTotals[j].@iiInflow = (QualTotals[j].iiInflow + (StepQualTotals[j].iiInflow * tStep));
            QualTotals[j].@exInflow = (QualTotals[j].exInflow + (StepQualTotals[j].exInflow * tStep));
            QualTotals[j].@flooding = (QualTotals[j].flooding + (StepQualTotals[j].flooding * tStep));
            QualTotals[j].@outflow = (QualTotals[j].outflow + (StepQualTotals[j].outflow * tStep));
            QualTotals[j].@reacted = (QualTotals[j].reacted + (StepQualTotals[j].reacted * tStep));
            QualTotals[j].@seepLoss = (QualTotals[j].seepLoss + (StepQualTotals[j].seepLoss * tStep));
            QualTotals[j].@finalStorage = (QualTotals[j].finalStorage + StepQualTotals[j].finalStorage);
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            NodeInflow[j] += (F_swmm5.Node_[j].inflow * tStep);
            if (((F_swmm5.Node_[j].type == OUTFALL) || (((F_swmm5.Node_[j].degree == 0) && (F_swmm5.Node_[j].type != STORAGE))))) {
                NodeOutflow[j] += (F_swmm5.Node_[j].inflow * tStep);
            } else {
                NodeOutflow[j] += (F_swmm5.Node_[j].outflow * tStep);
                if ((F_swmm5.Node_[j].newVolume <= F_swmm5.Node_[j].fullVolume)) {
                    NodeOutflow[j] += (F_swmm5.Node_[j].overflow * tStep);
                }
            }
        }
    }

    // massbal.c:638
    static double massbal_getStorage(int isFinalStorage) {
        int j = 0;
        double totalStorage = 0.0d;
        double nodeStorage = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            nodeStorage = F_swmm5.Node_[j].newVolume;
            if ((isFinalStorage != 0)) {
                NodeOutflow[j] += nodeStorage;
            }
            totalStorage += nodeStorage;
        }
        if (((isFinalStorage != 0) && (F_swmm5.RouteModel == SF))) {
            return totalStorage;
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            totalStorage += F_swmm5.Link[j].newVolume;
        }
        return totalStorage;
    }

    // massbal.c:670
    static void massbal_getSysFlows(double f, double[] sysFlows) {
        double f1 = (1.0d - f);
        sysFlows[SYS_DWFLOW] = ((((f1 * OldStepFlowTotals.dwInflow) + (f * StepFlowTotals.dwInflow))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_GWFLOW] = ((((f1 * OldStepFlowTotals.gwInflow) + (f * StepFlowTotals.gwInflow))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_IIFLOW] = ((((f1 * OldStepFlowTotals.iiInflow) + (f * StepFlowTotals.iiInflow))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_EXFLOW] = ((((f1 * OldStepFlowTotals.exInflow) + (f * StepFlowTotals.exInflow))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_FLOODING] = ((((f1 * OldStepFlowTotals.flooding) + (f * StepFlowTotals.flooding))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_OUTFLOW] = ((((f1 * OldStepFlowTotals.outflow) + (f * StepFlowTotals.outflow))) * F_swmm5.UCF(FLOW));
        sysFlows[SYS_STORAGE] = ((((f1 * OldStepFlowTotals.finalStorage) + (f * StepFlowTotals.finalStorage))) * F_swmm5.UCF(VOLUME));
    }

    // massbal.c:696
    static double massbal_getRunoffError() {
        int j = 0;
        double totalInflow = 0.0d;
        double totalOutflow = 0.0d;
        RunoffTotals.@finalStorage = 0.0d;
        RunoffTotals.@finalSnowCover = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            RunoffTotals.@finalStorage = (RunoffTotals.finalStorage + (F_subcatch.subcatch_getStorage(j)));
            RunoffTotals.@finalSnowCover = (RunoffTotals.finalSnowCover + (F_snow.snow_getSnowCover(j)));
        }
        RunoffTotals.@snowRemoved = F_swmm5.Snow.removed;
        totalInflow = (((RunoffTotals.rainfall + RunoffTotals.runon) + RunoffTotals.initStorage) + RunoffTotals.initSnowCover);
        totalOutflow = ((((((RunoffTotals.evap + RunoffTotals.infil) + RunoffTotals.runoff) + RunoffTotals.drains) + RunoffTotals.snowRemoved) + RunoffTotals.finalStorage) + RunoffTotals.finalSnowCover);
        RunoffTotals.@pctError = 0.0d;
        if ((Math.abs((totalInflow - totalOutflow)) < 1.0d)) {
            RunoffTotals.@pctError = 1e-06d;
        } else if (R.dgt(totalInflow, 0.0d)) {
            RunoffTotals.@pctError = (100.0d * ((1.0d - (totalOutflow / totalInflow))));
        } else if (R.dgt(totalOutflow, 0.0d)) {
            RunoffTotals.@pctError = (100.0d * (((totalInflow / totalOutflow) - 1.0d)));
        }
        F_swmm5.@RunoffError = RunoffTotals.pctError;
        return RunoffTotals.pctError;
    }

    // massbal.c:750
    static double massbal_getLoadingError() {
        int j = 0;
        double loadIn = 0.0d;
        double loadOut = 0.0d;
        double maxError = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
            LoadingTotals[j].@finalLoad = (LoadingTotals[j].finalLoad + (massbal_getBuildup(j)));
            loadIn = ((LoadingTotals[j].initLoad + LoadingTotals[j].buildup) + LoadingTotals[j].deposition);
            loadOut = ((((LoadingTotals[j].sweeping + LoadingTotals[j].infil) + LoadingTotals[j].bmpRemoval) + LoadingTotals[j].runoff) + LoadingTotals[j].finalLoad);
            LoadingTotals[j].@pctError = 0.0d;
            if ((Math.abs((loadIn - loadOut)) < 0.001d)) {
                LoadingTotals[j].@pctError = 1e-06d;
            } else if (R.dgt(loadIn, 0.0d)) {
                LoadingTotals[j].@pctError = (100.0d * ((1.0d - (loadOut / loadIn))));
            } else if (R.dgt(loadOut, 0.0d)) {
                LoadingTotals[j].@pctError = (100.0d * (((loadIn / loadOut) - 1.0d)));
            }
            maxError = (((R.dge((maxError), (LoadingTotals[j].pctError))) ? (maxError) : (LoadingTotals[j].pctError)));
            if ((F_swmm5.Pollut[j].units == COUNT)) {
                LoadingTotals[j].@initLoad = ((R.dgt((LoadingTotals[j].initLoad), 0.0d) ? Glibm.log10((LoadingTotals[j].initLoad)) : (LoadingTotals[j].initLoad)));
                LoadingTotals[j].@buildup = ((R.dgt((LoadingTotals[j].buildup), 0.0d) ? Glibm.log10((LoadingTotals[j].buildup)) : (LoadingTotals[j].buildup)));
                LoadingTotals[j].@deposition = ((R.dgt((LoadingTotals[j].deposition), 0.0d) ? Glibm.log10((LoadingTotals[j].deposition)) : (LoadingTotals[j].deposition)));
                LoadingTotals[j].@sweeping = ((R.dgt((LoadingTotals[j].sweeping), 0.0d) ? Glibm.log10((LoadingTotals[j].sweeping)) : (LoadingTotals[j].sweeping)));
                LoadingTotals[j].@infil = ((R.dgt((LoadingTotals[j].infil), 0.0d) ? Glibm.log10((LoadingTotals[j].infil)) : (LoadingTotals[j].infil)));
                LoadingTotals[j].@bmpRemoval = ((R.dgt((LoadingTotals[j].bmpRemoval), 0.0d) ? Glibm.log10((LoadingTotals[j].bmpRemoval)) : (LoadingTotals[j].bmpRemoval)));
                LoadingTotals[j].@runoff = ((R.dgt((LoadingTotals[j].runoff), 0.0d) ? Glibm.log10((LoadingTotals[j].runoff)) : (LoadingTotals[j].runoff)));
                LoadingTotals[j].@finalLoad = ((R.dgt((LoadingTotals[j].finalLoad), 0.0d) ? Glibm.log10((LoadingTotals[j].finalLoad)) : (LoadingTotals[j].finalLoad)));
            }
        }
        return maxError;
    }

    // massbal.c:813
    static double massbal_getGwaterError() {
        int j = 0;
        double totalInflow = 0.0d;
        double totalOutflow = 0.0d;
        GwaterTotals.@finalStorage = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            GwaterTotals.@finalStorage = (GwaterTotals.finalStorage + (F_gwater.gwater_getVolume(j) * F_swmm5.Subcatch[j].area));
        }
        totalInflow = (GwaterTotals.infil + GwaterTotals.initStorage);
        totalOutflow = ((((GwaterTotals.upperEvap + GwaterTotals.lowerEvap) + GwaterTotals.lowerPerc) + GwaterTotals.gwater) + GwaterTotals.finalStorage);
        GwaterTotals.@pctError = 0.0d;
        if ((Math.abs((totalInflow - totalOutflow)) < 1.0d)) {
            GwaterTotals.@pctError = 1e-06d;
        } else if (R.dgt(totalInflow, 0.0d)) {
            GwaterTotals.@pctError = (100.0d * ((1.0d - (totalOutflow / totalInflow))));
        } else if (R.dgt(totalOutflow, 0.0d)) {
            GwaterTotals.@pctError = (100.0d * (((totalInflow / totalOutflow) - 1.0d)));
        }
        F_swmm5.@GwaterError = GwaterTotals.pctError;
        return GwaterTotals.pctError;
    }

    // massbal.c:858
    static double massbal_getFlowError() {
        double totalInflow = 0.0d;
        double totalOutflow = 0.0d;
        FlowTotals.@finalStorage = massbal_getStorage(1);
        totalInflow = ((FlowTotals.initStorage + FlowTotals.wwInflow) + FlowTotals.iiInflow);
        totalOutflow = ((((FlowTotals.finalStorage + FlowTotals.flooding) + FlowTotals.evapLoss) + FlowTotals.seepLoss) + FlowTotals.reacted);
        if (R.dge(FlowTotals.dwInflow, 0.0d)) {
            totalInflow += FlowTotals.dwInflow;
        } else {
            totalOutflow -= FlowTotals.dwInflow;
        }
        if (R.dge(FlowTotals.gwInflow, 0.0d)) {
            totalInflow += FlowTotals.gwInflow;
        } else {
            totalOutflow -= FlowTotals.gwInflow;
        }
        if (R.dge(FlowTotals.exInflow, 0.0d)) {
            totalInflow += FlowTotals.exInflow;
        } else {
            totalOutflow -= FlowTotals.exInflow;
        }
        if (R.dge(FlowTotals.outflow, 0.0d)) {
            totalOutflow += FlowTotals.outflow;
        } else {
            totalInflow -= FlowTotals.outflow;
        }
        FlowTotals.@pctError = 0.0d;
        if ((Math.abs((totalInflow - totalOutflow)) < 1.0d)) {
            FlowTotals.@pctError = 1e-06d;
        } else if (R.dgt(Math.abs(totalInflow), 0.0d)) {
            FlowTotals.@pctError = (100.0d * ((1.0d - (totalOutflow / totalInflow))));
        } else if (R.dgt(Math.abs(totalOutflow), 0.0d)) {
            FlowTotals.@pctError = (100.0d * (((totalInflow / totalOutflow) - 1.0d)));
        }
        F_swmm5.@FlowError = FlowTotals.pctError;
        return FlowTotals.pctError;
    }

    // massbal.c:906
    static double massbal_getQualError() {
        int p = 0;
        double maxQualError = 0.0d;
        double totalInflow = 0.0d;
        double totalOutflow = 0.0d;
        double cf = 0.0d;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            QualTotals[p].@finalStorage = (QualTotals[p].finalStorage + (massbal_getStoredMass(p)));
            totalInflow = (((((QualTotals[p].dwInflow + QualTotals[p].wwInflow) + QualTotals[p].gwInflow) + QualTotals[p].iiInflow) + QualTotals[p].exInflow) + QualTotals[p].initStorage);
            totalOutflow = ((((QualTotals[p].flooding + QualTotals[p].outflow) + QualTotals[p].reacted) + QualTotals[p].seepLoss) + QualTotals[p].finalStorage);
            QualTotals[p].@pctError = 0.0d;
            if ((Math.abs((totalInflow - totalOutflow)) < 0.001d)) {
                QualTotals[p].@pctError = 1e-06d;
            } else if (R.dgt(totalInflow, 0.0d)) {
                QualTotals[p].@pctError = (100.0d * ((1.0d - (totalOutflow / totalInflow))));
            } else if (R.dgt(totalOutflow, 0.0d)) {
                QualTotals[p].@pctError = (100.0d * (((totalInflow / totalOutflow) - 1.0d)));
            }
            if (R.dgt(Math.abs(QualTotals[p].pctError), Math.abs(maxQualError))) {
                maxQualError = QualTotals[p].pctError;
            }
            cf = 28.317d;
            if ((F_swmm5.Pollut[p].units == COUNT)) {
                QualTotals[p].@dwInflow = ((R.dgt(((cf * QualTotals[p].dwInflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].dwInflow))) : ((cf * QualTotals[p].dwInflow))));
                QualTotals[p].@wwInflow = ((R.dgt(((cf * QualTotals[p].wwInflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].wwInflow))) : ((cf * QualTotals[p].wwInflow))));
                QualTotals[p].@gwInflow = ((R.dgt(((cf * QualTotals[p].gwInflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].gwInflow))) : ((cf * QualTotals[p].gwInflow))));
                QualTotals[p].@iiInflow = ((R.dgt(((cf * QualTotals[p].iiInflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].iiInflow))) : ((cf * QualTotals[p].iiInflow))));
                QualTotals[p].@exInflow = ((R.dgt(((cf * QualTotals[p].exInflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].exInflow))) : ((cf * QualTotals[p].exInflow))));
                QualTotals[p].@flooding = ((R.dgt(((cf * QualTotals[p].flooding)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].flooding))) : ((cf * QualTotals[p].flooding))));
                QualTotals[p].@outflow = ((R.dgt(((cf * QualTotals[p].outflow)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].outflow))) : ((cf * QualTotals[p].outflow))));
                QualTotals[p].@reacted = ((R.dgt(((cf * QualTotals[p].reacted)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].reacted))) : ((cf * QualTotals[p].reacted))));
                QualTotals[p].@seepLoss = ((R.dgt(((cf * QualTotals[p].seepLoss)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].seepLoss))) : ((cf * QualTotals[p].seepLoss))));
                QualTotals[p].@initStorage = ((R.dgt(((cf * QualTotals[p].initStorage)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].initStorage))) : ((cf * QualTotals[p].initStorage))));
                QualTotals[p].@finalStorage = ((R.dgt(((cf * QualTotals[p].finalStorage)), 0.0d) ? Glibm.log10(((cf * QualTotals[p].finalStorage))) : ((cf * QualTotals[p].finalStorage))));
            } else {
                cf = (cf * F_swmm5.UCF(MASS));
                if ((F_swmm5.Pollut[p].units == UG)) {
                    cf = (cf / 1000.0d);
                }
                QualTotals[p].@dwInflow = (QualTotals[p].dwInflow * cf);
                QualTotals[p].@wwInflow = (QualTotals[p].wwInflow * cf);
                QualTotals[p].@gwInflow = (QualTotals[p].gwInflow * cf);
                QualTotals[p].@iiInflow = (QualTotals[p].iiInflow * cf);
                QualTotals[p].@exInflow = (QualTotals[p].exInflow * cf);
                QualTotals[p].@flooding = (QualTotals[p].flooding * cf);
                QualTotals[p].@outflow = (QualTotals[p].outflow * cf);
                QualTotals[p].@reacted = (QualTotals[p].reacted * cf);
                QualTotals[p].@seepLoss = (QualTotals[p].seepLoss * cf);
                QualTotals[p].@initStorage = (QualTotals[p].initStorage * cf);
                QualTotals[p].@finalStorage = (QualTotals[p].finalStorage * cf);
            }
        }
        F_swmm5.@QualError = maxQualError;
        return maxQualError;
    }

    // massbal.c:995
    static double massbal_getStepFlowError() {
        double totalInflow = 0.0d;
        double totalOutflow = 0.0d;
        totalInflow = ((((StepFlowTotals.dwInflow + StepFlowTotals.wwInflow) + StepFlowTotals.gwInflow) + StepFlowTotals.iiInflow) + StepFlowTotals.exInflow);
        totalOutflow = ((((StepFlowTotals.flooding + StepFlowTotals.outflow) + StepFlowTotals.evapLoss) + StepFlowTotals.seepLoss) + StepFlowTotals.reacted);
        if (R.dgt(Math.abs(totalInflow), 0.0d)) {
            return (1.0d - (totalOutflow / totalInflow));
        } else if (R.dgt(Math.abs(totalOutflow), 0.0d)) {
            return ((totalInflow / totalOutflow) - 1.0d);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('massbal_getStepFlowError: no return');
    }

    // massbal.c:1025
    static double massbal_getStoredMass(int p) {
        int j = 0;
        double storedMass = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            storedMass += (F_swmm5.Node_[j].newVolume * F_swmm5.Node_[j].newQual[p]);
        }
        if ((F_swmm5.RouteModel != SF)) {
            L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
                storedMass += (F_swmm5.Link[j].newVolume * F_swmm5.Link[j].newQual[p]);
            }
        }
        return storedMass;
    }

}
