package machine

import java.net.{ URL, HttpURLConnection }

object DictionarySearchImpl extends DictionarySearch {

  val url: String = "https://api.dictionaryapi.dev/api/v2/entries/"
  var lang_api: String = "fr"
  def search(request: String, lang: String): Option[List[String]] = {
    // Définition de la langue
    if (lang.equalsIgnoreCase("français"))
      lang_api = "fr"
    else if (lang.equalsIgnoreCase("anglais"))
      lang_api = "en_US"
    else if (lang.equalsIgnoreCase("espagnol"))
      lang_api = "es"
    else if (lang.equalsIgnoreCase("italien"))
      lang_api = "it"
    else if (lang.equalsIgnoreCase("allemand"))
      lang_api = "de"
    else
      return None
    lang_api += "/"

    try {
      // Récupération de la réponse à la requête envoyée
      val response: String = get(url + lang_api + request.replace(' ', '+'))

      // Filtrage de la réponse vide de l'API de Google
      if (response.startsWith("{\"title\":\"No Definitions Found\"") || response.equals(""))
        return None

      val pattern = "\"definition\":\"[^\"]*".r

      // Détection des définitions dans la réponse de l'API
      val definitions: Iterator[String] = pattern findAllIn response

      var resp_list: List[String] = Nil

      // Suppression du mot clé "définition"
      while (definitions.hasNext)
        resp_list = resp_list ++ List(definitions.next().substring(14))

      // Ajout du numéro de la liste dans la réponse
      Some(addNumberList(resp_list))
    } catch {
      case _: Throwable => None
    }
  }

  /**
   * Get a response from an API
   * @param url String the URL of the API endpoint
   * @return List of the responses
   */
  def get(url: String) = {
    try {
      // Définition des variables de requête
      val timeout: Int = 5000
      val readTimeout: Int = 5000
      val requestMethod: String = "GET"

      // Création de la connexion avec le site internet
      val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]

      connection.setConnectTimeout(timeout)
      connection.setReadTimeout(readTimeout)
      connection.setRequestMethod(requestMethod)

      val inputStream = connection.getInputStream
      // Récupération de la réponse
      val content = io.Source.fromInputStream(inputStream).mkString

      if (inputStream != null)
        inputStream.close
      content
    } catch {
      case _: Throwable => ""
    }
  }

  /**
   * Add the index +1 to each element of the list
   * @param l A list of string
   * @return The same list with the index +1 at the beginning if there is more than one element in the list
   */
  def addNumberList(l: List[String]): List[String] = {
    var response: List[String] = Nil
    // S'il n'y a qu'un élément dans la liste : on rend la liste telle quelle
    if (l.length <= 1)
      return l
    var index: Int = 1;
    // Sinon on ajout l'index dans la liste
    for (e <- l) {
      response = response ++ List(index.toString() + ") " + e)
      index = index + 1
    }
    response
  }

}