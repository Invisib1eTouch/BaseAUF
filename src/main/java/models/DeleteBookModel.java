package models;

public class DeleteBookModel {
    private String userId;
    private String isbn;

    public static class Builder {
        private DeleteBookModel deleteBookModel;

        public Builder() {
            deleteBookModel = new DeleteBookModel();
        }

        public Builder addUserId(String userId) {
            deleteBookModel.userId = userId;
            return this;
        }

        public Builder addIsbn(String isbn) {
            deleteBookModel.isbn = isbn;
            return this;
        }

        public DeleteBookModel build() {
            return deleteBookModel;
        }
    }
}
