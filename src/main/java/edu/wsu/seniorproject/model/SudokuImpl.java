package edu.wsu.seniorproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuImpl extends Sudoku {
    public static final int BOARD_SIZE = 9;

    private int[][] puzzle;
    private int[][] solution;
    private boolean[][] check;
    private int choice;
    private GameState state;
    private String playerName;
    private int score;
    private int winStatus;


    public SudokuImpl() {
        state = GameState.NOT_RUNNING;
    }

    @Override
    public void startGame() {
        createGrid();
        check = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    @Override
    public int[][] getPuzzle() {
        return puzzle;
    }

    @Override
    public int[][] getSolution() {
        return solution;
    }

    @Override
    public void createGrid() {
        //create a solution grid
        solution = createSolution(new int[BOARD_SIZE][BOARD_SIZE], 0);
        //create the puzzle grid
        puzzle = createPuzzle(duplicate(solution));
        //observer stuff
        setChanged();
        notifyObservers(GameState.RUNNING);
    }

    @Override
    public void endGame() {
        checkStatus();
        setChanged();
        notifyObservers(GameState.COMPLETED);
    }

    @Override
    public void checkIfGameOver() {
        checkStatus();
        choice = 0;
        setChanged();
        notifyObservers(GameState.CHECK);
    }

    @Override
    public void setChoice(int choice) {
        this.choice = choice;
        setChanged();
        notifyObservers(GameState.CHOICE);
    }

    @Override
    public int getChoice() {
        return choice;
    }

    @Override
    public void checkStatus() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                check[i][j] = puzzle[i][j] == solution[i][j];
            }
        }
    }

    /**
     * Generates a solution to a possibly completed Sudoku puzzle.
     *
     * @param puzzle A 2D integer array representing the possibly completed Sudoku puzzle.
     * @param idx    An integer representing the index of the next box to be filled in the puzzle.
     * @return A 2D integer array representing the completed Sudoku puzzle, or an array of size 0x0 if no solution was found.
     */
    @Override
    public int[][] createSolution(int[][] puzzle, int idx) {
        if(idx>80) {
            return puzzle;
        }
        int x = idx % 9;
        int y = idx / 9;

        List<Integer> nums = new ArrayList<>();
        for(int i = 0; i <= 9; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);

        while(!nums.isEmpty()) {
            int num = nextNum(puzzle, x, y, nums);
            if(num == -1) {
                return new int[0][0];
            }
            puzzle[y][x] = num;
            int[][] tmp = createSolution(puzzle, idx + 1);
            if(tmp != null) {
                return tmp;
            }
            puzzle[y][x] = 0;
        }
        return new int[0][0];
    }

    /**
     * Generates a new Sudoku puzzle by removing some numbers from the input puzzle.
     *
     * @param puzzle A 2D integer array representing a completed Sudoku puzzle.
     * @return A new 2D integer array representing a partially completed Sudoku puzzle.
     */
    @Override
    public int[][] createPuzzle(int[][] puzzle) {
        List<Integer> places = new ArrayList<>();
        for(int i = 0; i < 81; i++) {
            places.add(i);
        }
        Collections.shuffle(places);
        return createPuzzle(puzzle, places);
    }

    /**
     * Generates a new Sudoku puzzle by removing some numbers from the input puzzle
     * based on a shuffled list of cell indices.
     *
     * @param puzzle A 2D integer array representing a completed Sudoku puzzle.
     * @param places A list of shuffled integers representing cell indices in the 9x9 Sudoku grid.
     * @return A new 2D integer array representing a possibly completed Sudoku puzzle.
     */
    @Override
    public int[][] createPuzzle(int[][] puzzle, List<Integer> places) {
        while(!places.isEmpty()) {
            int pos = places.remove(0);
            int x = pos % 9;
            int y = pos / 9;
            int tmp = puzzle[y][x];
            puzzle[y][x] = 0;

            if(!isValid(puzzle)) {
                puzzle[y][x] = tmp;
            }
        }
        return puzzle;
    }

    @Override
    public boolean isValid(int[][] puzzle) {
        return isValid(puzzle, 0, new int[] { 0 });
    }

    @Override
    public boolean isValid(int[][] puzzle, int idx, int[] solutionNum) {
        if(idx > 80) {
            return ++solutionNum[0] == 1;
        }
        int x = idx % 9;
        int y = idx / 9;

        if(puzzle[y][x] == 0) {
            List<Integer> nums = new ArrayList<>();
            for(int i = 1; i <= 9; i++) {
                nums.add(i);
            }
            while(!nums.isEmpty()) {
                int num = nextNum(puzzle, x, y, nums);
                if(num == -1) {
                    break;
                }
                puzzle[y][x] = num;

                if(!isValid(puzzle, idx+1, solutionNum)) {
                    puzzle[y][x] = 0;
                    return false;
                }
                puzzle[y][x] = 0;
            }
        } else return isValid(puzzle, idx + 1, solutionNum);
        return true;
    }

    @Override
    public int[][] duplicate(int[][] puzzle) {
        return Arrays.copyOf(puzzle, puzzle.length);
    }

    @Override
    public int nextNum(int[][] puzzle, int x, int y, List<Integer> nums) {
        while(!nums.isEmpty()) {
            int num = nums.remove(0);
            if(checkX(puzzle, y, num) && checkY(puzzle, x, num) && checkBlock(puzzle, x, y, num)) {
                return num;
            }
        }
        return -1;
    }

    /*
    Checks if num is present in the 3x3 block. If found, return false (not placeable).
    Otherwise, return true (is placeable).
     */
    @Override
    public boolean checkBlock(int[][] puzzle, int x, int y, int num) {
        int xCheck = x < 3 ? 0 : x < 6 ? 3 : 6;
        int yCheck = y < 3 ? 0 : y < 6 ? 3 : 6;
        for(int i = yCheck; i < yCheck + 3; i++) {
            for(int j = xCheck; j < xCheck + 3; j++) {
                if(puzzle[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkY(int[][] puzzle, int x, int num) {
        for(int i = 0; i < 9; i++) {
            if(puzzle[i][x] == num) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkX(int[][] puzzle, int y, int num) {
        for(int i = 0; i < 9; i++) {
            if(puzzle[y][i] == num) {
                return false;
            }
        }
        return true;
    }
}
