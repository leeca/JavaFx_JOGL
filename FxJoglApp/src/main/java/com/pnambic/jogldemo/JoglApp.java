package com.pnambic.jogldemo;

import com.pnambic.joglmodule.JoglModule;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JoglApp extends Application {

  private JoglModule jogl;

  @Override
  public void start(Stage stage) {
    Platform.setImplicitExit(true);
    jogl = new JoglModule();

    Canvas canvas = jogl.prepareCanvas();
    canvas.setWidth(150);
    canvas.setHeight(150);

    Parent clientTest = buildTabs(canvas);
    Scene scene = new Scene(clientTest, 350, 200);
    stage.setScene(scene);
    stage.show();

    jogl.demoDisplay();
    jogl.start();
  }

  @Override
  public void stop() throws Exception {
    if (jogl != null) {
      jogl.stop();
    }
  }

  private Node buildVbox(Node middle) {
    VBox result = new VBox();
    result.getChildren().add(new Label("Top"));
    result.getChildren().add(middle);
    result.getChildren().add(new Label("Bottom"));
    return result;
  }

  private Parent buildTabs(Canvas canvas) {
    TabPane result = new TabPane();
    Tab welcomeTab = new Tab("Welcome", new Label("Welcome"));
    result.getTabs().add(welcomeTab);

    Tab vboxTab = new Tab("VBox w/o Canvas", buildVbox(new Label("Middle")));
    result.getTabs().add(vboxTab);

    Tab joglTab = new Tab("VBox with JOGL", buildVbox(canvas));
    result.getTabs().add(joglTab);
    return result ;
  }

  public static void main(String[] args) {
    launch();
  }
}
