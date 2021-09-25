package kbgt.generation

import kbgt.logic._
import kbgt._
import scala.collection._
import java.io._
import scala.util.Random

/** A collection of knowledge base generation methods. */
object KBGenerator {

  def GenerateRanked(
      r: Int,
      s: Int,
      d: (Int, Int, Int) => Int,
      defeasibleOnly: Boolean
  ): MixedKnowledgeBase = {
    val k = new MixedKnowledgeBase()
    var lowerBound = 1
    if (defeasibleOnly)
      lowerBound = lowerBound + 1
    val contradictionAtom = Atom("B")
    var j = 0
    while (j < r) {
      val anchorAtom = Atom(s"a$j")
      if (j == 0) {
        k += DefeasibleFormula(anchorAtom, contradictionAtom)
        if (defeasibleOnly) {
          var paddingAtom = Atom("D")
          k += DefeasibleFormula(paddingAtom, anchorAtom)
        }
      } else {
        val prevAnchor = Atom(s"a${j - 1}")
        if (defeasibleOnly)
          k += DefeasibleFormula(anchorAtom, prevAnchor)
        else
          k += BinCon(BinOp.Implies, anchorAtom, prevAnchor)
        if ((j + 1) % 2 == 0)
          k += DefeasibleFormula(anchorAtom, UnCon(UnOp.Not, contradictionAtom))
        else
          k += DefeasibleFormula(anchorAtom, contradictionAtom)
      }
      var i = 0
      while (i < d(s, r, j + 1) - lowerBound) {
        val extensionAtom = Atom(s"y$j.$i")
        k += DefeasibleFormula(extensionAtom, anchorAtom)
        i += 1
      }
      j += 1
    }
    return k
  }

}
