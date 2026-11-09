// Generated from EPA SWMM 5.2.4 (lid.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_lid {
    public static CPtr[] LidLayerWords = new CPtr[] { L991, L992, L742, L993, L994, L995, L996, null };
    public static CPtr[] LidTypeWords = new CPtr[] { L997, L998, L953, L999, L1000, L1001, L1002, L1003, null };
    public static TLidProc[] LidProcs = null;
    public static int LidCount = 0;
    public static LidGroup[] LidGroups = null;
    public static int GroupCount = 0;
    public static double lid_EvapRate = 0.0d;
    public static double NativeInfil = 0.0d;
    public static double lid_MaxNativeInfil = 0.0d;
    public static int initLidRptFile_colCount = 14;
    public static CPtr[] initLidRptFile_head1 = new CPtr[] { L1022, L1023, L1024, L1024, L1025, L1026, L1027, L1028, L1025, L1029, L1025, L1026, L1027, L1030, null };
    public static CPtr[] initLidRptFile_head2 = new CPtr[] { L1022, L1031, L1032, L1033, L1034, L1035, L1035, L1036, L1037, L1038, L1039, L1039, L1040, L1041, null };
    public static CPtr[] initLidRptFile_units1 = new CPtr[] { L1042, L1043, L1044, L1044, L1044, L1044, L1044, L1044, L1044, L1044, L1045, L1045, L1046, L1047, null };
    public static CPtr[] initLidRptFile_units2 = new CPtr[] { L1042, L1043, L1048, L1048, L1048, L1048, L1048, L1048, L1048, L1048, L1049, L1049, L1046, L1050, null };
    public static CPtr initLidRptFile_line9 = R.bufFrom(L1051, 11);

    // lid.c:237
    static void lid_create(int lidCount, int subcatchCount) {
        int j = 0;
        LidProcs = (null);
        LidGroups = (null);
        LidCount = lidCount;
        GroupCount = subcatchCount;
        if ((GroupCount > 0)) {
            LidGroups = new LidGroup[(int) (GroupCount)];
            if ((LidGroups == null)) {
                F_swmm5.@ErrorCode = ERR_MEMORY;
                return;
            }
        }
        L_f1: for (j = 0; (j < GroupCount); j++) {
            LidGroups[j] = (null);
        }
        if ((LidCount == 0)) {
            return;
        }
        LidProcs = R.fill(new TLidProc[(int) (LidCount)], (() -> new TLidProc()));
        if ((LidProcs == null)) {
            F_swmm5.@ErrorCode = ERR_MEMORY;
            return;
        }
        L_f2: for (j = 0; (j < LidCount); j++) {
            LidProcs[j].@lidType = (-1);
            LidProcs[j].surface.@thickness = 0.0d;
            LidProcs[j].surface.@voidFrac = 1.0d;
            LidProcs[j].surface.@roughness = 0.0d;
            LidProcs[j].surface.@surfSlope = 0.0d;
            LidProcs[j].pavement.@thickness = 0.0d;
            LidProcs[j].soil.@thickness = 0.0d;
            LidProcs[j].storage.@thickness = 0.0d;
            LidProcs[j].storage.@kSat = 0.0d;
            LidProcs[j].drain.@coeff = 0.0d;
            LidProcs[j].drain.@offset = 0.0d;
            LidProcs[j].drainMat.@thickness = 0.0d;
            LidProcs[j].drainMat.@roughness = 0.0d;
            LidProcs[j].@drainRmvl = (null);
            LidProcs[j].@drainRmvl = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            if ((LidProcs[j].drainRmvl == null)) {
                F_swmm5.@ErrorCode = ERR_MEMORY;
                return;
            }
        }
    }

    // lid.c:304
    static void lid_delete() {
        int j = 0;
        L_f1: for (j = 0; (j < GroupCount); j++) {
            freeLidGroup(j);
        }
        if ((LidGroups != null)) {
            R.free(LidGroups);
            LidGroups = (null);
        }
        L_f2: for (j = 0; (j < LidCount); j++) {
            if ((LidProcs[j].drainRmvl != null)) {
                R.free(LidProcs[j].drainRmvl);
                LidProcs[j].@drainRmvl = (null);
            }
        }
        if ((LidProcs != null)) {
            R.free(LidProcs);
            LidProcs = (null);
        }
        GroupCount = 0;
        LidCount = 0;
    }

    // lid.c:322
    static void freeLidGroup(int j) {
        LidGroup lidGroup = LidGroups[j];
        TLidList lidList = null;
        TLidUnit lidUnit = null;
        TLidList nextLidUnit = null;
        if ((lidGroup == null)) {
            return;
        }
        lidList = lidGroup.lidList;
        L_w1: while ((lidList != null)) {
            lidUnit = lidList.lidUnit;
            if ((lidUnit.rptFile != null)) {
                if ((lidUnit.rptFile.file != null)) {
                    R.fclose(lidUnit.rptFile.file);
                }
                R.free(lidUnit.rptFile);
            }
            nextLidUnit = lidList.nextLidUnit;
            R.free(lidUnit);
            R.free(lidList);
            lidList = nextLidUnit;
        }
        R.free(lidGroup);
        LidGroups[j] = (null);
    }

    // lid.c:355
    static int lid_readProcParams(CPtr[] toks, int ntoks) {
        int j = 0;
        int m = 0;
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(LID, toks[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, toks[0]);
        }
        if ((LidProcs[j].ID == null)) {
            LidProcs[j].@ID = F_project.project_findID(LID, toks[0]);
        }
        m = F_input.findmatch(toks[1], LidTypeWords);
        if ((m >= 0)) {
            LidProcs[j].@lidType = m;
            return 0;
        } else {
            m = F_input.findmatch(toks[1], LidLayerWords);
        }
        int __sw1 = m;
        if (__sw1 == SURF) {
            return readSurfaceData(j, toks, ntoks);
        } else if (__sw1 == SOIL) {
            return readSoilData(j, toks, ntoks);
        } else if (__sw1 == STOR) {
            return readStorageData(j, toks, ntoks);
        } else if (__sw1 == PAVE) {
            return readPavementData(j, toks, ntoks);
        } else if (__sw1 == 5) {
            return readDrainData(j, toks, ntoks);
        } else if (__sw1 == DRAINMAT) {
            return readDrainMatData(j, toks, ntoks);
        } else if (__sw1 == REMOVALS) {
            return readRemovalsData(j, toks, ntoks);
        }
        return F_error.error_setInpError(ERR_KEYWORD, toks[1]);
    }

    // lid.c:415
    static int lid_readGroupParams(CPtr[] toks, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int n = 0;
        double[] x = new double[6];
        CPtr fname = null;
        int drainSubcatch = (-1);
        int drainNode = (-1);
        if ((ntoks < 8)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SUBCATCH, toks[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, toks[0]);
        }
        k = F_project.project_findObject(LID, toks[1]);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_NAME, toks[1]);
        }
        n = R.atoi(toks[2]);
        if ((n < 0)) {
            return F_error.error_setInpError(ERR_NUMBER, toks[2]);
        }
        if ((n == 0)) {
            return 0;
        }
        L_f1: for (i = 3; (i <= 7); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 3))) != 0)) || (x[(i - 3)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        L_f2: for (i = 2; (i <= 3); i++) {
            if (R.dgt(x[i], 100.0d)) {
                return F_error.error_setInpError(ERR_NUMBER, toks[(i + 3)]);
            }
        }
        if (((ntoks >= 9) && (R.strcmp(toks[8], L275) != 0))) {
            fname = toks[8];
        }
        if (((ntoks >= 10) && (R.strcmp(toks[9], L275) != 0))) {
            drainSubcatch = F_project.project_findObject(SUBCATCH, toks[9]);
            if ((drainSubcatch < 0)) {
                drainNode = F_project.project_findObject(NODE, toks[9]);
                if ((drainNode < 0)) {
                    return F_error.error_setInpError(ERR_NAME, toks[9]);
                }
            }
        }
        x[5] = 0.0d;
        if ((ntoks >= 11)) {
            if ((((!(F_input.getDouble(toks[10], new DElem(x, 5)) != 0)) || (x[5] < 0.0d)) || (R.dgt(x[5], 100.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[10]);
            }
        }
        return addLidUnit(j, k, n, x, fname, drainSubcatch, drainNode);
    }

    // lid.c:499
    static int addLidUnit(int j, int k, int n, double[] x, CPtr fname, int drainSubcatch, int drainNode) {
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if (!(lidGroup != null)) {
            lidGroup = new LidGroup();
            if (!(lidGroup != null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            lidGroup.@lidList = (null);
            LidGroups[j] = lidGroup;
        }
        lidUnit = new TLidUnit();
        if (!(lidUnit != null)) {
            return F_error.error_setInpError(ERR_MEMORY, L0);
        }
        lidUnit.@rptFile = (null);
        lidList = new TLidList();
        if (!(lidList != null)) {
            R.free(lidUnit);
            return F_error.error_setInpError(ERR_MEMORY, L0);
        }
        lidList.@lidUnit = lidUnit;
        lidList.@nextLidUnit = lidGroup.lidList;
        lidGroup.@lidList = lidList;
        lidUnit.@lidIndex = k;
        lidUnit.@number = n;
        lidUnit.@area = (x[0] / (((F_swmm5.UCF(LENGTH)) * (F_swmm5.UCF(LENGTH)))));
        lidUnit.@fullWidth = (x[1] / F_swmm5.UCF(LENGTH));
        lidUnit.@initSat = (x[2] / 100.0d);
        lidUnit.@fromImperv = (x[3] / 100.0d);
        lidUnit.@toPerv = ((R.dgt(x[4], 0.0d)) ? 1 : 0);
        lidUnit.@fromPerv = (x[5] / 100.0d);
        lidUnit.@drainSubcatch = drainSubcatch;
        lidUnit.@drainNode = drainNode;
        if ((fname != null)) {
            if (!(createLidRptFile(lidUnit, fname) != 0)) {
                return F_error.error_setInpError(ERR_RPT_FILE, fname);
            }
        }
        return 0;
    }

    // lid.c:567
    static int createLidRptFile(TLidUnit lidUnit, CPtr fname) {
        TLidRptFile rptFile = null;
        rptFile = new TLidRptFile();
        if ((rptFile == null)) {
            return 0;
        }
        lidUnit.@rptFile = rptFile;
        rptFile.@file = R.fopen(fname, L171);
        if ((rptFile.file == null)) {
            return 0;
        }
        return 1;
    }

    // lid.c:581
    static int readSurfaceData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        double[] x = new double[5];
        if ((ntoks < 7)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 2; (i < 7); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        if (R.dge(x[1], 1.0d)) {
            return F_error.error_setInpError(ERR_NUMBER, toks[3]);
        }
        if ((x[0] == 0.0d)) {
            x[1] = 0.0d;
        }
        LidProcs[j].surface.@thickness = (x[0] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].surface.@voidFrac = (1.0d - x[1]);
        LidProcs[j].surface.@roughness = x[2];
        LidProcs[j].surface.@surfSlope = (x[3] / 100.0d);
        LidProcs[j].surface.@sideSlope = x[4];
        return 0;
    }

    // lid.c:616
    static int readPavementData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        double[] x = new double[7];
        if ((ntoks < 7)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 2; (i < 7); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        x[5] = 0.0d;
        if ((ntoks > 7)) {
            if (((!(F_input.getDouble(toks[7], new DElem(x, 5)) != 0)) || (x[5] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[7]);
            }
        }
        x[6] = 0.0d;
        if ((ntoks > 8)) {
            if ((((!(F_input.getDouble(toks[8], new DElem(x, 6)) != 0)) || (x[6] < 0.0d)) || (R.dgt(x[6], 1.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[8]);
            }
        }
        x[1] = (x[1] / ((x[1] + 1.0d)));
        LidProcs[j].pavement.@thickness = (x[0] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].pavement.@voidFrac = x[1];
        LidProcs[j].pavement.@impervFrac = x[2];
        LidProcs[j].pavement.@kSat = (x[3] / F_swmm5.UCF(RAINFALL));
        LidProcs[j].pavement.@clogFactor = x[4];
        LidProcs[j].pavement.@regenDays = x[5];
        LidProcs[j].pavement.@regenDegree = x[6];
        return 0;
    }

    // lid.c:669
    static int readSoilData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        double[] x = new double[7];
        if ((ntoks < 9)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 2; (i < 9); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        LidProcs[j].soil.@thickness = (x[0] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].soil.@porosity = x[1];
        LidProcs[j].soil.@fieldCap = x[2];
        LidProcs[j].soil.@wiltPoint = x[3];
        LidProcs[j].soil.@kSat = (x[4] / F_swmm5.UCF(RAINFALL));
        LidProcs[j].soil.@kSlope = x[5];
        LidProcs[j].soil.@suction = (x[6] / F_swmm5.UCF(RAINDEPTH));
        return 0;
    }

    // lid.c:703
    static int readStorageData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        int covered = 0;
        double[] x = new double[6];
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 2; (i < 6); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        if ((ntoks > 6)) {
            if ((F_input.match(toks[6], L5) != 0)) {
                covered = 1;
            }
        }
        x[1] = (x[1] / ((x[1] + 1.0d)));
        LidProcs[j].storage.@thickness = (x[0] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].storage.@voidFrac = x[1];
        LidProcs[j].storage.@kSat = (x[2] / F_swmm5.UCF(RAINFALL));
        LidProcs[j].storage.@clogFactor = x[3];
        LidProcs[j].storage.@covered = covered;
        return 0;
    }

    // lid.c:749
    static int readDrainData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        double[] x = new double[6];
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 0; (i < 6); i++) {
            x[i] = 0.0d;
        }
        L_f2: for (i = 2; (i < 8); i++) {
            if ((((ntoks > i)) && (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        i = (-1);
        if ((ntoks >= 9)) {
            i = F_project.project_findObject(CURVE, toks[8]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, toks[8]);
            }
        }
        LidProcs[j].drain.@coeff = x[0];
        LidProcs[j].drain.@expon = x[1];
        LidProcs[j].drain.@offset = (x[2] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].drain.@delay = (x[3] * 3600.0d);
        LidProcs[j].drain.@hOpen = (x[4] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].drain.@hClose = (x[5] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].drain.@qCurve = i;
        return 0;
    }

    // lid.c:794
    static int readDrainMatData(int j, CPtr[] toks, int ntoks) {
        int i = 0;
        double[] x = new double[3];
        if ((ntoks < 5)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if ((LidProcs[j].lidType != GREEN_ROOF)) {
            return 0;
        }
        L_f1: for (i = 2; (i < 5); i++) {
            if (((!(F_input.getDouble(toks[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
        }
        LidProcs[j].drainMat.@thickness = (x[0] / F_swmm5.UCF(RAINDEPTH));
        LidProcs[j].drainMat.@voidFrac = x[1];
        LidProcs[j].drainMat.@roughness = x[2];
        return 0;
    }

    // lid.c:828
    static int readRemovalsData(int j, CPtr[] toks, int ntoks) {
        int i = 2;
        int p = 0;
        DBox rmvl = new DBox(0.0d);
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_w1: while ((ntoks > i)) {
            p = F_project.project_findObject(POLLUT, toks[i]);
            if ((p < 0)) {
                return F_error.error_setInpError(ERR_NAME, toks[i]);
            }
            i++;
            if ((ntoks == i)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((((!(F_input.getDouble(toks[i], rmvl) != 0)) || (rmvl.v < 0.0d)) || (R.dgt(rmvl.v, 100.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, toks[i]);
            }
            LidProcs[j].drainRmvl[p] = (rmvl.v / 100.0d);
            i++;
        }
        return 0;
    }

    // lid.c:869
    static void lid_writeSummary() {
        int j = 0;
        int k = 0;
        double pctArea = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        R.fprintf(F_swmm5.Frpt.file, L276);
        R.fprintf(F_swmm5.Frpt.file, L276);
        R.fprintf(F_swmm5.Frpt.file, L277);
        R.fprintf(F_swmm5.Frpt.file, L278);
        R.fprintf(F_swmm5.Frpt.file, L277);
        R.fprintf(F_swmm5.Frpt.file, L279);
        R.fprintf(F_swmm5.Frpt.file, L280);
        R.fprintf(F_swmm5.Frpt.file, L281);
        L_f1: for (j = 0; (j < GroupCount); j++) {
            lidGroup = LidGroups[j];
            if ((lidGroup == null)) {
                continue L_f1;
            }
            lidList = lidGroup.lidList;
            L_w2: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                k = lidUnit.lidIndex;
                pctArea = (((lidUnit.area * lidUnit.number) / F_swmm5.Subcatch[j].area) * 100.0d);
                R.fprintf(F_swmm5.Frpt.file, L282, F_swmm5.Subcatch[j].ID, LidProcs[k].ID);
                R.fprintf(F_swmm5.Frpt.file, L283, lidUnit.number, (lidUnit.area * (((F_swmm5.UCF(LENGTH)) * (F_swmm5.UCF(LENGTH))))), (lidUnit.fullWidth * F_swmm5.UCF(LENGTH)), pctArea, (lidUnit.fromImperv * 100.0d), (lidUnit.fromPerv * 100.0d));
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:918
    static void lid_validate() {
        int j = 0;
        L_f1: for (j = 0; (j < LidCount); j++) {
            validateLidProc(j);
        }
        L_f2: for (j = 0; (j < GroupCount); j++) {
            validateLidGroup(j);
        }
    }

    // lid.c:932
    static void validateLidProc(int j) {
        int layerMissing = 0;
        if ((LidProcs[j].lidType < 0)) {
            F_report.report_writeErrorMsg(ERR_LID_TYPE, LidProcs[j].ID);
            return;
        }
        int __sw1 = LidProcs[j].lidType;
        if (__sw1 == BIO_CELL || __sw1 == RAIN_GARDEN) {
            if ((LidProcs[j].soil.thickness <= 0.0d)) {
                layerMissing = 1;
            }
        } else if (__sw1 == GREEN_ROOF) {
            if ((LidProcs[j].soil.thickness <= 0.0d)) {
                layerMissing = 1;
            }
            if ((LidProcs[j].drainMat.thickness <= 0.0d)) {
                layerMissing = 1;
            }
        } else if (__sw1 == POROUS_PAVEMENT) {
            if ((LidProcs[j].pavement.thickness <= 0.0d)) {
                layerMissing = 1;
            }
        } else if (__sw1 == INFIL_TRENCH) {
            if ((LidProcs[j].storage.thickness <= 0.0d)) {
                layerMissing = 1;
            }
        }
        if ((layerMissing != 0)) {
            F_report.report_writeErrorMsg(ERR_LID_LAYER, LidProcs[j].ID);
            return;
        }
        if ((LidProcs[j].lidType == POROUS_PAVEMENT)) {
            if ((((((LidProcs[j].pavement.thickness <= 0.0d) || (LidProcs[j].pavement.kSat <= 0.0d)) || (LidProcs[j].pavement.voidFrac <= 0.0d)) || (R.dgt(LidProcs[j].pavement.voidFrac, 1.0d))) || (R.dgt(LidProcs[j].pavement.impervFrac, 1.0d)))) {
                Overrides.sstrncpy(F_swmm5.Msg, LidProcs[j].ID, 1024);
                Overrides.sstrcat(F_swmm5.Msg, L284, 1024);
                F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
            }
        }
        if (R.dgt(LidProcs[j].soil.thickness, 0.0d)) {
            if ((((((LidProcs[j].soil.porosity <= 0.0d) || (R.dge(LidProcs[j].soil.fieldCap, LidProcs[j].soil.porosity))) || (R.dge(LidProcs[j].soil.wiltPoint, LidProcs[j].soil.fieldCap))) || (LidProcs[j].soil.kSat <= 0.0d)) || (LidProcs[j].soil.kSlope < 0.0d))) {
                Overrides.sstrncpy(F_swmm5.Msg, LidProcs[j].ID, 1024);
                Overrides.sstrcat(F_swmm5.Msg, L285, 1024);
                F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
            }
        }
        if (R.dgt(LidProcs[j].storage.thickness, 0.0d)) {
            if (((LidProcs[j].storage.voidFrac <= 0.0d) || (R.dgt(LidProcs[j].storage.voidFrac, 1.0d)))) {
                Overrides.sstrncpy(F_swmm5.Msg, LidProcs[j].ID, 1024);
                Overrides.sstrcat(F_swmm5.Msg, L286, 1024);
                F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
            }
        } else {
            LidProcs[j].storage.@voidFrac = 1.0d;
            LidProcs[j].drain.@offset = 0.0d;
        }
        if (((R.dgt(LidProcs[j].drain.hOpen, 0.0d)) && (LidProcs[j].drain.hOpen <= LidProcs[j].drain.hClose))) {
            Overrides.sstrncpy(F_swmm5.Msg, LidProcs[j].ID, 1024);
            Overrides.sstrcat(F_swmm5.Msg, L287, 1024);
            F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
        }
        if ((LidProcs[j].lidType == VEG_SWALE)) {
            if ((((LidProcs[j].surface.roughness * LidProcs[j].surface.surfSlope) <= 0.0d) || (LidProcs[j].surface.thickness == 0.0d))) {
                Overrides.sstrncpy(F_swmm5.Msg, LidProcs[j].ID, 1024);
                Overrides.sstrcat(F_swmm5.Msg, L288, 1024);
                F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
            } else {
                LidProcs[j].surface.@alpha = ((1.49d * Math.sqrt(LidProcs[j].surface.surfSlope)) / LidProcs[j].surface.roughness);
            }
        } else {
            if (R.dgt(LidProcs[j].surface.roughness, 0.0d)) {
                LidProcs[j].surface.@alpha = ((1.49d / LidProcs[j].surface.roughness) * Math.sqrt(LidProcs[j].surface.surfSlope));
            } else {
                LidProcs[j].surface.@alpha = 0.0d;
            }
        }
        if (R.dgt(LidProcs[j].drainMat.roughness, 0.0d)) {
            LidProcs[j].drainMat.@alpha = ((1.49d / LidProcs[j].drainMat.roughness) * Math.sqrt(LidProcs[j].surface.surfSlope));
        } else {
            LidProcs[j].drainMat.@alpha = 0.0d;
        }
        if (R.dgt(LidProcs[j].pavement.thickness, 0.0d)) {
            LidProcs[j].pavement.@clogFactor = (LidProcs[j].pavement.clogFactor * ((LidProcs[j].pavement.thickness * LidProcs[j].pavement.voidFrac) * ((1.0d - LidProcs[j].pavement.impervFrac))));
        }
        if (R.dgt(LidProcs[j].storage.thickness, 0.0d)) {
            LidProcs[j].storage.@clogFactor = (LidProcs[j].storage.clogFactor * (LidProcs[j].storage.thickness * LidProcs[j].storage.voidFrac));
        } else {
            LidProcs[j].storage.@clogFactor = 0.0d;
        }
        LidProcs[j].surface.@canOverflow = 1;
        int __sw2 = LidProcs[j].lidType;
        if (__sw2 == ROOF_DISCON) {
            LidProcs[j].surface.@canOverflow = 0;
        } else if (__sw2 == INFIL_TRENCH || __sw2 == POROUS_PAVEMENT || __sw2 == BIO_CELL || __sw2 == RAIN_GARDEN || __sw2 == GREEN_ROOF) {
            if (R.dgt(LidProcs[j].surface.alpha, 0.0d)) {
                LidProcs[j].surface.@canOverflow = 0;
            }
        }
        if ((LidProcs[j].lidType == RAIN_BARREL)) {
            LidProcs[j].storage.@voidFrac = 1.0d;
            LidProcs[j].storage.@kSat = 0.0d;
        }
        if ((LidProcs[j].lidType == GREEN_ROOF)) {
            LidProcs[j].storage.@thickness = LidProcs[j].drainMat.thickness;
            LidProcs[j].storage.@voidFrac = LidProcs[j].drainMat.voidFrac;
            LidProcs[j].storage.@clogFactor = 0.0d;
            LidProcs[j].storage.@kSat = 0.0d;
        }
    }

    // lid.c:1113
    static void validateLidGroup(int j) {
        int k = 0;
        double[] p = new double[3];
        double totalArea = F_swmm5.Subcatch[j].area;
        double totalLidArea = 0.0d;
        double fromImperv = 0.0d;
        double fromPerv = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if ((lidGroup == null)) {
            return;
        }
        lidList = lidGroup.lidList;
        L_w1: while ((lidList != null)) {
            lidUnit = lidList.lidUnit;
            k = lidUnit.lidIndex;
            totalLidArea += ((lidUnit.area * lidUnit.number));
            fromImperv += lidUnit.fromImperv;
            fromPerv += lidUnit.fromPerv;
            lidUnit.soilInfil.@Ks = 0.0d;
            if (R.dgt(LidProcs[k].soil.thickness, 0.0d)) {
                p[0] = (LidProcs[k].soil.suction * F_swmm5.UCF(RAINDEPTH));
                p[1] = (LidProcs[k].soil.kSat * F_swmm5.UCF(RAINFALL));
                p[2] = (((LidProcs[k].soil.porosity - LidProcs[k].soil.wiltPoint)) * ((1.0d - lidUnit.initSat)));
                if ((F_infil.grnampt_setParams(lidUnit.soilInfil, p) == 0)) {
                    Overrides.sstrncpy(F_swmm5.Msg, LidProcs[k].ID, 1024);
                    Overrides.sstrcat(F_swmm5.Msg, L285, 1024);
                    F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
                }
            }
            if ((LidProcs[k].lidType == VEG_SWALE)) {
                if (((F_swmm5.Subcatch[j].infilModel == GREEN_AMPT) || (F_swmm5.Subcatch[j].infilModel == MOD_GREEN_AMPT))) {
                    F_infil.grnampt_getParams(j, p);
                    if ((F_infil.grnampt_setParams(lidUnit.soilInfil, p) == 0)) {
                        Overrides.sstrncpy(F_swmm5.Msg, LidProcs[k].ID, 1024);
                        Overrides.sstrcat(F_swmm5.Msg, L289, 1024);
                        F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
                    }
                }
                if ((lidUnit.fullWidth <= 0.0d)) {
                    Overrides.sstrncpy(F_swmm5.Msg, LidProcs[k].ID, 1024);
                    Overrides.sstrcat(F_swmm5.Msg, L290, 1024);
                    F_report.report_writeErrorMsg(ERR_LID_PARAMS, F_swmm5.Msg);
                }
            }
            if (R.dge(F_swmm5.Subcatch[j].fracImperv, 0.999d)) {
                lidUnit.@toPerv = 0;
            }
            if (((lidUnit.drainNode == (-1)) && (lidUnit.drainSubcatch == (-1)))) {
                lidUnit.@drainNode = F_swmm5.Subcatch[j].outNode;
                lidUnit.@drainSubcatch = F_swmm5.Subcatch[j].outSubcatch;
            }
            lidList = lidList.nextLidUnit;
        }
        if (R.dgt(totalLidArea, (1.001d * totalArea))) {
            F_report.report_writeErrorMsg(ERR_LID_AREAS, F_swmm5.Subcatch[j].ID);
        }
        if (((R.dgt(fromImperv, 1.001d)) || (R.dgt(fromPerv, 1.001d)))) {
            F_report.report_writeErrorMsg(ERR_LID_CAPTURE_AREA, F_swmm5.Subcatch[j].ID);
        }
        if (R.dgt(totalLidArea, (0.999d * totalArea))) {
            totalLidArea = totalArea;
        }
        F_swmm5.Subcatch[j].@lidArea = totalLidArea;
    }

    // lid.c:1211
    static void lid_initState() {
        int i = 0;
        int j = 0;
        int k = 0;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        double initVol = 0.0d;
        double initDryTime = (F_swmm5.StartDryDays * 86400);
        F_runoff.@HasWetLids = 0;
        L_f1: for (j = 0; (j < GroupCount); j++) {
            lidGroup = LidGroups[j];
            if ((lidGroup == null)) {
                continue L_f1;
            }
            lidGroup.@pervArea = 0.0d;
            lidGroup.@flowToPerv = 0.0d;
            lidGroup.@oldDrainFlow = 0.0d;
            lidGroup.@newDrainFlow = 0.0d;
            lidList = lidGroup.lidList;
            L_w2: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                k = lidUnit.lidIndex;
                lidUnit.@surfaceDepth = 0.0d;
                lidUnit.@storageDepth = 0.0d;
                lidUnit.@soilMoisture = 0.0d;
                lidUnit.@paveDepth = 0.0d;
                lidUnit.@dryTime = initDryTime;
                lidUnit.@volTreated = 0.0d;
                lidUnit.@nextRegenDay = LidProcs[k].pavement.regenDays;
                initVol = 0.0d;
                if (R.dgt(LidProcs[k].soil.thickness, 0.0d)) {
                    lidUnit.@soilMoisture = (LidProcs[k].soil.wiltPoint + (lidUnit.initSat * ((LidProcs[k].soil.porosity - LidProcs[k].soil.wiltPoint))));
                    initVol += (lidUnit.soilMoisture * LidProcs[k].soil.thickness);
                }
                if (R.dgt(LidProcs[k].storage.thickness, 0.0d)) {
                    lidUnit.@storageDepth = (lidUnit.initSat * LidProcs[k].storage.thickness);
                    initVol += (lidUnit.storageDepth * LidProcs[k].storage.voidFrac);
                }
                if (R.dgt(lidUnit.initSat, 0.0d)) {
                    F_runoff.@HasWetLids = 1;
                }
                F_lidproc.lidproc_initWaterBalance(lidUnit, initVol);
                lidUnit.@volTreated = 0.0d;
                if ((lidUnit.rptFile != null)) {
                    initLidRptFile(F_swmm5.Title[0], LidProcs[k].ID, F_swmm5.Subcatch[j].ID, lidUnit);
                }
                lidUnit.@oldDrainFlow = 0.0d;
                lidUnit.@newDrainFlow = 0.0d;
                L_f3: for (i = 0; (i < 4); i++) {
                    lidUnit.oldFluxRates[i] = 0.0d;
                }
                if (R.dgt(lidUnit.soilInfil.Ks, 0.0d)) {
                    F_infil.grnampt_initState(lidUnit.soilInfil);
                }
                if ((isLidPervious(lidUnit.lidIndex) != 0)) {
                    lidGroup.@pervArea = (lidGroup.pervArea + ((lidUnit.area * lidUnit.number)));
                }
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1302
    static void lid_setOldGroupState(int j) {
        TLidList lidList = null;
        if ((LidGroups[j] != null)) {
            LidGroups[j].@oldDrainFlow = LidGroups[j].newDrainFlow;
            LidGroups[j].@newDrainFlow = 0.0d;
            lidList = LidGroups[j].lidList;
            L_w1: while ((lidList != null)) {
                lidList.lidUnit.@oldDrainFlow = lidList.lidUnit.newDrainFlow;
                lidList.lidUnit.@newDrainFlow = 0.0d;
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1326
    static int isLidPervious(int k) {
        return ((((LidProcs[k].storage.thickness == 0.0d) || (R.dgt(LidProcs[k].storage.kSat, 0.0d)))) ? 1 : 0);
    }

    // lid.c:1339
    static double getSurfaceDepth(int j) {
        int k = 0;
        double depth = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if ((lidGroup == null)) {
            return 0.0d;
        }
        if ((F_swmm5.Subcatch[j].lidArea == 0.0d)) {
            return 0.0d;
        }
        lidList = lidGroup.lidList;
        L_w1: while ((lidList != null)) {
            lidUnit = lidList.lidUnit;
            k = lidUnit.lidIndex;
            depth += (((lidUnit.surfaceDepth * LidProcs[k].surface.voidFrac) * lidUnit.area) * lidUnit.number);
            lidList = lidList.nextLidUnit;
        }
        return (depth / F_swmm5.Subcatch[j].lidArea);
    }

    // lid.c:1370
    static double lid_getPervArea(int j) {
        if ((LidGroups[j] != null)) {
            return LidGroups[j].pervArea;
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('lid_getPervArea: no return');
    }

    // lid.c:1383
    static double lid_getFlowToPerv(int j) {
        if ((LidGroups[j] != null)) {
            return LidGroups[j].flowToPerv;
        }
        return 0.0d;
    }

    // lid.c:1397
    static double lid_getStoredVolume(int j) {
        double total = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if (((lidGroup == null) || (F_swmm5.Subcatch[j].lidArea == 0.0d))) {
            return 0.0d;
        }
        lidList = lidGroup.lidList;
        L_w1: while ((lidList != null)) {
            lidUnit = lidList.lidUnit;
            total += ((lidUnit.waterBalance.finalVol * lidUnit.area) * lidUnit.number);
            lidList = lidList.nextLidUnit;
        }
        return total;
    }

    // lid.c:1424
    static double lid_getDrainFlow(int j, int timePeriod) {
        if ((LidGroups[j] != null)) {
            if ((timePeriod == PREVIOUS)) {
                return LidGroups[j].oldDrainFlow;
            } else {
                return LidGroups[j].newDrainFlow;
            }
        }
        return 0.0d;
    }

    // lid.c:1442
    static void lid_addDrainLoads(int j, double[] c, double tStep) {
        int isRunoffLoad = 0;
        int p = 0;
        double r = 0.0d;
        double w = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if ((lidGroup != null)) {
            lidList = lidGroup.lidList;
            L_w1: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                isRunoffLoad = ((((lidUnit.drainNode >= 0) || (lidUnit.drainSubcatch == j))) ? 1 : 0);
                if (!(lidUnit.toPerv != 0)) {
                    L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                        w = ((((lidUnit.newDrainFlow * c[p]) * tStep) * 28.317d) * F_swmm5.Pollut[p].mcf);
                        r = LidProcs[lidUnit.lidIndex].drainRmvl[p];
                        F_massbal.massbal_updateLoadingTotals(BMP_REMOVAL_LOAD, p, (r * w));
                        if ((isRunoffLoad != 0)) {
                            F_massbal.massbal_updateLoadingTotals(RUNOFF_LOAD, p, (w * ((1.0d - r))));
                        }
                    }
                }
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1497
    static void lid_addDrainRunon(int j) {
        int i = 0;
        int k = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if ((lidGroup != null)) {
            lidList = lidGroup.lidList;
            L_w1: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                i = lidUnit.lidIndex;
                k = lidUnit.drainSubcatch;
                if (((k >= 0) && (k != j))) {
                    q = lidUnit.oldDrainFlow;
                    F_subcatch.subcatch_addRunonFlow(k, q);
                    L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                        w = ((q * F_swmm5.Subcatch[j].oldQual[p]) * 28.317d);
                        w = (w * ((1.0d - LidProcs[i].drainRmvl[p])));
                        F_swmm5.Subcatch[k].newQual[p] += w;
                    }
                }
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1549
    static void lid_addDrainInflow(int j, double f) {
        int i = 0;
        int k = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        double w1 = 0.0d;
        double w2 = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        lidGroup = LidGroups[j];
        if ((lidGroup != null)) {
            lidList = lidGroup.lidList;
            L_w1: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                i = lidUnit.lidIndex;
                k = lidUnit.drainNode;
                if ((k >= 0)) {
                    q = ((((1.0d - f)) * lidUnit.oldDrainFlow) + (f * lidUnit.newDrainFlow));
                    F_swmm5.Node_[k].@newLatFlow = (F_swmm5.Node_[k].newLatFlow + q);
                    F_massbal.massbal_addInflowFlow(WET_WEATHER_INFLOW, q);
                    L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                        w1 = (lidUnit.oldDrainFlow * F_swmm5.Subcatch[j].oldQual[p]);
                        w2 = (lidUnit.newDrainFlow * F_swmm5.Subcatch[j].newQual[p]);
                        w = ((((1.0d - f)) * w1) + (f * w2));
                        w = (w * ((1.0d - LidProcs[i].drainRmvl[p])));
                        F_swmm5.Node_[k].newQual[p] += w;
                        F_massbal.massbal_addInflowQual(WET_WEATHER_INFLOW, p, w);
                    }
                }
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1609
    static void lid_getRunoff(int j, double tStep) {
        LidGroup theLidGroup = null;
        TLidList lidList = null;
        TLidUnit lidUnit = null;
        double lidArea = 0.0d;
        double qImperv = 0.0d;
        double qPerv = 0.0d;
        double lidInflow = 0.0d;
        DBox qRunoff = new DBox(0.0d);
        DBox qDrain = new DBox(0.0d);
        DBox qReturn = new DBox(0.0d);
        theLidGroup = LidGroups[j];
        if (!(theLidGroup != null)) {
            return;
        }
        lidList = theLidGroup.lidList;
        if (!(lidList != null)) {
            return;
        }
        lid_EvapRate = F_swmm5.Evap.rate;
        if (((F_swmm5.Evap.dryOnly != 0) && (R.dgt(F_swmm5.Subcatch[j].rainfall, 0.0d)))) {
            lid_EvapRate = 0.0d;
        }
        findNativeInfil(j, tStep);
        if (R.dgt(F_swmm5.Subcatch[j].area, F_swmm5.Subcatch[j].lidArea)) {
            qImperv = getImpervAreaRunoff(j);
            qPerv = getPervAreaRunoff(j);
        }
        L_w1: while ((lidList != null)) {
            lidUnit = lidList.lidUnit;
            lidArea = (lidUnit.area * lidUnit.number);
            if (R.dgt(lidArea, 0.0d)) {
                lidInflow = ((((qImperv * lidUnit.fromImperv) + (qPerv * lidUnit.fromPerv))) / lidArea);
                F_subcatch.@VlidIn = (F_subcatch.VlidIn + ((lidInflow * lidArea) * tStep));
                lidInflow = (lidInflow + getRainInflow(j, lidUnit));
                if ((F_swmm5.Subcatch[j].area == F_swmm5.Subcatch[j].lidArea)) {
                    lidInflow += F_swmm5.Subcatch[j].runon;
                }
                evalLidUnit(j, lidUnit, lidArea, lidInflow, tStep, qRunoff, qDrain, qReturn);
            }
            lidList = lidList.nextLidUnit;
        }
        theLidGroup.@newDrainFlow = qDrain.v;
        theLidGroup.@flowToPerv = qReturn.v;
        F_subcatch.@VlidOut = (qRunoff.v * tStep);
        F_subcatch.@VlidDrain = (qDrain.v * tStep);
        F_subcatch.@VlidReturn = (qReturn.v * tStep);
    }

    // lid.c:1697
    static void findNativeInfil(int j, double tStep) {
        double nonLidArea = 0.0d;
        nonLidArea = (F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea);
        if (((R.dgt(nonLidArea, 0.0d)) && (F_swmm5.Subcatch[j].fracImperv < 1.0d))) {
            NativeInfil = ((F_subcatch.Vinfil / nonLidArea) / tStep);
        } else {
            NativeInfil = F_infil.infil_getInfil(j, tStep, F_swmm5.Subcatch[j].rainfall, F_swmm5.Subcatch[j].runon, getSurfaceDepth(j));
        }
        if (((!(F_swmm5.IgnoreGwater != 0)) && (F_swmm5.Subcatch[j].groundwater != null))) {
            lid_MaxNativeInfil = (F_swmm5.Subcatch[j].groundwater.maxInfilVol / tStep);
        } else {
            lid_MaxNativeInfil = 10000000000.0d;
        }
    }

    // lid.c:1734
    static double getRainInflow(int j, TLidUnit lidUnit) {
        TLidProc lidProc = LidProcs[lidUnit.lidIndex];
        if (((lidProc.lidType == RAIN_BARREL) && (lidProc.storage.covered == 1))) {
            return 0.0d;
        }
        return F_swmm5.Subcatch[j].rainfall;
    }

    // lid.c:1751
    static double getImpervAreaRunoff(int j) {
        int i = 0;
        double q = 0.0d;
        double nonLidArea = 0.0d;
        L_f1: for (i = IMPERV0; (i <= IMPERV1); i++) {
            q += (F_swmm5.Subcatch[j].subArea[i].runoff * F_swmm5.Subcatch[j].subArea[i].fArea);
        }
        if (((F_swmm5.Subcatch[j].subArea[IMPERV0].routeTo == TO_PERV) && (F_swmm5.Subcatch[j].fracImperv < 1.0d))) {
            q *= F_swmm5.Subcatch[j].subArea[IMPERV0].fOutlet;
        }
        nonLidArea = (F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea);
        return (q * nonLidArea);
    }

    // lid.c:1781
    static double getPervAreaRunoff(int j) {
        double q = 0.0d;
        double nonLidArea = 0.0d;
        q = (F_swmm5.Subcatch[j].subArea[PERV].runoff * F_swmm5.Subcatch[j].subArea[PERV].fArea);
        if (((F_swmm5.Subcatch[j].subArea[PERV].routeTo == TO_IMPERV) && (R.dgt(F_swmm5.Subcatch[j].fracImperv, 0.0d)))) {
            q *= F_swmm5.Subcatch[j].subArea[PERV].fOutlet;
        }
        nonLidArea = (F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea);
        return (q * nonLidArea);
    }

    // lid.c:1807
    static void evalLidUnit(int j, TLidUnit lidUnit, double lidArea, double lidInflow, double tStep, DRef qRunoff, DRef qDrain, DRef qReturn) {
        TLidProc lidProc = null;
        double lidRunoff = 0.0d;
        DBox lidEvap = new DBox(0.0d);
        DBox lidInfil = new DBox(0.0d);
        DBox lidDrain = new DBox(0.0d);
        lidProc = LidProcs[lidUnit.lidIndex];
        lidEvap.@v = 0.0d;
        lidInfil.@v = 0.0d;
        lidRunoff = (F_lidproc.lidproc_getOutflow(lidUnit, lidProc, lidInflow, lid_EvapRate, NativeInfil, lid_MaxNativeInfil, tStep, lidEvap, lidInfil, lidDrain) * lidArea);
        lidDrain.@v = (lidDrain.v * lidArea);
        if (((lidUnit.toPerv != 0) && (R.dgt(F_swmm5.Subcatch[j].area, F_swmm5.Subcatch[j].lidArea)))) {
            qReturn.set((qReturn.get() + lidRunoff));
            lidRunoff = 0.0d;
            if (((lidUnit.drainNode == F_swmm5.Subcatch[j].outNode) && (lidUnit.drainSubcatch == F_swmm5.Subcatch[j].outSubcatch))) {
                qReturn.set((qReturn.get() + lidDrain.v));
                lidDrain.@v = 0.0d;
            }
        }
        if ((lidUnit.drainNode >= 0)) {
            F_massbal.massbal_updateRunoffTotals(RUNOFF_DRAINS, (lidDrain.v * tStep));
        }
        lidUnit.@newDrainFlow = lidDrain.v;
        F_subcatch.@Vevap = (F_subcatch.Vevap + ((lidEvap.v * tStep) * lidArea));
        F_subcatch.@VlidInfil = (F_subcatch.VlidInfil + ((lidInfil.v * tStep) * lidArea));
        if ((isLidPervious(lidUnit.lidIndex) != 0)) {
            F_subcatch.@Vpevap = (F_subcatch.Vpevap + ((lidEvap.v * tStep) * lidArea));
        }
        if (R.dgt(F_swmm5.Subcatch[j].rainfall, 2.31481e-08d)) {
            lidUnit.@dryTime = 0.0d;
        } else {
            lidUnit.@dryTime = (lidUnit.dryTime + tStep);
        }
        F_lidproc.lidproc_saveResults(lidUnit, F_swmm5.UCF(RAINFALL), F_swmm5.UCF(RAINDEPTH));
        qRunoff.set((qRunoff.get() + lidRunoff));
        qDrain.set((qDrain.get() + lidDrain.v));
    }

    // lid.c:1890
    static void lid_writeWaterBalance() {
        int j = 0;
        int k = 0;
        double ucf = F_swmm5.UCF(RAINDEPTH);
        double inflow = 0.0d;
        double outflow = 0.0d;
        double err = 0.0d;
        TLidUnit lidUnit = null;
        TLidList lidList = null;
        LidGroup lidGroup = null;
        L_f1: for (j = 0; (j < GroupCount); j++) {
            if ((LidGroups[j] != null)) {
                k++;
            }
        }
        if ((k == 0)) {
            return;
        }
        R.fprintf(F_swmm5.Frpt.file, L291);
        R.fprintf(F_swmm5.Frpt.file, L292);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L293);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L294);
        }
        R.fprintf(F_swmm5.Frpt.file, L295);
        L_f2: for (j = 0; (j < GroupCount); j++) {
            lidGroup = LidGroups[j];
            if (((!(lidGroup != null)) || (F_swmm5.Subcatch[j].lidArea == 0.0d))) {
                continue L_f2;
            }
            lidList = lidGroup.lidList;
            L_w3: while ((lidList != null)) {
                lidUnit = lidList.lidUnit;
                k = lidUnit.lidIndex;
                R.fprintf(F_swmm5.Frpt.file, L296, F_swmm5.Subcatch[j].ID, LidProcs[k].ID);
                R.fprintf(F_swmm5.Frpt.file, L297, (lidUnit.waterBalance.inflow * ucf), (lidUnit.waterBalance.evap * ucf), (lidUnit.waterBalance.infil * ucf), (lidUnit.waterBalance.surfFlow * ucf), (lidUnit.waterBalance.drainFlow * ucf), (lidUnit.waterBalance.initVol * ucf), (lidUnit.waterBalance.finalVol * ucf));
                inflow = (lidUnit.waterBalance.initVol + lidUnit.waterBalance.inflow);
                outflow = ((((lidUnit.waterBalance.finalVol + lidUnit.waterBalance.evap) + lidUnit.waterBalance.infil) + lidUnit.waterBalance.surfFlow) + lidUnit.waterBalance.drainFlow);
                if (R.dgt(inflow, 0.0d)) {
                    err = (((inflow - outflow)) / inflow);
                } else {
                    err = 1.0d;
                }
                R.fprintf(F_swmm5.Frpt.file, L298, (err * 100.0d));
                lidList = lidList.nextLidUnit;
            }
        }
    }

    // lid.c:1972
    static void initLidRptFile(CPtr title, CPtr lidID, CPtr subcatchID, TLidUnit lidUnit) {
        int i = 0;
        CFile f = lidUnit.rptFile.file;
        if ((f == null)) {
            return;
        }
        R.fprintf(f, L299);
        R.fprintf(f, L300, title);
        R.fprintf(f, L301, lidID, subcatchID);
        L_f1: for (i = 0; (i < initLidRptFile_colCount); i++) {
            R.fprintf(f, L169, initLidRptFile_head1[i]);
        }
        L_f2: for (i = 0; (i < initLidRptFile_colCount); i++) {
            R.fprintf(f, L169, initLidRptFile_head2[i]);
        }
        if ((F_swmm5.UnitSystem == US)) {
            L_f3: for (i = 0; (i < initLidRptFile_colCount); i++) {
                R.fprintf(f, L169, initLidRptFile_units1[i]);
            }
        } else {
            L_f4: for (i = 0; (i < initLidRptFile_colCount); i++) {
                R.fprintf(f, L169, initLidRptFile_units2[i]);
            }
        }
        R.fprintf(f, L302);
        L_f5: for (i = 1; (i < initLidRptFile_colCount); i++) {
            R.fprintf(f, L303, initLidRptFile_line9);
        }
        lidUnit.rptFile.@wasDry = 1;
        Overrides.sstrncpy(lidUnit.rptFile.results, L0, 0);
    }

}
