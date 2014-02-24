package org.vaadin.presentation;

import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import javax.inject.Inject;
import org.vaadin.BookService;
import org.vaadin.entities.Book;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIUI
public class BookUI extends UI {

    @Inject
    private BookService bookService;

    MTable<Book> bookTable = new MTable().withColumnHeaders("ID", "Title");

    Header header = new Header(
            "This is a simple TomEE + Vaadin CDI example project");

    @Override
    public void init(VaadinRequest request) {

        listBooks();

        final TextField titleField = new TextField();

        Button addButton = new Button("Add book", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Book b = new Book();
                b.setBookTitle(titleField.getValue());
                bookService.addBook(b);
                titleField.setValue("");
                listBooks();
            }
        });

        setContent(new MVerticalLayout(header, bookTable, titleField, addButton));
    }

    private void listBooks() {
        bookTable.setBeans(bookService.getAllBooks());
    }

}
