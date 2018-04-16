import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P1 {

	public static void main(String[] args) throws IOException {
		String sourceString;// = new String("saturday");
		String destString;// = new String("ce");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> tempStr=new ArrayList<String>();
		
		String s;
		while ((s = in.readLine()) != null) {
			Pattern r = Pattern.compile("[A-Za-z]+");
			Matcher m = r.matcher(s);
			
			if(m.matches()) {
				tempStr.add(s);
			}
			else {
				System.out.println("Invalid Input");
				System.exit(0);
			}
		}

		sourceString=tempStr.get(0);
		destString=tempStr.get(1);
		
		// Find length of strings
		int sourceLength = sourceString.length();
		int destLength = destString.length();
		int maxOperation = 0;

		// Define a matrix
		int[][] myIntArray = new int[sourceLength + 1][destLength + 1];

		// Initialize row of string l1 l2
		for (int i = 0; i <= destLength; i++) {
			myIntArray[0][i] = i;
		}

		for (int i = 0; i <= sourceLength; i++) {
			myIntArray[i][0] = i;
		}

		for (int i = 1; i <= sourceLength; i++) {
			char sourceC = sourceString.charAt(i - 1);

			for (int j = 1; j <= destLength; j++) {
				int cost = 0;
				char destC = destString.charAt(j - 1);
				if (sourceC == destC) {
					cost = 0;
				} else {
					cost = 1;
				}
				myIntArray[i][j] = minimumVal(myIntArray[i - 1][j] + 1, myIntArray[i - 1][j - 1] + cost,
						myIntArray[i][j - 1] + 1);
			}
		}

		maxOperation = myIntArray[sourceLength][destLength];

		Graph g = new Graph((sourceLength + 1) * (destLength + 1));
		// Generate all possible paths
		for (int i = 0; i < sourceLength; i++) {
			for (int j = 0; j < destLength; j++) {

				g.addEdge((i * (destLength + 1)) + j, (i * (destLength + 1)) + (j + 1));
				g.addEdge((i * (destLength + 1)) + j, (i + 1) * (destLength + 1) + j);
				g.addEdge((i * (destLength + 1)) + j, ((i + 1) * (destLength + 1)) + (j + 1));

			}
		}

		for (int i = 0; i < sourceLength; i++) {
			for (int j = 0; j < 1; j++) {
				g.addEdge((i * (destLength + 1)) + destLength, (i + 1) * (destLength + 1) + destLength);
			}
		}

		for (int j = 0; j < destLength; j++) {
			g.addEdge((sourceLength * (destLength + 1)) + j, (sourceLength * (destLength + 1)) + (j + 1));
		}

		g.printAllPaths(0, ((sourceLength + 1) * (destLength + 1) - 1), myIntArray, sourceLength, destLength,
				maxOperation, sourceString, destString);
	}

	private static int minimumVal(int i, int j, int k) {
		return Math.min(Math.min(i, j), k);
	}
}