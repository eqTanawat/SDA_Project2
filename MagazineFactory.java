class MagazineFactory implements LibraryItemFactory {
    @Override
    public LibraryItem createItem() {
        return new Magazine();
    }
}