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
import javafx.util.Pair;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a {@link javafx.scene.control.Skin} for an {@link ImageProgressIndicator}. This class is not intended for public use,
 * refer to {@link ImageProgressIndicator} instead.
 */
class ImageProgressIndicatorSkin extends SkinBase<ImageProgressIndicator> {

    private static final String MAIN_LAYER_STYLE_CLASS = "main-layer";
    private static final String OVERLAY_STYLE_CLASS = "overlay";
    private static final String PROGRESS_PERCENT_STYLE_CLASS = "progress-percent";
    
    private static final ImageCache IMAGE_CACHE = new ImageCache();

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
        return IMAGE_CACHE.loadCachedImage(imageUrl, imageSize);
    }

    private void initialize() {

        initLayout();
        initStyle();
        initVisibility();
        initManageability();
        initOverlay();
        initProgressIndicator();
        initProgressPercent();
    }

    private void initLayout() {

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, overlay, progressPercent);

        mainLayer.getChildren().addAll(stackPane, textLabel);

        getChildren().add(mainLayer);
    }

    private void initStyle() {
        mainLayer.getStyleClass().add(MAIN_LAYER_STYLE_CLASS);
        overlay.getStyleClass().add(OVERLAY_STYLE_CLASS);
        progressPercent.getStyleClass().add(PROGRESS_PERCENT_STYLE_CLASS);
    }

    private void initVisibility() {
        overlay.visibleProperty().bind(progressIndicator.indeterminateProperty().not().and(overlayVisible));
        textLabel.visibleProperty().bind(textLabel.textProperty().isNotEmpty());
    }

    private void initManageability() {
        overlay.managedProperty().bind(progressIndicator.visibleProperty());
        imageView.managedProperty().bind(progressIndicator.visibleProperty());
        textLabel.managedProperty().bind(textLabel.visibleProperty());
        progressPercent.managedProperty().bind(progressPercent.visibleProperty());
    }

    private void initOverlay() {

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
    }

    private void initProgressIndicator() {
        progressIndicator.maxWidthProperty().bind(Bindings.max(imageView.getImage().widthProperty(), textLabel.widthProperty()));
        progressIndicator.maxHeightProperty().bind(imageView.getImage().heightProperty());
    }

    private void initProgressPercent() {
        progressPercent.textProperty().bind(Bindings.createStringBinding(() -> progressIndicator.progressProperty().multiply(100).intValue() + "%", progressIndicator.progressProperty()));
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

    /**
     * To avoid overconsumption of memory by loading the same image more than once, this simple cache holds the last {@link ImageCache#MAX_CACHE_SIZE} images that were loaded before.
     */
    private static class ImageCache {

        private final static int MAX_CACHE_SIZE = 5;

        private final Map<Pair<URL, Double>, Image> imageCache = new ConcurrentHashMap<>(MAX_CACHE_SIZE);

        private Image loadCachedImage(URL imageUrl, double imageSize) {

            Pair<URL, Double> key = new Pair<>(imageUrl, imageSize);
            Image cachedImage = imageCache.get(key);
            if (cachedImage != null)
                return cachedImage;

            /*as this is a very simple cache and in most usecases there should be only a couple of images, invalidate cache*/
            if (imageCache.size() >= MAX_CACHE_SIZE)
                imageCache.clear();

            Image image = new Image(imageUrl.toExternalForm(), false);
            image = new Image(image.getUrl(), image.getWidth() * imageSize, image.getHeight() * imageSize, true, true, true);
            imageCache.put(key, image);

            return image;
        }
    }
}