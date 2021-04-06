package GUI

// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame
// Imports awt.Color
import java.awt.Color
import event._

/**
 * Un champ pour ecrir du texte au programme
 */
class TextBox(ui: UI) extends TextField {

  background = null
  foreground = Color.WHITE
  text = ""
  columns = 80
  border = Swing.EmptyBorder(2)

  // Si la touche ENTER est pressée, une requête est envoyée au moteur du programme
  listenTo(this.keys)
  reactions += {
    case KeyPressed(_, Key.Enter, _, _) => ui.request()
  }

  /*
   * Reinitialise le champ text
   */
  def reinit(): Unit = {
    text = ""
  }

}