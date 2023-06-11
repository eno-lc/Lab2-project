package com.lab.application.views.dashboardView;


import com.lab.application.entity.ServiceHealth;
import com.lab.application.enums.Status;
import com.lab.application.service.ApartmentService;
import com.lab.application.service.ClientService;
import com.lab.application.service.ResourcesService;
import com.lab.application.service.UserService;
import com.lab.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import jakarta.annotation.security.PermitAll;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class DashboardView extends Main {

    private final UserService userService;
    private final ResourcesService resourcesService;
    private final ClientService clientService;
    private final ApartmentService apartmentService;

    public DashboardView(UserService userService, ResourcesService resourcesService, ClientService clientService, ApartmentService apartmentService) {

        this.userService = userService;
        this.resourcesService = resourcesService;
        this.clientService = clientService;
        this.apartmentService = apartmentService;

        addClassName("dashboard-view");

        Board board = new Board();
        board.addRow(createHighlight("Current users", String.valueOf(userService.getUsersCount()), (double) (userService.getUsersCount() / 100)), createHighlight("Generated resources", resourcesService.getResourcesValue() + "$", (double) (resourcesService.getResourcesValue() / 100)),
                createHighlight("Clients", String.valueOf(clientService.getClientCount()), (double) (clientService.getClientCount() / 100)), createHighlight("Total Apartments", String.valueOf(apartmentService.getApartmentCount()), (double) (apartmentService.getApartmentCount() / 100)));
        board.addRow(createViewEvents());
        board.addRow(createServiceHealth(), createResponseTimes());
        add(board);
    }

    private Component createHighlight(String title, String value, Double percentage) {
        VaadinIcon icon = VaadinIcon.ARROW_UP;
        String prefix = "";
        String theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        H2 h2 = new H2(title);
        h2.addClassNames(FontWeight.NORMAL, Margin.NONE, TextColor.SECONDARY, FontSize.XSMALL);

        Span span = new Span(value);
        span.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);

        Icon i = icon.create();
        i.addClassNames(BoxSizing.BORDER, Padding.XSMALL);

        Span badge = new Span(i, new Span(prefix + percentage.toString()));
        badge.getElement().getThemeList().add(theme);

        VerticalLayout layout = new VerticalLayout(h2, span, badge);
        layout.addClassName(Padding.LARGE);
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }

    private Component createViewEvents() {

        HorizontalLayout header = createHeader("View events", "City/month");

        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Apartments");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Berlin", resourcesService.getResourcesValue() / 200, resourcesService.getResourcesValue() / 500, resourcesService.getResourcesValue() / 800, resourcesService.getResourcesValue() / 900, resourcesService.getResourcesValue() / 700, resourcesService.getResourcesValue() / 333));
        conf.addSeries(new ListSeries("London", resourcesService.getResourcesValue() / 50, resourcesService.getResourcesValue() / 40, resourcesService.getResourcesValue() / 80, resourcesService.getResourcesValue() / 70, resourcesService.getResourcesValue() / 126, resourcesService.getResourcesValue() / 12));
        conf.addSeries(new ListSeries("New York", resourcesService.getResourcesValue() / 180, resourcesService.getResourcesValue() / 220, resourcesService.getResourcesValue() / 350, resourcesService.getResourcesValue() / 1239, resourcesService.getResourcesValue() / 1500, resourcesService.getResourcesValue() / 111));
        conf.addSeries(new ListSeries("Japan", resourcesService.getResourcesValue() / 80, resourcesService.getResourcesValue() / 70, resourcesService.getResourcesValue() / 60, resourcesService.getResourcesValue() / 40, resourcesService.getResourcesValue() / 142, resourcesService.getResourcesValue() / 100));

        // Add it all together
        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName(Padding.LARGE);
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
    }

    private Component createServiceHealth() {
        // Header
        HorizontalLayout header = createHeader("Apartments", "Market values");

        // Grid
        Grid<ServiceHealth> grid = new Grid();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(new ComponentRenderer<>(serviceHealth -> {
            Span status = new Span();
            String statusText = getStatusDisplayName(serviceHealth);
            status.getElement().setAttribute("aria-label", "Status: " + statusText);
            status.getElement().setAttribute("title", "Status: " + statusText);
            status.getElement().getThemeList().add(getStatusTheme(serviceHealth));
            return status;
        })).setHeader("").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(ServiceHealth::getCity).setHeader("City").setFlexGrow(1);
        grid.addColumn(ServiceHealth::getInput).setHeader("On use").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ServiceHealth::getOutput).setHeader("Total").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        grid.setItems(new ServiceHealth(Status.EXCELLENT, "New York", (int) apartmentService.findApartmentsByOnUseAndName(true, "New York"), (int) apartmentService.getApartmentsByName("New York")),
                new ServiceHealth(Status.OK, "Berlin", (int) apartmentService.findApartmentsByOnUseAndName(true, "Berlin"), (int) apartmentService.getApartmentsByName("Berlin")),
                new ServiceHealth(Status.FAILING, "Japan", (int) apartmentService.findApartmentsByOnUseAndName(true, "Japan"), (int) apartmentService.getApartmentsByName("Japan")));

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, grid);
        serviceHealth.addClassName(Padding.LARGE);
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private Component createResponseTimes() {
        HorizontalLayout header = createHeader("Allocated Apartments", "Around the globe");

        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        chart.setThemeName("gradient");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("New York", apartmentService.getApartmentsByName("New York")));
        series.add(new DataSeriesItem("Berlin", apartmentService.getApartmentsByName("Berlin")));
        series.add(new DataSeriesItem("Japan", apartmentService.getApartmentsByName("Japan")));
        series.add(new DataSeriesItem("London", apartmentService.getApartmentsByName("London")));
        series.add(new DataSeriesItem("Venezia", apartmentService.getApartmentsByName("Venezia")));
        series.add(new DataSeriesItem("Chicago", apartmentService.getApartmentsByName("Chicago")));
        conf.addSeries(series);

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, chart);
        serviceHealth.addClassName(Padding.LARGE);
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames(FontSize.XLARGE, Margin.NONE);

        Span span = new Span(subtitle);
        span.addClassNames(TextColor.SECONDARY, FontSize.XSMALL);

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

}
