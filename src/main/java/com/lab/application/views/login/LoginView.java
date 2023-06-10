package com.lab.application.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {
   private final LoginForm loginForm = new LoginForm();
    public static boolean isDark = false;
    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");
        loginForm.getStyle().set("margin-bottom", "210px");
        loginForm.addClassName("login-form");
        loginForm.setForgotPasswordButtonVisible(false);

        HorizontalLayout header = new HorizontalLayout(loginForm);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        header.addClassName("login-header");

        add(
                header,
                loginForm
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
    public static boolean checkTheme(){
        return isDark;
    }


}
