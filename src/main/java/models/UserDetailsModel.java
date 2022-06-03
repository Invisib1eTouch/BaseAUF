package models;

import java.util.Arrays;

public class UserDetailsModel {
    private String userId;
    private String username;
    private BookModel[] books;

    public static class Builder {
        private UserDetailsModel userDetailsModel;

        public Builder() {
            userDetailsModel = new UserDetailsModel();
        }

        public Builder addUserId(String userId) {
            userDetailsModel.userId = userId;
            return this;
        }

        public Builder addUsername(String username) {
            userDetailsModel.username = username;
            return this;
        }

        public Builder addBooksModel(BookModel[] books) {
            userDetailsModel.books = books;
            return this;
        }

        public UserDetailsModel build() {
            return userDetailsModel;
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public BookModel[] getBooks() {
        return books;
    }
}
