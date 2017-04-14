initStep = stepFactory.createStep("initStep")

initStep.run = {
  println("initStep.run")
    
  // record initial parameters in data here
  a.addEvent("InitParameters", 
  ["nColors" : nColors,
   "successBonus" : successBonus,
   "gameLength" : gameLength,
   "v" : v
  ])
  
  g.V.filter{ (it.active != null) && (it.active)}.each { player->
    a.add(player, [name: "Ready", 
                   result: { 
                     player.ready = true
                     player.text += "<p><strong>Please wait... the game will start momentarily.</strong></p>"
                   },
                   event: [
                     name: "PlayerReady",
                     data: [
                       pid: player.id  
                     ]
                   ]
                 ])
  }

} // initStep.run

initStep.done = {
  println("initStep.done")
  gameStartStep.start()
}