package domain;

import android.graphics.Bitmap;

public class Profile
{
    private Bitmap img;
    private String description;

    public void Profile ()
    {
        this.img = null;
        this.description = null;
    }

    public void setImage(Bitmap img)
    {
        this.img = img;
    }

    public Bitmap getImage()
    {
        return img;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Profile))return false;
        Profile that = (Profile)other;
        if((this.description == null) ? (that.description != null) : !this.description.equals(that.description)) return false;
        if(this.description != that.description) return false;
        if((this.img == null) ? (that.img != null) : !this.img.equals(that.img)) return false;
        if(this.img != that.img) return false;
        return true;
    }

}
