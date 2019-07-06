package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
	
	public Rook(Board board, Color color){
		super(board, color);
	}

	@Override
	public String toString(){
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//verificar as posi��es above
		p.setValues(position.getRow() - 1, position.getColumn());
		// enquanto a posi��o existir e n�o tem pe�as marca verdadeiro
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posi��es left
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posi��es right
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posi��es below
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		
		return mat;
	}
	
}
