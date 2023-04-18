package edu.wsu.model;

import java.util.*;

public class Sudoku {
    public static final int BOARD_SIZE = 9;

    public GameState state;
    private static Sudoku gameInstance;
    private int[][] puzzle;
    private int[][] solution;
    private boolean[][] check;
    private int choice;
    Difficulty difficulty;
    private boolean numberSelected;
    private int score;


    private Sudoku() {
        state = GameState.NOT_RUNNING;
        score = 1000;
    }

    public static Sudoku getInstance() {
        if(gameInstance == null) {
            gameInstance = new Sudoku();
        }
        return gameInstance;
    }

    public void togglePause() {
        if(state == GameState.PAUSED) {
            state = GameState.RUNNING;
        } else {
            state = GameState.PAUSED;
        }
    }

    public void update(double time) {
        if(state == GameState.PAUSED) return;

        if(time % 10 == 0) {
            score -= 10;
        }
    }

    public void startNewGame() {
        createGrid();
        state = GameState.RUNNING;
        score = 1000;
        numberSelected = false;
        check = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    public void setDifficulty(Difficulty difficulty) {
        switch(difficulty) {
            case EASY:
                this.difficulty = Difficulty.EASY;
                // remove the least amount of numbers from solution
                break;
            case MEDIUM:
                this.difficulty = Difficulty.MEDIUM;
                // remove a medium amount of numbers from solution
                break;
            case HARD:
                this.difficulty = Difficulty.HARD;
                // remove the most amount of numbers from solution
                break;
        }
    }

    public boolean isNumberSelected() {
        return numberSelected;
    }

    public int getScore() {
        return score;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void createGrid() {
        //create a solution grid
        solution = createSolution(new int[BOARD_SIZE][BOARD_SIZE], 0);
        //create the puzzle grid
        puzzle = createPuzzle(duplicate(solution));
    }

    public void endGame() {
        checkStatus();
        state = GameState.COMPLETED;
    }

    public void checkIfGameOver() {
        checkStatus();
        choice = 0;
        state = GameState.CHECK;
    }

    public void setChoice(int choice) {
        this.choice = choice;
        state = GameState.CHOICE;
    }

    public int getChoice() {
        return choice;
    }

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
    public int[][] createSolution(int[][] puzzle, int idx) {
        if(idx>80) {
            return puzzle;
        }
        int x = idx % 9;
        int y = idx / 9;

        List<Integer> nums = new ArrayList<>();
        for(int i = 1; i <= 9; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);

        while(!nums.isEmpty()) {
            int num = nextNum(puzzle, x, y, nums);
            if(num == -1) {
                return null;
            }
            puzzle[y][x] = num;
            int[][] tmp = createSolution(puzzle, idx + 1);
            if(tmp != null) {
                return tmp;
            }
            puzzle[y][x] = 0;
        }
        return null;
    }

    /**
     * Generates a new Sudoku puzzle by removing some numbers from the input puzzle.
     *
     * @param puzzle A 2D integer array representing a completed Sudoku puzzle.
     * @return A new 2D integer array representing a partially completed Sudoku puzzle.
     */
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
    public int[][] createPuzzle(int[][] puzzle, List<Integer> places) {
        while(places.size() > 0) {
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

    public boolean isValid(int[][] puzzle) {
        return isValid(puzzle, 0, new int[] { 0 });
    }

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

    public int[][] duplicate(int[][] puzzle) {
        return Arrays.copyOf(puzzle, puzzle.length);
    }

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

    public boolean checkY(int[][] puzzle, int x, int num) {
        for(int i = 0; i < 9; i++) {
            if(puzzle[i][x] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean checkX(int[][] puzzle, int y, int num) {
        for(int i = 0; i < 9; i++) {
            if(puzzle[y][i] == num) {
                return false;
            }
        }
        return true;
    }
}
