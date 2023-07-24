import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Program that returns metadata for however many requested user clips
 * Ones userID can be found under the response tab of inspect element on the Medal.tv webpage
 * 
 * @author Anthony Lopez
 * @version5 7.14.23
 */
public class MetadataGrabber implements LoggingUtility{
    private String apiKey;
    private String userID;
    private String metadataMass;
    private int limit;
    private int offset;

    //all of above data will be combined into a custom curl request below
    private String endpoint;

    //must be declared before fetch method
    private int responseCode;

    /**
     * Constructor for objects of class MetadataGrabber
     * The limit + offset states cannot exceed 1000
     */
    public MetadataGrabber(){
        //my apiKey and userID
        apiKey = "pub_ERFAc1soguXM2dmD1ccjDwRSaSSj9upn";
        userID = "12597";
        limit = 10;
        offset = 10;

        setEndpoint();
        initializeLogger();
    }

    /**
     * Constructor for objects of class MetadataGrabber (with parameters)
     */
    public MetadataGrabber(String apiKey, String userID){
        this.apiKey = apiKey;
        this.userID = userID;

        setEndpoint();
        initializeLogger();
    }

    /**
     * Method to send a get request for clip metadata using Medal.tv api
     */
    public void fetchClipMetadata(){
        try{
            //after it has been created you CANNOT change the endpoint without creating a new instance
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", apiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            responseCode = conn.getResponseCode();
            metadataMass = response.toString();
        }catch(MalformedURLException m){
            printMalformedUrlException(m);
        }catch(IOException i){
            printIOException(i);
        }catch(IllegalArgumentException ia){
            printIllegalArgumentException(ia);
        }finally{
            LoggingUtility.logConfig();
        }
        
        LoggingUtility.logConfigOnShutdown();
    }

    
    //exception error methods
    //need to move these methods to LoggingUtility
    /**
     * Method that prints and logs relevent info regarding MalforedURLException's
     */
    private void printMalformedUrlException(MalformedURLException m){
        System.out.println("Error: URL Not Properly Formed: " + responseCode);
        System.out.println("Error Message: " + m.getMessage());
        m.printStackTrace();
    }

    /**
     * Method that prints and logs relevent info regarding IOException's
     */
    private void printIOException(IOException i){
        System.out.println("Error: Requesting Metadata: " + responseCode);
        System.out.println("Error Message: " + i.getMessage());
        i.printStackTrace();
    }

    /**
     * Method that print and logs relevent info regarding IllegalArgumentException's
     */
    private void printIllegalArgumentException(IllegalArgumentException ia){
        System.out.println("Error: Invalid Argument: " + responseCode);
        System.out.println("Error message: " + ia.getMessage());
        ia.printStackTrace();
    }
    

    //set and get methods
    /**
     * Method to set apiKey state. Also calls setEndpoint.
     */
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
        setEndpoint();
    }

    /**
     * Method to set userID state. Also calls setEndpoint.
     */
    public void setUserID(String userID){
        this.userID = userID;
        setEndpoint();
    }

    /**
     * Method to set offset state. Also calls setEndpoint.
     */
    public void setOffset(int offset){
        this.offset = offset;
        setEndpoint();
    }

    /**
     * Method to set limit state. Also calls setEndpoint.
     */
    public void setLimit(int limit){
        this.limit = limit;
        setEndpoint();
    }

    /**
     * Method to update state of endpoint
     */
    public void setEndpoint(){
        endpoint = "https://developers.medal.tv/v1/latest?" + 
        "userId=" + userID + 
        "&offset=" + offset + 
        "&limit=" + limit;
    }

    /**
     * Method to return metadataMass
     */
    public String getMetadataMass() {
        return metadataMass;
    }
    
    /**
     * Method to return response code
     */
    public int getResponseCode(){
        return responseCode;
    }

    
    //These print methods are for debugging when necessary
    /**
     * Method to print endpoint to terminal
     */
    public void printEndpoint(){
        System.out.println(endpoint);
    }

    /**
     * Method to print metadata to terminal
     */
    public void printMetadataMass(){
        System.out.println(metadataMass);
    }
}
