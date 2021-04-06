package GUI

import scala.swing.Alignment
import java.awt.Color

/**
 * Une bulle de robot pour le chat
 * @param textToShow String La chaine de caractères à afficher dans la bulle
 */
class RobotBulle(textToShow: String) extends Bulle(textToShow, "Robot", false)