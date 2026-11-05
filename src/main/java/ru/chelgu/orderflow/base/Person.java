package ru.chelgu.orderflow.base;

public class Person extends AbstractPerson implements Renamable {
    // класс = представление данных + поведение

    // private, public, protected, -

    private String name;

    public String getName() {
        return name;
    }

    String setName(String name) {
        return this.name = name;
    }

    public String toString() {
        return "id=" + id + ", name=" + name;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }
}
