package application;

import controller.MainWindowView;
import controller.MainWindowViewModel;
import javafx.util.Callback;

class FXMLControllerFactory implements Callback<Class<?>, Object> {

    private final Application application;

    FXMLControllerFactory(Application application) {
        /*package restricted constructor*/
        this.application = application;
    }

    @Override
    public Object call(Class<?> type) {

        if (type.isAssignableFrom(MainWindowView.class))
            return new MainWindowView(application, new MainWindowViewModel(application));

        throw new IllegalStateException("No controller class found for " + type);
    }
}