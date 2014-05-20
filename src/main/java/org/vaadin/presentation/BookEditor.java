/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.presentation;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import javax.inject.Inject;
import org.vaadin.entities.Book;
import org.vaadin.maddon.BeanBinder;
import org.vaadin.maddon.button.ConfirmButton;
import org.vaadin.maddon.button.PrimaryButton;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;
import org.vaadin.presentation.BookEvent.Type;

public class BookEditor extends Window implements Button.ClickListener {

    /* The entity edited by this class */
    private Book book;

    private final MTextField bookTitle = new MTextField("Book Title");
    private final MTextField author = new MTextField("Author");

    /* Action buttons */
    private final Button cancel = new Button("Cancel", this);
    // PrimaryButton reacts hitting on ENTER
    private final Button save = new PrimaryButton("Save", this);
    // Confirm button shows a modal confirmation before firing the actaul event
    private final Button delete = new ConfirmButton("Delete",
            "Are you sure you wish to delete this book?", this);

    public BookEditor() {
        setCaption("Edit Book:");
        setModal(true);
        setContent(
            new MVerticalLayout(
                bookTitle, 
                author, 
                new MHorizontalLayout(save,cancel, delete).withMargin(false)
            )
        );
    }

    public void setBook(Book book) {
        this.book = book;
        /*
         * Bind fields from this class into the book entity, in this simple
         * example that is "bookTitle", in real world apps, probably n+1 other
         * fields as well
         */
        BeanBinder.bind(book, this);

        /* Focus and select existing text in bookTitle field */
        bookTitle.selectAll();

        /* Activate delete button for existing entities */
        boolean newBook = book.getBookId() == 0;
        delete.setVisible(!newBook);

    }

    @Override
    public void buttonClick(Button.ClickEvent ce) {
        /* Fire relevant CDI event on button clicks */
        if (ce.getButton() == save) {
            saveEvent.fire(book);
        } else if (ce.getButton() == delete) {
            deleteEvent.fire(book);
        } else {
            refrehsEvent.fire(book);
        }
    }

    /* "CDI interface" to notify decoupled components. Using traditional API to 
     * other componets would probably be easier in this small app, but just 
     * demonstrating here how all CDI stuff is available for Vaadin apps. 
     */
    
    @Inject
    @BookEvent(Type.SAVE)
    javax.enterprise.event.Event<Book> saveEvent;

    @Inject
    @BookEvent(Type.DELETE)
    javax.enterprise.event.Event<Book> deleteEvent;

    @Inject
    @BookEvent(Type.REFRESH)
    javax.enterprise.event.Event<Book> refrehsEvent;

}
