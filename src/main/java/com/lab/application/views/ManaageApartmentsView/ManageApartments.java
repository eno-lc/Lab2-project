package com.lab.application.views.ManaageApartmentsView;


import com.lab.application.entity.ImageCard;
import com.lab.application.service.ImageCardService;
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

@SpringComponent
@Scope("prototype")
@Route(value = "manage-apartments", layout = MainLayout.class)
@PageTitle("Manage Apartments")
@RolesAllowed({"ROLE_ADMIN"})
public class ManageApartments extends VerticalLayout {

    Grid<ImageCard> grid = new Grid<>(ImageCard.class);
    TextField filterText = new TextField();
    ApartmentsForm form;
    private final ImageCardService service;

    public ManageApartments(ImageCardService service){
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
        grid.setItems(service.findAll(filterText.getValue()));
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
        form = new ApartmentsForm(service);
        form.setWidth("25em");

        form.addListener(ApartmentsForm.SaveEvent.class, this::saveUser);
        form.addListener(ApartmentsForm.DeleteEvent.class, this::deleteUser);
        form.addListener(ApartmentsForm.CloseEvent.class, e -> closeEditor());
    }

    public void deleteUser(ApartmentsForm.DeleteEvent evt) {
        service.deleteImageCard(evt.getCard().getId());
        updateList();
        closeEditor();
    }

    public void saveUser(ApartmentsForm.SaveEvent evt) {
        service.saveImageCard(evt.getCard());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {

        filterText.setPlaceholder("Filter by description...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add apartment");
        addContactButton.addClassName("add-contact-button");
        addContactButton.addClickListener(click -> addCard());

        HorizontalLayout toolbarHorizontalLayout = new HorizontalLayout(filterText, addContactButton);
        toolbarHorizontalLayout.addClassName("toolbar");

        return toolbarHorizontalLayout;
    }

    private void addCard() {
        grid.asSingleSelect().clear();
        editCard(new ImageCard());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("url", "description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editCard(evt.getValue()));
    }

    private void editCard(ImageCard card) {
        if(card == null){
            closeEditor();
        } else {
            form.setUser(card);
            form.setVisible(true);
            addClassName("editing");
        }
    }



}
