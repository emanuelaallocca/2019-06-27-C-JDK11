/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenze;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<LocalDate> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenze> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo...\n");
    	String categoria = this.boxCategoria.getValue();
    	LocalDate data = this.boxGiorno.getValue();
    	
    	this.model.creaGrafo(categoria, data);
    	txtResult.appendText("Numero Vertici  "+this.model.getNumVertici()+"\n");
    	txtResult.appendText("Numero Archi  "+this.model.getNumArchi()+"\n");
    	
    	for (Adiacenze a1: this.model.getArchi())
    	 	   txtResult.appendText("id1   "+a1.getId1()+"  id2  "+a1.getId2()+"  peso  "+a1.getPeso()+"\n");
    	this.txtResult.appendText("****************\n");
    	for (Adiacenze a : this.model.getArchiMediani())
    	   txtResult.appendText("id1 "+a.getId1()+"id2 "+a.getId2()+"peso "+a.getPeso()+"\n");
  
    	this.boxArco.getItems().addAll(this.model.getArchiMediani());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    	txtResult.appendText("Calcola percorso...\n");
    	Adiacenze a = this.boxArco.getValue();
    	for (String s : this.model.getCammino(a))
    		txtResult.appendText(s+"\n");
    	
//    	for (String s : this.model.getVicini(a))
//    		this.txtResult.appendText(s);
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGiorno.getItems().addAll(model.getDate());
    	this.boxCategoria.getItems().addAll(model.getCategorie());
    }
}
