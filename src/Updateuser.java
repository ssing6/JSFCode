import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.sql.*;

@ManagedBean
@SessionScoped
public class Updateuser {
	
    private String email;
    private String name;
    private String pwd;
    private String npwd;
    private String nrpwd;
    private String retpwd;
    
    public String getNrpwd() {
		return nrpwd;
	}

	public void setNrpwd(String nrpwd) {
		this.nrpwd = nrpwd;
	}

	public String getNpwd() {
		return npwd;
	}

	public void setNpwd(String npwd) {
		this.npwd = npwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String check() {
    	System.out.println("I am here");
    	System.out.println(email);
    	dataConn db = new dataConn();
    	if(db.checkemail(email)) {
    		System.out.println("i am here1");
    		System.out.println("details : " + db.dbusername + ", " +  db.dbpassword);
    		this.name = db.dbusername;
    		this.retpwd = db.dbpassword;
    		return "updatedata";
    	}
    	else {
    		System.out.println("i am here2");
    		FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Email not in database",
                            "Email not found"));
    		return "error";
    	}
    }
	public String login() {
		return "login";
	}
	public String newuser() {
		return "newuser";
	}
	public String update() {
		if(pwd.equals(this.retpwd)) {
			if (npwd.equals(nrpwd)) {
				if(pwd.equals(npwd)) {
					FacesContext.getCurrentInstance().addMessage(
		                    null,
		                    new FacesMessage(FacesMessage.SEVERITY_WARN,
		                            "Old and New passwords match",
		                            "Passwords same error"));
					return "error";
				}
				else {
					dataConn db = new dataConn();
					if(db.updateEmail(this.email, this.nrpwd))
					{
						FacesContext.getCurrentInstance().addMessage(
		                        null,
		                        new FacesMessage("New Password Updated. Hit login"));
						return "updated";
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(
			                    null,
			                    new FacesMessage(FacesMessage.SEVERITY_WARN,
			                            "Unable to update details",
			                            "Database error"));
						return "error";
					}
				}
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_WARN,
	                            "New Passwords did not match",
	                            "Password Mismatch"));
				return "error";
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid Old Password",
                            "Old Password Error"));
			return "error";
		}
	}
}
