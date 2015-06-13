package com.example.tictactoe

/**
 * @author s.a.ramamuniappa
 */
case class InvalidPositionException(pos: Int, message : Option[String] = None) extends IllegalArgumentException(message.getOrElse(s"$pos is an invalid position in the board"))

case class InvalidMoveException(pos: Int, message : Option[String] = None) extends ArrayIndexOutOfBoundsException(message.getOrElse(s"$pos is alread taken in the board and is unavailable"))
