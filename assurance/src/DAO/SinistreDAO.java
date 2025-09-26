package DAO;

import Models.Contrat;
import Models.Sinistre;
import enums.TypeSinistre;
import Database.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SinistreDAO {
    private ContratDAO contratDAO = new ContratDAO();

    public int create(Sinistre sinistre) {
        String sql = "INSERT INTO sinistre (type_sinistre, date_time, cout, description, contrat_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(2, Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(3, sinistre.getCout());
            stmt.setString(4, sinistre.getDescription());
            stmt.setInt(5, sinistre.getContrat().getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        sinistre.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du sinistre: " + e.getMessage());
        }
        return -1;
    }

    public Optional<Sinistre> findById(int id) {
        String sql = "SELECT * FROM sinistre WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int contratId = rs.getInt("contrat_id");
                Optional<Contrat> contrat = contratDAO.findById(contratId);

                Sinistre sinistre = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        contrat.orElse(null)
                );

                return Optional.of(sinistre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du sinistre: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Sinistre> findAll() {
        List<Sinistre> sinistres = new ArrayList<>();
        String sql = "SELECT * FROM sinistre";
        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int contratId = rs.getInt("contrat_id");
                Optional<Contrat> contrat = contratDAO.findById(contratId);

                Sinistre sinistre = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        contrat.orElse(null) // if contrat not found, pass null
                );

                sinistres.add(sinistre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des sinistres: " + e.getMessage());
        }
        return sinistres;
    }


    public List<Sinistre> findByContratId(int contratId) {
        List<Sinistre> sinistres = new ArrayList<>();
        String sql = "SELECT * FROM sinistre WHERE contrat_id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contratId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Optional<Contrat> contrat = contratDAO.findById(contratId);

                Sinistre sinistre = new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        contrat.orElse(null)
                );

                sinistres.add(sinistre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des sinistres par contrat: " + e.getMessage());
        }
        return sinistres;
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM sinistre WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du sinistre: " + e.getMessage());
        }
        return false;
    }
}
