package machine
import org.junit.Test
import org.junit.Assert._

class TestIntegration {

  // initialisation des objets sous test
  val m= MachineImpl
  m.reinit

  @Test
  def test {
    assertEquals(List("Je ne comprends pas votre demande"), m.ask(""))
    assertEquals(List("bonjour"), m.ask("bonjour"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("Mairie de Rennes"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("Je cherche la Mairie de rennes"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("et la mairie ?"))
    assertEquals(List("Je ne comprends pas votre demande"), m.ask("Je_cherche"))
  }

  @Test
  def testSujet(): Unit ={
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("Je cherche la Mairie"))
    assertEquals(List("L'adresse de Gare SNCF est : 19, Place de la Gare"), m.ask("Et la Gare?"))
    assertEquals(List("Je ne comprends pas votre demande"), m.ask("je cherche"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("l'hotel de ville"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("hotl de valle"))
    assertEquals(List("L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("hotl de ville"))
    assertEquals(List("Je ne comprends pas votre demande"), m.ask("l'hot de ville"))

    assertEquals(List("bonjour", "L'adresse de Mairie de Rennes est : Place de la Mairie"), m.ask("Bonjour, où se trouve la Mairie?"))
    assertEquals(List("bonjour", "L'adresse de Mairie de Rennes est : Place de la Mairie", "L'adresse de Gare SNCF est : 19, Place de la Gare"), m.ask("Bonjour, où se trouve la Mairie? Et la gare ?"))
    assertEquals(List("bonjour"), m.ask("bonjour"))
    assertEquals(List("bonjour", "Je ne comprends pas votre demande"), m.ask("Bonjour, comment tu t'appelles?"))
  }

  @Test
  def testRemoveQuestion(): Unit ={
    assertEquals("", m.removeQuestion(""))
    assertEquals("la mairie", m.removeQuestion("la mairie"))
    assertEquals("mairie", m.removeQuestion("je cherche la mairie"))
    assertEquals("mairie  ", m.removeQuestion("Où se trouve la mairie ?"))
  }

  @Test
  def testCheckBonjour(): Unit = {
    assertEquals(false, m.checkBonjour(""))
    assertEquals(false, m.checkBonjour("je cherche la mairie"))
    assertEquals(false, m.checkBonjour("mairie"))
    assertEquals(true, m.checkBonjour("bonjour"))
    assertEquals(true, m.checkBonjour("bonjour je cherche"))
    assertEquals(true, m.checkBonjour("bonjour, je cherche la mairie"))
  }

  @Test
  def testLang(): Unit ={
    m.reinit()
    assertEquals(List("bonjour"), m.ask("Bonjour"))
    assertEquals(List("Do you speak english?"), m.ask("Hello"))
    assertEquals(List("OK, what is your query?"), m.ask("yes"))
    assertEquals(List("Parlez-vous français?"), m.ask("Je cherche la mairie"))
    m.ask("Bonjourno")
    assertEquals(List("Parlez-vous français?"), m.ask("francais"))
    assertEquals(List("D'accord, quelle est votre demande?"), m.ask("oui"))
  }
}