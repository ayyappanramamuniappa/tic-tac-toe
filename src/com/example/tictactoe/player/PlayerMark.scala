package com.example.tictactoe.player

/**
 * @author s.a.ramamuniappa
 */
sealed abstract class PlayerMark 

case object X extends PlayerMark

case object O extends PlayerMark

case object Empty extends PlayerMark {
  override def toString = " "
}
