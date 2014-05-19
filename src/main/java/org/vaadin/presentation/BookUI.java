package org.vaadin.presentation;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.vaadin.BookService;
import org.vaadin.entities.Book;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.presentation.BookEvent.Type;

@CDIUI
@Theme("dawn")
public class BookUI extends UI {

    @Inject
    private BookService bookService;

    @Inject
    BookEditor bookEditor;

    MTable<Book> bookTable = new MTable().withProperties("bookId", "bookTitle").withColumnHeaders("ID", "Title");

    Header header = new Header(
            "This is a simple TomEE + Vaadin CDI example project");

    @Override
    public void init(VaadinRequest request) {

        listBooks();

        /*
         * Add value change listener to table that opens the selected book into
         * an editor.
         */
        bookTable.addMValueChangeListener(new MValueChangeListener<Book>() {

            @Override
            public void valueChange(MValueChangeEvent<Book> event) {
                editBook(event.getValue());
            }
        });

        Button addButton = new Button("+", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                addBook();
            }
        });

        setContent(new MVerticalLayout(header, addButton, bookTable));
    }

    // Controller methods, in a big project, consider using separate
    // controller/presenter for improved testability
    private void listBooks() {
        bookTable.setBeans(bookService.getAllBooks());
    }

    void editBook(Book book) {
        if (book != null) {
            bookEditor.setBook(book);
            addWindow(bookEditor);
        } else {
            removeWindow(bookEditor);
        }
    }

    void addBook() {
        bookEditor.setBook(new Book());
        addWindow(bookEditor);
    }
    
    /* These methods get called by CDI event system, in this example events
     * are arised from BookEditor.
     */

    void saveBook(@Observes @BookEvent(Type.SAVE) Book book) {
        bookService.saveOrPersist(book);
        listBooks();
        removeWindow(bookEditor);
    }

    void resetBook(@Observes @BookEvent(Type.REFRESH) Book book) {
        listBooks();
        removeWindow(bookEditor);
    }

    void deleteBook(@Observes @BookEvent(Type.DELETE) Book book) {
        bookService.deleteBook(book);
        listBooks();
    }

}
