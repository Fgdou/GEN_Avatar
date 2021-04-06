package machine

trait BaseDeDonnee {
  /**
   * Load the database file into the class
   * @param path    The file relative from the root folder of the project
   */
  def load(path: String): Unit

  /**
   * @param s         The key to search
   * @param notFound  The value if not found
   * @return          The value searched of the notFound
   */
  def getOrElse(s : String, notFound: String): String

  /**
   * Get all the keys
   * @return
   */
  def getNoms():List[String]
}
