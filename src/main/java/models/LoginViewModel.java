package models;

public class LoginViewModel {
    private String userName;
    private String password;

    public static class Builder {
        private LoginViewModel newLoginViewModel;

        public Builder() {
            newLoginViewModel = new LoginViewModel();
        }

        public Builder addUsername(String username) {
            newLoginViewModel.userName = username;
            return this;
        }

        public Builder addPassword(String password) {
            newLoginViewModel.password = password;
            return this;
        }

        public LoginViewModel build() {
            return newLoginViewModel;
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
