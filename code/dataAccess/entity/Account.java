package dataAccess.entity;

import java.util.Objects;

public class Account extends DataEntity {

    private final String username;
    private String password;
    private String email;
    private final User user;

    public Account() {
        super();
        this.username = "username";
        this.password = "123";
        this.email = "mail@domain.com";
        this.user = new User();
    }

    public Account(int id, String username, String password, String email, User user) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getId() == account.getId() && username.equals(account.getUsername());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), username);
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
