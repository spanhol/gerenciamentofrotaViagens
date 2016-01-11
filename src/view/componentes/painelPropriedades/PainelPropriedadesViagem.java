package view.componentes.painelPropriedades;

import dao.CaminhaoDao;
import dao.ContasMatDao;
import dao.MotoristaDao;
import dao.RegiaoDao;
import dao.ViagemDao;
import entidades.Caminhao;
import entidades.ContasMat;
import entidades.Motorista;
import entidades.Regiao;
import wrapper.RegiaoWrapper;
import entidades.Viagem;
import wrapper.CaminhaoWrapper;
import wrapper.MotoristaWrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.CustomDate;
import relatorio.RelatorioTabela;
import relatorio.RelatorioViagem;
import view.ModalDialog;
import wrapper.ViagemWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class PainelPropriedadesViagem extends PainelPropriedades<ViagemDao, ViagemWrapper> {

	TextField tfId;
	TextField tfControle;
	TextField tfDatacarregamento;
	TextField tfCarga;
	TextField tfAdiantamento;
	TextField tfChapa;
	TextField tfFrete;
	TextField tfComissao;
	TextArea taObs;
	ComboBox<CaminhaoWrapper> cCaminhao;
	ObservableList<CaminhaoWrapper> caminhaoW;
	ComboBox<MotoristaWrapper> cMotorista;
	ObservableList<MotoristaWrapper> motoristaW;
	ObservableList<RegiaoWrapper> listaRegiao;
	ObservableList<RegiaoWrapper> listaFiltroRegiao;
	ListView<RegiaoWrapper> regiao;

	Button relatorioViagem;

	HBox botoesFiltro;
	Button relatorioTabela;

	ListView<RegiaoWrapper> fregiao;
	TextField ftfDatacarregamento;
	ComboBox<MotoristaWrapper> fcMotorista;
	ObservableList<MotoristaWrapper> motoristaFiltro;
	ComboBox<CaminhaoWrapper> fcCaminhao;
	ObservableList<CaminhaoWrapper> caminhaoFiltro;

	boolean estadoNovo;
	String ultimoValorTfFrete = "";

	TabPane tabs;

	public PainelPropriedadesViagem(ViagemDao dao) {
		super(dao);
		setMinWidth(250);
		setPadding(new Insets(0, 0, 0, 0));

		createTabPropriedades();
		createTabFiltro();

		tabs = new TabPane();
		Tab propriedades = new Tab("Propriedades");
		propriedades.setClosable(false);
		Tab tabFiltro = new Tab("Filtro");
		tabFiltro.setClosable(false);

		VBox f = new VBox();
		f.setPadding(new Insets(5, 10, 0, 10));
		f.setAlignment(Pos.TOP_LEFT);
		f.setSpacing(2);
		VBox d = new VBox();
		d.setPadding(new Insets(5, 10, 0, 10));
		d.setAlignment(Pos.TOP_LEFT);
		HBox colunas = new HBox();
		VBox esquerda = new VBox();
		esquerda.setSpacing(2);

		//propriedades
		HBox id = box("Id", tfId);
		HBox numcontrole = box("Controle", tfControle);
		HBox datacarregamento = box("Data Carreg.", tfDatacarregamento);
		HBox numcarga = box("Carga", tfCarga);
		HBox adiantamento = box("Adt", tfAdiantamento);
		HBox adiantamentochapa = box("Chapa", tfChapa);
		VBox obs = NovoTexto("Observações");
		obs.setPadding(new Insets(10, 0, 0, 0));
		HBox motorista = box("Motorista", cMotorista);
		HBox caminhao = box("Caminhão", cCaminhao);
		HBox frete = box("Frete", tfFrete);
		HBox comissao = box("Comissao", tfComissao);
		id.setVisible(false);

		//filtro
		HBox fdatacarregamento = box("Data Carreg.", ftfDatacarregamento);
		HBox fmotorista = box("Motorista", fcMotorista);
		HBox fcaminhao = box("Caminhão", fcCaminhao);

		esquerda.getChildren().addAll(motorista, caminhao, numcontrole, numcarga, datacarregamento, frete, comissao, adiantamento, adiantamentochapa, id);
		colunas.getChildren().addAll(esquerda);
		propriedades.setContent(d);
		tabFiltro.setContent(f);
		tabs.getTabs().addAll(propriedades, tabFiltro);
		d.getChildren().addAll(colunas, regiao, obs, botoes);
		f.getChildren().addAll(fdatacarregamento, fmotorista, fcaminhao, fregiao, botoesFiltro);
		getChildren().addAll(tabs);
	}

	final public VBox NovoTexto(String texto) {
		VBox re = new VBox();
		Label lre = new Label(texto);
		re.getChildren().addAll(lre, taObs);
		return re;
	}

	@Override
	public void setPropriedades(ViagemWrapper e) {
		limpaErro();
		setEstadoBotoesNovo();
		if (e == null) {
			limpaTexto();
			limpaOutros();
		} else {
			selecionado = e;
			tfId.setText(String.valueOf(selecionado.getId()));
			tfControle.setText(selecionado.getNumcontrole().toString());
			tfDatacarregamento.setText(sdf.format(selecionado.getDatacarregamento()));
			tfCarga.setText(selecionado.getNumcarga().toString());
			tfAdiantamento.setText(String.format("%,3.2f", selecionado.getAdiantamento()));
			tfChapa.setText(String.format("%,3.2f", selecionado.getAdiantamentochapa()));
			taObs.setText(selecionado.getObs());
			tfFrete.setText(String.format("%,3.2f", selecionado.getFrete()));
			tfComissao.setText(String.format("%,3.2f", selecionado.getComissao()));

			if (selecionado.getIdmotorista() != null) {
				for (MotoristaWrapper mw : motoristaW) {
					if (mw.get().getId().compareTo(selecionado.getIdmotorista().getId()) == 0) {
						cMotorista.getSelectionModel().select(mw);
						break;
					}
				}
			}

			if (selecionado.getIdcaminhao() != null) {
				for (CaminhaoWrapper cw : caminhaoW) {
					if (cw.get().getId().compareTo(selecionado.getIdcaminhao().getId()) == 0) {
						cCaminhao.getSelectionModel().select(cw);
						break;
					}
				}
			}

			for (RegiaoWrapper rs : listaRegiao) {
				rs.selectedProperty().set(false);
				for (Regiao rc : selecionado.getRegiaoCollection()) {
					if (rs.get().getId().compareTo(rc.getId()) == 0) {
						rs.selectedProperty().set(true);
					}
				}
			}
			setEstadoBotoesEditar();
			auto();
		}
	}

	@Override
	public ViagemWrapper valida(boolean existente) {
		limpaErro();
		boolean erro = false;
		if (existente) {
			if (tfId.getText().equals("")) {
				erro = true;
			}
			Viagem temp = dao.getById(Integer.valueOf(tfId.getText()));
			if (temp == null) {
				erro = true;
			}
		} else {
			selecionado = new ViagemWrapper();
		}
		boolean rsTrue = false;
		for (RegiaoWrapper rs : listaRegiao) {
			if (rs.getSelected()) {
				rsTrue = true;
				break;
			}
		}
		if (!rsTrue) {
			regiao.getStyleClass().add("erro");
			erro = true;
		}
		if (cCaminhao.getValue() == null) {
			cCaminhao.getStyleClass().add("erro");
			erro = true;
		}
		if (cMotorista.getValue() == null) {
			cMotorista.getStyleClass().add("erro");
			erro = true;
		}
		if (tfAdiantamento.getText().equals("")) {
			tfAdiantamento.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setAdiantamento(Double.valueOf(tfAdiantamento.getText().replace(".", "").replace(",", ".")));
			} catch (NumberFormatException ex) {
				tfAdiantamento.getStyleClass().add("erro");
				erro = true;
			}
		}

		if (tfCarga.getText().equals("")) {
			tfCarga.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setNumcarga(Integer.valueOf(tfCarga.getText()));
			} catch (NumberFormatException ex) {
				tfCarga.getStyleClass().add("erro");
				erro = true;
			}
		}
		if (tfChapa.getText().equals("")) {
			tfChapa.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setAdiantamentochapa(Double.valueOf(tfChapa.getText().replace(".", "").replace(",", ".")));
			} catch (NumberFormatException ex) {
				tfChapa.getStyleClass().add("erro");
				erro = true;
			}
		}
		if (tfControle.getText().equals("")) {
			tfControle.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setNumcontrole(Integer.valueOf(tfControle.getText()));
			} catch (NumberFormatException ex) {
				tfControle.getStyleClass().add("erro");
				erro = true;
			}
		}
		if (tfDatacarregamento.getText().equals("")) {
			tfDatacarregamento.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setDatacarregamento(new CustomDate(sdf.parse(tfDatacarregamento.getText()).getTime()));
			} catch (ParseException ex) {
				tfDatacarregamento.getStyleClass().add("erro");
				erro = true;
			}
		}
		if (tfFrete.getText().equals("")) {
			tfFrete.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setFrete(Double.valueOf(tfFrete.getText().replace(".", "").replace(",", ".")));
			} catch (NumberFormatException ex) {
				tfFrete.getStyleClass().add("erro");
				erro = true;
			}
		}
		if (tfComissao.getText().equals("")) {
			tfComissao.getStyleClass().add("erro");
			erro = true;
		} else {
			try {
				selecionado.setComissao(Double.valueOf(tfComissao.getText().replace(".", "").replace(",", ".")));
			} catch (NumberFormatException ex) {
				tfComissao.getStyleClass().add("erro");
				erro = true;
			}
		}

		selecionado.setIdcaminhao(cCaminhao.getValue().get());
		selecionado.setIdmotorista(cMotorista.getValue().get());
		selecionado.setObs(taObs.getText());
		selecionado.setRegiaoCollection(FXCollections.observableArrayList());
		listaRegiao.stream().filter((rs) -> (rs.getSelected())).forEach((rs) -> {
			selecionado.getRegiaoCollection().add(rs.get());
		});
		if (selecionado.getRegiaoCollection().isEmpty()) {
			System.out.println("aconteceu.");
			erro = true;
		}

		if (erro) {
			salvar.getStyleClass().add("erro");
			atualizar.getStyleClass().add("erro");
			return null;
		}
		return selecionado;
	}

	@Override
	public void limpaOutros() {
		tfDatacarregamento.setText(sdf.format(Calendar.getInstance().getTime()));
		cMotorista.getSelectionModel().select(null);
		cCaminhao.getSelectionModel().select(null);
		listaRegiao.stream().forEach((rese) -> {
			rese.selectedProperty().set(false);
		});
		taObs.setText("");
		tfControle.setText(String.valueOf(dao.maiorControle() + 1));
	}

	@Override
	public void limpaOutrosErros() {
		cCaminhao.getStyleClass().remove("erro");
		cMotorista.getStyleClass().remove("erro");
		regiao.getStyleClass().remove("erro");
		atualizar.getStyleClass().remove("erro");
		salvar.getStyleClass().remove("erro");
	}

	@Override
	public void setEstadoBotoesNovo() {
		botoes.getChildren().retainAll();
		deletar.setDisable(true);
		botoes.getChildren().addAll(relatorioViagem, novo, salvar, deletar);
		atualizar.getStyleClass().remove("erro");
		salvar.getStyleClass().remove("erro");
		estadoNovo = true;
		limpaTexto();
		limpaAuto();
		limpaErro();
	}

	@Override
	public void setEstadoBotoesEditar() {
		botoes.getChildren().retainAll();
		deletar.setDisable(false);
		botoes.getChildren().addAll(relatorioViagem, novo, atualizar, deletar);
		atualizar.getStyleClass().remove("erro");
		salvar.getStyleClass().remove("erro");
		estadoNovo = false;
	}

	private void createTabPropriedades() {
		estadoNovo = true;
		MotoristaDao motoristaDao = new MotoristaDao();
		CaminhaoDao caminhaoDao = new CaminhaoDao();
		RegiaoDao regiaoDao = new RegiaoDao();
		tfId = new TextField();
		tfId.setEditable(false);
		tfControle = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String reg = "[0-9]*";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				text = text.trim();
				String reg = "[0-9]*";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tfControle.setText(String.valueOf(dao.maiorControle() + 1));
		tfControle.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				Viagem temp = null;
				ViagemWrapper tempw;
				if (tfControle.getText().compareTo("") != 0) {
					temp = dao.getByControle(Integer.valueOf(tfControle.getText()));
				}
				if (temp != null) {
					tempw = new ViagemWrapper(temp);
					setPropriedades(tempw);
				} else {
					selecionado = null;
				}
			}
		});
		tfDatacarregamento = new TextField(sdf.format(Calendar.getInstance().getTime())) {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				if (text.compareToIgnoreCase("h") == 0) {
					super.setText(sdf.format(Calendar.getInstance().getTime()));
					return;
				}
				ArrayList<String> reg = new ArrayList<>();
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[/]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[/]");
				reg.add("[0-9]");
				reg.add("[0-9]");
				reg.add("[0-9]?");
				reg.add("[0-9]?");
				if (getCaretPosition() >= reg.size()) {
					return;
				}
				if (text.matches(reg.get(getCaretPosition())) || text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					if (end == 1 && getCaretPosition() == 2) {
						super.replaceText(2, 2, "/");
						positionCaret(3);
					}
					if (end == 4 && getCaretPosition() == 5) {
						super.replaceText(5, 5, "/");
						positionCaret(6);
					}
				}
				if (super.getText().length() > reg.size()) {
					int caret = getCaretPosition();
					super.setText(super.getText().substring(0, caret));
					super.positionCaret(caret);
				}
			}
		};
		tfDatacarregamento.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
			if (!newPropertyValue) {
				if (tfDatacarregamento.getText().matches("[0-9][0-9]/[0-9][0-9]/?")) {
					tfDatacarregamento.setText(tfDatacarregamento.getText().substring(0, 5) + "/" + Calendar.getInstance().get(Calendar.YEAR));
				}
				if (tfDatacarregamento.getText().matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]")) {
					tfDatacarregamento.setText(tfDatacarregamento.getText().substring(0, 6) + "20" + tfDatacarregamento.getText().substring(6, 8));
				}
				if (tfDatacarregamento.getText().matches("[0-9][0-9][0-9][0-9][0-9][0-9]")) {
					String tf = tfDatacarregamento.getText().substring(0, 2);
					tf = tf + "/" + tfDatacarregamento.getText().substring(2, 4);
					tf = tf + "/20" + tfDatacarregamento.getText().substring(4, 6);
					tfDatacarregamento.setText(tf);
				}
				if (tfDatacarregamento.getText().matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
					String tf = tfDatacarregamento.getText().substring(0, 2);
					tf = tf + "/" + tfDatacarregamento.getText().substring(2, 4);
					tf = tf + "/" + tfDatacarregamento.getText().substring(4, 8);
					tfDatacarregamento.setText(tf);
				}
			}
		});
		tfCarga = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String reg = "[0-9]*";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				text = text.trim();
				String reg = "[0-9]*";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tfAdiantamento = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				text = text.trim();
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tfChapa = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				text = text.trim();
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};
		tfFrete = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (text.compareTo("") == 0) {
					super.replaceText(start, end, text);
					return;
				}
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceText(start, end, text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}

			@Override
			public void replaceSelection(String text) {
				text = text.trim();
				String reg = "[\\d*\\.*\\d*]*,?\\d?\\d?";
				String old = super.getText();
				int caret = getCaretPosition();
				super.replaceSelection(text);
				if (!super.getText().matches(reg) && text.compareTo("") != 0) {
					super.setText(old);
					positionCaret(caret);
				}
			}
		};

		tfFrete.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
			if (newPropertyValue) {
				ultimoValorTfFrete = tfFrete.getText();
			}
			if (!newPropertyValue) {
				if (ultimoValorTfFrete.compareTo(tfFrete.getText()) != 0) {
					boolean atualizarFrete = estadoNovo;
					if (!estadoNovo) {
						ModalDialog.Resposta resposta;
						ModalDialog modal = new ModalDialog(new Stage(), ModalDialog.Tipo.simnao, "Recalcular o valor do frete?");
						resposta = modal.getResposta();
						if (resposta == ModalDialog.Resposta.sim) {
							atualizarFrete = true;
						}
					}
					if (atualizarFrete) {
						if (tfFrete.getText().compareTo("") != 0) {
							if (cCaminhao.getSelectionModel().getSelectedItem() != null) {
								tfComissao.setText(String.format("%,3.2f",
									Double.valueOf(tfFrete.getText().replace(".", "").replace(",", "."))
									* (cCaminhao.getSelectionModel().getSelectedItem().get().getComissao() / 100)));
							} else {
								tfComissao.setText("0");
							}
						}
					}
				}
			}
		});

		tfComissao = new TextField();
		tfComissao.setEditable(false);

		taObs = new TextArea();
		taObs.setPrefSize(230, 25);
		taObs.setMaxWidth(230);
		taObs.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			switch (event.getCode()) {
				case TAB: {
					botoes.getChildren().get(0).requestFocus();
					event.consume();
					break;
				}
			}
		});
		taObs.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				regiao.setMinSize(230, 25);
				taObs.setPrefSize(230, 235);
			} else {
				regiao.setPrefSize(230, 235);
				taObs.setPrefSize(230, 25);
			}
		});

		caminhaoW = caminhaoDao.listarWrapper(true);
		cCaminhao = new ComboBox<>();
		cCaminhao.getItems().addAll(caminhaoW.filtered((CaminhaoWrapper t) -> t.get().getAtivo()));
		motoristaW = motoristaDao.listarWrapper();
		cMotorista = new ComboBox<>();
		cMotorista.getItems().addAll(motoristaW.filtered((MotoristaWrapper t) -> t.get().getAtivo()));
		cMotorista.valueProperty().addListener((ObservableValue<? extends MotoristaWrapper> observable, MotoristaWrapper oldValue, MotoristaWrapper newValue) -> {
			if (newValue != null) {
				if (cCaminhao.getValue() == null) {
					if (newValue.get().getFavcaminhaoid() != null) {
						CaminhaoWrapper cw = new CaminhaoWrapper(cMotorista.getValue().get().getFavcaminhaoid());
						cCaminhao.getSelectionModel().select(cw);
					}
				}
			}
		});

		cCaminhao.valueProperty().addListener((ObservableValue<? extends CaminhaoWrapper> observable, CaminhaoWrapper oldValue, CaminhaoWrapper newValue) -> {
			if (newValue != null) {
				boolean atualizarFrete = estadoNovo;
				if (!estadoNovo) {
					ModalDialog.Resposta resposta;
					ModalDialog modal = new ModalDialog(new Stage(), ModalDialog.Tipo.simnao, "Recalcular o valor do frete?");
					resposta = modal.getResposta();
					if (resposta == ModalDialog.Resposta.sim) {
						atualizarFrete = true;
					}
				}
				if (atualizarFrete) {
					if (tfFrete.getText().compareTo("") != 0) {
						if (cCaminhao.getSelectionModel().getSelectedItem() != null) {
							tfComissao.setText(String.format("%,3.2f",
								Double.valueOf(tfFrete.getText().replace(".", "").replace(",", "."))
								* (cCaminhao.getSelectionModel().getSelectedItem().get().getComissao() / 100)));
						} else {
							tfComissao.setText("0");
						}
					}
				}
			}
		});

		caminhaoFiltro = caminhaoDao.listarWrapper(true);
		caminhaoFiltro.add(0, new CaminhaoWrapper(new Caminhao(-1)));
		motoristaFiltro = motoristaDao.listarWrapper();
		motoristaFiltro.add(0, new MotoristaWrapper(new Motorista(-1)));

		campos = new ArrayList<>();
		campos.add(tfId);
		campos.add(tfAdiantamento);
		campos.add(tfCarga);
		campos.add(tfChapa);
		campos.add(tfControle);
		campos.add(tfDatacarregamento);
		campos.add(tfFrete);
		campos.add(tfComissao);

		Image imgRel = new Image(getClass().getResourceAsStream("relatorio.png"));
		ImageView relView = new ImageView(imgRel);
		relView.setPreserveRatio(true);
		relatorioViagem = new Button("", relView);
		relatorioViagem.setTooltip(new Tooltip("Gerar relatório"));

		botoes.getChildren().add(0, relatorioViagem);
		relatorioViagem.setOnAction((ActionEvent event) -> {
			if (selecionado != null) {
				RelatorioViagem.gerar(selecionado.get(), taObs.getText());
			}
		});

		listaRegiao = regiaoDao.listarWrapper();

		regiao = new ListView<>();
		regiao.setPrefSize(230, 235);
		regiao.setEditable(true);
		regiao.setItems(this.listaRegiao);
		Callback<RegiaoWrapper, ObservableValue<Boolean>> getProperty = new Callback<RegiaoWrapper, ObservableValue<Boolean>>() {
			@Override
			public BooleanProperty call(RegiaoWrapper elemento) {
				return elemento.selectedProperty();
			}
		};
		Callback<ListView<RegiaoWrapper>, ListCell<RegiaoWrapper>> forListView = CheckBoxListCell.forListView(getProperty);
		regiao.setCellFactory(forListView);
		regiao.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case ENTER: {
						taObs.requestFocus();
						event.consume();
						break;
					}
					case SPACE: {
						if (regiao.getFocusModel().getFocusedIndex() != -1) {
							if (regiao.getFocusModel().getFocusedItem().selectedProperty().getValue()) {
								regiao.getFocusModel().getFocusedItem().selectedProperty().set(false);
							} else {
								regiao.getFocusModel().getFocusedItem().selectedProperty().set(true);
							}
						}
						event.consume();
						break;
					}
				}
			}
		});
	}

	private void createTabFiltro() {
		ftfDatacarregamento = new TextField();
		int ANO = Calendar.getInstance().get(Calendar.YEAR);
		String Y = String.valueOf(ANO).substring(2);
		int MES = Calendar.getInstance().get(Calendar.MONTH) + 1;
		String M = String.valueOf(MES);
		if (MES < 10) {
			M = "0" + M;
		}
		int DIAMAX = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		String periodoMes = "01/" + M + "/" + Y + "|" + DIAMAX + "/" + M + "/" + Y;
		ftfDatacarregamento.setText(periodoMes);
		try {
			filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
		} catch (ParseException ex) {
			Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
		}
		ftfDatacarregamento.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				ArrayList<String> listaDatas = mascaraStringData(ftfDatacarregamento.getText());
				String dats = "";
				for (String str : listaDatas) {
					dats = dats + str;
				}
				ftfDatacarregamento.setText(dats);
				try {
					filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
					dirty.set(true);
				} catch (ParseException ex) {
					Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		ftfDatacarregamento.setOnKeyReleased((KeyEvent e) -> {
			switch (e.getCode()) {
				case TAB: {	//enter = tab
					ArrayList<String> listaDatas = mascaraStringData(ftfDatacarregamento.getText());
					String dats = "";
					for (String str : listaDatas) {
						dats = dats + str;
					}
					ftfDatacarregamento.setText(dats);
					try {
						filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
						dirty.set(true);
					} catch (ParseException ex) {
						Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case H: {
					SimpleDateFormat curto = new SimpleDateFormat("dd/MM/yy");
					ftfDatacarregamento.setText(curto.format(Calendar.getInstance().getTime()));
					try {
						filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
						dirty.set(true);
					} catch (ParseException ex) {
						Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case M: {
					int ano = Calendar.getInstance().get(Calendar.YEAR);
					String y = String.valueOf(ano).substring(2);
					int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
					String m = String.valueOf(mes);
					if (mes < 10) {
						m = "0" + m;
					}
					int diaMax = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
					String str = "01/" + m + "/" + y + "|" + diaMax + "/" + m + "/" + y;
					ftfDatacarregamento.setText(str);
					try {
						filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
						dirty.set(true);
					} catch (ParseException ex) {
						Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
				case P: {
					Calendar mesPassado = Calendar.getInstance();
					mesPassado.add(Calendar.MONTH, -1);
					int ano = mesPassado.get(Calendar.YEAR);
					String y = String.valueOf(ano).substring(2);
					int mes = mesPassado.get(Calendar.MONTH) + 1;
					String m = String.valueOf(mes);
					if (mes < 10) {
						m = "0" + m;
					}
					int diaMax = mesPassado.getActualMaximum(Calendar.DAY_OF_MONTH);
					String str = "01/" + m + "/" + y + "|" + diaMax + "/" + m + "/" + y;
					ftfDatacarregamento.setText(str);
					try {
						filtro = processaData(mascaraStringData(ftfDatacarregamento.getText()));
						dirty.set(true);
					} catch (ParseException ex) {
						Logger.getLogger(PainelPropriedadesViagem.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
			}
		});

		Image imgRel = new Image(getClass().getResourceAsStream("relatorio.png"));
		ImageView relView = new ImageView(imgRel);
		relView.setPreserveRatio(true);
		relView.setFitHeight(32);
		relatorioTabela = new Button("", relView);
		relatorioTabela.setPrefWidth(32);
		relatorioTabela.setOnAction((ActionEvent event) -> {
			ObservableList<Motorista> m = FXCollections.observableArrayList();
			if (fcMotorista.getValue() != null) {
				if (fcMotorista.getValue().get().getId() != -1) {
					m.add(fcMotorista.getValue().get());
				}
			}
			ObservableList<Viagem> dados;
			if (m.isEmpty()) {
				dados = dao.filtrarViagem(getFiltroData(), getDatas(), null, null, getRegioesSelecionadas());
				MotoristaDao mDao = new MotoristaDao();
				m = mDao.listar();
			} else {
				dados = dao.filtrarViagem(getFiltroData(), getDatas(), null, fcMotorista.getSelectionModel().getSelectedItem().get(), getRegioesSelecionadas());
			}
			if (!dados.isEmpty()) {
				RelatorioTabela.gerar(dados, m, ftfDatacarregamento.getText(), getRegioesSelecionadas());
			}
		});

		botoesFiltro = new HBox();
		botoesFiltro.setAlignment(Pos.BASELINE_RIGHT);
		botoesFiltro.getChildren().add(relatorioTabela);

		fcCaminhao = new ComboBox<>();
		fcCaminhao.getItems().addAll(caminhaoFiltro);
		fcCaminhao.getSelectionModel().select(0);
		fcCaminhao.setOnAction((ActionEvent event) -> {
			dirty.set(true);
		});
		fcMotorista = new ComboBox<>();
		fcMotorista.getItems().addAll(motoristaFiltro);
		fcMotorista.getSelectionModel().select(0);
		fcMotorista.setOnAction((ActionEvent event) -> {
			dirty.set(true);
		});

		RegiaoDao regiaoDao = new RegiaoDao();
		listaFiltroRegiao = regiaoDao.listarWrapper();
		listaFiltroRegiao.stream().forEach((rwf) -> {
			rwf.selectedProperty().set(true);
		});

		fregiao = new ListView<>();
		fregiao.setPrefSize(230, 300);
		fregiao.setEditable(true);
		fregiao.setItems(this.listaFiltroRegiao);

		Callback<RegiaoWrapper, ObservableValue<Boolean>> getProperty = new Callback<RegiaoWrapper, ObservableValue<Boolean>>() {
			@Override
			public BooleanProperty call(RegiaoWrapper elemento) {
				elemento.selectedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						setDirty(true);
					}
				});
				return elemento.selectedProperty();
			}
		};
		Callback<ListView<RegiaoWrapper>, ListCell<RegiaoWrapper>> forListView = CheckBoxListCell.forListView(getProperty);
		fregiao.setCellFactory(forListView);
		fregiao.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			switch (event.getCode()) {
				case SPACE: {
					if (fregiao.getFocusModel().getFocusedIndex() != -1) {
						if (fregiao.getFocusModel().getFocusedItem().selectedProperty().getValue()) {
							fregiao.getFocusModel().getFocusedItem().selectedProperty().set(false);
						} else {
							fregiao.getFocusModel().getFocusedItem().selectedProperty().set(true);
						}
					}
					event.consume();
					break;
				}
			}
		});
	}

	public ArrayList<String> mascaraStringData(String original) {
		int datacount = 0;
		String data = original.replaceAll("[^0-9]", "");
		String separador = original.replaceAll("[^0-9,|]", "");
		ArrayList<String> finalizado = new ArrayList<>();
		String token;
		String dia;
		String mes;
		String ano;
		String ultimoSeparador = "";
		int index = 0;
		int indexSeparador = 0;
		if (original == null || original.compareTo("") == 0) {
			return finalizado;
		}
		while (index < data.length()) {
			if (index + 6 > data.length()) {
				finalizado.add("." + data.substring(index));
				break;
			}
			int separadorAte = -1;
			int separadorAnd = -1;
			separadorAte = separador.substring(indexSeparador, indexSeparador + 1).indexOf("|");
			separadorAnd = separador.substring(indexSeparador, indexSeparador + 1).indexOf(",");
			if (datacount > 0) {
				if (separadorAnd == -1 && separadorAte == -1) {
					if (datacount == 1) {
						finalizado.add("|");
						ultimoSeparador = "|";
					} else if (datacount > 1) {
						finalizado.add(",");
						ultimoSeparador = ",";
					}
				} else if (separadorAte == 0) {
					if (ultimoSeparador.compareTo("|") == 0) {
						finalizado.add(",");
						ultimoSeparador = ",";
					} else {
						finalizado.add("|");
						ultimoSeparador = "|";
					}
					indexSeparador++;
				} else if (separadorAnd == 0) {
					finalizado.add(",");
					ultimoSeparador = ",";
					indexSeparador++;
				}
			}
			dia = data.substring(index, index + 2);
			index = index + 2;
			indexSeparador = indexSeparador + 2;
			mes = data.substring(index, index + 2);
			index = index + 2;
			indexSeparador = indexSeparador + 2;
			ano = data.substring(index, index + 2);
			index = index + 2;
			indexSeparador = indexSeparador + 2;
			if (Integer.parseInt(dia) > 30) {
				dia = "31";
			}
			if (Integer.parseInt(dia) == 0) {
				dia = "01";
			}
			if (Integer.parseInt(mes) > 12) {
				mes = "12";
			}
			if (Integer.parseInt(mes) == 0) {
				mes = "01";
			}
			if (Integer.parseInt(ano) > 34) {
				ano = "34";
			}
			token = dia + "/" + mes + "/" + ano;
			finalizado.add(token);

			datacount++;
		}
		finalizado.add("");
		return finalizado;
	}

	public String processaData(ArrayList<String> original) throws ParseException {
		datas = new ArrayList<>();
		if (original == null) {
			return "";
		}
		if (original.isEmpty()) {
			return "";
		}
		if (original.get(original.size() - 2).startsWith(".")) {	//retira sobras depois do ponto
			original.remove(original.get(original.size() - 2));
		}
		if (original.size() < 2) {
			return "";
		}
		StringBuilder sql = new StringBuilder();
		int sequencia = -1;
		SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yy");
		boolean first = true;
		String lastToken = null;
		for (String token : original) {
			if (token.compareTo(",") == 0 || token.compareTo("") == 0) {
				if (!first) {
					sequencia++;
					Date d = dataformat.parse(lastToken);
					Calendar cal = Calendar.getInstance();
					cal.setTime(d);
					d = cal.getTime();
					datas.add(d);
					sql.append(":d").append(sequencia).append(" ");
					first = true;
				}
				if (token.compareTo("") != 0) {
					sql.append("OR ");
				}
				lastToken = token;
			} else if (token.compareTo("|") == 0) {
				lastToken = token;
			} else {
				if (first) {
					if (token.compareTo("") != 0) {
						sql.append("v.datacarregamento BETWEEN ");
						first = false;
						sequencia++;
						Date d = dataformat.parse(token);
						datas.add(d);
						sql.append(":d").append(sequencia).append(" ");
						sql.append("AND ");
					}
				} else {
					if (lastToken.compareTo("|") == 0 || lastToken.compareTo(",") == 0) {
						sequencia++;
						Date d = dataformat.parse(token);
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						cal.add(Calendar.DATE, 1);
						d = cal.getTime();
						datas.add(d);
						sql.append(":d").append(sequencia).append(" ");
					}
					first = true;
				}
				lastToken = token;
			}
		}
		return sql.toString();
	}

	public ComboBox<MotoristaWrapper> getFcMotorista() {
		return fcMotorista;
	}

	public void setFcMotorista(ComboBox<MotoristaWrapper> fcMotorista) {
		this.fcMotorista = fcMotorista;
	}

	public ComboBox<CaminhaoWrapper> getFcCaminhao() {
		return fcCaminhao;
	}

	public void setFcCaminhao(ComboBox<CaminhaoWrapper> fcCaminhao) {
		this.fcCaminhao = fcCaminhao;
	}

	public ListView<RegiaoWrapper> getFregiao() {
		return fregiao;
	}

	public ArrayList<Regiao> getRegioesSelecionadas() {
		ArrayList<Regiao> res = new ArrayList<>();
		listaFiltroRegiao.stream().filter((reg) -> (reg.getSelected())).forEach((reg) -> {
			res.add(reg.get());
		});
		return res;
	}

	public void setFregiao(ListView<RegiaoWrapper> fregiao) {
		this.fregiao = fregiao;
	}

	public TabPane getTabs() {
		return tabs;
	}

	public void setTabs(TabPane tabs) {
		this.tabs = tabs;
	}

	public void auto() {
		ContasMatDao ctasDao = new ContasMatDao();
		ObservableList<ContasMat> ctadeb = ctasDao.getByCcDeb(selecionado.get().getIdcaminhao().getCentrocusto());

		double chapa = 0;
		double adt = 0;
		for (ContasMat conta : ctadeb) {
			if (conta.getDescricao().matches("AC.*\\d\\d\\d\\d.*")) {
				String acerto = conta.getDescricao();
				int offset = 2;
				while (!acerto.substring(offset, offset + 4).matches("\\d\\d\\d\\d")) {
					offset++;
				}
				acerto = acerto.substring(offset, offset + 4);
				if (acerto.compareTo(String.valueOf(selecionado.getNumcontrole())) == 0) {
					if (conta.getCtadeb() == 1984) {		//chapa
//						System.out.println("\nconta " + acerto + " chapa " + conta.getValor());
						chapa += conta.getValor();
					}
					if (conta.getCtadeb() == 1019) {		//adt
//						System.out.println("\nconta " + acerto + " val " + conta.getValor());
						adt += conta.getValor();
					}
				}
			}
		}
		tfChapa.getStyleClass().remove("dif");
		tfChapa.getStyleClass().remove("auto");
		tfAdiantamento.getStyleClass().remove("dif");
		tfAdiantamento.getStyleClass().remove("auto");
		if (chapa != 0) {
			if (tfChapa.getText().compareTo("") == 0 || Double.parseDouble(tfChapa.getText().replace(".", "").replace(",", ".")) == 0) {
				tfChapa.setText(String.valueOf(chapa));
				tfChapa.getStyleClass().add("auto");
			} else {
				if (Double.parseDouble(tfChapa.getText().replace(".", "").replace(",", ".")) != chapa) {
					tfChapa.getStyleClass().add("dif");
				}
			}
		}

		if (adt != 0) {
			if (tfAdiantamento.getText().compareTo("") == 0 || Double.parseDouble(tfAdiantamento.getText().replace(".", "").replace(",", ".")) == 0) {
				tfAdiantamento.setText(String.valueOf(adt));
				tfAdiantamento.getStyleClass().add("auto");
			} else {
				if (Double.parseDouble(tfAdiantamento.getText().replace(".", "").replace(",", ".")) != adt) {
					tfAdiantamento.getStyleClass().add("dif");
				}
			}
		}
	}
}
