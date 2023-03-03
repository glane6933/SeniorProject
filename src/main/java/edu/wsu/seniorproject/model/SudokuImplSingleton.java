package edu.wsu.seniorproject.model;

public class SudokuImplSingleton {
    private static SudokuImpl model;

    private SudokuImplSingleton() {

    }

    public static SudokuImpl getInstance() {
        if(model == null) {
            model = new SudokuImpl();
        }
        return model;
    }
}
