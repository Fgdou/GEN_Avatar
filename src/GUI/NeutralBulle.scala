package GUI

import scala.swing.Alignment
import java.awt.Color

/**
 * Créer une bulle bleu sans nom d'émetteur
 * @param textToShow String une chaîne de caractères à afficher en bleu
 */
class NeutralBulle(textToShow: String) extends Bulle(textToShow, "", false)