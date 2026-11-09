// Generated from EPA SWMM 5.2.4 (rdii.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_rdii {
    public static double ZERO_RDII = 0.0001d;
    public static CPtr FileStamp = R.bufFrom(L352, 11);
    public static TUHGroup[] UHGroup = null;
    public static IBox RdiiStep = new IBox(0);
    public static IBox NumRdiiNodes = new IBox(0);
    public static int[] RdiiNodeIndex = null;
    public static float[] RdiiNodeFlow = null;
    public static int RdiiFlowUnits = 0;
    public static DBox RdiiStartDate = new DBox(0.0d);
    public static double RdiiEndDate = 0.0d;
    public static double TotalRainVol = 0.0d;
    public static double TotalRdiiVol = 0.0d;
    public static int RdiiFileType = 0;

    // rdii.c:152
    static int rdii_readRdiiInflow(CPtr[] tok, int ntoks) {
        int j = 0;
        int k = 0;
        DBox a = new DBox(0.0d);
        TRdiiInflow inflow = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(NODE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = F_project.project_findObject(UNITHYD, tok[1]);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        if (((!(F_input.getDouble(tok[2], a) != 0)) || (a.v < 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        inflow = F_swmm5.Node_[j].rdiiInflow;
        if ((inflow == null)) {
            inflow = new TRdiiInflow();
            if (!(inflow != null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
        }
        inflow.@unitHyd = k;
        inflow.@area = (a.v / F_swmm5.UCF(LANDAREA));
        F_swmm5.Node_[j].@rdiiInflow = inflow;
        return 0;
    }

    // rdii.c:198
    static void rdii_initUnitHyd(int j) {
        int i = 0;
        int m = 0;
        L_f1: for (m = 0; (m < 12); m++) {
            L_f2: for (i = 0; (i < 3); i++) {
                F_swmm5.UnitHyd[j].iaMax[m][i] = 0.0d;
                F_swmm5.UnitHyd[j].iaRecov[m][i] = 0.0d;
                F_swmm5.UnitHyd[j].iaInit[m][i] = 0.0d;
                F_swmm5.UnitHyd[j].r[m][i] = 0.0d;
                F_swmm5.UnitHyd[j].tPeak[m][i] = 0;
                F_swmm5.UnitHyd[j].tBase[m][i] = 0;
            }
        }
    }

    // rdii.c:224
    static int rdii_readUnitHydParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int g = 0;
        double[] x = new double[6];
        j = F_project.project_findObject(UNITHYD, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((F_swmm5.UnitHyd[j].ID == null)) {
            F_swmm5.UnitHyd[j].@ID = F_project.project_findID(UNITHYD, tok[0]);
        }
        if ((ntoks == 2)) {
            g = F_project.project_findObject(GAGE, tok[1]);
            if ((g < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            F_swmm5.UnitHyd[j].@rainGage = g;
            F_swmm5.Gage[g].@isUsed = 1;
            return 0;
        } else if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        m = F_datetime.datetime_findMonth(tok[1]);
        if ((m == 0)) {
            if (!(F_input.match(tok[1], L351) != 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
        }
        k = F_input.findmatch(tok[2], F_keywords.UHTypeWords);
        if ((k < 0)) {
            return readOldUHFormat(j, m, tok, ntoks);
        }
        L_f1: for (i = 0; (i < 3); i++) {
            if (!(F_input.getDouble(tok[(i + 3)], new DElem(x, i)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(i + 3)]);
            }
        }
        L_f2: for (i = 3; (i < 6); i++) {
            x[i] = 0.0d;
            if ((ntoks > (i + 3))) {
                if (!(F_input.getDouble(tok[(i + 3)], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(i + 2)]);
                }
            }
        }
        setUnitHydParams(j, k, m, x);
        return 0;
    }

    // rdii.c:293
    static int readOldUHFormat(int j, int m, CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        double[] p = new double[9];
        double[] x = new double[6];
        if ((ntoks < 11)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 0; (i < 9); i++) {
            if (!(F_input.getDouble(tok[(i + 2)], new DElem(p, i)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(i + 2)]);
            }
        }
        L_f2: for (i = 0; (i < 3); i++) {
            x[(i + 3)] = 0.0d;
            if ((ntoks > (i + 11))) {
                if (!(F_input.getDouble(tok[(i + 11)], new DElem(x, (i + 3))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(i + 11)]);
                }
            }
        }
        L_f3: for (k = 0; (k < 3); k++) {
            L_f4: for (i = 0; (i < 3); i++) {
                x[i] = p[((3 * k) + i)];
                setUnitHydParams(j, k, m, x);
            }
        }
        return 0;
    }

    // rdii.c:342
    static void setUnitHydParams(int j, int i, int m, double[] x) {
        int m1 = 0;
        int m2 = 0;
        double t = 0.0d;
        double k = 0.0d;
        double tBase = 0.0d;
        if ((m == 0)) {
            m1 = 0;
            m2 = 11;
        } else {
            m1 = (m - 1);
            m2 = m1;
        }
        L_f1: for (m = m1; (m <= m2); m++) {
            F_swmm5.UnitHyd[j].r[m][i] = x[0];
            t = x[1];
            k = x[2];
            tBase = (t * ((1.0d + k)));
            F_swmm5.UnitHyd[j].tPeak[m][i] = R.D2L(((t * 3600.0d)));
            F_swmm5.UnitHyd[j].tBase[m][i] = R.D2L(((tBase * 3600.0d)));
            F_swmm5.UnitHyd[j].iaMax[m][i] = x[3];
            F_swmm5.UnitHyd[j].iaRecov[m][i] = x[4];
            F_swmm5.UnitHyd[j].iaInit[m][i] = x[5];
        }
    }

    // rdii.c:389
    static void rdii_deleteRdiiInflow(int j) {
        if ((F_swmm5.Node_[j].rdiiInflow != null)) {
            R.free(F_swmm5.Node_[j].rdiiInflow);
            F_swmm5.Node_[j].@rdiiInflow = (null);
        }
    }

    // rdii.c:408
    static void rdii_openRdii() {
        CPtr fStamp = R.bufFrom(L352, 11);
        RdiiNodeIndex = (null);
        RdiiNodeFlow = (null);
        NumRdiiNodes.@v = 0;
        RdiiStartDate.@v = (-693594);
        if ((F_swmm5.IgnoreRDII != 0)) {
            return;
        }
        if ((F_swmm5.Frdii.mode != USE_FILE)) {
            createRdiiFile();
        }
        if (((F_swmm5.Frdii.mode == NO_FILE) || (F_swmm5.ErrorCode != 0))) {
            return;
        }
        F_swmm5.Frdii.@file = R.fopen(F_swmm5.Frdii.name, L353);
        if ((F_swmm5.Frdii.file == null)) {
            if ((F_swmm5.Frdii.mode == SCRATCH_FILE)) {
                F_report.report_writeErrorMsg(ERR_RDII_FILE_SCRATCH, L0);
            } else {
                F_report.report_writeErrorMsg(ERR_RDII_FILE_OPEN, F_swmm5.Frdii.name);
            }
            return;
        }
        R.fread(fStamp, 0, (int) (R.strlen(FileStamp)), F_swmm5.Frdii.file);
        if ((R.strcmp(fStamp, FileStamp) == 0)) {
            RdiiFileType = BINARY;
            F_swmm5.@ErrorCode = readRdiiFileHeader();
        } else {
            R.fclose(F_swmm5.Frdii.file);
            RdiiFileType = TEXT;
            openRdiiTextFile();
        }
        if ((F_swmm5.ErrorCode != 0)) {
            F_report.report_writeErrorMsg(F_swmm5.ErrorCode, F_swmm5.Frdii.name);
        } else {
            readRdiiFlows();
        }
    }

    // rdii.c:470
    static void openRdiiTextFile() {
        F_swmm5.Frdii.@file = R.fopen(F_swmm5.Frdii.name, L13);
        if ((F_swmm5.Frdii.file == null)) {
            if ((F_swmm5.Frdii.mode == SCRATCH_FILE)) {
                F_report.report_writeErrorMsg(ERR_RDII_FILE_SCRATCH, L0);
            } else {
                F_report.report_writeErrorMsg(ERR_RDII_FILE_OPEN, F_swmm5.Frdii.name);
            }
            return;
        }
        F_swmm5.@ErrorCode = readRdiiTextFileHeader();
        if ((F_swmm5.ErrorCode != 0)) {
            F_report.report_writeErrorMsg(F_swmm5.ErrorCode, F_swmm5.Frdii.name);
        }
    }

    // rdii.c:497
    static void rdii_closeRdii() {
        if ((F_swmm5.Frdii.file != null)) {
            R.fclose(F_swmm5.Frdii.file);
        }
        if ((F_swmm5.Frdii.mode == SCRATCH_FILE)) {
            R.remove(F_swmm5.Frdii.name);
        }
        if ((RdiiNodeIndex != null)) {
            R.free(RdiiNodeIndex);
            RdiiNodeIndex = (null);
        }
        if ((RdiiNodeFlow != null)) {
            R.free(RdiiNodeFlow);
            RdiiNodeFlow = (null);
        }
    }

    // rdii.c:512
    static int rdii_getNumRdiiFlows(double aDate) {
        if ((NumRdiiNodes.v == 0)) {
            return 0;
        }
        if (!(F_swmm5.Frdii.file != null)) {
            return 0;
        }
        L_w1: while (!(R.feof(F_swmm5.Frdii.file) != 0)) {
            if ((RdiiStartDate.v == (-693594))) {
                return 0;
            }
            if ((aDate < RdiiStartDate.v)) {
                return 0;
            }
            if ((aDate < RdiiEndDate)) {
                return NumRdiiNodes.v;
            } else {
                readRdiiFlows();
            }
        }
        return 0;
    }

    // rdii.c:542
    static void rdii_getRdiiFlow(int i, IRef j, DRef q) {
        if (((i >= 0) && (i < NumRdiiNodes.v))) {
            j.set(RdiiNodeIndex[i]);
            q.set(RdiiNodeFlow[i]);
        }
    }

    // rdii.c:560
    static int readRdiiFileHeader() {
        int i = 0;
        int j = 0;
        R.fread1(RdiiStep, 4, F_swmm5.Frdii.file);
        if ((RdiiStep.v <= 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fread1(NumRdiiNodes, 4, F_swmm5.Frdii.file);
        if ((NumRdiiNodes.v <= 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        RdiiNodeIndex = new int[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeIndex != null)) {
            return ERR_MEMORY;
        }
        RdiiNodeFlow = new float[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeFlow != null)) {
            return ERR_MEMORY;
        }
        if ((R.feof(F_swmm5.Frdii.file) != 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fread(RdiiNodeIndex, 0, (int) (NumRdiiNodes.v), F_swmm5.Frdii.file);
        L_f1: for (i = 0; (i < NumRdiiNodes.v); i++) {
            j = RdiiNodeIndex[i];
            if ((F_swmm5.Node_[j].rdiiInflow == null)) {
                return ERR_RDII_FILE_FORMAT;
            }
        }
        if ((R.feof(F_swmm5.Frdii.file) != 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        return 0;
    }

    // rdii.c:595
    static int readRdiiTextFileHeader() {
        int i = 0;
        int j = 0;
        CPtr line = R.buf(1025);
        CPtr s1 = R.bufFrom(L0, 1025);
        CPtr s2 = R.buf(1025);
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        if (((!(R.sscanf(line, L169, s1) != 0)) || (R.strcmp(s1, L181) != 0))) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        RdiiStep.@v = 0;
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        if (((!(R.sscanf(line, L182, RdiiStep) != 0)) || (RdiiStep.v <= 0))) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        if ((R.sscanf(line, L44, s1, s2) < 2)) {
            return ERR_RDII_FILE_FORMAT;
        }
        RdiiFlowUnits = F_input.findmatch(s2, F_keywords.FlowUnitWords);
        if ((RdiiFlowUnits < 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        if (((R.sscanf(line, L182, NumRdiiNodes) < 1) || (NumRdiiNodes.v <= 0))) {
            return ERR_RDII_FILE_FORMAT;
        }
        RdiiNodeIndex = new int[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeIndex != null)) {
            return ERR_MEMORY;
        }
        RdiiNodeFlow = new float[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeFlow != null)) {
            return ERR_MEMORY;
        }
        L_f1: for (i = 0; (i < NumRdiiNodes.v); i++) {
            if ((R.feof(F_swmm5.Frdii.file) != 0)) {
                return ERR_RDII_FILE_FORMAT;
            }
            R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
            if (!(R.sscanf(line, L169, s1) != 0)) {
                return ERR_RDII_FILE_FORMAT;
            }
            j = F_project.project_findObject(NODE, s1);
            if ((j < 0)) {
                return ERR_RDII_FILE_FORMAT;
            }
            RdiiNodeIndex[i] = j;
        }
        if ((R.feof(F_swmm5.Frdii.file) != 0)) {
            return ERR_RDII_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
        return 0;
    }

    // rdii.c:663
    static void readRdiiFlows() {
        if ((RdiiFileType == TEXT)) {
            readRdiiTextFlows();
        } else {
            RdiiStartDate.@v = (-693594);
            RdiiEndDate = (-693594);
            if ((R.feof(F_swmm5.Frdii.file) != 0)) {
                return;
            }
            R.fread1(RdiiStartDate, 8, F_swmm5.Frdii.file);
            if ((RdiiStartDate.v == (-693594))) {
                return;
            }
            if ((R.fread(RdiiNodeFlow, 0, (int) (NumRdiiNodes.v), F_swmm5.Frdii.file) < NumRdiiNodes.v)) {
                RdiiStartDate.@v = (-693594);
            } else {
                RdiiEndDate = F_datetime.datetime_addSeconds(RdiiStartDate.v, RdiiStep.v);
            }
        }
    }

    // rdii.c:686
    static void readRdiiTextFlows() {
        int i = 0;
        int n = 0;
        IBox yr = new IBox(0);
        IBox mon = new IBox(0);
        IBox day = new IBox(0);
        IBox hr = new IBox(0);
        IBox min = new IBox(0);
        IBox sec = new IBox(0);
        DBox x = new DBox(0.0d);
        CPtr line = R.buf(1025);
        CPtr s = R.buf(1025);
        RdiiStartDate.@v = (-693594);
        L_f1: for (i = 0; (i < NumRdiiNodes.v); i++) {
            if ((R.feof(F_swmm5.Frdii.file) != 0)) {
                return;
            }
            R.fgets(line, (int) (1024), F_swmm5.Frdii.file);
            n = R.sscanf(line, L354, s, yr, mon, day, hr, min, sec, x);
            if ((n < 8)) {
                return;
            }
            RdiiNodeFlow[i] = ((float) (((x.v / F_swmm5.Qcf[RdiiFlowUnits]))));
        }
        RdiiStartDate.@v = (F_datetime.datetime_encodeDate(yr.v, mon.v, day.v) + F_datetime.datetime_encodeTime(hr.v, min.v, sec.v));
        RdiiEndDate = F_datetime.datetime_addSeconds(RdiiStartDate.v, RdiiStep.v);
    }

    // rdii.c:720
    static void createRdiiFile() {
        int hasRdii = 0;
        double elapsedTime = 0.0d;
        double duration = 0.0d;
        double currentDate = 0.0d;
        RdiiStep.@v = F_swmm5.WetStep;
        NumRdiiNodes.@v = getNumRdiiNodes();
        if ((NumRdiiNodes.v == 0)) {
            F_swmm5.Frdii.@mode = NO_FILE;
            return;
        } else if ((F_swmm5.Frdii.mode == NO_FILE)) {
            F_swmm5.Frdii.@mode = SCRATCH_FILE;
        }
        validateRdii();
        initGageData();
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        openRdiiProcessor();
        if (!(F_swmm5.ErrorCode != 0)) {
            initUnitHydData();
            duration = (F_swmm5.TotalDuration / 1000.0d);
            elapsedTime = 0.0d;
            L_w1: while (((elapsedTime <= duration) && (!(F_swmm5.ErrorCode != 0)))) {
                currentDate = (F_swmm5.StartDateTime + (elapsedTime / 86400));
                getRainfall(currentDate);
                getUnitHydRdii(currentDate);
                hasRdii = getNodeRdii();
                if ((hasRdii != 0)) {
                    saveRdiiFlows(currentDate);
                }
                elapsedTime += RdiiStep.v;
            }
        }
        closeRdiiProcessor();
    }

    // rdii.c:793
    static int getNumRdiiNodes() {
        int j = 0;
        int n = 0;
        n = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].rdiiInflow != null)) {
                n++;
            }
        }
        return n;
    }

    // rdii.c:813
    static void validateRdii() {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        double rsum = 0.0d;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[UNITHYD]); j++) {
            L_f2: for (m = 0; (m < 12); m++) {
                rsum = 0.0d;
                L_f3: for (k = 0; (k < 3); k++) {
                    if ((F_swmm5.UnitHyd[j].tBase[m][k] == 0)) {
                        continue L_f3;
                    }
                    if ((F_swmm5.UnitHyd[j].tPeak[m][k] < 0.0d)) {
                        F_report.report_writeErrorMsg(ERR_UNITHYD_TIMES, F_swmm5.UnitHyd[j].ID);
                    }
                    if ((F_swmm5.UnitHyd[j].r[m][k] < 0.0d)) {
                        F_report.report_writeErrorMsg(ERR_UNITHYD_RATIOS, F_swmm5.UnitHyd[j].ID);
                    } else {
                        rsum += F_swmm5.UnitHyd[j].r[m][k];
                    }
                }
                if (R.dgt(rsum, 1.01d)) {
                    F_report.report_writeErrorMsg(ERR_UNITHYD_RATIOS, F_swmm5.UnitHyd[j].ID);
                }
            }
        }
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].rdiiInflow != null)) {
                if ((F_swmm5.Node_[i].rdiiInflow.area < 0.0d)) {
                    F_report.report_writeErrorMsg(ERR_RDII_AREA, F_swmm5.Node_[i].ID);
                }
            }
        }
    }

    // rdii.c:877
    static void openRdiiProcessor() {
        int j = 0;
        int n = 0;
        UHGroup = (null);
        RdiiNodeIndex = (null);
        RdiiNodeFlow = (null);
        TotalRainVol = 0.0d;
        TotalRdiiVol = 0.0d;
        if (!(allocRdiiMemory() != 0)) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            return;
        }
        if (!(openNewRdiiFile() != 0)) {
            F_report.report_writeErrorMsg(ERR_RDII_FILE_SCRATCH, L0);
            return;
        }
        n = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if (((F_swmm5.Node_[j].rdiiInflow != null) && (RdiiNodeIndex != null))) {
                RdiiNodeIndex[n] = j;
                n++;
            }
        }
    }

    // rdii.c:922
    static int allocRdiiMemory() {
        int i = 0;
        int k = 0;
        int n = 0;
        UHGroup = R.fill(new TUHGroup[(int) (F_swmm5.Nobjects[UNITHYD])], (() -> new TUHGroup()));
        if (!(UHGroup != null)) {
            return 0;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[UNITHYD]); i++) {
            UHGroup[i].@rainInterval = getRainInterval(i);
            L_f2: for (k = 0; (k < 3); k++) {
                UHGroup[i].uh[k].@pastRain = (null);
                UHGroup[i].uh[k].@pastMonth = null;
                UHGroup[i].uh[k].@maxPeriods = getMaxPeriods(i, k);
                n = UHGroup[i].uh[k].maxPeriods;
                if ((n > 0)) {
                    UHGroup[i].uh[k].@pastRain = new double[(int) (n)];
                    if (!(UHGroup[i].uh[k].pastRain != null)) {
                        return 0;
                    }
                    UHGroup[i].uh[k].@pastMonth = R.buf((int) (n));
                    if (!(UHGroup[i].uh[k].pastMonth != null)) {
                        return 0;
                    }
                }
            }
        }
        RdiiNodeIndex = new int[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeIndex != null)) {
            return 0;
        }
        RdiiNodeFlow = new float[(int) (NumRdiiNodes.v)];
        if (!(RdiiNodeFlow != null)) {
            return 0;
        }
        return 1;
    }

    // rdii.c:970
    static int getRainInterval(int i) {
        int ri = 0;
        int tLimb = 0;
        int k = 0;
        int m = 0;
        ri = F_swmm5.WetStep;
        L_f1: for (m = 0; (m < 12); m++) {
            L_f2: for (k = 0; (k < 3); k++) {
                if ((F_swmm5.UnitHyd[i].tPeak[m][k] > 0)) {
                    tLimb = (int) (F_swmm5.UnitHyd[i].tPeak[m][k]);
                    ri = (((((ri) <= (tLimb))) ? (ri) : (tLimb)));
                    tLimb = (int) ((F_swmm5.UnitHyd[i].tBase[m][k] - tLimb));
                    if ((tLimb > 0)) {
                        ri = (((((ri) <= (tLimb))) ? (ri) : (tLimb)));
                    }
                }
            }
        }
        return ri;
    }

    // rdii.c:1005
    static int getMaxPeriods(int i, int k) {
        int m = 0;
        int n = 0;
        int nMax = 0;
        int rainInterval = 0;
        rainInterval = UHGroup[i].rainInterval;
        nMax = 0;
        L_f1: for (m = 0; (m < 12); m++) {
            n = (int) (((R.ldiv(F_swmm5.UnitHyd[i].tBase[m][k], rainInterval)) + 1));
            nMax = (((((n) >= (nMax))) ? (n) : (nMax)));
        }
        return nMax;
    }

    // rdii.c:1034
    static void initGageData() {
        int i = 0;
        int g = 0;
        L_f1: for (g = 0; (g < F_swmm5.Nobjects[GAGE]); g++) {
            if ((F_swmm5.Gage[g].tSeries >= 0)) {
                F_table.table_tseriesInit(F_swmm5.Tseries[F_swmm5.Gage[g].tSeries]);
            }
            F_gage.gage_initState(g);
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[UNITHYD]); i++) {
            g = F_swmm5.UnitHyd[i].rainGage;
            if ((g >= 0)) {
                if ((F_swmm5.Gage[g].coGage >= 0)) {
                    F_swmm5.UnitHyd[i].@rainGage = F_swmm5.Gage[g].coGage;
                }
            }
        }
    }

    // rdii.c:1072
    static void initUnitHydData() {
        int i = 0;
        int j = 0;
        int k = 0;
        int n = 0;
        int month = 0;
        month = (F_datetime.datetime_monthOfYear(F_swmm5.StartDateTime) - 1);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[UNITHYD]); i++) {
            L_f2: for (k = 0; (k < 3); k++) {
                UHGroup[i].uh[k].@drySeconds = (((UHGroup[i].uh[k].maxPeriods * UHGroup[i].rainInterval)) + 1);
                UHGroup[i].uh[k].@period = (UHGroup[i].uh[k].maxPeriods + 1);
                UHGroup[i].uh[k].@hasPastRain = 0;
                UHGroup[i].uh[k].@iaUsed = F_swmm5.UnitHyd[i].iaInit[month][k];
            }
            UHGroup[i].@gageDate = F_swmm5.StartDateTime;
            UHGroup[i].@area = 0.0d;
            UHGroup[i].@rdii = 0.0d;
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[UNITHYD]); i++) {
            UHGroup[i].@isUsed = 0;
        }
        L_f4: for (n = 0; (n < NumRdiiNodes.v); n++) {
            j = RdiiNodeIndex[n];
            i = F_swmm5.Node_[j].rdiiInflow.unitHyd;
            UHGroup[i].@isUsed = 1;
            UHGroup[i].@lastDate = F_swmm5.StartDateTime;
            UHGroup[i].@area = (UHGroup[i].area + F_swmm5.Node_[j].rdiiInflow.area);
        }
    }

    // rdii.c:1128
    static int openNewRdiiFile() {
        IBox j = new IBox(0);
        if ((F_swmm5.Frdii.mode == SCRATCH_FILE)) {
            Overrides.getTempFileName(F_swmm5.Frdii.name);
        }
        F_swmm5.Frdii.@file = R.fopen(F_swmm5.Frdii.name, L166);
        if ((F_swmm5.Frdii.file == null)) {
            return 0;
        }
        R.fwrite(FileStamp, 0, (int) (R.strlen(FileStamp)), F_swmm5.Frdii.file);
        R.fwrite1(RdiiStep.v, 4, F_swmm5.Frdii.file);
        R.fwrite1(NumRdiiNodes.v, 4, F_swmm5.Frdii.file);
        L_f1: for (j.@v = 0; (j.v < F_swmm5.Nobjects[NODE]); j.@v = j.v + 1) {
            if ((F_swmm5.Node_[j.v].rdiiInflow != null)) {
                R.fwrite1(j.v, 4, F_swmm5.Frdii.file);
            }
        }
        return 1;
    }

    // rdii.c:1163
    static void getRainfall(double currentDate) {
        int j = 0;
        int k = 0;
        int g = 0;
        int i = 0;
        int month = 0;
        int rainInterval = 0;
        double rainDepth = 0.0d;
        double excessDepth = 0.0d;
        double gageDate = 0.0d;
        month = (F_datetime.datetime_monthOfYear(currentDate) - 1);
        L_f1: for (g = 0; (g < F_swmm5.Nobjects[GAGE]); g++) {
            F_swmm5.Gage[g].@isCurrent = 0;
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[UNITHYD]); j++) {
            g = F_swmm5.UnitHyd[j].rainGage;
            rainInterval = UHGroup[j].rainInterval;
            L_w3: while ((UHGroup[j].gageDate < currentDate)) {
                gageDate = UHGroup[j].gageDate;
                F_swmm5.Adjust.@rainFactor = F_swmm5.Adjust.rain[(F_datetime.datetime_monthOfYear(gageDate) - 1)];
                if (!(F_swmm5.Gage[g].isCurrent != 0)) {
                    F_gage.gage_setState(g, gageDate);
                    F_swmm5.Gage[g].@isCurrent = 1;
                }
                rainDepth = ((F_swmm5.Gage[g].rainfall * ((double) (rainInterval))) / 3600.0d);
                TotalRainVol += ((rainDepth / F_swmm5.UCF(RAINDEPTH)) * UHGroup[j].area);
                L_f4: for (k = 0; (k < 3); k++) {
                    excessDepth = applyIA(j, k, gageDate, rainInterval, rainDepth);
                    updateDryPeriod(j, k, excessDepth, rainInterval);
                    i = UHGroup[j].uh[k].period;
                    if ((i >= UHGroup[j].uh[k].maxPeriods)) {
                        i = 0;
                    }
                    UHGroup[j].uh[k].pastRain[i] = excessDepth;
                    UHGroup[j].uh[k].pastMonth.s(i, ((byte) (month)));
                    UHGroup[j].uh[k].@period = (i + 1);
                }
                UHGroup[j].@gageDate = F_datetime.datetime_addSeconds(gageDate, rainInterval);
            }
        }
    }

    // rdii.c:1231
    static double applyIA(int j, int k, double aDate, double dt, double rainDepth) {
        int m = 0;
        double ia = 0.0d;
        double netRainDepth = 0.0d;
        m = (F_datetime.datetime_monthOfYear(aDate) - 1);
        ia = (F_swmm5.UnitHyd[j].iaMax[m][k] - UHGroup[j].uh[k].iaUsed);
        ia = (((R.dge((ia), (0.0d))) ? (ia) : (0.0d)));
        if (R.dgt(rainDepth, 0.0d)) {
            netRainDepth = (rainDepth - ia);
            netRainDepth = (((R.dge((netRainDepth), (0.0d))) ? (netRainDepth) : (0.0d)));
            ia = (rainDepth - netRainDepth);
            UHGroup[j].uh[k].@iaUsed = (UHGroup[j].uh[k].iaUsed + ia);
        } else {
            UHGroup[j].uh[k].@iaUsed = (UHGroup[j].uh[k].iaUsed - ((dt / 86400.0d) * F_swmm5.UnitHyd[j].iaRecov[m][k]));
            UHGroup[j].uh[k].@iaUsed = (((R.dge((UHGroup[j].uh[k].iaUsed), (0.0d))) ? (UHGroup[j].uh[k].iaUsed) : (0.0d)));
            netRainDepth = 0.0d;
        }
        return netRainDepth;
    }

    // rdii.c:1276
    static void updateDryPeriod(int j, int k, double rainDepth, int rainInterval) {
        int i = 0;
        if (R.dgt(rainDepth, 0.0d)) {
            if ((UHGroup[j].uh[k].drySeconds >= (rainInterval * UHGroup[j].uh[k].maxPeriods))) {
                L_f1: for (i = 0; (i < UHGroup[j].uh[k].maxPeriods); i++) {
                    UHGroup[j].uh[k].pastRain[i] = 0.0d;
                }
                UHGroup[j].uh[k].@period = 0;
            }
            UHGroup[j].uh[k].@drySeconds = 0;
            UHGroup[j].uh[k].@hasPastRain = 1;
        } else {
            UHGroup[j].uh[k].@drySeconds = (UHGroup[j].uh[k].drySeconds + rainInterval);
            if ((UHGroup[j].uh[k].drySeconds >= (rainInterval * UHGroup[j].uh[k].maxPeriods))) {
                UHGroup[j].uh[k].@hasPastRain = 0;
            } else {
                UHGroup[j].uh[k].@hasPastRain = 1;
            }
        }
    }

    // rdii.c:1321
    static void getUnitHydRdii(double currentDate) {
        int j = 0;
        int k = 0;
        int rainInterval = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[UNITHYD]); j++) {
            if (!(UHGroup[j].isUsed != 0)) {
                continue L_f1;
            }
            if ((currentDate < UHGroup[j].lastDate)) {
                continue L_f1;
            }
            UHGroup[j].@lastDate = UHGroup[j].gageDate;
            rainInterval = UHGroup[j].rainInterval;
            UHGroup[j].@rdii = 0.0d;
            L_f2: for (k = 0; (k < 3); k++) {
                if ((UHGroup[j].uh[k].hasPastRain != 0)) {
                    UHGroup[j].@rdii = (UHGroup[j].rdii + (getUnitHydConvol(j, k, rainInterval)));
                }
            }
        }
    }

    // rdii.c:1358
    static double getUnitHydConvol(int j, int k, int rainInterval) {
        int i = 0;
        int m = 0;
        int p = 0;
        int pMax = 0;
        double t = 0.0d;
        double u = 0.0d;
        double v = 0.0d;
        double rdii = 0.0d;
        TUHData uh = null;
        rdii = 0.0d;
        uh = UHGroup[j].uh[k];
        i = (uh.period - 1);
        if ((i < 0)) {
            i = (uh.maxPeriods - 1);
        }
        pMax = uh.maxPeriods;
        p = 1;
        L_w1: while ((p < pMax)) {
            v = uh.pastRain[i];
            m = uh.pastMonth.g(i);
            if (R.dgt(v, 0.0d)) {
                t = (((((double) ((p))) - 0.5d)) * ((double) (rainInterval)));
                u = (getUnitHydOrd(j, m, k, t) * F_swmm5.UnitHyd[j].r[m][k]);
                rdii += (u * v);
            }
            p = (p + 1);
            i = (i - 1);
            if ((i < 0)) {
                i = (uh.maxPeriods - 1);
            }
        }
        return rdii;
    }

    // rdii.c:1411
    static double getUnitHydOrd(int h, int m, int k, double t) {
        double qPeak = 0.0d;
        double f = 0.0d;
        double t1 = 0.0d;
        double t2 = 0.0d;
        double tBase = 0.0d;
        tBase = F_swmm5.UnitHyd[h].tBase[m][k];
        if (R.dge(t, tBase)) {
            return 0.0d;
        }
        qPeak = ((2.0d / tBase) * 3600.0d);
        t1 = F_swmm5.UnitHyd[h].tPeak[m][k];
        t2 = (tBase - t1);
        if ((t <= t1)) {
            f = (t / t1);
        } else {
            f = (1.0d - (((t - t1)) / t2));
        }
        return ((((R.dge((f), (0.0d))) ? (f) : (0.0d))) * qPeak);
    }

    // rdii.c:1446
    static int getNodeRdii() {
        int hasRdii = 0;
        int i = 0;
        int j = 0;
        int n = 0;
        double rdii = 0.0d;
        L_f1: for (n = 0; (n < NumRdiiNodes.v); n++) {
            j = RdiiNodeIndex[n];
            i = F_swmm5.Node_[j].rdiiInflow.unitHyd;
            rdii = ((UHGroup[i].rdii * F_swmm5.Node_[j].rdiiInflow.area) / F_swmm5.UCF(RAINFALL));
            if ((rdii < ZERO_RDII)) {
                rdii = 0.0d;
            } else {
                hasRdii = 1;
            }
            RdiiNodeFlow[n] = ((float) (rdii));
            if (R.dgt(rdii, 0.0d)) {
                TotalRdiiVol += (rdii * ((double) (RdiiStep.v)));
            }
        }
        return hasRdii;
    }

    // rdii.c:1483
    static void saveRdiiFlows(double __currentDate) {
        DBox currentDate = new DBox(__currentDate);
        R.fwrite1(currentDate.v, 8, F_swmm5.Frdii.file);
        R.fwrite(RdiiNodeFlow, 0, (int) (NumRdiiNodes.v), F_swmm5.Frdii.file);
    }

    // rdii.c:1496
    static void closeRdiiProcessor() {
        if (!(F_swmm5.ErrorCode != 0)) {
            F_report.report_writeRdiiStats(TotalRainVol, TotalRdiiVol);
        }
        freeRdiiMemory();
        if ((F_swmm5.Frdii.file != null)) {
            R.fclose(F_swmm5.Frdii.file);
        }
    }

    // rdii.c:1516
    static void freeRdiiMemory() {
        int i = 0;
        int k = 0;
        if ((UHGroup != null)) {
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[UNITHYD]); i++) {
                L_f2: for (k = 0; (k < 3); k++) {
                    if ((UHGroup[i].uh[k].pastRain != null)) {
                        R.free(UHGroup[i].uh[k].pastRain);
                        UHGroup[i].uh[k].@pastRain = (null);
                    }
                    if ((UHGroup[i].uh[k].pastMonth != null)) {
                        R.free(UHGroup[i].uh[k].pastMonth);
                        UHGroup[i].uh[k].@pastMonth = null;
                    }
                }
            }
            if ((UHGroup != null)) {
                R.free(UHGroup);
                UHGroup = (null);
            }
        }
        if ((RdiiNodeIndex != null)) {
            R.free(RdiiNodeIndex);
            RdiiNodeIndex = (null);
        }
        if ((RdiiNodeFlow != null)) {
            R.free(RdiiNodeFlow);
            RdiiNodeFlow = (null);
        }
    }

}
