
/**
 * Class to identify chunks of metadata from the larger metadataMass, which descibes
 * how the metadata returned from the Medal.tv api is returned in one large mass. This 
 * mass contains many pieces of individual clip metadata. 
 *
 * @author Anthony Lopez
 * @version4 7.14.23
 * 
 * Notes for 6.08.23:
 *  - Removed test constructor 
 *  - Removed readFile method as this class is no longer being used for IO
 *    operations
 *  - Removed all file related things in class
 *  - Replaced index1, index2... with more descriptive names
 *  - Added a constructor that takes a parameter to assign to the 'metadataMass' 
 *    String variable. Will maybe be used at a later date, alongside a private
 *    'setMetadataMass()' method for better encapsulation. 
 *    
 *    
 */
public class ChunkGetter
{
    private String metadataMass;
    private String identifiedChunk;
    private String identifiedFluff; 

    private int firstChunkIndex;
    private int lastChunkIndex;
    private int firstFluffIndex;
    private int lastFluffIndex;

    /**
     * Constructor for objects of class ChunkGetter
     */
    public ChunkGetter()
    {
        metadataMass = "";
    }

    /**
     * Constructor for objects of class ChunkGetter (with parameters)
     */
    public ChunkGetter(String metadataMass){
        this.metadataMass = metadataMass;
    }

    /**
     * Method that identifies the indexes of a metadata chunk and
     * assigns the chunk. If theres an exception (no more chunks to 
     * be found) then it sets identified chunk to nothing, which is
     * detected by the main method and then stops the program.
     * 
     * Notes for 7.14.23:
     *  - Need to find a better way of going about this... for all 
     *    indexing methods.
     */
    public void indexOfChunk(){
        firstChunkIndex = metadataMass.indexOf("{\"contentId");

        if(firstChunkIndex != -1){
            lastChunkIndex = metadataMass.indexOf("360'") + 4;
            identifiedChunk = metadataMass.substring(firstChunkIndex, lastChunkIndex);
        }else{
            identifiedChunk = "";
        }
    }

    /**
     * Method that does the same as above but identifies the last instance
     * of a chunk
     */
    public void lastIndexOfChunk() {
        firstChunkIndex = metadataMass.lastIndexOf("{\"contentId");

        if(firstChunkIndex != -1){
            lastChunkIndex = metadataMass.lastIndexOf("360'") + 4;
            identifiedChunk = metadataMass.substring(firstChunkIndex, lastChunkIndex + 4);
        }else{
            identifiedChunk = "";
        }
    }

    /**
     * Method that removes the identified chunk from the 
     * metadataMass after it has been assigned to identifiedChunk
     */
    public void removeChunk() {
        metadataMass = metadataMass.replace(identifiedChunk, "");
    }   

    /**
     * Method that identifies fluff (stuff I don't need)
     */
    public void indexOfFluff(){
        firstFluffIndex = metadataMass.indexOf("src");

        if(firstFluffIndex != -1){
            lastFluffIndex = metadataMass.indexOf("},{") + 2;
            identifiedFluff = metadataMass.substring(firstFluffIndex, lastFluffIndex);
        }else{
            System.out.println("-No More Fluff Found-\n");
        }
    }
    
    /**
     * Method that identifies the last instance of fluff
     */
    public void lastIndexOfFluff(){
        firstFluffIndex = metadataMass.lastIndexOf("src");

        if(firstFluffIndex != -1){
            lastFluffIndex = metadataMass.lastIndexOf("},{") + 2;
            identifiedFluff = metadataMass.substring(firstFluffIndex, lastFluffIndex);
        }else{
            System.out.println("-No More Fluff Found-\n");
        }
    }

    /**
     * Method that will remove fluff in order to speed up process
     * of searching for next chunk.
     * 
     * Notes for 7.14.23:
     *  - I want to actually test if removing fluff does anything for
     *    the performance of the program. 
     */
    public void removeFluff(){
        metadataMass = metadataMass.replace(identifiedFluff, "");
    }

    
    //set and get methods
    /**
     * Method to return identified chunk
     */
    public String getIdentifiedChunk(){
        return identifiedChunk;
    }

    /**
     * Method to set metadataMass
     */
    public void setMetadataMass(String metadataMass){
        this.metadataMass = metadataMass;
    }
}
