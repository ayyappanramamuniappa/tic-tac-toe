package com.example.tictactoe.test

import org.scalatest.FlatSpec
import com.example.tictactoe.baseboard.{Board,Square}
import com.example.tictactoe.player.{X,O,Empty}
import com.example.tictactoe.{InvalidMoveException,InvalidPositionException}
import org.scalatest.Matchers
import com.example.tictactoe.InvalidPositionException

/**
 * @author s.a.ramamuniappa
 */
class BoardSpec extends FlatSpec with Matchers{
  
  "A board" should "not allow more than 9 positions to be created" in {
     
    intercept[IllegalArgumentException]{
      val squares = List(O, X, X, O,
                        O, X, O, X,
                        O, O, X, O,
                        X, O, O, X).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
      Board(squares)
    }
  }
  it should "return right marking in the specified position" in {
    val board = Board().move(5,X)
    board.markingAtPosition(5) shouldBe X
  }
  
  it should "thow exception when queried for invalid position" in {
    val board = Board()
    intercept[InvalidPositionException]{
      board.markingAtPosition(-1)
    }
  }
  
  "A move on board" should "mark in the specified position when the move is valid" in {
    val board = Board()
    val updatedBoard = board.move(8, O)
    updatedBoard.squares.find( _.position == 8).get.marking shouldBe O
  } 
  
  it should "throw exception when tried to move to position not present in board" in {
    val board = Board()
    intercept[InvalidPositionException]{
      board.move(9, X)
    }
  }
  
  it should "throw exception when tried to move to already marked position" in {
    val board = Board().move(3, X).move(4,O)
    intercept[InvalidMoveException]{
      board.move(3,O)
    }
  }
 
  "Max occurance of marking on board" should "return player mark which occurs most" in {
    val board = Board().move(3, X).move(5, O).move(1,X)
    board.maxOccuranceOfNonEmptyMarking shouldBe Some(X)
  }
  
  it should "return None when none of the marking are maximum" in {
    val board = Board().move(3, X).move(5, O)
    board.maxOccuranceOfNonEmptyMarking shouldBe None
  }
  
  "Winning calculation" should "identify diagonal win" in {
    val squares = List(X, O, O,
                       O, X, O,
                       O, O, X).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
    val board = Board(squares)
    board.whoWon shouldBe Some(X)
  }
  
   it should "identify horizontal win" in {
    val squares = List(X, X, X,
                       Empty, Empty, O,
                       O, O, Empty).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
    val board = Board(squares)
    board.whoWon shouldBe Some(X)
  }
   
  it should "identify vertical win" in {
    val squares = List(O, X, X,
                       O, X, O,
                       O, O, X).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
    val board = Board(squares)
    board.whoWon shouldBe Some(O)
  }
  
  it should "say draw when there is no winner" in {
    val squares = List(X, O, X,
                       O, X, O,
                       O, X, O).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
    val board = Board(squares)
    board.whoWon shouldBe None
  }
}
  
 