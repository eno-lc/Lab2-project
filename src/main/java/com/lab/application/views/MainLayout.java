package com.lab.application.views;

import com.lab.application.views.apartments.ImageListView;
import com.lab.application.views.creditCardView.CreditCardFormView;
import com.lab.application.views.dashboard.DashboardView;
import com.lab.application.views.listView.ListView;
import com.lab.application.views.map.MapView;
import com.lab.application.views.profile.ProfileView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
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

        nav.addItem(profile);
        nav.addItem(dashboard);
        nav.addItem(activeClients);
        nav.addItem(map);
        nav.addItem(apartments);
        nav.addItem(cardForm);

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