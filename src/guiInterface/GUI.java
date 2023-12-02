package guiInterface;

import javax.swing.*;

import Model.Aresta;
import Model.Dijkstra;
import Model.Grafo;
import Model.Vertice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame {

    private JTextField origemField, destinoField;
    private JButton calcularButton;
    private JTextArea resultadoArea;
    private Grafo grafo;

    public GUI(Grafo grafo) {
        this.grafo = grafo;

        setTitle("Menor Caminho - Dijkstra");
        setSize(400, 350); // Ajustei a altura para acomodar a imagem
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        initListeners();
    }
    
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Menor Caminho"));
        panel.setBackground(new Color(240, 240, 240));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Adição da imagem acima da caixa de texto
        ImageIcon imagem = new ImageIcon("src/assets/mapagrafo.jpg"); // Substitua pelo caminho real da sua imagem
        JLabel imagemLabel = new JLabel(imagem);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(imagemLabel, gbc);

        origemField = new JTextField(20);
        destinoField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Origem:"), gbc);

        gbc.gridx = 1;
        panel.add(origemField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Destino:"), gbc);

        gbc.gridx = 1;
        panel.add(destinoField, gbc);

        calcularButton = new JButton("Calcular Menor Caminho");
        calcularButton.setBackground(new Color(0, 123, 255));
        calcularButton.setForeground(Color.WHITE);
        calcularButton.setFocusPainted(false);

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);

        // Adicionando uma barra de rolagem
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Adicionando uma barra de rolagem
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(calcularButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel);
        customizeFonts();
    }



    private void initListeners() {
        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = origemField.getText();
                String destino = destinoField.getText();

                if (!origem.isEmpty() && !destino.isEmpty()) {
                    Vertice v1 = grafo.encontrarVertice(origem);
                    Vertice v2 = grafo.encontrarVertice(destino);

                    if (v1 != null && v2 != null) {
                        Dijkstra dijkstra = new Dijkstra();
                        List<Vertice> menorCaminho = dijkstra.encontrarMenorCaminhoDijkstra(grafo, v1, v2);

                        exibirResultado(menorCaminho);
                    } else {
                        JOptionPane.showMessageDialog(null, "Origem ou destino não encontrados no grafo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Digite origem e destino.");
                }
            }
        });
    }

    private void exibirResultado(List<Vertice> menorCaminho) {
        StringBuilder resultado = new StringBuilder("Menor Caminho: ");
        double custoDoCaminho = 0.0;

        for (int i = 0; i < menorCaminho.size(); i++) {
            resultado.append(menorCaminho.get(i).getNomeCidade());

            if (i < menorCaminho.size() - 1) {
                Vertice origem = menorCaminho.get(i);
                Vertice destino = menorCaminho.get(i + 1);
                Aresta aresta = encontrarArestaEntreVertices(origem, destino);

                resultado.append(" -> ");
                //resultado.append(aresta.getPeso());

                // Calcula o custo adicionando o peso da aresta ao custo do caminho
                custoDoCaminho += aresta.getPeso();
            }
        }

        resultado.append("\nCusto do Caminho: ").append(custoDoCaminho).append(" Quilômentros");

        resultadoArea.setText(resultado.toString());
    }


    private Aresta encontrarArestaEntreVertices(Vertice origem, Vertice destino) {
        for (Aresta aresta : origem.getArestas()) {
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                return aresta;
            }
        }
        return null;  // Retorna null se a aresta não for encontrada
    }

    
    private void customizeFonts() {
        Font font = new Font("Arial", Font.PLAIN, 14);
        origemField.setFont(font);
        destinoField.setFont(font);
        calcularButton.setFont(font);
        resultadoArea.setFont(font);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Grafo grafo = construirGrafo();
                new GUI(grafo).setVisible(true);
            }
        });
    }

     
    private static Grafo construirGrafo() {
    	Vertice ac = new Vertice();
    	ac.setNomeCidade("Acre");

    	Vertice al = new Vertice();
    	al.setNomeCidade("Alagoas");

    	Vertice ap = new Vertice();
    	ap.setNomeCidade("Amapá");

    	Vertice am = new Vertice();
    	am.setNomeCidade("Amazonas");

    	Vertice ba = new Vertice();
    	ba.setNomeCidade("Bahia");

    	Vertice ce = new Vertice();
    	ce.setNomeCidade("Ceará");

    	Vertice es = new Vertice();
    	es.setNomeCidade("Espírito Santo");

    	Vertice go = new Vertice();
    	go.setNomeCidade("Goiás");

    	Vertice ma = new Vertice();
    	ma.setNomeCidade("Maranhão");

    	Vertice mt = new Vertice();
    	mt.setNomeCidade("Mato Grosso");

    	Vertice ms = new Vertice();
    	ms.setNomeCidade("Mato Grosso do Sul");

    	Vertice mg = new Vertice();
    	mg.setNomeCidade("Minas Gerais");

    	Vertice pa = new Vertice();
    	pa.setNomeCidade("Pará");

    	Vertice pb = new Vertice();
    	pb.setNomeCidade("Paraíba");

    	Vertice pr = new Vertice();
    	pr.setNomeCidade("Paraná");

    	Vertice pe = new Vertice();
    	pe.setNomeCidade("Pernambuco");

    	Vertice pi = new Vertice();
    	pi.setNomeCidade("Piauí");

    	Vertice rj = new Vertice();
    	rj.setNomeCidade("Rio de Janeiro");

    	Vertice rn = new Vertice();
    	rn.setNomeCidade("Rio Grande do Norte");

    	Vertice rs = new Vertice();
    	rs.setNomeCidade("Rio Grande do Sul");

    	Vertice ro = new Vertice();
    	ro.setNomeCidade("Rondônia");

    	Vertice rr = new Vertice();
    	rr.setNomeCidade("Roraima");

    	Vertice sc = new Vertice();
    	sc.setNomeCidade("Santa Catarina");

    	Vertice sp = new Vertice();
    	sp.setNomeCidade("São Paulo");

    	Vertice se = new Vertice();
    	se.setNomeCidade("Sergipe");

    	Vertice to = new Vertice();
    	to.setNomeCidade("Tocantins");

        // Criando arestas
        
        Aresta rs_sc = new Aresta(rs, sc);
        
        Aresta sc_pr = new Aresta(sc, pr);
        
        Aresta pr_sp = new Aresta(pr, sp);
        Aresta pr_ms = new Aresta(pr, ms);
        
        Aresta sp_ms = new Aresta(sp, ms);
        Aresta sp_rj = new Aresta(sp, rj);
        Aresta sp_mg = new Aresta(sp, mg);
        
        Aresta ms_mt = new Aresta(ms, mt);        
        Aresta ms_mg = new Aresta(ms, mg);
        Aresta ms_go = new Aresta(ms, go);
        
        Aresta rj_mg = new Aresta(rj, mg);
        Aresta rj_es = new Aresta(rj, es);
        
        Aresta mg_go = new Aresta(mg, go);
        Aresta mg_es = new Aresta(mg, es);
        Aresta mg_ba = new Aresta(mg, ba);

        Aresta es_ba = new Aresta(es, ba);
        
        Aresta go_ba = new Aresta(go, ba);
        Aresta go_to = new Aresta(go, to);
        Aresta go_mt = new Aresta(go, mt);
        
        Aresta mt_to = new Aresta(mt, to);
        Aresta mt_pa = new Aresta(mt, pa);
        Aresta mt_ro = new Aresta(mt, ro);
        Aresta mt_am = new Aresta(mt, am);
        
        Aresta ro_ac = new Aresta(ro, ac);
        Aresta ro_am = new Aresta(ro, am);
        
        Aresta ac_am = new Aresta(ac, am);
        
        Aresta am_pa = new Aresta(am, pa);
        Aresta am_rr = new Aresta(am, rr);
        
        Aresta rr_pa = new Aresta(rr, pa);
        
        Aresta pa_ap = new Aresta(pa, ap);
        Aresta pa_to = new Aresta(pa, to);
        Aresta pa_ma = new Aresta(pa, ma);
        
        Aresta to_ba = new Aresta(to, ba);
        Aresta to_ma = new Aresta(to, ma);
        Aresta to_pi = new Aresta(to, pi);
        
        Aresta ma_pi = new Aresta(ma, pi);
        
        Aresta pi_ce = new Aresta(pi, ce);
        Aresta pi_ba = new Aresta(pi, ba);
        Aresta pi_pe = new Aresta(pi, pe);
        
        Aresta pe_ce = new Aresta(pe, ce);
        Aresta pe_al = new Aresta(pe, al);
        Aresta pe_pb = new Aresta(pe, pb);
        Aresta pe_ba = new Aresta(pe, ba);
        
        Aresta ba_se = new Aresta(ba, se);
        Aresta ba_al = new Aresta(ba, al);
        
        Aresta se_al = new Aresta(se, al);
        
        Aresta rn_ce = new Aresta(rn, ce);
        Aresta rn_pb = new Aresta(rn, pb);
        
        Aresta pb_ce = new Aresta(pb, ce);
        
        
        //testes:
        
        Aresta sc_rs = new Aresta(sc, rs);
        Aresta pr_sc = new Aresta(pr, sc);
        Aresta sp_pr = new Aresta(sp, pr);
        Aresta ms_pr = new Aresta(ms, pr);
        Aresta ms_sp = new Aresta(ms, sp);
        Aresta rj_sp = new Aresta(rj, sp);
        Aresta mg_sp = new Aresta(mg, sp);
        Aresta mt_ms = new Aresta(mt, ms);
        Aresta mg_ms = new Aresta(mg, ms);
        Aresta go_ms = new Aresta(go, ms);
        Aresta mg_rj = new Aresta(mg, rj);
        Aresta es_rj = new Aresta(es, rj);
        Aresta go_mg = new Aresta(go, mg);
        Aresta es_mg = new Aresta(es, mg);
        Aresta ba_mg = new Aresta(ba, mg);
        Aresta ba_es = new Aresta(ba, es);
        Aresta ba_go = new Aresta(ba, go);
        Aresta to_go = new Aresta(to, go);
        Aresta mt_go = new Aresta(mt, go);
        Aresta pa_mt = new Aresta(pa, mt);
        Aresta ro_mt = new Aresta(ro, mt);
        Aresta am_mt = new Aresta(am, mt);
        Aresta am_ro = new Aresta(am, ro);
        Aresta am_ac = new Aresta(am, ac);
        Aresta pa_am = new Aresta(pa, am);
        Aresta rr_am = new Aresta(rr, am);
        Aresta ap_pa = new Aresta(ap, pa);
        Aresta to_pa = new Aresta(to, pa);
        Aresta ma_pa = new Aresta(ma, pa);
        Aresta pi_to = new Aresta(pi, to);
        Aresta ba_pi = new Aresta(ba, pi);
        Aresta pe_pi = new Aresta(pe, pi);
        Aresta ce_pi = new Aresta(ce, pi);
        Aresta al_pe = new Aresta(al, pe);
        Aresta pb_pe = new Aresta(pb, pe);
        Aresta ba_pe = new Aresta(ba, pe);
        Aresta se_ba = new Aresta(se, ba);
        Aresta al_ba = new Aresta(al, ba);
        Aresta al_se = new Aresta(al, se);
        Aresta ce_rn = new Aresta(ce, rn);
        Aresta pb_rn = new Aresta(pb, rn);
        Aresta ce_pb = new Aresta(ce, pb);
        Aresta ac_ro = new Aresta(ac, ro);
        
        
        
        
        rs_sc.setPeso(490);
        sc_pr.setPeso(325);
        pr_sp.setPeso(575);
        pr_ms.setPeso(573);
        sp_ms.setPeso(893);
        sp_rj.setPeso(360);
        sp_mg.setPeso(491);
        ms_mt.setPeso(492);
        ms_mg.setPeso(391);
        ms_go.setPeso(432);
        rj_mg.setPeso(342);
        rj_es.setPeso(231);
        mg_go.setPeso(435);
        mg_es.setPeso(123);
        mg_ba.setPeso(155);
        es_ba.setPeso(431);
        go_ba.setPeso(231);
        go_to.setPeso(324);
        go_mt.setPeso(541);
        mt_to.setPeso(421);
        mt_pa.setPeso(311);
        mt_ro.setPeso(132);
        mt_am.setPeso(114);
        ro_ac.setPeso(415);
        ro_am.setPeso(712);
        ac_am.setPeso(612);
        am_pa.setPeso(431);
        am_rr.setPeso(423);
        rr_pa.setPeso(512);
        pa_ap.setPeso(431);
        pa_to.setPeso(523);
        pa_ma.setPeso(512);
        to_ba.setPeso(222);
        to_ma.setPeso(421);
        to_pi.setPeso(532);
        ma_pi.setPeso(452);
        pi_ce.setPeso(254);
        pi_ba.setPeso(543);
        pi_pe.setPeso(324);
        pe_ce.setPeso(432);
        pe_al.setPeso(324);
        pe_pb.setPeso(443);
        pe_ba.setPeso(322);
        ba_se.setPeso(112);
        ba_al.setPeso(134);
        se_al.setPeso(124);
        rn_ce.setPeso(242);
        rn_pb.setPeso(232);
        pb_ce.setPeso(331);
        sc_rs.setPeso(490);
        pr_sc.setPeso(325);
        sp_pr.setPeso(575);
        ms_pr.setPeso(573);
        ms_sp.setPeso(893);
        rj_sp.setPeso(360);
        mg_sp.setPeso(491);  
        mt_ms.setPeso(492);
        mg_ms.setPeso(391);
        go_ms.setPeso(432);
        mg_rj.setPeso(342);
        es_rj.setPeso(231);
        go_mg.setPeso(435);
        es_mg.setPeso(123);
        ba_mg.setPeso(155);
        ba_es.setPeso(431);
        ba_go.setPeso(231);
        to_go.setPeso(324);
        mt_go.setPeso(541);
        pa_mt.setPeso(421);
        ro_mt.setPeso(311);
        am_mt.setPeso(132);
        am_ro.setPeso(114);
        am_ac.setPeso(415);
        pa_am.setPeso(712);
        rr_am.setPeso(612);
        ap_pa.setPeso(431);
        to_pa.setPeso(423);
        ma_pa.setPeso(512);
        pi_to.setPeso(431);
        ba_pi.setPeso(523);
        pe_pi.setPeso(512);
        ce_pi.setPeso(222);
        al_pe.setPeso(421);
        pb_pe.setPeso(532);
        ba_pe.setPeso(452);
        se_ba.setPeso(342);
        al_ba.setPeso(332);
        al_se.setPeso(421);
        ce_rn.setPeso(242);
        pb_rn.setPeso(333);
        ce_pb.setPeso(442);
        
        
        Grafo grafo = new Grafo();

        
        grafo.adicionarVertice(rs);
        grafo.adicionarVertice(sc);
        grafo.adicionarVertice(pr);
        grafo.adicionarVertice(sp);
        grafo.adicionarVertice(ms);
        grafo.adicionarVertice(rj);
        grafo.adicionarVertice(mg);
        grafo.adicionarVertice(es);
        grafo.adicionarVertice(ba);
        grafo.adicionarVertice(go);
        grafo.adicionarVertice(to);
        grafo.adicionarVertice(mt);
        grafo.adicionarVertice(pa);
        grafo.adicionarVertice(ro);
        grafo.adicionarVertice(ac);
        grafo.adicionarVertice(am);
        grafo.adicionarVertice(rr);
        grafo.adicionarVertice(ap);
        grafo.adicionarVertice(pi);
        grafo.adicionarVertice(ma);  
        grafo.adicionarVertice(ce);
        grafo.adicionarVertice(pe);
        grafo.adicionarVertice(al);
        grafo.adicionarVertice(se);
        grafo.adicionarVertice(rn);
        grafo.adicionarVertice(pb);



   
        rs.setArestas(List.of(rs_sc, sc_rs));
        sc.setArestas(List.of(rs_sc, sc_pr, sc_rs));
        pr.setArestas(List.of(sc_pr, pr_sp, pr_ms, pr_sc));
        sp.setArestas(List.of(pr_sp, sp_ms, sp_rj, sp_mg, sp_pr));
        ms.setArestas(List.of(pr_ms, sp_ms, ms_mt, ms_mg, ms_go, ms_pr));
        rj.setArestas(List.of(sp_rj, rj_mg, rj_es, rj_sp));
        mg.setArestas(List.of(sp_mg, ms_mg, rj_mg, mg_go, mg_es, mg_ba, mg_sp));
        es.setArestas(List.of(rj_es, mg_es, es_ba, es_rj));
        ba.setArestas(List.of(mg_ba, es_ba, go_ba, to_ba, pi_ba, pe_ba, ba_se, ba_al, ba_mg));
        go.setArestas(List.of(go_ba, go_to, go_mt, ms_go, mg_go, go_ba));
        to.setArestas(List.of(to_ba, to_ma, to_pi, go_to, mt_to, pa_to, to_ba));
        mt.setArestas(List.of(go_mt, mt_to, mt_pa, mt_ro, mt_am, ms_mt, mt_go));
        pa.setArestas(List.of(mt_pa, pa_to, pa_ma, pa_ap, rr_pa, am_pa, pa_to));
        ro.setArestas(List.of(mt_ro, ro_ac, ro_am, ro_mt));
        ac.setArestas(List.of(ro_ac, ac_am, ac_ro));
        am.setArestas(List.of(mt_am, ro_am, ac_am, am_pa, am_rr, am_mt));
        rr.setArestas(List.of(am_rr, rr_pa, rr_am));
        ap.setArestas(List.of(pa_ap, ap_pa));
        pi.setArestas(List.of(to_pi, ma_pi, pi_ce, pi_ba, pi_pe, pi_to));
        ce.setArestas(List.of(pi_ce, pe_ce, rn_ce, pb_ce, ce_pi));
        pe.setArestas(List.of(pi_pe, pe_al, pe_pb, pe_ba, pe_ce, pe_pi));
        al.setArestas(List.of(se_al, ba_al, pe_al, al_ba));
        se.setArestas(List.of(ba_se, se_al, se_ba));
        rn.setArestas(List.of(rn_ce, rn_pb, rn_ce));
        pb.setArestas(List.of(pb_ce, rn_pb, pe_pb, pb_rn));


        

        return grafo;
    }
}
    
    