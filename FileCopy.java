//Aileen Dong (ydong8@toromail.csudh.edu)
import java.io.*;
import java.util.Scanner;
public class FileCopy {
    public static void main(String[] args) {

        // Prompt the user to input the source and target file
        System.out.println("Please enter the source file path: ");
        Scanner scanner = new Scanner(System.in);
        String sourcePath = scanner.nextLine();
        System.out.println("Please enter the target file path: ");
        String targetPath = scanner.nextLine();

        // Check that the source file exists and is not a directory
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            System.out.println("Error: The source file does not exist.");
            return;
        }
        if (sourceFile.isDirectory()) {
            System.out.println("Error: The source file is a directory.");
            return;
        }

        // Check that the target file is not a directory and does not exist already
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            System.out.println("Error: The target file is a directory.");
            return; }
        if (targetFile.exists()) {
            System.out.println("Error: The target file already exists.");
            return;
        }

        // Create any directories needed for the target file
        File targetDir = targetFile.getParentFile();
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // Copy the contents of the source file to the target file
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int length;
            long totalBytesCopied = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
                totalBytesCopied += length;
            }System.out.printf("%d bytes successfully copied from %s to %s%n",
                    totalBytesCopied, sourcePath, targetPath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}