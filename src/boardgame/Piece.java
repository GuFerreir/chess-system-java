package boardgame;

public abstract class Piece {

	//Posição na matriz, não visível para a camada do xadrez
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		//posição inicial é nula pois a peça criada ainda não é colocada no tabuleiro
		this.position = null;
		this.board = board;
	}

	//será protected pois queremos que não seja
	//visível pela camada de xadrez
	protected Board getBoard() {
		return board;
	}

	/*
	 * Não poderemos alterar o tabuleiro então o setBoard
	 * não deve existir 
	public void setBoard(Board board) {
		this.board = board;
	}
	*/
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position){
		//hook method.. só faz sentido quando existe uma implementação
		//concreta do metodo abstrato
		return possibleMoves()[position.getRow()][position.getColumn()];		
	}
	
	//ver se a peça não está travada
	public boolean isThereAnyPossibleMove(){
		//para ver precisaremos passar a matriz gerada pelo metodo abstrato nas classes
		//e vamos iterar pela matriz procurando valores true (quando a peça pode se mover pra uma casa)
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
