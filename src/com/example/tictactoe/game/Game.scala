package com.example.tictactoe.game

import com.example.tictactoe.baseboard.Board
import com.example.tictactoe.baseboard.Board.Position
import com.example.tictactoe.player.{Player,PlayerMark}

/**
 * @author s.a.ramamuniappa
 */
class Game private (var board: Board, val players: Set[Player], startPlayer: Player) {
    
  def playerMove(player: Player,position: Position) = {
    board = board.move(position, player.mark) 
  }
  
  def playerAtPosition(position: Position): Option[Player] = {
    val markOnSpecifiedPosition = board markingAtPosition(position)
    findPlayerByMarking(players,markOnSpecifiedPosition)
  }
  
  def nextPlayerTurn : Player = {
    if(!board.isBoardEmpty) {
      board.maxOccuranceOfNonEmptyMarking.fold(startPlayer){ playerMark => 
        val playerWithMaximumTurns = findPlayerByMarking(players,playerMark).get
        players.filterNot(_ equals playerWithMaximumTurns).head
      }
    }
    else startPlayer
  }
  
  protected def findPlayerByMarking(players:Set[Player],marking: PlayerMark) : Option[Player] = {
    players find(_.mark equals marking)
  }
}

object Game {
  
  def apply(board: Board = Board(),players: Set[Player],startPlayer: Player) : Game = {
    if(!(players.toList.length > 2) && players.contains(startPlayer)){
      new Game(board,players,startPlayer)
    }
    else 
      throw new IllegalArgumentException("Game could not be created due to invalid players")
  }
}
