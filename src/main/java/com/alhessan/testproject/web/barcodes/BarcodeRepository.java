package com.alhessan.testproject.web.barcodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("barcodeRepository")
public interface BarcodeRepository  extends CrudRepository<Barcode, String> {


//   @Query("SELECT b.* FROM barcodes b WHERE b.user_id = ?1")
    List<Barcode> findByUserId(long user_id);
}
