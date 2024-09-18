package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Supplier;

import java.util.List;
//  فهد المسلم
@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Supplier findSupplierById(Integer id);

    @Query("SELECT s FROM Supplier s WHERE s.badge = 'GOLD'")
    List<Supplier> findAllByBadgeGold();

    @Query("SELECT s FROM Supplier s WHERE s.badge = :badge")
    List<Supplier> findSuppliersByBadge(@Param("badge") String badge);


}
