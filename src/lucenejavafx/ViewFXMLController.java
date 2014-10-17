
package lucenejavafx;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

import static lucenejavafx.LuceneIndex.LuceneIndex;
import org.apache.lucene.index.IndexableField;
import static lucenejavafx.SearchIndex.retrieve;

/**
 * FXML Controller class
 *
 * @author kostas
 */
public class ViewFXMLController implements Initializable {

    @FXML
    private TextField textF;
    @FXML
    private Button search_b, reset_b, create_b;
    @FXML
    private ListView list, fileListView;
    @FXML
    private Label info;

    @FXML
    protected void create_index(ActionEvent event) throws CorruptIndexException, LockObtainFailedException, IOException, Exception {

        boolean a = LuceneIndex();
        if (a == true) {
            info.setText("Index Creared!");
            info.setVisible(true);
        }

    }

    @FXML
    protected void start_search(ActionEvent event) throws CorruptIndexException, LockObtainFailedException, IOException, Exception {

        String query = textF.getText();

        if (query.isEmpty()) {

            info.setText("Add a word!!!");
        } else {

            SearchIndex sr = new SearchIndex();
            String[] answers = sr.Searcher(query);

            ObservableList item = FXCollections.observableArrayList(answers);
            list.setItems(item);

            
            final int point = list.getSelectionModel().getSelectedIndex();

            list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    try {
                        retrieve(point);
                    } catch (IOException e) {
                    }

                }
            });

        }
    }

    @FXML
    protected void retrieve_file(MouseEvent e) throws FileNotFoundException, IOException {
        int point = -1;
        point = list.getSelectionModel().getSelectedIndex();

        System.out.println(point);

        info.setText(" " + point);

        if (point != -1) {

            fileListView.setDisable(false);

            SearchIndex sr = new SearchIndex();

            List<IndexableField> opened_fields = new ArrayList<IndexableField>();

            opened_fields = sr.retrieve(point);
            ObservableList olist = FXCollections.observableArrayList(opened_fields);
            fileListView.setItems(olist);

        }
    }

    @FXML
    protected void reset(ActionEvent event) {
        textF.setText("");
        info.setVisible(false);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
