package bo.ucb.edu.vic19.dto;

import java.util.List;

public class UserDataRequest {
    private List<UserResponse> users;
    private Integer total;

    public UserDataRequest() {
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserDataRequest{" +
                "users=" + users +
                ", total=" + total +
                '}';
    }
}
