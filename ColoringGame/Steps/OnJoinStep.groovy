onJoinStep = stepFactory.createNoUserActionStep()

onJoinStep.run = { playerId -> 
  println("onJoinStep.run")
  // Filter out dummy players added in tutorial
  if (! playerId.contains("dummy_")) {
    def player = g.getVertex(playerId) 

    player.ready = false
    player.color = 0
    player.active = false
    
    player.text = c.get("Welcome")
          
    a.add(player, [name: "Begin Tutorial >", result: { 
      player.active = true
      // see setupTutorial in Globals
      setupTutorial(player) 
    }])     
  }
} 

onJoinStep.done = {
  println("onJoinStep.done")
}