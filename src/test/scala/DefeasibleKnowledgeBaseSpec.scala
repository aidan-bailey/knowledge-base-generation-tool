package kbgt.test

import org.scalatest.funspec.AnyFunSpec
import org.tweetyproject.logics.pl.syntax._
import kbgt.logic._
import java.io.File
import java.io.PrintWriter

class DefeasibleKnowledgeBaseSpec extends AnyFunSpec {
  describe("The DefeasibleKnowledgeBase can") {
    it("be constructed from a sequence of DefeasibleFormulas") {
      val dkb = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("a"), Atom("b")),
        DefeasibleFormula(Atom("a"), Atom("b"))
      )
      assert(
        dkb.contains(DefeasibleFormula(Atom("a"), Atom("b"))) && dkb.contains(
          DefeasibleFormula(Atom("a"), Atom("b"))
        )
      )
    }
    it("have DefeasibleFormulas added to it") {
      val dkb = new DefeasibleKnowledgeBase()
      dkb.add(DefeasibleFormula(Atom("a"), Atom("b")))
      dkb.addAll(
        Seq(
          DefeasibleFormula(Atom("b"), Atom("c")),
          DefeasibleFormula(Atom("c"), Atom("d"))
        )
      )
      assert(
        dkb.contains(DefeasibleFormula(Atom("a"), Atom("b"))) && dkb.contains(
          DefeasibleFormula(Atom("b"), Atom("c"))
        ) && dkb.contains(
          DefeasibleFormula(Atom("c"), Atom("d"))
        )
      )
    }
    it("have DefeasibleFormulas removed from it") {
      val dkb = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("a"), Atom("b")),
        DefeasibleFormula(Atom("b"), Atom("c"))
      )
      dkb.remove(DefeasibleFormula(Atom("a"), Atom("b")))
      assert(!dkb.contains(DefeasibleFormula(Atom("a"), Atom("b"))))
    }
    it("not contain duplicates of formulas") {
      val dkb = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("a"), Atom("b")),
        DefeasibleFormula(Atom("a"), Atom("b"))
      )
      dkb.add(DefeasibleFormula(Atom("a"), Atom("b")))
      dkb.addAll(
        Seq(
          DefeasibleFormula(Atom("a"), Atom("b")),
          DefeasibleFormula(Atom("a"), Atom("b"))
        )
      )
      assert(dkb.size == 1)
    }
    it("be combined with other DefeasibleKnowledgeBases") {
      val dkb1 =
        new DefeasibleKnowledgeBase(
          DefeasibleFormula(Atom("a"), Atom("b")),
          DefeasibleFormula(Atom("a"), Atom("c"))
        )
      val dkb2 = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("b"), Atom("a")),
        DefeasibleFormula(Atom("b"), Atom("c"))
      )
      val dkb = new DefeasibleKnowledgeBase()
      dkb.addAll(dkb1)
      dkb.addAll(dkb2)
      assert(
        dkb.contains(DefeasibleFormula(Atom("a"), Atom("b"))) && dkb.contains(
          DefeasibleFormula(Atom("a"), Atom("c"))
        ) && dkb.contains(
          DefeasibleFormula(Atom("b"), Atom("a"))
        ) && dkb.contains(DefeasibleFormula(Atom("b"), Atom("c")))
      )
    }
    it("be cleared") {
      val ckb = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("a"), Atom("b")),
        DefeasibleFormula(Atom("b"), Atom("c"))
      )
      ckb.clear()
      assert(
        ckb.size == 0
      )
    }
    it("be cloned") {
      val ckb = new DefeasibleKnowledgeBase(
        DefeasibleFormula(Atom("a"), Atom("b")),
        DefeasibleFormula(Atom("b"), Atom("c"))
      )
      val ckbClone = ckb.clone()
      assert(
        ckbClone.contains(DefeasibleFormula(Atom("a"), Atom("b"))) && ckbClone
          .contains(DefeasibleFormula(Atom("b"), Atom("c")))
      )
      ckbClone.clear()
      assert(ckb.nonEmpty)
    }
  }

}
