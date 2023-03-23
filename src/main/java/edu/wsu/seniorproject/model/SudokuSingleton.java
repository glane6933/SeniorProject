package edu.wsu.seniorproject.model;

public class SudokuSingleton {
    private static Sudoku model;

    public static Sudoku getInstance() {
        if(model == null) {
            model = new SudokuImpl();
        }
        return model;
    }
}
