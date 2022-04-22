package com.ecampix.services;

import com.ecampix.entities.Reponse;
import com.ecampix.utils.DatabaseConnection;
import com.ecampix.utils.RelationObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReponseService {

    private static ReponseService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ReponseService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReponseService getInstance() {
        if (instance == null) {
            instance = new ReponseService();
        }
        return instance;
    }

    public List<Reponse> getAll() {
        List<Reponse> listReponse = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse` AS x RIGHT JOIN `reclamation` AS y ON x.reclamation_id = y.id RIGHT JOIN `admin` AS a ON x.admin_id = a.id WHERE x.reclamation_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReponse.add(new Reponse(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("admin_id"), resultSet.getString("a.nom")),
                        new RelationObject(resultSet.getInt("reclamation_id"), resultSet.getString("y.titre")),
                        resultSet.getString("text")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reponse : " + exception.getMessage());
        }
        return listReponse;
    }

    public List<RelationObject> getAllReclamations() {
        List<RelationObject> listReclamations = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listReclamations.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("titre")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reclamations : " + exception.getMessage());
        }
        return listReclamations;
    }

    public List<RelationObject> getAllAdmins() {
        List<RelationObject> listReclamations = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `admin`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listReclamations.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("nom")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) admin : " + exception.getMessage());
        }
        return listReclamations;
    }

    public boolean checkExist(Reponse reponse) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse` WHERE `admin_id` = ? AND `reclamation_id` = ? AND `text` = ?");

            preparedStatement.setInt(1, reponse.getAdmin().getId());
            preparedStatement.setInt(2, reponse.getReclamationId().getId());
            preparedStatement.setString(3, reponse.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (getAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(Reponse reponse) {

        if (checkExist(reponse)) {
            return false;
        }

        String request = "INSERT INTO `reponse`(`admin_id`, `reclamation_id`, `text`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reponse.getAdmin().getId());
            preparedStatement.setInt(2, reponse.getReclamationId().getId());
            preparedStatement.setString(3, reponse.getText());

            preparedStatement.executeUpdate();
            System.out.println("Reponse added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reponse : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Reponse reponse) {

        if (checkExist(reponse)) {
            return false;
        }

        String request = "UPDATE `reponse` SET `admin_id` = ?, `reclamation_id` = ?, `text` = ? WHERE `id`=" + reponse.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reponse.getAdmin().getId());
            preparedStatement.setInt(2, reponse.getReclamationId().getId());
            preparedStatement.setString(3, reponse.getText());

            preparedStatement.executeUpdate();
            System.out.println("Reponse edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reponse : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reponse` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Reponse deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reponse : " + exception.getMessage());
        }
        return false;
    }
}
