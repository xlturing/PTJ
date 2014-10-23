import mpi.*;

import java.io.*;
import java.security.KeyStore.Entry;
import java.util.*;

/**
 * Computes the pagerank in parallel.
 * 
 * The first process read the file, construct the adjacency matrix and
 * distribute the items to all the other processes.
 * 
 * Also each process will get an array that contains the global pageranks. Also
 * every process will get another array that maps the items in the global
 * pageranks array list items to its corresponding url keys.
 * 
 * The algorithm should run for fixed number of iterations provided by user or
 * until the difference of pageranks between two consecutive iterations become
 * less than a user defined threshold.
 * 
 */
public class MPIPageRank {
	/**
	 * This has values from key : values 0 : 1 : 2 2 : 1 3 : 0 1 4 : 1 3 5 5 : 1
	 * 4 6 : 1 4 7 : 1 4 8 : 1 4 9 : 4 10 : 4
	 * <p/>
	 * This maps the URL key to the outbound links that this URL has
	 */
	private Map<Integer, ArrayList<Integer>> localAdjMat = new HashMap<Integer, ArrayList<Integer>>();

	/**
	 * The total Number of urls
	 */
	private int totalUrls = 0;

	/**
	 * damping factor
	 */
	private double dampingFactor = 0.85;

	/**
	 * Keep an upper bound of iterations
	 */
	private int iterations = 50;

	/**
	 * Input file
	 */
	private String inputFile = "pagerank.input.1000.0";

	/**
	 * output file
	 */
	private String outputFile = "pagerank.output";

	/**
	 * Number of processes
	 */
	private int nproc = 0;

	/**
	 * MPI rank
	 */
	private int rank = 0;

	/**
	 * Global ranks of URLs, The key for the rank is kept in the globalKeys
	 * array. the URL key for the globalRanks[0] th item is in the globalKeys[0]
	 * th item.
	 */
	private double globalRanks[];

	/**
	 * Global keys, we are using a global ranks array we should keep the keys to
	 * array index mapping.
	 * 
	 */
	private int globalKeys[];

	/**
	 * This is the threshold to stop the calculation. When the change of values
	 * becomes less than this we will stop.
	 */
	private double delta = 0.001;

	/**
	 * Create the MPIPageRank algorithm using given params
	 * 
	 * @param inputFile
	 *            input file name
	 * @param outputFile
	 *            output file name
	 * @param delta
	 *            delta to be used
	 * @param dampingFactor
	 *            damping factor
	 */
	public MPIPageRank(String inputFile, String outputFile, double delta,
			double dampingFactor, int iterations) {
		nproc = MPI.COMM_WORLD.Size();
		rank = MPI.COMM_WORLD.Rank();

		if (rank == 0) {
			if (inputFile != null) {
				this.inputFile = inputFile;
			}

			if (outputFile != null) {
				this.outputFile = outputFile;
			}

			if (delta != 0) {
				this.delta = delta;
			}

			if (dampingFactor != 0) {
				this.dampingFactor = dampingFactor;
			}

			if (iterations != 0) {
				this.iterations = iterations;
			}
		}

		readAndDistribute();
	}

	/**
	 * Initialize the adjacency matrix and rank values
	 */
	public void readAndDistribute() {
		// broadcast the delta
		double[] valBuf = new double[1];
		valBuf[0] = delta;
		MPI.COMM_WORLD.Bcast(valBuf, 0, 1, MPI.DOUBLE, 0);
		delta = valBuf[0];

		// broadcast damping factor
		valBuf[0] = dampingFactor;
		MPI.COMM_WORLD.Bcast(valBuf, 0, 1, MPI.DOUBLE, 0);
		dampingFactor = valBuf[0];

		// broadcast no of iterations
		int itrBuf[] = new int[] { iterations };
		MPI.COMM_WORLD.Bcast(itrBuf, 0, 1, MPI.INT, 0);
		iterations = itrBuf[0];

		int totalNumBuf[] = new int[1];
		HashMap<Integer, List<Integer>> globalAdjMat = null;
		if (rank == 0) {
			globalAdjMat = readAdjMatrix(inputFile);
			totalUrls = globalAdjMat.size();
			totalNumBuf[0] = totalUrls;
		}

		// broadcast the total number of urls
		MPI.COMM_WORLD.Bcast(totalNumBuf, 0, 1, MPI.INT, 0);
		totalUrls = totalNumBuf[0];

		globalKeys = new int[totalUrls];
		globalRanks = new double[totalUrls];

		int count = 0;
		if (rank == 0) {
			for (Integer k : globalAdjMat.keySet()) {
				globalKeys[count++] = k;
			}
		}

		// broadcast the global keys
		MPI.COMM_WORLD.Bcast(globalKeys, 0, totalUrls, MPI.INT, 0);

		int divide = (totalUrls) / nproc;
		int rem = (totalUrls) % nproc;
		int[] localUrlsBuf = new int[1];

		int localUrls = 0;
		int[] localKeys;
		int[][] localValues;
		if (rank == 0) {
			int keys[] = new int[totalUrls];
			int values[][] = new int[totalUrls][];

			count = 0;
			// we are going to create a two dimentional arraylist from the
			// input, because it
			// makes our life easier when sending the values to different
			// processes.
			for (Integer key : globalAdjMat.keySet()) {
				keys[count] = key;
				values[count] = new int[globalAdjMat.get(key).size()];
				for (int j = 0; j < globalAdjMat.get(key).size(); j++) {
					values[count][j] = globalAdjMat.get(key).get(j);
				}
				count++;
			}

			// for process 0 the number of urls that we are going to assign
			if (rem > 0) {
				localUrls = divide + 1;
			}
			else {
				localUrls = divide;
			}
			int startIndex = localUrls;
			/* scatter adjacency_matrix[total_num_urls] to other processes */
			for (int i = 1; i < nproc; i++) {
				// add 1 to each divide until remainder is 0
				int numberOfUrls = (i < rem) ? divide + 1 : (divide);
				localUrlsBuf[0] = numberOfUrls;

				// first send the number of local urls
				MPI.COMM_WORLD.Send(localUrlsBuf, 0, 1, MPI.INT, i, 0);

				// now send the keys
				MPI.COMM_WORLD.Send(keys, startIndex, numberOfUrls, MPI.INT, i,
						0);

				// now send the arrays
				for (int j = 0; j < numberOfUrls; j++) {
					int urlsBuf[] = new int[1];
					urlsBuf[0] = values[startIndex + j].length;

					MPI.COMM_WORLD.Send(urlsBuf, 0, 1, MPI.INT, i, 0);
					MPI.COMM_WORLD.Send(values[startIndex + j], 0,
							values[startIndex + j].length, MPI.INT, i, 0);
				}
				startIndex += numberOfUrls;
			}

			// reduce to the local keys
			localKeys = new int[localUrls];
			localValues = new int[localUrls][];

			for (int i = 0; i < localUrls; i++) {
				localKeys[i] = keys[i];
				localValues[i] = values[i];
			}
		}
		else {
			// receive the number of local urls
			MPI.COMM_WORLD.Recv(localUrlsBuf, 0, 1, MPI.INT, 0, 0);
			localUrls = localUrlsBuf[0];
			int keys[] = new int[localUrls];
			int values[][] = new int[localUrls][];

			// receive the keys of local urls
			MPI.COMM_WORLD.Recv(keys, 0, localUrls, MPI.INT, 0, 0);

			int urlsBuf[] = new int[1];

			// receive the values
			// first receive the key, then receive the array
			for (int j = 0; j < localUrls; j++) {
				MPI.COMM_WORLD.Recv(urlsBuf, 0, 1, MPI.INT, 0, 0);
				values[j] = new int[urlsBuf[0]];
				MPI.COMM_WORLD.Recv(values[j], 0, urlsBuf[0], MPI.INT, 0, 0);
			}

			localKeys = keys;
			localValues = values;
		}

		// create the local adjacency matrix so that we can do the computation.
		// this is done by all the processes.
		for (int i = 0; i < localUrls; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j < localValues[i].length; j++) {
				list.add(localValues[i][j]);
			}
			localAdjMat.put(localKeys[i], list);
		}

		initRankVal();
	}

	/**
	 * Initialize the rank values to 1/N
	 */
	private void initRankVal() {
		for (int i = 0; i < totalUrls; i++) {
			globalRanks[i] = 1.0 / totalUrls;
		}
	}

	/**
	 * Read the input file and create the matrix required for calculations
	 * 
	 * @param inFileName
	 *            name of the input file
	 * @return global adjecency matrix
	 */
	HashMap<Integer, List<Integer>> readAdjMatrix(String inFileName) {
		HashMap<Integer, List<Integer>> globalAdjMat = new HashMap<Integer, List<Integer>>();
		BufferedReader fReader;
		try {
			fReader = new BufferedReader(new FileReader(inFileName));

			String inLine;
			while ((inLine = fReader.readLine()) != null) {
				String[] tokens = inLine.split(" ");
				Integer urlKey = Integer.parseInt(tokens[0]);

				ArrayList<Integer> outUrls = new ArrayList<Integer>();
				for (int i = 1; i < tokens.length; i++) {
					outUrls.add(Integer.parseInt(tokens[i]));
				}

				if (!globalAdjMat.containsKey(urlKey)) {
					globalAdjMat.put(urlKey, outUrls);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file cannot be found...." + inFileName);
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Error reading the input file.. " + inFileName);
			e.printStackTrace();
		}

		return globalAdjMat;
	}

	/**
	 * Compute the page ranks. Here is a rough outline of what you need to do.
	 * You may choose a different path than this.
	 * 
	 * The algorithm has a big loop that continues for fixed number iterations
	 * or until the difference in two consecutive pagerank calculations are
	 * below delta.
	 * 
	 * Inside the loop..
	 * 
	 * First we need to keep the current pageranks, we will use this to compute
	 * the difference between current one and the next one so that we can
	 * terminate
	 * 
	 * Then we are going to calculate the local page ranks as in sequential
	 * pagerank, but we are only going to use the local adjacency matrix and
	 * global pageranks. Note you don't need to calculate the (1-d)/N + d * part
	 * in this step.
	 * 
	 * After this step we are going to add up all the dangling values across all
	 * the processes
	 * 
	 * then we need to update the global pageranks with all the values computed
	 * in the local processes
	 * 
	 * after this we can calculate the actual pageranks by updating the values
	 * with (1-d)/N + d *
	 * 
	 * Now calculate the difference between old values and new values and
	 * terminate if less than delta
	 * 
	 * return the number of iterations performed
	 */
	public int computeRank() {
		// implment this
		int itr = 0;

		// keep current pageranks which will use to compute difference between
		// two iterations
		double[] prePtr = new double[totalUrls];
		// local rank
		double[] locRank = new double[totalUrls];

		Arrays.fill(prePtr, 0.0);
		while (itr++ < iterations && !checkDif(prePtr)) {
			if (rank == 0) {
				// keep current pageranks
				System.arraycopy(globalRanks, 0, prePtr, 0, totalUrls);
				Arrays.fill(globalRanks, (1 - dampingFactor) / totalUrls);

				// reduce all local process in root rank
				double[] tmp = new double[totalUrls];
				Arrays.fill(tmp, 0);
				for (int i = 1; i < nproc; i++) {
					MPI.COMM_WORLD.Recv(tmp, 0, totalUrls, MPI.DOUBLE, i, 0);
					for (int j = 0; j < totalUrls; j++) {
						globalRanks[j] += (tmp[j] * dampingFactor);
					}
				}
			}
			else {
				/* calculate the local rank in local process */
				Arrays.fill(locRank, 0);
				for (java.util.Map.Entry<Integer, ArrayList<Integer>> e : localAdjMat
						.entrySet()) {
					int key = e.getKey();
					List<Integer> outLinks = e.getValue();

					// the current vertix give outlinks weight
					double outWeight = 0.0;
					if (outLinks.size() != 0)
						outWeight = globalRanks[key] / outLinks.size();

					// add weight to ervery inLinks vertix
					for (Integer outKey : outLinks) {
						locRank[outKey] += outWeight;
					}
				}
				MPI.COMM_WORLD.Send(locRank, 0, locRank.length, MPI.DOUBLE, 0,
						0);
			}
		}
		return itr;
	}

	/**
	 * compute difference between two iterations. If the difference greater than
	 * delta, then return false, or return true
	 * 
	 * @return boolean
	 */
	private boolean checkDif(double[] prePtr) {
		double dis = 0;
		for (int i = 0; i < prePtr.length; i++) {
			dis += (prePtr[i] - globalRanks[i]) * (prePtr[i] - globalRanks[i]);
		}
		return Math.sqrt(dis) < delta ? true : false;
	}

	int getRankIndex(Integer key) {
		for (int i = 0; i < totalUrls; i++) {
			if (globalKeys[i] == key) {
				return i;
			}
		}
		throw new IllegalStateException(
				"This cannot be reached, key should be present");
	}

	/**
	 * s Save the ranks to a file as well as output the results to the console.
	 */
	private void printRank() {
		if (rank != 0) {
			return;
		}

		Map<Integer, Double> rankVal = new HashMap<Integer, Double>();
		for (int i = 0; i < totalUrls; i++) {
			rankVal.put(globalKeys[i], globalRanks[i]);
		}

		File outFile = new File(outputFile);
		int count;

		System.out.println("\n");
		List<Value> values = new ArrayList<Value>();
		for (Map.Entry<Integer, Double> e : rankVal.entrySet()) {
			values.add(new Value(e.getKey(), e.getValue()));

		}

		// sort the ranks
		Collections.sort(values, new Comparator<Value>() {
			public int compare(Value o1, Value o2) {
				Double v1 = o1.value;
				Double v2 = o2.value;

				double val = v1 - v2;
				if (Math.abs(val) < .00001) {
					return 0;
				}
				else if (val > 0) {
					return -1;
				}
				else {
					return 1;
				}
			}
		});

		System.out.println("Sorted page rank values:");

		double sum = 0.0;
		BufferedWriter writer = null;
		// print the values to the output file
		try {
			writer = new BufferedWriter(new FileWriter(outFile));
			count = 0;
			for (Value v : values) {
				if (count < 10) {
					writer.write(v.getKey() + " : " + v.getValue() + "\n");
					System.out.println(v.getKey() + " : " + v.getValue());
				}
				count++;
				// calculate the page rank sum
				sum += v.getValue();
			}
			writer.flush();
		}
		catch (IOException e) {
			System.out
					.println("Error occurred while opening output file for writing..: "
							+ outputFile);
			e.printStackTrace();
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
			}
			catch (IOException ignored) {}
		}

		System.out.println("Total value of page ranks:" + sum);
	}

	/**
	 * This is a class used for sorting the ranks.
	 */
	private class Value {
		private Integer key;
		private Double value;

		private Value(Integer key, Double value) {
			this.key = key;
			this.value = value;
		}

		public Integer getKey() {
			return key;
		}

		public Double getValue() {
			return value;
		}
	}

	public static void main(String args[]) {
		MPI.Init(args);

		double delta = 0;
		double df = 0;
		String input = null;
		String output = null;
		int iterations = 0;

		int rank = MPI.COMM_WORLD.Rank();
		if (rank == 0) {
			if (args.length < 8) {
				System.out
						.println("Usage <input_file_name> <output_file_name> <delta> <damp_factor> <iterations>");
				MPI.Finalize();
				System.exit(-1);
			}
			else {
				if (args[5] != null) {
					delta = Double.parseDouble(args[5]);
				}

				if (args[6] != null) {
					df = Double.parseDouble(args[6]);
				}
				input = args[3];
				output = args[4];

				if (args[7] != null) {
					iterations = Integer.parseInt(args[7]);
				}
			}
		}

		MPIPageRank pr = new MPIPageRank(input, output, delta, df, iterations);
		// compute the ranks
		int itr = pr.computeRank();

		if (MPI.COMM_WORLD.Rank() == 0) {
			System.out.println("The algorithm ran " + itr
					+ " number of iterations");
		}

		// print the ranks
		pr.printRank();

		MPI.Finalize();
	}

	// for debug
	void printMap(Map<Integer, ArrayList<Integer>> inMap) {
		Set<Integer> keySet = inMap.keySet();
		for (Integer key : keySet) {
			System.out.print(" " + key + " : ");

			List<Integer> values = inMap.get(key);
			for (Integer val : values) {
				System.out.print(val + " ");
			}
			System.out.println();
		}
	}

	// for debug
	void printMap(HashMap<Integer, List<Integer>> inMap) {
		Set<Integer> keySet = inMap.keySet();
		for (Integer key : keySet) {
			System.out.print(" " + key + " : ");

			List<Integer> values = inMap.get(key);
			for (Integer val : values) {
				System.out.print(val + " ");
			}
			System.out.println();
		}
	}

}
