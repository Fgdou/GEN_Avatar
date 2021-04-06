package machine

import java.text.Normalizer
import scala.annotation.tailrec

object CorrectionImpl extends CorrectionErreur {
  /**
   * Trouve des mots cles dans une chaine de caracteres
   * @param phrase la phrase envoyee par l'utilisateur
   * @param words la liste de String des mots cles de notre base de donnees
   * @return Liste de String qui correspond aux mots cles presents dans s
   */
  override def trouverMotsCles(phrase: String, words: List[String]): List[String] = {
    var response = List[String]()
    var phraseTab = phrase.split(" |'").toList
    for (w <- words) {
      if (w.nonEmpty && w != " " && trouverMotsCles_rec(phraseTab, w.split(" |'").toList, started = false))
        response = response :+ w
    }
    response
  }

  /**
   * Trouve des mots cles dans une chaine de caracteres (en recursif)
   * @param phrase    la phrase
   * @param word      le mot a chercher dans la phrase
   * @param started   si le mot a commence
   * @return          si le mot est trouve
   */
  @tailrec
  def trouverMotsCles_rec(phrase: List[String], word: List[String], started: Boolean): Boolean = {
    (phrase, word) match {
      case (m1 :: r1, m2 :: r2) =>
        if (hamming(m1, m2) && m1.nonEmpty && m2.nonEmpty)
          trouverMotsCles_rec(r1, r2, started = true)
        else if (started)
          false
        else
          trouverMotsCles_rec(r1, word, started = false)

      case (_, Nil) => true
      case (Nil, _) => false
    }
  }

  /**
   * Calcule si un mot est proche d'un autre selon le théorème de Hamming
   * @param ww1 Mot 1
   * @param ww2 Mot 2
   * @return    Si le mot1 est proche du mot 2
   */
  def hamming(ww1: String, ww2: String): Boolean = {

    if (ww1.isEmpty || ww2.isEmpty)
      return false

    val w1 = toLowerCase(ww1)
    val w2 = toLowerCase(ww2)

    var tolerance = 1
    var i1 = 0; var i2 = 0
    var dif = w2.length - w1.length

    if (w1.length == 0 && w2.length == 0)
      return true
    if (w1.length <= 2 || w2.length <= 2)
      return w1 == w2
    if (Math.abs(dif) > 1)
      return false

    while (i1 < w1.length && i2 < w2.length && tolerance >= 0) {
      if (w1.charAt(i1) != w2.charAt(i2)) {
        if (dif == 1 && w1.charAt(i1) == w2.charAt(i2 + 1)) {
          i2 += 1
          dif -= 1
        }
        else if (dif == -1 && w1.charAt(i1 + 1) == w2.charAt(i2)) {
          i1 += 1
          dif -= 1
        }
        tolerance -= 1
      }
      i1 += 1; i2 += 1
    }

    i1 >= w1.length - tolerance && i2 >= w2.length - tolerance && tolerance >= 0

  }
  /*
  def hamming2(w1: String, w2: String): Boolean = {
    if(w1.length == 0 || w2.length == 0){ return false }

    var w1final = toLowerCase(w1.toLowerCase)
    var w2final = toLowerCase(w2.toLowerCase)

    var longStr : String = ""
    var shortStr : String = ""
    if(w1final.length > w2final.length){
      longStr = w1final
      shortStr = w2final
    }else{
      longStr = w2final
      shortStr = w1final
    }

    var caracteresEnCommun : Int = 0

    for (i <- shortStr) {
      if(longStr.contains(i)) caracteresEnCommun += 1
    }
    println(shortStr + " " + longStr + " " + caracteresEnCommun)
    ((caracteresEnCommun.toDouble / shortStr.length + shortStr.length.toDouble / longStr.length) / 2 * 100) > 80
  }*/

  /**
   * Supprime les accents dans une chaine de caracteres
   * @param input une string avec peut etre des accents
   * @return input mais sans accents et pas de majucules
   */
  def toLowerCase(input: String): String = {
    val accents = Map('é' -> 'e', 'è' -> 'e', 'ê' -> 'e', 'ù' -> 'u', 'î' -> 'i',
      'ë' -> 'e', 'ï' -> 'i', '?' -> ' ', '!' -> ' ', ',' -> ' ', ':' -> ' ', '.' -> ' ',
      'â' -> 'a', 'ô' -> 'o', 'à' -> 'a', 'ñ' -> 'n')

    var response = ""
    for (c <- input) {
      val newC = c.toLower
      val search = accents.getOrElse(newC, newC)
      response = response + search
    }
    response
  }
}
