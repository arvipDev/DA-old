package domain;

import java.util.ArrayList;

public class BusinessEditJSONProSer
{
    private String name_for_url;
    private ArrayList<String> urlImage;
    private String busdesc;

    public String getName_for_url() {
        return name_for_url;
    }

    public void setName_for_url(String name_for_url) {
        this.name_for_url = name_for_url;
    }

    public ArrayList<String> getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(ArrayList<String> urlImage) {
        this.urlImage = urlImage;
    }

    public String getBusdesc() {
        return busdesc;
    }

    public void setBusdesc(String busdesc) {
        this.busdesc = busdesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessEditJSONProSer)) return false;

        BusinessEditJSONProSer that = (BusinessEditJSONProSer) o;

        if (name_for_url != null ? !name_for_url.equals(that.name_for_url) : that.name_for_url != null)
            return false;
        if (urlImage != null ? !urlImage.equals(that.urlImage) : that.urlImage != null)
            return false;
        return busdesc != null ? busdesc.equals(that.busdesc) : that.busdesc == null;

    }

    @Override
    public int hashCode() {
        int result = name_for_url != null ? name_for_url.hashCode() : 0;
        result = 31 * result + (urlImage != null ? urlImage.hashCode() : 0);
        result = 31 * result + (busdesc != null ? busdesc.hashCode() : 0);
        return result;
    }
}
