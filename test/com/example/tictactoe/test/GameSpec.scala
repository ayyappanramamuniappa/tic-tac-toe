package com.example.tictactoe.test

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.example.tictactoe.baseboard.Board
import com.example.tictactoe.player.Player
import com.example.tictactoe.game.Game
import com.example.tictactoe.player.{X,O}
import org.scalatest.BeforeAndAfterEach
import com.example.tictactoe.{InvalidMoveException,InvalidPositionException}

/**
 * @author s.a.ramamuniappa
 */
class GameSpec extends FlatSpec with Matchers with BeforeAndAfterEach{
  
  val player1 = new Player("Player1", X)
  val player2 = new Player("Player2", O)
  
  var game : Game = Game(Board(), Set(player1,player2),player2)

  override def beforeEach = {
    game = Game(Board(), Set(player1,player2),player2)
  }
  
  "A player at position" should "return the player in a given position" in {
    game.playerMove(player1, 3)
    game.playerAtPosition(3).get shouldBe player1
  }
  
  it should "throw exception when tried to move to position not present in board" in {
    intercept[InvalidPositionException]{
      game.playerAtPosition(10)
    }
  }
  
  "Next player turn" should "return startplayer when it is the first turn" in {
    game.nextPlayerTurn shouldBe player2
  }
  
  it should "return a opponent player when one player has made one move more than opponent" in {
     game.playerMove(player1,3)
    game.nextPlayerTurn shouldBe player2
  }
  
  it should "return the player who started first when there are equal occurance of markings" in {
    game.playerMove(player2, 3)
    game.playerMove(player1, 4)
    game.nextPlayerTurn shouldBe player2  
  }
  
}