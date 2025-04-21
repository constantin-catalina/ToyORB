package Applications.Math;

public class MathImpl implements Math {

    public MathImpl() {}

    @Override
    public float do_add(float a, float b) {
        return a + b;
    }

    @Override
    public float do_sqrt(float a) {
        return (float) java.lang.Math.sqrt(a);
    }
}
