package machine

import scala.io.Source

//TODO use map instead of reading the file
class LanguageImp extends Language {

  var words: Map[String, Map[String, List[String]]] = Map()
  var expression: Map[String, List[String]] = Map()

  /**
   * Load the file on the RAM
   * @param path the path
   */
  def load(path: String): Unit = {
    words = Map()
    expression = Map()
    val source = Source.fromFile(path);
    val lines: List[String] = source.getLines().toList; //On récupère chaque ligne du fichier qu'on met dans une liste

    var n = 0;
    while(lines(n) != "")
      n += 1                    //On avance dans le fichier tant que la ligne du fichier n'est pas vide
    n += 2                      //Puis on saute 2 lignes pour arriver sur la première catégorie

    while(n < lines.size-1 && lines(n) != ""){
      val word = lines(n).substring(0, lines(n).length-1)  // On récupère la String entre l'indice 0 et la longueur de la ligne
      n += 1

      var l: Map[String, List[String]] = Map()
      while(lines(n) != ""){
        val tab: List[String] = lines(n).split(": ").toList  //Création d'une liste que l'on split pour avoir seulement la langue et les mots correspondants

        val lang: String = tab.head  //On récupère la langue qui est le premier élément de la liste 
        val words: List[String] = tab(1).split(",").toList //On récupère les mots correspondants à la langue associée

        l = l + (lang -> words)  //Ajout dans la map de la clé lang et ses valeurs qui correspondent aux mots associés à la langue
        n += 1
      }

      words = words + (word -> l)
      n += 1
    }

    n += 2

    while(lines(n) != "")
      n += 1
    n += 1

    while(n < lines.size){
      val lang = lines(n).substring(0, lines(n).length-1) // on recupere la langue
      n += 1

      var list: List[String] = List()
      while(n < lines.size && lines(n) != ""){  // on recupere toutes les expressions de lang
        list = list :+ lines(n)
        n += 1
      }
      
      // ajout des clés valeurs dans la liste expression
      // lang : String (langue) -> list : List[String] (liste des expressions associées à la langue)
      expression = expression + (lang -> list)  
      n += 1
    }

    source.close();
  }

  /**
   *  @return all the different languages
   */
  def getLanguages : List[String] = {
    expression.keySet.toList    //Renvoie une liste de String contenant les différentes langues existantes dans le fichier
  }
  
  /**
   * @param category la catégorie recherchée, Politesse, Recherche ou Nom de langue
   * @param lang la langue recherchée
   * @param elseWord mot à return si on trouve rien
   * @return List[String] dans laquelle il y a les mots de la langue pour la catégorie recherchée
   * 
   */
  def getWordsOrElse(category: String, lang: String, elseWord: String): List[String] = {
    words.getOrElse(category, elseWord) match{  //On get les langues associées à leurs mots correspondants pour la catégorie recherchée
      case s:String => List(s)                  //Si la catégorie n'existe pas on renvoie la List(elseWord) 
      case m:Map[String, List[String]] =>       //On fait un case pour récupérer les différentes langues et mots compris dans la catégorie
        m.getOrElse(lang, elseWord) match{      //On get les mots de la bonne catégorie pour la langue recherchée
          case s:String => List(s)              //Si la langue n'existe pas on renvoie List(elseWord)
          case other: List[String] => other     //On renvoie finalement la List de String comprenant les mots associés à la bonne catégorie et à la bonne langue
        }
    }
  }
  
  /**
   * @param lang la langue
   * @param ligne le numero de la ligne de la langue que l'on veut obtenir
   * @param elseWord mot à retourner si on trouve pas
   * @return String la bonne expression en fonction de la langue et la ligne ou elseword
   */
  def getExpressionOrElse(lang: String, line: Int, placeholder: String, elseWord:String): String = {
    expression.getOrElse(lang, elseWord) match{
      case s:String => s  // si on obtient un mot, on le retourne
      case l: List[String] =>
        if(line >= l.size)
          elseWord  // erreur
        else
          l(line).replace("XXX", placeholder) // on remplace XXX par placeholder
    }
  }
}