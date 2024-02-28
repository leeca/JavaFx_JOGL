package com.pnambic.jogldemo;

import com.pnambic.joglmodule.JoglModule;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JoglApp extends Application {

  private JoglModule jogl;

  @Override
  public void start(Stage stage) {
    Platform.setImplicitExit(true);
    jogl = new JoglModule();
    jogl.initModule();

    TabPane clientTest = buildTabs();
    Scene scene = new Scene(clientTest, 300, 250);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    if (jogl != null) {
      jogl.stop();
    }
  }

  private TabPane buildTabs() {
    TabPane result = new TabPane();
    Tab welcomeTab = new Tab("Welcome", new Label("Welcome"));
    result.getTabs().add(welcomeTab);

    VBox simpleVBox = new VBox();
    fillVbox(simpleVBox, new Label("Middle"));
    Tab vboxTab = new Tab("VBox w/o Canvas", simpleVBox);
    result.getTabs().add(vboxTab);

    VBox joglVBox = new VBox();
    // Outside of Tab pane
    // Canvas canvas = jogl.prepareCanvas();
    // fillVbox(simpleVBox, canvas);

    Tab joglTab = new Tab("VBox with JOGL", joglVBox);
    addHandlers(joglTab, joglVBox, () -> jogl.prepareCanvas());
    result.getTabs().add(joglTab);

    Tab sceneTab = new Tab("Scene Wrapper", joglVBox);
    addHandlers(joglTab, joglVBox,
        () -> new SubScene(new StackPane(jogl.prepareCanvas()), 160.0d, 160.0d));
    result.getTabs().add(sceneTab);
    return result ;
  }

  private void fillVbox(VBox dst, Node middle) {
    dst.getChildren().add(new Label("Top"));
    dst.getChildren().add(middle);
    dst.getChildren().add(new Label("Bottom"));
  }

  private void addHandlers(Tab joglTab, VBox vBox, Supplier<Node> middleSrc) {

    joglTab.setOnSelectionChanged(new EventHandler<Event>() {

      @Override
      public void handle(Event event) {
        if (joglTab.isSelected()) {
          startJogl(vBox, middleSrc);
        } else {
          stopJogl(vBox);
        }
      }
    });

    joglTab.setOnClosed(new EventHandler<Event>() {

      @Override
      public void handle(Event event) {
        stopJogl(vBox);
      }
    });
  }

  private void startJogl(VBox joglVBox, Supplier<Node> middleSrc) {
    // Inside of Tab pane
    // Canvas canvas = jogl.prepareCanvas();
    fillVbox(joglVBox, middleSrc.get());

    jogl.demoDisplay();
    jogl.start();
  }

  private void stopJogl(VBox joglVBox) {
    try {
      jogl.stop();
      joglVBox.getChildren().clear();
    } catch (Exception err) {
      System.err.println("Unexpected JOGL shutdown error: " + err.getMessage());
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
