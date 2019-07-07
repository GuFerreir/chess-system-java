package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	/*
	 * A cor da pe�a n�o deve ser alterada, apenas
	 * acessada.
	public void setColor(Color color) {
		this.color = color;
	}
	*/
	
	public ChessPosition getChessPosition(){
		return ChessPosition.fromPosition(position);
	}

	//metodo para ver se tem pe�a adversaria no caminho pois a movimenta��o ainda � v�lida nesse caso (captura)
	//fica na classe gen�rica pois todas as pe�as o usar�o
	protected boolean isThereOpponentPiece(Position position){
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	
}
