package GUI

// Imports needed for working with Swing in Scala
import java.awt.BorderLayout
import scala.swing._
import scala.swing.MainFrame
// Imports awt.Color
import java.awt.Color
import javax.swing.border.EmptyBorder
import scala.concurrent.duration.Duration.Infinite
import javax.swing.ImageIcon
import javax.imageio.ImageIO

/**
 * Génératrice de la fenêtre de l'application
 * @param name Le nom de l'utilisateur
 */
class UI(name: String) extends MainFrame {

  // Fixation de la taille de la fenetre
  preferredSize = new Dimension(800, 512)
  this.resizable_=(false)
  this.location_=(new Point(32, 32))
  title = "Avatar App"

  // Importation de l'icône du programme
  try {
    iconImage = (new ImageIcon("doc/Logo.png")).getImage
    println("LOG : Logo loaded successfully")
  } catch {
    case _: Throwable => println("LOG : Error while loading the logo !")
  }

  val bp = new BoxPanel(Orientation.Vertical)

  val bp_chat = new BoxPanel(Orientation.Vertical)
  // On definit une taille fixe pour que l'utilisateur ait assez de place pour converser avec le robot

  // On ecoute ce que l'utilisateur a a nous dire...
  val textBox = new TextBox(this)
  val sendButton = new SendButton("Send", this)

  // On ajoute le contenu de bp_chat a un element supportant la barre de defilement
  val scrollPane: ScrollPane = new ScrollPane(bp_chat)

  bp.contents += scrollPane

  // Ajout des composants de saisie et envoi de la requete (avec sendButton)
  val bp_bottom = new BoxPanel(Orientation.Horizontal)
  bp_bottom.maximumSize_=(new Dimension(preferredSize.width, 64))

  bp_bottom.contents += textBox
  bp_bottom.contents += sendButton

  // Ajout des composants de saisie et d'interaction au contenu le bp
  bp.contents += bp_bottom
  background = new Color(0x151515)
  bp_chat.background = background
  bp.background = background
  scrollPane.background = background
  bp_bottom.background = new Color(0x1e6f5c)

  bp.border = Swing.EmptyBorder(0)
  bp_chat.border = Swing.EmptyBorder(5)
  scrollPane.border = Swing.EmptyBorder(0)
  bp_bottom.border = Swing.EmptyBorder(0)

  // Le contenu de bp devient le contenu de la Frame
  contents = bp

  bp_chat.contents += new RobotBulle(List("Bonjour " + name, "Voici ce que vous pouvez me demander : ", " - Recherche d'adresse sur Rennes", " - Recherche d'adresse de restaurant", " - Définitions").mkString("\n"))

  /**
   * Affichage du texte et interaction avec le backend
   */
  def request(): Unit = {
    val previous_contents: Int = bp_chat.contents.size
    bp_chat.contents += new HumanBulle(textBox.text, name)

    val response: List[String] = machine.MachineImpl.ask(textBox.text)
    bp_chat.contents += new RobotBulle(response.mkString("\n"))

    // Affichage des modifications
    textBox.reinit()
    bp_chat.validate()
    bp_chat.repaint()
    scrollPane.validate()
    scrollPane.repaint()
    scrollPane.verticalScrollBar.value = scrollPane.verticalScrollBar.maximum
  }

  /**
   * Ajout d'un element au BoxPanel bp_chat
   * @param element Component Element à ajouter au BoxPanel
   */
  def addContent(element: Component): Unit = {
    bp_chat.contents += element
    textBox.reinit()
    bp_chat.validate()
    bp_chat.repaint()
    scrollPane.validate()
    scrollPane.repaint()
    scrollPane.verticalScrollBar.value = scrollPane.verticalScrollBar.maximum
  }

}