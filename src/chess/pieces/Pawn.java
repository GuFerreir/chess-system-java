package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		//criando matriz aux
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		//criando posi��o aux
		Position p = new Position(0,0);
		
		//teste das pe�as brancas
		if(getColor() == Color.WHITE){
			//testando uma posi��o acima do peao branco
			p.setValues(position.getRow() - 1, position.getColumn());
			//se a posi��o existe e nao tem nada la
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testando duas posi��es acima (s� � possivel no primeiro movimento
			p.setValues(position.getRow() - 2, position.getColumn());
			//tambem � preciso ver se a posi��o entre a origem e a segunda casa esta livre (p2)
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			//se a posi��o existe, nao tem nada la e se � o movimento 1 (se esta zerado o move count)
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do pe�o (capturar pe�as adversarias) - diagonal esquerda
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			//se a posi��o existe e tem pe�a advers�ria
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do pe�o (capturar pe�as adversarias) - diagonal direita
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			//se a posi��o existe e tem pe�a advers�ria
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		}
		//teste das pe�as pretas
		else{
			//testando uma posi��o abaixo do pe�o preto
			p.setValues(position.getRow() + 1, position.getColumn());
			//se a posi��o existe e nao tem nada la
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testando duas posi��es acima (s� � possivel no primeiro movimento
			p.setValues(position.getRow() + 2, position.getColumn());
			//tambem � preciso ver se a posi��o entre a origem e a segunda casa esta livre (p2)
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			//se a posi��o existe, nao tem nada la e se � o movimento 1 (se esta zerado o move count)
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do pe�o (capturar pe�as adversarias) - diagonal esquerda
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			//se a posi��o existe e tem pe�a advers�ria
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do pe�o (capturar pe�as adversarias) - diagonal direita
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			//se a posi��o existe e tem pe�a advers�ria
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posi��o
				mat[p.getRow()][p.getColumn()] = true;				
			}			
		}		
		return mat;
	}
	
	
	@Override
	public String toString(){
		return "P";
	}
	

}
