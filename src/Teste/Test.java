package Teste;

import Model.Aresta;
import Model.Dijkstra;
import Model.Grafo;
import Model.Vertice;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Vertice rs = new Vertice();
        rs.setNomeCidade("Rio Grande do Sul");

        Vertice sc = new Vertice();
        sc.setNomeCidade("Santa Catarina");

        Vertice pr = new Vertice();
        pr.setNomeCidade("Paraná");

        Vertice sp = new Vertice();
        sp.setNomeCidade("São Paulo");

        Vertice ms = new Vertice();
        ms.setNomeCidade("Mato Grosso do Sul");

        Vertice mt = new Vertice();
        mt.setNomeCidade("Mato Grosso");

        Vertice go = new Vertice();
        go.setNomeCidade("Goiás");

        // Criando arestas
        Aresta rs_sc = new Aresta(rs, sc);
        Aresta sc_pr = new Aresta(sc, pr);
        Aresta pr_sp = new Aresta(pr, sp);

        Aresta sp_ms = new Aresta(sp, ms);
        Aresta ms_mt = new Aresta(ms, mt);
        Aresta mt_go = new Aresta(mt, go);
        Aresta ms_go = new Aresta(ms, go);

        rs_sc.setPeso(10);
        sc_pr.setPeso(20);
        pr_sp.setPeso(10);

        sp_ms.setPeso(15);
        ms_mt.setPeso(12);
        mt_go.setPeso(8);
        ms_go.setPeso(12);

        Grafo grafo = new Grafo();

        grafo.adicionarVertice(rs);
        grafo.adicionarVertice(sc);
        grafo.adicionarVertice(pr);
        grafo.adicionarVertice(sp);
        grafo.adicionarVertice(ms);
        grafo.adicionarVertice(mt);
        grafo.adicionarVertice(go);

        rs.setArestas(List.of(rs_sc));
        sc.setArestas(List.of(rs_sc, sc_pr));
        pr.setArestas(List.of(sc_pr, pr_sp));
        
        sp.setArestas(List.of(pr_sp, sp_ms));
        ms.setArestas(List.of(sp_ms, ms_mt, ms_go));
        mt.setArestas(List.of(ms_mt, mt_go));
        

        Dijkstra dijkstra = new Dijkstra();
        List<Vertice> menorCaminho = dijkstra.encontrarMenorCaminhoDijkstra(grafo, rs, go);

        System.out.println("Menor caminho de " + rs.getNomeCidade() + " para " + go.getNomeCidade() + ":");
        for (Vertice vertice : menorCaminho) {
            System.out.print(vertice.getNomeCidade() + " -> ");
        }
        System.out.println("\nCusto total: " + go.getDistancia());
    }
}
