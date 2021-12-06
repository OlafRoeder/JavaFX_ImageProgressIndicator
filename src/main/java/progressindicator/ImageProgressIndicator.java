package progressindicator;

import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ProgressIndicator;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * {@link ProgressIndicator} which uses an image to display progress.
 * Determinate progress will have the image faded in from left to right (horizontal parameter) or bottom to top (vertical parameter).
 * Indeterminate progress always shows the full image.
 * Image size can be scaled with imageSize parameter (width/height will be scaled proportional).
 */
public class ImageProgressIndicator extends ProgressIndicator {

    private static final String IMAGE_PROGRESS_INDICATOR_STYLE_CLASS = "image-progress-indicator";
    private static final String IMAGE_PROGRESS_INDICATOR_CSS = "ImageProgressIndicator.css";

    private final ImageProgressIndicatorSkin imageProgressIndicatorSkin;

    /**
     * {@link ImageProgressIndicator}
     *
     * @param imageUrl    in {@link URL} format, malformed url will throw IllegalArgumentException with usage-notice, Non-Null
     * @param imageSize   image scale, 1.0 for original size
     * @param orientation HORIZONTAL for a horizontal progress bar, VERTICAL for a vertical progress bar (also suggested for square images), Non-Null
     */
    public ImageProgressIndicator(@NamedArg("imageUrl") String imageUrl,
                                  @NamedArg(value = "imageSize", defaultValue = "1") double imageSize,
                                  @NamedArg(value = "orientation", defaultValue = "HORIZONTAL") ORIENTATION orientation) {

        Objects.requireNonNull(imageUrl);
        Objects.requireNonNull(orientation);

        imageProgressIndicatorSkin = new ImageProgressIndicatorSkin(this, getUrl(imageUrl), imageSize, orientation);

        setSkin(imageProgressIndicatorSkin);
        getStyleClass().add(IMAGE_PROGRESS_INDICATOR_STYLE_CLASS);

        URL resource = getClass().getResource(IMAGE_PROGRESS_INDICATOR_CSS);
        //in case someone purges the css
        if (resource != null)
            getStylesheets().add(resource.toExternalForm());
    }

    private URL getUrl(String imageUrl) {
        URL url;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(MessageFormat.format("Argument {0} is no valid URL, correct argument:\n" +
                    "from FXML: @relative-path-to-image-file\n (preferred)" +
                    "from Java: URL-to-file\n" +
                    "(see also constructor javadoc)", imageUrl));
        }
        return url;
    }

    /**
     * The Label's text below the progress indicator
     *
     * @return {@link StringProperty}
     */
    public StringProperty textProperty() {
        return imageProgressIndicatorSkin.textProperty();
    }

    /**
     * The percentage text centered on the image, false to hide text, true to show text
     *
     * @return {@link BooleanProperty}
     */
    public BooleanProperty progressPercentVisibleProperty() {
        return imageProgressIndicatorSkin.progressPercentVisibleProperty();
    }

    /**
     * The overlay that makes a bar appear growing from left to right or bottom to top
     *
     * @return {@link BooleanProperty}
     */
    public BooleanProperty overlayVisibleProperty() {
        return imageProgressIndicatorSkin.overlayVisibleProperty();
    }
}