package edu.wsu.seniorproject.model;

import java.util.List;
import java.util.Observable;

public abstract class Sudoku extends Observable {
    public abstract void startGame();
    public abstract int[][] getPuzzle();
    public abstract int[][] getSolution();
    public abstract void createGrid();
    public abstract void endGame();
    public abstract void checkIfGameOver();
    public abstract void setChoice(int choice);
    public abstract int getChoice();
    public abstract void checkStatus();
    public abstract int[][] createSolution(int[][] puzzle, int idx);
    public abstract int[][] createPuzzle(int[][] puzzle);
    public abstract int[][] createPuzzle(int[][] puzzle, List<Integer> places);
    public abstract boolean isValid(int[][] puzzle);
    public abstract boolean isValid(int[][] puzzle, int idx, int[] solutionNum);
    public abstract int[][] duplicate(int[][] puzzle);
    public abstract int nextNum(int[][] puzzle, int x, int y, List<Integer> nums);
    public abstract boolean checkBlock(int[][] puzzle, int x, int y, int num);
    public abstract boolean checkY(int[][] puzzle, int x, int num);
    public abstract boolean checkX(int[][] puzzle, int y, int num);
}
