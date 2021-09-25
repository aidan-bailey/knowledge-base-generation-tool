package kbgt.logic

import kbgt._
import scala.collection.mutable.ListBuffer
import org.tweetyproject.logics.pl.syntax._
import org.tweetyproject.logics.pl.sat._
import org.tweetyproject.logics.pl.reasoner.SatReasoner
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable
import java.util.ArrayList
import scala.io.Source

/** A ranked knowledge base.
  *
  * @constructor
  *   create a new ranked knowledge base with a listbuffer of defeasible ranks
  *   and a mixed knowledge base infinite rank.
  * @param dRanks
  *   the defeasible ranks
  * @param iRanks
  *   the infinite rank
  */
class RankedKnowledgeBase(
    dRanks: ListBuffer[MixedKnowledgeBase],
    iRank: MixedKnowledgeBase
) {

  def this() = this(ListBuffer(), new MixedKnowledgeBase())

  /** Gets the defeasible ranks.
    *
    * @return
    *   the defeasible ranks
    */
  def defeasibleRanks(): ListBuffer[MixedKnowledgeBase] = dRanks

  /** Gets the infinite rank.
    *
    * @return
    *   the infinite rank
    */
  def infiniteRank(): MixedKnowledgeBase = iRank

  /** Replaces the current ranked knowledge base with a new one.
    *
    * @param rankedKB
    *   the new ranked knowledge base
    */
  def replace(rankedKB: RankedKnowledgeBase): this.type = {
    dRanks.clear()
    dRanks.addAll(rankedKB.defeasibleRanks().clone())
    iRank.clear()
    iRank ++= rankedKB.infiniteRank().clone()
    this
  }

  /** Clear the ranked knowledge base. */
  def clear(): Unit = {
    dRanks.clear()
    iRank.clear()
  }

  /** Gets the defeasible rank count.
    *
    * @return
    *   the defeasible rank count
    */
  def rankCount() = dRanks.size

  /** Gets the mixed knowledge base.
    *
    * @return
    *   the mixed knowledge base
    */
  def getMixedKnowledgeBase(): MixedKnowledgeBase =
    dRanks
      .foldLeft(new MixedKnowledgeBase())((mkb, f) => mkb ++= f)
      .addAll(iRank)

  /** toString override. */
  override def toString(): String = {
    var result = StringBuilder.newBuilder
    result.addAll(
      "---------------------------------------------------------------\n"
    )
    result.addAll(" RANK | STATES\n")
    result.addAll(
      "---------------------------------------------------------------"
    )
    for (rankIndex <- 0 to dRanks.size) {
      result.addOne('\n')
      result.addAll(
        if (dRanks.size == rankIndex) "     ∞|"
        else f"$rankIndex%6s|"
      )
      val states =
        if (dRanks.size != rankIndex) dRanks(rankIndex).toList else iRank.toList
      var buffer = ""
      for (stateIndex <- 0 to states.size - 1) {
        val stateStr = states(stateIndex).toString()
        if (buffer.size + 1 + stateStr.size > 55) {
          result.addAll(buffer + "\n")
          buffer = ""
          result.addAll("      |")
        }
        buffer = buffer + ' ' + stateStr
      }
      result.addAll(buffer)
      result.addAll(
        "\n---------------------------------------------------------------"
      )
    }
    return result.toString()
  }

  /** equals method. */
  def equals(x: RankedKnowledgeBase): Boolean =
    x.defeasibleRanks
      .equals(defeasibleRanks()) && x.infiniteRank().equals(infiniteRank())

}
