import lab14lib.Generator;

/**
 * Created by Armstrong on 5/18/17.
 */
public class AcceleratingSawToothGenerator implements Generator{

    int period;
    int state;
    double aFactor;

    public  AcceleratingSawToothGenerator(int period, double aFactor) {
        this.period = period;
        this.aFactor = aFactor;
        state = 0;
    }

    @Override
    public double next() {
        double remainder = state%period;
        double res = remainder/period*2-1;
        state += 1;
        if (state%period == 0) {
            if (period * aFactor >= 1){
                period *= aFactor;
            }
            state = 0;
        }
        return res;
    }
}
