package domain;


public class BusinessEditJSONSocMedSet
{
    String socname, socwebsite, soccity, soczip;

    public String getSocname() {
        return socname;
    }

    public void setSocname(String socname) {
        this.socname = socname;
    }

    public String getSocwebsite() {
        return socwebsite;
    }

    public void setSocwebsite(String socwebsite) {
        this.socwebsite = socwebsite;
    }

    public String getSoccity() {
        return soccity;
    }

    public void setSoccity(String soccity) {
        this.soccity = soccity;
    }

    public String getSoczip() {
        return soczip;
    }

    public void setSoczip(String soczip) {
        this.soczip = soczip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessEditJSONSocMedSet)) return false;

        BusinessEditJSONSocMedSet that = (BusinessEditJSONSocMedSet) o;

        if (socname != null ? !socname.equals(that.socname) : that.socname != null) return false;
        if (socwebsite != null ? !socwebsite.equals(that.socwebsite) : that.socwebsite != null)
            return false;
        if (soccity != null ? !soccity.equals(that.soccity) : that.soccity != null) return false;
        return soczip != null ? soczip.equals(that.soczip) : that.soczip == null;

    }

    @Override
    public int hashCode() {
        int result = socname != null ? socname.hashCode() : 0;
        result = 31 * result + (socwebsite != null ? socwebsite.hashCode() : 0);
        result = 31 * result + (soccity != null ? soccity.hashCode() : 0);
        result = 31 * result + (soczip != null ? soczip.hashCode() : 0);
        return result;
    }

}
