package com.lmh.view;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;

@Push // habilita Vaadin Push globalmente
@PWA(name = "Bingo", shortName = "Bingo")
public class MyAppShell implements AppShellConfigurator {
    // clase vacía, solo sirve para configuración global
}
