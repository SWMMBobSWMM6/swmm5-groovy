// Generated from EPA SWMM 5.2.4 (hash.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_hash {
    // hash.c:24
    static int samestr(CPtr s1, CPtr s2) {
        int i = 0;
        L_f1: for (i = 0; (((((((s1.g(i)) >= 97) && ((s1.g(i)) <= 122))) ? (((s1.g(i)) & (~32))) : (s1.g(i)))) == ((((((s2.g(i)) >= 97) && ((s2.g(i)) <= 122))) ? (((s2.g(i)) & (~32))) : (s2.g(i))))); i++) {
            if (((!(s1.g((i + 1)) != 0)) && (!(s2.g((i + 1)) != 0)))) {
                return (1);
            }
        }
        return (0);
    }

    // hash.c:33
    static long hash(CPtr str) {
        long sum1 = ((0) & 0xFFFFFFFFL);
        long check1 = 0L;
        long sum2 = 0L;
        L_w1: while ((0 != str.g(0))) {
            sum1 = ((sum1 + ((((((((str.g(0)) >= 97) && ((str.g(0)) <= 122))) ? (((str.g(0)) & (~32))) : (str.g(0))))) & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            str = str.add(1);
            if ((((255) & 0xFFFFFFFFL) <= sum1)) {
                sum1 = ((sum1 - ((255) & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            }
            sum2 += sum1;
        }
        check1 = ((sum2) & 0xFFFFFFFFL);
        check1 = (check1 % ((255) & 0xFFFFFFFFL));
        check1 = ((((255) & 0xFFFFFFFFL) - ((((sum1 + check1) & 0xFFFFFFFFL)) % ((255) & 0xFFFFFFFFL))) & 0xFFFFFFFFL);
        sum1 = ((((255) & 0xFFFFFFFFL) - ((((sum1 + check1) & 0xFFFFFFFFL)) % ((255) & 0xFFFFFFFFL))) & 0xFFFFFFFFL);
        return (((((((check1 << 8) & 0xFFFFFFFFL)) | sum1)) % ((1999) & 0xFFFFFFFFL)));
    }

    // hash.c:51
    static HTentry[] HTcreate() {
        int i = 0;
        HTentry[] ht = new HTentry[(int) (1999)];
        if ((ht != null)) {
            L_f1: for (i = 0; (i < 1999); i++) {
                ht[i] = (null);
            }
        }
        return (ht);
    }

    // hash.c:59
    static int HTinsert(HTentry[] ht, CPtr key, int data) {
        long i = hash(key);
        HTentry entry = null;
        if ((i >= ((1999) & 0xFFFFFFFFL))) {
            return (0);
        }
        entry = new HTentry();
        if ((entry == null)) {
            return (0);
        }
        entry.@key = key;
        entry.@data = data;
        entry.@next = ht[(int) (i)];
        ht[(int) (i)] = entry;
        return (1);
    }

    // hash.c:73
    static int HTfind(HTentry[] ht, CPtr key) {
        long i = hash(key);
        HTentry entry = null;
        if ((i >= ((1999) & 0xFFFFFFFFL))) {
            return ((-1));
        }
        entry = ht[(int) (i)];
        L_w1: while ((entry != null)) {
            if ((samestr(entry.key, key) != 0)) {
                return (entry.data);
            }
            entry = entry.next;
        }
        return ((-1));
    }

    // hash.c:87
    static CPtr HTfindKey(HTentry[] ht, CPtr key) {
        long i = hash(key);
        HTentry entry = null;
        if ((i >= ((1999) & 0xFFFFFFFFL))) {
            return null;
        }
        entry = ht[(int) (i)];
        L_w1: while ((entry != null)) {
            if ((samestr(entry.key, key) != 0)) {
                return (entry.key);
            }
            entry = entry.next;
        }
        return null;
    }

    // hash.c:101
    static void HTfree(HTentry[] ht) {
        HTentry entry = null;
        HTentry nextentry = null;
        int i = 0;
        L_f1: for (i = 0; (i < 1999); i++) {
            entry = ht[i];
            L_w2: while ((entry != null)) {
                nextentry = entry.next;
                R.free(entry);
                entry = nextentry;
            }
        }
        R.free(ht);
    }

}
