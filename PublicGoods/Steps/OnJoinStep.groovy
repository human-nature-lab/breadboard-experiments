onJoinStep = stepFactory.createNoUserActionStep()

onJoinStep.run = { playerId->
  def player = g.getVertex(playerId)
  
  player.private.score = initScore // Private score is hidden from neighbors
  player.private.cooperation = 0 // Private cooperation for secret choices
  player.cooperation = 0 // Public cooperation for reveal during results step
  player.active = true // Active players have not been dropped
  
  player.text = c.get("Welcome")
  a.add(player, [name: "Next", result: {
    player.text = c.get("Tutorial1")
  }])
  a.add(player, [name: "Next", result: {
    player.text = c.get("Tutorial2", coc, po)
  }])
  a.add(player, [name: "Next", result: {
    player.text = c.get("PleaseWait")
  }])
}
onJoinStep.done = {
}