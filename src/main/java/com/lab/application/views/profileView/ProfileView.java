package com.lab.application.views.profileView;

import com.lab.application.entity.User;
import com.lab.application.service.impl.UserDetailsServiceImpl;
import com.lab.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;


@PageTitle("Person Form")
@Route(value = "person-form", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {

    private final EmailField email = new EmailField("Email");
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final DatePicker birthday = new DatePicker("Birth Date");
    private final Button save = new Button("Save");
    private final Binder<User> binder = new BeanValidationBinder<>(User.class);
    private final UserDetailsServiceImpl userDetailsService;

    public ProfileView(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
        configureForm();
        addClassName("person-form-view");


        binder.bindInstanceFields(this);
        binder.readBean(userDetailsService.getCurrentUser());
        save.addClickListener(click -> {
            validateAndSave();
            Notification.show("Profile updated successfully!").setPosition(Notification.Position.BOTTOM_END);
        });

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

    }
    private void validateAndSave() {
        var user = userDetailsService.getCurrentUser();
        try{
            binder.writeBean(user);
            userDetailsService.updateProfile(user);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ProfileEvent extends ComponentEvent<ProfileView> {
        private final User user;

        protected ProfileEvent(ProfileView source, User user) {
            super(source, false);
            this.user = user;
        }
        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends ProfileView.ProfileEvent {
        SaveEvent(ProfileView source, User user) {
            super(source, user);
        }
    }

    private void configureForm() {
        addListener(ProfileView.SaveEvent.class, this::saveUser);
    }

    private void saveUser(ProfileView.SaveEvent event) {
        userDetailsService.saveUser(event.getUser());
    }


    private Component createTitle() {
        H3 profile = new H3("Profile");
        profile.getElement().getStyle().set("margin-left", "20px");
        profile.getElement().getStyle().set("margin-top", "20px");
        return profile;
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(email, firstName, lastName, birthday);
        formLayout.getElement().getStyle().set("margin-left", "20px");
        formLayout.getElement().getStyle().set("margin-right", "20px");
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.getElement().getStyle().set("margin-left", "20px");
        return buttonLayout;
    }



}
