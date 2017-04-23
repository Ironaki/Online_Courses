package db;

/**
 * Created by Armstrong on 4/20/17.
 */
public class Test {

    public static void main (String[] args) {
//        testLoadFile();
//        testNewTable();
//        testJoint1();
        testJoint2();
//        testOutput();
    }

    public static void testOutput() {
        Database T10 = Database.loadFile("examples/T10.tbl");
        String[] row = {"2", "3"};
        T10.addRow(row);
        T10.output("Thaha");
    }

    public static void testJoint2() {
        Database teams = Database.loadFile("examples/teams.tbl");
        Database records = Database.loadFile("examples/records.tbl");
        Database fans = Database.loadFile("examples/fans.tbl");
        Database T4 = Database.loadFile("examples/T41.tbl");
        Database T5 = Database.loadFile("examples/T5.tbl");
        Database T7 = Database.loadFile("examples/T7.tbl");
        Database T8 = Database.loadFile("examples/T8.tbl");
        Database T10 = Database.loadFile("examples/T10.tbl");
        Database T11 = Database.loadFile("examples/T11.tbl");
        Database T1 = Database.loadFile("examples/T1.tbl");
        Database T2 = Database.loadFile("examples/T2.tbl");
        Database T12 = Database.loadFile("examples/T12.tbl");
        Database T13 = Database.loadFile("examples/T13.tbl");
        Database joint78 = Database.jointBody(T7, T8);
        Database joint87 = Database.jointBody(T8, T7);
        Database joint1011 = Database.jointBody(T10, T11);
        Database joint1110 = Database.jointBody(T11, T10);
        Database joint12 = Database.jointBody(T1, T2);
        Database joint21 = Database.jointBody(T2, T1);
        Database joint45 = Database.jointBody(T4, T5);
        Database joint54 = Database.jointBody(T5, T4);
        Database joint1213 = Database.jointBody(T12, T13);
        Database joint1312 = Database.jointBody(T13, T12);
    }

    public static void testJoint1() {
        Database records = Database.loadFile("examples/records.tbl");
        Database fans = Database.loadFile("examples/fans.tbl");
        Database FRjoint = Database.jointHeader(records, fans);
        System.out.println(FRjoint.header[7]);

    }

    public static void testLoadFile() {
        Database records = Database.loadFile("examples/records.tbl");
        System.out.println(records.body[6][3]);
        System.out.println(records.dataType[2]);
        System.out.println(records.header[2]);
        System.out.println(records.colSize);
        System.out.println(records.rowSize);
        System.out.println(records.header.length);
    }

    public static void testNewTable() {
        String[] header = {"a", "b", "c"};
        int[] type = {1, 1, 1};
        Database neu = Database.createNewTable(header, type);
        System.out.println(neu.header[2]);
    }

}
