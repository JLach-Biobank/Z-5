package com.biohack.z5.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringUI
public class MainUI extends UI {

	private VerticalLayout mainLayout;
	private TextArea inputArea;

	@Override
	protected void init(VaadinRequest request) {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		inputArea = new TextArea("Input");
		inputArea.setWidth(70, Unit.PERCENTAGE);
		mainLayout.addComponent(inputArea);
		setContent(mainLayout);
	}

}
