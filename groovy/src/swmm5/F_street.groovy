// Generated from EPA SWMM 5.2.4 (street.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_street {
    // street.c:29
    static int street_create(int nStreets) {
        F_swmm5.@Street = (null);
        F_swmm5.Nobjects[STREET] = 0;
        F_swmm5.@Street = R.fill(new TStreet[(int) (nStreets)], (() -> new TStreet()));
        if ((F_swmm5.Street == null)) {
            return ERR_MEMORY;
        }
        F_swmm5.Nobjects[STREET] = nStreets;
        return 0;
    }

    // street.c:46
    static void street_delete() {
        if ((F_swmm5.Street != null)) {
            R.free(F_swmm5.Street);
            F_swmm5.@Street = (null);
        }
    }

    // street.c:58
    static int street_readParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        IBox sides = new IBox(0);
        double[] x = new double[11];
        TStreet street = null;
        if ((ntoks < 5)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        i = F_project.project_findObject(STREET, tok[0]);
        if ((i < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        F_swmm5.Street[i].@ID = F_project.project_findID(STREET, tok[0]);
        L_f1: for (k = 0; (k <= 9); k++) {
            x[k] = 0.0d;
        }
        L_f2: for (k = 1; (k <= 4); k++) {
            if (((!(F_input.getDouble(tok[k], new DElem(x, k)) != 0)) || (x[k] <= 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[k]);
            }
        }
        if ((ntoks > 5)) {
            if (((!(F_input.getDouble(tok[5], new DElem(x, 5)) != 0)) || (x[5] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        if ((ntoks > 6)) {
            if (((!(F_input.getDouble(tok[6], new DElem(x, 6)) != 0)) || (x[6] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
        }
        sides.@v = 2;
        if ((ntoks > 7)) {
            if ((((!(F_input.getInt(tok[7], sides) != 0)) || (sides.v < 1)) || (sides.v > 2))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        if ((ntoks > 8)) {
            if (((!(F_input.getDouble(tok[8], new DElem(x, 8)) != 0)) || (x[8] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[8]);
            }
            if (R.dgt(x[8], 0.0d)) {
                if ((ntoks < 11)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                L_f3: for (k = 9; (k <= 10); k++) {
                    if (((!(F_input.getDouble(tok[k], new DElem(x, k)) != 0)) || (x[k] <= 0.0d))) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[k]);
                    }
                }
            }
        }
        street = F_swmm5.Street[i];
        street.@width = (x[1] / F_swmm5.UCF(LENGTH));
        street.@curbHeight = (x[2] / F_swmm5.UCF(LENGTH));
        street.@slope = (x[3] / 100.0d);
        street.@roughness = x[4];
        street.@gutterDepression = (x[5] / F_swmm5.UCF(LENGTH));
        street.@gutterWidth = (x[6] / F_swmm5.UCF(LENGTH));
        street.@sides = sides.v;
        street.@backWidth = (x[8] / F_swmm5.UCF(LENGTH));
        street.@backSlope = (x[9] / 100.0d);
        street.@backRoughness = x[10];
        F_transect.transect_createStreetTransect(street);
        return 0;
    }

    // street.c:141
    static double street_getExtentFilled(int link) {
        int k = 0;
        int t = 0;
        double filled = 0.0d;
        t = F_swmm5.Link[link].xsect.transect;
        if ((t < 0)) {
            return 0.0d;
        }
        if ((F_swmm5.RouteModel == DW)) {
            filled = (((R.dge((F_swmm5.Node_[F_swmm5.Link[link].node1].newDepth), (F_swmm5.Node_[F_swmm5.Link[link].node2].newDepth))) ? (F_swmm5.Node_[F_swmm5.Link[link].node1].newDepth) : (F_swmm5.Node_[F_swmm5.Link[link].node2].newDepth)));
        } else {
            k = F_swmm5.Link[link].subIndex;
            filled = (((R.dge((F_swmm5.Conduit[k].a1), (F_swmm5.Conduit[k].a2))) ? (F_swmm5.Conduit[k].a1) : (F_swmm5.Conduit[k].a2)));
        }
        return filled;
    }

}
