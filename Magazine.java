class Magazine implements LibraryItem {
    private String title;

    public Magazine() {
        this.title = "Sample Magazine";
    }

    @Override
    public String getTitle() {
        return title;
    }
}