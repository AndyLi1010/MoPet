package tw.com.MoPet.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface employeeRepository extends JpaRepository<employee, Integer> {

}
