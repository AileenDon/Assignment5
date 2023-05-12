//Aileen Dong (ydong8@toromail.csudh.edu)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DirectoryAnalyzer {
    public static void main(String[] args) {
        // Check if the directory name argument is provided
        if (args.length != 1) {
            System.out.println("Usage: java DirectoryAnalyzer <Test>");
            return;
        }

        // Get the directory name from the command line argument
        String dirName = args[0];

        // Create a File object for the directory
        File dir = new File(dirName);

        // Check if the directory exists
        if (!dir.exists()) {
            System.out.println("Error: Directory '" + dirName + "' does not exist.");
            return;
        }

        // Check if the directory is readable
        if (!dir.canRead()) {
            System.out.println("Error: No permission to read directory '" + dirName + "'.");
            return;
        }

        // Check if the directory is actually a directory
        if (!dir.isDirectory()) {
            System.out.println("Error: '" + dirName + "' is not a directory.");
            return;
        }

        // Print the header row for the file information table
        System.out.println("File Name\tSize\tAlpha Chars\tNumeric Chars\tSpaces");

        // Initialize counters for the total number of files and characters
        int totalFiles = 0;
        int totalAlphaChars = 0;
        int totalNumericChars = 0;
        int totalSpaceChars = 0;
        long totalSize = 0;

        // Loop through all files in the directory
        for (File file : dir.listFiles()) {
            // Skip directories
            if (file.isDirectory()) {
                continue;
            }

            // Get the file name
            String fileName = file.getName();

            // Get the file size in bytes
            long fileSize = file.length();

            // Read the file contents as a string
            String fileContents = "";
            try (Scanner scanner = new Scanner(file)) {
                fileContents = scanner.useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Could not read file '" + fileName + "'.");
                continue;
            }
            // Count the number of alpha, numeric, and space characters
            int alphaChars = 0;
            int numericChars = 0;
            int spaceChars = 0;
            for (int i = 0; i < fileContents.length(); i++) {
                char c = fileContents.charAt(i);
                if (Character.isLetter(c)) {
                    alphaChars++;
                } else if (Character.isDigit(c)) {
                    numericChars++;
                } else if (Character.isWhitespace(c)) {
                    spaceChars++;
                }
            }

            // Print the file information row
            System.out.println(fileName + "\t" + fileSize + " bytes\t" + alphaChars + "\t" + numericChars + "\t" + spaceChars);

            // Update the total counters
            totalFiles++;
            totalAlphaChars += alphaChars;
            totalNumericChars += numericChars;
            totalSpaceChars += spaceChars;
            totalSize += fileSize;
        }
        // Print the totals row
        System.out.println("Total Files: " + totalFiles + "\tTotal Alpha Chars: " + totalAlphaChars + "\tTotal Numeric Chars: " + totalNumericChars + "\tTotal Space Chars: " + totalSpaceChars + "\tTotal Size Disk: " + formatSize(totalSize));
    }
    private static String formatSize(long size) {
        if (size < 1024) {
            return size + " bytes";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }
}
/*test output:
File Name Size   Alpha Chars Numeric Chars Spaces
Two.java 898 bytes 452           26     303
one.java 470 bytes 192           2    209
Three.java 487 bytes 260           4    153
Total Files: 3 Total Alpha Chars: 904 Total Numeric Chars: 32 Total Space
Chars: 665 Total Size Disk: 1.81 KB*/
