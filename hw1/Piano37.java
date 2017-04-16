/**
 * Created by Armstrong on 4/17/17.
 *  This piano monitor is not perfect.
 *
 *  When you press any key that is not one of the 37 keys, it plays a default 440 Hz sound.
 *
 *  Do not press a key for a long time!
 *  Short press, please.
 */


public class Piano37 {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {

        while (true) {
            while (StdDraw.hasNextKeyTyped()) {

                //Basically read in a key and plays the sound for a while

                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                double freq = 440.0 * Math.pow(2, ((index - 24) / 12.0));

                synthesizer.GuitarString thisKey = new synthesizer.GuitarString(freq);

                thisKey.pluck();

                // Try to change the upper bound of this loop
                // you will get sound in different length
                for (int i = 0; i < 30000; i += 1) {
                    StdAudio.play(thisKey.sample());
                    thisKey.tic();

                }
            }
        }
    }
}
