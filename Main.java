import java.util.stream.IntStream;

public class SudokuSolver {

  private static final int BOX_SIZE = 3;

  private static final int GRID_SIZE = BOX_SIZE * BOX_SIZE;

  public static void main(String[] args) {

    int[][] board = {
      {0, 0, 0, 6, 0, 0, 4, 0, 0},
      {7, 0, 0, 0, 0, 3, 6, 0, 0},
      {0, 0, 0, 0, 9, 1, 0, 8, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 5, 0, 1, 8, 0, 0, 0, 3},
      {0, 0, 0, 3, 0, 6, 0, 4, 5},
      {0, 4, 0, 2, 0, 0, 0, 6, 0},
      {9, 0, 3, 0, 0, 0, 0, 0, 0},
      {0, 2, 0, 0, 0, 0, 1, 0, 0}
    };

    System.out.println("Initial board");
    printBoard(board);
    System.out.println();

    if (solveBoard(board)) {
      System.out.println("Solved successfully");
    }
    else {
      System.out.println("Unsolvable");
    }
    printBoard(board);
  }

  private static void printBoard(int[][] board) {
    for (int row = 0; row < GRID_SIZE; row++) {
      if (row % BOX_SIZE == 0 && row != 0) {
        System.out.println("---------------");
      }
      for (int column = 0; column < GRID_SIZE; column++) {
        if (column % BOX_SIZE == 0 && column != 0) {
          System.out.print(" | ");
        }
        System.out.print(board[row][column]);
      }
      System.out.println();
    }
  }

  private static boolean allowedInRow(int[][] board, int number, int row) {
    return IntStream.range(0, GRID_SIZE).noneMatch(column -> board[row][column] == number);
  }

  private static boolean allowedInColumn(int[][] board, int number, int column) {
    return IntStream.range(0, GRID_SIZE).noneMatch(row -> board[row][column] == number);
  }

  private static boolean allowedInBox(int[][] board, int number, int row, int column) {
    final int boxRow = row - (row % BOX_SIZE);
    final int boxColumn = column - (column % BOX_SIZE);
    for (int i = 0; i < BOX_SIZE; i++) {
      for (int j = 0; j < BOX_SIZE ; j++) {
        if (board[boxRow + 1][boxColumn + 1] == number) {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean isAllowed(int[][] board, int number, int row, int column) {
    return allowedInRow(board, number, row) &&
        allowedInColumn(board, number, column) &&
        allowedInBox(board, number, row, column);
  }

  private static boolean solveBoard(int[][] board) {
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++) {
        if (board[row][column] == 0) {
          for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
            if (isAllowed(board, numberToTry, row, column)) {
              board[row][column] = numberToTry;
              if (solveBoard(board)) {
                return true;
              }
              else{
                board[row][column] = 0;
              }
            }
          }
          return false;
        }
      }
    }
    return true;
  }
}