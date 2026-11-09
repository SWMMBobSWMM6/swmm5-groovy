// Generated from EPA SWMM 5.2.4 (runoff.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_runoff {
    public static int IsRaining = 0;
    public static int HasRunoff = 0;
    public static int HasSnow = 0;
    public static IBox runoff_Nsteps = new IBox(0);
    public static IBox MaxSteps = new IBox(0);
    public static long MaxStepsPos = 0L;
    public static int HasWetLids = 0;
    public static double[] OutflowLoad = null;

    // runoff.c:82
    static int runoff_open() {
        IsRaining = 0;
        HasRunoff = 0;
        HasSnow = 0;
        runoff_Nsteps.@v = 0;
        if (!(Overrides.odesolve_open(4) != 0)) {
            F_report.report_writeErrorMsg(ERR_ODE_SOLVER, L0);
        }
        OutflowLoad = (null);
        if ((F_swmm5.Nobjects[POLLUT] > 0)) {
            OutflowLoad = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            if (!(OutflowLoad != null)) {
                F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            }
        }
        int __sw1 = F_swmm5.Frunoff.mode;
        if (__sw1 == USE_FILE) {
            if ((((F_swmm5.Frunoff.@file = R.fopen(F_swmm5.Frunoff.name, L165))) == null)) {
                F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_OPEN, F_swmm5.Frunoff.name);
            } else {
                runoff_initFile();
            }
        } else if (__sw1 == SAVE_FILE) {
            if ((((F_swmm5.Frunoff.@file = R.fopen(F_swmm5.Frunoff.name, L166))) == null)) {
                F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_OPEN, F_swmm5.Frunoff.name);
            } else {
                runoff_initFile();
            }
        }
        return F_swmm5.ErrorCode;
    }

    // runoff.c:124
    static void runoff_close() {
        Overrides.odesolve_close();
        if ((OutflowLoad != null)) {
            R.free(OutflowLoad);
            OutflowLoad = (null);
        }
        if ((F_swmm5.Frunoff.file != null)) {
            if ((F_swmm5.Frunoff.mode == SAVE_FILE)) {
                R.fseek(F_swmm5.Frunoff.file, MaxStepsPos, 0);
                R.fwrite1(runoff_Nsteps.v, 4, F_swmm5.Frunoff.file);
            }
            R.fclose(F_swmm5.Frunoff.file);
        }
        if ((F_swmm5.Fclimate.file != null)) {
            R.fclose(F_swmm5.Fclimate.file);
        }
    }

    // runoff.c:155
    static void runoff_execute() {
        int j = 0;
        int day = 0;
        double runoffStep = 0.0d;
        double oldRunoffStep = 0.0d;
        double runoff = 0.0d;
        double currentDate = 0.0d;
        int canSweep = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        oldRunoffStep = (((F_swmm5.NewRunoffTime - F_swmm5.OldRunoffTime)) / 1000.0d);
        currentDate = F_swmm5.getDateTime(F_swmm5.NewRunoffTime);
        F_climate.climate_setState(currentDate);
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            F_swmm5.@OldRunoffTime = F_swmm5.NewRunoffTime;
            F_swmm5.@NewRunoffTime = (F_swmm5.NewRunoffTime + (((double) ((F_swmm5.DryStep))) * 1000.0d));
            F_swmm5.@NewRunoffTime = (((((F_swmm5.NewRunoffTime) <= (F_swmm5.TotalDuration))) ? (F_swmm5.NewRunoffTime) : (F_swmm5.TotalDuration)));
            return;
        }
        IsRaining = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            F_gage.gage_setState(j, currentDate);
            if (R.dgt(F_swmm5.Gage[j].rainfall, 0.0d)) {
                IsRaining = 1;
            }
        }
        if ((F_swmm5.Frunoff.mode == USE_FILE)) {
            runoff_readFromFile();
            return;
        }
        day = F_datetime.datetime_dayOfYear(currentDate);
        canSweep = 0;
        if ((F_swmm5.SweepStart <= F_swmm5.SweepEnd)) {
            if (((day >= F_swmm5.SweepStart) && (day <= F_swmm5.SweepEnd))) {
                canSweep = 1;
            }
        } else if (((day <= F_swmm5.SweepEnd) || (day >= F_swmm5.SweepStart))) {
            canSweep = 1;
        }
        runoffStep = runoff_getTimeStep(currentDate);
        if ((runoffStep <= 0.0d)) {
            F_swmm5.@ErrorCode = ERR_TIMESTEP;
            return;
        }
        F_swmm5.@OldRunoffTime = F_swmm5.NewRunoffTime;
        F_swmm5.@NewRunoffTime = (F_swmm5.NewRunoffTime + ((1000 * runoffStep)));
        if (R.dgt(F_swmm5.NewRunoffTime, F_swmm5.TotalDuration)) {
            runoffStep = (((F_swmm5.TotalDuration - F_swmm5.OldRunoffTime)) / 1000.0d);
            F_swmm5.@NewRunoffTime = F_swmm5.TotalDuration;
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            F_gage.gage_updatePastRain(j, R.D2I(runoffStep));
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_subcatch.subcatch_setOldState(j);
        }
        if (R.dgt(oldRunoffStep, 0.0d)) {
            runoff_getOutfallRunon(oldRunoffStep);
        }
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if ((F_swmm5.Subcatch[j].area == 0.0d)) {
                continue L_f4;
            }
            F_subcatch.subcatch_getRunon(j);
            if (!(F_swmm5.IgnoreSnowmelt != 0)) {
                F_snow.snow_plowSnow(j, runoffStep);
            }
        }
        HasSnow = 0;
        HasRunoff = 0;
        HasWetLids = 0;
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if ((F_swmm5.Subcatch[j].area == 0.0d)) {
                continue L_f5;
            }
            runoff = F_subcatch.subcatch_getRunoff(j, runoffStep);
            if (R.dgt(runoff, 0.0d)) {
                HasRunoff = 1;
            }
            if (R.dgt(F_swmm5.Subcatch[j].newSnowDepth, 0.0d)) {
                HasSnow = 1;
            }
            if ((F_swmm5.IgnoreQuality != 0)) {
                continue L_f5;
            }
            if ((runoff < 2.31481e-08d)) {
                F_surfqual.surfqual_getBuildup(j, runoffStep);
            }
            if (((canSweep != 0) && (F_swmm5.Subcatch[j].rainfall <= 2.31481e-08d))) {
                F_surfqual.surfqual_sweepBuildup(j, currentDate);
            }
            F_surfqual.surfqual_getWashoff(j, runoff, runoffStep);
        }
        F_stats.stats_updateMaxRunoff();
        runoff_Nsteps.@v = runoff_Nsteps.v + 1;
        if ((F_swmm5.Frunoff.mode == SAVE_FILE)) {
            runoff_saveToFile(((float) (runoffStep)));
        }
        L_f6: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_swmm5.Subcatch[j].@runon = 0.0d;
        }
    }

    // runoff.c:299
    static double runoff_getTimeStep(double currentDate) {
        int j = 0;
        long timeStep = 0L;
        long maxStep = F_swmm5.DryStep;
        timeStep = F_datetime.datetime_timeDiff(F_climate.climate_getNextEvapDate(), currentDate);
        if (((R.dgt(timeStep, 0.0d)) && (timeStep < maxStep))) {
            maxStep = timeStep;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            timeStep = F_datetime.datetime_timeDiff(F_gage.gage_getNextRainDate(j, currentDate), currentDate);
            if (((timeStep > 0) && (timeStep < maxStep))) {
                maxStep = timeStep;
            }
        }
        if (((((IsRaining != 0) || (HasSnow != 0)) || (HasRunoff != 0)) || (HasWetLids != 0))) {
            timeStep = F_swmm5.WetStep;
        } else {
            timeStep = F_swmm5.DryStep;
        }
        if ((timeStep > maxStep)) {
            timeStep = maxStep;
        }
        return ((double) (timeStep));
    }

    // runoff.c:335
    static void runoff_initFile() {
        IBox nSubcatch = new IBox(0);
        IBox nPollut = new IBox(0);
        IBox flowUnits = new IBox(0);
        CPtr fileStamp = R.bufFrom(L533, 13);
        CPtr fStamp = R.bufFrom(L533, 13);
        MaxSteps.@v = 0;
        if ((F_swmm5.Frunoff.mode == SAVE_FILE)) {
            nSubcatch.@v = F_swmm5.Nobjects[SUBCATCH];
            nPollut.@v = F_swmm5.Nobjects[POLLUT];
            flowUnits.@v = F_swmm5.FlowUnits;
            R.fwrite(fileStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Frunoff.file);
            R.fwrite1(nSubcatch.v, 4, F_swmm5.Frunoff.file);
            R.fwrite1(nPollut.v, 4, F_swmm5.Frunoff.file);
            R.fwrite1(flowUnits.v, 4, F_swmm5.Frunoff.file);
            MaxStepsPos = R.ftell(F_swmm5.Frunoff.file);
            R.fwrite1(MaxSteps.v, 4, F_swmm5.Frunoff.file);
        }
        if ((F_swmm5.Frunoff.mode == USE_FILE)) {
            R.fread(fStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Frunoff.file);
            if ((R.strcmp(fStamp, fileStamp) != 0)) {
                F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_FORMAT, L0);
                return;
            }
            nSubcatch.@v = (-1);
            nPollut.@v = (-1);
            flowUnits.@v = (-1);
            R.fread1(nSubcatch, 4, F_swmm5.Frunoff.file);
            R.fread1(nPollut, 4, F_swmm5.Frunoff.file);
            R.fread1(flowUnits, 4, F_swmm5.Frunoff.file);
            R.fread1(MaxSteps, 4, F_swmm5.Frunoff.file);
            if (((((nSubcatch.v != F_swmm5.Nobjects[SUBCATCH]) || (nPollut.v != F_swmm5.Nobjects[POLLUT])) || (flowUnits.v != F_swmm5.FlowUnits)) || (MaxSteps.v <= 0))) {
                F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_FORMAT, L0);
            }
        }
    }

    // runoff.c:391
    static void runoff_saveToFile(float __tStep) {
        FBox tStep = new FBox(__tStep);
        int j = 0;
        int n = ((9 + F_swmm5.Nobjects[POLLUT]) - 1);
        R.fwrite1(tStep.v, 4, F_swmm5.Frunoff.file);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_subcatch.subcatch_getResults(j, 1.0d, F_output.SubcatchResults);
            R.fwrite(F_output.SubcatchResults, 0, (int) (n), F_swmm5.Frunoff.file);
        }
    }

    // runoff.c:412
    static void runoff_readFromFile() {
        int i = 0;
        int j = 0;
        int nResults = 0;
        int kount = 0;
        FBox tStep = new FBox(0.0f);
        TGroundwater gw = null;
        if ((runoff_Nsteps.v > MaxSteps.v)) {
            F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_END, L0);
            return;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_subcatch.subcatch_setOldState(j);
        }
        kount = 0;
        kount += ((int) (R.fread1(tStep, 4, F_swmm5.Frunoff.file)));
        nResults = ((9 + F_swmm5.Nobjects[POLLUT]) - 1);
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            kount += ((int) (R.fread(F_output.SubcatchResults, 0, (int) (nResults), F_swmm5.Frunoff.file)));
            F_swmm5.Subcatch[j].@newSnowDepth = (F_output.SubcatchResults[SUBCATCH_SNOWDEPTH] / F_swmm5.UCF(RAINDEPTH));
            F_swmm5.Subcatch[j].@evapLoss = (F_output.SubcatchResults[SUBCATCH_EVAP] / F_swmm5.UCF(RAINFALL));
            F_swmm5.Subcatch[j].@infilLoss = (F_output.SubcatchResults[SUBCATCH_INFIL] / F_swmm5.UCF(RAINFALL));
            F_swmm5.Subcatch[j].@newRunoff = (F_output.SubcatchResults[SUBCATCH_RUNOFF] / F_swmm5.UCF(FLOW));
            gw = F_swmm5.Subcatch[j].groundwater;
            if ((gw != null)) {
                gw.@newFlow = (F_output.SubcatchResults[SUBCATCH_GW_FLOW] / F_swmm5.UCF(FLOW));
                gw.@lowerDepth = (F_swmm5.Aquifer[gw.aquifer].bottomElev - ((F_output.SubcatchResults[SUBCATCH_GW_ELEV] / F_swmm5.UCF(LENGTH))));
                gw.@theta = F_output.SubcatchResults[SUBCATCH_SOIL_MOIST];
            }
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                F_swmm5.Subcatch[j].newQual[i] = F_output.SubcatchResults[(SUBCATCH_WASHOFF + i)];
            }
        }
        if ((kount < (1 + (F_swmm5.Nobjects[SUBCATCH] * nResults)))) {
            F_report.report_writeErrorMsg(ERR_RUNOFF_FILE_READ, L0);
            return;
        }
        F_swmm5.@OldRunoffTime = F_swmm5.NewRunoffTime;
        F_swmm5.@NewRunoffTime = (F_swmm5.OldRunoffTime + ((tStep.v) * 1000.0d));
        F_swmm5.@NewRunoffTime = (((((F_swmm5.NewRunoffTime) <= (F_swmm5.TotalDuration))) ? (F_swmm5.NewRunoffTime) : (F_swmm5.TotalDuration)));
        runoff_Nsteps.@v = runoff_Nsteps.v + 1;
    }

    // runoff.c:491
    static void runoff_getOutfallRunon(double tStep) {
        int i = 0;
        int k = 0;
        int p = 0;
        double w = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nnodes[OUTFALL]); i++) {
            k = F_swmm5.Outfall[i].routeTo;
            if ((k < 0)) {
                continue L_f1;
            }
            if ((F_swmm5.Subcatch[k].area == 0.0d)) {
                continue L_f1;
            }
            F_subcatch.subcatch_addRunonFlow(k, (F_swmm5.Outfall[i].vRouted / tStep));
            F_massbal.massbal_updateRunoffTotals(RUNOFF_RUNON, F_swmm5.Outfall[i].vRouted);
            F_swmm5.Outfall[i].@vRouted = 0.0d;
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                w = (F_swmm5.Outfall[i].wRouted[p] * 28.317d);
                F_massbal.massbal_updateLoadingTotals(DEPOSITION_LOAD, p, (w * F_swmm5.Pollut[p].mcf));
                F_swmm5.Subcatch[k].newQual[p] += (w / tStep);
                F_swmm5.Outfall[i].wRouted[p] = 0.0d;
            }
        }
    }

}
