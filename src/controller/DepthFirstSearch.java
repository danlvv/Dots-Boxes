package controller;

import java.util.Stack;

import model.State;

public class DepthFirstSearch {
	
//	1  procedure DFS-iterative(G,v):
//		2      let S be a stack
//		3      S.push(v)
//		4      while S is not empty
//		5            v = S.pop()
//		6            if v is not labeled as discovered:
//		7                label v as discovered
//		8                for all edges from v to w in G.adjacentEdges(v) do
//		9                    S.push(w)
	
	public Stack<State> Search(State child){
		Stack<State> tempStack = new Stack<State>();		
		
		tempStack.push(child);
		while(!tempStack.isEmpty()){
			
		}
		
		return null;
	}
}
