package db;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import edu.princeton.cs.introcs.In;

/**
 * Created by Armstrong on 4/18/17.
 */
public class Experiment {

    public static void main (String[] args) {
//        doubleArrayEx();
//        inEx();
//        int i = 3;
//        System.out.println(i/2);
//        arrayList();
//        testConvertIntList();
//        set();
//        testSplit();
        regex();
//        tryHashMap();
    }

    public static void tryHashMap () {
        Map<String, Integer> newMap = new HashMap<>();
        newMap.put("Asuna", 17);
        newMap.put("Setsuna", 21);
        newMap.put("Kurisu", 19);
        System.out.println(newMap);

    }

    public static void regex () {
        String REST  = "\\s*(\\S+|\\S+.*\\S+)\\s*";
        String ArbitrarySpace = "\\s*";
        Pattern INSERT_CMD = Pattern.compile(ArbitrarySpace + "insert into " + "(\\S+|\\S+.*\\S+)" + REST);
        Pattern INSERT_CLS  = Pattern.compile("values\\s+(.+?" + "\\s*(?:,\\s*.+?\\s*)*)");
        Matcher valid = INSERT_CMD.matcher("insert into t1 values 12 13");
        valid.matches();
        String cmd = valid.group(2);
        System.out.println(cmd);
        Matcher m = INSERT_CLS.matcher(cmd);
        System.out.println(m.matches());
        System.out.println(m.group(1));
    }

    public static void testSplit () {
        String haha = "select     a,b from table1, table2";
        String[] toPrint = haha.split("\\s+");

        for (String i: toPrint) {
            System.out.println(i);
        }
        String REST  = "\\s*(.*)\\s*";
        System.out.println(REST);
    }

    public static void set() {

        Set<Integer> exSet = new HashSet<>();

        exSet.add(1);
        exSet.add(1);
        exSet.add(3);
        exSet.add(2);

        for (int i: exSet) {
            System.out.println(i);
        }


    }

    public static void testConvertIntList () {
        List<Integer> intList = new ArrayList<>();
        intList.add(3);
        intList.add(4);
        intList.add(5);

    }

    public static void arrayList () {
        List<Integer> intList = new ArrayList<>();
        intList.add(3);
        intList.add(4);
        intList.add(5);

        for (int i = intList.size()-1; i > -1; i--) {
            System.out.println(intList.get(i));
            System.out.println(intList.get(i));
        }


    }

    private static void inEx () {
        In records = new In("examples/records.tbl");

//        String firstLine = records.readLine();
//        String secondLine = records.readLine();
//        System.out.println(firstLine);
//        System.out.println(secondLine);

//        while (records.hasNextLine()) {
//            String line = records.readLine();
//            System.out.println(line);
//        }
        String header = records.readLine();

        String[] headerSplit = header.split(",|\\s");

        for (String x: headerSplit) {
            System.out.println(x);
        }

//        String[] allLine = records.readAllLines();
//
//        for (String x: allLine){
//            System.out.println(x);
//        }

    }

    private static void doubleArrayEx () {
        String[][] testDoubleArray = new String[3][3];

        testDoubleArray[2][2] = "klijia";

        System.out.println(testDoubleArray[2][2]);
        System.out.println(testDoubleArray.length);
        System.out.println(testDoubleArray[2].length);
    }

}
