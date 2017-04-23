package db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainDB {
    private static final String EXIT   = "exit";
    private static final String PROMPT = "> ";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<Database> dbs = new ArrayList<>();
        List<String> dbnames = new ArrayList<>();
        Database db = new Database();
        System.out.print(PROMPT);

        String line = "";
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }

            if (!line.trim().isEmpty()) {
                // Various common constructs, simplifies parsing.
                String REST  = "\\s*(\\S+|\\S+.*\\S+)\\s*";
                String COMMA = "\\s*,\\s*";
                String AND   = "\\s+and\\s+";
                String ArbitrarySpace = "\\s*";

                // Stage 1 syntax, contains the command name.
                Pattern CREATE_CMD = Pattern.compile(ArbitrarySpace + "create table " + REST);
                Pattern LOAD_CMD   = Pattern.compile(ArbitrarySpace + "load " + REST);
                Pattern STORE_CMD  = Pattern.compile(ArbitrarySpace + "store " + REST);
                Pattern DROP_CMD   = Pattern.compile(ArbitrarySpace + "drop table " + REST);
                Pattern INSERT_CMD = Pattern.compile(ArbitrarySpace + "insert into " + "(\\S+|\\S+.*\\S+)" + REST);
                Pattern PRINT_CMD  = Pattern.compile(ArbitrarySpace + "print " + REST);
                Pattern SELECT_CMD = Pattern.compile(ArbitrarySpace + "select " + REST);

                // Stage 2 syntax, contains the clauses of commands.
                Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
                        "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)");
                Pattern SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                        "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                        "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                        "[\\w\\s+\\-*/'<>=!.]+?)*))?");
                Pattern CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" + SELECT_CLS.pattern());
                Pattern INSERT_CLS  = Pattern.compile("\\s*values\\s+(.+?\\s*(?:,\\s*.+?\\s*)*)");


                Matcher m;
                if ((m = CREATE_CMD.matcher(line)).matches()) {

                } else if ((m = LOAD_CMD.matcher(line)).matches()) {
                    String fileName = m.group(1);
                    if (dbnames.contains(fileName)) {
                        int index = dbnames.indexOf(fileName);
                        dbs.remove(index);
                        dbnames.remove(index);
                    }
                    String filePath = "examples/" + fileName + ".tbl";
                    Database dbToAdd = new Database();
                    try {
                        dbToAdd = Database.loadFile(filePath);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: There is no such file");
                    }
                    dbs.add(dbToAdd);
                    dbnames.add(fileName);
                } else if ((m = STORE_CMD.matcher(line)).matches()) {
                    String fileName = m.group(1);
                    if (!dbnames.contains(fileName)) {
                        System.out.println("Error: There is no such database in the memory");
                    } else {
                        int index = dbnames.indexOf(fileName);
                        Database toStore = dbs.get(index);
                        toStore.output(fileName);
                    }
                } else if ((m = DROP_CMD.matcher(line)).matches()) {
                    String fileName = m.group(1);
                    if (dbnames.contains(fileName)) {
                        int index = dbnames.indexOf(fileName);
                        dbs.remove(index);
                        dbnames.remove(index);
                    } else {
                        System.out.println("Error: There is no such database in the memory");
                    }
                } else if ((m = INSERT_CMD.matcher(line)).matches()) {
                    String fileName = m.group(1);
                    if (!dbnames.contains(fileName)) {
                        System.out.println("Error: There is no such database in the memory");
                    } else {
                        int index = dbnames.indexOf(fileName);
                        Database toAddDB = dbs.get(index);
                        Matcher m2 = INSERT_CLS.matcher(m.group(2));
                        m2.matches();
                        try {
                            String[] toAdd = m2.group(1).split("\\s+");
                            if (toAdd.length != toAddDB.colSize) {
                                System.out.println("Invalid: Row size incorrect");
                            } else {
                                toAddDB.addRow(toAdd);
                            }
                        } catch (Exception e) {
                            System.out.println("The command is invalid");
                        }
                    }
                } else if ((m = PRINT_CMD.matcher(line)).matches()) {
                    String fileName = m.group(1);
                    if (!dbnames.contains(fileName)) {
                        System.out.println("Error: There is no such database in the memory");
                    } else {
                        int index = dbnames.indexOf(fileName);
                        Database toPrint = dbs.get(index);
                        toPrint.print();
                    }
                } else if ((m = SELECT_CMD.matcher(line)).matches()) {

                } else {
                }
            }

            if (!line.trim().isEmpty()) {
                String result = db.transact(line);
                if (result.length() > 0) {
                    System.out.println(result);
                }
            }
            System.out.print(PROMPT);
        }

        in.close();
    }
}
