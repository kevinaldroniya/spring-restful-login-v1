package spring.restful.login.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.restful.login.v1.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
