package models.addListOfBooks;

public class AddListOfBooks {
    private String userId;
    private CollectionOfIsbns[] collectionOfIsbns;

    public static class Builder {
        private AddListOfBooks addListOfBooks;

        public Builder() {
            addListOfBooks = new AddListOfBooks();
        }

        public Builder addUserId(String userId) {
            addListOfBooks.userId = userId;
            return this;
        }

        public Builder addCollectionOfIsbns(CollectionOfIsbns[] collectionOfIsbns) {
            addListOfBooks.collectionOfIsbns = collectionOfIsbns;
            return this;
        }

        public AddListOfBooks build() {
            return addListOfBooks;
        }
    }

    public String getUserId() {
        return userId;
    }

    public CollectionOfIsbns[] getCollectionOfIsbns() {
        return collectionOfIsbns;
    }
}
