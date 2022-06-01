package com.CRS.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.CRS.Model.ComplaintModel;
import com.CRS.Model.CrimeTypeModel;
@Repository
public interface ComplaintsRepository extends JpaRepository<ComplaintModel, Long>{

	List<ComplaintModel> findByCitizenName(String citizenName);
	
	@Modifying(clearAutomatically = true)
    @Query(value="UPDATE complaint_info SET status= :status WHERE gd_no= :gdNo",nativeQuery=true)
	int updateStatus(String gdNo, String status);

}
