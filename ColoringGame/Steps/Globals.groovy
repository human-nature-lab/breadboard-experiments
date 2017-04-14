curStep = "init"
gameEnded = false

def showConflicts(player) {
  player.conflicts = 0
  player.bothE.each { edge->
    if(edge.inV.next().color == edge.outV.next().color) {
      edge.conflict=1
    } else {
      edge.conflict=0
    }
  }
}

def showConflicts() {
  g.E.each { edge->
    if(edge.inV.next().color == edge.outV.next().color) {
      edge.conflict=1
    } else {
      edge.conflict=0
    }
  }	
}

def setupTutorial(player) {
  def neighbor1
  def neighbor2
  def neighbor3
  def neighbor4
  def neighbor5
  // Tutorial 1
  player.private.score = "You"
  player.text = c.get("Tutorial1")

  neighbor1 = g.addVertex("dummy_" + player.id + "_1")
  neighbor1.color = 0
  neighbor1.active = false

  neighbor2 = g.addVertex("dummy_" + player.id + "_2")
  neighbor2.color = 0
  neighbor2.active = false

  neighbor3 = g.addVertex("dummy_" + player.id + "_3")
  neighbor3.color = 0
  neighbor3.active = false

  neighbor4 = g.addVertex("dummy_" + player.id + "_4")
  neighbor4.active = false
  neighbor5 = g.addVertex("dummy_" + player.id + "_5")
  neighbor5.active = false

  g.addEdge(player, neighbor1)
  g.addEdge(player, neighbor2)
  g.addEdge(player, neighbor3) 

  // Tutorial 2
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial2", Math.round(gameLength / 60))
  }])

  // Tutorial 3
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial3")
    player.color = 2
    neighbor1.color = 1
    neighbor2.color = 2
    neighbor3.color = 2
    showConflicts(player)
  }])

  // Tutorial 4
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial4")
    player.color = 1
    neighbor1.color = 2
    neighbor2.color = 2
    neighbor3.color = 2
    showConflicts(player)
  }])

  // Tutorial 5
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial5")
  }])

  // Tutorial 6
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial6", currency.format(successBonus/100))
  }])

  // Tutorial 7
  a.add(player, [name: "Next >", result: {  
    player.text = c.get("Tutorial7")
  }])
  
  //Waiting for a real game
  a.add(player, [name: "Next >", result: { 
    player.text = c.get("Waiting")

    player.color = 0
    for (i in 1..5) {
      def neighbor = g.getVertex("dummy_" + player.id + "_" + i)
      if (neighbor != null) {
        g.removePlayer(neighbor.id)
      }     
    }
  },
    event: [
      name: "PlayerWaiting",
      data: [
        pid: player.id  
      ]
    ]
  ])

}