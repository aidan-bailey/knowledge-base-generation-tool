package kbgt.logic

import org.tweetyproject.logics.pl.syntax.PlFormula
import org.tweetyproject.logics.pl.sat.Sat4jSolver
import org.tweetyproject.logics.pl.reasoner.SatReasoner
import org.tweetyproject.logics.pl.sat.SatSolver

/** A propositional formula. */
abstract trait Formula {

  SatSolver.setDefaultSolver(new Sat4jSolver())
  protected val solver = new Sat4jSolver()
  protected val reasoner = new SatReasoner()

  /** Gets the formula's TweetyProject counterpart.
    *
    * @return
    *   PlFormula counterpart of the formula
    */
  def getPlFormula(): PlFormula

  /** toString override. */
  override def toString(): String

}
