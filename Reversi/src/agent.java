import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class agent {
	static int[][] bestChildBoard;
	static StringBuffer sb = new StringBuffer();
	
	public static void main(String[] args) throws FileNotFoundException {
		fileRead();
		bestChildBoard = startState;
		
		switch (task) {
			case 1:
				cutOff = 1;
				max("root",startState, 0);
				printBoard(bestChildBoard);
				break;
			case 2:
				max("root",startState, 0);
				printBoard(bestChildBoard);
				System.out.println("Node,Depth,Value");
				System.out.print(sb);
				break;
			case 3:
				max("root",startState, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
				printBoard(bestChildBoard);
				System.out.println("Node,Depth,Value,Alpha,Beta");
				System.out.print(sb);
				break;
		}
		
	}
	
	
	private static int min(String name, int[][] state, int depth, int alpha, int beta) {
		if(depth==cutOff || isTerminal(state)){
			int tmp = eval(state);
			sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
			return tmp;
		}
		int minVal = Integer.MAX_VALUE;
		sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');

		List<Move> moves = Move.findMoves(state, -player);
		if(moves.isEmpty()){
			if(name.equals("pass")){
				System.out.println("im here! d= "+depth);
				int tmp = eval(state);
				sb.append(name).append(",").append(depth+1).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
				sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
				return tmp;
			}

			minVal = max("pass", clone(state), depth + 1, alpha, beta);
			if(minVal<beta)beta=minVal;
			sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
			return minVal;
		}
		
		for(Move move:moves){
			int [][] cloned = clone(state);
			Move.makeMove(cloned, move, -player);
			int val = max(move.toString(), cloned, depth + 1, alpha, beta);
			if(val<minVal){
				minVal = val;
				if(depth == 0)bestChildBoard = cloned;
			}
			
			if(alpha>=minVal) {
				sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');	
				return alpha;
			}
			if(val<beta){
				beta = val;
			}
			sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
		}
		return minVal;
	}
	private static int max(String name, int[][] state, int depth, int alpha, int beta) {
		if(depth==cutOff || isTerminal(state)){
			int tmp = eval(state);
			sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
			return tmp;
		}
		int maxVal = -Integer.MAX_VALUE;
		sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');

		List<Move> moves = Move.findMoves(state, player);
		if(moves.isEmpty()){
			if(name.equals("pass")){
				int tmp = eval(state);
				sb.append(name).append(",").append(depth+1).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
				sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
				return tmp;
			}
			maxVal = min("pass", clone(state), depth + 1, alpha, beta);
			if(maxVal>alpha)alpha=maxVal;
			sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
			return maxVal;
		}
		
		for(Move move:moves){
			int [][] cloned = clone(state);
			Move.makeMove(cloned, move, player);
			int val = min(move.toString(), cloned, depth + 1, alpha, beta);
			if(val>maxVal){
				maxVal = val;
				if(depth == 0)bestChildBoard = cloned;
			}
			
			if(maxVal>=beta) {
				sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
				return beta;
			}
			if(alpha<val){
				alpha = val;
			}
			sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append(",").append(makeString(alpha)).append(",").append(makeString(beta)).append('\n');
		}
		return maxVal;
	}
	
	
	
	
	private static int min(String name, int[][] state, int depth) {
		if(depth==cutOff || isTerminal(state)){
			int tmp = eval(state);
			sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append('\n');
			return tmp;
		}
		int minVal = Integer.MAX_VALUE;
		sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append('\n');

		List<Move> moves = Move.findMoves(state, -player);
		if(moves.isEmpty()){
			if(name.equals("pass")){
				int tmp = eval(state);
				sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append('\n');
				return tmp;
			}
			return max("pass", clone(state), depth + 1);
		}
		
		for(Move move:moves){
			int [][] cloned = clone(state);
			Move.makeMove(cloned, move, -player);
			int val = max(move.toString(), cloned, depth + 1);
			if(val<minVal){
				minVal = val;
				if(depth == 0)bestChildBoard = cloned;
			}
			sb.append(name).append(",").append(depth).append(",").append(makeString(minVal)).append('\n');
		}
		return minVal;
	}
	private static int max(String name, int[][] state, int depth) {
		if(depth==cutOff || isTerminal(state)){
			int tmp = eval(state);
			sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append('\n');
			return tmp;
		}
		int maxVal = -Integer.MAX_VALUE;
		sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append('\n');

		List<Move> moves = Move.findMoves(state, player);
		if(moves.isEmpty()){
			if(name.equals("pass")){
				int tmp = eval(state);
				sb.append(name).append(",").append(depth).append(",").append(makeString(tmp)).append('\n');
				return tmp;
			}
			return min("pass", clone(state), depth + 1);
		}
		
		for(Move move:moves){
			int [][] cloned = clone(state);
			Move.makeMove(cloned, move, player);
			int val = min(move.toString(), cloned, depth + 1);
			if(val>maxVal){
				maxVal = val;
				if(depth == 0)bestChildBoard = cloned;
			}
			sb.append(name).append(",").append(depth).append(",").append(makeString(maxVal)).append('\n');
		}
		return maxVal;
	}
	
	

	
	private static boolean isTerminal(int[][] state) {
		boolean blankFound = false, xFound = false, oFound = false;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(state[i][j]==0)blankFound = true;
				else if(state[i][j]==-1)xFound = true;
				else if(state[i][j]==1)oFound = true;
		if(!blankFound) return true;
		return xFound&&!oFound || oFound && !xFound;
	}
	private static String makeString(int maxVal) {
		if(maxVal == -Integer.MAX_VALUE) return "-Infinity";
		if(maxVal == Integer.MAX_VALUE) return "Infinity";
		return maxVal+"";
	}
	private static int[][] clone(int[][] state) {
		int [][] val = new int[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				val[i][j] = state[i][j];
		return val;
	}




	static int [][] heuristic = new int[8][8];
	private static int eval(int[][] state) {
		int sum = 0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				sum+=state[i][j]*heuristic[i][j];
		return sum;
	}

	static int task, player, cutOff;
	static int[][] startState = new int[8][8];
	
	private static void printBoard(int [][] board){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++)
				System.out.print(board[i][j]==0?"*":board[i][j]==1?"X":"O");
			System.out.println("");
		}
	}
	
	public static void fileRead() throws FileNotFoundException{
		FileReader file = new FileReader(new File("input.txt"));
		Scanner inFile = new Scanner(file);
		task = inFile.nextInt();
		String turn = inFile.next();
		player = turn.charAt(0);
		cutOff = inFile.nextInt();
		char[][] currentState = new char[8][8];
		
		for(int i=0;i<8;i++) {
			String read = inFile.next();
			//System.out.println("read value is..."+ read);
			for(int len=0;len<8;len++){
				currentState[i][len] = read.charAt(len);
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(currentState[i][j]=='*'){
					startState[i][j]= 0;
				}else if(currentState[i][j]==player){
					startState[i][j]= 1;
				}else{
					startState[i][j] = -1;	
				}
			}
		}
		player= player=='O'?-1:1;
	}
	static {
		//TODO check heuristic
		int [][] eval = heuristic;
		eval[0][7]=99;
		eval[0][0]=99;
		eval[7][0]=99;
		eval[7][7]=99;
		eval[0][1]=-8;
		eval[0][6]=-8;
		eval[7][1]=-8;
		eval[7][6]=-8;
		eval[6][0]=-8;
		eval[6][7]=-8;
		eval[1][0]=-8;
		eval[1][7]=-8;
		eval[0][2]=8;
		eval[0][5]=8;
		eval[2][0]=8;
		eval[2][7]=8;
		eval[5][0]=8;
		eval[5][7]=8;
		eval[3][0]=6;
		eval[3][7]=6;
		eval[4][0]=6;
		eval[4][7]=6;
		eval[3][3]=0;
		eval[3][4]=0;
		eval[4][3]=0;
		eval[4][4]=0;
		eval[1][1]=-24;
		eval[1][6]=-24;
		eval[6][1]=-24;
		eval[6][6]=-24;
		eval[1][2]=-4;
		eval[1][5]=-4;
		eval[6][2]=-4;
		eval[6][5]=-4;
		eval[0][3]=6;
		eval[0][4]=6;
		eval[7][3]=6;
		eval[7][4]=6;
		eval[7][2]=8;
		eval[7][5]=8;
		eval[1][3]=-3;
		eval[1][4]=-3;
		eval[6][3]=-3;
		eval[6][4]=-3;
		eval[3][1]=-3;
		eval[4][1]=-3;
		eval[3][6]=-3;
		eval[4][6]=-3;
		eval[2][1]=-4;
		eval[2][6]=-4;
		eval[5][1]=-4;
		eval[5][6]=-4;
		eval[2][2]=7;
		eval[2][5]=7;
		eval[5][2]=7;
		eval[5][5]=7;
		eval[2][3]=4;
		eval[2][4]=4;
		eval[3][2]=4;
		eval[3][5]=4;
		eval[4][2]=4;
		eval[4][5]=4;
		eval[5][3]=4;
		eval[5][4]=4;
	}
}
