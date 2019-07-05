package boardgame;

public class Piece {

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
	 * 
	public void setBoard(Board board) {
		this.board = board;
	}
	*/
	
}
