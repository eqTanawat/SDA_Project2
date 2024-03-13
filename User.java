class User implements LibraryObserver {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(LibraryItem item) {
        System.out.println(name + ": New item added - " + item.getTitle());
    }
}
