package com.example.tictactoe.player

/**
 * @author s.a.ramamuniappa
 */

case class Player(val name: String, val mark: PlayerMark){
  override def toString = name
}