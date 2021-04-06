package client

import machine.MachineImpl
import scala.io.StdIn.readLine

object Test extends App {
  
  MachineImpl.reinit()

  while(true){
    val line = readLine()


    println(MachineImpl.ask(line))

  }
}
