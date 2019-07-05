package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	
	/*
	 * A cor da peça não deve ser alterada, apenas
	 * acessada.
	public void setColor(Color color) {
		this.color = color;
	}
	*/
	
	
	
}
