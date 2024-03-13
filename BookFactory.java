class BookFactory implements LibraryItemFactory {
    @Override
    public LibraryItem createItem() {
        return new Book();
    }
}