package machine

import scala.io.Source

class BaseDeDonneeImp extends BaseDeDonnee {

  var donnees: Map[String, String] = null // on initialise notre table à null
  var keyCaseSensitiv: List[String] = null // de même avec la liste de noms

  /**
   * Load the database file into the class
   *
   * @param path The file relative from the root folder of the project
   */
  override def load(path: String): Unit = {
    val source = Source.fromFile(path) // on charge le fichier grâce à son chemin dans l'arborescence de l'exploreur de packages
    val lines: List[String] = source.getLines().toList // on transforme le fichier texte en lignes dans une liste
    source.close() // on ferme notre source

    donnees = Map() // on initialise notre table 
    keyCaseSensitiv = List() // pareil avec la liste de noms

    for (line <- lines) { // pour chaque ligne de la liste de lignes
      val tab = line.split(";") // on récupère les mots séparés par un point virgule sur une seule et même ligne
      val key = CorrectionImpl.toLowerCase(tab(0)) // on applique la correction dues à d'eventuelles fautes d'accents ou grammaire
      val value = tab(1) // notre adresse correpondra au mot après le point virgule 
      donnees = donnees + (key -> value) // on associe les noms aux adresses en incrémentant à chaque fois les associations
      keyCaseSensitiv = keyCaseSensitiv :+ tab(0) // on en fait pas moins avec la liste de noms avant le point virgule (case 0)
    }
  }

  /**
   * Returns the value associated with a key, or a default value if the key is not contained in the map.
   * @param s         The key to search
   * @param notFound  The value if not found
   * @return          The value searched or the notFound
   */
  def getOrElse(s: String, notFound: String): String = {
    val key = CorrectionImpl.toLowerCase(s) // on applique la correction d'orthographe avec les accents et autres à notre nom
    if (donnees == null) // on regarde si la base de donnée est vide
      "Database not loaded" // si c'est le cas la base n'est donc pas chargée
    else
      donnees.getOrElse(key, notFound) // sinon on renvoie l'adresse associée avec notFound comme String par défaut dans l'autre cas
  }

  /**
   * Get all the keys
   *
   * @return
   */
  override def getNoms(): List[String] = {
    if (donnees == null) // on regarde si la table d'associations est vide
      List("Database not loaded") // on renvoie une liste indiquant que c'est le cas si c'est extact
    else
      keyCaseSensitiv // dans le cas contraire on retourne la liste de noms préalablement définie plus haut dans le code
  }
}
