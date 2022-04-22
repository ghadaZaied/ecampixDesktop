package com.ecampix.services;

import com.ecampix.entities.Reclamation;
import com.ecampix.utils.DatabaseConnection;
import com.ecampix.utils.RelationObject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    private static ReclamationService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ReclamationService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }

    public List<Reclamation> getAll() {
        List<Reclamation> listReclamation = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation` AS x RIGHT JOIN `client` AS y ON x.client_id = y.id WHERE x.client_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReclamation.add(new Reclamation(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("client_id"), resultSet.getString("y.name")),
                        resultSet.getString("titre"),
                        resultSet.getString("text"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reclamation : " + exception.getMessage());
        }
        return listReclamation;
    }

    public List<RelationObject> getAllClients() {
        List<RelationObject> listClients = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `client`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listClients.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) clients : " + exception.getMessage());
        }
        return listClients;
    }

    public boolean checkExist(Reclamation reclamation) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation` WHERE `client_id` = ? AND `titre` = ? AND `text` = ? AND `date` = ?");

            preparedStatement.setInt(1, reclamation.getClientId().getId());
            preparedStatement.setString(2, reclamation.getTitre());
            preparedStatement.setString(3, reclamation.getText());
            preparedStatement.setDate(4, Date.valueOf(reclamation.getDate()));

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (getAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(Reclamation reclamation) {

        if (checkExist(reclamation)) {
            return false;
        }

        String request = "INSERT INTO `reclamation`(`client_id`, `titre`, `text`, `date`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reclamation.getClientId().getId());
            preparedStatement.setString(2, reclamation.getTitre());
            preparedStatement.setString(3, reclamation.getText());
            preparedStatement.setDate(4, Date.valueOf(reclamation.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Reclamation added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reclamation : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Reclamation reclamation) {

        if (checkExist(reclamation)) {
            return false;
        }

        String request = "UPDATE `reclamation` SET `client_id` = ?, `titre` = ?, `text` = ?, `date` = ? WHERE `id`=" + reclamation.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reclamation.getClientId().getId());
            preparedStatement.setString(2, reclamation.getTitre());
            preparedStatement.setString(3, reclamation.getText());
            preparedStatement.setDate(4, Date.valueOf(reclamation.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Reclamation edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reclamation : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reclamation` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Reclamation deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reclamation : " + exception.getMessage());
        }
        return false;
    }
}
