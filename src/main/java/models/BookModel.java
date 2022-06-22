package models;

import io.cucumber.messages.internal.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BookModel {
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    @JsonProperty("publish_date")

    private String publishDate;
    private String publisher;
    private int pages;
    private String description;
    private String website;

    public static class Builder {
        private BookModel bookModel;

        public Builder() {
            bookModel = new BookModel();
        }

        public Builder addIsbn(String isbn) {
            bookModel.isbn = isbn;
            return this;
        }

        public Builder addTitle(String title) {
            bookModel.title = title;
            return this;
        }

        public Builder addAuthor(String author) {
            bookModel.author = author;
            return this;
        }

        public Builder addPublishDate(String publishDate) {
            bookModel.publishDate = publishDate;
            return this;
        }

        public Builder addPublisher(String publisher) {
            bookModel.publisher = publisher;
            return this;
        }

        public Builder addPages(int pages) {
            bookModel.pages = pages;
            return this;
        }

        public Builder addDescription(String description) {
            bookModel.description = description;
            return this;
        }

        public Builder addWebsite(String website) {
            bookModel.website = website;
            return this;
        }

        public BookModel build() {
            return bookModel;
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookModel bookModel = (BookModel) o;

        if (pages != bookModel.pages) return false;
        if (!Objects.equals(isbn, bookModel.isbn)) return false;
        if (!Objects.equals(title, bookModel.title)) return false;
        if (!Objects.equals(subTitle, bookModel.subTitle)) return false;
        if (!Objects.equals(author, bookModel.author)) return false;
        if (!Objects.equals(publishDate, bookModel.publishDate)) return false;
        if (!Objects.equals(publisher, bookModel.publisher)) return false;
        if (!Objects.equals(description, bookModel.description)) return false;
        return Objects.equals(website, bookModel.website);
    }
}
