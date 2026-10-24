# JavaFX ImageProgressIndicator

## Custom ProgressIndicator for JavaFX

* ProgressIndicator that uses custom images as animation
* usable just like the default ProgressIndicator

## Screenshots

### custom horizontal progress bar

![horizontal progress bar](screenshots/horizontalProgressBarExample.png)

### custom vertical progress bar

![vertical progress bar](screenshots/verticalProgressBarExample.png)

## Requirements

Build with a stable JDK 27 and the checked-in Gradle 9.8.2 wrapper. Set JAVA_HOME
to JDK 27 and select JDK 27 for the IDE's Gradle JVM. The daemon and compiler
use Java 27; the library targets Java 25, matching JavaFX 27's minimum runtime.
The OpenJFX plugin supplies platform-specific JavaFX dependencies and native libraries.

```sh
./gradlew clean build --no-daemon
```

Lombok 1.18.48 adds JDK 27 compiler support; FreeFair Lombok 9.8.2 supports the
Gradle 9.8 build. See https://projectlombok.org/changelog and
https://docs.gradle.org/current/userguide/compatibility.html . JavaFX requirements:
https://docs.oracle.com/en/java/java-components/javafx/27/release-notes/ .

The demo remains in `src/test/java/de/olaf_roeder/progressindicator/demo`.
Its entry point is `de.olaf_roeder.progressindicator.demo.application.DemoRunner`.
Enable native access for `javafx.graphics` when launching the demo on JDK 27.
There are no automated tests in this repository; `build` compiles the library and demo.

## Usage in FXML

See src/main/resources/demo/view/MainWindowView.fxml for a full example.

```
<?import de.olaf_roeder.progressindicator.ImageProgressIndicator?>
<ImageProgressIndicator imageUrl="@../progressindicator/LoadingBar.png"
                                    imageSize="1"
                                    orientation="HORIZONTAL"
                                    fx:id="determinateProgressIndicator"/>
```

### Attributes

<br>`imageUrl`: relative path to the image that should be used as progress indicator. Note the @-sign at the beginning
of the string. Any image supported by JavaFX can be used. Try an animated gif :)
<br>`imageSize`: Size to scale the image, default 1
<br>`orientation`: HORIZONTAL or VERTICAL: determines the orientation of the layer that covers determinate progress bars

## Usage in Java

See JavaDoc for a full description.
<br>See src/main/java/demo/view/MainWindowView.java for a full example.

```
String imageUrl = ImageProgressIndicator.class.getResource("loadingCircle.gif").toExternalForm();
ImageProgressIndicator imageProgressIndicator = new ImageProgressIndicator(imageUrl, 1, ORIENTATION.HORIZONTAL);
```

### Parameters

<br>`imageUrl`: URL to the image that should be used as progress indicator. Any image supported by JavaFX can be used.
Try an animated gif :)
<br>`imageSize`: Size to scale the image, default 1
<br>`orientation`: HORIZONTAL or VERTICAL: determines the orientation of the layer that covers determinate progress bars

### Extra Properties

There are three additional properties to the usual Node-properties:
<br>`StringProperty textProperty()`: The Label's text below the progress indicator
<br>`BooleanProperty progressPercentVisibleProperty()`: The percentage text centered on the image, false to hide text,
true to show text
<br>`BooleanProperty overlayVisibleProperty()`: The overlay that makes a bar appear growing from left to right or bottom
to top

## Styling

See src/main/resources/progressindicator/ImageProgressIndicator.css for an example.

Four styleclasses are added for styling:
<br>`image-progress-indicator`: Styleclass of the main component.
<br>`main-layer`: Styleclass of the main layer inside the progress indicator, contains the ImageView.
<br>`overlay`: Styleclass of the layer that covers the image during determinate progress. Set `-fx-opacity` to 0 to hide
the layer.
<br>`progress-percent`: Styleclass of the layer displaying the progress on top of the image.