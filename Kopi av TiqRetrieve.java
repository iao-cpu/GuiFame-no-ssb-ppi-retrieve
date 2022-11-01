package no.ssb.ppi.retrieve;

import no.ssb.ppi.connection.*;
import com.fame.timeiq.*;
import no.ssb.ppi.start.TiqTest;
import com.fame.timeiq.data.ArrayAdapter;
import com.fame.timeiq.data.ObservationList;
import com.fame.timeiq.data.Observation;
import com.fame.timeiq.dates.RegularCalendar;
import com.fame.timeiq.dates.SimpleCalendar;
import com.fame.timeiq.dates.TiqDateFormat;
import com.fame.timeiq.persistence.Connection;
import com.fame.timeiq.persistence.ConnectionFailedChkException;
import com.fame.timeiq.persistence.DataStoreOpenChkException;
import com.fame.timeiq.persistence.DateAlignmentChkException;
import com.fame.timeiq.persistence.ObjectAccessChkException;
import com.fame.timeiq.persistence.Server;
import com.fame.timeiq.persistence.Session;
import com.fame.timeiq.tools.charts.beans.FameTiqViews;
import com.fame.timeiq.TiqConstants;
import com.fame.timeiq.functors.*;
import com.fame.timeiq.functors.stats.Annpct;
import com.fame.timeiq.functors.stats.Pct;
import com.fame.timeiq.functors.operators.Divide;
import com.fame.timeiq.functors.date.ShiftYear;
import com.fame.timeiq.functors.operators.Multiply;
import com.fame.timeiq.functors.operators.Subtract;

import no.ssb.ppi.tiqviews.TiqViewsData;
import no.ssb.ppi.apoint.APChkException;
import no.ssb.ppi.apoint.AccessPointClient;
//import no.ssb.ppi.connection.*;
import no.ssb.ppi.connection.ConnectMcadbs;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OptionalDataException;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Iterator;
import java.util.Locale;
//import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

import no.ssb.ppi.start.TiqTest;

public class TiqRetrieve {

public TiqObject tiq1, tiq2, tiq3, tiq4, tiq5, tiq6, tiq7, tiq8, tiq9, tiq10, tiq11, tiq12, tiq13, tiq14, tiq15, tiq16, tiq17, tiq18, tiq19, tiq20, tiqCase, ordreSerCase1, ordreForspCase1;		 
public TiqObject[] ordreSerCase, ordreForspCase;
public TiqObject tiqChart1, tiqChart2, tiqChart3, tiqChart4, tiqChart5, tiqChart6, tiqChart7, tiqChart8;
public TiqView[] tiqview, tiqview1, tiqview2, tiqview3, tiqview4, tiqview5,tiqview6, tiqview7, tiqview8, tiqview10, tiqLineView;

public TiqView  divideView, subtractView, ytypctView, shiftView, shiftView1, ordreShiftView, ordreShiftView1, ordreDivideView, ordreSubstractView, ordreYtypctView;
public long startIndex, endIndex;

public ObservationList oL1, funcOl, caseOl, ordreSerCaseOl, ordreSerCaseOl1, ordreForspCaseOl, ordreSerOl, ordreSerOll;

public Observation obserLast, pctBeforeLast, pctLast, ytyBeforeLast, ytyLast, ordreYtypctLast;
public Object obserLastValue, pctBeforeLastValue, pctLastValue, ytyBeforeLastValue, ytyLastValue, ordreYtyLastValue;

public ArrayAdapter tiqCaseArray, ordreSerCaseArray, ordreSerCaseArray1, ordreForspCaseArray;
public TiqViewsData tiqviewsdata, tiqviewsdata1, tiqviewsdata2, tiqviewsdata3, tiqviewsdata4, tiqviewsdata5, tiqviewsdata6, tiqviewsdata7,tiqviewsdata8, tiqLineData;

public DecimalFormat formatValue, formatValue1;
public String tiqValue, funcFormat, pctValue1, pctValue2, ytypctValue1, ytypctValue2;
public String [] funcValues, funcIndexes;
public String [] spalteValues, spalteValues1, dateValues;
public Object[][] tableData;
public String [] tiqIndexes1;
public int row;      

public static final int q = 50;
public static final DateFormat dfm = new SimpleDateFormat("yyyy:MM");

public Pct pct;
public Annpct annpct;
public Divide divide, ordreDivide;
public ShiftYear shiftyear, ordreshiftyear;
public Multiply multiply, ordreMultiply;
public Subtract subtract, ordreSubstract;

//Variables to hold ordre series names, metadta and values
public String [] ordreSerNames;
public String [] ordreSerNames1;
public String [] ordreForspNames;
public String [] ordreSerValues;
public Object [] ordreSerFormat;
public String [] ordreSerTab2Names;

public TiqObject[] ordreTiqObj, ordreTiqObj1, ordreApObj;
public TiqView[] ordreTiqView, ordreTiqView1;
public String substr, substr1,forspStr;
public Vector ordreSerVector, ordreForspVector;
public int ordreTab1Rows;
public int ordreTab1Cols;

public String[][] ordreTabData1, ordreTabData2;

public NumberFormat nft;
public DecimalFormat twoDigits, twoDigits1;
public Locale locale;
public static final NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
public DefaultTableModel tableModel;
public JTable jtable;
public String mySuperscript = "<html>Næring<sup>1)</sup></html>";

public String fnoteh1 = "<html><sup>2)</sup>Pst. år</html>";
public String fnoteh2 = "<html><sup>3)</sup>Pst. kv</html>";

public String []colHeader1 = {"Ordrestatistikk, 4. kv. 2010\n    \n\n\nOrdretabell 1 (Verditall, ordretilgang), ujustert. 2005 = 100. \n    \n" + mySuperscript, " \n    \n\n\n4.kv 2009", 
			" \n    \n\n\n1.kv 2010", "Verditall\n    \n\n\n2.kv 2010",  " \n    \n\n\n3.kv 2010", " \n    \n\n\n4.kv 2010", fnoteh1+ "\nEndring fra i fjor (prosent)\n4.kv 2010/4.kv 2009", fnoteh2+"\nEndring fra forrige kv (prosent)\n4.kv 2010/3.kv.2010"};

public String [] colHeader2 = {"Ordrestatistikk, 4. kv. 2010\n    \n\n\nOrdretabell 2 (Verditall, ordrereserve), ujustert. 2005 = 100. \n    \n" + mySuperscript, " \n    \n\n\n4.kv 2009", 
		" \n    \n\n\n1.kv 2010", "Verditall\n    \n\n\n2.kv 2010",  " \n    \n\n\n3.kv 2010", " \n    \n\n\n4.kv 2010", fnoteh1+ "\nEndring fra i fjor (prosent)\n4.kv 2010/4.kv 2009", fnoteh2+"\nEndring fra forrige kv (prosent)\n4.kv 2010/3.kv.2010"};

public String [] colHeader3 = {"Lagerstatistikk, 4. kv. 2010\n    \n\n\nLagertabell 1 (Verdiindeks, lagerbeholdning), ujustert. 2005 = 100. \n    \n" + mySuperscript, " \n    \n\n\n4.kv 2009", 
		" \n    \n\n\n1.kv 2010", "Verdiindeks\n    \n\n\n2.kv 2010",  " \n    \n\n\n3.kv 2010", " \n    \n\n\n4.kv 2010", fnoteh1+ "\nEndring fra i fjor (prosent)\n4.kv 2010/4.kv 2009", fnoteh2+"\nEndring fra forrige kv (prosent)\n4.kv 2010/3.kv.2010"};

public String [] colHeader4 = {"Lagerstatistikk, 4. kv. 2010\n    \n\n\nLagertabell 2 (Volumindeks, lagerbeholdning), ujustert. 2005 = 100. \n    \n" + mySuperscript, " \n    \n\n\n4.kv 2009", 
		" \n    \n\n\n1.kv 2010", "Volumindeks\n    \n\n\n2.kv 2010",  " \n    \n\n\n3.kv 2010", " \n    \n\n\n4.kv 2010", fnoteh1+ "\nEndring fra i fjor (prosent)\n4.kv 2010/4.kv 2009", fnoteh2+"\nEndring fra forrige kv (prosent)\n4.kv 2010/3.kv.2010"};


public JPanel ordreTabPanel;
public TableColumn column0, column1, column2, column3, column4, column5, column6, column7, column8;
public JScrollPane scroll;
public long [] ordreDates, ordreDates1;
public int len, len1; 
public int numOfCol = 9;
public Locale locals;

public Observation annpctLastObs, pctLastObs;
public Object pctLastV, annpctLastV;
public String []annpctValues, pctValues;
public long ordreEndIndex;
public String[] lev1 = {"Ordrebasert industri"};
public String[] lev2 = {"Tekstil- og bekledningsindustri", "Papir- og papirvareindustri", "Kjemisk- og farmasøytisk industri", "Metallindustri", "Metallvareindustri", "Data-, elektrisk utstyrsindustri", "Maskinindustri", "Bygging av skip og oljeplattformer", "Annen verkstedsindustri", "Maskinreparasjon og -installasjon"};

public String[] lev3 = {"Kjemiske råvarer", "Ikke-jernholdige metaller"};
public String[] lev1_en = {"Manufacturing working on orders"};
public String[] lev2_en = {"Textiles and wearing apparel", "Paper and paper products", "Chemical and pharmaceutical products", "Basic metals", "Fabricated metal products", "Computer and electrical equipment", "Machinery and equipment", "Ships, boats and oil plattforms", "Transport equipment n.e.c", "Repair, installation of machinery"};

public String[] lev3_en = {"Basic chemicals", "Non-ferrous metals"};
public String indent2 = "  ";
public String indent3 = "    ";
public String indent4 = "      ";
public String padStr;
public String tab;
public String lan;

public ConnectMcadbs mcadbsconn;
public ObservationList ordreObsList, ordreTab2Ol;
public String[] ordreTab2Val;
public byte status;
public byte[] statuses;
public DefaultTableCellRenderer dtcr;
public String convertByte;
public String ordreYtyFormatVal;
public Object columnValue;

public JLabel footerLabel;
public PrintStream outHtml;
public String outFileName;
public File html1;
public FileOutputStream htmlStream;
// Date variables
public long dat1, dat2, dat3, dat4, dat5;
public static final TiqDateFormat dft = new TiqDateFormat("q'. kv. 'yyyy");
public static final TiqDateFormat dft_en = new TiqDateFormat("q'. qrt. 'yyyy");
public String s_dat1, s_dat2, s_dat3, s_dat4, s_dat5, s_dat6, s_dat7;
public NumberFormat valueFmt;
public TiqTest tiqtest;
public String cmdRequest;
AccessPointClient apClient1;
TiqObject[] apDesc;



public TiqRetrieve() throws ObjectAccessChkException, DataStoreOpenChkException, ConnectionFailedChkException, TiqCheckedException
{
				
		tab = null;
		lan = null;
		
		locals = new Locale("no", "NO");
		formatValue = new DecimalFormat("0.0");
		formatValue1 = new DecimalFormat("0.0");
			
		valueFmt = NumberFormat.getInstance(Locale.ENGLISH);
        valueFmt.setMaximumFractionDigits(1);
        valueFmt.setMinimumFractionDigits(0);
        valueFmt.setGroupingUsed(false);
		
		tiqview = new TiqView[20];
		tiqview1 = new TiqView[1];
		tiqview2 = new TiqView[1];
		tiqview3 = new TiqView[1];
		tiqview4 = new TiqView[1];
		tiqview5 = new TiqView[1];
		tiqview6 = new TiqView[1];
		tiqview7 = new TiqView[1];
		tiqview8 = new TiqView[1];
		tiqLineView = new TiqView[2];
				
		
		//delete Get PPI series used in a table 20.03.12
		
		//delete ppi series used in timeiq charts
		
		// delete tiqviews for ppi series
			
					
		
		// ********************Below is the code for retrieving sereies and creating table for Ordre ************************************************
		// The code is modify when using accessPoint 13.03
		
		
		nft = NumberFormat.getNumberInstance(Locale.getDefault());
        nft.setMaximumFractionDigits(1);		
        twoDigits = new DecimalFormat();
        twoDigits = (DecimalFormat)nft;
        twoDigits.applyPattern("0.0");
         
		
		//delete ordre retrieval of series names from tabell_adm_v42.db 20.03.12
       		
	} // end TiqRetrieve
		
	
	public void getSeries() throws ObjectAccessChkException, ConnectionFailedChkException
	{
				
		/* comment out when using accessPoint 13.03
		 * Server sr = Server.getInstance(); // create server instance
		Session ss = sr.getSession();  // create session
		Connection conn = null;		// create connection
		Properties prop = new Properties();
		String FAMEServer = "#48700@kpli-ovibos.ssb.no";*/
		
		
		 
		 /* comment out when using accessPoint
		  * try{
			
			//This property is for a wide area network.
			//prop = new Properties();
			prop.put("speed", "slow");	
		
			try
			{

				conn = ss.createConnection(FAMEServer, "","", prop); // open connection

			}
			catch(ObjectAccessChkException e){
				e.printStackTrace();
			}
			catch(ConnectionFailedChkException e)
			{
				System.out.print(e);
				return;
			}	*/
			
			
			
			// Get Ordre series names from case series stub.7.stub.stub_str in tabell_adm_v42.db
			if (tab == "ordretable1")
			{
				
				//Modified 13.03 to use accessPoint and change TiqObject ordreSerCase to TiqObject[] ordreSerCase
				//ordreSerCase = conn.remEval("STUB.7.STUB.STUB_STR");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.7.STUB.STUB_STR");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
				//comment out 201208.06
				//ordreSerNames1 = new String[ordreSerNames.length];
				
				/*for(int i = 0; i < ordreSerNames.length; i++)
				{
					ordreSerNames1[i] = ordreSerNames[i].substring(6).trim();
				}*/
				
			}
			// Get Ordre series names from case series sutb.8.stub.stub_str in tabell_adm_v42.db
			else if(tab == "ordretable2")
			{
				//ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.8.STUB.STUB_STR", 1, 59);
				//ordreSerCase = conn.remEval("STUB.8.STUB.STUB_STR");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.8.STUB.STUB_STR");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
			}
			else if (tab == "ordretable1eng")
			{
				//ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.STUB_STR", 1, 59);
				//ordreSerCase = conn.remEval("STUB.7.STUB.STUB_STR");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.7.STUB.STUB_STR");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
			}
			else if (tab == "ordretable2eng")
			{
				//ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.8.STUB.STUB_STR", 1, 59);
				//ordreSerCase = conn.remEval("STUB.8.STUB.STUB_STR");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.8.STUB.STUB_STR");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
			}
			else if (tab == "ordretable1ana")
			{
				//ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.STUB_STR_V", 1, 51);
				//ordreSerCase = conn.remEval("STUB.7.STUB.STUB_STR_V");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.7.STUB.STUB_STR_V");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
			}
			else if (tab == "ordretable2ana")
			{
				//ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.8.STUB.STUB_STR_V", 1, 51);
				//ordreSerCase = conn.remEval("STUB.8.STUB.STUB_STR_V");
				try {
					
					ordreSerCase = getData7("ORDREMETA'STUB.8.STUB.STUB_STR_V");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreSerCaseOl = ordreSerCase[0].getObservations();
				ordreSerCaseArray = ordreSerCaseOl.getValues();
				ordreSerNames = ordreSerCaseArray.getStringArray();
				
			}
			
			// delete Get Lager series names from case series stub.7.stub.stub_str, 20.03.12
		
	//}// end try comment out end try 13.03
		
	/* comment out when using accessPoint 13.03
	 * catch (Throwable t) {
		
		 t.printStackTrace();

	} finally {
		if (conn != null)
			conn.close();

	}*/
	
	
	}
	
	//Retrieve case series from ordremeta(tabell_adm_v42.db)via accessPoint 13.03
    public TiqObject[] getData7(String ordreStr) throws Exception
    {
    	
    	//comment out 2012.06.15
    	cmdRequest = null;
    	cmdRequest = "&cmd=get_objects" +
		"&app=getfamedata" + 
		"&object=" + ordreStr ;
    	
    	//String rq1 = "&" + URLEncoder.encode("cmd", "UTF-8") + "=" + URLEncoder.encode("get_objects", "UTF-8")+ 
    	//"&" + URLEncoder.encode("app", "UTF-8") + "=" + URLEncoder.encode("getfamedata", "UTF-8") + "&" + URLEncoder.encode("object", "UTF-8") + "=" + ordreStr;
    	
    	//String cmdRequest = ordreStr;
    	//String rq2 = rq1 + cmdRequest;
		
		/*String uri = "http://w03046.ssb.no:8080/accessPoint/accessPoint";
    	AccessPointClient apClient = new AccessPointClient(uri);
    	apClient.login("admin", "vip");
    	TiqObject[] apDesc = null;	    	
    	apDesc = apClient.issueRequest(cmdRequest, true);
    	//apDesc = apClient.issueRequest(rq2, true);
    	apClient.logout("admin","vip");*/
    	
    	getUri_1();
    	return apDesc;
	
    }
    
    public TiqObject[] getData8(String[] ordreStr) throws Exception
    {
    	   	
    	//comment out 2012.06.15
    	cmdRequest = null;
    	cmdRequest = "&cmd=get_objects" +
    	"&app=getfamedata" + 
		 getOrdreSer();
    	
		// removed 2012.06.13
    	String uri = "http://w03046.ssb.no:8080/accessPoint/accessPoint";
    	AccessPointClient apClient = new AccessPointClient(uri);
    	apClient.login("admin", "vip");
    	TiqObject[] apDesc = null;	    	  	
    	
    	apDesc = apClient.issueRequest(cmdRequest, true); 
    	//System.out.println(cmdRequest);
    	
    	//apClient.logout("admin","vip");
    	
    	//removed 2012.10.16
    	//getUri_1();
    		
    	return apDesc;	
	
    }
    
    public String getOrdreSer()
	{
		 
    	//String b = null;
		String a = null;
		
		a = "ORDRE_07";	
		
		String apOrdre = null;
		 	 
		//comment out 2012.08.06
		//apOrdre = "&object=" + a + "'" +  ordreSerNames1[0].trim();
		
		apOrdre = "&object=" + a + "'" +  ordreSerNames[0].trim();
		
		/*try {
			
			apOrdre = "&" + URLEncoder.encode("object", "UTF-8") + "=" + a + "'" +  ordreSerNames[0].trim();
		
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}*/


		for (int i = 1; i < ordreSerNames.length; i++)
		{
			apOrdre += "&object=" + "ORDRE_07'" + ordreSerNames[i].trim();
			
			//apOrdre += "&" + URLEncoder.encode("object") + "=" + "ORDRE_07'" + ordreSerNames[i].trim();
			
			//comment out 2012.08.06
			//apOrdre += "&object=" + a + "'" + ordreSerNames1[i].trim();
			
		}
		  
		
		return apOrdre;
		 
	 }   
     
    
	 
	//Add 2012.06.13
 // Get URI
    public void getUri_1()
    {
    	
    	String uri = "http://w03046.ssb.no:8080/accessPoint/accessPoint";
    	//String uri = "http://ktli-jboss-t01:8080/accessPoint/accessPoint";
    	
        //String aPoint_URI = appletBase.getProtocol() + "://" + appletBase.getHost() +
        //	":" + appletBase.getPort() +  accessPointContext + accessPointServlet;

        //    	System.err.println("*** new URI = " + aPoint_URI + " ***");
    	// Helper class from the accessPoint programmers samples pack

    	apClient1 = new AccessPointClient(uri);
    	
    	try {
			
    		apClient1.login("admin", "vip");
		
    	} catch (Exception e) {
		
			e.printStackTrace();
		}
    	apDesc = null;	    	
    	
    	try {
			
    		apDesc = apClient1.issueRequest(cmdRequest, true);
		
    	} catch (OptionalDataException e) {
			
			e.printStackTrace();
		
    	} catch (StreamCorruptedException e) {
			
			e.printStackTrace();
		
    	} catch (MalformedURLException e) {
			
			e.printStackTrace();
		
    	} catch (APChkException e) {
			
			e.printStackTrace();
		
    	} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	/*try {
			
    		apClient1.logout("admin", "vip");
		
    	} catch (Exception e) {
			
			e.printStackTrace();
		}*/
    }
    
    public void createTableData() throws FunctorConsistencyChkException, DateAlignmentChkException, ObjectAccessChkException, ConnectionFailedChkException
	{
		
		// Table 1
		// Get Ordre series names from case series stub.7.stub.stub_str in tabell_adm_v42.db
		/*ordreSerCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.STUB_STR", 1, 59);
		ordreSerCaseOl = ordreSerCase.getObservations();
		ordreSerCaseArray = ordreSerCaseOl.getValues();
		ordreSerNames = ordreSerCaseArray.getStringArray();*/
							
		// Get metadata from case series stub.7.stub.text1.no
				
		
		/* comment out 13.03 to use accessPoint
		 * Server sr = Server.getInstance(); // create server instance
		Session ss = sr.getSession();  // create session
		Connection conn = null;		// create connection
		Properties prop = new Properties();
		String FAMEServer = "#48700@kpli-ovibos.ssb.no";
		
		try{
			
			//This property is for a wide area network.
			//prop = new Properties();
			prop.put("speed", "slow");	
		
			try
			{

				conn = ss.createConnection(FAMEServer, "","", prop); // open connection

			}
			catch(ObjectAccessChkException e){
				e.printStackTrace();
			}
			catch(ConnectionFailedChkException e)
			{
				System.out.print(e);
				return;
			}		*/
		
		
			getSeries();
		
		
		if(tab == "ordretable1" || tab == "ordretable2")
		{
			//ordreForspCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.TEXT1.NO" , 1, 59);
			// Change TiqObject ordreForspCase to TiqObject[] ordreForspCase
			
			//ordreForspCase = conn.remEval("STUB.7.STUB.TEXT1.NO");
			try {
				
				ordreForspCase = getData7("ORDREMETA'STUB.7.STUB.TEXT1.NO");
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		else if (tab == "ordretable1ana" || tab == "ordretable2ana")
		{
			//ordreForspCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.TEXT1.NO.V" , 1, 51);
			//ordreForspCase = conn.remEval("STUB.7.STUB.TEXT1.NO.V");
			try {
				
				ordreForspCase = getData7("ORDREMETA'STUB.7.STUB.TEXT1.NO.V");
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		else if (tab == "ordretable1eng" || tab == "ordretable2eng")
		{
			//ordreForspCase = localconn.db5.getTiqObjectCopy("STUB.7.STUB.TEXT1.EN", 1, 59);
			//ordreForspCase = conn.remEval("STUB.7.STUB.TEXT1.EN");
			try {
				
				ordreForspCase = getData7("ORDREMETA'STUB.7.STUB.TEXT1.EN");
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		ordreForspCaseOl = ordreForspCase[0].getObservations();
		ordreForspCaseArray = ordreForspCaseOl.getValues();
		ordreForspNames = ordreForspCaseArray.getStringArray();
		//ordreForspNames1 = new String[ordreForspNames.length];
		//System.out.println(ordreForspNames.length + " Qi");
		
		//ordreTabData1 = new String[ordreForspNames2.length][numOfCol];-- comment out 03.01.11
		ordreTabData1 = new String[ordreForspNames.length][numOfCol];
		
		for (int i = 0; i < ordreForspNames.length; i++)
		{
			ordreTabData1[i][0]	= ordreForspNames[i];
			//System.out.println(ordreForspNames[i]);
			
		}
		
		ordreApObj = new TiqObject[ordreSerNames.length];
		ordreTiqView = new TiqView[ordreSerNames.length];
		ordreTiqObj = new TiqObject[ordreSerNames.length];
		
		
		if(tab == "ordretable1" || tab == "ordretable2")
		{
			for (int i = 0; i < ordreSerNames.length; i++)
			{
				//ordreTiqView[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));		
				//ordreTiqView[i] = conn.remEval(ordreSerNames[i].substring(6));
				
				//comment out 2012.06.18
				ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				//ordreSerNames1[i] = ordreSerNames[i];
				
				
				try {
					
					//endret 2012.08.06
					ordreApObj = getData8(ordreSerNames);
					//ordreApObj = getData8(ordreSerNames1);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
				ordreTiqView[i] = ordreApObj[i];
			}
			
			
			//ordreTiqObj = new TiqObject[ordreSerNames.length];
			//comment out 2010.06.18
			for ( int i = 0; i < ordreSerNames.length; i++)
			{
				 //ordreTiqObj[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));
				 //ordreTiqObj[i] = conn.remEval(ordreSerNames[i].substring(6));
				//ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				try {
					
					//endret 2012.08.06
					ordreApObj = getData8(ordreSerNames);
					//ordreApObj = getData8(ordreSerNames1);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreTiqObj[i] = ordreApObj[i];
				
			}
		
		}// end if
		else if(tab == "ordretable1ana" || tab == "ordretable2ana")
		{
			for (int i = 0; i < ordreSerNames.length; i++)
			{
				//ordreTiqView[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));
				//ordreTiqView[i] = conn.remEval(ordreSerNames[i].substring(6));
				ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				try {
					
					//ordreApObj = getData8(ordreSerNames1);
					ordreApObj = getData8(ordreSerNames);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreTiqView[i] = ordreApObj[i];
			}
			
			
			//ordreTiqObj = new TiqObject[ordreSerNames.length];
			for ( int i = 0; i < ordreSerNames.length; i++)
			{
				 //ordreTiqObj[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));
				 //ordreTiqObj[i] = conn.remEval(ordreSerNames[i].substring(6));
				//ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				try {
					
					ordreApObj = getData8(ordreSerNames);
					//ordreApObj = getData8(ordreSerNames1);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreTiqObj[i] = ordreApObj[i];
				
			}
		}
		
		else if (tab == "ordretable1eng" || tab == "ordretable2eng")
		{
			for (int i = 0; i < ordreSerNames.length; i++)
			{
				//ordreTiqView[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));
				//ordreTiqView[i] = conn.remEval(ordreSerNames[i].substring(6));
				//comment out 2012.06.18
				ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				
				//ordreSerNames1[i] = ordreSerNames[i];;
				
				
				try {
					
					//endret 2012.08.06
					ordreApObj = getData8(ordreSerNames);
					//ordreApObj = getData8(ordreSerNames1);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreTiqView[i] = ordreApObj[i];
			}
			
			
			//ordreTiqObj = new TiqObject[ordreSerNames.length];
			//comment out 2012.06.18
			for ( int i = 0; i < ordreSerNames.length; i++)
			{
				 //ordreTiqObj[i] = localconn.db4.getTiqObjectCopy(ordreSerNames[i].substring(6));
				 //ordreTiqObj[i] = conn.remEval(ordreSerNames[i].substring(6));
				//ordreSerNames[i] = ordreSerNames[i].substring(6).trim();
				try {
					
					//endret 2012.08.06
					ordreApObj = getData8(ordreSerNames);
					//ordreApObj = getData8(ordreSerNames1);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				ordreTiqObj[i] = ordreApObj[i];
			}
		}
		
					
		// getting all the date indexes of the series here		
		  ordreDates = ordreTiqObj[0].getObservations().getIndexes();
		  len = ordreDates.length;
		  
		  // get the last five dates that will be the date header to the table
		 		  
		  	if (tab == "ordretable1" || tab == "ordretable2" || tab == "lagertable1" || tab == "lagertable2" || tab == "ordretable1ana" || tab == "ordretable2ana")	
		  	{
		  		dat1 = ordreDates[len-5];
		  		s_dat1 = dft.format(dat1);
		  		dat2 = ordreDates[len-4];
		  		s_dat2 = dft.format(dat2);		 
		  		dat3 = ordreDates[len-3];
		  		s_dat3 = dft.format(dat3);
		  		dat4 = ordreDates[len-2];
		  		s_dat4 = dft.format(dat4);
		  		dat5 = ordreDates[len-1];
		  		s_dat5 = dft.format(dat5);
		  		s_dat6 = s_dat5 + "/" + s_dat1;
		  		s_dat7 = s_dat5 + "/" + s_dat4;
		  	}
		  	else if (tab == "ordretable1eng" || tab == "ordretable2eng" || tab == "lagertable1eng" || tab == "lagertable2eng")
		  	{
		  		dat1 = ordreDates[len-5];
			  	s_dat1 = dft_en.format(dat1);
			  	dat2 = ordreDates[len-4];
			  	s_dat2 = dft_en.format(dat2);		 
			  	dat3 = ordreDates[len-3];
			  	s_dat3 = dft_en.format(dat3);
			  	dat4 = ordreDates[len-2];
			  	s_dat4 = dft_en.format(dat4);
			  	dat5 = ordreDates[len-1];
			  	s_dat5 = dft_en.format(dat5);
			  	s_dat6 = s_dat5 + "/" + s_dat1;
			  	s_dat7 = s_dat5 + "/" + s_dat4;
		  	}
		  				  				  
		  		  
		// get the last five observations
		for (int i = 0; i < ordreTiqObj.length; i++)
		{		
			
			ordreObsList = ordreTiqObj[i].getObservations(ordreDates[len-5], ordreDates[len-1]);
			statuses = ordreObsList.getStatuses();
			ordreSerValues = ordreTiqObj[i].getObservations(ordreDates[len-5], ordreDates[len-1]).getValues().getStringArray();
			
					
			ordreSerFormat = new Object[ordreSerValues.length];
			for (int k = 0; k < 5; k++)
			{
							
				if(!TiqHelper.isMissing(statuses[k]))
				{
					if ( tab == "ordretable1eng" || tab == "ordretable2eng" || tab == "lagertable1eng" || tab == "lagertable2eng")
					{
						ordreSerValues[k] = ordreSerValues[k].replace(".", ",");			
						ordreSerFormat[k] = parseDouble(ordreSerValues[k]);
						ordreSerFormat[k] = twoDigits.format(ordreSerFormat[k]);				
						//ordreSerFormat[k] = valueFmt.format(ordreSerValues[k]);
						
						ordreTabData1[i][k+1] = (String) ordreSerFormat[k];
						ordreTabData1[i][k+1] = ordreTabData1[i][k+1].replace(",", ".");
						//ordreTabData1[i][k+2] = (String) ordreSerFormat[k];
						//System.out.println(ordreTabData1[i][k+1]);
					}
					else if ( tab == "ordretable1" || tab == "ordretable2" || tab == "lagertable1" || tab == "lagertable2" || tab == "ordretable1ana" || tab == "ordretable2ana")
					{
						ordreSerValues[k] = ordreSerValues[k].replace(".", ",");			
						ordreSerFormat[k] = parseDouble(ordreSerValues[k]);									
						ordreSerFormat[k] = twoDigits.format(ordreSerFormat[k]);				
						ordreTabData1[i][k+1] = (String) ordreSerFormat[k];
					}
					
				}	
				else 
				{
					//ordreTabData1[i][k+1] = (String) ordreSerFormat[k];
					ordreTabData1[i][k+1] = "";
				}
								
			}
						
		}
				
		ordreEndIndex = ordreTiqView[0].getMaxIndex();
		annpctValues = new String[ordreTiqView.length];
		pctValues = new String[ordreTiqView.length];
		
		// get last value of ytypct of each series
		ordreEndIndex = ordreTiqView[0].getMaxIndex();
		for (int i = 0; i < ordreTiqView.length; i ++)
		{
			
			ordreShiftView = ordreTiqView[i];
			ordreShiftView1 = getShiftYear(ordreShiftView, -1);
			ordreDivideView = getDivide(ordreShiftView, ordreShiftView1);
			ordreSubstractView = getSubtract1(ordreDivideView, s);
			ordreYtypctView = getMultiply1(ordreSubstractView, scalar);
			ordreYtypctLast = getMultiply1(ordreSubstractView, scalar).getObservation(ordreEndIndex);
			status = ordreYtypctLast.getStatus();
			
			if(!TiqHelper.isMissing(status))
			{
				if(tab == "ordretable1eng" || tab == "ordretable2eng" || tab == "lagertable1eng" || tab == "lagertable2eng")
				{
					ordreYtyLastValue = ordreYtypctLast.getDoubleValue();
					ordreYtyFormatVal = formatValue1.format(ordreYtyLastValue);
					ordreTabData1[i][6] = ordreYtyFormatVal;
					ordreTabData1[i][6] = ordreTabData1[i][6].replace(",", ".");
				}
				else if ( tab == "ordretable1" || tab == "ordretable2" || tab == "lagertable1" || tab == "lagertable2" || tab == "ordretable1ana" || tab == "ordretable2ana")
				{
					ordreYtyLastValue = ordreYtypctLast.getDoubleValue();
					ordreYtyFormatVal = formatValue1.format(ordreYtyLastValue);
					ordreTabData1[i][6] = ordreYtyFormatVal;
					//ordreTabData1[i][7] = ordreYtyFormatVal;
				}
			}
			else
			{
				ordreTabData1[i][6] = "";
			}
					
		}
				
		
		//Get pct of each series
		for (int i = 0; i < ordreTiqView.length; i ++)
		{
			
			pctLastObs = getPct(ordreTiqView[i]).getObservation(ordreEndIndex);
			status = pctLastObs.getStatus();
			
			if(!TiqHelper.isMissing(status))
			{
				if(tab == "ordretable1eng" || tab == "ordretable2eng" || tab == "lagertable1eng" || tab == "lagertable2eng")
				{
					pctLastV = pctLastObs.getDoubleValue();
					pctValues[i] = formatValue1.format(pctLastV);
					ordreTabData1[i][7] = pctValues[i];
					ordreTabData1[i][7] = ordreTabData1[i][7].replace(",", ".");
				}
				else if (tab == "ordretable1" || tab == "ordretable2" || tab == "lagertable1" || tab == "lagertable2" || tab == "ordretable1ana" || tab == "ordretable2ana")
				{
					pctLastV = pctLastObs.getDoubleValue();
					pctValues[i] = formatValue1.format(pctLastV);
					ordreTabData1[i][7] = pctValues[i];
					//ordreTabData1[i][8] = pctValues[i];
				}
			}
			else
			{
				ordreTabData1[i][7] = "";
			}
		}
		
		/*}catch (Throwable t) {
			
			 t.printStackTrace();

		} finally {
			if (conn != null)
				conn.close();

		}	*/
		
		// set footnote to Jlabel
		footerLabel = new JLabel();
		footerLabel.setText("<html><sup>1)</sup>Standard for næringsgruppering (SN2007).<BR> <sup>2)</sup>Pst. år er prosentvis endring fra samme periode i foregående år.<BR> " + 
		"<sup>3)</sup>Pst. kv. er prosentvis endring fra foregående kvartal. <BR> Kilde: SSB</html>");
		
	}
	
	private static Number parseDouble(String s)
	 {
	        ParsePosition p = new ParsePosition(0);
	        Number result = nf.parse(s, p);
	        nf.setMaximumFractionDigits(2);
	        nf.setMinimumFractionDigits(0);

	        if (p.getIndex() != s.length())
	            throw new IllegalArgumentException(s + "Could not parse " + s + " as a number");
	        return result;
	   }

	// create Tabell 1ny for ordre 
	public void createOrdreTab1(String[][]tab1) throws FunctorConsistencyChkException, DateAlignmentChkException, ObjectAccessChkException, ConnectionFailedChkException
	{
		ordreTabPanel = new JPanel();
		ordreTabPanel.setLayout(new BorderLayout());
		jtable = new JTable();
		
		createTableData();
		//int rowSize = ordreForspNames2.length + 2;
		//int rowSize = ordreForspNames.length + 2 ;
		int rowSize = ordreTabData1.length;
		//int colSize = 8;
		int colSize = colHeader1.length;
		//tableModel = new DefaultTableModel(rowSize, colSize);
		if(tab == "ordretable1")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader1);
		}
		else if (tab == "ordretable2")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader2);
		}
		else if (tab =="ordretable1ana")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader1);
		}
		else if (tab == "ordretable2ana")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader2);
		}
		else if (tab == "lagertable1")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader3);
		}
		else if (tab == "lagertable2")
		{
			tableModel = new DefaultTableModel(ordreTabData1, colHeader4);
		}
		
		jtable.setModel(tableModel);
		//jtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		//jtable.setRowHeight(18);
		//jtable.setRowHeight(ordreTabData1.length-1, 40);
		jtable.setCellSelectionEnabled(true);
		//jtable.setShowGrid(false);
		
		JTableHeader header = jtable.getTableHeader();
		header.setBackground(Color.darkGray);
		
		MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
		Enumeration e = jtable.getColumnModel().getColumns();
	    while (e.hasMoreElements()) {
	      ((TableColumn) e.nextElement()).setHeaderRenderer(renderer);
	    }

		Font f =  new Font("COURIER NEW",Font.PLAIN, 12);
		jtable.setFont(f);
			
		// write two-dimensional array to a jtable --- 
		//for (int i = 0; i < rowSize - 2; i++)
		for (int i = 0; i < rowSize - 1; i++)
		{
			
			for(int j = 0; j < colSize; j++)
			{							
				
				//tableModel.setValueAt(ordreTabData1[i][j], i+2, j);	
				tableModel.setValueAt(ordreTabData1[i][j], i, j);	

			}	
			 
		}
		
		
		// pad .....
		//for(int i = 0; i < rowSize - 2; i++)
		for(int i = 0; i < rowSize - 1; i++)
		{
			for(int j = 0; j < lev1.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev1[j]))
				{
					
					/*tableModel.setValueAt(ordreTabData1[i][0] + pad1(ordreTabData1[i][0], 50), i+2, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0]+ indent2, 50), i+3, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0]+ indent2, 50), i+4, 0);*/
					
					/*tableModel.setValueAt(ordreTabData1[i][0] + pad1(ordreTabData1[i][0], 50), i+1, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0]+ indent2, 50), i+2, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0]+ indent2, 50), i+3, 0);*/
					
					tableModel.setValueAt(ordreTabData1[i][0] + pad1(ordreTabData1[i][0], 50), i, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0]+ indent2, 50), i+1, 0);
					tableModel.setValueAt(indent2 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0]+ indent2, 50), i+2, 0);
					
					
				}
				
			}
		}
		
		
		//for(int i = 0; i < rowSize - 2; i++)	
		for(int i = 0; i < rowSize - 1; i++)	
		{
			for(int j = 0; j < lev2.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev2[j]))
				{							
					
					/*tableModel.setValueAt(indent2 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent2, 50), i+2, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0] + indent3, 50), i+3, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent3, 50), i+4, 0);*/
					
					/*tableModel.setValueAt(indent2 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent2, 50), i+1, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0] + indent3, 50), i+2, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent3, 50), i+3, 0);*/
					
					tableModel.setValueAt(indent2 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent2, 50), i, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+1][0] + pad1(ordreTabData1[i+1][0] + indent3, 50), i+1, 0);
					tableModel.setValueAt(indent3 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent3, 50), i+2, 0);
															
					
				}		
			}
		}
		
		//for ( int i = 0; i < rowSize - 2; i++)
		for ( int i = 0; i < rowSize - 1; i++)
		{
			for(int j = 0; j < lev3.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev3[j]))
				{
					
					/*tableModel.setValueAt(indent3 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent3, 50), i+2, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+1][0]+ pad1(ordreTabData1[i+1][0] + indent4, 50), i+3, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent4, 50), i+4, 0);*/
					
					/*tableModel.setValueAt(indent3 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent3, 50), i+1, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+1][0]+ pad1(ordreTabData1[i+1][0] + indent4, 50), i+2, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent4, 50), i+3, 0);*/
					
					tableModel.setValueAt(indent3 + ordreTabData1[i][0] + pad1(ordreTabData1[i][0] + indent3, 50), i, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+1][0]+ pad1(ordreTabData1[i+1][0] + indent4, 50), i+1, 0);
					tableModel.setValueAt(indent4 + ordreTabData1[i+2][0] + pad1(ordreTabData1[i+2][0] + indent4, 50), i+2, 0);
					
				}
			}
		}
		
		//System.out.println(jtable.getRowCount() + "Q");
       
		scroll = new JScrollPane(jtable);
		//scroll.setMinimumSize(new Dimension(200, 200));
		scroll.setPreferredSize(new Dimension(900, 900));
		jtable.revalidate();	
		
		//dtcr = new DefaultTableCellRenderer();
		CustomTableCellRenderer dtcr = new CustomTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		column0 = null;
        column1 = null;
        column2 = null;
        column3 = null;
        column4 = null;
        column5 = null;
        column6 = null;
        column7 = null;
       
        column0 = jtable.getColumnModel().getColumn(0);
        column1 = jtable.getColumnModel().getColumn(1);
        column2 = jtable.getColumnModel().getColumn(2);
        column3 = jtable.getColumnModel().getColumn(3);
        column4 = jtable.getColumnModel().getColumn(4);
        column5 = jtable.getColumnModel().getColumn(5);
        column6 = jtable.getColumnModel().getColumn(6);
        column7 = jtable.getColumnModel().getColumn(7);
       
        
        column0.setPreferredWidth(300);
        column1.setPreferredWidth(40);
        column1.setCellRenderer(dtcr);
        column2.setPreferredWidth(40);
        column2.setCellRenderer(dtcr);
        column3.setPreferredWidth(40);
        column3.setCellRenderer(dtcr);
        column4.setPreferredWidth(40);
        column4.setCellRenderer(dtcr);
        column5.setPreferredWidth(40);
        column5.setCellRenderer(dtcr);
        column6.setPreferredWidth(40);
        column6.setCellRenderer(dtcr);
        column7.setPreferredWidth(80);
        column7.setCellRenderer(dtcr);
                      
        ordreTabPanel.add(scroll, BorderLayout.CENTER);
        ordreTabPanel.add(footerLabel, BorderLayout.SOUTH);
		SwingUtilities.updateComponentTreeUI(ordreTabPanel);
		
			
	}	
	
	public void SaveHtml() throws FunctorConsistencyChkException, DateAlignmentChkException, ObjectAccessChkException, ConnectionFailedChkException
	{
		
		createTableData();
		int htmlRow = ordreTabData1.length;
		int htmlCol = colHeader1.length;
		outFileName = null;
		if (tab == "ordretable1")
		{
			outFileName = "ordre1.html";
		}
		else if ( tab == "ordretable2")
		{
			outFileName = "ordre2.html";
		}
		else if ( tab == "lagertable1")
		{
			outFileName = "lager1.html";
		}
		else if ( tab == "lagertable2")
		{
			outFileName = "lager2.html";
		}
		
		html1 = new File(outFileName);
		htmlStream = null;
		if (!html1.exists()) {

			try 
			{
				htmlStream = new FileOutputStream(outFileName);
			}
			catch(IOException ex) {}

		} // end if
		
		else {

			try 
			{		
					//If the file allready exists, open it in append modus
					htmlStream = new FileOutputStream(outFileName ,false);
			}
						
			catch(FileNotFoundException er) {}

		}//end else
		
		createTableData();
										
		outHtml = new PrintStream(htmlStream);
		outHtml.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		outHtml.println("<!-- TABELLEN ER LAGET I JAVA-APPLIKASJONEN MED FAME JAVA TOOLKIT -->");
		outHtml.println("<html>");

		outHtml.println("<head>");
		if ( tab == "ordretable1")
		{
			outHtml.println("<title>" + "Ordretabell 1 (Verdiindeks, ordrertilgang), ujustert. 2005 = 100" + "</title>");
		}
		else if (tab == "ordretable2")
		{
			outHtml.println("<title>" + "Ordretabell 2 (Verdiindeks, ordrereserve), ujustert. 2005 = 100" + "</title>");
		}
		else if ( tab == "lagertable1")
		{
			outHtml.println("<title>" + "Lagertabell 1 (Verdiindeks, lagerbeholdning), ujustert. 2005 = 100" + "</title>");
		}
		else if (tab == "lagertable2")
		{
			outHtml.println("<title>" + "Lagertabell 2 (Volumindeks, lagerbeholdning), ujustert. 2005 = 100" + "</title>");
		} 
		/*else if (tab == "ordretable1eng")
		{
			outHtml.println("<title>" + "New Orders Received, Selected Industries. Index Numbers of Value. 2005=100." + "</title>");
		}*/
		//outHtml.println("<title>" + "Ordretabell 1 (Verdiindeks, ordrertilgang), ujustert. 2005 = 100" + "</title>");
		//outHtml.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		outHtml.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"http://www.ssb.no/css/ssb.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"http://www.ssb.no/css/table.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://www.ssb.no/css/print.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://www.ssb.no/css/tableprint.css\">");
		outHtml.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"http://www.ssb.no/js/functions.js\">" + "</script>");
		outHtml.println("</head>");

		outHtml.println("<body bgcolor=\"#ffffff\">");
		outHtml.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		outHtml.println("<tr>");
		outHtml.println("<td align=\"left\">" + "<a href=\"/\"><img src=\"http://www.ssb.no/gifs/logo.gif\" border=\"0\" alt=\"Statistisk sentralbyr&aring;\" align=\"left\">" + "</a>" + "</td>");
		outHtml.println("<td align=\"right\">");
		outHtml.println("<form name=\"query\" method=\"get\" action=\"http://www.ssb.no/locate\" target=\"_top\">");
		outHtml.println("<input type=\"text\" size=\"29\" name=\"tekst\">");
		//outHtml.println("<input type=\"image\" name=\"submit\" src=\"http://www.ssb.no/gifs/soek.jpg\" alt=\"Søk\" title=\"Klikk her for avansert søk\">");
		outHtml.println("<input type=\"image\" name=\"submit\" src=\"http://www.ssb.no/gifs/soek.jpg\" alt=\"S&oslashk\" title=\"Klikk her for avansert s&oslashk\">");
		outHtml.println("<input type=\"hidden\" name=\"top\" value=\"http://www.ssb.no/index.html\">");
		outHtml.println("<input type=\"hidden\" name=\"main\" value=\"http://www.ssb.no/main.html\">");
		outHtml.println("</form>");

		outHtml.println("<div class=\"toolbar\">");
		//outHtml.println("<a href=\"http://www.ssb.no/veiviser/\" title=\"Alfabetisk stikkordliste\">" + "A-Å" + "</a>");
		outHtml.println("<a href=\"http://www.ssb.no/veiviser/\" title=\"Alfabetisk stikkordliste\">" + "A-&Aring" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/hjelp/\" title=\"Veiledning i bruk av www.ssb.no\">" + "Hjelp" + "</a>");
		//outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/biblioteket/\" title=\"Spør vårt bibliotek- og informasjonssenter\">" + "Spørsmål" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/biblioteket/\" title=\"Spør vårt bibliotek- og informasjonssenter\">" + "Sp&oslashrsm&aringl" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/omssb/hvemsvarer/\" title=\"Hvem svarer p&aring hva, adresser, telefon\">" + "Kontakter" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/english/\" target=\"_top\" onClick=\"alternate(); return false\" title=\"Same page in English\">" + "English" + "</a>");
		outHtml.println("</div>");
		outHtml.println("</td>");
		outHtml.println("</tr>");
		outHtml.println("</table>");
		outHtml.println("<br>");

		outHtml.println("<table border=\"0\">");
		outHtml.println("<tr>");
		if (tab == "ordretable1")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/osi/\" target=\"_top\">" + "Ordrestatistikk, "  + s_dat5 + "</a>" + "</td>");			
		}
		else if (tab == "ordretable2")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/osi/\" target=\"_top\">" + "Ordrestatistikk, " + s_dat5 + "</a>" + "</td>");
		}
		else if (tab == "lagertable1")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/lsi/\" target=\"_top\">" + "Lagerstatistikk, "  + s_dat5 + "</a>" + "</td>");
		}
		else if ( tab == "lagertable2")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/lsi/\" target=\"_top\">" + "Lagerstatistikk, " + s_dat5 + "</a>" + "</td>");
		}
		/*else if ( tab == "ordretable1eng")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/bkianl/\" target=\"_top\">" + "Statistics on Orders, Manufacturing, 3. qrt. 2010 " + "</a>" + "</td>");
		}*/
		//outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/bkianl/\" target=\"_top\">" + "Ordrestatistikk, 3. kv. 2010" + "</a>" + "</td>");
		outHtml.println("</tr>");
		outHtml.println("</table>");
		outHtml.println("<br>");

		outHtml.println("<!-- START TABELL -->");
		
		outHtml.println("<table border=\"0\" width=\"98%\">");
		outHtml.println("<caption class=\"tabelltittel\">");
		if(tab == "ordretable1")
		{
			outHtml.println("<span class=\"tabellnummer\">1</span>");
			outHtml.println("Verdiindeks, ordretilgang etter n&aeligring<sup>1)</sup>. Ujustert. 2005 = 100");		
		}
		else if ( tab == "ordretable2")
		{
			outHtml.println("<span class=\"tabellnummer\">2</span>");
			outHtml.println("Verdiindeks, ordrereserve etter n&aeligring<sup>1)</sup>. Ujustert. 2005 = 100");
			
		}
		else if ( tab == "lagertable1")
		{
			outHtml.println("<span class=\"tabellnummer\">1</span>");
			outHtml.println("Verdiindeks og lagerbeholdning, etter n&aeligring<sup>1)</sup>. Ujustert. 2005 = 100");			
		}
		else if ( tab == "lagertable2")
		{
			outHtml.println("<span class=\"tabellnummer\">2</span>");
			outHtml.println("Volumindeks og lagerbeholdning, etter n&aeligring<sup>1)</sup>. Ujustert. 2005 = 100");			
		}
		
		outHtml.println("</caption>");
		
		//outHtml.println("<caption class=\"tabelltittel\">" + "<html>Næring<sup>1)</sup></html>");
		//outHtml.println("</caption>");
		
		outHtml.println("<thead>");
		outHtml.println("<tr>");
		outHtml.println("<td class=\"sep\" colspan=\"80\"></td>");
		outHtml.println("</tr>");
				
		outHtml.println("<tr>");
		
		outHtml.println("<th class=\"multispan\" colspan=6 style=\"border-bottom: 1px #000000 solid; \"></th>");
		//outHtml.println("<th>Pst. år<sup>2)</sup></th>");
		outHtml.println("<th>Pst. &aringr<sup>2)</sup></th>");
		outHtml.println("<th>Pst. kv.<sup>3)</sup></th>");
		outHtml.println("</tr>");
		
		outHtml.println("<tr>");
		
		outHtml.println("<th class=\"multispan\" colspan=6 style=\"border-bottom: 1px #000000 solid; \"></th>");
		outHtml.println("<th>Endring fra i fjor<br>(prosent)</th>");
		outHtml.println("<th>Endring fra forrige kv.<br>(prosent)</th>");
		outHtml.println("</tr>");
				
		// Write date heading to the table
		outHtml.println("<tr>");
		outHtml.println("<th class=level11>" + "</th>");
			
		/*for ( int i = 0; i < htmlHeader1.length; i++)
		{
			
			outHtml.println("<th>" + htmlHeader1[i] + "</th>");
		}*/
		outHtml.println("<th>" + s_dat1 + "</<th>");
		outHtml.println("<th>" + s_dat2 + "</<th>");
		outHtml.println("<th>" + s_dat3 + "</<th>");
		outHtml.println("<th>" + s_dat4 + "</<th>");
		outHtml.println("<th>" + s_dat5 + "</<th>");
		outHtml.println("<th>" + s_dat6 + "</<th>");
		outHtml.println("<th>" + s_dat7 + "</<th>");
		
		outHtml.println("</tr>");
		outHtml.println("<tr>");
		outHtml.println("<td class=\"sep\" colspan=\"80\"></td>");
		outHtml.println("</tr>");
		outHtml.println("</thead>");
		
		outHtml.println("<tbody>");
			
		
		for (int i = 0; i < htmlRow; i++)
		{
			for ( int j = 0; j < lev1.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev1[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level1\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					//outHtml.println("<td align=right>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
						}
						else 
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
						outHtml.println("<td> </td>");
						
						
					}
							outHtml.println("</tr>");
					
				}
			}
			
			for ( int j = 0; j < lev2.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev2[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
							//outHtml.print("<td class=colorcs>" + "&nbsp" + "</td>");
						}
						else
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
						outHtml.println("<td> </td>");
						
					}
							outHtml.println("</tr>");
					
				}
			}
			
			for ( int j = 0; j < lev3.length; j++)
			{
				
				if(ordreTabData1[i][0].equals(lev3[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level4\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level4\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level4\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level4\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
							//outHtml.print("<td class=colorcs>" + "&nbsp" + "</td>");
						}
						else 
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
							
							outHtml.println("<td> </td>");
					}
							
							outHtml.println("</tr>");
				
			   }
			}
			
		}
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"sep\" colspan=\"50\"></td>");
					outHtml.println("</tr>");
					
					outHtml.println("</tbody>");
					outHtml.println("</table>");
					
					outHtml.println("<table border=\"0\" width=\"98%\">");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>1)</sup>Standard for næringsgruppering (SN2007).</td>");
					outHtml.println("</tr>");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>2)</sup>Pst. &aringr er prosentvis endring fra samme periode i foreg&aringende &aringr.</td>");
					outHtml.println("</tr>");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>3)</sup>Pst. kv. er prosentvis endring fra foreg&aringende kvartal.</td>");
					outHtml.println("</tr>");
					outHtml.println("</table>");
					outHtml.println("<p><span class=\"footer\"><a href=\"http://www.ssb.no/standardtegn.html\"> Standardtegn i tabeller </a></span>" + "</p>");
					outHtml.println("<hr>");
					outHtml.println("<div class=\"copy\">2011 <a href=\"http://www.ssb.no/include/copyright.html\" target=\"_self\" title=\"Copyright\">&copy;</a> <a href=\"/\">Statistisk sentralbyrå</a></div>");
					outHtml.println("</html>");
		  
		// end of writing table data
										
	
	} // end SaveHtml
	
	public void SaveHtmlEng()throws FunctorConsistencyChkException, DateAlignmentChkException, ObjectAccessChkException, ConnectionFailedChkException
	{
		createTableData();
		
		// filename = ordre1.html
		int htmlRow = ordreTabData1.length;
		int htmlCol = colHeader1.length;
		outFileName = null;
		if (tab == "ordretable1eng")
		{
			outFileName = "ordre1-eng.html";
		}
		else if ( tab == "ordretable2eng")
		{
			outFileName = "ordre2-eng.html";
		}		
		else if ( tab == "lagertable1eng")
		{
			outFileName = "lager1-eng.html";
		}
		else if ( tab == "lagertable2eng")
		{
			outFileName = "lager2-eng.html";
		}
		
		html1 = new File(outFileName);
		htmlStream = null;
		if (!html1.exists()) {

			try 
			{
				htmlStream = new FileOutputStream(outFileName);
			}
			catch(IOException ex) {}

		} // end if
		
		else {

			try 
			{		
					//If the file allready exists, open it in append modus
					htmlStream = new FileOutputStream(outFileName ,false);
			}
						
			catch(FileNotFoundException er) {}

		}//end else
		
		createTableData();
										
		outHtml = new PrintStream(htmlStream);
		outHtml.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		outHtml.println("<!-- The file is created by Java Application with Fame Java Toolkit -->");
		outHtml.println("<html>");

		outHtml.println("<head>");
		
		if(tab == "ordretable1eng")
		{
			outHtml.println("<title>" + "New Orders Received, Selected Industries. Indices of Value. 2005=100." + "</title>");
		}
		else if ( tab == "ordretable2eng")
		{
			outHtml.println("<title>" + "Unfilled orders , Selected Industries. Indices of Value. 2005=100." + "</title>");
		}
		else if ( tab == "lagertable1eng")
		{
			outHtml.println("<title>" + "Stocks, Selected Industries. Indices of value. 2005 = 100." + "</title>");
		}
		else if ( tab == "lagertable2eng")
		{
			outHtml.println("<title>" + "Stocks, Selected Industries. Indices of volume. 2005 = 100." + "</title>");
		}
		//outHtml.println("<title>" + "Ordretabell 1 (Verdiindeks, ordrertilgang), ujustert. 2005 = 100" + "</title>");
		outHtml.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"http://www.ssb.no/css/ssb.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"http://www.ssb.no/css/table.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://www.ssb.no/css/print.css\">");
		outHtml.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://www.ssb.no/css/tableprint.css\">");
		outHtml.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"http://www.ssb.no/js/functions.js\">" + "</script>");
		outHtml.println("</head>");

		outHtml.println("<body bgcolor=\"#ffffff\">");
		outHtml.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		outHtml.println("<tr>");
		outHtml.println("<td align=\"left\">" + "<a href=\"/\"><img src=\"http://www.ssb.no/gifs/logo.gif\" border=\"0\" alt=\"Statistisk sentralbyr&aring;\" align=\"left\">" + "</a>" + "</td>");
		outHtml.println("<td align=\"right\">");
		outHtml.println("<form name=\"query\" method=\"get\" action=\"http://www.ssb.no/locate/\" target=\"_top\">");
		outHtml.println("<input type=\"text\" size=\"29\" name=\"tekst\">");
		outHtml.println("<input type=\"image\" name=\"submit\" src=\"http://www.ssb.no/gifs/soek_en.jpg\" alt=\"Sok\" title=\"Click here for advanced search\">");
		outHtml.println("<input type=\"hidden\" name=\"top\" value=\"http://www.ssb.no/english/index.html\">");
		outHtml.println("<input type=\"hidden\" name=\"main\" value=\"http://www.ssb.no/english/main.html\">");
		outHtml.println("</form>");

		outHtml.println("<div class=\"toolbar\">");
		outHtml.println("<a href=\"http://www.ssb.no/english/guide/\" title=\"Guide to Norwegian Statistics A - Z\">" + "A-Z" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/english/help/\">" + "Help" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/english/library/\">" + "Queries" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/vis/english/about_ssb/contacts.html\" title=\"email, phone, addresses\">" + "Contacts" + "</a>");
		outHtml.println("|&nbsp;<a href=\"http://www.ssb.no/\" target=\"_top\" onClick=\"alternate(); return false\" title=\"Same page in Norwegian\">" + "Norwegian" + "</a>");
		outHtml.println("</div>");
		outHtml.println("</td>");
		outHtml.println("</tr>");
		outHtml.println("</table>");
		outHtml.println("<br>");

		outHtml.println("<table border=\"0\">");
		outHtml.println("<tr>");		
		if ( tab == "ordretable1eng")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/osi_en/\" target=\"_top\">" + "Statistics on Orders, Manufacturing, " + s_dat5 + "</a>" + "</td>");
		}
		else if (tab == "ordretable2eng")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/osi_en/\" target=\"_top\">" + "Statistics on Orders, Manufacturing, " + s_dat5 + "</a>" + "</td>");
		}
		else if ( tab == "lagertable1eng")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/lsi_en/\" target=\"_top\">" + "Statistics on stocks, Manufacturing, " + s_dat5 + "</a>" + "</td>");
		}
		else if ( tab == "lagertable2eng")
		{
			outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/lsi_en/\" target=\"_top\">" + "Statistics on stocks, Manufacturing, " + s_dat5 + "</a>" + "</td>");
		}
		//outHtml.println("<td class=\"produkttittel\">" + "<a href=\"http://www.ssb.no/bkianl/\" target=\"_top\">" + "Ordrestatistikk, 3. kv. 2010" + "</a>" + "</td>");
		outHtml.println("</tr>");
		outHtml.println("</table>");
		outHtml.println("<br>");

		outHtml.println("<!-- START TABELL -->");
		
		outHtml.println("<table border=\"0\" width=\"98%\">");
		outHtml.println("<caption class=\"tabelltittel\">");		
		if( tab == "ordretable1eng")
		{
			outHtml.println("<span class=\"tabellnummer\">1</span>");
			outHtml.println("New Orders Received, Selected Industries<sup>1)</sup>. Indices of value. 2005=100.");	
		}
		else if ( tab == "ordretable2eng")
		{
			outHtml.println("<span class=\"tabellnummer\">2</span>");
			outHtml.println("Unfilled orders, Selected Industries<sup>1)</sup>. Indices of value. 2005=100.");	
		}
		else if ( tab == "lagertable1eng")
		{
			outHtml.println("<span class=\"tabellnummer\">1</span>");
			outHtml.println("Stocks, Selected Industries<sup>1)</sup>. Indices of value. 2005 = 100.");
		}
		else if ( tab == "lagertable2eng")
		{
			outHtml.println("<span class=\"tabellnummer\">2</span>");
			outHtml.println("Stocks, Selected Industries<sup>1)</sup>. Indices of volume. 2005 = 100.");
			
		}
		outHtml.println("</caption>");
		//outHtml.println("<caption class=\"tabelltittel\">" + "<html>Industry<sup>1)</sup></html>");
		//outHtml.println("</caption>");
		outHtml.println("<thead>");
		outHtml.println("<tr>");
		outHtml.println("<td class=\"sep\" colspan=\"80\"></td>");
		outHtml.println("</tr>");
				
		outHtml.println("<tr>");
		
		outHtml.println("<th class=\"multispan\" colspan=6 style=\"border-bottom: 1px #000000 solid; \"></th>");
		outHtml.println("<th>Pst.yty<sup>2)</sup> </th>");
		outHtml.println("<th>Pst.qrt<sup>3)</sup> </th>");
		outHtml.println("</tr>");
		
		outHtml.println("<tr>");
		
		outHtml.println("<th class=\"multispan\" colspan=6 style=\"border-bottom: 1px #000000 solid; \"></th>");
		outHtml.println("<th>Change from last year<br>(per cent)</th>");
		outHtml.println("<th>Change from last quarter<br>(per cent)</th>");
		outHtml.println("</tr>");
				
		// Write date heading to the table
		outHtml.println("<tr>");
		outHtml.println("<th class=level11>" + "</th>");
			
		/*for ( int i = 0; i < htmlHeader1.length; i++)
		{
			
			outHtml.println("<th>" + htmlHeader1[i] + "</th>");
		}*/
		outHtml.println("<th>" + s_dat1 + "</<th>");
		outHtml.println("<th>" + s_dat2 + "</<th>");
		outHtml.println("<th>" + s_dat3 + "</<th>");
		outHtml.println("<th>" + s_dat4 + "</<th>");
		outHtml.println("<th>" + s_dat5 + "</<th>");
		outHtml.println("<th>" + s_dat6 + "</<th>");
		outHtml.println("<th>" + s_dat7 + "</<th>");
		
		outHtml.println("</tr>");
		outHtml.println("<tr>");
		outHtml.println("<td class=\"sep\" colspan=\"45\"></td>");
		outHtml.println("</tr>");
		outHtml.println("</thead>");
		
		outHtml.println("<tbody>");
			
		
		for (int i = 0; i < htmlRow; i++)
		{
			for ( int j = 0; j < lev1_en.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev1_en[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level1\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					//outHtml.println("<td align=right>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
						}
						else 
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
						outHtml.println("<td> </td>");
						
						
					}
							outHtml.println("</tr>");
					
				}
			}
			
			for ( int j = 0; j < lev2_en.length; j++)
			{
				if(ordreTabData1[i][0].equals(lev2_en[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level2\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level2\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
							//outHtml.print("<td class=colorcs>" + "&nbsp" + "</td>");
						}
						else
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
						outHtml.println("<td> </td>");
						
					}
							outHtml.println("</tr>");
					
				}
			}
			
			for ( int j = 0; j < lev3_en.length; j++)
			{
				
				if(ordreTabData1[i][0].equals(lev3_en[j]))
				{
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level3\">" + ordreTabData1[i][0]+ "</td>");
					//outHtml.print("<td class=\"level3\" class=colorcs>" + ordreTabData1[i][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level4\">" + ordreTabData1[i+1][0]+ "</td>");
					//outHtml.print("<td class=\"level4\" class=colorcs>" + ordreTabData1[i+1][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+1][7] + "</td>");
					outHtml.println("</tr>");
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level4\">" + ordreTabData1[i+2][0]+ "</td>");
					//outHtml.print("<td class=\"level4\" class=colorcs>" + ordreTabData1[i+2][0]+ "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][1] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][2] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][3] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][4] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][5] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][6] + "</td>");
					outHtml.println("<td>" + ordreTabData1[i+2][7] + "</td>");
					outHtml.println("</tr>");
					
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"level1\"> </td>");
					for (int k = 0; k < 7; k++)
					{
						/*if(k == 0)
						{
							outHtml.print("<td BGCOLOR=#C9C9C9>" + "&nbsp" + "</td>");
							//outHtml.print("<td class=colorcs>" + "&nbsp" + "</td>");
						}
						else 
						{
							outHtml.print("<td>" + "&nbsp" + "</td>");
						}*/
							
							outHtml.println("<td> </td>");
					}
							
							outHtml.println("</tr>");
				
			   }
			}
			
		}
					
					outHtml.println("<tr>");
					outHtml.println("<td class=\"sep\" colspan=\"50\"></td>");
					outHtml.println("</tr>");
					
					outHtml.println("</tbody>");
					outHtml.println("</table>");
					
					outHtml.println("<table border=\"0\" width=\"98%\">");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>1)</sup>Standard industrial classification. (SN2007).</td>");
					outHtml.println("</tr>");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>2)</sup>Pct.yty. is change in per cent from the same period the previous year. </td>");
					outHtml.println("</tr>");
					outHtml.println("<tr>");
					outHtml.println("<td class=\"footnote\" valign=\"top\" width=\"1%\"><sup>3)</sup>Pct. qrt. is change in per cent from the previous quarter.</td>");
					outHtml.println("</tr>");
					outHtml.println("</table>");
					outHtml.println("<p><span class=\"footer\"><a href=\"http://www.ssb.no/english/symbols.html\"> Explanation of symbols </a></span>" + "</p>");
					outHtml.println("<hr>");
					outHtml.println("<div class=\"copy\">2011 <a href=\"www.ssb.no/include/copyright_en.html\" target=\"_self\" title=\"Copyright\">&copy;</a> <a href=\"/\">Statistics Norway</a></div>");
					outHtml.println("</html>");
		  
		// end of writing table data
	
	} // end SaveHtmlEng
	
	
	public Object pad1(String str, int width)
    {
               
        StringBuffer buffer = new StringBuffer();
        padStr = "";
		for (int i=0; i< width-str.length(); i++)
        {
             
			 buffer.append(".");
			 padStr = buffer.toString();		 
                 
        }
        
		return padStr;
    }
	
	// find cell values in table
	public Object GetData(JTable table, int row_index, int col_index)
	{
		    return jtable.getModel().getValueAt(row_index, col_index);
	}  

	
	// Pct functor
	public TiqView getPct(TiqView pctFunc)
	{
		
		pct = new Pct(pctFunc);
		
		return pct;
		
	}
	
	public TiqView getAnnpct(TiqView annpctFunc)
	{
		annpct = new Annpct(annpctFunc);
		
		return annpct;
	}
	
	// ytypct functor
	public TiqView getYtypct(TiqView ytypctFunc)
	{
		
		return null;
	}
	
	// divide functor
	public TiqView getDivide(TiqView funcView1, TiqView funcView2)
	{
		
		divide = new Divide(funcView1, funcView2);
		return divide;
		
	}
	
	// ShiftYear 
	public TiqView getShiftYear(TiqView funcView1, int y)
	{
		shiftyear = new ShiftYear(funcView1, y);
		return shiftyear;
		
	}
	
	Scalar scalar = new Scalar(new Integer(100));
	public TiqView getMultiply(TiqView divideView, TiqView scalar)
	{
		
		multiply = new Multiply (divideView, scalar);
		
		return multiply;
		
	}
	
	public TiqView getMultiply1(TiqView ordreDivideView, TiqView scalar)
	{
		
		ordreMultiply = new Multiply (ordreDivideView, scalar);
		
		return ordreMultiply;
		
	}
	
	Scalar s = new Scalar( new Integer (1));
	public TiqView getSubtract(TiqView divideview, TiqView s)
	 {
	    try {
	        subtract = new Subtract( divideView, s);
	    }

	    catch (Exception e){
	            System.out.println(e);
	    }
	    return subtract;
	 }
	
	public TiqView getSubtract1(TiqView odreDivideview, TiqView s)
	 {
	    try {
	        ordreSubstract = new Subtract( ordreDivideView, s);
	    }

	    catch (Exception e){
	            System.out.println(e);
	    }
	    return ordreSubstract;
	 }

		
	public class CustomTableCellRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
			
			for (int i = 0; i < jtable.getRowCount(); i++)
			{
				if (row % 1 == 0)
				{
					//cell.setBackground(Color.lightGray);
				}
				else 
				{
					cell.setBackground(Color.WHITE);
				}
				
			}
			if (isSelected) {
				cell.setBackground(Color.green);
			} 
		
				/*if (row % 2 == 0 ) {
					cell.setBackground(Color.lightGray);
										
				}*/
				
				return cell;
		}
	}
	
	class MultiLineHeaderRenderer extends JList implements TableCellRenderer {
		  public MultiLineHeaderRenderer() {
		    setOpaque(true);
		    //setForeground(UIManager.getColor("TableHeader.foreground"));
		    //setForeground(Color.black);
		    //setBackground(UIManager.getColor("TableHeader.background"));
		    setBackground(Color.lightGray);
		    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		    ListCellRenderer renderer = getCellRenderer();
		    ((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
		    setCellRenderer(renderer);
		  }

		//@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
			      boolean isSelected, boolean hasFocus, int row, int column) {
			    setFont(jtable.getFont());
			    String str = (value == null) ? "" : value.toString();
			    BufferedReader br = new BufferedReader(new StringReader(str));
			    String line;
			    Vector v = new Vector();
			    try {
			      while ((line = br.readLine()) != null) {
			        v.addElement(line);
			      }
			    } catch (IOException ex) {
			      ex.printStackTrace();
			    }
			    setListData(v);
			    return this;
			  }

	 }

}
