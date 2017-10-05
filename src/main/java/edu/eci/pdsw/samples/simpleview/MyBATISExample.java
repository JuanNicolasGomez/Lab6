/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;

import edu.eci.pdsw.samples.entities.Consulta;

import edu.eci.pdsw.samples.entities.Eps;

import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;



import java.sql.Date;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBATISExample {
    
    
    SqlSessionFactory sessionfact = getSqlSessionFactory();
    SqlSession sqlss = sessionfact.openSession();
    PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
    List<Paciente> pacientes=pmapper.loadPacientes();


/**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage(),e);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);

        List<Paciente> pacientes=pmapper.loadPacientes();
        for(Paciente p: pacientes){
            System.out.println(p.getId());
            System.out.println(p.getTipoId());
            System.out.println(p.getNombre());
            System.out.println(p.getFechaNacimiento());
            if(p.getEps() != null){
                System.out.println(p.getEps().getNombre());
            }
            System.out.println("No de Consultas: " + p.getConsultas().size());
            
        }
        Paciente y = new Paciente(111, "CC", "Muppet", java.sql.Date.valueOf("1990-2-2"), new Eps("Compensar", "8456981")  );
        registrarNuevoPaciente(pmapper ,y);      
    }
    public void prueba(){
    }

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        pmapper.insertarPaciente(p);
        
        Consulta z=new Consulta(java.sql.Date.valueOf("2018-12-13"),"dolor de vida",5445);
        pmapper.insertConsulta(z, p.getId(), p.getTipoId(), 4546456);
        sqlss.commit();
    }
    
}
