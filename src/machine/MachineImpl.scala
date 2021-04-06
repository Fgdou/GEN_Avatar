package machine

object MachineImpl extends MachineDialogue {
  val bdd_synonymes: BaseDeDonnee = new BaseDeDonneeImp
  val bdd_addresses: BaseDeDonnee = new BaseDeDonneeImp

  val bdd_lang: Language = new LanguageImp
  val xml: XmlSearch = new XmlSearchImp
  val internaute = new InternauteSearchImp

  var language: String = "Français"
  var state: Int = 0
  var responses: List[(String, String)] = List()

  bdd_synonymes.load("doc/synonymes.txt")
  bdd_addresses.load("doc/DonneesInitiales.txt")

  bdd_lang.load("lib/international.txt")
  xml.load("lib/vAr.xml")

  /**
   * Propose une requete au programme
   * @param s la requete
   * @return la liste de reponses
   */
  def ask(s: String): List[String] = {

    val s_lower = CorrectionImpl.toLowerCase(s)
    var bonjour: Boolean = false;

    println(s_lower)

    var response: List[String] = List()
    //Check le changement de langue
    if(state != 1 && state != 3)
      response = checkLang(s_lower)
    if(response.isEmpty) {
      //Check si l'utilisateur doit choisir
      if (state == 1 || state == 3) {
        response = choose(s)
      } else {
        //check le bonjour du debut
        bonjour = checkBonjour(s_lower)
        var s_without_bonjour = s_lower
        if(bonjour) {
          val searchBonjour = s_lower.indexOf(' ')
          if(searchBonjour != -1)
            s_without_bonjour = s_lower.substring(searchBonjour)
          else
            return List(bdd_lang.getWordsOrElse("Politesse", language, "").head)
        }

        //Regarde dans la bdd
        response = askBdd(s_without_bonjour)
        if (response.isEmpty) {

          val restaurant: Boolean = CorrectionImpl.trouverMotsCles(s_without_bonjour, bdd_lang.getExpressionOrElse(language, 8, "", "").split(", ").toList).nonEmpty
          val definition: Boolean = CorrectionImpl.trouverMotsCles(s_without_bonjour, bdd_lang.getExpressionOrElse(language, 9, "", "").split(", ").toList).nonEmpty
          
          //Choisis ou chercher
          if(definition)
            response = askDefinition(s_without_bonjour)
          else if(restaurant)
            response = askInternaute(s_without_bonjour)
          else
            response = askXml(s_without_bonjour)

        }
      }

      //Formule la reponse
      if(response.isEmpty)
        response = response :+ bdd_lang.getExpressionOrElse(language, 3, "", "")

      if(bonjour)
        response = bdd_lang.getWordsOrElse("Politesse", language, "").head +: response

    }
    response = removeCopies(response)
    println(response)
    response

  }

  /**
   * Change the language of the bot
   * @param lang the string language ("Français")
   */
  def setLanguage(lang: String): Unit = {
    val list: List[String] = bdd_lang.getLanguages

    if(list.contains(lang))
      language = lang
  }

  /**
   * Check if the word Hello is present on any language
   * @param str the sentence
   * @return if the word is there
   */
  def checkBonjour(str: String): Boolean = {
    val words = bdd_lang.getWordsOrElse("Politesse", language, "")

    val response = CorrectionImpl.trouverMotsCles(str, words)

    response.nonEmpty
  }

  /**
   * rotate the langage
   * @return the sentence to say
   */
  def nextLang(): List[String] = {
    val list: List[String] = bdd_lang.getLanguages

    var n = list.indexOf(language)

    n = (n+1)%list.size

    language = list(n)

    List(bdd_lang.getExpressionOrElse(language, 4, "", ""))
  }

  /**
   * Change the langage if needed
   * @param s the sentence
   * @return  the answer
   */
  def checkLang(s: String): List[String] = {
    var response_list: List[String] = List()
    var new_language = ""
    if(state == 2) {
      if (s == bdd_lang.getExpressionOrElse(language, 0, "", "")) {
        state = 0
        return List(bdd_lang.getExpressionOrElse(language, 5, "", ""))
      }
    }
    
    //Cherche le changement de langue avec le mot exacte
    for (lang: String <- bdd_lang.getLanguages) {
      var response = findWordsExact(s, bdd_lang.getWordsOrElse("Politesse", lang, "NotFound"))
      if (!response) {
        response = findWordsExact(s, bdd_lang.getWordsOrElse("Recherche", lang, "NotFound"))
        if(!response){
          response = findWordsExact(s, bdd_lang.getWordsOrElse("Nom de langue", lang, "NotFound"))
        }
      }
      if (response) {
        if(lang == language) {
          state = 0
          return List()
        }
        if(response_list.isEmpty){
          state = 2
          new_language = lang
          response_list = List(bdd_lang.getExpressionOrElse(lang, 4, "", ""))
        }
      }
    }
    //Cherche le changement de langue avec un mot qui ressemble
    for (lang: String <- bdd_lang.getLanguages) {
      var response = findWords(s, bdd_lang.getWordsOrElse("Politesse", lang, "NotFound"))
      if (!response) {
        response = findWords(s, bdd_lang.getWordsOrElse("Recherche", lang, "NotFound"))
        if(!response){
          response = findWords(s, bdd_lang.getWordsOrElse("Nom de langue", lang, "NotFound"))
        }
      }
      if (response) {
        if(lang == language) {
          state = 0
          return List()
        }
        if(response_list.isEmpty){
          state = 2
          new_language = lang
          response_list = List(bdd_lang.getExpressionOrElse(lang, 4, "", ""))
        }
      }
    }
    if(response_list.nonEmpty) {
      language = new_language
      response_list
    }
    else if(state == 2)
      nextLang()
    else
      List()
  }

  /**
   * @param s the sentence
   * @param w the word to search
   * @return  if the word is there
   */
  def findWords(s: String, w: List[String]): Boolean ={
    for(ww <- w){
      for(ss <- s.split(" "))
        if(CorrectionImpl.hamming(ss,ww))
          return true
    }
    false
  }
  /**
   * @param s the sentence
   * @param w the word to search
   * @return  if the word is there
   */
  def findWordsExact(s: String, w: List[String]): Boolean ={
    for(ww <- w){
      for(ss <- s.split(" "))
        if(CorrectionImpl.toLowerCase(ss) ==  CorrectionImpl.toLowerCase(ww))
          return true
    }
    false
  }

  /**
   * Select an item in the list
   * @param s the string with the number
   * @return  the answer
   */
  def choose(s: String): List[String] = {
    var response:List[String] = List()
    if(state == 1 || state == 3) {
      val tab = s.split(" ").toList

      for(ss: String <- tab){
        try{
          val n = ss.toInt-1
          if (n<0 || n >= responses.size)
            response = List()
          else {
            if(state == 1)
              response = List(bdd_lang.getExpressionOrElse(language, 2, responses(n)._1, "error") + " : " + xml.getAddress(responses(n)._2))
            else
              response = List(bdd_lang.getExpressionOrElse(language, 2, responses(n)._1, "error") + " : " + internaute.getAddresse((responses(n)._2)))
            state = 0
          }

        }catch{
          case _: Throwable => ()
        }
      }
    }
    if(response.isEmpty)
      state = 0
    response
  }

  /**
   * Delete the question in the sentence
   * @param s the string
   * @return the string without the question
   */
  def removeQuestion(s: String): String = {
    var n = 0;
    for(w: String <- bdd_lang.getWordsOrElse("Recherche", language, "")){
      var i = 0;
      for(ss <- s.split(" ")){
        i += ss.length+1
        if(CorrectionImpl.hamming(w, ss) && i > n)
            n = i
      }
    }

    if(n == 0)
      s
    else if(n > s.length)
      ""
    else {
      val i = s.indexOf(" ", n+1)
      if(i != -1)
        CorrectionImpl.toLowerCase(s.substring(i+1))
      else
        CorrectionImpl.toLowerCase(s.substring(n))
    }
  }

  /**
   * Find the informations on the Dababase
   * @param s the question
   * @return the answer
   */
  def askBdd(s: String): List[String] ={
    val line = CorrectionImpl.toLowerCase(s)

    val possibleWords = bdd_addresses.getNoms() ++ bdd_synonymes.getNoms()
    val keyWords = CorrectionImpl.trouverMotsCles(line, possibleWords)

    var newKeyWords: List[String] = List()

    for (key <- keyWords)
      newKeyWords = newKeyWords :+ bdd_synonymes.getOrElse(key, key)

    var response: List[String] = List()

    for (key <- newKeyWords) {
      val res: String = bdd_addresses.getOrElse(key, key)
      response = response :+ (bdd_lang.getExpressionOrElse(language, 2, key, "")+" : "+res)
    }
    response
  }

  /**
   * Find the informations on the xml database
   * @param s the question
   * @return the answer
   */
  def askXml(s: String): List[String] = {
    val response = xml.search(removeQuestion(s))

    if(response.isEmpty)
      List()
    else if(response.size == 1)
      List(bdd_lang.getExpressionOrElse(language, 2, response(0), "") + ':' + xml.getAddress(response(0)))
    else{
      var list: List[String] = List(bdd_lang.getExpressionOrElse(language, 6, response.size.toString, ""))
      responses = List()
      
      var i = 1
      for(r <- response){
        list = list :+ (i.toString + ") " + r)
        i += 1
        responses = responses :+ (r, r)
      }

      list = list :+ bdd_lang.getExpressionOrElse(language, 7, "", "")

      state = 1

      list
    }
  }

  /**
   * Find the informations on internaute
   * @param s the question
   * @return the answer
   */
  def askInternaute(s: String): List[String] = {
    var search = removeQuestion(s)
    val i = search.indexOf(" ", 1)
    if(i != -1)
      search = search.substring(i+1)
    val response = internaute.search(search)
    
    if(response.size == 0)
      List()
    else if(response.size == 1)
      List(bdd_lang.getExpressionOrElse(language, 2, response(0)._1, "") + ": " + internaute.getAddresse(response(0)._2))
    else{
      var list: List[String] = List()
      
      list = list :+ bdd_lang.getExpressionOrElse(language, 6, response.size.toString, "")
      
      var i = 1
      for((name, add) <- response){
        list = list :+ (i.toString + ") " + name)
        i += 1
      }
      
      responses = response
      
      list = list :+ bdd_lang.getExpressionOrElse(language, 7, "", "")
      
      state = 3
      
      list
    }
        
  }

  def askDefinition(s: String): List[String] = {
    var search = removeQuestion(s)
    var i = search.indexOf(" ", 1)
    if(i != -1) {
      i = search.indexOf(" ", i+1)
      if (i != -1)
        search = search.substring(i+1)
    }

    DictionarySearchImpl.search(search, language) match {
      case None => List(bdd_lang.getExpressionOrElse(language, 10, "", ""))
      case Some(l) => l
    }
  }

  /**
   * Supprime les doublons dans une liste
   * @param s Une liste de réponse
   * @return  La liste de réponse sans doublon
   */
  def removeCopies(s: List[String]): List[String] = {
    var response: List[String] = List()
    for (l <- s) {
      if (!response.contains(l))
        response = response :+ l
    }
    response
  }

  /**
   * Reinitialisation de l'avatar
   */
  def reinit(): Unit = {
    bdd_synonymes.load("doc/synonymes.txt")
    bdd_addresses.load("doc/DonneesInitiales.txt")

    language = "Français"
    state = 0
    responses = List()

    bdd_lang.load("lib/international.txt")
    xml.load("lib/vAr.xml")
  }

  /**
   * Test de l'avatar
   * @param l une liste de requete
   * @return la liste des reponses produites par l'avatar
   */
  def test(l: List[String]): List[String] = {
    var response: List[String] = List()

    for (line <- l)
      response = response ++ ask(line)

    response
  }

}