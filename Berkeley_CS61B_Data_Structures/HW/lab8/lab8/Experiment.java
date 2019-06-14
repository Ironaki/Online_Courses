package lab8;


/**
 * Created by Armstrong on 4/27/17.
 */
public class Experiment {
    public static void main (String[] args) {
        BSTMap<String, Integer> characters = new BSTMap<>();

        characters.put("Kurisu", 19);
        characters.put("Asuna", 17);
        characters.put("Setsuna", 21);
        characters.put("Eru", 15);
        characters.put("Kawori", 13);
        characters.put("Reina", 16);
        characters.put("Nanami", 18);
        characters.put("Mio", 17);
        characters.put("Yukino", 18);
        characters.put("Utaha", 17);
        characters.put("Tomoyo", 16);

        System.out.println(characters.getRootKey());
        characters.printInOrder();
    }
}
