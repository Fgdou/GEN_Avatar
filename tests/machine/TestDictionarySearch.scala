package machine

import org.junit.Test
import org.junit.Assert._

class TestDictionarySearch {

  @Test
  def testGet(): Unit = {
    assertEquals(machine.DictionarySearchImpl.get("https://api.dictionaryapi.dev/api/v2/entries/fr/docteur"), "[{\"word\":\"docteur\",\"phonetics\":[{}],\"meanings\":[{\"partOfSpeech\":\"nom masculin\",\"definitions\":[{\"antonyms\":[],\"definition\":\"Celui qui enseignait des points de doctrine.\",\"synonyms\":[],\"example\":\"Les docteurs de la Loi (dans le judaïsme).\"}]},{\"partOfSpeech\":\"nom\",\"definitions\":[{\"antonyms\":[],\"definition\":\"Personne promue au plus haut grade universitaire d'une faculté (doctorat).\",\"synonyms\":[],\"example\":\"Docteur ès lettres.\"},{\"antonyms\":[],\"definition\":\"Personne qui possède le titre de docteur en médecine et qui exerce la médecine ou la chirurgie.\",\"synonyms\":[\"médecin\",\"toubib\"],\"example\":\"Aller chez le docteur.\"}]}]}]")
    assertEquals(machine.DictionarySearchImpl.get("https://api.dictionaryapi.dev/api/v2/entries/en_US/word"), "[{\"word\":\"word\",\"phonetics\":[{\"text\":\"/wərd/\",\"audio\":\"https://lex-audio.useremarkable.com/mp3/word_us_1.mp3\"}],\"meanings\":[{\"partOfSpeech\":\"transitive verb\",\"definitions\":[{\"definition\":\"Choose and use particular words in order to say or write (something)\",\"synonyms\":[\"phrase\",\"express\",\"put\",\"couch\",\"frame\",\"set forth\",\"formulate\",\"style\"],\"example\":\"he words his request in a particularly ironic way\"}]},{\"partOfSpeech\":\"noun\",\"definitions\":[{\"definition\":\"A single distinct meaningful element of speech or writing, used with others (or sometimes alone) to form a sentence and typically shown with a space on either side when written or printed.\",\"synonyms\":[\"term\",\"name\",\"expression\",\"designation\",\"locution\"],\"example\":\"I don't like the word “unofficial”\"},{\"definition\":\"A command, password, or signal.\",\"synonyms\":[\"instruction\",\"order\",\"command\"]},{\"definition\":\"One's account of the truth, especially when it differs from that of another person.\"},{\"definition\":\"The text or spoken part of a play, opera, or other performed piece; a script.\",\"synonyms\":[\"script\",\"text\"]},{\"definition\":\"A basic unit of data in a computer, typically 16 or 32 bits long.\"}]},{\"partOfSpeech\":\"exclamation\",\"definitions\":[{\"definition\":\"Used to express agreement.\"}]}]}]")
}

  @Test
  def testSearchNotThere(): Unit = {
    assertEquals(machine.DictionarySearchImpl.search("word", "français"), None)
    assertEquals(machine.DictionarySearchImpl.search("word", "espagnol"), None)
    assertEquals(machine.DictionarySearchImpl.search("word", "italien"), None)
    assertEquals(machine.DictionarySearchImpl.search("word", "allemand"), None)
    assertEquals(machine.DictionarySearchImpl.search("taz", "anglais"), None)
  }

  @Test
  def testSearch(): Unit = {
    assertEquals(machine.DictionarySearchImpl.search("word", "anglais"), Some(List("1) Choose and use particular words in order to say or write (something)", "2) A single distinct meaningful element of speech or writing, used with others (or sometimes alone) to form a sentence and typically shown with a space on either side when written or printed.", "3) A command, password, or signal.", "4) One's account of the truth, especially when it differs from that of another person.", "5) The text or spoken part of a play, opera, or other performed piece; a script.", "6) A basic unit of data in a computer, typically 16 or 32 bits long.", "7) Used to express agreement.")))
    assertEquals(machine.DictionarySearchImpl.search("palabra", "espagnol"), Some(List("1) Unidad léxica constituida por un sonido o conjunto de sonidos articulados que tienen un significado fijo y una categoría gramatical.", "2) Representación gráfica de estos sonidos, que consiste en una letra o un grupo de letras delimitado por espacios blancos.", "3) Capacidad de expresar el pensamiento por medio del lenguaje articulado.", "4) Promesa de que una cosa es verdad o de que se va a hacer lo que se dice.", "5) Afirmación de una persona que no se basa en ninguna prueba.", "6) Derecho o turno para hablar en una reunión o asamblea.", "7) Conjunto ordenado de caracteres que constituye la unidad normal en que la información puede ser almacenada, transmitida o manejada en una computadora.", "8) Expresión poco sincera o vacía de contenido.", "9) Discurso oral o escrito de una persona.")))
    assertEquals(machine.DictionarySearchImpl.search("presidente", "italien"), Some(List("La persona che, nominata elettivamente o per investitura dall'alto, dirige, con o senza poteri di rappresentanza, l'attività di un organo collegiale: il p. del comitato; il p. del consiglio di amministrazione; il p. del tribunale, della Corte dei conti.")))
    assertEquals(machine.DictionarySearchImpl.search("Wort", "allemand"), Some(List("1) kleinste selbstständige sprachliche Einheit von Lautung (2) und Inhalt (2a) bzw. Bedeutung", "2) Wort (1a) in speziellem Hinblick auf seinen bestimmten Inhalt, Sinn; Ausdruck, Begriff", "3) etwas, was jemand als Ausdruck seiner Gedanken, Gefühle o. Ä. zusammenhängend äußert; Äußerung", "4) Ausspruch", "5) Text, besonders Liedtext", "6) förmliches Versprechen; Versicherung", "7) Kanon, Sammlung heiliger Schriften, besonders die darin enthaltene Glaubenslehre", "8) Logos (4)")))
    assertEquals(machine.DictionarySearchImpl.search("Mot", "français"), Some(List("1) Chacun des sons ou groupes de sons (de lettres ou groupes de lettres) correspondant à un sens isolable spontanément, dans le langage ; (par écrit) suite ininterrompue de lettres, entre deux blancs.", "2) Élément du lexique, en tant que signe (opposé à pensée, à réalité…).", "3) Phrase, parole.", "4) Court message.", "5) Parole, énoncé, phrase exprimant une pensée de façon concise et frappante.", "6) Séquence de caractères traités comme un seul bloc.", "7) en apposition et qui interpelle (ex. Jean dans Jean, tais-toi !).")))
  }

  @Test
  def addNumberList(): Unit = {
    assertEquals(machine.DictionarySearchImpl.addNumberList(List()), List())
    assertEquals(machine.DictionarySearchImpl.addNumberList(List("tasmanie")), List("tasmanie"))
    assertEquals(machine.DictionarySearchImpl.addNumberList(List("taz", "tasmanie")), List("1) taz","2) tasmanie"))
    assertEquals(machine.DictionarySearchImpl.addNumberList(List("un", "deux", "trois")), List("1) un", "2) deux", "3) trois"))
  }
}