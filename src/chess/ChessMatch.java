package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
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
		
		//trocar o turno
		nextTurn();
		return (ChessPiece)capturedPiece;	
	}
	
	private Piece makeMove(Position source, Position target){
		//Logica para realizar o movimento
		// primeiro tiro ela da posição de origem
		Piece p = board.removePiece(source);
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
		return capturedPiece;		
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece){
		//desfazer o método movePiece
		//retira a peça que acabei de colocar
		Piece p = board.removePiece(target);
		//devolver para a posição de origem
		board.placePiece(p, source);
		
		//se alguma peça foi capturada, devolveremos ela ao destino
		//isso implica em atualizar as listas de controle
		if (capturedPiece != null){
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece){
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	//responsável por iniciar a partida colocando as peças de xadrez no tabuleiro
	private void initialSetup(){
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}

}
