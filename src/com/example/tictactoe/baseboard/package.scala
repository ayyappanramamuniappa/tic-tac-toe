package com.example.tictactoe

/**
 * @author s.a.ramamuniappa
 */
package object baseboard {
  
  private[baseboard] def isValidPosition(position: Int) = position >= 0 && position < 9

}