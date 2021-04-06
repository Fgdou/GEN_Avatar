package machine

trait XmlSearch {
  def load(path: String): Unit
  def search(keywords: String): List[String]
  def getAddress(name: String): String
}
