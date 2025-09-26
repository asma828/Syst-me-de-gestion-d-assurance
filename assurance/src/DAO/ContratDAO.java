package DAO;

import Database.Database;
import Models.Client;
import Models.Contrat;
import enums.TypeContrat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContratDAO {
    private ClientDAO clientDAO = new ClientDAO();

    public int create(Contrat contrat) {
        String sql = "INSERT INTO contrat (type_contrat, date_debut, date_fin, client_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClient().getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        contrat.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du contrat: " + e.getMessage());
        }
        return -1;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM contrat WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du contrat: " + e.getMessage());
        }
        return false;
    }

    public Optional<Contrat> findById(int id) {
        String sql = "SELECT * FROM contrat WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Optional<Client> client = clientDAO.findById(rs.getInt("client_id"));

                Contrat contrat = new Contrat(
                        rs.getInt("id"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        client.orElse(null)
                );

                return Optional.of(contrat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du contrat: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Contrat> findByClientId(int clientId) {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT * FROM contrat WHERE client_id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Optional<Client> client = clientDAO.findById(clientId);

                Contrat contrat = new Contrat(
                        rs.getInt("id"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        client.orElse(null)
                );

                contrats.add(contrat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des contrats par client: " + e.getMessage());
        }
        return contrats;
    }

}
