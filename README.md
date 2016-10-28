# breadboard-experiments
A public repository of example experiment code for use with breadboard.

If you have an experiment you'd like to submit to this repository, please contact us.

## [Public Goods Game](https://en.wikipedia.org/wiki/Public_goods_game)
This is a public goods game played over repeated rounds. In each round the players will choose to cooperate, pay [coc] points for
each neighbor in order for each neighbor to receive [po] points, or to defect, pay 0 points and not change the score of their neighbors.

## [Ultimatum Game](https://en.wikipedia.org/wiki/Ultimatum_game)
In this game players are paired up and one player is given the role of 'Proposer' and the other is given the role of 'Responder'. The Proposer takes a sum of points and divides it among themselves and their partner. The Responder can choose to Accept the proposed allocation in which case the players get the allocated points or Reject the proposal in which case neither player gets any points.

This game includes a custom aiBehavior closure to allow AI proposers to pick a random point allocation, to add AI players using this behavior run ```g.addAI(a, 20, aiBehavior)``` in the Script dialog.
