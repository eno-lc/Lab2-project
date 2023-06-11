package com.lab.application.views.usersView;

import com.lab.application.entity.User;
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

public class UserForm extends FormLayout {
    Binder<User> binder = new BeanValidationBinder<>(User.class);
    TextField username = new TextField("Username");
    ComboBox<String> roles = new ComboBox<>("Role");
    PasswordField password = new PasswordField("Password");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private User user;
    private final UserDetailsService jpaUserDetailsService;

    public UserForm(UserDetailsService jpaUserDetailsService){
        this.jpaUserDetailsService = jpaUserDetailsService;
        addClassName("user-form");
        binder.bindInstanceFields(this);
        roles.setItems("ROLE_ADMIN", "ROLE_MANAGER");
        roles.setItemLabelGenerator(String::toString);
        add(
                username,
                password,
                roles,
                createButtonLayout()
        );
    }

    public void setUser(User user){
        this.user = user;
        binder.readBean(user);
    }

    private Component createButtonLayout() {

        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setConfirmButtonTheme("error primary");
        confirmDialog.setHeader("Confirmation");
        confirmDialog.setText("Are you sure you want to delete this user?");
        confirmDialog.setConfirmButton("Delete", e -> fireEvent(new DeleteEvent(this, user)));
        confirmDialog.setCancelButton("Cancel", e -> confirmDialog.close());

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> confirmDialog.open());
        cancel.addClickListener(click -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private final User user;

        protected UserFormEvent(UserForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, User user) {
            super(source, user);
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.add("User saved successfully.");
            notification.setPosition(Notification.Position.TOP_END);
            notification.setDuration(3000);
            notification.open();
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, User user) {
            super(source, user);
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            notification.add("User deleted successfully.");
            notification.setPosition(Notification.Position.TOP_END);
            notification.setDuration(3000);
            notification.open();
        }
    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
