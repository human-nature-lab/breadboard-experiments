pairingStep = stepFactory.createStep()

pairingStep.run = {
  println "pairingStep.run"
  curStep = "pairing"
  def proposer = null
  def responder = null
  g.V.shuffle.each { v-> 
    if (proposer == null) {
      v.proposer = true
      proposer = v
    } else {
      v.responder = true
      responder = v
      g.addEdge(proposer, responder, "connected")
      proposer = null
      responder = null
    }
  }
  if (proposer != null) {
    println("unpaired proposer")
    proposer.dropped = true
  }
  
}

pairingStep.done = {
  println "pairingStep.done"
  proposalStep.run()
}
