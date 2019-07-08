package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch){
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString(){
		return "K";
	}
	
	//saber se o rei pode mover para uma posição
	private boolean canMove(Position position){
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	//testar se a torre esta apta para o movimento rook castling
	//vendo se na posição passada tem uma torre e se ela esta apta
	private boolean testRookCastling(Position position){
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}	

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}		
		
		//left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//nw - esquerda e cima
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//ne - cima e direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sw - baixo e esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//se - baixo e direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)){
			mat[p.getRow()][p.getColumn()] = true;
		}		
		
		// #Special move - Rook Castling
		//vejo se não foi movido ainda e se nao esta em check
		if (getMoveCount() == 0 && !chessMatch.getCheck()){
			// #Special move - Rook Castling - KingSide
			//pego a posição da onde deve estar a torre do rei
			Position Rook1 = new Position(position.getRow(), position.getColumn() + 3);
			if(testRookCastling(Rook1)){
				//vejo se as duas casas entre o rei e a torre estão livres
				Position p1 = new Position(position.getRow(),position.getColumn() + 1);
				Position p2 = new Position(position.getRow(),position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			// #Special move - Rook Castling - QueenSide
			//pego a posição da onde deve estar a torre da rainha
			Position Rook2 = new Position(position.getRow(), position.getColumn() - 4);
			if(testRookCastling(Rook2)){
				//vejo se as duas casas entre o rei e a torre estão livres
				Position p1 = new Position(position.getRow(),position.getColumn() - 1);
				Position p2 = new Position(position.getRow(),position.getColumn() - 2);
				Position p3 = new Position(position.getRow(),position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		
		return mat;
	}

}
