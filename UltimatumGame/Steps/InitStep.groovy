// Global variables
curRound = 0
curStep = "start"

aiBehavior = { player->
  if (player.getProperty("choices")) {
    def choices = player.getProperty("choices")
    def choice = choices[r.nextInt(choices.size())]
    def params = null
    if (curStep == "proposal" && player.proposer) {
      params = [allocation: (r.nextInt(sum)).toString()]
    }
    a.choose(choice.uid, params)
  }
}

initStep = stepFactory.createStep()

initStep.run = {
  println "initStep.run"
  curStep = "init"
  
  g.V.each { player->
    player.text = c.get("PleaseWait") + "<p><strong>Click 'Begin' to join the game.</strong></p>"
    a.add(player, [name: "Begin", result: {
      player.text = c.get("PleaseWait") + "<p><strong>Thank you, the game will begin in a moment.</strong></p>"
    }])
  }
}

initStep.done = {
  println "initStep.done"
  pairingStep.start()
}