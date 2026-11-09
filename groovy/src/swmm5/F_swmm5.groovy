// Generated from EPA SWMM 5.2.4 (swmm5.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_swmm5 {
    public static TFile Finp = new TFile();
    public static TFile Fout = new TFile();
    public static TFile Frpt = new TFile();
    public static TFile Fclimate = new TFile();
    public static TFile Frain = new TFile();
    public static TFile Frunoff = new TFile();
    public static TFile Frdii = new TFile();
    public static TFile Fhotstart1 = new TFile();
    public static TFile Fhotstart2 = new TFile();
    public static TFile Finflows = new TFile();
    public static TFile Foutflows = new TFile();
    public static long Nperiods = 0L;
    public static long TotalStepCount = 0L;
    public static long ReportStepCount = 0L;
    public static long NonConvergeCount = 0L;
    public static CPtr Msg = R.buf(1025);
    public static CPtr ErrorMsg = R.buf(1025);
    public static CPtr[] Title = R.bufs(3, 1025);
    public static CPtr TempDir = R.buf(260);
    public static CPtr InpDir = R.buf(260);
    public static TRptFlags RptFlags = new TRptFlags();
    public static int[] Nobjects = new int[18];
    public static int[] Nnodes = new int[5];
    public static int[] Nlinks = new int[5];
    public static int UnitSystem = 0;
    public static int FlowUnits = 0;
    public static int InfilModel = 0;
    public static int RouteModel = 0;
    public static int ForceMainEqn = 0;
    public static int LinkOffsets = 0;
    public static int SurchargeMethod = 0;
    public static int AllowPonding = 0;
    public static int InertDamping = 0;
    public static int NormalFlowLtd = 0;
    public static int SlopeWeighting = 0;
    public static int Compatibility = 0;
    public static int SkipSteadyState = 0;
    public static int IgnoreRainfall = 0;
    public static int IgnoreRDII = 0;
    public static int IgnoreSnowmelt = 0;
    public static int IgnoreGwater = 0;
    public static int IgnoreRouting = 0;
    public static int IgnoreQuality = 0;
    public static int ErrorCode = 0;
    public static int Warnings = 0;
    public static int WetStep = 0;
    public static int DryStep = 0;
    public static int ReportStep = 0;
    public static int RuleStep = 0;
    public static int SweepStart = 0;
    public static int SweepEnd = 0;
    public static int MaxTrials = 0;
    public static int NumThreads = 0;
    public static int NumEvents = 0;
    public static double RouteStep = 0.0d;
    public static DBox MinRouteStep = new DBox(0.0d);
    public static double LengtheningStep = 0.0d;
    public static double StartDryDays = 0.0d;
    public static DBox CourantFactor = new DBox(0.0d);
    public static DBox MinSurfArea = new DBox(0.0d);
    public static DBox MinSlope = new DBox(0.0d);
    public static double RunoffError = 0.0d;
    public static double GwaterError = 0.0d;
    public static double FlowError = 0.0d;
    public static double QualError = 0.0d;
    public static DBox HeadTol = new DBox(0.0d);
    public static DBox SysFlowTol = new DBox(0.0d);
    public static DBox LatFlowTol = new DBox(0.0d);
    public static double CrownCutoff = 0.0d;
    public static DBox StartDate = new DBox(0.0d);
    public static DBox StartTime = new DBox(0.0d);
    public static double StartDateTime = 0.0d;
    public static DBox EndDate = new DBox(0.0d);
    public static DBox EndTime = new DBox(0.0d);
    public static double EndDateTime = 0.0d;
    public static DBox ReportStartDate = new DBox(0.0d);
    public static DBox ReportStartTime = new DBox(0.0d);
    public static double ReportStart = 0.0d;
    public static double ReportTime = 0.0d;
    public static double OldRunoffTime = 0.0d;
    public static double NewRunoffTime = 0.0d;
    public static double OldRoutingTime = 0.0d;
    public static double NewRoutingTime = 0.0d;
    public static double TotalDuration = 0.0d;
    public static double ElapsedTime = 0.0d;
    public static TTemp Temp = new TTemp();
    public static TEvap Evap = new TEvap();
    public static TWind Wind = new TWind();
    public static TSnow Snow = new TSnow();
    public static TAdjust Adjust = new TAdjust();
    public static TSnowmelt[] Snowmelt = null;
    public static TGage[] Gage = null;
    public static TSubcatch[] Subcatch = null;
    public static TAquifer[] Aquifer = null;
    public static TUnitHyd[] UnitHyd = null;
    public static TNode[] Node_ = null;
    public static TOutfall[] Outfall = null;
    public static TDivider[] Divider = null;
    public static TStorage[] Storage = null;
    public static TLink[] Link = null;
    public static TConduit[] Conduit = null;
    public static TPump[] Pump = null;
    public static TOrifice[] Orifice = null;
    public static TWeir[] Weir = null;
    public static TOutlet[] Outlet = null;
    public static TPollut[] Pollut = null;
    public static TLanduse[] Landuse = null;
    public static TPattern[] Pattern = null;
    public static TTable[] Curve = null;
    public static TTable[] Tseries = null;
    public static TTransect[] Transect = null;
    public static TStreet[] Street = null;
    public static TShape[] Shape = null;
    public static TEvent[] Event = null;
    public static double[][] Ucf = new double[][] { new double[] { 43200.0d, 1097280.0d }, new double[] { 12.0d, 304.8d }, new double[] { 1036800.0d, 26334720.0d }, new double[] { 1.0d, 0.3048d }, new double[] { 2.2956e-5d, 0.92903e-5d }, new double[] { 1.0d, 0.02832d }, new double[] { 1.0d, 1.608d }, new double[] { 1.0d, 1.8d }, new double[] { 2.203e-6d, 1.0e-6d }, new double[] { 43560.0d, 3048.0d } };
    public static double[] Qcf = new double[] { 1.0d, 448.831d, 0.64632d, 0.02832d, 28.317d, 2.4466d };
    public static int IsOpenFlag = 0;
    public static int IsStartedFlag = 0;
    public static int SaveResultsFlag = 0;
    public static int ExceptionCount = 0;
    public static int DoRunoff = 0;
    public static int DoRouting = 0;
    public static double RoutingDuration = 0.0d;

    // swmm5.c:186
    static int swmm_run(CPtr f1, CPtr f2, CPtr f3) {
        long newHour = 0L;
        long oldHour = 0;
        long theDay = 0L;
        long theHour = 0L;
        DBox elapsedTime = new DBox(0.0d);
        IsOpenFlag = 0;
        IsStartedFlag = 0;
        SaveResultsFlag = 1;
        ErrorCode = 0;
        writecon(L642);
        swmm_open(f1, f2, f3);
        if (!(ErrorCode != 0)) {
            swmm_start(1);
            if (!(ErrorCode != 0)) {
                writecon(L643);
                L_d1: do {
                    swmm_step(elapsedTime);
                    newHour = R.D2L(((elapsedTime.v * 24.0d)));
                    if ((newHour > oldHour)) {
                        theDay = R.D2L(elapsedTime.v);
                        theHour = R.D2L(((((elapsedTime.v - Math.floor(elapsedTime.v))) * 24.0d)));
                        writecon(L644);
                        R.snprintf(Msg, (int) (1024), L645, theDay, theHour);
                        writecon(Msg);
                        oldHour = newHour;
                    }
                } while (((R.dgt(elapsedTime.v, 0.0d)) && (!(ErrorCode != 0))));
                writecon(L646);
                writecon(L647);
            }
            swmm_end();
        }
        if (((!(ErrorCode != 0)) && (Fout.mode == SCRATCH_FILE))) {
            writecon(L648);
            swmm_report();
        }
        swmm_close();
        return ErrorCode;
    }

    // swmm5.c:256
    static int swmm_open(CPtr f1, CPtr f2, CPtr f3) {
        F_datetime.datetime_setDateFormat(1);
        ErrorCode = 0;
        ErrorMsg.s(0, 0);
        Warnings = 0;
        IsOpenFlag = 0;
        IsStartedFlag = 0;
        ExceptionCount = 0;
        R.strcpy(InpDir, L0);
        F_project.project_open(f1, f2, f3);
        Overrides.getAbsolutePath(f1, InpDir, 260L);
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        IsOpenFlag = 1;
        F_report.report_writeLogo();
        F_project.project_readInput();
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        F_report.report_writeTitle();
        F_project.project_validate();
        return ErrorCode;
    }

    // swmm5.c:314
    static int swmm_start(int saveResults) {
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        if (!(IsOpenFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_OPEN));
        }
        if ((IsStartedFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_ENDED));
        }
        if (!(RptFlags.disabled != 0)) {
            if ((RptFlags.input != 0)) {
                F_inputrpt.inputrpt_writeInput();
            }
            F_report.report_writeOptions();
        }
        SaveResultsFlag = saveResults;
        ExceptionCount = 0;
        ElapsedTime = 0.0d;
        RoutingDuration = TotalDuration;
        NewRunoffTime = 0.0d;
        NewRoutingTime = 0.0d;
        ReportTime = (1000 * ((double) (ReportStep)));
        TotalStepCount = 0;
        ReportStepCount = 0;
        NonConvergeCount = 0;
        IsStartedFlag = 1;
        RunoffError = 0.0d;
        GwaterError = 0.0d;
        FlowError = 0.0d;
        QualError = 0.0d;
        if (!(IgnoreRainfall != 0)) {
            F_rain.rain_open();
        }
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        F_project.project_init();
        if ((Nobjects[SUBCATCH] > 0)) {
            DoRunoff = 1;
        } else {
            DoRunoff = 0;
        }
        if (((Nobjects[NODE] > 0) && (!(IgnoreRouting != 0)))) {
            DoRouting = 1;
        } else {
            DoRouting = 0;
        }
        F_output.output_open();
        if ((DoRunoff != 0)) {
            F_runoff.runoff_open();
        }
        if (!(F_hotstart.hotstart_open() != 0)) {
            return ErrorCode;
        }
        if ((DoRouting != 0)) {
            F_routing.routing_open();
        }
        F_massbal.massbal_open();
        F_stats.stats_open();
        if (((!(RptFlags.disabled != 0)) && (RptFlags.controls != 0))) {
            F_report.report_writeControlActionsHeading();
        }
        return ErrorCode;
    }

    // swmm5.c:410
    static int swmm_step(DRef elapsedTime) {
        elapsedTime.set(0.0d);
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        if (!(IsOpenFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_OPEN));
        }
        if (!(IsStartedFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_STARTED));
        }
        if ((NewRoutingTime < RoutingDuration)) {
            execRouting();
        }
        if ((SaveResultsFlag != 0)) {
            saveResults();
        }
        if ((NewRoutingTime < RoutingDuration)) {
            ElapsedTime = (NewRoutingTime / 86400000.0d);
        } else {
            ElapsedTime = 0.0d;
        }
        elapsedTime.set(ElapsedTime);
        return ErrorCode;
    }

    // swmm5.c:466
    static int swmm_stride(int strideStep, DRef elapsedTime) {
        double realRouteStep = RouteStep;
        elapsedTime.set(0.0d);
        if ((ErrorCode != 0)) {
            return ErrorCode;
        }
        if (!(IsOpenFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_OPEN));
        }
        if (!(IsStartedFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_STARTED));
        }
        RoutingDuration = (NewRoutingTime + (1000.0d * strideStep));
        RoutingDuration = (((((TotalDuration) <= (RoutingDuration))) ? (TotalDuration) : (RoutingDuration)));
        if ((strideStep < RouteStep)) {
            RouteStep = strideStep;
        }
        L_d1: do {
            swmm_step(elapsedTime);
        } while (((R.dgt(elapsedTime.get(), 0.0d)) && (!(ErrorCode != 0))));
        RouteStep = realRouteStep;
        RoutingDuration = TotalDuration;
        if ((NewRoutingTime < TotalDuration)) {
            ElapsedTime = (NewRoutingTime / 86400000.0d);
        } else {
            ElapsedTime = 0.0d;
        }
        elapsedTime.set(ElapsedTime);
        return ErrorCode;
    }

    // swmm5.c:514
    static void execRouting() {
        double nextRoutingTime = 0.0d;
        double routingStep = 0.0d;
        TotalStepCount++;
        if (!(DoRouting != 0)) {
            routingStep = (((((WetStep) <= (ReportStep))) ? (WetStep) : (ReportStep)));
        } else {
            routingStep = F_routing.routing_getRoutingStep(RouteModel, RouteStep);
        }
        if ((routingStep <= 0.0d)) {
            ErrorCode = ERR_TIMESTEP;
            return;
        }
        nextRoutingTime = (NewRoutingTime + (1000.0d * routingStep));
        if (R.dgt(nextRoutingTime, RoutingDuration)) {
            routingStep = (((RoutingDuration - NewRoutingTime)) / 1000.0d);
            routingStep = (((R.dge((routingStep), ((1.0d / 1000.0d)))) ? (routingStep) : ((1.0d / 1000.0d))));
            nextRoutingTime = RoutingDuration;
        }
        if ((DoRunoff != 0)) {
            L_w1: while ((NewRunoffTime < nextRoutingTime)) {
                F_runoff.runoff_execute();
                if ((ErrorCode != 0)) {
                    return;
                }
            }
        } else {
            F_climate.climate_setState(getDateTime(NewRoutingTime));
        }
        if ((DoRouting != 0)) {
            F_routing.routing_execute(RouteModel, routingStep);
        } else {
            NewRoutingTime = nextRoutingTime;
        }
    }

    // swmm5.c:579
    static void saveResults() {
        if (R.dge(NewRoutingTime, ReportTime)) {
            if ((RptFlags.averages != 0)) {
                if ((NewRoutingTime == ReportTime)) {
                    F_output.output_updateAvgResults();
                }
                F_output.output_saveResults(ReportTime);
                if (R.dgt(NewRoutingTime, ReportTime)) {
                    F_output.output_updateAvgResults();
                }
            } else {
                F_output.output_saveResults(ReportTime);
            }
            ReportTime = (ReportTime + (1000 * ((double) (ReportStep))));
        } else if ((RptFlags.averages != 0)) {
            F_output.output_updateAvgResults();
        }
    }

    // swmm5.c:618
    static int swmm_end() {
        if (!(IsOpenFlag != 0)) {
            return ((ErrorCode = ERR_API_NOT_OPEN));
        }
        if ((IsStartedFlag != 0)) {
            if ((Fout.file != null)) {
                F_output.output_end();
            }
            if (((!(ErrorCode != 0)) && (RptFlags.disabled == 0))) {
                F_massbal.massbal_report();
                F_stats.stats_report();
            }
            F_stats.stats_close();
            F_massbal.massbal_close();
            if (!(IgnoreRainfall != 0)) {
                F_rain.rain_close();
            }
            if ((DoRunoff != 0)) {
                F_runoff.runoff_close();
            }
            if ((DoRouting != 0)) {
                F_routing.routing_close(RouteModel);
            }
            F_hotstart.hotstart_close();
            IsStartedFlag = 0;
        }
        return ErrorCode;
    }

    // swmm5.c:655
    static int swmm_report() {
        if (!(ErrorCode != 0)) {
            F_report.report_writeReport();
        }
        return ErrorCode;
    }

    // swmm5.c:669
    static void swmm_writeLine(CPtr line) {
        if ((IsOpenFlag != 0)) {
            F_report.report_writeLine(line);
        }
    }

    // swmm5.c:682
    static int swmm_close() {
        if ((Fout.file != null)) {
            F_output.output_close();
        }
        if ((IsOpenFlag != 0)) {
            F_project.project_close();
        }
        F_report.report_writeSysTime();
        if ((Finp.file != null)) {
            R.fclose(Finp.file);
        }
        if ((Frpt.file != null)) {
            R.fclose(Frpt.file);
        }
        if ((Fout.file != null)) {
            R.fclose(Fout.file);
            if ((Fout.mode == SCRATCH_FILE)) {
                R.remove(Fout.name);
            }
        }
        IsOpenFlag = 0;
        IsStartedFlag = 0;
        return 0;
    }

    // swmm5.c:708
    static int swmm_getMassBalErr(FRef runoffErr, FRef flowErr, FRef qualErr) {
        runoffErr.set((float) (0.0d));
        flowErr.set((float) (0.0d));
        qualErr.set((float) (0.0d));
        if (((IsOpenFlag != 0) && (!(IsStartedFlag != 0)))) {
            runoffErr.set(((float) (RunoffError)));
            flowErr.set(((float) (FlowError)));
            qualErr.set(((float) (QualError)));
        }
        return 0;
    }

    // swmm5.c:734
    static int swmm_getVersion() {
        return 52004;
    }

    // swmm5.c:749
    static int swmm_getWarnings() {
        return Warnings;
    }

    // swmm5.c:760
    static int swmm_getError(CPtr errMsg, int msgLen) {
        if (((ErrorCode > 0) && (R.strlen(ErrorMsg) == 0))) {
            F_error.error_getMsg(ErrorCode, ErrorMsg);
        }
        Overrides.sstrncpy(errMsg, ErrorMsg, msgLen);
        if (((msgLen > 0) && (errMsg.g(0) == 10))) {
            errMsg.s(0, 32);
        }
        return ErrorCode;
    }

    // swmm5.c:780
    static int swmm_getCount(int objType) {
        if (!(IsOpenFlag != 0)) {
            return 0;
        }
        if (((objType < swmm_GAGE) || (objType > swmm_LINK))) {
            return 0;
        }
        return Nobjects[objType];
    }

    // swmm5.c:795
    static void swmm_getName(int objType, int index, CPtr name, int size) {
        CPtr idName = null;
        name.s(0, 0);
        if (!(IsOpenFlag != 0)) {
            return;
        }
        if (((objType < swmm_GAGE) || (objType > swmm_LINK))) {
            return;
        }
        if (((index < 0) || (index >= Nobjects[objType]))) {
            return;
        }
        int __sw1 = objType;
        if (__sw1 == GAGE) {
            idName = Gage[index].ID;
        } else if (__sw1 == SUBCATCH) {
            idName = Subcatch[index].ID;
        } else if (__sw1 == NODE) {
            idName = Node_[index].ID;
        } else if (__sw1 == LINK) {
            idName = Link[index].ID;
        }
        if ((idName != null)) {
            Overrides.sstrncpy(name, idName, size);
        }
    }

    // swmm5.c:826
    static int swmm_getIndex(int objType, CPtr name) {
        if (!(IsOpenFlag != 0)) {
            return (-1);
        }
        if (((objType < swmm_GAGE) || (objType > swmm_LINK))) {
            return (-1);
        }
        return F_project.project_findObject(objType, name);
    }

    // swmm5.c:842
    static double swmm_getValue(int property, int index) {
        if (!(IsOpenFlag != 0)) {
            return 0;
        }
        if ((property < 100)) {
            return getSystemValue(property);
        }
        if ((property < 200)) {
            return getGageValue(property, index);
        }
        if ((property < 300)) {
            return getSubcatchValue(property, index);
        }
        if ((property < 400)) {
            return getNodeValue(property, index);
        }
        if ((property < 500)) {
            return getLinkValue(property, index);
        }
        return 0;
    }

    // swmm5.c:867
    static void swmm_setValue(int property, int index, double value) {
        if (!(IsOpenFlag != 0)) {
            return;
        }
        int __sw1 = property;
        if (__sw1 == swmm_GAGE_RAINFALL) {
            if (((index < 0) || (index >= Nobjects[GAGE]))) {
                return;
            }
            if (R.dge(value, 0.0d)) {
                Gage[index].@apiRainfall = value;
            }
            return;
        } else if (__sw1 == swmm_SUBCATCH_RPTFLAG) {
            if ((((!(IsStartedFlag != 0)) && (index >= 0)) && (index < Nobjects[SUBCATCH]))) {
                Subcatch[index].@rptFlag = ((R.dgt(value, 0.0d)) ? 1 : 0);
            }
            return;
        } else if (__sw1 == swmm_NODE_LATFLOW) {
            setNodeLatFlow(index, value);
            return;
        } else if (__sw1 == swmm_NODE_HEAD) {
            setOutfallStage(index, value);
            return;
        } else if (__sw1 == swmm_NODE_RPTFLAG) {
            if ((((!(IsStartedFlag != 0)) && (index >= 0)) && (index < Nobjects[NODE]))) {
                Node_[index].@rptFlag = ((R.dgt(value, 0.0d)) ? 1 : 0);
            }
            return;
        } else if (__sw1 == swmm_LINK_SETTING) {
            setLinkSetting(index, value);
            return;
        } else if (__sw1 == swmm_LINK_RPTFLAG) {
            if ((((!(IsStartedFlag != 0)) && (index >= 0)) && (index < Nobjects[LINK]))) {
                Link[index].@rptFlag = ((R.dgt(value, 0.0d)) ? 1 : 0);
            }
            return;
        } else if (__sw1 == swmm_ROUTESTEP) {
            setRoutingStep(value);
            return;
        } else if (__sw1 == swmm_REPORTSTEP) {
            if (((!(IsStartedFlag != 0)) && (R.dgt(value, 0)))) {
                ReportStep = R.D2I(value);
            }
            return;
        } else if (__sw1 == swmm_NOREPORT) {
            if (!(IsStartedFlag != 0)) {
                RptFlags.@disabled = ((R.dgt(value, 0.0d)) ? 1 : 0);
            }
            return;
        }
    }

    // swmm5.c:922
    static double swmm_getSavedValue(int property, int index, int period) {
        if (!(IsOpenFlag != 0)) {
            return 0;
        }
        if ((IsStartedFlag != 0)) {
            return 0;
        }
        if (((period < 1) || (period > Nperiods))) {
            return 0;
        }
        if ((property == swmm_CURRENTDATE)) {
            return getSavedDate(period);
        }
        if (((property >= 200) && (property < 300))) {
            return getSavedSubcatchValue(property, index, period);
        }
        if ((property < 400)) {
            return getSavedNodeValue(property, index, period);
        }
        if ((property < 500)) {
            return getSavedLinkValue(property, index, period);
        }
        return 0;
    }

    // swmm5.c:949
    static void swmm_decodeDate(double date, IRef year, IRef month, IRef day, IRef hour, IRef minute, IRef second, IRef dayOfWeek) {
        F_datetime.datetime_decodeDate(date, year, month, day);
        F_datetime.datetime_decodeTime(date, hour, minute, second);
        dayOfWeek.set(F_datetime.datetime_dayOfWeek(date));
    }

    // swmm5.c:966
    static double getGageValue(int property, int index) {
        if (((index < 0) || (index >= Nobjects[GAGE]))) {
            return 0;
        }
        if ((property == swmm_GAGE_RAINFALL)) {
            return Gage[index].reportRainfall;
        }
        return 0;
    }

    // swmm5.c:982
    static double getSubcatchValue(int property, int index) {
        TSubcatch subcatch = null;
        if (((index < 0) || (index >= Nobjects[SUBCATCH]))) {
            return 0;
        }
        subcatch = Subcatch[index];
        int __sw1 = property;
        if (__sw1 == swmm_SUBCATCH_AREA) {
            return (subcatch.area * UCF(LANDAREA));
        } else if (__sw1 == swmm_SUBCATCH_RAINGAGE) {
            return subcatch.gage;
        } else if (__sw1 == swmm_SUBCATCH_RAINFALL) {
            if ((subcatch.gage >= 0)) {
                return Gage[subcatch.gage].reportRainfall;
            } else {
                return 0.0d;
            }
        } else if (__sw1 == swmm_SUBCATCH_EVAP) {
            return (subcatch.evapLoss * UCF(EVAPRATE));
        } else if (__sw1 == swmm_SUBCATCH_INFIL) {
            return (subcatch.infilLoss * UCF(RAINFALL));
        } else if (__sw1 == swmm_SUBCATCH_RUNOFF) {
            return (subcatch.newRunoff * UCF(FLOW));
        } else if (__sw1 == swmm_SUBCATCH_RPTFLAG) {
            return (((subcatch.rptFlag > 0)) ? 1 : 0);
        } else {
            return 0;
        }
        throw new IllegalStateException('getSubcatchValue: no return');
    }

    // swmm5.c:1019
    static double getNodeValue(int property, int index) {
        TNode node = null;
        if (((index < 0) || (index >= Nobjects[NODE]))) {
            return 0;
        }
        node = Node_[index];
        int __sw1 = property;
        if (__sw1 == swmm_NODE_TYPE) {
            return node.type;
        } else if (__sw1 == swmm_NODE_ELEV) {
            return (node.invertElev * UCF(LENGTH));
        } else if (__sw1 == swmm_NODE_MAXDEPTH) {
            return (node.fullDepth * UCF(LENGTH));
        } else if (__sw1 == swmm_NODE_DEPTH) {
            return (node.newDepth * UCF(LENGTH));
        } else if (__sw1 == swmm_NODE_HEAD) {
            return (((node.newDepth + node.invertElev)) * UCF(LENGTH));
        } else if (__sw1 == swmm_NODE_VOLUME) {
            return (node.newVolume * UCF(VOLUME));
        } else if (__sw1 == swmm_NODE_LATFLOW) {
            return (node.newLatFlow * UCF(FLOW));
        } else if (__sw1 == swmm_NODE_INFLOW) {
            return (node.inflow * UCF(FLOW));
        } else if (__sw1 == swmm_NODE_OVERFLOW) {
            return (node.overflow * UCF(FLOW));
        } else if (__sw1 == swmm_NODE_RPTFLAG) {
            return (((node.rptFlag > 0)) ? 1 : 0);
        } else {
            return 0;
        }
        throw new IllegalStateException('getNodeValue: no return');
    }

    // swmm5.c:1059
    static double getLinkValue(int property, int index) {
        TLink link = null;
        if (((index < 0) || (index >= Nobjects[LINK]))) {
            return 0;
        }
        link = Link[index];
        int __sw1 = property;
        if (__sw1 == swmm_LINK_TYPE) {
            return link.type;
        } else if (__sw1 == swmm_LINK_NODE1) {
            return link.node1;
        } else if (__sw1 == swmm_LINK_NODE2) {
            return link.node2;
        } else if (__sw1 == swmm_LINK_LENGTH) {
            if ((link.type == CONDUIT)) {
                return (Conduit[link.subIndex].length * UCF(LENGTH));
            } else {
                return 0;
            }
        } else if (__sw1 == swmm_LINK_SLOPE) {
            if ((link.type == CONDUIT)) {
                return Conduit[link.subIndex].slope;
            } else {
                return 0;
            }
        } else if (__sw1 == swmm_LINK_FULLDEPTH) {
            return (link.xsect.yFull * UCF(LENGTH));
        } else if (__sw1 == swmm_LINK_FULLFLOW) {
            return (link.qFull * UCF(FLOW));
        } else if (__sw1 == swmm_LINK_FLOW) {
            return ((link.newFlow * UCF(FLOW)) * ((double) (link.direction)));
        } else if (__sw1 == swmm_LINK_VELOCITY) {
            return (F_link.link_getVelocity(index, Math.abs(link.newFlow), link.newDepth) * UCF(LENGTH));
        } else if (__sw1 == swmm_LINK_DEPTH) {
            return (link.newDepth * UCF(LENGTH));
        } else if (__sw1 == swmm_LINK_TOPWIDTH) {
            if ((link.type == CONDUIT)) {
                return (F_xsect.xsect_getWofY(link.xsect, link.newDepth) * UCF(LENGTH));
            } else {
                return 0;
            }
        } else if (__sw1 == swmm_LINK_SETTING) {
            return link.setting;
        } else if (__sw1 == swmm_LINK_TIMEOPEN) {
            if (R.dgt(link.setting, 0.0d)) {
                return (((getDateTime(NewRoutingTime) - link.timeLastSet)) * 24.0d);
            } else {
                return 0;
            }
        } else if (__sw1 == swmm_LINK_TIMECLOSED) {
            if ((link.setting == 0.0d)) {
                return (((getDateTime(NewRoutingTime) - link.timeLastSet)) * 24.0d);
            } else {
                return 0;
            }
        } else if (__sw1 == swmm_LINK_RPTFLAG) {
            return (((link.rptFlag > 0)) ? 1 : 0);
        } else {
            return 0;
        }
        throw new IllegalStateException('getLinkValue: no return');
    }

    // swmm5.c:1126
    static double getSystemValue(int property) {
        int __sw1 = property;
        if (__sw1 == swmm_STARTDATE) {
            return StartDateTime;
        } else if (__sw1 == swmm_CURRENTDATE) {
            return (StartDateTime + ElapsedTime);
        } else if (__sw1 == swmm_ELAPSEDTIME) {
            return ElapsedTime;
        } else if (__sw1 == swmm_ROUTESTEP) {
            return RouteStep;
        } else if (__sw1 == swmm_MAXROUTESTEP) {
            return getMaxRouteStep();
        } else if (__sw1 == swmm_REPORTSTEP) {
            return ReportStep;
        } else if (__sw1 == swmm_TOTALSTEPS) {
            return Nperiods;
        } else if (__sw1 == swmm_NOREPORT) {
            return RptFlags.disabled;
        } else if (__sw1 == swmm_FLOWUNITS) {
            return FlowUnits;
        } else {
            return 0;
        }
        throw new IllegalStateException('getSystemValue: no return');
    }

    // swmm5.c:1159
    static void setNodeLatFlow(int index, double value) {
        if (((index < 0) || (index >= Nobjects[NODE]))) {
            return;
        }
        Node_[index].@apiExtInflow = (value / UCF(FLOW));
    }

    // swmm5.c:1173
    static void setOutfallStage(int index, double value) {
        TNode node = null;
        if (((index < 0) || (index >= Nobjects[NODE]))) {
            return;
        }
        node = Node_[index];
        if ((node.type != OUTFALL)) {
            return;
        }
        Outfall[node.subIndex].@fixedStage = (value / UCF(LENGTH));
        Outfall[node.subIndex].@type = FIXED_OUTFALL;
    }

    // swmm5.c:1192
    static void setLinkSetting(int index, double value) {
        TLink link = null;
        if (((index < 0) || (index >= Nobjects[LINK]))) {
            return;
        }
        link = Link[index];
        if (((value < 0.0d) || (link.type == CONDUIT))) {
            return;
        }
        if (((link.type != PUMP) && (R.dgt(value, 1.0d)))) {
            value = 1.0d;
        }
        if ((link.targetSetting == value)) {
            return;
        }
        link.@targetSetting = value;
        if (((link.targetSetting * link.setting) == 0.0d)) {
            link.@timeLastSet = (StartDateTime + ElapsedTime);
        }
        F_link.link_setSetting(index, 0.0d);
    }

    // swmm5.c:1217
    static double getSavedDate(int period) {
        DBox days = new DBox(0.0d);
        F_output.output_readDateTime(period, days);
        return days.v;
    }

    // swmm5.c:1230
    static double getSavedSubcatchValue(int property, int index, int period) {
        int outIndex = (Subcatch[index].rptFlag - 1);
        if ((outIndex < 0)) {
            return 0;
        }
        F_output.output_readSubcatchResults(period, outIndex);
        int __sw1 = property;
        if (__sw1 == swmm_SUBCATCH_RAINFALL) {
            return F_output.SubcatchResults[SUBCATCH_RAINFALL];
        } else if (__sw1 == swmm_SUBCATCH_EVAP) {
            return F_output.SubcatchResults[SUBCATCH_EVAP];
        } else if (__sw1 == swmm_SUBCATCH_INFIL) {
            return F_output.SubcatchResults[SUBCATCH_INFIL];
        } else if (__sw1 == swmm_SUBCATCH_RUNOFF) {
            return F_output.SubcatchResults[SUBCATCH_RUNOFF];
        } else {
            return 0;
        }
        throw new IllegalStateException('getSavedSubcatchValue: no return');
    }

    // swmm5.c:1265
    static double getSavedNodeValue(int property, int index, int period) {
        int outIndex = (Node_[index].rptFlag - 1);
        if ((outIndex < 0)) {
            return 0;
        }
        F_output.output_readNodeResults(period, outIndex);
        int __sw1 = property;
        if (__sw1 == swmm_NODE_DEPTH) {
            return F_output.NodeResults[NODE_DEPTH];
        } else if (__sw1 == swmm_NODE_HEAD) {
            return F_output.NodeResults[NODE_HEAD];
        } else if (__sw1 == swmm_NODE_VOLUME) {
            return F_output.NodeResults[NODE_VOLUME];
        } else if (__sw1 == swmm_NODE_LATFLOW) {
            return F_output.NodeResults[NODE_LATFLOW];
        } else if (__sw1 == swmm_NODE_INFLOW) {
            return F_output.NodeResults[NODE_INFLOW];
        } else if (__sw1 == swmm_NODE_OVERFLOW) {
            return F_output.NodeResults[NODE_OVERFLOW];
        } else {
            return 0;
        }
        throw new IllegalStateException('getSavedNodeValue: no return');
    }

    // swmm5.c:1304
    static double getSavedLinkValue(int property, int index, int period) {
        double y = 0.0d;
        double w = 0.0d;
        int outIndex = (Link[index].rptFlag - 1);
        if ((outIndex < 0)) {
            return 0;
        }
        F_output.output_readLinkResults(period, outIndex);
        int __sw1 = property;
        if (__sw1 == swmm_LINK_FLOW) {
            return F_output.LinkResults[LINK_FLOW];
        } else if (__sw1 == swmm_LINK_DEPTH) {
            return F_output.LinkResults[LINK_DEPTH];
        } else if (__sw1 == swmm_LINK_VELOCITY) {
            return F_output.LinkResults[LINK_VELOCITY];
        } else if (__sw1 == swmm_LINK_TOPWIDTH) {
            y = (F_output.LinkResults[LINK_DEPTH] / UCF(LENGTH));
            w = F_xsect.xsect_getWofY(Link[index].xsect, y);
            return (w * UCF(LENGTH));
        } else if (__sw1 == swmm_LINK_SETTING) {
            return F_output.LinkResults[LINK_CAPACITY];
        } else {
            return 0;
        }
        throw new IllegalStateException('getSavedLinkValue: no return');
    }

    // swmm5.c:1345
    static double getMaxRouteStep() {
        double tmpCourantFactor = CourantFactor.v;
        double result = RouteStep;
        if (((!(IsStartedFlag != 0)) || (RouteModel != DW))) {
            return result;
        }
        CourantFactor.@v = 1.0d;
        result = F_routing.routing_getRoutingStep(RouteModel, MinRouteStep.v);
        CourantFactor.@v = tmpCourantFactor;
        return result;
    }

    // swmm5.c:1360
    static void setRoutingStep(double value) {
        if ((value <= 0.0d)) {
            return;
        }
        if ((value <= MinRouteStep.v)) {
            value = MinRouteStep.v;
        }
        CourantFactor.@v = 0.0d;
        RouteStep = value;
    }

    // swmm5.c:1378
    static double UCF(int u) {
        if ((u < FLOW)) {
            return Ucf[u][UnitSystem];
        } else {
            return Qcf[FlowUnits];
        }
        throw new IllegalStateException('UCF: no return');
    }

    // swmm5.c:1453
    static int strcomp(CPtr s1, CPtr s2) {
        int i = 0;
        L_f1: for (i = 0; (((((((s1.g(i)) >= 97) && ((s1.g(i)) <= 122))) ? (((s1.g(i)) & (~32))) : (s1.g(i)))) == ((((((s2.g(i)) >= 97) && ((s2.g(i)) <= 122))) ? (((s2.g(i)) & (~32))) : (s2.g(i))))); i++) {
            if (((!(s1.g((i + 1)) != 0)) && (!(s2.g((i + 1)) != 0)))) {
                return (1);
            }
        }
        return (0);
    }

    // swmm5.c:1518
    static void getElapsedTime(double aDate, IRef days, IRef hrs, IRef mins) {
        double x = 0.0d;
        IBox secs = new IBox(0);
        x = (aDate - ReportStart);
        if ((x <= 0.0d)) {
            days.set(0);
            hrs.set(0);
            mins.set(0);
        } else {
            days.set(R.D2I(x));
            F_datetime.datetime_decodeTime(x, hrs, mins, secs);
        }
    }

    // swmm5.c:1543
    static double getDateTime(double elapsedMsec) {
        return F_datetime.datetime_addSeconds(StartDateTime, (((elapsedMsec + 1)) / 1000.0d));
    }

    // swmm5.c:1556
    static int isRelativePath(CPtr fname) {
        if ((R.strchr(fname, 58) != null)) {
            return 0;
        }
        if ((fname.g(0) == 92)) {
            return 0;
        }
        if ((fname.g(0) == 47)) {
            return 0;
        }
        return 1;
    }

    // swmm5.c:1617
    static CPtr addAbsolutePath(CPtr fname) {
        long n = 0L;
        CPtr buffer = R.buf(259);
        if ((isRelativePath(fname) != 0)) {
            n = R.snprintf(buffer, (int) (259), L649, InpDir, fname);
            if ((n > 0)) {
                Overrides.sstrncpy(fname, buffer, 259);
            }
        }
        return fname;
    }

    // swmm5.c:1638
    static void writecon(CPtr s) {
        R.fprintf(R.stdout, L169, s);
        R.fflush(R.stdout);
    }

}
