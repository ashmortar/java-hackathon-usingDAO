package models;

public class Member {
    private int id;
    private int teamId;
    private String name;
    private String email;
    private String twitter;

    public Member(String name, String email, String twitter) {
        this.name = name;
        this.email = email;
        this.twitter = twitter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != member.id) return false;
        if (teamId != member.teamId) return false;
        if (!name.equals(member.name)) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        return twitter != null ? twitter.equals(member.twitter) : member.twitter == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + teamId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        return result;
    }
}
