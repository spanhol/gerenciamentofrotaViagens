package view;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModalDialog {

	Button ok;
	Button sim;
	Button nao;
	Button cancelar;
	Resposta resposta;

	public enum Resposta {

		cancelar,
		ok,
		sim,
		nao;
	}

	public enum Tipo {

		mensagem,
		simnao,
		simnaocancelar;
	}

	public ModalDialog(final Stage stg, Tipo t, String mensagem) {
		resposta = Resposta.cancelar;
		ok = new Button("OK");
		ok.setPrefWidth(80);
		sim = new Button("Sim");
		sim.setPrefWidth(80);
		nao = new Button("Não");
		nao.setPrefWidth(80);
		cancelar = new Button("Cancelar");
		cancelar.setPrefWidth(80);

		Image warning = new Image(getClass().getResourceAsStream("warning.png"));
		ImageView warningView = new ImageView(warning);
		warningView.setPreserveRatio(true);
//		warningView.setFitHeight(32);

		Label mens = new Label(mensagem, warningView);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setOnShowing((WindowEvent event) -> {
			resposta = Resposta.cancelar;
		});

		VBox root = new VBox();
		HBox m = new HBox();
		m.setPadding(new Insets(20));
		m.setAlignment(Pos.BASELINE_CENTER);
		HBox botao = new HBox();
		botao.setSpacing(10);
		botao.setPadding(new Insets(20));
		botao.setAlignment(Pos.BASELINE_CENTER);

		m.getChildren().add(mens);
		m.setPrefWidth(350);

		root.getChildren().addAll(m, botao);
		Scene scene = new Scene(root);

		ok.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				resposta = Resposta.ok;
				stage.hide();
			}
		});
		sim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				resposta = Resposta.sim;
				stage.hide();
			}
		});
		nao.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				resposta = Resposta.nao;
				stage.hide();
			}
		});
		cancelar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				resposta = Resposta.cancelar;
				stage.hide();
			}
		});
		if (t == Tipo.mensagem) {
			botao.getChildren().addAll(ok);
		} else if (t == Tipo.simnao) {
			botao.getChildren().addAll(sim, nao);
		} else if (t == Tipo.simnaocancelar) {
			botao.getChildren().addAll(sim, nao, cancelar);
		}
		stage.setScene(scene);
		stage.showAndWait();
	}

	public Resposta getResposta() {
		return resposta;
	}
}
