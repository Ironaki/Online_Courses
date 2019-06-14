package db;

import edu.princeton.cs.introcs.In;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/** Design log
 *
 *  V 1.0
 *  Use a String array for the header.
 *  Use an int array to store type. (0 for string, 1 for int, 2 for float)
 *  Use a double String array (row and col) for body of the database.
 *
 *
 */

public class Database {

    List<Database> dbs = new ArrayList<>();
    List<String> dbnames = new ArrayList<>();

    int colSize;
    int rowSize;
    String[] header;
    String[][] body;
    int[] dataType; // 0 represents String, 1 int, 2 float.

    /** Constructor for a default database. */
    public Database() {
        header = new String[8];
        body = new String[8][8];
        dataType = new int[8];
        rowSize = 0;
        colSize = 0;
    }

    /** Constructor for a database with colDim and rowDim. */
    public Database(int colDim, int rowDim) {
        header = new String[colDim];
        body = new String[rowDim][colDim];
        dataType = new int[colDim];
        rowSize = 0;
        colSize = 0;
    }

    public int getColSize () {
        return colSize;
    }

    public int getRowSize () {
        return rowSize;
    }

    private void resizeCol (double factor) {

        // Resize the header and dataType
        String[] tempHeader = new String[(int) (header.length*factor)];
        System.arraycopy(header, 0, tempHeader, 0, rowSize);
        header = tempHeader;

        int[] tempDataType = new int[(int) (dataType.length*factor)];
        System.arraycopy(dataType, 0 , tempDataType, 0, rowSize);
        dataType = tempDataType;

        // Resize the body
        String[][] tempBody = new String[body.length][(int) (body[0].length*factor)];
        doubleArrayCopy(tempBody, body);
        body = tempBody;
    }

    private void resizeRow (double factor) {

        //Resizing header is unnecessary. Only resize the body and the dataType.
        String[][] tempBody = new String[(int) (body.length*factor)][body[0].length];
        doubleArrayCopy(tempBody, body);
        body = tempBody;

    }

    /** Helper function that copies an double String array into another. */
    private void doubleArrayCopy(String[][] neu, String[][] alt) {
        /** copy the alt array into the neu array */
        for (int i = 0; i < alt.length; i += 1){
            for (int j = 0; j < alt[i].length; j += 1) {
                neu[i][j] = alt[i][j];
            }
        }
    }

    /** This is an helper function of loadFile. */
    private int getColSizeFromFile (String fileName) {
        In file = new In(fileName);
        String firstLine = file.readLine();
        String[] columns = firstLine.split(",");
        return columns.length;
    }

    /** This is an helper function of loadFile. */
    private int getRowSizeFromFile (String fileName) {
        In file = new In(fileName);
        String[] rows = file.readAllLines();
        return rows.length - 1;
    }

    /** Takes file path as argument, returns a new database */
    public static Database loadFile (String fileName) {

        Database neu = new Database();

        // Resize the Database if necessary
        if (neu.getColSizeFromFile(fileName) > neu.getColSize()) {
            neu.resizeCol(2.0);
        }
        if (neu.getRowSizeFromFile(fileName) > neu.getRowSize()) {
            neu.resizeRow(2.0);
        }

        In file = new In(fileName);

        // Read header, put info into header and dataType array.
        String firstLine = file.readLine();
        String[] nameAndType = firstLine.split(",|\\s");
        for (int i = 0; i < nameAndType.length; i += 2) {
            neu.header[i/2] = nameAndType[i];
        }
        for (int i = 1; i < nameAndType.length; i += 2) {
            if (nameAndType[i].equals("string")){
                neu.dataType[i/2] = 0;
            } else if (nameAndType[i].equals("int")) {
                neu.dataType[i/2] = 1;
            } else {
                neu.dataType[i/2] = 2;
            }
        }

        // Read body
        String[] bodyToCopy = file.readAllLines();
        for (int i = 0; i < bodyToCopy.length; i += 1){
            String[] item = bodyToCopy[i].split(",");
            System.arraycopy(item,0, neu.body[i], 0, item.length );
        }

        // Set colSize and rowSize
        neu.colSize = neu.getColSizeFromFile(fileName);
        neu.rowSize = neu.getRowSizeFromFile(fileName);

        return neu;
    }

    /** Takes a header array and type array, returns a new database*/
    public static Database createNewTable (String[] name, int[] type) {

        Database neu = new Database();

        // Resize
        if (name.length > neu.getColSize()) {
            neu.resizeCol(2.0);
        }

        // Copy name and type
        System.arraycopy(name, 0, neu.header,0, name.length);
        System.arraycopy(type, 0, neu.dataType, 0, type.length);

        // Set colSize
        neu.colSize = name.length;

        return neu;
    }

    public void addRow (String[] row) {

        // Resize
        if (rowSize == body.length) {
            resizeRow(2.0);
        }

        // Copy
        System.arraycopy(row, 0, body[rowSize], 0, row.length);

        rowSize += 1;
    }

    public static Database jointHeader (Database dbOne, Database dbTwo) {

        String[] dbOneHeader = dbOne.header;
        String[] dbTwoHeader = dbTwo.header;
        int[] dbOneType = dbOne.dataType;
        int[] dbTwoType = dbTwo.dataType;
        int dbOneColSize = dbOne.colSize;
        int dbTwoColSize = dbTwo.colSize;

        // Two Integer list to store index of common header
        List<Integer> dbOneComIndex = new ArrayList<>();
        List<Integer> dbTwoComIndex = new ArrayList<>();

        // Find the columns with the same header. Create two List of header info
        List<String> newHeaderList = new ArrayList<>();
        List<Integer> newTypeList = new ArrayList<>();

        for (int i = 0; i < dbOneColSize; i += 1) {
            for (int j = 0; j < dbTwoColSize; j += 1){
                if (dbOneHeader[i].equals(dbTwoHeader[j]) && (dbOneType[i] == dbTwoType[j])) {
                    newHeaderList.add(dbOneHeader[i]);
                    newTypeList.add(dbOneType[i]);
                    dbOneComIndex.add(i);
                    dbTwoComIndex.add(j);
                }
            }
        }

        // append header that are not same to the end of new header info
        for (int i = 0; i < dbOneColSize; i += 1) {
            if (!dbOneComIndex.contains(i)) {
                newHeaderList.add(dbOneHeader[i]);
                newTypeList.add(dbOneType[i]);
            }
        }

        for (int i = 0; i < dbTwoColSize; i += 1) {
            if (!dbTwoComIndex.contains(i)) {
                newHeaderList.add(dbTwoHeader[i]);
                newTypeList.add(dbTwoType[i]);
            }
        }

        String[] newHeader = newHeaderList.toArray(new String[0]);
        int[] newType = convertIntList(newTypeList);

        // Now we are ready with our new header info
        // First create an new database with colSize and rowSize as the old size combined
        Database neuDB = createNewTable(newHeader, newType);

        return neuDB;
    }

    /** A helper function that converts Integer List to int array */
    private static int[] convertIntList (List<Integer> aList) {
        Integer[] intArrayInter = aList.toArray(new Integer[0]);
        int[] intArray = new int[intArrayInter.length];
        for (int i = 0; i < intArrayInter.length; i++) {
            intArray[i] = intArrayInter[i];
        }
        return intArray;
    }

    /** A Helper function to do Cartesian Joint. */
    private static Database cartesianJoint(Database one, Database two) {
        Database neu = jointHeader(one, two);
        for (int i = 0; i < one.rowSize; i++) {
            for (int j = 0; j < two.rowSize; j++) {
                List<String> rowToAdd = new ArrayList<>();
                for (int k = 0; k < one.colSize; k++) {
                    rowToAdd.add(one.body[i][k]);
                }
                for (int k = 0; k < two.colSize; k++) {
                    rowToAdd.add(two.body[j][k]);
                }
                String[] toAddString = rowToAdd.toArray(new String[0]);
                neu.addRow(toAddString);
            }
        }
        return neu;
    }

    public static Database jointBody (Database one, Database two) {

        Database neu = jointHeader(one, two);

        String[] oneHeader = one.header;
        String[] twoHeader = two.header;
        int[] oneType = one.dataType;
        int[] twoType = two.dataType;
        int oneColSize = one.colSize;
        int twoColSize = two.colSize;

        // Two Integer list to store index of common header
        List<Integer> oneComIndex = new ArrayList<>();
        List<Integer> twoComIndex = new ArrayList<>();

        for (int i = 0; i < oneColSize; i += 1) {
            for (int j = 0; j < twoColSize; j += 1){
                if (oneHeader[i].equals(twoHeader[j]) && (oneType[i] == twoType[j])) {
                    oneComIndex.add(i);
                    twoComIndex.add(j);
                }
            }
        }

        if (oneComIndex.size() == 0) {
            //return Cartesian Production
            return cartesianJoint(one, two);
        }

        // index of the first common column
        int oneCol = oneComIndex.get(0);
        int twoCol = twoComIndex.get(0);

        // The following 30 lines of code are quite smart. (At least in my point of view)
        // The first double loop finds all row (sets) that are the same
        // Then check for these row (sets), whether for all common columns, their items are equal
        // If there is any items that are not equal, add it to the rows to remove, and go to next row.
        List<Integer> firstRowSelection = new ArrayList<>();
        List<Integer> secondRowSelection = new ArrayList<>();

        for (int i = 0; i < one.rowSize; i++) {
            for (int j = 0; j < two.rowSize; j++) {
                if (one.body[i][oneCol].equals(two.body[j][twoCol])) {
                    firstRowSelection.add(i);
                    secondRowSelection.add(j);
                }
            }
        }

        // Check if the selected rows has every common column items the same
        // remove those do not
        List<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < firstRowSelection.size(); i++) {
            for (int j = 0; j < oneComIndex.size(); j++) {
                if (!one.body[firstRowSelection.get(i)][oneComIndex.get(j)].equals(two.body[secondRowSelection.get(i)][twoComIndex.get(j)])) {
                    toRemove.add(i);
                    break;
                }
            }
        }

        for (int i = toRemove.size()-1; i > -1; i -= 1) {
            int index = toRemove.get(i);
            firstRowSelection.remove(index);
            secondRowSelection.remove(index);
        }
        //Did I just find a bug here?
        //No, it comes from the issue int and Integer
        //remove function behaves in different way if you give an int and an Integer


        // Create two Integer List for columns that to be add later
        List<Integer> oneToBeAddAfterCom = new ArrayList<>();
        List<Integer> twoToBeAddAfterCom = new ArrayList<>();
        for (int i = 0; i < one.colSize; i++) {
            if (!oneComIndex.contains(i)) {
                oneToBeAddAfterCom.add(i);
            }
        }
        for (int i = 0; i < two.colSize; i++) {
            if (!twoComIndex.contains(i)) {
                twoToBeAddAfterCom.add(i);
            }
        }


        // Create new row Strings and add it to neu
        for (int i = 0; i < firstRowSelection.size(); i++){
            List<String> toAdd = new ArrayList<>();
            for (int j = 0; j < oneComIndex.size(); j++){
                toAdd.add(one.body[firstRowSelection.get(i)][oneComIndex.get(j)]);
            }
            for (int l = 0; l < oneToBeAddAfterCom.size(); l++) {
                toAdd.add(one.body[firstRowSelection.get(i)][oneToBeAddAfterCom.get(l)]);
            }
            for (int m = 0; m < twoToBeAddAfterCom.size(); m++) {
                toAdd.add(two.body[secondRowSelection.get(i)][twoToBeAddAfterCom.get(m)]);
            }
            String[] toAddString = toAdd.toArray(new String[0]);
            neu.addRow(toAddString);
        }

        return neu;

    }

    public void print() {

        //print header and type
        for (int i = 0; i < colSize; i++) {
            System.out.print(header[i]);
            System.out.print(" ");
            if (dataType[i] == 0) {
                System.out.print("string");
            } else if (dataType[i] == 1) {
                System.out.print("int");
            } else {
                System.out.println("float");
            }
            if (i != colSize-1) {
                System.out.print(",");
            }
        }
        System.out.println("");

        //print body
        for (int i = 0; i < rowSize; i++) {
            for (int j =0; j < colSize; j++) {
                System.out.print(body[i][j]);
                if (j != colSize-1) {
                    System.out.print(",");
                }
            }
            System.out.println("");
        }

    }

    public void output (String name) {
        String tblName = name+".tbl";
        try {
            PrintWriter file = new PrintWriter(tblName);
            for (int i = 0; i < colSize; i++) {
                file.print(header[i]);
                file.print(" ");
                if (dataType[i] == 0) {
                    file.print("string");
                } else if (dataType[i] == 1) {
                    file.print("int");
                } else {
                    file.print("float");
                }
                if (i != colSize-1) {
                    file.print(",");
                }
            }
            file.println("");

            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    file.print(body[i][j]);
                    if (j != colSize-1) {
                        file.print(",");
                    }
                }
                file.println("");
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Could not find the file");
        }

    }

    public String transact(String query) {
        return "";
    }
}
