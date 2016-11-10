package domain;

public class GetProfileJSONResponse
{
    private String name, profileURLName, looking_for, profilePicture;
    private Boolean isBusiness;

    public Boolean isBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public String getProfileURLname() {
        return profileURLName;
    }

    public void setProfileURLname(String profileURLName) {
        this.profileURLName = profileURLName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetProfileJSONResponse)) return false;

        GetProfileJSONResponse that = (GetProfileJSONResponse) o;

        if (!name.equals(that.name)) return false;
        if (!profileURLName.equals(that.profileURLName)) return false;
        if (!looking_for.equals(that.looking_for)) return false;
        return isBusiness.equals(that.isBusiness);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + profileURLName.hashCode();
        result = 31 * result + looking_for.hashCode();
        result = 31 * result + isBusiness.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return ("[" + name + " " + profileURLName + " " + looking_for + " " + isBusiness.toString() + "]");
    }
}
