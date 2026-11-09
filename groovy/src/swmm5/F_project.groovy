// Generated from EPA SWMM 5.2.4 (project.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_project {
    public static HTentry[][] Htable = new HTentry[18][];
    public static int MemPoolAllocated = 0;

    // project.c:70
    static int omp_get_max_threads() {
        return 1;
    }

    // project.c:113
    static void project_open(CPtr f1, CPtr f2, CPtr f3) {
        initPointers();
        setDefaults();
        openFiles(f1, f2, f3);
    }

    // project.c:129
    static void project_readInput() {
        createHashTables();
        F_input.input_countObjects();
        createObjects();
        F_input.input_readData();
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_swmm5.@StartDateTime = (F_swmm5.StartDate.v + F_swmm5.StartTime.v);
        F_swmm5.@EndDateTime = (F_swmm5.EndDate.v + F_swmm5.EndTime.v);
        F_swmm5.@ReportStart = (F_swmm5.ReportStartDate.v + F_swmm5.ReportStartTime.v);
        F_swmm5.@ReportStart = (((R.dge((F_swmm5.ReportStart), (F_swmm5.StartDateTime))) ? (F_swmm5.ReportStart) : (F_swmm5.StartDateTime)));
        if ((F_swmm5.EndDateTime <= F_swmm5.StartDateTime)) {
            F_report.report_writeErrorMsg(ERR_START_DATE, L0);
        } else if ((F_swmm5.EndDateTime <= F_swmm5.ReportStart)) {
            F_report.report_writeErrorMsg(ERR_REPORT_DATE, L0);
        } else {
            F_swmm5.@TotalDuration = Math.floor((((F_swmm5.EndDateTime - F_swmm5.StartDateTime)) * 86400));
            if (R.dgt(((double) (F_swmm5.ReportStep)), F_swmm5.TotalDuration)) {
                F_swmm5.@ReportStep = R.D2I((F_swmm5.TotalDuration));
            }
            if ((((double) (F_swmm5.ReportStep)) < F_swmm5.RouteStep)) {
                F_report.report_writeErrorMsg(ERR_REPORT_STEP, L0);
            }
            F_swmm5.@TotalDuration = (F_swmm5.TotalDuration * 1000.0d);
        }
    }

    // project.c:186
    static void project_validate() {
        int i = 0;
        int j = 0;
        int err = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[CURVE]); i++) {
            err = F_table.table_validate(F_swmm5.Curve[i]);
            if ((err != 0)) {
                F_report.report_writeErrorMsg(ERR_CURVE_SEQUENCE, F_swmm5.Curve[i].ID);
            }
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[TSERIES]); i++) {
            err = F_table.table_validate(F_swmm5.Tseries[i]);
            if ((err != 0)) {
                F_report.report_writeTseriesErrorMsg(err, F_swmm5.Tseries[i]);
            }
        }
        F_climate.climate_validate();
        F_lid.lid_validate();
        if ((F_swmm5.Nobjects[SNOWMELT] == 0)) {
            F_swmm5.@IgnoreSnowmelt = 1;
        }
        if ((F_swmm5.Nobjects[AQUIFER] == 0)) {
            F_swmm5.@IgnoreGwater = 1;
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[AQUIFER]); i++) {
            F_gwater.gwater_validateAquifer(i);
        }
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
            F_subcatch.subcatch_validate(i);
        }
        L_f5: for (i = 0; (i < F_swmm5.Nobjects[GAGE]); i++) {
            F_gage.gage_validate(i);
        }
        L_f6: for (i = 0; (i < F_swmm5.Nobjects[SNOWMELT]); i++) {
            F_snow.snow_validateSnowmelt(i);
        }
        j = 0;
        L_f7: for (i = 0; (i < F_swmm5.Nobjects[CURVE]); i++) {
            if ((F_swmm5.Curve[i].curveType == SHAPE_CURVE)) {
                F_swmm5.Curve[i].@refersTo = j;
                F_swmm5.Shape[j].@curve = i;
                if (!(F_shape.shape_validate(F_swmm5.Shape[j], F_swmm5.Curve[i]) != 0)) {
                    F_report.report_writeErrorMsg(ERR_CURVE_SEQUENCE, F_swmm5.Curve[i].ID);
                }
                j++;
            }
        }
        L_f8: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_swmm5.Node_[i].@oldDepth = F_swmm5.Node_[i].fullDepth;
        }
        L_f9: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            F_link.link_validate(i);
        }
        L_f10: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_node.node_validate(i);
        }
        if ((F_swmm5.DryStep < F_swmm5.WetStep)) {
            F_report.report_writeWarningMsg(L319, L0);
            F_swmm5.@DryStep = F_swmm5.WetStep;
        }
        if (R.dgt(F_swmm5.RouteStep, ((double) (F_swmm5.WetStep)))) {
            F_report.report_writeWarningMsg(L320, L0);
            F_swmm5.@RouteStep = F_swmm5.WetStep;
        }
        if ((F_swmm5.RptFlags.subcatchments == ALL)) {
            L_f11: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
                F_swmm5.Subcatch[i].@rptFlag = 1;
            }
        }
        if ((F_swmm5.RptFlags.nodes == ALL)) {
            L_f12: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                F_swmm5.Node_[i].@rptFlag = 1;
            }
        }
        if ((F_swmm5.RptFlags.links == ALL)) {
            L_f13: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                F_swmm5.Link[i].@rptFlag = 1;
            }
        }
        if ((F_swmm5.RouteModel == DW)) {
            F_dynwave.dynwave_validate();
        }
        F_inlet.inlet_validate();
        if ((F_swmm5.NumThreads == 0)) {
            F_swmm5.@NumThreads = omp_get_max_threads();
        } else {
            F_swmm5.@NumThreads = (((((F_swmm5.NumThreads) <= (omp_get_max_threads()))) ? (F_swmm5.NumThreads) : (omp_get_max_threads())));
        }
        if ((F_swmm5.Nobjects[LINK] < (4 * F_swmm5.NumThreads))) {
            F_swmm5.@NumThreads = 1;
        }
    }

    // project.c:274
    static void project_close() {
        deleteObjects();
        deleteHashTables();
    }

    // project.c:287
    static int project_init() {
        int j = 0;
        int k = 0;
        F_climate.climate_initState();
        F_lid.lid_initState();
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[TSERIES]); j++) {
            F_table.table_tseriesInit(F_swmm5.Tseries[j]);
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            F_gage.gage_initState(j);
        }
        k = 1;
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_subcatch.subcatch_initState(j);
            if ((F_swmm5.Subcatch[j].rptFlag > 0)) {
                F_swmm5.Subcatch[j].@rptFlag = k;
                k++;
            }
        }
        k = 1;
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_node.node_initState(j);
            if ((F_swmm5.Node_[j].rptFlag > 0)) {
                F_swmm5.Node_[j].@rptFlag = k;
                k++;
            }
        }
        k = 1;
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_link.link_initState(j);
            if ((F_swmm5.Link[j].rptFlag > 0)) {
                F_swmm5.Link[j].@rptFlag = k;
                k++;
            }
        }
        return F_swmm5.ErrorCode;
    }

    // project.c:334
    static int project_addObject(int type, CPtr id, int n) {
        int result = 0;
        long len = 0L;
        CPtr newID = null;
        if ((project_findObject(type, id) >= 0)) {
            return 0;
        }
        len = R.strlen(id);
        newID = Overrides.Alloc((((len + 1)) * 1L));
        Overrides.sstrncpy(newID, id, len);
        result = F_hash.HTinsert(Htable[type], newID, n);
        if ((result == 0)) {
            result = (-1);
        }
        return result;
    }

    // project.c:364
    static int project_findObject(int type, CPtr id) {
        return F_hash.HTfind(Htable[type], id);
    }

    // project.c:377
    static CPtr project_findID(int type, CPtr id) {
        return F_hash.HTfindKey(Htable[type], id);
    }

    // project.c:445
    static int project_readOption(CPtr s1, CPtr s2) {
        int k = 0;
        IBox m = new IBox(0);
        IBox h = new IBox(0);
        IBox s = new IBox(0);
        DBox tStep = new DBox(0.0d);
        CPtr strDate = R.buf(25);
        DBox aTime = new DBox(0.0d);
        DBox aDate = new DBox(0.0d);
        k = F_input.findmatch(s1, F_keywords.OptionWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, s1);
        }
        int __sw1 = k;
        if (__sw1 == FLOW_UNITS) {
            m.@v = F_input.findmatch(s2, F_keywords.FlowUnitWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@FlowUnits = m.v;
            if ((F_swmm5.FlowUnits <= MGD)) {
                F_swmm5.@UnitSystem = US;
            } else {
                F_swmm5.@UnitSystem = SI;
            }
        } else if (__sw1 == INFIL_MODEL) {
            m.@v = F_input.findmatch(s2, F_keywords.InfilModelWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@InfilModel = m.v;
        } else if (__sw1 == ROUTE_MODEL) {
            m.@v = F_input.findmatch(s2, F_keywords.RouteModelWords);
            if ((m.v < 0)) {
                m.@v = F_input.findmatch(s2, F_keywords.OldRouteModelWords);
            }
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            if ((m.v == NO_ROUTING)) {
                F_swmm5.@IgnoreRouting = 1;
            } else {
                F_swmm5.@RouteModel = m.v;
            }
            if ((F_swmm5.RouteModel == EKW)) {
                F_swmm5.@RouteModel = KW;
            }
        } else if (__sw1 == START_DATE) {
            if (!(F_datetime.datetime_strToDate(s2, F_swmm5.StartDate) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == START_TIME) {
            if (!(F_datetime.datetime_strToTime(s2, F_swmm5.StartTime) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == END_DATE) {
            if (!(F_datetime.datetime_strToDate(s2, F_swmm5.EndDate) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == END_TIME) {
            if (!(F_datetime.datetime_strToTime(s2, F_swmm5.EndTime) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == REPORT_START_DATE) {
            if (!(F_datetime.datetime_strToDate(s2, F_swmm5.ReportStartDate) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == REPORT_START_TIME) {
            if (!(F_datetime.datetime_strToTime(s2, F_swmm5.ReportStartTime) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
        } else if (__sw1 == SWEEP_START || __sw1 == SWEEP_END) {
            Overrides.sstrncpy(strDate, s2, 24);
            Overrides.sstrcat(strDate, L35, 25);
            if (!(F_datetime.datetime_strToDate(strDate, aDate) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
            m.@v = F_datetime.datetime_dayOfYear(aDate.v);
            if ((k == SWEEP_START)) {
                F_swmm5.@SweepStart = m.v;
            } else {
                F_swmm5.@SweepEnd = m.v;
            }
        } else if (__sw1 == START_DRY_DAYS) {
            F_swmm5.@StartDryDays = R.atof(s2);
            if ((F_swmm5.StartDryDays < 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
        } else if (__sw1 == WET_STEP || __sw1 == DRY_STEP || __sw1 == REPORT_STEP || __sw1 == RULE_STEP) {
            if (!(F_datetime.datetime_strToTime(s2, aTime) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, s2);
            }
            F_datetime.datetime_decodeTime(aTime.v, h, m, s);
            h.@v = (h.v + (24 * R.D2I(aTime.v)));
            s.@v = ((s.v + (60 * m.v)) + (3600 * h.v));
            if ((k == RULE_STEP)) {
                if ((s.v < 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, s2);
                }
            } else if ((s.v <= 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            int __sw2 = k;
            if (__sw2 == WET_STEP) {
                F_swmm5.@WetStep = s.v;
            } else if (__sw2 == DRY_STEP) {
                F_swmm5.@DryStep = s.v;
            } else if (__sw2 == REPORT_STEP) {
                F_swmm5.@ReportStep = s.v;
            } else if (__sw2 == RULE_STEP) {
                F_swmm5.@RuleStep = s.v;
            }
        } else if (__sw1 == INERT_DAMPING) {
            m.@v = F_input.findmatch(s2, F_keywords.InertDampingWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            } else {
                F_swmm5.@InertDamping = m.v;
            }
        } else if (__sw1 == ALLOW_PONDING || __sw1 == SLOPE_WEIGHTING || __sw1 == SKIP_STEADY_STATE || __sw1 == IGNORE_RAINFALL || __sw1 == IGNORE_SNOWMELT || __sw1 == IGNORE_GWATER || __sw1 == IGNORE_ROUTING || __sw1 == IGNORE_QUALITY || __sw1 == IGNORE_RDII) {
            m.@v = F_input.findmatch(s2, F_keywords.NoYesWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            int __sw3 = k;
            if (__sw3 == ALLOW_PONDING) {
                F_swmm5.@AllowPonding = m.v;
            } else if (__sw3 == SLOPE_WEIGHTING) {
                F_swmm5.@SlopeWeighting = m.v;
            } else if (__sw3 == SKIP_STEADY_STATE) {
                F_swmm5.@SkipSteadyState = m.v;
            } else if (__sw3 == IGNORE_RAINFALL) {
                F_swmm5.@IgnoreRainfall = m.v;
            } else if (__sw3 == IGNORE_SNOWMELT) {
                F_swmm5.@IgnoreSnowmelt = m.v;
            } else if (__sw3 == IGNORE_GWATER) {
                F_swmm5.@IgnoreGwater = m.v;
            } else if (__sw3 == IGNORE_ROUTING) {
                F_swmm5.@IgnoreRouting = m.v;
            } else if (__sw3 == IGNORE_QUALITY) {
                F_swmm5.@IgnoreQuality = m.v;
            } else if (__sw3 == IGNORE_RDII) {
                F_swmm5.@IgnoreRDII = m.v;
            }
        } else if (__sw1 == NORMAL_FLOW_LTD) {
            m.@v = F_input.findmatch(s2, F_keywords.NormalFlowWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@NormalFlowLtd = m.v;
        } else if (__sw1 == FORCE_MAIN_EQN) {
            m.@v = F_input.findmatch(s2, F_keywords.ForceMainEqnWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@ForceMainEqn = m.v;
        } else if (__sw1 == LINK_OFFSETS) {
            m.@v = F_input.findmatch(s2, F_keywords.LinkOffsetWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@LinkOffsets = m.v;
        } else if (__sw1 == COMPATIBILITY) {
            if ((F_swmm5.strcomp(s2, L321) != 0)) {
                F_swmm5.@Compatibility = SWMM3;
            } else if ((F_swmm5.strcomp(s2, L322) != 0)) {
                F_swmm5.@Compatibility = SWMM4;
            } else if ((F_swmm5.strcomp(s2, L323) != 0)) {
                F_swmm5.@Compatibility = SWMM5;
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
        } else if (__sw1 == ROUTE_STEP || __sw1 == LENGTHENING_STEP) {
            if (!(F_input.getDouble(s2, tStep) != 0)) {
                if (!(F_datetime.datetime_strToTime(s2, aTime) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, s2);
                } else {
                    F_datetime.datetime_decodeTime(aTime.v, h, m, s);
                    h.@v = (h.v + (24 * R.D2I(aTime.v)));
                    s.@v = ((s.v + (60 * m.v)) + (3600 * h.v));
                    tStep.@v = s.v;
                }
            }
            if ((k == ROUTE_STEP)) {
                if ((tStep.v <= 0.0d)) {
                    return F_error.error_setInpError(ERR_NUMBER, s2);
                }
                F_swmm5.@RouteStep = tStep.v;
            } else {
                F_swmm5.@LengtheningStep = (((R.dge((0.0d), (tStep.v))) ? (0.0d) : (tStep.v)));
            }
        } else if (__sw1 == MIN_ROUTE_STEP) {
            if (((!(F_input.getDouble(s2, F_swmm5.MinRouteStep) != 0)) || (F_swmm5.MinRouteStep.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
        } else if (__sw1 == NUM_THREADS) {
            m.@v = R.atoi(s2);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            F_swmm5.@NumThreads = m.v;
        } else if (__sw1 == VARIABLE_STEP) {
            if (!(F_input.getDouble(s2, F_swmm5.CourantFactor) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            if (((F_swmm5.CourantFactor.v < 0.0d) || (R.dgt(F_swmm5.CourantFactor.v, 2.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
        } else if (__sw1 == MIN_SURFAREA) {
            if (!(F_input.getDouble(s2, F_swmm5.MinSurfArea) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            if ((F_swmm5.MinSurfArea.v < 0.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
        } else if (__sw1 == MIN_SLOPE) {
            if (!(F_input.getDouble(s2, F_swmm5.MinSlope) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            if (((F_swmm5.MinSlope.v < 0.0d) || (R.dge(F_swmm5.MinSlope.v, 100)))) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            F_swmm5.MinSlope.@v = (F_swmm5.MinSlope.v / 100.0d);
        } else if (__sw1 == MAX_TRIALS) {
            m.@v = R.atoi(s2);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            F_swmm5.@MaxTrials = m.v;
        } else if (__sw1 == HEAD_TOL) {
            if (!(F_input.getDouble(s2, F_swmm5.HeadTol) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
        } else if (__sw1 == SYS_FLOW_TOL) {
            if (!(F_input.getDouble(s2, F_swmm5.SysFlowTol) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            F_swmm5.SysFlowTol.@v = (F_swmm5.SysFlowTol.v / 100.0d);
        } else if (__sw1 == LAT_FLOW_TOL) {
            if (!(F_input.getDouble(s2, F_swmm5.LatFlowTol) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, s2);
            }
            F_swmm5.LatFlowTol.@v = (F_swmm5.LatFlowTol.v / 100.0d);
        } else if (__sw1 == SURCHARGE_METHOD) {
            m.@v = F_input.findmatch(s2, F_keywords.SurchargeWords);
            if ((m.v < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, s2);
            }
            F_swmm5.@SurchargeMethod = m.v;
        } else if (__sw1 == TEMPDIR) {
            Overrides.sstrncpy(F_swmm5.TempDir, s2, 259);
        }
        return 0;
    }

    // project.c:773
    static void initPointers() {
        F_swmm5.@Gage = (null);
        F_swmm5.@Subcatch = (null);
        F_swmm5.@Node_ = (null);
        F_swmm5.@Outfall = (null);
        F_swmm5.@Divider = (null);
        F_swmm5.@Storage = (null);
        F_swmm5.@Link = (null);
        F_swmm5.@Conduit = (null);
        F_swmm5.@Pump = (null);
        F_swmm5.@Orifice = (null);
        F_swmm5.@Weir = (null);
        F_swmm5.@Outlet = (null);
        F_swmm5.@Pollut = (null);
        F_swmm5.@Landuse = (null);
        F_swmm5.@Pattern = (null);
        F_swmm5.@Curve = (null);
        F_swmm5.@Tseries = (null);
        F_swmm5.@Transect = (null);
        F_swmm5.@Shape = (null);
        F_swmm5.@Aquifer = (null);
        F_swmm5.@UnitHyd = (null);
        F_swmm5.@Snowmelt = (null);
        F_swmm5.@Event = (null);
        MemPoolAllocated = 0;
    }

    // project.c:808
    static void setDefaults() {
        int i = 0;
        int j = 0;
        L_f1: for (i = 0; (i < 3); i++) {
            Overrides.sstrncpy(F_swmm5.Title[i], L0, 0);
        }
        Overrides.sstrncpy(F_swmm5.TempDir, L0, 0);
        F_swmm5.Frain.@mode = SCRATCH_FILE;
        F_swmm5.Fclimate.@mode = NO_FILE;
        F_swmm5.Frunoff.@mode = NO_FILE;
        F_swmm5.Frdii.@mode = NO_FILE;
        F_swmm5.Fhotstart1.@mode = NO_FILE;
        F_swmm5.Fhotstart2.@mode = NO_FILE;
        F_swmm5.Finflows.@mode = NO_FILE;
        F_swmm5.Foutflows.@mode = NO_FILE;
        F_swmm5.Frain.@file = (null);
        F_swmm5.Fclimate.@file = (null);
        F_swmm5.Frunoff.@file = (null);
        F_swmm5.Frdii.@file = (null);
        F_swmm5.Fhotstart1.@file = (null);
        F_swmm5.Fhotstart2.@file = (null);
        F_swmm5.Finflows.@file = (null);
        F_swmm5.Foutflows.@file = (null);
        F_swmm5.Fout.@file = (null);
        F_swmm5.Fout.@mode = NO_FILE;
        F_swmm5.@UnitSystem = US;
        F_swmm5.@FlowUnits = CFS;
        F_swmm5.@InfilModel = HORTON;
        F_swmm5.@RouteModel = DW;
        F_swmm5.@SurchargeMethod = EXTRAN;
        F_swmm5.@CrownCutoff = 0.96d;
        F_swmm5.@AllowPonding = 0;
        F_swmm5.@InertDamping = PARTIAL_DAMPING;
        F_swmm5.@NormalFlowLtd = BOTH;
        F_swmm5.@ForceMainEqn = H_W;
        F_swmm5.@LinkOffsets = DEPTH_OFFSET;
        F_swmm5.@LengtheningStep = 0;
        F_swmm5.CourantFactor.@v = 0.75d;
        F_swmm5.MinSurfArea.@v = 0.0d;
        F_swmm5.MinSlope.@v = 0.0d;
        F_swmm5.@SkipSteadyState = 0;
        F_swmm5.@IgnoreRainfall = 0;
        F_swmm5.@IgnoreRDII = 0;
        F_swmm5.@IgnoreSnowmelt = 0;
        F_swmm5.@IgnoreGwater = 0;
        F_swmm5.@IgnoreRouting = 0;
        F_swmm5.@IgnoreQuality = 0;
        F_swmm5.@WetStep = 300;
        F_swmm5.@DryStep = 3600;
        F_swmm5.@RuleStep = 0;
        F_swmm5.@RouteStep = 20;
        F_swmm5.MinRouteStep.@v = 0.5d;
        F_swmm5.@ReportStep = 900;
        F_swmm5.@StartDryDays = 0.0d;
        F_swmm5.@MaxTrials = 0;
        F_swmm5.HeadTol.@v = 0.0d;
        F_swmm5.SysFlowTol.@v = 0.05d;
        F_swmm5.LatFlowTol.@v = 0.05d;
        F_swmm5.@NumThreads = 1;
        F_swmm5.@NumEvents = 0;
        F_swmm5.@SlopeWeighting = 1;
        F_swmm5.@Compatibility = SWMM4;
        F_swmm5.StartDate.@v = F_datetime.datetime_encodeDate(2004, 1, 1);
        F_swmm5.StartTime.@v = F_datetime.datetime_encodeTime(0, 0, 0);
        F_swmm5.@StartDateTime = (F_swmm5.StartDate.v + F_swmm5.StartTime.v);
        F_swmm5.EndDate.@v = F_swmm5.StartDate.v;
        F_swmm5.EndTime.@v = 0.0d;
        F_swmm5.ReportStartDate.@v = (-693594);
        F_swmm5.ReportStartTime.@v = (-693594);
        F_swmm5.@SweepStart = 1;
        F_swmm5.@SweepEnd = 365;
        F_swmm5.RptFlags.@disabled = 0;
        F_swmm5.RptFlags.@input = 0;
        F_swmm5.RptFlags.@continuity = 1;
        F_swmm5.RptFlags.@flowStats = 1;
        F_swmm5.RptFlags.@controls = 0;
        F_swmm5.RptFlags.@subcatchments = 0;
        F_swmm5.RptFlags.@nodes = 0;
        F_swmm5.RptFlags.@links = 0;
        F_swmm5.RptFlags.@averages = 0;
        F_swmm5.Temp.@dataSource = NO_TEMP;
        F_swmm5.Temp.@tSeries = (-1);
        F_swmm5.Temp.@ta = 70.0d;
        F_swmm5.Temp.@elev = 0.0d;
        F_swmm5.Temp.@anglat = 40.0d;
        F_swmm5.Temp.@dtlong = 0.0d;
        F_swmm5.Temp.@tmax = (-10000000000.0d);
        F_swmm5.Wind.@type = MONTHLY_WIND;
        L_f2: for (i = 0; (i < 12); i++) {
            F_swmm5.Wind.aws[i] = 0.0d;
        }
        F_swmm5.Snow.@snotmp = 34.0d;
        F_swmm5.Snow.@tipm = 0.5d;
        F_swmm5.Snow.@rnm = 0.6d;
        L_f3: for (i = 0; (i < 2); i++) {
            L_f4: for (j = 0; (j < 10); j++) {
                F_swmm5.Snow.adc[i][j] = 1.0d;
            }
        }
        F_swmm5.Evap.@type = CONSTANT_EVAP;
        L_f5: for (i = 0; (i < 12); i++) {
            F_swmm5.Evap.monthlyEvap[i] = 0.0d;
            F_swmm5.Evap.panCoeff[i] = 1.0d;
        }
        F_swmm5.Evap.@recoveryPattern = (-1);
        F_swmm5.Evap.@recoveryFactor = 1.0d;
        F_swmm5.Evap.@tSeries = (-1);
        F_swmm5.Evap.@dryOnly = 0;
        L_f6: for (i = 0; (i < 12); i++) {
            F_swmm5.Adjust.temp[i] = 0.0d;
            F_swmm5.Adjust.evap[i] = 0.0d;
            F_swmm5.Adjust.rain[i] = 1.0d;
            F_swmm5.Adjust.hydcon[i] = 1.0d;
        }
        F_swmm5.Adjust.@rainFactor = 1.0d;
        F_swmm5.Adjust.@hydconFactor = 1.0d;
    }

    // project.c:954
    static void openFiles(CPtr f1, CPtr f2, CPtr f3) {
        F_swmm5.Finp.@file = (null);
        F_swmm5.Frpt.@file = (null);
        F_swmm5.Fout.@file = (null);
        Overrides.sstrncpy(F_swmm5.Finp.name, f1, 259);
        Overrides.sstrncpy(F_swmm5.Frpt.name, f2, 259);
        Overrides.sstrncpy(F_swmm5.Fout.name, f3, 259);
        if ((((F_swmm5.strcomp(f1, f2) != 0) || (F_swmm5.strcomp(f1, f3) != 0)) || (F_swmm5.strcomp(f2, f3) != 0))) {
            F_swmm5.writecon(L324);
            F_swmm5.@ErrorCode = ERR_FILE_NAME;
            return;
        }
        if ((((F_swmm5.Finp.@file = R.fopen(f1, L13))) == null)) {
            F_swmm5.writecon(L325);
            F_swmm5.writecon(f1);
            F_swmm5.@ErrorCode = ERR_INP_FILE;
            return;
        }
        if ((((F_swmm5.Frpt.@file = R.fopen(f2, L171))) == null)) {
            F_swmm5.writecon(L326);
            F_swmm5.@ErrorCode = ERR_RPT_FILE;
            return;
        }
    }

    // project.c:999
    static void createObjects() {
        int j = 0;
        int k = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_swmm5.@Gage = R.fill(new TGage[(int) (F_swmm5.Nobjects[GAGE])], (() -> new TGage()));
        F_swmm5.@Subcatch = R.fill(new TSubcatch[(int) (F_swmm5.Nobjects[SUBCATCH])], (() -> new TSubcatch()));
        F_swmm5.@Node_ = R.fill(new TNode[(int) (F_swmm5.Nobjects[NODE])], (() -> new TNode()));
        F_swmm5.@Outfall = R.fill(new TOutfall[(int) (F_swmm5.Nnodes[OUTFALL])], (() -> new TOutfall()));
        F_swmm5.@Divider = R.fill(new TDivider[(int) (F_swmm5.Nnodes[DIVIDER])], (() -> new TDivider()));
        F_swmm5.@Storage = R.fill(new TStorage[(int) (F_swmm5.Nnodes[STORAGE])], (() -> new TStorage()));
        F_swmm5.@Link = R.fill(new TLink[(int) (F_swmm5.Nobjects[LINK])], (() -> new TLink()));
        F_swmm5.@Conduit = R.fill(new TConduit[(int) (F_swmm5.Nlinks[CONDUIT])], (() -> new TConduit()));
        F_swmm5.@Pump = R.fill(new TPump[(int) (F_swmm5.Nlinks[PUMP])], (() -> new TPump()));
        F_swmm5.@Orifice = R.fill(new TOrifice[(int) (F_swmm5.Nlinks[ORIFICE])], (() -> new TOrifice()));
        F_swmm5.@Weir = R.fill(new TWeir[(int) (F_swmm5.Nlinks[WEIR])], (() -> new TWeir()));
        F_swmm5.@Outlet = R.fill(new TOutlet[(int) (F_swmm5.Nlinks[OUTLET])], (() -> new TOutlet()));
        F_swmm5.@Pollut = R.fill(new TPollut[(int) (F_swmm5.Nobjects[POLLUT])], (() -> new TPollut()));
        F_swmm5.@Landuse = R.fill(new TLanduse[(int) (F_swmm5.Nobjects[LANDUSE])], (() -> new TLanduse()));
        F_swmm5.@Pattern = R.fill(new TPattern[(int) (F_swmm5.Nobjects[TIMEPATTERN])], (() -> new TPattern()));
        F_swmm5.@Curve = R.fill(new TTable[(int) (F_swmm5.Nobjects[CURVE])], (() -> new TTable()));
        F_swmm5.@Tseries = R.fill(new TTable[(int) (F_swmm5.Nobjects[TSERIES])], (() -> new TTable()));
        F_swmm5.@Aquifer = R.fill(new TAquifer[(int) (F_swmm5.Nobjects[AQUIFER])], (() -> new TAquifer()));
        F_swmm5.@UnitHyd = R.fill(new TUnitHyd[(int) (F_swmm5.Nobjects[UNITHYD])], (() -> new TUnitHyd()));
        F_swmm5.@Snowmelt = R.fill(new TSnowmelt[(int) (F_swmm5.Nobjects[SNOWMELT])], (() -> new TSnowmelt()));
        F_swmm5.@Shape = R.fill(new TShape[(int) (F_swmm5.Nobjects[SHAPE])], (() -> new TShape()));
        F_swmm5.@Event = R.fill(new TEvent[(int) ((F_swmm5.NumEvents + 1))], (() -> new TEvent()));
        F_swmm5.Event[F_swmm5.NumEvents].@start = 10000000000.0d;
        F_swmm5.Event[F_swmm5.NumEvents].@end = (10000000000.0d + 1.0d);
        F_lid.lid_create(F_swmm5.Nobjects[LID], F_swmm5.Nobjects[SUBCATCH]);
        F_swmm5.@ErrorCode = F_controls.controls_create(F_swmm5.Nobjects[CONTROL]);
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_swmm5.@ErrorCode = F_transect.transect_create(F_swmm5.Nobjects[TRANSECT]);
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_swmm5.@ErrorCode = F_street.street_create(F_swmm5.Nobjects[STREET]);
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_swmm5.@ErrorCode = F_inlet.inlet_create(F_swmm5.Nobjects[INLET]);
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_infil.infil_create(F_swmm5.Nobjects[SUBCATCH]);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_swmm5.Subcatch[j].@initBuildup = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Subcatch[j].@oldQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Subcatch[j].@newQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Subcatch[j].@pondedQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Subcatch[j].@totalLoad = new double[(int) (F_swmm5.Nobjects[POLLUT])];
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@oldQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Node_[j].@newQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Node_[j].@extInflow = (null);
            F_swmm5.Node_[j].@dwfInflow = (null);
            F_swmm5.Node_[j].@rdiiInflow = (null);
            F_swmm5.Node_[j].@treatment = (null);
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_swmm5.Link[j].@inlet = (null);
            F_swmm5.Link[j].@oldQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Link[j].@newQual = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            F_swmm5.Link[j].@totalLoad = new double[(int) (F_swmm5.Nobjects[POLLUT])];
        }
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[LANDUSE]); j++) {
            F_swmm5.Landuse[j].@buildupFunc = R.fill(new TBuildup[(int) (F_swmm5.Nobjects[POLLUT])], (() -> new TBuildup()));
            F_swmm5.Landuse[j].@washoffFunc = R.fill(new TWashoff[(int) (F_swmm5.Nobjects[POLLUT])], (() -> new TWashoff()));
        }
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_swmm5.Subcatch[j].@landFactor = R.fill(new TLandFactor[(int) (F_swmm5.Nobjects[LANDUSE])], (() -> new TLandFactor()));
            L_f6: for (k = 0; (k < F_swmm5.Nobjects[LANDUSE]); k++) {
                F_swmm5.Subcatch[j].landFactor[k].@buildup = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            }
        }
        L_f7: for (j = 0; (j < F_swmm5.Nobjects[LANDUSE]); j++) {
            L_f8: for (k = 0; (k < F_swmm5.Nobjects[POLLUT]); k++) {
                F_swmm5.Landuse[j].buildupFunc[k].@funcType = NO_BUILDUP;
                F_swmm5.Landuse[j].buildupFunc[k].@normalizer = PER_AREA;
                F_swmm5.Landuse[j].washoffFunc[k].@funcType = NO_WASHOFF;
            }
        }
        L_f9: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            F_swmm5.Gage[j].@tSeries = (-1);
            Overrides.sstrncpy(F_swmm5.Gage[j].fname, L0, 0);
        }
        L_f10: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_swmm5.Subcatch[j].@outSubcatch = (-1);
            F_swmm5.Subcatch[j].@outNode = (-1);
            F_swmm5.Subcatch[j].@infil = (-1);
            F_swmm5.Subcatch[j].@groundwater = (null);
            F_swmm5.Subcatch[j].@gwLatFlowExpr = (null);
            F_swmm5.Subcatch[j].@gwDeepFlowExpr = (null);
            F_swmm5.Subcatch[j].@snowpack = (null);
            F_swmm5.Subcatch[j].@lidArea = 0.0d;
            L_f11: for (k = 0; (k < F_swmm5.Nobjects[POLLUT]); k++) {
                F_swmm5.Subcatch[j].initBuildup[k] = 0.0d;
            }
        }
        L_f12: for (j = 0; (j < F_swmm5.Nobjects[UNITHYD]); j++) {
            F_rdii.rdii_initUnitHyd(j);
        }
        L_f13: for (j = 0; (j < F_swmm5.Nobjects[SNOWMELT]); j++) {
            F_snow.snow_initSnowmelt(j);
        }
        L_f14: for (j = 0; (j < F_swmm5.Nnodes[STORAGE]); j++) {
            F_swmm5.Storage[j].@exfil = (null);
        }
        L_f15: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_swmm5.Link[j].xsect.@type = (-1);
            F_swmm5.Link[j].@cLossInlet = 0.0d;
            F_swmm5.Link[j].@cLossOutlet = 0.0d;
            F_swmm5.Link[j].@cLossAvg = 0.0d;
            F_swmm5.Link[j].@hasFlapGate = 0;
        }
        L_f16: for (j = 0; (j < F_swmm5.Nlinks[PUMP]); j++) {
            F_swmm5.Pump[j].@pumpCurve = (-1);
        }
        L_f17: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_swmm5.Subcatch[j].@rptFlag = 0;
        }
        L_f18: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_swmm5.Node_[j].@rptFlag = 0;
        }
        L_f19: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_swmm5.Link[j].@rptFlag = 0;
        }
        L_f20: for (j = 0; (j < F_swmm5.Nobjects[CURVE]); j++) {
            F_table.table_init(F_swmm5.Curve[j]);
        }
        L_f21: for (j = 0; (j < F_swmm5.Nobjects[TSERIES]); j++) {
            F_table.table_init(F_swmm5.Tseries[j]);
        }
        L_f22: for (j = 0; (j < F_swmm5.Nobjects[TIMEPATTERN]); j++) {
            F_inflow.inflow_initDwfPattern(j);
        }
    }

    // project.c:1176
    static void deleteObjects() {
        int j = 0;
        int k = 0;
        if ((F_swmm5.Subcatch != null)) {
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                L_f2: for (k = 0; (k < F_swmm5.Nobjects[LANDUSE]); k++) {
                    if ((F_swmm5.Subcatch[j].landFactor[k].buildup != null)) {
                        R.free(F_swmm5.Subcatch[j].landFactor[k].buildup);
                        F_swmm5.Subcatch[j].landFactor[k].@buildup = (null);
                    }
                }
                if ((F_swmm5.Subcatch[j].landFactor != null)) {
                    R.free(F_swmm5.Subcatch[j].landFactor);
                    F_swmm5.Subcatch[j].@landFactor = (null);
                }
                if ((F_swmm5.Subcatch[j].groundwater != null)) {
                    R.free(F_swmm5.Subcatch[j].groundwater);
                    F_swmm5.Subcatch[j].@groundwater = (null);
                }
                F_gwater.gwater_deleteFlowExpression(j);
                if ((F_swmm5.Subcatch[j].snowpack != null)) {
                    R.free(F_swmm5.Subcatch[j].snowpack);
                    F_swmm5.Subcatch[j].@snowpack = (null);
                }
            }
        }
        if ((F_swmm5.Landuse != null)) {
            L_f3: for (j = 0; (j < F_swmm5.Nobjects[LANDUSE]); j++) {
                if ((F_swmm5.Landuse[j].buildupFunc != null)) {
                    R.free(F_swmm5.Landuse[j].buildupFunc);
                    F_swmm5.Landuse[j].@buildupFunc = (null);
                }
                if ((F_swmm5.Landuse[j].washoffFunc != null)) {
                    R.free(F_swmm5.Landuse[j].washoffFunc);
                    F_swmm5.Landuse[j].@washoffFunc = (null);
                }
            }
        }
        if ((F_swmm5.Subcatch != null)) {
            L_f4: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                if ((F_swmm5.Subcatch[j].initBuildup != null)) {
                    R.free(F_swmm5.Subcatch[j].initBuildup);
                    F_swmm5.Subcatch[j].@initBuildup = (null);
                }
                if ((F_swmm5.Subcatch[j].oldQual != null)) {
                    R.free(F_swmm5.Subcatch[j].oldQual);
                    F_swmm5.Subcatch[j].@oldQual = (null);
                }
                if ((F_swmm5.Subcatch[j].newQual != null)) {
                    R.free(F_swmm5.Subcatch[j].newQual);
                    F_swmm5.Subcatch[j].@newQual = (null);
                }
                if ((F_swmm5.Subcatch[j].pondedQual != null)) {
                    R.free(F_swmm5.Subcatch[j].pondedQual);
                    F_swmm5.Subcatch[j].@pondedQual = (null);
                }
                if ((F_swmm5.Subcatch[j].totalLoad != null)) {
                    R.free(F_swmm5.Subcatch[j].totalLoad);
                    F_swmm5.Subcatch[j].@totalLoad = (null);
                }
            }
        }
        if ((F_swmm5.Node_ != null)) {
            L_f5: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                if ((F_swmm5.Node_[j].oldQual != null)) {
                    R.free(F_swmm5.Node_[j].oldQual);
                    F_swmm5.Node_[j].@oldQual = (null);
                }
                if ((F_swmm5.Node_[j].newQual != null)) {
                    R.free(F_swmm5.Node_[j].newQual);
                    F_swmm5.Node_[j].@newQual = (null);
                }
            }
        }
        if ((F_swmm5.Link != null)) {
            L_f6: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
                if ((F_swmm5.Link[j].oldQual != null)) {
                    R.free(F_swmm5.Link[j].oldQual);
                    F_swmm5.Link[j].@oldQual = (null);
                }
                if ((F_swmm5.Link[j].newQual != null)) {
                    R.free(F_swmm5.Link[j].newQual);
                    F_swmm5.Link[j].@newQual = (null);
                }
                if ((F_swmm5.Link[j].totalLoad != null)) {
                    R.free(F_swmm5.Link[j].totalLoad);
                    F_swmm5.Link[j].@totalLoad = (null);
                }
            }
        }
        F_infil.infil_delete();
        if ((F_swmm5.Node_ != null)) {
            L_f7: for (j = 0; (j < F_swmm5.Nnodes[STORAGE]); j++) {
                if ((F_swmm5.Storage[j].exfil != null)) {
                    if ((F_swmm5.Storage[j].exfil.btmExfil != null)) {
                        R.free(F_swmm5.Storage[j].exfil.btmExfil);
                        F_swmm5.Storage[j].exfil.@btmExfil = (null);
                    }
                    if ((F_swmm5.Storage[j].exfil.bankExfil != null)) {
                        R.free(F_swmm5.Storage[j].exfil.bankExfil);
                        F_swmm5.Storage[j].exfil.@bankExfil = (null);
                    }
                    if ((F_swmm5.Storage[j].exfil != null)) {
                        R.free(F_swmm5.Storage[j].exfil);
                        F_swmm5.Storage[j].@exfil = (null);
                    }
                }
            }
        }
        if ((F_swmm5.Node_ != null)) {
            L_f8: for (j = 0; (j < F_swmm5.Nnodes[OUTFALL]); j++) {
                if ((F_swmm5.Outfall[j].wRouted != null)) {
                    R.free(F_swmm5.Outfall[j].wRouted);
                    F_swmm5.Outfall[j].@wRouted = (null);
                }
            }
        }
        if ((F_swmm5.Node_ != null)) {
            L_f9: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                F_inflow.inflow_deleteExtInflows(j);
                F_inflow.inflow_deleteDwfInflows(j);
                F_rdii.rdii_deleteRdiiInflow(j);
                F_treatmnt.treatmnt_delete(j);
            }
        }
        if ((F_swmm5.Tseries != null)) {
            L_f10: for (j = 0; (j < F_swmm5.Nobjects[TSERIES]); j++) {
                F_table.table_deleteEntries(F_swmm5.Tseries[j]);
            }
        }
        if ((F_swmm5.Curve != null)) {
            L_f11: for (j = 0; (j < F_swmm5.Nobjects[CURVE]); j++) {
                F_table.table_deleteEntries(F_swmm5.Curve[j]);
            }
        }
        F_transect.transect_delete();
        F_street.street_delete();
        F_inlet.inlet_delete();
        F_controls.controls_delete();
        F_lid.lid_delete();
        if ((F_swmm5.Gage != null)) {
            R.free(F_swmm5.Gage);
            F_swmm5.@Gage = (null);
        }
        if ((F_swmm5.Subcatch != null)) {
            R.free(F_swmm5.Subcatch);
            F_swmm5.@Subcatch = (null);
        }
        if ((F_swmm5.Node_ != null)) {
            R.free(F_swmm5.Node_);
            F_swmm5.@Node_ = (null);
        }
        if ((F_swmm5.Outfall != null)) {
            R.free(F_swmm5.Outfall);
            F_swmm5.@Outfall = (null);
        }
        if ((F_swmm5.Divider != null)) {
            R.free(F_swmm5.Divider);
            F_swmm5.@Divider = (null);
        }
        if ((F_swmm5.Storage != null)) {
            R.free(F_swmm5.Storage);
            F_swmm5.@Storage = (null);
        }
        if ((F_swmm5.Link != null)) {
            R.free(F_swmm5.Link);
            F_swmm5.@Link = (null);
        }
        if ((F_swmm5.Conduit != null)) {
            R.free(F_swmm5.Conduit);
            F_swmm5.@Conduit = (null);
        }
        if ((F_swmm5.Pump != null)) {
            R.free(F_swmm5.Pump);
            F_swmm5.@Pump = (null);
        }
        if ((F_swmm5.Orifice != null)) {
            R.free(F_swmm5.Orifice);
            F_swmm5.@Orifice = (null);
        }
        if ((F_swmm5.Weir != null)) {
            R.free(F_swmm5.Weir);
            F_swmm5.@Weir = (null);
        }
        if ((F_swmm5.Outlet != null)) {
            R.free(F_swmm5.Outlet);
            F_swmm5.@Outlet = (null);
        }
        if ((F_swmm5.Pollut != null)) {
            R.free(F_swmm5.Pollut);
            F_swmm5.@Pollut = (null);
        }
        if ((F_swmm5.Landuse != null)) {
            R.free(F_swmm5.Landuse);
            F_swmm5.@Landuse = (null);
        }
        if ((F_swmm5.Pattern != null)) {
            R.free(F_swmm5.Pattern);
            F_swmm5.@Pattern = (null);
        }
        if ((F_swmm5.Curve != null)) {
            R.free(F_swmm5.Curve);
            F_swmm5.@Curve = (null);
        }
        if ((F_swmm5.Tseries != null)) {
            R.free(F_swmm5.Tseries);
            F_swmm5.@Tseries = (null);
        }
        if ((F_swmm5.Aquifer != null)) {
            R.free(F_swmm5.Aquifer);
            F_swmm5.@Aquifer = (null);
        }
        if ((F_swmm5.UnitHyd != null)) {
            R.free(F_swmm5.UnitHyd);
            F_swmm5.@UnitHyd = (null);
        }
        if ((F_swmm5.Snowmelt != null)) {
            R.free(F_swmm5.Snowmelt);
            F_swmm5.@Snowmelt = (null);
        }
        if ((F_swmm5.Shape != null)) {
            R.free(F_swmm5.Shape);
            F_swmm5.@Shape = (null);
        }
        if ((F_swmm5.Event != null)) {
            R.free(F_swmm5.Event);
            F_swmm5.@Event = (null);
        }
    }

    // project.c:1304
    static void createHashTables() {
        int j = 0;
        MemPoolAllocated = 0;
        L_f1: for (j = 0; (j < MAX_OBJ_TYPES); j++) {
            Htable[j] = F_hash.HTcreate();
            if ((Htable[j] == null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            }
        }
        if (((Overrides.AllocInit()) == null)) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
        } else {
            MemPoolAllocated = 1;
        }
    }

    // project.c:1325
    static void deleteHashTables() {
        int j = 0;
        L_f1: for (j = 0; (j < MAX_OBJ_TYPES); j++) {
            if ((Htable[j] != null)) {
                F_hash.HTfree(Htable[j]);
            }
        }
        if ((MemPoolAllocated != 0)) {
            Overrides.AllocFreePool();
        }
    }

}
