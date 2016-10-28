proposalStep = stepFactory.createStep()

proposalStep.run = {
  println "proposalStep.run"
  curStep = "proposal"
  g.V.filter{it.active && it.responder}.each { responder->
    responder.text = c.get("WaitProposal")
  }
  
  g.V.filter{it.active && it.proposer}.each { proposer->
    proposer.text = c.get("MakeProposal")
    
    a.add(proposer, 
          [name: "Submit Proposal",
           custom: """ 
             <input type="range" class="param" min="0" max="${sum}" value="${sum/2}" ng-init="allocation=${sum/2}" ng-model="allocation" name="allocation" required>
             <p>If your partner accepts the proposed allocation:</p>
             <ul>
               <li><strong>you</strong> will receive {{ allocation }} points</li>
               <li><strong>your partner</strong> will receive {{ (${sum} - allocation) }} points</li>
             </ul>
                   """.toString(),
           result: { params->
             if (params != null) {
               def responder = proposer.neighbors.next()
               proposer.allocation = Integer.parseInt(params.allocation)
               proposer.fairness = Math.round(proposer.allocation / sum * 10)
               responder.allocation = (sum - proposer.allocation)
               responder.text = c.get("MakeResponse", proposer.allocation, responder.allocation)
               proposer.text = c.get("WaitResponse", proposer.allocation, responder.allocation)
               a.add(responder, 
                     [name: "Accept", 
                      result: { 
                        responder.accept = 1 
                        proposer.text = c.get("AcceptAllocation", "The Responder has", proposer.allocation, responder.allocation)
                        responder.text = c.get("AcceptAllocation", "You have", responder.allocation, proposer.allocation)
                      }],
                     [name: "Reject", 
                      result: { 
                        responder.accept = -1
                        proposer.text = c.get("RejectAllocation", "The Responder has")
                        responder.text = c.get("RejectAllocation", "You have")
                      }])
             }
           }])
  }
  
}

proposalStep.done = {
  println "proposalStep.done"
}
