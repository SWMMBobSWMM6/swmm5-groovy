// Generated from EPA SWMM 5.2.4 (iface.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_iface {
    public static int IfaceFlowUnits = 0;
    public static IBox IfaceStep = new IBox(0);
    public static IBox NumIfacePolluts = new IBox(0);
    public static int[] IfacePolluts = null;
    public static IBox NumIfaceNodes = new IBox(0);
    public static int[] IfaceNodes = null;
    public static double[][] OldIfaceValues = null;
    public static double[][] NewIfaceValues = null;
    public static double IfaceFrac = 0.0d;
    public static double OldIfaceDate = 0.0d;
    public static double NewIfaceDate = 0.0d;

    // iface.c:66
    static int iface_readFileParams(CPtr[] tok, int ntoks) {
        int k = 0;
        int j = 0;
        CPtr fname = R.buf(260);
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        k = ((byte) (F_input.findmatch(tok[0], F_keywords.FileModeWords)));
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        j = F_input.findmatch(tok[1], F_keywords.FileTypeWords);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        if ((ntoks < 3)) {
            return 0;
        }
        Overrides.sstrncpy(fname, tok[2], 259);
        int __sw1 = j;
        if (__sw1 == RAINFALL_FILE) {
            F_swmm5.Frain.@mode = k;
            Overrides.sstrncpy(F_swmm5.Frain.name, F_swmm5.addAbsolutePath(fname), 259);
        } else if (__sw1 == RUNOFF_FILE) {
            F_swmm5.Frunoff.@mode = k;
            Overrides.sstrncpy(F_swmm5.Frunoff.name, F_swmm5.addAbsolutePath(fname), 259);
        } else if (__sw1 == HOTSTART_FILE) {
            if ((k == USE_FILE)) {
                F_swmm5.Fhotstart1.@mode = k;
                Overrides.sstrncpy(F_swmm5.Fhotstart1.name, F_swmm5.addAbsolutePath(fname), 259);
            } else if ((k == SAVE_FILE)) {
                F_swmm5.Fhotstart2.@mode = k;
                Overrides.sstrncpy(F_swmm5.Fhotstart2.name, F_swmm5.addAbsolutePath(fname), 259);
            }
        } else if (__sw1 == RDII_FILE) {
            F_swmm5.Frdii.@mode = k;
            Overrides.sstrncpy(F_swmm5.Frdii.name, fname, 259);
        } else if (__sw1 == INFLOWS_FILE) {
            if ((k != USE_FILE)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            F_swmm5.Finflows.@mode = k;
            Overrides.sstrncpy(F_swmm5.Finflows.name, F_swmm5.addAbsolutePath(fname), 259);
        } else if (__sw1 == OUTFLOWS_FILE) {
            if ((k != SAVE_FILE)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            F_swmm5.Foutflows.@mode = k;
            Overrides.sstrncpy(F_swmm5.Foutflows.name, F_swmm5.addAbsolutePath(fname), 259);
        }
        return 0;
    }

    // iface.c:138
    static void iface_openRoutingFiles() {
        NumIfacePolluts.@v = 0;
        IfacePolluts = (null);
        NumIfaceNodes.@v = 0;
        IfaceNodes = (null);
        OldIfaceValues = (null);
        NewIfaceValues = (null);
        if (((F_swmm5.Foutflows.mode != NO_FILE) && (F_swmm5.Finflows.mode != NO_FILE))) {
            if ((F_swmm5.strcomp(F_swmm5.Foutflows.name, F_swmm5.Finflows.name) != 0)) {
                F_report.report_writeErrorMsg(ERR_ROUTING_FILE_NAMES, L0);
                return;
            }
        }
        if ((F_swmm5.Foutflows.mode == SAVE_FILE)) {
            openFileForOutput();
        }
        if ((F_swmm5.Finflows.mode == USE_FILE)) {
            openFileForInput();
        }
    }

    // iface.c:170
    static void iface_closeRoutingFiles() {
        if ((IfacePolluts != null)) {
            R.free(IfacePolluts);
            IfacePolluts = (null);
        }
        if ((IfaceNodes != null)) {
            R.free(IfaceNodes);
            IfaceNodes = (null);
        }
        if ((OldIfaceValues != null)) {
            Overrides.project_freeMatrix(OldIfaceValues);
        }
        if ((NewIfaceValues != null)) {
            Overrides.project_freeMatrix(NewIfaceValues);
        }
        if ((F_swmm5.Finflows.file != null)) {
            R.fclose(F_swmm5.Finflows.file);
        }
        if ((F_swmm5.Foutflows.file != null)) {
            R.fclose(F_swmm5.Foutflows.file);
        }
    }

    // iface.c:187
    static int iface_getNumIfaceNodes(double currentDate) {
        if (R.dgt(OldIfaceDate, currentDate)) {
            return 0;
        }
        L_w1: while (((NewIfaceDate < currentDate) && (NewIfaceDate != (-693594)))) {
            setOldIfaceValues();
            readNewIfaceValues();
        }
        if ((NewIfaceDate == (-693594))) {
            return 0;
        }
        IfaceFrac = (((currentDate - OldIfaceDate)) / ((NewIfaceDate - OldIfaceDate)));
        IfaceFrac = (((R.dge((0.0d), (IfaceFrac))) ? (0.0d) : (IfaceFrac)));
        IfaceFrac = (((((IfaceFrac) <= (1.0d))) ? (IfaceFrac) : (1.0d)));
        return NumIfaceNodes.v;
    }

    // iface.c:219
    static int iface_getIfaceNode(int index) {
        if (((index >= 0) && (index < NumIfaceNodes.v))) {
            return IfaceNodes[index];
        } else {
            return (-1);
        }
        throw new IllegalStateException('iface_getIfaceNode: no return');
    }

    // iface.c:232
    static double iface_getIfaceFlow(int index) {
        double q1 = 0.0d;
        double q2 = 0.0d;
        if (((index >= 0) && (index < NumIfaceNodes.v))) {
            q1 = OldIfaceValues[index][0];
            q2 = NewIfaceValues[index][0];
            return ((((1.0d - IfaceFrac)) * q1) + (IfaceFrac * q2));
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('iface_getIfaceFlow: no return');
    }

    // iface.c:253
    static double iface_getIfaceQual(int index, int pollut) {
        int j = 0;
        double c1 = 0.0d;
        double c2 = 0.0d;
        if (((index >= 0) && (index < NumIfaceNodes.v))) {
            j = IfacePolluts[pollut];
            if ((j < 0)) {
                return 0.0d;
            }
            c1 = OldIfaceValues[index][(j + 1)];
            c2 = NewIfaceValues[index][(j + 1)];
            return ((((1.0d - IfaceFrac)) * c1) + (IfaceFrac * c2));
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('iface_getIfaceQual: no return');
    }

    // iface.c:281
    static void iface_saveOutletResults(double reportDate, CFile file) {
        int i = 0;
        int p = 0;
        IBox yr = new IBox(0);
        IBox mon = new IBox(0);
        IBox day = new IBox(0);
        IBox hr = new IBox(0);
        IBox min = new IBox(0);
        IBox sec = new IBox(0);
        CPtr theDate = R.buf(26);
        F_datetime.datetime_decodeDate(reportDate, yr, mon, day);
        F_datetime.datetime_decodeTime(reportDate, hr, min, sec);
        R.snprintf(theDate, (int) (26), L167, yr.v, mon.v, day.v, hr.v, min.v, sec.v);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if (!(isOutletNode(i) != 0)) {
                continue L_f1;
            }
            R.fprintf(file, L168, F_swmm5.Node_[i].ID);
            R.fprintf(file, L169, theDate);
            R.fprintf(file, L170, (F_swmm5.Node_[i].inflow * F_swmm5.UCF(FLOW)));
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(file, L170, F_swmm5.Node_[i].newQual[p]);
            }
        }
    }

    // iface.c:313
    static void openFileForOutput() {
        int i = 0;
        int n = 0;
        F_swmm5.Foutflows.@file = R.fopen(F_swmm5.Foutflows.name, L171);
        if ((F_swmm5.Foutflows.file == null)) {
            F_report.report_writeErrorMsg(ERR_ROUTING_FILE_OPEN, F_swmm5.Foutflows.name);
            return;
        }
        R.fprintf(F_swmm5.Foutflows.file, L172);
        R.fprintf(F_swmm5.Foutflows.file, L173, F_swmm5.Title[0]);
        R.fprintf(F_swmm5.Foutflows.file, L174, F_swmm5.ReportStep);
        R.fprintf(F_swmm5.Foutflows.file, L175, (F_swmm5.Nobjects[POLLUT] + 1));
        R.fprintf(F_swmm5.Foutflows.file, L176, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
            R.fprintf(F_swmm5.Foutflows.file, L177, F_swmm5.Pollut[i].ID, F_keywords.QualUnitsWords[F_swmm5.Pollut[i].units]);
        }
        n = 0;
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((isOutletNode(i) != 0)) {
                n++;
            }
        }
        R.fprintf(F_swmm5.Foutflows.file, L178, n);
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((isOutletNode(i) != 0)) {
                R.fprintf(F_swmm5.Foutflows.file, L173, F_swmm5.Node_[i].ID);
            }
        }
        R.fprintf(F_swmm5.Foutflows.file, L179);
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
            R.fprintf(F_swmm5.Foutflows.file, L180, F_swmm5.Pollut[i].ID);
        }
        if ((F_swmm5.ReportStart == F_swmm5.StartDateTime)) {
            iface_saveOutletResults(F_swmm5.ReportStart, F_swmm5.Foutflows.file);
        }
    }

    // iface.c:377
    static void openFileForInput() {
        int err = 0;
        CPtr line = R.buf(1025);
        CPtr s = R.buf(1025);
        F_swmm5.Finflows.@file = R.fopen(F_swmm5.Finflows.name, L13);
        if ((F_swmm5.Finflows.file == null)) {
            F_report.report_writeErrorMsg(ERR_ROUTING_FILE_OPEN, F_swmm5.Finflows.name);
            return;
        }
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        if (((!(R.sscanf(line, L169, s) != 0)) || (!(F_swmm5.strcomp(s, L181) != 0)))) {
            F_report.report_writeErrorMsg(ERR_ROUTING_FILE_FORMAT, F_swmm5.Finflows.name);
            return;
        }
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        IfaceStep.@v = 0;
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        if (((!(R.sscanf(line, L182, IfaceStep) != 0)) || (IfaceStep.v <= 0))) {
            F_report.report_writeErrorMsg(ERR_ROUTING_FILE_FORMAT, F_swmm5.Finflows.name);
            return;
        }
        err = getIfaceFilePolluts();
        if ((err > 0)) {
            F_report.report_writeErrorMsg(err, F_swmm5.Finflows.name);
            return;
        }
        err = getIfaceFileNodes();
        if ((err > 0)) {
            F_report.report_writeErrorMsg(err, F_swmm5.Finflows.name);
            return;
        }
        OldIfaceValues = Overrides.project_createMatrix(NumIfaceNodes.v, (1 + NumIfacePolluts.v));
        NewIfaceValues = Overrides.project_createMatrix(NumIfaceNodes.v, (1 + NumIfacePolluts.v));
        if (((OldIfaceValues == null) || (NewIfaceValues == null))) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            return;
        }
        readNewIfaceValues();
        OldIfaceDate = NewIfaceDate;
    }

    // iface.c:450
    static int getIfaceFilePolluts() {
        int i = 0;
        int j = 0;
        CPtr line = R.buf(1025);
        CPtr s1 = R.buf(1025);
        CPtr s2 = R.buf(1025);
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        NumIfacePolluts.@v = (-1);
        if ((R.sscanf(line, L182, NumIfacePolluts) != 0)) {
            NumIfacePolluts.@v = NumIfacePolluts.v - 1;
        }
        if ((NumIfacePolluts.v < 0)) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        if ((R.sscanf(line, L44, s1, s2) < 2)) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        if (!(F_swmm5.strcomp(s1, L183) != 0)) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        IfaceFlowUnits = F_input.findmatch(s2, F_keywords.FlowUnitWords);
        if ((IfaceFlowUnits < 0)) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        if ((F_swmm5.Nobjects[POLLUT] > 0)) {
            IfacePolluts = new int[(int) (F_swmm5.Nobjects[POLLUT])];
            if (!(IfacePolluts != null)) {
                return ERR_MEMORY;
            }
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                IfacePolluts[i] = (-1);
            }
        }
        if (((NumIfacePolluts.v > 0) && (F_swmm5.Nobjects[POLLUT] > 0))) {
            L_f2: for (i = 0; (i < NumIfacePolluts.v); i++) {
                if ((R.feof(F_swmm5.Finflows.file) != 0)) {
                    return ERR_ROUTING_FILE_FORMAT;
                }
                R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
                if ((R.sscanf(line, L44, s1, s2) < 2)) {
                    return ERR_ROUTING_FILE_FORMAT;
                }
                j = F_project.project_findObject(POLLUT, s1);
                if ((j < 0)) {
                    continue L_f2;
                }
                if (!(F_swmm5.strcomp(s2, F_keywords.QualUnitsWords[F_swmm5.Pollut[j].units]) != 0)) {
                    return ERR_ROUTING_FILE_NOMATCH;
                }
                IfacePolluts[j] = i;
            }
        }
        return 0;
    }

    // iface.c:510
    static int getIfaceFileNodes() {
        int i = 0;
        CPtr line = R.buf(1025);
        CPtr s = R.buf(1025);
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        if (((!(R.sscanf(line, L182, NumIfaceNodes) != 0)) || (NumIfaceNodes.v <= 0))) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        IfaceNodes = new int[(int) (NumIfaceNodes.v)];
        if (!(IfaceNodes != null)) {
            return ERR_MEMORY;
        }
        L_f1: for (i = 0; (i < NumIfaceNodes.v); i++) {
            if ((R.feof(F_swmm5.Finflows.file) != 0)) {
                return ERR_ROUTING_FILE_FORMAT;
            }
            IfaceNodes[i] = 0;
            R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
            if (!(R.sscanf(line, L169, s) != 0)) {
                return ERR_ROUTING_FILE_FORMAT;
            }
            IfaceNodes[i] = F_project.project_findObject(NODE, s);
        }
        if ((R.feof(F_swmm5.Finflows.file) != 0)) {
            return ERR_ROUTING_FILE_FORMAT;
        }
        R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
        return 0;
    }

    // iface.c:550
    static void readNewIfaceValues() {
        int i = 0;
        int j = 0;
        CPtr s = null;
        int yr = 0;
        int mon = 0;
        int day = 0;
        int hr = 0;
        int min = 0;
        int sec = 0;
        CPtr line = R.buf(1025);
        NewIfaceDate = (-693594);
        L_f1: for (i = 0; (i < NumIfaceNodes.v); i++) {
            if ((R.feof(F_swmm5.Finflows.file) != 0)) {
                return;
            }
            R.fgets(line, (int) (1024), F_swmm5.Finflows.file);
            if (((R.strtok(line, L184)) == null)) {
                return;
            }
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            yr = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            mon = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            day = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            hr = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            min = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            sec = R.atoi(s);
            s = R.strtok(null, L184);
            if ((s == null)) {
                return;
            }
            NewIfaceValues[i][0] = (R.atof(s) / F_swmm5.Qcf[IfaceFlowUnits]);
            L_f2: for (j = 1; (j <= NumIfacePolluts.v); j++) {
                s = R.strtok(null, L184);
                if ((s == null)) {
                    return;
                }
                NewIfaceValues[i][j] = R.atof(s);
            }
        }
        NewIfaceDate = (F_datetime.datetime_encodeDate(yr, mon, day) + F_datetime.datetime_encodeTime(hr, min, sec));
    }

    // iface.c:613
    static void setOldIfaceValues() {
        int i = 0;
        int j = 0;
        OldIfaceDate = NewIfaceDate;
        L_f1: for (i = 0; (i < NumIfaceNodes.v); i++) {
            L_f2: for (j = 0; (j < (NumIfacePolluts.v + 1)); j++) {
                OldIfaceValues[i][j] = NewIfaceValues[i][j];
            }
        }
    }

    // iface.c:633
    static int isOutletNode(int i) {
        if ((F_swmm5.RouteModel == DW)) {
            return (((F_swmm5.Node_[i].type == OUTFALL)) ? 1 : 0);
        } else {
            return (((F_swmm5.Node_[i].degree == 0)) ? 1 : 0);
        }
        throw new IllegalStateException('isOutletNode: no return');
    }

}
