import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Newuser {
    
	private String nuser;
    private String ppwd;
    private String repwd;
    private String email;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNuser() {
		return nuser;
	}

	public void setNuser(String nuser) {
		this.nuser = nuser;
	}

	public String getPpwd() {
		return ppwd;
	}

	public void setPpwd(String ppwd) {
		this.ppwd = ppwd;
	}
    
    public String getRepwd() {
		return repwd;
	}

	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}
	
	public String signup() {
    	System.out.println("I am here");
    	System.out.println(email + "," + nuser + "," + ppwd);
    	if (ppwd.equals(repwd)) {
    		dataConn db = new dataConn();
        	
    		if (db.insertRow(nuser,ppwd,email)) {
    			String message = nuser + " - account created";
        		this.nuser = null;
        		this.email = null;
        		FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(message));
                return "newuser";
    		}
    		else {
    			FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Email exists in database",
                                "Error in user registration"));
                return "error";
    		}
        	
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Passwords did not match",
                            "Password Mismatch"));
            return "error";
        }
    }
	public String login() {
		return "login";
	}
}
