cooperationStep = stepFactory.createStep()

cooperationStep.run = {
  println "cooperationStep.run: ${curRound}"
  g.V.filter{it.active}.each { player->
    player.text = c.get("CooperationStep", coc, po)
    a.add(player, [name: "Cooperate", result: {
      player.private.cooperation = 1
      player.neighbors.each { n->
        player.private.score -= coc       
      }
      player.text += "<p>Please wait for the other players to make their choices.</p>"
    }], [name: "Defect", result: {
      player.private.cooperation = -1
      player.text += "<p>Please wait for the other players to make their choices.</p>"
    }])
  }
}
cooperationStep.done = {
  println "cooperationStep.done: ${curRound}"
  resultsStep.start() 
}