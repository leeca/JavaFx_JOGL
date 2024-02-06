package com.pnambic.jogldemo;

import com.pnambic.joglmodule.JoglModule;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JoglApp extends Application {

  private JoglModule jogl;

  @Override
  public void start(Stage stage) {
    Platform.setImplicitExit(true);
    final Group group = new Group();
    Scene scene = new Scene(group, 800, 600);
    stage.setScene(scene);
    stage.show();

    jogl = new JoglModule();
    group.getChildren().add(jogl.prepareCanvas());
    jogl.demoDisplay();
    jogl.start();
  }

  @Override
  public void stop() throws Exception {
    if (jogl != null) {
      jogl.stop();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
