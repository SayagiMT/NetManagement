package com.NetProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.NetProject.util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenericDAO<T, ID> {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Transaction Template (Dùng cho Thêm/Sửa/Xóa)
    protected void executeTransaction(Consumer<Session> action) {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            action.accept(session);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            throw new RuntimeException("Lỗi thao tác CSDL: " + e.getMessage(), e);
        }
    }

    // =========================
    // 🔥 Query Template (Dùng cho Truy vấn/Lấy dữ liệu)
    // =========================
    protected <R> R executeQuery(Function<Session, R> action) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return action.apply(session);
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn CSDL: " + e.getMessage());
            return null;
        }
    }


    // CREATE
    public void create(T entity) {
        executeTransaction(session -> session.persist(entity));
    }


    // READ BY ID

    public T findById(ID id) {
        return executeQuery(session -> session.find(entityClass, id));
    }

    // READ ALL

    public List<T> findAll() {
        List<T> result = executeQuery(session ->
                session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list()
        );
        return result != null ? result : Collections.emptyList();
    }

    // UPDATE

    public void update(T entity) {
        executeTransaction(session -> session.merge(entity));
    }

    // DELETE

    public void delete(T entity) {
        executeTransaction(session -> {
            T managedEntity = session.contains(entity) ? entity : session.merge(entity);
            session.remove(managedEntity);
        });
    }

    // DELETE BY ID

    public void deleteById(ID id) {
        executeTransaction(session -> {
            T entity = session.find(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
        });
    }
}