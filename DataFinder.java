import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.*;

/**
 * A class that deals with finding various pieces of information about a clip
 *
 * @author Anthony Lopez
 * @version5 7.14.23
 *    
 * 7.14.23 Notes:
 *  - Replacing all index variables with 'firstXIndex' and 'lastXIndex' for easier management since methods are called
 *    one after the other, and not at the same time though that could be a cool thing to implement (multithreading?)
 *  - Tried implementing multithreading in a previous version however I'm not sure it did anything to impact performace 
 *    or if I even properly implemented it. Either way I'll come back to this topic later.
 *    
 */
public class DataFinder
{    
    //each pair of indexes represents respective info being located by the class
    private int firstCIndex;
    private int lastCIndex;

    private int firstTIndex;
    private int lastTIndex;

    private int firstTNIndex;
    private int lastTNIndex;

    private int firstCTIndex;
    private int lastCTIndex;

    private String clipMetadata;
    private String pushResponse;

    private int clipNumber;
    private String clipUrl;
    private String clipTitle;
    private String clipThumbnailUrl;
    private String clipCreatedTimestamp;
    private String readableDate;
    private boolean isPrivate;

    private String clipID;

    private Date date;
    private SimpleDateFormat dateFormat;

    /**
     * Method that runs all locator info methods
     */
    public void locateAllInfo(){
        locateClipUrl();
        locateClipTitle();
        locateClipThumbnail();
        locateClipCreatedTimestamp();
    }

    /**
     * Method that runs all set info set methods
     */
    public void extractAndAssignAllInfo(){
        extractAndAssignUrl();
        extractAndAssignTitle();
        extractAndAssignThumbnail();
        extractAndAssignCreatedTimestamp();
        extractAndAssignClipID();
    }

    
    //locator methods
    //the reason numbers are being added and subtracted from indexes is so-
    //-it can properly locate each piece of info while also excluding fluff
    /**
     * Method to identify start and end of clip Url from metadata
     */
    private void locateClipUrl() {
        firstCIndex = clipMetadata.indexOf("ClipUrl") + 16;
        lastCIndex = clipMetadata.indexOf("embed") - 3;
    }

    /**
     * Method to identify title of clip
     */
    private void locateClipTitle() {
        firstTIndex = clipMetadata.indexOf("Title") + 8;
        lastTIndex = clipMetadata.indexOf("contentViews") - 3;
    }

    /**
     * Method to identify thumbnail of clip
     */
    private void locateClipThumbnail(){
        firstTNIndex = clipMetadata.indexOf("Thumbnail") + 12;
        lastTNIndex = clipMetadata.indexOf("width") + 9;
    }

    /**
     * Method to identify created timestamp of clip
     */
    private void locateClipCreatedTimestamp(){
        firstCTIndex = clipMetadata.indexOf("Timestamp") + 11;
        lastCTIndex = clipMetadata.indexOf("direct") - 2;
    }

    
    //extract and assign methods
    /**
     * Method to assign clip url with given indexes 
     */
    private void extractAndAssignUrl(){
        clipUrl = clipMetadata.substring(firstCIndex, lastCIndex);
    }

    /**
     * Method to assign clip title with given indexes
     */
    private void extractAndAssignTitle(){
        clipTitle = clipMetadata.substring(firstTIndex, lastTIndex);
        //removes all special characters, since Windows WOS
        clipTitle = clipTitle.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    /**
     * Method to assign clip thumbnail with given indexes
     */
    private void extractAndAssignThumbnail(){
        clipThumbnailUrl = clipMetadata.substring(firstTNIndex, lastTNIndex);
    }

    /**
     * Method to assign clip created timestamp with given indexes
     */
    private void extractAndAssignCreatedTimestamp(){
        clipCreatedTimestamp = clipMetadata.substring(firstCTIndex, lastCTIndex);
        convertTimestamp();
    }

    /**
     * Method to identify and assign clipID
     */
    public void extractAndAssignClipID() {
        //pattern object that looks for clipID by looking for "/clip/" followed by 1 or more digits
        Pattern pattern = Pattern.compile("/clip/(\\d+)/");
        //takes caller parameter and applies matcher method to find a matching pattern in the given parameter
        Matcher matcher = pattern.matcher(clipUrl);

        //"group(1)" is the found clipID
        if (matcher.find()) {
            clipID = matcher.group(1);
        }else{
            System.out.println("ClipID not found.");
        }
    }

    
    //set methods
    /**
     * Method to set clipMetadata 
     */
    public void setClipMetadata(String clipMetadata){
        this.clipMetadata = clipMetadata;
    }

    /**
     * Method to set pushResponse
     */
    public void setPushResponse(String pushResponse){
        this.pushResponse = pushResponse;
    }
    
    /**
     * Method to set clipNumber
     */
    public void setClipNumber(int clipNumber){
        this.clipNumber = clipNumber;
    }
    

    //other methods
    /**
     * Method to print all relevent clip info
     */
    public void printClipInfo(){
        System.out.println("\nClip Number: " + clipNumber + 
                           "\nTitle: " + clipTitle + 
                           "\nClip Url: " + clipUrl +
                           "\nThumbnail Url: " + clipThumbnailUrl + 
                           "\nDate clipped: " + readableDate + "\n");
    }

    /**
     * Method to convert UNIX time format into a more readable/standard time format
     */
    public void convertTimestamp(){
        date = new Date(Long.parseLong(clipCreatedTimestamp));
        dateFormat = new SimpleDateFormat("MM-dd-yyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        readableDate = dateFormat.format(date);
    }

    
    //set and return methods
    /**
     * Method to return clipTitle
     */
    public String getClipTitle(){
        return clipTitle;
    }
    
    /**
     * Method to return isPrivate
     */
    public boolean getIsPrivate(){
        return isPrivate;
    }
}
