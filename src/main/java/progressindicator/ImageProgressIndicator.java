package progressindicator;

import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ProgressIndicator;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class ImageProgressIndicator extends ProgressIndicator {

    private final ImageProgressIndicatorSkin imageProgressIndicatorSkin;

    public ImageProgressIndicator(@NamedArg("imageUrl") String imageUrl, @NamedArg(value = "size", defaultValue = "1") double size) {
        imageProgressIndicatorSkin = new ImageProgressIndicatorSkin(this, getUrl(imageUrl), size);
        setSkin(imageProgressIndicatorSkin);
        getStyleClass().add("image-progress-indicator");
        URL resource = getClass().getResource("ImageProgressIndicator.css");
        if (resource != null)
            getStylesheets().add(resource.toExternalForm());
    }

    private URL getUrl(String imageUrl) {
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(MessageFormat.format("Argument {0} is no valid URL, correct argument:\n" +
                    "from FXML: @relative-path-to-image-file\n (preferred)" +
                    "from Java: URL-to-file", imageUrl));
        }
        return url;
    }

    public BooleanProperty textVisibleProperty() {
        return imageProgressIndicatorSkin.textVisibleProperty();
    }

    public StringProperty textProperty() {
        return imageProgressIndicatorSkin.textProperty();
    }

    public BooleanProperty progressPercentVisibleProperty() {
        return imageProgressIndicatorSkin.progressPercentVisibleProperty();
    }
}
