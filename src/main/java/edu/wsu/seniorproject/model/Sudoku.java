package edu.wsu.seniorproject.model;

import edu.wsu.seniorproject.view.GameView;
import edu.wsu.seniorproject.view.MenuView;

import java.util.List;

public interface Sudoku {
    int[][] getPuzzle();
    int[][] getSolution();
    void createGrid();
    void endGame();
    void checkIfGameOver();
    void setChoice(int choice);
    int getChoice();
    void checkStatus();
    int[][] createSolution(int[][] puzzle, int idx);
    int[][] createPuzzle(int[][] puzzle);
    int[][] createPuzzle(int[][] puzzle, List<Integer> places);
    boolean isValid(int[][] puzzle);
    boolean isValid(int[][] puzzle, int idx, int[] solutionNum);
    int[][] duplicate(int[][] puzzle);
    int nextNum(int[][] puzzle, int x, int y, List<Integer> nums);
    boolean checkBlock(int[][] puzzle, int x, int y, int num);
    boolean checkY(int[][] puzzle, int x, int num);
    boolean checkX(int[][] puzzle, int y, int num);
    void setGameView(GameView view);
    void setMenuView(MenuView view);
}
