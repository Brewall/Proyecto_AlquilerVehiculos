package dao;

import model.Persona;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    // Crear una nueva persona
    public int createPersona(Persona persona) {
        String query = "INSERT INTO Persona (nombres, apellido_paterno, apellido_materno, dni, direccion, telefono, correo, genero, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGenerada = -1;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidoPaterno());
            ps.setString(3, persona.getApellidoMaterno());
            ps.setString(4, persona.getDni());
            ps.setString(5, persona.getDireccion());
            ps.setString(6, persona.getTelefono());
            ps.setString(7, persona.getCorreo());
            ps.setString(8, persona.getGenero());
            ps.setString(9, persona.getFechaNacimiento());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerada = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerada;
    }

    // Obtener todas las personas
    public List<Persona> getAllPersonas() {
        List<Persona> personas = new ArrayList<>();
        String query = "SELECT * FROM Persona";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona persona = new Persona();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidoPaterno(rs.getString("apellido_paterno"));
                persona.setApellidoMaterno(rs.getString("apellido_materno"));
                persona.setDni(rs.getString("dni"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));
                persona.setGenero(rs.getString("genero"));
                persona.setFechaNacimiento(rs.getString("fecha_nacimiento"));

                personas.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personas;
    }

    // Obtener una persona por ID
    public Persona getPersonaById(int idPersona) {
        Persona persona = null;
        String query = "SELECT * FROM Persona WHERE id_persona = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idPersona);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    persona = new Persona();
                    persona.setIdPersona(rs.getInt("id_persona"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidoPaterno(rs.getString("apellido_paterno"));
                    persona.setApellidoMaterno(rs.getString("apellido_materno"));
                    persona.setDni(rs.getString("dni"));
                    persona.setDireccion(rs.getString("direccion"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setCorreo(rs.getString("correo"));
                    persona.setGenero(rs.getString("genero"));
                    persona.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persona;
    }

    // Actualizar una persona
    public boolean updatePersona(Persona persona) {
        String query = "UPDATE Persona SET nombres = ?, apellido_paterno = ?, apellido_materno = ?, dni = ?, direccion = ?, telefono = ?, correo = ?, genero = ?, fecha_nacimiento = ? WHERE id_persona = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidoPaterno());
            ps.setString(3, persona.getApellidoMaterno());
            ps.setString(4, persona.getDni());
            ps.setString(5, persona.getDireccion());
            ps.setString(6, persona.getTelefono());
            ps.setString(7, persona.getCorreo());
            ps.setString(8, persona.getGenero());
            ps.setString(9, persona.getFechaNacimiento());
            ps.setInt(10, persona.getIdPersona());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una persona
    public boolean deletePersona(int idPersona) {
        String query = "DELETE FROM Persona WHERE id_persona = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idPersona);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
