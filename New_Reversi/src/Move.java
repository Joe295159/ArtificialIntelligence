import java.util.ArrayList;
import java.util.List;

public class Move {
	public int x;//row
	public int y;//column
	@Override
	public String toString() {
		return ((char)(y+97))+""+(x+1);
	}
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}


	static List<Move> findMoves(int [][] board, int currentPlayer){
		List<Move> finalList = new ArrayList<Move>();
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(board[i][j]==0){
					if(isValid(board, currentPlayer, i, j)){
						finalList.add(new Move(i, j));
					}
				}
		
		
		return finalList;
	}


	private static boolean isValid(int[][] board, int currentPlayer, int i,
			int j) {
		int p, q;
		
		p=i; q=j;
		
		if(p+1<7 && board[p+1][q]==-currentPlayer)
			while(true){
				p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(p>0 &&board[p-1][q]==-currentPlayer)
			while(true){
				p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(q+1<7 &&board[p][q+1]==-currentPlayer)
			while(true){
				q++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(q>0 &&board[p][q-1]==-currentPlayer)
			while(true){
				q--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(p>0 && q>0 && board[p-1][q-1]==-currentPlayer)
			while(true){
				q--;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(p+1<7 && q>0 && board[p+1][q-1]==-currentPlayer)
			while(true){
				q--;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(p>0 && q+1<7 && board[p-1][q+1]==-currentPlayer)
			while(true){
				q++;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		p=i; q=j;
		if(p+1<7 && q+1<7 && board[p+1][q+1]==-currentPlayer)
			while(true){
				q++;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) return true;
			}
		return false;
	}


	public static void makeMove(int[][] board, Move move, int currentPlayer) {
		int p, q;
		boolean flag;
		
		board[move.x][move.y] = currentPlayer;
		
		p=move.x; q=move.y;
		flag = false;
		if(p+1<7 && board[p+1][q]==-currentPlayer)
			while(true){
				p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}

		p=move.x; q=move.y;
		flag = false;
		if(p>0 &&board[p-1][q]==-currentPlayer)
			while(true){
				p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
		
		p=move.x; q=move.y;
		flag = false;
		if(q+1<7 &&board[p][q+1]==-currentPlayer)
			while(true){
				q++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
		
		p=move.x; q=move.y;
		flag = false;
		if(q>0 &&board[p][q-1]==-currentPlayer)
			while(true){
				q--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}

		p=move.x; q=move.y;
		flag = false;
		if(p>0 && q>0 && board[p-1][q-1]==-currentPlayer)
			while(true){
				q--;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q--;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
		
		p=move.x; q=move.y;
		flag = false;
		if(p+1<7 && q>0 && board[p+1][q-1]==-currentPlayer)
			while(true){
				q--;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q--;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
		
		p=move.x; q=move.y;
		flag = false;
		if(p>0 && q+1<7 && board[p-1][q+1]==-currentPlayer)
			while(true){
				q++;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q++;p--;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
		
		p=move.x; q=move.y;
		flag = false;
		if(p+1<7 && q+1<7 && board[p+1][q+1]==-currentPlayer)
			while(true){
				q++;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {flag=true; break;}
			}
		p=move.x; q=move.y;
		if(flag){
			while(true){
				q++;p++;
				if(!(p<=7 && q<=7 && p>=0 && q>=0)) break;
				if(board[p][q]==0) break;
				if(board[p][q]==currentPlayer) {break;}
				board[p][q]=currentPlayer;
			}
		}
	}
}