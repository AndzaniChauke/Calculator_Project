package com.example.Calculator_Project.repository;

import com.example.Calculator_Project.model.AppUser;
import com.example.Calculator_Project.model.Calculations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CalculationsRepository extends JpaRepository<Calculations,Long> {

    @Query(value="SELECT u FROM Calculations u WHERE u.appUser=?1")
    List<Calculations> findAppUserById(AppUser appUser);

    @Query("SELECT c FROM Calculations c WHERE " +
            "((:email IS NULL OR :email = '') OR c.appUser.email LIKE %:email%) AND " +
            "((:fromTime IS NULL OR :fromTime = '') OR c.formattedDateTime >= :fromTime) AND " +
            "((:toTime IS NULL OR   :toTime = '') OR c.formattedDateTime <= :toTime)")
    List<Calculations> searchWithLikeAndTimeRange(
            @Param("email") String email,
            @Param("fromTime") String fromTime, // Accept time as a string
            @Param("toTime") String toTime);
}
