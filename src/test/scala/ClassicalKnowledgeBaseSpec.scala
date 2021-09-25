package kbgt.test

import org.scalatest.funspec.AnyFunSpec
import org.tweetyproject.logics.pl.syntax._
import kbgt.logic._
import java.io.File
import java.io.PrintWriter

class ClassicalKnowledgeBaseSpec extends AnyFunSpec {
  describe("The ClassicalKnowledgeBase can") {
    it("be constructed from a sequence of ClassicalFormulas") {
      val ckb = new ClassicalKnowledgeBase(Atom("a"), Atom("b"))
      assert(ckb.contains(Atom("a")) && ckb.contains(Atom("b")))
    }
    it("have ClassicalFormulas added to it") {
      val ckb = new ClassicalKnowledgeBase()
      ckb.add(Atom("a"))
      ckb.addAll(Seq(Atom("b"), Atom("c")))
      assert(
        ckb.contains(Atom("a")) && ckb.contains(Atom("b")) && ckb.contains(
          Atom("c")
        )
      )
    }
    it("have ClassicalFormulas removed from it") {
      val ckb = new ClassicalKnowledgeBase(Atom("a"), Atom("b"))
      ckb.remove(Atom("a"))
      assert(!ckb.contains(Atom("a")))
    }
    it("not contain duplicates of formulas") {
      val ckb = new ClassicalKnowledgeBase(Atom("a"), Atom("a"))
      ckb.add(Atom("a"))
      ckb.addAll(Seq(Atom("a"), Atom("a")))
      assert(ckb.size == 1)
    }
    it("be combined with other ClassicalKnowledgeBases") {
      val ckb1 = new ClassicalKnowledgeBase(Atom("a"), Atom("b"))
      val ckb2 = new ClassicalKnowledgeBase(Atom("c"), Atom("d"))
      val ckb = new ClassicalKnowledgeBase()
      ckb.addAll(ckb1)
      ckb.addAll(ckb2)
      assert(
        ckb.contains(Atom("a")) && ckb.contains(Atom("b")) && ckb.contains(
          Atom("c")
        ) && ckb.contains(Atom("d"))
      )
    }
    it("be cleared") {
      val ckb = new ClassicalKnowledgeBase(Atom("a"), Atom("b"))
      ckb.clear()
      assert(
        ckb.size == 0
      )
    }
    it("be cloned") {
      val ckb = new ClassicalKnowledgeBase(Atom("a"), Atom("b"))
      val ckbClone = ckb.clone()
      assert(ckbClone.contains(Atom("a")) && ckbClone.contains(Atom("b")))
      ckbClone.clear()
      assert(ckb.nonEmpty)
    }
  }
}
