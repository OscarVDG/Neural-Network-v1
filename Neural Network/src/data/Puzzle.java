package data;

import java.util.LinkedList;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class Puzzle {

	String id;
	Board board;
	LinkedList<String> sol = new LinkedList<String>();

	LinkedList<String> archiveSol = new LinkedList<String>();
	String fen;
	
	
	Puzzle(String id_, String fen_, String s) {
		id = id_;
		fen = fen_;
		
		board = new Board();
		board.loadFromFen(fen);
		
		for (String m : s.split(" ")) {
			sol.add(m);
			archiveSol.add(m);
		}

		board.doMove(new Move(sol.removeFirst(), board.getSideToMove()));
	}

	// Always a list that starts at white's left corner, only whole numbers
	public double[] boardToIntArray() {

		Piece[] boardStr = board.boardToArray();
		double[] out = new double[65];

		for (int i = 0; i < 64; i++) {

			int s;

			if (boardStr[i].getPieceSide() == Side.WHITE) {
				s = 1;
			} else {
				s = -1;
			}

			out[64] = s;

			if (boardStr[i].getPieceType() != null) {
				switch (boardStr[i].getPieceType()) {
				case PAWN:
					out[i] = 1;
					break;
				case ROOK:
					out[i] = 2;
					break;
				case KNIGHT:
					out[i] = 3;
					break;
				case BISHOP:
					out[i] = 4;
					break;
				case QUEEN:
					out[i] = 5;
					break;
				case KING:
					out[i] = 6;
					break;
				case NONE:
					System.out.println("WHat!!");
					break;
				}
			} else {
				out[i] = 0;
			}

			out[i] *= s;

		}

		return out;

	}

	// in double form -- expected answer, only whole numbers though
	public double[] getNextMove() {

		if (sol.size() < 1) {
			System.out.println("No more moves!");
			return null;
		}

		double[] out = new double[16];
		for (int i = 0; i < 16; i++) {
			out[i] = 0;
		}

		String next = sol.getFirst();

		out[next.charAt(0) - 97] = Integer.parseInt(next.substring(1, 2));
		out[next.charAt(2) - 97 + 8] = Integer.parseInt(next.substring(3, 4));

		return out;
	}

	// Returns true if there are more steps left, false if this was the last one, or
	// if none were left.
	public boolean proceed() {

		if (sol.size() > 1) {
			board.doMove(new Move(sol.removeFirst(), board.getSideToMove()));
			board.doMove(new Move(sol.removeFirst(), board.getSideToMove()));
			return true;
		} else if (sol.size() == 1) {
			board.doMove(new Move(sol.removeFirst(), board.getSideToMove()));
			return false;
		} else {
			return false;
		}
	}

	public void reset() {
		board.loadFromFen(fen);
		sol = new LinkedList<String>(archiveSol);
		board.doMove(new Move(sol.removeFirst(), board.getSideToMove()));
	}
	
}
