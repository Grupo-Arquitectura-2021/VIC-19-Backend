package bo.ucb.edu.vic19.dto;

import java.util.Date;

public class UserResponse {
    private Integer idUser;
    private String userName;
    private String lastName;
    private String email;
    private String password;
//    private String status;

    public UserResponse(Integer idUser,String userName,String lastName, String email, String password) {
        this.idUser = idUser;
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserResponse() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "idNews='" + idUser + '\'' +
                ", title=" + userName + '\'' +
                ", content=" + lastName + '\'' +
                ", dateNews=" + email + '\'' +
                ", urlImage=" + password +
                '}';
    }
}
