package com.example.tp2.repository;
import com.example.tp2.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/*
 * JpaRepository<Cliente, Integer>:
    "Cliente" --> le dice a Spring que este repositorio maneja la entidad Cliente
    "Integer" --> es el tipo de dato del @Id de Cliente (el campo "id").
 * 
  Al extender JpaRepository, heredamos un montón de métodos ya
  implementados, sin escribir una sola línea de SQL:
    - save(cliente)         --> INSERT o UPDATE según corresponda
    - findAll()              --> SELECT * FROM clientes
    - findById(id)           --> SELECT * FROM clientes WHERE id = ?
    - deleteById(id)         --> DELETE FROM clientes WHERE id = ?
    - count()                --> cuenta cuántas filas hay
    entre otros.
 */
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
    /*
    para el Endpoint 2 (verificar si el email
    ya existe antes de guardar).
    
     Es un "método derivado" (query method): Spring Data JPA LEE el
      nombre del método ("findBy" + "Email") y, automáticamente,
      arma la consulta SQL equivalente:
     
           SELECT * FROM clientes WHERE email = ?
     */
    /* 
     Devuelve Optional<Cliente>  porque
      puede que NO exista ningún cliente con ese email 
     */
    Optional<Cliente> findByEmail(String email);
    
}
