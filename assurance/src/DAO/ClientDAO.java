package DAO;

import Database.Database;
import Models.Client;
import Models.Conseiller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO {
    private ConseillerDAO conseillerDAO = new ConseillerDAO();

    public int create(Client client) {
        String sql = "INSERT INTO client(nom,prenom,email,conseiller_id) VALUES (?,?,?,?)";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getConseiller().getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        client.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du client: " + e.getMessage());
        }
        return -1;
    }

    public Optional<Client> findById(int id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int conseillerId = rs.getInt("conseiller_id");
                Optional<Conseiller> conseiller = conseillerDAO.findById(conseillerId);

                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        conseiller.orElse(null)
                );

                return Optional.of(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int conseillerId = rs.getInt("conseiller_id");
                Optional<Conseiller> conseiller = conseillerDAO.findById(conseillerId);

                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        conseiller.orElse(null)
                );

                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des clients: " + e.getMessage());
        }
        return clients;
    }

    public List<Client> findByConseillerId(int conseillerId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE conseiller_id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conseillerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Optional<Conseiller> conseiller = conseillerDAO.findById(conseillerId);

                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        conseiller.orElse(null)
                );

                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des clients par conseiller: " + e.getMessage());
        }
        return clients;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
        }
        return false;
    }
}
