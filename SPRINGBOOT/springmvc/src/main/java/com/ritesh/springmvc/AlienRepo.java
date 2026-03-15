package com.ritesh.springmvc;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ritesh.springmvc.model.Alien;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlienRepo extends JpaRepository<Alien,Integer> {
    public List<Alien> findByAname(String aname);    //Query DSL
    public List<Alien> findByAnameOrderByAidDesc(String aname);

    @Query("from Alien where aname= :name")
    public List<Alien> find(@Param("name") String name);
}
