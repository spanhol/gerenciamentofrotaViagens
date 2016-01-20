package view;

import view.telas.CadRegiao;
import view.telas.CadCaminhao;
import view.telas.CadViagem;
import view.telas.CadMotorista;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Fernando Spanhol
 */
public class MainMenu extends Application {

	//tela
	Stage stage;

	CadViagem cadastroViagem;
	static final String tituloMainMenu = "SMMV";	//sistema movelmar de manutencao de viagens
	Scene cadastroRegiao;
	static final String tituloCadastroRegiao = "SMMV - Cadastro de regiões";	//sistema movelmar de manutencao de viagens
	Scene cadastroCaminhao;
	static final String tituloCadastroCaminhao = "SMMV - Cadastro de caminhões";	//sistema movelmar de manutencao de viagens
	Scene cadastroMotorista;
	static final String tituloCadastroMotorista = "SMMV - Cadastro de motoristas";	//sistema movelmar de manutencao de viagens

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		duildCadastroViagem();

		stage.setTitle(tituloMainMenu);
		stage.setScene(cadastroViagem);
		stage.setWidth(1075);
		stage.setHeight(650);
		stage.setMaximized(true);
		stage.show();
	}

	public MenuBar buildMenuBar() {
		//menubar
		final Menu menuCad = new Menu("Cadastro");
		final MenuItem menuCadViagens = new MenuItem("Viagens");
		final MenuItem menuCadMotorista = new MenuItem("Mototorista");
		final MenuItem menuCadCaminhao = new MenuItem("Caminhões");
		final MenuItem menuCadRegiao = new MenuItem("Regiões");

		MenuBar m = new MenuBar();
		menuCadViagens.setOnAction((ActionEvent event) -> {
			duildCadastroViagem();
			stage.setTitle(tituloMainMenu);
		});
		menuCadMotorista.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroMotorista);
			buildCadastroMotorista();
		});
		menuCadCaminhao.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroCaminhao);
			buildCadastroCaminhao();
		});
		menuCadRegiao.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroRegiao);
			buildCadastroRegiao();
		});
		menuCad.getItems().addAll(menuCadViagens, menuCadMotorista, menuCadCaminhao, menuCadRegiao);

		m.getMenus().addAll(menuCad);
		return m;
	}

	public void duildCadastroViagem() {
		if (cadastroViagem == null) {
			VBox root = new VBox();
			root.getChildren().addAll(buildMenuBar());
			cadastroViagem = new CadViagem(root);
			stage.setScene(cadastroViagem);
		} else {
			stage.setScene(cadastroViagem);
		}
	}

	public void buildCadastroRegiao() {
		if (cadastroRegiao == null) {
			VBox root = new VBox();
			root.getChildren().addAll(buildMenuBar());
			cadastroRegiao = new CadRegiao(root);
		}
		stage.setScene(cadastroRegiao);
	}

	public void buildCadastroCaminhao() {
		if (cadastroCaminhao == null) {
			VBox root = new VBox();
			root.getChildren().addAll(buildMenuBar());
			cadastroCaminhao = new CadCaminhao(root);
		}
		stage.setScene(cadastroCaminhao);
	}

	public void buildCadastroMotorista() {
		if (cadastroMotorista == null) {
			VBox root = new VBox();
			root.getChildren().addAll(buildMenuBar());
			cadastroMotorista = new CadMotorista(root);
		}
		stage.setScene(cadastroMotorista);
	}
}
