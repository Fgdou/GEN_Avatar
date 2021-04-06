package machine

trait Language {
  /**
   * Load the file on the RAM
   * @param path the path
   */
  def load(path: String): Unit

  /**
   * @param category like "Politesse"
   * @param lang like "Français"
   * @param elseWord if the words are not found
   * @return the words in this language
   */
  def getWordsOrElse(category: String, lang: String, elseWord: String): List[String]

  /**
   * @param lang the language like "Français"
   * @param line the line (check on the file)
   * @param placeholder word to fill the XXX
   * @param elseWord if not found
   * @return the expression with the placeholder
   */
  def getExpressionOrElse(lang: String, line: Int, placeholder: String, elseWord:String): String

  /**
   * @return all the different languages
   */
  def getLanguages: List[String]
}
