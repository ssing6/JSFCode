import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
 
import java.sql.*;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
 
    private static final long serialVersionUID = 1094801825228386363L;
     
    private String pwd;
    private String user;
    
    private String zipcode;

	private Date startDate;
    private Date endDate;
    private String output;
    
    private List<String> list = new ArrayList<String> ();
    private String crop;
    private Map<String,String> cropval;
    private List<List<String>> crops;

    @PostConstruct
    public void init() {
        // list = Arrays.asList("one", "two", "three");
    	cropval  = new HashMap<String, String>();
    	cropval.put("Alfalfa", "Alfalfa");
    	cropval.put("Corn", "Corn");
    	cropval.put("Cotton", "Cotton");
    	cropval.put("Grass Hay", "Grass Hay");
    	cropval.put("Peanuts", "Peanuts");
    	cropval.put("Sorghum", "Sorghum");
    	cropval.put("Soybean", "Soybean");
    	cropval.put("Wheat", "Wheat");
    	//////////////////////////////////////////
    	this.crops = new ArrayList<>();
		List<String> cropvals = new ArrayList<>();
			cropvals.add("Alfalfa");
			cropvals.add("41");
			cropvals.add("86");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Corn");
			cropvals.add("50");
			cropvals.add("86");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Cotton");
			cropvals.add("60");
			cropvals.add("100");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Grass Hay");
			cropvals.add("50");
			cropvals.add("86");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Peanuts");
			cropvals.add("55");
			cropvals.add("95");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Sorghum");
			cropvals.add("55");
			cropvals.add("95");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Soybean");
			cropvals.add("50");
			cropvals.add("95");
		this.crops.add(cropvals);
		
		cropvals = new ArrayList<> ();
			cropvals.add("Wheat");
			cropvals.add("32");
			cropvals.add("86");
		this.crops.add(cropvals);
		
		for(int i=0; i<crops.size(); i++)
			System.out.println((this.crops.get(i)).get(0));
    }
    
    
    public Map<String, String> getCropval() {
        return cropval;
    }
    
    public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	public void setList(List list) {
		this.list = list;
	}
    
    public List<String> getList() {
        return list;
    }
    
    public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPwd() {
        return pwd;
    }
 
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
 
    public String getUser() {
        return user;
    }
 
    public void setUser(String user) {
        this.user = user;
    }
 
    //validate login
    public String validateUsernamePassword() {
    	System.out.println(user + "," + pwd);
    	
    	// call to database // 
    	
        //if (user.equals("admin") && pwd.equals("password")) {
    	dataConn db = new dataConn();
    	boolean chk = false;
    	chk = db.checkUser(user,pwd);
    	// professor bypass credentials
    	if (user.equals("prof@prof.com") && pwd.equals("prof"))
    		chk = true;
    	//
    	if (chk) {
            HttpSession session = SessionBean.getSession();
            System.out.println(session);
            this.user = db.dbusername;
            session.setAttribute("username", user);
            return "admin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username/Password",
                            "Please enter correct username and Password"));
            return "login";
        }
    }
 
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        this.user = null;
        return "login";
    }
    
    public String dateauth() throws JSONException {
    	this.output = null;
    	System.out.println(list.size());
    	 while (!list.isEmpty()) {
    	        list.remove(list.size() - 1);
    	    }
    	System.out.println(startDate + "," + endDate + "," + zipcode);
		String sb = null;
		System.out.println(startDate.compareTo(endDate));
    	if (!(startDate.compareTo(endDate) >= 0)) {
    		try {
    			this.output = "";
    			int zipc = Integer.parseInt(zipcode);
    			System.out.println(zipc);
    			// function call here
    			double tbase = 0;
    			double tmax = 0;
    			double gdd = 0;
    			
    			for(int i=0; i<crops.size(); i++) {
    				if((this.crops.get(i)).get(0).equals(this.crop)) {
    					System.out.println(this.crops.get(i));
    					tbase = Double.parseDouble((this.crops.get(i)).get(1));
    					tmax = Double.parseDouble((this.crops.get(i)).get(2));
    					break;
    				}
    			}
    			System.out.println("Crop selcted is " + this.crop + " temp base is " + tbase + " temp max is " + tmax);
    			this.list.add("Crop " + this.crop + " details are : " + " tbase value " + tbase + ", tmax value " + tmax);
    			List<Date> l= getDaysBetweenDates(startDate, endDate);
    			System.out.println("start Date " + startDate + " end Date " + endDate);
    			for(int i=0; i<l.size(); i++) {
    	    		System.out.println(l.get(i));
    	    		Calendar cal = Calendar.getInstance();
    	    	    cal.setTime(l.get(i));
    	    		System.out.println(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH));
    	    		sb = getData(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH), this.zipcode);
    	    		System.out.println(sb);
    	    		JSONObject jsonObject = new JSONObject(sb.substring(sb.indexOf("{"), (sb.indexOf("}")) + 1));
    	    		System.out.println(jsonObject.get("timestamp").toString());
    	    		System.out.println(jsonObject.get("tempMin"));
    	    		System.out.println(jsonObject.get("tempAvg"));
    	    		System.out.println(jsonObject.get("tempMax"));
    	    		System.out.println(jsonObject.get("postal_code"));
    	    		System.out.println(jsonObject.get("country"));
    	    		System.out.println("-----------------------------");
    	    		this.output = this.output + l.get(i).toString()
    	    								+ " - Minimum Temp " + jsonObject.get("tempMin").toString()
    	    								+ ", Maximum Temp " + jsonObject.get("tempMax").toString() + System.lineSeparator();
    	    		if(Double.parseDouble(jsonObject.get("tempMax").toString())> tmax) {
    	    			gdd = ((Double.parseDouble(jsonObject.get("tempMax").toString()) + Double.parseDouble(jsonObject.get("tempMin").toString()))/2)-tbase; 
    	    		}
    	    		else
    	    		{
    	    			gdd = ((tmax + Double.parseDouble(jsonObject.get("tempMin").toString()))/2)-tbase;
    	    		}
    	    		this.list.add("On " + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH)
    	    						+ " (YYYY/MM/DD), Min Temp is " + jsonObject.get("tempMin").toString()
    	    						+ ", Max Temp is " + jsonObject.get("tempMax").toString() + ". "
    	    						+ "GDD value for crop " + this.crop + " is " + Math.round(gdd*100)/100.00);
    			}
    			System.out.println(this.output);
    			System.out.println(list.size());
    			for(int i=0; i<list.size(); i++)
    				System.out.println(list.get(i));
    		}
    		catch (NumberFormatException e)
    		{
    			e.printStackTrace();
    			FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Invalid Zip Code",
                                "Invalid Zipcode"));
                return "error";
    		}
    		catch (RuntimeException e) {
    			System.out.println("I am here" + sb);
    			e.printStackTrace();
    			if(sb.equals("403"))
    				FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Exceed limit. Wait. Resp - " + sb,
                                "Invalid Data"));
    			else if(sb.equals("400"))
    				FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            		"Invalid Zip Code. Resp - " + sb,
                                    "Invalid Data"));
    			else
    				FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            		"Unable to process request for a date",
                                    "Invalid Data"));
                return "error";
    		} 
            return "admin"; 
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Please check Start Date & End date",
                            "Dates problem"));
            return "error";
        }
    }
    public static String getData(String parsThis, String zipc) throws JSONException
    {
    	String myURL = //"http://www.wunderground.com/history/airport/AAAA/YYYY/MM/DD/DailyHistory.html?HideSpecis=1&format=1";
    					// "http://www.wunderground.com/history/airport/EGLL/2014/12/31/DailyHistory.html?HideSpecis=1&format=1";
    	//"https://api.weathersource.com/v1/0a602b879fa4fb961f5b/history_by_postal_code.json?_callback=processJSON&period=day&postal_code_eq=06516&country_eq=US&timestamp_eq=2016-01-01&fields=postal_code%2Ccountry%2Ctimestamp%2CtempMax%2CtempAvg%2CtempMin";
    			"https://api.weathersource.com/v1/0a602b879fa4fb961f5b/history_by_postal_code.json?_callback=processJSON&period=day&postal_code_eq="+zipc+"&country_eq=US&timestamp_eq=" + parsThis + "&fields=postal_code%2Ccountry%2Ctimestamp%2CtempMax%2CtempAvg%2CtempMin";
    	System.out.println("Requeted URL : " + myURL);
    	StringBuilder sb = new StringBuilder();
    	HttpURLConnection urlConn = null;
    	InputStreamReader in = null;
    	String responseCode = null;
    	try {
    		URL url = new URL(myURL);
    		urlConn = (HttpURLConnection)url.openConnection();
    		//urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        	urlConn.connect();
        	responseCode = Integer.toString(urlConn.getResponseCode());
        	System.out.println(sb + " Response code - " + urlConn.getResponseCode());
    		if (urlConn != null)
    			urlConn.setReadTimeout(60 * 1000);
    		if (urlConn != null && urlConn.getInputStream() != null) {
    			in = new InputStreamReader(urlConn.getInputStream(),
    					Charset.defaultCharset());
    			BufferedReader bufferedReader = new BufferedReader(in);
    			if (bufferedReader != null) {
    				int cp;
    				while ((cp = bufferedReader.read()) != -1) {
    					sb.append((char) cp);
    				}
    				bufferedReader.close();
    			}
    		}
    	in.close();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return responseCode;
    		//throw new RuntimeException("Exception while calling URL:" + myURL, e);
    	}
    	
    	return (sb.toString());
    }
    
    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        dates.add(enddate);
        return dates;
    }

}