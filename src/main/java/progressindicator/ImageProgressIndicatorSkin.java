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

public class ImageProgressIndicatorSkin extends SkinBase<ImageProgressIndicator> {

    private final ImageProgressIndicator progressIndicator;

    private final VBox mainLayer = new VBox();
    private final Label textLabel = new Label();
    private final Label progressPercent = new Label();
    private final VBox overlay = new VBox();
    private final ImageView imageView;
    private final BooleanProperty overlayVisible = new SimpleBooleanProperty(true);
    private final ORIENTATION orientation;

    ImageProgressIndicatorSkin(ImageProgressIndicator progressIndicator, URL imageUrl, double imageSize, ORIENTATION orientation) {
        super(progressIndicator);
        this.orientation = orientation;

        Objects.requireNonNull(progressIndicator);
        Objects.requireNonNull(imageUrl);

        this.progressIndicator = progressIndicator;
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

    BooleanProperty textVisibleProperty() {
        return textLabel.visibleProperty();
    }

    StringProperty textProperty() {
        return textLabel.textProperty();
    }

    BooleanProperty progressPercentVisibleProperty() {
        return progressPercent.visibleProperty();
    }

    BooleanProperty overlayVisibleProperty() {
        return overlayVisible;
    }

    public enum ORIENTATION {
        HORIZONTAL,
        VERTICAL;
    }
}