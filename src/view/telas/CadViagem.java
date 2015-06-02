package view.telas;

import dao.ViagemDao;
import entidades.Caminhao;
import entidades.Motorista;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import view.componentes.tabela.TabelaViagem;
import view.componentes.painelPropriedades.PainelPropriedades;
import view.componentes.painelPropriedades.PainelPropriedadesViagem;
import view.componentes.tabela.Table;
import wrapper.ViagemWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class CadViagem extends SceneTabOverload {

	Pane root;
	final ObservableList<ViagemWrapper> dados;
	ViagemDao dao;
	Table<ViagemWrapper> tabela;
	HBox hTabelaPropriedades;
	PainelPropriedades propriedades;

	public CadViagem(Pane main) {
		super((Parent) main);
		root = main;
		hTabelaPropriedades = new HBox();
		dao = new ViagemDao();
		propriedades = new PainelPropriedadesViagem(dao);
		dados = dao.filtrar(propriedades.getFiltroData(), propriedades.getDatas(), null, null, null);
		tabela = new TabelaViagem(dados);
		tabela.setPrefHeight(2000);
		tabela.setPrefWidth(2000);

		propriedades.getDirty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean isDirty) -> {
			if (isDirty) {
				PainelPropriedadesViagem p = (PainelPropriedadesViagem) propriedades;
				Motorista m = p.getFcMotorista().getValue().get();
				if (m.getId() == -1) {
					m = null;
				}
				Caminhao c = p.getFcCaminhao().getValue().get();
				if (c.getId() == -1) {
					c = null;
				}
				dados.retainAll();
				ObservableList<ViagemWrapper> update = dao.filtrar(propriedades.getFiltroData(), propriedades.getDatas(), c, m, p.getRegioesSelecionadas());
				dados.addAll(update);
				propriedades.setDirty(false);
			}
		});
		tabela.getNovaSelecao().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				propriedades.setPropriedades(tabela.getSelecionado());
				tabela.setNovaSelecao(false);
			}
		});

		tabela.getDuploClick().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				if (propriedades instanceof PainelPropriedadesViagem) {
					PainelPropriedadesViagem ppv = (PainelPropriedadesViagem) propriedades;
					ppv.getTabs().getSelectionModel().select(0);
				}
				tabela.setDuploClick(false);
			}
		});
		hTabelaPropriedades.getChildren().addAll(propriedades, tabela);
		main.getChildren().add(hTabelaPropriedades);
	}
}
