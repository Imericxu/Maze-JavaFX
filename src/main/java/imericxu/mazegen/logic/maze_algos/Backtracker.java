package imericxu.mazegen.logic.maze_algos;

import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.Stack;
import java.util.stream.Collectors;


public class Backtracker extends MazeAlgorithm {
	private final Stack<Integer> exploreStack = new Stack<>();

	public Backtracker(Node[] nodes) {
		super(nodes);
		final int startId = rand.nextInt(nodes.length);
		exploreStack.push(startId);
		changeState(startId, State.PARTIAL);
	}

	@Override
	public boolean loopOnceImpl() {
		final Node current = nodes[exploreStack.peek()];
		final Node randomNeighbor = selectRandomUnvisitedNeighbor(current);

		if (randomNeighbor != null) {
			Node.connect(current, randomNeighbor);
			changeState(randomNeighbor, State.PARTIAL);
			exploreStack.push(randomNeighbor.id);
		} else {
			exploreStack.pop();
			changeState(current.id, State.SOLID);
		}

		return exploreStack.isEmpty();
	}

	private Node selectRandomUnvisitedNeighbor(Node node) {
		final var unvisitedNeighbors = node.getNeighbors().stream()
				.filter(id -> states[id] == State.EMPTY)
				.collect(Collectors.toUnmodifiableList());

		if (unvisitedNeighbors.isEmpty()) return null;

		final int index = rand.nextInt(unvisitedNeighbors.size());
		return nodes[unvisitedNeighbors.get(index)];
	}
}
