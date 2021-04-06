package machine

import org.junit.Test
import org.junit.Assert._

class TestBaseDeDonnee {
  @Test
  def bdd(): Unit ={
    val bdd: BaseDeDonnee = new BaseDeDonneeImp

    assertEquals("Database not loaded", bdd.getOrElse("A", "Not found"))

    bdd.load("doc/DonneesTest.txt")

    assertEquals("Not found", bdd.getOrElse("e", "Not found"))
    assertEquals("B", bdd.getOrElse("a", "Not found"))
    assertEquals("B", bdd.getOrElse("A", "Not found"))
    assertEquals("C", bdd.getOrElse("b", "Not found"))
    assertEquals("C", bdd.getOrElse("B", "Not found"))
    assertEquals("A", bdd.getOrElse("c", "Not found"))
    assertEquals("BC", bdd.getOrElse("ab", "Not found"))
    assertEquals("BC", bdd.getOrElse("aB", "Not found"))

    assertEquals(List("A", "B", "C", "AB"), bdd.getNoms())
  }
}
