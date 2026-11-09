// Generated from EPA SWMM 5.2.4 (dynwave.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_dynwave {
    public static double MINTIMESTEP = 0.001d;
    public static double dynwave_OMEGA = 0.5d;
    public static double DEFAULT_SURFAREA = 12.566d;
    public static double DEFAULT_HEADTOL = 0.005d;
    public static double EXTRAN_CROWN_CUTOFF = 0.96d;
    public static double SLOT_CROWN_CUTOFF = 0.985257d;
    public static int DEFAULT_MAXTRIALS = 8;
    public static double VariableStep = 0.0d;
    public static TXnode[] Xnode = null;
    public static double Omega = 0.0d;
    public static int Steps = 0;

    // dynwave.c:117
    static void dynwave_init() {
        int i = 0;
        int j = 0;
        double z = 0.0d;
        VariableStep = 0.0d;
        Xnode = R.fill(new TXnode[(int) (F_swmm5.Nobjects[NODE])], (() -> new TXnode()));
        if ((Xnode == null)) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L45);
            return;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            Xnode[i].@newSurfArea = 0.0d;
            Xnode[i].@oldSurfArea = 0.0d;
            F_swmm5.Node_[i].@crownElev = F_swmm5.Node_[i].invertElev;
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            j = F_swmm5.Link[i].node1;
            z = ((F_swmm5.Node_[j].invertElev + F_swmm5.Link[i].offset1) + F_swmm5.Link[i].xsect.yFull);
            F_swmm5.Node_[j].@crownElev = (((R.dge((F_swmm5.Node_[j].crownElev), (z))) ? (F_swmm5.Node_[j].crownElev) : (z)));
            j = F_swmm5.Link[i].node2;
            z = ((F_swmm5.Node_[j].invertElev + F_swmm5.Link[i].offset2) + F_swmm5.Link[i].xsect.yFull);
            F_swmm5.Node_[j].@crownElev = (((R.dge((F_swmm5.Node_[j].crownElev), (z))) ? (F_swmm5.Node_[j].crownElev) : (z)));
            F_swmm5.Link[i].@flowClass = DRY;
            F_swmm5.Link[i].@dqdh = 0.0d;
        }
        if ((F_swmm5.SurchargeMethod == SLOT)) {
            F_swmm5.@CrownCutoff = SLOT_CROWN_CUTOFF;
        } else {
            F_swmm5.@CrownCutoff = EXTRAN_CROWN_CUTOFF;
        }
    }

    // dynwave.c:165
    static void dynwave_close() {
        if ((Xnode != null)) {
            R.free(Xnode);
            Xnode = (null);
        }
    }

    // dynwave.c:177
    static void dynwave_validate() {
        if (R.dgt(F_swmm5.MinRouteStep.v, F_swmm5.RouteStep)) {
            F_swmm5.MinRouteStep.@v = F_swmm5.RouteStep;
        }
        if ((F_swmm5.MinRouteStep.v < MINTIMESTEP)) {
            F_swmm5.MinRouteStep.@v = MINTIMESTEP;
        }
        if ((F_swmm5.MinSurfArea.v == 0.0d)) {
            F_swmm5.MinSurfArea.@v = DEFAULT_SURFAREA;
        } else {
            F_swmm5.MinSurfArea.@v = (F_swmm5.MinSurfArea.v / (F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LENGTH)));
        }
        if ((F_swmm5.HeadTol.v == 0.0d)) {
            F_swmm5.HeadTol.@v = DEFAULT_HEADTOL;
        } else {
            F_swmm5.HeadTol.@v = (F_swmm5.HeadTol.v / (F_swmm5.UCF(LENGTH)));
        }
        if ((F_swmm5.MaxTrials == 0)) {
            F_swmm5.@MaxTrials = DEFAULT_MAXTRIALS;
        }
    }

    // dynwave.c:195
    static double dynwave_getRoutingStep(double fixedStep) {
        if ((F_swmm5.CourantFactor.v == 0.0d)) {
            return fixedStep;
        }
        if ((fixedStep < MINTIMESTEP)) {
            return fixedStep;
        }
        if ((VariableStep == 0.0d)) {
            VariableStep = F_swmm5.MinRouteStep.v;
        } else {
            VariableStep = getVariableStep(fixedStep);
        }
        VariableStep = (Math.floor((1000.0d * VariableStep)) / 1000.0d);
        return VariableStep;
    }

    // dynwave.c:224
    static int dynwave_execute(double tStep) {
        int converged = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return 0;
        }
        Steps = 0;
        converged = 0;
        Omega = dynwave_OMEGA;
        initRoutingStep();
        L_w1: while ((Steps < F_swmm5.MaxTrials)) {
            initNodeStates();
            findLinkFlows(tStep);
            converged = findNodeDepths(tStep);
            Steps++;
            if ((Steps > 1)) {
                if ((converged != 0)) {
                    break L_w1;
                }
                findBypassedLinks();
            }
        }
        if (!(converged != 0)) {
            updateConvergenceStats();
        }
        findLimitedLinks();
        return Steps;
    }

    // dynwave.c:266
    static void updateConvergenceStats() {
        int i = 0;
        F_swmm5.@NonConvergeCount = F_swmm5.NonConvergeCount + 1L;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_stats.stats_updateConvergenceStats(i, Xnode[i].converged);
        }
    }

    // dynwave.c:276
    static void initRoutingStep() {
        int i = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            Xnode[i].@converged = 0;
            Xnode[i].@dYdT = 0.0d;
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            F_swmm5.Link[i].@bypassed = 0;
            F_swmm5.Link[i].@surfArea1 = 0.0d;
            F_swmm5.Link[i].@surfArea2 = 0.0d;
        }
        L_f3: for (i = 0; (i < F_swmm5.Nlinks[CONDUIT]); i++) {
            F_swmm5.Conduit[i].@a2 = F_swmm5.Conduit[i].a1;
        }
    }

    // dynwave.c:297
    static void initNodeStates() {
        int i = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.AllowPonding != 0)) {
                Xnode[i].@newSurfArea = F_node.node_getPondedArea(i, F_swmm5.Node_[i].newDepth);
            } else {
                Xnode[i].@newSurfArea = F_node.node_getSurfArea(i, F_swmm5.Node_[i].newDepth);
            }
            F_swmm5.Node_[i].@inflow = 0.0d;
            F_swmm5.Node_[i].@outflow = F_swmm5.Node_[i].losses;
            if (R.dge(F_swmm5.Node_[i].newLatFlow, 0.0d)) {
                F_swmm5.Node_[i].@inflow = (F_swmm5.Node_[i].inflow + F_swmm5.Node_[i].newLatFlow);
            } else {
                F_swmm5.Node_[i].@outflow = (F_swmm5.Node_[i].outflow - F_swmm5.Node_[i].newLatFlow);
            }
            Xnode[i].@sumdqdh = 0.0d;
        }
    }

    // dynwave.c:335
    static void findBypassedLinks() {
        int i = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (((Xnode[F_swmm5.Link[i].node1].converged != 0) && (Xnode[F_swmm5.Link[i].node2].converged != 0))) {
                F_swmm5.Link[i].@bypassed = 1;
            } else {
                F_swmm5.Link[i].@bypassed = 0;
            }
        }
    }

    // dynwave.c:349
    static void findLimitedLinks() {
        int j = 0;
        int n1 = 0;
        int n2 = 0;
        int k = 0;
        double h1 = 0.0d;
        double h2 = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if (!(isTrueConduit(j) != 0)) {
                continue L_f1;
            }
            k = F_swmm5.Link[j].subIndex;
            F_swmm5.Conduit[k].@capacityLimited = 0;
            if (R.dge(F_swmm5.Conduit[k].a1, F_swmm5.Link[j].xsect.aFull)) {
                n1 = F_swmm5.Link[j].node1;
                n2 = F_swmm5.Link[j].node2;
                h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
                h2 = (F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev);
                if (R.dgt(((h1 - h2)), (Math.abs(F_swmm5.Conduit[k].slope) * F_swmm5.Conduit[k].length))) {
                    F_swmm5.Conduit[k].@capacityLimited = 1;
                }
            }
        }
    }

    // dynwave.c:382
    static void findLinkFlows(double dt) {
        int i = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (((isTrueConduit(i) != 0) && (!(F_swmm5.Link[i].bypassed != 0)))) {
                F_dwflow.dwflow_findConduitFlow(i, Steps, Omega, dt);
            }
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if ((isTrueConduit(i) != 0)) {
                updateNodeFlows(i);
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (!(isTrueConduit(i) != 0)) {
                if (!(F_swmm5.Link[i].bypassed != 0)) {
                    findNonConduitFlow(i, dt);
                }
                updateNodeFlows(i);
            }
        }
    }

    // dynwave.c:416
    static int isTrueConduit(int j) {
        return ((((F_swmm5.Link[j].type == CONDUIT) && (F_swmm5.Link[j].xsect.type != DUMMY))) ? 1 : 0);
    }

    // dynwave.c:423
    static void findNonConduitFlow(int i, double dt) {
        double qLast = 0.0d;
        double qNew = 0.0d;
        qLast = F_swmm5.Link[i].newFlow;
        F_swmm5.Link[i].@dqdh = 0.0d;
        qNew = F_link.link_getInflow(i);
        if ((F_swmm5.Link[i].type == PUMP)) {
            qNew = getModPumpFlow(i, qNew, dt);
        }
        findNonConduitSurfArea(i);
        if (((Steps > 0) && (F_swmm5.Link[i].type != PUMP))) {
            qNew = ((((1.0d - Omega)) * qLast) + (Omega * qNew));
            if (((qNew * qLast) < 0.0d)) {
                qNew = (0.001d * (((((qNew) < 0)) ? ((-1)) : (1))));
            }
        }
        F_swmm5.Link[i].@newFlow = qNew;
    }

    // dynwave.c:458
    static double getModPumpFlow(int i, double q, double dt) {
        int j = F_swmm5.Link[i].node1;
        int k = F_swmm5.Link[i].subIndex;
        double newNetInflow = 0.0d;
        double netFlowVolume = 0.0d;
        double y = 0.0d;
        if ((q == 0.0d)) {
            return q;
        }
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            return F_node.node_getMaxOutflow(j, q, dt);
        }
        int __sw1 = F_swmm5.Pump[k].type;
        if (__sw1 == TYPE1_PUMP) {
            return F_node.node_getMaxOutflow(j, q, dt);
        } else if (__sw1 == TYPE2_PUMP || __sw1 == TYPE4_PUMP || __sw1 == TYPE3_PUMP) {
            newNetInflow = ((F_swmm5.Node_[j].inflow - F_swmm5.Node_[j].outflow) - q);
            netFlowVolume = ((0.5d * ((F_swmm5.Node_[j].oldNetInflow + newNetInflow))) * dt);
            y = (F_swmm5.Node_[j].oldDepth + (netFlowVolume / Xnode[j].newSurfArea));
            if ((y <= 0.0d)) {
                return F_swmm5.Node_[j].inflow;
            }
        }
        return q;
    }

    // dynwave.c:503
    static void findNonConduitSurfArea(int i) {
        if ((F_swmm5.Link[i].type == ORIFICE)) {
            F_swmm5.Link[i].@surfArea1 = (F_swmm5.Orifice[F_swmm5.Link[i].subIndex].surfArea / 2.0d);
        } else {
            F_swmm5.Link[i].@surfArea1 = 0.0d;
        }
        F_swmm5.Link[i].@surfArea2 = F_swmm5.Link[i].surfArea1;
        if (((F_swmm5.Link[i].flowClass == UP_CRITICAL) || (F_swmm5.Node_[F_swmm5.Link[i].node1].type == STORAGE))) {
            F_swmm5.Link[i].@surfArea1 = 0.0d;
        }
        if (((F_swmm5.Link[i].flowClass == DN_CRITICAL) || (F_swmm5.Node_[F_swmm5.Link[i].node2].type == STORAGE))) {
            F_swmm5.Link[i].@surfArea2 = 0.0d;
        }
    }

    // dynwave.c:528
    static void updateNodeFlows(int i) {
        int k = 0;
        int barrels = 1;
        int n1 = F_swmm5.Link[i].node1;
        int n2 = F_swmm5.Link[i].node2;
        double q = F_swmm5.Link[i].newFlow;
        double conduitLossRate = 0.0d;
        if (R.dge(q, 0.0d)) {
            F_swmm5.Node_[n1].@outflow = (F_swmm5.Node_[n1].outflow + q);
            F_swmm5.Node_[n2].@inflow = (F_swmm5.Node_[n2].inflow + q);
        } else {
            F_swmm5.Node_[n1].@inflow = (F_swmm5.Node_[n1].inflow - q);
            F_swmm5.Node_[n2].@outflow = (F_swmm5.Node_[n2].outflow - q);
        }
        if ((F_swmm5.Link[i].type == CONDUIT)) {
            k = F_swmm5.Link[i].subIndex;
            barrels = F_swmm5.Conduit[k].barrels;
            conduitLossRate = (((F_swmm5.Conduit[k].evapLossRate + F_swmm5.Conduit[k].seepLossRate)) * barrels);
            if (R.dgt(conduitLossRate, 0.0d)) {
                if (((F_swmm5.Node_[n1].type != OUTFALL) && (F_swmm5.Node_[n2].type != OUTFALL))) {
                    conduitLossRate = (conduitLossRate / 2.0d);
                }
                if ((F_swmm5.Node_[n1].type != OUTFALL)) {
                    F_swmm5.Node_[n1].@outflow = (F_swmm5.Node_[n1].outflow + conduitLossRate);
                }
                if ((F_swmm5.Node_[n2].type != OUTFALL)) {
                    F_swmm5.Node_[n2].@outflow = (F_swmm5.Node_[n2].outflow + conduitLossRate);
                }
            }
        }
        Xnode[F_swmm5.Link[i].node1].@newSurfArea = (Xnode[F_swmm5.Link[i].node1].newSurfArea + (F_swmm5.Link[i].surfArea1 * barrels));
        Xnode[F_swmm5.Link[i].node2].@newSurfArea = (Xnode[F_swmm5.Link[i].node2].newSurfArea + (F_swmm5.Link[i].surfArea2 * barrels));
        Xnode[F_swmm5.Link[i].node1].@sumdqdh = (Xnode[F_swmm5.Link[i].node1].sumdqdh + F_swmm5.Link[i].dqdh);
        if ((F_swmm5.Link[i].type == PUMP)) {
            k = F_swmm5.Link[i].subIndex;
            if ((F_swmm5.Pump[k].type != TYPE4_PUMP)) {
                Xnode[n2].@sumdqdh = (Xnode[n2].sumdqdh + F_swmm5.Link[i].dqdh);
            }
        } else {
            Xnode[n2].@sumdqdh = (Xnode[n2].sumdqdh + F_swmm5.Link[i].dqdh);
        }
    }

    // dynwave.c:593
    static int findNodeDepths(double dt) {
        int i = 0;
        double yOld = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            F_link.link_setOutfallDepth(i);
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == OUTFALL)) {
                continue L_f2;
            }
            yOld = F_swmm5.Node_[i].newDepth;
            setNodeDepth(i, dt);
            Xnode[i].@converged = 1;
            if (R.dgt(Math.abs((yOld - F_swmm5.Node_[i].newDepth)), F_swmm5.HeadTol.v)) {
                Xnode[i].@converged = 0;
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == OUTFALL)) {
                continue L_f3;
            }
            if ((Xnode[i].converged == 0)) {
                return 0;
            }
        }
        return 1;
    }

    // dynwave.c:636
    static void setNodeDepth(int i, double dt) {
        int canPond = 0;
        int isPonded = 0;
        int isSurcharged = 0;
        double dQ = 0.0d;
        double dV = 0.0d;
        double dy = 0.0d;
        double yMax = 0.0d;
        double yOld = 0.0d;
        double yLast = 0.0d;
        double yNew = 0.0d;
        double yCrown = 0.0d;
        double surfArea = 0.0d;
        double denom = 0.0d;
        double corr = 0.0d;
        double f = 0.0d;
        canPond = ((((F_swmm5.AllowPonding != 0) && (R.dgt(F_swmm5.Node_[i].pondedArea, 0.0d)))) ? 1 : 0);
        isPonded = ((((canPond != 0) && (R.dgt(F_swmm5.Node_[i].newDepth, F_swmm5.Node_[i].fullDepth)))) ? 1 : 0);
        yCrown = (F_swmm5.Node_[i].crownElev - F_swmm5.Node_[i].invertElev);
        yOld = F_swmm5.Node_[i].oldDepth;
        yLast = F_swmm5.Node_[i].newDepth;
        F_swmm5.Node_[i].@overflow = 0.0d;
        surfArea = Xnode[i].newSurfArea;
        surfArea = (((R.dge((surfArea), (F_swmm5.MinSurfArea.v))) ? (surfArea) : (F_swmm5.MinSurfArea.v)));
        dQ = (F_swmm5.Node_[i].inflow - F_swmm5.Node_[i].outflow);
        dV = ((0.5d * ((F_swmm5.Node_[i].oldNetInflow + dQ))) * dt);
        if ((F_swmm5.SurchargeMethod == EXTRAN)) {
            if ((isPonded != 0)) {
                isSurcharged = 0;
            } else if ((F_swmm5.Node_[i].type == STORAGE)) {
                isSurcharged = ((((R.dgt(F_swmm5.Node_[i].surDepth, 0.0d)) && (R.dgt(yLast, F_swmm5.Node_[i].fullDepth)))) ? 1 : 0);
            } else {
                isSurcharged = ((((R.dgt(yCrown, 0.0d)) && (R.dgt(yLast, yCrown)))) ? 1 : 0);
            }
        }
        if (!(isSurcharged != 0)) {
            dy = (dV / surfArea);
            yNew = (yOld + dy);
            if (!(isPonded != 0)) {
                Xnode[i].@oldSurfArea = surfArea;
            }
            if ((Steps > 0)) {
                yNew = ((((1.0d - Omega)) * yLast) + (Omega * yNew));
            }
            if (((isPonded != 0) && (yNew < F_swmm5.Node_[i].fullDepth))) {
                yNew = (F_swmm5.Node_[i].fullDepth - 0.0001d);
            }
        } else {
            corr = 1.0d;
            if ((F_swmm5.Node_[i].degree < 0)) {
                corr = 0.6d;
            }
            denom = Xnode[i].sumdqdh;
            if ((yLast < (1.25d * yCrown))) {
                f = (((yLast - yCrown)) / yCrown);
                denom += ((((Xnode[i].oldSurfArea / dt) - Xnode[i].sumdqdh)) * Glibm.exp(((-15.0d) * f)));
            }
            if ((denom == 0.0d)) {
                dy = 0.0d;
            } else {
                dy = ((corr * dQ) / denom);
            }
            yNew = (yLast + dy);
            if ((yNew < yCrown)) {
                yNew = (yCrown - 0.0001d);
            }
            if (((canPond != 0) && (R.dgt(yNew, F_swmm5.Node_[i].fullDepth)))) {
                yNew = (F_swmm5.Node_[i].fullDepth + 0.0001d);
            }
        }
        if ((yNew < 0)) {
            yNew = 0.0d;
        }
        yMax = F_swmm5.Node_[i].fullDepth;
        if ((canPond == 0)) {
            yMax += F_swmm5.Node_[i].surDepth;
        }
        if (R.dgt(yNew, yMax)) {
            yNew = getFloodedDepth(i, canPond, dV, yNew, yMax, dt);
        } else {
            F_swmm5.Node_[i].@newVolume = F_node.node_getVolume(i, yNew);
        }
        Xnode[i].@dYdT = (Math.abs((yNew - yOld)) / dt);
        F_swmm5.Node_[i].@newDepth = yNew;
    }

    // dynwave.c:766
    static double getFloodedDepth(int i, int canPond, double dV, double yNew, double yMax, double dt) {
        if ((canPond == 0)) {
            F_swmm5.Node_[i].@overflow = (dV / dt);
            F_swmm5.Node_[i].@newVolume = F_swmm5.Node_[i].fullVolume;
            yNew = yMax;
        } else {
            F_swmm5.Node_[i].@newVolume = (((R.dge((((F_swmm5.Node_[i].oldVolume + dV))), (F_swmm5.Node_[i].fullVolume))) ? (((F_swmm5.Node_[i].oldVolume + dV))) : (F_swmm5.Node_[i].fullVolume)));
            F_swmm5.Node_[i].@overflow = (((F_swmm5.Node_[i].newVolume - (((R.dge((F_swmm5.Node_[i].oldVolume), (F_swmm5.Node_[i].fullVolume))) ? (F_swmm5.Node_[i].oldVolume) : (F_swmm5.Node_[i].fullVolume))))) / dt);
        }
        if ((F_swmm5.Node_[i].overflow < 0.0001d)) {
            F_swmm5.Node_[i].@overflow = 0.0d;
        }
        return yNew;
    }

    // dynwave.c:799
    static double getVariableStep(double maxStep) {
        IBox minLink = new IBox((-1));
        IBox minNode = new IBox((-1));
        double tMin = 0.0d;
        double tMinLink = 0.0d;
        double tMinNode = 0.0d;
        tMin = maxStep;
        tMinLink = getLinkStep(tMin, minLink);
        tMinNode = getNodeStep(tMinLink, minNode);
        tMin = tMinLink;
        if ((tMinNode < tMin)) {
            tMin = tMinNode;
            minLink.@v = (-1);
        }
        F_stats.stats_updateCriticalTimeCount(minNode.v, minLink.v);
        if ((tMin < F_swmm5.MinRouteStep.v)) {
            tMin = F_swmm5.MinRouteStep.v;
        }
        return tMin;
    }

    // dynwave.c:836
    static double getLinkStep(double tMin, IRef minLink) {
        int i = 0;
        int k = 0;
        double q = 0.0d;
        double t = 0.0d;
        double tLink = tMin;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if ((F_swmm5.Link[i].type == CONDUIT)) {
                k = F_swmm5.Link[i].subIndex;
                q = (Math.abs(F_swmm5.Link[i].newFlow) / F_swmm5.Conduit[k].barrels);
                if ((((q <= 0.0001d) || (F_swmm5.Conduit[k].a1 <= 0.0001d)) || (F_swmm5.Link[i].froude <= 0.01d))) {
                    continue L_f1;
                }
                t = ((F_swmm5.Link[i].newVolume / F_swmm5.Conduit[k].barrels) / q);
                t = ((t * F_swmm5.Conduit[k].modLength) / F_link.link_getLength(i));
                t = (((t * F_swmm5.Link[i].froude) / ((1.0d + F_swmm5.Link[i].froude))) * F_swmm5.CourantFactor.v);
                if ((t < tLink)) {
                    tLink = t;
                    minLink.set(i);
                }
            }
        }
        return tLink;
    }

    // dynwave.c:881
    static double getNodeStep(double tMin, IRef minNode) {
        int i = 0;
        double maxDepth = 0.0d;
        double dYdT = 0.0d;
        double t1 = 0.0d;
        double tNode = tMin;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == OUTFALL)) {
                continue L_f1;
            }
            if ((F_swmm5.Node_[i].newDepth <= 0.0001d)) {
                continue L_f1;
            }
            if (R.dge((F_swmm5.Node_[i].newDepth + 0.0001d), (F_swmm5.Node_[i].crownElev - F_swmm5.Node_[i].invertElev))) {
                continue L_f1;
            }
            maxDepth = (((F_swmm5.Node_[i].crownElev - F_swmm5.Node_[i].invertElev)) * 0.25d);
            if ((maxDepth < 0.0001d)) {
                continue L_f1;
            }
            dYdT = Xnode[i].dYdT;
            if ((dYdT < 0.0001d)) {
                continue L_f1;
            }
            t1 = (maxDepth / dYdT);
            if ((t1 < tNode)) {
                tNode = t1;
                minNode.set(i);
            }
        }
        return tNode;
    }

}
