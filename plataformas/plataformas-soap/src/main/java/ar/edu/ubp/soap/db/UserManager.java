package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.soap.beans.AssociationRequestBean;
import ar.edu.ubp.soap.beans.NewAssociationRequestBean;
import ar.edu.ubp.soap.beans.NewPlatformUserBean;
import ar.edu.ubp.soap.beans.NewSessionBean;
import ar.edu.ubp.soap.beans.PlatformUserBean;
import ar.edu.ubp.soap.beans.SessionBean;
import ar.edu.ubp.soap.resources.utils.AuthUrlGenerator;

public class UserManager {

    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    public PlatformUserBean creatPlatformUser(NewPlatformUserBean newPlatformUser) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL dbo.CreatePlatformUser(?,?,?,?,?)}")) {

            conn.setAutoCommit(false);

            stmt.setString(1, newPlatformUser.getFirstname());
            stmt.setString(2, newPlatformUser.getLastname());
            stmt.setString(3, newPlatformUser.getEmail());
            stmt.setString(4, newPlatformUser.getPassword());
            stmt.setString(5, newPlatformUser.getPhone());

            // Se ejecuta la consulta

            try (ResultSet rs = stmt.executeQuery();) {
                PlatformUserBean user = null;
                // Se verifica si hay resultados y se crea el objeto AssociationRequestBean
                if (rs.next()) {
                    user = PlatformUserBean.builder()
                            .userId(rs.getInt("userId"))
                            .firstname(rs.getString("firstname"))
                            .lastname(rs.getString("lastname"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .phone(rs.getString("phone"))
                            .build();
                } else {
                    throw new Fault(new Exception("Invalid user credentials"));
                }

                conn.commit();

                return user;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public PlatformUserBean getUserByEmail(String email) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL dbo.GetPlatformUserByEmail(?)}")) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery();) {
                PlatformUserBean user = null;

                if (rs.next()) {
                    user = PlatformUserBean.builder()
                            .userId(rs.getInt("userId"))
                            .firstname(rs.getString("firstname"))
                            .lastname(rs.getString("lastname"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .phone(rs.getString("phone"))
                            .build();
                }

                return user;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public AssociationRequestBean createAssociationRequest(NewAssociationRequestBean newAssociationRequest,
            Integer serviceId) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL dbo.CreateAssociationRequest(?,?,?,?,?)}")) {

            conn.setAutoCommit(false);
            Date requestedAt = new Date();

            String uuid = AuthUrlGenerator.generateUuid();
            stmt.setInt(1, serviceId);
            stmt.setString(2, newAssociationRequest.getAssociationType());
            stmt.setString(3, uuid);
            stmt.setString(4, newAssociationRequest.getRedirectUrl());
            stmt.setDate(5, new java.sql.Date(requestedAt.getTime()));

            // Se ejecuta la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                AssociationRequestBean associationRequest = null;
                // Se verifica si hay resultados y se crea el objeto AssociationRequestBean
                if (rs.next()) {
                    associationRequest = AssociationRequestBean.builder()
                            .associationId(rs.getInt("associationId"))
                            .serviceId(rs.getInt("serviceId"))
                            .associationType(rs.getString("associationType"))
                            .state(rs.getString("state"))
                            .authUrl(AuthUrlGenerator.generateAuthUrl(rs.getString("associationType"),
                                    rs.getString("uuid")))
                            .redirectUrl(rs.getString("redirectUrl"))
                            .requestedAt(rs.getDate("requestedAt"))
                            .build();
                }
                conn.commit();

                return associationRequest;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public AssociationRequestBean completeAssociationRequest(Integer userId, String uuid) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL SetUserToken(?,?)}")) {

            stmt.setInt(1, userId);
            stmt.setString(2, uuid);

            try (ResultSet rs = stmt.executeQuery()) {
                AssociationRequestBean associationRequest = null;
                // Se verifica si hay resultados y se crea el objeto AssociationRequestBean
                if (rs.next()) {
                    associationRequest = AssociationRequestBean.builder()
                            .associationId(rs.getInt("associationId"))
                            .serviceId(rs.getInt("serviceId"))
                            .associationType(rs.getString("associationType"))
                            .state(rs.getString("state"))
                            .authUrl(AuthUrlGenerator.generateAuthUrl(rs.getString("associationType"),
                                    rs.getString("uuid")))
                            .redirectUrl(rs.getString("redirectUrl"))
                            .requestedAt(rs.getDate("requestedAt"))
                            .userId(rs.getInt("userId"))
                            .userToken(rs.getString("userToken"))
                            .build();
                }

                return associationRequest;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public AssociationRequestBean getAssociationData(Integer associationId) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL GetAssociationRequestById(?)}")) {

            stmt.setInt(1, associationId);

            try (ResultSet rs = stmt.executeQuery()) {
                AssociationRequestBean associationRequest = null;
                // Se verifica si hay resultados y se crea el objeto AssociationRequestBean
                if (rs.next()) {
                    associationRequest = AssociationRequestBean.builder()
                            .associationId(rs.getInt("associationId"))
                            .serviceId(rs.getInt("serviceId"))
                            .associationType(rs.getString("associationType"))
                            .state(rs.getString("state"))
                            .authUrl(AuthUrlGenerator.generateAuthUrl(rs.getString("associationType"),
                                    rs.getString("uuid")))
                            .redirectUrl(rs.getString("redirectUrl"))
                            .requestedAt(rs.getDate("requestedAt"))
                            .userId(rs.getInt("userId"))
                            .userToken(rs.getString("userToken"))
                            .build();
                }

                return associationRequest;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public SessionBean createSession(NewSessionBean newSession, Integer serviceId, Integer userId) throws Exception {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL dbo.CreateSession(?,?,?,?)}")) {

            conn.setAutoCommit(false);

            Date createdAt = new Date();

            stmt.setInt(1, serviceId);
            stmt.setInt(2, userId);
            stmt.setString(3, newSession.getFilmCode());
            stmt.setDate(4, new java.sql.Date(createdAt.getTime()));

            // Se ejecuta la consulta

            try (ResultSet rs = stmt.executeQuery()) {
                SessionBean session = null;
                // Se verifica si hay resultados y se crea el objeto AssociationRequestBean
                if (rs.next()) {
                    session = SessionBean.builder()
                            .sessionId(rs.getInt("sessionId"))
                            .associationId(rs.getInt("associationId"))
                            .filmCode(rs.getString("filmCode"))
                            .sessionUrl(rs.getString("sessionUrl"))
                            .createdAt(rs.getDate("createdAt"))
                            .usedAt(rs.getDate("usedAt"))
                            .expired(rs.getBoolean("expired"))
                            .build();
                }

                conn.commit();

                return session;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }
}
