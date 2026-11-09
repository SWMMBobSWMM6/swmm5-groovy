// Generated from EPA SWMM 5.2.4 (lidproc.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_lidproc {
    public static TLidUnit theLidUnit = null;
    public static TLidProc theLidProc = null;
    public static double lidproc_Tstep = 0.0d;
    public static double lidproc_EvapRate = 0.0d;
    public static double lidproc_MaxNativeInfil = 0.0d;
    public static double SurfaceInflow = 0.0d;
    public static double SurfaceInfil = 0.0d;
    public static double SurfaceEvap = 0.0d;
    public static double SurfaceOutflow = 0.0d;
    public static double SurfaceVolume = 0.0d;
    public static double PaveEvap = 0.0d;
    public static double PavePerc = 0.0d;
    public static double PaveVolume = 0.0d;
    public static double SoilEvap = 0.0d;
    public static double SoilPerc = 0.0d;
    public static double SoilVolume = 0.0d;
    public static double StorageInflow = 0.0d;
    public static double StorageExfil = 0.0d;
    public static double StorageEvap = 0.0d;
    public static double StorageDrain = 0.0d;
    public static double StorageVolume = 0.0d;
    public static double[] Xold = new double[4];

    // lidproc.c:178
    static void lidproc_initWaterBalance(TLidUnit lidUnit, double initVol) {
        lidUnit.waterBalance.@inflow = 0.0d;
        lidUnit.waterBalance.@evap = 0.0d;
        lidUnit.waterBalance.@infil = 0.0d;
        lidUnit.waterBalance.@surfFlow = 0.0d;
        lidUnit.waterBalance.@drainFlow = 0.0d;
        lidUnit.waterBalance.@initVol = initVol;
        lidUnit.waterBalance.@finalVol = initVol;
    }

    // lidproc.c:197
    static double lidproc_getOutflow(TLidUnit lidUnit, TLidProc lidProc, double inflow, double evap, double infil, double maxInfil, double tStep, DRef lidEvap, DRef lidInfil, DRef lidDrain) {
        int i = 0;
        double[] x = new double[4];
        double[] xOld = new double[4];
        double[] xPrev = new double[4];
        double[] xMin = new double[4];
        double[] xMax = new double[4];
        double[] fOld = new double[4];
        double[] f = new double[4];
        double[] xTol = new double[] { 0.00328d, 0.00328d, 0.00328d, 0.00328d };
        double omega = 0.0d;
        FP2 fluxRates = (null);
        theLidProc = lidProc;
        theLidUnit = lidUnit;
        lidproc_EvapRate = evap;
        lidproc_MaxNativeInfil = maxInfil;
        lidproc_Tstep = tStep;
        x[SURF] = theLidUnit.surfaceDepth;
        x[SOIL] = theLidUnit.soilMoisture;
        x[STOR] = theLidUnit.storageDepth;
        x[PAVE] = theLidUnit.paveDepth;
        SurfaceVolume = 0.0d;
        PaveVolume = 0.0d;
        SoilVolume = 0.0d;
        StorageVolume = 0.0d;
        SurfaceInflow = inflow;
        SurfaceInfil = 0.0d;
        SurfaceEvap = 0.0d;
        SurfaceOutflow = 0.0d;
        PaveEvap = 0.0d;
        PavePerc = 0.0d;
        SoilEvap = 0.0d;
        SoilPerc = 0.0d;
        StorageInflow = 0.0d;
        StorageExfil = 0.0d;
        StorageEvap = 0.0d;
        StorageDrain = 0.0d;
        L_f1: for (i = 0; (i < 4); i++) {
            f[i] = 0.0d;
            fOld[i] = theLidUnit.oldFluxRates[i];
            xMin[i] = 0.0d;
            xMax[i] = 10000000000.0d;
            Xold[i] = x[i];
        }
        if ((theLidProc.lidType == POROUS_PAVEMENT)) {
            SurfaceInfil = 0.0d;
        } else if (R.dgt(theLidUnit.soilInfil.Ks, 0.0d)) {
            SurfaceInfil = F_infil.grnampt_getInfil(theLidUnit.soilInfil, lidproc_Tstep, SurfaceInflow, theLidUnit.surfaceDepth, MOD_GREEN_AMPT);
        } else {
            SurfaceInfil = infil;
        }
        if (R.dgt(theLidProc.soil.thickness, 0.0d)) {
            xMin[SOIL] = theLidProc.soil.wiltPoint;
            xMax[SOIL] = theLidProc.soil.porosity;
        }
        if (R.dgt(theLidProc.pavement.thickness, 0.0d)) {
            xMax[PAVE] = theLidProc.pavement.thickness;
        }
        if (R.dgt(theLidProc.storage.thickness, 0.0d)) {
            xMax[STOR] = theLidProc.storage.thickness;
        }
        if ((theLidProc.lidType == GREEN_ROOF)) {
            xMax[STOR] = theLidProc.drainMat.thickness;
        }
        int __sw1 = theLidProc.lidType;
        if (__sw1 == BIO_CELL || __sw1 == RAIN_GARDEN) {
            fluxRates = F_lidproc::biocellFluxRates;
        } else if (__sw1 == GREEN_ROOF) {
            fluxRates = F_lidproc::greenRoofFluxRates;
        } else if (__sw1 == INFIL_TRENCH) {
            fluxRates = F_lidproc::trenchFluxRates;
        } else if (__sw1 == POROUS_PAVEMENT) {
            fluxRates = F_lidproc::pavementFluxRates;
        } else if (__sw1 == RAIN_BARREL) {
            fluxRates = F_lidproc::barrelFluxRates;
        } else if (__sw1 == ROOF_DISCON) {
            fluxRates = F_lidproc::roofFluxRates;
        } else if (__sw1 == VEG_SWALE) {
            fluxRates = F_lidproc::swaleFluxRates;
            omega = 0.5d;
        } else {
            return 0.0d;
        }
        i = modpuls_solve(4, x, xOld, xPrev, xMin, xMax, xTol, fOld, f, tStep, omega, fluxRates);
        if (((theLidProc.surface.canOverflow != 0) || (theLidUnit.fullWidth == 0.0d))) {
            SurfaceOutflow += (getSurfaceOverflowRate(new DElem(x, SURF)));
        }
        theLidUnit.@surfaceDepth = x[SURF];
        theLidUnit.@paveDepth = x[PAVE];
        theLidUnit.@soilMoisture = x[SOIL];
        theLidUnit.@storageDepth = x[STOR];
        L_f3: for (i = 0; (i < 4); i++) {
            theLidUnit.oldFluxRates[i] = f[i];
        }
        lidEvap.set((((SurfaceEvap + PaveEvap) + SoilEvap) + StorageEvap));
        lidInfil.set(StorageExfil);
        lidDrain.set(StorageDrain);
        return SurfaceOutflow;
    }

    // lidproc.c:360
    static void lidproc_saveResults(TLidUnit lidUnit, double ucfRainfall, double ucfRainDepth) {
        double ucf = 0.0d;
        double totalEvap = 0.0d;
        double totalVolume = 0.0d;
        double[] rptVars = new double[12];
        int isDry = 0;
        CPtr timeStamp = R.buf(22);
        double elapsedHrs = 0.0d;
        totalEvap = (((SurfaceEvap + PaveEvap) + SoilEvap) + StorageEvap);
        totalVolume = (((SurfaceVolume + PaveVolume) + SoilVolume) + StorageVolume);
        updateWaterBalance(theLidUnit, SurfaceInflow, totalEvap, StorageExfil, SurfaceOutflow, StorageDrain, totalVolume);
        if ((((((SurfaceInflow < 2.3e-08d) && (SurfaceOutflow < 2.3e-08d)) && (StorageDrain < 2.3e-08d)) && (StorageExfil < 2.3e-08d)) && (totalEvap < 2.3e-08d))) {
            isDry = 1;
        }
        if (!(isDry != 0)) {
            F_runoff.@HasWetLids = 1;
        }
        if ((lidUnit.rptFile != null)) {
            ucf = ucfRainfall;
            rptVars[SURF_INFLOW] = (SurfaceInflow * ucf);
            rptVars[TOTAL_EVAP] = (totalEvap * ucf);
            rptVars[SURF_INFIL] = (SurfaceInfil * ucf);
            rptVars[PAVE_PERC] = (PavePerc * ucf);
            rptVars[SOIL_PERC] = (SoilPerc * ucf);
            rptVars[STOR_EXFIL] = (StorageExfil * ucf);
            rptVars[SURF_OUTFLOW] = (SurfaceOutflow * ucf);
            rptVars[STOR_DRAIN] = (StorageDrain * ucf);
            ucf = ucfRainDepth;
            rptVars[SURF_DEPTH] = (theLidUnit.surfaceDepth * ucf);
            rptVars[PAVE_DEPTH] = (theLidUnit.paveDepth * ucf);
            rptVars[SOIL_MOIST] = theLidUnit.soilMoisture;
            rptVars[STOR_DEPTH] = (theLidUnit.storageDepth * ucf);
            if (((!(isDry != 0)) && (theLidUnit.rptFile.wasDry > 1))) {
                R.fprintf(theLidUnit.rptFile.file, L169, theLidUnit.rptFile.results);
            }
            elapsedHrs = ((F_swmm5.NewRunoffTime / 1000.0d) / 3600.0d);
            F_datetime.datetime_getTimeStamp(1, F_swmm5.getDateTime(F_swmm5.NewRunoffTime), 21, timeStamp);
            R.snprintf(theLidUnit.rptFile.results, (int) (256L), L304, timeStamp, elapsedHrs, rptVars[0], rptVars[1], rptVars[2], rptVars[3], rptVars[4], rptVars[5], rptVars[6], rptVars[7], rptVars[8], rptVars[9], rptVars[10], rptVars[11]);
            if ((isDry != 0)) {
                if ((theLidUnit.rptFile.wasDry == 0)) {
                    R.fprintf(theLidUnit.rptFile.file, L169, theLidUnit.rptFile.results);
                }
                theLidUnit.rptFile.@wasDry = theLidUnit.rptFile.wasDry + 1;
            } else {
                R.fprintf(theLidUnit.rptFile.file, L169, theLidUnit.rptFile.results);
                theLidUnit.rptFile.@wasDry = 0;
            }
        }
    }

    // lidproc.c:469
    static void roofFluxRates(double[] x, double[] f) {
        DBox surfaceDepth = new DBox(x[SURF]);
        lidproc_getEvapRates(surfaceDepth.v, 0.0d, 0.0d, 0.0d, 1.0d);
        SurfaceVolume = surfaceDepth.v;
        SurfaceInfil = 0.0d;
        if (R.dgt(theLidProc.surface.alpha, 0.0d)) {
            SurfaceOutflow = getSurfaceOutflowRate(surfaceDepth.v);
        } else {
            getSurfaceOverflowRate(surfaceDepth);
        }
        StorageDrain = ((((((theLidProc.drain.coeff / F_swmm5.UCF(RAINFALL))) <= (SurfaceOutflow))) ? ((theLidProc.drain.coeff / F_swmm5.UCF(RAINFALL))) : (SurfaceOutflow)));
        SurfaceOutflow -= StorageDrain;
        f[SURF] = ((((SurfaceInflow - SurfaceEvap) - StorageDrain) - SurfaceOutflow));
    }

    // lidproc.c:491
    static void greenRoofFluxRates(double[] x, double[] f) {
        double surfaceDepth = 0.0d;
        double soilTheta = 0.0d;
        double storageDepth = 0.0d;
        double availVolume = 0.0d;
        double maxRate = 0.0d;
        double soilThickness = theLidProc.soil.thickness;
        double storageThickness = theLidProc.storage.thickness;
        double soilPorosity = theLidProc.soil.porosity;
        double storageVoidFrac = theLidProc.storage.voidFrac;
        double soilFieldCap = theLidProc.soil.fieldCap;
        double soilWiltPoint = theLidProc.soil.wiltPoint;
        surfaceDepth = x[SURF];
        soilTheta = x[SOIL];
        storageDepth = x[STOR];
        SurfaceVolume = (surfaceDepth * theLidProc.surface.voidFrac);
        SoilVolume = (soilTheta * soilThickness);
        StorageVolume = (storageDepth * storageVoidFrac);
        availVolume = (SoilVolume - (soilWiltPoint * soilThickness));
        lidproc_getEvapRates(SurfaceVolume, 0.0d, availVolume, StorageVolume, 1.0d);
        if (R.dge(soilTheta, soilPorosity)) {
            StorageEvap = 0.0d;
        }
        SoilPerc = getSoilPercRate(soilTheta);
        availVolume = (((soilTheta - soilFieldCap)) * soilThickness);
        maxRate = (((((R.dge((availVolume), (0.0d))) ? (availVolume) : (0.0d))) / lidproc_Tstep) - SoilEvap);
        SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
        SoilPerc = (((R.dge((SoilPerc), (0.0d))) ? (SoilPerc) : (0.0d)));
        StorageExfil = 0.0d;
        StorageDrain = getDrainMatOutflow(storageDepth);
        if (((R.dge(soilTheta, soilPorosity)) && (R.dge(storageDepth, storageThickness)))) {
            maxRate = (((((SoilPerc) <= (StorageDrain))) ? (SoilPerc) : (StorageDrain)));
            SoilPerc = maxRate;
            StorageDrain = maxRate;
            SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
        } else {
            maxRate = (((storageDepth * storageVoidFrac) / lidproc_Tstep) - StorageEvap);
            if (R.dge(storageDepth, storageThickness)) {
                maxRate += SoilPerc;
            }
            maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
            StorageDrain = (((((StorageDrain) <= (maxRate))) ? (StorageDrain) : (maxRate)));
            maxRate = ((((((storageThickness - storageDepth)) * storageVoidFrac) / lidproc_Tstep) + StorageDrain) + StorageEvap);
            SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
            maxRate = ((((((soilPorosity - soilTheta)) * soilThickness) / lidproc_Tstep) + SoilPerc) + SoilEvap);
            SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
        }
        SurfaceOutflow = getSurfaceOutflowRate(surfaceDepth);
        f[SURF] = (((((SurfaceInflow - SurfaceEvap) - SurfaceInfil) - SurfaceOutflow)) / theLidProc.surface.voidFrac);
        f[SOIL] = ((((SurfaceInfil - SoilEvap) - SoilPerc)) / theLidProc.soil.thickness);
        f[STOR] = ((((SoilPerc - StorageEvap) - StorageDrain)) / theLidProc.storage.voidFrac);
    }

    // lidproc.c:589
    static void biocellFluxRates(double[] x, double[] f) {
        double surfaceDepth = 0.0d;
        double soilTheta = 0.0d;
        double storageDepth = 0.0d;
        double availVolume = 0.0d;
        double maxRate = 0.0d;
        double soilThickness = theLidProc.soil.thickness;
        double soilPorosity = theLidProc.soil.porosity;
        double soilFieldCap = theLidProc.soil.fieldCap;
        double soilWiltPoint = theLidProc.soil.wiltPoint;
        double storageThickness = theLidProc.storage.thickness;
        double storageVoidFrac = theLidProc.storage.voidFrac;
        surfaceDepth = x[SURF];
        soilTheta = x[SOIL];
        storageDepth = x[STOR];
        SurfaceVolume = (surfaceDepth * theLidProc.surface.voidFrac);
        SoilVolume = (soilTheta * soilThickness);
        StorageVolume = (storageDepth * storageVoidFrac);
        availVolume = (SoilVolume - (soilWiltPoint * soilThickness));
        lidproc_getEvapRates(SurfaceVolume, 0.0d, availVolume, StorageVolume, 1.0d);
        if (R.dge(soilTheta, soilPorosity)) {
            StorageEvap = 0.0d;
        }
        SoilPerc = getSoilPercRate(soilTheta);
        availVolume = (((soilTheta - soilFieldCap)) * soilThickness);
        maxRate = (((((R.dge((availVolume), (0.0d))) ? (availVolume) : (0.0d))) / lidproc_Tstep) - SoilEvap);
        SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
        SoilPerc = (((R.dge((SoilPerc), (0.0d))) ? (SoilPerc) : (0.0d)));
        StorageExfil = getStorageExfilRate();
        StorageDrain = 0.0d;
        if (R.dgt(theLidProc.drain.coeff, 0.0d)) {
            StorageDrain = getStorageDrainRate(storageDepth, soilTheta, 0.0d, surfaceDepth);
        }
        if ((storageThickness == 0.0d)) {
            StorageEvap = 0.0d;
            maxRate = (((((SoilPerc) <= (StorageExfil))) ? (SoilPerc) : (StorageExfil)));
            SoilPerc = maxRate;
            StorageExfil = maxRate;
            maxRate = ((((((soilPorosity - soilTheta)) * soilThickness) / lidproc_Tstep) + SoilPerc) + SoilEvap);
            SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
        } else {
            if (((R.dge(soilTheta, soilPorosity)) && (R.dge(storageDepth, storageThickness)))) {
                maxRate = (StorageExfil + StorageDrain);
                if ((SoilPerc < maxRate)) {
                    maxRate = SoilPerc;
                    if (R.dgt(maxRate, StorageExfil)) {
                        StorageDrain = (maxRate - StorageExfil);
                    } else {
                        StorageExfil = maxRate;
                        StorageDrain = 0.0d;
                    }
                } else {
                    SoilPerc = maxRate;
                }
                SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
            } else {
                maxRate = ((SoilPerc - StorageEvap) + ((storageDepth * storageVoidFrac) / lidproc_Tstep));
                StorageExfil = (((((StorageExfil) <= (maxRate))) ? (StorageExfil) : (maxRate)));
                StorageExfil = (((R.dge((StorageExfil), (0.0d))) ? (StorageExfil) : (0.0d)));
                if (R.dgt(StorageDrain, 0.0d)) {
                    maxRate = ((-StorageExfil) - StorageEvap);
                    if (R.dge(storageDepth, storageThickness)) {
                        maxRate += SoilPerc;
                    }
                    if ((theLidProc.drain.offset <= storageDepth)) {
                        maxRate += ((((storageDepth - theLidProc.drain.offset)) * storageVoidFrac) / lidproc_Tstep);
                    }
                    maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
                    StorageDrain = (((((StorageDrain) <= (maxRate))) ? (StorageDrain) : (maxRate)));
                }
                maxRate = (((StorageExfil + StorageDrain) + StorageEvap) + ((((storageThickness - storageDepth)) * storageVoidFrac) / lidproc_Tstep));
                SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
                maxRate = ((((((soilPorosity - soilTheta)) * soilThickness) / lidproc_Tstep) + SoilPerc) + SoilEvap);
                SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
            }
        }
        SurfaceOutflow = getSurfaceOutflowRate(surfaceDepth);
        f[SURF] = (((((SurfaceInflow - SurfaceEvap) - SurfaceInfil) - SurfaceOutflow)) / theLidProc.surface.voidFrac);
        f[SOIL] = ((((SurfaceInfil - SoilEvap) - SoilPerc)) / theLidProc.soil.thickness);
        if ((storageThickness == 0.0d)) {
            f[STOR] = 0.0d;
        } else {
            f[STOR] = (((((SoilPerc - StorageEvap) - StorageExfil) - StorageDrain)) / theLidProc.storage.voidFrac);
        }
    }

    // lidproc.c:735
    static void trenchFluxRates(double[] x, double[] f) {
        double surfaceDepth = 0.0d;
        double storageDepth = 0.0d;
        double availVolume = 0.0d;
        double maxRate = 0.0d;
        double storageThickness = theLidProc.storage.thickness;
        double storageVoidFrac = theLidProc.storage.voidFrac;
        surfaceDepth = x[SURF];
        storageDepth = x[STOR];
        SurfaceVolume = (surfaceDepth * theLidProc.surface.voidFrac);
        SoilVolume = 0.0d;
        StorageVolume = (storageDepth * storageVoidFrac);
        availVolume = (((storageThickness - storageDepth)) * storageVoidFrac);
        lidproc_getEvapRates(SurfaceVolume, 0.0d, 0.0d, StorageVolume, 1.0d);
        if (R.dgt(surfaceDepth, 0.0d)) {
            StorageEvap = 0.0d;
        }
        StorageInflow = (SurfaceInflow + (SurfaceVolume / lidproc_Tstep));
        StorageExfil = getStorageExfilRate();
        StorageDrain = 0.0d;
        if (R.dgt(theLidProc.drain.coeff, 0.0d)) {
            StorageDrain = getStorageDrainRate(storageDepth, 0.0d, 0.0d, surfaceDepth);
        }
        maxRate = ((StorageInflow - StorageEvap) + ((storageDepth * storageVoidFrac) / lidproc_Tstep));
        StorageExfil = (((((StorageExfil) <= (maxRate))) ? (StorageExfil) : (maxRate)));
        StorageExfil = (((R.dge((StorageExfil), (0.0d))) ? (StorageExfil) : (0.0d)));
        if (R.dgt(StorageDrain, 0.0d)) {
            maxRate = ((-StorageExfil) - StorageEvap);
            if (R.dge(storageDepth, storageThickness)) {
                maxRate += StorageInflow;
            }
            if ((theLidProc.drain.offset <= storageDepth)) {
                maxRate += ((((storageDepth - theLidProc.drain.offset)) * storageVoidFrac) / lidproc_Tstep);
            }
            maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
            StorageDrain = (((((StorageDrain) <= (maxRate))) ? (StorageDrain) : (maxRate)));
        }
        maxRate = (((((((storageThickness - storageDepth)) * storageVoidFrac) / lidproc_Tstep) + StorageExfil) + StorageEvap) + StorageDrain);
        StorageInflow = (((((StorageInflow) <= (maxRate))) ? (StorageInflow) : (maxRate)));
        SurfaceInfil = StorageInflow;
        SurfaceOutflow = getSurfaceOutflowRate(surfaceDepth);
        f[SURF] = (((((SurfaceInflow - SurfaceEvap) - StorageInflow) - SurfaceOutflow)) / theLidProc.surface.voidFrac);
        f[STOR] = (((((StorageInflow - StorageEvap) - StorageExfil) - StorageDrain)) / theLidProc.storage.voidFrac);
        f[SOIL] = 0.0d;
    }

    // lidproc.c:823
    static void pavementFluxRates(double[] x, double[] f) {
        double surfaceDepth = 0.0d;
        double paveDepth = 0.0d;
        double soilTheta = 0.0d;
        double storageDepth = 0.0d;
        double pervFrac = ((1.0d - theLidProc.pavement.impervFrac));
        double storageInflow = 0.0d;
        double availVolume = 0.0d;
        double maxRate = 0.0d;
        double paveVoidFrac = (theLidProc.pavement.voidFrac * pervFrac);
        double paveThickness = theLidProc.pavement.thickness;
        double soilThickness = theLidProc.soil.thickness;
        double soilPorosity = theLidProc.soil.porosity;
        double soilFieldCap = theLidProc.soil.fieldCap;
        double soilWiltPoint = theLidProc.soil.wiltPoint;
        double storageThickness = theLidProc.storage.thickness;
        double storageVoidFrac = theLidProc.storage.voidFrac;
        surfaceDepth = x[SURF];
        paveDepth = x[PAVE];
        soilTheta = x[SOIL];
        storageDepth = x[STOR];
        SurfaceVolume = (surfaceDepth * theLidProc.surface.voidFrac);
        PaveVolume = (paveDepth * paveVoidFrac);
        SoilVolume = (soilTheta * soilThickness);
        StorageVolume = (storageDepth * storageVoidFrac);
        availVolume = (SoilVolume - (soilWiltPoint * soilThickness));
        lidproc_getEvapRates(SurfaceVolume, PaveVolume, availVolume, StorageVolume, pervFrac);
        if (((R.dge(paveDepth, paveThickness)) || (((R.dgt(soilThickness, 0.0d)) && (R.dge(soilTheta, soilPorosity)))))) {
            StorageEvap = 0.0d;
        }
        SurfaceInfil = (SurfaceInflow + ((SurfaceVolume / lidproc_Tstep)));
        PavePerc = (getPavementPermRate() * pervFrac);
        SurfaceInfil = (((((SurfaceInfil) <= (PavePerc))) ? (SurfaceInfil) : (PavePerc)));
        maxRate = (((PaveVolume / lidproc_Tstep) + SurfaceInfil) - PaveEvap);
        maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
        PavePerc = (((((PavePerc) <= (maxRate))) ? (PavePerc) : (maxRate)));
        if (R.dgt(soilThickness, 0.0d)) {
            SoilPerc = getSoilPercRate(soilTheta);
            availVolume = (((soilTheta - soilFieldCap)) * soilThickness);
            maxRate = (((((R.dge((availVolume), (0.0d))) ? (availVolume) : (0.0d))) / lidproc_Tstep) - SoilEvap);
            SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
            SoilPerc = (((R.dge((SoilPerc), (0.0d))) ? (SoilPerc) : (0.0d)));
        } else {
            SoilPerc = PavePerc;
        }
        StorageExfil = getStorageExfilRate();
        StorageDrain = 0.0d;
        if (R.dgt(theLidProc.drain.coeff, 0.0d)) {
            StorageDrain = getStorageDrainRate(storageDepth, soilTheta, paveDepth, surfaceDepth);
        }
        if ((((soilThickness == 0.0d) && (R.dge(storageDepth, storageThickness))) && (R.dge(paveDepth, paveThickness)))) {
            maxRate = ((StorageEvap + StorageDrain) + StorageExfil);
            if (R.dgt(PavePerc, maxRate)) {
                PavePerc = maxRate;
            } else {
                StorageExfil = (((((StorageExfil) <= (PavePerc))) ? (StorageExfil) : (PavePerc)));
                StorageDrain = (PavePerc - StorageExfil);
            }
            SoilPerc = PavePerc;
            SurfaceInfil = (((((SurfaceInfil) <= (PavePerc))) ? (SurfaceInfil) : (PavePerc)));
        } else if (((((R.dgt(soilThickness, 0)) && (R.dge(storageDepth, storageThickness))) && (R.dge(soilTheta, soilPorosity))) && (R.dge(paveDepth, paveThickness)))) {
            maxRate = (StorageExfil + StorageDrain);
            if ((SoilPerc < maxRate)) {
                maxRate = SoilPerc;
            } else {
                maxRate = (((((maxRate) <= (PavePerc))) ? (maxRate) : (PavePerc)));
            }
            if (R.dgt(maxRate, StorageExfil)) {
                StorageDrain = (maxRate - StorageExfil);
            } else {
                StorageExfil = maxRate;
                StorageDrain = 0.0d;
            }
            SoilPerc = maxRate;
            PavePerc = maxRate;
            SurfaceInfil = (((((SurfaceInfil) <= (PavePerc))) ? (SurfaceInfil) : (PavePerc)));
        } else if ((((R.dgt(soilThickness, 0.0d)) && (R.dge(storageDepth, storageThickness))) && (R.dge(soilTheta, soilPorosity)))) {
            maxRate = (StorageDrain + StorageExfil);
            if (R.dgt(SoilPerc, maxRate)) {
                SoilPerc = maxRate;
            } else {
                StorageExfil = (((((StorageExfil) <= (SoilPerc))) ? (StorageExfil) : (SoilPerc)));
                StorageDrain = (SoilPerc - StorageExfil);
            }
            PavePerc = (((((PavePerc) <= (SoilPerc))) ? (PavePerc) : (SoilPerc)));
            availVolume = (((paveThickness - paveDepth)) * paveVoidFrac);
            maxRate = (((availVolume / lidproc_Tstep) + PavePerc) + PaveEvap);
            SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
        } else if ((((R.dgt(soilThickness, 0.0d)) && (R.dge(paveDepth, paveThickness))) && (R.dge(soilTheta, soilPorosity)))) {
            PavePerc = (((((PavePerc) <= (SoilPerc))) ? (PavePerc) : (SoilPerc)));
            SoilPerc = PavePerc;
            SurfaceInfil = (((((SurfaceInfil) <= (PavePerc))) ? (SurfaceInfil) : (PavePerc)));
            maxRate = (((R.dge(((((StorageVolume / lidproc_Tstep) + SoilPerc) - StorageEvap)), (0.0d))) ? ((((StorageVolume / lidproc_Tstep) + SoilPerc) - StorageEvap)) : (0.0d)));
            StorageExfil = (((((StorageExfil) <= (maxRate))) ? (StorageExfil) : (maxRate)));
        } else {
            maxRate = ((SoilPerc - StorageEvap) + (StorageVolume / lidproc_Tstep));
            maxRate = (((R.dge((0.0d), (maxRate))) ? (0.0d) : (maxRate)));
            StorageExfil = (((((StorageExfil) <= (maxRate))) ? (StorageExfil) : (maxRate)));
            if (R.dgt(StorageDrain, 0.0d)) {
                maxRate = ((-StorageExfil) - StorageEvap);
                if (R.dge(storageDepth, storageThickness)) {
                    maxRate += SoilPerc;
                }
                if ((theLidProc.drain.offset <= storageDepth)) {
                    maxRate += ((((storageDepth - theLidProc.drain.offset)) * storageVoidFrac) / lidproc_Tstep);
                }
                maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
                StorageDrain = (((((StorageDrain) <= (maxRate))) ? (StorageDrain) : (maxRate)));
            }
            availVolume = (((storageThickness - storageDepth)) * storageVoidFrac);
            maxRate = ((((availVolume / lidproc_Tstep) + StorageEvap) + StorageDrain) + StorageExfil);
            maxRate = (((R.dge((maxRate), (0.0d))) ? (maxRate) : (0.0d)));
            if (R.dgt(soilThickness, 0.0d)) {
                SoilPerc = (((((SoilPerc) <= (maxRate))) ? (SoilPerc) : (maxRate)));
                maxRate = (((((soilPorosity - soilTheta)) * soilThickness) / lidproc_Tstep) + SoilPerc);
            }
            PavePerc = (((((PavePerc) <= (maxRate))) ? (PavePerc) : (maxRate)));
            availVolume = (((paveThickness - paveDepth)) * paveVoidFrac);
            maxRate = (((availVolume / lidproc_Tstep) + PavePerc) + PaveEvap);
            SurfaceInfil = (((((SurfaceInfil) <= (maxRate))) ? (SurfaceInfil) : (maxRate)));
        }
        SurfaceOutflow = getSurfaceOutflowRate(surfaceDepth);
        f[SURF] = (((SurfaceInflow - SurfaceEvap) - SurfaceInfil) - SurfaceOutflow);
        f[PAVE] = ((((SurfaceInfil - PaveEvap) - PavePerc)) / paveVoidFrac);
        if (R.dgt(theLidProc.soil.thickness, 0.0d)) {
            f[SOIL] = ((((PavePerc - SoilEvap) - SoilPerc)) / soilThickness);
            storageInflow = SoilPerc;
        } else {
            f[SOIL] = 0.0d;
            storageInflow = PavePerc;
            SoilPerc = 0.0d;
        }
        f[STOR] = (((((storageInflow - StorageEvap) - StorageExfil) - StorageDrain)) / storageVoidFrac);
    }

    // lidproc.c:1061
    static void swaleFluxRates(double[] x, double[] f) {
        double depth = 0.0d;
        double topWidth = 0.0d;
        double botWidth = 0.0d;
        double length = 0.0d;
        double surfInflow = 0.0d;
        double surfWidth = 0.0d;
        double surfArea = 0.0d;
        double flowArea = 0.0d;
        double lidArea = 0.0d;
        double hydRadius = 0.0d;
        double slope = 0.0d;
        double volume = 0.0d;
        double dVdT = 0.0d;
        double dStore = 0.0d;
        double xDepth = 0.0d;
        depth = x[SURF];
        depth = (((((depth) <= (theLidProc.surface.thickness))) ? (depth) : (theLidProc.surface.thickness)));
        dStore = 0.0d;
        slope = theLidProc.surface.sideSlope;
        topWidth = theLidUnit.fullWidth;
        topWidth = (((R.dge((topWidth), (0.5d))) ? (topWidth) : (0.5d)));
        botWidth = (topWidth - ((2.0d * slope) * theLidProc.surface.thickness));
        if ((botWidth < 0.5d)) {
            botWidth = 0.5d;
            slope = ((0.5d * ((topWidth - 0.5d))) / theLidProc.surface.thickness);
        }
        lidArea = theLidUnit.area;
        length = (lidArea / topWidth);
        surfWidth = (botWidth + ((2.0d * slope) * depth));
        surfArea = (length * surfWidth);
        flowArea = (((depth * ((botWidth + (slope * depth))))) * theLidProc.surface.voidFrac);
        volume = (length * flowArea);
        surfInflow = (SurfaceInflow * lidArea);
        SurfaceEvap = (lidproc_EvapRate * surfArea);
        SurfaceEvap = (((((SurfaceEvap) <= ((volume / lidproc_Tstep)))) ? (SurfaceEvap) : ((volume / lidproc_Tstep))));
        StorageExfil = (SurfaceInfil * surfArea);
        xDepth = (depth - dStore);
        if ((xDepth <= 1e-10d)) {
            SurfaceOutflow = 0.0d;
        } else {
            flowArea -= (((dStore * ((botWidth + (slope * dStore))))) * theLidProc.surface.voidFrac);
            if ((flowArea < 1e-10d)) {
                SurfaceOutflow = 0.0d;
            } else {
                botWidth = (botWidth + ((2.0d * dStore) * slope));
                hydRadius = (botWidth + ((2.0d * xDepth) * Math.sqrt((1.0d + (slope * slope)))));
                hydRadius = (flowArea / hydRadius);
                SurfaceOutflow = ((theLidProc.surface.alpha * flowArea) * Glibm.pow(hydRadius, (2.0d / 3.0d)));
            }
        }
        dVdT = (((surfInflow - SurfaceEvap) - StorageExfil) - SurfaceOutflow);
        if (((depth == theLidProc.surface.thickness) && (R.dgt(dVdT, 0.0d)))) {
            SurfaceOutflow += dVdT;
            dVdT = 0.0d;
        }
        SurfaceEvap = (SurfaceEvap / lidArea);
        StorageExfil = (StorageExfil / lidArea);
        SurfaceOutflow = (SurfaceOutflow / lidArea);
        f[SURF] = (dVdT / surfArea);
        f[SOIL] = 0.0d;
        f[STOR] = 0.0d;
        SurfaceVolume = (volume / lidArea);
        SoilVolume = 0.0d;
        StorageVolume = 0.0d;
    }

    // lidproc.c:1176
    static void barrelFluxRates(double[] x, double[] f) {
        double storageDepth = x[STOR];
        double head = 0.0d;
        double maxValue = 0.0d;
        SurfaceVolume = 0.0d;
        SoilVolume = 0.0d;
        StorageVolume = storageDepth;
        SurfaceInfil = 0.0d;
        SurfaceOutflow = 0.0d;
        StorageDrain = 0.0d;
        if (((theLidProc.drain.delay == 0.0d) || (R.dge(theLidUnit.dryTime, theLidProc.drain.delay)))) {
            head = (storageDepth - theLidProc.drain.offset);
            if (R.dgt(head, 0.0d)) {
                StorageDrain = getStorageDrainRate(storageDepth, 0.0d, 0.0d, 0.0d);
                maxValue = ((head / lidproc_Tstep));
                StorageDrain = (((((StorageDrain) <= (maxValue))) ? (StorageDrain) : (maxValue)));
            }
        }
        StorageInflow = SurfaceInflow;
        maxValue = ((((theLidProc.storage.thickness - storageDepth)) / lidproc_Tstep) + StorageDrain);
        StorageInflow = (((((StorageInflow) <= (maxValue))) ? (StorageInflow) : (maxValue)));
        SurfaceInfil = StorageInflow;
        f[SURF] = (SurfaceInflow - StorageInflow);
        f[STOR] = (StorageInflow - StorageDrain);
        f[SOIL] = 0.0d;
    }

    // lidproc.c:1226
    static double getSurfaceOutflowRate(double depth) {
        double delta = 0.0d;
        double outflow = 0.0d;
        delta = (depth - theLidProc.surface.thickness);
        if ((delta < 0.0d)) {
            return 0.0d;
        }
        outflow = (((theLidProc.surface.alpha * Glibm.pow(delta, (5.0d / 3.0d))) * theLidUnit.fullWidth) / theLidUnit.area);
        outflow = (((((outflow) <= ((delta / lidproc_Tstep)))) ? (outflow) : ((delta / lidproc_Tstep))));
        return outflow;
    }

    // lidproc.c:1251
    static double getPavementPermRate() {
        double permReduction = 0.0d;
        double clogFactor = theLidProc.pavement.clogFactor;
        double regenDays = theLidProc.pavement.regenDays;
        if (R.dgt(clogFactor, 0.0d)) {
            if (R.dgt(regenDays, 0.0d)) {
                if (R.dge(((F_swmm5.OldRunoffTime / 1000.0d) / 86400), theLidUnit.nextRegenDay)) {
                    theLidUnit.@volTreated = (theLidUnit.volTreated * ((1.0d - theLidProc.pavement.regenDegree)));
                    theLidUnit.@nextRegenDay = (theLidUnit.nextRegenDay + regenDays);
                }
            }
            permReduction = (theLidUnit.volTreated / clogFactor);
            permReduction = (((((permReduction) <= (1.0d))) ? (permReduction) : (1.0d)));
        }
        return (theLidProc.pavement.kSat * ((1.0d - permReduction)));
    }

    // lidproc.c:1293
    static double getSoilPercRate(double theta) {
        double delta = 0.0d;
        if ((theta <= theLidProc.soil.fieldCap)) {
            return 0.0d;
        }
        delta = (theLidProc.soil.porosity - theta);
        return (theLidProc.soil.kSat * Glibm.exp(((-delta) * theLidProc.soil.kSlope)));
    }

    // lidproc.c:1313
    static double getStorageExfilRate() {
        double infil = 0.0d;
        double clogFactor = 0.0d;
        if ((theLidProc.storage.kSat == 0.0d)) {
            return 0.0d;
        }
        if ((lidproc_MaxNativeInfil == 0.0d)) {
            return 0.0d;
        }
        clogFactor = theLidProc.storage.clogFactor;
        if (R.dgt(clogFactor, 0.0d)) {
            clogFactor = (theLidUnit.waterBalance.inflow / clogFactor);
            clogFactor = (((((clogFactor) <= (1.0d))) ? (clogFactor) : (1.0d)));
        }
        infil = (theLidProc.storage.kSat * ((1.0d - clogFactor)));
        return (((((infil) <= (lidproc_MaxNativeInfil))) ? (infil) : (lidproc_MaxNativeInfil)));
    }

    // lidproc.c:1344
    static double getStorageDrainRate(double storageDepth, double soilTheta, double paveDepth, double surfaceDepth) {
        int curve = theLidProc.drain.qCurve;
        double head = storageDepth;
        double outflow = 0.0d;
        double paveThickness = theLidProc.pavement.thickness;
        double soilThickness = theLidProc.soil.thickness;
        double soilPorosity = theLidProc.soil.porosity;
        double soilFieldCap = theLidProc.soil.fieldCap;
        double storageThickness = theLidProc.storage.thickness;
        if (R.dge(storageDepth, storageThickness)) {
            if (R.dgt(soilThickness, 0.0d)) {
                if (R.dgt(soilTheta, soilFieldCap)) {
                    head += ((((soilTheta - soilFieldCap)) / ((soilPorosity - soilFieldCap))) * soilThickness);
                    if (R.dge(soilTheta, soilPorosity)) {
                        if (R.dgt(paveThickness, 0.0d)) {
                            head += paveDepth;
                            if (R.dge(paveDepth, paveThickness)) {
                                head += surfaceDepth;
                            }
                        } else {
                            head += surfaceDepth;
                        }
                    }
                }
            } else if (R.dgt(paveThickness, 0.0d)) {
                head += paveDepth;
                if (R.dge(paveDepth, paveThickness)) {
                    head += surfaceDepth;
                }
            }
        }
        if (((theLidUnit.oldDrainFlow == 0.0d) && (head <= theLidProc.drain.hOpen))) {
            return 0.0d;
        }
        if (((R.dgt(theLidUnit.oldDrainFlow, 0.0d)) && (head <= theLidProc.drain.hClose))) {
            return 0.0d;
        }
        head -= theLidProc.drain.offset;
        if (R.dgt(head, 1e-10d)) {
            head *= (F_swmm5.UCF(RAINDEPTH));
            outflow = (theLidProc.drain.coeff * Glibm.pow(head, theLidProc.drain.expon));
            if ((curve >= 0)) {
                outflow *= (F_table.table_lookup(F_swmm5.Curve[curve], head));
            }
            outflow = (outflow / (F_swmm5.UCF(RAINFALL)));
        }
        return outflow;
    }

    // lidproc.c:1436
    static double getDrainMatOutflow(double depth) {
        double result = SoilPerc;
        if (R.dgt(theLidProc.drainMat.alpha, 0.0d)) {
            result = ((((theLidProc.drainMat.alpha * Glibm.pow(depth, (5.0d / 3.0d))) * theLidUnit.fullWidth) / theLidUnit.area) * theLidProc.drainMat.voidFrac);
        }
        return result;
    }

    // lidproc.c:1458
    static void lidproc_getEvapRates(double surfaceVol, double paveVol, double soilVol, double storageVol, double pervFrac) {
        double availEvap = 0.0d;
        availEvap = lidproc_EvapRate;
        SurfaceEvap = (((((availEvap) <= ((surfaceVol / lidproc_Tstep)))) ? (availEvap) : ((surfaceVol / lidproc_Tstep))));
        SurfaceEvap = (((R.dge((0.0d), (SurfaceEvap))) ? (0.0d) : (SurfaceEvap)));
        availEvap = (((R.dge((0.0d), (((availEvap - SurfaceEvap))))) ? (0.0d) : (((availEvap - SurfaceEvap)))));
        availEvap *= pervFrac;
        if (R.dgt(SurfaceInfil, 0.0d)) {
            PaveEvap = 0.0d;
            SoilEvap = 0.0d;
            StorageEvap = 0.0d;
        } else {
            PaveEvap = (((((availEvap) <= ((paveVol / lidproc_Tstep)))) ? (availEvap) : ((paveVol / lidproc_Tstep))));
            availEvap = (((R.dge((0.0d), (((availEvap - PaveEvap))))) ? (0.0d) : (((availEvap - PaveEvap)))));
            SoilEvap = (((((availEvap) <= ((soilVol / lidproc_Tstep)))) ? (availEvap) : ((soilVol / lidproc_Tstep))));
            availEvap = (((R.dge((0.0d), (((availEvap - SoilEvap))))) ? (0.0d) : (((availEvap - SoilEvap)))));
            StorageEvap = (((((availEvap) <= ((storageVol / lidproc_Tstep)))) ? (availEvap) : ((storageVol / lidproc_Tstep))));
        }
    }

    // lidproc.c:1503
    static double getSurfaceOverflowRate(DRef surfaceDepth) {
        double delta = (surfaceDepth.get() - theLidProc.surface.thickness);
        if ((delta <= 0.0d)) {
            return 0.0d;
        }
        surfaceDepth.set(theLidProc.surface.thickness);
        return ((delta * theLidProc.surface.voidFrac) / lidproc_Tstep);
    }

    // lidproc.c:1518
    static void updateWaterBalance(TLidUnit lidUnit, double inflow, double evap, double infil, double surfFlow, double drainFlow, double storage) {
        lidUnit.@volTreated = (lidUnit.volTreated + (inflow * lidproc_Tstep));
        lidUnit.waterBalance.@inflow = (lidUnit.waterBalance.inflow + (inflow * lidproc_Tstep));
        lidUnit.waterBalance.@evap = (lidUnit.waterBalance.evap + (evap * lidproc_Tstep));
        lidUnit.waterBalance.@infil = (lidUnit.waterBalance.infil + (infil * lidproc_Tstep));
        lidUnit.waterBalance.@surfFlow = (lidUnit.waterBalance.surfFlow + (surfFlow * lidproc_Tstep));
        lidUnit.waterBalance.@drainFlow = (lidUnit.waterBalance.drainFlow + (drainFlow * lidproc_Tstep));
        lidUnit.waterBalance.@finalVol = storage;
    }

    // lidproc.c:1544
    static int modpuls_solve(int n, double[] x, double[] xOld, double[] xPrev, double[] xMin, double[] xMax, double[] xTol, double[] qOld, double[] q, double dt, double omega, FP2 derivs) {
        int i = 0;
        int canStop = 0;
        int steps = 1;
        int maxSteps = 20;
        L_f1: for (i = 0; (i < n); i++) {
            xOld[i] = x[i];
            xPrev[i] = x[i];
        }
        L_w2: while ((steps < maxSteps)) {
            canStop = 1;
            derivs.call(x, q);
            L_f3: for (i = 0; (i < n); i++) {
                x[i] = (xOld[i] + ((((omega * qOld[i]) + (((1.0d - omega)) * q[i]))) * dt));
                x[i] = (((((x[i]) <= (xMax[i]))) ? (x[i]) : (xMax[i])));
                x[i] = (((R.dge((x[i]), (xMin[i]))) ? (x[i]) : (xMin[i])));
                if (((R.dgt(omega, 0.0d)) && (R.dgt(Math.abs((x[i] - xPrev[i])), xTol[i])))) {
                    canStop = 0;
                }
                xPrev[i] = x[i];
            }
            if ((canStop != 0)) {
                return steps;
            }
            steps++;
        }
        return 0;
    }

}
