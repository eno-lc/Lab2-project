package com.lab.application.views.usersView;

import com.lab.application.entity.User;
import com.lab.application.service.impl.UserDetailsServiceImpl;
import com.lab.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringComponent
@Scope("prototype")
@Route(value = "manage-users", layout = MainLayout.class)
@PageTitle("Manage Users")
@RolesAllowed({"ROLE_ADMIN"})
public class ManagingUsers extends VerticalLayout {


    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserForm form;
    private final UserDetailsServiceImpl service;

    public ManagingUsers(UserDetailsServiceImpl service){
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllUsers(filterText.getValue()));
    }


    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new UserForm(service);
        form.setWidth("25em");

        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
    }

    public void deleteUser(UserForm.DeleteEvent evt) {
        service.deleteUser(evt.getUser());
        updateList();
        closeEditor();
    }

    public void saveUser(UserForm.SaveEvent evt) {
        service.saveUser(evt.getUser());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {

        filterText.setPlaceholder("Filter by username...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Create user");
        addContactButton.addClassName("add-contact-button");
        addContactButton.addClickListener(click -> addUser());

        HorizontalLayout toolbarHorizontalLayout = new HorizontalLayout(filterText, addContactButton);
        toolbarHorizontalLayout.addClassName("toolbar");

        return toolbarHorizontalLayout;
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "roles");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void editUser(User user) {
        if(user == null){
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
