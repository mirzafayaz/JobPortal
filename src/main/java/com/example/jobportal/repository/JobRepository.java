
package com.example.jobportal.repository;

import com.example.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByCompanyContainingIgnoreCase(String company);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findBySalaryBetween(Double minSalary, Double maxSalary);

    @Query("""
    SELECT j FROM Job j
    WHERE (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')))
    AND (:company IS NULL OR LOWER(j.company) LIKE LOWER(CONCAT('%', :company, '%')))
    AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
    AND (:minSalary IS NULL OR j.salary >= :minSalary)
    AND (:maxSalary IS NULL OR j.salary <= :maxSalary)
""")
    List<Job> advancedSearch(
            @Param("title") String title,
            @Param("company") String company,
            @Param("location") String location,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary
    );
    Page<Job> findAll(Pageable pageable);



}

