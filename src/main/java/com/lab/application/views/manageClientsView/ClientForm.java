package com.lab.application.views.manageClientsView;

import com.lab.application.entity.Client;
import com.lab.application.service.ClientService;
import com.lab.application.service.impl.ClientServiceImpl;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.formlayout.FormLayout;


import static com.vaadin.flow.component.ComponentUtil.addListener;

public class ClientForm extends FormLayout{

    private TextField client = new TextField("Client");
    private ComboBox<String> status = new ComboBox<>("Status");
    private TextField amount = new TextField("Amount");
    private DatePicker date = new DatePicker("Date");

    private Button validateClient = new Button("Validate Client");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<Client> binder = new BeanValidationBinder<>(Client.class);

    private final ClientService clientService;

    public ClientForm(ClientService clientService) {
        this.clientService = clientService;
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        status.setItems("Success", "Pending", "Error");

            add(
                client,
                amount,
                date,
                status,
                validateClient,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        Client test = binder.getBean();

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); // <3>
        validateClient.addClickListener(event -> {
            boolean isValid = clientService.validateClient(binder.getBean());

            if(isValid){
                Notification.show("Client is fraud!");
            }else{
                Notification.show("Client is valid!");
            }
        });

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }



    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean())); // <6>
        }
    }


    public void setContact(Client contact) {
        binder.setBean(contact); // <1>
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ClientForm> {
        private Client contact;

        protected ContactFormEvent(ClientForm source, Client contact) {
            super(source, false);
            this.contact = contact;
        }

        public Client getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ClientForm source, Client contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ClientForm source, Client contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ClientForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }



}
