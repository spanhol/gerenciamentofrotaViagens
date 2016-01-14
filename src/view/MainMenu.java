package view;

import view.telas.CadRegiao;
import view.telas.CadCaminhao;
import view.telas.CadViagem;
import view.telas.CadMotorista;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Fernando Spanhol
 */
public class MainMenu extends Application {

	//tela
	Stage stage;
	double largura;
	double altura;

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
		BuildCadastroViagem();

		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(bounds.getWidth());
		largura = bounds.getWidth();
		stage.setHeight(bounds.getHeight());
		altura = bounds.getHeight();
		stage.setTitle(tituloMainMenu);
		stage.setScene(cadastroViagem);
		stage.show();
	}

	public MenuBar BuildMenuBar() {
		//menubar
		final Menu menuCad = new Menu("Cadastro");
		final MenuItem menuCadViagens = new MenuItem("Viagens");
		final MenuItem menuCadMotorista = new MenuItem("Mototorista");
		final MenuItem menuCadCaminhao = new MenuItem("Caminhões");
		final MenuItem menuCadRegiao = new MenuItem("Regiões");

		MenuBar m = new MenuBar();
		menuCadViagens.setOnAction((ActionEvent event) -> {
			BuildCadastroViagem();
			stage.setTitle(tituloMainMenu);
		});
		menuCadMotorista.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroMotorista);
			BuildCadastroMotorista();
		});
		menuCadCaminhao.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroCaminhao);
			BuildCadastroCaminhao();
		});
		menuCadRegiao.setOnAction((ActionEvent event) -> {
			stage.setTitle(tituloCadastroRegiao);
			BuildCadastroRegiao();
		});
		menuCad.getItems().addAll(menuCadViagens, menuCadMotorista, menuCadCaminhao, menuCadRegiao);

		m.getMenus().addAll(menuCad);
		return m;
	}

	public void BuildCadastroViagem() {
		if (cadastroViagem == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			cadastroViagem = new CadViagem(root);
			stage.setScene(cadastroViagem);
		} else {
			stage.setScene(cadastroViagem);
		}
	}

	public void BuildCadastroRegiao() {
		if (cadastroRegiao == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			cadastroRegiao = new CadRegiao(root);
		}
		stage.setScene(cadastroRegiao);
	}

	public void BuildCadastroCaminhao() {
		if (cadastroCaminhao == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			cadastroCaminhao = new CadCaminhao(root);
		}
		stage.setScene(cadastroCaminhao);
	}

	public void BuildCadastroMotorista() {
		if (cadastroMotorista == null) {
			VBox root = new VBox();
			root.getChildren().addAll(BuildMenuBar());
			cadastroMotorista = new CadMotorista(root);
		}
		stage.setScene(cadastroMotorista);
	}

	public double getLargura() {
		return largura;
	}

	public double getAltura() {
		return altura;
	}
}
