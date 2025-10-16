package com.lmh.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lmh.service.impl.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import jakarta.annotation.security.PermitAll;

@Route("")
@PageTitle("Panel de Control")
@PermitAll
public class MainView extends AppLayout implements BeforeEnterObserver {

	private static final long serialVersionUID = -1913329759275013100L;

	private List<Class<?>> classes = new ArrayList<>();

	private DrawerToggle drawerToggle = new DrawerToggle();
	
	private SecurityService securityService;

	public MainView(@Autowired SecurityService securityService) throws ClassNotFoundException {
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}
	
	private void createHeader() {
		H1 title = new H1("Administración BHernández");

		drawerToggle.setIcon(VaadinIcon.MENU.create());

		HorizontalLayout header = new HorizontalLayout(
				drawerToggle,
				title
				);

		header.setWidth("100%");
		header.setHeight("10%");

		Button logout = new Button(new Icon(VaadinIcon.EXIT)); logout.addClickListener(event1 -> {
			securityService.logout();
		});

		logout.getStyle().set("margin-right", "20px");
		logout.setWidth("20px");
		
		addToNavbar(header); 
		addToNavbar(logout);
	}  

	private void createDrawer() throws ClassNotFoundException {
		VerticalLayout vl = new VerticalLayout();

		RouterLink route = new RouterLink("SALAS", SalaView.class);
		route.setHighlightCondition(HighlightConditions.sameLocation()); 
		route.setClassName("routerlink-style");
		HorizontalLayout hl = new HorizontalLayout(VaadinIcon.COGS.create(), route);
		vl.add(hl);
		addToDrawer(vl);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		setDrawerOpened(false);
	}
}
