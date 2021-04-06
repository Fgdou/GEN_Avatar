package GUI

import scala.swing.Alignment
import java.awt.Color

/**
 * Une bulle d'humain pour le chat
 * @param textToShow String La chaine de caractères à afficher dans la bulle
 * @param nameUser String La chaine de carastères représentant le nom du l'utilisateur
 */
class HumanBulle(textToShow: String, nameUser: String) extends Bulle(textToShow, nameUser, true)