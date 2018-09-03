/**
 * Copyright (c) 2016, 2018 Gluon Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse
 *    or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.devoxx.views;

import com.devoxx.DevoxxApplication;
import com.devoxx.model.Conference;
import com.devoxx.service.Service;
import com.devoxx.util.DevoxxSettings;
import com.devoxx.views.cell.ConferenceCell;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.SettingsService;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.StatusBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import com.gluonhq.charm.glisten.layout.layer.MenuPopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.gluonhq.charm.glisten.layout.layer.PopupView.PopupSide.RIGHT;

public class ConfSelectorPresenter extends GluonPresenter<DevoxxApplication> {

    private static final StatusBar STATUS_BAR = MobileApplication.getInstance().getStatusBar();
    private static final PseudoClass PSEUDO_CLASS_STATUS_VOXXED = PseudoClass.getPseudoClass("voxxed");

    @FXML
    private ResourceBundle bundle = ResourceBundle.getBundle("com/devoxx/views/confselector");
    
    @FXML
    private CharmListView<Conference, LocalDateTime> selector;
    
    @FXML
    private View confSelectorView;

    @FXML
    private StackPane filterPane;

    @Inject
    private Service service;

    public void initialize() {
        final Optional<SettingsService> settingsService = Services.get(SettingsService.class);
        if (settingsService.isPresent()) {
            final SettingsService settings = settingsService.get();
            final String eventType = settings.retrieve(DevoxxSettings.SAVED_CONFERENCE_TYPE);
            if (eventType == null || eventType.equals("")) {
                selector.setItems(service.retrieveConferences(Conference.Type.DEVOXX));
            } else {
                selector.setItems(service.retrieveConferences(Conference.Type.valueOf(eventType)));
            }
        } else {
            selector.setItems(service.retrieveConferences(Conference.Type.DEVOXX));
        }

        selector.setPlaceholder(new ProgressIndicator());
        selector.setCellFactory(p -> new ConferenceCell(service));
        selector.setComparator((s1, s2) -> LocalDate.parse(s1.getFromDate()).compareTo(LocalDate.parse(s2.getFromDate())));

        confSelectorView.setOnShowing(event -> {
            getApp().getAppBar().setVisible(false);
        });

        final Button filter = MaterialDesignIcon.FILTER_LIST.button();
        MenuPopupView popup = getMenuPopupView(filter);
        
        filter.setOnAction(e -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                popup.show();
            }
            e.consume();
        });

        filterPane.getChildren().add(filter);
        
    }

    private MenuPopupView getMenuPopupView(Button filter) {
        Menu menu = new Menu();
        MenuItem devoxx = new MenuItem(Conference.Type.DEVOXX.name());
        MenuItem voxxed = new MenuItem(Conference.Type.VOXXED.name());
        menu.getItems().addAll(devoxx, voxxed);
        
        devoxx.setOnAction(e -> {
            selector.setItems(service.retrieveConferences(Conference.Type.DEVOXX));
            STATUS_BAR.pseudoClassStateChanged(PSEUDO_CLASS_STATUS_VOXXED, false);
        });
        voxxed.setOnAction(e -> {
            selector.setItems(service.retrieveConferences(Conference.Type.VOXXED));
            STATUS_BAR.pseudoClassStateChanged(PSEUDO_CLASS_STATUS_VOXXED, true);
        });

        final MenuPopupView menuPopupView = new MenuPopupView(filter, menu);
        menuPopupView.setSide(RIGHT);
        return menuPopupView;
    }
}
