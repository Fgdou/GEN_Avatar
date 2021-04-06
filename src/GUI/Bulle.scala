package GUI

// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame
import scala.swing.event.ButtonClicked
// Imports awt.Color
import java.awt.Color
import java.awt.geom.RoundRectangle2D

/**
 * Une bulle pour l'affichage du chat
 * @param _message String Message à afficher
 * @param _name String Nom de l'émetteur
 * @param right Boolean True si et seulement si la bulle doit être à droite
 */
class Bulle(_message: String, _name: String, right: Boolean) extends BoxPanel(Orientation.Vertical) {

  val align = if (right) Alignment.Right else Alignment.Left

  background = null
  foreground = Color.WHITE

  val message: String = _message
  val names: String = _name

  border = Swing.EmptyBorder(10)

  // Ecoute du click de la souris pour déclencher la synthèse vocale
  listenTo(mouse.clicks)
  reactions += {
    case _: event.MouseClicked => MaryVoice.say(message, machine.MachineImpl.language)
  }

  val title = new Label(names, null, align)
  val bottom = new BoxPanel(Orientation.Vertical)

  for (str <- message.split("\n")) {
    val msg = cut(str, 66)
    var used = false
    for (m <- msg) {
      val lab = new Label(if (used) ("        " + m) else m, null, align)
      lab.font = new Font("Sans serif", 0, 20)
      lab.background = null
      lab.foreground = foreground

      bottom.contents += lab
      used = true
    }
  }

  title.font = new Font("Sans serif", 0, 14)

  title.background = background
  title.foreground = foreground
  bottom.background = background
  bottom.foreground = foreground

  contents += title
  contents += bottom

  xLayoutAlignment = 0

  /**
   * Découpe la chaîne de caractère d'entrée tous les n caractères
   * @param str String Une chaîne de caractères à découper
   * @param n Integer L'indice auquel la chaîne est découpée
   * @return List de strings découpés tous les n caractères
   */
  def cut(str: String, n: Int): List[String] = {
    if (str == "" || str.length <= n)
      List(str)
    else {
      val i = str.lastIndexOf(" ", n)
      if (i == -1)
        str.substring(0, n) +: cut(str.substring(n), n)
      else
        str.substring(0, i) +: cut(str.substring(i + 1), n)
    }
  }

}