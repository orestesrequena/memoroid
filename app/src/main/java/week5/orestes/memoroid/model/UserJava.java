package week5.orestes.memoroid.model;

import java.util.Objects;

public class UserJava {

    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJava userJava = (UserJava) o;
        return Objects.equals(firstName, userJava.firstName) &&
                Objects.equals(lastName, userJava.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName);
    }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserJava{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
