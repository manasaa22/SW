package com.example.SW.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SW.model.Ride;
import com.example.SW.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    @Query("SELECT R FROM Ride R where R.status=COMPLETED AND R.user.Id=:userId ORDER BY R.endTime DESC")
    public List<Ride> getCompletedRides(@Param("userId") Integer userId);
}
