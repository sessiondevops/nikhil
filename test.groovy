def alert = com.eviware.soapui.support.UISupport;
//Define a file pointer for groovy to handle the file operations.
def inputFile = new File("http://13.52.98.219:8081/repository/maven_nikhil/com/marsh/et2/0.0.3-SNAPSHOT/maven-metadata.xml")
if(!inputFile.exists())
{
    //Display an alert if the file is not found.
    alert.showInfoMessage("Input File not found!");   
}
else
{
    //Read and parse XML file and store it into a variable
    def InputXML = new XmlParser().parseText(inputFile.text)
    //Find Filter XML nodes based on a condition
    
    InputRow.each{
            //Display the value of name node from the filtered record
            log.info(it.groupId.text());
        }
} 
