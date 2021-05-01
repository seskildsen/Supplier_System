package finalproject.suppliersystem.supplier.repository;

import finalproject.suppliersystem.supplier.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long>
{
}
