package boardgame;

public abstract class Piece {

	//Posi��o na matriz, n�o vis�vel para a camada do xadrez
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		//posi��o inicial � nula pois a pe�a criada ainda n�o � colocada no tabuleiro
		this.position = null;
		this.board = board;
	}

	//ser� protected pois queremos que n�o seja
	//vis�vel pela camada de xadrez
	protected Board getBoard() {
		return board;
	}

	/*
	 * N�o poderemos alterar o tabuleiro ent�o o setBoard
	 * n�o deve existir 
	public void setBoard(Board board) {
		this.board = board;
	}
	*/
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position){
		//hook method.. s� faz sentido quando existe uma implementa��o
		//concreta do metodo abstrato
		return possibleMoves()[position.getRow()][position.getColumn()];		
	}
	
	//ver se a pe�a n�o est� travada
	public boolean isThereAnyPossibleMove(){
		//para ver precisaremos passar a matriz gerada pelo metodo abstrato nas classes
		//e vamos iterar pela matriz procurando valores true (quando a pe�a pode se mover pra uma casa)
		boolean[][] mat = possibleMoves();
		for(int i=0; i<mat.length; i++){
			for(int j=0; j<mat.length; j++){
				if(mat[i][j]){
					return true;
				}				
			}
		}
		return false;
		//Ps.: outro hook method
	}
}
