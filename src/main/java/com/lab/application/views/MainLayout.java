package com.lab.application.views;

import com.lab.application.security.SecurityService;
import com.lab.application.views.apartmentsView.ImageListView;
import com.lab.application.views.appNavView.AppNav;
import com.lab.application.views.appNavView.AppNavItem;
import com.lab.application.views.chatView.ChatView;
import com.lab.application.views.creditCardView.CreditCardFormView;
import com.lab.application.views.dashboardView.DashboardView;
import com.lab.application.views.listView.ListView;
import com.lab.application.views.mapView.MapView;
import com.lab.application.views.profileView.ProfileView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;
    private final SecurityService securityService;
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Button logout = new Button("Log out", event -> securityService.logout());
        Button profile = new Button("Profile", event -> UI.getCurrent().navigate("person-form"));
        logout.addClassName("logout-button");
        HorizontalLayout header = new HorizontalLayout(logout, profile);

        header.getElement().getStyle().set("margin-left", "auto");
        header.addClassNames("py-0", "px-m");

        addToNavbar(toggle, header);
    }

    private void addDrawerContent() {

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();
        AppNavItem dashboard = new AppNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create());
        dashboard.addClassName("main-layout__nav-item");

        AppNavItem activeClients = new AppNavItem("Active Clients", ListView.class, LineAwesomeIcon.USER.create());
        activeClients.addClassName("main-layout__nav-item");

        AppNavItem map = new AppNavItem("Map", MapView.class, LineAwesomeIcon.MAP.create());
        map.addClassName("main-layout__nav-item");

        AppNavItem profile = new AppNavItem("Profile", ProfileView.class, LineAwesomeIcon.USER.create());
        profile.addClassName("main-layout__profile");

        AppNavItem apartments = new AppNavItem("Apartments", ImageListView.class, LineAwesomeIcon.HOME_SOLID.create());
        apartments.addClassName("main-layout__nav-item");

        AppNavItem cardForm = new AppNavItem("Card Form", CreditCardFormView.class, LineAwesomeIcon.CREDIT_CARD.create());
        cardForm.addClassName("main-layout__nav-item");

        AppNavItem chat = new AppNavItem("Chat", ChatView.class, LineAwesomeIcon.COMMENTS.create());
        chat.addClassName("main-layout__nav-item");

        nav.addItem(dashboard);
        nav.addItem(activeClients);
        nav.addItem(map);
        nav.addItem(apartments);
        nav.addItem(chat);

        return nav;
    }

    private Footer createFooter() {
        return new Footer();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}