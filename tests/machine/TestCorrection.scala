package machine

import org.junit.Test
import org.junit.Assert._

class TestCorrection {
  @Test
  def trouverMotsClefs(): Unit ={
    assertEquals(List(), CorrectionImpl.trouverMotsCles("", List()))
    assertEquals(List(), CorrectionImpl.trouverMotsCles("text", List()))
    assertEquals(List(), CorrectionImpl.trouverMotsCles("", List("aaaa")))
    assertEquals(List("aaaa"), CorrectionImpl.trouverMotsCles("aaaa", List("aaaa")))
    assertEquals(List("aaaa"), CorrectionImpl.trouverMotsCles("test aaaa test", List("aaaa")))
    assertEquals(List("aaaa"), CorrectionImpl.trouverMotsCles("aaaa", List("aaaa", "bbbb")))
    assertEquals(List("aaaa"), CorrectionImpl.trouverMotsCles("test aaaa test", List("aaaa", "bbbb")))
    assertEquals(List("aaaa", "bbbb"), CorrectionImpl.trouverMotsCles("aaaa bbbb", List("aaaa", "bbbb")))
    assertEquals(List("aaaa", "bbbb"), CorrectionImpl.trouverMotsCles("test aaaa bbbb test", List("aaaa", "bbbb")))
    assertEquals(List("aaaa", "bbbb aaaa"), CorrectionImpl.trouverMotsCles("bbbb aaaa", List("aaaa", "bbbb aaaa")))
    assertEquals(List("aaaa", "bbbb aaaa"), CorrectionImpl.trouverMotsCles("test bbbb aaaa test", List("aaaa", "bbbb aaaa")))
    assertEquals(List("aaaa", "bbbb aaaa"), CorrectionImpl.trouverMotsCles("test abbb abaa test", List("aaaa", "bbbb aaaa")))
    assertEquals(List(), CorrectionImpl.trouverMotsCles("test bbbb testaaaa test", List("aaaa", "bbbb aaaa")))

    assertEquals(List("hotel de ville"), CorrectionImpl.trouverMotsCles("hotel de ville", List("hotel de ville")))
    assertEquals(List("hotel de ville"), CorrectionImpl.trouverMotsCles("hotl de ville", List("hotel de ville")))
    assertEquals(List("hotel de ville"), CorrectionImpl.trouverMotsCles("hotl de valle", List("hotel de ville")))
  }

  @Test
  def hamming(): Unit ={
    assertEquals(false, CorrectionImpl.hamming("", ""))
    assertEquals(false, CorrectionImpl.hamming("", "a"))
    assertEquals(true, CorrectionImpl.hamming("a", "a"))
    assertEquals(false, CorrectionImpl.hamming("a", "b"))
    assertEquals(false, CorrectionImpl.hamming("a", ""))

    assertEquals(true, CorrectionImpl.hamming("aaaaa", "abaaa"))
    assertEquals(true, CorrectionImpl.hamming("aaaaa", "aaaaa"))
    assertEquals(true, CorrectionImpl.hamming("abaaa", "aaaaa"))
    assertEquals(true, CorrectionImpl.hamming("aaaa", "aaaaa"))
    assertEquals(false, CorrectionImpl.hamming("aaaaa", "abcaa"))
    assertEquals(false, CorrectionImpl.hamming("aaaaa", "abaa"))
    assertEquals(false, CorrectionImpl.hamming("aba", " aaaa"))
    assertEquals(false, CorrectionImpl.hamming("aaaa", "aaaaav"))

    assertEquals(false, CorrectionImpl.hamming("tu", "TNB"))
    assertEquals(false, CorrectionImpl.hamming("t", "TNB"))
    assertEquals(true, CorrectionImpl.hamming("tnu", "TNB"))
    assertEquals(false, CorrectionImpl.hamming("tn", "TNB"))
    assertEquals(false, CorrectionImpl.hamming("TNB", "tu"))
    assertEquals(false, CorrectionImpl.hamming("TNB", "t"))
    assertEquals(true, CorrectionImpl.hamming("TNB", "tnu"))
    assertEquals(false, CorrectionImpl.hamming("TNB", "tn"))

    assertEquals(false, CorrectionImpl.hamming("renee", "rennes"))

  }

  @Test
  def accents(): Unit ={
    assertEquals("", CorrectionImpl.toLowerCase(""))
    assertEquals("salut", CorrectionImpl.toLowerCase("sAlut"))
    assertEquals("ello", CorrectionImpl.toLowerCase("Éllo"))
    assertEquals("ullo", CorrectionImpl.toLowerCase("ùllo"))
  }
}
