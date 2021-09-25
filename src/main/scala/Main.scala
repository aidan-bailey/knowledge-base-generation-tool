import kbgt.logic._
import kbgt.generation._
import kbgt._
import scopt._
import scala.collection._
import org.tweetyproject.logics.pl.parser.PlParser
import org.tweetyproject.logics.pl.syntax._
import org.tweetyproject.commons.ParserException
import java.io.File
import java.io.PrintWriter
import java.io.FileNotFoundException

object Main extends App {
  val builder = OParser.builder[Config]
  val parser1 = {
    import builder._
    OParser.sequence(
      programName("kbgt"),
      head("kbgt", "1.0"),
      opt[Int]('r', "rankCount")
        .action((x, c) => c.copy(rankCount = x))
        .validate(x =>
          if (x > 0) success
          else failure("Value <ranks> must be >0")
        )
        .required()
        .text("total number of defeasible ranks"),
      opt[Int]('s', "stateCount")
        .action((x, c) => c.copy(stateCount = x))
        .validate(x =>
          if (x > 0) success
          else failure("Value <states> must be >0")
        )
        .required()
        .text("total number of defeasible statements"),
      opt[String]('d', "distribution")
        .valueName("<opt>")
        .action((x, c) =>
          x match {
            case "uniform" =>
              c.copy(distributionFunction = DistributionFunction.Uniform)
            case "exponential" =>
              c.copy(distributionFunction = DistributionFunction.Exponential)
            case "normal" =>
              c.copy(distributionFunction = DistributionFunction.Normal)
            case "inverted-exponential" =>
              c.copy(distributionFunction =
                DistributionFunction.InvertedExponential
              )
            case "inverted-normal" =>
              c.copy(distributionFunction = DistributionFunction.InvertedNormal)
          }
        )
        .text(
          "distribution function {uniform, exponential, normal, inverted-exponential, inverted-normal}"
        ),
      opt[Unit]("defeasibleOnly")
        .action((_, c) => c.copy(defeasibleOnly = true))
        .text("generate only defeasible statements"),
      opt[String]('o', "out")
        .valueName("<filename>")
        .action((x, c) => c.copy(filename = x))
        .required()
        .text("output file name"),
      help("help").text("prints this usage text"),
      note("\n*First elements in option sets are defaults")
    )
  }

  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
      var kb = new MixedKnowledgeBase
      println("Generating knowledge base...")
      kb = KBGenerator.GenerateRanked(
        config.rankCount,
        config.stateCount,
        config.distributionFunction,
        config.defeasibleOnly
      )
      println("Knowledge base generation complete.")
      println("Writing to file...")
      kb.writeFile(config.filename + ".txt")
      println(s"Knowledge base written to ${config.filename + ".txt"}.")
    case _ =>
  }
}
