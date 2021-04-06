package client

import GUI.{ MaryVoice, UI }

import java.awt.PopupMenu
// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame
import java.util.Scanner
import GUI.RobotBulle

/**
 * Lancement de l'application à interface graphique
 */
object Application extends SimpleSwingApplication {
  println("Launching the service...")
  // Initialisation de la synthèse vocale (chargement)
  MaryVoice.init()
  println("LOG :  Voice initialized")
  var userName = ""
  // Définition du nom d'utilisateur
  val r: Unit = Dialog.showInput(null, "Entrez votre nom", initial = "") match {
    case Some("")    => userName = "USER"
    case Some(value) => userName = value
    case None        => userName = "USER"
  }
  println("LOG : Username set to " + userName)
  // Création de la fenêtre principale
  def top = new UI(userName)
  println("Service launched successfully.")
}
