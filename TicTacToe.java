import java.util.Scanner;
import java.util.ArrayList;
public class TicTacToe {
  public static void main(String[] args) {
    boolean playing = true;
    char[][] board = new char[3][3];
    char player, curIcon;
    int[] move = {-1,-1};
    int[] score = {0,0};
    System.out.println("This is the game of Tic Tac Toe.");
    System.out.println("You will be playing against the computer.");
    Scanner sc = new Scanner(System.in);
    player = randIcon();
    while (playing) {
      board = clearBoard(board);
      curIcon = 'O';
      System.out.println("You will be playing "+player+".");
      while (!findWinner(board,curIcon) && numMoves(board) > 0) {
      	curIcon = swapIcon(curIcon);
        if (player == curIcon) {
          System.out.print("Enter x coordinate: ");
          move[1] = Integer.parseInt(sc.nextLine());
          System.out.print("Enter y coordinate: ");
          move[0] = Integer.parseInt(sc.nextLine());
          if (0 > move[0] || move[0] > 2 || 0 > move[1] || move[1] > 2 || board[move[0]][move[1]] != ' ') {
            System.out.println("You inputted an illegal move. -1 point");
            score[0] -= 1;
            curIcon = swapIcon(curIcon);
            continue;
          }
        } else {
          move = findMove(board,curIcon);
        }
        board[move[0]][move[1]] = curIcon;
        if (curIcon != player || findWinner(board,curIcon) || numMoves(board) == 0) {
        	dispBoard(board);
        }
      }
      if (findWinner(board,curIcon)) {
	      System.out.println(curIcon+" won!");
	      if (curIcon == player) {
	        score[0] += 100;
	      } else {
	        score[1] += 100;
	      }
      } else {
      	System.out.println("It's a tie.");
      }
      System.out.println("You: "+score[0]+" Computer: "+score[1]);
      System.out.print("Play again? (y/n) ");
      playing = sc.nextLine().charAt(0) == 'y';
      player = swapIcon(player);
    }
    sc.close();
  }
  public static void dispBoard(char[][] board) {
    for (int i=0;i<board.length;i++) {
      for (int j=0;j<board[i].length;j++) {
        System.out.print(" "+board[i][j]+" ");
        if (j<board[i].length-1) {
          System.out.print("|");
        } else {
          System.out.println();
        }
      }
      if (i<board.length-1) {
        System.out.println("---|---|---");
      }
    }
  }
  public static char[][] clearBoard(char[][] board) {
    for (int i=0;i<3;i++) {
      for (int j=0;j<3;j++) {
        board[i][j] = ' ';
      }
    }
    return board;
  }
  public static char swapIcon(char icon) {
    if (icon == 'X') {
      return 'O';
    } else {
        return 'X';
    }
  }
  public static char randIcon() {
    if ((int)(Math.random()+0.5) == 1) {
      return 'O';
    } else {
      return 'X';
    }
  }
  public static int[] findMove(char[][] board, char player) {
    int[] bestMove = {-1,-1};
    double bestScore = -1;
    double score;
    for (int[] move:availableMoves(board)) {
    	score = findScore(board,player,move);
    	if (score > bestScore) {
    		bestScore = score;
    		bestMove = move;
    	}
    }
    return bestMove;
  }
  public static double findScore(char[][] board, char player, int[] move) {
  	char[][] newboard = new char[board.length][];
  	for(int i=0;i<board.length;i++)
  	    newboard[i] = board[i].clone();
  	newboard[move[0]][move[1]] = player;
  	if (findWinner(newboard,player)) {
  		return 1;
  	}
  	double sum = 0;
  	int num = 0;
  	double score;
  	for (int[] enemyMove:availableMoves(newboard)) {
  		num++;
  		score = findScore(newboard,swapIcon(player),enemyMove);
  		if (score == 1) {
  			return 0;
  		}
  		sum += (1-score);
  	}
  	if (num == 0) {
  		return 0.5;
  	}
  	return sum/num;
  }
  public static boolean findWinner(char[][] board, char player) {
    int[] sums = new int[board.length+board[0].length+2];
    for (int i=0;i<board.length;i++) {
      for (int j=0;j<board.length;j++) {
        if (board[i][j] == player) {
          sums[i] += 1;
          sums[board.length+j] += 1;
          if (i == j) {
            sums[board.length+board[0].length] += 1;
          }
          if (i + j + 1 == board.length) {
            sums[board.length+board[0].length+1] += 1;
          }
        }
      }
    }
    for (int sum:sums) {
      if (sum == 3) {
        return true;
      }
    }
    return false;
  }
  public static ArrayList<int[]> availableMoves(char[][] board) {
  	ArrayList<int[]> moves = new ArrayList<int[]>();
  	for (int i=0;i<board.length;i++) {
      for (int j=0;j<board.length;j++) {
      	if (board[i][j] == ' ') {
      		int[] move = {i,j};
      		moves.add(move);
      	}
      }
  	}
  	return moves;
  }
  public static int numMoves(char[][] board) {
  	int moves = 0;
  	for (int i=0;i<board.length;i++) {
      for (int j=0;j<board.length;j++) {
      	if (board[i][j] == ' ') {
      		moves++;
      	}
      }
  	}
  	return moves;
  }
}