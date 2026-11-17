package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioRepository {
    private Dao<Funcionario, Integer> dao;

    public FuncionarioRepository() {
        try {
            ConnectionSource connectionSource = Database.getConnection();
            TableUtils.createTableIfNotExists(connectionSource, Funcionario.class);
            dao = DaoManager.createDao(connectionSource, Funcionario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarOuAtualizar(Funcionario funcionario) throws SQLException {
        dao.createOrUpdate(funcionario);
    }

    public List<Funcionario> listarTodos() throws SQLException {
        return dao.queryForAll();
    }
    
    //coloquei o delete para não excluir o ID, mas dar valores nulos a tudo, como orientado
    public void deletar(Funcionario funcionario) throws SQLException {
        funcionario.setNome(null);
        funcionario.setCargo(null);
        funcionario.setSalario(null);
        dao.update(funcionario);
    }
}