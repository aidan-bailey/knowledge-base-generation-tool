package kbgt

import kbgt.generation.DistributionFunction

case class Config(
    rankCount: Int = -1,
    stateCount: Int = -1,
    filename: String = "",
    defeasibleOnly: Boolean = false,
    distributionFunction: (Int, Int, Int) => Int = DistributionFunction.Uniform
)
