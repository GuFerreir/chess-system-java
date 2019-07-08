package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn(){
		return turn;
	}
	
	public Color getCurrentPlayer(){
		return currentPlayer;
	}
	
	public boolean getCheck(){
		return check;
	}
	
	public boolean getCheckMate(){
		return checkMate;
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
	
	//para que quando a peça seja selecionada, apareça pro usuario uma
	//cor onde a peça pode se movimentar pelo tabuleiro
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
		//Primeiro converter as posições para posições da matriz
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		//Depois ver se a posição tem uma peça que existe
		validateSourcePosition(source);
		//Tambem é preciso validar a posição de destino
		validateTargetPosition(source, target);
		//Se existir eu movo a peça
		Piece capturedPiece = makeMove(source, target);
		
		//testar o movimento para ver se você se colocou em check
		if (testCheck(currentPlayer)){
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		// testa o check para o oponente do player atual 
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		//testar o check mate
		if (testCheckMate(currentPlayer)){
			checkMate = true;
		}		
		
		//trocar o turno
		nextTurn();
		return (ChessPiece)capturedPiece;	
	}
	
	private Piece makeMove(Position source, Position target){
		//Logica para realizar o movimento
		// primeiro tiro ela da posição de origem
		ChessPiece p = (ChessPiece)board.removePiece(source);
		//contar os movimentos
		p.increaseMoveCount();
		//tiro uma possivel peça da posição de destino
		Piece capturedPiece = board.removePiece(target);
		//coloco a peça de origem no seu destino
		board.placePiece(p, target);
		
		// vejo se capturou alguma
		if (capturedPiece != null){
			//se capturou eu atualizo minhas listas de controle
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}		
		
		// #Special Move - Rook Castling - KingSide
		//se a peça movida é um rei e moveu duas vezes para direita
		if(p instanceof King && target.getColumn() == source.getColumn() + 2){
			//pego a torre
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			//defino onde é o destino dela
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			//retiro a peço da origem (deve ter uma torre la) e retorno a peça que estava la na variavel rook
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			//coloco a torre no destino selecionado antes
			board.placePiece(rook, targetT);
			//incremento o controle de movimentos da torre, pois movi ela
			rook.increaseMoveCount();
		}		
		
		// #Special Move - Rook Castling - QueenSide
		//se a peça movida é um rei e moveu duas vezes para esquerda
		if(p instanceof King && target.getColumn() == source.getColumn() - 2){
			//pego a torre
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			//defino onde é o destino dela
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			//retiro a peço da origem (deve ter uma torre la) e retorno a peça que estava la na variavel rook
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			//coloco a torre no destino selecionado antes
			board.placePiece(rook, targetT);
			//incremento o controle de movimentos da torre, pois movi ela
			rook.increaseMoveCount();
		}
		
		return capturedPiece;		
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece){
		//desfazer o método movePiece
		//retira a peça que acabei de colocar
		ChessPiece p = (ChessPiece)board.removePiece(target);
		//decrementar o contador de movimentos
		p.decreaseMoveCount();
		//devolver para a posição de origem
		board.placePiece(p, source);
		
		//se alguma peça foi capturada, devolveremos ela ao destino
		//isso implica em atualizar as listas de controle
		if (capturedPiece != null){
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// #Special Move - Rook Castling - KingSide
		//se a peça movida é um rei e moveu duas vezes para direita
		if(p instanceof King && target.getColumn() == source.getColumn() + 2){
			//pego a torre
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			//defino onde é o destino dela
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			//retiro a peço de destino (deve ter uma torre la) e retorno a peça que estava la na variavel rook
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			//coloco a torre na origem, selecionada antes
			board.placePiece(rook, sourceT);
			//decremento o controle de movimentos da torre, pois movi ela
			rook.decreaseMoveCount();
		}		
		
		// #Special Move - Rook Castling - QueenSide
		//se a peça movida é um rei e moveu duas vezes para esquerda
		if(p instanceof King && target.getColumn() == source.getColumn() - 2){
			//pego a torre
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			//defino onde é o destino dela
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			//retiro a peço do destino e retorno a peça que estava la na variavel rook
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			//coloco a torre na origem, selecionada antes
			board.placePiece(rook, sourceT);
			//decremento o controle de movimentos da torre, pois movi ela
			rook.decreaseMoveCount();
		}
		
		
		
	}
	
	private void validateSourcePosition(Position position){
		//verifica se tem peça na posição dada
		if (!board.thereIsAPiece(position)){
			throw new ChessException("There is no piece on source position");
		}
		//verifica se você esta tentando mover uma peça adversária
		if (currentPlayer != (((ChessPiece)board.piece(position)).getColor())){
			throw new ChessException("The chosen piece is not yours");
		}
		//verifica se tem movimentos possiveis para a peça dada
		if (!board.piece(position).isThereAnyPossibleMove()){
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target){
		//se pra peça de origem a posição de destino não é possivel lanço uma excessão
		if(!board.piece(source).possibleMove(target)){
			throw new ChessException("The chosen piece can't move to target position");			
		}
	}
	
	private void nextTurn(){
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color){
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//procurar o rei pelo tabuleiro dada sua cor
	private ChessPiece king(Color color){
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list){
			if (p instanceof King){
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}	
	
	//método para ver se o seu rei esta em check
	private boolean testCheck(Color color){
		//vemos onde esta o nosso rei
		Position kingPosition = king(color).getChessPosition().toPosition();
		//fazemos uma lista com as peças do oponente
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		//passaremos por todas as peças do oponente
		for (Piece p : opponentPieces){
			//faremos uma matriz com todos os movimentos possiveis da peça
			boolean[][] mat = p.possibleMoves();
			//se o movimento for possivel estrará true para a matriz na posição do rei então retorna true
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
				return true;
			}			
		}
		return false;
	}
	
	//testar se nenhuma peça pode tirar o rei do check
	private boolean testCheckMate(Color color){
		//tirar a possibilidade da peça não estar em check
		if(!testCheck(color)) return false;
		
		//testar todas as peças da cor passada e ver se em algum movimento possivel ela tira do check
		//1 faz uma lista com todas as peças
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		// percorrer peças
		for(Piece p : list){
			//matriz de movimentos possiveis
			boolean[][] mat = p.possibleMoves();
			//percorrer essa matriz gerada
			for(int i=0; i<board.getRows(); i++){
				for (int j=0; j<board.getColumns(); j++){
					if(mat[i][j]){
						//testar se essa posição (movimento possivel) tira do check
						// mover a peça "p" para a possição mat[i][j] (movimento possivel) e testar se esta em check ainda
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck){
							return false;
						}
					}
				}
			}
		}
		//se esgotar o for significa que não achou movimento possivel que tira do check mate
		return true;
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece){
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	//responsável por iniciar a partida colocando as peças de xadrez no tabuleiro
	private void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); 
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}

}
