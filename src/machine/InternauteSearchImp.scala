package machine

class InternauteSearchImp extends InternauteSearch {
  val url = "https://www.linternaute.com"
  val url_search = "https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name="
  
  /**
   * Search with keywords
   * @param keywords
   * @return the names and addresses of the response
   */
  def search(keywords: String): List[(String,String)] = {
    val home = get(url_search + keywords.replace(' ', '+'))
    
    getUrls(home)
  }
  
  /**
   * Search the address of the restaurant in the website
   * @param url the url
   * @return the address
   */
  def getAddresse(u: String): String = {
    
    var str = get(url + u).replace('\n', ' ')
    
    val regex = "<li aria-hidden=\"true\" class=\"icomoon-location\" >.*<\\/li>".r
    
    val line = regex.findFirstIn(str).mkString
    
    val (rue, n) = getBetweenSpan(line, 0)
    val (postalcode, n2) = getBetweenSpan(line, n)
    val (city, n3) = getBetweenSpan(line, n2)
    
    rue + ' ' + postalcode + ' ' + city

  }
  
  /**
   * @param str a string
   * @param start the index to start searching
   * @return the content between two span : <span > content </span>
   */
  def getBetweenSpan(str: String, start: Int): (String, Int) = {
    //TODO here
    val span_first = str.indexOf("<span >", start)
    if(span_first == -1)
      return ("", 0)
    val span_end = str.indexOf("</span>", start+7)
    
    (str.substring(span_first+7, span_end).trim, span_end+7)
  }
  
  /**
   * @param str the html
   * @return the names and urls of all the restaurant on this page
   */
  def getUrls(str: String): List[(String, String)] = {
    val regex = "<h2 class=\"bu_restaurant_title_2\">.*<\\/h2>".r
    
    val list = regex.findAllIn(str).toList
    
    var urls: List[(String, String)] = List()
    
    for(s <- list){
      val start = 43
      
      
      val quote = s.indexOf('"', start)
      
      val url = s.substring(start, quote)
      
      val end = s.indexOf("</a>")
      
      val name = s.substring(quote+2, end)
      
      urls = urls :+ (name, url)
    }
    
    urls
  }
  
  /**
   * HTML request
   * @return the html page
   */
  def get(url: String): String = scala.io.Source.fromURL(url).mkString
}
