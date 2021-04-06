package machine
import org.junit.Test
import org.junit.Assert._

class TestLanguageImp {
  
   val language_bdd: Language = new LanguageImp
   language_bdd.load("lib/international.txt")
  
  @Test
  def testGetWordOrElse(): Unit ={
    /*
     * Test pour getWordsOrElse
     */
    
    //Test Not Found
    assertEquals(List("Not Found"), language_bdd.getWordsOrElse("Test", "Français", "Not Found"))
    assertEquals(List("Not Found"), language_bdd.getWordsOrElse("Recherche", "Test", "Not Found"))
    
    //Test Politesse
    assertEquals(List("hi","hello","morning","evening","afternoon","hey"), language_bdd.getWordsOrElse("Politesse", "Anglais", "Not Found"))
    assertEquals(List("buongiorno","ciao","salve","buon","pomeriggio","buonasera","incantato"), language_bdd.getWordsOrElse("Politesse", "Italien", "Not Found"))
    
    
    //Test Recherche
    assertEquals(List("recherche","cherche","ou","est","donc","trouve","trouver"), language_bdd.getWordsOrElse("Recherche", "Français", "Not Found"))
    assertEquals(List("wo","ist","suche","suchen"), language_bdd.getWordsOrElse("Recherche", "Allemand", "Not Found"))
    
    //Test Nom de langue
    assertEquals(List("español"), language_bdd.getWordsOrElse("Nom de langue", "Espagnol", "Not Found"))
    assertEquals(List("english"), language_bdd.getWordsOrElse("Nom de langue", "Anglais", "Not Found"))  
    
  }
  
   
   
   /*
  * Test getExpressionOrElse
  */
   
   
   
  @Test
  def testGetExpressionOrElseFrancais(): Unit={    
    /*
     * Français
     */
    assertEquals("oui", language_bdd.getExpressionOrElse("Français", 0, "pas besoin ici", "elseword"))
    assertEquals("L'adresse de la gare est", language_bdd.getExpressionOrElse("Français", 2, "la gare", "elseword"))   
  }
  
  @Test
  def testGetExpressionOrElseElseWord(): Unit={
    /*
     * elseword
     */
    assertEquals("elseword", language_bdd.getExpressionOrElse("mmmmmhhh", 2, "pas besoin ici", "elseword"))   
    assertEquals("elseword", language_bdd.getExpressionOrElse("", 2, "pas besoin ici", "elseword"))   
    assertEquals("elseword", language_bdd.getExpressionOrElse("Anglais", 14, "pas besoin ici", "elseword"))
  }
  
  @Test
  def testGetExpressionOrElseItalien(): Unit={
    /*
     * Italien
     */
    assertEquals("no", language_bdd.getExpressionOrElse("Italien", 1, "pas besoin ici", "elseword"))
    assertEquals("ristorante, creperie, pizzeria", language_bdd.getExpressionOrElse("Italien", 8, "pas besoin ici", "elseword")) //derniere ligne du fichier international.txt  
  }
  
    @Test
  def testGetExpressionOrElseAnglais(): Unit={    
    /*
     * Anglais
     */
    assertEquals("yes", language_bdd.getExpressionOrElse("Anglais", 0, "pas besoin ici", "elseword"))
    assertEquals("The address of the dog is", language_bdd.getExpressionOrElse("Anglais", 2, "the dog", "elseword"))   
  }
    
    @Test
  def testGetExpressionOrElseEspagnol(): Unit={    
    /*
     * Espagnol
     */
    assertEquals("si", language_bdd.getExpressionOrElse("Espagnol", 0, "pas besoin ici", "elseword"))
    assertEquals("Tengo 95685144 opciones", language_bdd.getExpressionOrElse("Espagnol", 6, "95685144", "elseword"))   
  }
    
    @Test
  def testGetExpressionOrElseAllemand(): Unit={    
    /*
     * Allemand
     */
    assertEquals("ja", language_bdd.getExpressionOrElse("Allemand", 0, "pas besoin ici", "elseword"))
    assertEquals("Ich habe Angela Merkel Antworten", language_bdd.getExpressionOrElse("Allemand", 6, "Angela Merkel", "elseword"))   
  }  
}