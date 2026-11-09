// Generated from EPA SWMM 5.2.4 (stats.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_stats {
    public static TTimeStepStats TimeStepStats = new TTimeStepStats();
    public static TMaxStats[] MaxMassBalErrs = R.fill(new TMaxStats[5], (() -> new TMaxStats()));
    public static TMaxStats[] MaxCourantCrit = R.fill(new TMaxStats[5], (() -> new TMaxStats()));
    public static TMaxStats[] MaxFlowTurns = R.fill(new TMaxStats[5], (() -> new TMaxStats()));
    public static TMaxStats[] MaxNonConverged = R.fill(new TMaxStats[5], (() -> new TMaxStats()));
    public static double SysOutfallFlow = 0.0d;
    public static TSubcatchStats[] SubcatchStats = null;
    public static TNodeStats[] NodeStats = null;
    public static TLinkStats[] LinkStats = null;
    public static TStorageStats[] StorageStats = null;
    public static TOutfallStats[] OutfallStats = null;
    public static TPumpStats[] PumpStats = null;
    public static double MaxOutfallFlow = 0.0d;
    public static double MaxRunoffFlow = 0.0d;
    public static double RoutingTimeSpan = 0.0d;

    // stats.c:102
    static int stats_open() {
        int j = 0;
        int k = 0;
        double timeStepDelta = 0.0d;
        double logMaxTimeStep = 0.0d;
        double logMinTimeStep = 0.0d;
        NodeStats = (null);
        LinkStats = (null);
        StorageStats = (null);
        OutfallStats = (null);
        PumpStats = (null);
        SubcatchStats = (null);
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            SubcatchStats = R.fill(new TSubcatchStats[(int) (F_swmm5.Nobjects[SUBCATCH])], (() -> new TSubcatchStats()));
            if (!(SubcatchStats != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                SubcatchStats[j].@precip = 0.0d;
                SubcatchStats[j].@runon = 0.0d;
                SubcatchStats[j].@evap = 0.0d;
                SubcatchStats[j].@infil = 0.0d;
                SubcatchStats[j].@runoff = 0.0d;
                SubcatchStats[j].@maxFlow = 0.0d;
                SubcatchStats[j].@impervRunoff = 0.0d;
                SubcatchStats[j].@pervRunoff = 0.0d;
            }
            L_f2: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                if ((F_swmm5.Subcatch[j].groundwater == null)) {
                    continue L_f2;
                }
                F_swmm5.Subcatch[j].groundwater.stats.@avgUpperMoist = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@avgWaterTable = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@infil = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@latFlow = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@deepFlow = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@evap = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@maxFlow = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@finalUpperMoist = 0.0d;
                F_swmm5.Subcatch[j].groundwater.stats.@finalWaterTable = 0.0d;
            }
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            NodeStats = R.fill(new TNodeStats[(int) (F_swmm5.Nobjects[NODE])], (() -> new TNodeStats()));
            LinkStats = R.fill(new TLinkStats[(int) (F_swmm5.Nobjects[LINK])], (() -> new TLinkStats()));
            if (((!(NodeStats != null)) || (!(LinkStats != null)))) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
        }
        if ((NodeStats != null)) {
            L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                NodeStats[j].@avgDepth = 0.0d;
                NodeStats[j].@maxDepth = 0.0d;
                NodeStats[j].@maxDepthDate = F_swmm5.StartDateTime;
                NodeStats[j].@maxRptDepth = 0.0d;
                NodeStats[j].@volFlooded = 0.0d;
                NodeStats[j].@timeFlooded = 0.0d;
                NodeStats[j].@timeSurcharged = 0.0d;
                NodeStats[j].@timeCourantCritical = 0.0d;
                NodeStats[j].@totLatFlow = 0.0d;
                NodeStats[j].@maxLatFlow = 0.0d;
                NodeStats[j].@maxInflow = 0.0d;
                NodeStats[j].@maxOverflow = 0.0d;
                NodeStats[j].@maxPondedVol = 0.0d;
                NodeStats[j].@nonConvergedCount = 0;
                NodeStats[j].@maxInflowDate = F_swmm5.StartDateTime;
                NodeStats[j].@maxOverflowDate = F_swmm5.StartDateTime;
            }
        }
        if ((LinkStats != null)) {
            L_f4: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
                LinkStats[j].@maxFlow = 0.0d;
                LinkStats[j].@maxVeloc = 0.0d;
                LinkStats[j].@maxDepth = 0.0d;
                LinkStats[j].@maxStreetFilled = 0.0d;
                LinkStats[j].@timeSurcharged = 0.0d;
                LinkStats[j].@timeFullUpstream = 0.0d;
                LinkStats[j].@timeFullDnstream = 0.0d;
                LinkStats[j].@timeFullFlow = 0.0d;
                LinkStats[j].@timeCapacityLimited = 0.0d;
                LinkStats[j].@timeCourantCritical = 0.0d;
                L_f5: for (k = 0; (k < MAX_FLOW_CLASSES); k++) {
                    LinkStats[j].timeInFlowClass[k] = 0.0d;
                }
                LinkStats[j].@flowTurns = 0;
                LinkStats[j].@flowTurnSign = 0;
            }
        }
        if ((F_swmm5.Nnodes[STORAGE] > 0)) {
            StorageStats = R.fill(new TStorageStats[(int) (F_swmm5.Nnodes[STORAGE])], (() -> new TStorageStats()));
            if (!(StorageStats != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            } else {
                L_f6: for (k = 0; (k < F_swmm5.Nobjects[NODE]); k++) {
                    if ((F_swmm5.Node_[k].type != STORAGE)) {
                        continue L_f6;
                    }
                    j = F_swmm5.Node_[k].subIndex;
                    StorageStats[j].@initVol = F_swmm5.Node_[k].newVolume;
                    StorageStats[j].@avgVol = 0.0d;
                    StorageStats[j].@maxVol = 0.0d;
                    StorageStats[j].@maxFlow = 0.0d;
                    StorageStats[j].@evapLosses = 0.0d;
                    StorageStats[j].@exfilLosses = 0.0d;
                    StorageStats[j].@maxVolDate = F_swmm5.StartDateTime;
                }
            }
        }
        if ((F_swmm5.Nnodes[OUTFALL] > 0)) {
            OutfallStats = R.fill(new TOutfallStats[(int) (F_swmm5.Nnodes[OUTFALL])], (() -> new TOutfallStats()));
            if (!(OutfallStats != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            } else {
                L_f7: for (j = 0; (j < F_swmm5.Nnodes[OUTFALL]); j++) {
                    OutfallStats[j].@avgFlow = 0.0d;
                    OutfallStats[j].@maxFlow = 0.0d;
                    OutfallStats[j].@totalPeriods = 0;
                    if ((F_swmm5.Nobjects[POLLUT] > 0)) {
                        OutfallStats[j].@totalLoad = new double[(int) (F_swmm5.Nobjects[POLLUT])];
                        if (!(OutfallStats[j].totalLoad != null)) {
                            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                            return F_swmm5.ErrorCode;
                        }
                        L_f8: for (k = 0; (k < F_swmm5.Nobjects[POLLUT]); k++) {
                            OutfallStats[j].totalLoad[k] = 0.0d;
                        }
                    } else {
                        OutfallStats[j].@totalLoad = (null);
                    }
                }
            }
        }
        if ((F_swmm5.Nlinks[PUMP] > 0)) {
            PumpStats = R.fill(new TPumpStats[(int) (F_swmm5.Nlinks[PUMP])], (() -> new TPumpStats()));
            if (!(PumpStats != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            } else {
                L_f9: for (j = 0; (j < F_swmm5.Nlinks[PUMP]); j++) {
                    PumpStats[j].@utilized = 0.0d;
                    PumpStats[j].@minFlow = 0.0d;
                    PumpStats[j].@avgFlow = 0.0d;
                    PumpStats[j].@maxFlow = 0.0d;
                    PumpStats[j].@volume = 0.0d;
                    PumpStats[j].@energy = 0.0d;
                    PumpStats[j].@startUps = 0;
                    PumpStats[j].@offCurveLow = 0.0d;
                    PumpStats[j].@offCurveHigh = 0.0d;
                }
            }
        }
        MaxRunoffFlow = 0.0d;
        MaxOutfallFlow = 0.0d;
        TimeStepStats.@maxTimeStep = 0.0d;
        TimeStepStats.@minTimeStep = F_swmm5.RouteStep;
        TimeStepStats.@routingTime = 0.0d;
        TimeStepStats.@trialsCount = 0.0d;
        TimeStepStats.@steadyStateTime = 0.0d;
        TimeStepStats.@timeStepCount = 0;
        logMaxTimeStep = Glibm.log10(F_swmm5.RouteStep);
        logMinTimeStep = Glibm.log10(F_swmm5.MinRouteStep.v);
        timeStepDelta = (((logMaxTimeStep - logMinTimeStep)) / ((double) (((6 - 1)))));
        TimeStepStats.timeStepIntervals[0] = F_swmm5.RouteStep;
        L_f10: for (j = 1; (j < 6); j++) {
            TimeStepStats.timeStepIntervals[j] = Glibm.pow(10.0d, (logMaxTimeStep - (j * timeStepDelta)));
            TimeStepStats.timeStepCounts[j] = 0;
        }
        TimeStepStats.timeStepIntervals[(6 - 1)] = F_swmm5.MinRouteStep.v;
        RoutingTimeSpan = 0.0d;
        return 0;
    }

    // stats.c:318
    static void stats_close() {
        int j = 0;
        if ((SubcatchStats != null)) {
            R.free(SubcatchStats);
            SubcatchStats = (null);
        }
        if ((NodeStats != null)) {
            R.free(NodeStats);
            NodeStats = (null);
        }
        if ((LinkStats != null)) {
            R.free(LinkStats);
            LinkStats = (null);
        }
        if ((StorageStats != null)) {
            R.free(StorageStats);
            StorageStats = (null);
        }
        if ((OutfallStats != null)) {
            L_f1: for (j = 0; (j < F_swmm5.Nnodes[OUTFALL]); j++) {
                if ((OutfallStats[j].totalLoad != null)) {
                    R.free(OutfallStats[j].totalLoad);
                    OutfallStats[j].@totalLoad = (null);
                }
            }
            if ((OutfallStats != null)) {
                R.free(OutfallStats);
                OutfallStats = (null);
            }
        }
        if ((PumpStats != null)) {
            R.free(PumpStats);
            PumpStats = (null);
        }
    }

    // stats.c:342
    static void stats_report() {
        if (((F_swmm5.Nobjects[LINK] > 0) && (F_swmm5.RouteModel != NO_ROUTING))) {
            stats_findMaxStats();
            if (((!(F_swmm5.RptFlags.disabled != 0)) && (F_swmm5.RptFlags.flowStats != 0))) {
                F_report.report_writeMaxStats(MaxMassBalErrs, MaxCourantCrit, 5);
                F_report.report_writeMaxFlowTurns(MaxFlowTurns, 5);
                F_report.report_writeNonconvergedStats(MaxNonConverged, 5);
                F_report.report_writeTimeStepStats(TimeStepStats);
            }
        }
        if (!(F_swmm5.RptFlags.disabled != 0)) {
            F_statsrpt.statsrpt_writeReport();
        }
    }

    // stats.c:369
    static void stats_updateSubcatchStats(int j, double rainVol, double runonVol, double evapVol, double infilVol, double impervVol, double pervVol, double runoffVol, double runoff) {
        SubcatchStats[j].@precip = (SubcatchStats[j].precip + rainVol);
        SubcatchStats[j].@runon = (SubcatchStats[j].runon + runonVol);
        SubcatchStats[j].@evap = (SubcatchStats[j].evap + evapVol);
        SubcatchStats[j].@infil = (SubcatchStats[j].infil + infilVol);
        SubcatchStats[j].@runoff = (SubcatchStats[j].runoff + runoffVol);
        SubcatchStats[j].@maxFlow = (((R.dge((SubcatchStats[j].maxFlow), (runoff))) ? (SubcatchStats[j].maxFlow) : (runoff)));
        SubcatchStats[j].@impervRunoff = (SubcatchStats[j].impervRunoff + impervVol);
        SubcatchStats[j].@pervRunoff = (SubcatchStats[j].pervRunoff + pervVol);
    }

    // stats.c:399
    static void stats_updateGwaterStats(int j, double infil, double evap, double latFlow, double deepFlow, double theta, double waterTable, double tStep) {
        F_swmm5.Subcatch[j].groundwater.stats.@infil = (F_swmm5.Subcatch[j].groundwater.stats.infil + (infil * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@evap = (F_swmm5.Subcatch[j].groundwater.stats.evap + (evap * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@latFlow = (F_swmm5.Subcatch[j].groundwater.stats.latFlow + (latFlow * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@deepFlow = (F_swmm5.Subcatch[j].groundwater.stats.deepFlow + (deepFlow * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@avgUpperMoist = (F_swmm5.Subcatch[j].groundwater.stats.avgUpperMoist + (theta * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@avgWaterTable = (F_swmm5.Subcatch[j].groundwater.stats.avgWaterTable + (waterTable * tStep));
        F_swmm5.Subcatch[j].groundwater.stats.@finalUpperMoist = theta;
        F_swmm5.Subcatch[j].groundwater.stats.@finalWaterTable = waterTable;
        if (R.dgt(Math.abs(latFlow), Math.abs(F_swmm5.Subcatch[j].groundwater.stats.maxFlow))) {
            F_swmm5.Subcatch[j].groundwater.stats.@maxFlow = latFlow;
        }
    }

    // stats.c:419
    static void stats_updateMaxRunoff() {
        int j = 0;
        double sysRunoff = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            sysRunoff += F_swmm5.Subcatch[j].newRunoff;
        }
        MaxRunoffFlow = (((R.dge((MaxRunoffFlow), (sysRunoff))) ? (MaxRunoffFlow) : (sysRunoff)));
    }

    // stats.c:435
    static void stats_updateMaxNodeDepth(int j, double depth) {
        if ((NodeStats != null)) {
            NodeStats[j].@maxRptDepth = (((R.dge((NodeStats[j].maxRptDepth), (depth))) ? (NodeStats[j].maxRptDepth) : (depth)));
        }
    }

    // stats.c:449
    static void stats_updateFlowStats(double tStep, double aDate) {
        int j = 0;
        if ((aDate < F_swmm5.ReportStart)) {
            return;
        }
        SysOutfallFlow = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            stats_updateNodeStats(j, tStep, aDate);
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            stats_updateLinkStats(j, tStep, aDate);
        }
        F_swmm5.@ReportStepCount = F_swmm5.ReportStepCount + 1L;
        RoutingTimeSpan += tStep;
        MaxOutfallFlow = (((R.dge((MaxOutfallFlow), (SysOutfallFlow))) ? (MaxOutfallFlow) : (SysOutfallFlow)));
    }

    // stats.c:484
    static void stats_updateTimeStepStats(double tStep, int trialsCount, int steadyState) {
        int j = 0;
        TimeStepStats.@steadyStateTime = (TimeStepStats.steadyStateTime + (steadyState * tStep));
        if ((steadyState == 0)) {
            if (R.dgt(F_swmm5.OldRoutingTime, 0)) {
                TimeStepStats.@minTimeStep = (((((TimeStepStats.minTimeStep) <= (tStep))) ? (TimeStepStats.minTimeStep) : (tStep)));
                L_f1: for (j = 1; (j < 6); j++) {
                    if (R.dge(tStep, TimeStepStats.timeStepIntervals[j])) {
                        TimeStepStats.timeStepCounts[j]++;
                        break L_f1;
                    }
                }
            }
            TimeStepStats.@maxTimeStep = (((R.dge((TimeStepStats.maxTimeStep), (tStep))) ? (TimeStepStats.maxTimeStep) : (tStep)));
            TimeStepStats.@routingTime = (TimeStepStats.routingTime + tStep);
            TimeStepStats.@timeStepCount = TimeStepStats.timeStepCount + 1;
            TimeStepStats.@trialsCount = (TimeStepStats.trialsCount + trialsCount);
        }
    }

    // stats.c:522
    static void stats_updateCriticalTimeCount(int node, int link) {
        if ((node >= 0)) {
            NodeStats[node].@timeCourantCritical = (NodeStats[node].timeCourantCritical + 1.0d);
        } else if ((link >= 0)) {
            LinkStats[link].@timeCourantCritical = (LinkStats[link].timeCourantCritical + 1.0d);
        }
    }

    // stats.c:536
    static void stats_updateConvergenceStats(int node, int converged) {
        if ((converged == 0)) {
            NodeStats[node].@nonConvergedCount = NodeStats[node].nonConvergedCount + 1;
        }
    }

    // stats.c:543
    static void stats_updateNodeStats(int j, double tStep, double aDate) {
        int k = 0;
        int p = 0;
        double newVolume = F_swmm5.Node_[j].newVolume;
        double newDepth = F_swmm5.Node_[j].newDepth;
        int canPond = ((((F_swmm5.AllowPonding != 0) && (R.dgt(F_swmm5.Node_[j].pondedArea, 0.0d)))) ? 1 : 0);
        NodeStats[j].@avgDepth = (NodeStats[j].avgDepth + newDepth);
        if (R.dgt(newDepth, NodeStats[j].maxDepth)) {
            NodeStats[j].@maxDepth = newDepth;
            NodeStats[j].@maxDepthDate = aDate;
        }
        if ((F_swmm5.Node_[j].type != OUTFALL)) {
            if (((R.dgt(newVolume, F_swmm5.Node_[j].fullVolume)) || (R.dgt(F_swmm5.Node_[j].overflow, 0.0d)))) {
                NodeStats[j].@timeFlooded = (NodeStats[j].timeFlooded + tStep);
                NodeStats[j].@volFlooded = (NodeStats[j].volFlooded + (F_swmm5.Node_[j].overflow * tStep));
                if ((canPond != 0)) {
                    NodeStats[j].@maxPondedVol = (((R.dge((NodeStats[j].maxPondedVol), (((newVolume - F_swmm5.Node_[j].fullVolume))))) ? (NodeStats[j].maxPondedVol) : (((newVolume - F_swmm5.Node_[j].fullVolume)))));
                }
            }
            if ((F_swmm5.RouteModel == DW)) {
                if (((((F_swmm5.Node_[j].type != STORAGE) || (R.dgt(F_swmm5.Node_[j].surDepth, 0.0d)))) && (R.dge(((newDepth + F_swmm5.Node_[j].invertElev) + 0.0001d), F_swmm5.Node_[j].crownElev)))) {
                    NodeStats[j].@timeSurcharged = (NodeStats[j].timeSurcharged + tStep);
                }
            }
        }
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            k = F_swmm5.Node_[j].subIndex;
            StorageStats[k].@avgVol = (StorageStats[k].avgVol + newVolume);
            StorageStats[k].@evapLosses = (StorageStats[k].evapLosses + F_swmm5.Storage[F_swmm5.Node_[j].subIndex].evapLoss);
            StorageStats[k].@exfilLosses = (StorageStats[k].exfilLosses + F_swmm5.Storage[F_swmm5.Node_[j].subIndex].exfilLoss);
            newVolume = (((((newVolume) <= (F_swmm5.Node_[j].fullVolume))) ? (newVolume) : (F_swmm5.Node_[j].fullVolume)));
            if (R.dgt(newVolume, StorageStats[k].maxVol)) {
                StorageStats[k].@maxVol = newVolume;
                StorageStats[k].@maxVolDate = aDate;
            }
            StorageStats[k].@maxFlow = (((R.dge((StorageStats[k].maxFlow), (F_swmm5.Node_[j].outflow))) ? (StorageStats[k].maxFlow) : (F_swmm5.Node_[j].outflow)));
        }
        if ((F_swmm5.Node_[j].type == OUTFALL)) {
            k = F_swmm5.Node_[j].subIndex;
            if (R.dge(F_swmm5.Node_[j].inflow, 0.001d)) {
                OutfallStats[k].@avgFlow = (OutfallStats[k].avgFlow + F_swmm5.Node_[j].inflow);
                OutfallStats[k].@maxFlow = (((R.dge((OutfallStats[k].maxFlow), (F_swmm5.Node_[j].inflow))) ? (OutfallStats[k].maxFlow) : (F_swmm5.Node_[j].inflow)));
                OutfallStats[k].@totalPeriods = OutfallStats[k].totalPeriods + 1;
            }
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                OutfallStats[k].totalLoad[p] += ((F_swmm5.Node_[j].inflow * F_swmm5.Node_[j].newQual[p]) * tStep);
            }
            SysOutfallFlow += F_swmm5.Node_[j].inflow;
        }
        NodeStats[j].@totLatFlow = (NodeStats[j].totLatFlow + (((((F_swmm5.Node_[j].oldLatFlow + F_swmm5.Node_[j].newLatFlow)) * 0.5d) * tStep)));
        if (R.dgt(Math.abs(F_swmm5.Node_[j].newLatFlow), Math.abs(NodeStats[j].maxLatFlow))) {
            NodeStats[j].@maxLatFlow = F_swmm5.Node_[j].newLatFlow;
        }
        if (R.dgt(F_swmm5.Node_[j].inflow, NodeStats[j].maxInflow)) {
            NodeStats[j].@maxInflow = F_swmm5.Node_[j].inflow;
            NodeStats[j].@maxInflowDate = aDate;
        }
        if (R.dgt(F_swmm5.Node_[j].overflow, NodeStats[j].maxOverflow)) {
            NodeStats[j].@maxOverflow = F_swmm5.Node_[j].overflow;
            NodeStats[j].@maxOverflowDate = aDate;
        }
    }

    // stats.c:647
    static void stats_updateLinkStats(int j, double tStep, double aDate) {
        int k = 0;
        double q = 0.0d;
        double v = 0.0d;
        double dq = 0.0d;
        dq = (F_swmm5.Link[j].newFlow - F_swmm5.Link[j].oldFlow);
        q = Math.abs(F_swmm5.Link[j].newFlow);
        if (R.dgt(q, LinkStats[j].maxFlow)) {
            LinkStats[j].@maxFlow = q;
            LinkStats[j].@maxFlowDate = aDate;
        }
        v = F_link.link_getVelocity(j, q, F_swmm5.Link[j].newDepth);
        if (R.dgt(v, LinkStats[j].maxVeloc)) {
            LinkStats[j].@maxVeloc = v;
        }
        if (R.dgt(F_swmm5.Link[j].newDepth, LinkStats[j].maxDepth)) {
            LinkStats[j].@maxDepth = F_swmm5.Link[j].newDepth;
        }
        if ((F_swmm5.Link[j].type == PUMP)) {
            if (R.dge(q, F_swmm5.Link[j].qFull)) {
                LinkStats[j].@timeFullFlow = (LinkStats[j].timeFullFlow + tStep);
            }
            if (R.dgt(q, 0.001d)) {
                k = F_swmm5.Link[j].subIndex;
                PumpStats[k].@minFlow = (((((PumpStats[k].minFlow) <= (q))) ? (PumpStats[k].minFlow) : (q)));
                PumpStats[k].@maxFlow = LinkStats[j].maxFlow;
                PumpStats[k].@avgFlow = (PumpStats[k].avgFlow + q);
                PumpStats[k].@volume = (PumpStats[k].volume + (q * tStep));
                PumpStats[k].@utilized = (PumpStats[k].utilized + tStep);
                PumpStats[k].@energy = (PumpStats[k].energy + ((F_link.link_getPower(j) * tStep) / 3600.0d));
                if ((F_swmm5.Link[j].flowClass == DN_DRY)) {
                    PumpStats[k].@offCurveLow = (PumpStats[k].offCurveLow + tStep);
                }
                if ((F_swmm5.Link[j].flowClass == UP_DRY)) {
                    PumpStats[k].@offCurveHigh = (PumpStats[k].offCurveHigh + tStep);
                }
                if ((F_swmm5.Link[j].oldFlow < 0.001d)) {
                    PumpStats[k].@startUps = PumpStats[k].startUps + 1;
                }
                PumpStats[k].@totalPeriods = PumpStats[k].totalPeriods + 1;
                LinkStats[j].@timeSurcharged = (LinkStats[j].timeSurcharged + tStep);
                LinkStats[j].@timeFullUpstream = (LinkStats[j].timeFullUpstream + tStep);
                LinkStats[j].@timeFullDnstream = (LinkStats[j].timeFullDnstream + tStep);
            }
        } else if ((F_swmm5.Link[j].type == CONDUIT)) {
            if ((F_swmm5.Link[j].normalFlow != 0)) {
                LinkStats[j].@timeNormalFlow = (LinkStats[j].timeNormalFlow + tStep);
            }
            if ((F_swmm5.Link[j].inletControl != 0)) {
                LinkStats[j].@timeInletControl = (LinkStats[j].timeInletControl + tStep);
            }
            k = F_swmm5.Link[j].flowClass;
            if (((k >= 0) && (k < MAX_FLOW_CLASSES))) {
                LinkStats[j].timeInFlowClass[k] += tStep;
            }
            k = F_swmm5.Link[j].subIndex;
            if (R.dge(q, (F_swmm5.Link[j].qFull * ((double) (F_swmm5.Conduit[k].barrels))))) {
                LinkStats[j].@timeFullFlow = (LinkStats[j].timeFullFlow + tStep);
            }
            if ((F_swmm5.Conduit[k].capacityLimited != 0)) {
                LinkStats[j].@timeCapacityLimited = (LinkStats[j].timeCapacityLimited + tStep);
            }
            int __sw1 = F_swmm5.Conduit[k].fullState;
            if (__sw1 == ALL_FULL) {
                LinkStats[j].@timeSurcharged = (LinkStats[j].timeSurcharged + tStep);
                LinkStats[j].@timeFullUpstream = (LinkStats[j].timeFullUpstream + tStep);
                LinkStats[j].@timeFullDnstream = (LinkStats[j].timeFullDnstream + tStep);
            } else if (__sw1 == UP_FULL) {
                LinkStats[j].@timeFullUpstream = (LinkStats[j].timeFullUpstream + tStep);
            } else if (__sw1 == DN_FULL) {
                LinkStats[j].@timeFullDnstream = (LinkStats[j].timeFullDnstream + tStep);
            }
            if ((F_swmm5.Link[j].xsect.type == STREET_XSECT)) {
                LinkStats[j].@maxStreetFilled = (((R.dge((LinkStats[j].maxStreetFilled), (F_street.street_getExtentFilled(j)))) ? (LinkStats[j].maxStreetFilled) : (F_street.street_getExtentFilled(j))));
            }
        }
        k = LinkStats[j].flowTurnSign;
        LinkStats[j].@flowTurnSign = (((((dq) < 0)) ? ((-1)) : (1)));
        if (((R.dgt(Math.abs(dq), 0.001d)) && ((k * LinkStats[j].flowTurnSign) < 0))) {
            LinkStats[j].@flowTurns = LinkStats[j].flowTurns + 1L;
        }
    }

    // stats.c:756
    static void stats_findMaxStats() {
        int j = 0;
        double x = 0.0d;
        double z = 0.0d;
        double stepCount = 0.0d;
        L_f1: for (j = 0; (j < 5); j++) {
            MaxMassBalErrs[j].@objType = NODE;
            MaxMassBalErrs[j].@index = (-1);
            MaxMassBalErrs[j].@value = (-1.0d);
            MaxCourantCrit[j].@index = (-1);
            MaxCourantCrit[j].@value = (-1.0d);
            MaxFlowTurns[j].@index = (-1);
            MaxFlowTurns[j].@value = (-1.0d);
            MaxNonConverged[j].@index = (-1);
            MaxNonConverged[j].@value = 0.0d;
        }
        if ((F_swmm5.ReportStepCount > 2)) {
            stepCount = F_swmm5.ReportStepCount;
            z = (100.0d / (((2.0d / 3.0d) * ((stepCount - 2.0d)))));
            L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
                x = (LinkStats[j].flowTurns * z);
                stats_updateMaxStats(MaxFlowTurns, LINK, j, x);
            }
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].degree <= 0)) {
                continue L_f3;
            }
            if ((F_massbal.NodeInflow[j] <= 0.1d)) {
                continue L_f3;
            }
            if (R.dgt(F_massbal.NodeInflow[j], 0.0d)) {
                x = (1.0d - (F_massbal.NodeOutflow[j] / F_massbal.NodeInflow[j]));
            } else if (R.dgt(F_massbal.NodeOutflow[j], 0.0d)) {
                x = (-1.0d);
            } else {
                x = 0.0d;
            }
            stats_updateMaxStats(MaxMassBalErrs, NODE, j, (100.0d * x));
        }
        stepCount = TimeStepStats.timeStepCount;
        if ((F_swmm5.RouteModel == DW)) {
            L_f4: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                stats_updateMaxStats(MaxNonConverged, NODE, j, (NodeStats[j].nonConvergedCount / stepCount));
            }
        }
        if (((F_swmm5.RouteModel != DW) || (F_swmm5.CourantFactor.v == 0.0d))) {
            return;
        }
        if ((stepCount == 0)) {
            return;
        }
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            x = (NodeStats[j].timeCourantCritical / stepCount);
            stats_updateMaxStats(MaxCourantCrit, NODE, j, (100.0d * x));
        }
        L_f6: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            x = (LinkStats[j].timeCourantCritical / stepCount);
            stats_updateMaxStats(MaxCourantCrit, LINK, j, (100.0d * x));
        }
    }

    // stats.c:841
    static void stats_updateMaxStats(TMaxStats[] maxStats, int i, int j, double x) {
        int k = 0;
        TMaxStats maxStats1 = new TMaxStats();
        TMaxStats maxStats2 = new TMaxStats();
        maxStats1.@objType = i;
        maxStats1.@index = j;
        maxStats1.@value = x;
        L_f1: for (k = 0; (k < 5); k++) {
            if (R.dgt(Math.abs(maxStats1.value), Math.abs(maxStats[k].value))) {
                maxStats2 = maxStats[k].copy();
                maxStats[k] = maxStats1.copy();
                maxStats1 = maxStats2.copy();
            }
        }
    }

}
