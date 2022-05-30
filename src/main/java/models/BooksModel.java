package models;

public class BooksModel {
    private BookModel[] books;

    public static class Builder {
        BooksModel booksModel;

        public Builder() {
            booksModel = new BooksModel();
        }

        public Builder addBooksModel(BookModel[] bookModels) {
            booksModel.books = bookModels;
            return this;
        }
    }

    public BookModel[] getBooks() {
        return books;
    }
}
