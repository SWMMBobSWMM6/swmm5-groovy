// Generated from EPA SWMM 5.2.4 (error.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_error {
    public static CPtr ErrString = R.buf(256);

    // error.c:30
    static CPtr error_getMsg(int errCode, CPtr msg) {
        int __sw1 = errCode;
        if (__sw1 == 101) {
            R.strcpy(msg, L46);
        } else if (__sw1 == 103) {
            R.strcpy(msg, L47);
        } else if (__sw1 == 105) {
            R.strcpy(msg, L48);
        } else if (__sw1 == 107) {
            R.strcpy(msg, L49);
        } else if (__sw1 == 108) {
            R.strcpy(msg, L50);
        } else if (__sw1 == 109) {
            R.strcpy(msg, L51);
        } else if (__sw1 == 110) {
            R.strcpy(msg, L52);
        } else if (__sw1 == 111) {
            R.strcpy(msg, L53);
        } else if (__sw1 == 112) {
            R.strcpy(msg, L54);
        } else if (__sw1 == 113) {
            R.strcpy(msg, L55);
        } else if (__sw1 == 114) {
            R.strcpy(msg, L56);
        } else if (__sw1 == 115) {
            R.strcpy(msg, L57);
        } else if (__sw1 == 117) {
            R.strcpy(msg, L58);
        } else if (__sw1 == 119) {
            R.strcpy(msg, L59);
        } else if (__sw1 == 121) {
            R.strcpy(msg, L60);
        } else if (__sw1 == 122) {
            R.strcpy(msg, L61);
        } else if (__sw1 == 131) {
            R.strcpy(msg, L62);
        } else if (__sw1 == 133) {
            R.strcpy(msg, L63);
        } else if (__sw1 == 134) {
            R.strcpy(msg, L64);
        } else if (__sw1 == 135) {
            R.strcpy(msg, L65);
        } else if (__sw1 == 136) {
            R.strcpy(msg, L66);
        } else if (__sw1 == 137) {
            R.strcpy(msg, L67);
        } else if (__sw1 == 138) {
            R.strcpy(msg, L68);
        } else if (__sw1 == 139) {
            R.strcpy(msg, L69);
        } else if (__sw1 == 140) {
            R.strcpy(msg, L70);
        } else if (__sw1 == 141) {
            R.strcpy(msg, L71);
        } else if (__sw1 == 143) {
            R.strcpy(msg, L72);
        } else if (__sw1 == 145) {
            R.strcpy(msg, L73);
        } else if (__sw1 == 151) {
            R.strcpy(msg, L74);
        } else if (__sw1 == 153) {
            R.strcpy(msg, L75);
        } else if (__sw1 == 155) {
            R.strcpy(msg, L76);
        } else if (__sw1 == 156) {
            R.strcpy(msg, L77);
        } else if (__sw1 == 157) {
            R.strcpy(msg, L78);
        } else if (__sw1 == 158) {
            R.strcpy(msg, L79);
        } else if (__sw1 == 159) {
            R.strcpy(msg, L80);
        } else if (__sw1 == 161) {
            R.strcpy(msg, L81);
        } else if (__sw1 == 171) {
            R.strcpy(msg, L82);
        } else if (__sw1 == 173) {
            R.strcpy(msg, L83);
        } else if (__sw1 == 181) {
            R.strcpy(msg, L84);
        } else if (__sw1 == 182) {
            R.strcpy(msg, L85);
        } else if (__sw1 == 183) {
            R.strcpy(msg, L86);
        } else if (__sw1 == 184) {
            R.strcpy(msg, L87);
        } else if (__sw1 == 185) {
            R.strcpy(msg, L88);
        } else if (__sw1 == 187) {
            R.strcpy(msg, L89);
        } else if (__sw1 == 188) {
            R.strcpy(msg, L90);
        } else if (__sw1 == 191) {
            R.strcpy(msg, L91);
        } else if (__sw1 == 193) {
            R.strcpy(msg, L92);
        } else if (__sw1 == 195) {
            R.strcpy(msg, L93);
        } else if (__sw1 == 200) {
            R.strcpy(msg, L94);
        } else if (__sw1 == 201) {
            R.strcpy(msg, L95);
        } else if (__sw1 == 203) {
            R.strcpy(msg, L96);
        } else if (__sw1 == 205) {
            R.strcpy(msg, L97);
        } else if (__sw1 == 207) {
            R.strcpy(msg, L98);
        } else if (__sw1 == 209) {
            R.strcpy(msg, L99);
        } else if (__sw1 == 211) {
            R.strcpy(msg, L100);
        } else if (__sw1 == 213) {
            R.strcpy(msg, L101);
        } else if (__sw1 == 217) {
            R.strcpy(msg, L102);
        } else if (__sw1 == 219) {
            R.strcpy(msg, L103);
        } else if (__sw1 == 221) {
            R.strcpy(msg, L104);
        } else if (__sw1 == 223) {
            R.strcpy(msg, L105);
        } else if (__sw1 == 225) {
            R.strcpy(msg, L106);
        } else if (__sw1 == 227) {
            R.strcpy(msg, L107);
        } else if (__sw1 == 229) {
            R.strcpy(msg, L108);
        } else if (__sw1 == 231) {
            R.strcpy(msg, L109);
        } else if (__sw1 == 233) {
            R.strcpy(msg, L110);
        } else if (__sw1 == 235) {
            R.strcpy(msg, L111);
        } else if (__sw1 == 301) {
            R.strcpy(msg, L112);
        } else if (__sw1 == 303) {
            R.strcpy(msg, L113);
        } else if (__sw1 == 305) {
            R.strcpy(msg, L114);
        } else if (__sw1 == 307) {
            R.strcpy(msg, L115);
        } else if (__sw1 == 308) {
            R.strcpy(msg, L116);
        } else if (__sw1 == 309) {
            R.strcpy(msg, L117);
        } else if (__sw1 == 311) {
            R.strcpy(msg, L118);
        } else if (__sw1 == 313) {
            R.strcpy(msg, L119);
        } else if (__sw1 == 315) {
            R.strcpy(msg, L120);
        } else if (__sw1 == 317) {
            R.strcpy(msg, L121);
        } else if (__sw1 == 318) {
            R.strcpy(msg, L122);
        } else if (__sw1 == 319) {
            R.strcpy(msg, L123);
        } else if (__sw1 == 320) {
            R.strcpy(msg, L124);
        } else if (__sw1 == 321) {
            R.strcpy(msg, L125);
        } else if (__sw1 == 323) {
            R.strcpy(msg, L126);
        } else if (__sw1 == 325) {
            R.strcpy(msg, L127);
        } else if (__sw1 == 327) {
            R.strcpy(msg, L128);
        } else if (__sw1 == 329) {
            R.strcpy(msg, L129);
        } else if (__sw1 == 331) {
            R.strcpy(msg, L130);
        } else if (__sw1 == 333) {
            R.strcpy(msg, L131);
        } else if (__sw1 == 335) {
            R.strcpy(msg, L132);
        } else if (__sw1 == 336) {
            R.strcpy(msg, L133);
        } else if (__sw1 == 337) {
            R.strcpy(msg, L134);
        } else if (__sw1 == 338) {
            R.strcpy(msg, L135);
        } else if (__sw1 == 339) {
            R.strcpy(msg, L136);
        } else if (__sw1 == 341) {
            R.strcpy(msg, L137);
        } else if (__sw1 == 343) {
            R.strcpy(msg, L138);
        } else if (__sw1 == 345) {
            R.strcpy(msg, L139);
        } else if (__sw1 == 351) {
            R.strcpy(msg, L140);
        } else if (__sw1 == 353) {
            R.strcpy(msg, L141);
        } else if (__sw1 == 355) {
            R.strcpy(msg, L142);
        } else if (__sw1 == 357) {
            R.strcpy(msg, L143);
        } else if (__sw1 == 361) {
            R.strcpy(msg, L144);
        } else if (__sw1 == 363) {
            R.strcpy(msg, L145);
        } else if (__sw1 == 500) {
            R.strcpy(msg, L146);
        } else if (__sw1 == 501) {
            R.strcpy(msg, L147);
        } else if (__sw1 == 502) {
            R.strcpy(msg, L148);
        } else if (__sw1 == 503) {
            R.strcpy(msg, L149);
        } else if (__sw1 == 504) {
            R.strcpy(msg, L150);
        } else if (__sw1 == 505) {
            R.strcpy(msg, L151);
        } else if (__sw1 == 506) {
            R.strcpy(msg, L152);
        } else if (__sw1 == 507) {
            R.strcpy(msg, L153);
        } else if (__sw1 == 508) {
            R.strcpy(msg, L154);
        } else if (__sw1 == 509) {
            R.strcpy(msg, L155);
        } else {
            R.strcpy(msg, L0);
        }
        return (msg);
    }

    // error.c:45
    static int error_setInpError(int errcode, CPtr s) {
        R.strcpy(ErrString, s);
        return errcode;
    }

}
