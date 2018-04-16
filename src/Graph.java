import java.util.ArrayList;

public class Graph {

	private int v;
	private ArrayList<Integer>[] adjList;
	static int possibility;
	public Graph(int vertices) {
		this.v = vertices;
		initAdjList();
	}

	@SuppressWarnings("unchecked")
	private void initAdjList() {
		adjList = new ArrayList[v];
		for (int i = 0; i < v; i++) {
			adjList[i] = new ArrayList<>();
		}
	}

	public void addEdge(int i, int j) {
		adjList[i].add(j);
	}

	public void printEdges() {
		for (int i = 0; i < this.v; i++) {
			System.out.println(adjList[i]);
		}
	}

	public void printAllPaths(int i, int j, int[][] myIntArray, int sourceLength, int destLength, int maxOperation,
			String sourceString, String destString) {
		boolean[] isVisited = new boolean[v];
		ArrayList<Integer> pathList = new ArrayList<>();

		// add source to path[]
		pathList.add(i);

		// Call recursive utility
		printAllPathsUtil(i, j, isVisited, pathList, myIntArray, sourceLength, destLength, maxOperation, sourceString,
				destString);

	}

	private void printAllPathsUtil(int i, int j, boolean[] isVisited, ArrayList<Integer> pathList, int[][] myIntArray,
			int sourceLength, int destLength, int maxOperation, String sourceString, String destString) {
		isVisited[i] = true;

		Boolean validPath;
		if (i == j) {
			validPath = validatePath(pathList, myIntArray, sourceLength, destLength, maxOperation, sourceString,
					destString);
			if (validPath == true) {
				int prevIndexV = 0;
				int operations = 0;

				String operationToDo = new String();

				for (int k = 0; k < pathList.size(); k++) {
					int currentIndexV = pathList.get(k);

					if (k != 0) {
						if ((prevIndexV + destLength + 1) == currentIndexV) {
							operations = operations + 1;
						} else if ((prevIndexV + 1) == currentIndexV) {
							operations = operations + 1;
						} else if ((prevIndexV + destLength + 2) == currentIndexV) {
							if (myIntArray[currentIndexV / (destLength + 1)][currentIndexV
									% (destLength + 1)] == myIntArray[prevIndexV / (destLength + 1)][prevIndexV
											% (destLength + 1)]) {
								// operationToDo = operationToDo + "(DN)";
							} else if (myIntArray[currentIndexV / (destLength + 1)][currentIndexV % (destLength
									+ 1)] == (myIntArray[prevIndexV / (destLength + 1)][prevIndexV % (destLength + 1)]
											+ 1)) {
								operations = operations + 1;
							}
						}
					}
					prevIndexV = currentIndexV;
				}
				if (operations == maxOperation) {
					int lengthPathList = pathList.size();

					char generateChar;
					String generateStr = new String();
					String operatonStr = new String();
					StringBuffer tempStr = new StringBuffer();
					tempStr.append(sourceString);
					int lastElement = pathList.get(lengthPathList - 1);
					int currentElement = 0;
					String generateOpStr = new String();

					for (int l = 1; l < lengthPathList; l++) {
						currentElement = pathList.get(lengthPathList - 1 - l);

						if (((currentElement + 1) == lastElement) && destLength > 1) {
							// horizontal
							generateChar = destString.charAt((lastElement % (destLength + 1)) - 1);
							generateStr = generateChar + generateStr;

							tempStr.insert(((lastElement / (destLength + 1)) - 1) + 1,generateChar);
							generateOpStr = tempStr + "->" + generateOpStr + "->";
							operatonStr = operatonStr + "-->Insert " + generateChar + " at position " + lastElement / (destLength + 1)
									+ "->" +tempStr +'\n';
						}
						if ((currentElement + destLength + 1) == lastElement) {
							// vertical
							tempStr.deleteCharAt((lastElement / (destLength + 1)) - 1);
							generateOpStr = tempStr + "->" + generateOpStr + "->";
							operatonStr = operatonStr + "-->Delete " + sourceString.charAt((lastElement / (destLength + 1)) - 1)
									+ " at position " + lastElement / (destLength + 1) + "->" +tempStr + '\n';

						}
						if ((currentElement + destLength + 2) == lastElement) {
							// diagonal
							if (myIntArray[lastElement / (destLength + 1)][lastElement
									% (destLength + 1)] == myIntArray[currentElement / (destLength + 1)][currentElement
											% (destLength + 1)]) {
								generateChar = sourceString.charAt((lastElement / (destLength + 1)) - 1);
								generateStr = generateChar + generateStr;
								operatonStr = operatonStr + "-->Don't change " + generateChar + " at position "
										+ lastElement / (destLength + 1) + "->"+ tempStr +'\n';
							}

							if (myIntArray[lastElement / (destLength + 1)][lastElement
									% (destLength + 1)] == (myIntArray[currentElement / (destLength + 1)][currentElement
											% (destLength + 1)] + 1)) {
								generateChar = destString.charAt((lastElement % (destLength + 1)) - 1);
								generateStr = generateChar + generateStr;
								tempStr.replace(((lastElement / (destLength + 1)) - 1), ((lastElement / (destLength + 1))), generateChar + "");
								generateOpStr = tempStr + "->" + generateOpStr + "->";
								operatonStr = operatonStr + "-->Replace " + sourceString.charAt((lastElement / (destLength + 1)) - 1)
										+ " with " + generateChar + " at position " + lastElement / (destLength + 1)
										+ "->"+ tempStr +'\n';
							}
						}
						lastElement = currentElement;
					}
					if (generateStr.equals(destString)) {
						//System.out.println(generateOpStr);
						possibility = possibility + 1;
						System.out.print(possibility);
						System.out.println(operationToDo);
						System.out.println(operatonStr);
						//System.out.println(pathList);
					}
				}
				operations = 0;
				operationToDo = "";
			}
		}

		for (Integer s : adjList[i]) {
			if (!isVisited[s]) {
				pathList.add(s);
				printAllPathsUtil(s, j, isVisited, pathList, myIntArray, sourceLength, destLength, maxOperation,
						sourceString, destString);
				pathList.remove(s);
			}
		}
		isVisited[i] = false;
	}

	private boolean validatePath(ArrayList<Integer> pathList, int[][] myIntArray, int sourceLength, int destLength,
			int maxOperation, String sourceString, String destString) {
		int prevIndex, currentIndex, prevValue, currentValue;

		for (int i = 1; i < pathList.size(); i++) {
			prevIndex = pathList.get(i - 1);
			currentIndex = pathList.get(i);

			prevValue = myIntArray[prevIndex / (destLength + 1)][prevIndex % (destLength + 1)];
			currentValue = myIntArray[currentIndex / (destLength + 1)][currentIndex % (destLength + 1)];

			if (prevValue > currentValue) {
				return false;
			}
		}
		return true;
	}
}