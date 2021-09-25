package kbgt.test

import org.scalatest.funspec.AnyFunSpec
import org.tweetyproject.logics.pl.syntax._
import kbgt.logic._
import java.io.File
import java.io.PrintWriter
import scala.collection.mutable.ListBuffer

class RankedKnowledgeBaseSpec extends AnyFunSpec {
  describe("The RankedKnowledgeBase can") {
    it("can be replaced") {
      val rkb = new RankedKnowledgeBase(
        ListBuffer[MixedKnowledgeBase](
          new MixedKnowledgeBase(DefeasibleFormula(Atom("a"), Atom("b")))
        ),
        new MixedKnowledgeBase(Atom("a"), Atom("b"))
      )
      val rkb2 = new RankedKnowledgeBase(
        ListBuffer[MixedKnowledgeBase](
          new MixedKnowledgeBase(DefeasibleFormula(Atom("p"), Atom("q")))
        ),
        new MixedKnowledgeBase(Atom("z"), Atom("e"))
      )
      rkb.replace(rkb2)
      assert(rkb.equals(rkb2))
    }
    it("can be cleared") {
      val rkb = new RankedKnowledgeBase(
        ListBuffer[MixedKnowledgeBase](
          new MixedKnowledgeBase(DefeasibleFormula(Atom("a"), Atom("b")))
        ),
        new MixedKnowledgeBase(Atom("a"), Atom("b"))
      )
      rkb.clear()
      assert(rkb.defeasibleRanks().isEmpty && rkb.infiniteRank().isEmpty)
    }
  }
}
