package models.addListOfBooks;

public class AddListOfBooks {
    private String userId;
    private CollectionOfIsbns[] collectionOfIsbns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CollectionOfIsbns[] getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(CollectionOfIsbns[] collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}
