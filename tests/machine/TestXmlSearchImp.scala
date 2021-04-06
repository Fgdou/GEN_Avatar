package machine

import org.junit.Test
import org.junit.Assert._

class TestXmlSearchImp {
  
  
  //tests avant initialisation des données
  @Test
  def firstTest():Unit={
    assertEquals("notFound", xml.getAddress("A"))
    assertEquals("notFound", xml.getAddress("2ème essai"))
    assertEquals(List(),xml.search("12345"))
    assertEquals(List(),xml.search("4ème essai"))
  }
  
  
  //initialisation des données 
  val xml:XmlSearch = new XmlSearchImp
  xml.load("lib/vAr.xml")
  
  
  //tests après initialisation des données
  @Test
  def testSearch():Unit={
    assertEquals(xml.search("piscine"),List("Piscine Gayeulles","Piscine Saint-Georges","Piscine Villejean","Piscine Bréquigny"))
    assertEquals(xml.search("gare"),List("CARL - Cabinet médical de garde", "Gare SNCF", "Pharmacien de garde", "Dentiste de garde", "Centre de référence national maladie rare Spina Bifida-CHU", "Association gré à gré", "Plombier de garde 24h/24"))
    assertEquals(xml.search("sncf"),List("Service Fret - SNCF", "Gare SNCF"))
    assertEquals(xml.search("Gare SNCF"),List("Gare SNCF"))
    assertEquals(xml.search("Mairie de Rennes"),List("Mairie de Rennes"))
    assertEquals(xml.search("nouveau test"),List())
    assertEquals(xml.search(""),List())
  }
  
  @Test
  def testGetAdress():Unit={
    assertEquals(xml.getAddress("Gare SNCF"),"19,Place de la Gare")
    assertEquals(xml.getAddress("Mairie de Rennes"),"Place de la Mairie")
    assertEquals(xml.getAddress("Piscine Villejean"),"1,SQUARE D'ALSACE")
    assertEquals(xml.getAddress("Piscine Bréquigny"),"10,Boulevard Albert 1er")
    assertEquals(xml.getAddress("Tour Eiffel"),"notFound")
    assertEquals(xml.getAddress("Mont Saint-Michel"),"notFound")
    assertEquals(xml.getAddress(""),"notFound")
  }
  
  
}