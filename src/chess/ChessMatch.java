package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}

		}
		return mat;
	}
	
	//respons�vel por iniciar a partida colocando as pe�as de xadrez no tabuleiro
	private void initialSetup(){
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
	}

}