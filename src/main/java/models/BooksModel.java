package models;

public class BooksModel {
    private BookModel[] books;

    public static class Builder {
        private BooksModel booksModel;

        public Builder() {
            booksModel = new BooksModel();
        }

        public Builder addBooksModel(BookModel[] bookModels) {
            booksModel.books = bookModels;
            return this;
        }

        public BooksModel build() {
            return booksModel;
        }
    }

    public BookModel[] getBooks() {
        return books;
    }
}
