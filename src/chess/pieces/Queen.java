package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
	
	public Queen(Board board, Color color){
		super(board, color);
	}

	@Override
	public String toString(){
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//verificar as posições above
		p.setValues(position.getRow() - 1, position.getColumn());
		// enquanto a posição existir e não tem peças marca verdadeiro
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições left
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições right
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições below
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições nw - noroeste - acima e a esquerda
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		// enquanto a posição existir e não tem peças marca verdadeiro
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() - 1);;
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições ne - nordeste - acima e direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições se - sudeste - baixo e direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//verificar as posições sw - sudoeste - baixo e esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}		
		
		return mat;
	}	
}
