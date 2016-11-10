package domain;


public class BusinessEditJSONContacts
{
    private String contact1, contact2, contact3;
    private String email1, email2, email3;
    private String phone1, phone2, phone3;

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessEditJSONContacts)) return false;

        BusinessEditJSONContacts that = (BusinessEditJSONContacts) o;

        if (contact1 != null ? !contact1.equals(that.contact1) : that.contact1 != null)
            return false;
        if (contact2 != null ? !contact2.equals(that.contact2) : that.contact2 != null)
            return false;
        if (contact3 != null ? !contact3.equals(that.contact3) : that.contact3 != null)
            return false;
        if (email1 != null ? !email1.equals(that.email1) : that.email1 != null) return false;
        if (email2 != null ? !email2.equals(that.email2) : that.email2 != null) return false;
        if (email3 != null ? !email3.equals(that.email3) : that.email3 != null) return false;
        if (phone1 != null ? !phone1.equals(that.phone1) : that.phone1 != null) return false;
        if (phone2 != null ? !phone2.equals(that.phone2) : that.phone2 != null) return false;
        if (phone3 != null ? !phone3.equals(that.phone3) : that.phone3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contact1 != null ? contact1.hashCode() : 0;
        result = 31 * result + (contact2 != null ? contact2.hashCode() : 0);
        result = 31 * result + (contact3 != null ? contact3.hashCode() : 0);
        result = 31 * result + (email1 != null ? email1.hashCode() : 0);
        result = 31 * result + (email2 != null ? email2.hashCode() : 0);
        result = 31 * result + (email3 != null ? email3.hashCode() : 0);
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
        result = 31 * result + (phone3 != null ? phone3.hashCode() : 0);
        return result;
    }
}
