package machine

trait MachineDialogue {
  /** Envoi d'une requete a la machine et recuperation de sa reponse
   *  @param s la requete
   *  @return la liste de reponses
   */
  def ask(s:String):List[String]
  
  // Pour la partie test par le client
  /**
   * Reinitialisation de l'avatar
   */
  def reinit()
  
  /**
   * Test de l'avatar
   * @param l une liste de requetes
   * @return la liste des reponses produites par l'avatar
   */
	def test(l:List[String]):List[String]
  
}