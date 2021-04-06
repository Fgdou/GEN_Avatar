package machine

import scala.xml._

class XmlSearchImp extends XmlSearch {

  var donnees: Map[String, String] = null
  var keyCaseSensitiv: List[String] = null

   /**
   * Load the database file into the class
   *
   * @param path The file relative from the root folder of the project
   */
  def load(path: String): Unit = {
    val source = XML.load(path) \\ "organization" //on se place dans la section organization

    donnees = Map() // on initilialise notre map vide
    keyCaseSensitiv = List() // ainsi que la liste des noms
    
    for(line <- source){ // on parcourt chaque ligne du xml
      if((line \\ "city").text == "Rennes"){ //on vérifie que le lieu se toruve à Rennes
        val name : String = (line \ "name").apply(0).text //on créé une variable nom dans la première étiquette qui vient
        var adresse : String = (((line \ "addresses") \ "address")).apply(0).text //même chose avec les adresses en valeurs
        for(adr <- (((line \"addresses")\ "address")).apply(0)){ // on regarde chaque adresse 
          if(((adr \ "street")\"number").text.isEmpty()) { //on regarde si elles ne comportent pas de numéro de rue
            adresse = ((adr\"street")\"name").text // si c'est le cas on ajoute juste le nom de la rue
          }
          else{
            adresse = ((adr\"street")\"number").text + "," + ((adr\"street")\"name").text //sinon on ajoute le nom et le numéro de rue
          }
        }
        val value = adresse // ici c'est juste un changement de nom pour plus de visibilité mais pas essentiel au code
        donnees = donnees + (name -> value) // ici on associe les noms aux valeurs, en ajoutant dans la table à chaque passage
        keyCaseSensitiv = keyCaseSensitiv :+ name //on fait de même avec la liste des noms
      }
    }
  }

  
  /*
   * Search all the requests matching with the keyword
   * 
   * @param keyword the keyword
   * @return all the requests in a list of String
   */
  def search(keywords: String): List[String] = {
    var reponse : List[String] = List() // on initialise notre liste de noms 
    var num =0; // ainsi que le compteur de réponses disponibles à nul

    for(key <- donnees.keySet){ // on regarde pour chaque nom dans la table
      val tab = keywords.split(" ").toList // on récupère les mots séparés par un espace dans les mots clés dans une liste
      val response = CorrectionImpl.trouverMotsCles(key, tab) // on corrige les éventuelles fautes d'accents ou d'orthographes
      if(response.size == tab.size){ // si la taille de la liste response équivaut à celle de tab
        reponse = reponse :+ key // on ajoute le nom en question à la liste
        num = num + 1; // on incrémente notre nombre de choix
      }
    }
    reponse // on retourne notre liste de réponses
  }

  /*
   * Give the address of a research
   * 
   * @param name a research
   * @return the address of the research 
   */
  def getAddress(name: String): String = {
    if (donnees == null) // si la map n'existe pas encore
      "Database not loaded" //il n'y a rien à afficher
    else
      donnees.getOrElse(name,"notFound") // sinon on récupère l'adresse associée au nom avec notFound sinon
  }

}
