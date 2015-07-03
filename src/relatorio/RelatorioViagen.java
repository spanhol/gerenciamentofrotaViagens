package relatorio;

import entidades.Regiao;
import entidades.Viagem;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando Spanhol
 */
public class RelatorioViagen {

	static File f = new File("C:\\Movelmar\\ControleViagens\\relatorio.html");
	static String pagina = "<!DOCTYPE html>\n"
		+ "<html>\n"
		+ "	<head>\n"
		+ "		<title>Relatório Viagens</title>\n"
		+ "		<meta charset=\"windows-1252\">\n"
		+ "		<link rel=\"stylesheet\" type=\"text/css\" href=\"coluna.css\">\n"
		+ "	</head>\n"
		+ "	<body>\n"
		+ "		<div>\n"
		+ "			<table>\n"
		+ "				<col width=\"150\">\n"
		+ "				<col width=\"400\">\n"
		+ "				<col width=\"75\">\n"
		+ "				<col width=\"75\">\n"
		+ "				<tr>\n"
		+ "					<th colspan=\"1\" rowspan=\"3\"><img src=\"logo.png\" alt=\"\" height=\"40\"></th>\n"
		+ "					<th class=\"negrito12\" colspan=\"1\">FATURAMENTO</th>\n"
		+ "					<td class=\"norightborder\" colspan=\"1\">Código:</td>\n"
		+ "					<th class=\"noleftborder\" colspan=\"1\">FO-FAT-062</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<th class=\"negrito12\" colspan=\"1\" rowspan=\"2\">Controle de Viagem</th>\n"
		+ "					<td class=\"norightborder\" colspan=\"1\">Revisão:</td>\n"
		+ "					<th class=\"noleftborder\" colspan=\"1\">04</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"norightborder\" colspan=\"1\">Página:</td>\n"
		+ "					<th class=\"noleftborder\" colspan=\"1\">01</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<th class=\"cinza\" colspan=\"4\">Lei nº 12.619 de 30/04/2012 - Portaria do MTE nº 3.626 de 13/11/1991</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"4\"></td>\n"
		+ "				</tr>\n"
		+ "			</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"70\">\n"
		+ "				<col width=\"200\">\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"200\">\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"130\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Empresa</td>\n"
		+ "					<td colspan=\"1\">MOVELMAR IND. DE MÓVEIS LTDA.</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">CNPJ</td>\n"
		+ "					<td colspan=\"1\">04.172.775/0001-03</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">IE</td>\n"
		+ "					<td colspan=\"1\">90234611-26</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Endereço</td>\n"
		+ "					<td colspan=\"5\">Rua Etelvino Delani,11 Bairro Industrial Rude J. Spanhol - Ampére-PR - CEP 85640-000 - Fone (46) 3547-1683</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"6\"></td>\n"
		+ "				</tr>\n"
		+ "			</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"70\">	\n"
		+ "				<col width=\"150\">\n"
		+ "				<col width=\"30\">\n"
		+ "				<col width=\"120\">	\n"
		+ "				<col width=\"30\">\n"
		+ "				<col width=\"120\">	\n"
		+ "				<col width=\"30\">\n"
		+ "				<col width=\"150\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Motorista</td>\n"
		+ "					<td id=\"nomemotorista\" colspan=\"4\">@nomemotorista@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Data Admissão</td>\n"
		+ "					<td id=\"admissao\" colspan=\"2\">@admissao@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">CTPS</td>\n"
		+ "					<td id=\"ctps\" colspan=\"1\">@ctps@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">CPF</td>\n"
		+ "					<td id=\"cpf\" colspan=\"1\">@cpf@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">RG</td>\n"
		+ "					<td id=\"rg\" colspan=\"1\">@rg@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">CNH</td>\n"
		+ "					<td id=\"cnh\" colspan=\"1\">@cnh@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"8\"></td>\n"
		+ "				</tr>				\n"
		+ "			</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"70\">	\n"
		+ "				<col width=\"150\">\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"100\">	\n"
		+ "				<col width=\"30\">\n"
		+ "				<col width=\"120\">	\n"
		+ "				<col width=\"30\">\n"
		+ "				<col width=\"150\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Nº Carga</td>\n"
		+ "					<td colspan=\"1\">@carga@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Veículo</td>\n"
		+ "					<td colspan=\"1\">@veiculo@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">Data Carregamento</td>\n"
		+ "					<td colspan=\"2\">@carregamento@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">N° Controle</td>\n"
		+ "					<td colspan=\"1\">@controle@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Destino</td>\n"
		+ "					<td colspan=\"5\">@destino@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"8\"></td>\n"
		+ "				</tr>\n"
		+ "			</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"230\">\n"
		+ "				<col width=\"100\">\n"
		+ "				<col width=\"300\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">Recebi como adiantamento de despesas de viagem</td>\n"
		+ "					<td colspan=\"1\">@adiantamento@</td>\n"
		+ "					<td colspan=\"1\">@adiantamentoporextenso@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"assinaturacinza\" colspan=\"1\">Assinatura</td>\n"
		+ "					<td class=\"assinaturadireita\" colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">Recebi as despesas com auxiliar de descarga (\"chapa\")</td>\n"
		+ "					<td colspan=\"1\">@chapa@</td>\n"
		+ "					<td colspan=\"1\">@chapaporextenso@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"assinaturacinza\" colspan=\"1\">Assinatura</td>\n"
		+ "					<td class=\"assinaturadireita\" colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"4\"></td>\n"
		+ "				</tr>\n"
		+ "				</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"125\">\n"
		+ "				<col width=\"175\">\n"
		+ "				\n"
		+ "				<col width=\"175\">\n"
		+ "				<col width=\"175\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"norightborder\" colspan=\"2\">Data Saída: ____/____/______</td>\n"
		+ "					<th class=\"noleftborder\" colspan=\"1\">Hora: ____:____</th>\n"
		+ "					<td class=\"norightborder\" colspan=\"1\">Data Chegada: ____/____/______</td>\n"
		+ "					<th class=\"noleftborder\" colspan=\"1\">Hora: ____:____</th>\n"
		+ "				</tr>\n"
		+ "<!--				<tr>\n"
		+ "					<th class=\"vermelho\" colspan=\"5\">ITENS CONFERIDOS</th>\n"
		+ "				</tr>-->\n"
		+ "				<tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"2\">Km Saída: </td>\n"
		+ "					<td colspan=\"1\">Km Chegada:</td>\n"
		+ "					<td colspan=\"1\">Km Percorrido:</td>\n"
		+ "					<td colspan=\"1\">Média km/l:</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\" >OBS:</td>\n"
		+ "					<td colspan=\"4\">@obs@<br><br></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"5\" ><br></td>\n"
		+ "				</tr>\n"
		+ "			</table>\n"
		+ "\n"
		+ "			<table>\n"
		+ "				<col width=\"75\">\n"
		+ "				<col width=\"75\">\n"
		+ "				<col width=\"100\">\n"
		+ "				<col width=\"70\">\n"
		+ "				<col width=\"80\">\n"
		+ "				<col width=\"100\">\n"
		+ "				<col width=\"50\">\n"
		+ "				<col width=\"75\">\n"
		+ "				<col width=\"75\">\n"
		+ "				<tr>\n"
		+ "					<td class=\"noborder\" colspan=\"9\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">CONTROLE DE VIAGEM Nº</td>\n"
		+ "					<td id=\"controle\" colspan=\"1\">@controle@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Motorista</td>\n"
		+ "					<td colspan=\"2\">@nomemotorista@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Data</td>\n"
		+ "					<td colspan=\"2\">@carregamento@</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">Adiantamento</td>\n"
		+ "					<td colspan=\"2\">@adiantamento@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Despesas</td>\n"
		+ "					<td colspan=\"4\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinza\" colspan=\"2\">Chapa</td>\n"
		+ "					<td colspan=\"2\">@chapa@</td>\n"
		+ "					<td class=\"cinza\" colspan=\"1\">Devolução</td>\n"
		+ "					<td colspan=\"4\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<th class=\"vermelho\" colspan=\"9\">Despesa com Combustível</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"3\">Posto</td>\n"
		+ "					<td colspan=\"2\">Lt Diesel</td>\n"
		+ "					<td colspan=\"2\">R$ Unit</td>\n"
		+ "					<td colspan=\"2\">R$ Total</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"3\">Posto</td>\n"
		+ "					<td colspan=\"2\">Lt Diesel</td>\n"
		+ "					<td colspan=\"2\">R$ Unit</td>\n"
		+ "					<td colspan=\"2\">R$ Total</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"3\">Posto</td>\n"
		+ "					<td colspan=\"2\">Lt Diesel</td>\n"
		+ "					<td colspan=\"2\">R$ Unit</td>\n"
		+ "					<td colspan=\"2\">R$ Total</td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"cinzadireita\" colspan=\"3\">TOTAIS </td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<th class=\"cinza\" colspan=\"1\">Data</th>\n"
		+ "					<th class=\"cinza\" colspan=\"2\">Diária</th>\n"
		+ "					<th class=\"cinza\" colspan=\"2\">Créditos/Débitos</th>\n"
		+ "					<th class=\"cinza\" colspan=\"1\">Valor</th>\n"
		+ "					<th class=\"cinza\" colspan=\"3\">Observações</th>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(+)Diárias</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(+)Comis. Retorno</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(+)Outras despesas</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">&nbsp;</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">&nbsp;</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(-)Desp. Cartão</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(-)Adiantamento</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">&nbsp;</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">(=) Saldo</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"2\"></td>\n"
		+ "					<td colspan=\"2\">&nbsp;</td>\n"
		+ "					<td colspan=\"1\"></td>\n"
		+ "					<td colspan=\"3\"></td>\n"
		+ "				</tr>\n"
		+ "				<tr>\n"
		+ "					<td class=\"assinaturacinza\" colspan=\"1\">Assinatura Motorista:</td>\n"
		+ "					<td class=\"assinaturadireita\" colspan=\"8\"></td>\n"
		+ "				</tr>\n"
		+ "			</table>\n"
		+ "		</div>\n"
		+ "	</body>\n"
		+ "</html>";
	static String motorista = "@nomemotorista@";
	static String admissao = "@admissao@";
	static String ctps = "@ctps@";
	static String cpf = "@cpf@";
	static String rg = "@rg@";
	static String cnh = "@cnh@";
	static String carga = "@carga@";
	static String controle = "@controle@";
	static String veiculo = "@veiculo@";
	static String carregamento = "@carregamento@";
	static String destino = "@destino@";
	static String adiantamento = "@adiantamento@";
	static String adiantamentoporextenso = "@adiantamentoporextenso@";
	static String chapa = "@chapa@";
	static String chapaporextenso = "@chapaporextenso@";
	static String data = "@data@";
	static String obs = "@obs@";

	public static void Gerar(Viagem v, String observacao) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		pagina = pagina.replace(motorista, formata(v.getIdmotorista().getNome()));
		pagina = pagina.replace(admissao, sdf.format(v.getIdmotorista().getAdmissao()));
		pagina = pagina.replace(ctps, v.getIdmotorista().getCtps());
		pagina = pagina.replace(cpf, v.getIdmotorista().getCpf());
		pagina = pagina.replace(cnh, v.getIdmotorista().getCnh());
		pagina = pagina.replace(rg, v.getIdmotorista().getRg());
		pagina = pagina.replace(carga, String.valueOf(v.getNumcarga()));
		pagina = pagina.replace(controle, String.valueOf(v.getNumcontrole()));
		pagina = pagina.replace(veiculo, v.getIdcaminhao().getPlaca());
		pagina = pagina.replace(carregamento, sdf.format(v.getDatacarregamento()));
		String dest = "";
		for (Regiao r : v.getRegiaoCollection()) {
			dest = dest + formata(r.getNome()) + ", ";
		}
		dest = dest.substring(0, dest.length() - 2);
		pagina = pagina.replace(destino, dest);
		pagina = pagina.replace(adiantamento, String.format("%,3.2f", v.getAdiantamento()));

		Extenso adt = new Extenso(new BigDecimal(v.getAdiantamento()));
		pagina = pagina.replace(adiantamentoporextenso, formata(adt.toString()));
		pagina = pagina.replace(chapa, String.format("%,3.2f", v.getAdiantamentochapa()));
		Extenso chp = new Extenso(new BigDecimal(v.getAdiantamentochapa()));
		pagina = pagina.replace(chapaporextenso, formata(chp.toString()));
		pagina = pagina.replace(obs, formata(observacao));

		try {
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			out.println(pagina);
			out.close();
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException ex) {
			Logger.getLogger(RelatorioViagen.class.getName()).log(Level.SEVERE, null, ex);
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
}
