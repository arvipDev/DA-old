package domain;


public class UserCred
{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof UserCred))return false;
        UserCred that = (UserCred)other;
        if((this.username == null) ? (that.username != null) : !this.username.equals(that.username)) return false;
        if(this.username != that.username) return false;
        if((this.password == null) ? (that.password != null) : !this.password.equals(that.password)) return false;
        if(this.password != that.password) return false;
        return true;
    }


}
