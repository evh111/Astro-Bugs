1.) File -> Project Structure -> Project -

SDK must be the same as version specified in build/run configuration.

2.) File -> Project Structure -> Libraries -

Add JavaFx SDK as registered library.

3.) Add below VM options -

--module-path={path/to/javafx/lib}
--add-modules=javafx.controls,javafx.fxml,javafx.media 
--enable-native-access=javafx.graphics,javafx.media
