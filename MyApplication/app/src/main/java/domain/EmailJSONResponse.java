package domain;

public class EmailJSONResponse
{
    private String name;
    private String email;
    private String body;
    private String subject;
    private String date_time;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailJSONResponse)) return false;

        EmailJSONResponse that = (EmailJSONResponse) o;

        if (!name.equals(that.name)) return false;
        if (!email.equals(that.email)) return false;
        if (!body.equals(that.body)) return false;
        if (!subject.equals(that.subject)) return false;
        return date_time.equals(that.date_time);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + body.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + date_time.hashCode();
        return result;
    }

}
