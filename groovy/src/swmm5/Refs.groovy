// Refs.groovy - C's pointer-to-one-object, for the Groovy port of EPA SWMM 5.2.4. A scalar pointer parameter
// becomes a DRef/IRef/LRef/FRef (double/int/long/float) or an ORef<T>; the caller passes a box (a local whose
// address is taken), an array element, a struct field or a static variable. The Java port's Refs.java in Groovy:
// every field is public (Groovy makes a field without a modifier a property). Written by Claude (Anthropic) for
// Robert Dickinson; see ../../README.md.
package swmm5

import groovy.transform.CompileStatic
import java.util.function.*

@CompileStatic abstract class DRef { abstract double get(); abstract double set(double v) }
@CompileStatic abstract class IRef { abstract int get(); abstract int set(int v) }
@CompileStatic abstract class LRef { abstract long get(); abstract long set(long v) }
@CompileStatic abstract class FRef { abstract float get(); abstract float set(float v) }
@CompileStatic abstract class ORef<T> { abstract T get(); abstract T set(T v) }

@CompileStatic
final class DBox extends DRef {
    public double v
    DBox(double v) { this.v = v }
    double get() { return v }
    double set(double x) { v = x; return x }
}
@CompileStatic
final class IBox extends IRef {
    public int v
    IBox(int v) { this.v = v }
    int get() { return v }
    int set(int x) { v = x; return x }
}
@CompileStatic
final class LBox extends LRef {
    public long v
    LBox(long v) { this.v = v }
    long get() { return v }
    long set(long x) { v = x; return x }
}
@CompileStatic
final class FBox extends FRef {
    public float v
    FBox(float v) { this.v = v }
    float get() { return v }
    float set(float x) { v = x; return x }
}
@CompileStatic
final class OBox<T> extends ORef<T> {
    public T v
    OBox(T v) { this.v = v }
    T get() { return v }
    T set(T x) { v = x; return x }
}

@CompileStatic
final class DElem extends DRef {
    public final double[] a; public final int i
    DElem(double[] a, int i) { this.a = a; this.i = i }
    double get() { return a[i] }
    double set(double x) { a[i] = x; return x }
}
@CompileStatic
final class IElem extends IRef {
    public final int[] a; public final int i
    IElem(int[] a, int i) { this.a = a; this.i = i }
    int get() { return a[i] }
    int set(int x) { a[i] = x; return x }
}
@CompileStatic
final class LElem extends LRef {
    public final long[] a; public final int i
    LElem(long[] a, int i) { this.a = a; this.i = i }
    long get() { return a[i] }
    long set(long x) { a[i] = x; return x }
}
@CompileStatic
final class FElem extends FRef {
    public final float[] a; public final int i
    FElem(float[] a, int i) { this.a = a; this.i = i }
    float get() { return a[i] }
    float set(float x) { a[i] = x; return x }
}
@CompileStatic
final class OElem<T> extends ORef<T> {
    public final T[] a; public final int i
    OElem(T[] a, int i) { this.a = a; this.i = i }
    T get() { return a[i] }
    T set(T x) { a[i] = x; return x }
}

interface FloatGetter<T> { float get(T o) }
interface FloatSetter<T> { void set(T o, float x) }

@CompileStatic
final class DField<T> extends DRef {
    public final T o; public final ToDoubleFunction<T> g; public final ObjDoubleConsumer<T> s
    DField(T o, ToDoubleFunction<T> g, ObjDoubleConsumer<T> s) { this.o = o; this.g = g; this.s = s }
    double get() { return g.applyAsDouble(o) }
    double set(double x) { s.accept(o, x); return x }
}
@CompileStatic
final class IField<T> extends IRef {
    public final T o; public final ToIntFunction<T> g; public final ObjIntConsumer<T> s
    IField(T o, ToIntFunction<T> g, ObjIntConsumer<T> s) { this.o = o; this.g = g; this.s = s }
    int get() { return g.applyAsInt(o) }
    int set(int x) { s.accept(o, x); return x }
}
@CompileStatic
final class LField<T> extends LRef {
    public final T o; public final ToLongFunction<T> g; public final ObjLongConsumer<T> s
    LField(T o, ToLongFunction<T> g, ObjLongConsumer<T> s) { this.o = o; this.g = g; this.s = s }
    long get() { return g.applyAsLong(o) }
    long set(long x) { s.accept(o, x); return x }
}
@CompileStatic
final class FField<T> extends FRef {
    public final T o; public final FloatGetter<T> g; public final FloatSetter<T> s
    FField(T o, FloatGetter<T> g, FloatSetter<T> s) { this.o = o; this.g = g; this.s = s }
    float get() { return g.get(o) }
    float set(float x) { s.set(o, x); return x }
}
@CompileStatic
final class OField<T, V> extends ORef<V> {
    public final T o; public final Function<T, V> g; public final BiConsumer<T, V> s
    OField(T o, Function<T, V> g, BiConsumer<T, V> s) { this.o = o; this.g = g; this.s = s }
    V get() { return g.apply(o) }
    V set(V x) { s.accept(o, x); return x }
}

interface FloatSupplier { float get() }
interface FloatConsumer { void accept(float x) }

@CompileStatic
final class DAcc extends DRef {
    public final DoubleSupplier g; public final DoubleConsumer s
    DAcc(DoubleSupplier g, DoubleConsumer s) { this.g = g; this.s = s }
    double get() { return g.getAsDouble() }
    double set(double x) { s.accept(x); return x }
}
@CompileStatic
final class IAcc extends IRef {
    public final IntSupplier g; public final IntConsumer s
    IAcc(IntSupplier g, IntConsumer s) { this.g = g; this.s = s }
    int get() { return g.getAsInt() }
    int set(int x) { s.accept(x); return x }
}
@CompileStatic
final class LAcc extends LRef {
    public final LongSupplier g; public final LongConsumer s
    LAcc(LongSupplier g, LongConsumer s) { this.g = g; this.s = s }
    long get() { return g.getAsLong() }
    long set(long x) { s.accept(x); return x }
}
@CompileStatic
final class FAcc extends FRef {
    public final FloatSupplier g; public final FloatConsumer s
    FAcc(FloatSupplier g, FloatConsumer s) { this.g = g; this.s = s }
    float get() { return g.get() }
    float set(float x) { s.accept(x); return x }
}
@CompileStatic
final class OAcc<T> extends ORef<T> {
    public final Supplier<T> g; public final Consumer<T> s
    OAcc(Supplier<T> g, Consumer<T> s) { this.g = g; this.s = s }
    T get() { return g.get() }
    T set(T x) { s.accept(x); return x }
}

@CompileStatic
final class CharRef extends IRef {
    public final CPtr p; public final int i
    CharRef(CPtr p, int i) { this.p = p; this.i = i }
    int get() { return p.g(i) }
    int set(int x) { return p.s(i, x) }
}
