def colorStep(player) {
  def labels = ["","Green","Orange","Purple","Pink","E","F"]
  def choices = [] 
  for (i in 1..nColors) {
    def curColor = i
    def choice = [
      name: labels[curColor], 
      class: "color" + curColor,
      event: [
        name: "ColorEvent",
        data: [
          color: curColor,
          pid: player.id  
        ]
      ],
      result : {
	    player.color = curColor
    	showConflicts(player)
        if (!gameEnded){
          colorStep(player)
        }
      }]
    choices << choice  
  }
  a.add(player, {}, *choices)
}