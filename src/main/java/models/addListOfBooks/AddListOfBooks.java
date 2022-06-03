package models.addListOfBooks;

public class AddListOfBooks {
    private String userId;
    private CollectionOfIsbns[] collectionOfIsbns;

    public static class Build {
        private AddListOfBooks addListOfBooks;

        public Build() {
            addListOfBooks = new AddListOfBooks();
        }

        public Build addUserId(String userId) {
            addListOfBooks.userId = userId;
            return this;
        }

        public Build addCollectionOfIsbns(CollectionOfIsbns[] collectionOfIsbns) {
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
