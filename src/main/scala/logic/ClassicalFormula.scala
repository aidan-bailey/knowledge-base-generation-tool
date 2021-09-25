package kbgt.logic

import kbgt._
import scala.collection.mutable.ListBuffer
import org.tweetyproject.logics.pl.syntax._
import org.tweetyproject.logics.pl.sat._
import org.tweetyproject.logics.pl.reasoner.SatReasoner
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable

/** A classical propositional formula.
  *
  * Extends [[kbgt.logic.Formula]]
  */
sealed abstract trait ClassicalFormula extends Formula {

  /** Checks whether this classical formula entails another classical formula.
    *
    * @param formula
    *   the other classical formula
    * @return
    *   true if this formula entails the other formula, false if otherwise
    */
  def entails(formula: ClassicalFormula) =
    reasoner.query(getPlFormula(), formula.getPlFormula())

}

/** A propositional atom.
  *
  * Extends [[kbgt.logic.ClassicalFormula]]
  * @constructor
  *   create a new atom with a name
  * @param name
  *   the atom name
  */
case class Atom(name: String) extends ClassicalFormula {

  /** Gets the classical formula's TweetyProject counterpart.
    *
    * @return
    *   PlFormula version of formula
    */
  override def getPlFormula() = new Proposition(name)

  /** toString override. */
  override def toString() = name

}

/** A propositional constant.
  *
  * Extends [[kbgt.logic.ClassicalFormula]]
  * @constructor
  *   create a new constant with a constant symbol
  * @param symbol
  *   the constant symbol
  */
case class Const(symbol: Constant.Value) extends ClassicalFormula {

  /** Gets the classical formula's TweetyProject counterpart.
    *
    * @return
    *   PlFormula version of classical formula
    */
  override def getPlFormula() = Constant.getPlFormula(symbol)

  /** toString override. */
  override def toString() = Constant.getNotation(symbol, Notation.Tweety)

}

/** A Binary connective.
  *
  * Extends [[kbgt.logic.ClassicalFormula]]
  * @constructor
  *   create a new binary connective with a binary operator, left classical
  *   formula operand and right classical formula operand
  * @param operator
  *   the binary connective operator
  * @param leftOperand
  *   the left classical formula operand
  * @param rightOperand
  *   the right classical formula operand
  */
case class BinCon(
    operator: BinOp.Value,
    leftOperand: ClassicalFormula,
    rightOperand: ClassicalFormula
) extends ClassicalFormula {

  /** Gets the classical formula's TweetyProject counterpart.
    *
    * @return
    *   PlFormula counterpart of the binary connective
    */
  override def getPlFormula() =
    BinOp.getPlFormula(operator, leftOperand, rightOperand)

  /** toString override. */
  override def toString() =
    s"(${leftOperand + BinOp.getNotation(operator, Notation.Tweety) + rightOperand})"

}

/** A propositional unary connective.
  *
  * Extends [[kbgt.logic.ClassicalFormula]]
  * @constructor
  *   create a new unary connective with a unary operator and classical formula
  *   operand
  * @param operator
  *   the binary connective operator
  * @param operand
  *   the classical formula operand
  */
case class UnCon(operator: UnOp.Value, operand: ClassicalFormula)
    extends ClassicalFormula {

  /** Gets the classical formula's TweetyProject counterpart.
    *
    * @return
    *   PlFormula counterpart of the classical formula
    */
  override def getPlFormula() = UnOp.getPlFormula(operator, operand)

  /** toString override. */
  override def toString() =
    s"(${UnOp.getNotation(operator, Notation.Tweety) + operand})"

}
