package progressindicator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;

/**
 * This is a {@link javafx.scene.control.Skin} for an {@link ImageProgressIndicator}. This class is not intended for public use,
 * refer to {@link ImageProgressIndicator} instead.
 */
class ImageProgressIndicatorSkin extends SkinBase<ImageProgressIndicator> {

    private final ImageProgressIndicator progressIndicator;

    private final VBox mainLayer = new VBox();

    private final Label textLabel = new Label();
    private final Label progressPercent = new Label();

    private final VBox overlay = new VBox();
    private final BooleanProperty overlayVisible = new SimpleBooleanProperty(true);

    private final ImageView imageView;
    private final ORIENTATION orientation;

    /**
     * @param progressIndicator the {@link ImageProgressIndicator} to skin
     * @param imageUrl          URL to image, Non-Null
     * @param imageSize         image scale, 1.0 for original size
     * @param orientation       HORIZONTAL for a horizontal progress bar, VERTICAL for a vertical progress bar (also suggested for square images), Non-Null
     */
    ImageProgressIndicatorSkin(ImageProgressIndicator progressIndicator, URL imageUrl, double imageSize, ORIENTATION orientation) {
        super(progressIndicator);

        Objects.requireNonNull(progressIndicator);
        Objects.requireNonNull(imageUrl);
        Objects.requireNonNull(orientation);

        this.progressIndicator = progressIndicator;
        this.orientation = orientation;

        this.imageView = new ImageView(loadScaledImage(imageUrl, imageSize));

        initialize();
    }

    private Image loadScaledImage(URL imageUrl, double imageSize) {

        Image image = new Image(imageUrl.toExternalForm(), false);
        image = new Image(image.getUrl(), image.getWidth() * imageSize, image.getHeight() * imageSize, true, true, true);

        return image;
    }

    private void initialize() {

        StackPane stackPane = new StackPane();

        stackPane.getChildren().addAll(imageView, overlay, progressPercent);

        mainLayer.getChildren().addAll(stackPane, textLabel);

        getChildren().add(mainLayer);

        mainLayer.getStyleClass().add("main-layer");
        overlay.getStyleClass().add("overlay");
        progressPercent.getStyleClass().add("progress-percent");

        textLabel.visibleProperty().bind(textLabel.textProperty().isNotEmpty());
        textLabel.managedProperty().bind(textLabel.visibleProperty());

        imageView.managedProperty().bind(progressIndicator.visibleProperty());

        overlay.visibleProperty().bind(progressIndicator.indeterminateProperty().not().and(overlayVisible));
        overlay.managedProperty().bind(progressIndicator.visibleProperty());
        SimpleDoubleProperty one = new SimpleDoubleProperty(1);
        switch (orientation) {
            case VERTICAL:
                StackPane.setAlignment(overlay, Pos.TOP_CENTER);
                overlay.maxHeightProperty().bind(mainLayer.heightProperty().multiply(one.subtract(progressIndicator.progressProperty())));
                break;
            case HORIZONTAL:
                StackPane.setAlignment(overlay, Pos.CENTER_RIGHT);
                overlay.maxWidthProperty().bind(mainLayer.widthProperty().multiply(one.subtract(progressIndicator.progressProperty())));
                break;
            default:
                throw new EnumConstantNotPresentException(ORIENTATION.class, orientation.name());
        }

        progressIndicator.maxWidthProperty().bind(Bindings.max(imageView.getImage().widthProperty(), textLabel.widthProperty()));
        progressIndicator.maxHeightProperty().bind(imageView.getImage().heightProperty());

        progressPercent.textProperty().bind(Bindings.createStringBinding(() -> progressIndicator.progressProperty().multiply(100).intValue() + "%", progressIndicator.progressProperty()));
        progressPercent.managedProperty().bind(progressPercent.visibleProperty());
    }

    /**
     * The Label's text below the progress indicator
     *
     * @return {@link StringProperty}
     */
    StringProperty textProperty() {
        return textLabel.textProperty();
    }

    /**
     * The percentage text centered on the image, false to hide text, true to show text
     *
     * @return {@link BooleanProperty}
     */
    BooleanProperty progressPercentVisibleProperty() {
        return progressPercent.visibleProperty();
    }

    /**
     * The overlay that makes a bar appear growing from left to right or bottom to top
     *
     * @return {@link BooleanProperty}
     */
    BooleanProperty overlayVisibleProperty() {
        return overlayVisible;
    }
}