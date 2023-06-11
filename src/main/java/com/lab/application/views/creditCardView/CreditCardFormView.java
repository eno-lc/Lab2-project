package com.lab.application.views.creditCardView;

import com.lab.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Credit Card Form")
@Route(value = "credit-card-form", layout = MainLayout.class)
@PermitAll
public class CreditCardFormView extends Div {

    private TextField cardNumber;
    private TextField cardholderName;
    private Select<Integer> month;
    private Select<Integer> year;
    private ExpirationDateField expiration;
    private PasswordField csc;
    private Button cancel;
    private Button submit;

    public CreditCardFormView() {
        addClassName("credit-card-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> {
            Notification.show("Reservation cancelled successfully!");
            UI.getCurrent().navigate("image-list");
        });
        submit.addClickListener(e -> {
            Notification.show("Reservation created successfully!");
            UI.getCurrent().navigate("image-list");
        });
    }

    private Component createTitle() {
        H3 creditCard = new H3("Credit Card");
        creditCard.addClassName("cc-form-layout");
        return creditCard;
    }

    private Component createFormLayout() {
        cardNumber = new TextField("Credit card number");
        cardNumber.setPlaceholder("1234 5678 9123 4567");
        cardNumber.setAllowedCharPattern("[\\d ]");
        cardNumber.setRequired(true);
        cardNumber.setErrorMessage("Please enter a valid credit card number");

        cardholderName = new TextField("Cardholder name");

        month = new Select<>();
        month.setPlaceholder("Month");
        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        year = new Select<>();
        year.setPlaceholder("Year");
        year.setItems(20, 21, 22, 23, 24, 25);

        expiration = new ExpirationDateField("Expiration date", month, year);
        csc = new PasswordField("CSC");

        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("cc-form-layout");
        formLayout.add(cardNumber, cardholderName, expiration, csc);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        submit = new Button("Submit");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancel = new Button("Cancel");

        buttonLayout.add(submit);
        buttonLayout.add(cancel);
        buttonLayout.setClassName("cc-form-layout");
        return buttonLayout;
    }

    private class ExpirationDateField extends CustomField<String> {
        public ExpirationDateField(String label, Select<Integer> month, Select<Integer> year) {
            setLabel(label);
            HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
        }

    }

}
