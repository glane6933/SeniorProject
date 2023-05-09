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


    private Sudoku() {
        state = GameState.NOT_RUNNING;
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

    public void startNewGame() {
        createGrid();
        state = GameState.RUNNING;
        check = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    public void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case EASY -> this.difficulty = Difficulty.EASY;

            // remove the least amount of numbers from solution
            case MEDIUM -> this.difficulty = Difficulty.MEDIUM;

            // remove a medium amount of numbers from solution
            case HARD -> this.difficulty = Difficulty.HARD;

            // remove the most amount of numbers from solution
        }
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

    public int getSolutionNumber(int x, int y) {
        return solution[y][x];
    }

    public void createGrid() {
        //create a solution grid
        solution = createSolution(new int[BOARD_SIZE][BOARD_SIZE], 0);
        //create the puzzle grid
        puzzle = createPuzzle(duplicate(solution));
    }

    public void endGame() {
        state = GameState.COMPLETED;
    }

    public boolean checkIfGameOver() {
        if(checkStatus()) {
            state = GameState.COMPLETED;
            return true;
        } else {
            state = GameState.RUNNING;
            return false;
        }
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setNumber(int x, int y, int number) {
        puzzle[y][x] = number;
    }

    public int getChoice() {
        return choice;
    }

    public boolean checkStatus() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(check[i][j] = puzzle[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

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

        while(nums.size() > 0) {
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

    public List<Integer> generatePlacesList() {
        List<Integer> places = new ArrayList<>();
        for(int i = 0; i < 81; i++) {
            places.add(i);
        }
        Collections.shuffle(places);
        return places;
    }

    public int[][] createPuzzle(int[][] puzzle) {
        List<Integer> places = generatePlacesList();
        int removalCount = getRemovalCount(difficulty);
        while(places.size() > removalCount) {
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

    private int getRemovalCount(Difficulty difficulty) {
        // Adjust the number of removed numbers based on the difficulty
        return switch (difficulty) {
            case EASY -> 30; //
            case MEDIUM -> 15; //
            case HARD -> 0; // 25, 25, 23, 25, 24
        };
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
            while(nums.size() > 0) {
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
        } else if(!isValid(puzzle, idx+1, solutionNum)) {
            return false;
        }
        return true;
    }

    public int[][] duplicate(int[][] puzzle) {
        int[][] tmp = new int[9][9];
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                tmp[i][j] = puzzle[i][j];
            }
        }
        return tmp;
    }

    public int nextNum(int[][] puzzle, int x, int y, List<Integer> nums) {
        while(nums.size() > 0) {
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
