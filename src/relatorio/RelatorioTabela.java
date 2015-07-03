package relatorio;

import entidades.Motorista;
import entidades.Regiao;
import entidades.Viagem;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Fernando Spanhol
 */
public class RelatorioTabela {

	static File f = new File("C:\\Movelmar\\ControleViagens\\relatoriotabela.html");
	static String pagina = "<!DOCTYPE html>\n"
		+ "<html>\n"
		+ "	<head>\n"
		+ "		<title>CONTROLE DE VIAGENS</title>\n"
		+ "		<meta charset=\"windows-1252\">\n"
		+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"coluna.css\">\n"
		+ "	</head>\n"
		+ "	<body>\n"
		+ "		<div class=\"nomerelatorio\">CONTROLE DE VIAGENS<br><br></div>\n"
		+ "			@tabela@\n"
		+ "	</body>\n"
		+ "</html>";
	static String tabela
		= "<table>\n"
		+ "			<col width=\"70\">\n"
		+ "			<col width=\"100\">\n"
		+ "			<col width=\"70\">\n"
		+ "			<col width=\"150\">\n"
		+ "			<col width=\"70\">\n"
		+ "			<col width=\"70\">\n"
		+ "			<col width=\"70\">\n"
		+ "			<col width=\"100\">"
		+ "\n"
		+ "			@filtro@\n"
		+ "				\n"
		+ "			<tr>\n"
		+ "				<td class=\"noborder\" colspan=\"8\"></td>\n"
		+ "			</tr>\n"
		+ "			<tr>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">CARGA</th>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">DATA CARREGAMENTO</th>\n"
		+ "				<td class=\"titulo\" colspan=\"1\">VALOR DO FRETE R$</td>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">REGI&Atilde;O</th>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">COMISSÃO %</th>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">COMISSÃO R$</th>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">PLACA DO VE&Iacute;CULO</th>\n"
		+ "				<th class=\"titulo\" colspan=\"1\">OBS</th>\n"
		+ "			</tr>\n"
		+ "			@dados@\n"
		+ "			<tr>\n"
		+ "				<td class=\"titulo12\">TOTAIS</td>\n"
		+ "				<td class=\"negrito12\"></td>\n"
		+ "				<td class=\"negrito12\">@total@</td>\n"
		+ "				<td class=\"negrito12\"></td>\n"
		+ "				<td class=\"negrito12\"></td>\n"
		+ "				<td class=\"negrito12\">@totalComis@</td>\n"
		+ "				<td class=\"negrito12\"></td>\n"
		+ "				<td class=\"negrito12\"></td>\n"
		+ "			</tr>\n"
		+ "			<tr>\n"
		+ "				<td class=\"noborder\" colspan=\"8\"><br><br></td>\n"
		+ "			</tr>\n"
		+ "</table>\n";

	static String t = "@tabela@";
	static String filtro = "@filtro@";
	static String dados = "@dados@";
	static String totalFrete = "@total@";
	static double sumFrete;
	static String totalComis = "@totalComis@";
	static double sumComis;

	public static void gerar(ObservableList<Viagem> v, ObservableList<Motorista> motoristas, String periodo, ArrayList<Regiao> regioes) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder total = new StringBuilder();
		for (Motorista m : motoristas) {
			String modelo = tabela;
			sumFrete = 0;
			sumComis = 0;
			int paridade = 0;
			String classe;
			StringBuilder tabelaDados = new StringBuilder();
			for (Viagem viagem : v) {
				if (viagem.getIdmotorista().getId().compareTo(m.getId()) != 0) {
					continue;
				}
				sumFrete += viagem.getFrete();
				sumComis += (viagem.getIdcaminhao().getComissao() * viagem.getFrete()) / 100;
				if (paridade == 0) {
					classe = "par";
					paridade = 1;
				} else {
					classe = "impar";
					paridade = 0;
				}
				tabelaDados.append("<TR class = \" ");
				tabelaDados.append(classe);
				tabelaDados.append("\">\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(viagem.getNumcarga());
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(sdf.format(viagem.getDatacarregamento()));
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(String.format("%,3.2f", viagem.getFrete()));
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				for (Iterator<Regiao> it = viagem.getRegiaoCollection().iterator(); it.hasNext();) {
					Regiao r = it.next();
					tabelaDados.append(formata(r.getNome()));
					if (it.hasNext()) {
						tabelaDados.append(", ");
					}
				}
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(String.format("%,3.2f", viagem.getIdcaminhao().getComissao()));
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(String.format("%,3.2f", (viagem.getIdcaminhao().getComissao() * viagem.getFrete()) / 100));
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(viagem.getIdcaminhao().getPlaca());
				tabelaDados.append("</TD>\n");
				tabelaDados.append("<TD>");
				tabelaDados.append(formata(viagem.getObs()));
				tabelaDados.append("</TD>\n");
				tabelaDados.append("</TR>\n");
			}

			String per = formataData(periodo);

			String tfiltro = "";
			if (m != null) {
				if (m.getId() != -1) {
					tfiltro = tfiltro
						+ "\t\t\t\t<tr>\n"
						+ "\t\t\t\t\t<td class=\"titulo\" colspan=\"2\">MOTORISTA</td>\n"
						+ "\t\t\t\t\t<td colspan=\"7\">" + m.getNome() + "</td>\n"
						+ "\t\t\t\t</tr>\n";
				}
			}
			if (per.compareTo("") != 0) {
				tfiltro = tfiltro
					+ "\t\t\t\t<tr>\n"
					+ "\t\t\t\t\t<td class=\"titulo\" colspan=\"2\">PERIODO</td>\n"
					+ "\t\t\t\t\t<td colspan=\"7\">" + per + "</td>\n"
					+ "\t\t\t\t</tr>\n";
			}

			modelo = modelo.replace(filtro, tfiltro);
			modelo = modelo.replace(dados, tabelaDados.toString());
			modelo = modelo.replace(totalFrete, String.format("%,3.2f", sumFrete));
			modelo = modelo.replace(totalComis, String.format("%,3.2f", sumComis));

			if (sumFrete > 0) {
				total.append(modelo);
			}
		}

		pagina = pagina.replace(t, total.toString());
		try {
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			out.println(pagina);
			out.close();
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException ex) {
			Logger.getLogger(RelatorioTabela.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String formata(String str) {
		str = str.replace("é", "&eacute;");
		str = str.replace("É", "&Eacute;");
		str = str.replace("ã", "&atilde;");
		str = str.replace("Ã", "&Atilde;");
		str = str.replace("ç", "&ccedil;");
		str = str.replace("Ç", "&Ccedil;");
		str = str.replace("\n", "<BR>");
		return str;
	}

	public static String formataData(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat mesapenas = new SimpleDateFormat("MMMMMMMMMMM/yyyy");
		String res = "";
		if (str.contains("|")) {
			str = str.substring(0, 17);
			Date data0;
			Date data1;
			try {
				data0 = sdf.parse(str.substring(0, 8));
				data1 = sdf.parse(str.substring(9));
			} catch (ParseException ex) {
				Logger.getLogger(RelatorioTabela.class.getName()).log(Level.SEVERE, null, ex);
				return "";
			}
			Calendar c0 = Calendar.getInstance();
			c0.setTime(data0);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(data1);
			if (c0.get(Calendar.MONTH) == c1.get(Calendar.MONTH)
				&& c0.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
				if (c0.get(Calendar.DAY_OF_MONTH) == c0.getActualMinimum(Calendar.DAY_OF_MONTH)
					&& c1.get(Calendar.DAY_OF_MONTH) == c1.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					res = mesapenas.format(data0);
				}
			}
		}
		if (res.compareTo("") == 0) {
			str = str.replace("|", "&nbsp;at&eacute;&nbsp;");
			str = str.replace(",", "&nbsp;e;&nbsp;");
			res = str;
		}
		return res;
	}
}
