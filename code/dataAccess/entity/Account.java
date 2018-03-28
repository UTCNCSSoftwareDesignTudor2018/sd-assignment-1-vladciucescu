package dataAccess.entity;

import java.util.Objects;

public class Account extends DataEntity {

    private final String username;
    private final String password;
    private final String email;
    private final Profile profile;

    public Account() {
        super();
        this.username = "username";
        this.password = "123";
        this.email = "mail@domain.com";
        this.profile = new Profile();
    }

    public Account(int id, String username, String password, String email, Profile profile) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(email, account.email) &&
                Objects.equals(profile, account.profile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), username, password, email, profile);
    }

    @Override
    public String toString() {
        return "Account{id=" + getId() + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
