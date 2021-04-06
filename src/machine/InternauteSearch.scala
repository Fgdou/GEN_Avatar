package machine

trait InternauteSearch {

  /**
   * Search with keywords
   * @param keywords
   * @return the names and addresses of the response
   */
  def search(keywords: String): List[(String, String)]
  /**
   * Search the address of the restaurant in the website
   * @param url the url
   * @return the address
   */
  def getAddresse(url: String): String
}
