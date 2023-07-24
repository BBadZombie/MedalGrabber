import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Class that works with file input and output. Mainly concerning some text documents,
 * including 'metadataMass' and 'ListOfURLs', which this class reads and writes to. 
 *
 * @author Anthony Lopez
 * @version4 6.08.23
 * 
 * Notes for 6.08.23:
 *  - Added static modifer to METADATA_FILE and CLIP_INFO_FILE because they
 *    wont be changed and if they were to change it would break the class
 *  - At this moment in time FileIOWorker doesnt do much for the program as
 *    a whole. Its more for reliable testing and debugging with the program
 *    in separate chunks. 
 *  - Not sure whether to have the 'ListOfURLs.txt' doc be exclusively for Urls, 
 *    for all info, or to have both. 
 *  - Modified both return LinkedList methodsto specify that the LinkedList being 
 *    returned will contain elements of type String
 *  - Modified both readFile methods to use try-with-resources statement
 *  - Modified both writeToFile methods to use a stringBuilder instead of 
 *    concatenation
 */
public class FileIOWorker {
    private static final String METADATA_FILE = "metadataMass.txt";
    private static final String CLIP_INFO_FILE = "ListOfURLs.txt";
    private static final String LOG_FILE = "log.txt";
    private String metadataMass;
    private String clipInfo;

    private LinkedList<String> metadataList;
    private LinkedList<String> clipUrlList;

    /**
     * Constructor for objects of class IOWork
     */
    public FileIOWorker(){
        metadataList = new LinkedList<String>();
        clipUrlList = new LinkedList<String>();
    }

    /**
     * Method that reads info from metadata.txt and assigns it to metadataMass
     */
    public void readMetadataFile() {
        try (Scanner fileScanner = new Scanner(new File(METADATA_FILE))) {
            while (fileScanner.hasNextLine()) {
                metadataMass += fileScanner.nextLine();
                System.out.println("-Successfully Read " + METADATA_FILE + " File-\n");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Method that reads info from ListOfURLs.txt and assigns it to infoMass
     */
    public void readInfoFile() {
        try (Scanner fileScanner = new Scanner(new File(CLIP_INFO_FILE))) {
            while (fileScanner.hasNextLine()) {
                clipUrlList.add(fileScanner.nextLine());
                //System.out.println("Successfully read " + CLIP_INFO_FILE + " file");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Method that writes given info to metadataMass.txt
     */
    public void writeToMetadataFile(String metadata) {
        try (FileWriter fileWriter = new FileWriter(METADATA_FILE, true)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(metadata).append("\n");
            fileWriter.write(stringBuilder.toString());
            //System.out.println("-Wrote to " + METADATA_FILE + " file-");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Method that writes given info to ListOfURLs.txt
     */
    public void writeToInfoFile(String clipInfo) {
        try (FileWriter fileWriter = new FileWriter(CLIP_INFO_FILE, true)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(clipInfo).append("\n");
            fileWriter.write(stringBuilder.toString());
            System.out.println("-Wrote to " + CLIP_INFO_FILE + " file-");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Method to write to write to given file
     */
    public void writeToLogFile(String logInfo){
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(logInfo).append("\n");
            fileWriter.write(stringBuilder.toString());
            //System.out.println("-Wrote to " + CLIP_INFO_FILE + " file-");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Method to print metadataList
     */
    public void printMetadataList(){
        int piecesOfMetadata = 0;
        for(String metadata: metadataList){
            piecesOfMetadata += 1;
            System.out.println("Metadata: " + metadata);
        }
        System.out.println("Items in metadataList: " + piecesOfMetadata);
    }
    
    
    //get methods
    /**
     * Method that returns metadataMass
     */
    public String getMetadataMass(){
        return metadataMass;
    }

    /**
     * Method that returns listOfURLsMass
     */
    public String getClipInfo(){
        return clipInfo;
    }

    /**
     * Method that returns metadataList
     */
    public LinkedList<String> getMetadataList(){
        return metadataList;
    }

    /**
     * Method that returns clipUrlList
     */
    public LinkedList<String> getClipUrlList(){
        return clipUrlList;
    }
}
