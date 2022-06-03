package models.addListOfBooks;

public class CollectionOfIsbns {
    private String isbn;

    public static class Builder {
        private CollectionOfIsbns collectionOfIsbns;

        public Builder() {
            collectionOfIsbns = new CollectionOfIsbns();
        }

        public Builder addIsbn(String isbn) {
            collectionOfIsbns.isbn = isbn;
            return this;
        }

        public CollectionOfIsbns build() {
            return collectionOfIsbns;
        }
    }

    public String getIsbn() {
        return isbn;
    }
}
