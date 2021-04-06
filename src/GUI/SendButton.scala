package GUI

// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame
// Imports awt.Color
import java.awt.Color
import event._

import machine._

/**
 * Un bouton cliquable pour interagir avec le programme
 * @param name Le text affiche sur le bouton
 */
class SendButton(name: String, ui: UI) extends Button {

  text = name
  
  background = new Color(0x29bb89)
  foreground = Color.WHITE
  this.borderPainted_=(false)

  // Si le bouton est pressé, une requête est soumise au moteur du programme
  reactions += {
    case ButtonClicked(_) => ui.request
  }

}