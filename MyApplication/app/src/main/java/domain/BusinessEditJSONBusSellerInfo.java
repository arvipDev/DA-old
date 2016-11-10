package domain;


public class BusinessEditJSONBusSellerInfo
{
    private String busdesc;
    private String buslook;

    public String getBusdesc() {
        return busdesc;
    }

    public void setBusdesc(String busdesc) {
        this.busdesc = busdesc;
    }

    public String getBuslook() {
        return buslook;
    }

    public void setBuslook(String buslook) {
        this.buslook = buslook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessEditJSONBusSellerInfo)) return false;

        BusinessEditJSONBusSellerInfo that = (BusinessEditJSONBusSellerInfo) o;

        if (busdesc != null ? !busdesc.equals(that.busdesc) : that.busdesc != null) return false;
        return buslook != null ? buslook.equals(that.buslook) : that.buslook == null;

    }

    @Override
    public int hashCode() {
        int result = busdesc != null ? busdesc.hashCode() : 0;
        result = 31 * result + (buslook != null ? buslook.hashCode() : 0);
        return result;
    }
}
