package com.biohack.z5.cmd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Params: [0] - sorted Snipets file path [1] - map file path [2] - ped input
 * file path [3] - result output file path
 * 
 * @author plapinski
 *
 */
public class Convert2Pairs {

	public static void main(String[] args) {
		List<String> sortedPairsDef;
		List<Integer> convertedMapFile;
		try {
			sortedPairsDef = getSortedMap(args[0]);
		} catch (Exception e) {
			System.out.println("Error read pairs" + e.getMessage());
			return;
		}
		try {
			convertedMapFile = convertMapFile(args[1], sortedPairsDef);
			Collections.sort(convertedMapFile);
		} catch (Exception e) {
			System.out.println("Error read map file" + e.getMessage());
			return;
		}
		convertPedFile(args[2], args[3], convertedMapFile);
	}

	private static List<String> getSortedMap(String sortedFilePath) {
		List<String> result = new ArrayList<>();
		try {
			Files.lines(Paths.get(sortedFilePath)).forEach(row -> {
				result.add(getSnipIdFromRow(row));
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private static String getSnipIdFromRow(String row) {
		return row.split(" ")[1];
	}

	private static List<Integer> convertMapFile(String mapFilePath, List<String> sortedPairsDef) {
		List<Integer> result = new ArrayList<>();
		AtomicInteger interator = new AtomicInteger(1);
		try {
			Files.lines(Paths.get(mapFilePath)).forEach(row -> {
				String snipedId = getSnipIdFromRowMapFile(row);
				int i = interator.getAndIncrement();
				if (sortedPairsDef.contains(snipedId)) {
					result.add(i);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private static String getSnipIdFromRowMapFile(String row) {
		return row.split("	")[1];
	}

	private static void convertPedFile(String pedInputFilePath, String resultPedOutputFilePath,
			List<Integer> sortedColumns) {
		final BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(new File(resultPedOutputFilePath)));
			Files.lines(Paths.get(pedInputFilePath)).forEach(row -> {
				writeOutputRow(row, bufferedWriter, sortedColumns);
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// TODO close bufferedWriter
		}
	}

	private static void writeOutputRow(String row, BufferedWriter bufferedWriter, List<Integer> sortedColumns) {
		String[] splittedRow = row.split(" ");
		try {
			bufferedWriter.write(splittedRow[0] + " " + splittedRow[1] + " " + splittedRow[2] + " " + splittedRow[3]
					+ " " + splittedRow[4] + " " + splittedRow[5]);
			sortedColumns.stream().forEach(columnNumber -> {
				try {
					bufferedWriter
							.write(" " + splittedRow[columnNumber * 2 + 4] + " " + splittedRow[columnNumber * 2 + 5]);
				} catch (IOException e) {
					throw new RuntimeException("Error write output file");
				}
			});
			bufferedWriter.newLine();
		} catch (IOException e) {
			throw new RuntimeException("Error write output file");
		}
	}

}
