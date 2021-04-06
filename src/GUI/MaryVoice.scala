package GUI

import marytts.modules.synthesis.Voice

import javax.sound.sampled.AudioInputStream
import marytts.LocalMaryInterface
import marytts.MaryInterface
import marytts.exceptions.MaryConfigurationException
import marytts.exceptions.SynthesisException
import marytts.util.data.audio.AudioPlayer

/**
 * Gestion de la synthèse vocale
 */
object MaryVoice {
  
  protected var marytts: MaryInterface = new LocalMaryInterface()

  /**
   * Initialisation de la synthèse vocale
   */
  def init(): Unit = {
    marytts.setVoice("upmc-pierre-hsmm")
  }

  /**
   * Lance la lecture de la chaîne de caractères par Mary-TTS
   * @param textToSay String La chaîne de caractères à dire
   * @param lang String Le langage oral désiré (fr, en, al, it)
   */
  def say(textToSay: String, lang: String): Unit = {
    if (textToSay == "")
      return
    if (lang.toLowerCase() == "français")
      marytts.setVoice("upmc-pierre-hsmm")
    else if (lang.toLowerCase() == "anglais")
      marytts.setVoice("cmu-slt-hsmm")
    else if (lang.toLowerCase() == "allemand")
      marytts.setVoice("dfki-pavoque-neutral-hsmm")
    else if (lang.toLowerCase() == "italien")
      marytts.setVoice("istc-lucia-hsmm")
    else
      return
    
    val ap: AudioPlayer = new AudioPlayer()
    val audio: AudioInputStream = marytts.generateAudio(textToSay)
    ap.setAudio(audio)
    ap.start()
    ap.join()
  }

}