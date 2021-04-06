package machine

trait CorrectionErreur {
  /**
   * @param phrase la phrase envoyee par l'utilisateur
   * @param words la liste de String des mots cles de notre base de donnees
   * @return Liste de String qui correspond aux mots cles presents dans s
   */
  def trouverMotsCles (phrase : String, words: List[String]): List[String]
}
