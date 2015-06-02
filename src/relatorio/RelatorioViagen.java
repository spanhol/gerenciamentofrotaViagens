package relatorio;

import entidades.Regiao;
import entidades.Viagem;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	String pagina;
	String motorista = "@nomemotorista@";
	String admissao = "@admissao@";
	String ctps = "@ctps@";
	String cpf = "@cpf@";
	String rg = "@rg@";
	String cnh = "@cnh@";
	String carga = "@carga@";
	String controle = "@controle@";
	String veiculo = "@veiculo@";
	String carregamento = "@carregamento@";
	String destino = "@destino@";
	String adiantamento = "@adiantamento@";
	String adiantamentoporextenso = "@adiantamentoporextenso@";
	String chapa = "@chapa@";
	String chapaporextenso = "@chapaporextenso@";
	String data = "@data@";
	String obs = "@obs@";

	public RelatorioViagen() {
		BufferedReader bufferedReader = null;
		StringBuilder sb = new StringBuilder();
		try {
			bufferedReader = new BufferedReader(new FileReader("C:\\ControleViagens\\modelo.html"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(RelatorioViagen.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioViagen.class.getName()).log(Level.SEVERE, null, ex);
		}
		pagina = sb.toString();
	}

	public void Gerar(Viagem v, String observacao) {
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

		File f = new File("C:\\ControleViagens\\relatorio.html");
		try {
			f.createNewFile();
			PrintWriter out = new PrintWriter(f);
			out.println(pagina);
			out.close();
			Desktop d = Desktop.getDesktop();
			String path = f.getAbsolutePath().replace("\\", "/");
			d.browse(new URI("file:///C:/ControleViagens/relatorio.html"));
		} catch (URISyntaxException | IOException ex) {
			Logger.getLogger(RelatorioViagen.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String formata(String str) {
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
