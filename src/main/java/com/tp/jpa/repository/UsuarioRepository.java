package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Optional<Usuario> buscarPorMail(String mail){
        if(mail == null || mail.trim().isEmpty()){
            return Optional.empty();
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Consulta JPQL: busca un usuario activo por su dirección de correo electrónico.
            // Retorna Optional para manejar el caso en que el mail no esté registrado.
            String jpql = "SELECT u FROM Usuario u WHERE u.mail = :mail AND u.eliminado = false";
            TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
            q.setParameter("mail", mail);
            List<Usuario> res = q.getResultList();
            return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
        }finally {
            em.close();
        }
    }

    public List<Pedido> buscarPedidosPorUsuario(Long idUsuario) {
        if(idUsuario == null || idUsuario <= 0){
            return Collections.emptyList();
        }

        EntityManager em = JPAUtil.getEntityManager();

        try {
            // Consulta JPQL: retorna los pedidos activos de un usuario.
            // Como la relación es unidireccional y Usuario es el dueño, se navega
            // desde Usuario hacia su colección u.pedidos mediante JOIN.
            // Se filtra por el id del usuario (:uid) y por p.eliminado = false
            // para excluir las bajas lógicas.
            String jpql = "SELECT p FROM Usuario u JOIN u.pedidos p "
                    + "WHERE u.id = :uid AND p.eliminado = false";
            List<Pedido> pedidos = em.createQuery(jpql, Pedido.class)
                    .setParameter("uid", idUsuario)
                    .getResultList();
            return pedidos;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar pedidos para el usuario con ID: " + idUsuario, e);
        }finally{
            em.close();
        }
    }

}
