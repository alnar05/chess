package puzzles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PuzzleDatabase {
    public static final String databasePath = "database.txt";
    private ArrayList<Puzzle> puzzles;

    public PuzzleDatabase() {
        load();
    }
    public void load() {
        try {
            Scanner sc = new Scanner(new FileInputStream(databasePath));
            int puzzleCount = sc.nextInt();
            puzzles = new ArrayList<Puzzle>(puzzleCount);
            sc.nextLine();
            for (int i = 0; i < puzzleCount; i++) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }
            Collections.sort(puzzles);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the database file.");
            System.exit(0);
        } catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(databasePath);
            pw.println(puzzles.size());
            for (int i = 0; i < puzzles.size(); i++)
                pw.println(puzzles.get(i));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }
    public void addPuzzlesFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new FileInputStream(filename));
            while (sc.hasNextLine()) {
                Puzzle current = new Puzzle(sc.nextLine(), sc.nextLine());
                if (!contains(current))
                    puzzles.add(current);
            }
            Collections.sort(puzzles);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the specified file with puzzles.");
            System.exit(0);
        }
        catch (MalformedPuzzleException e) {
            System.out.println("Malformed puzzle in the database.");
            System.exit(0);
        }
    }
    public int getSize() {
        return puzzles.size();
    }
    public Puzzle getPuzzle(int i) {
        return puzzles.get(i);
    }

    private boolean contains(Puzzle p) {
        for (int i = 0; i < puzzles.size(); i++)
            if (puzzles.get(i).equals(p))
                return true;
        return false;
    }
}
