gameStartStep = stepFactory.createStep("gameStartStep")

gameStartStep.run = {
  println("gameStartStep.run")
 
  // Preferential attachment network with v edges is always solvable with v + 1 colors
  g.barbasiAlbert(v)
  
  // Add a timer to all players
  g.addTimer(gameLength, "gameEndTimer", "Your bonus is currently ", "down",  "currency", "300", {}, null, "success")
  
  g.V.filter{it.ready != null && it.ready}.each { player ->
    a.ai.remove(player)
    player.text = c.get("GameOn", Math.round(gameLength / 60))
      
    // Random starting color
    player.color = (r.nextInt(nColors) + 1)
              
    player.private.score = "You"
    a.addEvent("InitColor",
              ["pid"  : player.id,
               "color": player.color])
    colorStep(player)
  } // g.V.filter
   
  def endGameTimer = new Timer()
  def graphTimer = new Timer()
  def count = 0

  showConflicts()

  a.addEvent("GameStart", ["GameStart": 1])

  use(TimerMethods) {
    graphTimer.runEvery(100, 100) {
      def numConflicts = g.E.filter { it.conflict == 1 }.toList().size()
      if (numConflicts == 0) {
        if (!gameEnded) {
          gameEnded = true
          def bonus = Math.round(successBonus * (gameLength - count / 10) / gameLength) / 100
          finishGame(graphTimer, c.get("GameWon", currency.format(bonus)), bonus)
        }
      } // if (numConflicts == 0)
      count += 1
    } // graphTimer.runEvery(100,100)
  } // use (TimerMethods)

  endGameTimer.runAfter(gameLength * 1000) {
    if (!gameEnded) {
      gameEnded = true
      finishGame(graphTimer, c.get("GameLost"), 0)
    }
  }
} // gameStartStep.run

gameStartStep.done = {
  println("gameStartStep.done")
}

def finishGame(graphTimer, endText, bonus){
  graphTimer.cancel()

  a.addEvent("GameEnd",["bonus" : bonus, "conflicts" : g.E.filter{it.conflict == 1}.toList().size()])

  g.V.filter{it.ready != null && it.ready}.each { player ->
    a.remove(player)
    player.timers = [:]
    player.text = endText
  }
}
