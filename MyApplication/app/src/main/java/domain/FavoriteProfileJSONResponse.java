package domain;

import java.util.List;

public class FavoriteProfileJSONResponse
{
    private String name, profile_pic, share, email, bus_desc;
    private List<String> product_url;

    public List<String> getProduct_url() {
        return product_url;
    }

    public void setProduct_url(List<String> product_url) {
        this.product_url = product_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBus_desc() {
        return bus_desc;
    }

    public void setBus_desc(String bus_desc) {
        this.bus_desc = bus_desc;
    }

}
