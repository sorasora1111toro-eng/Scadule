package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<DEMO, Long> {
    List<DEMO> findByDateOrderByTitle(String date);
    List<DEMO> findAllByOrderByDate();
}
