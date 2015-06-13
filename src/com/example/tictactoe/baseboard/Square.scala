package com.example.tictactoe.baseboard

import com.example.tictactoe.InvalidPositionException
import com.example.tictactoe.player.PlayerMark
import com.example.tictactoe.baseboard.Board.Position

/**
 * @author s.a.ramamuniappa
 */
case class Square (val position: Position, marking: PlayerMark) {
  require(isValidPosition(position)) 
}
