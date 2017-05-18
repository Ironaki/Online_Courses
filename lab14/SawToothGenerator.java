import lab14lib.Generator;

/**
 * Created by Armstrong on 5/18/17.
 */
public class SawToothGenerator implements Generator {

    int period;
    int state;

    public  SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        double remainder = state%period;
        double res = remainder/period*2-1;
        state += 1;
        return res;
    }
}
