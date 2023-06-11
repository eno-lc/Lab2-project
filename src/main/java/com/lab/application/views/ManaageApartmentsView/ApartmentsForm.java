package com.lab.application.views.ManaageApartmentsView;

import com.lab.application.entity.ImageCard;
import com.lab.application.entity.User;
import com.lab.application.repository.ImageCardRepository;
import com.lab.application.service.ImageCardService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.security.core.userdetails.UserDetailsService;

public class ApartmentsForm extends FormLayout {
    Binder<ImageCard> binder = new BeanValidationBinder<>(ImageCard.class);
    TextField url = new TextField("Image URL");
    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private ImageCard imageCard;
    private final ImageCardService imageCardService;

    public ApartmentsForm(ImageCardService imageCardService){
        this.imageCardService = imageCardService;
        addClassName("user-form");
        binder.bindInstanceFields(this);
        add(
                url,
                title,
                description,
                createButtonLayout()
        );
    }

    public void setUser(ImageCard imageCard){
        this.imageCard = imageCard;
        binder.readBean(imageCard);
    }

    private Component createButtonLayout() {

        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setConfirmButtonTheme("error primary");
        confirmDialog.setHeader("Confirmation");
        confirmDialog.setText("Are you sure you want to delete this apartment?");
        confirmDialog.setConfirmButton("Delete", e -> fireEvent(new ApartmentsForm.DeleteEvent(this, imageCard)));
        confirmDialog.setCancelButton("Cancel", e -> confirmDialog.close());

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> confirmDialog.open());
        cancel.addClickListener(click -> fireEvent(new ApartmentsForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(imageCard);
            fireEvent(new ApartmentsForm.SaveEvent(this, imageCard));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ApartmentsFormEvent extends ComponentEvent<ApartmentsForm> {
        private final ImageCard card;

        protected ApartmentsFormEvent(ApartmentsForm source, ImageCard card) {
            super(source, false);
            this.card = card;
        }

        public ImageCard getCard() {
            return card;
        }
    }

    public static class SaveEvent extends ApartmentsForm.ApartmentsFormEvent {
        SaveEvent(ApartmentsForm source, ImageCard card) {
            super(source, card);
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.add("Apartment saved successfully.");
            notification.setPosition(Notification.Position.TOP_END);
            notification.setDuration(3000);
            notification.open();
        }
    }

    public static class DeleteEvent extends ApartmentsForm.ApartmentsFormEvent {
        DeleteEvent(ApartmentsForm source, ImageCard card) {
            super(source, card);
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            notification.add("Apartment deleted successfully.");
            notification.setPosition(Notification.Position.TOP_END);
            notification.setDuration(3000);
            notification.open();
        }
    }

    public static class CloseEvent extends ApartmentsForm.ApartmentsFormEvent {
        CloseEvent(ApartmentsForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

