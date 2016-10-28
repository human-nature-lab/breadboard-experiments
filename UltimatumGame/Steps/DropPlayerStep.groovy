a.setDropPlayers(false)
// Players will be warned after 30 seconds and dropped after 60 seconds
a.setIdleTime(30)
a.setDropPlayerClosure({ player ->
  a.addEvent("DropPlayer", ["pid": (player.id)])
  player.text = c.get("Dropped")
  // drop the player from the graph
  player.active = false
  player.dropped = true
  a.remove(player.id)
  player.getEdges(Direction.BOTH).each { g.removeEdge(it) }
  // or, take over the player with random AI
  //a.ai.add(player)
})