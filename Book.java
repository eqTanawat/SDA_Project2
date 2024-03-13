class Book implements LibraryItem {
    private String title;

    public Book() {
        this.title = "Sample Book";
    }

    @Override
    public String getTitle() {
        return title;
    }
}