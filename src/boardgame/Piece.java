package boardgame;

public class Piece {

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
	 * 
	public void setBoard(Board board) {
		this.board = board;
	}
	*/
	
}
