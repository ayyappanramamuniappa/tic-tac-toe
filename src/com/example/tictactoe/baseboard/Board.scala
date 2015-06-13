package com.example.tictactoe.baseboard

import com.example.tictactoe.player.PlayerMark
import com.example.tictactoe.{InvalidMoveException,InvalidPositionException}
import com.example.tictactoe.player.{X,O,Empty}
import com.example.tictactoe.baseboard.Board.Position

/**
 * @author s.a.ramamuniappa
 */
class Board private (val squares: List[Square]) {

  override def toString = squares.toString()
  
  def move(position: Position, mark: PlayerMark): Board = {
    isValidMove(position) match {
      case Left(valid) if valid => Board(squares updated(position, Square(position,mark)))
      case Right(invalidMove)   => throw invalidMove
    }
  }
  
  def whoWon : Option[PlayerMark] = {
   val playerAndTheirMarkinPosition = squares.groupBy(_.marking).mapValues(_.map ( _.position )).par
   
   playerAndTheirMarkinPosition.find( squares => hasWon(squares._2)).fold[Option[PlayerMark]](None)( a => Some(a._1))
  }   

  private def hasWon(finalPositions : List[Position]) = {
      Board.winCombinations.par.find{ combination => combination.par.forall(finalPositions.contains(_)) } isDefined
  }
  
  def markingAtPosition(position: Position): PlayerMark = {
    squares.find(_.position equals position).fold(throw new InvalidPositionException(position))(_.marking)
  }
  
  def isBoardEmpty = {
    squares.forall(_.marking equals Empty)
  }
  
  def maxOccuranceOfNonEmptyMarking : Option[PlayerMark] = {
    val occuranceOfXandY = squares.foldLeft((0,0)){
      case((x,o),Square(pos,mark)) if(mark equals X) => (x+1,o) 
      case((x,o),Square(pos,mark)) if(mark equals O) => (x,o+1)
      case((x,o),Square(pos,mark)) if(mark equals Empty) => (x,o)
    }
    occuranceOfXandY match {
      case (occuranceOfX,occuranceOfO) if occuranceOfX > occuranceOfO => Some(X) 
      case (occuranceOfX,occuranceOfO) if occuranceOfO > occuranceOfX => Some(O)
      case _ => None
    }
 }
  
  private def isValidMove(position: Position): Either[Boolean, Exception] = {
    position match {
      case pos if !isValidPosition(pos) => Right(new InvalidPositionException(pos))
      case pos if !isPositionAvailable(pos) => Right(new InvalidMoveException(pos))
      case validPos => Left(true)
    }
  }
  
  private def isPositionAvailable(position: Position) = {
    squares find { square => (square.position equals position) && (square.marking equals Empty) } isDefined
  }
} 

object Board {

  type Position = Int

  val winCombinations = List(List(0, 1, 2),List(3, 4, 5),List(6, 7, 8),List(0, 3, 6),
                              List(1, 4, 7),List(0, 4, 8),List(0, 4, 8),List(2, 4, 6))

  def apply()= {
    val squares = List.fill(9)(Empty).zipWithIndex.map{ case (mark, position) => Square(position,mark)}
    new Board(squares)
  }
  
  def apply(squares: List[Square]) = {
    new Board(squares)
  }
}