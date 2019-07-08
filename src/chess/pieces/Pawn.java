package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			
			//testar #SpecialMove - en Passant white
			// s� posso fazer na linha 5 do tabuleiro (posi��o 3 da matriz)
			if (position.getRow() == 3){
				//pego a posi��o � esquerda, onde dever� ter o pe�o advers�rio suscetivel ao movimento
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				//pego a posi��o � direita, onde dever� ter o pe�o advers�rio suscetivel ao movimento
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
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
						
			//testar #SpecialMove - en Passant pretas
			// s� posso fazer na linha 4 do tabuleiro (posi��o 4 da matriz)
			if (position.getRow() == 4){
				//pego a posi��o � esquerda, onde dever� ter o pe�o advers�rio suscetivel ao movimento
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				//pego a posi��o � direita, onde dever� ter o pe�o advers�rio suscetivel ao movimento
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
			
			
		}		
		return mat;
	}
	
	
	@Override
	public String toString(){
		return "P";
	}
	

}
