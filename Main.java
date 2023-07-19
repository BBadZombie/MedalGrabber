import java.util.LinkedList;
import java.util.Iterator;

/**
 * Combines all of the utilities of other classes in the project to neatly organize information
 *
 * @author Anthony Lopez
 * @version9 7.17.23
 * 
 * Notes for whole project: 
 *  - Want to add api key getter to metadata grabber instead of manually ge
 */
public class Main
{
    /**
     * Main method for class Main
     */
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        System.out.println("-Program Started-");
        
        MetadataGrabber MG = new MetadataGrabber();
        FileIOWorker FIOW = new FileIOWorker();
        ChunkGetter CG = new ChunkGetter();
        DataFinder DF = new DataFinder();

        int limit = 1000;
        int offset = 0;
        int count = 0;
        String identifiedChunk = "";
        System.out.println("-Initializing Complete-");
        
        System.out.println("-Fetching Metadata-\n");
        MG.setLimit(limit);
        MG.setOffset(offset);
        MG.fetchClipMetadata(); 
        String metadataMass = MG.getMetadataMass(); 
        FIOW.writeToMetadataFile(metadataMass);

        CG.setMetadataMass(metadataMass);
        while(count == 0 || identifiedChunk != ""){
            try{
                CG.indexOfChunk();
                CG.removeChunk();
                CG.indexOfFluff();
                CG.removeFluff();
                identifiedChunk = CG.getIdentifiedChunk();
                count++;

                DF.setClipMetadata(identifiedChunk);
                DF.locateAllInfo();
                DF.extractAndAssignAllInfo();
                
                System.out.println("Clip #" + count);
                DF.printClipInfo();
                
                FIOW.writeToLogFile("Clip #" + (count + 1)); 
                FIOW.writeToLogFile("Push Response Code: " + MG.getResponseCode() + "\n");
            }catch(Exception e){
                System.out.println("Exception");
            }
        }
        System.out.println("Program Done");

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        FIOW.writeToLogFile("Total Run Time: " + totalTime + " milliseconds");
        FIOW.writeToLogFile("Total Run Time: " + totalTime/1000 + " seconds");
        System.out.println("Total Run Time: " + totalTime + " milliseconds");
        System.out.println("Total Run Time: " + totalTime/1000 + " seconds");
    }
}
