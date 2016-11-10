package domain;


public class LoginJSONResponse
{
    private String auth_token;
    private boolean success;
    private String email;
    private String _id;

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    private String profile_id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginJSONResponse)) return false;

        LoginJSONResponse that = (LoginJSONResponse) o;

        if (success != that.success) return false;
        if (!auth_token.equals(that.auth_token)) return false;
        if (!email.equals(that.email)) return false;
        return _id.equals(that._id);

    }

    @Override
    public int hashCode() {
        int result = auth_token.hashCode();
        result = 31 * result + (success ? 1 : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + _id.hashCode();
        return result;
    }

    /*
    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Profile))return false;
        LoginJSONResponse that = (LoginJSONResponse)other;
        if((this.email == null) ? (that.email != null) : !this.email.equals(that.email)) return false;
        if(this.email != that.email) return false;
        if((this.success == false) ? (that.success == true) : !this.success == that.success) return false;
        if(this.success != that.success) return false;
        if((this.auth_token == null) ? (that.auth_token != null) : !this.auth_token.equals(that.auth_token)) return false;
        if(this.auth_token != that.auth_token) return false;
        return true;
    }
     */


}
