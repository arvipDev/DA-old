package domain;

public class FavoritesJSONResponse
{
    private String profile_id;
    private String image_url;
    private String created_at;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritesJSONResponse)) return false;

        FavoritesJSONResponse favorite = (FavoritesJSONResponse) o;

        if (!profile_id.equals(favorite.profile_id)) return false;
        if (image_url != null ? !image_url.equals(favorite.image_url) : favorite.image_url != null)
            return false;
        if (!created_at.equals(favorite.created_at)) return false;
        return _id.equals(favorite._id);

    }

    @Override
    public int hashCode() {
        int result = profile_id.hashCode();
        result = 31 * result + (image_url != null ? image_url.hashCode() : 0);
        result = 31 * result + created_at.hashCode();
        result = 31 * result + _id.hashCode();
        return result;
    }

}
