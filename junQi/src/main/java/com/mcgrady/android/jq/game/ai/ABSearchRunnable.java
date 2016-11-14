package com.mcgrady.android.jq.game.ai;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import android.util.Log;

import com.mcgrady.android.jq.game.Board;
import com.mcgrady.android.jq.game.Pieces;

public class ABSearchRunnable extends ABSearch implements Runnable{
	// 璁＄畻鏈鸿绠楁椂闂达紝鐢变簬鏄�褰掕皟鐢紝鍥犳鏈夋椂浼氳秴鍑鸿繖涓椂闂�
	private static final int COMPUTER_THINKING_TIME = 3000;
	// 璁＄畻鏈簊et from & to 鐨勬椂闂撮棿闅�
	private static final int COMPUTER_SELECTION_TIME = 240;
	private Random rdm = new Random();
	
	public ABSearchRunnable(){
		super();
	}
	public ABSearchRunnable(Board b){
		super(b);
	}

	
	public void run() {
		int rint = rdm.nextInt(100);
		int depth = 0;
		if (rint < 35) { 
			depth = 0;	// 35%
		} else if (rint < 80) {
			depth = 1;	// 45%
		} else {
			depth = 2;	// 20%
		}
		Log.d(this.getClass().getName(), "Search depth:" + String.valueOf(depth));
		
		long startThinking = System.currentTimeMillis();
		byte[][] boardCopy = boardClone.newCopyOfBoard();
		Vector<Movement> moves = possibleMoves(true);
		int[] values = new int[moves.size()];

		// 鍏堝仛涓�笅灞�潰璇勪及
		for (int i = 0; i < moves.size(); i++) {
			Movement mm = moves.get(i);
			values[i] = Integer.MAX_VALUE;
			boardClone.makeaMove(mm);
			mm.setValue(evaluation(true));
			boardClone.recoverBoard(boardCopy);
		}
		
		// 缁橮ossible Move鎸塿alue浠庡ぇ鍒板皬鎺掑簭
		Collections.sort(moves);
		for (int i = 0; i < moves.size(); i++) {
			boardClone.makeaMove(moves.get(i));
			values[i] = alphaBeta(depth, false, Integer.MIN_VALUE,
					Integer.MAX_VALUE);
			boardClone.recoverBoard(boardCopy);

			// Check the search time. The computer can think 2 seconds at most
			long endThinking = System.currentTimeMillis();
			if (endThinking - startThinking > COMPUTER_THINKING_TIME) {
				break;
			}
		}

		int chosen = 0;	// 鐢佃剳閫夋嫨鐨勬楠�
		int value = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			if (values[i] < value) {
				chosen = i; // 鐢佃剳閫夋嫨瀵笻uman鏉ヨ浼板�鏈�皬鐨刴ove
				value = values[i];
			}
		}

		// 鐢佃剳閲囧彇琛屽姩
		board.clearFromTo ();
		board.setFromTo(Pieces.AI_TAG, moves.get(chosen).getStart());
		try {
			Thread.sleep(COMPUTER_SELECTION_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (board.setFromTo(Pieces.AI_TAG, moves.get(chosen).getEnd())){
			board.tryToMove();	
		}
	}
}
