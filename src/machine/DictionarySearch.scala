package machine

trait DictionarySearch {
  
  /**
   * Look for a word's definition(s) in a dictionary
   * Internet is required
   * @param request A word we wanna search
   * @param lang The language of the word (français, anglais, allemand, espagnol, italien)
   * @return List of definition(s)
   */
  def search(request: String, lang: String): Option[List[String]]
  
}