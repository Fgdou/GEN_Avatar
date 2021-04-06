package machine

import org.junit.Test
import org.junit.Assert._

class TestInternaute{
  
  val i = new InternauteSearchImp
  
  @Test
  def testInternaute(): Unit = {
    assertEquals(("", 0), i.getBetweenSpan("", 0))
    assertEquals(("", 0), i.getBetweenSpan("fqzfqzfqzfqzf", 0))
    assertEquals(("", 14), i.getBetweenSpan("<span ></span>", 0))
    assertEquals(("", 27), i.getBetweenSpan("sah oiut oiu <span ></span> fzqf zfqzff", 0))
    assertEquals(("hi", 25), i.getBetweenSpan("fq zf qz <span >hi</span>fqz qzf zq ", 0))
  }
}