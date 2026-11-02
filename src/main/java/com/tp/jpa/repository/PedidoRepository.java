package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.List;

public class PedidoRepository extends BaseRepository<Pedido>{

    public PedidoRepository() {
        super(Pedido.class);
    }

    public List<Pedido> buscarPorEstado(Estado estado){
        if(estado == null){
            return Collections.emptyList();
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Consulta JPQL: retorna todos los pedidos activos con un estado específico.
            // Útil para filtrar PENDIENTE, CONFIRMADO, TERMINADO o CANCELADO.
            String jpql = "SELECT p FROM Pedido p WHERE p.estado = :estado AND p.eliminado = false";
            List<Pedido> pedidos = em.createQuery(jpql, Pedido.class)
                    .setParameter("estado", estado)
                    .getResultList();
            return pedidos;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar pedidos por estado: " + estado, e);
        }finally {
            em.close();
        }
    }
}
