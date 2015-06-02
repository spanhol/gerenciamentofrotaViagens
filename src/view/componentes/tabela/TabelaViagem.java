package view.componentes.tabela;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import wrapper.ViagemWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class TabelaViagem extends Table<ViagemWrapper> {

	public TabelaViagem(ObservableList<ViagemWrapper> dados) {
		super(dados);
		elementos = dados;

		TableColumn id = new TableColumn<>("id");
		colunas.add(id);
		id.setCellValueFactory(new PropertyValueFactory("id"));
		id.setCellFactory(getWholeIntegerCellFactory(""));

		TableColumn controle = new TableColumn<>("Controle");
		colunas.add(controle);
		controle.setCellValueFactory(new PropertyValueFactory("numcontrole"));
		controle.setCellFactory(getWholeIntegerCellFactory(""));
		estiloCodigo(controle, "");

		TableColumn carga = new TableColumn<>("Carga");
		colunas.add(carga);
		carga.setCellValueFactory(new PropertyValueFactory("numcarga"));
		carga.setCellFactory(getWholeIntegerCellFactory(""));
		estiloCodigo(carga, "");

		TableColumn carregamento = new TableColumn<>("Data carregamento");
		colunas.add(carregamento);
		carregamento.setCellValueFactory(new PropertyValueFactory("datacarregamento"));
		carregamento.setCellFactory(getDateCellFactory(""));
		estiloData(carregamento, "");

		TableColumn destino = new TableColumn<>("Destino");
		colunas.add(destino);
		destino.setCellValueFactory(new PropertyValueFactory("regiao"));
		destino.setCellFactory(getStringCellFactory(""));
		destino.setMinWidth(100);
		destino.setPrefWidth(200);
		destino.setMaxWidth(1000);

		TableColumn frete = new TableColumn<>("Frete");
		colunas.add(frete);
		frete.setCellValueFactory(new PropertyValueFactory("frete"));
		frete.setCellFactory(getDoubleCellFactory(""));
		estiloDouble(frete, "");

		TableColumn comissao = new TableColumn<>("Comissão");
		colunas.add(comissao);
		comissao.setCellValueFactory(new PropertyValueFactory("comissao"));
		comissao.setCellFactory(getDoubleCellFactory(""));
		estiloDouble(comissao, "");

		TableColumn motorista = new TableColumn<>("Motorista");
		colunas.add(motorista);
		motorista.setCellValueFactory(new PropertyValueFactory("motorista"));
		motorista.setCellFactory(getMotoristaCellFactory(""));
		motorista.setMinWidth(100);
		motorista.setPrefWidth(200);
		motorista.setMaxWidth(1000);

		TableColumn caminhao = new TableColumn<>("Caminhão");
		colunas.add(caminhao);
		caminhao.setCellValueFactory(new PropertyValueFactory("caminhao"));
		caminhao.setCellFactory(getCaminhaoCellFactory(""));
		caminhao.setMinWidth(75);//caminhao
		caminhao.setMaxWidth(75);

		TableColumn adiantamento = new TableColumn<>("Adiantamento");
		colunas.add(adiantamento);
		adiantamento.setCellValueFactory(new PropertyValueFactory("adiantamento"));
		adiantamento.setCellFactory(getDoubleCellFactory(""));
		estiloDouble(adiantamento, "");

		TableColumn adiantamentochapa = new TableColumn<>("Chapa");
		colunas.add(adiantamentochapa);
		adiantamentochapa.setCellValueFactory(new PropertyValueFactory("adiantamentochapa"));
		adiantamentochapa.setCellFactory(getDoubleCellFactory(""));
		estiloDouble(adiantamentochapa, "");

		setOnKeyReleased(new MyKeyEventHandler());

		getColumns().setAll(colunas);
		getColumns().get(0).setVisible(false);

		controle.setSortType(TableColumn.SortType.ASCENDING);
		sort();
	}
}
