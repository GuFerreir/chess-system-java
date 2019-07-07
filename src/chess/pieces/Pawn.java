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
		//criando posição aux
		Position p = new Position(0,0);
		
		//teste das peças brancas
		if(getColor() == Color.WHITE){
			//testando uma posição acima do peao branco
			p.setValues(position.getRow() - 1, position.getColumn());
			//se a posição existe e nao tem nada la
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testando duas posições acima (só é possivel no primeiro movimento
			p.setValues(position.getRow() - 2, position.getColumn());
			//tambem é preciso ver se a posição entre a origem e a segunda casa esta livre (p2)
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			//se a posição existe, nao tem nada la e se é o movimento 1 (se esta zerado o move count)
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do peão (capturar peças adversarias) - diagonal esquerda
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			//se a posição existe e tem peça adversária
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do peão (capturar peças adversarias) - diagonal direita
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			//se a posição existe e tem peça adversária
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
		}
		//teste das peças pretas
		else{
			//testando uma posição abaixo do peão preto
			p.setValues(position.getRow() + 1, position.getColumn());
			//se a posição existe e nao tem nada la
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testando duas posições acima (só é possivel no primeiro movimento
			p.setValues(position.getRow() + 2, position.getColumn());
			//tambem é preciso ver se a posição entre a origem e a segunda casa esta livre (p2)
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			//se a posição existe, nao tem nada la e se é o movimento 1 (se esta zerado o move count)
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do peão (capturar peças adversarias) - diagonal esquerda
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			//se a posição existe e tem peça adversária
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posição
				mat[p.getRow()][p.getColumn()] = true;				
			}
			
			//testar o movimento diagonal do peão (capturar peças adversarias) - diagonal direita
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			//se a posição existe e tem peça adversária
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				//torna true a posição
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
