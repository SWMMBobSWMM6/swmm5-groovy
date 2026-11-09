// Generated from EPA SWMM 5.2.4 (flowrout.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_flowrout {
    public static double flowrout_OMEGA = 0.55d;
    public static int MAXITER = 10;
    public static double STOPTOL = 0.005d;

    // flowrout.c:75
    static void flowrout_init(int routingModel) {
        if ((routingModel == DW)) {
            validateGeneralLayout();
            F_dynwave.dynwave_init();
            if ((F_swmm5.Fhotstart1.mode == NO_FILE)) {
                initNodeDepths();
                initLinkDepths();
            }
        } else {
            validateTreeLayout();
        }
        initNodes();
        initLinks(routingModel);
    }

    // flowrout.c:107
    static void flowrout_close(int routingModel) {
        if ((routingModel == DW)) {
            F_dynwave.dynwave_close();
        }
    }

    // flowrout.c:119
    static double flowrout_getRoutingStep(int routingModel, double fixedStep) {
        if ((routingModel == DW)) {
            return F_dynwave.dynwave_getRoutingStep(fixedStep);
        }
        return fixedStep;
    }

    // flowrout.c:136
    static int flowrout_execute(int[] links, int routingModel, double tStep) {
        int i = 0;
        int j = 0;
        int n1 = 0;
        DBox qin = new DBox(0.0d);
        DBox qout = new DBox(0.0d);
        double steps = 0.0d;
        if ((F_swmm5.ErrorCode != 0)) {
            return 0;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@updated = 0;
            F_swmm5.Node_[j].@overflow = 0.0d;
            if (((F_swmm5.Node_[j].type != STORAGE) && (R.dgt(F_swmm5.Node_[j].newVolume, F_swmm5.Node_[j].fullVolume)))) {
                F_swmm5.Node_[j].@overflow = (((F_swmm5.Node_[j].newVolume - F_swmm5.Node_[j].fullVolume)) / tStep);
            }
        }
        if ((routingModel == DW)) {
            return F_dynwave.dynwave_execute(tStep);
        }
        steps = 0.0d;
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            j = links[i];
            n1 = F_swmm5.Link[j].node1;
            if ((F_swmm5.Node_[n1].type == STORAGE)) {
                updateStorageState(n1, i, links, tStep);
            }
            qin.@v = getLinkInflow(j, tStep);
            if ((routingModel == SF)) {
                steps += (steadyflow_execute(j, qin, qout, tStep));
            } else {
                steps += (F_kinwave.kinwave_execute(j, qin, qout, tStep));
            }
            F_swmm5.Link[j].@newFlow = qout.v;
            F_swmm5.Node_[F_swmm5.Link[j].node1].@outflow = (F_swmm5.Node_[F_swmm5.Link[j].node1].outflow + qin.v);
            F_swmm5.Node_[F_swmm5.Link[j].node2].@inflow = (F_swmm5.Node_[F_swmm5.Link[j].node2].inflow + qout.v);
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            steps = (steps / F_swmm5.Nobjects[LINK]);
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            setNewNodeState(j, tStep);
        }
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            setNewLinkState(j);
        }
        return R.D2I(((steps + 0.5d)));
    }

    // flowrout.c:203
    static void validateTreeLayout() {
        int j = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            int __sw1 = F_swmm5.Node_[j].type;
            if (__sw1 == DIVIDER) {
                if ((F_swmm5.Node_[j].degree > 2)) {
                    F_report.report_writeErrorMsg(ERR_DIVIDER, F_swmm5.Node_[j].ID);
                }
            } else if (__sw1 == OUTFALL) {
                if ((F_swmm5.Node_[j].degree > 0)) {
                    F_report.report_writeErrorMsg(ERR_OUTFALL, F_swmm5.Node_[j].ID);
                }
            } else if (__sw1 == STORAGE) {
            } else {
                if ((F_swmm5.Node_[j].degree > 1)) {
                    F_report.report_writeErrorMsg(ERR_MULTI_OUTLET, F_swmm5.Node_[j].ID);
                }
            }
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            int __sw2 = F_swmm5.Link[j].type;
            if (__sw2 == CONDUIT) {
                if (((F_swmm5.Conduit[F_swmm5.Link[j].subIndex].slope < 0.0d) && (F_swmm5.Link[j].xsect.type != DUMMY))) {
                    F_report.report_writeErrorMsg(ERR_SLOPE, F_swmm5.Link[j].ID);
                }
            } else if (__sw2 == ORIFICE || __sw2 == WEIR || __sw2 == OUTLET) {
                if ((F_swmm5.Node_[F_swmm5.Link[j].node1].type != STORAGE)) {
                    F_report.report_writeErrorMsg(ERR_REGULATOR, F_swmm5.Link[j].ID);
                }
            }
        }
    }

    // flowrout.c:274
    static void validateGeneralLayout() {
        int i = 0;
        int j = 0;
        int outletCount = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_swmm5.Node_[i].@inflow = 0.0d;
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            i = F_swmm5.Link[j].node1;
            if ((F_swmm5.Node_[i].type != OUTFALL)) {
                i = F_swmm5.Link[j].node2;
            }
            F_swmm5.Node_[i].@inflow = (F_swmm5.Node_[i].inflow + 1.0d);
            if (((((F_swmm5.Link[j].type == CONDUIT) && (F_swmm5.Link[j].xsect.type == DUMMY))) || (((F_swmm5.Link[j].type == PUMP) && (F_swmm5.Pump[F_swmm5.Link[j].subIndex].type == IDEAL_PUMP))))) {
                i = F_swmm5.Link[j].node1;
                if ((F_swmm5.Link[j].direction < 0)) {
                    i = F_swmm5.Link[j].node2;
                }
                if ((F_swmm5.Node_[i].degree > 1)) {
                    F_report.report_writeErrorMsg(ERR_DUMMY_LINK, F_swmm5.Node_[i].ID);
                }
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == OUTFALL)) {
                if (((F_swmm5.Node_[i].degree + R.D2I(F_swmm5.Node_[i].inflow)) > 1)) {
                    F_report.report_writeErrorMsg(ERR_OUTFALL, F_swmm5.Node_[i].ID);
                } else {
                    outletCount++;
                }
            }
        }
        if ((outletCount == 0)) {
            F_report.report_writeErrorMsg(ERR_NO_OUTLETS, L0);
        }
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].inflow == 0.0d)) {
                F_swmm5.Node_[i].@degree = (-F_swmm5.Node_[i].degree);
            }
            F_swmm5.Node_[i].@inflow = 0.0d;
        }
    }

    // flowrout.c:337
    static void initNodeDepths() {
        int i = 0;
        int n = 0;
        double y = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_swmm5.Node_[i].@inflow = 0.0d;
            F_swmm5.Node_[i].@outflow = 0.0d;
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (R.dgt(F_swmm5.Link[i].newDepth, 0.0001d)) {
                y = (F_swmm5.Link[i].newDepth + F_swmm5.Link[i].offset1);
            } else {
                y = 0.0d;
            }
            n = F_swmm5.Link[i].node1;
            F_swmm5.Node_[n].@inflow = (F_swmm5.Node_[n].inflow + y);
            F_swmm5.Node_[n].@outflow = (F_swmm5.Node_[n].outflow + 1.0d);
            n = F_swmm5.Link[i].node2;
            F_swmm5.Node_[n].@inflow = (F_swmm5.Node_[n].inflow + y);
            F_swmm5.Node_[n].@outflow = (F_swmm5.Node_[n].outflow + 1.0d);
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == OUTFALL)) {
                continue L_f3;
            }
            if ((F_swmm5.Node_[i].type == STORAGE)) {
                continue L_f3;
            }
            if (R.dgt(F_swmm5.Node_[i].initDepth, 0.0d)) {
                continue L_f3;
            }
            if (R.dgt(F_swmm5.Node_[i].outflow, 0.0d)) {
                F_swmm5.Node_[i].@newDepth = (F_swmm5.Node_[i].inflow / F_swmm5.Node_[i].outflow);
            }
        }
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            F_link.link_setOutfallDepth(i);
        }
    }

    // flowrout.c:389
    static void initLinkDepths() {
        int i = 0;
        double y = 0.0d;
        double y1 = 0.0d;
        double y2 = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if ((F_swmm5.Link[i].type == CONDUIT)) {
                if ((F_swmm5.Link[i].q0 != 0.0d)) {
                    continue L_f1;
                }
                y1 = (F_swmm5.Node_[F_swmm5.Link[i].node1].newDepth - F_swmm5.Link[i].offset1);
                y1 = (((R.dge((y1), (0.0d))) ? (y1) : (0.0d)));
                y1 = (((((y1) <= (F_swmm5.Link[i].xsect.yFull))) ? (y1) : (F_swmm5.Link[i].xsect.yFull)));
                y2 = (F_swmm5.Node_[F_swmm5.Link[i].node2].newDepth - F_swmm5.Link[i].offset2);
                y2 = (((R.dge((y2), (0.0d))) ? (y2) : (0.0d)));
                y2 = (((((y2) <= (F_swmm5.Link[i].xsect.yFull))) ? (y2) : (F_swmm5.Link[i].xsect.yFull)));
                y = (0.5d * ((y1 + y2)));
                y = (((R.dge((y), (0.0001d))) ? (y) : (0.0001d)));
                F_swmm5.Link[i].@newDepth = y;
            }
        }
    }

    // flowrout.c:425
    static void initNodes() {
        int i = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_swmm5.Node_[i].@inflow = F_swmm5.Node_[i].newLatFlow;
            F_swmm5.Node_[i].@outflow = 0.0d;
            F_swmm5.Node_[i].@newVolume = 0.0d;
            if ((((F_swmm5.AllowPonding != 0) && (R.dgt(F_swmm5.Node_[i].pondedArea, 0.0d))) && (R.dgt(F_swmm5.Node_[i].newDepth, F_swmm5.Node_[i].fullDepth)))) {
                F_swmm5.Node_[i].@newVolume = (F_swmm5.Node_[i].fullVolume + (((F_swmm5.Node_[i].newDepth - F_swmm5.Node_[i].fullDepth)) * F_swmm5.Node_[i].pondedArea));
            } else {
                F_swmm5.Node_[i].@newVolume = F_node.node_getVolume(i, F_swmm5.Node_[i].newDepth);
            }
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (R.dge(F_swmm5.Link[i].newFlow, 0.0d)) {
                F_swmm5.Node_[F_swmm5.Link[i].node1].@outflow = (F_swmm5.Node_[F_swmm5.Link[i].node1].outflow + F_swmm5.Link[i].newFlow);
                F_swmm5.Node_[F_swmm5.Link[i].node2].@inflow = (F_swmm5.Node_[F_swmm5.Link[i].node2].inflow + F_swmm5.Link[i].newFlow);
            } else {
                F_swmm5.Node_[F_swmm5.Link[i].node1].@inflow = (F_swmm5.Node_[F_swmm5.Link[i].node1].inflow - F_swmm5.Link[i].newFlow);
                F_swmm5.Node_[F_swmm5.Link[i].node2].@outflow = (F_swmm5.Node_[F_swmm5.Link[i].node2].outflow - F_swmm5.Link[i].newFlow);
            }
        }
    }

    // flowrout.c:472
    static void initLinks(int routingModel) {
        int i = 0;
        int k = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if ((routingModel == SF)) {
                F_swmm5.Link[i].@newFlow = 0.0d;
            } else if ((F_swmm5.Link[i].type == CONDUIT)) {
                k = F_swmm5.Link[i].subIndex;
                F_swmm5.Conduit[k].@q1 = (F_swmm5.Link[i].newFlow / F_swmm5.Conduit[k].barrels);
                F_swmm5.Conduit[k].@q2 = F_swmm5.Conduit[k].q1;
                F_swmm5.Conduit[k].@a1 = F_xsect.xsect_getAofY(F_swmm5.Link[i].xsect, F_swmm5.Link[i].newDepth);
                F_swmm5.Conduit[k].@a2 = F_swmm5.Conduit[k].a1;
                F_swmm5.Link[i].@newVolume = ((F_swmm5.Conduit[k].a1 * F_link.link_getLength(i)) * F_swmm5.Conduit[k].barrels);
                F_swmm5.Link[i].@oldVolume = F_swmm5.Link[i].newVolume;
            }
        }
    }

    // flowrout.c:511
    static double getLinkInflow(int j, double dt) {
        int n1 = F_swmm5.Link[j].node1;
        double q = 0.0d;
        if ((((F_swmm5.Link[j].type == CONDUIT) || (F_swmm5.Link[j].type == PUMP)) || (F_swmm5.Node_[n1].type == STORAGE))) {
            q = F_link.link_getInflow(j);
        } else {
            q = 0.0d;
        }
        return F_node.node_getMaxOutflow(n1, q, dt);
    }

    // flowrout.c:531
    static void updateStorageState(int i, int j, int[] links, double dt) {
        int iter = 0;
        int stopped = 0;
        double vFixed = 0.0d;
        double v2 = 0.0d;
        double d1 = 0.0d;
        double d2 = 0.0d;
        if ((F_swmm5.Node_[i].type != STORAGE)) {
            return;
        }
        if ((F_swmm5.Node_[i].updated != 0)) {
            return;
        }
        vFixed = (F_swmm5.Node_[i].oldVolume + ((0.5d * (((F_swmm5.Node_[i].oldNetInflow + F_swmm5.Node_[i].inflow) - F_swmm5.Node_[i].outflow))) * dt));
        d1 = F_swmm5.Node_[i].newDepth;
        iter = 1;
        stopped = 0;
        L_w1: while (((iter < MAXITER) && (!(stopped != 0)))) {
            v2 = (vFixed - ((0.5d * getStorageOutflow(i, j, links, dt)) * dt));
            v2 = (((R.dge((0.0d), (v2))) ? (0.0d) : (v2)));
            F_swmm5.Node_[i].@overflow = 0.0d;
            if (R.dgt(v2, F_swmm5.Node_[i].fullVolume)) {
                F_swmm5.Node_[i].@overflow = (((v2 - (((R.dge((F_swmm5.Node_[i].oldVolume), (F_swmm5.Node_[i].fullVolume))) ? (F_swmm5.Node_[i].oldVolume) : (F_swmm5.Node_[i].fullVolume))))) / dt);
                if ((F_swmm5.Node_[i].overflow < 0.0001d)) {
                    F_swmm5.Node_[i].@overflow = 0.0d;
                }
                if (((!(F_swmm5.AllowPonding != 0)) || (F_swmm5.Node_[i].pondedArea == 0.0d))) {
                    v2 = F_swmm5.Node_[i].fullVolume;
                }
            }
            F_swmm5.Node_[i].@newVolume = v2;
            d2 = F_node.node_getDepth(i, v2);
            F_swmm5.Node_[i].@newDepth = d2;
            d2 = ((((1.0d - flowrout_OMEGA)) * d1) + (flowrout_OMEGA * d2));
            if ((Math.abs((d2 - d1)) <= STOPTOL)) {
                stopped = 1;
            }
            d1 = d2;
            iter++;
        }
        F_swmm5.Node_[i].@updated = 1;
    }

    // flowrout.c:605
    static double getStorageOutflow(int i, int j, int[] links, double dt) {
        int k = 0;
        int m = 0;
        double outflow = 0.0d;
        L_f1: for (k = j; (k < F_swmm5.Nobjects[LINK]); k++) {
            m = links[k];
            if ((F_swmm5.Link[m].node1 != i)) {
                break L_f1;
            }
            outflow += (getLinkInflow(m, dt));
        }
        return outflow;
    }

    // flowrout.c:629
    static void setNewNodeState(int j, double dt) {
        int canPond = 0;
        double newNetInflow = 0.0d;
        if ((F_swmm5.Node_[j].type == STORAGE)) {
            if ((F_swmm5.Node_[j].updated == 0)) {
                updateStorageState(j, F_swmm5.Nobjects[LINK], (null), dt);
            }
            return;
        }
        newNetInflow = ((F_swmm5.Node_[j].inflow - F_swmm5.Node_[j].outflow) - F_swmm5.Node_[j].losses);
        F_swmm5.Node_[j].@newVolume = (F_swmm5.Node_[j].oldVolume + (newNetInflow * dt));
        if ((F_swmm5.Node_[j].newVolume < 0.0001d)) {
            F_swmm5.Node_[j].@newVolume = 0.0d;
        }
        F_swmm5.Node_[j].@overflow = 0.0d;
        canPond = ((((F_swmm5.AllowPonding != 0) && (R.dgt(F_swmm5.Node_[j].pondedArea, 0.0d)))) ? 1 : 0);
        if (R.dgt(F_swmm5.Node_[j].newVolume, F_swmm5.Node_[j].fullVolume)) {
            F_swmm5.Node_[j].@overflow = (((F_swmm5.Node_[j].newVolume - (((R.dge((F_swmm5.Node_[j].oldVolume), (F_swmm5.Node_[j].fullVolume))) ? (F_swmm5.Node_[j].oldVolume) : (F_swmm5.Node_[j].fullVolume))))) / dt);
            if ((F_swmm5.Node_[j].overflow < 0.0001d)) {
                F_swmm5.Node_[j].@overflow = 0.0d;
            }
            if (!(canPond != 0)) {
                F_swmm5.Node_[j].@newVolume = F_swmm5.Node_[j].fullVolume;
            }
        }
        F_swmm5.Node_[j].@newDepth = F_node.node_getDepth(j, F_swmm5.Node_[j].newVolume);
    }

    // flowrout.c:673
    static void setNewLinkState(int j) {
        int k = 0;
        double a = 0.0d;
        double y1 = 0.0d;
        double y2 = 0.0d;
        F_swmm5.Link[j].@newDepth = 0.0d;
        F_swmm5.Link[j].@newVolume = 0.0d;
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            k = F_swmm5.Link[j].subIndex;
            a = (0.5d * ((F_swmm5.Conduit[k].a1 + F_swmm5.Conduit[k].a2)));
            F_swmm5.Link[j].@newVolume = ((a * F_link.link_getLength(j)) * F_swmm5.Conduit[k].barrels);
            y1 = F_xsect.xsect_getYofA(F_swmm5.Link[j].xsect, F_swmm5.Conduit[k].a1);
            y2 = F_xsect.xsect_getYofA(F_swmm5.Link[j].xsect, F_swmm5.Conduit[k].a2);
            F_swmm5.Link[j].@newDepth = (0.5d * ((y1 + y2)));
            updateNodeDepth(F_swmm5.Link[j].node1, (y1 + F_swmm5.Link[j].offset1));
            updateNodeDepth(F_swmm5.Link[j].node2, (y2 + F_swmm5.Link[j].offset2));
            if (R.dge(F_swmm5.Conduit[k].a1, F_swmm5.Link[j].xsect.aFull)) {
                F_swmm5.Conduit[k].@capacityLimited = 1;
                F_swmm5.Conduit[k].@fullState = ALL_FULL;
            } else {
                F_swmm5.Conduit[k].@capacityLimited = 0;
                F_swmm5.Conduit[k].@fullState = 0;
            }
        }
    }

    // flowrout.c:717
    static void updateNodeDepth(int i, double y) {
        if ((F_swmm5.Node_[i].type == STORAGE)) {
            return;
        }
        if ((((F_swmm5.Node_[i].type != OUTFALL) && (F_swmm5.Node_[i].degree > 0)) && (R.dgt(F_swmm5.Node_[i].overflow, 0.0d)))) {
            y = F_swmm5.Node_[i].fullDepth;
        }
        if ((F_swmm5.Node_[i].newDepth < y)) {
            F_swmm5.Node_[i].@newDepth = y;
            if (((R.dgt(F_swmm5.Node_[i].fullDepth, 0.0d)) && (R.dgt(y, F_swmm5.Node_[i].fullDepth)))) {
                F_swmm5.Node_[i].@newDepth = F_swmm5.Node_[i].fullDepth;
            }
        }
    }

    // flowrout.c:748
    static int steadyflow_execute(int j, DRef qin, DRef qout, double tStep) {
        int k = 0;
        double s = 0.0d;
        double q = 0.0d;
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            k = F_swmm5.Link[j].subIndex;
            q = ((qin.get()) / F_swmm5.Conduit[k].barrels);
            if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
                F_swmm5.Conduit[k].@a1 = 0.0d;
            } else {
                q -= (F_link.link_getLossRate(j, SF, q, tStep));
                if (R.dgt(q, F_swmm5.Link[j].qFull)) {
                    q = F_swmm5.Link[j].qFull;
                    F_swmm5.Conduit[k].@a1 = F_swmm5.Link[j].xsect.aFull;
                    qin.set((q * F_swmm5.Conduit[k].barrels));
                } else {
                    s = (q / F_swmm5.Conduit[k].beta);
                    F_swmm5.Conduit[k].@a1 = F_xsect.xsect_getAofS(F_swmm5.Link[j].xsect, s);
                }
            }
            F_swmm5.Conduit[k].@a2 = F_swmm5.Conduit[k].a1;
            F_swmm5.Conduit[k].@q1Old = F_swmm5.Conduit[k].q1;
            F_swmm5.Conduit[k].@q2Old = F_swmm5.Conduit[k].q2;
            F_swmm5.Conduit[k].@q1 = q;
            F_swmm5.Conduit[k].@q2 = q;
            qout.set((q * F_swmm5.Conduit[k].barrels));
        } else {
            qout.set((qin.get()));
        }
        return 1;
    }

}
