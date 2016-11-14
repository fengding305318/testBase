package com.mcgrady.android.jq.game.ai;

import static com.mcgrady.android.jq.game.Board.CAMP;
import static com.mcgrady.android.jq.game.Board.HEADQUARTER;
import static com.mcgrady.android.jq.game.Board.BOARD_HEIGHT;
import static com.mcgrady.android.jq.game.Board.BOARD_WIDTH;

import static com.mcgrady.android.jq.game.Pieces.BOMB_N;
import static com.mcgrady.android.jq.game.Pieces.BOMB_S;
import static com.mcgrady.android.jq.game.Pieces.FLAG_N;
import static com.mcgrady.android.jq.game.Pieces.FLAG_S;
import static com.mcgrady.android.jq.game.Pieces.GONGBING_N;
import static com.mcgrady.android.jq.game.Pieces.GONGBING_S;
import static com.mcgrady.android.jq.game.Pieces.JUNZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.JUNZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.LIANZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.LIANZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.LVZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.LVZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.MINE_N;
import static com.mcgrady.android.jq.game.Pieces.MINE_S;
import static com.mcgrady.android.jq.game.Pieces.PAIZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.PAIZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.SHIZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.SHIZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.SILING_N;
import static com.mcgrady.android.jq.game.Pieces.SILING_S;
import static com.mcgrady.android.jq.game.Pieces.TUANZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.TUANZHANG_S;
import static com.mcgrady.android.jq.game.Pieces.YINGZHANG_N;
import static com.mcgrady.android.jq.game.Pieces.YINGZHANG_S;

import java.util.Vector;

import com.mcgrady.android.jq.game.Board;
import com.mcgrady.android.jq.game.Coordinate;
import com.mcgrady.android.jq.game.Pieces;

/**
 * Alpha-beta search for the game
 */
public class ABSearch {
	protected static final int FLAG_VALUE = 10000000;
	protected Board board;
	protected Board boardClone;
	protected Movement bestMove = null;

	public ABSearch() {
	}

	public ABSearch(Board b) {
		this.board = b;
		this.boardClone = (Board) b.clone();
	}
	
	/**
	 * All possible moves 
	 * @parameter true = NORTH / AI
	 */
	public Vector<Movement> possibleMoves(boolean ai) {
		Vector<Movement> moves = new Vector<Movement>();
		for (int j = 0 ; j < BOARD_HEIGHT ; j++) {
			for (int i = 0; i < BOARD_WIDTH; i++) {
				if (ai && (Pieces.getLocated(boardClone.getBoardArea()[j][i]) == Pieces.AI_TAG) || 
					!ai	&& (Pieces.getLocated(boardClone.getBoardArea()[j][i]) == Pieces.MAN_TAG)) {
					for (int tj = BOARD_HEIGHT - 1; tj >=0; tj--) {
						for (int ti = BOARD_WIDTH -1; ti >=0; ti --){ 
							Vector<Coordinate> path = boardClone.pathFinding(i, j, ti, tj);
							if (path != null && path.size() > 0) {
								moves.add(new Movement(i, j, ti, tj));
							}
						}
					}
				}
			}
		}
		// Collections.sort(moves);
		// for (int i = 0; i < moves.size(); i++) {
		// logger.debug(moves.get(i));
		// }
		return moves;
	}

	/**
	 * Evaluation, @parameter true = NORTH / AI
	 */
	public int evaluation(boolean ai) {
		byte[][] stations = boardClone.getStations();
		byte[][] ba = boardClone.getBoardArea();
		int value = 0;
		for (int j = 0; j < BOARD_HEIGHT; j++) {
			for (int i = 0; i < BOARD_WIDTH; i++) {
				// 瀛愬姏鍔犲拰鍒嗘暟
				if (ba[j][i] == SILING_N) {
					value += 350;
				} else if (ba[j][i] == JUNZHANG_N) {
					value += 260;
				} else if (ba[j][i] == SHIZHANG_N) {
					value += 170;
				} else if (ba[j][i] == LVZHANG_N) {
					value += 120;
				} else if (ba[j][i] == TUANZHANG_N) {
					value += 90;
				} else if (ba[j][i] == YINGZHANG_N) {
					value += 70;
				} else if (ba[j][i] == LIANZHANG_N) {
					value += 40;
				} else if (ba[j][i] == PAIZHANG_N) {
					value += 20;
				} else if (ba[j][i] == GONGBING_N) {
					value += 60;
				} else if (ba[j][i] == BOMB_N) {
					value += 130;
				} else if (ba[j][i] == MINE_N) {
					value += 39;
				} else if (ba[j][i] == FLAG_N) {
					value += FLAG_VALUE;
				} else if (ba[j][i] == SILING_S) {
					value -= 350;
				} else if (ba[j][i] == JUNZHANG_S) {
					value -= 260;
				} else if (ba[j][i] == SHIZHANG_S) {
					value -= 170;
				} else if (ba[j][i] == LVZHANG_S) {
					value -= 120;
				} else if (ba[j][i] == TUANZHANG_S) {
					value -= 90;
				} else if (ba[j][i] == YINGZHANG_S) {
					value -= 70;
				} else if (ba[j][i] == LIANZHANG_S) {
					value -= 40;
				} else if (ba[j][i] == PAIZHANG_S) {
					value -= 20;
				} else if (ba[j][i] == GONGBING_S) {
					value -= 60;
				} else if (ba[j][i] == BOMB_S) {
					value -= 130;
				} else if (ba[j][i] == MINE_S) {
					value -= 39;
				} else if (ba[j][i] == FLAG_S) {
					value -= FLAG_VALUE;
				}

				// 鏃楀乏鍙崇殑浣嶇疆鏄痥iller鎷涙硶锛岃繖涓昏寮ヨˉ鎼滅储娣卞害鐨勪笉瓒�
				if (ba[j][i] == FLAG_S
						&& (Pieces.getLocated(ba[j][i + 1])== Pieces.AI_TAG ||Pieces.getLocated(ba[j][i - 1])== Pieces.AI_TAG)) {
					value += FLAG_VALUE / 100;
				} else if (ba[j][i] == FLAG_N
						&& (Pieces.getLocated(ba[j][i + 1])== Pieces.MAN_TAG ||Pieces.getLocated(ba[j][i - 1])== Pieces.MAN_TAG)) {
					value -= FLAG_VALUE / 100;
				}

				// 瑕佺牬涓夎闆�
				if (ba[j][i] == FLAG_N
						&& ba[j][i + 1] == MINE_N
						&& ba[j][i - 1] == MINE_N
						&& ba[j + 1][i] == MINE_N) {
					value += 220;
				} else if (ba[j][i] == FLAG_S
						&& ba[j][i + 1] == MINE_S
						&& ba[j][i - 1] == MINE_S
						&& ba[j - 1][i] == MINE_S) {
					value -= 220;
				}

				// 涓嶈杩涢潪鏃楃殑澶ф湰钀�
				if (stations[j][i] == HEADQUARTER && Pieces.getLocated(ba[j][i])== Pieces.AI_TAG ) {
					value -= 100;
				} else if (stations[j][i] == HEADQUARTER && Pieces.getLocated(ba[j][i])== Pieces.MAN_TAG) {
					value += 100;
				}

				// 鍗犲鏂规棗涓婄殑琛岃惀锛岃繖涓綅缃緢閲嶈
				if (ba[j][i] == FLAG_S && Pieces.getLocated(ba[j - 2][i])== Pieces.AI_TAG ){
					value += 25;
				} else if (ba[j][i] == FLAG_N && Pieces.getLocated(ba[j + 2][i])== Pieces.MAN_TAG ){
					value -= 25;
				}

				// 鏃椾笂闈㈢殑浣嶇疆涔熷緢閲嶈
				if (ba[j][i] == FLAG_S && Pieces.getLocated(ba[j - 1][i])== Pieces.AI_TAG) {
					value += 20;
				} else if (ba[j][i] == FLAG_N && Pieces.getLocated(ba[j + 1][i])== Pieces.MAN_TAG) {
					value -= 20;
				}

				// 鏀诲崰瀵规柟搴曠嚎鍔犲垎锛堥紦鍔辫繘鏀诲拰鍔犲己闃插畧锛�
				if (j == 10 && Pieces.getLocated(ba[j][i])== Pieces.AI_TAG) { 
					value += 8;
				} else if (j == 1 &&  Pieces.getLocated(ba[j][i])== Pieces.MAN_TAG) {
					value -= 8;
				}

				// 鍏朵粬琛岃惀鍗犲垎
				if (stations[j][i] == CAMP && Pieces.getLocated(ba[j][i])== Pieces.AI_TAG) { 
					value += 5;
				} else if (stations[j][i] == CAMP && Pieces.getLocated(ba[j][i])== Pieces.MAN_TAG) {
					value -= 5;
				}
			}
		}

		if (!ai) { // 璐熷�鏈�ぇ鎼滅储銆傚褰撳墠涓�柟鑰岃█锛屽鏋滃崰浼樺垯杩斿洖姝ｆ暟锛屽惁鍒欒繑鍥炶礋鏁�
			value = -value;
		}
		return value;
	}

	/**
	 * True if game is over.
	 */
	public boolean isGameOver(boolean player) {
		return evaluation(player) > FLAG_VALUE / 2;
	}

	/**
	 * Alpha-beta search
	 */
	public int alphaBeta(int depth, boolean player, int alpha, int beta) {
		if (depth == 0 || isGameOver(player)) {
			return evaluation(player);
		}
		// Movement best = null;
		Vector<Movement> moves = possibleMoves(player);
		// For each possible move
		for (int i = 0; i < moves.size(); i++) {
			Movement move = moves.get(i);
			byte[][] boardCopy = boardClone.newCopyOfBoard();
			// Make move
			boardClone.makeaMove(move);
			int value = - alphaBeta(depth - 1, !player, -beta, -alpha);
			// Undo the move
			boardClone.recoverBoard(boardCopy);
			if (value >= alpha) {
				alpha = value;
			}
			if (alpha >= beta) {
				break;
			}
		}
		return alpha;
	}
}