// Generated from EPA SWMM 5.2.4 (routing.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_routing {
    public static int[] SortedLinks = null;
    public static int NextEvent = 0;
    public static int BetweenEvents = 0;
    public static double NewRuleTime = 0.0d;

    // routing.c:93
    static int routing_open() {
        if (!(F_treatmnt.treatmnt_open() != 0)) {
            return F_swmm5.ErrorCode;
        }
        SortedLinks = (null);
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            SortedLinks = new int[(int) (F_swmm5.Nobjects[LINK])];
            if (!(SortedLinks != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
                return F_swmm5.ErrorCode;
            }
            F_toposort.toposort_sortLinks(SortedLinks);
            if ((F_swmm5.ErrorCode != 0)) {
                return F_swmm5.ErrorCode;
            }
        }
        F_iface.iface_openRoutingFiles();
        F_flowrout.flowrout_init(F_swmm5.RouteModel);
        if ((F_swmm5.Fhotstart1.mode == NO_FILE)) {
            F_qualrout.qualrout_init();
        }
        if ((F_swmm5.NumEvents > 0)) {
            sortEvents();
        }
        NextEvent = 0;
        BetweenEvents = (((F_swmm5.NumEvents > 0)) ? 1 : 0);
        NewRuleTime = 0.0d;
        return F_swmm5.ErrorCode;
    }

    // routing.c:134
    static void routing_close(int routingModel) {
        F_iface.iface_closeRoutingFiles();
        F_flowrout.flowrout_close(routingModel);
        F_treatmnt.treatmnt_close();
        if ((SortedLinks != null)) {
            R.free(SortedLinks);
            SortedLinks = (null);
        }
    }

    // routing.c:152
    static double routing_getRoutingStep(int routingModel, double fixedStep) {
        double date1 = 0.0d;
        double date2 = 0.0d;
        double nextTime = 0.0d;
        double routingStep = 0.0d;
        double nextRuleTime = 0.0d;
        double nextRoutingTime = 0.0d;
        if ((F_swmm5.Nobjects[LINK] == 0)) {
            return fixedStep;
        }
        if (((F_swmm5.NumEvents > 0) && (BetweenEvents != 0))) {
            nextTime = (((((F_swmm5.NewRunoffTime) <= (F_swmm5.ReportTime))) ? (F_swmm5.NewRunoffTime) : (F_swmm5.ReportTime)));
            date1 = F_swmm5.getDateTime(F_swmm5.NewRoutingTime);
            date2 = F_swmm5.getDateTime(nextTime);
            if (((R.dgt(date2, date1)) && (date2 < F_swmm5.Event[NextEvent].start))) {
                routingStep = (((nextTime - F_swmm5.NewRoutingTime)) / 1000.0d);
            } else {
                date1 = F_swmm5.getDateTime((F_swmm5.NewRoutingTime + (1000.0d * fixedStep)));
                if ((date1 < F_swmm5.Event[NextEvent].start)) {
                    return fixedStep;
                }
            }
        }
        if ((routingStep == 0.0d)) {
            routingStep = F_flowrout.flowrout_getRoutingStep(routingModel, fixedStep);
        }
        if ((F_swmm5.RuleStep > 0)) {
            nextRuleTime = (NewRuleTime + (1000.0d * F_swmm5.RuleStep));
            nextRoutingTime = (F_swmm5.NewRoutingTime + (1000.0d * routingStep));
            if (R.dge(nextRoutingTime, nextRuleTime)) {
                routingStep = (((nextRuleTime - F_swmm5.NewRoutingTime)) / 1000.0d);
            }
        }
        return routingStep;
    }

    // routing.c:203
    static void routing_execute(int routingModel, double routingStep) {
        int trialsCount = 1;
        int actionCount = 0;
        int inSteadyState = 1;
        double currentDate = 0.0d;
        double stepFlowError = 0.0d;
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_massbal.massbal_updateRoutingTotals((routingStep / 2.0d));
        currentDate = F_swmm5.getDateTime(F_swmm5.NewRoutingTime);
        actionCount = evaluateControlRules(currentDate, routingStep);
        stepFlowError = F_massbal.massbal_getStepFlowError();
        F_massbal.massbal_initTimeStepTotals();
        initSystemInflows();
        BetweenEvents = isBetweenEvents(currentDate);
        if ((BetweenEvents == 0)) {
            addSystemInflows(currentDate, routingStep);
            F_inlet.inlet_findCapturedFlows(routingStep);
            inSteadyState = isInSteadyState(actionCount, stepFlowError);
            if ((inSteadyState == 0)) {
                trialsCount = routeFlow(routingModel, routingStep);
            }
            if (((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) {
                F_inlet.inlet_adjustQualInflows();
                F_qualrout.qualrout_execute(routingStep);
            }
            removeSystemOutflows(routingStep);
            F_inlet.inlet_adjustQualOutflows();
            if ((F_swmm5.Nobjects[LINK] > 0)) {
                F_stats.stats_updateFlowStats(routingStep, F_swmm5.getDateTime(F_swmm5.NewRoutingTime));
                F_stats.stats_updateTimeStepStats(routingStep, trialsCount, inSteadyState);
            }
        }
        F_massbal.massbal_updateRoutingTotals((routingStep / 2.0d));
    }

    // routing.c:269
    static int evaluateControlRules(double currentDate, double routingStep) {
        int j = 0;
        int actionCount = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_link.link_setTargetSetting(j);
        }
        if (((F_swmm5.RuleStep == 0) || (Math.abs((F_swmm5.NewRoutingTime - NewRuleTime)) < 1.0d))) {
            F_controls.controls_evaluate(currentDate, (currentDate - F_swmm5.StartDateTime), (routingStep / 86400));
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].targetSetting != F_swmm5.Link[j].setting)) {
                if (((F_swmm5.Link[j].targetSetting * F_swmm5.Link[j].setting) == 0.0d)) {
                    F_swmm5.Link[j].@timeLastSet = currentDate;
                }
                F_link.link_setSetting(j, routingStep);
                actionCount++;
            }
        }
        F_swmm5.@OldRoutingTime = F_swmm5.NewRoutingTime;
        F_swmm5.@NewRoutingTime = (F_swmm5.NewRoutingTime + (1000.0d * routingStep));
        if ((Math.abs((F_swmm5.NewRoutingTime - ((NewRuleTime + (1000.0d * F_swmm5.RuleStep))))) < 1)) {
            NewRuleTime += (1000.0d * F_swmm5.RuleStep);
        }
        return actionCount;
    }

    // routing.c:312
    static void initSystemInflows() {
        int j = 0;
        if ((F_swmm5.Nobjects[POLLUT] > 0)) {
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                F_node.node_setOldQualState(j);
            }
            L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
                F_link.link_setOldQualState(j);
            }
        }
        F_infil.infil_setInfilFactor((-1));
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@oldLatFlow = F_swmm5.Node_[j].newLatFlow;
            F_swmm5.Node_[j].@newLatFlow = 0.0d;
        }
    }

    // routing.c:337
    static int isBetweenEvents(double currentDate) {
        if ((F_swmm5.NumEvents == 0)) {
            return 0;
        }
        if (R.dgt(currentDate, F_swmm5.Event[NextEvent].end)) {
            NextEvent++;
            return 1;
        } else if (R.dge(currentDate, F_swmm5.Event[NextEvent].start)) {
            return 0;
        }
        return 1;
    }

    // routing.c:359
    static void addSystemInflows(double currentDate, double routingStep) {
        int j = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@losses = F_node.node_getLosses(j, routingStep);
        }
        addExternalInflows(currentDate);
        addDryWeatherInflows(currentDate);
        addWetWeatherInflows(F_swmm5.OldRoutingTime);
        addGroundwaterInflows(F_swmm5.OldRoutingTime);
        addLidDrainInflows(F_swmm5.OldRoutingTime);
        addRdiiInflows(currentDate);
        addIfaceInflows(currentDate);
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@qualInflow = (((R.dge((0.0d), (F_swmm5.Node_[j].newLatFlow))) ? (0.0d) : (F_swmm5.Node_[j].newLatFlow)));
        }
    }

    // routing.c:383
    static int isInSteadyState(int actionCount, double stepFlowError) {
        if ((F_swmm5.SkipSteadyState != 0)) {
            if (((((F_swmm5.OldRoutingTime == 0.0d) || (actionCount > 0)) || (R.dgt(Math.abs(stepFlowError), F_swmm5.SysFlowTol.v))) || (inflowHasChanged() != 0))) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

    // routing.c:399
    static int routeFlow(int routingModel, double routingStep) {
        int j = 0;
        int stepCount = 1;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_link.link_setOldHydState(j);
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_node.node_setOldHydState(j);
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_node.node_initFlows(j, routingStep);
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            stepCount = F_flowrout.flowrout_execute(SortedLinks, routingModel, routingStep);
        }
        return stepCount;
    }

    // routing.c:425
    static void removeSystemOutflows(double routingStep) {
        removeStorageLosses(routingStep);
        removeConduitLosses();
        removeOutflows(routingStep);
    }

    // routing.c:435
    static void addExternalInflows(double currentDate) {
        int j = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        TExtInflow inflow = null;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            q = F_swmm5.Node_[j].apiExtInflow;
            inflow = F_swmm5.Node_[j].extInflow;
            L_w2: while ((inflow != null)) {
                if ((inflow.type == FLOW_INFLOW)) {
                    q += (F_inflow.inflow_getExtInflow(inflow, currentDate));
                    break L_w2;
                } else {
                    inflow = inflow.next;
                }
            }
            if ((Math.abs(q) < 1e-05d)) {
                q = 0.0d;
            }
            F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow + q);
            if (R.dge(q, 0.0d)) {
                F_massbal.massbal_addInflowFlow(EXTERNAL_INFLOW, q);
            } else {
                F_massbal.massbal_addOutflowFlow((-q), 0);
                continue L_f1;
            }
            if (((F_swmm5.Node_[j].type == OUTFALL) && (F_swmm5.Node_[j].oldNetInflow < 0.0d))) {
                q = (q - F_swmm5.Node_[j].oldNetInflow);
            }
            inflow = F_swmm5.Node_[j].extInflow;
            L_w3: while ((inflow != null)) {
                if ((inflow.type != FLOW_INFLOW)) {
                    p = inflow.param;
                    w = F_inflow.inflow_getExtInflow(inflow, currentDate);
                    if ((inflow.type == CONCEN_INFLOW)) {
                        w *= q;
                    }
                    F_swmm5.Node_[j].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(EXTERNAL_INFLOW, p, w);
                }
                inflow = inflow.next;
            }
        }
    }

    // routing.c:498
    static void addDryWeatherInflows(double currentDate) {
        int j = 0;
        int p = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        double q = 0.0d;
        double w = 0.0d;
        TDwfInflow inflow = null;
        month = (F_datetime.datetime_monthOfYear(currentDate) - 1);
        day = (F_datetime.datetime_dayOfWeek(currentDate) - 1);
        hour = F_datetime.datetime_hourOfDay(currentDate);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            inflow = F_swmm5.Node_[j].dwfInflow;
            if (!(inflow != null)) {
                continue L_f1;
            }
            q = 0.0d;
            L_w2: while ((inflow != null)) {
                if ((inflow.param < 0)) {
                    q = F_inflow.inflow_getDwfInflow(inflow, month, day, hour);
                    break L_w2;
                }
                inflow = inflow.next;
            }
            if ((Math.abs(q) < 1e-05d)) {
                q = 0.0d;
            }
            F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow + q);
            F_massbal.massbal_addInflowFlow(DRY_WEATHER_INFLOW, q);
            if ((q <= 0.0d)) {
                continue L_f1;
            }
            L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                if (R.dgt(F_swmm5.Pollut[p].dwfConcen, 0.0d)) {
                    w = (q * F_swmm5.Pollut[p].dwfConcen);
                    F_swmm5.Node_[j].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(DRY_WEATHER_INFLOW, p, w);
                }
            }
            inflow = F_swmm5.Node_[j].dwfInflow;
            L_w4: while ((inflow != null)) {
                if ((inflow.param >= 0)) {
                    p = inflow.param;
                    w = (q * F_inflow.inflow_getDwfInflow(inflow, month, day, hour));
                    F_swmm5.Node_[j].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(DRY_WEATHER_INFLOW, p, w);
                    if (R.dgt(F_swmm5.Pollut[p].dwfConcen, 0.0d)) {
                        w = (q * F_swmm5.Pollut[p].dwfConcen);
                        F_swmm5.Node_[j].newQual[p] -= w;
                        F_massbal.massbal_addInflowQual(DRY_WEATHER_INFLOW, p, (-w));
                    }
                }
                inflow = inflow.next;
            }
        }
    }

    // routing.c:579
    static void addWetWeatherInflows(double routingTime) {
        int i = 0;
        int j = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        double f = 0.0d;
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        f = (((routingTime - F_swmm5.OldRunoffTime)) / ((F_swmm5.NewRunoffTime - F_swmm5.OldRunoffTime)));
        if ((f < 0.0d)) {
            f = 0.0d;
        }
        if (R.dgt(f, 1.0d)) {
            f = 1.0d;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
            j = F_swmm5.Subcatch[i].outNode;
            if ((j >= 0)) {
                q = F_subcatch.subcatch_getWtdOutflow(i, f);
                F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow + q);
                F_massbal.massbal_addInflowFlow(WET_WEATHER_INFLOW, q);
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    w = F_surfqual.surfqual_getWtdWashoff(i, p, f);
                    F_swmm5.Node_[j].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(WET_WEATHER_INFLOW, p, w);
                }
            }
        }
    }

    // routing.c:621
    static void addGroundwaterInflows(double routingTime) {
        int i = 0;
        int j = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        double f = 0.0d;
        TGroundwater gw = null;
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        f = (((routingTime - F_swmm5.OldRunoffTime)) / ((F_swmm5.NewRunoffTime - F_swmm5.OldRunoffTime)));
        if ((f < 0.0d)) {
            f = 0.0d;
        }
        if (R.dgt(f, 1.0d)) {
            f = 1.0d;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
            gw = F_swmm5.Subcatch[i].groundwater;
            if ((gw != null)) {
                j = gw.node;
                if ((j >= 0)) {
                    q = ((((((1.0d - f)) * (gw.oldFlow)) + (f * (gw.newFlow)))) * F_swmm5.Subcatch[i].area);
                    if ((Math.abs(q) < 1e-05d)) {
                        continue L_f1;
                    }
                    F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow + q);
                    F_massbal.massbal_addInflowFlow(GROUNDWATER_INFLOW, q);
                    if (R.dgt(q, 0.0d)) {
                        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                            w = (q * F_swmm5.Pollut[p].gwConcen);
                            F_swmm5.Node_[j].newQual[p] += w;
                            F_massbal.massbal_addInflowQual(GROUNDWATER_INFLOW, p, w);
                        }
                    }
                }
            }
        }
    }

    // routing.c:674
    static void addLidDrainInflows(double routingTime) {
        int j = 0;
        double f = 0.0d;
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        f = (((routingTime - F_swmm5.OldRunoffTime)) / ((F_swmm5.NewRunoffTime - F_swmm5.OldRunoffTime)));
        if ((f < 0.0d)) {
            f = 0.0d;
        }
        if (R.dgt(f, 1.0d)) {
            f = 1.0d;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if (((R.dgt(F_swmm5.Subcatch[j].area, 0.0d)) && (R.dgt(F_swmm5.Subcatch[j].lidArea, 0.0d)))) {
                F_lid.lid_addDrainInflow(j, f);
            }
        }
    }

    // routing.c:698
    static void addRdiiInflows(double currentDate) {
        int i = 0;
        IBox j = new IBox(0);
        int p = 0;
        DBox q = new DBox(0.0d);
        double w = 0.0d;
        int numRdiiNodes = 0;
        numRdiiNodes = F_rdii.rdii_getNumRdiiFlows(currentDate);
        L_f1: for (i = 0; (i < numRdiiNodes); i++) {
            F_rdii.rdii_getRdiiFlow(i, j, q);
            if ((j.v < 0)) {
                continue L_f1;
            }
            if ((Math.abs(q.v) < 1e-05d)) {
                continue L_f1;
            }
            F_swmm5.Node_[j.v].@newLatFlow = (F_swmm5.Node_[j.v].newLatFlow + q.v);
            F_massbal.massbal_addInflowFlow(RDII_INFLOW, q.v);
            if (R.dgt(q.v, 0.0d)) {
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    w = (q.v * F_swmm5.Pollut[p].rdiiConcen);
                    F_swmm5.Node_[j.v].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(RDII_INFLOW, p, w);
                }
            }
        }
    }

    // routing.c:736
    static void addIfaceInflows(double currentDate) {
        int i = 0;
        int j = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        int numIfaceNodes = 0;
        if ((F_swmm5.Finflows.mode != USE_FILE)) {
            return;
        }
        numIfaceNodes = F_iface.iface_getNumIfaceNodes(currentDate);
        L_f1: for (i = 0; (i < numIfaceNodes); i++) {
            j = F_iface.iface_getIfaceNode(i);
            if ((j < 0)) {
                continue L_f1;
            }
            q = F_iface.iface_getIfaceFlow(i);
            if ((Math.abs(q) < 1e-05d)) {
                continue L_f1;
            }
            F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow + q);
            F_massbal.massbal_addInflowFlow(EXTERNAL_INFLOW, q);
            if (R.dgt(q, 0.0d)) {
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    w = (q * F_iface.iface_getIfaceQual(i, p));
                    F_swmm5.Node_[j].newQual[p] += w;
                    F_massbal.massbal_addInflowQual(EXTERNAL_INFLOW, p, w);
                }
            }
        }
    }

    // routing.c:776
    static int inflowHasChanged() {
        int j = 0;
        double diff = 0.0d;
        double qOld = 0.0d;
        double qNew = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            qOld = F_swmm5.Node_[j].oldLatFlow;
            qNew = F_swmm5.Node_[j].newLatFlow;
            if (R.dgt(Math.abs(qOld), 1e-06d)) {
                diff = (((qNew / qOld)) - 1.0d);
            } else if (R.dgt(Math.abs(qNew), 1e-06d)) {
                diff = 1.0d;
            } else {
                diff = 0.0d;
            }
            if (R.dgt(Math.abs(diff), F_swmm5.LatFlowTol.v)) {
                return 1;
            }
            if (((F_swmm5.Node_[j].type == OUTFALL) || (F_swmm5.Node_[j].degree == 0))) {
                qOld = F_swmm5.Node_[j].oldFlowInflow;
                qNew = F_swmm5.Node_[j].inflow;
                if (R.dgt(Math.abs(qOld), 1e-06d)) {
                    diff = (((qNew / qOld)) - 1.0d);
                } else if (R.dgt(Math.abs(qNew), 1e-06d)) {
                    diff = 1.0d;
                } else {
                    diff = 0.0d;
                }
                if (R.dgt(Math.abs(diff), F_swmm5.LatFlowTol.v)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    // routing.c:812
    static void removeStorageLosses(double tStep) {
        int i = 0;
        double evapLoss = 0.0d;
        double exfilLoss = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type == STORAGE)) {
                evapLoss += F_swmm5.Storage[F_swmm5.Node_[i].subIndex].evapLoss;
                exfilLoss += F_swmm5.Storage[F_swmm5.Node_[i].subIndex].exfilLoss;
            }
        }
        F_massbal.massbal_addNodeLosses((evapLoss / tStep), (exfilLoss / tStep));
    }

    // routing.c:841
    static void removeConduitLosses() {
        int i = 0;
        int k = 0;
        double barrels = 0.0d;
        double evapLoss = 0.0d;
        double seepLoss = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if ((F_swmm5.Link[i].type == CONDUIT)) {
                k = F_swmm5.Link[i].subIndex;
                barrels = F_swmm5.Conduit[k].barrels;
                evapLoss += (F_swmm5.Conduit[k].evapLossRate * barrels);
                seepLoss += (F_swmm5.Conduit[k].seepLossRate * barrels);
            }
        }
        F_massbal.massbal_addLinkLosses(evapLoss, seepLoss);
    }

    // routing.c:872
    static void removeOutflows(double tStep) {
        int i = 0;
        int p = 0;
        int k = 0;
        IBox isFlooded = new IBox(0);
        double q = 0.0d;
        double w = 0.0d;
        double v = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if (((F_swmm5.Node_[i].type == OUTFALL) && (R.dgt(F_swmm5.Node_[i].inflow, 0.0d)))) {
                k = F_swmm5.Node_[i].subIndex;
                if ((F_swmm5.Outfall[k].routeTo >= 0)) {
                    v = (F_swmm5.Node_[i].inflow * tStep);
                    F_swmm5.Outfall[k].@vRouted = (F_swmm5.Outfall[k].vRouted + v);
                    L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                        F_swmm5.Outfall[k].wRouted[p] += (F_swmm5.Node_[i].newQual[p] * v);
                    }
                }
            }
            q = F_node.node_getSystemOutflow(i, isFlooded);
            if (R.dgt(q, 0.0d)) {
                F_massbal.massbal_addOutflowFlow(q, isFlooded.v);
                L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    w = (q * F_swmm5.Node_[i].newQual[p]);
                    F_massbal.massbal_addOutflowQual(p, w, isFlooded.v);
                }
            } else {
                F_massbal.massbal_addInflowFlow(EXTERNAL_INFLOW, (-q));
            }
            q = F_swmm5.Node_[i].newLatFlow;
            if ((q < 0.0d)) {
                L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    w = ((-q) * F_swmm5.Node_[i].newQual[p]);
                    F_massbal.massbal_addOutflowQual(p, w, 0);
                }
            }
        }
    }

    // routing.c:929
    static void sortEvents() {
        int i = 0;
        int j = 0;
        int gap = 0;
        TEvent temp = new TEvent();
        L_f1: for (gap = R.idiv(F_swmm5.NumEvents, 2); (gap >= 1); gap = R.idiv(gap, 2)) {
            L_f2: for (i = gap; (i < F_swmm5.NumEvents); i += gap) {
                temp = F_swmm5.Event[i].copy();
                j = (i - gap);
                L_w3: while (((j >= 0) && (R.dgt(F_swmm5.Event[j].start, temp.start)))) {
                    F_swmm5.Event[(j + gap)] = F_swmm5.Event[j].copy();
                    j -= gap;
                }
                if ((j != (i - gap))) {
                    F_swmm5.Event[(j + gap)] = temp.copy();
                }
            }
        }
        L_f4: for (i = 0; (i < (F_swmm5.NumEvents - 1)); i++) {
            if (R.dgt(F_swmm5.Event[i].end, (F_swmm5.Event[(i + 1)]).start)) {
                F_swmm5.Event[i].@end = (F_swmm5.Event[(i + 1)]).start;
            }
        }
    }

}
