resultsStep = stepFactory.createStep()

resultsStep.run = {
  println "resultsStep.run: ${curRound}"
  g.V.filter{it.active}.each { player->
    // Make each players' cooperation choice public
    player.cooperation = player.private.cooperation
  }
  g.V.filter{it.active}.each { player->    
    // Now apply payoffs to neighbors of cooperators
    if (player.cooperation == 1) {
      player.neighbors.each { n->
        n.private.score += po       
      }
    }
    
    // fills for the results step 
    def fill0 = (player.cooperation == 1) ? "cooperate" : "defect"
    def fill1 = (player.cooperation == 1) ? coc : "0"
    def fill2 = player.neighbors.filter{ it.cooperation == 1 }.count()
    def fill3 = po
    def fill4 = player.neighbors.filter{ it.cooperation == -1 }.count()
    player.text = c.get("ResultsStep", fill0, fill1, fill2, fill3, fill4)
    a.add(player, [name: "Next", result: {
      player.text += "<p>Please wait for the other players to click 'Next'.</p>"
    }])
  }
}

resultsStep.done = {
  println "resultsStep.done: ${curRound}"
  if (curRound < nRounds) {
    curRound++
    cooperationStep.start()
  } else {
    println "Done"
    g.V.filter{it.active}.each { player->
      player.text = c.get("Finished", player.private.score)
    }
  }
}
