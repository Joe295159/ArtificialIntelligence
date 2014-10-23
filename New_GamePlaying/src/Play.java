
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


public class Play {
	static char[][] currentState= new char[8][8];
	static int[][] intState = new int [8][8];
	static int[][] printState = new int [8][8];
	static int[][] eval = new int [8][8];
	static int count=0;
	static int task;
	static int d=0;
	static char player;
	static int cutOff;
	static int flag=0;
	static int playerInt=1;
	static int oppInt=0;
	static int flagplayer=0;
	static int flagopponent=0;
	static char[][] outputState= new char[8][8];
	static ArrayList<LegalMoves> log = new ArrayList<LegalMoves>();
	public static void main (String args[]) throws FileNotFoundException{
		fileRead();
		if(task==1){
			greedy();
		}
		else if(task==2)
		{
			minimax();
		}
		else if(task==3)
		{
			//
		}
		
	}
	
	public static void fileRead() throws FileNotFoundException{
		FileReader file = new FileReader(new File("input.txt"));
		Scanner inFile = new Scanner(file);
		task = inFile.nextInt();
		String turn = inFile.next();
		player = turn.charAt(0);
		cutOff = inFile.nextInt();
		
		for(int i=0;i<8;i++) {
			String read = inFile.next();
//			System.out.println("read value is..."+ read);
			for(int len=0;len<8;len++){
				currentState[i][len] = read.charAt(len);
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(currentState[i][j]=='*'){
					intState[i][j]= -1;
				}else if(currentState[i][j]==player){
					intState[i][j]= 1;
				}else{
					intState[i][j] = 0;	
				}
			}
		}
	}
	public static int evalValues(int x, int y){
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
		eval[5][7]=9;
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
//		System.out.println("eval["+x+"]["+y+"]"+ eval[x][y]);
		//TODO check the eval values
		return eval[x][y];
	}
	public static boolean legalMove(int x, int y, int[][] state, int currentPlayer, CopyOnWriteArrayList<LegalMoves> moves, int depth, int v){
		int tempX,tempY,count;
		tempX = x-1;
		tempY = y;
		count = 0;
		if (state[x][y] != -1) {
			return false;
		}
		//Going top from (x,y) position
		while (tempX>=0) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX--;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x+1;
		tempY = y;
		count = 0;
		//Going bottom from (x,y) position
		while (tempX<=7) {
//				System.out.println("Temp x: "+ tempX);
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX++;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x;
		tempY = y-1;
		count = 0;
		//Going left from (x,y) position
		while (tempY>=0) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempY--;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x;
		tempY = y+1;
		count = 0;
		//Going right from (x,y) position
		while (tempY<=7) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempY++;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x-1;
		tempY = y-1;
		count = 0;
		//Going top left from (x,y) position
		while (tempX>=0 && tempY>=0) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX--;
				tempY--;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x+1;
		tempY = y-1;
		count = 0;
		//Going bottom left from (x,y) position
		while (tempX<=7 && tempY>=0) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX++;
				tempY--;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x-1;
		tempY = y+1;
		count = 0;
		//Going top right from (x,y) position
		while (tempX>=0 && tempY<=7) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX--;
				tempY++;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}
		tempX = x+1;
		tempY = y+1;
		count = 0;
		//Going bottom right from (x,y) position
		while (tempX<=7 && tempY<=7) {
			if (state[tempX][tempY] != currentPlayer && state[tempX][tempY] != -1) {
				count++;
				tempX++;
				tempY++;
			} else {
				break;
			}
		}
		if (count > 0 && inBoard(tempX, tempY) && state[tempX][tempY] == currentPlayer) {
			LegalMoves nextMove = new LegalMoves(x, y, count, "name", depth, valPrint(v));
//			moves.add(nextMove);
			return true;
		}	
		return false;
	}
	
	public static boolean inBoard(int x, int y) {
		if (x>=0 && x<=7 && y>=0 && y<=7) {
			return true;
		} else {
			return false;
		}
	}
	
	/*public static int checkmove(int tempx, int tempy, int x, int y, int[][] state, int currentPlayer, CopyOnWriteArrayList<LegalMoves> moves, int count, int depth,int v){
		int other=-22;
		//System.out.println("value of current value in checkmove="+ currentPlayer);
		if (currentPlayer==1)
			other =0;
		else if(currentPlayer==0)
			other =1;
		//TODO swap numbers 0,1,-1 based on players!
		if(state[tempx][tempy]==other){
			count++;
			return count;
		}else if(state[tempx][tempy]==currentPlayer){
			if(count>0){
				//TO DO calculate and pass the eval function here
				System.out.println("value of x:"+x+" value of y:"+y);
				String name=convert(x, y);
				String value= valPrint(v);
				LegalMoves nextMove= new LegalMoves(x, y, count,name,depth,value);
				moves.add(nextMove);
				count=0;
				//check what happens to global count variable here? And change position of declaration if necessary
			}else {
				count=0;
				//TODO not a legal move. anything to be done here?
			}
		}else count =0;
		return count;	
	}*/
	
	public static int[][] legalMoveBoardValue(int x, int y, int[][] state, int currentPlayer){
		int[][]statePosition = state;
		if (statePosition[x][y]==currentPlayer){
			int tempx =x;
			int tempy =y;
			int count =0;
			//case 1: left
			do{
				tempy--;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y, state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 1");
					for(int i=y;i>tempy;i--){
//						System.out.println("case1 x="+x+" y="+i);
						statePosition[x][i] = currentPlayer;
					}
				}
			}while(count>0);
			//case 2: right
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempy++;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y, state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 2");
					for(int i=y;i<tempy;i++){
//						System.out.println("case2 x="+x+" y="+i);
						statePosition[x][i] = currentPlayer;
					}
				}
				//TODO count=0;
			}while(count>0);
			//case 3: up
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempx--;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					
					count = changeBoard(tempx, tempy,x,y, state,currentPlayer);
				}
//				System.out.println("count!!!="+count);
				if(count==100){
					count = 0;
//					System.out.println("case 3");
//					System.out.println("im here...CASE3!!!!!!");
					for(int i=x;i>tempx;i--){
//						System.out.println("case3 x="+i+" y="+y);
						statePosition[i][y] = currentPlayer;
					}
				}
			}while(count>0);
			//case 4: bottom
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempx++;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y,state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 4");
//					System.out.println("im here!!!!!!");
					for(int i=x;i<tempx;i++){
//						System.out.println("case4 x="+i+" y="+y);
						statePosition[i][y] = currentPlayer;
					}
				}
			}while(count>0);
			//case 5 :top right
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempx--;
				tempy++;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y,state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 5");
					int temp=y-1;
					for(int i=x;i>tempx;i--){
						temp++; //TODO check correctness of j loop
//							System.out.println("case5 x="+i+" y="+temp);
							statePosition[i][temp] = currentPlayer;
					}
				}
			}while(count>0);
			//case 6: top left
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempx--;
				tempy--;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y, state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 6");
					int temp=y+1;
					for(int i=x;i>tempx;i--){
						temp--; //TODO check correctness of j loop
//						System.out.println("case6 x="+i+" y="+temp);
						statePosition[i][temp] = currentPlayer;
					}
				}
			}while(count>0);
			//case 7: bottom right
			count =0;
			tempx =x;
			tempy =y;
			
			do{
				tempx++;
				tempy++;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					//TODO is this while condition for temp ok?s
					count = changeBoard(tempx, tempy,x,y,state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 7");
					int temp=y-1;
//					System.out.println("x===="+x+"y===="+y);
					for(int i=x;i<tempx;i++){
						 //TODO check correctness of j loop
						temp++;
//							System.out.println("case7 x="+i+" y="+temp);
							statePosition[i][temp] = currentPlayer;
					}
					
				}
			}while(count>0);
			//case 8: bottom left
			count =0;
			tempx =x;
			tempy =y;
			do{
				tempx++;
				tempy--;
				if(!(tempx<0)&& !(tempy<0) && !(tempx>7)&& !(tempy>7)){
					count = changeBoard(tempx, tempy,x,y,state,currentPlayer);
				}
				if(count==100){
					count = 0;
//					System.out.println("case 8");
					int temp=y+1;
					for(int i=x;i<tempx;i++){
						temp--;
						//TODO check correctness of j loop
//							System.out.println("case 8 x="+i+" y="+temp);
							statePosition[i][temp] = currentPlayer;
					}
					
				}
			}while(count>0);
		}
		//TODO change the return 0 and return x
		return statePosition;
	}
	
	public static int changeBoard(int tempx, int tempy, int x, int y, int[][] state, int currentPlayer){
		//System.out.println("Inside changeBoard function.. currentValue= "+currentPlayer);
		int other=-22;
		if (currentPlayer==1)
			other =0;
		else if(currentPlayer==0)
			other =1;
		//TODO swap numbers 0,1,-1 based on players!
		if(state[tempx][tempy]==other){
			//System.out.println("VALUE  x:"+ x+ "  y:"+y);
			
			count++;
			//return count;
		}else if(state[tempx][tempy]==currentPlayer){
			if(count>0){
				//TO DO calculate and pass the eval function here
				count=100;
				//check what happens to global count variable here? And change position of declaration if necessary
			}else {
				count=0;
				//TODO not a legal move. anything to be done here?
			}
		}else count =0;
		return count;	
	}
	
	public static CopyOnWriteArrayList<LegalMoves> nextMove(int[][] state, int currentPlayer, int depth, int v){
		int[][] tempState = new int [8][8];
		
		CopyOnWriteArrayList<LegalMoves> moves = new CopyOnWriteArrayList<LegalMoves>();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if (legalMove(i,j,state,currentPlayer,moves, depth, v)) {
					tempState = returnCopy(state);
					tempState[i][j] = currentPlayer;
					int value = evalBoardValue(legalMoveBoardValue(i, j, tempState, currentPlayer));
					LegalMoves legalMove = new LegalMoves(i, j, 0, convert(i, j), depth, valPrint(value));
					moves.add(legalMove);
				}
			}
		}
		return moves;
	}
	
	public static int[][] returnCopy(int[][] state) {
		int[][] copyState = new int[8][8];
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				copyState[i][j] = state[i][j];
			}
		}
		return copyState;
	}

	public static int evalBoardValue(int[][] state){
		//System.out.println("Entered evalBoardValue.....");
		int playerValue=0,oppValue=0,boardValue=0;
		//System.out.println("playerInt value="+playerInt);
		//System.out.println("oppInt value="+oppInt);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(state[i][j]==playerInt){
					//System.out.println("state["+i+"]["+j+"]"+state[i][j]);
					//System.out.println("playerInt value="+playerInt);
					//TODO change these values to generalize
					playerValue=playerValue+evalValues(i, j);
					
				}else if(state[i][j]==oppInt){
					//System.out.println("state["+i+"]["+j+"]"+state[i][j]);
					//System.out.println("oppInt value="+oppInt);
					oppValue = oppValue+evalValues(i,j);
				}
			}
			boardValue= playerValue-oppValue;
			//System.out.println("playerValue: "+playerValue+ " oppValue: "+oppValue +" boardValue: "+boardValue);
			//TO DO check this evaluation in the pdf
		}
		//System.out.println("The value returned at evalBoard Value function="+boardValue);
		return boardValue;
	}
	
	/*public static void greedy() throws FileNotFoundException{
		fileRead();
		nextMove(state);
		//System.out.println("In here...");
		for(LegalMoves item: moves){
			System.out.println("move lists is: "+item);
		}
		//System.out.println("moves");
		int nextMoveX, nextMoveY;
		int maxVal =0;
		for(LegalMoves item: moves){
		if(item.eval> maxVal)
			maxVal = item.eval;
			nextMoveX = item.x;
			nextMoveY = item.y;
		}
		//TODO output the value in required format!!
	}*/

	public static String convert(int x, int y){
		String alphabet;
		int num;
		String name, numString;
		if(y==0){
			alphabet= "a";
		}else if(y==1){
			alphabet= "b";
		}else if(y==2){
			alphabet= "c";
		}else if(y==3){
			alphabet= "d";
		}else if(y==4){
			alphabet= "e";
		}else if(y==5){
			alphabet= "f";
		}else if(y==6){
			alphabet= "g";
		}else if(y==7){
			alphabet= "h";
		}else if (x==-50 && y==-50){
			alphabet="root";
		}else {
			alphabet="pass";
		}
		num=x+1;
		numString= String.valueOf(num);
		if (alphabet.length() > 1) {
			return alphabet;
		}
		name=alphabet.concat(numString);
		return name;
	}

	public static String valPrint(int v){
		String val;
		if(v==Integer.MAX_VALUE){
			val = "Infinity";
		}else if(v==Integer.MIN_VALUE){
			val= "-Infinity";
		}else
			val = Integer.toString(v);
		return val;
	}
	
	
	/*public static void objprint( LegalMoves obj, int[][] state){
		System.out.println("obj x and y"+ obj.x+obj.y);
		state[i][j]=
		
	}*/
	
	public static void printOutput(int[][]state){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(state[i][j]==-1){
					outputState[i][j]='*';
				}
				else if(state[i][j]==0){
					outputState[i][j]='O';
				}else if(state[i][j]==1){
					outputState[i][j]='X';
				}
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(outputState[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
		
	}
	public static void printLog(){
		if(task==2)
		{
			System.out.println("Node,Depth,Value");
			for(LegalMoves move : log)
			{
				System.out.println(move.name+","+move.depth+","+move.value);
			}
		}
	}
	
	public static void greedy(){
		d=0;
		int currentPlayer = playerInt;
		CopyOnWriteArrayList<LegalMoves> nextMoves = nextMove(intState, currentPlayer, 0, 0);
		int bestVal = Integer.MIN_VALUE;
		LegalMoves currentMove;
		currentMove = new LegalMoves(-50, -50, 0, convert(-50, -50), 0, valPrint(bestVal));
		LegalMoves bestMove = null;
		int[][] newBoard= returnCopy(intState);
		log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		for (LegalMoves legalMove : nextMoves) {
			newBoard= returnCopy(intState);
			newBoard[legalMove.x][legalMove.y] = currentPlayer;
			newBoard = legalMoveBoardValue(legalMove.x, legalMove.y, newBoard, currentPlayer);
//			int val = maxFinder(newBoard,cutOff-1, d,legalMove.x,legalMove.y,bestVal,nextMoves);
			int val = minFinder(newBoard, 1-1, legalMove.x, legalMove.y, getOppPlayer(currentPlayer));
			if (val > bestVal) {
				bestVal = val;
				bestMove = legalMove;
			}
			currentMove.value = valPrint(bestVal);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
//			System.out.println("root,0,"+bestVal);
		}
		if (bestMove == null) {
			// No possible moves
			currentPlayer = getOppPlayer(currentPlayer);
			CopyOnWriteArrayList<LegalMoves> nextMovesOpp = nextMove(intState, currentPlayer, 0, 0);
			if (nextMovesOpp.isEmpty()) {
				printOutput(intState);
				printLog();
				return;
			}
			
			LegalMoves bestMoveOpp = null;
			int[][] newBoardOpp= returnCopy(intState);
			for (LegalMoves legalMoveOpp : nextMovesOpp) {
				newBoardOpp= returnCopy(intState);
				newBoardOpp[legalMoveOpp.x][legalMoveOpp.y] = currentPlayer;
				newBoardOpp = legalMoveBoardValue(legalMoveOpp.x, legalMoveOpp.y, newBoardOpp, currentPlayer);
//				int val = maxFinder(newBoard,cutOff-1, d,legalMove.x,legalMove.y,bestVal,nextMoves);
				int val = minFinder(newBoardOpp, 1-1, legalMoveOpp.x, legalMoveOpp.y, getOppPlayer(currentPlayer));
				if (val > bestVal) {
					bestVal = val;
					bestMoveOpp = legalMoveOpp;
				}
				currentMove.value = valPrint(bestVal);
				log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
			}
			
//				newBoardOpp = legalMoveBoardValue(legalMoveOpp.x, legalMoveOpp.y, intState, oppInt);
//				int val = maxFinder(newBoardOpp, cutOff-1, legalMoveOpp.x, legalMoveOpp.y, getOppPlayer(oppInt));			
//				if (val < bestVal) {
//					bestVal = val;
//					bestMoveOpp = legalMoveOpp;
//				}
			
//			int[][] finalBoard = intState;
//			finalBoard[bestMove.x][bestMove.y]=playerInt;
//			finalBoard =legalMoveBoardValue( bestMove.x, bestMove.y, finalBoard, playerInt);
//			System.out.println("FINAL");
//			printOutput();
			
//			if(bestMoveOpp==null && flagplayer==1)
//			{
//				flagopponent=1;
//				printOutput();
//			}
//			else
//			{
//				flagplayer=0;
//				printOutput();
//			}
			//printOutput();//TODO check where to place
		} else {
			int[][] finalBoard = intState;
			finalBoard[bestMove.x][bestMove.y]=playerInt;
			finalBoard =legalMoveBoardValue( bestMove.x, bestMove.y, finalBoard, playerInt);
			System.out.println("FINAL");
			printOutput(finalBoard);
			printLog();
		}
		
		
	}
	
	
	
	public static void minimax(){
		//d=0;
		int currentPlayer = playerInt;
		CopyOnWriteArrayList<LegalMoves> nextMoves = nextMove(intState, currentPlayer, 0, 0);
		int bestVal = Integer.MAX_VALUE;//612
		LegalMoves currentMove;
		currentMove = new LegalMoves(-50, -50, 0, convert(-50, -50), 0, valPrint(bestVal));
		LegalMoves bestMove = null;
		//int[][] newBoard= returnCopy(intState);
		log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		for (LegalMoves legalMove : nextMoves) {
			int[][]newBoard= returnCopy(intState);
			newBoard[legalMove.x][legalMove.y] = currentPlayer;
			newBoard = legalMoveBoardValue(legalMove.x, legalMove.y, newBoard, currentPlayer);
			int val = maxFinder(newBoard,cutOff-1,legalMove.x,legalMove.y,getOppPlayer(currentPlayer));//uncomment
			//int val = minFinder(newBoard, cutOff-1, legalMove.x, legalMove.y, getOppPlayer(currentPlayer));
			if(flagopponent==1 && flagplayer==1)
			{	printLog();
				printOutput(intState);
				return;//Check what this does
			}
			if (val < bestVal) {//617
				bestVal = val;
				bestMove = legalMove;
			}
			currentMove.value = valPrint(bestVal);
			//check where this is getting printed
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
//if it has unentered x and y values then check what to do
			
			printState = newBoard;
			//printOutput(bestMove);
			int newX = bestMove.x;
			int newY = bestMove.y;
			printState[newX][newY]= currentPlayer;
			printState = legalMoveBoardValue(newX, newY, printState, currentPlayer);
			//objprint(bestMove);
			
			//return;
		}
		/*printOutput(printState);
		printLog();*/
		
		
		
		
		//////////////////////////////////////////
		if (bestMove == null) {
			// No possible moves
			currentPlayer = getOppPlayer(currentPlayer);
			CopyOnWriteArrayList<LegalMoves> nextMovesOpp = nextMove(intState, currentPlayer, 0, 0);
			if (nextMovesOpp.isEmpty()) {
				printOutput(intState); //Check if the state going into it is correct
				printLog();
				return;
			}
			LegalMoves bestMoveOpp = null;
			int[][] newBoardOpp= returnCopy(intState);
			for (LegalMoves legalMoveOpp : nextMovesOpp) {
				newBoardOpp= returnCopy(intState);
				newBoardOpp[legalMoveOpp.x][legalMoveOpp.y] = currentPlayer;
				newBoardOpp = legalMoveBoardValue(legalMoveOpp.x, legalMoveOpp.y, newBoardOpp, currentPlayer);
//				int val = maxFinder(newBoard,cutOff-1, d,legalMove.x,legalMove.y,bestVal,nextMoves);
				int val = minFinder(newBoardOpp, cutOff-1, legalMoveOpp.x, legalMoveOpp.y, getOppPlayer(currentPlayer));
				if (val > bestVal) {
					bestVal = val;
					bestMoveOpp = legalMoveOpp;
				}
				currentMove.value = valPrint(bestVal);
				log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
			}
			if(nextMovesOpp.isEmpty()){
				log.add(new LegalMoves(55,55, currentPlayer, convert(55, 55), cutOff-1,valPrint(bestVal)));
			}
			printOutput(intState);//check value passed in  1st
			printLog();
			/*int newX = bestMove.x;
			int newY = bestMove.y;
			printState[newX][newY]= currentPlayer;
			printState = legalMoveBoardValue(newX, newY, printState, currentPlayer);*/
		} else {
			/*int[][] finalBoard = intState;
			finalBoard[bestMove.x][bestMove.y]=currentPlayer;
			finalBoard =legalMoveBoardValue( bestMove.x, bestMove.y, finalBoard, getOppPlayer(currentPlayer));
			System.out.println("FINAL");
			printOutput(finalBoard);*/
			printOutput(printState);
			printLog();
		}
		
		
	}
	/**
	 * Computes the best move as the minimum player i.e the first player (X) .
	 * @param boardState - The state of the board before the minimum player has to make the move
	 * @param depth - The current depth of the search
	 * @return
	 */
	public static int minFinder(int[][] boardState, int depth, int x, int y, int currentPlayer) {
		LegalMoves currentMove = new LegalMoves(x, y, 0, convert(x, y), cutOff - depth, valPrint(Integer.MAX_VALUE));
		//int[][] newBoard = new int[8][8];
		//int bestVal = Integer.MAX_VALUE;
		int bestVal = Integer.MAX_VALUE;
		int value;
		CopyOnWriteArrayList<LegalMoves> nextMoves = nextMove(boardState, currentPlayer, 0, 0);
		if (depth <= 0) { //added condition
			int nodeValue = evalBoardValue(boardState);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, valPrint(nodeValue)));
			return nodeValue;
		}//int[][] newBoard = new int[8][8];
		else if (!(depth <=0) && nextMoves.isEmpty()) {
			int nodeValue = evalBoardValue(boardState);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, valPrint(nodeValue)));
			if(currentPlayer == 1){
				flagplayer =1;
				if(flagopponent ==1){
					 //END GAME //TODO
				}
			}else {
				flagopponent =1;
				if(flagplayer ==1){
					 //END GAME //TODO
				}
			}
			return nodeValue;
		} else {//not cut off and has legal moves
			if(currentPlayer ==1){
				flagopponent =0;
			}else flagplayer=0;
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		}
		for (LegalMoves legalMove : nextMoves) {
			int[][] newBoard = returnCopy(boardState);
			newBoard[legalMove.x][legalMove.y] = currentPlayer;
			newBoard = legalMoveBoardValue(legalMove.x, legalMove.y, newBoard, currentPlayer);
//			value = -1 * evalBoardValue(newBoard);
			value  =  maxFinder(newBoard, depth-1, legalMove.x, legalMove.y, getOppPlayer(currentPlayer));
			if (value < bestVal) {
				bestVal = value;
			}
			currentMove.value = valPrint(bestVal);
			//check if log below required
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		}
		return bestVal;
	}
	/**
	 * Computes the best move as the maximum player i.e the second player (O) .
	 * @param boardState - The state of the board before the maximum player has to make the move
	 * @param depth - The current depth of the search
	 * @return
	 */
	public static int maxFinder(int[][] boardState, int depth, int x, int y, int currentPlayer) {
		LegalMoves currentMove = new LegalMoves(x, y, 0, convert(x, y), cutOff - depth, valPrint(Integer.MIN_VALUE));
		CopyOnWriteArrayList<LegalMoves> nextMoves = nextMove(boardState, currentPlayer, 0, 0);
		int bestVal = Integer.MIN_VALUE;
		int value;
		if (depth <= 0) { //added condition
			int nodeValue = evalBoardValue(boardState);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, valPrint(nodeValue)));
			return nodeValue;
		}//int[][] newBoard = new int[8][8];
		else if (!(depth <=0) && nextMoves.isEmpty()) {
			int nodeValue = evalBoardValue(boardState);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, valPrint(nodeValue)));
			if(currentPlayer == 1){
				flagplayer =1;
				if(flagopponent ==1){
					 //END GAME //TODO
				}
			}else {
				flagopponent =1;
				if(flagplayer ==1){
					 //END GAME //TODO
				}
			}
			return nodeValue;
		} else {//not cut off and has legal moves
			if(currentPlayer ==1){
				flagopponent =0;
			}else flagplayer=0;
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		}
		for (LegalMoves legalMove : nextMoves) {
			int[][]newBoard = returnCopy(boardState); //commented
			newBoard[legalMove.x][legalMove.y] = currentPlayer;//commented
			newBoard = legalMoveBoardValue(legalMove.x, legalMove.y, newBoard, currentPlayer);
//			value = -1 * evalBoardValue(newBoard);
			value  = minFinder(newBoard, depth-1, legalMove.x, legalMove.y, getOppPlayer(currentPlayer));
			if (value > bestVal) {
				bestVal = value;
			}
			currentMove.value = valPrint(bestVal);
			//check if log below requried
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		}
		return bestVal;
	}
	
	public static int getOppPlayer(int currentPlayer) {
		if (currentPlayer == 1) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/*
	public static void alphaBeta(){
		int currentPlayer = playerInt;
		CopyOnWriteArrayList<LegalMoves> nextMoves = nextMove(intState, currentPlayer, 0, 0);
		//int bestVal = Integer.MIN_VALUE;
		int a = Integer.MIN_VALUE;
		LegalMoves currentMove;
		currentMove = new LegalMoves(-50, -50, 0, convert(-50, -50), 0, valPrint(a));
		LegalMoves bestMove = null;
		int[][] newBoard= returnCopy(intState);
		log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
		for (LegalMoves legalMove : nextMoves) {
			newBoard= returnCopy(intState);
			newBoard[legalMove.x][legalMove.y] = currentPlayer;
			newBoard = legalMoveBoardValue(legalMove.x, legalMove.y, newBoard, currentPlayer);
//			int val = maxFinder(newBoard,cutOff-1, d,legalMove.x,legalMove.y,bestVal,nextMoves);
			int val = minFinder(newBoard, cutOff-1, legalMove.x, legalMove.y, getOppPlayer(currentPlayer));
			if (val > a) {
				a = val;
				bestMove = legalMove;
			}
			currentMove.value = valPrint(a);
			log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
//			System.out.println("root,0,"+bestVal);
		}
		if (bestMove == null) {
			// No possible moves
			currentPlayer = getOppPlayer(currentPlayer);
			CopyOnWriteArrayList<LegalMoves> nextMovesOpp = nextMove(intState, currentPlayer, 0, 0);
			if (nextMovesOpp.isEmpty()) {
				printOutput();
				return;
			}
			
			LegalMoves bestMoveOpp = null;
			int[][] newBoardOpp= returnCopy(intState);
			for (LegalMoves legalMoveOpp : nextMovesOpp) {
				newBoardOpp= returnCopy(intState);
				newBoardOpp[legalMoveOpp.x][legalMoveOpp.y] = currentPlayer;
				newBoardOpp = legalMoveBoardValue(legalMoveOpp.x, legalMoveOpp.y, newBoardOpp, currentPlayer);
//				int val = maxFinder(newBoard,cutOff-1, d,legalMove.x,legalMove.y,bestVal,nextMoves);
				int val = minFinder(newBoardOpp, cutOff-1, legalMoveOpp.x, legalMoveOpp.y, getOppPlayer(currentPlayer));
				if (val > bestVal) {
					bestVal = val;
					bestMoveOpp = legalMoveOpp;
				}
				currentMove.value = valPrint(bestVal);
				log.add(new LegalMoves(currentMove.x, currentMove.y, 0, currentMove.name, currentMove.depth, currentMove.value));
			}
			
//				newBoardOpp = legalMoveBoardValue(legalMoveOpp.x, legalMoveOpp.y, intState, oppInt);
//				int val = maxFinder(newBoardOpp, cutOff-1, legalMoveOpp.x, legalMoveOpp.y, getOppPlayer(oppInt));			
//				if (val < bestVal) {
//					bestVal = val;
//					bestMoveOpp = legalMoveOpp;
//				}
			
//			int[][] finalBoard = intState;
//			finalBoard[bestMove.x][bestMove.y]=playerInt;
//			finalBoard =legalMoveBoardValue( bestMove.x, bestMove.y, finalBoard, playerInt);
//			System.out.println("FINAL");
//			printOutput();
			
//			if(bestMoveOpp==null && flagplayer==1)
//			{
//				flagopponent=1;
//				printOutput();
//			}
//			else
//			{
//				flagplayer=0;
//				printOutput();
//			}
			printOutput();
		} else {
			int[][] finalBoard = intState;
			finalBoard[bestMove.x][bestMove.y]=playerInt;
			finalBoard =legalMoveBoardValue( bestMove.x, bestMove.y, finalBoard, playerInt);
			System.out.println("FINAL");
			printOutput();
		}
		
		
	}*/
	
	
}



