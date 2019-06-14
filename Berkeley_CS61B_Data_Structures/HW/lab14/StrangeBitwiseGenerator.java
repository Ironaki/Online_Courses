import lab14lib.Generator;

/**
 * Created by Armstrong on 5/18/17.
 */
public class StrangeBitwiseGenerator implements Generator{
    int period;
    int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
//        int weirdState = state & (state >>> 3) % period;
//        int weirdState = state & (state >> 3) & (state >> 8) % period;
        int weirdState = state & (state >> 7) % period;
        double remainder = weirdState%period;
        double res = remainder/period*2-1;
        state += 1;
        return res;
    }
}
