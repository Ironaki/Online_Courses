import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.Stopwatch;



/**
 * Created by Armstrong on 5/19/17.
 */
public class Boggle {

    Trie dict = new Trie();
    char[][] board;
    int rowSize;
    int colSize;

    public Boggle() {
        rowSize = 4;
        colSize = 4;
        board = new char[rowSize][colSize];
    }

    public Boggle(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        board = new char[rowSize][colSize];
    }

    public void addWordToDict(String s) {
        dict.insert(s);
    }

    public void addRowToBoard(String s, int row) {
        for (int i = 0; i < colSize; i++) {
            board[row][i] = s.charAt(i);
        }
    }

    public char[] neighborChar (int row, int col, char[][] board) {
        char[] neighbors = new char[9];

        int index = 0;
        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i < 0 || i > rowSize-1 || j < 0 || j > colSize-1) {
                    index += 1;
                    continue;
                }
                neighbors[index] = board[i][j];
                index += 1;
            }
        }

        return neighbors;
    }


    /* Returns a copy of the board*/
    private char[][] boardCopy(char[][] board) {
        char[][] copy = new char[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                copy[row][col] = board[row][col];
            }
        }
        return copy;
    }

    public Set<String> findAllWord (int row, int col) {
        Set<String> res = new HashSet<>();

        class state {
            private String s;
            private char[][] board;
            private int col;
            private int row;

            state(String s, char[][] board, int row, int col) {
                this.s = s;
                this.board = board;
                this.row = row;
                this.col = col;
            }

            String getString() {
                return s;
            }

            char[][] getBoard() {
                return board;
            }

            int getRow() {
                return row;
            }

            int getCol() {
                return col;
            }
        }

        Queue<state> toConsider = new ArrayDeque<>();

        char startChar = board[row][col];
        char[][] startBoard = boardCopy(board);
        startBoard[row][col] = 0;
        toConsider.add(new state(String.valueOf(startChar), startBoard, row, col));

        while (toConsider.size() != 0) {
            state first = toConsider.poll();
            String s = first.getString();
            int rowFirst = first.getRow();
            int colFirst = first.getCol();
            char[][] boardFirst = first.getBoard();
            if (dict.contains(s)) {
                res.add(s);
            } else if (!dict.isPrefix(s)) {
                continue;
            }
            char[] neighbors = neighborChar(rowFirst, colFirst, boardFirst);
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] == 0) {
                    continue;
                }
                if (i == 0) {
                    int newRow = rowFirst - 1;
                    int newCol = colFirst - 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 1) {
                    int newRow = rowFirst - 1;
                    int newCol = colFirst;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 2) {
                    int newRow = rowFirst - 1;
                    int newCol = colFirst + 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 3) {
                    int newRow = rowFirst;
                    int newCol = colFirst - 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 5) {
                    int newRow = rowFirst;
                    int newCol = colFirst + 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 6) {
                    int newRow = rowFirst + 1;
                    int newCol = colFirst - 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 7) {
                    int newRow = rowFirst + 1;
                    int newCol = colFirst;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
                if (i == 8) {
                    int newRow = rowFirst + 1;
                    int newCol = colFirst + 1;
                    char[][] newBoard = boardCopy(boardFirst);
                    newBoard[newRow][newCol] = 0;
                    String newS = s + String.valueOf(neighbors[i]);
                    toConsider.add(new state(newS, newBoard, newRow, newCol));
                }
            }
        }
        return res;
    }

    public void setDict(String path) {

        List<String> wordList = new ArrayList<>();
        try {
            wordList = Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            System.out.println("This path is invalid.");
        }

        for (String s: wordList) {
            boolean flag = true;
            for (int i = 0; i < s.length(); i++ ) {
                if (s.charAt(i)<97 || s.charAt(i) >122) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                dict.insert(s);
            }
        }
    }


    public void setBoard(String path) {
        List<String> boardRow = new ArrayList<>();
        try {
            boardRow = Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            System.out.println("This path is invalid.");
        }

        rowSize = boardRow.size();
        colSize = boardRow.get(0).length();
        board = new char[rowSize][colSize];

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                String aRow = boardRow.get(row);
                if (aRow.length() != colSize) {
                    throw new IllegalArgumentException("The board is not rectangular");
                }
                board[row][col] = aRow.charAt(col);
            }
        }
    }

    private class word implements Comparable<word> {
        String s;

        public word (String s) {
            this.s = s;
        }

        @Override
        public int compareTo(word w) {
            if (this.s.length() - w.s.length() > 0) {
                return -1;
            } else if (this.s.length() - w.s.length() < 0) {
                return 1;
            } else {
                return this.s.compareTo(w.s);
            }
        }
    }

    public PriorityQueue<word> findAllWordsByLength() {

        Set<String> allWords = new HashSet<>();
        PriorityQueue<word> res = new PriorityQueue<>();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                Set<String> wordsFromABox= findAllWord(row, col);
                for (String s: wordsFromABox) {
                    allWords.add(s);
                }
            }
        }

        for (String s: allWords) {
            res.add(new word(s));
        }

        return res;
    }

    public List<String> firstNLongestWord(int n) {
        List<String> res = new ArrayList<>();
        PriorityQueue<word> words = findAllWordsByLength();
        for (int i = 0; i < n; i++) {
            if (words.size() == 0) {
                break;
            }
            res.add(words.poll().s);
        }
        return res;
    }

    public void generateRandomBoard() {

        Random rand = new Random();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                board[i][j] = (char) (rand.nextInt(26)+97);
            }
        }
    }


    public static void main(String[] args) {
//       test();

        Stopwatch watch = new Stopwatch();

        int userRowSize = 4;
        int userColSize = 4;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-n")) {
                try{
                    userColSize = Integer.parseInt(args[i+1]);
                } catch (Exception e) {
                    System.out.println("The given width is invalid. Use default size 4");
                }
            }
            if (args[i].equals("-m")) {
                try{
                    userRowSize = Integer.parseInt(args[i+1]);
                } catch (Exception e) {
                    System.out.println("The given height is invalid. Use default size 4.");
                }
            }
        }

        if (userColSize < 0 || userRowSize < 0) {
            throw new IllegalArgumentException();
        }

        Boggle boggleMain = new Boggle(userRowSize, userColSize);

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                try {
                    boggleMain.setDict(args[i+1]);
                } catch (Exception e) {
                    System.out.println("The given dictionary is invalid. Use the default one.");
                }
            }
            if (args[i].equals("<")) {
                try {
                    boggleMain.setBoard(args[i+1]);
                } catch (Exception e) {
                    System.out.println("The given board is invalid; Generate a random one");
                }

            }
        }

        if (boggleMain.dict.getSize() == 0) {
            boggleMain.setDict("words");
        }
        if (boggleMain.board[0][0] == 0) {
            boggleMain.generateRandomBoard();
        }

        int K = 1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-k")) {
                try{
                    K = Integer.parseInt(args[i+1]);
                } catch (Exception e) {
                    System.out.println("The given number of longest words is invalid. Use the default 1");
                }
            }
        }

        if (K < 0) {
            throw new IllegalArgumentException();
        }

        Stopwatch watchTwo = new Stopwatch();
        List<String> KLongest = boggleMain.firstNLongestWord(K);
        System.out.println("The search for longest part takes " + watchTwo.elapsedTime() +" seconds");

        for (String s: KLongest) {
            System.out.println(s);
        }

        System.out.println("The program finishes in " + watch.elapsedTime() + " seconds");

    }


    public static void test() {
        Boggle testEins = new Boggle();

        Stopwatch watchEins = new Stopwatch();
        testEins.setDict("testDIct");
        System.out.println(watchEins.elapsedTime());

        System.out.println(testEins.dict.contains("aardvark"));

        System.out.println(testEins.dict.contains("aardvar"));
        System.out.println(testEins.dict.isPrefix("aardvar"));


        Stopwatch watchZwei = new Stopwatch();
        testEins.setBoard("testBoggle2");
        Set<String> wordFromT = testEins.findAllWord(0,1);
        System.out.println(wordFromT);

        List<String> sevenLongest= testEins.firstNLongestWord(1);

        System.out.println(sevenLongest);

        System.out.println(watchZwei.elapsedTime());

    }

}
