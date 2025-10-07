package com.lmh.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lmh.manager.SalaManager;
import com.lmh.manager.SalaRuntime;
import com.lmh.utils.BeanFactory;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.vavr.control.Option;
import jakarta.annotation.security.PermitAll;

@Route(value = "jugar", layout = MainView.class)
@PageTitle("Jugar a Bingo")
@PermitAll
public class JugarView extends VerticalLayout {

	@Autowired
	private SalaManager salaManager;
	
	public JugarView() {
		Map<Integer, SalaRuntime> salasActivas = getSalaManager().getSalasActivas();
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidthFull();
		
		for (int i = 0; i < salasActivas.size(); i++) {
			if(i != 0 && i % 3 == 0) {
				add(hl);
				hl = new HorizontalLayout();
				hl.setWidthFull();
			}
			
			
		}
	}
	
	private SalaManager getSalaManager() {
		salaManager = Option.of(salaManager).getOrElse(BeanFactory.getBean(SalaManager.class));
		return salaManager;
	}
}
