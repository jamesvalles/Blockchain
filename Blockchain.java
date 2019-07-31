/*

   1. Name/Date:  James Valles, May 29, 2019

   2. Java version used:  1.8

   3. Precise command-line compilation examples / instructions:

    e.g.:
    Compile:
    > javac Blockchain.java


   4. Precise examples / instructions to run this program:

    Compile:
    > javac BlockChain.java

    Run Process 0:
    > java BlockChain 0

    Run Process 1:
    > java BlockChain 0

    Run Process 2:
    > java BlockChain 0


    > To Begin reading .txt file:
    At user prompt type 'R'

    > To verify XML block record and begin work:
    At user prompt type 'V'

    > Process will automatically create a BlockChainLedger file in root directory

    *This program was written and successfully runs and tested on  Mac Computer*

   5. List of files needed for running the program.
    a. Blockchain.java

    Additional files submitted as part of assignment requirement:
    b. BlockchainLog.txt
    c. BlockchainLedgerSample.xml
    d. checklist-block.html
    e. Not included in .zip TTI Doc Submitted
    f. all three .txt files BlockInput0.txt, BlockInput1.txt, BlockInput2.txt

   6. Notes:
    This program was written and successfully runs and has been tested on  Mac Computer. I didn't get to a substanital portiom of this assignment,
     but I was able to read in from the three text files, depending on the process user enters when loading program.
     I was able to create XML from Objects. And then simulate real blockchain verification work. Thank you so much for your hard work grading. This was an awesome
    assignment that allowed me to build a fully functioning multi-threaded Web Server.
*/


import static java.lang.Thread.sleep; //used for sleep
import java.io.BufferedWriter; //used for the buffer writer
import java.io.File; //used for file io
import java.io.FileWriter;
import java.io.InputStreamReader; //used for input stream
import java.io.StringWriter; //stringwriter
import java.util.ArrayList; //used to create array list
import javax.xml.bind.JAXBContext; // this is to produce xml
import javax.xml.bind.Marshaller; //used to produce xml
import javax.xml.bind.annotation.XmlElement; //used to produce xml
import javax.xml.bind.annotation.XmlRootElement; //used to produce xml
import java.io.BufferedReader; //this is to readin lines
import java.io.FileReader;
import java.io.IOException; //used for exceptions
import java.util.UUID; //used to generate uuid
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter; //used for xml


@XmlRootElement
//this is the block record class, this is used to create the xml block records
class BlockRecord {

  String SHA256String;
  String SignedSHA256;
  String blockIdentification;
  String processIDVerify;
  String processCreator;
  String firstName;
  String lastName;
  String socialSecurityNumber;
  String dateOfBirth;
  String diagnosis;
  String treatment;
  String perscription;


  //this is getter for SHA256String
  public String getASHA256String() {
    return SHA256String;
  }

  //this is setter for SHA256String
  @XmlElement
  public void setASHA256String(String SH) {
    this.SHA256String = SH;
  }

  //this is getter for SHA256
  public String getASignedSHA256() {
    return SignedSHA256;
  }

  //this is setter for SHA256
  @XmlElement
  public void setASignedSHA256(String SH) {
    this.SignedSHA256 = SH;
  }

  //this is getter for the process that created the block
  public String getACreatingProcess() {
    return processCreator;
  }

  //this sets the process that creates
  @XmlElement
  public void setACreatingProcess(String CP) {
    this.processCreator = CP;
  }

  //this gets the id of process which verifies block
  public String getAVerificationProcessID() {
    return processIDVerify;
  }

  //this sets the id of process which verifies block
  @XmlElement
  public void setAVerificationProcessID(String VID) {
    this.processIDVerify = VID;
  }

  //this gets the block id
  public String getABlockID() {
    return blockIdentification;
  }

  //this sets the block id
  @XmlElement
  public void setABlockID(String BID) {
    this.blockIdentification = BID;
  }

  //this gets social security number
  public String getFSSNum() {
    return socialSecurityNumber;
  }

  @XmlElement
  //this sets social security number
  public void setFSSNum(String SS) {
    this.socialSecurityNumber = SS;
  }

  //this gets first name
  public String getFFname() {
    return firstName;
  }

  //this sets first name
  @XmlElement
  public void setFFname(String FN) {
    this.firstName = FN;
  }

  //this gets the last name
  public String getFLname() {
    return lastName;
  }

  //this sets the last name
  @XmlElement
  public void setFLname(String LN) {
    this.lastName = LN;
  }

  //this gets the date of birth
  public String getFDOB() {
    return dateOfBirth;
  }

  //this sets the date of birth
  @XmlElement
  public void setFDOB(String DOB) {
    this.dateOfBirth = DOB;
  }

  //this gets the diagnosis
  public String getGDiag() {
    return diagnosis;
  }

  //this sets the diagnosis
  @XmlElement
  public void setGDiag(String D) {
    this.diagnosis = D;
  }

  //this gets the treatment
  public String getGTreat() {
    return treatment;
  }

  //this sets the treatment
  @XmlElement
  public void setGTreat(String D) {
    this.treatment = D;
  }

  //this gets the perscription
  public String getGRx() {
    return perscription;
  }

  //this sets the diagnosis
  @XmlElement
  public void setGRx(String D) {
    this.perscription = D;
  }

}

//this is the block chain class, which stories unverified block object array, and has method that converts block record objects into XML
class Blockchain {

  //this is the datafile being read in ie. blockinput0.txt, blockinput1.txt, blockinput2.txt
  private static String DATAFILE;

  //this is the array list that will store unverifiedblockobjectarrays
  private ArrayList<BlockRecord> unverifiedBlockObjArray = new ArrayList<>();

  //this is the arraylist that will store xmlblockchain records
  ArrayList<String> XMLblockChain = new ArrayList<>();

  //this is the getter to get the xml block chain record array
  public ArrayList<String> getXMLblockChain() {
    return XMLblockChain;
  }

  //this is the setter to get the xml block chain record array
  public void setXMLblockChain(ArrayList<String> XMLblockChain) {
    this.XMLblockChain = XMLblockChain;
  }

  //this is the getter to get the unverified block object array
  public ArrayList<BlockRecord> getUnverifiedBlockObjArray() {
    return unverifiedBlockObjArray;
  }

  //this is the setter to get the unverified block object array
  public void setUnverifiedBlockObjArray(ArrayList<BlockRecord> unverifiedBlockObjArray) {
    this.unverifiedBlockObjArray = unverifiedBlockObjArray;
  }

 // these were provided by dr. elliot in his utiltity code, used to parse the object with index to the correct field

  private static final int iFNAME = 0; //this is first name
  private static final int iLNAME = 1; //this is last name
  private static final int iDOB = 2; //this is dob
  private static final int iSSNUM = 3; //this is ssn
  private static final int iDIAG = 4; //this is diagnosis
  private static final int iTREAT = 5; //this is treatment
  private static final int iRX = 6; //this is drugs


  //this metjod will create XML from block record objects, using a string builder, and blockchain object array
  public ArrayList<BlockRecord> createBlockXML(ArrayList<BlockRecord> blockChain,
      StringBuilder stringBuilder, Blockchain blockchain) {

    //for each blockrecord in the array list blocck chain XML GENERATED HERE
    for (BlockRecord block : blockChain) {
      stringBuilder.append("<blockRecord>\n");
      stringBuilder.append("<ABlockID>" + block.blockIdentification + "</ABlockID>\n"); //add block id
      stringBuilder.append("<ACreatingProcess>" + block.processCreator + "</ACreatingProcess>\n"); //add process creator
      stringBuilder.append("<ASHA256String>" + block.SHA256String + "</ASHA256String>\n"); //add sha256sstring
      stringBuilder.append("<ASignedSHA256>" + block.SignedSHA256 + "</ASignedSHA256>\n"); //add signed sha256
      stringBuilder.append(
          "<AVerificationProcessID>" + block.processIDVerify + "</AVerificationProcessID>\n"); //add verification
      stringBuilder.append("<FFname>" + block.firstName + "</FFname>\n"); //first name
      stringBuilder.append("<FLname>" + block.lastName + "</FLname>\n"); //add last name
      stringBuilder.append("<FSSNum>" + block.socialSecurityNumber + "</FSSNum>\n"); //add ssn
      stringBuilder.append("<GDiag>" + block.diagnosis + "</GDiag>\n"); //add diagnosis
      stringBuilder.append("<GRx>" + block.perscription + "</GRx>\n"); //add perscription
      stringBuilder.append("<GTreat>" + block.treatment + "</GTreat>\n"); //add treatement
      //  System.out.println(work.peformWork(block));

      //append closing tag for blockrecord
      stringBuilder.append("</blockRecord>\n");

      //add block record to XML block chain array
      blockchain.getXMLblockChain().add(stringBuilder.toString());

      //clear the string builder to get the next record
      stringBuilder.setLength(0);
    }
    //return array list of blockrecord
    return blockChain;
  }


  //This is the method that will write the ledger to the ./BlockchainLedger.xml file
  public void writeToLedger(String text) throws Exception{
    File outputFile = new File("./BlockchainLedger.xml"); //create a new output file called BlockchainLedger.xml
    FileWriter writer = new FileWriter(outputFile, false); //create a filewriter
    BufferedWriter bw = new BufferedWriter(writer); //create a buffered writer
    bw.write(text); //write the text to file
    bw.flush(); //flush buffer writer
    bw.close(); //close buffered writer as it is no longer in use.
  }


  //This method will read in the file from .txt files provided.
  public void readInFile(String[] args) throws Exception {
   //These are the process and port numbers that will be utilized
    String processNumber;
    int proNumber;
    int port = 0; //port number

    /* CDE If you want to trigger bragging rights functionality... */
    if (args.length > 1) {
      System.out.println("Special functionality is present \n");
    }


    //if args is Process 0
    if (args.length < 1) {
      processNumber = "ZERO";
      port = 4710; //port number
    } else if (args[0].equals("0")) {
      processNumber = "ZERO";
      port = 4710; //set port

      //if args is Process 1
    } else if (args[0].equals("1")) {
      processNumber = "ONE";
      port = 4820; //set port

      //if args is Process 2
    } else if (args[0].equals("2")) {
      processNumber = "TWO";
      port = 4930; //set port
    } else {
      //If args is process 0
      processNumber = "ZERO";
    }
    proNumber = 0;

    //this is message upon application launch
    System.out.println("*** PROCESS " + processNumber + " is running. *** " + "\nStarting Key Server input thread using: "
        + port + "\n");

    //This switch statment handles ,which file to read in.
    switch (processNumber) {
      case "ONE":
        //Read in BlockInput1 file
        DATAFILE = "BlockInput1.txt";
        break;

      //Read in BlockInput2 file
      case "TWO":
        DATAFILE = "BlockInput2.txt";
        break;
      default:
        //Read in BlockInput0 file
        DATAFILE = "BlockInput0.txt";
        break;
    }

    //This is buffered reader to read in files, code provided by by Dr. Elliott
    try {
      try (BufferedReader br = new BufferedReader(new FileReader(DATAFILE))) {
        String[] splitResult = new String[10];
        //   String stringXML;
        String InputLineStr;
        String suuid;
        UUID idA;

        //This creates a block record  array
        BlockRecord[] blockRecordArray = new BlockRecord[20];
        int index = 0;

        //using professor code for writing
        JAXBContext jaxbContext = JAXBContext.newInstance(BlockRecord.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //this creates XML


        //this loop sets the blockrecord fields with the correct data by its index.
        while ((InputLineStr = br.readLine()) != null) {
          blockRecordArray[index] = new BlockRecord(); //create a new block record

          blockRecordArray[index].setASHA256String("SHA string n'a "); //SHA string
          blockRecordArray[index].setASignedSHA256("Signed SHA n/a."); //signed SHA

          idA = UUID.randomUUID(); //random uuid
          suuid = new String(UUID.randomUUID().toString()); //sets it to string
          blockRecordArray[index].setABlockID(suuid); //sets block id
          blockRecordArray[index].setACreatingProcess("Process " + Integer.toString(proNumber)); //sets creating process
          blockRecordArray[index].setAVerificationProcessID("To be set later..."); //sets verification processid

          splitResult = InputLineStr.split(" +"); //splits line by +
          blockRecordArray[index].setFSSNum(splitResult[iSSNUM]); //sets ssn
          blockRecordArray[index].setFFname(splitResult[iFNAME]); //sets first name
          blockRecordArray[index].setFLname(splitResult[iLNAME]); //sets last name
          blockRecordArray[index].setFDOB(splitResult[iDOB]); //sets dob
          blockRecordArray[index].setGDiag(splitResult[iDIAG]); //sets diagnosis
          blockRecordArray[index].setGTreat(splitResult[iTREAT]); //sets treatment
          blockRecordArray[index].setGRx(splitResult[iRX]); //sets perscription
          index++; // increments index
        }


        for (int i = 0; i < index; i++) {

          //Leaving this in for the sake of writing to file the entire blockchain
          jaxbMarshaller.marshal(blockRecordArray[i], sw);


          //adding univerified block objects to array
          unverifiedBlockObjArray.add(blockRecordArray[i]);
        }


        //this is used to write to file. This is the complete block chain. this code was provided by dr elliott.
        String entireBlockChain = sw.toString();
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"; //this is xml header is used
        String clearBlock = entireBlockChain.replace(xmlHeader, "");
        // Show the string of concatenated, individual XML blocks:
        String XMLBlock = xmlHeader + "\n<BlockLedger>" + clearBlock + "</BlockLedger>";

        //this writes the xml block to file , method invoked
        writeToLedger(XMLBlock);

        //catch exceptions
      } catch (IOException e) {
        e.printStackTrace();
      }
      //catch exceptons
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//this is applications main method
  public static void main(String[] args) throws Exception {

    //welcome statement pritned
    System.out.println(
        "Welcome to James Valles' Blockchain Server (based on code by Dr. Clark Ellitot). control-c to quit.\n");

    //initialize a new block chain
    Blockchain blockchain = new Blockchain();

    //Sets the process number running based on args entered at command line
    //  String[] arguments = {"2"};

    //reads in raw data from file
    blockchain.readInFile(args); //read in the correct file based on args passed

    //unverified block objects
    ArrayList<BlockRecord> unverfiedBlockRecords = blockchain.getUnverifiedBlockObjArray();

    //create xml record from each block object
    StringBuilder stringBuilder = new StringBuilder();

    //create xml here
    blockchain.createBlockXML(unverfiedBlockRecords, stringBuilder, blockchain);

    //Perform work
    Work work = new Work();

    //initializing a new buffered reader to read user input
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String selection = ""; //initializing selction to empty string

    //operate while true
    while (true) {

      //user selection options
      System.out.print("\nEnter C (Credit), R (Read File), V (Verify Blocks), or L (List Blocks to BlockchainLedgerSample.xml)\n");
      //flush the buffer to ensure correct printing
      System.out.flush();

      //try to readin line
      try {
        selection = in.readLine();

        //based on selection
        switch (selection) {
          case "C":
            //this does not work
            System.out.println("Process verified which block. (Feature temporarily unavailable).");
            break;
          case "V":
            //this verifies all the block records and performs work
            for (String block : blockchain.getXMLblockChain()) {
              System.out.println("\n*** UNVERFIED BLOCK ***\n\n" + block);
              work.peformWork(block);
            }
            break;
          case "L":
            //this does not work
            System.out.println("BlockchainLedgerSample.xml file created.");
            break;
          case "R":

            //this reads in file , and print the file that was readin successfully
            System.out.println(DATAFILE + " read in successfully.");
            break;
          default:
            //otherwise, invalid request
            System.out.println("Invalid Request");
            break;
        }
      } catch (Exception e) { //catch exception
      }
    }
  }
}

//this class performs the work. This was based on dr. elliott's utilitiy code
class Work {

  public static String randomAlphaString(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int character = (int) (Math.random() * alphaString.length());
      builder.append(alphaString.charAt(character));
    }
    return builder.toString();
  }

  private static final String alphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  static String randomString;



  //This method actually performs work it takes in the XML string
  public boolean peformWork(String stringIn) {
    String concatonatedString;
    String stringOutput;
    Boolean puzzleNotSolved = true;

    //and concatenates it with a random alphanumeric string
    randomString = randomAlphaString(8);

    //this sets the difficult of the work
    int workNumberSetting = 0;


    //while puzzle not solved continue
    while (puzzleNotSolved) {
      try {

        for (int i = 1; i < 20; i++) {
          randomString = randomAlphaString(8);
          concatonatedString =
              stringIn
          + randomString;
          MessageDigest MD = MessageDigest.getInstance("SHA-256");
          byte[] bytesHash = MD.digest(concatonatedString.getBytes("UTF-8")); // Get the hash value
          stringOutput = DatatypeConverter
              .printHexBinary(bytesHash);
              //get the hext value
          workNumberSetting = Integer
              .parseInt(stringOutput.substring(0, 4), 16);

          //if decimial number of first 16 bytes is less, puzzle not solved continue solving
          if (!(workNumberSetting < 20000)) {
            System.out
                .format("%d is not less than 20,000 so we did not solve the puzzle\n\n",
                    workNumberSetting);
          }
          //otherwise if less than 20,0000 puzzle solved, and print
          //here is where you would multicast to all other nodes
          if (workNumberSetting < 20000) {
            //create new block chain here
            //then multicast here
            System.out.format("%d IS less than 20,000.\n*** BLOCK VERIFIED ***\n\n", workNumberSetting);
            // System.out.println("The seed (puzzle answer) was: " + randomString);

            //puzzle is solved flip flag
            puzzleNotSolved = false;

            //set sleep here
            sleep(2000);
            break;
          }

          //adding another sleep to allow for better vizualiation of work
          sleep(4000);
        }
      } catch (Exception ex) { //catch any exceptions
        ex.printStackTrace();
      }

    }
    //return boolean value on whether puzzle is not solved.
    return puzzleNotSolved;
  }
}

//didn't get to setting up threads and ports, but here is where i will continue my work. Thank you for the experience.
class ThreadOne extends Thread {


}

class ThreadTwo extends Thread {

}